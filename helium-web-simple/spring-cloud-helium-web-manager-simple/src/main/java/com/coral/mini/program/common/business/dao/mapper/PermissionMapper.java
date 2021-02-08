package com.coral.mini.program.common.business.dao.mapper;


import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.coral.mini.program.common.business.entity.Permission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author coral
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    List<Permission> findByUserId(@Param("userId") String userId);
}
