package com.xy.cloud.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/23 16:13
 **/
@TableName("audit")
public class Audit implements Serializable {

    @TableId("sjbb")
    private Integer sjbb;
    @TableField("bbms")
    private String bbms;
    @TableField("cjsj")
    private String cjsj;
    @TableField("proid")
    private String proid;

    @Override
    public String toString() {
        return "Audit{" +
                "sjbb=" + sjbb +
                ", bbms='" + bbms + '\'' +
                ", cjsj='" + cjsj + '\'' +
                ", proid='" + proid + '\'' +
                '}';
    }

    public Integer getSjbb() {
        return sjbb;
    }

    public void setSjbb(Integer sjbb) {
        this.sjbb = sjbb;
    }

    public String getBbms() {
        return bbms;
    }

    public void setBbms(String bbms) {
        this.bbms = bbms;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public String getProid() {
        return proid;
    }

    public void setProid(String proid) {
        this.proid = proid;
    }
}
