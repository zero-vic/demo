package com.hy.im.server.publish;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hy.im.codec.proto.Message;
import com.hy.im.codec.proto.MessageHeader;
import com.hy.im.common.constant.RabbitConstants;
import com.hy.im.common.enums.command.CommandType;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


/**
 * @ClassName MqMessageProducer
 * description: mq 消息 生产类 (给用户发消息)
 * yao create 2023年06月29日
 * version: 1.0
 */
@Slf4j
@Component
public class MqMessageProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private static RabbitTemplate rabbitTemplateStatic;
    @PostConstruct
    private void initMqMessage(){
        rabbitTemplateStatic = this.rabbitTemplate;
    }

    public static void sendMessage(Message message, Integer command){
        Channel channel = null;
        String commandStr = command.toString();
        String commandSub = commandStr.substring(0, 1);
        CommandType commandType = CommandType.getCommandType(commandSub);
        String channelName = "";
        if(commandType == CommandType.MESSAGE){
            channelName = RabbitConstants.IM_2_MESSAGE_SERVICE;
        } else if (commandType == CommandType.GROUP) {
            channelName = RabbitConstants.IM_2_GROUP_SERVICE;
        } else if (commandType == CommandType.FRIEND) {
            channelName = RabbitConstants.IM_2_FRIENDSHIP_SERVICE;
        } else if (commandType == CommandType.USER) {
            channelName = RabbitConstants.IM_2_USER_SERVICE;
        }

        try {
            JSONObject json = (JSONObject) JSONObject.toJSON(message.getMessagePack());
            json.put("command",command);
            json.put("clientType",message.getMessageHeader().getClientType());
            json.put("imei",message.getMessageHeader().getImei());
            json.put("appId",message.getMessageHeader().getAppId());
//            json.put("siteId",message.getMessageHeader().getSiteId());
            rabbitTemplateStatic.convertAndSend(channelName,"",json.toJSONString());

        } catch (Exception e) {
            log.error("发送消息出现异常：{}",e.getMessage(),e);
        }
    }
    public static void sendMessage(Object message, MessageHeader header, Integer command){
        Channel channel = null;
        String com = command.toString();
        String commandSub = com.substring(0, 1);
        CommandType commandType = CommandType.getCommandType(commandSub);
        String channelName = "";
        if(commandType == CommandType.MESSAGE){
            channelName = RabbitConstants.IM_2_MESSAGE_SERVICE;
        } else if (commandType == CommandType.GROUP) {
            channelName = RabbitConstants.IM_2_GROUP_SERVICE;
        } else if (commandType == CommandType.FRIEND) {
            channelName = RabbitConstants.IM_2_FRIENDSHIP_SERVICE;
        } else if (commandType == CommandType.USER) {
            channelName = RabbitConstants.IM_2_USER_SERVICE;
        }

        try {

            JSONObject o = (JSONObject) JSON.toJSON(message);
            o.put("command",command);
            o.put("clientType",header.getClientType());
            o.put("imei",header.getImei());
            o.put("appId",header.getAppId());
//            o.put("siteId",header.getSiteId());
            rabbitTemplateStatic.convertAndSend(channelName,"",o.toJSONString());
        }catch (Exception e){
            log.error("发送消息出现异常：{}",e.getMessage(),e);
        }
    }

}
