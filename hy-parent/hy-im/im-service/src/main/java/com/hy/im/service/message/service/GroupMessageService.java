package com.hy.im.service.message.service;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.im.codec.pack.message.ChatMessageAck;
import com.hy.im.common.enums.ConversationTypeEnum;
import com.hy.im.common.enums.DelFlagEnum;
import com.hy.im.common.enums.command.GroupEventCommand;
import com.hy.im.common.model.ClientInfo;
import com.hy.im.common.model.message.GroupChatMessageContent;
import com.hy.im.common.model.message.MessageContent;
import com.hy.im.common.model.message.MessageOfflineContent;
import com.hy.im.common.response.ResponseVO;
import com.hy.im.service.message.producer.MessageProducer;
import com.hy.sys.common.utils.SnowflakeIdWorker;
import com.hy.sys.entity.Progroupmember;
import com.hy.sys.service.ProgroupmemberService;;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName GroupMessageService
 * description:
 * yao create 2023年08月16日
 * version: 1.0
 */
@Component
public class GroupMessageService {

    @Autowired
    private CheckSendMessageService checkSendMessageService;

    @Autowired
    private MessageProducer messageProducer;

    @Autowired
    private MessageStoreService messageStoreService;
    @Autowired
    private ProgroupmemberService progroupmemberService;




    public void process(GroupChatMessageContent messageContent){
        String fromId = messageContent.getFromId();
        String groupId = messageContent.getGroupId();
        Integer appId = messageContent.getAppId();
        //前置校验
        //这个用户是否被禁言 是否被禁用
        //发送方和接收方是否是好友
        Boolean flag = checkSendMessageService.checkGroupMessage(fromId, groupId);
        if(!flag){
            return;
        }
        // 消息持久化
        // 离线消息 会话记录
        MessageOfflineContent messageOfflineContent = new MessageOfflineContent();
        messageOfflineContent.setMessageId(SnowflakeIdWorker.getId());
        messageOfflineContent.setAppId(messageContent.getAppId());
        messageOfflineContent.setSiteId(messageContent.getSiteId());
        messageOfflineContent.setPostId(messageContent.getFromId());
        messageOfflineContent.setReceiveId(groupId);
        messageOfflineContent.setSendtime(LocalDateTime.now());
        messageOfflineContent.setIsdel(DelFlagEnum.NORMAL.getCode());
        messageOfflineContent.setConversationType(ConversationTypeEnum.GROUP.getCode());
        JSONObject msg = JSONObject.parseObject(messageContent.getMessageBody());
        messageOfflineContent.setMsg(msg.getString("content"));
        messageOfflineContent.setMsgtype(msg.getInteger("type"));
        // 持久化
        messageStoreService.storeGroupMessage(messageOfflineContent);
        // 离线消息
        messageStoreService.storeGroupOfflineMessage(messageOfflineContent);
        //1.回ack成功给自己
        ack(messageContent,ResponseVO.successResponse());

        //3.发消息给对方在线端
        dispatchMessage(messageContent);



    }
    private void dispatchMessage(GroupChatMessageContent messageContent){
        String postId = messageContent.getFromId();
        List<Progroupmember> list = progroupmemberService.list(new QueryWrapper<Progroupmember>().lambda().eq(Progroupmember::getQid, messageContent.getGroupId()));
        list.forEach(member -> {
            String memberId = member.getYhid();
            if(!memberId.equals(postId)){
                messageProducer.sendToUser(memberId,
                        GroupEventCommand.MSG_GROUP,
                        messageContent,messageContent.getSiteId());
            }
        });
    }

    private void ack(MessageContent messageContent, ResponseVO responseVO){

        ChatMessageAck chatMessageAck = new ChatMessageAck(messageContent.getMessageId());
        responseVO.setData(chatMessageAck);
        //發消息
        messageProducer.sendToUser(messageContent.getFromId(),
                GroupEventCommand.GROUP_MSG_ACK,
                responseVO,messageContent
        );
    }

    private void syncToSender(GroupChatMessageContent messageContent, ClientInfo clientInfo){
        messageProducer.sendToUserExceptClient(messageContent.getFromId(),
                GroupEventCommand.MSG_GROUP,messageContent,messageContent);
    }




}
