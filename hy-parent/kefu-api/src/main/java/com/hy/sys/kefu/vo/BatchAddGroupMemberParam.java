package com.hy.sys.kefu.vo;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @ClassName BatchAddGroupMemberParam
 * description:
 * yao create 2023年08月15日
 * version: 1.0
 */
@Data
public class BatchAddGroupMemberParam {

    @NotNull(message = "群id不能为空")
    private String groupId;

    private List<String> memberIds;
}
