package cn.itcast;

import cn.itcast.dao.DictDao;
import cn.itcast.entity.Dict;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@ContextConfiguration(locations = "classpath:spring/spring-dao.xml")
@RunWith(SpringRunner.class)
public class DictTest {

    @Autowired
    private DictDao dictDao;

    /*
    测试根据父id查询所有子节点的方法
     */

    @Test
    public void testFindListParentId(){
        List<Dict> listByParentId = dictDao.findListByParentId(1L);
        for (Dict dict : listByParentId) {
            System.out.println("dict.getName() = " + dict.getName());
        }
    }
}
