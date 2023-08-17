package com.hy.im.service.message.service;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.im.common.constant.RedisConstants;
import com.hy.im.common.enums.ConversationTypeEnum;
import com.hy.im.common.enums.DelFlagEnum;
import com.hy.im.common.model.message.*;
import com.hy.im.service.utils.WriteUserSeq;
import com.hy.sys.common.utils.SnowflakeIdWorker;
import com.hy.sys.entity.Progroupmember;
import com.hy.sys.entity.Promsgrecord;
import com.hy.sys.mapper.PromsgrecordMapper;
import com.hy.sys.service.ProgroupService;
import com.hy.sys.service.ProgroupmemberService;
import com.hy.sys.service.PromsgrecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName MessageStoreService
 * description: 消息持久化服务
 * yao create 2023年07月04日
 * version: 1.0
 */
@Service
@Slf4j
public class MessageStoreService {

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ProgroupmemberService progroupmemberService;

    @Autowired
    private PromsgrecordService promsgrecordService;

    @Autowired
    private WriteUserSeq writeUserSeq;

    @Value("${appConfig.offlineMessageCount}")
    private Integer offlineMessageCount;


    @Transactional(rollbackFor = Exception.class)
    public void storeMessage(MessageOfflineContent messageContent) {
        Promsgrecord promsgrecord = new Promsgrecord();
        promsgrecord.setTid(messageContent.getMessageId());
        promsgrecord.setSendtime(messageContent.getSendtime());
        promsgrecord.setPostid(messageContent.getPostId());
        promsgrecord.setReceiveid(messageContent.getReceiveId());
        promsgrecord.setIsdel(DelFlagEnum.NORMAL.getCode());
        promsgrecord.setWebid(messageContent.getSiteId());
        promsgrecord.setMsg(messageContent.getMsg());
        promsgrecord.setMsgtype(messageContent.getMsgtype());
        promsgrecordService.save(promsgrecord);

    }

    /**
     * 缓存客户端消息防重，格式： appId + :cacheMessage: + messageId
     *
     * @param appId
     * @param messageId
     * @param messageContent
     */
    public void setMessageFromMessageIdCache(Integer appId, String messageId, Object messageContent) {
        //appid : cache : messageId
        String key = appId + ":" + RedisConstants.CACHE_MESSAGE + ":" + messageId;
        stringRedisTemplate.opsForValue().set(key, JSONObject.toJSONString(messageContent), 300, TimeUnit.SECONDS);
    }

    /**
     * 获取redis中的缓存客户端消息
     *
     * @param appId
     * @param messageId
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T getMessageFromMessageIdCache(Integer appId,
                                              String messageId, Class<T> clazz) {
        //appid : cache : messageId
        String key = appId + ":" + RedisConstants.CACHE_MESSAGE + ":" + messageId;
        String msg = stringRedisTemplate.opsForValue().get(key);
        if (StrUtil.isBlank(msg)) {
            return null;
        }
        return JSONObject.parseObject(msg, clazz);
    }

    public void storeOfflineMessage1(MessageOfflineContent messageOfflineContent) {
        // key =  siteId : offlineMessage :type_postId_receiveId
        String fromKey = RedisConstants.OFFLINE_MESSAGE + ":" + messageOfflineContent.getConversationType()
                + "_" + messageOfflineContent.getPostId() + "_" + messageOfflineContent.getReceiveId();

        String toKey = RedisConstants.OFFLINE_MESSAGE + ":" + messageOfflineContent.getConversationType()
                + "_" + messageOfflineContent.getReceiveId() + "_" + messageOfflineContent.getPostId();

        ZSetOperations<String, String> operations = stringRedisTemplate.opsForZSet();
        // 判断队列中的数量是否超过设定值
        if (operations.zCard(fromKey) > offlineMessageCount) {
            operations.removeRange(fromKey, 0, 0);
        }
        messageOfflineContent.setConversationId(conversationService.convertConversationId(
                ConversationTypeEnum.P2P.getCode(), messageOfflineContent.getPostId(), messageOfflineContent.getReceiveId()
        ));
        // 插入 数据 根据messageKey 作为分值
        operations.add(fromKey, JSONObject.toJSONString(messageOfflineContent),
                Long.parseLong(messageOfflineContent.getMessageId()));

        //判断 队列中的数据是否超过设定值
        if (operations.zCard(toKey) > offlineMessageCount) {
            operations.removeRange(toKey, 0, 0);
        }
        // 更新 发送者的会话seq
        writeUserSeq.setConversationReadedSeq(messageOfflineContent.getConversationId(),
                Long.parseLong(messageOfflineContent.getMessageId()));

        messageOfflineContent.setConversationId(conversationService.convertConversationId(
                ConversationTypeEnum.P2P.getCode(), messageOfflineContent.getReceiveId(), messageOfflineContent.getPostId()
        ));
        // 插入 数据 根据messageKey 作为分值
        operations.add(toKey, JSONObject.toJSONString(messageOfflineContent),
                Long.parseLong(messageOfflineContent.getMessageId()));


    }


    /**
     * 群聊消息持久化
     *
     * @param messageContent
     */
    public void storeGroupMessage(MessageOfflineContent messageContent) {
        Promsgrecord promsgrecord = new Promsgrecord();
        promsgrecord.setTid(messageContent.getMessageId());
        promsgrecord.setSendtime(messageContent.getSendtime());
        promsgrecord.setPostid(messageContent.getPostId());
//        promsgrecord.setReceiveid(messageContent.getReceiveId());
        promsgrecord.setGroupid(messageContent.getReceiveId());
        promsgrecord.setIsdel(DelFlagEnum.NORMAL.getCode());
        promsgrecord.setWebid(messageContent.getSiteId());
        promsgrecord.setMsg(messageContent.getMsg());
        promsgrecord.setMsgtype(messageContent.getMsgtype());
        promsgrecordService.save(promsgrecord);
    }

    /**
     * 存储离线消息
     *
     * @param messageOfflineContent
     */
    public void storeGroupOfflineMessage(MessageOfflineContent messageOfflineContent) {
        // 生成会话
        String groupId = messageOfflineContent.getReceiveId();
        Integer type = messageOfflineContent.getConversationType();
        String siteId = messageOfflineContent.getSiteId();
        QueryWrapper<Progroupmember> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Progroupmember::getQid, groupId);
        List<Progroupmember> list = progroupmemberService.list(queryWrapper);
        ZSetOperations<String, String> operations = stringRedisTemplate.opsForZSet();
        conversationService.generateGroupConversation(messageOfflineContent, list);
        String cId = type + "_" + messageOfflineContent.getPostId() + groupId;
        // 更新 发送者的会话seq
        writeUserSeq.setConversationReadedSeq(cId, Long.parseLong(messageOfflineContent.getMessageId()));
        list.forEach(member -> {
            String memberId = member.getYhid();
            String key = RedisConstants.OFFLINE_MESSAGE + ":" + type + "_" + memberId + "_" + groupId;
            // 判断队列中的数量是否超过设定值
            if (operations.zCard(key) > offlineMessageCount) {
                operations.removeRange(key, 0, 0);
            }
            messageOfflineContent.setConversationId(conversationService.convertConversationId(type, memberId, groupId));
            // 插入 数据 根据messageKey 作为分值
            operations.add(key, JSONObject.toJSONString(messageOfflineContent),
                    Long.parseLong(messageOfflineContent.getMessageId()));
        });

    }
}
