package cn.itcast.service.impl;


import cn.itcast.dao.BaseDao;
import cn.itcast.dao.HouseUserDao;
import cn.itcast.entity.HouseUser;
import cn.itcast.service.HouseUserService;
import cn.itcast.service.Impl.BaseServiceImpl;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = HouseUserService.class)
@Transactional
public class HouseUserServiceImpl extends BaseServiceImpl<HouseUser>  implements HouseUserService {

    @Autowired
    private HouseUserDao houseUserDao;

    @Override
    public List<HouseUser> getHouseUsersByHouseId(Long houseId) {
        return houseUserDao.getHouseUsersByHouseId(houseId);
    }

    @Override
    protected BaseDao<HouseUser> getEntityDao() {
        return houseUserDao;
    }
}
