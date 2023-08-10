package com.hy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.sys.common.enums.DelFlagEnum;
import com.hy.sys.entity.Promsgrecord;
import com.hy.sys.mapper.PromsgrecordMapper;
import com.hy.sys.service.PromsgrecordService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息记录表 服务实现类
 * </p>
 *
 * @author xy
 * @since 2023-07-18
 */
@Service
public class PromsgrecordServiceImpl extends ServiceImpl<PromsgrecordMapper, Promsgrecord> implements PromsgrecordService {


    @Override
    public IPage<Promsgrecord> getMsgRecordList(String siteId, String postId, String receiveId, long pageNum, long pageSize) {
        QueryWrapper<Promsgrecord> queryWrapper = new QueryWrapper<>();
        IPage<Promsgrecord> page = new Page<>(pageNum,pageSize);
//        webid = 'cssite' and isdel = 0 and ((postid = '2a4f53eb8f6d892a11ebcddfb0e44e05' and receiveid = 'lld') or (postid = 'lld' and receiveid = '2a4f53eb8f6d892a11ebcddfb0e44e05'))
        queryWrapper.eq("webid",siteId).eq("isdel", DelFlagEnum.NORMAL.getCode())
                        .and(i-> i.eq("postid",postId).eq("receiveid",receiveId)).or(i-> i.eq("postid",receiveId).eq("receiveid",postId))
                        .orderByDesc("sendtime");
        return this.page(page,queryWrapper);
    }
}
