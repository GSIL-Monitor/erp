package com.stosz.product.mapper;

import com.stosz.plat.mapper.FsmHistoryBuilder;
import com.stosz.plat.model.FsmHistory;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("pcFsmHistoryMapper")
public interface FsmHistoryMapper extends com.stosz.plat.mapper.IFsmHistoryMapper {

    //=================      公共方法 开始       ===================
    @SelectProvider(type = FsmHistoryBuilder.class, method = "getById")
    FsmHistory getById(@Param("id") Integer id);

    @InsertProvider(type = FsmHistoryBuilder.class, method = "createHistory")
    Integer insert(@Param("fsmName") String fsmName, @Param("objectId") Integer objectId, @Param("parentId") Integer parentId, @Param("eventName") String eventName, @Param("srcState") String srcState, @Param("dstState") String dstState, @Param("optUid") String optUid, @Param("memo") String memo, @Param("id") Integer id);

    @UpdateProvider(type = FsmHistoryBuilder.class, method = "update")
    Integer update(FsmHistory history);

    @DeleteProvider(type = FsmHistoryBuilder.class, method = "delete")
    Integer delete(@Param("id") Integer id);

    @Delete("delete from fsm_history where fsm_name=#{fsmName} and object_id=#{instanceId}")
    int deleteByNameAndId(@Param("fsmName") String fsmName, @Param("instanceId") Integer instanceId);

    //=================      公共方法 结束       ===================

    //    @SelectProvider(type = FsmHistoryBuilder.class, method = "search")
    @Select("select * from fsm_history")
    List<FsmHistory> find(FsmHistory param);

    @Select("select count(1) from fsm_history")
    Integer count(FsmHistory iFsmHistory);

    @Select("<script>" +
            "select _this.* from fsm_history _this where _this.fsm_name=#{fsmName} and _this.object_id=#{objectId} " +
            " order by _this.id desc " +
//            " <if test.py=\"offset!=null\"> limit ${offset} , ${limit}</if>" +
//            " <if test.py=\"offset==null\"> limit ${limit}</if>" +

            "</script>"
    )
    List<FsmHistory> queryByNameAndId(@Param("fsmName") String fsmName,
                                      @Param("objectId") Integer objectId, @Param("offset") Integer offset, @Param("limit") Integer limit);

    @Select("select count(1) from fsm_history where fsm_name=#{fsmName} and object_id=#{objectId}")
    int countByNameAndId(@Param("fsmName") String fsmName, @Param("objectId") Integer objectId);

    /**
     * 查询状态机经过某一事件跳转到另一状态的操作记录
     * 需要时间,操作人(loginId),事件,状态等参数
     */
    @Select("SELECT * FROM " +
            "(SELECT * FROM fsm_history WHERE dst_state = #{dstState} AND create_at BETWEEN #{minCreateAt} AND #{maxCreateAt} AND opt_uid = #{optUid} " +
            "UNION " +
            "SELECT * FROM fsm_history WHERE event_name = #{eventName} AND create_at BETWEEN #{minCreateAt} AND #{maxCreateAt} ) a " +
            "GROUP BY object_id HAVING COUNT(object_id) > 1 ")
    List<FsmHistory> findFsmHistory(FsmHistory param);

}
