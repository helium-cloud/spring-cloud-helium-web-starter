package com.coral.mini.program.common.business.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.coral.mini.program.common.business.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import javax.persistence.*;

/**
 * @author coral
 */

@Entity
@Table( name = "t_user_role",
        indexes = {
                @Index(columnList = "userId"),
                @Index(columnList = "roleId")
        }
)
@TableName("t_user_role")
@ApiModel(value = "用户角色")
public class UserRole extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户唯一id")
    @Column(length = 64)
    private String userId;

    @ApiModelProperty(value = "角色唯一id")
    @Column(length = 64)
    private String roleId;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "角色名")
    private String roleName;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
