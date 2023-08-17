package com.hy.im.service.utils;

import com.hy.im.common.constant.RedisConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @ClassName WriteUserSeq
 * description: seq 工具类
 * 把seq 以hash的结构存入redis
 * yao create 2023年07月05日
 * version: 1.0
 */
@Service
public class WriteUserSeq {

    //redis
    //uid friend 10
    //    group 12
    //    conversation 123
    @Autowired
    RedisTemplate redisTemplate;

    public void writeUserSeq(Integer appId,String userId,String type,Long seq){
        String key = appId + ":" + RedisConstants.SEQ_PREFIX + ":" + userId;
        redisTemplate.opsForHash().put(key,type,seq);
    }

    public void setConversationReadedSeq(String siteId,String conversationId,Long seq){
        String key = siteId + ":readedSeq:"+conversationId;
        redisTemplate.opsForValue().set(key,String.valueOf(seq));
    }

    public void setConversationReadedSeq(String conversationId,Long seq){
        String key = "readedSeq:"+conversationId;
        redisTemplate.opsForValue().set(key,String.valueOf(seq));
    }

    public Long getConversationReadedSeq(String siteId,String conversationId){
        String key = siteId + ":readedSeq:"+conversationId;
        Object o = redisTemplate.opsForValue().get(key);
        return o==null?0L:Long.parseLong(redisTemplate.opsForValue().get(key).toString());
    }

}