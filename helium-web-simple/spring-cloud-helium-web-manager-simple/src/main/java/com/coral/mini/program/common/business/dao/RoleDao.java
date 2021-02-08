package com.coral.mini.program.common.business.dao;

import com.coral.mini.program.common.business.BaseDao;
import com.coral.mini.program.common.business.entity.Role;

import java.util.List;

/**
 * 角色数据处理层
 * @author coral
 */
public interface RoleDao extends BaseDao<Role,String> {

    /**
     * 获取默认角色
     * @param defaultRole
     * @return
     */
    List<Role> findByDefaultRole(Boolean defaultRole);
}
