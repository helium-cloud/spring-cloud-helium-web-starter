package com.coral.mini.program.common.business.service;


import com.coral.mini.program.common.business.BaseService;
import com.coral.mini.program.common.business.entity.UserRole;

import java.util.List;

/**
 * 用户角色接口
 * @author coral
 */
public interface UserRoleService extends BaseService<UserRole,String> {

    /**
     * 通过roleId查找
     * @param roleId
     * @return
     */
    List<UserRole> findByRoleId(String roleId);

    /**
     * 删除用户角色
     * @param userId
     */
    void deleteByUserId(String userId);
}
