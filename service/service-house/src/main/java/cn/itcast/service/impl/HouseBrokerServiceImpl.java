package cn.itcast.service.impl;


import cn.itcast.dao.BaseDao;
import cn.itcast.dao.HouseBrokerDao;
import cn.itcast.entity.HouseBroker;
import cn.itcast.service.HouseBrokerService;
import cn.itcast.service.Impl.BaseServiceImpl;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = HouseBrokerService.class)
@Transactional
public class HouseBrokerServiceImpl extends BaseServiceImpl<HouseBroker> implements HouseBrokerService {

    @Autowired
    private HouseBrokerDao houseBrokerDao;

    @Override
    public List<HouseBroker> getHouseBrokersByHouseId(Long houseId) {

        return houseBrokerDao.getHouseBrokersByHouseId(houseId);
    }

    @Override
    protected BaseDao<HouseBroker> getEntityDao() {
        return houseBrokerDao;
    }
}
