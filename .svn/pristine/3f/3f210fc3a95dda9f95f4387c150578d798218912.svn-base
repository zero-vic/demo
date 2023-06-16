package com.hy.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/31 8:47
 **/
@TableName("t_user")
@Data
public class User implements Serializable {
//
//    @TableId(value = "u_id",type = IdType.ASSIGN_ID)
//    private Long uid;
//    @TableField("user_name")
//    private String username;
//    @TableField("create_time")
//    private LocalDateTime createTime;
//    @TableId(value = "tid",type = IdType.ASSIGN_ID)
//    private Long id;
//
//    @TableField("user_name")
//    private String userName;
//
//    private Long groupId;
//
//    private String password;
//
//    private String phoneNumber;
//
//    private Date cTime;

    @TableId(value = "uid",type = IdType.ASSIGN_ID)
    private Long id;

    @TableField("user_name")
    private String userName;
    @TableField("create_time")
    private LocalDateTime cTime;
}

