package cn.itcast.dao;

import cn.itcast.entity.UserFollow;
import cn.itcast.vo.UserFollowVo;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

public interface UserFollowDao extends BaseDao<UserFollow> {

    Integer countByUserIdAndHouseId(@Param("userId") Long userId, @Param("houseId")Long houseId);

    Page<UserFollowVo> findPageList(Long userId);


}
