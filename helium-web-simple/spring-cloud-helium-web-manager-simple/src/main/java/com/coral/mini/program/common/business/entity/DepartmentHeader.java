package com.coral.mini.program.common.business.entity;


import com.baomidou.mybatisplus.annotations.TableName;
import com.coral.mini.program.common.business.BaseEntity;
import com.coral.mini.program.common.common.constant.CommonConstant;
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
@Table( name = "t_department_header",
        indexes = {
                @Index(columnList = "departmentId"),
                @Index(columnList = "userId")
            }
)
@TableName("t_department_header")
@ApiModel(value = "部门负责人")
public class DepartmentHeader extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "关联部门id")
    @Column(length = 64)
    private String departmentId;

    @ApiModelProperty(value = "关联部门负责人")
    @Column(length = 64)
    private String userId;

    @ApiModelProperty(value = "负责人类型 默认0主要 1副职")
    private Integer type = CommonConstant.HEADER_TYPE_MAIN;
}