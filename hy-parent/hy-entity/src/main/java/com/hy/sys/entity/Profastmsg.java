package com.hy.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 * 快捷语设置
 * </p>
 *
 * @author xy
 * @since 2023-07-18
 */
@TableName("pro_fastmsg")
public class Profastmsg implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId("tid")
    private String tid;

    /**
     * 消息内容
     */
    @TableField("titles")
    private String titles;

    /**
     * 站点ID
     */
    @TableField("webid")
    private String webid;

    /**
     * 排序
     */
    @TableField("sorts")
    private Integer sorts;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getWebid() {
        return webid;
    }

    public void setWebid(String webid) {
        this.webid = webid;
    }

    public Integer getSorts() {
        return sorts;
    }

    public void setSorts(Integer sorts) {
        this.sorts = sorts;
    }

    @Override
    public String toString() {
        return "Profastmsg{" +
        ", tid = " + tid +
        ", titles = " + titles +
        ", webid = " + webid +
        ", sorts = " + sorts +
        "}";
    }
}
