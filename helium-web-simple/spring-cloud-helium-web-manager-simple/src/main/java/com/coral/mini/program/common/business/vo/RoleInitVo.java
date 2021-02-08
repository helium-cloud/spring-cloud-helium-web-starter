package com.coral.mini.program.common.business.vo;

import lombok.Data;

import java.util.List;

@Data
public class RoleInitVo {

    private String id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色描述
     */
    private String description;

    private List<DepartmentInitVo> departments;

    private List<PermissionInitVo> permissions;

}
