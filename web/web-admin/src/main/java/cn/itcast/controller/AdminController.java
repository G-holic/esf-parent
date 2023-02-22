package cn.itcast.controller;

import cn.itcast.entity.Admin;
import cn.itcast.service.AdminService;

import cn.itcast.service.RoleService;
import cn.itcast.util.QiniuUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
//

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    private final static String SUCCESS_PAGE ="common/successPage";

//    @Autowired
    @Reference // 消费者要改成dubbo的
    private AdminService adminService;

    @Reference
    private RoleService roleService;

    // 注入密码加密器
    @Autowired
    private PasswordEncoder passwordEncoder;


    // 分页及带条件的查询
    @RequestMapping
    public String findPage(Map map, HttpServletRequest request){
        // 获取请求参数
        Map<String, Object> filters = getFilters(request);
        // 将filters放到request域中
        map.put("filters",filters);
        // 调用AdminService中分页的方法
        PageInfo<Admin> pageInfo = adminService.findPage(filters);
        // 将pageInfo对象放到request域中
        map.put("page",pageInfo);
        return "admin/index";
    }

    // 去添加用户的页面
    @RequestMapping("/create")
    public String toCreatePage(){
        return "admin/create";
    }

    // 添加用户
    @RequestMapping("/save")
    public String toSave(Admin admin){
        // 对Admin对象中的密码进行加密
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        //设置默认头像
        admin.setHeadUrl("http://rqdbqp53c.hn-bkt.clouddn.com/026431d1-2409-4134-9a01-279d951424b1");
        // 调用AdminService的添加方法
        adminService.insert(admin);

        return SUCCESS_PAGE;
    }

    // 删除
    @RequestMapping("/delete/{adminId}")
    public String toDelete(@PathVariable("adminId") Long adminId){
        // 调用AdminService的删除方法
        adminService.delete(adminId);
        // 重定向
        return "redirect:/admin";
    }

    // 去更新的页面
    @RequestMapping("/edit/{adminId}")
    public String toEditPage(@PathVariable("adminId") Long adminId,Map map){
        // 调用AdminService中的根据id查询一 个对象的方法
        Admin admin = adminService.getById(adminId);
        // 将数据共享到请求域中
        map.put("admin",admin);
        return "admin/edit";
    }
    // 更新用户
    @RequestMapping("/update")
    public String toUpdate(Admin admin){
        // 调用AdminService的更新方法
        adminService.update(admin);

        // 去成功页面
        return SUCCESS_PAGE;
    }

    // 去上传头像的页面
    @RequestMapping("/uploadShow/{id}")
    public String toUploadPage(@PathVariable Long id,Map map){
        // 将用户的id放到request域中
        map.put("id",id);
        return "admin/upload";
    }

    // 上传头像
    @RequestMapping("/upload/{id}")
    public String upload(@PathVariable Long id, MultipartFile file) throws Exception{
        // 调用AdminService中根据id查询用户的方法
        Admin admin = adminService.getById(id);
        // 获取字节数组
        byte[] bytes = file.getBytes(); 
        // 通过UUID随机生成一个字符串作为上传到七牛云的图片的名字
        String fileName = UUID.randomUUID().toString();
        // 通过QiniuUtil工具类上传图片到七牛云的图片的名字
        QiniuUtil.upload2Qiniu(bytes,fileName);
        // 给用户设置头像地址
        admin.setHeadUrl("http://rqdbqp53c.hn-bkt.clouddn.com/"+fileName);
        // 调用AdminService中更新的方法
        adminService.update(admin);

        // 去成功页面
        return "common/successPage";
    }

    // 去分配角色的页面
    @RequestMapping("/assignShow/{adminId}")
    public String toAssignShowPage(@PathVariable Long adminId, ModelMap modelMap){
        // 将用户的id放到request域中
        modelMap.addAttribute("adminId",adminId);
        // 到RoleService中根据用户id查询用户角色的方法
        Map<String, Object> rolesByAdminId = roleService.findRolesByAdminId(adminId);
        // 将Map放到request域中
        modelMap.addAllAttributes(rolesByAdminId);
        return "admin/assignShow";
    }

    // 分配角色
    @RequestMapping("/assignRole")
    public String assignRole(Long adminId,Long[] roleIds){
        // 调用RoleService中分配角色的方法
        roleService.assignRole(adminId,roleIds);

        // 去成功页面
        return "common/successPage";

    }
}
