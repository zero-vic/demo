package com.hy.sys.common.dto;



/**
 *  @ClassName FileUploadDto
 *  description: 文件上传传输对象 传输文件表其他的信息
 *  yao create 2023年06月06日
 *  version: 1.0
 */
public class FileUploadDto {


    /**
     * 项目id
     */

    private String proid;

    /**
     * 文件类型:	批复文件类：1.立项批复信息 2.可研批复信息  3.概算批复信息	周报文件类:1.周报 7.周报进度图片	其他文件类型：(具体类型根据枚举定义)	 4.合同项目立项  6.一阶段清标报告
     */

    private Short type;


    private String add_userid;



    /**
     * 数据id
     */

    private String dataid;

    /**
     * 状态
     */

    private Short state;

    /**
     * 附件类型:0 默认(其他附件) 1 项目附件
     */

    private Short filetype;

    /**
     * 附件分类 具体枚举
     */

    private Short fileclass;

    /**
     * 文件来源
     */

    private String source;

    /**
     * 链接地址
     */

    private String linkaddr;

    /**
     * 单位Id;区分多单位
     */

    private String host_govid;

    /**
     * 当前操作人
     */

    private String curruerid;

    /**
     * 所有操作人
     */

    private String alluserid;

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

    public String getAdd_userid() {
        return add_userid;
    }

    public void setAdd_userid(String add_userid) {
        this.add_userid = add_userid;
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
}
