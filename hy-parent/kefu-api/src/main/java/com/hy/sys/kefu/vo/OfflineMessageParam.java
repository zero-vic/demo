package com.hy.sys.kefu.vo;

import lombok.Data;

/**
 * @ClassName OfflineMessageParam
 * description:
 * yao create 2023年07月24日
 * version: 1.0
 */
@Data
public class OfflineMessageParam {

    private String siteId;

    private String conversationId;

    private Long lastSequence;

    private Integer maxLimit;

}
