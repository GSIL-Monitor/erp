package com.stosz.product.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.model.ProductSku;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.stosz.plat.enums.LabelTypeEnum;
import com.stosz.product.ext.model.Label;
import com.stosz.product.ext.model.LabelObject;
import com.stosz.product.ext.service.ILabelObjectServcie;
import com.stosz.product.mapper.LabelObjectMapper;

import javax.annotation.Resource;

@Service
public class LabelObjectService implements ILabelObjectServcie {

	@Resource
	private LabelService labelService;
	@Resource
	private LabelObjectMapper mapper;
	@Resource
	private ProductSkuService productSkuService;
	@Resource
	private ProductService productService;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 标签数据的增加
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(LabelObject param){
		UserDto userDto = ThreadLocalUtils.getUser();
		param.setCreatorId(userDto.getId());
		param.setCreator(userDto.getLastName());
		//objectMemo
		if (LabelTypeEnum.Material.name().equals(param.getObjectType()) ||
				LabelTypeEnum.Logistics.name().equals(param.getObjectType())){
			Product product = productService.getById(param.getObjectId());
			Assert.notNull(product, "该标签只能打在产品对象上,对象ID:["+param.getObjectId()+"]不是产品ID!");
			param.setObjectMemo(product.getTitle());
		}
//		Label label = null;
//		if (param.getLabelId() != null){
//			label = labelService.getById(param.getLabelId());
//			Assert.notNull(label, "标签ID为["+label.getId()+"]的标签不存在!");
//		}else {
//			List<Label> labelList = labelService.findLabel(param.getLabelKeyword());
//			Assert.isTrue(labelList != null && labelList.size() == 1, "标签唯一标识keyword对应的标签不存在,或者有多个,请排查!");
//			label = labelList.get(0);
//		}
//		param.setLabelId(label.getId());
		mapper.insert(param);
		logger.info("添加标签数据成功成功! 添加内容:{} ", param);
	}

	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addBatch(Integer objectId, List<Integer> labelIds, String objectType){
		LabelObject param = new LabelObject();
		List<LabelObject> paramList = new ArrayList<>();
		UserDto userDto = ThreadLocalUtils.getUser();
		param.setCreatorId(userDto.getId());
		param.setCreator(userDto.getLastName());
		param.setObjectType(objectType);
		for (Integer id : labelIds){
			param.setLabelId(id);
			paramList.add(param);
		}
		mapper.addLabelObject(paramList);
		logger.info("添加标签数据成功成功! 添加内容:{} ", param);
	}

	/**
	 * 标签数据的删除
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Integer id){
		LabelObject labelObject = load(id);
		mapper.delete(id);
		logger.info("删除标签数据成功!,具体信息为: {}", labelObject);
	}

	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteBatch(List<Integer> ids){
		mapper.deleteBatch(ids);
		logger.info("删除标签数据成功!,具体ID信息为: {}", ids);
	}

	/**
	 * 标签数据的修改
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(LabelObject param){
		LabelObject labelObject = load(param.getId());
		mapper.update(param);
		logger.info("修改标签成功! 将{} 修改成{} ", labelObject, param);
	}

	/**
	 * 根据标签id查询标签对象信息
	 */
	public List<LabelObject> findByLabelId(Integer id){
		Assert.notNull(id, "未指明标签id,不能查询到对应的标签对象信息");
		return mapper.findByLabelId(id);
	}

	/**
	 * 标签数据的查询
	 */
	private LabelObject load(Integer id){
		LabelObject labelObject = mapper.getById(id);
		Assert.notNull(labelObject, "未在标签数据中查询到ID为[" + id + "]的数据");
		return labelObject;
	}


	/**
	 * 标签数据唯一性校验
	 */
	private void lableObjectCheck(Integer id, Integer labelId, Integer objectId, String objectType){
		int count = mapper.lableObjectCheck(id, labelId, objectId, objectType);
		Assert.isTrue(count == 0, "标签数据组合[labelId:" + labelId + ",objectId:"+objectId+",objectType:"+objectType+"]已经存在");
	}

	/**
	 * 单条对象数据查询
	 */
	public List<LabelObject> findByObject(Integer objectId, String objectType){
		Assert.notNull(objectId, "对象ID不能为空");
		Assert.isTrue(objectType != null && !"".equals(objectType), "对象类型不能为空!");
		List<LabelObject> list = mapper.queryLabelObjectAttr(objectId, objectType);
		return list;
	}

	/**
	 * 标签数据批量查询
	 */
	public Map<Integer, List<LabelObject>> queryListLabelObject(List<Integer> objectIds, String ObjectType) {
		List<LabelObject> LabelObjects = mapper.findLabelByList(objectIds, ObjectType);
		Map<Integer, List<LabelObject>> LabelObjectMap = LabelObjects.stream().collect(Collectors.groupingBy(LabelObject::getObjectId));
		return LabelObjectMap;
	}

	@Override
	public void addLabel(List<LabelObject> LabelObjects) {
		Assert.notEmpty(LabelObjects, "对象的标签集合不能为空");
		for (LabelObject LabelObject : LabelObjects) {
			LabelObject.setCreateAt(new Date());
			LabelObject.setUpdateAt(new Date());
		}
		int batchSize = 30;
		int size = LabelObjects.size();
		int count = (int) Math.ceil(size / batchSize);
		for (int i = 0; i <= count; i++) {
			int endIndex = i * batchSize + 30;
			endIndex = endIndex > size ? size : endIndex;
			List<LabelObject> subLists = LabelObjects.subList(i * batchSize, endIndex);
			mapper.addLabelObject(subLists);
		}
	}

	@Override
	public List<LabelObject> queryLabelObject(Integer objectId, LabelTypeEnum labelTypeEnum) {
		return mapper.queryLabel(objectId, labelTypeEnum.name());
	}

	@Override
	public List<LabelObject> queryLabelObjectAttr(Integer objectId, String objectType) {
		return mapper.queryLabelObjectAttr(objectId, objectType);
	}

	@Override
	public int deleteLabel(Integer objectId, String objectType) {
		return mapper.delLabel(objectId, objectType);
	}

	@Override
	public List<Label> queryLabelByKey(String parentKeyword) {
		Label label = labelService.queryLabelByKey(parentKeyword);
		if (label == null) {
			return null;
		}
		List<Label> labelList = labelService.queryLabelByParent(label.getId());
		return labelList;
	}

	@Override
	public Map<Integer, List<LabelObject>> queryListLabelObject(List<Integer> objectIds, LabelTypeEnum laTypeEnum) {
		List<LabelObject> LabelObjects = mapper.queryLabelByList(objectIds, laTypeEnum.name());
		Map<Integer, List<LabelObject>> LabelObjectMap = LabelObjects.stream().collect(Collectors.groupingBy(LabelObject::getObjectId));
		return LabelObjectMap;
	}

}
