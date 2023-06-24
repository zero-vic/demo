package com.xy.blog.po;

import com.xy.blog.entity.Resource;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created With IntelliJ IDEA
 * User: yao
 * Date:2023/06/24
 * Description:
 * Version:V1.0
 */
@Data
public class RolePo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String rId;


    private String roleName;


    private String remark;


    private String updateUser;


    private Date updateTime;


    private String createUser;


    private Date createTime;


    private String pid;

    private Boolean deleteFlag;

}
