package com.coral.mini.program.common.business.service;


import com.coral.mini.program.common.business.BaseService;
import com.coral.mini.program.common.business.entity.Role;

import java.util.List;

/**
 * 角色接口
 * @author coral
 */
public interface RoleService extends BaseService<Role,String> {

    /**
     * 获取默认角色
     * @param defaultRole
     * @return
     */
    List<Role> findByDefaultRole(Boolean defaultRole);
}
