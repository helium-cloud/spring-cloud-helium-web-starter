package com.coral.mini.program.common.business.vo;



import java.util.List;


public class InitSourceModel {
    private List<UserInitVo> users;


    private List<RoleInitVo> roles;

    private List<DepartmentInitVo> departments;

    private List<PermissionInitVo> permissions;

    public List<UserInitVo> getUsers() {
        return users;
    }

    public void setUsers(List<UserInitVo> users) {
        this.users = users;
    }

    public List<RoleInitVo> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleInitVo> roles) {
        this.roles = roles;
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
