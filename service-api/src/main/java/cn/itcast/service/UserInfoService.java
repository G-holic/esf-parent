package cn.itcast.service;

import cn.itcast.entity.UserInfo;

public interface UserInfoService extends BaseService<UserInfo> {
    UserInfo getUserInfoByPhone(String phone);
}
