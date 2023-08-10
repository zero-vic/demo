package com.hy.sys.kefu.controller;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hy.sys.common.constants.ErrorMsgConstant;
import com.hy.sys.common.dto.TransferDto;
import com.hy.sys.common.enums.DelFlagEnum;
import com.hy.sys.common.result.CommonPage;
import com.hy.sys.common.result.CommonResult;
import com.hy.sys.entity.Proconversation;
import com.hy.sys.entity.Promsgrecord;
import com.hy.sys.entity.Prowebsite;
import com.hy.sys.kefu.vo.*;
import com.hy.sys.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName WebSiteController
 * description:
 * yao create 2023年07月20日
 * version: 1.0
 */
@RestController
@RequestMapping("system")
@Api(value = "站点管理接口",tags = {"WebSiteController"})
public class WebSiteController {

    @Autowired
    private ProwebsiteService prowebsiteService;

    @Autowired
    private PromsgrecordService promsgrecordService;

    @Autowired
    private ProconversationService proconversationService;

    @Autowired
    private OfflineMessageService offlineMessageService;

    @Autowired
    private SyspersonsService syspersonsService;

    /**
     * 获取站点列表
     * @param pageParam
     * @return
     */
    @ApiOperation(value = "获取站点列表接口")
    @PostMapping("website/GetListPage")
    public CommonResult getWebSiteList(PageParam pageParam){
        IPage<Prowebsite> page = new Page<>(pageParam.getPage(), pageParam.getLimit());

        QueryWrapper<Prowebsite> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ws.isdel", DelFlagEnum.NORMAL.getCode());
        if(StringUtils.isNotBlank(pageParam.getCondition())){
            queryWrapper.like("ws.name",pageParam.getCondition());
        }
        IPage<Prowebsite> iPage = prowebsiteService.getListPage(page,queryWrapper);
        return CommonResult.success(new CommonPage<>(iPage));
    }

    @ApiOperation(value = "新增站点接口")
    @PostMapping("website/add")
    public CommonResult addWebSite(@Valid ProwebsiteParam prowebsiteParam){

        Prowebsite prowebsite = new Prowebsite();
        BeanUtils.copyProperties(prowebsiteParam,prowebsite);
        prowebsite.setTid(UUID.randomUUID().toString());
        prowebsite.setIsdel(DelFlagEnum.NORMAL.getCode());
        boolean save = prowebsiteService.save(prowebsite);
        if(!save){
            return CommonResult.failed(ErrorMsgConstant.ADD_ERROR);
        }
        return CommonResult.success();
    }


