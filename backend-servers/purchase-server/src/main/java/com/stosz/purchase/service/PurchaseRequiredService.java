package com.stosz.purchase.service;

import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.order.ext.dto.OrderRequiredResponse;
import com.stosz.order.ext.service.IOrderService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.utils.DateUtils;
import com.stosz.plat.utils.JsonUtil;
import com.stosz.plat.utils.StringUtil;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.model.ProductSku;
import com.stosz.product.ext.service.IProductService;
import com.stosz.product.ext.service.IProductSkuService;
import com.stosz.purchase.ext.common.PurchaseDto;
import com.stosz.purchase.ext.common.PurchaseRequiredDto;
import com.stosz.purchase.ext.common.PurchaseRequiredItemDto;
import com.stosz.purchase.ext.enums.*;
import com.stosz.purchase.ext.model.*;
import com.stosz.purchase.mapper.PurchaseRequiredMapper;
import com.stosz.purchase.utils.ArrayUtils;
import com.stosz.store.ext.dto.request.QueryQtyReq;
import com.stosz.store.ext.dto.response.PurchaseQueryRes;
import com.stosz.store.ext.model.Wms;
import com.stosz.store.ext.service.IStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 采购需求的接口
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/11/7]
 */
@Service
public class PurchaseRequiredService {

    @Resource
    private PurchaseRequiredMapper purchaseRequiredMapper;

    @Resource
    private IProductSkuService iProductSkuService;

    @Resource
    private IProductService iProductService;

    @Resource
    private SkuSupplierService skuSupplierService;

    @Resource
    private SupplierService supplierService;

    @Resource
    private PurchaseService purchaseService;

    @Resource
    private IUserService iUserService;

    @Resource
    private IDepartmentService iDepartmentService;

    @Resource
    private UserBuDeptRelService userBuDeptRelService;

    @Resource
    private UserProductRelService userProductRelService;

    @Resource
    public IStockService iStockService;

    @Resource
    private UserAuthorityService userAuthorityService;

    @Resource
    private IOrderService iOrderService;

    @Value("${songgang.wms.id}")
    private Integer wmsId;//仓库ID
    @Value("${songgang.wms.sysCode}")
    private String wmsSysCode;//仓库Code
    @Value("${songgang.wms.name}")
    private String wmsName;//仓库Code

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 采购需求列表
     */
    public List<PurchaseRequired> queryList(PurchaseRequired purchaseRequired) {
        // 查询采购需求
        List<PurchaseRequired> requiredList = purchaseRequiredMapper.queryList(purchaseRequired);

        HashSet<String> productSpuList = new HashSet<>();
        List<String> productSkuSet = new ArrayList<>();
        requiredList.forEach(item -> {
            Integer budeptId = item.getBuDeptId();
            String sku = item.getSku();
            String spu = item.getSpu();
            productSkuSet.add(sku);
            productSpuList.add(spu);
            // 根据部门SKU 查询明细
            List<PurchaseRequired> childRequiredList = purchaseRequiredMapper.queryListByParam(budeptId, spu, sku);
            Optional.ofNullable(childRequiredList).ifPresent(target -> {
                Set<Integer> supplierIdList = childRequiredList.stream().map(PurchaseRequired::getSupplierId).collect(Collectors.toSet());
                Supplier supplier = skuSupplierService.getLastSupplierByIds(supplierIdList);
                if (supplier != null) {
                    item.setSupplierId(supplier.getId());
                    item.setSupplierName(supplier.getName());
                }
                PurchaseRequired lastRequired = childRequiredList.get(0);
                if (childRequiredList.size() > 1) {
                    // 获取采购员
                    lastRequired = childRequiredList.stream().max((src, dest) -> {
                        Long srcTime = DateUtils.getTime(src.getUpdateAt());
                        Long destTime = DateUtils.getTime(dest.getUpdateAt());
                        return srcTime.compareTo(destTime);
                    }).get();
                }
                item.setBuyer(lastRequired.getBuyer());
                item.setBuyerId(lastRequired.getBuyerId());
            });
            item.setChildRequiredList(childRequiredList);
        });

        // 根据SPU查询产品
        Set<String> spuList = productSpuList.stream().collect(Collectors.toSet());
        List<Product> productList = iProductService.findProductBySpuList(spuList);
        Map<String, Product> productMap = null;
        if (ArrayUtils.isNotEmpty(productList)) {
            productMap = productList.stream().collect(Collectors.toMap(Product::getSpu, Product -> Product));
        } else {
            productMap = new HashMap<String, Product>();
        }
        // 根据SKU获取属性
        Map<String, String> skuAttrValueMap = iProductSkuService.getAttrValueCombinations(productSkuSet);
        for (PurchaseRequired item : requiredList) {
            Product product = productMap.get(item.getSpu());
            String attrValue = skuAttrValueMap.get(item.getSku());
            item.setProductTitle(product.getTitle());
            item.setMainImageUrl(product.getMainImageUrl());
            item.setProductId(product.getId());
            item.setSkuTitle(attrValue);
        }

        return requiredList;
    }

