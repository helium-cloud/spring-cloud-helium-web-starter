package com.coral.mini.program.common.business.serviceimpl;

import com.coral.mini.program.common.common.exception.MpException;
import com.coral.mini.program.common.common.utils.JsonUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 初始化服务,用户可根据需求扩展初始数据,方式如下:
 */

@Service
@ConditionalOnProperty(prefix = "program.data.init", name = "base", havingValue = "true")
public class InitBaseSourceService extends AbstractInitSourceService<InitSourceModel> {

    private static final String INIT_SOURCE_PATH = "init_base.json";

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private DepartmentDao departmentDao;

    @Autowired
    private RoleDepartmentDao roleDepartmentDao;

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    private static final Logger log = LoggerFactory.getLogger(InitBaseSourceService.class);

    @Override
    protected String getResourcePath() {
        return INIT_SOURCE_PATH;
    }

    @Override
    protected Class<InitSourceModel> getModelClass() {
        return InitSourceModel.class;
    }

    @Override
    protected void saveModel(InitSourceModel sourceModel) {
        // 1.初始化角色
        initRole(sourceModel.getRoles());
        // 2.初始化部门
        initDepartment(sourceModel.getDepartments());
        // 3.初始化权限
        initPermissions(sourceModel.getPermissions());
        // 4.建立角色与部门的关系
        initRoleDepartment(sourceModel.getRoles(), sourceModel.getDepartments());
        // 5.建立角色与权限的关系
        initRolePermissions(sourceModel.getRoles(), sourceModel.getPermissions());
        // 6.初始化用户
        initUser(sourceModel.getUsers(), sourceModel.getDepartments());
        // 7.建立用户与角色的关系
        initUserRole(sourceModel.getUsers(), sourceModel.getRoles());
    }


    @Override
    protected boolean isInit() {
        boolean result = false;
        if (userDao.count() > 0) {
            result = true;
        }
        if (roleDao.count() > 0) {
            result = true;
        }
        if (departmentDao.count() > 0) {
            result = true;
        }
        if (userRoleDao.count() > 0) {
            result = true;
        }
        if (rolePermissionDao.count() > 0) {
            result = true;
        }
        if (roleDepartmentDao.count() > 0) {
            result = true;
        }
        if (permissionDao.count() > 0) {
            result = true;
        }
        return result;
    }


    private void initRolePermissions(List<RoleInitVo> roleInitVos, List<PermissionInitVo> permissionInitVos) {

        List<PermissionInitVo> permissions = new ArrayList<>(permissionInitVos);
        for (PermissionInitVo permission : permissionInitVos) {
            if (!CollectionUtils.isEmpty(permission.getChildren())) {
                permissions.addAll(permission.getChildren());
            }
        }
        roleInitVos.forEach(vo -> {
            if (!CollectionUtils.isEmpty(vo.getPermissions())) {
                vo.getPermissions().forEach(permissionInitVo -> {
                    String permissionId = permissions.stream().filter(p -> Objects.equals(permissionInitVo.getTitle(), p.getTitle())).map(PermissionInitVo::getId).findFirst().orElse("");
                    RolePermission rolePermission = createRolePermission(vo.getId(), permissionId);
                    rolePermissionDao.save(rolePermission);
                });
            }
        });
    }

    private void initPermissions(List<PermissionInitVo> vos) {

        for (PermissionInitVo vo : vos) {
            Permission permission = createPermission(vo);
            permission.setParentId("");
            Permission parent = permissionDao.save(permission);
            vo.setId(parent.getId());
            if (!CollectionUtils.isEmpty(vo.getChildren())) {
                for (PermissionInitVo childrenVo : vo.getChildren()) {
                    Permission children = createPermission(childrenVo);
                    children.setParentId(parent.getId());
                    childrenVo.setId(permissionDao.save(children).getId());
                }
            }
        }
    }


    private void initUserRole(List<UserInitVo> userInitVos, List<RoleInitVo> roleInitVos) {

        userInitVos.forEach(userInitVo -> {
            String roleId = roleInitVos.stream().filter(roleInitVo -> Objects.equals(roleInitVo.getName(), userInitVo.getRole())).map(RoleInitVo::getId).findFirst().orElse("");
            UserRole userRole = createUserRole(userInitVo);
            userRole.setRoleId(roleId);
            userRoleDao.save(userRole);
        });

    }

