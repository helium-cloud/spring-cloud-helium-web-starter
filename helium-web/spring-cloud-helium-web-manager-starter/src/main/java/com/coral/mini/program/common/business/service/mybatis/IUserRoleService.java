package com.coral.mini.program.common.business.service.mybatis;

import com.baomidou.mybatisplus.service.IService;
import com.coral.mini.program.common.business.entity.Role;
import com.coral.mini.program.common.business.entity.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author coral
 */
public interface IUserRoleService extends IService<UserRole> {

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
    List<String> findDepIdsByUserId(String userId);
}
