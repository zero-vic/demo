package com.hy.sys.common.enums;
/**
 * 操作类型（1.增加 2.修改 3.删除 4.查询 5.登录/退出）
 */
public enum BusinessType {
    /**
     * 增加
     */
    INSERT(1),
    /**
     * 更新
     */
    UPDATE(2),
    /**
     * 删除
     */
    DELETE(3),
    /**
     * 查询
     */
    SELECT(4),
    /**
     * 登陆
     */
    LOGIN(5),
    /**
     * 登出
     */
    LOGOUT(5),
    /**
     * 其他
     */
    OTHER(6),
    ;
    private int code;
    BusinessType(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
