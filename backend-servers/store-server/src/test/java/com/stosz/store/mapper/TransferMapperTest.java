package com.stosz.store.mapper;

import com.stosz.BaseTest;
import com.stosz.store.ext.dto.request.SearchTransferReq;
import com.stosz.store.ext.model.Transfer;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ChenShifeng
 * @version [1.0 , 2017/11/28]
 */
public class TransferMapperTest extends BaseTest {

    @Resource
    private TransferMapper mapper;

    @Test
    public void insert() throws Exception {
    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void count() throws Exception {

        SearchTransferReq req = new SearchTransferReq();

        int count = mapper.count(req);
        logger.info("result:" + count);
    }

    @Test
    public void findList() throws Exception {
        SearchTransferReq req = new SearchTransferReq();

        List<Transfer> ls=mapper.findList(req);
        logger.info("result:" + ls);

    }

    @Test
    public void findPage() throws Exception {
        SearchTransferReq req = new SearchTransferReq();

        List<Transfer> ls=mapper.findPage(req);
        logger.info("result:" + ls);

    }

}