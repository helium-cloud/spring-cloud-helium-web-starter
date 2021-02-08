package com.coral.mini.program.common.business.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.coral.mini.program.common.business.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * @author coral
 */
@Data
@Entity
@Table( name = "t_role_department",
        indexes = {
            @Index(columnList = "roleId"),
            @Index(columnList = "departmentId")
        }
)
@TableName("t_role_department")
@ApiModel(value = "角色部门")
public class RoleDepartment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色id")
    @Column(length = 64)
    private String roleId;

    @ApiModelProperty(value = "部门id")
    @Column(length = 64)
    private String departmentId;
}