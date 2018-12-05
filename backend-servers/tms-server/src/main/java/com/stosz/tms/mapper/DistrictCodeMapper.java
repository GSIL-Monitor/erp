package com.stosz.tms.mapper;

import com.stosz.tms.mapper.builder.DistrictCodeBuilder;
import com.stosz.tms.mapper.builder.ShippingWayBuilder;
import com.stosz.tms.mapper.builder.ShippingZoneStoreRelBuilder;
import com.stosz.tms.model.DistrictCode;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.model.ShippingZoneStoreRel;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictCodeMapper {

    @SelectProvider(type = DistrictCodeBuilder.class, method = "count")
    public int count(DistrictCode districtCode);

    @SelectProvider(type = DistrictCodeBuilder.class, method = "find")
    public List<DistrictCode> queryList(DistrictCode districtCode);


    @InsertProvider(type = DistrictCodeBuilder.class, method = "batchInsertData")
    public int batchInsert(List<DistrictCode> list);

    @UpdateProvider(type=DistrictCodeBuilder.class,method="updateSelective")
    public int update(DistrictCode districtCode);
}
