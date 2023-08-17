package com.hy.im.service.mq;

import com.alibaba.fastjson.JSONObject;
import com.hy.im.common.constant.RabbitConstants;
import com.hy.im.common.enums.command.GroupEventCommand;
import com.hy.im.common.model.message.GroupChatMessageContent;
import com.hy.im.service.message.service.GroupMessageService;
import com.hy.im.service.message.service.MessageSyncService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @ClassName GroupChatOperateReceiver
 * description:群聊消息监听
 * yao create 2023年08月16日
 * version: 1.0
 */
@Component
@Slf4j
public class GroupChatOperateReceiver {

    @Autowired
    private GroupMessageService groupMessageService;

    @Autowired
    private MessageSyncService messageSyncService;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = RabbitConstants.IM_2_GROUP_SERVICE,declare = "true"),
                    exchange = @Exchange(value = RabbitConstants.IM_2_GROUP_SERVICE,declare = "true")),concurrency = "1"
    )
    public void onChatMessage(@Payload Message message, @Headers Map<String,Object> headers, Channel channel) throws Exception {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("CHAT MSG FORM QUEUE ::: {}", msg);
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try{
            JSONObject jsonObject = JSONObject.parseObject(msg);
            Integer command = jsonObject.getInteger("command");
            if(command.equals(GroupEventCommand.MSG_GROUP.getCommand())){
                //处理消息
                GroupChatMessageContent messageContent = jsonObject.toJavaObject(GroupChatMessageContent.class);
                groupMessageService.process(messageContent);
            }
            channel.basicAck(deliveryTag, false);
        }catch (Exception e){
            log.error("处理消息出现异常：{}", e.getMessage(),e);
            //第一个false 表示不批量拒绝，第二个false表示不重回队列
            channel.basicNack(deliveryTag, false, false);
        }
    }
}
