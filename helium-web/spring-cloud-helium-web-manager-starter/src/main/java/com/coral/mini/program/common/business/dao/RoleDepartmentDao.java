package com.coral.mini.program.common.business.dao;

import com.coral.mini.program.common.business.BaseDao;
import com.coral.mini.program.common.business.entity.RoleDepartment;

import java.util.List;

/**
 * 角色部门数据处理层
 * @author coral
 */
public interface RoleDepartmentDao extends BaseDao<RoleDepartment,String> {

    /**
     * 通过roleId获取
     * @param roleId
     * @return
     */
    List<RoleDepartment> findByRoleId(String roleId);

    /**
     * 通过角色id删除
     * @param roleId
     */
    void deleteByRoleId(String roleId);

    /**
     * 通过角色id删除
     * @param departmentId
     */
    void deleteByDepartmentId(String departmentId);
}