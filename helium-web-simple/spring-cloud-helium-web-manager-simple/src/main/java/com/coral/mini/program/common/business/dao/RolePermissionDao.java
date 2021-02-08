package com.coral.mini.program.common.business.dao;

import com.coral.mini.program.common.business.BaseDao;
import com.coral.mini.program.common.business.entity.RolePermission;

import java.util.List;

/**
 * 角色权限数据处理层
 * @author coral
 */
public interface RolePermissionDao extends BaseDao<RolePermission,String> {

    /**
     * 通过permissionId获取
     * @param permissionId
     * @return
     */
    List<RolePermission> findByPermissionId(String permissionId);

    /**
     * 通过roleId获取
     * @param roleId
     */
    List<RolePermission> findByRoleId(String roleId);

    /**
     * 通过roleId删除
     * @param roleId
     */
    void deleteByRoleId(String roleId);


    /**
     * 通过permissionId删除
     * @param permissionId
     */
    void deleteByPermissionId(String permissionId);
}