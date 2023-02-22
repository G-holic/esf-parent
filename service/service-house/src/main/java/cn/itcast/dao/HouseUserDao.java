package cn.itcast.dao;

import cn.itcast.entity.HouseUser;

import java.util.List;

public interface HouseUserDao extends BaseDao<HouseUser> {

    /**
     * 根据房源id查询该房源的房东
     * @param houseId
     * @return
     */
    List<HouseUser> getHouseUsersByHouseId(Long houseId);
}
