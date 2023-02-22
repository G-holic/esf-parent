package cn.itcast.service;

import cn.itcast.entity.Dict;

import java.util.List;
import java.util.Map;

public interface DictService {
    /**
     * 根据数据字典中的数据，通过zTree进行渲染
     * @param id
     * @return
     */
    List<Map<String, Object>> findZnodes(Long id);


    /**
     * 根据父id获取该节点下所有的子节点
     * @param parentId
     * @return
     */
    List<Dict> findListByParentId(Long parentId);

    /**
     * 根据编码获取该节点下的所有子节点
     *
     */
    List<Dict> findListByDictCode(String dictCode);

    String getNameById(Long id);
}
