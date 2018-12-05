package com.stosz.product.service;

import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.product.ext.enums.ProductState;
import com.stosz.product.ext.model.*;
import com.stosz.product.mapper.AttributeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.w3c.dom.Attr;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 属性service实现层
 *
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Service
public class AttributeService {
	@Resource
	private AttributeMapper mapper;
	@Resource
	private AttributeValueService attributeValueService;
	@Resource
	private CategoryAttributeRelService categoryAttributeRelService;
	@Resource
	private ProductSkuAttributeValueRelService productSkuAttributeValueRelService;
	@Resource
	private ProductSkuService productSkuService;
	@Resource
	private ProductAttributeRelService productAttributeRelService;
	@Resource
	private AttributeLangService attributeLangService;
	@Resource
	private AttributeValueLangService attributeValueLangService;
	@Resource
	private ProductAttributeValueRelService productAttributeValueRelService;
	@Resource
	private CategoryService categoryService;
	@Resource
	private ProductService productService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 属性添加 属性列表和品类属性列表
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addAttribute(Attribute attribute) {
		try {
			mapper.insert(attribute);
			logger.info("属性: {} 添加成功!", attribute);
		} catch (DuplicateKeyException e) {
			throw new IllegalArgumentException("属性名[" + attribute.getTitle() + "]已经存在,不能重复添加!");
		}
	}


