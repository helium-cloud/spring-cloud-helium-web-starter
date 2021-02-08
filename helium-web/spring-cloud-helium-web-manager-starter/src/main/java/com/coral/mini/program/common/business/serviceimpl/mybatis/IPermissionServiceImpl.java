package com.coral.mini.program.common.business.serviceimpl.mybatis;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.coral.mini.program.common.business.dao.mapper.PermissionMapper;
import com.coral.mini.program.common.business.entity.Permission;
import com.coral.mini.program.common.business.service.mybatis.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author coral
 */
@Service
public class IPermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public List<Permission> findByUserId(String userId) {

        return permissionMapper.findByUserId(userId);
    }
}
