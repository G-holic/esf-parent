package cn.itcast.dao;

import cn.itcast.entity.Admin;

import java.util.List;

public interface AdminDao extends BaseDao<Admin> {

    List<Admin> findAll();

    Admin getAdminByUsername(String username);
}
