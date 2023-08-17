package com.hy.im.server.reciver;

import com.alibaba.fastjson.JSONObject;
import com.hy.im.codec.proto.MessagePack;
import com.hy.im.common.constant.RabbitConstants;
import com.hy.im.server.reciver.process.BaseProcess;
import com.hy.im.server.reciver.process.ProcessFactory;
import com.rabbitmq.client.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @ClassName MessageReceiver
 * description: 消息接收类(监听)
 * yao create 2023年06月29日
 * version: 1.0
 */
@Slf4j
@Component
public class MessageReceiver {
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = RabbitConstants.MESSAGE_SERVICE_2_IM + 1000, durable = "true"),
                    exchange = @Exchange(value = RabbitConstants.MESSAGE_SERVICE_2_IM + 1000, durable = "true"),
                    key = "1000"
            ), concurrency = "1"
    )
    public void serverReceiverMessage(@Payload Message message,
                                      @Headers Map<String, Object> headers,
                                      Channel channel) throws Exception {
        Long deliveryTag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            String msg = new String(message.getBody(), StandardCharsets.UTF_8);
            log.info(" 处理逻辑层发来的消息！ msg: {} ", msg);
            MessagePack messagePack = JSONObject.parseObject(msg, MessagePack.class);
            BaseProcess massageProcess = ProcessFactory.getMassageProcess(messagePack.getCommand());
            massageProcess.process(messagePack);
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            log.error("消息处理异常：{}", e.getMessage(), e);
            channel.basicNack(deliveryTag, false, false);
        }
    }


}
