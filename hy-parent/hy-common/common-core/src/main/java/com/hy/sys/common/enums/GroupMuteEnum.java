package com.hy.sys.common.enums;

/**
 * 删除标识枚举
 */
public enum GroupMuteEnum {

    /**
     * 0 不禁言；1 禁言
     */
    NORMAL(0),

    MUTE(1),
    ;

    private int code;

    GroupMuteEnum(int code){
        this.code=code;
    }

    public int getCode() {
        return code;
    }
}