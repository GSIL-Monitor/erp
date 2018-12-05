package com.stosz.product.sync.service;

import com.stosz.olderp.ext.model.OldErpCategory;
import com.stosz.olderp.ext.service.IOldErpCategoryService;
import com.stosz.product.ext.model.Category;
import com.stosz.product.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 老erp分类同步的service
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/8/30]
 */
@Service
public class OldErpCategorySyncService {

    @Resource
    private IOldErpCategoryService iOldErpCategoryService;
    @Resource
    private CategoryService categoryService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 拉取老erp分类信息
     */
    @Async
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Object> pull() {
        logger.info("============================ 同步pull老erp分类信息开始了 ===========================");
        //判断是否有根节点，没有则插入
        Category categoryDB = categoryService.getSyncById(0);
        if (categoryDB == null) {
            categoryService.insertRoot();
        }

        List<OldErpCategory> oldErpCategoryList = iOldErpCategoryService.findAll();
        Assert.notNull(oldErpCategoryList, "pull老erp分类信息失败，获取到的老erp分类集合为null ！");
        Assert.notEmpty(oldErpCategoryList, "pull老erp分类信息失败，获取到的老erp分类集合长度为0！");
        for (OldErpCategory oldErpCategory : oldErpCategoryList) {
            Category category = new Category();
            category.setId(oldErpCategory.getId());
            String name = oldErpCategory.getTitle();
            name = name.replaceAll(" ", "").trim();
            category.setName(name);
            category.setParentId(oldErpCategory.getParentId());
            category.setUsable(oldErpCategory.getStatus());
            insertOrUpdate(category);
        }
        logger.info("同步pull分类信息成功，分类集合{}", oldErpCategoryList);
        logger.info("============================ 同步pull老erp分类信息结束了 ===========================");
        return new AsyncResult<>("同步pull分类信息成功！");
    }

    /**
     * 将分类信息push到老erp
     */
    @Async
    public Future<Object> push() {
        logger.info("============================ 同步push分类信息开始了 ===========================");
        List<Category> categoryList = categoryService.findAll();
        Assert.notNull(categoryList, "push新erp分类信息到老erp失败，获取到的新erp分类集合为null ！");
        Assert.notEmpty(categoryList, "push新erp分类信息到老erp失败，获取到的新erp分类集合长度为0！");
        for (Category category : categoryList) {
            OldErpCategory oldErpCategory = new OldErpCategory();
            oldErpCategory.setId(category.getId());
            oldErpCategory.setTitle(category.getName());
            oldErpCategory.setParentId(category.getParentId());
            oldErpCategory.setStatus(category.getUsable());
            insertOrUpdate(oldErpCategory);
        }
        logger.info("============================ 同步push分类信息结束了 ===========================");
        logger.info("同步push分类信息成功，分类集合{}", categoryList);
        return new AsyncResult<>("同步push分类信息成功");
    }

    /**
     * 判断是插入还是修改分类(pull)
     */
    private void insertOrUpdate(Category category) {
        Category categoryDB = categoryService.getById(category.getId());
        if (categoryDB == null) {
            category.setSortNo(100);
            category.setCreatorId(0);
            category.setCreator("系统");
            categoryService.insertBySync(category);
            logger.info("同步老erp分类时，新增{}分类成功！", category);
        } else {
            category.setNo(categoryDB.getNo());
            category.setSortNo(categoryDB.getSortNo());
            category.setCreator(categoryDB.getCreator());
            category.setCreatorId(categoryDB.getCreatorId());
            category.setLeaf(categoryDB.getLeaf());
            categoryService.updateCategorySync(category);
            logger.info("同步老erp分类时，将{}分类修改为{}成功！", categoryDB, category);
        }
    }

    /**
     * 判断是插入还是修改分类(push)
     */
    private void insertOrUpdate(OldErpCategory oldErpCategory) {
        OldErpCategory oldErpCategoryDB = iOldErpCategoryService.getById(oldErpCategory.getId());
        if (oldErpCategoryDB == null) {
            iOldErpCategoryService.insert(oldErpCategory);
            logger.info("push分类{}到老erp新增成功！", oldErpCategory);
        } else {
            iOldErpCategoryService.update(oldErpCategory);
            logger.info("push分类{}到老erp更新{}成功！", oldErpCategory, oldErpCategoryDB);
        }
    }


}