    /**
     * 采购需求列表
     */
    public List<PurchaseRequiredSpu> queryPageList(PurchaseRequired purchaseRequired) {
        List<PurchaseRequiredSpu> resultList = new ArrayList<>();
        // 查询采购需求
        List<PurchaseRequired> requiredList = purchaseRequiredMapper.queryList(purchaseRequired);

        Set<String> detaiSet = requiredList.stream().map(item -> "(" + item.getBuDeptId() + "," + item.getSku() + ")").collect(Collectors.toSet());
        List<PurchaseRequired> purchaseSkuList = purchaseRequiredMapper.queryListByDetailSet(detaiSet);

        StringBuilder builder = new StringBuilder();
        Map<String, List<PurchaseRequired>> deptSkuPurchaseMap = purchaseSkuList.stream().collect(Collectors.groupingBy(item -> {
            builder.append(item.getBuDeptId());
            builder.append("_");
            builder.append(item.getSku());
            String value = builder.toString();
            builder.delete(0, builder.length());
            return value;
        }));

        Map<String, List<PurchaseRequired>> spuPurchaseMap = new HashMap<String, List<PurchaseRequired>>();
        // 获取所有SPU 查询产品
        Set<String> spuSet = new HashSet<>();
        // 获取所有SKU 查询SKU中文属性名
        Set<String> skuList = new HashSet<>();
        for (PurchaseRequired item : requiredList) {
            builder.append(item.getBuDeptId());
            builder.append("_");
            builder.append(item.getSpu());
            String value = builder.toString();
            builder.delete(0, builder.length());

            List<PurchaseRequired> skuPurchaseList = spuPurchaseMap.get(value);
            if (skuPurchaseList == null) {
                skuPurchaseList = new ArrayList<>();
                spuPurchaseMap.put(value, skuPurchaseList);
            }
            skuPurchaseList.add(item);

            spuSet.add(item.getSpu());
            skuList.add(item.getSku());
        }

        // 根据spu 查询产品
        List<Product> productList = iProductService.findProductBySpuList(spuSet);
        Map<String, Product> productMap = productList.stream().collect(Collectors.toMap(Product::getSpu, Function.identity()));

        // 根据SKU查询产品属性
        Map<String, String> skuAttrValueMap = iProductSkuService.getAttrValueCombinations(new ArrayList<>(skuList));

        List<SkuPurchaseInfo> skuPurchaseInfoList = new ArrayList<>();

        Set<String> spuPurchaseSet = spuPurchaseMap.keySet();
        for (String spuKey : spuPurchaseSet) {
            PurchaseRequiredSpu purchaseRequiredSpu = new PurchaseRequiredSpu();
            String values[] = spuKey.split("_");
            Integer buDeptId = Integer.parseInt(values[0]);
            String spu = values[1];
            // 设置产品属性
            Product product = productMap.get(spu);
            purchaseRequiredSpu.setProductId(product.getId());
            purchaseRequiredSpu.setProductTitle(product.getTitle());
            purchaseRequiredSpu.setMainImageUrl(product.getMainImageUrl());
            purchaseRequiredSpu.setBuDeptId(buDeptId);
            purchaseRequiredSpu.setSpu(spu);

            List<PurchaseRequired> spuPurchaseList = spuPurchaseMap.get(spuKey);
            if (ArrayUtils.isEmpty(spuPurchaseList)) {
                continue;
            }
            PurchaseRequired lastRequired = this.getLastPurchaseRequired(purchaseSkuList, buDeptId, spu);
            // 设置采购员
            purchaseRequiredSpu.setBuyer(lastRequired.getBuyer());
            purchaseRequiredSpu.setBuyerId(lastRequired.getBuyerId());
            // 获取最后一次采购的供应商
            Integer lastSupplierId = lastRequired.getSupplierId();

            ArrayList<PurchaseRequiredSku> purchaseRequiredSkus = new ArrayList<>();
            // 遍历SPU下的SKU
            for (PurchaseRequired spuPurchase : spuPurchaseList) {

                PurchaseRequiredSku purchaseRequiredSku = new PurchaseRequiredSku(spuPurchase.getSku(), lastSupplierId, spuPurchase.getPlanQty(),
                        spuPurchase.getPurchaseQty(), spuPurchase.getAvgSaleQty(), spuPurchase.getPendingOrderQty());
                if (ArrayUtils.isNotEmpty(skuAttrValueMap)) {
                    purchaseRequiredSku.setSkuTitle(skuAttrValueMap.get(spuPurchase.getSku()));
                }
                purchaseRequiredSkus.add(purchaseRequiredSku);
                if (lastSupplierId != null) {
                    skuPurchaseInfoList.add(new SkuPurchaseInfo(spuPurchase.getSku(), lastSupplierId));
                }

                // 根据事业部ID+SKU 组合Key 查明细
                builder.append(spuPurchase.getBuDeptId());
                builder.append("_");
                builder.append(spuPurchase.getSku());
                String deptSkuKey = builder.toString();
                builder.delete(0, builder.length());

                List<PurchaseRequired> deptSkuPurchaseList = deptSkuPurchaseMap.get(deptSkuKey);
                if (ArrayUtils.isEmpty(deptSkuPurchaseList)) {
                    continue;
                }
                // 遍历SKU下的部门
                ArrayList<PurchaseRequiredDept> purchaseRequiredDepts = new ArrayList<>();
                deptSkuPurchaseList.forEach(item -> {
                    PurchaseRequiredDept purchaseRequiredDept = new PurchaseRequiredDept();
                    purchaseRequiredDept.setPlanQty(item.getPlanQty());
                    purchaseRequiredDept.setAvgSaleQty(item.getAvgSaleQty());
                    purchaseRequiredDept.setPendingOrderQty(item.getPendingOrderQty());
                    purchaseRequiredDept.setPurchaseQty(item.getPurchaseQty());
                    purchaseRequiredDept.setId(item.getId());

                    purchaseRequiredDept.setDeptId(item.getDeptId());
                    purchaseRequiredDept.setDeptName(item.getDeptName());
                    purchaseRequiredDept.setDeptNo(item.getDeptNo());
                    purchaseRequiredDepts.add(purchaseRequiredDept);
                });
                // 设置SKU状态
                Integer state = deptSkuPurchaseList.get(0).getState();
                purchaseRequiredSku.setState(state);
                purchaseRequiredSku.setPurchaseRequiredDepts(purchaseRequiredDepts);
            }
            purchaseRequiredSpu.setPurchaseRequiredSkus(purchaseRequiredSkus);
            resultList.add(purchaseRequiredSpu);
        }
        this.fillPurchaseRequired(skuPurchaseInfoList, resultList);
        return resultList;
    }

