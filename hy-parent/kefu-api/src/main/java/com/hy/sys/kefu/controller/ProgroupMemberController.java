package com.hy.sys.kefu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.hy.sys.common.constants.ErrorMsgConstant;
import com.hy.sys.common.dto.UserJwtDto;
import com.hy.sys.common.enums.GroupRoleEnum;
import com.hy.sys.common.result.CommonResult;
import com.hy.sys.common.result.ResultCode;
import com.hy.sys.common.utils.UserUtils;
import com.hy.sys.entity.Progroup;
import com.hy.sys.entity.Progroupmember;
import com.hy.sys.entity.Syspersons;
import com.hy.sys.kefu.vo.BatchAddGroupMemberParam;
import com.hy.sys.kefu.vo.GroupMemberParam;
import com.hy.sys.service.ProgroupService;
import com.hy.sys.service.ProgroupmemberService;
import com.hy.sys.service.SyspersonsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * @author Administrator
 * @ClassName ProgroupMemberController
 * description:
 * yao create 2023年08月15日
 * version: 1.0
 */
@RestController
@RequestMapping("group/member")
@Api(value = "群成员接口管理", tags = {"ProgroupMemberController"})
public class ProgroupMemberController {

    @Autowired
    private ProgroupmemberService progroupmemberService;

    @Autowired
    private ProgroupService progroupService;

    @Autowired
    private SyspersonsService syspersonsService;


    @PostMapping("batchAdd")
    @ApiOperation(value = "批量添加群成员")
    public CommonResult batchAdd(@Valid BatchAddGroupMemberParam param) {

        return progroupmemberService.batchAdd(param.getGroupId(), param.getMemberIds());
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除群成员")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "groupId", value = "群组id", required = true, paramType = "query", dataType = "String", dataTypeClass = String.class),
                    @ApiImplicitParam(name = "memberId", value = "用户id", required = true, paramType = "query", dataType = "String", dataTypeClass = String.class),})
    public CommonResult delete(String groupId, String memberId) {
        QueryWrapper<Progroupmember> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Progroupmember::getQid, groupId)
                .eq(Progroupmember::getYhid, memberId);
        boolean b = progroupmemberService.remove(queryWrapper);
        if (!b) {
            CommonResult.failed(ErrorMsgConstant.DELETE_ERROR);
        }
        return CommonResult.success();
    }

    @PostMapping("add")
    @ApiOperation(value = "新增群成员")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "groupId", value = "群组id", required = true, paramType = "query", dataType = "String", dataTypeClass = String.class),
                    @ApiImplicitParam(name = "memberId", value = "用户id", required = true, paramType = "query", dataType = "String", dataTypeClass = String.class),})
    public CommonResult add(String groupId, String memberId) {
        Progroup progroup = progroupService.getById(groupId);
        if (progroup == null) {
            return CommonResult.failed("群聊不存在");
        }
        Syspersons user = syspersonsService.getUserById(memberId);
        if (user == null) {
            return CommonResult.failed("用户不存在");
        }
        Progroupmember progroupmember = new Progroupmember();
        progroupmember.setQid(groupId);
        progroupmember.setYhid(memberId);
        progroupmember.setQjs(GroupRoleEnum.MEMBER.getCode());
        progroupmember.setCjsj(LocalDateTime.now());
        progroupmember.setQnc(user.getNickname());
        boolean b = progroupmemberService.save(progroupmember);
        if (!b) {
            CommonResult.failed(ErrorMsgConstant.DELETE_ERROR);
        }
        return CommonResult.success();
    }


    @PostMapping("updateNickname")
    @ApiOperation(value = "修改群昵称")
    public CommonResult updateNickName(@Valid GroupMemberParam groupMemberParam) {
        Progroup progroup = progroupService.getById(groupMemberParam.getQid());
        if (progroup == null) {
            return CommonResult.failed("群聊不存在");
        }
        Syspersons user = syspersonsService.getUserById(groupMemberParam.getYhid());
        if (user == null) {
            return CommonResult.failed("用户不存在");
        }
        UpdateWrapper<Progroupmember> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Progroupmember::getQid, groupMemberParam.getQid())
                .eq(Progroupmember::getYhid, groupMemberParam.getYhid())
                .set(Progroupmember::getQnc, groupMemberParam.getQnc());
        boolean b = progroupmemberService.update(updateWrapper);
        if (!b) {
            CommonResult.failed(ErrorMsgConstant.UPDATE_ERROR);
        }
        return CommonResult.success();
    }

    @PostMapping("updateRole")
    @ApiOperation(value = "修改群角色")
    public CommonResult updateGroupRole(@Valid GroupMemberParam groupMemberParam, HttpServletRequest request) {
        Progroup progroup = progroupService.getById(groupMemberParam.getQid());
        if (progroup == null) {
            return CommonResult.failed("群聊不存在");
        }
        QueryWrapper<Progroupmember> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.lambda().eq(Progroupmember::getQid, groupMemberParam.getQid())
                .eq(Progroupmember::getYhid, groupMemberParam.getYhid());
        Progroupmember user = progroupmemberService.getOne(userQueryWrapper);
        if (user == null) {
            return CommonResult.failed("用户不存在");
        }
        UserJwtDto userJwtDto = UserUtils.getUser(request);
        if (userJwtDto == null) {
            return CommonResult.failed(ResultCode.UNAUTHORIZED);
        }
        QueryWrapper<Progroupmember> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Progroupmember::getQid, groupMemberParam.getQid())
                .eq(Progroupmember::getYhid, userJwtDto.getId());
        Progroupmember member = progroupmemberService.getOne(queryWrapper);
        if (member == null) {
            return CommonResult.failed("非法用户");
        }
        // 群主才可以修改角色
        if (GroupRoleEnum.OWNER.getCode() == groupMemberParam.getQjs()) {
            return CommonResult.failed("不能修改群主角色");
        } else if (GroupRoleEnum.MANAGER.getCode() == groupMemberParam.getQjs()
                || GroupRoleEnum.MEMBER.getCode() == groupMemberParam.getQjs()) {
            if (member.getQjs() == GroupRoleEnum.OWNER.getCode()) {
                user.setQjs(groupMemberParam.getQjs());
                progroupmemberService.updateById(user);
            } else {
                return CommonResult.failed("暂无操作权限");
            }

        }

        return CommonResult.success();
    }


}
