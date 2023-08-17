package com.hy.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.sys.common.enums.GroupRoleEnum;
import com.hy.sys.common.enums.GroupStatusEnum;
import com.hy.sys.common.result.CommonResult;
import com.hy.sys.entity.Progroup;
import com.hy.sys.entity.Progroupmember;
import com.hy.sys.entity.Syspersons;
import com.hy.sys.mapper.ProgroupmemberMapper;
import com.hy.sys.service.ProgroupService;
import com.hy.sys.service.ProgroupmemberService;
import com.hy.sys.service.SyspersonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 群成员表 服务实现类
 * </p>
 *
 * @author 
 * @since 2023-08-15
 */
@Service
public class ProgroupmemberServiceImpl extends ServiceImpl<ProgroupmemberMapper, Progroupmember> implements ProgroupmemberService {

    @Autowired
    private ProgroupService progroupService;
    @Autowired
    private SyspersonsService syspersonsService;
    @Override
    public CommonResult batchAdd(String groupId, List<String> memberIds) {
        QueryWrapper<Progroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Progroup::getTid,groupId).eq(Progroup::getQzt, GroupStatusEnum.NORMAL.getCode());
        Progroup progroup = progroupService.getOne(queryWrapper);
        if(progroup == null) {
            return CommonResult.failed("群聊不存在");
        }
        memberIds.forEach(memberId -> {
            Progroupmember member = new Progroupmember();
            member.setQid(groupId);
            member.setYhid(memberId);
            member.setQjs(GroupRoleEnum.MEMBER.getCode());
            Syspersons user = syspersonsService.getUserById(memberId);
            if(user != null){
                member.setQnc(user.getNickname());
            }
            member.setCjsj(LocalDateTime.now());
            this.save(member);

        });
        return CommonResult.success();
    }


}