    /**
     * 根据条件查询当前页的采购需求
     *
     * @param purchaseRequired 采购需求数
     * @return 查询结果
     */
    public List<PurchaseRequiredSpu> find(PurchaseRequired purchaseRequired) {

        List<PurchaseRequiredSpu> purchaseRequiredSpuList = new ArrayList<>();
        //获取到当前采购员有权限查看的部门列表，默认选中第一个部门

        List<String> spuList = purchaseRequiredMapper.findSpu(purchaseRequired);
        List<PurchaseRequired> purchaseRequiredList = purchaseRequiredMapper.findItem(purchaseRequired, spuList);

        Map<String, PurchaseRequiredSpu> spuMap = new HashMap<>();
        //判断这个spu层实体是否塞过某个sku
        Map<String, Set<String>> spuHasMap = new HashMap<>();
        //保存这个sku对应的sku
        Map<String, PurchaseRequiredSku> skuMap = new HashMap<>();
        for (PurchaseRequired item : purchaseRequiredList) {
            String spu = item.getSpu();
            String sku = item.getSku();
            Integer buyerId = item.getBuyerId();
            String buyer = item.getBuyer();
            PurchaseRequiredSku skuItem = new PurchaseRequiredSku();
            if (skuMap.containsKey(sku)) {
                skuItem = skuMap.get(sku);
            }
            skuItem.setSku(sku);
            skuItem.setSkuTitle(item.getSkuTitle());
            skuItem.setSupplierId(item.getSupplierId());
            skuItem.setSupplierName(item.getSupplierName());
            skuItem.setAvgSaleQty(skuItem.getAvgSaleQty() + item.getAvgSaleQty());
            skuItem.setPlanQty(skuItem.getPlanQty() + item.getPlanQty());
            skuItem.setPurchaseQty(skuItem.getPurchaseQty() + item.getPurchaseQty());
            skuItem.setPendingOrderQty(skuItem.getPendingOrderQty() + item.getPendingOrderQty());
            LocalDate time1 = item.getDisplayAt().toLocalDate();
            LocalDate time2 = LocalDate.now().plusDays(1);
            Boolean display = time1.equals(time2);
            skuItem.setDisplay(!display);
            List<PurchaseRequired> purchaseRequireds = skuItem.getPurchaseRequiredList();
            if (purchaseRequireds == null) {
                purchaseRequireds = new ArrayList<>();
            }
            purchaseRequireds.add(item);
            skuItem.setPurchaseRequiredList(purchaseRequireds);
            skuMap.put(sku, skuItem);
            PurchaseRequiredSpu spuItem = new PurchaseRequiredSpu();
            spuItem.setSpu(spu);
            spuItem.setBuyer(buyer);
            spuItem.setBuyerId(buyerId);
            Set<String> skuList = new HashSet<>();
            if (spuHasMap.containsKey(spu)) {
                skuList = spuHasMap.get(spu);
            }
            skuList.add(sku);
            spuHasMap.put(spu, skuList);
            spuMap.put(spu, spuItem);
        }

        for (String key : spuHasMap.keySet()) {
            PurchaseRequiredSpu purchaseRequiredSpu = spuMap.get(key);
            Product product = iProductService.getBySpu(key);
            if(product != null){
                purchaseRequiredSpu.setProductId(product.getId());
                purchaseRequiredSpu.setProductTitle(product.getTitle());
                purchaseRequiredSpu.setMainImageUrl(product.getMainImageUrl());
            }
            List<PurchaseRequiredSku> skuList = new ArrayList<>();
            Set<String> skuSet = spuHasMap.get(key);
            for (String sku : skuSet) {
                PurchaseRequiredSku purchaseRequiredSku = skuMap.get(sku);
                skuList.add(purchaseRequiredSku);
            }
            purchaseRequiredSpu.setPurchaseRequiredSkus(skuList);
            purchaseRequiredSpu.setBuDeptId(purchaseRequired.getBuDeptId());
            purchaseRequiredSpuList.add(purchaseRequiredSpu);
        }

        return purchaseRequiredSpuList;
    }


