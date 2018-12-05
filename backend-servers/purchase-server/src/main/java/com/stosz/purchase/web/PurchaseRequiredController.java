package com.stosz.purchase.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.admin.ext.model.Department;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.web.AbstractController;
import com.stosz.purchase.ext.common.PurchaseRequiredDto;
import com.stosz.purchase.ext.common.PurchaseRequiredItemDto;
import com.stosz.purchase.ext.model.*;
import com.stosz.purchase.service.PurchaseRequiredService;
import com.stosz.purchase.service.SkuSupplierService;
import com.stosz.purchase.service.SupplierService;
import com.stosz.purchase.service.UserAuthorityService;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author feiheping
 * @version [1.0, 2017年11月9日]
 */
@RequestMapping("/purchase/manage/required")
@Controller
public class PurchaseRequiredController extends AbstractController {

    @Resource
    private PurchaseRequiredService purchaseRequiredService;

    @Resource
    private SkuSupplierService skuSupplierService;
    @Resource
    private UserAuthorityService userAuthorityService;
    @Resource
    private SupplierService supplierService;

    /**
     * 获取采购需求列表
     *
     * @param purchaseRequired 查询条件
     * @return 查询结果
     */
    @RequestMapping("/queryList")
    @ResponseBody
    public RestResult queryList(PurchaseRequired purchaseRequired) {
        RestResult restResult = new RestResult();
        if (purchaseRequired.getBuDeptId() == null) {
            Integer deptId = getFirstDeptId();
            if(deptId == null) {
                restResult.setCode(RestResult.FAIL);
                restResult.setDesc("您当前没有分配到事业部，请先联系管理员给您分配事业部！");
                return restResult;
            }
            purchaseRequired.setBuDeptId(deptId);
        }
        List<PurchaseRequiredSpu> userProductRelList = purchaseRequiredService.find(purchaseRequired);
        restResult.setItem(userProductRelList);

        return restResult;
    }

    @RequestMapping("/countList")
    @ResponseBody
    public RestResult countList(PurchaseRequired purchaseRequired) {
        RestResult restResult = new RestResult();
        if (purchaseRequired.getBuDeptId() == null) {
            Integer deptId = getFirstDeptId();
            if(deptId == null) {
                restResult.setCode(RestResult.FAIL);
                restResult.setDesc("您当前没有分配到事业部，请先联系管理员给您分配事业部！");
                return restResult;
            }
            purchaseRequired.setBuDeptId(deptId);
        }
        int count = purchaseRequiredService.count(purchaseRequired);
        restResult.setTotal(count);
        return restResult;
    }





    /**
     * 修改采购需求为延迟采购
     *
     * @param buDeptId 事业部id
     * @param spu      产品spu
     * @param sku      产品sku
     */
    @RequestMapping(method = RequestMethod.POST, value = "/updateDelayPurchase")
    @ResponseBody
    public RestResult updateDelayPurchase(Integer buDeptId, String spu, String sku) {
        Assert.notNull(buDeptId, "事业部ID不能为空");
        Assert.hasText(spu, "SPU不能为空");
        Assert.hasText(sku, "SKU不能为空");
        RestResult restResult = new RestResult();
        purchaseRequiredService.updateDelayPurcharse(buDeptId, spu, sku);
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("修改成功");
        return restResult;
    }

    /**
     * 修改计划采购数
     *
     * @param id  id
     * @param qty 计划采购数
     */
    @RequestMapping(method = RequestMethod.POST, value = "updatePlanQty")
    @ResponseBody
    public RestResult updatePlanQty(Integer id, Integer qty) {
        RestResult restResult = new RestResult();
        Assert.notNull(id, "采购需求ID不能为空");
        Assert.notNull(qty, "计划采购数不能为空");
        Assert.isTrue(qty >= 0, "计划采购数不能小于0");
        Assert.isTrue(String.valueOf(qty).length() <= 6, "计划采购数不能超过6位数");
        purchaseRequiredService.updatePlanQty(id, qty);
        restResult.setCode(RestResult.OK);
        restResult.setDesc("修改成功");
        return restResult;
    }

