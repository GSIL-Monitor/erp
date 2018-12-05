package com.stosz.store.service;

import com.stosz.store.ext.model.Invoicing;
import com.stosz.store.mapper.InvoicingMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/23 17:56
 * @Update Time:
 */
@Service
public class InvoicingService {

    @Resource
    private InvoicingMapper invoicingMapper;

    /**
     * 插入仓库流水
     *
     * @param invoicing
     */
    public void insert(Invoicing invoicing) {
        int insert = invoicingMapper.insert(invoicing);
        Assert.isTrue(insert==1,"插入进销存失败");
    }

    /**
     * 月结表获取相关数据
     *
     * @param invoicing
     */
    public List<Invoicing> query(Invoicing invoicing) {

        return invoicingMapper.query(invoicing);
    }

    /**
     * 统计条数
     *
     * @param invoicing
     */
    public int count(Invoicing invoicing) {

        return invoicingMapper.count(invoicing);
    }
}
