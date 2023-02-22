package cn.itcast.service.impl;

import cn.itcast.dao.BaseDao;
import cn.itcast.dao.CommunityDao;
import cn.itcast.dao.DictDao;
import cn.itcast.entity.Community;
import cn.itcast.service.CommunityService;
import cn.itcast.service.Impl.BaseServiceImpl;
import cn.itcast.util.CastUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CommunityService.class)
@Transactional
public class CommunityServiceImpl extends BaseServiceImpl<Community> implements CommunityService {

    @Autowired
    private CommunityDao communityDao;

    @Autowired
    private DictDao dictDao;

    @Override
    protected BaseDao<Community> getEntityDao() {
        return communityDao;
    }

    /**
     * 重写分页的方法，目的是为了给小区中的区域和板块的名字赋值
     * @param filters
     * @return
     */
    @Override
    public PageInfo<Community> findPage(Map<String, Object> filters) {
        //当前页数
        int pageNum = CastUtil.castInt(filters.get("pageNum"), 1);
        //每页显示的记录条数
        int pageSize = CastUtil.castInt(filters.get("pageSize"), 10);

        PageHelper.startPage(pageNum, pageSize);
        // 调用CommunityDao中的分页及带条件查询的方法
        Page<Community> page = communityDao.findPage(filters);
        // 遍历page对象
        for (Community community : page) {
            // 根据区域的id获取区域的名字
            String areaName = dictDao.getNameById(community.getAreaId());
            // 根据板块的id获取板块的名字
            String plateName = dictDao.getNameById(community.getPlateId());
            // 给community对象的区域和板块名赋值
            community.setAreaName(areaName);
            community.setPlateName(plateName);
        }
        return new PageInfo<>(page,10);
    }

    @Override
    public List<Community> findAll() {
        return communityDao.findAll();
    }

    /**
     * 重写该方法是为了展示小区的区域/板块：等信息
     * @param id
     * @return
     */
    @Override
    public Community getById(Serializable id) {
        // 获取区域
        Community community = communityDao.getById(id);
        // 获取板块
        String areaName = dictDao.getNameById(community.getAreaId());
        // 获取区域
        String plateName = dictDao.getNameById(community.getPlateId());
        //设置
        community.setAreaName(areaName);
        community.setPlateName(plateName);

        return community;
    }


}
