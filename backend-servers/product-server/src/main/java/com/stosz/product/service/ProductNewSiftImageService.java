package com.stosz.product.service;

import com.stosz.product.ext.model.ProductNewSiftImage;
import com.stosz.product.mapper.ProductNewSiftImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/26]
 */
@Service
public class ProductNewSiftImageService {

    @Resource
    private ProductNewSiftImageMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public void insert(ProductNewSiftImage productNewSiftImage) {
        Assert.notNull(productNewSiftImage, "不允许插入空的产品图片比对结果！");
        int i = mapper.insert(productNewSiftImage);
        Assert.isTrue(i == 1, "插入产品图片比对结果失败！");
        logger.info("插入产品图片比对结果{}成功！", productNewSiftImage);
    }

    public void delete(Integer id) {
        Assert.notNull(id, "要删除的记录入参id不允许为空");
        ProductNewSiftImage productNewSiftImage = mapper.getById(id);
        Assert.notNull(productNewSiftImage, "要删除的图片比对结果在数据库中不存在！");
        int i = mapper.delete(id);
        logger.info("删除图片比对结果{}成功！", productNewSiftImage);
    }

    public void deleteByProductNewId(Integer productNewId) {
        Assert.notNull(productNewId, "入参新品id不允许为空！");
        mapper.deleteByProductNewId(productNewId);
        logger.info("删除新品id{}的图片比对结果成功！", productNewId);
    }



}  
