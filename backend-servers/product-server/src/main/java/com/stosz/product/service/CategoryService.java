package com.stosz.product.service;


import com.stosz.olderp.ext.model.OldErpCategoryNew;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.StoszCoder;
import com.stosz.plat.enums.CategoryUserTypeEnum;
import com.stosz.plat.rabbitmq.RabbitMQConfig;
import com.stosz.plat.rabbitmq.RabbitMQMessage;
import com.stosz.plat.rabbitmq.RabbitMQPublisher;
import com.stosz.product.ext.model.*;
import com.stosz.product.mapper.CategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 产品类别service实现
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Service
@EnableTransactionManagement
public class CategoryService {
	@Resource
	private CategoryMapper categoryMapper;
	@Resource
	private AttributeService attributeService;
	@Resource
	private ProductService productService;
	@Resource
	private ProductNewService ProductNewService;
	@Resource
	private CategoryAttributeRelService categoryAttributeRelService;
	@Resource
	private CategoryUserRelService categoryUserRelService;
	@Resource
	private RabbitMQPublisher rabbitMQPublisher;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 批量新增产品分类，主要是老erp同步时用到
	 */
	public void insertBatch(List<Category> categoryList) {
		Integer i = categoryMapper.insertBatch(categoryList);
		Assert.isTrue(i > 0, "批量新增产品分类失败！");
		logger.info("批量新增产品分类成功，分类集合===={}", categoryList);


		categoryList.stream().forEach(category -> rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_insert).setData(category)));

	}

	/**
	 * 产品类别添加(叶子节点)
	 *
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addCategory(Category category) {
		Assert.isNull(category.getId(), "新增的品类不能有ID");
		checkCategoryName(category);
		Category parent = getById(category.getParentId());
		Assert.notNull(parent, "该品类的父节点[ID:"+category.getParentId()+"]有误");
		if (parent.getLeaf()) {
			// 如果叶子品类下面挂接了属性、产品、新品，则不允许添加子品类，也不允许删除
			checkLeafCategoryEmpty(parent);
			categoryMapper.updateLeaf(false, parent.getId());
			logger.info("父品类:{} 添加了新的子品类 ，将父品类设置为非叶子结点!", parent.getId());
		}
		String newNo = getNewCategoryNoByParent(parent);
		category.setNo(newNo);
		// category.setCreateAt(LocalDateTime.now());
		//category.setCreator(MBox.getLoginUser().getName());
		//category.setCreatorId(MBox.getLoginUserId());
		category.setLeaf(true);
		Integer id = categoryMapper.insert(category);
		category.setId(id);
		logger.info("添加新的品类：{}成功！", category);

		Category categoryFindParam = new Category();
		categoryFindParam.setParentId(category.getParentId());
		categoryFindParam.setName(category.getName());
		List<Category> categoryList = categoryMapper.find(categoryFindParam);
		Assert.isTrue(com.stosz.plat.utils.CollectionUtils.isNotNullAndEmpty(categoryList),"可以找到刚刚添加的品类");

		categoryList.stream().filter(item->category.getName().equals(item.getName())).findFirst().ifPresent(cat->rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_insert).setData(cat)));

	}

	public void insertBySync(Category category) {
		Category parent = getById(category.getParentId());
		Assert.notNull(parent, "找不到该品类对应的父级");
		if (parent.getLeaf()) {
			categoryMapper.updateLeaf(false, parent.getId());
			logger.info("父品类:{} 添加了新的子品类 ，将父品类设置为非叶子结点!", parent.getId());
		}
		String newNo = getNewCategoryNoByParent(parent);
		category.setNo(newNo);
		category.setLeaf(true);
        categoryMapper.insertBySync(category);
        logger.info("添加新的品类：{}成功！", category);

    }

    public void insertRoot() {
        categoryMapper.insertRoot(0);
        categoryMapper.updateRoot();
        logger.info("插入根节点成功！");
    }

	/**
	 * 品类的删除 sqg 品类删除，需要更新父结点的叶子结点属性
	 *
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteCategory(Integer id) {
		Category category = categoryMapper.getById(id);
		Assert.notNull(category, "计划删除的品类id:" + id + "在数据库中不存在！");
		Assert.isTrue(category.getLeaf(), "此品类非叶子结点，不允许删除！");
		checkLeafCategoryEmpty(category);
		categoryMapper.deleteById(id);
		logger.info("品类:{} 被成功删除！", category);
		rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_delete).setData(category));

		if (category.getParentId() == 0) {
			return;
		}
		Category parent = categoryMapper.getById(category.getParentId());
		Assert.notNull(parent,
				"计划删除的品类id:" + id + "的父结点：" + category.getParentId()
						+ "在数据库中不存在！");

		List<Category> categories = categoryMapper.findByCategoryNo(parent
				.getNo());
		Assert.isTrue(!categories.isEmpty(), "根据父结点编码:" + parent.getNo()
				+ " 在数据库中没有找到记录？不科学！");
		if (categories.size() == 1) {
			// 如果只有1条，说明只找到了自己，直接将父结点设置为叶子结点！
			categoryMapper.updateLeaf(true, parent.getId());
			logger.info("删除结点:{}  之后，将父结点:{} 设置为叶子结点！");

			rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_update).setData(categoryMapper.getById(parent.getId())));
		}
	}

	/**
	 * 品类的更新
	 *
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateCategory(Category category) {
		Category categoryResult = categoryMapper.getById(category.getId());
		Assert.notNull(categoryResult, "修改品类时，品类id:" + category.getId()
				+ "在数据库中已经不存在了!");
		checkCategoryName(category);
		categoryMapper.updateById(category);
		logger.info("品类修改成功，原内容:{} --> 新内容:{}", categoryResult, category);

		rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_update).setData(category));
	}


	/**
	 * 同步时，品类的更新
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateCategorySync(Category category) {
		Category categoryResult = categoryMapper.getById(category.getId());
		Assert.notNull(categoryResult, "修改品类时，品类id:" + category.getId()
				+ "在数据库中已经不存在了!");
		checkCategoryName(category);
		categoryMapper.updateById(category);
		logger.info("品类修改成功，原内容:{} --> 新内容:{}", categoryResult, category);
	}

	/**
	 * sqg 移动品类树的时候，传入一个当前id以及目标id即可
	 *
	 * @param currentCategoryId
	 *            选中计划移动的结点
	 * @param targetCategoryId
	 *            移动到的目标结点
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void moveCategory(Integer currentCategoryId, Integer targetCategoryId) {
		Assert.isTrue(currentCategoryId != 0, "根结点不允许移动！");
		Category targetCategory = getById(targetCategoryId);
		Assert.notNull(targetCategory, "目标节点[ID:"+targetCategory+"]不存在");
		//用户和一级品类的关系  he_guitang
		Category  currentCategory = categoryMapper.getById(currentCategoryId);
		Assert.notNull(currentCategory, "计划移动的当前品类id:" + currentCategoryId + "在数据库中不存在！");
		Assert.isTrue(!targetCategory.getNo().startsWith(currentCategory.getNo()),"不允许将自己移动到自己的子结点下");
		if(currentCategory.getParentId() == 0){
			int count = categoryUserRelService.findByCategoryID(currentCategoryId);
			Assert.isTrue(count == 0, "品类["+currentCategory.getName()+"]已经和用户相关联,不允许移动");
		}
		// 将目标节点修改成非叶子节点
		if (targetCategory.getLeaf()) {
			checkLeafCategoryEmpty(targetCategory);
			categoryMapper.updateLeaf(false, targetCategory.getId());
			logger.info("父品类:{} 添加了新的子品类:{} ，将父品类设置为非叶子结点!",targetCategory.getId(), currentCategoryId);
		}
		/**
		 * 原节点的父节点修改 he_guitang
		 * 	若原节点没有兄弟节点,修改成叶子节点 若原节点有兄弟节点,不修改
		 */
		Integer currentParentNode = categoryMapper.getById(currentCategory.getParentId()).getId();
		// 根据父节点获取子节点
		List<Category> list = categoryMapper.findByParentId(currentParentNode);
		if (list.size() == 1) {
			categoryMapper.updateLeaf(true, currentParentNode);

			logger.info("父品类:{} 下的子品类被移除 ，将父品类设置为叶子结点!", currentCategoryId);

			rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_update).setData(categoryMapper.getById(currentParentNode)));
		}
		// --------------------------------------------------------------------