    public Integer count(PurchaseRequired purchaseRequired) {
        return purchaseRequiredMapper.countSpu(purchaseRequired);
    }

    private void fillPurchaseRequired(List<SkuPurchaseInfo> skuPurchaseInfoList, List<PurchaseRequiredSpu> resultList) {
        if (ArrayUtils.isEmpty(resultList)) {
            return;
        }
        if (ArrayUtils.isNotEmpty(skuPurchaseInfoList)) {
            // 根据SKU+SupplierID 查询供应商
            Set<String> skuPurchaseSet = skuPurchaseInfoList.stream().map(t -> StringUtil.concat("(", t.getSku(), "," + t.getSupplierId(), ")"))
                    .collect(Collectors.toSet());
            List<SkuPurchaseInfo> purchaseInfos = skuSupplierService.querySkuSupplier(skuPurchaseSet);
            Map<String, SkuPurchaseInfo> skuSupplierMap = purchaseInfos.stream()
                    .collect(Collectors.toMap(t -> t.getSupplierId() + "_" + t.getSku(), Function.identity()));

            for (PurchaseRequiredSpu purchaseRequiredSpu : resultList) {
                List<PurchaseRequiredSku> purchaseRequiredSkus = purchaseRequiredSpu.getPurchaseRequiredSkus();
                purchaseRequiredSkus.forEach(item -> {
                    if (item.getSupplierId() == null) {
                        return;
                    }
                    String key = StringUtil.concat(item.getSupplierId(), "_", item.getSku());
                    SkuPurchaseInfo skuPurchaseInfo = skuSupplierMap.get(key);
                    if (skuPurchaseInfo != null) {
                        item.setSupplierName(skuPurchaseInfo.getSupplierName());
                        item.setPurchaseUrl(skuPurchaseInfo.getPurchaseUrl());
                    }
                });
            }
        }
    }

