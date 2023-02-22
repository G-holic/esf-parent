package cn.itcast.controller;

import cn.itcast.entity.HouseImage;
import cn.itcast.result.Result;
import cn.itcast.service.HouseImageService;
import cn.itcast.util.QiniuUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/houseImage")
public class HouseImageController {

    @Reference
    private HouseImageService houseImageService;


    // 去上传图片的页面
    @RequestMapping("/uploadShow/{houseId}/{type}")
    public String goUploadPage(@PathVariable Long houseId, @PathVariable Long type, Map map) {
        // 将数据放到request域中

        map.put("houseId", houseId);
        map.put("type", type);
        return "house/upload";
    }

    // 上传房源或房产图片
    @RequestMapping("/upload/{houseId}/{type}")
    @ResponseBody
    public Result upload(@PathVariable Long houseId, @PathVariable Integer type, @RequestParam(value = "file") MultipartFile[] files) {
        try {
            if (files != null && files.length > 0){
                for (MultipartFile file : files) {
                    // 获取字节数组
                    byte[] bytes = file.getBytes();
                    // 获取图片名字
                    String originalFilename = file.getOriginalFilename();
                    // 通过UUID随机生成一个字符串作为上传到七牛云的图片的名字
                    String newFileName = UUID.randomUUID().toString();
                    // 通过QiniuUtil工具类上传图片到七牛云的图片的名字
                    QiniuUtil.upload2Qiniu(bytes,newFileName);
                    // 创建HouseImage对象
                    HouseImage houseImage = new HouseImage();
                    houseImage.setHouseId(houseId);
                    houseImage.setType(type);
                    houseImage.setImageName(originalFilename);
                    // 设置图片的路径，路径的格式：http://七牛云的域名/随机生成的图片的名字
                    houseImage.setImageUrl("http://rqdbqp53c.hn-bkt.clouddn.com/"+newFileName);
                    // 调用HouseImageService中保存的方法
                    houseImageService.insert(houseImage);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return Result.ok();

    }

    // 删除
    @RequestMapping("/delete/{houseId}/{id}")
    public String delete(@PathVariable Long houseId, @PathVariable Long id){
        // 调用houseImageService的删除方法
        houseImageService.delete(id);

        return "redirect:/house/"+houseId;
    }

}
