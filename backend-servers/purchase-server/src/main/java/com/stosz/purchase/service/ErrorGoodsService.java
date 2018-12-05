package com.stosz.purchase.service;

import com.stosz.admin.ext.model.Department;
import com.stosz.fsm.FsmHistoryService;
import com.stosz.fsm.FsmProxyService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.service.IProductService;
import com.stosz.product.ext.service.IProductSkuService;
import com.stosz.purchase.ext.common.PurchaseDto;
import com.stosz.purchase.ext.common.PurchaseSkuDto;
import com.stosz.purchase.ext.enums.*;
import com.stosz.purchase.ext.model.ErrorGoods;
import com.stosz.purchase.ext.model.ErrorGoodsItem;
import com.stosz.purchase.ext.model.Purchase;
import com.stosz.purchase.ext.model.PurchaseItem;
import com.stosz.purchase.mapper.ErrorGoodsMapper;
import com.stosz.purchase.mapper.PurchaseMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 错货单的service
 * @author xiongchenyang
 * @version [1.0 , 2017/11/11]
 */
@Service
public class ErrorGoodsService {

    @Resource
    private ErrorGoodsMapper mapper;
    @Resource
    private PurchaseItemService purchaseItemService;
    @Resource
    private ErrorGoodsItemService errorGoodsItemService;
    @Resource
    private UserAuthorityService userAuthorityService;
    @Resource
    private FsmProxyService<ErrorGoods> errorGoodsFsmProxyService;
    @Resource
    private IProductSkuService iProductSkuService;
    @Resource
    private PurchaseService purchaseService;
    @Resource
    private FsmHistoryService fsmHistoryService;


    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 新增错货单
     * @param errorGoods 错货单
     */
    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insert(ErrorGoods errorGoods){
        Assert.notNull(errorGoods,"要添加的错货单不允许为空！");
        // 判断错货数>0,判断原sku，新sku不允许为空，判断原采购单号不允许为空
        // 获取每一个错货单明细中,原sku和实际到货sku的采购单价是否相等，如果有不等的，那么直接抛错 入参List<sku>,返回map<sku,price>
        // 算每条明细的错货总价。得到总的错货数量和错货总价。
        // 插入错货单，获得错货单编号，然后插入每条错货明细
        String originalPurchaseNo = errorGoods.getOriginalPurchaseNo();
        Assert.hasLength(originalPurchaseNo,"原采购单号不允许为空！");
        Integer countUnCompleted = mapper.countByOriginalPurchaseNo(originalPurchaseNo);
        Purchase purchase = purchaseService.getByPurchaseNo(originalPurchaseNo);
        Assert.isTrue( countUnCompleted == 0,"该原采购单还有未完结的错货单，不允许重复添加！");
        List<ErrorGoodsItem> errorGoodsItemList = errorGoods.getErrorGoodsItemList();
        Assert.notNull(errorGoodsItemList,"错货单明细不允许为空！");
        Assert.notEmpty(errorGoodsItemList,"错货单明细不允许为空");
        Integer originalPurchaseId = purchase.getId();
        Map<String,BigDecimal> priceMap = purchaseItemService.findPrice(originalPurchaseId);
        Integer totalQuantity = 0;
        BigDecimal totalAmount = BigDecimal.valueOf(0.00);
        for(ErrorGoodsItem errorGoodsItem : errorGoodsItemList){
            String originalSku = errorGoodsItem.getOriginalSku().trim();
            String realSku = errorGoodsItem.getRealSku().trim();
            Assert.hasLength(originalSku,"原始sku不允许为空！");
            Assert.hasLength(realSku,"实际到货sku不允许为空！");
            Assert.isTrue(!originalSku.equals(realSku),"原始sku与实际到货sku不能相同！");
            BigDecimal originalPrice = priceMap.get(originalSku);
            BigDecimal realPrice = priceMap.get(realSku);
            Assert.isTrue((originalPrice != null && realPrice != null && originalPrice.equals(realPrice))," "+originalSku+"与"+realSku+"采购单价不相等，或该采购单中没有"+realSku+",不允许生成错货单！");
            Integer quantity = errorGoodsItem.getQuantity();
            Assert.isTrue(quantity!=null && quantity>0,"原始sku"+originalSku+"错货数不允许为空！");
            BigDecimal amount = realPrice.multiply(BigDecimal.valueOf(quantity));
            totalAmount = totalAmount.add(amount) ;
            totalQuantity += quantity;
            String realSkuTitle = iProductSkuService.getAttrValueTitleBySku(realSku);
            errorGoodsItem.setRealSkuTitle(realSkuTitle);
            errorGoodsItem.setAmount(amount);
        }
        errorGoods.setState(ErrorGoodsState.waitBusinessApprove.name());
        errorGoods.setCreator(MBox.getLoginUserName());
        errorGoods.setCreatorId(MBox.getLoginUserId());
        errorGoods.setQuantity(totalQuantity);
        errorGoods.setAmount(totalAmount);
        errorGoods.setState(ErrorGoodsState.start.name());
        int i = mapper.insert(errorGoods);
        errorGoods.setNo(errorGoods.getId()+"");
        errorGoodsFsmProxyService.processNew(errorGoods, errorGoods.getMemo());
        errorGoodsFsmProxyService.processEvent(errorGoods, ErrorGoodsEvent.create, errorGoods.getMemo());
        update(errorGoods);
        Assert.isTrue(i==1,"添加错货单失败！");
        for (ErrorGoodsItem result: errorGoodsItemList) {
            result.setErrorId(errorGoods.getId());
            result.setErrorNo(errorGoods.getNo());
            result.setCreator(MBox.getLoginUserName());
            result.setCreatorId(MBox.getLoginUserId());
            result.setState(ErrorGoodsItemState.start.name());
            errorGoodsItemService.insert(result);
        }
        logger.info("添加错货单{}成功！",errorGoods);
    }


