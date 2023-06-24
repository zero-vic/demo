package com.xy.blog.po;


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
public class ResourcePo implements Serializable {
    private static final long serialVersionUID = 1L;


    private String resId;


    private String name;


    private String remark;


    private Integer seq;

    private String url;


    private String pid;


    private String type;


    private String createUser;


    private Date createTime;


    private String updateUser;

    private Date updateTime;


    private Boolean deleteFlag;

    private List<RolePo> roles;
}
