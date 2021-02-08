package com.coral.mini.program.common.business.vo;

import lombok.Data;

import java.util.List;

@Data
public class InitSourceModel {
    private List<UserInitVo> users;


    private List<RoleInitVo> roles;

    private List<DepartmentInitVo> departments;

    private List<PermissionInitVo> permissions;


}