    public void update(ErrorGoods errorGoods){
        Assert.notNull(errorGoods, "要修改的错货单不允许为空！");
        Integer id = errorGoods.getId();
        Assert.notNull(id, "要修改的错货单id不允许为空！");
        ErrorGoods errorGoodsDB = mapper.getById(id);
        Assert.notNull(errorGoodsDB,"要修改的错货单在数据库中不存在！");
        int i = mapper.update(errorGoods);
        Assert.isTrue(i==1,"修改错货单失败！");
        logger.info("将错货单{}修改为{}成功！",errorGoodsDB,errorGoods);
    }

    public void delete(Integer id){
        Assert.notNull(id,"要删除的错货单不存在！");
        ErrorGoods errorGoodsDB = mapper.getById(id);
        Assert.notNull(errorGoodsDB,"要删除的错货单在数据库中不存在！");
        int i = mapper.delete(id);
        //TODO 删除错货单明细
        Assert.isTrue(i==1,"删除错货单失败！");
        logger.info("将错货单{}删除成功！",errorGoodsDB);
    }

    public ErrorGoods getById(Integer id ){
        Assert.notNull(id,"要查询的错货单id不允许为空！");
        return mapper.getById(id);
    }

    public ErrorGoods getItemById(Integer id){
        ErrorGoods errorGoods = getById(id);
        List<ErrorGoodsItem> errorGoodsItemList = errorGoodsItemService.findByErrorId(id);
        Assert.isTrue(CollectionUtils.isNotNullAndEmpty(errorGoodsItemList),"要查询的错货单没有明细！");
        ErrorGoodsItem errorGoodsItem = errorGoodsItemList.get(0);
        errorGoods.setProductTitle(errorGoodsItem.getProductTitle());
        errorGoods.setErrorGoodsItemList(errorGoodsItemList);
        return errorGoods;
    }

    public List<ErrorGoods> find(ErrorGoods param){
        Assert.notNull(param, "查询条件不允许为null");
        List<Department> departmentList = userAuthorityService.findBuDeptList();
        //判断该用户是否有权限访问部门，如果获取的部门列表为空，那么直接抛错；
        if(CollectionUtils.isNullOrEmpty(departmentList)){
            throw new RuntimeException("您没有配置任何访问采购单的数据权限，如要访问，请联系管理员！！！");
        }
        List<Integer> buDeptIdList = departmentList.stream().map(Department::getId).collect(Collectors.toList());
        Integer buDeptId = param.getBuDeptId();
        if(buDeptId != null && !buDeptIdList.contains(buDeptId)){
            throw new RuntimeException("您目前没有权限查询该部门的信息，如要访问，请联系管理员！！！");
        }
        //如果搜索条件中带了部门，那么不带数据权限的搜索
        if(buDeptId != null){
            buDeptIdList  = new ArrayList<>();
        }
        List<ErrorGoods> errorGoods = mapper.findByParam(param, buDeptIdList);
        Map<Integer,String> errorGoodsProduct = errorGoodsItemService.findProduct(errorGoods);
        errorGoods.forEach(e->e.setProductTitle(errorGoodsProduct.get(e.getId())));
        Assert.notNull(errorGoods, "查询采购单列表出现错误！");
        return errorGoods;
    }

    /**
     * 操作错货单的状态流转
     * @param errorGoods 错货单
     * @param errorGoodsEvent 将触发的状态机时间
     * @param memo 备注
     * @param uid 用户id
     */
    @Transactional(value = "purchaseTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void processEvent(ErrorGoods errorGoods, ErrorGoodsEvent errorGoodsEvent, String memo, String uid) {
        Assert.notNull(errorGoods, "传入的id不允许为空！");
        if (StringUtils.isBlank(uid)) {
            uid = MBox.getSysUser();
        }
        this.errorGoodsFsmProxyService.processEvent(errorGoods, errorGoodsEvent, uid, LocalDateTime.now(), memo);
    }

    public Integer count(ErrorGoods param){
        Assert.notNull(param, "查询条件不允许为null");
        List<Department> departmentList = userAuthorityService.findBuDeptList();
        //判断该用户是否有权限访问部门，如果获取的部门列表为空，那么直接抛错；
        if(CollectionUtils.isNullOrEmpty(departmentList)){
            throw new RuntimeException("您没有配置任何访问采购单的数据权限，如要访问，请联系管理员！！！");
        }
        List<Integer> buDeptIdList = departmentList.stream().map(Department::getId).collect(Collectors.toList());
        Integer buDeptId = param.getBuDeptId();
        if(buDeptId != null && !buDeptIdList.contains(buDeptId)){
            throw new RuntimeException("您目前没有权限查询该部门的信息，如要访问，请联系管理员！！！");
        }
        //如果搜索条件中带了部门，那么不带数据权限的搜索
        if(buDeptId != null){
            buDeptIdList  = new ArrayList<>();
        }
            return mapper.count(param,buDeptIdList);
    }

    /**
     * 根据错货明细查询状态机历史记录
     *
     * @param id
     * @return
     */
    public RestResult queryHistory(Integer id, Integer start, Integer limit) {
        return fsmHistoryService.queryHistory("ErrorGoods", id, start, limit);
    }
}
