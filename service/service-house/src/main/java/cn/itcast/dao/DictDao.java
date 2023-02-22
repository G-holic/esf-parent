package cn.itcast.dao;

import cn.itcast.entity.Dict;

import java.util.List;

public interface DictDao {
    /**
     * 根据父id获取该节点下所有的子节点
     * @param id
     * @return
     */
    List<Dict> findListByParentId(Long id);

    /**
     * 根据父id判断该节点是否是父节点的方法
     * @param id
     * @return
     */
    Integer isParentNode(Long id);

    /**
     * 根据编码获取Dict对象
     * @param code
     * @return
     */
    Dict findDictByDictCode(String code);

    /**
     * 根据id获取name
     * @param id
     * @return
     */
    String getNameById(Long id);

}
