package com.stosz.order.ext.model;

import com.stosz.plat.enums.InterfaceNameEnum;
import com.stosz.plat.enums.ResponseResultEnum;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

/**
 * Created by carter on 2018-01-17. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 数据库:orders下的表orders_web_interface_record_detail对应的实体类
 */
public class OrdersWebInterfaceRecordDetail  extends AbstractParamEntity implements ITable {

	/**主键ID*/
	@DBColumn
	private	Integer	id	=	new Integer(0);
	/**任务id，record表的id*/
	@DBColumn
	private	Integer	recordId	=	new Integer(0);
	/**接口类型 新增销售订单 取消销售订单 出库通知 其它*/
	@DBColumn
	private InterfaceNameEnum interfaceNameEnum	=	null;
	/**业务对象的主键ID,这里是订单*/
	@DBColumn
	private	Integer	objectId	=	new Integer(0);
	/**请求接口地址*/
	@DBColumn
	private	String	requestUrl	=	"";
	/**请求的json数据*/
	@DBColumn
	private	String	requestParam	=	"";
	/**推送时间*/
	@DBColumn
	private	java.time.LocalDateTime	requestTime	=	java.time.LocalDateTime.now();
	/**返回状态码*/
	@DBColumn
	private	String	responseStatusCode	=	"";
	/**返回内容*/
	@DBColumn
	private	String	responseDesc	=	"";
	/**返回内容时间*/
	@DBColumn
	private	java.time.LocalDateTime	responseTime	=	java.time.LocalDateTime.now();
	/**推送结果 -1 失败  0待发送   1成功*/
	@DBColumn
	private ResponseResultEnum responseResultEnum	=	null;
	/**创建时间*/
	@DBColumn
	private	java.time.LocalDateTime	createAt	=	java.time.LocalDateTime.now();

	public OrdersWebInterfaceRecordDetail(){}

	public void setId(Integer	id){
		this.id = id;
	}

	public Integer getId(){
		return id.intValue();
	}

	public void setRecordId(Integer	recordId){
		this.recordId = recordId;
	}

	public Integer getRecordId(){
		return recordId;
	}

	public void setInterfaceNameEnum(InterfaceNameEnum interfaceNameEnum){
		this.interfaceNameEnum = interfaceNameEnum;
	}

	public InterfaceNameEnum getInterfaceNameEnum(){
		return interfaceNameEnum;
	}

	public void setObjectId(Integer	objectId){
		this.objectId = objectId;
	}

	public Integer getObjectId(){
		return objectId;
	}

	public void setRequestUrl(String	requestUrl){
		this.requestUrl = requestUrl;
	}

	public String getRequestUrl(){
		return requestUrl;
	}

	public void setRequestParam(String	requestParam){
		this.requestParam = requestParam;
	}

	public String getRequestParam(){
		return requestParam;
	}

	public void setRequestTime(java.time.LocalDateTime	requestTime){
		this.requestTime = requestTime;
	}

	public java.time.LocalDateTime getRequestTime(){
		return requestTime;
	}

	public void setResponseStatusCode(String	responseStatusCode){
		this.responseStatusCode = responseStatusCode;
	}

	public String getResponseStatusCode(){
		return responseStatusCode;
	}

	public void setResponseDesc(String	responseDesc){
		this.responseDesc = responseDesc;
	}

	public String getResponseDesc(){
		return responseDesc;
	}

	public void setResponseTime(java.time.LocalDateTime	responseTime){
		this.responseTime = responseTime;
	}

	public java.time.LocalDateTime getResponseTime(){
		return responseTime;
	}

	public void setResponseResultEnum(ResponseResultEnum responseResultEnum){
		this.responseResultEnum = responseResultEnum;
	}

	public ResponseResultEnum getResponseResultEnum(){
		return responseResultEnum;
	}

	public void setCreateAt(java.time.LocalDateTime	createAt){
		this.createAt = createAt;
	}

	public java.time.LocalDateTime getCreateAt(){
		return createAt;
	}

	@Override
	public String getTable() {
		return "orders_web_interface_record_detail";
	}

}