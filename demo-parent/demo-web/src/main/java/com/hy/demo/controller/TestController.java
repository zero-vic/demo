package com.hy.demo.controller;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.hy.demo.common.CommonResult;
import com.hy.demo.constants.FileTypeConstant;
import com.hy.demo.entity.Profile;
import com.hy.demo.entity.TestUser;
import com.hy.demo.service.ITestUserService;
import com.hy.demo.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @Description
 * @Author yao
 * @Date 2023/6/1 10:28
 **/
@Controller
public class TestController {
    @Value("${upload.location}")
    private String fileUploadPath;
    @Autowired
    private ITestUserService userService;
    @Autowired
    private ProfileService profileService;
    @RequestMapping("/hello")
    public String getHello(Model model){
        List<TestUser> list = userService.list();
        model.addAttribute("users",list);
        return "hello";
    }
    @GetMapping(value = "/goToUpload")
    public String goToUploadHtml() {
        return "upload";
    }
    @PostMapping("upload")
    @ResponseBody
    public CommonResult upload(@RequestParam("file") MultipartFile file,@RequestParam("proid") String proid,HttpServletRequest request) throws IOException {
        if (file != null){
//            String token = request.getHeader(AuthConstant.JWT_TOKEN_HEADER);
//            if(StrUtil.isEmpty(token)){
//                throw new RuntimeException("暂未登陆");
//            }
//            String realToken = token.replace(AuthConstant.JWT_TOKEN_PREFIX, "");
//            JWT jwt = JWTUtil.parseToken(realToken);
//            String id = (String)jwt.getPayloads().get("id");
            //获取文件原始名称
            String originalFilename = file.getOriginalFilename();
            // 获取文件类型
            String contentType = FileUtil.extName(originalFilename);
            if(!FileTypeConstant.IMAGE_TYPES.contains(contentType)){
                throw new RuntimeException("上传文件类型错误");
            }
            // 获取文件大小
            long fileSize = file.getSize();
            // 创建文件名称
            String uuid = UUID.randomUUID().toString();
            String suffix = StrUtil.DOT+contentType;
            String filepath = uuid+ suffix;
            String filename = StrUtil.removeSuffix(originalFilename, suffix);
//            String fileUploadPath = "F:/temp/";
            File uploadParentFile = new File(fileUploadPath);
            //判断文件目录是否存在
            if(!uploadParentFile.exists()) {
                //如果不存在就创建文件夹
                uploadParentFile.mkdirs();
            }
            File uploadFile = new File(fileUploadPath + filepath);
            file.transferTo(uploadFile);
            // 保存上传信息到数据库


            Profile profile = new Profile();
            profile.setSuffix(suffix);
//            profile.setTid(UUID.randomUUID().toString());
            profile.setFile_name(filename);
            profile.setFile_size(StrUtil.toStringOrNull(fileSize));
            profile.setAddtime(DateUtil.date());
            profile.setFile_path(uploadFile.getPath());
            profile.setIsdel((short) 0);
//            profile.setAdd_userid(id);
            profile.setProid(proid);
            profileService.save(profile);


        }


        return CommonResult.success();
    }
}
