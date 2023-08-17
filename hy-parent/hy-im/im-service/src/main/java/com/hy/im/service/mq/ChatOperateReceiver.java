package com.hy.im.service.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.hy.im.common.constant.RabbitConstants;
import com.hy.im.common.enums.command.MessageCommand;
import com.hy.im.common.model.message.MessageContent;
import com.hy.im.common.model.message.MessageReadedContent;
import com.hy.im.common.model.message.MessageReciveAckContent;
import com.hy.im.common.model.message.RecallMessageContent;
import com.hy.im.service.message.service.MessageSyncService;
import com.hy.im.service.message.service.P2PMessageService;
import com.hy.im.service.message.service.VisitorMessageService;
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
 * @ClassName ChatOperateReceiver
 * description: 聊天操作消息监听
 * yao create 2023年07月03日
 * version: 1.0
 */
@Component
@Slf4j
public class ChatOperateReceiver {

    @Autowired
    private P2PMessageService p2PMessageService;

    @Autowired
    private VisitorMessageService visitorMessageService;


    @Autowired
    private MessageSyncService messageSyncService;

    /**
     * // 使用 @Payload 和 @Headers 注解可以消息中的 body 与 headers 信息
     */

    @RabbitListener(
            bindings =@QueueBinding(
                value = @Queue(value = RabbitConstants.IM_2_MESSAGE_SERVICE,durable = "true"),
                exchange = @Exchange(value = RabbitConstants.IM_2_MESSAGE_SERVICE,durable = "true")
            ),concurrency = "1"
    )
    public void onChatMessage(@Payload Message message,
                              @Headers Map<String,Object> headers,
                              Channel channel) throws Exception {

        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("IM服务发送给逻辑层的消息处理! queue：{},msg:{}",RabbitConstants.IM_2_MESSAGE_SERVICE,msg);
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);

        try {
            JSONObject jsonObject = JSONObject.parseObject(msg);
            Integer command = jsonObject.getInteger("command");
            if(command.equals(MessageCommand.MSG_P2P.getCommand())){
                // 单聊消息处理
                log.info("单聊消息处理");
                MessageContent messageContent = jsonObject.toJavaObject(MessageContent.class);
                p2PMessageService.process(messageContent);
            }else if (command.equals(MessageCommand.MSG_RECIVE_ACK.getCommand())){
                // 接受消息确认
                log.info("消息收到ack");
                MessageReciveAckContent messageReciveAckContent = jsonObject.toJavaObject(MessageReciveAckContent.class);
                messageSyncService.receiveMark(messageReciveAckContent);
            } else if (command.equals(MessageCommand.MSG_VISITOR_RECIVE_ACK.getCommand())){
                // 接受消息确认
                log.info("消息收到ack");
                MessageReciveAckContent messageReciveAckContent = jsonObject.toJavaObject(MessageReciveAckContent.class);
                messageSyncService.receiveVisitorMark(messageReciveAckContent);
            }else if (command.equals(MessageCommand.MSG_READED.getCommand())) {
                // 已读消息
                log.info("已读消息处理");
                MessageReadedContent messageReadedContent = jsonObject.toJavaObject(MessageReadedContent.class);
                messageSyncService.readMark(messageReadedContent);

            } else if (command.equals(MessageCommand.MSG_VISITOR.getCommand())) {
                // 访客消息处理
                log.info("访客消息处理");
                MessageContent messageContent = jsonObject.toJavaObject(MessageContent.class);
                visitorMessageService.process(messageContent);

            }

            channel.basicAck(deliveryTag,false);
        } catch (Exception e) {
            log.error("处理消息出现异常：{}", e.getMessage(),e);
            //第一个false 表示不批量拒绝，第二个false表示不重回队列
            channel.basicNack(deliveryTag, false, false);
        }


    }

}
