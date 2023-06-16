package com.hy.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 权限配置表
 * </p>
 *
 * @author yao
 * @since 2023-06-02
 */
@TableName("sys_powers")
public class Syspowers implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId("tid")
    private String tid;

    /**
     * 角色ID
     */
    @TableField("roleid")
    private String roleid;

    /**
     * 菜单ID
     */
    @TableField("menuid")
    private String menuid;

    /**
     * 菜单标识
     */
    @TableField("menucode")
    private String menucode;

    /**
     * 更新时间
     */
    @TableField("addtime")
    private Date addtime;

    /**
     * 备注
     */
    @TableField("marks")
    private String marks;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public String getMenuid() {
        return menuid;
    }

    public void setMenuid(String menuid) {
        this.menuid = menuid;
    }

    public String getMenucode() {
        return menucode;
    }

    public void setMenucode(String menucode) {
        this.menucode = menucode;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Syspowers{" +
        ", tid = " + tid +
        ", roleid = " + roleid +
        ", menuid = " + menuid +
        ", menucode = " + menucode +
        ", addtime = " + addtime +
        ", marks = " + marks +
        "}";
    }
}
