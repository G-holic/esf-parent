package cn.itcast.service;

import cn.itcast.entity.Role;

import java.util.List;
import java.util.Map;

public interface RoleService extends BaseService<Role>{
    List<Role> findAll();

//    Integer insert(Role role);
//    Long delete(Long roleId);
//    Role getById(Long roleId);
//    Integer update(Role role);
//    PageInfo<Role> findPage(Map<String, Object> filters);

    // 根据用户的id查询用户的角色
    Map<String, Object> findRolesByAdminId(Long adminId);

    void assignRole(Long adminId, Long[] roleIds);
}
