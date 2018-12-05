package com.stosz.plat.mapper;

import com.stosz.fsm.history.IFsmHistoryDao;
import com.stosz.plat.model.FsmHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IFsmHistoryMapper extends IFsmHistoryDao<FsmHistory>{

    //=================      公共方法 开始       ===================
    FsmHistory getById(@Param("id") Integer id);

    Integer insert(@Param("fsmName")String fsmName,@Param("objectId")Integer objectId,@Param("parentId")Integer parentId,@Param("eventName")String eventName,@Param("srcState")String srcState,@Param("dstState")String dstState,@Param("optUid")String optUid,@Param("memo")String memo,@Param("id")Integer id);

    Integer update(FsmHistory history);

    Integer delete(@Param("id") Integer id);

    int deleteByNameAndId(String fsmName, Integer instanceId);


    //=================      公共方法 结束       ===================

    List<FsmHistory> find(FsmHistory param);

    Integer count(FsmHistory FsmHistory);

    List<FsmHistory> queryByNameAndId(@Param("fsmName") String fsmName,
                                      @Param("objectId") Integer objectId, @Param("offset") Integer offset, @Param("limit") Integer limit);

    int countByNameAndId(@Param("fsmName") String fsmName, @Param("objectId") Integer objectId);
}