    @ApiOperation(value = "获取站点详情接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "id",value = "用户id",paramType = "query",required = true,dataType = "String",dataTypeClass = String.class),})
    @PostMapping("website/Details")
    public CommonResult getWebSiteDetail(String id){
        Prowebsite prowebsite = prowebsiteService.getWebsiteById(id);
        return CommonResult.success(prowebsite);
    }


    @ApiOperation(value = "更新站点接口")
    @PostMapping("website/update")
    public CommonResult updateWebSite(@Valid ProwebsiteParam prowebsiteParam){
        Prowebsite prowebsite = new Prowebsite();
        BeanUtils.copyProperties(prowebsiteParam,prowebsite);
        boolean b = prowebsiteService.updateById(prowebsite);
        if(!b){
            return CommonResult.failed(ErrorMsgConstant.UPDATE_ERROR);
        }
        return CommonResult.success();
    }


    @ApiOperation(value = "删除站点接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "id",value = "站点id",required = true,paramType = "query",dataType = "String",dataTypeClass = String.class),})
    @PostMapping("website/Delete")
    public CommonResult deleteWebSite(String id){
        Prowebsite prowebsite = prowebsiteService.getById(id);
        if(prowebsite!=null){
            prowebsite.setIsdel(DelFlagEnum.DELETE.getCode());
        }else {
            return CommonResult.failed(ErrorMsgConstant.NO_DATA_ERROR);
        }
        prowebsiteService.updateById(prowebsite);
        return CommonResult.success();
    }

    /**
     * 获取客服数据 用于编辑回显
     * @param id
     * @return
     */
    @PostMapping("website/getPersonData")
    public CommonResult getPersonData(String id){
        Prowebsite prowebsite = prowebsiteService.getById(id);
        if(prowebsite == null){
            return CommonResult.failed(ErrorMsgConstant.NO_DATA_ERROR);
        }
        List<TransferDto> allData = new ArrayList<>();
        List<String> allocatedData = new ArrayList<>();
        if(!StringUtils.isBlank(prowebsite.getUnitid())){
            allData = syspersonsService.getUserByUnitId(prowebsite.getUnitid());
            String[] split = prowebsite.getPersonids().split(",");
            allocatedData = Arrays.asList(split);


        }
        Map<String,Object> map  = new HashMap<>(2);
        map.put("allData",allData);
        map.put("allocatedData",allocatedData);
        return CommonResult.success(map);
    }


    /**
     * 获取历史消息记录
     * @param msgRecordParam
     * @return
     */
    @PostMapping("msg/GetRecordListPage")
    public CommonResult getMsgRecordList(MsgRecordParam msgRecordParam){
        IPage<Promsgrecord> msgRecordList = promsgrecordService.getMsgRecordList(msgRecordParam.getSiteId(),
                msgRecordParam.getPostId(), msgRecordParam.getReceiveId(),
                msgRecordParam.getPageNum(), msgRecordParam.getPageSize());
        return CommonResult.success(new CommonPage<>(msgRecordList));
    }

    /**
     * 获取会话列表
     * @param siteId
     * @param postId
     * @return
     */
    @PostMapping("conversion/GetList")
    public CommonResult getConversionList(String siteId,String postId){
        List<Proconversation> conversationList = proconversationService.getConversationList(siteId, postId);
        if(CollectionUtils.isEmpty(conversationList)){
            return CommonResult.success(null);
        }
        List<ConversationVo> list = conversationList.stream().map(item -> {
            ConversationVo conversationVo = new ConversationVo();
            BeanUtils.copyProperties(item, conversationVo);
            String conversationId = item.getType() + "_" + item.getPostid() + "_" + item.getReceiveid();
            Long unreadMsgCount = offlineMessageService.getUnreadMsgCount(item.getSiteid(), conversationId);
            conversationVo.setUnreadCount(unreadMsgCount);
            return conversationVo;
        }).collect(Collectors.toList());

        return CommonResult.success(list);
    }

    /**
     * 获取离线聊天记录
     * @param offlineMessageParam
     * @return
     */
    @PostMapping("msg/GetList")
    public CommonResult getOfflineMessage(OfflineMessageParam offlineMessageParam){
        List<JSONObject> list = offlineMessageService.getOfflineMessage(offlineMessageParam.getSiteId(),
                offlineMessageParam.getConversationId(), offlineMessageParam.getLastSequence(),
                offlineMessageParam.getMaxLimit());

        return CommonResult.success(list);
    }

    /**
     * 获取离线聊天记录
     * @param offlineMessageParam
     * @param num
     * @return
     */
    @PostMapping("msg/GetNewList")
    public CommonResult getOfflineMessageByNum(OfflineMessageParam offlineMessageParam,long num){
        Set set = offlineMessageService.getOfflineMessageByNum(offlineMessageParam.getSiteId(), offlineMessageParam.getConversationId(), num);
        return CommonResult.success(set);
    }

    /**
     * 获取未读消息条数
     * @param siteId
     * @param conversationId
     * @return
     */
    @PostMapping("msg/getUnreadNum")
    public CommonResult getConversationUnreadCount(String siteId,String conversationId){
        Long unreadMsgCount = offlineMessageService.getUnreadMsgCount(siteId, conversationId);
        return CommonResult.success(unreadMsgCount);
    }



}
