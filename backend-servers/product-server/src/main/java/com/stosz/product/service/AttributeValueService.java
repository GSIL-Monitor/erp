package com.stosz.product.service;

import com.google.common.base.Strings;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.product.ext.model.*;
import com.stosz.product.ext.service.IAttributeValueService;
import com.stosz.product.mapper.AttributeValueMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 属性值Service
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Service
public class AttributeValueService implements IAttributeValueService{
	@Resource
	private AttributeValueMapper mapper;
	@Resource
	private ProductSkuAttributeValueRelService productSkuAttributeValueRelService;
	@Resource
	private AttributeValueLangService attributeValueLangService;
	@Resource
	private ProductAttributeValueRelService productAttributeValueRelService;
	@Resource
	private ProductAttributeRelService productAttributeRelService;
	@Resource
	private AttributeService attributeService;

	private final Logger logger = LoggerFactory.getLogger(AttributeValueService.class);

	/**
	 * 属性值的 添加(属性列表)
	 * 
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addAttributeValue(AttributeValue attributeValue) {
		Attribute attribute = attributeService.get(attributeValue.getAttributeId());
		Assert.notNull(attribute, "属性值ID["+attributeValue.getId()+"]已经不存在,请刷新!");
		attributeValue.setVersion(MBox.ERP_ATTRIBUTEVALUE_VERSION);
		try{
			mapper.insert(attributeValue);
			logger.info("属性值:{} 添加成功!", attributeValue);
		} catch (org.springframework.dao.DuplicateKeyException e) {
//			throw new IllegalArgumentException("版本["+attributeValue.getVersion()+"]下的属性["+attributeValue.getAttributeId()+"]"
//					+ "已经有了属性值["+attributeValue.getTitle()+"]");
			throw new IllegalArgumentException("属性[ID:"+attributeValue.getAttributeId()+"]下已经有了属性值["+attributeValue.getTitle()+"],不能重复添加!");
		}
	}

	/**
	 * @author xiongchenyang 2017/9/12
	 * 同步时，插入老erp属性值使用
	 */
	public void insertOld(AttributeValue attributeValue) {
		mapper.insert(attributeValue);
		logger.info("属性值:{} 添加成功!", attributeValue);
	}

    public void insertList(List<AttributeValue> attributeValueList) {
        Assert.notNull(attributeValueList, "不允许插入空的属性值集合！");
        mapper.insertList(0, attributeValueList);
        logger.info("插入属性值集合成功-------------------集合{}", attributeValueList);
    }

    /**
	 * 属性值的添加(产品属性值)
	 * 
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addByProduct(AttributeValue attributeValue, Integer productId) {
		AttributeValue attrValue = mapper.getByTitle(MBox.ERP_ATTRIBUTEVALUE_VERSION,
								attributeValue.getAttributeId(),attributeValue.getTitle().trim());
		//	ProductAttributeRel paRel = productAttributeRelService.findRelId(productId, attributeValue.getAttributeId());
		ProductAttributeRel paRel = productAttributeRelService.getByAttrProductId(attributeValue.getAttributeId(), productId);
		Assert.notNull(paRel, "属性[ID:" + attributeValue.getAttributeId() + "]还未绑定产品[ID:" + productId + "],不能绑定属性值");
		ProductAttributeValueRel rel = new ProductAttributeValueRel();
		//1.不存在该属性值
		if(attrValue == null){
			attributeValue.setVersion(MBox.ERP_ATTRIBUTEVALUE_VERSION);
			mapper.insert(attributeValue);
			logger.info("属性值: {} 添加成功!", attributeValue);
			// 产品属性值关系表的增加
			Assert.notNull(attributeValue.getId(), "返回当前属性值id出错");
			rel.setAttributeValueId(attributeValue.getId());
		}else{
			rel.setAttributeValueId(attrValue.getId());
		}
		rel.setProductId(productId);
		rel.setProductAttributeId(paRel.getId());
		rel.setCreatorId(MBox.getLoginUserId());
		rel.setCreator(MBox.getLoginUserName());
		productAttributeValueRelService.add(rel);	
	}

	/**
	 * 属性值的删除(属性列表,品类属性列表)
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Integer attributeValueId) {
		AttributeValue attrValResult = load(attributeValueId);
		deleteCommon(attributeValueId, attrValResult);
		// 删除产品属性值关系表
		productAttributeValueRelService.deleteByAttributeValueId(attributeValueId);
		mapper.delete(attributeValueId);
		logger.info("根据属性值ID: {} 删除属性: {} 成功!", attributeValueId, attrValResult.getTitle());
	}

	/**
	 * 属性值删除的公共方法
	 */
	private void deleteCommon(Integer attributeValueId, AttributeValue attr) {
		// 删除属性值的时候 与sku关系的判断
		int productSku = productSkuAttributeValueRelService.countByAttributeValueId(attributeValueId);
		Assert.isTrue(productSku == 0, "该属性值不能被删除,关联了sku");
		// 删除属性值语言表
		attributeValueLangService.deleteByAttributeValueId(attributeValueId);
	}
	
