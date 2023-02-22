package cn.itcast.service.impl;

import cn.itcast.dao.BaseDao;
import cn.itcast.dao.DictDao;
import cn.itcast.dao.HouseDao;
import cn.itcast.entity.House;
import cn.itcast.service.HouseService;
import cn.itcast.service.Impl.BaseServiceImpl;
import cn.itcast.vo.HouseQueryVo;
import cn.itcast.vo.HouseVo;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service(interfaceClass = HouseService.class)
@Transactional
public class HouseServiceImpl extends BaseServiceImpl<House> implements HouseService {

    @Autowired
    private HouseDao houseDao;

    @Autowired
    private DictDao dictDao;

    @Override
    protected BaseDao<House> getEntityDao() {
        return houseDao;
    }

    @Override
    public void publish(Long houseId, Integer status) {
        // 创建House对象
        House house = new House();
        // 设置id
        house.setId(houseId);
        // 设置状态
        house.setStatus(status);
        // 调用HouseDao中更新的方法
        houseDao.update(house);
    }

    /**
     * 前端分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo) {
        // 开启分页
        PageHelper.startPage(pageNum,pageSize);
        // 调用houseService中前端分页及带条件的查询方法
        Page<HouseVo> page = houseDao.findPageList(houseQueryVo);
        // 遍历page
        for (HouseVo houseVo : page) {
            // 获取房屋的类型
            String houseTypeName = dictDao.getNameById(houseVo.getHouseTypeId());
            // 获取楼层
            String floorName = dictDao.getNameById(houseVo.getFloorId());
            // 获取朝向
            String directionName = dictDao.getNameById(houseVo.getDirectionId());
            // 设置
            houseVo.setHouseTypeName(houseTypeName);
            houseVo.setFloorName(floorName);
            houseVo.setDirectionName(directionName);

        }
        return new PageInfo<>(page,5);
    }

    /**
     * 重写该方法是为了展示房源中户型，楼层，朝向等信息
     * @param id
     * @return
     */
    @Override
    public House getById(Serializable id) {
        House house = houseDao.getById(id);
        // 获取户型
        String houseTypeName = dictDao.getNameById(house.getHouseTypeId());
        // 获取楼层
        String floorName = dictDao.getNameById(house.getFloorId());
        // 获取建筑结构
        String decorationName = dictDao.getNameById(house.getDecorationId());
        // 获取朝向
        String directionName = dictDao.getNameById(house.getDirectionId());
        // 获取装修情况
        String buildStructureName = dictDao.getNameById(house.getBuildStructureId());
        // 获取房屋用途
        String houseUseName = dictDao.getNameById(house.getHouseUseId());
        // 设置
        house.setHouseTypeName(houseTypeName);
        house.setFloorName(floorName);
        house.setDecorationName(decorationName);
        house.setDirectionName(directionName);
        house.setBuildStructureName(buildStructureName);
        house.setHouseUseName(houseUseName);

        return house;
    }
}
