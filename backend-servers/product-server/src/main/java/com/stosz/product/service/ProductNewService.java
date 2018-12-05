package com.stosz.product.service;

import com.google.common.collect.Sets;
import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.fsm.FsmProxyService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.enums.CategoryUserTypeEnum;
import com.stosz.product.ext.enums.ProductNewEvent;
import com.stosz.product.ext.enums.ProductNewState;
import com.stosz.product.ext.model.*;
import com.stosz.product.ext.service.IProductNewService;
import com.stosz.product.mapper.ProductNewMapper;
import com.stosz.product.mapper.ProductNewZoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 新品管理公用service
 * @author Administrator
 *
 */
@Service
public class ProductNewService implements IProductNewService {
	@Resource
	private ProductNewMapper productNewMapper;
	@Resource
	private ProductNewZoneMapper productNewZoneMapper;
	@Resource
	private SensitiveWordService sensitiveWordService;
	@Resource
	private FsmProxyService<ProductNew> pcProductNewFsmProxyService;
    @Resource
	private ProductService productService;
    @Resource
    private ProductZoneService productZoneService;
    @Resource
    private CategoryService categoryService;
	@Resource
	private CategoryUserRelService categoryUserRelService;
	@Resource
	private ProductNewZoneService productNewZoneService;
	@Resource
	private IDepartmentService iDepartmentService;

	@Value("${product_new.morning_time}")
	private Integer morningTime;
	@Value("${product_new.morning_branch}")
	private Integer morningBranch;
	@Value("${product_new.morning_second}")
	private Integer morningSecond;
	@Value("${product_new.afternoon_time}")
	private Integer afternoonTime;
	@Value("${product_new.afternoon_branch}")
	private Integer afternoonBranch;
	@Value("${product_new.afternoon_second}")
	private Integer afternoonSecond;
	@Value("${product_new.advertising_submit_number}")
	private Integer advertisingSubmitNumber;
	@Value("${product_new.noAstrictDeptIds}")
	private String noAstrictDeptIds;
	@Value("${product_new.userLabel}")
	private String userLabels;
	private Set<Integer> noAstrictSet = new HashSet<>();
	private Set<Integer> userLabelSet = new HashSet<>();
	private final Logger logger = LoggerFactory.getLogger(getClass());
	HashSet<ProductNewEvent> ements = Sets.newHashSet(ProductNewEvent.duplicate,ProductNewEvent.warn , ProductNewEvent.checkOk);

	public Set<Integer> getNoAstrictSet() {
		try {
			if(noAstrictSet.isEmpty()){
                String[] strs = noAstrictDeptIds.split(",");
                List<Integer> deptList = new ArrayList<>();
                for (int i = 0; i < strs.length; i++) {
                    deptList.add(Integer.parseInt(strs[i]));
                }
                List<Department> list = iDepartmentService.findByIds(deptList);
                Assert.isTrue(list != null && list.size() > 0, "配置信息错误,根据部门ID[" + strs + "]未查询到部门信息!");
                List<String> noList = list.stream().map(Department::getDepartmentNo).collect(Collectors.toList());
                List<Department> ListResult = iDepartmentService.findByNos(noList);
                noAstrictSet = ListResult.stream().map(Department::getId).collect(Collectors.toSet());
            }
		} catch (NumberFormatException e) {
			logger.error("初始化失败，{}！",e);
		}
		return noAstrictSet;
	}

	/**
	 * 黑五类标签
	 */
	private Set<Integer> getUserLabelIdSet() {
		try {
			if(userLabelSet.isEmpty()){
				String[] strs = userLabels.split(",");
				for (int i = 0; i < strs.length; i++) {
					userLabelSet.add(Integer.parseInt(strs[i]));
				}
			}
		} catch (NumberFormatException e) {
			logger.error("初始化失败，{}！",e);
		}
		return userLabelSet;
	}



