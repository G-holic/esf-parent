package cn.itcast.service.impl;

import cn.itcast.dao.BaseDao;
import cn.itcast.dao.PermissionDao;
import cn.itcast.dao.RolePermissionDao;
import cn.itcast.entity.Permission;

import cn.itcast.entity.RolePermission;
import cn.itcast.helper.PermissionHelper;
import cn.itcast.service.Impl.BaseServiceImpl;
import cn.itcast.service.PermissionService;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = PermissionService.class)
@Transactional
public class PermissionServiceImpl  extends BaseServiceImpl<Permission> implements PermissionService {

    @Autowired
    private PermissionDao permissionDao;

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    protected BaseDao<Permission> getEntityDao() {
        return permissionDao;
    }

    @Override
    public List<Map<String, Object>> findPermissionsByRoleId(Long roleId) {
        // 获取所有的权限
        List<Permission> permissionList = permissionDao.findAll();
        // 根据角色id查询已分配的权限的权限id
        List<Long> permissionIds = rolePermissionDao.findPermissionIdsByRoleId(roleId);

        // 创建返回的List
        List<Map<String, Object>> returnList = new ArrayList<>();

        // 遍历所有的权限
        for (Permission permission : permissionList) {
            // map数据的格式：{ id:221, pId:22, name:"随意勾选 2-2-1", checked:true},
            // 创建一个map
            Map<String, Object> map = new HashMap<>();
            map.put("id",permission.getId());
            map.put("pId",permission.getParentId());
            map.put("name",permission.getName());
            // 判断当前权限的id在不在permissionIds中
            if (permissionIds.contains(permission.getId())){
                // 证明该权限是已经分配的权限了
                map.put("check",true);
            }

            // 将map放到返回的List中
            returnList.add(map);
        }
        return returnList;
    }

    @Override
    public void assignPermission(Long roleId, Long[] permissionIds) {
        // 调用rolePermissionDao中根据角色id删除已分配权限的方法
        rolePermissionDao.deletePermissionIdsByuRoleId(roleId);
        // 遍历所有的权限id
        for (Long permissionId : permissionIds) {
            if (permissionId != null){
                // 调用rolePermissionDao中保存权限id和角色id的方法
                rolePermissionDao.addRoleIdAndPermission(roleId,permissionId);
            }

        }
    }

    @Override
    public List<Permission> getMenuPermissionByAdminId(Long userId) {
        List<Permission> permissionList = null;
        // 判断是否是系统管理员
        if (userId ==1 ){
            // 证明是系统管理员，获取所有的权限
            permissionList  = permissionDao.findAll();
        }else {
            // 根据用户的id查询权限菜单
            permissionList = permissionDao.getMenuPermissionsByAdminId(userId);

        }
        // 通过PermissionHelper工具类将List转换为树形结构
        List<Permission> treeList = PermissionHelper.bulid(permissionList);

        return treeList;
    }

    @Override
    public List<Permission> findAllMenu() {
        //全部权限列表
        List<Permission> permissionList = permissionDao.findAll();
        if(CollectionUtils.isEmpty(permissionList)) return null;

        //构建树形数据,总共三级
        //把权限数据构建成树形结构数据
        List<Permission> result = PermissionHelper.bulid(permissionList);
        return result;
    }

    @Override
    public List<String> getPermissionCodesByAdminId(Long id) {
        List<String> permissionCodes = null;
        if (id == 1){
            // 证明是系统管理员
            permissionCodes = permissionDao.getAllPermissionCodes();
        }else {
            // 根据用户id查询权限码
            permissionCodes = permissionDao.getPermissionCodesByAdminId(id);
        }

        return permissionCodes;
    }
}