	/**
	 * 根据属性id删除属性值
	 */
	public void deleteByAttributeId(Integer attributeId) {
		mapper.deleteByAttributeId(attributeId);
    }
	
	/**
	 * 属性值的更新
	 * 
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateAttributeValue(AttributeValue attributeValue) {
		// 修改id的查询
		AttributeValue attributeValResult = getById(attributeValue.getId());
		Assert.notNull(attributeValResult, "没有符合要修改项");
		if(!attributeValResult.getTitle().equals(attributeValue.getTitle())){
			int skuCount = productSkuAttributeValueRelService.countByAttributeValueId(attributeValue.getId());
			Assert.isTrue(skuCount == 0, "修改失败,该属性值已经生成了sku,不能再修改!");
		}
		// 修改
		try{
			attributeValue.setVersion(MBox.ERP_ATTRIBUTEVALUE_VERSION);
			mapper.update(attributeValue);
			logger.info("属性值ID: {} 更新成功", attributeValue.getId());
		} catch (org.springframework.dao.DuplicateKeyException e) {
//			throw new IllegalArgumentException("版本["+attributeValue.getVersion()+"]下的属性["+attributeValue.getAttributeId()+"]"
//					+ "已经有了属性值["+attributeValue.getTitle()+"]");
			throw new IllegalArgumentException("属性[ID:"+attributeValue.getAttributeId()+"]下已经有了属性值["+attributeValue.getTitle()+"],不能重复添加!");
		}
	}

	/**
	 * 属性值的绑定
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public RestResult bind(ProductAttributeValueRel param, Integer attributeId) {
		RestResult result = new RestResult();
		bindIsCheck(param.getAttributeValueId());
		// 1.根据产品和属性id,获取产品属性关系表的id
		ProductAttributeRel rel = productAttributeRelService.getByAttrProductId(attributeId, param.getProductId());
		Assert.notNull(rel, "属性[ID:" + attributeId + "]还未绑定产品[ID:" + param.getProductId() + "],不能绑定属性值");
		// 2.产品属性值关系表的绑定
		param.setProductAttributeId(rel.getId());
		productAttributeValueRelService.add(param);
		result.setCode(RestResult.NOTICE);
		result.setDesc("属性值ID["+param.getAttributeValueId()+"]绑定成功");
		return result;
	}

	private void bindIsCheck(Integer attributeValueId){
		AttributeValue attrValue = mapper.getById(attributeValueId);
		Assert.notNull(attrValue, "没有属性值ID为["+attributeValueId+"]的属性值存在");
		Assert.isTrue(attrValue.getVersion() == MBox.ERP_ATTRIBUTEVALUE_VERSION, "旧erp的属性值不允许做解绑和绑定操作");
	}
	
	/**
	 * 属性值的解绑
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public RestResult unBind(ProductAttributeValueRel param, Integer attributeId) {
		RestResult result = new RestResult();
		bindIsCheck(param.getAttributeValueId());
		// 1.判断某产品下的属性值与sku的关系
		int productSku = productSkuAttributeValueRelService.countByPcAttrValId(param.getProductId(), param.getAttributeValueId());
		Assert.isTrue(productSku == 0, "该属性值ID["+param.getAttributeValueId()+"]不能解绑,已经关联了sku");
		productAttributeValueRelService.unBinding(param);
		result.setCode(RestResult.NOTICE);
		result.setDesc("属性值ID["+param.getAttributeValueId()+"]解绑成功");
		return result;
	}
	
	public int countProductSkuList(Integer productId,Integer attributeId){
		return mapper.countProductSkuList(productId, attributeId);
	}
	
	/**
	 * 通过产品id查询已绑定的属性值
	 */
	public List<AttributeValue> findAttrValueByProductId(Integer productId){
		return mapper.findAttrValueByProductId(productId);
	}
	
	
	/**
	 * 通过属性值id获取属性值
	 */
	public AttributeValue getById(Integer id) {
		return mapper.getById(id);
	}

