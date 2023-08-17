package com.hy.sys.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hy.sys.service.OfflineMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @ClassName OfflineMessageServiceImpl
 * description:
 * yao create 2023年07月24日
 * version: 1.0
 */
@Service
public class OfflineMessageServiceImpl implements OfflineMessageService {

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public List<JSONObject> getOfflineMessage(String conversationId, Long lastSeq, Integer maxLimit) {
        String key = "offlineMessage:" + conversationId;
        //获取最大的seq
        Long maxSeq = 0L;
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        // 获取最大的score
        Set set = zSetOperations.reverseRangeWithScores(key, 0, 0);
        if (!CollectionUtils.isEmpty(set)) {
            List list = new ArrayList(set);
            DefaultTypedTuple o = (DefaultTypedTuple) list.get(0);
            maxSeq = o.getScore().longValue();
        }
        List<JSONObject> msgList = new ArrayList<>();

        Set<ZSetOperations.TypedTuple> querySet = zSetOperations.rangeByScoreWithScores(key,
                lastSeq, maxSeq, 0, maxLimit);
        for (ZSetOperations.TypedTuple<String> typedTuple : querySet) {
            String value = typedTuple.getValue();
//            MessageOffline offlineMessageContent = JSONObject.parseObject(value, MessageOffline.class);
            JSONObject jsonObject = JSONUtil.parseObj(value);
            msgList.add(jsonObject);
        }
        return msgList;
    }

    // 拉去最新的num条数据
    public Set getOfflineMessageByNum(String conversationId, long num) {
        if (num == 0) {
            return null;
        }
        String key = "offlineMessage:" + conversationId;
        Set set = redisTemplate.opsForZSet().reverseRange(key, 0, num - 1);
        return set;

    }

    @Override
    public Long getUnreadMsgCount(String conversationId) {
        String key = "offlineMessage:" + conversationId;
        // 获取最大的seq
        Long maxSeq = 0L;
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        // 获取最大的score
        Set set = zSetOperations.reverseRangeWithScores(key, 0, 0);
        if (!CollectionUtils.isEmpty(set)) {
            List list = new ArrayList(set);
            DefaultTypedTuple o = (DefaultTypedTuple) list.get(0);
            maxSeq = o.getScore().longValue();
        }
        String readedKey = "readedSeq:" + conversationId;
        Long readedSeq = 0L;
        if (redisTemplate.opsForValue().get(readedKey) != null) {
            readedSeq = Long.parseLong(redisTemplate.opsForValue().get(readedKey).toString());
        }
        Long count = zSetOperations.count(key, readedSeq, maxSeq);
        return count - 1 < 0 ? 0 : count - 1;
    }


}
