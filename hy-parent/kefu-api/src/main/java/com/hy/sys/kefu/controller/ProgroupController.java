package com.hy.sys.kefu.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.sys.common.constants.AuthConstant;
import com.hy.sys.common.constants.ErrorMsgConstant;
import com.hy.sys.common.constants.MessageConstant;
import com.hy.sys.common.dto.UserJwtDto;
import com.hy.sys.common.enums.GroupMuteEnum;
import com.hy.sys.common.enums.GroupRoleEnum;
import com.hy.sys.common.enums.GroupStatusEnum;
import com.hy.sys.common.result.CommonPage;
import com.hy.sys.common.result.CommonResult;
import com.hy.sys.common.result.ResultCode;
import com.hy.sys.common.utils.UserUtils;
import com.hy.sys.entity.Progroup;
import com.hy.sys.entity.Progroupmember;
import com.hy.sys.entity.Prowebsite;
import com.hy.sys.entity.Syspersons;
import com.hy.sys.kefu.vo.ProgroupParam;
import com.hy.sys.kefu.vo.ProgroupVo;
import com.hy.sys.service.ProgroupService;
import com.hy.sys.service.ProgroupmemberService;
import com.hy.sys.service.ProwebsiteService;
import com.hy.sys.service.SyspersonsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.LocalDateTime;

/**
 * @ClassName ProgroupController
 * description:
 * yao create 2023年08月15日
 * version: 1.0
 */
@Api(value = "群组管理接口",tags = {"ProgroupController"})
@RestController
@RequestMapping("group")
public class ProgroupController {
    @Autowired
    private ProgroupService progroupService;
    @Autowired
    private ProgroupmemberService progroupmemberService;
    @Autowired
    private SyspersonsService syspersonsService;

    @Autowired
    private ProwebsiteService prowebsiteService;

    @PostMapping("createGroup")
    @ApiOperation(value = "创建群组接口")
    public CommonResult createGroup(@Valid ProgroupParam progroupParam, HttpServletRequest request){
        UserJwtDto userJwtDto = UserUtils.getUser(request);
        if(userJwtDto==null){
            return CommonResult.failed(ResultCode.UNAUTHORIZED);
        }
        Progroup progroup = new Progroup();
        BeanUtils.copyProperties(progroupParam,progroup);
        LocalDateTime now = LocalDateTime.now();
        progroup.setCjsj(now);
        progroup.setXgsj(now);
        progroup.setQzt(GroupStatusEnum.NORMAL.getCode());
        progroup.setSfjy(GroupMuteEnum.NORMAL.getCode());
        progroupService.save(progroup);
        Syspersons user = syspersonsService.getUserById(userJwtDto.getId());
        // 添加群成员
        Progroupmember progroupmember = new Progroupmember();
        progroupmember.setYhid(userJwtDto.getId());
        progroupmember.setQid(progroup.getTid());
        progroupmember.setQnc(user.getNickname());
        progroupmember.setCjsj(now);
        progroupmember.setQjs(GroupRoleEnum.OWNER.getCode());
        progroupmemberService.save(progroupmember);

        return CommonResult.success();
    }

    @PostMapping("details")
    @ApiOperation(value = "获取群组详情")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "id",value = "群组id",required = true,paramType = "query",dataType = "String",dataTypeClass = String.class),})
    public CommonResult groupDetails(String id){
        Progroup progroup = progroupService.getById(id);
        if(progroup == null){
            return CommonResult.failed(ErrorMsgConstant.NO_DATA_ERROR);
        }
        ProgroupVo progroupVo = new ProgroupVo();
        BeanUtils.copyProperties(progroup,progroupVo);
        String wzid = progroup.getWzid();
        if(StrUtil.isEmpty(wzid)){
            Prowebsite site = prowebsiteService.getById(wzid);
            if(site!=null){
                progroupVo.setWzmc(site.getName());
            }
        }
        return CommonResult.success(progroupVo);
    }

    @PostMapping("update")
    @ApiOperation(value = "更新群组信息")
    public CommonResult updateGroup(@Valid ProgroupParam progroupParam){
        Progroup progroup = new Progroup();
        BeanUtils.copyProperties(progroupParam,progroup);
        LocalDateTime now = LocalDateTime.now();
        progroup.setXgsj(now);
        boolean b = progroupService.updateById(progroup);
        if(!b){
            return CommonResult.failed(ErrorMsgConstant.UPDATE_ERROR);
        }
        return CommonResult.success();
    }
    @PostMapping("getGroups")
    @ApiOperation(value = "获取已加入的群组")
    public CommonResult getJoinedGroups(Long page,Long limit ,String userId){
        page = page == null  ? 0: page;
        limit= limit == null ? 10 :limit;
        IPage<Progroup> iPage = new Page<>(page,limit);
        IPage<Progroup> pageList =  progroupService.getGroupsByUserId(iPage,userId);
        return CommonResult.success(new CommonPage<>(pageList));
    }
    @PostMapping("dissolveGroup")
    @ApiOperation(value = "解散群聊")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "id",value = "群组id",required = true,paramType = "query",dataType = "String",dataTypeClass = String.class),})
    public CommonResult dissolveGroup(String id,HttpServletRequest request){
        UserJwtDto user = UserUtils.getUser(request);
        if(user == null){
            return CommonResult.failed(ResultCode.UNAUTHORIZED);
        }
        QueryWrapper<Progroupmember> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Progroupmember::getQid,id)
                .eq(Progroupmember::getYhid,user.getId())
                .eq(Progroupmember::getQjs,GroupRoleEnum.OWNER.getCode());
        Progroupmember one = progroupmemberService.getOne(queryWrapper);
        if(one == null){
            return CommonResult.failed("群主才能解散群聊");
        }
        UpdateWrapper<Progroup> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Progroup::getTid,id)
                .set(Progroup::getQzt,GroupStatusEnum.DELETE.getCode())
                .set(Progroup::getXgsj,LocalDateTime.now());
        boolean b = progroupService.update(updateWrapper);
        if(!b){
            return CommonResult.failed(ErrorMsgConstant.UPDATE_ERROR);
        }
        return CommonResult.success();
    }
}
