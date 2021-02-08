package com.coral.mini.program.common.business.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DepartmentInitVo {

    private String id;
    /**
     * 部门名称
     */
    private String title;
    /**
     * 父级部门
     */
    private String parent;
    /**
     * 排序字段
     */
    private BigDecimal sortOrder;
    /**
     * 子部门
     */
    private List<DepartmentInitVo> children;
}
