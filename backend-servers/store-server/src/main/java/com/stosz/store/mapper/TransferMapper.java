package com.stosz.store.mapper;

import com.stosz.fsm.IFsmDao;
import com.stosz.store.ext.dto.request.SearchTransferReq;
import com.stosz.store.ext.model.Transfer;
import com.stosz.store.mapper.builder.TransferBuilder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:TransferMapper
 * @Created Time:2017/11/28 16:53
 * @Update Time:2017/11/28 16:53
 */
@Repository
public interface TransferMapper extends IFsmDao<Transfer> {

    //-----------------------------------基础数据增删改查------------------------------------//
    @InsertProvider(type = TransferBuilder.class, method = "insert")
    int insert(Transfer transfer);

    @DeleteProvider(type = TransferBuilder.class, method = "delete")
    int delete(Integer id);

    @UpdateProvider(type = TransferBuilder.class, method = "update")
    int update(Transfer transfer);

    @SelectProvider(type = TransferBuilder.class, method = "getById")
    Transfer getById(Integer id);

    @UpdateProvider(type = TransferBuilder.class, method = "updateState")
    Integer updateState(@Param("id") Integer id, @Param("state") String state, @Param("stateTime") LocalDateTime stateTime);

    @SelectProvider(type = TransferBuilder.class, method = "countSearch")
    int count(SearchTransferReq req);

    @SelectProvider(type = TransferBuilder.class, method = "findPage")
    List<Transfer> findPage(SearchTransferReq req);

    @SelectProvider(type = TransferBuilder.class, method = "findList")
    List<Transfer> findList(SearchTransferReq req);

    @Select("select * from transfer where id=" +
            "(select tran_id from transfer_item where tracking_no=#{trackingNoOld} order by create_at desc limit 1)")
    Transfer findByTrack(String trackingNoOld);

    //----------------------------------自定义业务逻辑------------------------------------//

}
