package cn.itcast.service;

import cn.itcast.entity.House;
import cn.itcast.vo.HouseQueryVo;
import cn.itcast.vo.HouseVo;
import com.github.pagehelper.PageInfo;

public interface HouseService extends BaseService<House> {
    /**
     * 发布或取消发布
     * @param houseId
     * @param status
     */
    void publish(Long houseId, Integer status);

    PageInfo<HouseVo> findPageList(Integer pageNum, Integer pageSize, HouseQueryVo houseQueryVo);
}
