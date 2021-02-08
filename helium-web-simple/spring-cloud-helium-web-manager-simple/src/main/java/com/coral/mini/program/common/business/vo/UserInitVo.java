package com.coral.mini.program.common.business.vo;

import lombok.Data;

@Data
public class UserInitVo {

    private String id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 用户部门名称
     */
    private String department;
    /**
     * 角色
     */
    private String role;
}
