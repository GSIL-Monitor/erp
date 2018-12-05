package com.stosz.purchase.web;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.web.AbstractController;
import com.stosz.purchase.ext.enums.PurchaseItemEvent;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.service.PurchaseItemService;
import com.stosz.purchase.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/15]
 */
@Controller
@RequestMapping("/purchase/manage/purchaseItem")
public class PurchaseItemController extends AbstractController {

    @Resource
    private PurchaseItemService purchaseItemService;

    @Autowired
    private PurchaseService purchaseService;

    @RequestMapping(value = "/processEvent", method = RequestMethod.POST)
    @ResponseBody
    public RestResult processEvent(@RequestParam Integer id, @RequestParam PurchaseItemEvent purchaseItemEvent,
                                   @RequestParam(required = false, defaultValue = "") String memo) {
        RestResult result = new RestResult();
        PurchaseItem purchaseItem = purchaseItemService.getById(id);
        Assert.notNull(purchaseItem, "采购明细id【" + id + "】在数据库中找不到对应的记录！");
        purchaseItemService.processEvent(purchaseItem, purchaseItemEvent, memo, null);
        result.setCode(RestResult.NOTICE);
        result.setDesc("修改采购明细的状态成功！");
        return result;
    }


    @RequestMapping(value = "/processEventByIdStr", method = RequestMethod.POST)
    @ResponseBody
    public RestResult processEventByIdStr(String idStr, @RequestParam PurchaseItemEvent purchaseItemEvent,
                                   @RequestParam(required = false, defaultValue = "") String memo) {
        RestResult result = new RestResult();
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> list = new ArrayList<>();
        try {
             list = objectMapper.readValue(idStr, new TypeReference<List<Integer>>(){});
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        for(Integer id :list){
            PurchaseItem purchaseItem = purchaseItemService.getById(id);
            Assert.notNull(purchaseItem, "采购明细id【" + id + "】在数据库中找不到对应的记录！");
            purchaseItemService.processEvent(purchaseItem, purchaseItemEvent, memo, null);
        }
        result.setCode(RestResult.NOTICE);
        result.setDesc("修改采购明细状态成功！");
        return result;
    }


    @RequestMapping(value = "findList", method = RequestMethod.GET)
    @ResponseBody
    public RestResult findList(PurchaseItem purchaseItem) {
        RestResult restResult = new RestResult();
        int count = purchaseItemService.findPurchaseItemCount(purchaseItem);
        restResult.setTotal(count);
        if (count == 0) {
            restResult.setCode(RestResult.OK);
            return restResult;
        }
        List<PurchaseItem> purchaseItemList = purchaseItemService.findPurchaseItem(purchaseItem);
        restResult.setItem(purchaseItemList);
        return restResult;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public RestResult delete(Integer id) {
        RestResult result = new RestResult();
        Assert.notNull(id, "传入的采购明细id不允许为空！");
        PurchaseItem purchaseItem = purchaseItemService.getById(id);
        Purchase purchase = purchaseService.getById(purchaseItem.getPurchaseId());
        List<PurchaseItem> purchaseItemList = purchaseItemService.findByPurchaseId(purchase.getId());
        Assert.isTrue(CollectionUtils.isNotNullAndEmpty(purchaseItemList)&& purchaseItemList.size()>1,"一个采购单至少要有一条明细，如要删除采购单，请在采购单页面删除！");
        purchaseItemService.delete(id);
        result.setCode(RestResult.NOTICE);
        result.setDesc("删除采购明细【" + id + "】成功！");
        return result;
    }


    /**
     * 状态机历史记录
     * @param id
     * @return
     */
    @RequestMapping("/queryFsmHistory")
    @ResponseBody
    public RestResult queryFsmHistory(@RequestParam("purchaseItemId") Integer id,
                                      @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                      @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        return purchaseItemService.queryHistory(id,start,limit);
    }
}
