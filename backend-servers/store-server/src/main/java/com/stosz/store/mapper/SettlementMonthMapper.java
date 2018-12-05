package com.stosz.store.mapper;

import com.stosz.store.ext.model.SettlementMonth;
import com.stosz.store.mapper.builder.SettlementBuilder;
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
public interface SettlementMonthMapper {
    //-----------------------------------基础数据增删改查------------------------------------//
    @InsertProvider(type = SettlementBuilder.class, method = "insert")
    int insert(SettlementMonth settlementMonth);

    @DeleteProvider(type = SettlementBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = SettlementBuilder.class, method = "update")
    int update(SettlementMonth settlementMonth);

    @SelectProvider(type = SettlementBuilder.class, method = "getById")
    SettlementMonth getById(Integer id);

    @SelectProvider(type = SettlementBuilder.class, method = "find")
    SettlementMonth find(SettlementMonth settlementMonth);

    //-----------------------------------业务扩展相关操作------------------------------------//

    @SelectProvider(type = SettlementBuilder.class, method = "query")
    List<SettlementMonth> query(SettlementMonth settlementMonth);
}