	/**
	 * 通过属性值id批量获取
	 */
	public List<AttributeValue> findByIds(List<Integer> ids) {
		return mapper.findByIds(ids);
	}

	private AttributeValue load(Integer attributeValueId) {
		AttributeValue attr = mapper.getById(attributeValueId);
		Assert.notNull(attr, "属性值ID:" + attributeValueId + "在数据库中不存在!");
		return attr;
	}

	/**
	 * 根据title和attributeId获取到对应的属性值
	 */
    public AttributeValue getByTitleAndAttribute(Integer version, String title, Integer attributeId) {
		Assert.notNull(version, "版本Version不允许为空！");
		Assert.notNull(title, "标题不允许为空！");
		Assert.notNull(attributeId, "属性ID不允许为空！");
		return mapper.getByTitleAndAttribute(version, title, attributeId);
	}
    
	/**
	 * 通过属性id查询属性值集合
	 * 
	 */
	public List<AttributeValue> findByAttributeId(Integer productId,Integer attributeId) {
		AttributeValue param = new AttributeValue();
		param.setProductId(productId);
		param.setAttributeId(attributeId);
		param.setVersion(MBox.ERP_ATTRIBUTEVALUE_VERSION);
		List<AttributeValue> lst = mapper.findByAttributeValue(param);
		// 为属性值绑定语言包
		lst = bindAttributeValueLang(lst);
		param.setBindIs(true);
		param.setVersion(null);
		List<AttributeValue> list = mapper.findByAttributeValue(param);
		for(Iterator<AttributeValue> iter = list.iterator();iter.hasNext();){
			if(MBox.ERP_ATTRIBUTEVALUE_VERSION == iter.next().getVersion()){
				iter.remove();
			}
		}
		lst.addAll(list);
		return lst;
	}

	private List<AttributeValue> bindAttributeValueLang(List<AttributeValue> list){
		if(list != null && list.isEmpty()){
    		return list;
    	}
    	Assert.notNull(list);
		List<Integer> ids = list.stream().map(AttributeValue::getId).collect(Collectors.toList());
		Map<Integer, List<AttributeValueLang>> mapAttributeLangs = attributeValueLangService.findByAttributeValueId(ids);
		for(AttributeValue lst : list){
			lst.setAttributeValueLangs(mapAttributeLangs.get(lst.getId()));
		}
		return list;
	}
	
	/**
	 * 通过产品id获取属性值
	 * 
	 */
	public List<AttributeValue> findByProductId(Integer productId) {
		return mapper.findByProductId(MBox.ERP_ATTRIBUTEVALUE_VERSION,productId);
	}

	public List<AttributeValue> findByProductId(Integer productId,boolean ignoreVersion) {
		if (ignoreVersion) return mapper.findByProductIdIgnoreVersion(productId);
		return mapper.findByProductId(MBox.ERP_ATTRIBUTEVALUE_VERSION,productId);
	}
	
	/**
	 * 属性值列表
	 * 		属性/品类
	 */
	public List<AttributeValue> findByAttId(Integer attributeId) {
		List<AttributeValue> lst = mapper.findByAttId(MBox.ERP_ATTRIBUTEVALUE_VERSION,attributeId);
		// 为属性值绑定语言包
		lst = bindAttributeValueLang(lst);
//		List<AttributeValue> list = mapper.findByAttId(MBox.ERP_OLD_ATTRIBUTEVALUE_VERSION, attributeId);
//		lst.addAll(list);
		return lst;
	 }

	public int countByAttId(Integer attributeId) {
		return mapper.countByAttId(MBox.ERP_ATTRIBUTEVALUE_VERSION, attributeId);
	}

	public List<AttributeValue> findByAttributeId(int attributeId) {
		return mapper.findByAttId(MBox.ERP_ATTRIBUTEVALUE_VERSION, attributeId);
	}

	public List<AttributeValue> getValueListBySku(String sku) {
		Assert.isTrue(!Strings.isNullOrEmpty(sku),"sku不能为空");
		return mapper.getValueListBySku(sku);
	}

	public RestResult findByAttrId(AttributeValue param){
		RestResult result = new RestResult();
		int count = mapper.count(param);
		result.setTotal(count);
		if (count == 0) {
			return result;
		}
		List<AttributeValue> attributeValueList = mapper.find(param);
		result.setItem(attributeValueList);
		result.setDesc("属性值列表查询成功");
		result.setCode(RestResult.OK);
		return result;

	}
}