	/**
	 * 新增新品信息
	 * @param erpProduct
	 * @return
	 * @throws Exception
	 */
	@Transactional(value="pcTxManager")
	public RestResult add(ProductNew erpProduct) {
		RestResult result = new RestResult();
		//updated by xcy 2017-10-19 内部名去重
		String innerName = erpProduct.getInnerName();
		Product productDB = productService.getByInnerName(innerName);
        ProductNew productNewDB = productNewMapper.getByInnerName(erpProduct);
        if ((productDB != null || productNewDB != null) && StringUtils.isEmpty(erpProduct.getSpu())) {
			result.setCode(RestResult.FAIL);
			result.setDesc("操作失败，内部名重复！");
			return result;
		}
		//updated by xcy 2017-10-19 内部名去重

		//1.标题中包含敏感词 不允许保存，敏感词基本就是一些大牌，可以由排重人员随手添加！
		String sensitiveWord = sensitiveWordService.findSensitiveWordInString(erpProduct.getTitle() );
		Assert.isNull(sensitiveWord, "产品标题包含敏感词:" + sensitiveWord);
		erpProduct.setState(ProductNewState.draft.name());
		erpProduct.setStateTime(LocalDateTime.now());
		erpProduct.setCreator(ThreadLocalUtils.getUser().getLastName());
		erpProduct.setCreatorId(MBox.getLoginUserId());
		//2.如果有spu,判断spu he_guitang
		if(erpProduct.getSpu() != null){
			if(!"".equals(erpProduct.getSpu().trim())){
				int count = productService.countCheckSpu(erpProduct.getSpu());
				Assert.isTrue(count == 1, "spu有误,请留空或者重新输入 ");
			} else {
				//spu是空串，直接不存spu
				erpProduct.setSpu(null);
			}
		}
		List<Integer> categoryIds = categoryUserRelService.checkUserCategoryrel(CategoryUserTypeEnum.advertis);
		Assert.isTrue(categoryIds.contains(erpProduct.getTopCategoryId()), "属于你的品类下不包含该产品,无法新建!");
		Assert.isTrue(erpProduct.getDepartmentNo() != null && !"".equals(erpProduct.getDepartmentNo()),"部门编码信息有误,部门编码不能为空");
		productNewMapper.insert(erpProduct);
		pcProductNewFsmProxyService.processNew(erpProduct,"");
		pcProductNewFsmProxyService.processEvent(erpProduct , ProductNewEvent.create , MBox.getLoginUserName());
		logger.info("保存新品成功:{}" , erpProduct);
		result.setCode(RestResult.NOTICE);
		result.setDesc("操作成功!");
		return result;
	}


	public void insertBatch(List<ProductNew> productNewList) {
		Assert.notNull(productNewList, "新品集合不允许为空！");
		Assert.isTrue(!productNewList.isEmpty(), "新品集合不允许为空！");
		Integer i = productNewMapper.insertBatch(0, productNewList);
	}


	/**
	 * 删除产品信息
	 * @return
	 * @throws Exception
	 */
	@Transactional(value="pcTxManager")
	public void delete(Integer id){
		ProductNew p = productNewMapper.getById(id);
		Assert.notNull(p , "新品:" + id + "在数据库中不存在，无法删除！");
		Assert.isTrue(p.getStateEnum() == ProductNewState.draft, "新品:" + id + " 当前已经处于:" + p.getStateName() + "状态，不允许删除！");
		productNewMapper.delete(id);
		productNewZoneMapper.deleteByProductNewId(id);
        int j = pcProductNewFsmProxyService.deleteFsmHistory(ProductNew.class.getSimpleName() , id);

	}


