package com.hy.sys.kefu.vo;

import lombok.Data;

/**
 * @ClassName MsgRecordParam
 * description:
 * yao create 2023年07月21日
 * version: 1.0
 */
@Data
public class MsgRecordParam {

    private String siteId;

    private String postId;

    private String receiveId;

    private long pageNum = 1;

    private long pageSize = 10;
}
