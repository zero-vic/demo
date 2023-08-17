package com.hy.sys.kefu.vo;


import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @ClassName GroupMemberParam
 * description:
 * yao create 2023年08月16日
 * version: 1.0
 */
@Data
public class GroupMemberParam {

    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    private String yhid;

    /**
     * 群id
     */
    @NotNull(message = "群聊id不能为空")
    private String qid;

    /**
     * 群角色 1群主，2管理员，普通成员
     */
    private Integer qjs;

    /**
     * 群昵称
     */
    private String qnc;

}
