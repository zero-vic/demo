package com.hy.demo.service; /**
 * Description：
 * yao create 2023/6/6 16:00
 **/

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.hy.demo.common.ResultCode;
import com.hy.demo.constants.FileTypeConstant;
import com.hy.demo.dto.FileUploadDto;
import com.hy.demo.entity.Profile;
import com.hy.demo.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *  @ClassName FileService
 *  description: 文件服务
 *  yao create 2023年06月06日
 *  version: 1.0
 */
@Component
public class FileService {
    @Value("${upload.location}")
    private String fileUploadPath;

    @Autowired
    private ProfileService profileService;

    /**
     * 限定文件大小，指定文件类型集合
     * @param file 文件
     * @param prefix 额外添加的路径
     * @param fileTypes 文件类型集合
     * @param limitFileSize 文件大小限制
     * @param fileDto 额外的信息
     * @return 0.0
     * @throws IOException
     */
    public Boolean uploadFile(MultipartFile file, String prefix, List<String> fileTypes,Long limitFileSize, FileUploadDto fileDto) throws IOException {
        //获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        // 获取文件类型
        String contentType = FileUtil.extName(originalFilename);
        // 获取文件大小
        long fileSize = file.getSize();
        if(CollUtil.isEmpty(fileTypes)){
            return false;
        }
        if(!fileTypes.contains(contentType)){
            throw new BusinessException(ResultCode.FILE_TYPE_ERROR.getCode(),ResultCode.FILE_TYPE_ERROR.getMessage());
        }
        if (fileSize > limitFileSize) {
            throw new BusinessException(ResultCode.FILE_SIZE_LIMIT.getCode(), ResultCode.FILE_SIZE_LIMIT.getMessage());
        }
        return uploadFile(file,prefix,fileDto);
    }
    /**
     * 限制文件大小，指定文件类型
     * @param file 文件
     * @param prefix 额外添加的路径
     * @param fileType 文件类型
     * @param limitFileSize 文件大小限制
     * @param fileDto 额外的信息
     * @return 0.0
     * @throws IOException
     */
    public Boolean uploadFile(MultipartFile file, String prefix, String fileType,Long limitFileSize, FileUploadDto fileDto) throws IOException {
        //获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        // 获取文件类型
        String contentType = FileUtil.extName(originalFilename);
        // 获取文件大小 1
        long fileSize = file.getSize();
        if (fileSize > limitFileSize) {
            throw new BusinessException(ResultCode.FILE_SIZE_LIMIT.getCode(), ResultCode.FILE_SIZE_LIMIT.getMessage());
        }
        if(StrUtil.isEmpty(fileType)){
            return false;
        }
        if(fileType.toLowerCase().equals(contentType)){
            throw new BusinessException(ResultCode.FILE_TYPE_ERROR.getCode(),ResultCode.FILE_TYPE_ERROR.getMessage());
        }
        return uploadFile(file,prefix,fileDto);
    }

    /**
     * 不限制文件大小，根据文件类型集合
     * @param file 文件
     * @param prefix 额外添加的路径
     * @param fileTypes 文件类型集合
     * @param fileDto 额外的信息
     * @return 0.0
     * @throws IOException
     */
    public Boolean uploadFile(MultipartFile file, String prefix, List<String> fileTypes, FileUploadDto fileDto) throws IOException {
        //获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        // 获取文件类型
        String contentType = FileUtil.extName(originalFilename);
        // 获取文件大小
        long fileSize = file.getSize();
        if(CollUtil.isEmpty(fileTypes)){
            return false;
        }
        if(!fileTypes.contains(contentType)){
            throw new BusinessException(ResultCode.FILE_TYPE_ERROR.getCode(),ResultCode.FILE_TYPE_ERROR.getMessage());
        }
        return uploadFile(file,prefix,fileDto);
    }

    private Boolean uploadFile(MultipartFile file,String prefix,FileUploadDto fileDto) throws IOException {
        //获取文件原始名称
        String originalFilename = file.getOriginalFilename();
        // 获取文件类型
        String contentType = FileUtil.extName(originalFilename);
        // 获取文件大小
        long fileSize = file.getSize();
        //处理前缀
        String pathPrefix = StrUtil.appendIfMissing(prefix, StrUtil.SLASH, StrUtil.SLASH);
        // 创建文件名称
        String uuid = UUID.randomUUID().toString();
        //文件后缀
        String suffix = StrUtil.DOT+contentType;
        //文件路径
        String filepath =pathPrefix+ uuid+ suffix;
        //文件名
        String filename = StrUtil.removeSuffix(originalFilename, suffix);
//            String fileUploadPath = "F:/temp/";
        File uploadParentFile = new File(fileUploadPath);
        //判断文件目录是否存在
        if(!uploadParentFile.exists()) {
            //如果不存在就创建文件夹
            uploadParentFile.mkdirs();
        }
        File uploadFile = new File(fileUploadPath + filepath);
        if (!uploadFile.exists()) {
            uploadFile.mkdirs();
        }
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
        if(fileDto!=null){
            profile.setAdd_userid(fileDto.getAdd_userid());
            profile.setProid(fileDto.getProid());
            profile.setAlluserid(fileDto.getAlluserid());
            profile.setCurruerid(fileDto.getCurruerid());
            profile.setDataid(fileDto.getDataid());
            profile.setFileclass(fileDto.getFileclass());
            profile.setFiletype(fileDto.getFiletype());
            profile.setHost_govid(fileDto.getHost_govid());
            profile.setSource(fileDto.getSource());
            profile.setState(fileDto.getState());
            profile.setLinkaddr(fileDto.getLinkaddr());
            profile.setType(fileDto.getType());
        }
        profileService.save(profile);
        return true;
    }
}
