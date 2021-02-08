package com.coral.mini.program.common.business.service;

import com.coral.mini.program.common.business.BaseService;
import com.coral.mini.program.common.business.entity.DepartmentHeader;

import java.util.List;

/**
 * 部门负责人接口
 * @author coral
 */
public interface DepartmentHeaderService extends BaseService<DepartmentHeader,String> {

    /**
     * 通过部门和负责人类型获取
     * @param departmentId
     * @param type
     * @return
     */
    List<String> findHeaderByDepartmentId(String departmentId, Integer type);

    /**
     * 通过部门id删除
     * @param departmentId
     */
    void deleteByDepartmentId(String departmentId);

    /**
     * 通过userId删除
     * @param userId
     */
    void deleteByUserId(String userId);
}