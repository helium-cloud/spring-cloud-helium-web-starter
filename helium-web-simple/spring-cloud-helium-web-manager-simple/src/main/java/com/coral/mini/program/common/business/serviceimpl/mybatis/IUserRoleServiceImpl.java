package com.coral.mini.program.common.business.serviceimpl.mybatis;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import com.coral.mini.program.common.business.dao.mapper.UserRoleMapper;
import com.coral.mini.program.common.business.entity.Role;
import com.coral.mini.program.common.business.entity.UserRole;
import com.coral.mini.program.common.business.service.mybatis.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author coral
 */
@Service
public class IUserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements IUserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<Role> findByUserId(String userId) {

        return userRoleMapper.findByUserId(userId);
    }

    @Override
    public List<String> findDepIdsByUserId(String userId) {

        return userRoleMapper.findDepIdsByUserId(userId);
    }
}
