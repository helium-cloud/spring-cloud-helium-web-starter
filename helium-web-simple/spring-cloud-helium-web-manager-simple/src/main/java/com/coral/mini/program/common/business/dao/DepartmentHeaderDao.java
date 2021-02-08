package com.coral.mini.program.common.business.dao;

import com.coral.mini.program.common.business.BaseDao;
import com.coral.mini.program.common.business.entity.DepartmentHeader;

import java.util.List;

/**
 * 部门负责人数据处理层
 * @author coral
 */
public interface DepartmentHeaderDao extends BaseDao<DepartmentHeader,String> {

    /**
     * 通过部门和负责人类型获取
     * @param departmentId
     * @param type
     * @return
     */
    List<DepartmentHeader> findByDepartmentIdAndType(String departmentId, Integer type);

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