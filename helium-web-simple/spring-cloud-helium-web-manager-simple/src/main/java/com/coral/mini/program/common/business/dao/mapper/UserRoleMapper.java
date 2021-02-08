package com.coral.mini.program.common.business.dao.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.coral.mini.program.common.business.entity.Role;
import com.coral.mini.program.common.business.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author coral
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 通过用户id获取
     * @param userId
     * @return
     */
    List<Role> findByUserId(@Param("userId") String userId);

    /**
     * 通过用户id获取用户角色关联的部门数据
     * @param userId
     * @return
     */
    List<String> findDepIdsByUserId(@Param("userId") String userId);
}
