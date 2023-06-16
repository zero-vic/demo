package com.hy.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xy
 * @since 2023-05-29
 */
@TableName("test_user")
public class Testuser implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId("id")
    private String id;
    @TableField("username")
    private String username;
    @TableField("password")
    private String password;
    @TableField("t_remark")
    private String t_remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String gett_remark() {
        return t_remark;
    }

    public void sett_remark(String t_remark) {
        this.t_remark = t_remark;
    }

    @Override
    public String toString() {
        return "Testuser{" +
        ", id = " + id +
        ", username = " + username +
        ", password = " + password +
        ", t_remark = " + t_remark +
        "}";
    }
}
