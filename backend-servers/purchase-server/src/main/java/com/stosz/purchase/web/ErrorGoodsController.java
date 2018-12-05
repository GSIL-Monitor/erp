package com.stosz.purchase.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.StringUtils;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.constant.MenuKeyword;
import com.stosz.product.ext.enums.TypeEnum;
import com.stosz.product.ext.model.Partner;
import com.stosz.product.ext.service.IPartnerService;
import com.stosz.purchase.ext.common.PurchaseDto;
import com.stosz.purchase.ext.common.PurchaseSkuDto;
import com.stosz.purchase.ext.enums.ErrorGoodsEvent;
import com.stosz.purchase.ext.enums.PurchaseEvent;
import com.stosz.purchase.ext.enums.PurchaseState;
import com.stosz.purchase.ext.enums.ShippingTypeEnum;
import com.stosz.purchase.ext.model.ErrorGoods;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.ext.model.PurchaseTrackingNo;
import com.stosz.purchase.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/13]
 */
@Controller
@RequestMapping("/purchase/manage/errorGoods")
public class ErrorGoodsController extends AbstractController {

    @Resource
    private ErrorGoodsService errorGoodsService;
    @Resource
    private PurchaseService purchaseService;
    @Resource
    private PurchaseItemService purchaseItemService;

    @RequestMapping("/find")
    @ResponseBody
    public RestResult find(ErrorGoods errorGoods) {
        RestResult result = new RestResult();
        List<ErrorGoods> errorGoodsList = errorGoodsService.find(errorGoods);
        result.setItem(errorGoodsList);
        Integer count = errorGoodsService.count(errorGoods);
        result.setTotal(count);
        return result;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public RestResult add(String errorGoodsStr) {
        RestResult result = new RestResult();
        ObjectMapper objectMapper = new ObjectMapper();
        ErrorGoods errorGoods = new ErrorGoods();
        try {
            errorGoods = objectMapper.readValue(errorGoodsStr,ErrorGoods.class);
        } catch (IOException e) {
            logger.error("传入参数不合法！",e);
            throw new RuntimeException("传入参数不合法！");
        }
        errorGoodsService.insert(errorGoods);
        result.setCode(RestResult.NOTICE);
        result.setDesc("添加成功！");
        return result;
    }

    @RequestMapping(value = "/cancel",method = RequestMethod.POST)
    @ResponseBody
    public RestResult cancel(Integer id,String memo){
        RestResult result = new RestResult();
        Assert.notNull(id,"要取消的错货单不允许为空！");
        ErrorGoods errorGoods = errorGoodsService.getById(id);
        Assert.notNull(errorGoods,"要取消的错货单不存在！");
        errorGoodsService.processEvent(errorGoods,ErrorGoodsEvent.giveUp,memo, MBox.getSysUser());
        result.setCode(RestResult.NOTICE);
        result.setDesc("取消成功！");
        return result;
    }

    @RequestMapping(value = "/getById",method = RequestMethod.POST)
    @ResponseBody
    public RestResult getById(Integer id){
        RestResult result = new RestResult();
        ErrorGoods errorGoods = errorGoodsService.getItemById(id);
        result.setItem(errorGoods);
        return result;
    }

    /**
     * 状态机历史记录
     * @param id
     * @return
     */
    @RequestMapping("/queryFsmHistory")
    @ResponseBody
    public RestResult queryFsmHistory(@RequestParam("errorGoodsId") Integer id,
                                      @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                      @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        return errorGoodsService.queryHistory(id,start,limit);
    }


    @RequestMapping(value = "/getPurchaseDtoByNo", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getPurchaseDtoByNo(String purchaseNo) {
        RestResult result = new RestResult();
        Assert.hasLength(purchaseNo, "传入的采购单NO不允许为空！");
        PurchaseDto purchaseDto = purchaseService.getPurchaseDtoByNo(purchaseNo);
        Assert.isTrue(PurchaseState.executing.name().equals(purchaseDto.getState()),"只有执行中的采购单才能生成错货单！");
        List<PurchaseSkuDto> purchaseSkuDtoList = purchaseDto.getPurchaseSkuDtoList();
        Assert.isTrue(CollectionUtils.isNotNullAndEmpty(purchaseSkuDtoList)&&purchaseSkuDtoList.size()>1,"该采购单只有单个sku，无法生成错货单！");
        result.setItem(purchaseDto);
        result.setCode(RestResult.OK);
        return result;
    }



}  