	/**
	 * @author xiongchenyang 2017/9/12
	 * 同步属性时，使用的
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void insertOld(Attribute attribute) {
		Assert.notNull(attribute, "不允许添加空的属性");
		mapper.insert(attribute);
		logger.info("属性: {} 添加成功!", attribute);
	}


    public void insertList(List<Attribute> attributeList) {
        Assert.notNull(attributeList, "不允许插入空集合！");
        mapper.insertList(0, attributeList);
        logger.info("属性插入成功---------插入属性集合{}成功", attributeList);
    }

	/**
	 * 属性添加 产品属性的添加(直接添加属性)
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public RestResult addByProduct(Attribute attribute, ProductAttributeRel rel, Integer categoryId) {
		RestResult rest = new RestResult();
		Attribute result = mapper.getByTitle(MBox.ERP_ATTRIBUTE_VERSION,attribute.getTitle());
		int countSku = productSkuService.countByProductId(rel.getProductId());
		Assert.isTrue(countSku == 0, "该产品[ID:"+rel.getProductId()+"]已经生成了sku,其属性不能再修改!");
		Category category = categoryService.getById(categoryId);
		Assert.notNull(category, "该产品[ID:"+rel.getProductId()+"]的品类[ID:"+categoryId+"]在品类列表中不存在!");
		// 1.属性池不存在该属性,添加属性,然后绑定
		if (result == null) {
			mapper.insert(attribute);
			logger.info("属性: {} 添加成功!", attribute);
			// 产品属性关系表的添加
			Assert.notNull(attribute.getId(), "返回当前属性id出错");
			rel.setAttributeId(attribute.getId());
			productAttributeRelService.insert(rel);
			// 该属性同步到品类下
			categoryAttributeSyn(categoryId, attribute.getId());
			rest.setCode(RestResult.NOTICE);
			rest.setDesc("属性[" + attribute.getTitle() + "]添加成功");
			return rest;
		} else {
			int count = categoryAttributeRelService.countByAttrCateID(categoryId, result.getId());
			if (count == 0) {
				// 2.存在该属性名称,但是在该品类下没有:直接绑定,并添加到该品类下
				RestResult rst = productAttributeSyn(rel.getProductId(), result.getId());
				Assert.isTrue(RestResult.NOTICE.equals(rst.getCode()), "该产品[ID:"+rel.getProductId()+"]与属性[ID:"+result.getId()+"]绑定失败!");
				// 该属性同步到品类下
				categoryAttributeSyn(categoryId, result.getId());
				rest.setCode(RestResult.NOTICE);
				rest.setDesc("属性[" + attribute.getTitle() + "]与品类[" + categoryId + "],产品[" + rel.getProductId() + "]绑定成功");
				return rest;
			} else {
				// 3.存在该属性名称,但是在该品类下有,直接绑定
				return productAttributeSyn(rel.getProductId(), result.getId());
			}
		}
	}

	private RestResult productAttributeSyn(Integer productId, Integer attributeId) {
		ProductAttributeRel param = new ProductAttributeRel();
		param.setProductId(productId);
		param.setAttributeId(attributeId);
		param.setCreatorId(MBox.getLoginUserId());
		param.setCreator(MBox.getLoginUserName());
		return bindingProduct(param);
	}
	
	/**
	 * 产品属性的绑定
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public RestResult bindingProduct(ProductAttributeRel param) {
		RestResult result = new RestResult();
		bindIsCheck(param.getProductId(), param.getAttributeId());
		// 判断产品绑定的属性在老erp中是否也绑定的该属性
		Attribute attr = mapper.getById(param.getAttributeId());
		List<Attribute> bindList = mapper.findBindList(param.getProductId());
		if (bindList != null && bindList.size() > 0) {
			for (Attribute list : bindList) {
				if (attr.getTitle().trim().equals(list.getTitle().trim())) {
					result.setCode(RestResult.FAIL);
					result.setDesc("该产品已经绑定了[" + attr.getTitle() + "]属性,不能重复绑定");
					return result;
				}
			}
		}
		// 新增操作-->需要全部的字段
		productAttributeRelService.insert(param);
		result.setCode(RestResult.NOTICE);
		result.setDesc("属性[ID:" + param.getAttributeId() + "]与产品[ID:" + param.getProductId() + "]绑定成功");
		logger.info("产品与属性的绑定成功");
		return result;
	}
	/**
	 * 产品属性的解绑
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public RestResult unBindingProduct(ProductAttributeRel param) {
		RestResult result = new RestResult();
		bindIsCheck(param.getProductId(),param.getAttributeId());
		// 解绑操作
		ProductAttributeRel rel =
				productAttributeRelService.getByAttrProductId(param.getAttributeId(), param.getProductId());
		Assert.notNull(rel, "解绑失败,没有查询到该产品[ID:"+param.getProductId()+"]与该属性[ID:"+param.getAttributeId()+"]的绑定记录!");
		productAttributeRelService.deleteByUnBind(param);
		productAttributeValueRelService.deleteByProductAttributeId(rel.getId());
		result.setCode(RestResult.NOTICE);
		result.setDesc("解绑成功");
		logger.info("产品与属性的解绑成功");
		return result;
	}

	private void bindIsCheck(Integer productId,Integer attributeId){
		Product product = productService.getById(productId);
		Assert.isTrue(product.getState().equals(ProductState.waitFill.name()), "产品属性的绑定和解绑只能在[" + ProductState.waitFill.display() + "]时进行");
		int count = productSkuService.countByProductId(productId);
		Assert.isTrue(count == 0, "该产品ID为["+productId+"]已经生成了sku,其属性不能再改变!");
		Attribute attribute = mapper.getById(attributeId);
		Assert.notNull(attribute, "没有属性ID["+attributeId+"]的属性存在");
		Assert.isTrue( MBox.ERP_ATTRIBUTE_VERSION==attribute.getVersion(), "旧erp的属性不允许做解绑和绑定操作");
	}

	private void categoryAttributeSyn(Integer categoryId, Integer attributeId) {
		CategoryAttributeRel cateRel = new CategoryAttributeRel();
		cateRel.setCategoryId(categoryId);
		cateRel.setAttributeId(attributeId);
		cateRel.setCreatorId(MBox.getLoginUserId());
		cateRel.setCreator(MBox.getLoginUserName());
		categoryAttributeRelService.insertRel(cateRel);
	}

	/**
	 * 属性删除 --属性列表/品类属性
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Integer attributeId) {
		Attribute attributeResult = load(attributeId);
		deleteCommon(attributeId, attributeResult);
		// 删除产品属性关系表
		productAttributeRelService.deleteByAttributeId(attributeId);
		mapper.delete(attributeId);
		logger.info("根据属性ID: {} 删除属性: {} 成功!", attributeId,attributeResult);
	}


	/**
	 * 删除的公共方法
	 */
	private void deleteCommon(Integer attributeId, Attribute attributeResult) {
		// 删除属性时,需要检查次属性是否已经关联了sku或者spu,如果有,报错
		int spuCount = productAttributeRelService.countByAttributeId(attributeId);
		Assert.isTrue(spuCount == 0, "该属性不允许删除,已经关联了产品");
		int skuCount = productSkuAttributeValueRelService.countByAttributeId(attributeId);
		Assert.isTrue(skuCount == 0, "该属性不允许删除,已经关联了sku");
		// 删除属性语言包,该属性下的属性值语言包
		attributeLangService.deleteByAttributeId(attributeId);
		List<AttributeValue> attrValList = attributeValueService.findByAttId(attributeId);
		if (attrValList != null && attrValList.size() != 0) {
			List<Integer> ids = attrValList.stream().map(AttributeValue::getId).collect(Collectors.toList());
			attributeValueLangService.deleteByAttrValIds(ids);
		}
		// 删除属性的时候,删除属性的属性值
		attributeValueService.deleteByAttributeId(attributeId);
		logger.info("删除属性: {} 下的属性值成功!", attributeResult.getTitle());
		// 判断是否与品类绑定,删除品类属性关系表
		categoryAttributeRelService.deleteByAttributeId(attributeId);
	}