    private void initUser(List<UserInitVo> users, List<DepartmentInitVo> departments) {

        users.forEach(vo -> {
            String departmentId = departments.stream().filter(department -> Objects.equals(department.getTitle(), vo.getDepartment())).map(DepartmentInitVo::getId).findFirst().orElse("");
            User user = createUser(vo);
            user.setDepartmentId(departmentId);
            vo.setId(userDao.save(user).getId());
        });


    }

    private void initRoleDepartment(List<RoleInitVo> roleInitVos, List<DepartmentInitVo> departmentInitVos) {

        List<DepartmentInitVo> departments = new ArrayList<>(departmentInitVos);
        for (DepartmentInitVo department : departmentInitVos) {
            if (!CollectionUtils.isEmpty(department.getChildren())) {
                departments.addAll(department.getChildren());
            }
        }
        for (RoleInitVo vo : roleInitVos) {
            List<DepartmentInitVo> departmentVos = vo.getDepartments();
            for (DepartmentInitVo departmentInitVo : departmentVos) {
                String departmentId = departments.stream().filter(d -> Objects.equals(departmentInitVo.getTitle(), d.getTitle())).map(DepartmentInitVo::getId).findFirst().orElse("");
                RoleDepartment roleDepartment = createRoleDepartment(vo.getId(), departmentId);
                roleDepartmentDao.save(roleDepartment);
            }

        }
    }

    private void initDepartment(List<DepartmentInitVo> vos) {

        vos.forEach(vo -> {
            Department department = createDepartment(vo);
            Department parent = departmentDao.save(department);
            vo.setId(parent.getId());
            if (!CollectionUtils.isEmpty(vo.getChildren())) {
                vo.getChildren().forEach(childrenVo -> {
                    Department children = createDepartment(childrenVo);
                    children.setParentId(parent.getId());
                    childrenVo.setId(departmentDao.save(children).getId());
                });
            }

        });
    }

    private void initRole(List<RoleInitVo> vos) {

        vos.forEach(vo -> {
            Role role = createRole(vo);
            vo.setId(roleDao.save(role).getId());
        });
    }

    @Override
    protected void checkSourceModel(InitSourceModel model) {
        // 校验数据的正确性
        List<UserInitVo> users = model.getUsers();
        List<RoleInitVo> roles = model.getRoles();
        List<DepartmentInitVo> departments = new ArrayList<>(model.getDepartments());
        List<PermissionInitVo> permissions = new ArrayList<>(model.getPermissions());
        // 初始化部门列表,因为存在子部门,因此需要整合
        for (DepartmentInitVo department : model.getDepartments()) {
            if (!CollectionUtils.isEmpty(department.getChildren())) {
                departments.addAll(department.getChildren());
            }
        }
        // 校验部门的重复性
        for (DepartmentInitVo department : departments) {
            long departmentCount = departments.stream().filter(d -> Objects.equals(department.getTitle(), d.getTitle())).count();
            if (departmentCount > 1) {
                log.error("配置信息部门[" + department.getTitle() + "]存在多个!");
            }
        }
        // 初始化部门列表,因为存在子权限,因此需要整合
        for (PermissionInitVo permission : model.getPermissions()) {
            if (!CollectionUtils.isEmpty(permission.getChildren())) {
                permissions.addAll(permission.getChildren());
            }
        }
        // 校验权限的重复性
        for (PermissionInitVo permission : permissions) {
            long permissionCount = permissions.stream().filter(p -> Objects.equals(permission.getTitle(), p.getTitle())).count();
            if (permissionCount > 1) {
                log.error("配置信息权限[" + permission.getTitle() + "]存在多个!");
            }
        }

        for (UserInitVo user : users) {
            // 重复性校验,用户名不可重复
            long userCount = users.stream().filter(u -> Objects.equals(u.getUsername(), user.getUsername())).count();
            if (userCount > 1) {
                log.error("配置信息用户[" + user.getUsername() + "]存在多个!");
            }
            // 用户部门存在性校验
            long departmentCount = departments.stream().filter(d -> Objects.equals(user.getDepartment(), d.getTitle())).count();
            if (departmentCount < 1) {
                log.error("配置信息用户[" + user.getUsername() + "]的用户部门[" + user.getDepartment() + "]不存在!");
            }
            // 用户角色存在性校验
            long roleCount = roles.stream().filter(r -> Objects.equals(user.getRole(), r.getName())).count();
            if (roleCount < 1) {
                log.error("配置信息用户[" + user.getUsername() + "]的角色[" + user.getRole() + "]不存在!");
            }
        }

        for (RoleInitVo role : roles) {
            // 重复性校验,角色不可重复
            long userCount = roles.stream().filter(r -> Objects.equals(r.getName(), role.getName())).count();
            if (userCount > 1) {
                log.error("配置信息角色[" + role.getName() + "]存在多个!");
            }
            // 角色部门存在性校验
            for (DepartmentInitVo department : role.getDepartments()) {
                long departmentCount = departments.stream().filter(d -> Objects.equals(department.getTitle(), d.getTitle())).count();
                if (departmentCount < 1) {
                    log.error("配置信息角色[" + role.getName() + "]的部门[" + department.getTitle() + "]不存在!");
                }
            }
            // 角色权限存在性校验
            for (PermissionInitVo permission : role.getPermissions()) {
                long permissionCount = permissions.stream().filter(p -> Objects.equals(permission.getTitle(), p.getTitle())).count();
                if (permissionCount < 1) {
                    log.error("配置信息角色[" + role.getName() + "]的权限[" + permission.getTitle() + "]不存在!");
                }
            }
        }
    }

