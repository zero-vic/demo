package com.hy.im.service.message.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hy.sys.common.enums.GroupStatusEnum;
import com.hy.sys.entity.Progroup;
import com.hy.sys.entity.Progroupmember;
import com.hy.sys.service.ProgroupService;
import com.hy.sys.service.ProgroupmemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName CheckSendMessageService
 * description: 发送消息校验服务
 * yao create 2023年08月16日
 * version: 1.0
 */
@Component
public class CheckSendMessageService {

    @Autowired
    private ProgroupService progroupService;

    @Autowired
    private ProgroupmemberService progroupmemberService;

    /**
     * 校验群聊消息之前
     * @param fromId
     * @param groupId
     * @return
     */
    public Boolean checkGroupMessage(String fromId,String groupId){
        // 检查群存在
        QueryWrapper<Progroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Progroup::getTid,groupId).eq(Progroup::getQzt, GroupStatusEnum.NORMAL.getCode());
        Progroup progroup = progroupService.getOne(queryWrapper);
        if(progroup == null){
            return false;
        }
        // 判断是否在群内
        QueryWrapper<Progroupmember> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.lambda().eq(Progroupmember::getQid,groupId).eq(Progroupmember::getYhid,fromId);
        Progroupmember progroupmember = progroupmemberService.getOne(queryWrapper1);
        return progroupmember != null;
    }
}
