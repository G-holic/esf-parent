package cn.itcast.controller;

import cn.itcast.entity.Community;
import cn.itcast.entity.Dict;
import cn.itcast.result.Result;
import cn.itcast.service.CommunityService;
import cn.itcast.service.DictService;
import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/community")
public class CommunityController extends BaseController {

    @Reference
    private CommunityService communityService;

    @Reference
    private DictService dictService;

    // 分页及带条件查询的方法
    @RequestMapping
    public String index(Map map, HttpServletRequest request){
        // 获取请求参数
        Map<String, Object> filters = getFilters(request);
        // 将filters放到request域中
        map.put("filters",filters);
        // 调用communityService中分页的方法
        PageInfo<Community> pageInfo = communityService.findPage(filters);
        // 将数据共享到域中
        map.put("page",pageInfo);


        // 根据编码获取北京所有的区
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        // 将北京所有的区域放到request域中
        map.put("areaList",areaList);

        return "community/index";

    }

    // 去添加小区的页面
    @RequestMapping("/create")
    public String toAddPage(Map map){
        // 根据编码获取北京所有的区
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        // 将北京所有的区域放到request域中
        map.put("areaList",areaList);

        return "community/create";
    }

    // 添加小区
    @RequestMapping("/save")
    public String toSave(Community community){
        // 调用CommunityService中添加的方法
        communityService.insert(community);

        // 去成功页面
        return "common/successPage";
    }

    // 去修改小区的页面
    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable  Long id,Map map){
        // 根据编码获取北京所有的区
        List<Dict> areaList = dictService.findListByDictCode("beijing");
        // 将北京所有的区域放到request域中
        map.put("areaList",areaList);
        // 调用CommunityService中查询的方法
        Community community = communityService.getById(id);
        // 将小区放到request域中
        map.put("community",community);

        return "community/edit";
    }

    //   修改小区信息
    @RequestMapping("/update")
    public String toUpdate(Community community){
        // 调用CommunityService中的修改方法
        communityService.update(community);

        return "common/successPage";
    }

    // 删除
    @RequestMapping("/delete/{id}")
    public String toDelete(@PathVariable Long id){
        // // 调用CommunityService中的删除方法
        communityService.delete(id);

        return "redirect:/community";
    }


}
