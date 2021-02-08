package com.coral.mini.program.common.business.controller;

import cn.hutool.core.util.StrUtil;
import com.coral.mini.program.common.business.service.mybatis.IUserRoleService;
import com.coral.mini.program.common.common.constant.CommonConstant;
import com.coral.mini.program.common.common.utils.PageUtil;
import com.coral.mini.program.common.common.utils.ResultUtil;
import com.coral.mini.program.common.common.utils.SecurityUtil;
import com.coral.mini.program.common.common.vo.PageVo;
import com.coral.mini.program.common.common.vo.Result;
import com.coral.mini.program.common.common.vo.SearchVo;
import com.coral.mini.program.common.utils.CheckNumber;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.hql.internal.ast.tree.AbstractNullnessCheckNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import springfox.documentation.service.ResponseMessage;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.io.File;
import java.io.FileOutputStream;


/**
 * @author coral
 */
@Slf4j
@RestController
@Api(description = "用户接口")
@RequestMapping("/user")
@CacheConfig(cacheNames = "user")
@Transactional
@ConditionalOnProperty(prefix = "mp.admin", name = "enable", havingValue = "true")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private IUserRoleService iUserRoleService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private DepartmentHeaderService departmentHeaderService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private SecurityUtil securityUtil;

    @PersistenceContext
    private EntityManager entityManager;

    @RequestMapping(value = "/regist", method = RequestMethod.POST)
    @ApiOperation(value = "注册用户")
    public Result<Object> regist(@ModelAttribute User u,
                                 @RequestParam String verify,
                                 @RequestParam String captchaId) {

        if (StrUtil.isBlank(verify) || StrUtil.isBlank(u.getUsername())
                || StrUtil.isBlank(u.getPassword())) {
            return new ResultUtil<Object>().setErrorMsg("缺少必需表单字段");
        }

        //验证码
        String code = redisTemplate.opsForValue().get(captchaId);
        if (StrUtil.isBlank(code)) {
            return new ResultUtil<Object>().setErrorMsg("验证码已过期，请重新获取");
        }

        if (!verify.toLowerCase().equals(code.toLowerCase())) {
            log.error("注册失败，验证码错误：code:" + verify + ",redisCode:" + code.toLowerCase());
            return new ResultUtil<Object>().setErrorMsg("验证码输入错误");
        }

        if (userService.findByUsername(u.getUsername()) != null) {
            return new ResultUtil<Object>().setErrorMsg("该用户名已被注册");
        }
        //删除缓存
        redisTemplate.delete(getUserKey(u.getUsername()));

        String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(encryptPass);
        u.setType(CommonConstant.USER_TYPE_NORMAL);
        User user = userService.save(u);
        if (user == null) {
            return new ResultUtil<Object>().setErrorMsg("注册失败");
        }
        // 默认角色
        List<Role> roleList = roleService.findByDefaultRole(true);
        if (roleList != null && roleList.size() > 0) {
            for (Role role : roleList) {
                UserRole ur = new UserRole();
                ur.setUserId(user.getId());
                ur.setRoleId(role.getId());
                iUserRoleService.insert(ur);
            }
        }

        return new ResultUtil<Object>().setData(user);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ApiOperation(value = "获取当前登录用户接口")
    public Result<User> getUserInfo() {

        User u = securityUtil.getCurrUser();
        // 清除持久上下文环境 避免后面语句导致持久化
        entityManager.clear();
        u.setPassword(null);
        return new ResultUtil<User>().setData(u);
    }

    @RequestMapping(value = "/unlock", method = RequestMethod.POST)
    @ApiOperation(value = "解锁验证密码")
    public Result<Object> unLock(@RequestParam String password) {

        User u = securityUtil.getCurrUser();
        if (!new BCryptPasswordEncoder().matches(password, u.getPassword())) {
            return new ResultUtil<Object>().setErrorMsg("密码不正确");
        }
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    @ApiOperation(value = "修改用户自己资料", notes = "用户名密码不会修改 需要username更新缓存")
    @CacheEvict(key = "#u.username")
    public Result<Object> editOwn(@ModelAttribute User u) {

        User old = securityUtil.getCurrUser();
        u.setUsername(old.getUsername());
        u.setPassword(old.getPassword());
        User user = userService.update(u);
        if (user == null) {
            return new ResultUtil<Object>().setErrorMsg("修改失败");
        }
        return new ResultUtil<Object>().setSuccessMsg("修改成功");
    }

    /**
     * @param u
     * @param roles
     * @return
     */
    @RequestMapping(value = "/admin/edit", method = RequestMethod.POST)
    @ApiOperation(value = "管理员修改资料", notes = "需要通过id获取原用户信息 需要username更新缓存")
    @CacheEvict(key = "#u.username")
    public Result<Object> edit(@ModelAttribute User u,
                               @RequestParam(required = false) String[] roles) {

        User old = userService.get(u.getId());
        //若修改了用户名
        if (!old.getUsername().equals(u.getUsername())) {
            //若修改用户名删除原用户名缓存
            redisTemplate.delete(getUserKey(u.getUsername()));
            //判断新用户名是否存在
            if (userService.findByUsername(u.getUsername()) != null) {
                return new ResultUtil<Object>().setErrorMsg("该用户名已被存在");
            }
            //删除缓存
            redisTemplate.delete(getUserKey(u.getUsername()));
        }

        // 若修改了手机和邮箱判断是否唯一
        if (!old.getMobile().equals(u.getMobile()) && userService.findByMobile(u.getMobile()) != null) {
            return new ResultUtil<Object>().setErrorMsg("该手机号已绑定其他账户");
        }
        if (!old.getEmail().equals(u.getEmail()) && userService.findByMobile(u.getEmail()) != null) {
            return new ResultUtil<Object>().setErrorMsg("该邮箱已绑定其他账户");
        }

        u.setPassword(old.getPassword());
        User user = userService.update(u);
        if (user == null) {
            return new ResultUtil<Object>().setErrorMsg("修改失败");
        }
        //删除该用户角色
        userRoleService.deleteByUserId(u.getId());
        if (roles != null && roles.length > 0) {
            //新角色
            for (String roleId : roles) {
                UserRole ur = new UserRole();
                ur.setRoleId(roleId);
                ur.setUserId(u.getId());
                userRoleService.save(ur);
            }
        }
        //手动删除缓存
        redisTemplate.delete(getUserRoles(u.getId()));
        redisTemplate.delete(getUserRolesIds(u.getId()));
        return new ResultUtil<Object>().setSuccessMsg("修改成功");
    }

    /**
     * 线上demo不允许测试账号改密码
     *
     * @param password
     * @param newPass
     * @return
     */
    @RequestMapping(value = "/modifyPass", method = RequestMethod.POST)
    @ApiOperation(value = "修改密码")
    public Result<Object> modifyPass(@ApiParam("旧密码") @RequestParam String password,
                                     @ApiParam("新密码") @RequestParam String newPass) {

        User user = securityUtil.getCurrUser();

        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            return new ResultUtil<Object>().setErrorMsg("旧密码不正确");
        }

        String newEncryptPass = new BCryptPasswordEncoder().encode(newPass);
        user.setPassword(newEncryptPass);
        userService.update(user);

        //手动更新缓存
        redisTemplate.delete(getUserKey(user.getUsername()));

        return new ResultUtil<Object>().setSuccessMsg("修改密码成功");
    }

    @RequestMapping(value = "/getByCondition", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取用户列表")
    public Result<Page<User>> getByCondition(@ModelAttribute User user,
                                             @ModelAttribute SearchVo searchVo,
                                             @ModelAttribute PageVo pageVo) {

        Page<User> page = userService.findByCondition(user, searchVo, PageUtil.initPage(pageVo));
        for (User u : page.getContent()) {
            // 关联部门
            if (StrUtil.isNotBlank(u.getDepartmentId())) {
                Department department = departmentService.get(u.getDepartmentId());
                u.setDepartmentTitle(department.getTitle());
            }
            // 关联角色
            List<Role> list = iUserRoleService.findByUserId(u.getId());
            u.setRoles(list);
            // 清除持久上下文环境 避免后面语句导致持久化
            entityManager.clear();
            u.setPassword(null);
        }
        return new ResultUtil<Page<User>>().setData(page);
    }


    @RequestMapping(value = "/getByDepartmentId/{departmentId}", method = RequestMethod.GET)
    @ApiOperation(value = "多条件分页获取用户列表")
    public Result<List<User>> getByCondition(@PathVariable String departmentId) {

        List<User> list = userService.findByDepartmentId(departmentId);
        entityManager.clear();
        list.forEach(u -> {
            u.setPassword(null);
        });
        return new ResultUtil<List<User>>().setData(list);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(value = "获取全部用户数据")
    public Result<List<User>> getByCondition() {

        List<User> list = userService.getAll();
        for (User u : list) {
            // 关联部门
            if (StrUtil.isNotBlank(u.getDepartmentId())) {
                Department department = departmentService.get(u.getDepartmentId());
                u.setDepartmentTitle(department.getTitle());
            }
            // 清除持久上下文环境 避免后面语句导致持久化
            entityManager.clear();
            u.setPassword(null);
        }
        return new ResultUtil<List<User>>().setData(list);
    }

    @RequestMapping(value = "/admin/add", method = RequestMethod.POST)
    @ApiOperation(value = "添加用户")
    public Result<Object> regist(@ModelAttribute User u,
                                 @RequestParam(required = false) String[] roles) {

        if (StrUtil.isBlank(u.getUsername()) || StrUtil.isBlank(u.getPassword())) {
            return new ResultUtil<Object>().setErrorMsg("缺少必需表单字段");
        }

        if (userService.findByUsername(u.getUsername()) != null) {
            return new ResultUtil<Object>().setErrorMsg("该用户名已被注册");
        }
        //删除缓存
        redisTemplate.delete(getUserKey(u.getUsername()));

        String encryptPass = new BCryptPasswordEncoder().encode(u.getPassword());
        u.setPassword(encryptPass);
        User user = userService.save(u);
        if (user == null) {
            return new ResultUtil<Object>().setErrorMsg("添加失败");
        }
        if (roles != null && roles.length > 0) {
            //添加角色
            for (String roleId : roles) {
                UserRole ur = new UserRole();
                ur.setUserId(u.getId());
                ur.setRoleId(roleId);
                userRoleService.save(ur);
            }
        }

        return new ResultUtil<Object>().setData(user);
    }

    @RequestMapping(value = "/admin/disable/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "后台禁用用户")
    public Result<Object> disable(@ApiParam("用户唯一id标识") @PathVariable String userId) {

        User user = userService.get(userId);
        if (user == null) {
            return new ResultUtil<Object>().setErrorMsg("通过userId获取用户失败");
        }
        user.setStatus(CommonConstant.USER_STATUS_LOCK);
        userService.update(user);
        //手动更新缓存
        redisTemplate.delete(getUserKey(user.getUsername()));
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/admin/enable/{userId}", method = RequestMethod.POST)
    @ApiOperation(value = "后台启用用户")
    public Result<Object> enable(@ApiParam("用户唯一id标识") @PathVariable String userId) {

        User user = userService.get(userId);
        if (user == null) {
            return new ResultUtil<Object>().setErrorMsg("通过userId获取用户失败");
        }
        user.setStatus(CommonConstant.USER_STATUS_NORMAL);
        userService.update(user);
        //手动更新缓存
        redisTemplate.delete(getUserKey(user.getUsername()));
        return new ResultUtil<Object>().setData(null);
    }

    @RequestMapping(value = "/delByIds/{ids}", method = RequestMethod.DELETE)
    @ApiOperation(value = "批量通过ids删除")
    public Result<Object> delAllByIds(@PathVariable String[] ids) {

        for (String id : ids) {
            User u = userService.get(id);
            //删除缓存
            redisTemplate.delete(getUserKey(u.getUsername()));
            redisTemplate.delete(getUserRoles(u.getId()));
            redisTemplate.delete(getUserRolesIds(u.getId()));
            Set<String> keys = redisTemplate.keys("department::*");
            redisTemplate.delete(keys);
            userService.delete(id);
            //删除关联角色
            userRoleService.deleteByUserId(id);
            //删除关联部门负责人
            departmentHeaderService.deleteByUserId(id);
        }

        return new ResultUtil<Object>().setSuccessMsg("批量通过id删除数据成功");
    }

    //Author Yichen
    //About Login Authentication Code
    //通过@RequestMapping注入的方式对路径进行映射，以及用@PathVariable进行传值。
    @SneakyThrows
    @RequestMapping("/CheckNumber")
    @ResponseBody
    public String getCheckNumber(HttpServletResponse response,HttpSession session){
        //create the CheckNumber object
        CheckNumber ck = new CheckNumber();
        //get the picture object
        BufferedImage image = ck.getImage();
        //get the picture text
        String text = ck.getText();
        //save the system generated file into the session
        session.setAttribute("ck", text);
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        //return the picture to the frontend
        ImageIO.write(image, "JPEG", out);
        byte[] a =out.toByteArray();
        String str = Base64.getEncoder().encodeToString(a);
        //将image写入本地
//        String fileName = "abc.jpeg";
//        File file = new File("E:\\" + fileName);
//        FileOutputStream fops = new FileOutputStream(file);
//                   fops.write(a);
//         str = "<img src=\"data:image/jpg;base64,"+str+"\">";
//        response.getOutputStream().write(str.getBytes());
        return str;
    }

    //Validate the user input
    @PostMapping("/CheckNumber/{userCaptcha}")//PostMapping的数据量会大，
    @ResponseBody
    public Result<Object> checkNumber(@PathVariable("userCaptcha") String userCaptcha, HttpServletRequest request, HttpSession session) throws IOException{
        //get the user input and then to compare with the generated one
        //System.out.println("Start validate code");
        Result<Object> msg = new Result<Object>();
        String sessionCheckNumber = (String) session.getAttribute("ck");
        //print out the session check number
        System.out.println(userCaptcha + ":" + sessionCheckNumber);
        //estimate whether the sessionCheckNumber equals to userCaptcha
        if (sessionCheckNumber.equalsIgnoreCase(userCaptcha)){
            msg.setCode(200);
            return msg;
        }
        msg.setCode(500);
        msg.setMessage("验证码输入有误，请重新输入!");
        return msg;

    }

    private String getUserKey(String key){
        return "user::" + key;
    }
    private String getUserRoles(String key){
        return "userRoles" + key;
    }
    private String getUserRolesIds(String key){
        return "userRolesIds::" + key;
    }
}
