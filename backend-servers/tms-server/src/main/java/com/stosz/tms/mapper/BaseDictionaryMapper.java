package com.stosz.tms.mapper;

import com.stosz.tms.mapper.builder.BaseDictionaryBuilder;
import com.stosz.tms.mapper.builder.ShippingStoreRelationBuilder;
import com.stosz.tms.model.BaseDictionary;
import com.stosz.tms.model.ShippingStoreRel;
import org.apache.ibatis.annotations.SelectProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BaseDictionaryMapper {

    @SelectProvider(type = BaseDictionaryBuilder.class,method = "find")
    public List<BaseDictionary> select(BaseDictionary shippingStoreRel);

}
