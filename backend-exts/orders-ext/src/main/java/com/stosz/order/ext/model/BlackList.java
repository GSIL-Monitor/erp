package com.stosz.order.ext.model;

import com.stosz.order.ext.enums.BlackLevelEnum;
import com.stosz.order.ext.enums.BlackTypeEnum;
import com.stosz.plat.model.AbstractParamEntity;
import com.stosz.plat.model.DBColumn;
import com.stosz.plat.model.ITable;

import java.io.Serializable;

/**
 * Created by carter on 2017-11-14. Copyright © 2016 －2017 深圳布谷鸟信息技术有限公司
 * 黑名单实体类
 */
public class BlackList  extends AbstractParamEntity implements ITable, Serializable {

    /**自增主键*/
    @DBColumn
    private	Long	id	=	new Long(0);
    /**等级 警告，严重*/
    @DBColumn
    private BlackLevelEnum blackLevelEnum	=	BlackLevelEnum.other;
    /**类型 ip 邮箱地址 地址等*/
    @DBColumn
    private	BlackTypeEnum	blackTypeEnum	=	BlackTypeEnum.other;
    /**内容*/
    @DBColumn
    private	String	content	=	"";
    /**创建时间*/
    @DBColumn
    private	java.time.LocalDateTime	createAt	=	null;
    /**更新时间*/
    @DBColumn
    private	java.time.LocalDateTime	updateAt	=	null;
    /**创建者id*/
    @DBColumn
    private	Integer	creatorId	=	new Integer(0);
    /**创建人*/
    @DBColumn
    private	String	creator	=	"";



    public BlackList(){}

    public void setId(Integer	id){
        this.id = Long.valueOf(id);
    }

    public Integer getId(){
        return id.intValue();
    }


    public void setContent(String	content){
        this.content = content;
    }

    public String getContent(){
        return content;
    }

    public void setCreateAt(java.time.LocalDateTime	createAt){
        this.createAt = createAt;
    }

    public java.time.LocalDateTime getCreateAt(){
        return createAt;
    }

    public void setUpdateAt(java.time.LocalDateTime	updateAt){
        this.updateAt = updateAt;
    }

    public java.time.LocalDateTime getUpdateAt(){
        return updateAt;
    }

    public void setCreatorId(Integer	creatorId){
        this.creatorId = creatorId;
    }

    public Integer getCreatorId(){
        return creatorId;
    }

    public void setCreator(String	creator){
        this.creator = creator;
    }

    public String getCreator(){
        return creator;
    }


    public BlackLevelEnum getBlackLevelEnum() {
        return blackLevelEnum;
    }

    public void setBlackLevelEnum(BlackLevelEnum blackLevelEnum) {
        this.blackLevelEnum = blackLevelEnum;
    }

    public BlackTypeEnum getBlackTypeEnum() {
        return blackTypeEnum;
    }

    public void setBlackTypeEnum(BlackTypeEnum blackTypeEnum) {
        this.blackTypeEnum = blackTypeEnum;
    }

    @Override
    public String getTable() {
        return "black_list";
    }

}