	/**
	 * 修改新品信息
	 * @return
	 */
	@Transactional(value="pcTxManager")
	public RestResult update(ProductNew productNew) {
		RestResult restResult = new RestResult();
		//如果有spu,判断spu he_guitang
		if(productNew.getSpu() != null){
			if(!"".equals(productNew.getSpu().trim())){
				int count = productService.countCheckSpu(productNew.getSpu());
				Assert.isTrue(count == 1, "spu有误,请留空或者重新输入");
			} else {
				productNew.setSpu(null);
			}
		}
		//updated by xcy 2017-10-19 内部名去重
		String innerName = productNew.getInnerName();
		Product productDB = productService.getByInnerName(innerName);
        ProductNew productNewDB = productNewMapper.getByInnerName(productNew);
        if (((productDB != null && Objects.equals(productDB.getInnerName(), productNew.getInnerName())) || productNewDB != null)&& productNew.getSpu() == null) {
            restResult.setCode(RestResult.FAIL);
			restResult.setDesc("操作失败，内部名重复！");
			return restResult;
		}
		//updated by xcy 2017-10-19 内部名去重
		List<Integer> categoryIds = categoryUserRelService.checkUserCategoryrel(CategoryUserTypeEnum.advertis);
		Assert.isTrue(categoryIds.contains(productNew.getTopCategoryId()), "该产品不属于你的品类,无法新建!");
		String sensitiveWord = sensitiveWordService.findSensitiveWordInString(productNew.getTitle() );
		Assert.isNull(sensitiveWord, "产品标题包含敏感词:" + sensitiveWord);
		//新品修改的时候  信息的同步
		ProductNew result= productNewMapper.getById(productNew.getId());
		productNew.setCategoryId(result.getCategoryId());
		productNew.setState(result.getState());
		productNew.setStateTime(result.getStateTime());
		productNew.setCreatorId(result.getCreatorId());
		productNew.setCreator(result.getCreator());
		productNew.setDepartmentId(result.getDepartmentId());
		productNew.setDepartmentNo(result.getDepartmentNo());
		productNew.setDepartmentName(result.getDepartmentName());
		productNew.setCheckerId(result.getCheckerId());
		productNew.setChecker(result.getChecker());
//		productNew.setAdvertTopCategoryId(productNew.getTopCategoryId());
		productNewMapper.update(productNew);
		logger.info("新品修改成功:{}" , productNew);
		restResult.setCode(RestResult.NOTICE);
		restResult.setDesc("新品修改成功!");
		return restResult;
	}


	public void updateSpu(Integer id, String spu) {
		int j = productNewMapper.updateSpu(id, spu);
		logger.info("新品id:{} 更新spu：{} 成功，影响行数：{}" , id,spu,j);
	}


	/**
	 * 修改新品信息
	 */
	@Transactional(value="pcTxManager")
	public RestResult updateCategory(Integer id, Integer categoryId){
		RestResult result = new RestResult();
		ProductNew productNewTmp = load(id);
		//判断category是否是叶子节点
		Category category = categoryService.getById(categoryId);
		if(category.getLeaf()){
			Assert.isTrue(category.getNo().length() / 2 != 0, "品类[" + category.getName() + "]编码[" + category + "]有误,请联系工程师排重!");
			Category cate = categoryService.getByNo(category.getNo().substring(0, 1));
			productNewMapper.updateCategory(id, categoryId);
			productNewTmp.setCategoryId(categoryId);
			pcProductNewFsmProxyService.processEvent(productNewTmp,ProductNewEvent.assign,"品类细化为：" + categoryId + " - " + category.getName());
			logger.info("新品id:{} -  {} 品类细化成: {} 成功,一级品类ID为: {} !", id, productNewTmp.getTitle(), category.getName());
			result.setCode(RestResult.NOTICE);
		    result.setDesc("新品分类修改成功!");
		    return result;
		}else{
			result.setCode(RestResult.FAIL);
			result.setDesc("细化分类需要细化到该品类的最末级");
			return result;
		}
	}

	/**
	 * 批量细化
	 */
	@Transactional(value="pcTxManager")
	public RestResult updateCategoryBatch(List<Integer> ids, Integer categoryId){
		RestResult result = new RestResult();
//		ProductNew productNewTmp = load(id);
		List<ProductNew> list = productNewMapper.findByIds(ids);
		//判断category是否是叶子节点
		Category category = categoryService.getById(categoryId);
		if(category.getLeaf()){
			Assert.isTrue(category.getNo().length() / 2 != 0, "品类[" + category.getName() + "]编码[" + category + "]有误,请联系工程师排重!");
			Category cate = categoryService.getByNo(category.getNo().substring(0, 1));
			productNewMapper.updateCategoryBatch(ids, categoryId);
			for (ProductNew productNewTmp : list) {
				productNewTmp.setCategoryId(categoryId);
				pcProductNewFsmProxyService.processEvent(productNewTmp, ProductNewEvent.assign, "品类细化为：" + categoryId + " - " + category.getName());
				logger.info("新品id:{} -  {} 品类细化成: {} 成功,一级品类ID为: {} !", productNewTmp.getId(), productNewTmp.getTitle(), category.getName());
			}
			result.setCode(RestResult.NOTICE);
			result.setDesc("新品分类修改成功!");
			return result;
		}else{
			result.setCode(RestResult.FAIL);
			result.setDesc("细化分类需要细化到该品类的最末级");
			return result;
		}
	}


