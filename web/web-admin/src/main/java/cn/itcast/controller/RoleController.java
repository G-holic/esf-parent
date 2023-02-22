package cn.itcast.controller;


import cn.itcast.entity.Role;
import cn.itcast.service.PermissionService;
import cn.itcast.service.RoleService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{

    private final static String PAGE_INDEX = "role/index";
    private final static String SUCCESS_PAGE = "common/successPage";

//    @Autowired
    @Reference
    private RoleService roleService;

    @Reference
    private PermissionService permissionService;

//    @RequestMapping
//    public String index(Map<String,Object> map){
//        // 调用RoleService中获取所有角色信息的方法
//        List<Role> roleList = roleService.findAll();
//        // 将所有角色放到request域中
//        map.put("list",roleList);
//        // 去渲染数据的页面
//        return PAGE_INDEX;
//    }

    // 分页及带条件查询的方法
    @PreAuthorize("hasAnyAuthority('role.show')")
    @RequestMapping
    public String index(Map map, HttpServletRequest request){
        // 获取请求参数
        Map<String, Object> filters = getFilters(request);
        // 将filters放到request域中
        map.put("filters",filters);
        // 调用RoleService中分页及带条件查询的方法
        PageInfo<Role> pageInfo = roleService.findPage(filters);
        // 将pageInfo对象放入request域中
        map.put("page",pageInfo);
        return PAGE_INDEX;
    }


    // 去添加角色的页面
    @PreAuthorize("hasAnyAuthority('role.create')")
    @RequestMapping("/create")
    public String toAddPage(){
        return "role/create";
    }

    // 添加角色
    @PreAuthorize("hasAnyAuthority('role.create')")
    @RequestMapping("/save")
    public String toSavePage(Role role){
        // 调用RoleService中添加的方法
        roleService.insert(role);
        // 重定向到查询所有角色的方法

//        return "redirect:/role"; // 不友好，修改
        // 去common下的successPage页面
        return SUCCESS_PAGE;
    }

    // 删除角色
    @PreAuthorize("hasAnyAuthority('role.delete')")
    @RequestMapping("delete/{roleId}")
    public String deleteRole(@PathVariable("roleId") Long roleId){
        // 调用RoleService中删除的方法
        roleService.delete(roleId);
        // 重定向到查询所有角色的方法
        return "redirect:/role";
    }

    // 去修改页面的方法
    @PreAuthorize("hasAnyAuthority('role.edit')")
    @RequestMapping("edit/{roleId}")
    public String toEditPage(@PathVariable("roleId") Long roleId,Map<String,Object> map){
        // 调用RoleService中根据id查询的方法
        Role role = roleService.getById(roleId);
        // 将数据共享到域中
        map.put("role",role);
        // 去修改的页面
        return "role/edit";
    }

    // 修改角色
    @PreAuthorize("hasAnyAuthority('role.edit')")
    @RequestMapping("/update")
    public String updateRole(Role role){
        // 调用RoleService中的修改方法
        roleService.update(role);
        // 去common下的successPage页面
        return SUCCESS_PAGE;
    }

    // 去分配权限的页面
    @PreAuthorize("hasAnyAuthority('role.assign')")
    @RequestMapping("/assignShow/{roleId}")
    public String toAssignShowPage(@PathVariable Long roleId,Map map){
        // 将角色id放到request域中
        map.put("roleId",roleId);
        // 调用permissionService中根据角色id获取权限的方法
        List<Map <String,Object>> zNodes = permissionService.findPermissionsByRoleId(roleId);
        // 将zNodes放到request域中
        map.put("zNodes",zNodes);
        return "role/assignShow";
    }

    // 给角色分配权限
    @PreAuthorize("hasAnyAuthority('role.assign')")
    @RequestMapping("assignPermission")
    public String assignPermission(@RequestParam("roleId") Long roleId, @RequestParam("permissionIds")Long[] permissionIds) {
        // 调用permissionService中分配权限的方法
        permissionService.assignPermission(roleId, permissionIds);
        // 去成功页面
        return "common/successPage";
    }

}
