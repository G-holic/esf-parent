package cn.itcast.controller;


import cn.itcast.entity.Dict;
import cn.itcast.result.Result;
import cn.itcast.service.DictService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dict")
public class DictController {

    @Reference
    private DictService dictService;

    // 根据编码获取所有子节点
    @RequestMapping("/findListByDictCode/{dictCode}")
    public Result findListByDictCode(@PathVariable String dictCode){
        // 调用dictService中根据编码获取所有子节点的方法
        List<Dict> listByDictCode = dictService.findListByDictCode(dictCode);

        return Result.ok(listByDictCode);
    }

    // 根据父id查询所有子节点
    @RequestMapping("/findListByParentId/{areaId}")
    public Result findListByParentId(@PathVariable Long areaId){
        // 调用dictService中根据父id获取所有子节点的方法
        List<Dict> listByParentId = dictService.findListByParentId(areaId);

        return Result.ok(listByParentId);
    }
}