//		Category currentCategory = categoryMapper.getById(currentCategoryId);
//		Assert.notNull(currentCategory, "计划移动的当前品类id:" + currentCategoryId + "在数据库中不存在！");
//		Assert.isTrue(!targetCategory.getNo().startsWith(currentCategory.getNo()),"不允许将自己移动到自己的子结点下");
		String newNo = this.getNewCategoryNoByParent(targetCategory);
		String beforeMoveNo = currentCategory.getNo();
		logger.info("移动品类结点：{} --> {} ", currentCategory, targetCategory);
		currentCategory.setParentId(targetCategoryId);
		currentCategory.setNo(newNo);
		categoryMapper.updateById(currentCategory);

		rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_update).setData(currentCategory));

		logger.info("结点移动后的数据:{}", currentCategory);
		// 所有将被移动的子结点
		List<Category> willMovedCategoryList = categoryMapper
				.findByCategoryNo(beforeMoveNo);
		Collections.sort(willMovedCategoryList);
		int cnt = 0;
		for (Category category : willMovedCategoryList) {
			String childrenNo = category.getNo();
			String childrenNewNo = childrenNo.replaceFirst(beforeMoveNo, newNo);
			category.setNo(childrenNewNo);
			categoryMapper.updateById(category);

			rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_update).setData(category));

			logger.info("结点移动后，当前结点:{} 的孩子结点:{} 孩子编码:{} 变更为:{}",
					currentCategoryId, category.getId(), childrenNo,
					childrenNewNo);
			cnt++;
		}
		logger.info("将结点：{} 成功移动到了:{}  的下面，涉及到移动的结点的子孙后代品类共计:{} 个",
				currentCategory.getName(), targetCategory.getName(), cnt);
	}

	/**
	 * 品类id查询
	 *
	 */
	public Category getById(Integer id) {
		return id == 0 ? Category.ROOT() : categoryMapper.getById(id);
	}

    /**
     * 同步时用到，需要查出数据库中的真实信息
     */
    public Category getSyncById(Integer id) {
        return categoryMapper.getById(id);
    }

	public Integer getMaxId() {
		return categoryMapper.getMaxId();
	}

	/**
	 * 根据父结点，计算新增结点时最新的一个编码 sqg 根据父结点获取一个新的品类编码
	 */
	public String getNewCategoryNoByParent(Integer parentId) {
		Category parent = getById(parentId);
		return getNewCategoryNoByParent(parent);
	}

	/**
	 * 根据父结点，计算新增结点时最新的一个编码 sqg 根据父结点获取一个新的品类编码
	 */
	public String getNewCategoryNoByParent(Category parent) {
		Category param = new Category();
		param.setParentId(parent.getId());
		List<Category> caList = categoryMapper.find(param);
		List<String> list = caList.stream().map(Category::getNo)
				.collect(Collectors.toList());
		return StoszCoder.toHex(list, parent.getNo());
	}

	/**
	 * 品类树的获取
	 *
	 */
	public Category buildTreeByCategoryNo(String no) {
		List<Category> lst = findByNo(no);
		// lst没有数据
		Assert.isTrue(lst != null && !lst.isEmpty(), "在品类库中没有找到编码为[" + no
				+ "]开头的任何数据！");
		Collections.sort(lst);
		// lst有数据
		Category root = lst.get(0);
		buildTree(root, lst);
		return root;
	}

	/**
	 *
	 */
	public List<Category> findAll() {
		return categoryMapper.findAll();
	}
	/**
	 * 品类构建树方法
	 *
	 */
	public void buildTree(Category root, List<Category> lst) {
		if (lst == null || lst.isEmpty()) {
			return;
		}
		List<Category> children = lst.stream()
				.filter(e -> e.getParentId().equals(root.getId()))
				.collect(Collectors.toList());
		//logger.trace("开始构建树，root.no:{} - {}", root.getNo(), children);
		for (Category cate : children) {
			root.addChild(cate);
			buildTree(cate, lst);
		}
	}

	/**
	 * 根据品类编码查询所有子节点 返回list集合显示
	 *
	 */
	public List<Category> findByNo(String no) {
		List<Category> lst = categoryMapper.findByCategoryNo(no);
		return lst;
	}

	/**
	 * 根据品类编码查询所有子节点 返回list集合显示 老erp实体接收
	 */
	public List<OldErpCategoryNew> findResultOldCateByNo(String no){
		return categoryMapper.findResultOldCateByNo(no);
	}

    /**
     * 通过品类编码获取一个品类的实体
     */
    public Category getByNo(String no) {
        Assert.isTrue(no != null && !"".equals(no), "请传入参数品类编码,查询所属的品类实体!");
        return categoryMapper.getByNo(no);
    }

    /**
	 * 查询列表集合+分页
	 */
	public RestResult find(Category category) {
		RestResult rs = new RestResult();
		int count = categoryMapper.count(category);
		rs.setTotal(count);
		if (count == 0) {
			return rs;
		}
		List<Category> lst = categoryMapper.find(category);
		rs.setItem(lst);
		return rs;
	}

	/**
	 * 根据品类id获取其对应的属性
	 *
	 */
	public List<Attribute> findAttribvuteByCategoryId(Integer categoryId) {
		// 根据类别获取 属性
		List<Attribute> attributeList = attributeService
				.findAttributeByCategoryId(categoryId);
		return attributeList;
	}

	/**
	 * 检查此叶子结点是否允许删除、添加子结点
	 *
	 */
	public void checkLeafCategoryEmpty(Category parent) {
		// 判断是否挂接了属性
		CategoryAttributeRel rel = new CategoryAttributeRel();
		rel.setCategoryId(parent.getId());
		Integer count = categoryAttributeRelService.count(rel);
		Assert.isTrue(count == 0, "品类：" + parent.getId() + "-" + parent.getNo()
				+ "-" + parent.getName() + "下面挂接了 " + count
				+ " 个属性，所以不允许添加子品类，也不允许删除");
		// 判断是否挂接了新品
		ProductNew productNew = new ProductNew();
		productNew.setCategoryId(parent.getId());
		Integer pwcount = ProductNewService.count(productNew);
		Assert.isTrue(
				pwcount == 0,
				"品类：" + parent.getId() + "-" + parent.getNo() + "-"
						+ parent.getName() + "下面挂接了 " + pwcount
						+ " 个新品，所以不允许添加子品类，也不允许删除");

		// 判断是否挂接了产品
		Product product = new Product();
//		Category cate = new Category();
//		cate.setId(parent.getId());
//		product.setCategory(cate);
		product.setCategoryId(parent.getId());
		Integer pcoun = productService.findListCount(product);
		Assert.isTrue(pcoun == 0, "品类：" + parent.getId() + "-" + parent.getNo()
				+ "-" + parent.getName() + "下面挂接了 " + pcoun
				+ " 个产品，所以不允许添加子品类，也不允许删除");
	}
	
	/**
	 * 根据品类编码,获取所有上级节点,并用字符串拼接起来返回
	 */
	public String getSuperiorNameByNo(String no, String delimiter){
		if(no == null || "".equals(no)){
			return "品类错误";
		}
		if(delimiter == null || "".equals(delimiter)){
			delimiter = ",";
		}
		Assert.isTrue(no.length() % 2 == 0, "品类编码["+no+"]有误");
		StringBuilder sb = new StringBuilder();
		String str = new String();
		List<String> noList = new ArrayList<String>();
		noList.add(no);
		while(no.length() > 2){
			no = no.substring(0, no.length()-2);
			noList.add(no);
		}
		Assert.notEmpty(noList,"品类编码的List不能为空");
		List<Category> list = categoryMapper.findByNos(noList);
		for(Category lst : list){
			sb.append(lst.getName() + delimiter);
		}
		if(sb.length() > 0){
			str = sb.substring(0, sb.length() - delimiter.length());
		}
		return str;
	}

	/**
	 * 根据品类叶子节点id获取顶级品类信息
	 */
	public Category getTopCategory(Integer categoryId) {
		if (categoryId == null) return null;
		Category category = categoryMapper.getById(categoryId);
		if (category == null) return null;
		if (category.getNo() == null || "".equals(category.getNo())) return null;
		if (category.getNo().length() % 2 != 0) return null;
		Category cate = categoryMapper.getByNo(category.getNo().substring(0, 2));
		return cate;
	}
	

	public List<Category> findFirstLevelByUserId(Integer userId,CategoryUserTypeEnum userType){
		return categoryMapper.findFirstLevelByUserId(userId,userType);
	}
	
	/**
	 * 品类搜索
	 */
	public RestResult nodeNameLike(Category param) {
		RestResult result = new RestResult();
		Integer count = categoryMapper.categorySearchCount(param);
		result.setTotal(count);
		if(count == 0){
			result.setDesc("没有查询到符合的记录");
			return result;
		}
		List<Category> list = categoryMapper.categorySearch(param);
		result.setItem(list);
		result.setDesc("查询成功");
		return result;
    }
	
	
	/**
	 * 叶子节点品类搜索
	 * 		全树搜索(排重的细化分类没有一级品类的限制)
	 */
	public RestResult leafSearch(Category param){
		RestResult result = new RestResult();
		param.setLeaf(true);
		if(param.getName() == null){
			param.setName("");
		}
//		if(param.getLimit() == null){
//			param.setLimit(100);
//		}
		if(param.getNo() == null){
			if(param.getId() != null){
				String no = categoryMapper.getById(param.getId()).getNo();
				param.setNo(no);
			}
		}
		Integer count = categoryMapper.categorySearchCount(param);
		result.setTotal(count);
		if(count == 0){
			result.setDesc("没有查询到符合的记录");
			return result;
		}
		List<Category> list = categoryMapper.categorySearch(param);
		for(Category lst : list){
			lst.setName(getSuperiorNameByNo(lst.getNo(),"＞"));
		}
		result.setItem(list);
		result.setDesc("查询成功");
		return result;
	}
	
	/**
	 * 品类同名检查
	 */
	private void checkCategoryName(Category category){
		int count = categoryMapper.checkCategoryName(category);
		Assert.isTrue(count == 0, "父品类下[ID:"+category.getParentId()+"]已经有了品类名["+category.getName()+"]");
	}
	
