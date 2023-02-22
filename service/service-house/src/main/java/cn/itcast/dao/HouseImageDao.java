package cn.itcast.dao;

import cn.itcast.entity.HouseImage;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface HouseImageDao extends BaseDao<HouseImage> {

    /**
     * 根据房源id和类型查询房源图片或房产图片
     * @param houseId
     * @param type
     * @return
     */
    List<HouseImage> getHouseImagesByHouseIdAndType(@Param("houseId") Long houseId,@Param("type") Integer type);
}
