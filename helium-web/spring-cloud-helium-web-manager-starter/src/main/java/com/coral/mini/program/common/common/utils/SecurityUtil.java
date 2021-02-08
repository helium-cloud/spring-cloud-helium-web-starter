package com.coral.mini.program.common.common.utils;

import com.coral.mini.program.common.common.constant.CommonConstant;
import com.coral.mini.program.common.business.entity.Role;
import com.coral.mini.program.common.business.entity.User;
import com.coral.mini.program.common.business.service.UserService;
import com.coral.mini.program.common.business.service.mybatis.IUserRoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author coral
 */
@Component

public class SecurityUtil {

    @Autowired
    private UserService userService;

    @Autowired
    private IUserRoleService iUserRoleService;

    /**
     * 获取当前登录用户
     * @return
     */
    public User getCurrUser(){

        UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userService.findByUsername(user.getUsername());
    }

    /**
     * 获取当前用户数据权限 null代表具有所有权限
     */
    public List<String> getDeparmentIds(){

        List<String> deparmentIds = new ArrayList<>();
        User u = getCurrUser();
        // 用户角色
        List<Role> userRoleList = iUserRoleService.findByUserId(u.getId());
        // 判断有无全部数据的角色
        Boolean flagAll = false;
        for(Role r : userRoleList){
            if(r.getDataType()==null||r.getDataType().equals(CommonConstant.DATA_TYPE_ALL)){
                flagAll = true;
                break;
            }
        }
        if(flagAll){
            return null;
        }
        // 查找自定义
        return iUserRoleService.findDepIdsByUserId(u.getId());
    }
}
