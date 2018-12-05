package com.stosz.purchase.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.StringUtils;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.constant.MenuKeyword;
import com.stosz.product.ext.enums.TypeEnum;
import com.stosz.product.ext.model.Partner;
import com.stosz.product.ext.service.IPartnerService;
import com.stosz.purchase.ext.common.PurchaseDto;
import com.stosz.purchase.ext.enums.PurchaseEvent;
import com.stosz.purchase.ext.enums.ShippingTypeEnum;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.ext.model.PurchaseTrackingNo;
import com.stosz.purchase.ext.model.SkuPurchaseInfo;
import com.stosz.purchase.service.PurchaseService;
import com.stosz.purchase.service.PurchaseTrackingNoRelService;
import com.stosz.purchase.service.PurchaseTrackingNoService;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/13]
 */
@Controller
@RequestMapping("/purchase/manage/purchase")
public class PurchaseController extends AbstractController {

    @Resource
    private PurchaseService purchaseService;
    @Resource
    private PurchaseTrackingNoService purchaseTrackingNoService;
    @Resource
    private PurchaseTrackingNoRelService purchaseTrackingNoRelService;
    @Resource
    private IPartnerService iPartnerService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("/purchase/manage/purchase/list");
        model.addObject("keyword", MenuKeyword.PURCHASE_MANAGE_PURCHASE_LIST);
        return model;
    }

    @RequestMapping("/find")
    @ResponseBody
    public RestResult find(Purchase purchase) {
        RestResult result = new RestResult();
        //如果未填运单号为true并且运单号条件为空，那么只搜索未填运单号的
        if (purchase.getHasTracking() && StringUtils.isBlank(purchase.getTrackingNo())) {
            purchase.setTrackingNo("");
        } else if (!purchase.getHasTracking() && StringUtils.isBlank(purchase.getTrackingNo())) {
            //如果未填运单号为false并且运单号为空，那么搜索全部，即不管运单号这个搜素条件
            purchase.setTrackingNo(null);
        }
        List<Purchase> purchaseList = purchaseService.find(purchase);
        result.setItem(purchaseList);
        Integer count = purchaseService.count(purchase);
        result.setTotal(count);
        return result;
    }

    @RequestMapping(value = "/processEvent", method = RequestMethod.POST)
    @ResponseBody
    public RestResult processEvent(@RequestParam Integer id,
                                   @RequestParam PurchaseEvent purchaseEvent,
                                   @RequestParam String memo) {
        RestResult result = new RestResult();
        Purchase purchase = purchaseService.getById(id);
        if (PurchaseEvent.submit.equals(purchaseEvent)) {
            Assert.notNull(purchase.getSupplierId(), "供应商不允许为空！");
            Assert.notNull(purchase.getAmount(), "采购总价不允许为空！");
            Assert.notNull(purchase.getPlatOrdersNo(), "渠道订单号不允许为空！");
            Assert.hasLength(purchase.getBuyerAccount(), "买手账号不允许为空！");
        } else if (PurchaseEvent.pay.equals(purchaseEvent)) {
            purchase.setFactAmount(purchase.getAmount());
            Assert.notNull(purchase.getFactAmount(), "实付总金额不允许为空！");
        }
        Assert.notNull(purchase, "采购单id【" + id + "】在数据库中没有找到！");
        purchaseService.processEvent(purchase, purchaseEvent, memo, null);
        result.setCode(RestResult.NOTICE);
        result.setDesc("修改采购单【" + id + "】的状态成功！");
        return result;
    }

    @RequestMapping(value = "/findPartnerList", method = RequestMethod.POST)
    @ResponseBody
    public RestResult findPartnerList() {
        RestResult result = new RestResult();
        List<Partner> partners = iPartnerService.findPartnerByType(TypeEnum.purchase);
        result.setItem(partners);
        return result;
    }

    @RequestMapping(value = "/updatePurchaseDto", method = RequestMethod.POST)
    @ResponseBody
    public RestResult updatePurchaseDto(String purchaseDtoStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        PurchaseDto purchaseDto = null;
        try {
            purchaseDto = objectMapper.readValue(purchaseDtoStr, PurchaseDto.class);
        } catch (IOException e) {
            throw new RuntimeException("数据转化失败！", e);
        }
        Assert.isTrue(purchaseDto.getMemo().length()<=64,"备注过长，请修改！！！");
        RestResult result = new RestResult();
        Assert.notNull(purchaseDto, "传入的数据不允许为空！");
        purchaseService.updateDto(purchaseDto);
        result.setCode(RestResult.NOTICE);
        result.setDesc("修改成功！");
        return result;
    }

    @RequestMapping(value = "/getPurchaseDtoById", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getPurchaseDtoById(Integer purchaseId) {
        RestResult result = new RestResult();
        Assert.notNull(purchaseId, "传入的采购单id不允许为空！");
        PurchaseDto purchaseDto = purchaseService.getPurchaseDtoById(purchaseId);
        result.setItem(purchaseDto);
        result.setCode(RestResult.OK);
        return result;
    }

    @RequestMapping(value = "/getPurchaseDtoByNo", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getPurchaseDtoByNo(String purchaseNo) {
        RestResult result = new RestResult();
        Assert.hasLength(purchaseNo, "传入的采购单NO不允许为空！");
        PurchaseDto purchaseDto = purchaseService.getPurchaseDtoByNo(purchaseNo);
        result.setItem(purchaseDto);
        result.setCode(RestResult.OK);
        return result;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public RestResult delete(Integer id) {
        RestResult result = new RestResult();
        Assert.notNull(id, "传入的采购单id不允许为空！");
        purchaseService.delete(id);
        result.setCode(RestResult.NOTICE);
        result.setDesc("删除采购单【" + id + "】成功！");
        return result;
    }

    @RequestMapping(value = "/updateTrackingNo", method = RequestMethod.POST)
    @ResponseBody
    public RestResult updateTrackingNo(PurchaseTrackingNo purchaseTrackingNo) {
        RestResult result = new RestResult();
        Assert.notNull(purchaseTrackingNo, "要修改的采购运单号不允许为空！");
        purchaseTrackingNoService.update(purchaseTrackingNo);
        result.setCode(RestResult.NOTICE);
        result.setDesc("修改采购运单号成功！");
        return result;
    }

    @RequestMapping(value = "/updateShippingEnum", method = RequestMethod.POST)
    @ResponseBody
    public RestResult updateShippingEnum(PurchaseTrackingNo purchaseTrackingNo) {
        RestResult result = new RestResult();
        Assert.notNull(purchaseTrackingNo, "要修改的采购运单号不允许为空！");
        if(purchaseTrackingNo.getShippingId() != null){
            Partner partner = iPartnerService.getById(purchaseTrackingNo.getShippingId());
            purchaseTrackingNo.setShippingName(partner.getName());
            purchaseTrackingNo.setShippingCode(partner.getNo());
        }
        purchaseTrackingNoService.updateSelect(purchaseTrackingNo);
        result.setCode(RestResult.NOTICE);
        result.setDesc("修改采购运单号成功！");
        return result;
    }

    @RequestMapping(value = "/deleteTrackingNoRel", method = RequestMethod.POST)
    @ResponseBody
    public RestResult deleteTrackingNoRel(PurchaseTrackingNo purchaseTrackingNo) {
        RestResult result = new RestResult();
        Assert.notNull(purchaseTrackingNo, "要删除的采购运单号不允许为空！");
        purchaseTrackingNoRelService.deleteByUnique(purchaseTrackingNo);
        result.setDesc("删除成功！");
        result.setCode(RestResult.NOTICE);
        return result;
    }

    @RequestMapping(value = "/addTrackingNoRel", method = RequestMethod.POST)
    @ResponseBody
    public RestResult addTrackingNoRel(PurchaseTrackingNo purchaseTrackingNo) {
        RestResult result = new RestResult();
        if (ShippingTypeEnum.toPay.equals(purchaseTrackingNo.getShippingTypeEnum())) {
            Assert.notNull(purchaseTrackingNo.getShippingId(), "到付的物流承运商不允许为空！");
            Assert.notNull(purchaseTrackingNo.getShippingAmount(), "到付的运费不允许为空！");
            Partner partner = iPartnerService.getById(purchaseTrackingNo.getShippingId());
            Assert.notNull(partner, "找不到对应的物流承运商！");
            purchaseTrackingNo.setShippingCode(partner.getNo());
            purchaseTrackingNo.setShippingName(partner.getName());
        }
        Assert.notNull(purchaseTrackingNo, "要新增的采购运单号不允许为空！");
        purchaseTrackingNoRelService.insertByTrackingNo(purchaseTrackingNo);
        result.setDesc("添加成功！");
        result.setCode(RestResult.NOTICE);
        return result;
    }

    @RequestMapping(value = "/getTrackingNo", method = RequestMethod.GET)
    @ResponseBody
    public RestResult getTrackingNo(String purchaseNo) {
        RestResult result = new RestResult();
        long i = System.currentTimeMillis();
        List<PurchaseTrackingNo> purchaseTrackingNoList = purchaseTrackingNoService.findByPurchaseNo(purchaseNo);
        long j = System.currentTimeMillis();
        List<Partner> partnerList = iPartnerService.findPartnerByType(TypeEnum.purchase);
        long k = System.currentTimeMillis();
        logger.info("时间1："+(j-i));
        logger.info("时间2："+(k-j));
        for (PurchaseTrackingNo p : purchaseTrackingNoList
                ) {
            p.setPartnerList(partnerList);
        }
        result.setItem(purchaseTrackingNoList);
        return result;
    }

    @RequestMapping(value = "/getTrackingNoByNo",method = RequestMethod.POST)
    @ResponseBody
    public RestResult getTrackingNoByNo(String trackingNo){
        RestResult result = new RestResult();
        PurchaseTrackingNo purchaseTrackingNo = purchaseTrackingNoService.getByTrackingNo(trackingNo);
        result.setItem(purchaseTrackingNo);
        return result;
    }

    @RequestMapping(value = "/querySupplierByPurchaseId",method = RequestMethod.POST)
    @ResponseBody
    public RestResult querySupplierByPurchaseId(Integer id){
        RestResult result = new RestResult();
        List<SkuPurchaseInfo>  supplierList = purchaseService.querySupplierByPurchaseId(id);
        result.setItem(supplierList);
        return result;
    }


    @RequestMapping(value = "/deleteSkuSupplier",method = RequestMethod.POST)
    @ResponseBody
    public RestResult deleteSkuSupplier(Integer id,Integer supplierId){
        RestResult result = new RestResult();
        purchaseService.deleteSkuSupplier(id,supplierId);
        result.setCode(RestResult.NOTICE);
        result.setDesc("删除成功!");
        return result;
    }

    @RequestMapping(value = "/addSupplier",method = RequestMethod.POST)
    @ResponseBody
    public RestResult addSkuSupplier(Integer id,Integer supplierId,String supplierName){
        RestResult result = new RestResult();
        purchaseService.addSkuSupplier(id,supplierId,supplierName);
        result.setDesc("新增成功！");
        result.setCode(RestResult.NOTICE);
        return result;
    }

    @RequestMapping(value = "/updateSupplier",method = RequestMethod.POST)
    @ResponseBody
    public RestResult updateSupplier(Integer id,Integer supplierId){
        RestResult result = new RestResult();
        purchaseService.updateSupplier(id,supplierId);
        result.setDesc("修改成功！");
        result.setCode(RestResult.NOTICE);
        return result;
    }

    /**
     * 状态机历史记录
     * @param id
     * @return
     */
    @RequestMapping("/queryFsmHistory")
    @ResponseBody
    public RestResult queryFsmHistory(@RequestParam("purchaseId") Integer id,
                                      @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                      @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        return purchaseService.queryHistory(id,start,limit);
    }


}  
