package com.hy.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hy.sys.common.result.CommonResult;
import com.hy.sys.entity.Progroupmember;

import java.util.List;


/**
 * <p>
 * 群成员表 服务类
 * </p>
 *
 * @author 
 * @since 2023-08-15
 */
public interface ProgroupmemberService extends IService<Progroupmember> {

    CommonResult batchAdd(String groupId, List<String> memberIds);
}
