package com.stosz.product.service;

import com.google.common.base.Strings;
import com.google.common.collect.Sets;
import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.ext.service.IUserDepartmentRelService;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.fsm.FsmProxyService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.StoszCoder;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.enums.ClassifyEnum;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.deamon.ProductPushService;
import com.stosz.product.engine.SkuCombination;
import com.stosz.product.ext.enums.ProductEvent;
import com.stosz.product.ext.enums.ProductState;
import com.stosz.product.ext.enums.ProductZoneEvent;
import com.stosz.product.ext.enums.ProductZoneState;
import com.stosz.product.ext.model.*;
import com.stosz.product.ext.service.IWmsService;
import com.stosz.product.mapper.ProductMapper;
import com.stosz.wms.service.WmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 产品Service
 *
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Service
public class ProductService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private ProductMapper mapper;
    @Resource
    private ProductZoneService productZoneService;
    @Resource
    private AttributeService attributeService;
    @Resource
    private AttributeValueService attributeValueService;
    @Resource
    private AttributeLangService attributeLangService;
    @Resource
    private AttributeValueLangService attributeValueLangService;
    @Resource
    private ProductAttributeRelService productAttributeRelService;
    @Resource
    private ProductAttributeValueRelService productAttributeValueRelService;
    @Resource
    private ProductLangService productLangService;
    @Resource
    private IUserService iUserService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private IUserDepartmentRelService iUserDepartmentRelService;
    @Resource
    private ZoneService zoneService;
    @Resource
    private ProductZoneDomainService productZoneDomainService;
    @Resource
    private ProductSkuAttributeValueRelService productSkuAttributeValueRelService;
    @Resource
    private ProductSkuService productSkuService;
    @Resource
    private FsmProxyService<Product> pcProductFsmProxyService;
    @Resource
    private IDepartmentService iDepartmentService;
    @Resource
    private ProductPushService productPushService;
    @Resource
    private IWmsService iWmsService;


    /**
     * 产品增加
     */
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void insertProduct(Product product) {
        mapper.insert(product);
        logger.info("产品: {} 添加成功!", product);
    }

    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void createProduct(Product product) {
        product.setState(ProductState.start.name());
        product.setStateTime(LocalDateTime.now());
        mapper.insert(product);
        logger.info("产品: {} 创建成功!", product);
        pcProductFsmProxyService.processNew(product, product.getMemo());
        pcProductFsmProxyService.processEvent(product, ProductEvent.create, product.getMemo());
        String spu = StoszCoder.generateSpu(product.getClassifyEnum(), product.getId());
        mapper.updateSpu(product.getId(), spu);
        logger.info("产品ID:{} 的spu由更新成: {} 成功!", product.getId(), product);
        product.setSpu(spu);
    }

    public void insertList(List<Product> productList) {
        Assert.notNull(productList, "不允许插入空的产品集合！");
        mapper.insertList(0, productList);
        logger.info("产品: {} 批量插入成功!", productList);
    }

    /**
     * 产品删除
     */
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteProduct(Integer productId) {
        Product proResult = mapper.getById(productId);
        Assert.notNull(proResult, "要删除的产品不存在");
        // 删除产品属性关系表
        productAttributeRelService.deleteByProductId(productId);
        logger.info("产品:{} 与属性的关系被成功删除");
        // 删除产品属性值关系表
        productAttributeValueRelService.deleteRel(productId);
        logger.info("产品:{} 与属性值的关系被成功删除");
        mapper.delete(productId);
        logger.info("删除产品:{} 成功", proResult);
    }

    /**
     * 根据id修改产品内部名
     */
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateById(String innerName, Integer id) {
        Product proResult = mapper.getById(id);
        Assert.notNull(proResult, "要修改的产品不存在");
        mapper.updateById(innerName, id);
        logger.info("修改产品内部名:{} 为: {} 成功", proResult.getInnerName(), innerName);
    }

    /**
     * 批量修改产品状态
     */
    public void updateStateByIds(List<Integer> ids, String state) {
        Assert.notNull(ids, "产品id的集合不允许为空！");
        mapper.updateStateByIds(ids, state);
    }

    /**
     * 产品状态的撤回
     */
    public void productStateFallback(Integer productId, ProductState currentState, ProductState fallbackState) {
        Product product = loadById(productId);
        Assert.isTrue(product.getState().equals(currentState.name()), "产品状态有误,当前产品[" + product.getTitle() + "]的状态是[" + product.getState() + "],而不是[" + currentState + "]");
        //关系表的删除
        productSkuService.deleteByProduct(productId);
        productSkuAttributeValueRelService.deleteByProductId(productId);
        //状态的修改
        mapper.updateProductState(productId, fallbackState, LocalDateTime.now());
        logger.info("产品状态回退成功!产品[" + product.getTitle() + "]状态由[" + currentState + "]修改成[" + fallbackState + "]成功");
    }


    /**
     * 产品的更新
     */
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateProduct(Product product) {
        Product productResult = mapper.getById(product.getId());
        Assert.notNull(productResult, "没有查询到符合修改的记录");
        // 设置状态的更新时间(当状态发送改变后)
        if (!productResult.getState().equals(product.getState())) {
            product.setStateTime(LocalDateTime.now());
        }
        mapper.update(product);
        logger.info("产品:{}更新成:{}成功", productResult, product);
    }

    public void updateState(Integer id, String state, LocalDateTime stateTime) {
        Product productResult = mapper.getById(id);
        Assert.notNull(productResult, "没有查询到符合修改的记录");
        mapper.updateState(id, state, stateTime);
        logger.info("产品:{}更新状态成:{}成功", productResult, state);
    }

    public void updateTotalStock(Product product) {
        Assert.notNull(product, "不允许将产品修改为null");
        mapper.updateTotalStock(product);
        logger.info("产品更新成:{}成功", product);
    }

    public void processEvent(Product product, ProductEvent event, String memo, String uid) {
        if (uid == null || uid.equals("")) {
            uid = MBox.getSysUser();
        }
        this.pcProductFsmProxyService.processEvent(product, event, uid, LocalDateTime.now(), memo);

    }

    public void processLog(Product product, String event, String memo, String uid) {
        if (uid == null || uid.equals("")) {
            uid = MBox.getSysUser();
        }
        this.pcProductFsmProxyService.processLog(product, event, uid, LocalDateTime.now(), memo);

    }

    /**
     * 建站前端建档信息回传处理
     *
     * @param productId       产品id
     * @param seoLoginId      优化师的loginid
     * @param zoneId          区域id
     * @param webDirectory    二级目录名称
     * @param domain          域名
     * @param siteProductId   建站端唯一的产品id，用来更新domain 和  webDirectory
     * @param operatorLoginid 建站端操作账号的loginid
     */
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void frontEndArchive(Integer productId, String seoLoginId, Integer zoneId, String webDirectory, String domain, String siteProductId, String operatorLoginid) {

        Assert.notNull(seoLoginId, "建档时优化师loginid不允许为空！");
        Assert.notNull(zoneId, "建档时区域id不允许为空！");
        Assert.notNull(domain, "建档时域名不允许为空！");
        Assert.isTrue(!Strings.isNullOrEmpty(siteProductId), "建档时建站端产品id不允许为空！");
        Assert.isTrue(!Strings.isNullOrEmpty(operatorLoginid), "建档时建站端操作loginid不允许为空！");

        User seoUser = iUserService.getByLoginid(seoLoginId);
        Assert.notNull(seoUser, "请求参数中优化师loginid:" + seoLoginId + "在数据库中不存在!");
        User opertorUser = iUserService.getByLoginid(operatorLoginid);
        Assert.notNull(opertorUser, "请求参数中操作loginid:" + seoLoginId + "在数据库中不存在!");

        Zone zone = zoneService.load(zoneId);
        Assert.notNull(zone, "区域id不存在");

        webDirectory = (webDirectory == null ? "/" : webDirectory);
        if (!webDirectory.startsWith("/")) webDirectory = "/" + webDirectory;
        if (!domain.startsWith("http")) domain = "http://" + domain;
        if (!webDirectory.startsWith("http")) webDirectory = domain + webDirectory;
        /////////////////////////////////////////////////////////////////////////////////////////参数校验


        ProductZone param = new ProductZone();
        param.setProductId(productId);
        param.setZoneCode(zone.getCode());
//		param.setDepartmentId(seoUser.getDepartmentId());

        // 这三个条件应该只能取到一条记录  一个产品id,一个区域编码，用户所在的部门只有一个产品区域
        List<ProductZone> productIdZoneCodeSeoUserDepartmentFindProductZoneList = productZoneService.find(param);


        Set<Integer> seoUserAndChildrenDepartmentIdSet = getChildrenDepartmentIds(seoUser.getDepartmentId(), Sets.newHashSet());

        productIdZoneCodeSeoUserDepartmentFindProductZoneList = productIdZoneCodeSeoUserDepartmentFindProductZoneList.stream()
                .filter(productZone -> seoUserAndChildrenDepartmentIdSet.contains(productZone.getDepartmentId()))
                .collect(Collectors.toList());


        Assert.isTrue(productIdZoneCodeSeoUserDepartmentFindProductZoneList != null && productIdZoneCodeSeoUserDepartmentFindProductZoneList.size() == 1,
                "建档失败，产品id:" + productId + " 区域:" + zone.getTitle() + " 优化师::" + seoLoginId + " 所在部门没有产品区域信息，可能尚未排重。");
        ProductZone productZone = productIdZoneCodeSeoUserDepartmentFindProductZoneList.get(0);
        ProductZoneState zoneState = productZone.getStateEnum();

        String zoneStateName = (null == zoneState ? "不存在" : zoneState.display());

        Assert.isTrue(zoneState == ProductZoneState.archiving || zoneState == ProductZoneState.onsale,
                String.format("用户 %s 对产品 %s 建档失败！此产品所在的部门：%s 区域：%s 的档案当前是:%s状态！要求的状态是：%s 或者 %s ",
                        seoLoginId, productId, seoUser.getDepartmentId(), zone.getTitle(), zoneStateName, ProductZoneState.archiving.display(), ProductZoneState.onsale.display()));

        //////////////////////////////////////////////////////////////////////////////////////////找到产品区域信息，校验数量和状态
        productZoneService.updateArchiveUser(seoLoginId, productZone.getId());
        /////////////////////////////////////////////////////////////////////////////////////////////////更新建档的区域的对应的建档的用户loginid


        ProductZoneDomain pzdParam = new ProductZoneDomain();
        pzdParam.setSiteProductId(siteProductId);

        List<ProductZoneDomain> siteProductIdFindProductZoneDomainList = productZoneDomainService.find(pzdParam);
        /////////////////////////////////////////////////////////////////////////////////////////找到siteProductId对应的产品区域域名信息

        if (productZone.getStateEnum() == ProductZoneState.archiving)
            productZoneService.processEvent(productZone, ProductZoneEvent.archive, "建档域名:" + domain + " 二级目录:" + webDirectory, seoLoginId);
        else
            productZoneService.processLog(productZone, ProductZoneEvent.archive, "建档域名:" + domain + " 二级目录:" + webDirectory, seoLoginId);

        ////////////////////////////////////////////////////////////////////////////////////结合状态,对产品的状态机做处理


        if (CollectionUtils.isNotNullAndEmpty(siteProductIdFindProductZoneDomainList)) {
            productZoneDomainService.update(siteProductId, domain, webDirectory);
            return;
        }

        ///////////////////////////////////////////////////////////////////////////////如果存在siteProductId对应的产品区域域名信息,更新信息
        ProductZoneDomain zoneDomain = new ProductZoneDomain();
        zoneDomain.setSiteProductId(siteProductId);
        zoneDomain.setDomain(domain);
        zoneDomain.setLoginid(operatorLoginid);
        zoneDomain.setWebDirectory(webDirectory);
        zoneDomain.setProductId(productId);
        zoneDomain.setZoneId(zoneId);
        zoneDomain.setDepartmentId(seoUser.getDepartmentId());
        zoneDomain.setProductZoneId(productZone.getId());
        zoneDomain.setSeoLoginid(seoLoginId);
        productZoneDomainService.insert(zoneDomain);
        ///////////////////////////////////////////////////////////////////////////////////////初始化要保存的productZoneDomain并插入


    }

    /**
     * 产品列表的查询(单表)
     */
    public RestResult findProduct(Product product) {
        RestResult result = new RestResult();
        int count = mapper.count(product);
        result.setTotal(count);
        if (count == 0) {
            return result;
        }
        List<Product> list = mapper.find(product);
        // 为产品绑定语言包
        list = bindProductLang(list);
        result.setItem(list);
        result.setDesc("产品列表查询成功!");
        return result;
    }

    /**
     * 产品绑定语言包
     */
    private List<Product> bindProductLang(List<Product> list) {
        if (list != null && list.isEmpty()) {
            return list;
        }
        Assert.notNull(list, "该产品没有语言包!");
        List<Integer> ids = list.stream().map(Product::getId).collect(Collectors.toList());
        Map<Integer, List<ProductLang>> mapProductLang = productLangService.findProductLang(ids);
        for (Product lst : list) {
            lst.setProductLangList(mapProductLang.get(lst.getId()));
        }
        return list;
    }

    /**
     * 产品列表的查询(关联表==产品表，品类表)
     * 排重查看的列表
     */
