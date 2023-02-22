package cn.itcast.service.impl;

import cn.itcast.dao.AdminRoleDao;
import cn.itcast.dao.BaseDao;
import cn.itcast.dao.RoleDao;

import cn.itcast.entity.Role;
import cn.itcast.service.Impl.BaseServiceImpl;
import cn.itcast.service.RoleService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
//import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService {

    @Autowired
//    @Resource
    private RoleDao roleDao;

    @Autowired
    private AdminRoleDao adminRoleDao;

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Map<String, Object> findRolesByAdminId(Long adminId) {
        // 获取所有的角色
        List<Role> roleList = roleDao.findAll();
        // 获取用户已拥有的角色的id
        List<Long> roleIds = adminRoleDao.findRoleIdsByAdminId(adminId);
        // 创建两个List,一个存放未选中的角色，一个存放已选中的角色
        List<Role> noAssginRoleList = new ArrayList<>();
        List<Role> assginRoleList = new ArrayList<>();
        // 遍历所有的角色
        for (Role role : roleList) {
            // 判断当前角色的id在不在集合roleIds中
            if (roleIds.contains(role.getId())) {
                // 将当前角色放到已选中的List1中
                assginRoleList.add(role);
            } else {
                // 证明当前角色是未选中的角色，放到未选中的List中
                noAssginRoleList.add(role);
            }
        }
        // 创建返回的Map
        Map<String, Object> roleMap = new HashMap<>();
        roleMap.put("noAssginRoleList", noAssginRoleList);
        roleMap.put("assginRoleList", assginRoleList);

        return roleMap;
    }

    @Override
    public void assignRole(Long adminId, Long[] roleIds) {
        // 先根据用户的id将已分配的角色删除
        adminRoleDao.deleteRoleIdsByAdminId(adminId);
        // 遍历所有的角色id
        for (Long roleId : roleIds) {
            if (roleId != null) {
                // 角色id和用户id插入到数据库中
                adminRoleDao.addRoleIdAndAdminId(roleId, adminId);
            }

        }
    }

    @Override
    protected BaseDao<Role> getEntityDao() {
        return this.roleDao;
    }
   /* @Override
    public Integer insert(Role role) {
        return roleDao.insert(role);
    }
    @Override
    public Long delete(Long roleId) {
        return roleDao.delete(roleId);
    }
    @Override
    public Role getById(Long roleId) {
        return roleDao.getById(roleId);
    }
    @Override
    public Integer update(Role role) {
        return roleDao.update(role);
    }

    @Override
    public PageInfo<Role> findPage(Map<String, Object> filters) {
        // 获取当前页和每页显示的条数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);
        // 通过PageHelper插件开启分页
        PageHelper.startPage(pageNum,pageSize);
        // 调用RoleDao中分页及带条件查询的方法
        Page<Role> page = roleDao.findPage(filters);

        return new PageInfo<>(page,5);
    }*/

}
