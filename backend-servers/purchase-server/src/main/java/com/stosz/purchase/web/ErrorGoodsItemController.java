package com.stosz.purchase.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.web.AbstractController;
import com.stosz.purchase.ext.enums.ErrorGoodsItemEvent;
import com.stosz.purchase.ext.model.ErrorGoodsItem;
import com.stosz.purchase.service.ErrorGoodsItemService;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/13]
 */
@Controller
@RequestMapping("/purchase/manage/errorGoodsItem")
public class ErrorGoodsItemController extends AbstractController {

    @Resource
    private ErrorGoodsItemService errorGoodsItemService;

    @RequestMapping("/find")
    @ResponseBody
    public RestResult find(ErrorGoodsItem errorGoodsItem) {
        RestResult result = new RestResult();
        List<ErrorGoodsItem> errorGoodsItemList = errorGoodsItemService.find(errorGoodsItem);
        result.setItem(errorGoodsItemList);
        Integer count = errorGoodsItemService.count(errorGoodsItem);
        result.setTotal(count);
        return result;
    }


    @RequestMapping(value = "/processEventByIdStr", method = RequestMethod.POST)
    @ResponseBody
    public RestResult processEventByIdStr(String idStr, ErrorGoodsItemEvent errorGoodsItemEvent, String memo) {
        RestResult result = new RestResult();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> ids = null;
        try {
            ids = objectMapper.readValue(idStr, new TypeReference<List<Integer>>() {
            });
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        Assert.isTrue(CollectionUtils.isNotNullAndEmpty(ids), "要操作的错货明细不允许为空！");
        for (Integer id : ids) {
            errorGoodsItemService.processEvent(id, errorGoodsItemEvent, memo, MBox.getSysUser());
        }
        result.setCode(RestResult.NOTICE);
        result.setDesc("操作成功！");
        return result;
    }


    @RequestMapping(value = "/processEvent", method = RequestMethod.POST)
    @ResponseBody
    public RestResult processEvent(Integer id, ErrorGoodsItemEvent errorGoodsItemEvent, String memo) {
        RestResult result = new RestResult();
        errorGoodsItemService.processEvent(id, errorGoodsItemEvent, memo, MBox.getSysUser());
        result.setCode(RestResult.NOTICE);
        result.setDesc("操作成功！");
        return result;
    }

    /**
     * 状态机历史记录
     * @param id
     * @return
     */
    @RequestMapping("/queryFsmHistory")
    @ResponseBody
    public RestResult queryFsmHistory(@RequestParam("errorGoodsItemId") Integer id,
                                      @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                      @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        return errorGoodsItemService.queryHistory(id,start,limit);
    }

}  
