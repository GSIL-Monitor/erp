package com.stosz.tms.mapper.builder;

import com.stosz.tms.dto.ShippingTrackingNoSectionAddDto;
import com.stosz.tms.dto.ShippingTrackingNumberListAddDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import com.stosz.plat.mapper.AbstractBuilder;
import com.stosz.tms.model.ShippingTrackingNoList;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShippingTrackingNoListBuilder extends AbstractBuilder<ShippingTrackingNoList> {

	@Override
	public void buildSelectOther(SQL sql) {

	}

	@Override
	public void buildJoin(SQL sql) {

	}

	@Override
	public void buildWhere(SQL sql, ShippingTrackingNoList param) {
		buildWhereBase(sql,param);
		eq(sql,"shipping_way_id","shippingWayId",param.getShippingWayId());
		eq(sql,"wms_id","wmsId",param.getWmsId());
		eq(sql,"track_status","trackStatus",param.getTrackStatus());
		in(sql,"shipping_way_id","shippingWayId",param.getShippingWayIdList());
	}


	public String sectionAdd(@Param("sectionAddDto")ShippingTrackingNoSectionAddDto sectionAddDto,@Param("trackNumbers")List<String> trackNumbers){
		SQL sql = new SQL();
		sql.INSERT_INTO(getTableName());
		sql.INTO_COLUMNS("shipping_way_id","track_number","track_status","product_type","wms_id","wms_name","creator","creator_id");

		StringBuilder sb = new StringBuilder(" values ");

		MessageFormat mf = new MessageFormat("("+sectionAddDto.getShippingWayId()+",\"{0}\",0,"+sectionAddDto.getProductType()+"," +
														""+sectionAddDto.getWmsId()+",\""+sectionAddDto.getWmsName()+"\"," +
														"\""+sectionAddDto.getCreator()+"\","+sectionAddDto.getCreatorId()+")");

		String startTrackingNo = sectionAddDto.getStartTrackingNo();
		String endTrackingNo = sectionAddDto.getEndTrackingNo();

		String prefix = "";

		int perfixLength = 0;

		int noLength = 0;

		Long startNo = null;
		Long endNo = null;

		Pattern p = Pattern.compile("[1-9]");

		Matcher startMatcher = p.matcher(startTrackingNo);
		Matcher endMatcher = p.matcher(endTrackingNo);

		Assert.isTrue(startMatcher.find(),"开始运单号格式错误，必须有数字");
		Assert.isTrue(endMatcher.find(),"结束运单号格式错误，必须有数字");


		Pattern pattern = Pattern.compile("([0-9])");

		//判断是否为数字
		if (!StringUtils.isNumeric(startTrackingNo)){

			noLength = startTrackingNo.length();


			Matcher startPrefixMatcher = pattern.matcher(startTrackingNo);
			Matcher endPrefixmatcher = pattern.matcher(endTrackingNo);

			Assert.isTrue(startPrefixMatcher.find(),"开始区间号中没有数字");
			Assert.isTrue(endPrefixmatcher.find(),"开始区间号中没有数字");


			prefix =  startTrackingNo.substring(0,startPrefixMatcher.start());
			String endPrefix =  endTrackingNo.substring(0,endPrefixmatcher.start());

			Assert.isTrue(prefix.equals(endPrefix),"开始区间号与结束区间号前缀不相同");

			perfixLength = prefix.length();

			startNo = Long.valueOf(startTrackingNo.substring(startMatcher.start()));
			endNo =  Long.valueOf(endTrackingNo.substring(endMatcher.start()));
		}else{
			//判断字符串是否为0开头
			if (startTrackingNo.startsWith("0")){
				noLength = startTrackingNo.length();
				startNo = Long.valueOf(startTrackingNo.substring(startMatcher.start()));
			}else{
				startNo = Long.valueOf(startTrackingNo);
			}
			if (endTrackingNo.startsWith("0")){
				endNo =  Long.valueOf(endTrackingNo.substring(endMatcher.start()));
			}else{
				endNo = Long.valueOf(endTrackingNo);
			}
		}

		Assert.isTrue(endNo > startNo,"结束运单号必须比开始运单号大");

		boolean dataCheck  = false;

		for ( ; startNo <= endNo; startNo++){
			StringBuilder trackingNo = new StringBuilder(prefix);

			for(int i = 0 ,zeroLength =  noLength - perfixLength - String.valueOf(startNo).length();i <zeroLength;i++){
				trackingNo.append("0");
			}
			trackingNo.append(startNo);
			if (trackNumbers.contains(trackingNo.toString()))
				continue;
			dataCheck = true;
			sb.append(mf.format(new Object[]{trackingNo.toString()}));
			if (startNo < endNo ) {
				sb.append(",");
			}
		}
		Assert.isTrue(dataCheck,"进行添加物流线路的运单号都已存在");

		return sql.toString()+sb.toString();
	}


	public String numberListAdd(@Param("numberListAddDto")ShippingTrackingNumberListAddDto numberListAddDto){
		SQL sql = new SQL();
		sql.INSERT_INTO(getTableName());
		sql.INTO_COLUMNS("shipping_way_id","track_number","track_status","product_type","wms_id","wms_name","creator","creator_id");

		StringBuilder sb = new StringBuilder(" values ");

		MessageFormat mf = new MessageFormat("("+numberListAddDto.getShippingWayId()+",\"{0}\",0,"+numberListAddDto.getProductType()+"," +
				""+numberListAddDto.getWmsId()+",\""+numberListAddDto.getWmsName()+"\"," +
				"\""+numberListAddDto.getCreator()+"\","+numberListAddDto.getCreatorId()+")");

		final List<String> trackNumberList = numberListAddDto.getTrackNumberList();

		trackNumberList.forEach(e -> {
			sb.append(mf.format(new Object[]{e}));
			sb.append(",");
		});

		return sql.toString()+sb.substring(0,sb.length()-1);
	}

}
