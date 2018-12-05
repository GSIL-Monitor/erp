package com.stosz.fsm;

import com.stosz.fsm.history.IFsmHistoryDao;
import com.stosz.fsm.model.EventModel;
import com.stosz.fsm.model.IFsmHistory;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 状态机历史管理模块Service
 *
 * @author shrek
 * @version 1.0
 * @ClassName FsmHistoryService
 */
@Transactional
public class FsmHistoryService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private IFsmHistoryDao fsmHistoryDao;

    public void setFsmHistoryDao(IFsmHistoryDao fsmHistoryDao) {
        this.fsmHistoryDao = fsmHistoryDao;
    }

    protected int deleteHistory(String fsmName, Integer instanceId) {
        return fsmHistoryDao.deleteByNameAndId(fsmName, instanceId);
    }

    /**
     * 增加状态机历史记录
     *
     * @param
     * @param event
     * @return Integer    fsmHistoryId
     * @throws
     */
    protected Integer add(IFsmInstance fsmInstance, EventModel event, String optUid, LocalDateTime optTime, String description) {

        Assert.notNull(fsmInstance, "添加状态机历史时检测到状态机实体为空fsmInstance");
        logger.debug("增加状态机历史Entity开始, fsmInstance={}, state:{}, event:{} Entity:{}", fsmInstance.getId(), fsmInstance.getState(), event.getEventName(), fsmInstance.getClass().getName());

        Integer id = fsmHistoryDao.insert(event.getFsmName(), fsmInstance.getId(), fsmInstance.getParentId(), event.getEventName(), event.getSrcState(), event.getDstState(), optUid, description, null);
        logger.info("保存状态机历史Entity成功 history id, id={} entityid:{} srcState:{} event:{} uid:{} entity:{} ",
                id, fsmInstance.getId(), event.getSrcState(), event.getEventName(), fsmInstance.getClass().getName());
        return id;
    }

//	private String desc(IFsmInstance a){
//		return String.format("FsmInstance-%s[ id=%s, parentId=%s, state=%s stateTime=%s", 
//				a.getClass().getSimpleName(),a.getId() , a.getParentId(), a.getState(), a.getStateTime());
//	}

    /**
     * 增加状态机历史记录
     *
     * @param fsmInstance
     * @param description
     * @return Integer    fsmHistoryId
     * @throws
     */
    protected Integer add(IFsmInstance fsmInstance, String description) {

        EventModel em = new EventModel();
        em.setSrcState("start");
        em.setEventName("start");
        em.setDstState("start");
        return this.add(fsmInstance, em, MBox.getLoginid(), LocalDateTime.now(), description);
    }


    public List<IFsmHistory> queryHistory(String fsmName, Integer instanceId) {
        List<IFsmHistory> lst = fsmHistoryDao.queryByNameAndId(fsmName, instanceId, null, null);
        return lst;
    }

    public RestResult queryHistory(String fsmName, Integer instanceId,
                                   Integer start, Integer limit) {
        List<IFsmHistory> lst = fsmHistoryDao.queryByNameAndId(fsmName, instanceId, start, limit);
        int count = fsmHistoryDao.countByNameAndId(fsmName, instanceId);
        RestResult pr = new RestResult();
        pr.setItem(lst);
        pr.setTotal(count);
        return pr;
    }

    public IFsmHistory queryHistory(String fsmName, Integer instanceId, String sdtState) {
        return fsmHistoryDao.queryByDstState(fsmName, instanceId, sdtState);
    }


}
