package com.coral.mini.program.common.business.serviceimpl;

import com.coral.mini.program.common.business.dao.UserRoleDao;
import com.coral.mini.program.common.business.entity.UserRole;
import com.coral.mini.program.common.business.service.UserRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户角色接口实现
 * @author coral
 */

@Service
@Transactional
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public UserRoleDao getRepository() {
        return userRoleDao;
    }

    @Override
    public List<UserRole> findByRoleId(String roleId) {
        return userRoleDao.findByRoleId(roleId);
    }

    @Override
    public void deleteByUserId(String userId) {
        userRoleDao.deleteByUserId(userId);
    }
}
