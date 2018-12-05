package com.stosz.oldErp;

import com.stosz.BaseTest;
import com.stosz.admin.service.olderp.OldErpCategoryNewService;
import com.stosz.olderp.ext.model.OldErpCategoryNew;
import org.junit.Test;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author he_guitang
 * @version [1.0 , 2018/1/19]
 */
public class OldErpCategoryServiceTest extends BaseTest {

    @Resource
    private OldErpCategoryNewService service;

    @Test
    public void add(){
        OldErpCategoryNew param = new OldErpCategoryNew();
        param.setId(50000);
        param.setCreateAt(LocalDateTime.now());
        param.setUpdateAt(LocalDateTime.now());
        param.setName("测试数据2");
        param.setNo("00000");
        param.setSortNo(10);
        param.setUsable(true);
        param.setCreatorId(1);
        param.setCreator("哈哈哈哈哈啊哈");
        param.setLeaf(true);

        OldErpCategoryNew param1 = new OldErpCategoryNew();
        param1.setId(50001);
        param1.setCreateAt(LocalDateTime.now());
        param1.setUpdateAt(LocalDateTime.now());
        param1.setName("测试数据22");
        param1.setNo("00000");
        param1.setSortNo(10);
        param1.setUsable(true);
        param1.setCreatorId(1);
        param1.setCreator("哈哈哈哈哈啊哈");
        param1.setLeaf(true);

        List<OldErpCategoryNew> list = new ArrayList<>();
        list.add(param);
        list.add(param1);
//        service.add(param);
        service.addBatch(list);
    }
}
