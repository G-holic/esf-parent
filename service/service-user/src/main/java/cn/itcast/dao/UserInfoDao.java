package cn.itcast.dao;

import cn.itcast.entity.UserInfo;

public interface UserInfoDao  extends BaseDao<UserInfo>{
    UserInfo getUserInfoByPhone(String phone);
}
