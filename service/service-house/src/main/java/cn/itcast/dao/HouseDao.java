package cn.itcast.dao;

import cn.itcast.entity.House;
import cn.itcast.vo.HouseQueryVo;
import cn.itcast.vo.HouseVo;
import com.github.pagehelper.Page;

public interface HouseDao extends BaseDao<House> {
    Page<HouseVo> findPageList(HouseQueryVo houseQueryVo);
}
