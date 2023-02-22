package cn.itcast.controller;

import cn.itcast.entity.UserInfo;
import cn.itcast.result.Result;
import cn.itcast.service.UserFollowService;
import cn.itcast.vo.UserFollowVo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value="/userFollow")
public class UserFollowController {

    @Reference
    private UserFollowService userFollowService;

    // 关注房源
    @RequestMapping("/auth/follow/{houseId}")
    public Result follow(@PathVariable Long houseId, HttpSession session){
        // 获取UserInfo对象
        UserInfo userInfo = (UserInfo)session.getAttribute("user");
        // 调用UserFollowService中关注房源的方法
        userFollowService.follow(userInfo.getId(),houseId);
        return Result.ok();
    }

    // 查询我关注的房源
    @RequestMapping("/auth/list/{pageNum}/{pageSize}")
    public Result myFollowed(@PathVariable Integer pageNum,
                               @PathVariable Integer pageSize,
                               HttpSession session) {
        // 从Session域中获取UserInfo对象
        UserInfo userInfo = (UserInfo)session.getAttribute("user");
        // 调用UserFollowService中分页的方法
        PageInfo<UserFollowVo> pageInfo = userFollowService.findPageList(pageNum, pageSize, userInfo.getId());

        return Result.ok(pageInfo);
    }

    // 取消关注
    @RequestMapping("/auth/cancelFollow/{id}")
    public Result cancelFollowed(@PathVariable Long id){
        // 调用userFollowService中取消关注的方法
        userFollowService.cancelFollowed(id);

        return Result.ok();
    }
}
