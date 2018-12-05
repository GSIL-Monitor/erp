package com.stosz.store.service;

import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.store.ext.model.Wms;
import com.stosz.store.mapper.WmsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/24 17:36
 * @Update Time:
 */
@Service
public class WmsService {

    @Resource
    private WmsMapper wmsMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 查询所有仓库情况
     *
     * @param wms
     */
    public List<Wms> query(Wms wms) {

        return wmsMapper.query(wms);
    }

    /**
     * 查询仓库情况
     *
     * @param wmsId
     */
    public Wms findById(Integer wmsId) {
        logger.info("根据wmsId:{}查询wms信息", wmsId);
        Assert.notNull(wmsId, "查询id不能为空");
        return wmsMapper.getById(wmsId);
    }

    public List<Wms> findByIds(List<Integer> wmsId) {
        logger.info("根据wmsId:{}查询wms信息", wmsId);
        Assert.notNull(wmsId, "查询id不能为空");
        return wmsMapper.findByIds(wmsId);
    }

    /**
     * 添加仓库
     *
     * @param wms
     */
    public void insertOrUpdate(Wms wms) {
        logger.info("添加或者修改仓库信息为：{}！", wms);
        Assert.notNull(wms, "插入仓库的数据不能为空");
        if (wms.getId() == null) {
            UserDto user = ThreadLocalUtils.getUser();
            wms.setState(0);
            wms.setCreatorId(user.getId());
            wms.setCreator(user.getLastName());
            int i = wmsMapper.insert(wms);
            Assert.isTrue(i == 1, "插入仓库数据失败！");
            logger.info("插入仓库{}成功！", wms);
        } else {
            Wms oldWms = wmsMapper.getById(wms.getId());
            Assert.notNull(oldWms, "不存在该仓库");
            wmsMapper.update(wms);
            logger.info("将仓库{}修改为{}！", oldWms, wms);
        }
    }

    /**
     * 修改仓库状态
     *
     * @param wms
     */
    public void changeState(Wms wms) {
        logger.info("修改仓库状态为：{}！", wms);
        Assert.notNull(wms, "插入仓库的数据不能为空");
        wmsMapper.changeState(wms);
    }

    /**
     * 删除仓库 ,修改删除状态
     *
     * @param wms
     */
    public void del(Wms wms) {
        logger.error("删除仓库id为：{}！", wms.getId());
        Assert.notNull(wms.getId(), "id不能为空");
        wmsMapper.changeDel(wms);
    }

    public int count(Wms wms) {
        return wmsMapper.count(wms);
    }

    public Integer insertBatch(List<Wms> newLists) {
        return wmsMapper.insertBatch(newLists);
    }
}
