package com.hy.im.service.message.service;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.hy.im.codec.pack.message.ChatMessageAck;
import com.hy.im.codec.pack.message.MessageReciveServerAckPack;
import com.hy.im.common.enums.ConversationTypeEnum;
import com.hy.im.common.enums.DelFlagEnum;
import com.hy.im.common.enums.command.MessageCommand;
import com.hy.im.common.model.ClientInfo;
import com.hy.im.common.model.message.MessageContent;
import com.hy.im.common.model.message.MessageConversationContent;
import com.hy.im.common.model.message.MessageOfflineContent;
import com.hy.im.common.response.ResponseVO;
import com.hy.im.service.message.producer.MessageProducer;
import com.hy.im.service.utils.SnowflakeIdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName P2PMessageService
 * description: 单聊消息处理
 * yao create 2023年07月03日
 * version: 1.0
 */
@Service
@Slf4j
public class P2PMessageService {


    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private MessageStoreService messageStoreService;

    @Autowired
    private ConversationService conversationService;






    /**
     * 私有线程池
     */
    private final ThreadPoolExecutor threadPoolExecutor;

    {
        final AtomicInteger num = new AtomicInteger(0);
        threadPoolExecutor = new ThreadPoolExecutor(8, 8, 60, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(1000), new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                thread.setName("message-process-thread-" + num.getAndIncrement());
                return thread;
            }
        });
    }

    /**
     *
     * @param messageContent
     */
    public void process(MessageContent messageContent){
        log.info("消息开始处理：{}",messageContent.getMessageId());

        // 生成会话
        MessageConversationContent conversationContent = new MessageConversationContent();
        BeanUtils.copyProperties(messageContent,conversationContent);
        conversationContent.setConversationType(ConversationTypeEnum.P2P.getCode());
        conversationService.generateConversation(conversationContent);
        // 插入离线消息

        MessageOfflineContent messageOfflineContent = new MessageOfflineContent();
        messageOfflineContent.setMessageId(SnowflakeIdWorker.getId());
        messageOfflineContent.setAppId(messageContent.getAppId());
        messageOfflineContent.setSiteId(messageContent.getSiteId());
        messageOfflineContent.setPostId(messageContent.getFromId());
        messageOfflineContent.setReceiveId(messageContent.getToId());
        messageOfflineContent.setSendtime(LocalDateTime.now());
        messageOfflineContent.setIsdel(DelFlagEnum.NORMAL.getCode());
        messageOfflineContent.setConversationType(ConversationTypeEnum.P2P.getCode());
        JSONObject msg = JSONObject.parseObject(messageContent.getMessageBody());
        messageOfflineContent.setMsg(msg.getString("content"));
        messageOfflineContent.setMsgtype(msg.getInteger("type"));
        messageStoreService.storeOfflineMessage1(messageOfflineContent);



        // msg 持久化
            messageStoreService.storeMessage(messageOfflineContent);
//            //1.回ack成功给自己
            log.info("回ack 成功给自己");
            ack(messageContent,ResponseVO.successResponse());
            //2.发消息给同步在线端
//            log.info("发消息给自己的同步在线端");
//            syncToSender(messageContent,messageContent);
            //3.发消息给对方在线端
            log.info("开始发消息给对方的在线端");
            log.info("messageContent:{}",messageContent.toString());

            dispatchToMessage(messageContent);
            reciverAck(messageContent);
            log.info("完成发消息给对方的在线端");

            log.info("消息处理完成：{}",messageContent.getMessageId());



    }



    /**
     * 处理ack
     * @param messageContent
     * @param responseVO
     */
    private void ack(MessageContent messageContent,ResponseVO responseVO){
        log.info("msg ack,msgId={},checkResut{}",messageContent.getMessageId(),responseVO.getCode());
        ChatMessageAck chatMessageAck = new
                ChatMessageAck(messageContent.getMessageId(),messageContent.getMessageSequence());
        responseVO.setData(chatMessageAck);
        //发消息
        messageProducer.sendToUser(messageContent.getFromId(), MessageCommand.MSG_ACK,
                responseVO,messageContent
        );
    }

    /**
     * 接受确认ack 服务端使用
     * @param messageContent
     */
    public void reciverAck(MessageContent messageContent){
        MessageReciveServerAckPack pack = new MessageReciveServerAckPack();
        pack.setFromId(messageContent.getToId());
        pack.setToId(messageContent.getFromId());
        pack.setMessageKey(messageContent.getMessageKey());
        pack.setMessageSequence(messageContent.getMessageSequence());
        pack.setServerSend(true);
        messageProducer.sendToUser(messageContent.getFromId(),MessageCommand.MSG_RECIVE_ACK,
                pack,new ClientInfo(messageContent.getAppId(),messageContent.getClientType()
                        ,messageContent.getImei(),messageContent.getSiteId()));
    }

    /**
     * 发消息给同步在线端
     * @param messageContent
     * @param clientInfo
     */
    private void syncToSender(MessageContent messageContent, ClientInfo clientInfo){
        messageProducer.sendToUserExceptClient(messageContent.getFromId(),
                MessageCommand.MSG_P2P,messageContent,messageContent);
    }

    /**
     * 发消息给对方在线端
     * @param messageContent
     * @return
     */
    private List<ClientInfo> dispatchMessage(MessageContent messageContent){
        List<ClientInfo> clientInfos = messageProducer.sendToUser(messageContent.getToId(), MessageCommand.MSG_P2P,
                messageContent, messageContent.getSiteId());
        return clientInfos;
    }

    /**
     * 发消息给对方在线端
     * @param messageContent
     */
    private void dispatchToMessage(MessageContent messageContent){
        messageProducer.sendToUser(messageContent.getToId(),MessageCommand.MSG_P2P,
                messageContent,messageContent.getSiteId());
    }
}