    private PurchaseRequired getLastPurchaseRequired(List<PurchaseRequired> purchaseSkuList, Integer buDeptId, String spu) {
        List<PurchaseRequired> childRequiredList = purchaseSkuList.stream().filter(t -> t.getBuDeptId().equals(buDeptId) && t.getSpu().equals(spu))
                .collect(Collectors.toList());
        // 获取采购员
        PurchaseRequired lastRequired = childRequiredList.get(0);
        if (childRequiredList.size() > 1) {
            lastRequired = childRequiredList.stream().max((src, dest) -> {
                Long srcTime = DateUtils.getTime(src.getUpdateAt());
                Long destTime = DateUtils.getTime(dest.getUpdateAt());
                return srcTime.compareTo(destTime);
            }).get();
        }
        return lastRequired;
    }

    public int queryListCount(PurchaseRequired purchaseRequired) {
        return purchaseRequiredMapper.queryListCount(purchaseRequired);
    }

    /**
     * 修改SKU采购需求订单为先不买
     *
     * @param buDeptId
     * @param spu
     * @param sku
     * @return
     */
    public int updateDelayPurcharse(Integer buDeptId, String spu, String sku) {
        return purchaseRequiredMapper.updatePurchaseDelay(buDeptId, spu, sku);
    }

    /**
     * 修改明细计划采购数
     *
     * @param id
     * @param qty
     * @return
     */
    public int updatePlanQty(Integer id, Integer qty) {
        return purchaseRequiredMapper.updatePlanQty(id, qty);
    }

    /**
     * 修改SKU 采购供应商
     *
     * @param purchaseRequired
     * @return
     */
    public int updateSupplier(PurchaseRequired purchaseRequired) {
        return purchaseRequiredMapper.updateSupplier(purchaseRequired);
    }

    /**
     * 获取采购需求
     *
     * @param purchaseRequired
     * @return
     */
    public List<PurchaseRequired> query(PurchaseRequired purchaseRequired) {
        return purchaseRequiredMapper.query(purchaseRequired);
    }

    /**
     * 新增采购需求
     *
     * @param purchaseRequired
     * @return
     */
    public int addRequired(PurchaseRequired purchaseRequired) {
        return purchaseRequiredMapper.insert(purchaseRequired);
    }

    /**
     * 修改采购需求
     *
     * @return
     */
    public int updateRequired(PurchaseRequired purchaseRequired) {
        return purchaseRequiredMapper.updatePurchase(purchaseRequired);
    }

    /**
     * 采购需求->生成草稿采购单
     */
    public String createPurchase(PurchaseRequiredDto purchaseRequiredDto) {
        logger.info("createPurchase() 创建采购单 param={}", JsonUtil.toJson(purchaseRequiredDto));

        Integer buyerId = purchaseRequiredDto.getBuyerId();
        User buyUser = iUserService.getById(buyerId);
        Assert.notNull(buyUser, "采购员ID[" + buyerId + "]不存在");

        Supplier supplier = supplierService.getById(purchaseRequiredDto.getSupplierId());
        Assert.notNull(supplier, "供应商ID[" + purchaseRequiredDto.getSupplierId() + "]不存在");

        Department department = iDepartmentService.get(purchaseRequiredDto.getBuDeptId());
        Assert.notNull(department, "事业部ID[" + department.getId() + "]不存在");

        List<PurchaseRequiredItemDto> requiredList = purchaseRequiredDto.getRequiredItemDtos();
        // 查询采购需求单明细
        List<Integer> idList = requiredList.stream().map(PurchaseRequiredItemDto::getId).collect(Collectors.toList());
        List<PurchaseRequired> purchaseRequireds = purchaseRequiredMapper.queryByIdList(idList);
        Map<Integer, PurchaseRequired> purchaseMap = purchaseRequireds.stream()
                .collect(Collectors.toMap(PurchaseRequired::getId, Function.identity()));
        // 根据SPU查询产品
        Set<String> spuList = purchaseRequireds.stream().map(PurchaseRequired::getSpu).collect(Collectors.toSet());
        purchaseRequireds.clear();

        List<Product> products = iProductService.findProductBySpuList(spuList);
        // SPU作为键值
        Map<String, Product> spuProductMap = products.stream().collect(Collectors.toMap(Product::getSpu, Function.identity()));
        products.clear();

        // 采购单
        PurchaseDto purchaseDto = this.createPurchase(supplier, buyUser, purchaseRequiredDto, requiredList, department);
        BigDecimal totalQtyDecimal = new BigDecimal(purchaseDto.getQuantity());
        // 采购单价
        BigDecimal price = null;
        if (purchaseRequiredDto.getTotalAmount() != null && purchaseRequiredDto.getTotalAmount().compareTo(new BigDecimal(0)) == 1) {
            price = purchaseRequiredDto.getTotalAmount().divide(totalQtyDecimal, 2, BigDecimal.ROUND_HALF_DOWN);
        }
        // 采购明细
        ArrayList<PurchaseItem> purchaseItemList = createPurchaseItemList(requiredList, purchaseMap, price, purchaseRequiredDto, spuProductMap);
        purchaseDto.setItems(purchaseItemList);
        purchaseDto.setWmsSysCode(wmsSysCode);// 先默认福永仓
        purchaseDto.setWmsId(wmsId);// 先默认福永仓
        purchaseDto.setWmsName(wmsName);//  先默认福永仓
        // 创建采购单
        String purchaseNo = purchaseService.insert(purchaseDto);
        if (StringUtils.isEmpty(purchaseNo)) {
            logger.warn("createPurchase() 创建采购单失败,产品SPU={},事业部ID={}", purchaseRequiredDto.getSpu(), purchaseRequiredDto.getBuDeptId());
            return null;
        }
        for(PurchaseRequiredItemDto requiredUpdate: requiredList){
            //如果计划采购数，大于等于采购需求数，那么该条记录设置展现时间推迟一天
            if(requiredUpdate.getPurchaseQty()<=requiredUpdate.getPlanQty()){
                purchaseRequiredMapper.updatePurchaseDelayById(requiredUpdate.getId());
            }else{
                purchaseRequiredMapper.updatePlanQty(requiredUpdate.getId(),0);
            }
        }
        return purchaseNo;
    }


