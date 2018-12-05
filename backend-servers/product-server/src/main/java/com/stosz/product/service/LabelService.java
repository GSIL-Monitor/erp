package com.stosz.product.service;

import java.util.*;
import java.util.stream.Collectors;

import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.enums.LabelTypeEnum;
import com.stosz.product.ext.model.LabelObject;
import com.stosz.product.mapper.LabelObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import com.stosz.product.ext.model.Label;
import com.stosz.product.mapper.LabelMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Service
public class LabelService {

	@Resource
	private LabelMapper mapper;
	@Resource
	private LabelObjectMapper LabelObjectMapper;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 标签的增加
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(Label param){
		UserDto userDto = ThreadLocalUtils.getUser();
		param.setCreatorId(userDto.getId());
		param.setCreator(userDto.getLastName());
		checkLabelName(param.getId(), param.getParentId(), param.getName());
		try {
			mapper.insert(param);
			logger.info("添加标签成功! 添加内容:{} ", param);
		} catch (DuplicateKeyException e) {
			throw new IllegalArgumentException("标签唯一标识[" + param.getKeyword() + "]已经存在!");
		}
	}

	/**
	 * 标签的修改
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(Label param){
		Label label = load(param.getId());
		lableCheck(param.getId(), label.getName());
		checkLabelName(param.getId(), param.getParentId(), param.getName());
		param.setCreator(label.getCreator());
		param.setCreatorId(label.getCreatorId());
		try {
			mapper.update(param);
			logger.info("修改标签成功! 将{} 修改成{} ", label, param);
		} catch (DuplicateKeyException e) {
			throw new IllegalArgumentException("标签唯一标识[" + param.getKeyword() + "]已经存在!");
		}
	}

	/**
	 * 标签的删除
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Integer id){
		Label label = load(id);
		lableCheck(id, label.getName());
		List<Label> list = mapper.queryLabelByParent(label.getId());
		Assert.isTrue(list == null || list.size() == 0, "该标签非叶子节点,不能删除!");
		mapper.delete(id);
		logger.info("删除标签[" + label.getName() + "]成功!,具体信息为: {}", label);
	}

	/**
	 *根据唯一的key查询数据
	 */
	public Label queryLabelByKey(String key) {
		if (key == null || "".equals(key)){
			return null;
		}
		return mapper.queryLabel(key);
	}

	/**
	 * 根据父ID查询所有子类
	 */
	public List<Label> queryLabelByParent(Integer parentId) {
		if (parentId == null) return null;
		return mapper.queryLabelByParent(parentId);
	}

	/**
	 * 标签树结构
	 */
	public Label tree(Boolean allPower, Boolean materialPower, Boolean classify){
		List<Label> list = new ArrayList<>();
		if (allPower) {
			list = mapper.findAll();
		}else if (materialPower && classify){
			list = mapper.findAll();
		}
		else if (materialPower){
			Label l = mapper.queryLabel(LabelTypeEnum.Material.getKeyword());
			list = mapper.queryLabelByParent(l.getId());
			list.add(l);
		}else if (classify){
			Label l = mapper.queryLabel(LabelTypeEnum.Logistics.getKeyword());
			list = mapper.queryLabelByParent(l.getId());
			list.add(l);
		}
		Assert.isTrue(list != null && list.size() != 0, "标签库没有符合你身份的数据!");
		Label root = new Label();
		if (list == null || list.size() == 0){
			root.setId(0);
			root.setParentId(-1);
			root.setName("标签");
			return root;
		}else{
			Collections.sort(list);
			Label label = list.get(0);
			root.setId(label.getParentId());
			root.setParentId(-1);
			root.setName("标签");
		}
		buildTableTrue(root, list);
		return root;
	}

	/**
	 * 构建标签树
	 */
	private void buildTableTrue(Label root, List<Label> lst){
		if (root == null || lst.isEmpty()){
			return;
		}
		List<Label> children = lst.stream()
				.filter(e -> e.getParentId().equals(root.getId())).collect(Collectors.toList());
		for (Label label : children){
			root.addChild(label);
			buildTableTrue(label, lst);
		}
	}

	private Label load(Integer id){
		Label label = mapper.getById(id);
		Assert.notNull(label, "根据标签ID[" + id + "]未查到对应的标签信息");
		Assert.isTrue(label.getParentId() != -1, "根标签不允许进行修改删除操作!");
		return label;
	}

	/**
	 * 根据ID查询
	 */
	public Label getById(Integer id){
		Assert.notNull(id, "ID不能为空");
		return mapper.getById(id);
	}


	/**
	 * 标签检查
	 */
	private void lableCheck(Integer id, String name){
		List<LabelObject> list = LabelObjectMapper.findByLabelId(id);
		if (list != null && list.size() != 0) {
			Map<String, List<LabelObject>> map = list.stream().collect(Collectors.groupingBy(LabelObject::getObjectType));
			Set<String> set = map.keySet();
			Assert.isTrue(false, "标签[" + name + "]绑定了对象类型" + set + ",不允许再对其进行其他操作!");
		}
	}

	/**
	 * name 唯一性检查
	 */
	private void checkLabelName (Integer id, Integer parentId, String name){
		int count = mapper.countByName(id, parentId, name);
		Assert.isTrue(count == 0, "标签的名称" + name + "已经存在");
	}

	/**
	 *
	 */
	public List<Label> findLabel(String keyWord){
		Assert.isTrue(keyWord != null && !"".equals(keyWord), "标签的keyword不存在");
		return mapper.findLabel(keyWord);
	}

	/**
	 * 树的递归查询
	 */
	private Set<Integer> recursionTree(Set<Integer> ids, Integer parentId){
		if (ids != null && ids.size() > 2000){
			Assert.isTrue(false, "标签层级过多,不允查询!");
		}
		ids.add(parentId);
		List<Label> list = mapper.queryLabelByParent(parentId);
		if (list == null || list.size() == 0){
			return ids;
		}
		for (Label lst : list){
//			ids.add(lst.getId());
			recursionTree(ids, lst.getId());
		}
		return ids;
	}


	/**
	 *查询标签绑定
	 */
	public Label findLabelBind(Integer objectId, String keyword, String objectType){
		Assert.notNull(objectId, "请先选定对象ID");
		List<Label> labelList = mapper.findLabel(keyword);
		Assert.isTrue(labelList != null && labelList.size() == 1, "keyword为" + keyword + "的标签不唯一,请联系工程师排查");
//		List<Label> list = mapper.findLabelBind(objectId, objectType, labelList.get(0).getId());

		Set<Integer> setIds = new HashSet<>();
		Set<Integer> set = recursionTree(setIds, labelList.get(0).getId());
		List<Label> list = mapper.findLabelBinds(objectId, objectType, set);


		list.add(labelList.get(0));
		if (list != null && list.size() != 0){
			Collections.sort(list);
			Label root = new Label();
			Label label = list.get(0);
			root.setId(label.getParentId());
			root.setParentId(-1);
			root.setName("标签");
			buildTableTrue(root, list);
			return root;
		}else {
			return null;
		}
	}
}
