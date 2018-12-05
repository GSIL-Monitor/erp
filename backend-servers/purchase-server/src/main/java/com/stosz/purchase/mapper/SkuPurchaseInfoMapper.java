package com.stosz.purchase.mapper;

import com.stosz.purchase.ext.model.SkuPurchaseInfo;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SkuPurchaseInfoMapper {

	@Select("SELECT s.*,p.name supplierName FROM sku_purchase_info s LEFT JOIN supplier p ON s.supplier_id=p.id WHERE sku=#{sku} order by last_purchase_at desc")
	public List<SkuPurchaseInfo> getListBySku(String sku);

	@InsertProvider(type = SkuPurchaseInfoBuilder.class, method = "insert")
	public int add(SkuPurchaseInfo skuPurchaseInfo);

	@DeleteProvider(type = SkuPurchaseInfoBuilder.class, method = "delete")
	public int del(Integer id);
	
	@Select("SELECT s.*,p.name supplierName FROM sku_purchase_info s LEFT JOIN supplier p ON s.supplier_id=p.id WHERE sku=#{sku} order by last_purchase_at desc limit 1")
	public SkuPurchaseInfo getLastBySku(String sku);

	@Select("<script>SELECT sp.*,p.name supplierName FROM sku_purchase_info sp LEFT JOIN  supplier p ON sp.supplier_id=p.id WHERE (sku) in"
	        +"<foreach item=\"item\" index=\"index\" collection=\"set\" open=\"(\" separator=\",\" close=\")\">"
            +"'${item}'"
            + "</foreach></script>")
    public List<SkuPurchaseInfo> querySkuSupplier(@Param("set") Set<String> set);

	@DeleteProvider(type = SkuPurchaseInfoBuilder.class, method = "delSkuPurchaseInfoByPurchaseId")
	void delSkuPurchaseInfoByPurchaseId(@Param("supplierId") Integer supplierId, @Param("set") Set<String> set);
}
