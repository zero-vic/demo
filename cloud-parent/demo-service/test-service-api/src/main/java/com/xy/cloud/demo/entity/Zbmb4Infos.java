package com.xy.cloud.demo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * @Description
 * @Author yao
 * @Date 2023/5/23 16:35
 **/
@TableName("zbmb4_infos")
public class Zbmb4Infos implements Serializable {
    @TableField("name")
    private String name;
    @TableField("value")
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Zbmb4Infos{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }

    public void setValue(String value) {
        this.value = value;
    }
}