//    public RestResult findListing(Product product, Boolean isSystemMatch) {
//        RestResult result = new RestResult();
//        List<Integer> departmentIds = getDepartmentViewByCurrentUser();
//        product.setDepartmentIds(departmentIds);
//        List<Product> list;
//        if (!isSystemMatch) {
//            // 如果有品类的搜索
//            if (product.getCategoryId() != null) {
//                Category category = categoryService.getById(product.getCategoryId());
//                List<Integer> categoryIds = new ArrayList<Integer>();
//                if (category.getLeaf()) {
//                    categoryIds.add(product.getCategoryId());
//                } else {
//                    List<Category> lst = categoryService.findByNo(category.getNo());
//                    categoryIds = lst.stream().map(Category::getId).collect(Collectors.toList());
//                }
//                product.setCategoryIds(categoryIds);
//            }
//            int count = mapper.countListing(product);
//            result.setTotal(count);
//            if (count == 0) {
//                return result;
//            }
//            list = mapper.findListing(product);
//        } else {
//            int count = mapper.countByProductNewId(product.getProductNewId());
//            list = mapper.findByProductNew(product);
//            result.setTotal(count);
//        }
//        if (list == null || list.isEmpty()) {
//            result.setDesc("不存在该筛选条件的产品！");
//            result.setItem(list);
//            result.setCode(RestResult.NOTICE);
//            return result;
//        }
//        List<Integer> ids = list.stream().map(Product::getId).collect(Collectors.toList());
//        Map<Integer, List<ProductLang>> mapProductLang = productLangService.findProductLang(ids);
//        List<ProductZone> productZoneList = productZoneService.findByProductIds(ProductZoneState.disappeared.name(), ids);
//        Map<Integer, List<ProductZone>> mapProductZone = productZoneList.stream().collect(Collectors.groupingBy(ProductZone::getProductId));
//        Map<Integer, List<Attribute>> mapAttribute = attributeService.findByProductIds(ids);
//        Map<Integer, String> map = new HashMap<>();
//        for (Integer key : mapAttribute.keySet()) {
//            StringBuilder sb = new StringBuilder();
//            for (Attribute l : mapAttribute.get(key)) {
//                sb.append(l.getTitle() + " ");
//            }
//            map.put(key, sb.toString());
//        }
//        // 为产品绑定语言包,绑定产品区域信息
//        for (Product lst : list) {
//            lst.setProductLangList(mapProductLang.get(lst.getId()));
//            lst.setProductZoneList(mapProductZone.get(lst.getId()));
////			lst.setAttributeList(mapAttribute.get(lst.getId()));
//            lst.setAttributeName(map.get(lst.getId()));
//        }
//        result.setItem(list);
//        result.setDesc("产品列表查询成功!");
//        return result;
//    }

    public RestResult findImage(Product product, boolean isSystemMatch) {
        RestResult result = new RestResult();
        List<Integer> departmentIds = getDepartmentViewByCurrentUser();
        product.setDepartmentIds(departmentIds);
        List<Product> productList;
        if (isSystemMatch) {
            int count = mapper.countByProductNewId(product.getProductNewId());
            productList = mapper.findByProductNew(product);
            result.setTotal(count);
        } else {
            // 如果有品类的搜索
            if (product.getCategoryId() != null) {
                List<Integer> cateIds = findCategoryInfo(product.getCategoryId());
                product.setCategoryIds(cateIds);
            }
            int count = mapper.countImage(product);
            result.setTotal(count);
            if (count == 0) {
                return result;
            }
            productList = mapper.findImage(product);
        }
        if (productList == null || productList.isEmpty()) {
            result.setDesc("不存在该筛选条件的产品！");
            result.setItem(productList);
            result.setCode(RestResult.NOTICE);
            return result;
        }
        result.setItem(productList);
        result.setDesc("产品列表查询成功!");
        return result;
    }

    /**
     * 统计排重列表产品总条数
     */
    public RestResult countImage(Product product, boolean isSystemMatch) {
        RestResult result = new RestResult();
        List<Integer> departmentIds = getDepartmentViewByCurrentUser();
        product.setDepartmentIds(departmentIds);
        if (isSystemMatch) {
            int count = mapper.countByProductNewId(product.getProductNewId());
            result.setTotal(count);
        } else {
            // 如果有品类的搜索
            if (product.getCategoryId() != null) {
                List<Integer> cateIds = findCategoryInfo(product.getCategoryId());
                product.setCategoryIds(cateIds);
            }
            int count = mapper.countImage(product);
            result.setTotal(count);
        }
        result.setCode(RestResult.OK);
        result.setDesc("排重列表产品总条数查询成功!");
        return result;
    }

    /**
     * 获取品类及其下级
     */
    private List<Integer> findCategoryInfo(Integer categoryId) {
        if (categoryId == null) return null;
        List<Integer> categoryIds = new ArrayList<>();
        Category category = categoryService.getById(categoryId);
        if (category.getLeaf()) {
            categoryIds.add(categoryId);
        } else {
            List<Category> lst = categoryService.findByNo(category.getNo());
            categoryIds = lst.stream().map(Category::getId).collect(Collectors.toList());
        }
        return categoryIds;
    }


    /**
     * 获取产品及其属性信息
     */
    public RestResult getInfo(Integer id) {
        RestResult result = new RestResult();
        if (id == null) return result;
        Product product = mapper.getDetails(id);
        Assert.notNull(product, "产品ID[" + id + "]对应的产品信息不存在,无法查询详细信息!");
        List<Attribute> attributes = attributeService.findByProductId(id);
        product.setAttributeList(attributes);
        result.setCode(RestResult.NOTICE);
        result.setDesc("产品详细信息查询成功!");
        result.setItem(product);
        return result;
    }

    /**
     * 产品列表的查询(关联表==》产品表，产品区域表)
     */
    public RestResult queryList(Product product) {
        Assert.notNull(product, "查询的product信息不能为空");
        RestResult result = new RestResult();
        //品类设置
        if (product.getCategoryId() != null) {
            List<Integer> categoryIds = new ArrayList<Integer>();
            Category category = categoryService.getById(product.getCategoryId());
            if (category == null) {
                categoryIds.add(-1);
                product.setCategoryIds(categoryIds);
            } else {
                List<Category> lst = categoryService.findByNo(category.getNo());
                categoryIds = lst.stream().map(Category::getId).collect(Collectors.toList());
                product.setCategoryIds(categoryIds);
            }
        }
//	   参考一下；
//		Optional.ofNullable(product.getCategoryId()).ifPresent(productCategoryId->{
//
//			product.setCategoryIds(Arrays.asList(-1));
//
//			Category category = categoryService.getById(productCategoryId);
//			Optional.ofNullable(category).ifPresent(item->{
//				List<Category> categoryListFindByNo = categoryService.findByNo(category.getNo());
//				product.setCategoryIds(categoryListFindByNo.stream().map(Category::getId).collect(Collectors.toList()));
//			});
//		});
        int count = mapper.countQueryListPz(product);
        result.setTotal(count);
        if (count == 0) {
            return result;
        }

        List<Product> list = mapper.queryListPz(product);
        // 为产品绑定语言包
        list = bindProductLang(list);
        //list设置品类路径
        List<Integer> categoryIds = new ArrayList<Integer>();
        categoryIds = list.stream().map(Product::getCategoryId).collect(Collectors.toList());
        List<Category> categoryList = categoryService.findByIds(categoryIds);
        for (Category lst : categoryList) {
            lst.setName(categoryService.getSuperiorNameByNo(lst.getNo(), "＞"));
        }
        Map<Integer, List<Category>> map = categoryList.stream().collect(Collectors.groupingBy(Category::getId));
        for (Product lst : list) {
            List<Category> categorylist = map.get(lst.getCategoryId());
            if (categorylist == null || categorylist.size() == 0) {
                categorylist = new ArrayList<Category>();
                Category category = new Category();
                category.setName("品类错误");
                categorylist.add(category);
            }
            String name = categorylist.get(0).getName();
            lst.setName(name);
        }
        result.setItem(list);
        result.setDesc("产品列表查询成功!");
        return result;
    }

    /**
     * 产品列表的查询
     */
    public RestResult queryApproveList(Product product) {
        RestResult result = new RestResult();
        List<Integer> idList = getDepartmentViewByCurrentUser();
        product.setDepartmentIds(idList);
        int count = mapper.countQueryList(product);
        result.setTotal(count);
        if (count == 0) {
            return result;
        }
        List<Product> list = mapper.queryList(product);
        // 为产品绑定语言包
        list = bindProductLang(list);
        result.setItem(list);
        result.setDesc("产品列表查询成功!");
        return result;
    }

    /**
     * 根据品类查询产品
     */
    public List<Product> findProductByCategoryId(Integer categoryId) {
        return mapper.findProductByCategoryId(categoryId);
    }

    /**
     * 检查产品库的spu
     */
    public int countCheckSpu(String spu) {
        Assert.notNull(spu, "请输入spu");
        Assert.isTrue(!"".equals(spu), "请输入spu");
        return mapper.countCheckSpu(spu);
    }

    /**
     * 产品详情(个人)
     */
    public Product getDetails(Integer id) {
        Product product = commonProductDetails(id);
        ProductZone zone = new ProductZone();
        zone.setProductId(id);
        zone.setCreatorId(MBox.getLoginUserId());
        product.setProductZoneList(productZoneService.find(zone));
        return product;
    }

    /**
     * 主管助理_产品详情
     */
    public Product getApproveDetails(Integer id) {
        List<Integer> idList = getDepartmentViewByCurrentUser();
        Product product = commonProductDetails(id);
        ProductZone zone = new ProductZone();
        zone.setProductId(id);
        zone.setDepartmentIds(idList);
        product.setProductZoneList(productZoneService.find(zone));
        return product;
    }

    /**
     * 开发权限_产品详情
     */
    public Product getAllDetails(Integer id) {
        Product product = commonProductDetails(id);
        product.setProductZoneList(productZoneService.findByProductId(id));
        return product;
    }


    /**
     * 当前用户的部门数据权限
     * 返回用户部门id集合
     */
    public List<Integer> getDepartmentViewByCurrentUser() {
        List<Integer> idLists = new ArrayList<>();
        List<UserDepartmentRel> noList = iUserDepartmentRelService.findByUserId(MBox.getLoginUserId(), true);
        Department dept = iDepartmentService.get(ThreadLocalUtils.getUser().getDeptId());
        Assert.notNull(dept, "当前用户[ID:" + MBox.getLoginUserId() + "]的部门信息有误[为null]!");
//		idLists.add(MBox.getLoginUser().getDepartmentId());// 获取登录用户的部门id
        List<String> nos = noList.stream().map(UserDepartmentRel::getDepartmentNo).collect(Collectors.toList());
        nos.add(dept.getDepartmentNo());
        for (String no : nos) {
            List<Department> list = iDepartmentService.findByNo(no);
            Set<Integer> lst = list.stream().map(Department::getId).collect(Collectors.toSet());
            idLists.addAll(lst);
        }
        return idLists;
    }

    private Product commonProductDetails(Integer productId) {
        Product product = mapper.getDetails(productId);
        Assert.notNull(product, "没有查询到该产品的信息");
        product.setName(categoryService.getSuperiorNameByNo(product.getNo(), "＞"));
        if (product.getNo() == null) {
            return product;
        }
        // 产品属性
        List<Attribute> list = attributeService.findByProductId(MBox.ERP_ATTRIBUTE_VERSION, productId);
        List<Attribute> oldList = attributeService.findByOldProductId(productId);
        for (Iterator<Attribute> iter = oldList.iterator(); iter.hasNext(); ) {
            if (MBox.ERP_ATTRIBUTE_VERSION == iter.next().getVersion()) {
                iter.remove();
            }
        }
        list.addAll(oldList);
        product.setAttributeList(list);
        product.setProductLangList(productLangService.findProductLang(productId));
        return product;
    }

    /**
     * sku生成
     */
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RestResult productSkuGenerateTrans(Integer productId, List<ProductSkuAttributeValueRel> listParam) {
        RestResult result = new RestResult();
        Product product = generateSkuCheck(productId);
        Map<String, String> skuMap = AttributeValueSkuKeyMap(productId);
        String maxSku = productSkuAttributeValueRelService.maxSkuByProductId(productId);
        // maxSku检查
        Integer sku = resultMaxSkuCheck(maxSku);
        List<ProductAttributeRel> list = productAttributeRelService.findByProductId(productId);
        Map<Integer, List<ProductAttributeRel>> relMap = list.stream().collect(Collectors.groupingBy(ProductAttributeRel::getAttributeId));
        for (ProductSkuAttributeValueRel rel : listParam) {
            //生成产品sku
            if (rel.getAttributeValueIds() != null && rel.getAttributeValueIds().isEmpty()) {
                generateSingleSku(productId);
            } else {
                Assert.isTrue(rel.getAttributeValueIds().size() == list.size(), "sku生成失败,请检查每个属性下是否都绑定了属性值!");
                List<AttributeValue> attrList = attributeValueService.findByIds(rel.getAttributeValueIds());
                StringBuilder sb = new StringBuilder();
                for (AttributeValue attrVal : attrList) {
                    sb.append(attrVal.getTitle() + "^");
                }
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
                generateSku(product, rel.getAttributeValueIds(), skuMap, (sku++).toString(), relMap, sb.toString());
            }
        }
        result.setCode(RestResult.NOTICE);
        result.setDesc("产品sku生成成功!");
        return result;
    }

    /**
     * 生成单个sku
     */
    public void generateSingleSku(Integer productId) {
        Product product = generateSkuCheck(productId);
        int countSku = productSkuService.countByProductId(productId);
        Assert.isTrue(countSku == 0, "该产品已经有了唯一的sku,不能再生成新的sku");
        //检查该产品是否绑定了属性
        int count = productAttributeRelService.countByProductId(productId);
        Assert.isTrue(count == 0, "该产品生成sku的数据有误[有绑定属性,但是没有绑定属性值]");
        String skugenerate = SkuCombination.generateSku(product.getSpu(), null);
        Assert.notNull(skugenerate, "产品[ID" + productId + "]sku有误[后5位不是纯数字],请联系管理员排查");
        ProductSku productSku = productSkuAssignment(productId, product.getSpu(), skugenerate, null);
        productSkuService.insert(productSku);
    }

    /**
     * 生成所有的sku
     */
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void generateAllSku(Integer productId) {
        Product product = generateSkuCheck(productId);
        Map<String, String> skuMap = AttributeValueSkuKeyMap(productId);
        List<ProductSku> ps = productSkuList(productId);
        List<ProductSku> shouldGenerated = ps.stream().filter(e -> e.getSku() == null).collect(Collectors.toList());
        String maxSku = productSkuAttributeValueRelService.maxSkuByProductId(product.getId());
        // maxSku检查
        Integer sku = resultMaxSkuCheck(maxSku);
        List<ProductAttributeRel> list = productAttributeRelService.findByProductId(productId);
        Map<Integer, List<ProductAttributeRel>> relMap = list.stream().collect(Collectors.groupingBy(ProductAttributeRel::getAttributeId));
        for (ProductSku ps2 : shouldGenerated) {
            List<Integer> attributeValueIds = ps2.getAttributeValues().stream().map(AttributeValue::getId).collect(Collectors.toList());
            if (attributeValueIds == null || attributeValueIds.isEmpty()) {
                generateSingleSku(productId);
                return;
            }
            if (attributeValueIds.size() == 1 && attributeValueIds.get(0) == null) {
                generateSingleSku(productId);
                return;
            }
            Assert.isTrue(attributeValueIds.size() == list.size(), "sku生成失败,请检查每个属性下是否都绑定了属性值!");
            generateSku(product, attributeValueIds, skuMap, (sku++).toString(), relMap, ps2.getAttributeValueTitle());
        }
    }

    /**
     * 根据产品id和属性值组合  生成sku
     *
     * @param product           产品实体,获取产品id
     * @param attributeValueIds 属性值的组合
     * @param maxSku            当前库中最大的sku
     * @param relMap            产品属性关系表
     */
    public void generateSku(
            Product product,
            List<Integer> attributeValueIds,
            Map<String, String> skuMap,
            String maxSku,
            Map<Integer, List<ProductAttributeRel>> relMap,
            String attrValTitle) {
        //判断该组合是否有sku
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < attributeValueIds.size(); i++) {
            sb.append(attributeValueIds.get(i) + "_");
        }
        if (sb.toString().length() > 0) {
            sb.deleteCharAt(sb.toString().length() - 1);
        }
        if (skuMap != null && !skuMap.isEmpty()) {
            Assert.isNull(skuMap.get(sb.toString()), "该组合[" + sb.toString() + "]已经有了sku");
        }
        String skugenerate = SkuCombination.generateSku(product.getSpu(), maxSku);
        Assert.notNull(skugenerate, "产品[ID" + product.getId() + "]sku有误[后5位不是纯数字],请联系管理员排查");
        ProductSku productSku = productSkuAssignment(product.getId(), product.getSpu(), skugenerate, attrValTitle);
        List<ProductSkuAttributeValueRel> rels = new ArrayList<>();//批量
        //判断属性值是否有对应的属性
        List<AttributeValue> attrList = attributeValueService.findByIds(attributeValueIds);
        Map<Integer, List<AttributeValue>> map = attrList.stream().collect(Collectors.groupingBy(AttributeValue::getId));
        for (Integer attrValueId : attributeValueIds) {
            Assert.notNull(map.get(attrValueId), "属性值[ID" + attrValueId + "]没有对应的属性");
            Integer attributeId = map.get(attrValueId).get(0).getAttributeId();
            Assert.notNull(relMap.get(attributeId), "属性[ID" + attributeId + "]没有绑定相应的产品!");
            ProductSkuAttributeValueRel rel = relAssignment(product.getId(), map.get(attrValueId).get(0).getAttributeId(),
                    attrValueId, skugenerate, relMap.get(attributeId).get(0).getId());
            rels.add(rel);
        }
        productSkuAttributeValueRelService.insertList(rels);
        productSkuService.insert(productSku);
    }

    /**
     * 产品sku列表
     */
    public List<ProductSku> productSkuList(Integer productId) {
        List<ProductSku> combinationAttrList = new ArrayList<>();// 返回的结果
        List<List<AttributeValue>> combinations = combinationsAttrValue(productId);
        // 通过产品id,获取所有的sku
        List<ProductSku> productSkus = productSkuService.findByProductId(productId);
        List<ProductSkuAttributeValueRel> productSkuAttributeValueRels =
                productSkuAttributeValueRelService.findByProductId(productId);
        //key为sku,value为属性值组合
        Map<String, List<ProductSkuAttributeValueRel>> skuAvalueRelMap =
                productSkuAttributeValueRels.stream().collect(Collectors.groupingBy(ProductSkuAttributeValueRel::getSku));
        //获取属性值id为key,sku为value的一个map
        Map<String, String> avKeySkuValueMap = new HashMap<>();
        convertSkuMap(skuAvalueRelMap, avKeySkuValueMap, productSkus);
        if (productSkus.size() == 1 && skuAvalueRelMap.size() == 0) {
            //当前产品没有绑定属性值，但是有sku
            ProductSku skuEntity = noCombination();
            skuEntity.setSku(productSkus.get(0).getSku());
            combinationAttrList.add(skuEntity);
            return combinationAttrList;
        }
        // combinations包装返回
        for (int i = 0; i < combinations.size(); i++) {
            List<AttributeValue> list = combinations.get(i);
            String key = "NULL";
            Integer attributeId = null;
            if (list != null && !list.isEmpty()) {
                key = getKeyByAttributeValueRelIds(list.stream().map(AttributeValue::getId).collect(Collectors.toList()));
                attributeId = list.get(0).getAttributeId();
            }
            ProductSku skuEntity = new ProductSku();
            skuEntity.setAttributeValues(list);
            String sku = avKeySkuValueMap.get(key);
            skuEntity.setSku(sku);
            skuEntity.setAttributeId(attributeId);
            StringBuilder sb = new StringBuilder();
            for (AttributeValue val : list) {
                sb.append(val.getTitle() + "^");
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
            skuEntity.setAttributeValueTitle(sb.toString());
            combinationAttrList.add(skuEntity);
        }
        if (combinationAttrList.isEmpty()) {
            combinationAttrList.add(noCombination());
        }
        return combinationAttrList;
    }

    /**
     * 属性值sku组合的map
     */
    private Map<String, String> AttributeValueSkuKeyMap(Integer productId) {
        // 通过产品id,获取所有的sku
        List<ProductSku> productSkus = productSkuService.findByProductId(productId);
        List<ProductSkuAttributeValueRel> productSkuAttributeValueRels =
                productSkuAttributeValueRelService.findByProductId(productId);
        Map<String, List<ProductSkuAttributeValueRel>> skuAvalueRelMap =
                productSkuAttributeValueRels.stream().collect(Collectors.groupingBy(ProductSkuAttributeValueRel::getSku));
        // 获取属性值id为key,sku为value的一个map
        Map<String, String> avKeySkuValueMap = new HashMap<>();
        convertSkuMap(skuAvalueRelMap, avKeySkuValueMap, productSkus);
        return avKeySkuValueMap;
    }

    /**
     * map转换
     * 将skuAvalueRelMap转换成avKeySkuValueMap
     */
    private void convertSkuMap(
            Map<String, List<ProductSkuAttributeValueRel>> skuAvalueRelMap,
            Map<String, String> avKeySkuValueMap,
            List<ProductSku> productSkus) {
        for (ProductSku ps : productSkus) {
            String key = "NULL";
            if (skuAvalueRelMap.containsKey(ps.getSku())) {
                List<ProductSkuAttributeValueRel> valueRels = skuAvalueRelMap.get(ps.getSku());
                key = getKeyByAttributeValueRelIds(valueRels.stream()
                        .map(ProductSkuAttributeValueRel::getAttributeValueId).collect(Collectors.toList()));
            }
            avKeySkuValueMap.put(key, ps.getSku());
            logger.debug("组合sku的属性值对应map,key: {} , sku:{}", key, ps.getSku());
        }
    }

    /**
     * 传入sku,返回int类型的数字,String ==> int
     */
    private Integer resultMaxSkuCheck(String maxSku) {
        if (maxSku == null || "".equals(maxSku)) {
            return 0;
        } else {
            maxSku = maxSku.substring(maxSku.length() - MBox.ERP_SKU_LENGTH, maxSku.length());
            Assert.isTrue(maxSku.matches("^[0-9]{1,5}$"), "sku最后五位[" + maxSku + "]不是纯数字,请联系管理员!");
            return Integer.parseInt(maxSku);
        }
    }

    /**
     * 产品sku的组合情况
     * 属性值的组合情况
     */
    private List<List<AttributeValue>> combinationsAttrValue(Integer productId) {
        List<List<AttributeValue>> combinations = new ArrayList<>();
        List<Attribute> attributeList = attributeService.queryByProductId(productId);
        List<AttributeValue> attributeValues = attributeValueService.findAttrValueByProductId(productId);
        Map<Integer, List<AttributeValue>> attributeValueMap = attributeValues.stream()
                .collect(Collectors.groupingBy(AttributeValue::getAttributeId));
        for (Attribute attribute : attributeList) {
            Assert.notNull(attribute, "该产品[ID:" + productId + "]绑定了一个空属性,请联系管理员排查");
            List<AttributeValue> attrVals = attributeValueMap.get(attribute.getId());
            Assert.notNull(attrVals, "系统组合SKU失败,请检查该产品的每个属性是否都绑定了属性值!");
            SkuCombination.combinateOne(attrVals, combinations);
        }
        return combinations;
    }

    /**
     * 属性值id的组合
     */
    private String getKeyByAttributeValueRelIds(List<Integer> valueRels) {
        return StringUtils.join(valueRels, '_');
    }

    /**
     * 没有属性值的组合情况
     */
    private ProductSku noCombination() {
        ProductSku productSku = new ProductSku();
        AttributeValue value = new AttributeValue();
        List<AttributeValue> list = new ArrayList<AttributeValue>();
        value.setTitle("无");
        list.add(value);
        productSku.setAttributeValues(list);
        return productSku;
    }

    private Product generateSkuCheck(Integer productId) {
        Product product = mapper.getById(productId);
        Assert.notNull(product, "该产品[ID:" + productId + "]不存在,无法生成sku");
        Assert.notNull(product.getSpu(), "该产品[ID:" + productId + "]的spu不存在!");
        return product;
    }

    /**
     * 产品sku实体类组装
     * String attrValTitle
     */
    private ProductSku productSkuAssignment(Integer productId, String spu, String sku, String attrValTitle) {
        ProductSku productSku = new ProductSku();
        productSku.setProductId(productId);
        productSku.setSpu(spu);
        productSku.setSku(sku);
        productSku.setBarcode(sku);
        if (attrValTitle == null) attrValTitle = "";
        productSku.setAttributeValueTitle(attrValTitle);
        return productSku;
    }

    /**
     * 产品sku属性值关系表组装
     */
    private ProductSkuAttributeValueRel relAssignment(Integer productId, Integer attributeId, Integer attributeValueId, String sku, Integer productAttributeId) {
        ProductSkuAttributeValueRel rel = new ProductSkuAttributeValueRel();
        rel.setProductId(productId);
        rel.setAttributeId(attributeId);
        rel.setAttributeValueId(attributeValueId);
        rel.setProductAttributeId(productAttributeId);
        rel.setSku(sku);
        return rel;
    }

    /**
     * 产品查询
     */
    public Product getById(Integer productId) {
        return mapper.getById(productId);
    }

    public List<Product> getByIds(List<Integer> productIds) {
        if (productIds == null || productIds.size() == 0) return new ArrayList<>();
        return mapper.getByIds(productIds);
    }

    /**
     * 根据产品内部名获取产品信息
     */
    public Product getByInnerName(String innerName) {
        MBox.assertLenth(innerName, "内部名", 2, 200);
        return mapper.getByInnerName(innerName);
    }

    /**
     * 产品查询
     */
//	@Cacheable(value = "getProductById", unless = "#result == null")
    public Product getByIdSync(Integer productId) {
        return mapper.getById(productId);
    }

    public Product loadById(Integer productId) {
        Product product = mapper.getById(productId);
        Assert.notNull(product, "产品id：" + productId + " 在数据库中不存在!");
        return product;
    }

    public Product loadBySpu(String spu) {
        Product product = getBySpu(spu);
        Assert.notNull(product, "spu：" + spu + " 在数据库中不存在!");
        return product;
    }

    public Product getBySpu(String spu) {
        Product product = mapper.getBySpu(spu);
        return product;
    }

    /**
     * 根据产品ID修改产品特性
     */
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updatePushProduct(Product param){
        Product product = loadById(param.getId());
        String classisyEnum = product.getClassifyEnumName();
        String innerName = product.getInnerName();
        product.setClassifyEnum(param.getClassifyEnum());
        product.setInnerName(param.getInnerName());
        HashSet<String> ements = Sets.newHashSet(ClassifyEnum.D.name(),ClassifyEnum.S.name(), ClassifyEnum.Y.name());
//        if (ements.contains(param.getClassifyEnum())){
            mapper.update(product);
            //同步老erp
            try {
                productPushService.pushProductThing(product.getId());
            }catch(Exception e){
                Assert.isTrue(false, "修改产品信息时,同步到老ERP出现异常,产品ID[" + product.getId() + "]");
            }
            try {
                List<ProductSku> list = productSkuService.findByProductId(product.getId());
                List<String> skuList = list.stream().map(ProductSku::getSku).collect(Collectors.toList());
                for (String sku : skuList){
                    iWmsService.repostBySku(sku);
                }
            }catch(Exception e){
                Assert.isTrue(false, "修改产品信息时,同步到WMS出现异常,产品ID[" + product.getId() + "]");
            }
            pcProductFsmProxyService.processLog(product,"产品信息被修改:产品特性由["+classisyEnum+"]修改成["+product.getClassifyEnumName()+"]," +
                    "内部名由["+innerName+"]修改成["+product.getInnerName()+"]," +
                    "修改人:["+ThreadLocalUtils.getUser().getLastName()+"部门:"+ThreadLocalUtils.getUser().getDeptName()+"]");
//        }else {
//            Assert.isTrue(false, "产品特性修改不合法,未定义的枚举类型["+product.getClassifyEnum()+"]==>["+product.getClassifyEnumName()+"]");
//        }
    }


    /**
     * 产品列表总条数查询
     */
    public Integer findListCount(Product product) {
        return mapper.count(product);
    }


// ---------------------------------------数据同步--------------------------------------

    public List<Product> findByDate(Product product) {
        Assert.notNull(product, "根据时间查询，入参不允许为空！");
        Assert.notNull(product.getMinCreateAt(), "起始时间不允许为空！");
        Assert.notNull(product.getMaxCreateAt(), "结束时间不允许为空！");
        return mapper.findByDate(product);
    }

    public int countByDate(LocalDateTime startTime, LocalDateTime endTime) {
        Assert.notNull(startTime, "起始时间不允许为空！");
        Assert.notNull(endTime, "结束时间不允许为空！");
        return mapper.countByDate(startTime, endTime);
    }

    public void insertOld(Product product) {
        Assert.notNull(product, "新增的产品不允许为空！");
        int i = 0;
        try {
            i = mapper.insertOld(product);
        } catch (DuplicateKeyException e) {
            logger.info("产品{}已经存在！", product);
        }
        Assert.isTrue(i == 1, "新增产品失败！");
        logger.info("同步老erp时，新增产品{}成功！", product);
    }

    public void updateOld(Product product) {
        Assert.notNull(product, "修改产品不允许为空！");
        Product productDB = mapper.getById(product.getId());
        Assert.notNull(productDB, "修改的产品在数据库中不存在");
        int i = mapper.updateProduct(product);
        Assert.isTrue(i == 1, "修改产品失败！");
        logger.info("同步老erp时，将产品{}修改为{} 成功！", productDB, product);
    }

    /**
     * 返回根据用户、产品id返回一个产品， 约束条件，当用户id所在的部门没有备案此产品时，将返回空 add by sqg
     * <p>
     * 包装填充产品的信息
     *
     * @param product 产品实体
     * @param seoUser 优化师用户实体
     * @return
     */
    public void wraperFullInfoByProductAndLoginId(Product product, User seoUser) {

        Integer productId = product.getId();
        List<ProductZone> productIdSeoUserDepartmentIdFindProductZoneList = productZoneService.findByProductId(productId);

        Assert.notNull(productIdSeoUserDepartmentIdFindProductZoneList, "产品id " + productId + "无对应的区域");

        Set<Integer> childrenDepartmentIdSet = getChildrenDepartmentIds(seoUser.getDepartmentId(), Sets.newHashSet());

        productIdSeoUserDepartmentIdFindProductZoneList = productIdSeoUserDepartmentIdFindProductZoneList.stream().filter(productZone -> childrenDepartmentIdSet.contains(productZone.getDepartmentId()))
                .collect(Collectors.toList());

        Assert.isTrue(CollectionUtils.isNotNullAndEmpty(productIdSeoUserDepartmentIdFindProductZoneList), "优化师loginid：" + seoUser.getLoginid() + "对应的部门没有申报此产品！");

        //////过滤去掉消档和下架的产品区域 add by carter 20171031
        productIdSeoUserDepartmentIdFindProductZoneList = productIdSeoUserDepartmentIdFindProductZoneList.stream().filter(item -> !Arrays.asList("offsale", "disappeared").contains(item.getState())).collect(Collectors.toList());


        ////////user表的 department_id对应关系跟 老ERP的对应不上； 是因为OA跟老ERP对应不上 这里先不做判断，待准确对应之后在做判断  todo
//				productZoneService.findByProductIdAndDepartmentId(productId, seoUser.getDepartmentId());
//		Assert.isTrue(CollectionUtils.isNotNullAndEmpty(productIdSeoUserDepartmentIdFindProductZoneList), "优化师loginid：" + seoUser.getLoginid() + "对应的部门没有申报此产品！");

        Optional.ofNullable(productIdSeoUserDepartmentIdFindProductZoneList).ifPresent(item -> item.forEach(productZone -> {
            Department department = iDepartmentService.get(productZone.getDepartmentId());
            if (null != department)
                productZone.setDepartmentOldId(String.valueOf(Optional.ofNullable(department.getOldId()).orElse(0)));

            Zone zone = zoneService.getByCode(productZone.getZoneCode());
            Optional.ofNullable(zone).ifPresent(zoneItem -> productZone.setZoneId(zone.getId()));

        }));
        product.setProductZoneList(productIdSeoUserDepartmentIdFindProductZoneList);
        ////////////////////////////////////////////////////////////////////////////////包装产品区域信息，特别设置oldErpDepartmentId，提供给到建站端，用来处理订单部分逻辑

        Optional.ofNullable(product.getProductZoneList()).ifPresent(item -> item.forEach(productZone -> {
            List<Integer> departmentIds = product.getDepartmentIds();
            if (!departmentIds.contains(productZone.getDepartmentId()))
                departmentIds.add(productZone.getDepartmentId());

            List<String> departmentNos = product.getDepartmentNos();
            if (!departmentNos.contains(productZone.getDepartmentNo()))
                departmentNos.add(productZone.getDepartmentNo());
        }));
        ////////////////////////////////////////////////////////////设置产品的部门信息,装成id列表，建站端用使用这种格式的

        fillProductAttribute(product);

    }

    private Set<Integer> getChildrenDepartmentIds(Integer departmentId, Set<Integer> departmentIdSet) {

        departmentIdSet.add(departmentId);

        List<Department> departmentList = iDepartmentService.findByParentId(departmentId);
        if (CollectionUtils.isNullOrEmpty(departmentList)) {
            return departmentIdSet;
        }

        for (Department department : departmentList) {
            departmentIdSet.addAll(getChildrenDepartmentIds(department.getId(), departmentIdSet));
        }

        return departmentIdSet;


    }

    /**
     * 填充产品的属性信息；
     *
     * @param product 产品实体
     */
    public void fillProductAttribute(Product product) {

        Integer productId = product.getId();
        List<Attribute> productIdFindAttributeList = attributeService.findByProductIdUseRelId(productId);
        if (CollectionUtils.isNullOrEmpty(productIdFindAttributeList)) {
            logger.warn("产品没有属性:" + product.getTitle());
            return;
        }


        List<Integer> productAttributeIdList = productIdFindAttributeList.stream().map(Attribute::getId).collect(Collectors.toList());

        // 填充属性名的多语言
        List<AttributeLang> attributeLangList = attributeLangService.findByAttributeIds(productAttributeIdList);
        if (CollectionUtils.isNotNullAndEmpty(attributeLangList)) {
            Map<Integer, List<AttributeLang>> langMap = attributeLangList.stream().collect(Collectors.groupingBy(AttributeLang::getAttributeId));
            productIdFindAttributeList.stream().forEach(e -> e.setAttributeLangs(langMap.get(e.getId())));
        }


        // 填充属性值的多语言
        List<AttributeValue> attributeValueList = attributeValueService.findByProductId(productId, true);
        if (CollectionUtils.isNotNullAndEmpty(attributeValueList)) {
            //////////////////attributeid 的分组
            Map<Integer, List<AttributeValue>> avMap = attributeValueList.stream().collect(Collectors.groupingBy(AttributeValue::getAttributeId));
            productIdFindAttributeList.stream().forEach(e -> e.setAttributeValueList(avMap.get(e.getId())));

            ///////////////////////属性值的多语言
            List<Integer> avIds = attributeValueList.stream().map(AttributeValue::getId).collect(Collectors.toList());
            List<AttributeValueLang> attributeValueLangs = attributeValueLangService.findByAttributeValueIds(avIds);
            //////////////////////属性值id 对应的多语言分组
            Map<Integer, List<AttributeValueLang>> avLangMap = attributeValueLangs.stream().collect(Collectors.groupingBy(AttributeValueLang::getAttributeValueId));
            attributeValueList.stream().forEach(e -> e.setAttributeValueLangs(avLangMap.get(e.getId())));
        }

        //转换一下id为老erp系统对应的id信息；
        product.setAttributeList(productIdFindAttributeList.stream().map(attribute -> {
            attribute.setId(attribute.getProductAttributeRelId());

            Optional.ofNullable(attribute.getAttributeValueList()).ifPresent(item -> item.stream().map(attributeValue -> {
                attributeValue.setId(attributeValue.getProductAttributeValueRelId());
                attributeValue.setAttributeId(attribute.getProductAttributeRelId());
                return attributeValue;
            }).collect(Collectors.toList()));

            return attribute;
        }).collect(Collectors.toList()));

    }

    public void updateWaitFillFsm(Integer id) {
//		Product product = getAllDetails(id);
        Product product = mapper.getById(id);
        Assert.notNull(product, "产品信息有误,待填充提交失败!");
        //待填充到建档中的sku校验
        productSkuList(id);
        processEvent(product, ProductEvent.fill, "产品填充完成，状态改为建档中", ThreadLocalUtils.getUser().getLoginid());
    }

    /**
     * 根据图片路径获取到对应产品id的结果集
     *
     * @param matchPaths 图片路径
     * @return id的结果集
     */
    public List<Integer> findByImagePaths(List<String> matchPaths) {
        Assert.notNull(matchPaths, "产品图片路径的结果集不允许为空！");
        return mapper.findByImagePaths(matchPaths);
    }

    /**
     * 查询所有待细化分类的产品
     *
     * @return
     */
    public Integer countByDivide(Product product) {
        return mapper.countByDivide(product);
    }

    public List<Product> findByDivide(Product product) {
        Assert.notNull(product, "入参不允许为空！");
        return mapper.findByDivide(product);
    }

    public void updateCategoryById(Integer categoryId, Integer id) {
        Assert.notNull(categoryId, "品类id不能为空！");
        Assert.notNull(id, "产品id不能为空！");
        Product product = mapper.getById(id);
        Assert.notNull(product, "产品id对应的产品在数据库中不存在！");
        mapper.updateCategoryById(categoryId, id);
        logger.info("将产品{}的分类更新为{}成功！", product, categoryId);
    }

    public List<Category> findCategoryByDivide() {
        return categoryService.findByDivide();
    }


    public void updateCategoryBatchById(List<Integer> productIds, Integer categoryId) {
        Assert.notNull(productIds, "批量细化分类的产品id集合不允许为空！");
        Assert.isTrue(!productIds.isEmpty(), "批量细化分类的产品id集合不允许为空！");
        mapper.updateCategoryBatchById(productIds, categoryId);
        logger.info("将产品id为{}的品类更新为{}成功！", productIds, categoryId);
    }

    public Product findProductBySpu(String spu) {
        return mapper.findProductBySpu(spu);
    }

    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public RestResult revokeProduct(Integer id) {
        RestResult result = new RestResult();
        Product product = mapper.getById(id);
        Assert.notNull(product, "要撤销的产品为空！");
        String memo = "产品id为{}的产品申请撤回！";
        processEvent(product, ProductEvent.revoke, memo, MBox.getSysUser());
        logger.info("撤回产品{}成功！", product);
        result.setCode(RestResult.NOTICE);
        result.setDesc("产品 " + product.getTitle() + " 撤回成功！");
        return result;
    }


    public List<Product> findProductBySupList(Set<String> spuList) {
        Assert.notEmpty(spuList, "spu列表不能为空");
        return mapper.findBySpuList(spuList);
    }

    public String getProductSourceUrl(Integer skuId) {
        if (skuId == null) return null;
        return mapper.getProductSourceUrl(skuId);
    }

    public List<Product> findByWebDirectory(String webDirectory) {
        if (webDirectory == null || "".equals(webDirectory)) return null;
        return mapper.findByWebDirectory(webDirectory,"http://"+webDirectory);
    }

    public List<Product> findBySkuList(List<String> skuList) {
        if (skuList == null || skuList.size() == 0) {
            logger.info("根据sku查询产品信息的时候,参数为空 ,直接返回null");
            return null;
        }
        return mapper.findBySkuList(skuList);
    }
}
