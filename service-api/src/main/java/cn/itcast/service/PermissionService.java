package cn.itcast.service;

import cn.itcast.entity.Permission;

import java.util.List;
import java.util.Map;

public interface PermissionService extends BaseService<Permission> {

    List<Map<String, Object>> findPermissionsByRoleId(Long roleId);

    void assignPermission(Long roleId, Long[] permissionIds);

    List<Permission> getMenuPermissionByAdminId(Long userId);

    /**
     * 菜单全部数据
     * @return
     */
    List<Permission> findAllMenu();

    List<String> getPermissionCodesByAdminId(Long id);
}
