package cn.itcast.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionDao extends BaseDao<RolePermissionDao> {
    List<Long> findPermissionIdsByRoleId(Long roleId);

    void deletePermissionIdsByuRoleId(Long roleId);

    void addRoleIdAndPermission(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
}
