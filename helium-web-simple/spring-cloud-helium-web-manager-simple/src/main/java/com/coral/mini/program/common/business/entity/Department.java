package com.coral.mini.program.common.business.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.coral.mini.program.common.business.BaseEntity;
import com.coral.mini.program.common.common.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author coral
 */
@Data
@Entity
@Table( name = "t_department",
        indexes = {
            @Index(columnList = "parentId")
        }
)
@TableName("t_department")
@ApiModel(value = "部门")
public class Department extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "部门名称")
    private String title;

    @ApiModelProperty(value = "父id")
    @Column(length = 64)
    private String parentId;

    @ApiModelProperty(value = "是否为父节点(含子节点) 默认false")
    private Boolean isParent = false;

    @ApiModelProperty(value = "排序值")
    @Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    @ApiModelProperty(value = "是否启用 0启用 -1禁用")
    private Integer status = CommonConstant.STATUS_NORMAL;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "父节点名称")
    private String parentTitle;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "主负责人")
    private List<String> mainHeader;

    @Transient
    @TableField(exist=false)
    @ApiModelProperty(value = "副负责人")
    private List<String> viceHeader;
}