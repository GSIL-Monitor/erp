package com.stosz.store.mapper;

import com.stosz.store.ext.model.Invoicing;
import com.stosz.store.mapper.builder.InvoicingBuilder;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:InvoicingMapper
 * @Created Time:2017/11/23 14:23
 * @Update Time:
 */
@Repository
public interface InvoicingMapper {
    //-----------------------------------基础数据增删改查------------------------------------//
    @InsertProvider(type = InvoicingBuilder.class, method = "insert")
    int insert(Invoicing invoicing);

    @DeleteProvider(type = InvoicingBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = InvoicingBuilder.class, method = "update")
    int update(Invoicing invoicing);

    @SelectProvider(type = InvoicingBuilder.class, method = "getById")
    Invoicing getById(Integer id);

    @SelectProvider(type = InvoicingBuilder.class, method = "find")
    Invoicing find(Invoicing invoicing);

    @SelectProvider(type = InvoicingBuilder.class, method = "count")
    int count(Invoicing invoicing);

    //-----------------------------------业务扩展相关操作------------------------------------//

    @SelectProvider(type = InvoicingBuilder.class, method = "query")
    List<Invoicing> query(Invoicing invoicing);
}
