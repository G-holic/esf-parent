package cn.itcast.controller;

import cn.itcast.entity.Admin;
import cn.itcast.entity.Permission;
import cn.itcast.service.AdminService;
import cn.itcast.service.PermissionService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
public class IndexController {

    @Reference
    private AdminService adminService;

    @Reference
    private PermissionService permissionService;

    // 去首页
//    @RequestMapping("/")
//    public String toIndex() {
//        return "frame/index";
//    }

    @RequestMapping("/")
    public String toIndex(Map map) {
        // 设置默认的用户的id
//        Long userId = 1L; // 超级管理员

        // 调用AdminService中查询的方法
//        Admin admin = adminService.getById(userId);

        // 通过SecurityContextHolder获取User对象
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // 调用AdminService中根据用户名获取Admin对象的方法
        Admin admin = adminService.getAdminByUsername(user.getUsername());

        // 根据用户的id调用permissionService中获取用户权限菜单的方法
        List<Permission> permissionList = permissionService.getMenuPermissionByAdminId(admin.getId());
        // 将用户的权限菜单放到request域中
        map.put("admin",admin);
        map.put("permissionList",permissionList);

    return "frame/index";
    }

    // 去主页面
    @RequestMapping("/main")
    public String toMain() {
        return "frame/main";
    }


    // 去登陆页面
    @RequestMapping("/login")
    public String toLogin(){
        return "frame/login";
    }

    // 去没有权限的页面
    @RequestMapping("/auth")
    public String toAuth(){
        return "frame/auth";
    }


}
