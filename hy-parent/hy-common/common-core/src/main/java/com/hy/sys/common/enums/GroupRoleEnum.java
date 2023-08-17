package com.hy.sys.common.enums;

/**
 * 删除标识枚举
 * @author Administrator
 */
public enum GroupRoleEnum {

    /**
     * 1群主，2管理员，3普通成员
     */
    OWNER(1),

    MANAGER(2),

    MEMBER(3),
    ;

    private int code;

    GroupRoleEnum(int code){
        this.code=code;
    }

    public int getCode() {
        return code;
    }
}