    /**
     * 根据SPU buDeptID 修改供应商
     *
     * @param userProductRel
     * @return
     */
    public int updateRequiredByuer(UserProductRel userProductRel) {
        Integer buDeptId = userProductRel.getBuDeptId();
        Department department = iDepartmentService.get(buDeptId);
        Assert.notNull(department, "事业部ID[" + buDeptId + "]不存在");
        Integer userId = userProductRel.getUserId();
        User user = iUserService.getById(userId);
        Assert.notNull(user, "用户ID[" + MBox.getLoginUserId() + "]不存在");
        // 查询部门与采购员的关系
        UserBuDept userBuDept = userBuDeptRelService.getByUnique(buDeptId, userId);
        if (userBuDept == null) {
            userBuDept = new UserBuDept();
            userBuDept.setCreatorId(userProductRel.getCreatorId());
            userBuDept.setCreator(userProductRel.getCreator());
            userBuDept.setUserId(userId);
            userBuDept.setUserName(user.getLastname());
            userBuDept.setBuDepartmentName(department.getDepartmentName());
            userBuDept.setBuDepartmentNo(department.getDepartmentNo());
            userBuDept.setBuDepartmentId(buDeptId);
            userBuDept.setEnable(UserBuDeptState.ENABLE.ordinal());
            userBuDeptRelService.insert(userBuDept);
        } else {
            if (userBuDept.getEnable() == null || userBuDept.getEnable() != 2) {// 修改采购与部门关系 状态为启用
                userBuDeptRelService.updateState(userBuDept.getId(), UserBuDeptState.ENABLE.ordinal());
            }
        }
        // 查询采购与SPU的关系
        UserProductRel productRel = userProductRelService.getBySpuAndId(userProductRel.getSpu(), userId);
            if (productRel == null) {
            productRel = new UserProductRel();
            productRel.setCreatorId(userProductRel.getCreatorId());
            productRel.setCreator(userProductRel.getCreator());
            productRel.setUserId(userId);
            productRel.setUserName(user.getLastname());
            productRel.setSpu(userProductRel.getSpu());
            productRel.setEnable(UserProductRelState.ENABLE.ordinal());
            userProductRelService.insert(productRel);
        } else {
            if (productRel.getEnable() == null || productRel.getEnable() != 2) {// 修改采购与SPU关系 状态为启用
                userProductRelService.updateState(productRel.getId(), UserProductRelState.ENABLE.ordinal());
            }
        }
        PurchaseRequired purchaseRequired = new PurchaseRequired();
        purchaseRequired.setBuyerId(userId);
        purchaseRequired.setBuDeptId(buDeptId);
        purchaseRequired.setSpu(userProductRel.getSpu());
        purchaseRequired.setBuyer(user.getLastname());
        return purchaseRequiredMapper.updateBuyer(purchaseRequired);
    }

