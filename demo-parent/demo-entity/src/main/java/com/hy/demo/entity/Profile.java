package com.hy.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

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

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getFile_size() {
        return file_size;
    }

    public void setFile_size(String file_size) {
        this.file_size = file_size;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public String getAdd_userid() {
        return add_userid;
    }

    public void setAdd_userid(String add_userid) {
        this.add_userid = add_userid;
    }

    public Short getIsdel() {
        return isdel;
    }

    public void setIsdel(Short isdel) {
        this.isdel = isdel;
    }

    public String getDataid() {
        return dataid;
    }

    public void setDataid(String dataid) {
        this.dataid = dataid;
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public Short getFiletype() {
        return filetype;
    }

    public void setFiletype(Short filetype) {
        this.filetype = filetype;
    }

    public Short getFileclass() {
        return fileclass;
    }

    public void setFileclass(Short fileclass) {
        this.fileclass = fileclass;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getLinkaddr() {
        return linkaddr;
    }

    public void setLinkaddr(String linkaddr) {
        this.linkaddr = linkaddr;
    }

    public String getHost_govid() {
        return host_govid;
    }

    public void setHost_govid(String host_govid) {
        this.host_govid = host_govid;
    }

    public String getCurruerid() {
        return curruerid;
    }

    public void setCurruerid(String curruerid) {
        this.curruerid = curruerid;
    }

    public String getAlluserid() {
        return alluserid;
    }

    public void setAlluserid(String alluserid) {
        this.alluserid = alluserid;
    }

    @Override
    public String toString() {
        return "Profile{" +
        ", tid = " + tid +
        ", proid = " + proid +
        ", type = " + type +
        ", file_name = " + file_name +
        ", file_path = " + file_path +
        ", file_size = " + file_size +
        ", suffix = " + suffix +
        ", addtime = " + addtime +
        ", add_userid = " + add_userid +
        ", isdel = " + isdel +
        ", dataid = " + dataid +
        ", state = " + state +
        ", filetype = " + filetype +
        ", fileclass = " + fileclass +
        ", source = " + source +
        ", linkaddr = " + linkaddr +
        ", host_govid = " + host_govid +
        ", curruerid = " + curruerid +
        ", alluserid = " + alluserid +
        "}";
    }
}
