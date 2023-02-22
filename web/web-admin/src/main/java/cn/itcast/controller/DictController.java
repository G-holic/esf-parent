package cn.itcast.controller;

import cn.itcast.entity.Dict;
import cn.itcast.result.Result;
import cn.itcast.service.DictService;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dict")
public class DictController {

    @Reference
    private DictService dictService;

    // 去展示数据字典的页面
    @RequestMapping
    public String index(){
        return "dict/index";
    }

    // 获取数据字典中的数据
    @RequestMapping("/findZnodes")
    @ResponseBody
    public Result findZnodes(@RequestParam(value = "id",defaultValue = "0") Long id){
        // 调用DictService中工具父id查询数据字典中的数据的方法
        List<Map<String,Object>> zNodes = dictService.findZnodes(id);

        return Result.ok(zNodes);
    }

    // 根据父id获取所有子节点
    @ResponseBody
    @RequestMapping(value = "findListByParentId/{areaId}",method = RequestMethod.GET)
    public Result findListByParentId(@PathVariable("areaId") Long areaId){
        // 调用DictService中的父id查询所有子节点的方法
        List<Dict> listByParentId = dictService.findListByParentId(areaId);
        return Result.ok(listByParentId);

    }

    /*//**
     * 根据编码获取子节点数据列表
     * @param dictCode
     * @return
     *//*
    @GetMapping(value = "findListByDictCode/{dictCode}")
    @ResponseBody
    public Result<List<Dict>> findListByDictCode(@PathVariable String dictCode) {
        List<Dict> list = dictService.findListByDictCode(dictCode);
        return Result.ok(list);
    }*/
}