    private PurchaseDto createPurchase(Supplier supplier, User buyUser, PurchaseRequiredDto purchaseRequiredDto,
                                       List<PurchaseRequiredItemDto> requiredList, Department department) {
        PurchaseDto purchaseDto = new PurchaseDto();
        purchaseDto.setSupplierId(supplier.getId());
        purchaseDto.setSupplierName(supplier.getName());
        purchaseDto.setBuyer(MBox.getLoginUserName());
        purchaseDto.setBuyerId(MBox.getLoginUserId());
        purchaseDto.setAmount(purchaseRequiredDto.getTotalAmount());
        purchaseDto.setBuDeptId(department.getId());
        purchaseDto.setBuDeptName(department.getDepartmentName());
        purchaseDto.setBuDeptNo(department.getDepartmentNo());
        Wms wms = new Wms();
        purchaseDto.setWmsId(wms.getId());// 默认福永仓
        purchaseDto.setWmsName(wms.getName());
        purchaseDto.setWmsSysCode(wms.getWmsSysCode());
        purchaseDto.setState(PurchaseState.start.toString());
        purchaseDto.setStateTime(LocalDateTime.now());
        purchaseDto.setPayMethodEnum(PayMethodEnum.payFirst); // 通过采购需求方式创建的采购单全部是先款后货，是否要配置到供应商项目
        purchaseDto.setCreatorId(purchaseRequiredDto.getCreatorId());
        purchaseDto.setCreator(purchaseRequiredDto.getCreator());
        // 采购总数
        Integer quantity = requiredList.stream().mapToInt(PurchaseRequiredItemDto::getPlanQty).sum();
        Integer purchaseQty = requiredList.stream().mapToInt(PurchaseRequiredItemDto::getPurchaseQty).sum();
        Integer avgSaleQty = requiredList.stream().mapToInt(PurchaseRequiredItemDto::getAvgSaleQty).sum();
        Integer pendingOrderQty = requiredList.stream().mapToInt(PurchaseRequiredItemDto::getPendingOrderQty).sum();
        purchaseDto.setPurchaseQty(purchaseQty);
        purchaseDto.setAvgSaleQty(avgSaleQty);
        purchaseDto.setPendingOrderQty(pendingOrderQty);
        purchaseDto.setQuantity(quantity);
        return purchaseDto;
    }

    private ArrayList<PurchaseItem> createPurchaseItemList(List<PurchaseRequiredItemDto> requiredList, Map<Integer, PurchaseRequired> purchaseMap,
                                                           BigDecimal price, PurchaseRequiredDto purchaseRequiredDto, Map<String, Product> spuProductMap) {
        ArrayList<PurchaseItem> purchaseItemList = new ArrayList<>();
        for (PurchaseRequiredItemDto requiredItemDto : requiredList) {
            Integer requiredItemId = requiredItemDto.getId();
            PurchaseRequired required = purchaseMap.get(requiredItemId);
            Assert.notNull(required, "采购需求明细ID[" + requiredItemId + "]不存在");
            Assert.isTrue(required.getSku().equals(requiredItemDto.getSku()),
                    "采购需求明细SKU[" + requiredItemDto.getSku() + "][" + requiredItemId + "]与数据库中不一致");
            Product product = spuProductMap.get(required.getSpu());
            Assert.notNull(product, "SPU[" + required.getSpu() + "]对应的产品不存在");

            PurchaseItem purchaseItem = new PurchaseItem();
            purchaseItem.setDeptId(required.getDeptId());
            purchaseItem.setDeptNo(required.getDeptNo());
            purchaseItem.setDeptName(required.getDeptName());
            purchaseItem.setSku(required.getSku());
            purchaseItem.setSkuTitle(required.getSkuTitle());
            purchaseItem.setSpu(required.getSpu());
            purchaseItem.setProductTitle(product.getTitle());
            // 采购需求数
            purchaseItem.setPurchaseQty(requiredItemDto.getPurchaseQty());
            // 3日平均交易量
            purchaseItem.setAvgSaleQty(requiredItemDto.getAvgSaleQty());
            // 待审单数
            purchaseItem.setPendingOrderQty(requiredItemDto.getPendingOrderQty());
            // 采购数量
            purchaseItem.setQuantity(requiredItemDto.getPlanQty());
            purchaseItem.setIntransitCancleQty(0);
            purchaseItem.setQcfailQty(0);
            purchaseItem.setState(PurchaseItemState.start.toString());
            // 单价
            purchaseItem.setPrice(price);
            purchaseItem.setCreatorId(purchaseRequiredDto.getCreatorId());
            if (price != null) {
                // 明细采购总价
                purchaseItem.setAmount(price.multiply(new BigDecimal(requiredItemDto.getPlanQty())));
            }
            purchaseItemList.add(purchaseItem);

        }
        return purchaseItemList;
    }


