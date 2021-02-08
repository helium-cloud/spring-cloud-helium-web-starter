package com.coral.mini.program.common.business.serviceimpl;

import com.coral.mini.program.common.business.dao.RolePermissionDao;
import com.coral.mini.program.common.business.entity.RolePermission;
import com.coral.mini.program.common.business.service.RolePermissionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 角色权限接口实现
 * @author coral
 */

@Service
@Transactional
public class RolePermissionServiceImpl implements RolePermissionService {

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public RolePermissionDao getRepository() {
        return rolePermissionDao;
    }

    @Override
    public List<RolePermission> findByPermissionId(String permissionId) {

        return rolePermissionDao.findByPermissionId(permissionId);
    }

    @Override
    public List<RolePermission> findByRoleId(String roleId) {

        return rolePermissionDao.findByRoleId(roleId);
    }

    @Override
    public void deleteByRoleId(String roleId) {

        rolePermissionDao.deleteByRoleId(roleId);
    }

    @Override
    public void deleteByPermissionId(String permissionId) {

        rolePermissionDao.deleteByPermissionId(permissionId);
    }
}