	/**
	 * 属性修改 --属性列表/品类属性
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(Attribute attribute) {
		// 修改id的查询
		Attribute attributeResult = load(attribute.getId());
		//属性与spu的判断
		if(!attributeResult.getTitle().equals(attribute.getTitle())){
			int spuCount = productAttributeRelService.countByAttributeId(attribute.getId());
			Assert.isTrue(spuCount == 0, "修改失败,该属性已经绑定了产品,不能再修改!");
		}
		try {
			// 修改
			mapper.update(attribute);
			logger.info("属性: {} 修改成: {} 成功", attributeResult, attributeResult);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new IllegalArgumentException("属性名[" + attribute.getTitle() + "]已经存在,不能重复添加!");
		}
	}

	/**
	 * 品类与属性的绑定
	 *
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public RestResult bindingCategory(CategoryAttributeRel car) {
		RestResult result = new RestResult();
		// 新增操作-->需要全部的字段
		categoryAttributeRelService.insertRel(car);
		result.setCode(RestResult.NOTICE);
		result.setDesc("绑定成功");
		logger.info("产品类别与属性的绑定成功");
		return result;
	}

	/**
	 * 品类与属性的解绑
	 *
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public RestResult unBindingCategory(CategoryAttributeRel param) {
		RestResult result = new RestResult();
		//属性与spu,sku判断
		int spuCount = productAttributeRelService.countByCategoryAttributeId(param);
		Assert.isTrue(spuCount == 0, "该属性不允许解绑,已经关联了产品");
		// 删除操作
		categoryAttributeRelService.deleteByBind(param);
		result.setCode(RestResult.NOTICE);
		result.setDesc("解绑成功");
		logger.info("产品类别与属性的解绑成功");
		return result;
	}


//    @Cacheable(value = "getAttributeByTitle", unless = "#result == null")
    public Attribute getByTitle(Integer version, String title) {
		return mapper.getByTitle(version,title);
	}
	
	
	public List<Attribute> queryByProductId(Integer productId){
		return mapper.queryByProductId(productId);
	}
	
	/**
	 * 根据属性id查询单条属性记录
	 * 
	 */
	public Attribute get(Integer id) {
		return mapper.getById(id);
	}

	public Attribute load(Integer id) {
		Attribute attr = get(id);
		Assert.notNull(attr, "属性id:" + id + "在数据库中不存在!");
		return attr;
	}

	/**
	 * 根据品类id获取属性
	 * 
	 */
	public List<Attribute> findAttributeByCategoryId(Integer categoryId) {
		return mapper.findByCategoryId(MBox.ERP_ATTRIBUTE_VERSION,categoryId);
	}

