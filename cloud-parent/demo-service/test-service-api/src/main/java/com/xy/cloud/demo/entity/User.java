package com.xy.cloud.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@TableName("t_user")
@ApiModel(value = "user对象",description = "测试用户")
public class User implements Serializable {

    /**
     * 指定主键名、主键生产策略
     */
    @TableId(value = "u_id", type = IdType.ASSIGN_UUID)
    @ApiModelProperty(value = "用户id")
    private String uId;
    /**
     *指定列名,若一致可以不用指定
     */
    @TableField("user_name")
    @ApiModelProperty(value = "用户名")
    private String userName;

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "uId=" + uId +
                ", userName='" + userName + '\'' +
                '}';
    }
}