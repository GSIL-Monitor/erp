package com.stosz.store.mapper;

import com.stosz.store.ext.model.PlanRecord;
import com.stosz.store.mapper.builder.PlanRecordBuilder;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/24 17:04
 * @Update Time:
 */
@Repository
public interface PlanRecordMapper {

    //-----------------------------------基础数据增删改查------------------------------------//
    @InsertProvider(type = PlanRecordBuilder.class, method = "insert")
    int insert(PlanRecord planRecord);

    @DeleteProvider(type = PlanRecordBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = PlanRecordBuilder.class, method = "update")
    int update(PlanRecord planRecord);

    @SelectProvider(type = PlanRecordBuilder.class, method = "getById")
    PlanRecord getById(Integer id);

    @SelectProvider(type = PlanRecordBuilder.class, method = "find")
    PlanRecord find(PlanRecord planRecord);

    @SelectProvider(type = PlanRecordBuilder.class, method = "query")
    List<PlanRecord> query(PlanRecord planRecord);
}
