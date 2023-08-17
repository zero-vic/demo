package com.hy.sys.service;

import cn.hutool.json.JSONObject;

import java.util.List;
import java.util.Set;

/**
 * @ClassName OfflineMessageService
 * description:
 * yao create 2023年07月24日
 * version: 1.0
 */
public interface OfflineMessageService {

    List<JSONObject> getOfflineMessage(String conversationId, Long lastSeq, Integer maxLimit);

    Set getOfflineMessageByNum( String conversationId,long num);

    Long getUnreadMsgCount(String conversationId);
}