    private Wms getWms(){
        Wms wms = new Wms();
        wms.setId(1);
        wms.setName("福永仓");
        wms.setWmsSysCode("Y");
        return wms;
    }

    public StockInfo findStock(PurchaseRequired purchaseRequired,String type){
        StockInfo stockInfo = new StockInfo();
        stockInfo.setWmsId(wmsId);

        //要查询的sku集合
        Set<String> skus = new HashSet<>();
        Map<String,String> skuMap = new HashMap<>();

        //产品信息
        Product product = iProductService.getBySpu(purchaseRequired.getSpu());



        if("sku".equals(type)){
            skus.add(purchaseRequired.getSku());
            String skuTitle = iProductSkuService.getAttrValueTitleBySku(purchaseRequired.getSku());
            skuMap.put(purchaseRequired.getSku(),skuTitle);

            ProductSku productSku = iProductSkuService.getBySku(purchaseRequired.getSku());
            List<ProductSku> productSkuList = new ArrayList<>();
            productSkuList.add(productSku);
            product.setSkuList(productSkuList);
        }else{
            List<ProductSku> productSkuList = iProductSkuService.findBySpu(purchaseRequired.getSpu());
            product.setSkuList(productSkuList);

            for (ProductSku productSku : productSkuList){
                skus.add(productSku.getSku());
                skuMap.put(productSku.getSku(),iProductSkuService.getAttrValueTitleBySku(productSku.getSku()));
            }
        }

        //获取当前用户拥有的事业部集合
        List<Department> departmentList = userAuthorityService.findList();

        Set<Integer> requiredDeptIds = new HashSet<>();


        //查询库存
        List<QueryQtyReq> queryQtyReqs = new ArrayList<>();
        for(Department department : departmentList){
            for(String sku : skus){
                QueryQtyReq queryQtyReq = new QueryQtyReq();
                queryQtyReq.setDeptId(department.getId());
                queryQtyReq.setDepartmentName(department.getDepartmentName());
                queryQtyReq.setWmsId(wmsId);
                queryQtyReq.setSku(sku);
                queryQtyReqs.add(queryQtyReq);
            }
            requiredDeptIds.add(department.getId());
        }
        List<PurchaseQueryRes> purchaseQueryResList = iStockService.purchaseQuery(queryQtyReqs);

        //部门集合
        Set<Integer> deptIds = new HashSet<>();
        for (PurchaseQueryRes purchaseQueryRes : purchaseQueryResList){
            deptIds.add(purchaseQueryRes.getDeptId());
        }

        //转换数据结构 供前端展示
        List<InStockDepartment> inStockDepartmentList = new ArrayList<>();
        for (Integer deptId : deptIds){
            InStockDepartment inStockDepartment = new InStockDepartment();
            List<SkuStockInfo> skuStockInfoList = new ArrayList<>();
            for (PurchaseQueryRes pqr : purchaseQueryResList){
                if(deptId.equals(pqr.getDeptId())){
                    inStockDepartment.setDeptName(pqr.getDepartmentName());

                    SkuStockInfo skuStockInfo = new SkuStockInfo();
                    skuStockInfo.setSku(pqr.getSku());
                    skuStockInfo.setTitle(skuMap.get(pqr.getSku()));
                    skuStockInfo.setDeptId(deptId);
                    skuStockInfo.setDeptName(pqr.getDepartmentName());
                    OrderRequiredResponse orp = iOrderService.pullOrderReuired(deptId,pqr.getSku());
                    if(orp != null){
                        skuStockInfo.setDailySales(orp.getAvgSaleQty());
                    }
                    skuStockInfo.setOverseasStockQty(pqr.getOtherUsableQty());
                    skuStockInfo.setSgStockQty(pqr.getUsableQty());
                    skuStockInfo.setPurchaseCost(pqr.getPrice());
                    skuStockInfoList.add(skuStockInfo);
                }
            }
            inStockDepartment.setSkuStockInfoList(skuStockInfoList);
            inStockDepartmentList.add(inStockDepartment);
        }

        //需求部门
        List<PurchaseRequired> purchaseRequiredList = purchaseRequiredMapper.queryBySkuAndDeptId(skus,requiredDeptIds);
        stockInfo.setProduct(product);
        stockInfo.setPurchaseRequireds(purchaseRequiredList);
        stockInfo.setInStockDepartments(inStockDepartmentList);

        return stockInfo;
    }


}
