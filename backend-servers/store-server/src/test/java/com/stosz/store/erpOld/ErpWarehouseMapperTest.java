package com.stosz.store.erpOld;

import com.stosz.BaseTest;
import com.stosz.old.erp.ext.model.OldErpWarehouse;
import com.stosz.store.erpOld.sync.SyncWms;
import com.stosz.store.erpOld.sync.mapper.ErpWarehouseMapper;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
public class ErpWarehouseMapperTest extends BaseTest {

    @Resource
    private ErpWarehouseMapper mapper;

    @Resource
    private SyncWms syncWms;

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void getById() throws Exception {
    }



    @Test
    public void query() throws Exception {
        List<OldErpWarehouse> result=mapper.findLimit(1,9);

        logger.info("result:"+result);
    }

    @Test
    public void getOldMaxId() throws Exception {
        int result=mapper.getOldMaxId();

        logger.info("result:"+result);

    }

    @Test
    public void sync() throws Exception {
        logger.info("同步 erp_warehouse=>wms");
        syncWms.SyncWms();
        logger.info("同步结束");

    }





}