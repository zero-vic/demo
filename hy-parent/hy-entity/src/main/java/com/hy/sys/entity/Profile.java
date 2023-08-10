package com.hy.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 项目附件表
 * </p>
 *
 * @author xy
 * @since 2023-06-06
 */
@TableName("pro_file")
@Data
public class Profile implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "tid",type = IdType.ASSIGN_ID)
    private String tid;

    /**
     * 项目id
     */
    @TableField("proid")
    private String proid;

    /**
     * 文件类型:	批复文件类：1.立项批复信息 2.可研批复信息  3.概算批复信息	周报文件类:1.周报 7.周报进度图片	其他文件类型：(具体类型根据枚举定义)	 4.合同项目立项  6.一阶段清标报告
     */
    @TableField("type")
    private Short type;

    /**
     * 附件名称
     */
    @TableField("file_name")
    private String file_name;

    /**
     * 附件地址
     */
    @TableField("file_path")
    private String file_path;

    /**
     * 文件大小
     */
    @TableField("file_size")
    private String file_size;

    /**
     * 文件后缀名
     */
    @TableField("suffix")
    private String suffix;

    /**
     * 添加时间
     */
    @TableField("addtime")
    private Date addtime;

    /**
     * 添加人
     */
    @TableField("add_userid")
    private String add_userid;

    /**
     * 是否删除 1是 0否
     */
    @TableField("isdel")
    private Short isdel;

    /**
     * 数据id
     */
    @TableField("dataid")
    private String dataid;

    /**
     * 状态
     */
    @TableField("state")
    private Short state;

    /**
     * 附件类型:0 默认(其他附件) 1 项目附件
     */
    @TableField("filetype")
    private Short filetype;

    /**
     * 附件分类 具体枚举
     */
    @TableField("fileclass")
    private Short fileclass;

    /**
     * 文件来源
     */
    @TableField("source")
    private String source;

    /**
     * 链接地址
     */
    @TableField("linkaddr")
    private String linkaddr;

    /**
     * 单位Id;区分多单位
     */
    @TableField("host_govid")
    private String host_govid;

    /**
     * 当前操作人
     */
    @TableField("curruerid")
    private String curruerid;

    /**
     * 所有操作人
     */
    @TableField("alluserid")
    private String alluserid;

}
