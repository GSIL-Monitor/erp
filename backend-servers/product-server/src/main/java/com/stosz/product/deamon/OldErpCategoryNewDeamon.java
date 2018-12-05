package com.stosz.product.deamon;

import com.stosz.olderp.ext.model.OldErpCategoryNew;
import com.stosz.olderp.ext.service.IOldErpCategoryNewService;
import com.stosz.product.ext.model.Category;
import com.stosz.product.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author he_guitang
 * @version [1.0 , 2018/1/19]
 */
@Component
public class OldErpCategoryNewDeamon {

    @Resource
    private CategoryService categoryService;
    @Resource
    private IOldErpCategoryNewService iOldErpCategoryNewService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 老erp品类表同步
     */
    public void oldErpCategoryNewTask(){
        int oldCount = iOldErpCategoryNewService.countAll();
        List<OldErpCategoryNew> categoryList = categoryService.findResultOldCateByNo("");
        Assert.isTrue(categoryList != null && categoryList.size() != 0, "同步品类表时,未获取到新erp品类表的数据!");
        List<OldErpCategoryNew> categoryNews = categoryList.stream().filter(e -> e.getParentId().equals(-1)).collect(Collectors.toList());
        Assert.isTrue(categoryNews != null && categoryNews.size() == 1, "新ERP品类表数据有误,根节点不存在或者有多个!");
        if (oldCount == categoryList.size()){
            logger.info("同步老ERP品类表的时候,两表总数一致,进行更新操作....");
            for (OldErpCategoryNew categoryNew : categoryList){
                int updateCount = iOldErpCategoryNewService.update(categoryNew);
                if (updateCount != 1){
                    logger.info("修改老ERP品类表的时候,根据ID修改老ERP品类表,返回结果为:["+updateCount+"],截断表,重新插入");
                    oldErpCateAdd(oldCount, categoryList);
                    return;
                }
            }
        }else{
            logger.info("同步老ERP品类表的时候,两表数据总数不一致,老ERP总数为["+oldCount+"],新erp总数为["+categoryList.size()+"],直接截断表,重新插入");
            oldErpCateAdd(oldCount, categoryList);
        }
    }

    private void oldErpCateAdd(Integer oldCount, List<OldErpCategoryNew> categoryList){
        iOldErpCategoryNewService.truncate();
        logger.info("截断ERP品类表成功....");
        int addCount = iOldErpCategoryNewService.addBatch(categoryList);
        Assert.isTrue(addCount == categoryList.size(), "老ERP同步品类表失败,总数为["+oldCount+"],实际为["+addCount+"]!");
        logger.info("重新插入老erp品类表成功,直接返回,耗时[]....");
        int updateRootCount = iOldErpCategoryNewService.updateRootId();
        Assert.isTrue(updateRootCount == 1, "老ERP同步品类表时,更新根节点ID失败!");
        logger.info("更新品类根节点成功");
    }

}
