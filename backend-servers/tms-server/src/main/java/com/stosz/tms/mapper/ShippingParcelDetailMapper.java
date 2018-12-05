package com.stosz.tms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import com.stosz.tms.mapper.builder.ShippingParcelDetailBuilder;
import com.stosz.tms.model.ShippingParcelDetail;

@Repository
public interface ShippingParcelDetailMapper {

	@SelectProvider(type = ShippingParcelDetailBuilder.class, method = "count")
	public int count(ShippingParcelDetail shippingParcelDetail);

	@SelectProvider(type = ShippingParcelDetailBuilder.class, method = "find")
	public List<ShippingParcelDetail> queryList(ShippingParcelDetail shippingParcelDetail);

	@Insert("<script>INSERT INTO shipping_parcel_detail(parcel_id,order_item_id,sku,product_title,product_title_en,order_detail_qty,price,total_amount,discount_amount,real_amount"
			+ ",inlandHsCode,foreignHsCode,create_at,update_at) values "
			+ "<foreach item=\"item\" collection=\"list\" separator=\",\" >"
			+ "(#{item.parcelId},#{item.orderItemId},#{item.sku},#{item.productTitle},#{item.productTitleEn},#{item.orderDetailQty},#{item.price},#{item.totalAmount},"
			+ "#{item.discountAmount},#{item.realAmount},#{item.inlandhscode},#{item.foreignhscode},now(),now())" + "</foreach></script>")
	public int addList(List<ShippingParcelDetail> parcelDetails);

	@Select("select * from shipping_parcel_detail where parcel_id=#{parcelId}")
	public List<ShippingParcelDetail> queryParcelDetail(Integer parcelId);

	@Select("<script>select * from shipping_parcel_detail where parcel_id in "
			+ "<foreach item=\"item\" collection=\"parcelIds\" open=\"(\" separator=\",\" close=\")\">${item}</foreach></script>")
	public List<ShippingParcelDetail> queryListByParcelIds(@Param("parcelIds")List<Integer> parcelIds);
}
