package com.hy.im.service.message.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.hy.im.codec.pack.message.MessageReadedPack;
import com.hy.im.common.constant.RedisConstants;
import com.hy.im.common.enums.command.Command;
import com.hy.im.common.enums.command.GroupEventCommand;
import com.hy.im.common.enums.command.MessageCommand;
import com.hy.im.common.model.SyncReq;
import com.hy.im.common.model.SyncResp;
import com.hy.im.common.model.message.MessageReadedContent;
import com.hy.im.common.model.message.MessageReciveAckContent;
import com.hy.im.common.model.message.OfflineMessageContent;
import com.hy.im.common.response.ResponseVO;
import com.hy.im.service.message.producer.MessageProducer;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @ClassName MessageSyncService
 * description: 消息同步服务
 * yao create 2023年07月04日
 * version: 1.0
 */
@Service
public class MessageSyncService {

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private ConversationService conversationService;


    @Autowired
    private RedisTemplate redisTemplate;




    /**
     * 发送确认ack
     * @param messageReciveAckContent
     */
    public void receiveMark(MessageReciveAckContent messageReciveAckContent){
        messageProducer.sendToUser(messageReciveAckContent.getToId(),
                MessageCommand.MSG_RECIVE_ACK,messageReciveAckContent,messageReciveAckContent.getSiteId());
    }
    /**
     * 发送确认visitor ack
     * @param messageReciveAckContent
     */
    public void receiveVisitorMark(MessageReciveAckContent messageReciveAckContent){
        messageProducer.sendToUser(messageReciveAckContent.getToId(),
                MessageCommand.MSG_VISITOR_RECIVE_ACK,messageReciveAckContent,messageReciveAckContent.getSiteId());
    }

    /**
     * 消息已读。更新会话的seq，通知在线的同步端发送指定command ，发送已读回执通知对方（消息发起方）我已读
     * @param messageContent
     */
    public void readMark(MessageReadedContent messageContent) {
        conversationService.messageMarkRead(messageContent);
        MessageReadedPack messageReadedPack = new MessageReadedPack();
        BeanUtils.copyProperties(messageContent,messageReadedPack);
        // 发送给自己的其他端
//        syncToSender(messageReadedPack,messageContent,MessageCommand.MSG_READED_NOTIFY);
        //发送给对方 发给发送方
        messageProducer.sendToUser(messageContent.getToId(),
                MessageCommand.MSG_READED_RECEIPT,messageReadedPack,messageContent.getSiteId());
    }

    /**
     * 发送给自己的其他端
     * @param pack
     * @param content
     * @param command
     */
    private void syncToSender(MessageReadedPack pack, MessageReadedContent content, Command command){
        MessageReadedPack messageReadedPack = new MessageReadedPack();
//        BeanUtils.copyProperties(messageReadedContent,messageReadedPack);
        //发送给自己的其他端
        messageProducer.sendToUserExceptClient(pack.getFromId(),
                command,pack,
                content);
    }

    /**
     * 群聊使用
     * @param messageReaded
     */
    public void groupReadMark(MessageReadedContent messageReaded) {
        conversationService.messageMarkRead(messageReaded);
        MessageReadedPack messageReadedPack = new MessageReadedPack();
        BeanUtils.copyProperties(messageReaded,messageReadedPack);
        syncToSender(messageReadedPack,messageReaded, GroupEventCommand.MSG_GROUP_READED_NOTIFY);
        if(!messageReaded.getFromId().equals(messageReaded.getToId())){
            messageProducer.sendToUser(messageReadedPack.getToId(),GroupEventCommand.MSG_GROUP_READED_RECEIPT
                    ,messageReaded,messageReaded.getSiteId());
        }
    }

    public ResponseVO syncOfflineMessage(SyncReq req) {

        SyncResp<OfflineMessageContent> resp = new SyncResp<>();

        String key = req.getAppId() + ":" + RedisConstants.OFFLINE_MESSAGE + ":" + req.getOperater();
        //获取最大的seq
        Long maxSeq = 0L;
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        // 获取最大的score
        Set set = zSetOperations.reverseRangeWithScores(key, 0, 0);
        if(!CollectionUtils.isEmpty(set)){
            List list = new ArrayList(set);
            DefaultTypedTuple o = (DefaultTypedTuple) list.get(0);
            maxSeq = o.getScore().longValue();
        }

        List<OfflineMessageContent> respList = new ArrayList<>();
        resp.setMaxSequence(maxSeq);

        Set<ZSetOperations.TypedTuple> querySet = zSetOperations.rangeByScoreWithScores(key,
                req.getLastSequence(), maxSeq, 0, req.getMaxLimit());
        for (ZSetOperations.TypedTuple<String> typedTuple : querySet) {
            String value = typedTuple.getValue();
            OfflineMessageContent offlineMessageContent = JSONObject.parseObject(value, OfflineMessageContent.class);
            respList.add(offlineMessageContent);
        }
        resp.setDataList(respList);

        if(!CollectionUtils.isEmpty(respList)){
            OfflineMessageContent offlineMessageContent = respList.get(respList.size() - 1);
            resp.setCompleted(maxSeq <= offlineMessageContent.getMessageKey());
        }

        return ResponseVO.successResponse(resp);
    }

}
