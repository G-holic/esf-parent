package cn.itcast.dao;

import cn.itcast.entity.Role;

import java.util.List;


public interface RoleDao extends BaseDao<Role>{

    /**
     * 获取所有角色信息
     * @return
     */
    List<Role> findAll();

    /**
     * 添加角色信息
     * @param role
     * @return
     */
//    Integer insert(Role role);

    /**
     * 删除角色
     * @param roleId
     * @return
     */
//    Long delete(Long roleId);

    /**
     * 通过id查询角色信息
     * @param roleId
     * @return
     */
//    Role getById(Long roleId);

    /**
     * 更新角色信息
     * @param role
     * @return
     */
//    Integer update(Role role);

    /**
     * 分页及带条件查询的方法
     * @param filters
     * @return
     */
//    Page<Role> findPage(Map<String, Object> filters);



}