    private Department createDepartment(DepartmentInitVo vo) {
        Department department = new Department();
        if (StringUtils.isEmpty(vo.getParent())) {
            department.setParent(true);
            department.setParentId("0");
        } else {
            department.setParent(false);
        }
        department.setTitle(vo.getTitle());
        department.setStatus(0);
        department.setSortOrder(vo.getSortOrder());
        department.setDelFlag(0);
        department.setCreateTime(new Date());
        return department;
    }

    private Permission createPermission(PermissionInitVo vo) {
        Permission permission = new Permission();
        permission.setCreateTime(new Date());
        permission.setDelFlag(0);
        permission.setDescription("");
        permission.setName(vo.getName());
        permission.setType(0);
        permission.setSortOrder(vo.getSortOrder());
        permission.setComponent(vo.getComponent());
        permission.setPath(vo.getPath());
        permission.setTitle(vo.getTitle());
        permission.setIcon(vo.getIcon());
        permission.setLevel(vo.getLevel());
        permission.setButtonType("");
        permission.setStatus(0);
        permission.setUrl("");
        return permission;
    }

    private RoleDepartment createRoleDepartment(String roleId, String departmentId) {
        RoleDepartment roleDepartment = new RoleDepartment();
        roleDepartment.setRoleId(roleId);
        roleDepartment.setDepartmentId(departmentId);
        roleDepartment.setDelFlag(0);
        roleDepartment.setCreateTime(new Date());
        return roleDepartment;
    }

    private User createUser(UserInitVo vo) {
        User user = new User();
        user.setCreateTime(new Date());
        user.setSex(1);
        user.setStatus(0);
        user.setType(1);
        user.setUsername(vo.getUsername());
        user.setDelFlag(0);
        user.setPassword(new BCryptPasswordEncoder().encode(vo.getPassword()));
        user.setPassStrength("弱");
        return user;
    }

    private UserRole createUserRole(UserInitVo vo) {
        UserRole userRole = new UserRole();
        userRole.setUserId(vo.getId());
        userRole.setCreateTime(new Date());
        return userRole;
    }

    private Role createRole(RoleInitVo vo) {
        Role role = new Role();
        role.setName(vo.getName());
        role.setDefaultRole(true);
        role.setDescription(vo.getDescription());
        role.setCreateTime(new Date());
        role.setDelFlag(0);
        role.setDataType(0);
        return role;
    }

    private RolePermission createRolePermission(String roleId, String permissionId) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(roleId);
        rolePermission.setPermissionId(permissionId);
        rolePermission.setCreateTime(new Date());
        rolePermission.setDelFlag(0);
        return rolePermission;
    }

}

