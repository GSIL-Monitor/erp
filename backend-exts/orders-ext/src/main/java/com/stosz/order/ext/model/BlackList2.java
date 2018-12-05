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
public class BlackList2 extends AbstractParamEntity implements ITable, Serializable {

    /**自增主键*/
    @DBColumn
    private	Long	id	=	new Long(0);
    /**等级 警告，严重*/
    @DBColumn
    private BlackLevelEnum blackLevelEnum	=	BlackLevelEnum.other;
    /**类型 ip 邮箱地址 地址等*/
    @DBColumn
    private BlackTypeEnum blackTypeEnum = BlackTypeEnum.ip;
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



    public BlackList2(){}

    public void setId(Integer	id){
        this.id = Long.valueOf(id);
    }

    public Integer getId(){
        return id.intValue();
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


    @Override
    public String getTable() {
        return "black_list";
    }


}

//package com.stosz.order.ext.model;
//
//import com.stosz.plat.model.AbstractParamEntity;
//import com.stosz.plat.model.DBColumn;
//import com.stosz.plat.model.ITable;
//
//import java.io.Serializable;
//import java.time.LocalDateTime;
//import java.util.Date;
//
///**
// * @author wangqian
// * 黑名单
// */
//public class BlackList extends AbstractParamEntity implements ITable, Serializable {
//
//    @DBColumn
//    private Integer id;
//
//    /**
//     * 等级
//     */
//    @DBColumn
//    private String blackLevelKey;
//
//    /**
//     * 类型 ip 邮箱地址 地址等
//     */
//    @DBColumn
//    private String blackTypeKey;
//
//    /**
//     * 内容
//     */
//    @DBColumn
//    private String content;
//
//    /**
//     * 创建时间
//     */
//    @DBColumn
//    private LocalDateTime createAt;
//
//    /**
//     * 创建者id
//     */
//    @DBColumn
//    private Integer creatorId;
//
//    /**
//     * 创建者
//     */
//    @DBColumn
//    private String creator;
//
//    private static final long serialVersionUID = 1L;
//
//    @Override
//    public Integer getId() {
//        return this.id;
//    }
//
//    @Override
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getBlackLevelKey() {
//        return blackLevelKey;
//    }
//
//    public void setBlackLevelKey(String blackLevelKey) {
//        this.blackLevelKey = blackLevelKey;
//    }
//
//    public String getBlackTypeKey() {
//        return blackTypeKey;
//    }
//
//    public void setBlackTypeKey(String blackTypeKey) {
//        this.blackTypeKey = blackTypeKey;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//    public LocalDateTime getCreateAt() {
//        return createAt;
//    }
//
//    public void setCreateAt(LocalDateTime createAt) {
//        this.createAt = createAt;
//    }
//
//    @Override
//    public Integer getCreatorId() {
//        return creatorId;
//    }
//
//    @Override
//    public void setCreatorId(Integer creatorId) {
//        this.creatorId = creatorId;
//    }
//
//    public String getCreator() {
//        return creator;
//    }
//
//    public void setCreator(String creator) {
//        this.creator = creator;
//    }
//
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(getClass().getSimpleName());
//        sb.append(" [");
//        sb.append("Hash = ").append(hashCode());
//        sb.append(", id=").append(id);
//        sb.append(", blackLevelKey=").append(blackLevelKey);
//        sb.append(", blackTypeKey=").append(blackTypeKey);
//        sb.append(", content=").append(content);
//        sb.append(", createAt=").append(createAt);
//        sb.append(", creatorId=").append(creatorId);
//        sb.append(", creator=").append(creator);
//        sb.append(", serialVersionUID=").append(serialVersionUID);
//        sb.append("]");
//        return sb.toString();
//    }
//
//    @Override
//    public String getTable() {
//        return "black_list";
//    }
//}