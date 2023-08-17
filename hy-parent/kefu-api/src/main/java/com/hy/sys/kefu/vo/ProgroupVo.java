package com.hy.sys.kefu.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @ClassName GroupVo
 * description:
 * yao create 2023年08月15日
 * version: 1.0
 */
@Data
public class ProgroupVo {
    /**
     * 群id
     */
    private String tid;

    /**
     * 站点id
     */
    private String wzid;

    /**
     * 群名称
     */
    private String qmc;

    /**
     * 群头像
     */
    private String qtx;

    /**
     * 群描述
     */
    private String qms;

    /**
     * 群公告
     */
    private String qgg;

    /**
     * 群状态 0正常 1解散
     */
    private Integer qzt;

    /**
     * 创建时间
     */
    private LocalDateTime cjsj;

    /**
     * 修改时间
     */
    private LocalDateTime xgsj;

    /**
     * 是否禁言 0 不禁言 1 全体禁言
     */
    private Integer sfjy;

    /**
     * 网站名称
     */
    private String wzmc;

}
