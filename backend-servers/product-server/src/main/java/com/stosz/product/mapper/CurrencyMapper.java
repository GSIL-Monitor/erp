package com.stosz.product.mapper;

import com.stosz.product.ext.model.Currency;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyMapper {
	
	@InsertProvider(type = CurrencyBulider.class,method = "insert")
	int insert(Currency param);

	@DeleteProvider(type = CurrencyBulider.class, method = "delete")
	int delete(@Param("id") Integer id);

	@UpdateProvider(type = CurrencyBulider.class, method = "update")
	int update(Currency param);
	
    @SelectProvider(type = CurrencyBulider.class, method = "find")
	List<Currency> find(Currency param);

	@SelectProvider(type = CurrencyBulider.class, method = "findList")
	List<Currency> findList(Currency param);

	@SelectProvider(type = CurrencyBulider.class, method = "findAllList")
	List<Currency> findAllList(Currency param);

    @SelectProvider(type = CurrencyBulider.class, method = "count")
	int count(Currency param);
	
	@SelectProvider(type = CurrencyBulider.class, method = "getById")
	Currency getById(@Param("id") Integer id);

	@SelectProvider(type = CurrencyBulider.class, method = "getByCurrencyCode")
	Currency getByCurrencyCode(@Param("currencyCode") String currencyCode);

	@Select("SELECT * FROM currency ")
	List<Currency> findAll();
}