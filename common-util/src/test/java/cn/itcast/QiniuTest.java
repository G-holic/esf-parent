package cn.itcast;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

public class QiniuTest {

    /**
     * 测试向七牛云上传图片
     * Zone.zone0():华东
     * Zone.zone1():华北
     * Zone.zone2():华南
     */

    @Test
    public void testUpload(){
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
//...其他参数参考类注释

        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传
        // 去个人中心，密钥管理中将AK和SK拷贝过来
        String accessKey = "NMiludzIsM7_V694VfOzJb1T1gjUC-g15YRlJ_es";
        String secretKey = "3swBE6y3Pi9uEdXgkKF14AdPkWwZI6lDeBLBqLmQ";
        // 设置你空间的，名字
        String bucket = "esf2023";
//如果是Windows情况下，格式是 D:\\qiniu\\test.png
        String localFilePath = "C:/Users/24998/Desktop/矢量图标/book.png";
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null; // 指定名字。不指定默认哈希值作为名字

        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }

    }


}
