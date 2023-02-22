package cn.itcast.controller;

import cn.itcast.entity.Admin;
import cn.itcast.entity.HouseBroker;
import cn.itcast.service.AdminService;
import cn.itcast.service.HouseBrokerService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/houseBroker")
public class HouseBrokerController {

    @Reference
    private AdminService adminService;
    @Reference
    private HouseBrokerService houseBrokerService;

    // 去添加经纪人的页面
    @RequestMapping("/create")
    public String toAddPage(@RequestParam("houseId") Long houseId, Map map) {
        // 将房源的id放到request域中
        map.put("houseId", houseId);
        // 调用AdminService中获取所有用户的方法
        List<Admin> adminList = adminService.findAll();
        // 将所有用户放到request域中
        map.put("adminList", adminList);
        return "houseBroker/create";
    }

    // 添加经纪人
    @RequestMapping("/save")
    public String toSave(HouseBroker houseBroker) {
        // 根据AdminService中的方法查询经纪人的完整信息
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());
        // 调用HouseBrokerService中保存的方法
        houseBrokerService.insert(houseBroker);
        // 去成功页面
        return "common/successPage";
    }

    // 去修改经纪人的页面
    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable Long id, Map map) {
        // 调用HouseBrokerService中的方法根据id查询经纪人的完整信息
        HouseBroker broker = houseBrokerService.getById(id);
        // 将经纪人放到request域中
        map.put("houseBroker", broker);
        // 调用AdminService中获取所有用户的方法
        List<Admin> adminList = adminService.findAll();
        // 将所有用户放到request域中
        map.put("adminList", adminList);

        return "houseBroker/edit";
    }

    // 修改经纪人
    @RequestMapping("/update")
    public String update(HouseBroker houseBroker) {
        // 根据AdminService中的方法查询经纪人的完整信息
        Admin admin = adminService.getById(houseBroker.getBrokerId());
        houseBroker.setBrokerHeadUrl(admin.getHeadUrl());
        houseBroker.setBrokerName(admin.getName());
        // 调用HouseBrokerService中更新的方法
        houseBrokerService.update(houseBroker);

        return "common/successPage";
    }

    // 删除
    @RequestMapping("/delete/{houseId}/{brokerId}")
    public String delete(@PathVariable Long houseId, @PathVariable Long brokerId) {
        // 调用HouseBrokerService中删除的方法
        houseBrokerService.delete(brokerId);

        return "redirect:/house/" + houseId;
    }

}
