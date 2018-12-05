package com.stosz.product.web;

import com.stosz.admin.ext.model.FsmHistory;
import com.stosz.fsm.FsmHistoryService;
import com.stosz.fsm.model.IFsmHistory;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台用户的Controller
 *
 * @author shiqiangguo
 * @version 1.0
 * @ClassName UserController
 */
@Controller
@RequestMapping(value = "/product/fsmHistory")
public class PcFsmHistoryController extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private FsmHistoryService pcFsmHistoryService = null;


    @RequestMapping("/find")
    @ResponseBody
    public RestResult search(@RequestParam(value = "fsmName", required = false) String fsmName,
                             @RequestParam(value = "objectId", required = false) Integer objectId,
                             @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                             @RequestParam(value = "limit", required = false, defaultValue = "20") Integer limit) {
        RestResult searchResult = pcFsmHistoryService.queryHistory(fsmName, objectId, start, limit);
        fillHistoryEnumDisplay((List<IFsmHistory>)(searchResult.getItem()));
        return searchResult;
    }

    private void fillHistoryEnumDisplay(List<IFsmHistory> lst) {
        if (lst == null || lst.isEmpty()) {
            return;
        }
        for (IFsmHistory fsmHistory : lst) {
            if (!(fsmHistory instanceof FsmHistory)) {
                continue;
            }
            FsmHistory h = (FsmHistory) fsmHistory;
            String fsmName = h.getFsmName();
            h.setEventNameDisplay(MBox.getDisplayByFsmEnum(h.getFsmName(), MBox.FSM_ENUM_TYPE_EVENT, h.getEventName()));
            h.setSrcStateDisplay(MBox.getDisplayByFsmEnum(h.getFsmName(), MBox.FSM_ENUM_TYPE_STATE, h.getSrcState()));
            h.setDstStateDisplay(MBox.getDisplayByFsmEnum(h.getFsmName(), MBox.FSM_ENUM_TYPE_STATE, h.getDstState()));
        }
    }

}
