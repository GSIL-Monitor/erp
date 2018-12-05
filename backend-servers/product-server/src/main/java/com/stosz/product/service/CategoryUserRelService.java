package com.stosz.product.service;

import com.stosz.admin.ext.model.User;
import com.stosz.plat.common.MBox;
import com.stosz.plat.enums.CategoryUserTypeEnum;
import com.stosz.product.ext.model.Category;
import com.stosz.product.ext.model.CategoryUserRel;
import com.stosz.product.mapper.CategoryUserRelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryUserRelService {
	
	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private CategoryUserRelMapper categoryUserRelMapper;
	@Resource
	private CategoryService categoryService;
	

	/**
	 * 查询分页
	 * @param categoryUserRel
	 * @return
	 */
	public List<CategoryUserRel> queryByPage(CategoryUserRel categoryUserRel) {
		return categoryUserRelMapper.find(categoryUserRel);
	}

	public List<CategoryUserRel> queryAll() {
		return categoryUserRelMapper.findAll();
	}


	/**
	 * 汇总条件的记录数
	 * @param categoryUserRel
	 * @return
	 */
	public int queryCount(CategoryUserRel categoryUserRel) {
		return categoryUserRelMapper.count(categoryUserRel);
	}
	
	/**
	 * 用户id+一级品类id+用户类型(排重,广告)的唯一
	 */
	public int indexCount(CategoryUserRel param) {
		return categoryUserRelMapper.indexCount(param);
	}
	
	/**
	 * 用户与一级品类关系的删除
	 * 			he_guitang
	 */
	public void delete(Integer id) {
		CategoryUserRel rel = categoryUserRelMapper.getById(id);
		Assert.notNull(rel, "没有符合删除条件的信息");
		Assert.isTrue(rel.getUsable() == false, "开启状态的数据不允许删除!");
		categoryUserRelMapper.delete(id);
		logger.info("id为: {}用户与一级品类的关系删除成功", id);
	}
	
	/**
	 * 查询符合条件的记录数
	 * @param id
	 * @return
	 */
	public CategoryUserRel get(Integer id) {
		CategoryUserRel categoryUserRel = categoryUserRelMapper.getById(id);
		return categoryUserRel;
	}

	/**
	 * 新增用户品类信息
	 * @param categoryUserRel
	 * @return
	 */
	@Transactional(value="pcTxManager",rollbackFor=Exception.class)
    public void add(CategoryUserRel categoryUserRel, User dto) {
 //       int countExists = queryCount(categoryUserRel);
        int countExists = indexCount(categoryUserRel);
		Assert.isTrue(countExists == 0,"用户所属品类对应关系已存在,不能重复添加!");
		categoryUserRel.setDepartmentNo(dto.getDepartmentNo());
		int count =  categoryUserRelMapper.insert(categoryUserRel);
	    Assert.isTrue(count == 1,"用户品类保存失败!");
		logger.info("用户品类保存成功:{}" , categoryUserRel);
	}

	/**
	 * 用户品类信息
	 * @param categoryUserRel
	 * @return
	 */
	@Transactional(value="pcTxManager",rollbackFor=Exception.class)
	public void update(CategoryUserRel categoryUserRel) {
		CategoryUserRel rel = categoryUserRelMapper.getById(categoryUserRel.getId());
		Assert.notNull(rel, "传入的用户品类信息不存在！");
		int count =  categoryUserRelMapper.update(categoryUserRel);
	    Assert.isTrue(count == 1,"用户品类修改失败!");
		logger.info("用户品类修改成功:{}" , categoryUserRel);
	}

	/**
	 * 
	 * @param userId
	 * @param usable
	 * @param userType
	 * @return 
	 */
	public List<Integer> findByUserId(Integer userId, Boolean usable, CategoryUserTypeEnum userType) {
		CategoryUserRel rel = new CategoryUserRel();
		rel.setUserId(userId);
		rel.setUsable(usable);
		rel.setUserType(userType);
		return categoryUserRelMapper.findTopCategoryByUserId(rel);
	}
	
	/**
	 * 根据品类判断是否有用户与该品类相关联
	 */
	public int findByCategoryID(Integer categoryId){
		return categoryUserRelMapper.findByCategoryID(categoryId);
	}
	
	/**
	 * 根据用户查询对应的品类id
	 */
	public List<Integer> checkUserCategoryrel(CategoryUserTypeEnum userType){
		//当前用户与一级品类的关系
		CategoryUserRel rel = new CategoryUserRel();
		rel.setUserId(MBox.getLoginUserId());
		rel.setUsable(true);
		rel.setUserType(userType);
		List<Integer> ids = categoryUserRelMapper.findTopCategoryByUserId(rel);
		List<Category> list = categoryService.findByIds(ids);
		List<String> nos = list.stream().map(Category::getNo).collect(Collectors.toList());
		List<Category> lst = categoryService.findByCategoryNos(nos);
		List<Integer> categoryIds = lst.stream().map(Category::getId).collect(Collectors.toList());
		return categoryIds;
	}
	
	
}
