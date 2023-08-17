package com.hy.im.common.model.message;

import lombok.Data;

/**
 * @ClassName MessageConversationContent
 * description:
 * yao create 2023年07月24日
 * version: 1.0
 */
@Data
public class MessageConversationContent  {

    private long messageSequence;

    private String fromId;

    private String toId;
    /**
     * 会话类型
     */
    private Integer conversationType;

    private String siteId;

    private Integer appId;

    private Integer clientType;

    private String imei;
}
