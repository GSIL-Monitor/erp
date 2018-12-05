package com.stosz.product.service;

import com.stosz.BaseTest;
import com.stosz.olderp.ext.model.OldErpCategoryNew;
import com.stosz.olderp.ext.service.IOldErpCategoryNewService;
import com.stosz.product.deamon.OldErpCategoryNewDeamon;
import com.stosz.product.ext.model.Category;
import org.junit.Test;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author he_guitang
 * @version [1.0 , 2018/1/19]
 */
public class OldErpCategoryNewTestService extends BaseTest {

    @Resource
    private IOldErpCategoryNewService iOldErpCategoryNewService;
    @Resource
    private OldErpCategoryNewDeamon oldErpCategoryNewDeamon;

    @Test
    public void add(){
        OldErpCategoryNew param = new OldErpCategoryNew();
        param.setCreateAt(LocalDateTime.now());
        param.setUpdateAt(LocalDateTime.now());
        param.setName("测试数据1");
        param.setNo("00000");
        param.setSortNo(10);
        param.setUsable(true);
        param.setCreatorId(406);
        param.setCreator("哈哈哈哈哈啊哈");
        param.setLeaf(true);
        iOldErpCategoryNewService.add(param);
    }

    @Test
    public void test(){
        oldErpCategoryNewDeamon.oldErpCategoryNewTask();
    }

}
