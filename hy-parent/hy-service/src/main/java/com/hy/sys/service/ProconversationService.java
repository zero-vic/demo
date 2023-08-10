package com.hy.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.sys.entity.Proconversation;

import java.util.List;

public interface ProconversationService extends IService<Proconversation> {
    /**
     * 获取会话列表
     * @param siteId
     * @param postId
     * @return
     */
    List<Proconversation> getConversationList(String siteId,String postId);
}
