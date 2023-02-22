package cn.itcast.service;

import cn.itcast.entity.HouseUser;

import java.util.List;

public interface HouseUserService extends BaseService<HouseUser> {
    /**
     * 根据房源id查询该房源的房东
     * @param houseId
     * @return
     */
    List<HouseUser> getHouseUsersByHouseId(Long houseId);
}
