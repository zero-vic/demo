package com.hy.im.server.utils;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.hy.im.codec.pack.user.UserStatusChangeNotifyPack;
import com.hy.im.codec.proto.MessageHeader;
import com.hy.im.common.constant.Constants;
import com.hy.im.common.constant.RedisConstants;
import com.hy.im.common.enums.ImConnectStatusEnum;
import com.hy.im.common.model.UserClientDto;
import com.hy.im.common.model.UserSession;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName SessionSocketHolder
 * description: 存channel的工具类
 * yao create 2023年06月28日
 * version: 1.0
 */

@Component
public class SessionSocketHolder {
    private static RedissonClient redissonClientStatic;

    @Autowired
    private RedissonClient redissonClient;

    @PostConstruct
    private void initRedisson() {
        redissonClientStatic = this.redissonClient;
    }

    private static final Map<UserClientDto, NioSocketChannel> CHANNEL_MAP = new ConcurrentHashMap<>();

    public static void put(Integer appId, String userId, Integer clientType, NioSocketChannel channel, String imei) {
        UserClientDto dto = new UserClientDto();
        dto.setImei(imei);
        dto.setAppId(appId);
        dto.setClientType(clientType);
        dto.setUserId(userId);
        CHANNEL_MAP.put(dto, channel);
    }

    public static void put(Integer appId, String userId, Integer clientType, NioSocketChannel channel, String imei, String siteId) {
        UserClientDto dto = new UserClientDto();
        dto.setImei(imei);
        dto.setAppId(appId);
        dto.setClientType(clientType);
        dto.setUserId(userId);
        dto.setSiteId(siteId);
        CHANNEL_MAP.put(dto, channel);
    }

    public static NioSocketChannel get(Integer appId, String userId, Integer clientType, String imei) {
        UserClientDto dto = new UserClientDto();
        dto.setImei(imei);
        dto.setAppId(appId);
        dto.setClientType(clientType);
        dto.setUserId(userId);
        return CHANNEL_MAP.get(dto);
    }

    public static NioSocketChannel get(Integer appId, String userId, Integer clientType, String imei, String siteId) {
        UserClientDto dto = new UserClientDto();
        dto.setImei(imei);
        dto.setAppId(appId);
        dto.setClientType(clientType);
        dto.setUserId(userId);
        dto.setSiteId(siteId);
        return CHANNEL_MAP.get(dto);
    }

    public static void remove(Integer appId, String userId, Integer clientType, String imei) {
        UserClientDto dto = new UserClientDto();
        dto.setImei(imei);
        dto.setAppId(appId);
        dto.setClientType(clientType);
        dto.setUserId(userId);
        CHANNEL_MAP.remove(dto);
    }

    public static void remove(Integer appId, String userId, Integer clientType, String imei, String siteId) {
        UserClientDto dto = new UserClientDto();
        dto.setImei(imei);
        dto.setAppId(appId);
        dto.setClientType(clientType);
        dto.setUserId(userId);
        dto.setSiteId(siteId);
        CHANNEL_MAP.remove(dto);
    }

    public static void remove(NioSocketChannel channel) {
        CHANNEL_MAP.entrySet().stream()
                .filter(entry -> entry.getValue() == channel)
                .forEach(entry -> CHANNEL_MAP.remove(entry.getKey()));
    }

    /**
     * 跟据 appId 和 userId 获取 NioSocketChannel 集合
     *
     * @param appId
     * @param userId
     * @return
     */
    public static List<NioSocketChannel> get(Integer appId, String userId) {
        Set<UserClientDto> userClientDtos = CHANNEL_MAP.keySet();
        List<NioSocketChannel> channels = new ArrayList<>();
        userClientDtos.forEach(dto -> {
            if (dto.getAppId().equals(appId) && dto.getUserId().equals(userId)) {
                channels.add(CHANNEL_MAP.get(dto));
            }
        });
        return channels;
    }

    /**
     * 登出session处理
     *
     * @param channel
     */
    public static void removeUserSession(NioSocketChannel channel) {
        String userId = (String) channel.attr(AttributeKey.valueOf(Constants.USER_ID)).get();
        Integer appId = (Integer) channel.attr(AttributeKey.valueOf(Constants.APP_ID)).get();
        Integer clientType = (Integer) channel.attr(AttributeKey.valueOf(Constants.CLIENT_TYPE)).get();
        String imei = (String) channel.attr(AttributeKey.valueOf(Constants.IMEI)).get();
        String siteId = (String) channel.attr(AttributeKey.valueOf(Constants.SITE_ID)).get();

        SessionSocketHolder.remove(appId, userId, clientType, imei, siteId);
//        RedissonClient redissonClient = RedisManager.getRedissonClient();
//        String key = appId + RedisConstants.USER_SESSION_CONSTANTS + userId;
        String key = RedisConstants.USER_SESSION_CONSTANTS + userId;
        RMap<Object, Object> map = redissonClientStatic.getMap(key);
        map.remove(clientType + ":" + imei);
        // 通知逻辑层下线通知
//        MessageHeader messageHeader = new MessageHeader();
//        messageHeader.setAppId(appId);
//        messageHeader.setImei(imei);
//        messageHeader.setClientType(clientType);

//        UserStatusChangeNotifyPack userStatusChangeNotifyPack = new UserStatusChangeNotifyPack();
//        userStatusChangeNotifyPack.setAppId(appId);
//        userStatusChangeNotifyPack.setUserId(userId);
//        userStatusChangeNotifyPack.setStatus(ImConnectStatusEnum.OFFLINE_STATUS.getCode());
//        MqMessageProducer.sendMessage(userStatusChangeNotifyPack,messageHeader, UserEventCommand.USER_ONLINE_STATUS_CHANGE.getCommand());

        channel.close();
    }

    /**
     * 离线session处理
     *
     * @param channel
     */
    public static void offlineUserSession(NioSocketChannel channel) {
        String userId = (String) channel.attr(AttributeKey.valueOf(Constants.USER_ID)).get();
        Integer appId = (Integer) channel.attr(AttributeKey.valueOf(Constants.APP_ID)).get();
        Integer clientType = (Integer) channel.attr(AttributeKey.valueOf(Constants.CLIENT_TYPE)).get();
        String imei = (String) channel.attr(AttributeKey.valueOf(Constants.IMEI)).get();
        String siteId = (String) channel.attr(AttributeKey.valueOf(Constants.SITE_ID)).get();

        SessionSocketHolder.remove(appId, userId, clientType, imei, siteId);
//        RedissonClient redissonClient = RedisManager.getRedissonClient();
//        String key = appId + RedisConstants.USER_SESSION_CONSTANTS + userId;
        String key = RedisConstants.USER_SESSION_CONSTANTS + userId;
        RMap<String, String> map = redissonClientStatic.getMap(key);
        String sessionKey = clientType + ":" + imei;
        String sessionStr = map.get(sessionKey);
        if (!StrUtil.isBlank(sessionStr)) {
            UserSession userSession = JSONObject.parseObject(sessionStr, UserSession.class);
            userSession.setConnectState(ImConnectStatusEnum.OFFLINE_STATUS.getCode());
            map.put(sessionKey, JSONObject.toJSONString(userSession));
        }
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setAppId(appId);
        messageHeader.setImei(imei);
        messageHeader.setClientType(clientType);
        messageHeader.setSiteId(siteId);

        UserStatusChangeNotifyPack userStatusChangeNotifyPack = new UserStatusChangeNotifyPack();
        userStatusChangeNotifyPack.setAppId(appId);
    }
}