package cn.itcast.service;

import cn.itcast.entity.UserFollow;
import cn.itcast.vo.UserFollowVo;
import com.github.pagehelper.PageInfo;

public interface UserFollowService  extends BaseService<UserFollow> {

    void follow(Long id, Long houseId);

    // 查询是否关注了该房源
    Boolean isFollowed(Long id, Long id1);

    /**
     * 分页查询我关注的房源
     * @param pageNum
     * @param pageSize
     * @param userId
     * @return
     */
    PageInfo<UserFollowVo> findPageList(Integer pageNum, Integer pageSize, Long userId);

    // 取消关注
    void  cancelFollowed(Long id);
}
