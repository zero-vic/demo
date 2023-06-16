package com.hy.demo.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hy.demo.annotation.AuthRequire;
import com.hy.demo.common.CommonPage;
import com.hy.demo.common.CommonResult;
import com.hy.demo.common.ResultCode;
import com.hy.demo.constants.AuthConstant;
import com.hy.demo.constants.FileTypeConstant;
import com.hy.demo.dto.FileUploadDto;
import com.hy.demo.entity.Zbmb4Infos;
import com.hy.demo.exception.BusinessException;
import com.hy.demo.service.FileService;
import com.hy.demo.service.IZbmb4InfosService;
import com.hy.demo.service.ProfileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * @Description
 * @Author yao
 * @Date 2023/5/24 15:09
 **/
@RestController
@Api(tags = "TestController",description = "测试接口管理")
public class TestController {
    @Value("${upload.location}")
    private String fileUploadPath;


    @Autowired
    private IZbmb4InfosService zbmb4InfosService;
    @Autowired
    private ProfileService profileService;

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "测式 admin权限接口")
    @GetMapping("/test")
    public String getStr(){
        System.out.println("-------------hello ----------");
        return "hello !!!!";
    }

    @ApiOperation(value = "测试test权限通用匹配接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum",value = "页码",required = true,paramType = "path",dataType = "Long",defaultValue = "1",dataTypeClass = Long.class),
            @ApiImplicitParam(name = "pageSize",value = "页码长度",required = true,paramType = "path",dataType = "Long",defaultValue = "5",dataTypeClass = Long.class)})
    @GetMapping("/user/test/{pageNum}/{pageSize}")
    public IPage<Zbmb4Infos> getList(@PathVariable("pageNum") Long pageNum, @PathVariable("pageSize") Long pageSize){
        System.out.println("-------------12我问问----------");
        return zbmb4InfosService.getList(pageNum, pageSize);
    }

    @GetMapping("pages/test")
    @ApiOperation(value = "测试test权限 ")
    public CommonResult getListTest(){

        return CommonResult.success("测试权限");
    }
    @GetMapping("pages/test/{pageNum}/{pageSize}")
    @ApiOperation(value = "测试test权限2 ")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum",value = "页码",required = true,paramType = "path",dataType = "Long",defaultValue = "1",dataTypeClass = Long.class),
            @ApiImplicitParam(name = "pageSize",value = "页码长度",required = true,paramType = "path",dataType = "Long",defaultValue = "5",dataTypeClass = Long.class)})
    public CommonResult getListTest2(){

        return CommonResult.success("测试权限2");
    }
    @GetMapping("pages/testPage")
    @ApiOperation(value = "测试分页 ")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum",value = "页码",required = true,paramType = "path",dataType = "Long",defaultValue = "1",dataTypeClass = Long.class),
            @ApiImplicitParam(name = "pageSize",value = "页码长度",required = true,paramType = "path",dataType = "Long",defaultValue = "5",dataTypeClass = Long.class)})
    public CommonResult getListTestPage(@RequestParam("pageNum")Long pageNum,@RequestParam("pageSize") Long pageSize){
        pageNum = pageNum == null ? 1L: pageNum;
        pageSize = pageSize == null ? 10L: pageSize;


        return CommonResult.success("测试分页");
    }

    @GetMapping("pages/test2")
    @ApiOperation(value = "测试test权限，权限标识符注解")
    @AuthRequire("1555225")
    public CommonResult getListTest3(@RequestParam("name") String name){

        return CommonResult.success("测试权限"+name);
    }

    @GetMapping("pages/exception")
    @ApiOperation(value = "测试test权限，自定义异常返回")
    public CommonResult testException(@RequestParam("name") String name){
        if(StrUtil.isNotEmpty(name)){
            if ("1".equals(name)) {
                throw new BusinessException("自定义异常");
            }
            if ("2".equals(name)) {
                throw new BusinessException(ResultCode.FILE_TYPE_ERROR.getCode(),ResultCode.FILE_TYPE_ERROR.getMessage());
            }

        }
        return CommonResult.success("测试权限"+name);
    }
    @ApiOperation("封装结果返回测试接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "pageNum",value = "页码",required = true,paramType = "path",dataType = "Long",defaultValue = "1",dataTypeClass = Long.class),
            @ApiImplicitParam(name = "pageSize",value = "页码长度",required = true,paramType = "path",dataType = "Long",defaultValue = "5",dataTypeClass = Long.class)})
    @GetMapping("/user/test/list/{pageNum}/{pageSize}")
    public CommonResult<CommonPage<Zbmb4Infos>> list(@PathVariable("pageNum") Long pageNum, @PathVariable("pageSize") Long pageSize){

            return CommonResult.success(new CommonPage<>(zbmb4InfosService.getList(pageNum,pageSize)));
    }
    @ApiOperation(value = "文件上传")
    @PostMapping("upload")
    //allowMultiple = true 设置多文件上传
    @ApiImplicitParams({@ApiImplicitParam(name = "file", value = "文件流对象", required = true,paramType = "query",dataType = "MultipartFile",allowMultiple = false,dataTypeClass = MultipartFile.class),
            @ApiImplicitParam(name = "proid", value = "proid", required = true,dataType = "String",dataTypeClass = String.class)})
    public CommonResult upload(@RequestPart("file") MultipartFile file, @RequestParam("proid") String proid, HttpServletRequest request) throws IOException {
        if (file != null){
            String token = request.getHeader(AuthConstant.JWT_TOKEN_HEADER);
            if(StrUtil.isEmpty(token)){
                throw new RuntimeException("暂未登陆");
            }
            String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
            JWT jwt = JWTUtil.parseToken(realToken);
            String id = (String)jwt.getPayloads().get("id");
            FileUploadDto fileDto = new FileUploadDto();
            fileDto.setProid("cetsssk-1111");
            fileDto.setAdd_userid(id);
            String profix = "test";
            Boolean flag = fileService.uploadFile(file, profix, FileTypeConstant.IMAGE_TYPES, fileDto);
            if (flag) {
                return CommonResult.success();
            }else {
                return CommonResult.failed();
            }

        }


        return CommonResult.success();
    }


}
