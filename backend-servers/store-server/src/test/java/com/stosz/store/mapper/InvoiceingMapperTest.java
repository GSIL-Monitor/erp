package com.stosz.store.mapper;

import com.stosz.BaseTest;
import com.stosz.store.ext.model.Invoicing;
import org.junit.Test;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/10]
 */
public class InvoiceingMapperTest extends BaseTest {

    @Resource
    private InvoicingMapper mapper;

    @Test
    public void insert() throws Exception {
        Invoicing stock = new Invoicing();
        stock.setId(5);
        stock.setCreator("TransferEventEnum");
        stock.setCreatorId(1);
        stock.setDeptId(1);
        stock.setDeptNo("123123");
        stock.setDeptName("一营一部");
        stock.setWmsId(3);
        stock.setSku("1");
        stock.setVoucherNo("");
        stock.setQuantity(0);
        stock.setType(1);
        stock.setAmount(new BigDecimal(10));
        stock.setPlanId(1);
        mapper.insert(stock);

    }

    @Test
    public void delete() throws Exception {
        mapper.delete(1);
        logger.info("ok");
    }

    @Test
    public void update() throws Exception {
    }

    @Test
    public void getById() throws Exception {
    }

    @Test
    public void count() throws Exception {
        Invoicing invoicing=new Invoicing();
        int result=mapper.count(invoicing);

        logger.info("result:"+result);
    }

    @Test
    public void query() throws Exception {
        Invoicing invoicing=new Invoicing();
        List<Invoicing> result=mapper.query(invoicing);

        logger.info("result:"+result);
    }

}