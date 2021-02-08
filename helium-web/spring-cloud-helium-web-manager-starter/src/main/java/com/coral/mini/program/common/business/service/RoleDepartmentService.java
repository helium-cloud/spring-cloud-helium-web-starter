package com.coral.mini.program.common.business.service;

import com.coral.mini.program.common.business.BaseService;
import com.coral.mini.program.common.business.entity.RoleDepartment;

import java.util.List;

/**
 * 角色部门接口
 * @author coral
 */
public interface RoleDepartmentService extends BaseService<RoleDepartment,String> {

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