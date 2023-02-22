package cn.itcast.controller;

import cn.itcast.entity.HouseUser;
import cn.itcast.service.HouseUserService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequestMapping("/houseUser")
public class HouseUserController {

    @Reference
    private HouseUserService houseUserService;

    // 去添加房东的页面
    @RequestMapping("/create")
    public String toCreatePage(@RequestParam("houseId") Long houseId, Map map) {
        // 将房源id放倒request域中
        map.put("houseId", houseId);

        return "houseUser/create";
    }

    // 添加房东
    @RequestMapping("/save")
    public String save(HouseUser houseUser) {
        // 调用HouseUserService中的添加方法
        houseUserService.insert(houseUser);

        return "common/successPage";
    }

    // 去到更新的页面
    @RequestMapping("/edit/{id}")
    public String toEditPage(@PathVariable Long id, Map map) {
        // 调用HouseUserService中根据id查询房东信息的方法
        HouseUser houseUser = houseUserService.getById(id);
        // 将数据放到request域中
        map.put("houseUser", houseUser);

        return "houseUser/edit";
    }

    // 更新
    @RequestMapping("/update")
    public String update(HouseUser houseUser) {
        // 调用HouseUserService中的更新方法
        houseUserService.update(houseUser);

        // 去成功页面
        return "common/successPage";
    }

    // 删除
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId, @PathVariable Long id) {
        // 调用HouseUserService中的删除方法
        houseUserService.delete(id);

        return "redirect:/house/" + houseId;
    }
}
