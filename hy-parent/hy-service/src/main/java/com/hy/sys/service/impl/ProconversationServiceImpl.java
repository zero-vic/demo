package com.hy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.sys.entity.Proconversation;
import com.hy.sys.mapper.ProconversionMapper;
import com.hy.sys.service.ProconversationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName ProconversationServiceImpl
 * description:
 * yao create 2023年07月24日
 * version: 1.0
 */
@Service
public class ProconversationServiceImpl extends ServiceImpl<ProconversionMapper, Proconversation> implements ProconversationService {

    @Override
    public List<Proconversation> getConversationList(String siteId, String postId) {
        QueryWrapper<Proconversation> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Proconversation::getPostid,postId)
                .eq(Proconversation::getSiteid,siteId);
        return this.list(queryWrapper);
    }
}
