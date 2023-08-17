package com.hy.im.service.message.service;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.im.common.constant.RedisConstants;
import com.hy.im.common.model.message.MessageConversationContent;
import com.hy.im.common.model.message.MessageOfflineContent;
import com.hy.im.common.model.message.MessageReadedContent;
import com.hy.im.service.utils.WriteUserSeq;
import com.hy.sys.entity.Proconversation;
import com.hy.sys.entity.Progroup;
import com.hy.sys.entity.Progroupmember;
import com.hy.sys.entity.Syspersons;
import com.hy.sys.mapper.SyspersonsMapper;
import com.hy.sys.service.ProconversationService;
import com.hy.sys.service.ProgroupService;
import com.hy.sys.service.SyspersonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @ClassName ConversationService
 * description:
 * yao create 2023年08月15日
 * version: 1.0
 */
@Service
public class ConversationService {
    @Autowired
    private WriteUserSeq writeUserSeq;

    @Autowired
    private SyspersonsService syspersonsService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ProconversationService proconversationService;

    @Autowired
    private ProgroupService progroupService;

    /**
     * 生成会话
     *
     * @param conversationContent
     */
    @Transactional(rollbackFor = Exception.class)
    public void generateConversation(MessageConversationContent conversationContent) {
        String postId = conversationContent.getFromId();
        String receiveId = conversationContent.getToId();
        String siteId = conversationContent.getSiteId();
        Integer type = conversationContent.getConversationType();
        String id = type + "_" + postId + "_" + receiveId;
//        String key = siteId + RedisConstants.CONVERSATION_LIST + postId;
        String key = RedisConstants.CONVERSATION_LIST + postId;
        // 用hash存
        if (!stringRedisTemplate.opsForHash().hasKey(key, id)) {

            Proconversation proconversation = new Proconversation();
            proconversation.setTid(id);
            proconversation.setAddtime(LocalDateTime.now());
            proconversation.setPostid(postId);
            proconversation.setReceiveid(receiveId);
            proconversation.setType(type);
            proconversation.setSiteid(conversationContent.getSiteId());
            writeUserSeq.setConversationReadedSeq(id, 0L);
            Syspersons syspersons = syspersonsService.getById(receiveId);
            if (syspersons != null) {
                proconversation.setName(syspersons.getName());
            } else {
                String str = "游客" + DateUtil.format(new Date(), "yyMMdd") + String.format("%04d", new Random().nextInt(10000));
                proconversation.setName(str);
            }
            proconversationService.save(proconversation);
            stringRedisTemplate.opsForHash().put(key, id, JSONObject.toJSONString(proconversation));
        }
        String id2 = type + "_" + receiveId + "_" + postId;
        String key2 =RedisConstants.CONVERSATION_LIST + receiveId;
        if (!stringRedisTemplate.opsForHash().hasKey(key2, id2)) {

            Proconversation proconversation = new Proconversation();
            proconversation.setTid(id2);
            proconversation.setAddtime(LocalDateTime.now());
            proconversation.setPostid(receiveId);
            proconversation.setReceiveid(postId);
            proconversation.setType(type);
            proconversation.setSiteid(conversationContent.getSiteId());
            writeUserSeq.setConversationReadedSeq(id2, 0L);
            Syspersons syspersons = syspersonsService.getById(postId);
            if (syspersons != null) {
                proconversation.setName(syspersons.getName());
            } else {
                String str = "游客" + DateUtil.format(new Date(), "yyMMdd") + String.format("%04d", new Random().nextInt(10000));
                proconversation.setName(str);
            }
            proconversationService.save(proconversation);
            stringRedisTemplate.opsForHash().put(key2, id2, JSONObject.toJSONString(proconversation));
        }
    }

    /**
     * 生成会话id
     *
     * @param type
     * @param fromId
     * @param toId
     * @return
     */
    public String convertConversationId(Integer type, String fromId, String toId) {
        return type + "_" + fromId + "_" + toId;
    }

    /**
     * 消息标记已读
     *
     * @param messageReadedContent
     */
    public void messageMarkRead(MessageReadedContent messageReadedContent) {

        long messageSequence = messageReadedContent.getMessageSequence();
        String siteId = messageReadedContent.getSiteId();
        // 区分群消息
//        if(messageReadedContent.getConversationType() == ConversationTypeEnum.GROUP.getCode()){
//            toId = messageReadedContent.getGroupId();
//        }
//        String conversationId = convertConversationId(messageReadedContent.getConversationType(),
//                messageReadedContent.getFromId(), toId);
        String conversationId = messageReadedContent.getConversationId();
        writeUserSeq.setConversationReadedSeq(conversationId, messageSequence);

    }

    /**
     * 生成群聊会话
     * @param messageOfflineContent
     * @param list
     */
    @Transactional(rollbackFor = Exception.class)
    public void generateGroupConversation(MessageOfflineContent messageOfflineContent, List<Progroupmember> list) {
        String groupId = messageOfflineContent.getReceiveId();
        String groupname = progroupService.getById(groupId).getQmc();
        Integer type = messageOfflineContent.getConversationType();
        String siteId = messageOfflineContent.getSiteId();

        list.forEach(member -> {
            String memberId = member.getYhid();
            String id = type + "_" + memberId + "_" + groupId;
            String key = RedisConstants.CONVERSATION_LIST + memberId;
            if (!stringRedisTemplate.opsForHash().hasKey(key, id)) {
                Proconversation proconversation = proconversationService.getById(id);
                if(proconversation == null){
                    proconversation = new Proconversation();
                    proconversation.setTid(id);
                    proconversation.setName(groupname);
                    proconversation.setAddtime(LocalDateTime.now());
                    proconversation.setPostid(memberId);
                    proconversation.setReceiveid(groupId);
                    proconversation.setType(type);
                    proconversation.setSiteid(siteId);
                    writeUserSeq.setConversationReadedSeq(id, 0L);
                    proconversationService.save(proconversation);
                    stringRedisTemplate.opsForHash().put(key, id, JSONObject.toJSONString(proconversation));
                }
            }

        });

    }
}
