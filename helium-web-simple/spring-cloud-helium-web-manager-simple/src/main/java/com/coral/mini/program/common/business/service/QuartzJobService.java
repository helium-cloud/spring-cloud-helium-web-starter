package com.coral.mini.program.common.business.service;

import com.coral.mini.program.common.business.BaseService;
import com.coral.mini.program.common.business.entity.QuartzJob;

import java.util.List;

/**
 * 定时任务接口
 * @author coral
 */
public interface QuartzJobService extends BaseService<QuartzJob,String> {

    /**
     * 通过类名获取
     * @param jobClassName
     * @return
     */
    List<QuartzJob> findByJobClassName(String jobClassName);
}