    /**
     * 修改供应商
     *
     * @param purchaseRequired 需求的实体
     */
    @RequestMapping(method = RequestMethod.POST, value = "updateSupplier")
    @ResponseBody
    public RestResult updateSupplier(PurchaseRequired purchaseRequired) {
        RestResult restResult = new RestResult();
        Assert.notNull(purchaseRequired.getBuDeptId(), "事业部ID不能为空");
        Assert.notNull(purchaseRequired.getSku(), "SKU不能为空");
        Assert.notNull(purchaseRequired.getSupplierId(), "供应商ID不能为空");
        purchaseRequiredService.updateSupplier(purchaseRequired);
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("修改成功");
        return restResult;
    }

    /**
     * 根据SKU查询供应商
     *
     * @param sku 产品sku
     */
    @RequestMapping(method = RequestMethod.POST, value = "/querySupplierBySku")
    @ResponseBody
    public RestResult querySupplierBySku(String sku) {
        Assert.notNull(sku, "SKU不能为空");
        RestResult restResult = new RestResult();
        List<SkuPurchaseInfo> purchaseInfos = skuSupplierService.querySupplierBySku(sku);
        restResult.setDesc("success");
        restResult.setItem(purchaseInfos);
        return restResult;
    }

    /**
     * 新增供应商
     */
    @RequestMapping("/addSupplier")
    @ResponseBody
    public RestResult addSupplier(Integer supplierId,String sku, String supplierName, String purchaseUrl) {
        Assert.notNull(sku, "SKU不能为空");
        Assert.notNull(supplierName, "供应商名不能为空");
        Assert.notNull(purchaseUrl, "采购连接不能为空");
        Assert.isTrue(supplierName.length() <= 50, "供应商名称长度不能超过50位");
        Assert.isTrue(purchaseUrl.length() <= 100, "采购连接长度不能超过100位");
        Assert.isTrue(purchaseUrl.contains("."), "采购连接必须包含(.)");

        RestResult restResult = new RestResult();
        Integer creatorId = MBox.getLoginUserId();
        SkuPurchaseInfo skuPurchaseInfo = new SkuPurchaseInfo();
        skuPurchaseInfo.setCreatorId(creatorId);
        skuPurchaseInfo.setCreator(MBox.getLoginUserName());
        skuPurchaseInfo.setSku(sku);
        skuPurchaseInfo.setSupplierName(supplierName);
        skuPurchaseInfo.setPurchaseUrl(purchaseUrl);
        skuPurchaseInfo.setSupplierId(supplierId);
        skuSupplierService.addSupplier(skuPurchaseInfo);
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("添加成功");
        return restResult;
    }

    /**
     * 删除供应商
     */
    @RequestMapping("/deleteSupplier")
    @ResponseBody
    public RestResult deleteSupplier(Integer id){
        RestResult result = new RestResult();
        skuSupplierService.delSkuPurchaseInfo(id);
        result.setCode(RestResult.NOTICE);
        result.setDesc("删除成功！");
        return result;
    }

    /**
     * 修改采购员
     *
     * @param spu      产品spu
     * @param buDeptId 事业部id
     */
    @RequestMapping("/updateBuyer")
    @ResponseBody
    public RestResult updateBuyer(String spu, Integer buDeptId, Integer buyerId) {
        Assert.hasText(spu, "SPU不能为空");
        Assert.notNull(buDeptId, "事业部ID不能为空");
        Assert.notNull(buyerId, "采购员用户ID不能为空");

        RestResult restResult = new RestResult();
        UserProductRel userProductRel = new UserProductRel();
        userProductRel.setSpu(spu);
        userProductRel.setBuDeptId(buDeptId);
        userProductRel.setUserId(buyerId);
        userProductRel.setCreator(MBox.getLoginUserName());
        userProductRel.setCreatorId(MBox.getLoginUserId());
        purchaseRequiredService.updateRequiredByuer(userProductRel);

        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("设置成功");
        return restResult;
    }

