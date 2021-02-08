package com.coral.mini.program.common.business.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class PermissionInitVo {

    private String id;

    /**
     * 权限名称
     */
    private String name;
    /**
     * 权限级别
     */
    private int level;
    /**
     * 权限标题
     */
    private String title;
    /**
     * 权限路径
     */
    private String path;
    /**
     * 权限组件
     */
    private String component;
    /**
     * 权限图标
     */
    private String icon;
    /**
     * 排序字段
     */
    private BigDecimal sortOrder;

    /**
     * 子权限
     */
    private List<PermissionInitVo> children;
}
