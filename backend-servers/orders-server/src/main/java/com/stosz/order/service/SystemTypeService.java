package com.stosz.order.service;

import com.google.common.base.Strings;
import com.stosz.order.ext.model.SystemType;
import com.stosz.order.mapper.order.SystemTypeMapper;
import com.stosz.plat.common.MBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @auther carter
 * create time    2017-10-31
 *
 * 服务类
 *
 */
@Service
public class SystemTypeService {


    @Autowired
    private SystemTypeMapper systemTypeMapper;


    public SystemType insert(SystemType record)
    {
        record.setCreatorId(MBox.getLoginUserId());
        Assert.isTrue(!Strings.isNullOrEmpty(record.getTypeKey()),"类型key不能为空");
        Assert.isTrue(!Strings.isNullOrEmpty(record.getTypeValue()),"类型key不能为空");
        Assert.isTrue(!Strings.isNullOrEmpty(record.getTypeDesc()),"类型key不能为空");

        record.setCreatorId(MBox.getLoginUserId());
        systemTypeMapper.insert(record);
        return record;
    }

    public SystemType findById(Long id)
    {
        Assert.isTrue(null!=id && id.longValue() > 0,"请输入一个合法的id");
        return systemTypeMapper.findById(id);
    }

    public List<SystemType> findByParam(SystemType record)
    {
        List<SystemType> systemTypeList = systemTypeMapper.findByParam(record);
        return systemTypeList;
    }


    public boolean  update(SystemType record)
    {

        record.setCreatorId(MBox.getLoginUserId());
        Assert.isTrue(null!=record.getId() && record.getId().longValue()> 0, "id must be long ！");
        Assert.isTrue(null!=record.getCreatorId() && record.getCreatorId().intValue()> 0, "creatorId must be int ！");
         SystemType record_update = findById(Long.valueOf(record.getId()));

        if(null ==record.getParentId())  record_update.setParentId(record.getParentId());

         if(!Strings.isNullOrEmpty(record.getTypeKey())) record_update.setTypeKey(record.getTypeKey());
        if(!Strings.isNullOrEmpty(record.getTypeValue()))record_update.setTypeValue(record.getTypeValue());
        if(!Strings.isNullOrEmpty(record.getTypeDesc())) record_update.setTypeDesc(record.getTypeDesc());

        record_update.setCreatorId(record.getCreatorId());

        return systemTypeMapper.update(record_update)> 0;
    }


    public boolean  delete(Long id)
    {
        Assert.isTrue(null!=id && id.longValue() > 0,"请输入一个合法的id");
        return  systemTypeMapper.delete(id)> 0;
    }


    public Integer findCountByParam(SystemType systemType) {
        return systemTypeMapper.findCountByParam(systemType);
    }
}
