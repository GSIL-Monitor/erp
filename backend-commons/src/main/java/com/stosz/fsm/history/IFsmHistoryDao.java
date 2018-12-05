package com.stosz.fsm.history;

import com.stosz.fsm.model.IFsmHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 状态机历史管理模块Dao
 *
 * @author shiqiangguo
 * @version 1.0
 * @ClassName FsmHistoryDao
 */
public interface IFsmHistoryDao<T extends IFsmHistory> {


    Integer insert(@Param("fsmName") String fsmName, @Param("objectId") Integer objectId, @Param("parentId") Integer parentId, @Param("eventName") String eventName, @Param("srcState") String srcState, @Param("dstState") String dstState, @Param("optUid") String optUid, @Param("memo") String memo, @Param("id") Integer id);

//    public T createHistory();

    List<T> queryByNameAndId(String fsmName, Integer instanceId, Integer offset, Integer limit);

    T queryByDstState(@Param("fsmName") String fsmName, @Param("objectId") Integer objectId, @Param("dstState") String dstState);

    int countByNameAndId(String fsmName, Integer instanceId);

    int deleteByNameAndId(String fsmName, Integer instanceId);


}
