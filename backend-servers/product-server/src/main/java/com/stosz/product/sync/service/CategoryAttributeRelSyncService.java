package com.stosz.product.sync.service;

import com.stosz.product.ext.model.CategoryAttributeRel;
import com.stosz.product.service.CategoryAttributeRelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 废弃了
 * @author xiongchenyang
 * @version [1.0 , 2017/9/7]
 */
@Service
public class CategoryAttributeRelSyncService {


    @Resource
    private CategoryAttributeRelService categoryAttributeRelService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Async
    public Future<Object> syncCategoryAttributeRel() {
        final int limit = 2000;
        int count = categoryAttributeRelService.countByProduct();
        if (count <= 0) {
            logger.info("产品属性表中没有品类属性关系的记录！");
            return new AsyncResult<>("没有要同步的品类属性关系！");
        }
        int start = 0;
        List<Future<Integer>> futureList = new ArrayList<>();
        for (; start < count; start = start + limit) {
            List<CategoryAttributeRel> categoryAttributeRelList = categoryAttributeRelService.findByProduct(limit, start);
            Future<Integer> future = categoryAttributeRelService.syncList(categoryAttributeRelList);
            futureList.add(future);
        }
        int success = 0;
        for (Future<Integer> future : futureList) {
            try {
                Integer i = future.get();
                success += i;
                logger.info("同步品类属性关系成功，当前批次成功数量{}", i);
            } catch (InterruptedException | RuntimeException | ExecutionException e) {
                logger.info("同步品类属性关系时发生异常！当前同步范围{}到{}", start - limit, start);
                throw new RuntimeException("同步品类属性关系时发生异常！");
            }
        }
        logger.info("同步品类属性关系成功，同步总数{}，成功总数{}！", count, success);
        return new AsyncResult<>("同步品类属性关系成功！");
    }
}  
