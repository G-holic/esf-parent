package cn.itcast.controller;

import cn.itcast.entity.*;
import cn.itcast.service.*;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/house")
public class HouseController extends BaseController {

    @Reference
    private HouseService houseService;

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    @Reference
    private HouseBrokerService houseBrokerService;
    @Reference
    private HouseImageService houseImageService;
    @Reference
    private HouseUserService houseUserService;

    // 分页及带条件查询的方法
    @RequestMapping
    public String index(Map map, HttpServletRequest request) {
        // 获取请求参数
        Map<String, Object> filters = getFilters(request);
        // 将filters放入域中
        map.put("filters", filters);
        // 调用HouseService中的分页方法
        PageInfo<House> pageInfo = houseService.findPage(filters);
        // 将数据放入域中
        map.put("page", pageInfo);
        // 将小区和数据字典中的数据放到域中
        setRequestAttribute(map);


        return "house/index";
    }

    // 去添加的页面
    @RequestMapping("/create")
    public String toCreatePage(Map map) {
        // 将小区和数据字典中的数据放到域中
        setRequestAttribute(map);

        return "house/create";
    }

    // 添加房源
    @RequestMapping("/save")
    public String toSave(House house) {
        // 调用HouseService中的新增、方法
        houseService.insert(house);

        return "common/successPage";
    }

    // 去修改的页面
    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable Long id, Map map) {
        // 调用HouseService中根据id查询房源信息的方法
        House house = houseService.getById(id);
        // 将数据共享到域中
        map.put("house", house);
        // 将小区和数据字典中的数据放到域中
        setRequestAttribute(map);

        return "house/edit";
    }

    // 修改房源信息
    @RequestMapping("/update")
    public String toUpdate(House house,Map map) {

        // 调用HouSeService中的修改方法
        houseService.update(house);

        return "common/successPage";
    }

    // 删除
    @RequestMapping("/delete/{id}")
    public String toDelete(@PathVariable Long id) {
        // 调用HouSeService中的删除方法
        houseService.delete(id);

        return "redirect:/house";
    }


    // 发布和取消发布
    @RequestMapping("/publish/{houseId}/{status}")
    public String publish(@PathVariable Long houseId,@PathVariable Integer status){
        // 调用HouseService中发布或取消发布的方法
        houseService.publish(houseId,status);
        // 重定向到查询房源的方法
        return "redirect:/house";

    }

    // 查看房源详情
    @RequestMapping("/{houseId}")
    public String toShow(@PathVariable Long houseId,Map map){
        // 调用HuoService中根据id查询房源的方法
        House house = houseService.getById(houseId);
        // 将房源信息放到Request域中
        map.put("house",house);
        // 调用CommunityService中根据小区id查询小区的方法
        Community community = communityService.getById(house.getCommunityId());
        // 将小区信息放到Request域中
        map.put("community",community);

        // 查询房源图片
        List<HouseImage> houseImage1List = houseImageService.getHouseImagesByHouseIdAndType(houseId, 1);
        // 查询房产图片
        List<HouseImage> houseImage21List = houseImageService.getHouseImagesByHouseIdAndType(houseId, 2);

        // 查询经纪人
        List<HouseBroker> houseBrokerList = houseBrokerService.getHouseBrokersByHouseId(houseId);
        
        // 查询房东
        List<HouseUser> houseUserList = houseUserService.getHouseUsersByHouseId(houseId);
        // 放到request域中
        map.put("houseImage1List",houseImage1List);
        map.put("houseImage21List",houseImage21List);
        map.put("houseBrokerList",houseBrokerList);
        map.put("houseUserList",houseUserList);

        return "house/show";
    }





    // 获取所有小区及数据字典中数据的方法放到request域中
    public void setRequestAttribute(Map map){
        // 获取所有的小区
        List<Community> communityList = communityService.findAll();
        // 获取所有户型
        List<Dict> houseTypeList = dictService.findListByDictCode("houseType");
        // 获取楼层
        List<Dict> floorList = dictService.findListByDictCode("floor");
        // 获取建筑结构
        List<Dict> buildStructureList = dictService.findListByDictCode("buildStructure");
        // 获取朝向
        List<Dict> directionList = dictService.findListByDictCode("direction");
        // 获取装修情况
        List<Dict> decorationList = dictService.findListByDictCode("decoration");
        // 获取房屋用途
        List<Dict> houseUseList = dictService.findListByDictCode("houseUse");

        // 将数据共享到域中
        map.put("communityList", communityList);
        map.put("houseTypeList", houseTypeList);
        map.put("floorList", floorList);
        map.put("buildStructureList", buildStructureList);
        map.put("directionList", directionList);
        map.put("decorationList", decorationList);
        map.put("houseUseList", houseUseList);
    }
}
