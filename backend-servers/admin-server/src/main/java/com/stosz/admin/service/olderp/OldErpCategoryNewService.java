package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.OldErpCategoryNewMapper;
import com.stosz.olderp.ext.model.OldErpCategoryNew;
import com.stosz.olderp.ext.service.IOldErpCategoryNewService;
import com.stosz.product.ext.model.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author he_guitang
 * @version [1.0 , 2018/1/19]
 */
@Service
public class OldErpCategoryNewService implements IOldErpCategoryNewService{

    @Resource
    private OldErpCategoryNewMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 品类的添加
     */
    @Override
    public void add(OldErpCategoryNew param){
        mapper.add(param);
        logger.info("老ERP品类添加成功!");
    }

    /**
     * 品类批量增加
     */
    @Override
    public int addBatch(List<OldErpCategoryNew> param){
        Assert.isTrue(param != null && param.size() != 0, "批量增加老erp的品类表的时候,参数为空!");
        return mapper.insertBatch(param);
    }

    /**
     * 品类表截断
     */
    @Override
    public void truncate(){
        mapper.truncate();
    }

    /**
     * 品类删除
     */
    @Override
    public void delete(Integer id){
        mapper.delete(id);
        logger.info("老ERP品类删除成功,ID为["+id+"]!");
    }

    /**
     * 品类修改
     */
    @Override
    public int update(OldErpCategoryNew param){
        logger.info("老ERP品类修改成功,ID:[" + param.getId() +"]");
        return mapper.update(param);
    }

    /**
     * 品类信息校验
     */
    private OldErpCategoryNew load(Integer id){
        OldErpCategoryNew oldErpCategory = mapper.getById(id);
        Assert.notNull(oldErpCategory, "品类ID["+id+"]未查询到对应的品类数据!");
        return oldErpCategory;
    }

    /**
     * 查询总数
     */
    @Override
    public int countAll(){
        return mapper.countAll();
    }

    /**
     * 品类最后一条数据获取
     */
    @Override
    public OldErpCategoryNew getLastData(){
        return mapper.getLastData();
    }

    /**
     * 更新品类根节点的id为0
     */
    @Override
    public int updateRootId() {
        return mapper.updateRootId();
    }
}
