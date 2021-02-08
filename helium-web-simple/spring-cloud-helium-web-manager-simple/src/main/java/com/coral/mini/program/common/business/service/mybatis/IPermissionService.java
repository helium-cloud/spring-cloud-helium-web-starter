package com.coral.mini.program.common.business.service.mybatis;

import com.baomidou.mybatisplus.service.IService;
import com.coral.mini.program.common.business.BaseService;
import com.coral.mini.program.common.business.entity.Permission;

import java.util.List;

/**
 * @author coral
 */
public interface IPermissionService extends IService<Permission> {

    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    List<Permission> findByUserId(String userId);
}
