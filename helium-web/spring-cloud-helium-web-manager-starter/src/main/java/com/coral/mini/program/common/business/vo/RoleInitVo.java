package com.coral.mini.program.common.business.vo;


import java.util.List;


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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DepartmentInitVo> getDepartments() {
        return departments;
    }

    public void setDepartments(List<DepartmentInitVo> departments) {
        this.departments = departments;
    }

    public List<PermissionInitVo> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionInitVo> permissions) {
        this.permissions = permissions;
    }
}