//    @Cacheable(value = "getNewCategoryById", unless = "#result == null")
    public Integer getNewCategoryById(Integer oldCategory) {
		return categoryMapper.getNewCategoryById(oldCategory);
	}

	public List<Category> findByDivide() {
		return categoryMapper.findByDivide();
	}
	
	public List<Category> findByIds(List<Integer> ids){
		Assert.isTrue(ids != null && !ids.isEmpty(), "查询的集合["+ids+"]为空,不能查询对应的品类信息!");
		return categoryMapper.findByIds(ids);
	}
	
	public List<Category> findByCategoryNos(List<String> nos){
		return categoryMapper.findByCategoryNos(nos);
	}

	public Category findFirstCategoryByNo(String no) {
		return categoryMapper.findFirstCategoryByNo(no);
	}


	public void reFlashNo() {
		List<Category> list = categoryMapper.findByParentId(0);
		for (Category category : list) {
			List<Category> childrenList = categoryMapper.findByParentId(category.getId());
			for (Category secondCategory : childrenList
					) {
				String secondNo = getNewCategoryNoByParent(category);
				secondCategory.setNo(secondNo);
				categoryMapper.updateNoById(secondCategory);
				List<Category> threeCategoryList = categoryMapper.findByParentId(secondCategory.getId());
				for (Category threeCategory : threeCategoryList) {
					String threeNo = getNewCategoryNoByParent(secondCategory);
					threeCategory.setNo(threeNo);
					categoryMapper.updateNoById(threeCategory);
				}
			}
		}
	}

	//品类的搜索 ==>根据当前品类,获取所有的下级品类的叶子节点id
	public List<Integer> categorySearch(Integer categoryId) {
		Assert.notNull(categoryId, "品类ID为空,不能根据品类ID查询品类信息");
		List<Integer> categoryIds = new ArrayList<>();
		Category category = categoryMapper.getById(categoryId);
		if (category.getLeaf()) {
			categoryIds.add(category.getId());
		} else {
			List<Category> lst = categoryMapper.findByCategoryNo(category.getNo());
			categoryIds = lst.stream().map(Category::getId).collect(Collectors.toList());
		}
		return categoryIds;
	}

	public List<Category> query(){
		return categoryMapper.findAll();
	}



}
