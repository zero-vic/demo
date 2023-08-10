package com.hy.sys.kefu.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.hy.sys.common.constants.AuthConstant;
import com.hy.sys.common.constants.ErrorMsgConstant;
import com.hy.sys.common.constants.FileTypeConstant;
import com.hy.sys.common.dto.FileUploadDto;
import com.hy.sys.common.exception.BusinessException;
import com.hy.sys.common.result.CommonResult;
import com.hy.sys.common.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @ClassName SysUploadController
 * description:
 * yao create 2023年08月01日
 * version: 1.0
 */
@RestController
@RequestMapping("system")
@Api(value = "系统上传接口",tags = {"SysUploadController"})
public class SysUploadController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "头像上传接口")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "file", value = "图片类型", required = true,paramType = "query",dataType = "MultipartFile",allowMultiple = false,dataTypeClass = MultipartFile.class),})
    @PostMapping("upload/avatar")
    public CommonResult uploadAvatar(@RequestPart("file") MultipartFile file, HttpServletRequest request) throws IOException {
        if (file != null){
            String token = request.getHeader(AuthConstant.USER_TOKEN_HEADER);
            if(StrUtil.isEmpty(token)){
                throw new BusinessException("暂未登陆");
            }

            JSONObject jsonObject = JSONUtil.parseObj(token);

            FileUploadDto fileDto = new FileUploadDto();
            fileDto.setCurruerid(jsonObject.getStr("id"));
            fileDto.setAdd_userid(jsonObject.getStr("id"));
            String profix = "system";
            String filePath = fileService.uploadFile(file, profix, FileTypeConstant.IMAGE_TYPES,FileTypeConstant.AVATAR_FILE_SIZE, fileDto);
            if (StringUtils.isNotBlank(filePath)) {
                return CommonResult.success(filePath);
            }else {
                return CommonResult.failed(ErrorMsgConstant.FILE_UPLOAD_ERROR);
            }

        }
        return CommonResult.failed(ErrorMsgConstant.FILE_EMPTY_ERROR);
    }


}