    @RequestMapping("/createPurchase")
    @ResponseBody
    public RestResult createPurchase(String purchaseRequiredDtoStr) {
        ObjectMapper objectMapper = new ObjectMapper();
        PurchaseRequiredDto purchaseRequiredDto = null;
        try {
            purchaseRequiredDto = objectMapper.readValue(purchaseRequiredDtoStr,PurchaseRequiredDto.class);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        List<PurchaseRequiredItemDto> itemList = purchaseRequiredDto.getRequiredItemDtos().stream().filter(e->e.getPlanQty() > 0).collect(Collectors.toList());
        purchaseRequiredDto.setRequiredItemDtos(itemList);
        Assert.notNull(itemList, "采购需求明细不能为空");
        Assert.notNull(purchaseRequiredDto.getTotalAmount(),"采购总价不允许为空！");
        Assert.notNull(purchaseRequiredDto.getBuDeptId(), "事业部ID不能为空");
        Assert.notNull(purchaseRequiredDto.getBuyerId(), "采购员ID不能为空");
        Assert.notNull(purchaseRequiredDto.getSpu(), "SPU不能为空");
        Assert.notNull(purchaseRequiredDto.getSupplierId(), "供应商ID不能为空");
        BigDecimal pageTotalAmount = purchaseRequiredDto.getTotalAmount();
        for (PurchaseRequiredItemDto itemDto : itemList) {
            Assert.notNull(itemDto.getPlanQty(),"计划采购数量不能小于0");
            Assert.isTrue(itemDto.getPlanQty() >= 0, "计划采购数量不能小于0");
            if (pageTotalAmount != null && pageTotalAmount.compareTo(new BigDecimal(0)) == 1) {
                Assert.notNull(itemDto.getPurchasePrice(), "明细采购单价不能为空");
                Assert.isTrue(itemDto.getPurchasePrice().compareTo(new BigDecimal(0)) == 1, "明细采购单价必须大于0");
            }
        }
        if (pageTotalAmount != null && pageTotalAmount.compareTo(new BigDecimal(0)) == 1) {
            Integer totalPlanQty = itemList.stream().mapToInt(PurchaseRequiredItemDto::getPlanQty).sum();
            BigDecimal price = itemList.get(0).getPurchasePrice();

            BigDecimal totalAmout = price.multiply(new BigDecimal(totalPlanQty));
            Assert.isTrue(Math.abs(pageTotalAmount.doubleValue() - totalAmout.doubleValue()) < 1, "单价*数量不等于总价，请检查！");
        }
        RestResult restResult = new RestResult();
        purchaseRequiredDto.setCreator(MBox.getLoginUserName());
        purchaseRequiredDto.setCreatorId(MBox.getLoginUserId());
        // 将采购员设置为当前用户
        purchaseRequiredDto.setBuyer(MBox.getLoginUserName());
        purchaseRequiredDto.setBuyerId(MBox.getLoginUserId());

        String purchaseNo = purchaseRequiredService.createPurchase(purchaseRequiredDto);
        restResult.setItem(purchaseNo);
        restResult.setCode(RestResult.NOTICE);
        return restResult;
    }

    /**
     * 获取当前用户有权限查看的第一个事业部ID
     * @return 事业部id
     */
    private Integer getFirstDeptId(){
        List<Department> buyerBuDeptList = userAuthorityService.findBuDeptList();
        if (CollectionUtils.isNotNullAndEmpty(buyerBuDeptList)) {
            Department department = buyerBuDeptList.get(0);
            return department.getId();
        }
        return null;
    }

    @ResponseBody
    @RequestMapping("/findSupplierBySearch")
    public RestResult findBySearch(String search){
        RestResult result = new RestResult();
        List<Supplier> supplierList = supplierService.findBySearch(search);
        result.setItem(supplierList);
        return  result;
    }

    /**
     * 根据spu查询库存信息
     * @param purchaseRequired
     * @return
     */
    @ResponseBody
    @RequestMapping("/findStockBySpu")
    public RestResult findStockBySpu(PurchaseRequired purchaseRequired){
        Assert.notNull(purchaseRequired.getSpu(), "spu不能为空");

        RestResult result = new RestResult();
        StockInfo stockInfo = purchaseRequiredService.findStock(purchaseRequired,"spu");
        result.setItem(stockInfo);
        return result;
    }

    @ResponseBody
    @RequestMapping("/findStockBySku")
    public RestResult findStockBySku(PurchaseRequired purchaseRequired){
        Assert.notNull(purchaseRequired.getSku(), "sku不能为空");

        RestResult result = new RestResult();
        StockInfo stockInfo = purchaseRequiredService.findStock(purchaseRequired,"sku");
        result.setItem(stockInfo);
        return result;
    }

}
