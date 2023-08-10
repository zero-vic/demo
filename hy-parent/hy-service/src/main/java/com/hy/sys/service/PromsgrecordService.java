package com.hy.sys.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.sys.entity.Promsgrecord;


/**
 * <p>
 * 消息记录表 服务类
 * </p>
 *
 * @author xy
 * @since 2023-07-18
 */
public interface PromsgrecordService extends IService<Promsgrecord> {
    /**
     * 消息记录 列表
     * @param siteId
     * @param postId
     * @param receiveId
     * @param pageNum
     * @param pageSize
     * @return
     */
    IPage<Promsgrecord> getMsgRecordList(String siteId,String postId,String receiveId,long pageNum,long pageSize);

}