	/**
	 * 通过品类和产品获取属性列表
	 * 		-获取品类下的属性,获取产品下的属性
	 */
	public List<Attribute> findByCateProId(Integer categoryId, Integer productId){
		if (categoryId == null || productId == null )return null;

		List<Attribute> attributes = mapper.findByCategoryId(MBox.ERP_ATTRIBUTE_VERSION, categoryId);
//		List<Attribute> attributes = mapper.findByCateProId(MBox.ERP_ATTRIBUTE_VERSION, categoryId, productId);
		List<Integer> ids = new ArrayList<>();
		ids.add(productId);
		//产品绑定的属性
		List<Attribute> attributeList = mapper.findByProductIds(ids);
		if (attributes == null) attributes = new ArrayList<>();
		if (attributeList == null) attributeList = new ArrayList<>();
		List<Integer> bindIds = attributeList.stream().map(Attribute::getId).collect(Collectors.toList());
		for (Attribute attribute : attributes){
			if (bindIds.contains(attribute.getId())){
				attribute.setBindIs(true);
			}else {
				attribute.setBindIs(false);
			}
		}
		List<Attribute> oldAttribute = attributeList.stream().filter(e -> e.getVersion() < MBox.ERP_ATTRIBUTE_VERSION).collect(Collectors.toList());
		if (oldAttribute != null && oldAttribute.size() > 0){
			for (Attribute att : oldAttribute){
				att.setBindIs(true);
			}
		}
		attributes.addAll(oldAttribute);
		Collections.sort(attributes, new Attribute());
		return attributes;
	}

	/**
	 * 根据产品id获取属性
	 * 
	 */
	public List<Attribute> findByProductId(Integer version,Integer productId) {
		return mapper.findByProductId(version,productId);
	}

	public List<Attribute> findByProductId(Integer productId) {
		return mapper.findByProductId(null,productId);
	}

	public List<Attribute> findByProductIdUseRelId(Integer productId) {
		return mapper.findByProductIdUseRelId(null,productId);
	}


	/**
	 * 获取旧erp产品已经绑定的数据
	 */
	public List<Attribute> findByOldProductId(Integer id) {
	    return mapper.findByOldProductId(id);
    }
	
	/**
	 * 属性列表(属性页面)
	 * 
	 */
	public RestResult findAttribute(Attribute attribute) {
		RestResult rs = new RestResult();
		int count = mapper.count(attribute);
		rs.setTotal(count);
		if (count == 0) {
			return rs;
		}
		List<Attribute> lst = mapper.find(attribute);
		lst = bindAttributeLang(lst,attribute.getCategoryId());
		rs.setItem(lst);
		rs.setDesc("属性列表查询成功");
		return rs;
	}

	private List<Attribute> bindAttributeLang(List<Attribute> list,Integer categoryId){
		if(list != null && list.isEmpty()){
    		return list;
    	}
    	Assert.notNull(list);
		List<Integer> ids = list.stream().map(Attribute::getId).collect(Collectors.toList());
		Map<Integer, List<AttributeLang>> mapAttributeLang = attributeLangService.findByAttributeId(ids);
		for (Attribute lst : list) {
			lst.setAttributeLangs(mapAttributeLang.get(lst.getId()));
			lst.setCategoryId(categoryId);
		}
		return list;
	}
	
	/**
	 * 属性列表(品类下的属性页面)
	 * 
	 */
	public RestResult findListAttribute(Attribute attribute) {
		RestResult rs = new RestResult();
		int count = mapper.countCategoryBindCase(attribute);
		rs.setTotal(count);
		if (count == 0) {
			return rs;
		}
		List<Attribute> lst = mapper.findCategoryBindCase(attribute);
		lst = bindAttributeLang(lst,attribute.getCategoryId());
		rs.setItem(lst);
		return rs;
	}

	/**
	 * 属性列表记录数
	 * 
	 */
	public Integer findListCount(Attribute attribute) {
		return mapper.count(attribute);
	}


	public Map<Integer, List<Attribute>> findByProductIds(List<Integer> ids) {
		List<Attribute> list = mapper.findByProductIds(ids);
		return list.stream().collect(Collectors.groupingBy(Attribute::getProductId));
	}

}
