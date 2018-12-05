package com.stosz.admin.service.admin;

import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IUserDepartmentRelService;
import com.stosz.admin.mapper.admin.UserDepartmentRelMapper;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.StringUtils;
import com.stosz.product.ext.model.UserDepartmentRel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * pc数据权限
 * 
 * @author  he_guitang
 * @version  [版本号, 1.0]
 */
@Service
public class UserDepartmentRelService implements IUserDepartmentRelService {

    @Autowired
	private UserDepartmentRelMapper mapper;

	@Resource
	private UserService userService;
	@Resource
	private DepartmentService departmentService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
			
	/**
	 * 添加
	 */
	@Override
	@Transactional(value = "adminTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void add(UserDepartmentRel param){
		try {
			param.setUsable(false);
			Integer departmentId = param.getDepartmentId();
			Department department = departmentService.get(departmentId);
			Assert.notNull(department,"找不到对应的部门！");
			param.setDepartmentName(department.getDepartmentName());
			param.setDepartmentNo(department.getDepartmentNo());
			mapper.insert(param);
	        logger.info("产品数据权限添加成功: {}", param);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new IllegalArgumentException("用户[" + param.getUserId() + "]已经在部门[" + param.getDepartmentName() + "]中存在!");
		}
	}
	
	/**
	 * 删除
	 */
	@Override
	@Transactional(value = "adminTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Integer id){
		UserDepartmentRel result = load(id);
		mapper.delete(id);
        logger.info("产品数据权限删除成功: {}", result);
	}
	
	/**
	 * 修改
	 */
	@Override
	@Transactional(value = "adminTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(UserDepartmentRel param){
		UserDepartmentRel result = load(param.getId());
		try {
			result.setUsable(param.getUsable());
			mapper.update(result);
			logger.info("产品数据权限: {}修改成: {}成功", result, param);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			throw new IllegalArgumentException("用户["+param.getUserId()+"]已经在部门["+param.getId()+"]中存在!");
		}
	}
	
	/**
	 * 查询
	 */
	@Override
	public RestResult find(UserDepartmentRel param){
		RestResult result = new RestResult();
		if(StringUtils.isNotBlank(param.getUsername())){
			User user = userService.getByLastname(param.getUsername());
			Assert.notNull(user,"数据库中找不到该用户！");
			param.setUserId(user.getId());
		}
		int count  = mapper.count(param);
		result.setTotal(count);
		if(count == 0){
			return result;
		}
		List<UserDepartmentRel> list = mapper.find(param);
		formatUserDepartmentRel(list);
		result.setItem(list);
		result.setDesc("产品数据权限查询成功");
		return result;
	}
	
//	/**
//	 * 根据用户id查询部门
//	 */
//	@Override
//	public List<UserDepartmentRel> findByUserId(Integer userId){
//		return findByUserId(userId, true);
//	}
	
	@Override
	public List<UserDepartmentRel> findByUserId(Integer userId, Boolean usable){
		return mapper.findByUserId(userId, usable);
	}
	
	private void formatUserDepartmentRel(List<UserDepartmentRel> list) {
		if (list != null && list.size() > 0) {
			for (UserDepartmentRel userDepartmentRel : list) {
				Integer userId = userDepartmentRel.getUserId();
				User user = userService.getByUserId(userId);
				if (user != null) {
					userDepartmentRel.setUsername(user.getLastname());
				}
			}
		}
	}

	/**
	 * 查询
	 */
	@Override
	public List<UserDepartmentRel> findByParam(UserDepartmentRel param) {
		List<UserDepartmentRel> list = mapper.find(param);
		return list;
	}
	

	private UserDepartmentRel load(Integer id){
		UserDepartmentRel result = mapper.getById(id);
		Assert.notNull(result,"id为["+id+"]的数据权限在数据库中不存在");
		return result;
	}


	@Override
	public List<Integer> findDeptIdByUser(Integer userId) {
		return mapper.findDeptIdByUserId(userId);
	}
}