	@Transactional(value="pcTxManager")
	public void processEvent(Integer id , ProductNewState state , ProductNewEvent event,String memo,
							 String spu, List<Integer> productZoneIds, Boolean leftCheckOk){
		ProductNew p = load(id);
		String firstMemo = p.getMemo();
		String fsmMemo = memo;
		if ("图片待匹配通过，状态变为待排重".equals(memo)) memo = "";
		if (firstMemo == null) firstMemo = "";
		if (StringUtils.isEmpty(memo)) memo = "";

		productNewSubmitStint(p, event, state);
//		if (!getNoAstrictSet().contains(p.getDepartmentId())) {
//			//新品提交次数的时间限制
//			if (ProductNewEvent.submit.equals(event) && ProductNewState.draft.equals(state)) {
//				int countProductNew = productNewZoneMapper.countByProductNewId(id);
//				Assert.isTrue(countProductNew > 0, "产品[" + p.getTitle() + "]未设置区域,不能提交!");
//				p.setSubmitTime(LocalDateTime.now());
//				//上午时间界限
//				LocalTime morning_time_limit = LocalTime.of(morningTime, morningBranch, morningSecond);
//				//下午时间界限
//				LocalTime afternoon_time_limit = LocalTime.of(afternoonTime, afternoonBranch, afternoonSecond);
//
//				Assert.isTrue(afternoon_time_limit.isAfter(LocalTime.now()), "提交失败!每天[" + afternoon_time_limit + "]前才能提交新品");
//				ProductNew param = new ProductNew();
//				param.setCreatorId(MBox.getLoginUserId());
//				param.setMaxCreateAt(LocalDateTime.now());
//				int count = 0;
//				if (morning_time_limit.isAfter(LocalTime.now())) {
//					//上午
//					param.setMinCreateAt(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
//					count = productNewMapper.countTimeNumber(param);
//					Assert.isTrue(count < advertisingSubmitNumber, "提交失败!每天[" + morning_time_limit + "]之前最多只能提交[ " + advertisingSubmitNumber + " ]次新品!");
//				} else {
//					//下午
//					param.setMinCreateAt(LocalDateTime.of(LocalDate.now(), morning_time_limit));
//					count = productNewMapper.countTimeNumber(param);
//					Assert.isTrue(count < advertisingSubmitNumber, "提交失败!每天[" + morning_time_limit + "]到[" + afternoon_time_limit + "]之间最多只能提交[ " + advertisingSubmitNumber + " ]次新品!");
//				}
//			}
//		}
		if(  ements.contains(event) ){
            Assert.notNull(p.getCategoryId(),"此新品[ID:" + id + "]尚未细化分类,不允许进行排重操作!");
        }
		Assert.isTrue(p.getStateEnum() == state,"新品[ID:" + id + "]在数据库中的状态已经修改为:[" + p.getStateName()+"]");
        if (spu != null && !"".equals(spu)) {
            p.setSpu(spu);
        }
		if (leftCheckOk){
			p.setSpu("");
		}
        //新品重复
        if (event == ProductNewEvent.duplicate) {
            Assert.notNull(spu,"新品重复必须指出是哪个spu重复了！");
            Assert.isTrue(productZoneIds != null && productZoneIds.size() > 0, "新品重复必须指明重复区域!");
            List<ProductZone> productZoneList = productZoneService.findByIds(productZoneIds);
			Assert.isTrue(productZoneList != null && productZoneList.size() > 0, "跟新品重复的区域不存在,请检查参数!");
			Map<Integer, List<ProductZone>> map = productZoneList.stream().collect(Collectors.groupingBy(ProductZone::getDepartmentId));
			StringBuilder sb = new StringBuilder();
			sb.append("您选择的产品区域与[");
//			for (ProductZone productZone : productZoneList){
//				sb.append(productZone.getDepartmentName() + ":");
//				sb.append(productZone.getCreator());
//				sb.append("选择" + productZone.getZoneName() + "<>");
//			}
//			sb.append("区域重复了,排重友情提示:重复产品ID为[" + productZoneList.get(0).getParentId() + "]");
//			sb.append(memo);
			for (Integer deptId : map.keySet()){
				List<ProductZone> list = map.get(deptId);
				if (list != null && list.size() > 0){
					sb.append(list.get(0).getDepartmentName() + "选择的:");
					for (ProductZone pz : list){
						sb.append(pz.getZoneName() + ",");
					}
				}
			}
			sb.append("区域重复了,重复产品ID为[" + productZoneList.get(0).getProductId() + "]");
			sb.append(memo);

//			fsmMemo = "您选择的产品区域与 [" + pz.getDepartmentName() + ":" + pz.getCreator() + "]选择的[" + pz.getZoneName() + "]区域重复了,排重友情提示:重复产品ID为[" + pz.getProductId() + "] " + memo + " !";
			memo = sb.toString();
			if (p.getDisableMarkup() == null || "".equals(p.getDisableMarkup())) {
				p.setDisableMarkup(ProductNewState.duplication.name());
			} else {
				p.setDisableMarkup(p.getDisableMarkup() + "," + ProductNewState.duplication.name());
			}
//			p.setMemo(memo)
			logger.info("新品id:{} 事件:{} 备注：{}" , id , event , memo);
        }
		//有风险
		if (event == ProductNewEvent.warn) {
			if (p.getDisableMarkup() == null || "".equals(p.getDisableMarkup())) {
				p.setDisableMarkup(ProductNewState.checkWarn.name());
			} else {
				p.setDisableMarkup(p.getDisableMarkup() + "," + ProductNewState.checkWarn.name());
			}
		}

		//判断新品的spu是否存在，存在则提交后直接状态到待排重
		if (event == ProductNewEvent.submit && p.getSpu() != null && !"".equals(p.getSpu().trim())) {
			event = ProductNewEvent.verify;
			fsmMemo = "新品已有spu，状态直接改变到待排重";
		}
		productNewMapper.update(p);
		this.pcProductNewFsmProxyService.processEvent(p, event, fsmMemo);
		if (memo != null && !"".equals(memo)) {
			if (firstMemo != null && !"".equals(firstMemo)) {
				productNewMapper.updateMemo(id, memo + "〈〈 " + firstMemo);
			} else {
				productNewMapper.updateMemo(id, memo);
			}
		}
		logger.info("新品状态变化成功，id:{} 原始状态:{} 事件：{} 备注:{}" ,id,state,event,memo );
	}

