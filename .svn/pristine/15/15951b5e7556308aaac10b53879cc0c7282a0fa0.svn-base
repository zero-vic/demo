package com.hy.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/25 9:36
 **/
@TableName("test_user")
@Data
@ApiModel("测试用户实体")
public class TestUser implements Serializable {
    @ApiModelProperty("主键id")
    @TableId(value = "id",type = IdType.ASSIGN_UUID )
    private String id;
    @ApiModelProperty("用户名")
    @TableField("username")
    private String username;
    @ApiModelProperty("密码")
    @TableField("password")
    private String password;
}
