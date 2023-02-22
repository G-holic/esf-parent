package cn.itcast.dao;

import cn.itcast.entity.HouseBroker;

import java.util.List;

public interface HouseBrokerDao extends BaseDao<HouseBroker> {

    /**
     * 根据房源id查询该房源的经纪人
     * @param houseId
     * @return
     */
    List<HouseBroker> getHouseBrokersByHouseId(Long houseId);
}