	/**
	 * 新品提交限制
	 */
	private void productNewSubmitStint(ProductNew productNew, ProductNewEvent event, ProductNewState state ){
		if (!getNoAstrictSet().contains(productNew.getDepartmentId())) {
			//新品提交次数的时间限制
			if (ProductNewEvent.submit.equals(event) && ProductNewState.draft.equals(state)) {
				int countProductNew = productNewZoneMapper.countByProductNewId(productNew.getId());
				Assert.isTrue(countProductNew > 0, "产品[" + productNew.getTitle() + "]未设置区域,不能提交!");
				productNew.setSubmitTime(LocalDateTime.now());
				//上午时间界限
				LocalTime morning_time_limit = LocalTime.of(morningTime, morningBranch, morningSecond);
				//下午时间界限
				LocalTime afternoon_time_limit = LocalTime.of(afternoonTime, afternoonBranch, afternoonSecond);

				Assert.isTrue(afternoon_time_limit.isAfter(LocalTime.now()), "提交失败!每天[" + afternoon_time_limit + "]前才能提交新品");
				ProductNew param = new ProductNew();
				param.setCreatorId(MBox.getLoginUserId());
				param.setMaxCreateAt(LocalDateTime.now());
				int count = 0;
				if (morning_time_limit.isAfter(LocalTime.now())) {
					//上午
					param.setMinCreateAt(LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0)));
					count = productNewMapper.countTimeNumber(param);
					Assert.isTrue(count < advertisingSubmitNumber, "提交失败!每天[" + morning_time_limit + "]之前最多只能提交[ " + advertisingSubmitNumber + " ]次新品!");
				} else {
					//下午
					param.setMinCreateAt(LocalDateTime.of(LocalDate.now(), morning_time_limit));
					count = productNewMapper.countTimeNumber(param);
					Assert.isTrue(count < advertisingSubmitNumber, "提交失败!每天[" + morning_time_limit + "]到[" + afternoon_time_limit + "]之间最多只能提交[ " + advertisingSubmitNumber + " ]次新品!");
				}
			}
		}
	}

	public void updateCheckUserInfo(Integer productNewId){
        int j = productNewMapper.updateCheckUserInfo(productNewId, MBox.getLoginUserId(), MBox.getLoginUserName());
        logger.info("将新品id:{} 的审核人设置为：{}-{}" , productNewId , MBox.getLoginUserId() , MBox.getLoginUserName());
    }


	public ProductNew load(Integer id) {
		ProductNew productNew = productNewMapper.getById(id);
		Assert.notNull(productNew, "新品ID:" + id + "在数据库中已经不存在！");
		return productNew;
	}

	/**
	 * 获取单条新品信息
	 */
	public ProductNew get(Integer id) {
		return productNewMapper.getById(id);
	}

	/**
	 * 获取新品详情
	 * 		-新品信息组装
	 */
	public ProductNew getInfo(Integer id){
		ProductNew productNew = productNewMapper.getById(id);
		if (getUserLabelIdSet().contains(productNew.getCreatorId())){
			productNew.setUserLabel(true);
		}
		//为新品设置区域
		productNew.setProductNewZones(productNewZoneService.findByProductNewId(id));
		return productNew;
	}


	public int count(ProductNew param) {
		return productNewMapper.count(param);
	}

	/**
	 * 查询符合条件的记录数,用作分页
	 * @param param
	 * @return
	 */
	public List<ProductNew> find(ProductNew param) {
		return productNewMapper.find(param);
	}

	/**
	 * 根据spu查询新品
	 *
	 * @param spu
	 * @return
	 */
	public Product findProductBySpu(String spu) {
		//当前用户与一级品类的关系
		Product pc = productService.getBySpu(spu.trim());
		Assert.notNull(pc, "该spu[" + spu.trim() + "]不存在,请核对后重新输入!");
		List<Integer> categoryIds = categoryUserRelService.checkUserCategoryrel(CategoryUserTypeEnum.advertis);
		Assert.isTrue(categoryIds.contains(pc.getCategoryId()), "该产品不属于你的品类,无法新建!");
        Product product = productService.findProductBySpu(spu.trim());
        Integer categoryId = product.getCategoryId();
		Category ca = categoryService.getById(categoryId);
		String no = ca.getNo();
		if (no.length() > 2) {
			no = no.substring(0, 2);
		}
		Category category = categoryService.findFirstCategoryByNo(no);
		product.setCategory(category);
		return product;
	}


	public int countProductNewReport(LocalDateTime minCreateAt, LocalDateTime maxCreateAt) {
		return productNewMapper.countProductNewReport(minCreateAt, maxCreateAt);
	}

    public List<Integer> excludeRepeatUser(LocalDateTime minCreateAt, LocalDateTime maxCreateAt) {
        return productNewMapper.excludeRepeatUser(minCreateAt, maxCreateAt);
    }

	//查重报表操作
	public List<ProductNew> findCheckDay(LocalDateTime minCreateAt, LocalDateTime maxCreateAt, String fsmName, String optUid) {
		return productNewMapper.findCheckDay(minCreateAt, maxCreateAt, fsmName, optUid);
	}

	public List<FsmHistory> findStateNumber(LocalDateTime minCreateAt, LocalDateTime maxCreateAt, String fsmName, String state, String optUid) {
		return productNewMapper.findStateNumber(minCreateAt, maxCreateAt, fsmName, state, optUid);
	}

	public List<FsmHistory> findNoRepeatStateNumber(LocalDateTime minCreateAt, LocalDateTime maxCreateAt, String fsmName, String state, String optUid) {
		return productNewMapper.findNoRepeatStateNumber(minCreateAt, maxCreateAt, fsmName, state, optUid);
	}

	public List<FsmHistory> findFsmHistoryDept(ExcludeRepeat param) {
		return productNewMapper.findFsmHistoryDept(param);
	}
}
