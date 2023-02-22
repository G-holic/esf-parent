package cn.itcast.dao;

import cn.itcast.entity.Community;

import java.util.List;


public interface CommunityDao extends BaseDao<Community> {

    List<Community> findAll();
}
