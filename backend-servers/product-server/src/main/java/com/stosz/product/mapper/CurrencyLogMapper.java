package com.stosz.product.mapper;

import com.stosz.product.ext.model.Currency;
import com.stosz.product.ext.model.CurrencyLog;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyLogMapper {
	
	@InsertProvider(type = CurrencyLogBulider.class,method = "insert")
	int insert(CurrencyLog param);

	@DeleteProvider(type = CurrencyLogBulider.class, method = "delete")
	int delete(@Param("id") Integer id);

	@UpdateProvider(type = CurrencyLogBulider.class, method = "update")
	int update(CurrencyLog param);
	
	@SelectProvider(type = CurrencyLogBulider.class, method = "getById")
	CurrencyLog getById(@Param("id") Integer id);
	
    @SelectProvider(type = CurrencyLogBulider.class, method = "find")
	List<CurrencyLog> find(CurrencyLog param);

    @SelectProvider(type = CurrencyLogBulider.class, method = "count")
	int count(CurrencyLog param);

    /**
     * 根据汇率code3查询相应的汇率历史
     */
	@Select("SELECT * FROM currency_log WHERE currency_code = #{currencyCode} ORDER BY id DESC ")
	List<CurrencyLog> historyCurrency(Currency currency);
}