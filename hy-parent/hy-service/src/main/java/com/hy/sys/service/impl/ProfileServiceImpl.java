package com.hy.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hy.sys.entity.Profile;
import com.hy.sys.mapper.ProfileMapper;
import com.hy.sys.service.ProfileService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 项目附件表 服务实现类
 * </p>
 *
 * @author xy
 * @since 2023-06-06
 */
@Service
public class ProfileServiceImpl extends ServiceImpl<ProfileMapper, Profile> implements ProfileService {

}
