package cn.itcast.controller;

import cn.itcast.entity.*;
import cn.itcast.result.Result;
import cn.itcast.service.*;
import cn.itcast.vo.HouseQueryVo;
import cn.itcast.vo.HouseVo;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/house")
public class HouseController {

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private HouseImageService houseImageService;

    @Reference
    private HouseBrokerService houseBrokerService;

    @Reference
    private UserFollowService userFollowService;

    // 分页及带条件查询的方法
    @RequestMapping("/list/{pageNum}/{pageSize}")
    public Result findPageList(@PathVariable Integer pageNum, @PathVariable Integer pageSize,
                               @RequestBody HouseQueryVo houseQueryVo){

        // 调用houseService中前端分页及带条件的查询方法
        PageInfo<HouseVo> pageInfo = houseService.findPageList(pageNum,pageSize,houseQueryVo);

        return Result.ok(pageInfo);
    }

    // 查看房源详情（前端）
    @RequestMapping("/info/{id}")
    public Result info(@PathVariable Long id, HttpSession session){
        // 获取房源信息
        House house = houseService.getById(id);
        // 获取小区信息
        Community community = communityService.getById(house.getCommunityId());
        // 获取房源的图片
        List<HouseImage> houseImage1List = houseImageService.getHouseImagesByHouseIdAndType(id, 1);
        // 获取房源经纪人
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokersByHouseId(id);
        // 创建一个Map
        Map map = new HashMap<>();
        // 将房源信息、小区信息，房源图片和房源的经纪人放到map中
        map.put("house",house);
        map.put("community",community);
        map.put("houseImage1List",houseImage1List);
        map.put("houseBrokerList",houseBrokerList);
        // 默认设置没有关注该房源
//        map.put("isFollow",false);
        // 设置一个变量
        Boolean isFollowed = false;
        // 从Session中获取userInfo对象
        UserInfo userInfo = (UserInfo)session.getAttribute("user");
        if (userInfo != null){
            // 证明已经登录，调用userFollowService中查询是否关注该房源的方法
            isFollowed = userFollowService.isFollowed(userInfo.getId(),id);
        }
        // 将isFollow放入map中
        map.put("isFollow",isFollowed);

        return Result.ok(map);
    }
}
