package com.stosz.tms.mapper;

import com.stosz.tms.mapper.builder.CustomsCodeBuilder;
import com.stosz.tms.mapper.builder.DistrictCodeBuilder;
import com.stosz.tms.model.CustomsCode;
import com.stosz.tms.model.DistrictCode;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomsCodeMapper {


    @SelectProvider(type = CustomsCodeBuilder.class, method = "count")
    public int count(CustomsCode customsCode);

    @SelectProvider(type = CustomsCodeBuilder.class, method = "find")
    public List<CustomsCode> queryList(CustomsCode customsCode);

    @InsertProvider(type = CustomsCodeBuilder.class, method = "batchInsertData")
    public int batchInsert(List<CustomsCode> list);

    @UpdateProvider(type=CustomsCodeBuilder.class,method="updateSelective")
    public int update(CustomsCode customsCode);
}
