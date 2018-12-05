package com.stosz.purchase.mapper;

import com.stosz.purchase.ext.model.Supplier;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SupplierMapper {

	@InsertProvider(type = SupplierBuilder.class, method = "insert")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	public int add(Supplier supplier);

	@DeleteProvider(type = SupplierBuilder.class, method = "delete")
	public int del(Integer id);

	@UpdateProvider(type = SupplierBuilder.class, method = "update")
	int update(Supplier supplier);

	@Select("SELECT * FROM supplier WHERE NAME=#{name} limit 1")
	public Supplier getByName(String name);

	@Select("<script>SELECT id,NAME FROM supplier WHERE id in "
			+ "<foreach item=\"item\" index=\"index\" collection=\"idList\" open=\"(\" separator=\",\" close=\")\">"
			+"#{item}"
			+ "</foreach> ORDER BY update_at DESC LIMIT 1 </script>")
	public Supplier getLastSupplierByIds(@Param("idList") Set<Integer> idList);
	
	
	@SelectProvider(type=SupplierBuilder.class,method="getById")
	public Supplier getById(Integer id);

	@Select("select * from supplier where lower(name) like lower(concat('%',#{search},'%'))")
	List<Supplier> findBySearch(@Param("search") String search);

}
