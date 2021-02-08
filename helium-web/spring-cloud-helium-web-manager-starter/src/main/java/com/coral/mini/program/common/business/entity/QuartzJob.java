package com.coral.mini.program.common.business.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.coral.mini.program.common.business.BaseEntity;
import com.coral.mini.program.common.common.constant.CommonConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author coral
 */

@Entity
@Table(name = "t_quartz_job")
@TableName("t_quartz_job")
@ApiModel(value = "定时任务")
public class QuartzJob extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "任务类名")
    private String jobClassName;

    @ApiModelProperty(value = "cron表达式")
    private String cronExpression;

    @ApiModelProperty(value = "参数")
    private String parameter;

    @ApiModelProperty(value = "备注")
    private String description;

    @ApiModelProperty(value = "状态 0正常 -1停止")
    private Integer status = CommonConstant.STATUS_NORMAL;

    public String getJobClassName() {
        return jobClassName;
    }

    public void setJobClassName(String jobClassName) {
        this.jobClassName = jobClassName;
    }

    public String getCronExpression() {
        return cronExpression;
    }

    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
