package com.stosz.product.service;

import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.plat.common.RestResult;
import com.stosz.product.ext.model.DepartmentZoneRel;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.mapper.DepartmentZoneRelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 部门区域关系表
 * 
 * @author  he_guitang
 * @version  [版本号, 1.0]
 */
@Service
public class DepartmentZoneRelService {
	@Resource
	private DepartmentZoneRelMapper mapper;
	@Resource
	private IDepartmentService iDepartmentService;
	@Resource
	private ZoneService zoneService;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 添加操作
	 */
	public void insert(Integer departmentId, Integer zoneId){
		Department department = null;
		Zone zone = null;
		try {
			department = iDepartmentService.get(departmentId);
			Assert.notNull(department, "添加部门区域关系中的部门ID["+departmentId+"]有误!");
			zone = zoneService.getById(zoneId);
			Assert.notNull(zone, "添加部门区域关系中的区域ID["+zoneId+"]有误!");
			List<Department> list = iDepartmentService.findByNo(department.getDepartmentNo());
			Assert.isTrue(list != null && list.size() > 0, "部门编码[" + department.getDepartmentNo() + "]有误,请联系管理员!");
			List<DepartmentZoneRel> relParam = new ArrayList<DepartmentZoneRel>();
			if(list.size() == 1){
				DepartmentZoneRel param = new DepartmentZoneRel();
				param.setDepartmentId(departmentId);
				param.setDepartmentName(department.getDepartmentName());
				param.setDepartmentNo(department.getDepartmentNo());
				param.setZoneId(zoneId);
				param.setUsable(true);
				mapper.insert(param);
				logger.info("部门区域关系表: {} 添加成功!", param);
			}else{
				for(Department lst : list){
					DepartmentZoneRel rel = new DepartmentZoneRel();
					rel.setDepartmentId(lst.getId());
					rel.setDepartmentNo(lst.getDepartmentNo());
					rel.setDepartmentName(lst.getDepartmentName());
					rel.setZoneId(zoneId);
					rel.setUsable(true);
					relParam.add(rel);
				}
				mapper.insertList(0, relParam);
			}
        } catch (DuplicateKeyException e) {
        	throw new IllegalArgumentException("部门["+department.getDepartmentName()+"]或下属部门已经绑定了区域["+zone.getTitle()+"]");
        }
	}
	
	/**
	 * 部门区域表关系表删除
	 */
	public void delete(Integer id){
		DepartmentZoneRel rel = load(id);
//		Assert.isTrue(rel.getUsable() == false, "部门["+rel.getDepartmentName()+"]与地区[ID:"+rel.getZoneId()+"]已绑定,不能删除!");
		Assert.isTrue(rel.getUsable() == false, "开启状态的数据不允许删除!");
		mapper.delete(id);
		logger.info("根据ID: {} 删除部门区域关系表成功", id);
	}
	
	/**
	 * 部门区域表的修改
	 */
	public void update(DepartmentZoneRel param){
		DepartmentZoneRel rel = load(param.getId());
		try {
			mapper.update(param);
			logger.info("根据ID: {} 将状态: {} 修改成: {} 成功", param.getId(), rel.getUsable(), param.getUsable());
		} catch (DuplicateKeyException e) {
        	throw new IllegalArgumentException("该部门[ID"+param.getDepartmentId()+"]已经绑定了该区域[ID"+param.getZoneId()+"]");
        }
	}
	
	/**
	 * 部门区域表状态的修改
	 */
	public void updateUsable(Integer id, Boolean usable){
		mapper.updateUsable(id, usable);
		logger.info("部门区域关系表ID: {} 状态变为: {}", id, usable);
	}
	
	/**
	 * 部门区域表的查询
	 */
	public RestResult findList(DepartmentZoneRel param){
		RestResult result = new RestResult();
		int count = mapper.count(param);
		result.setTotal(count);
		if(count == 0){
			return result;
		}
		List<DepartmentZoneRel> list = mapper.find(param);
//		List<Integer> departmentIds = list.stream().map(DepartmentZoneRel::getDepartmentId).collect(Collectors.toList());
		List<Integer> zoneIds = list.stream().map(DepartmentZoneRel::getZoneId).collect(Collectors.toList());
//		List<Zone> zoneList = zoneService.queryByIds(zoneIds);
//		Map<Integer, List<Zone>> zoneMaps = zoneList.stream().collect(Collectors.groupingBy(Zone::getId));
		Map<Integer, Zone> zoneMap = zoneService.findByIds(zoneIds);
		
//		List<Department> departmentList = iDepartmentService.findByIds(departmentIds);
//		Map<Integer, Department> departmentMap = departmentList.stream().collect(Collectors.toMap(Department::getId, Function.identity()));
		for(DepartmentZoneRel lst : list){
			Assert.notNull(zoneMap.get(lst.getZoneId()), "数据错误,区域[ID:"+lst.getZoneId()+"]在区域表中不存在!");
			lst.setZoneName(zoneMap.get(lst.getZoneId()).getTitle());
//			lst.setDepartmentName(departmentMap.get(lst.getDepartmentId()).getDepartmentName());
		}
		result.setItem(list);
		result.setCode(RestResult.OK);
		result.setDesc("部门区域关系表查询成功!");
		return result;
	}
	
	public List<DepartmentZoneRel> find(DepartmentZoneRel param){
		return mapper.find(param);
	}

	public List<DepartmentZoneRel> findAll(DepartmentZoneRel param){
		return mapper.findAll(param);
	}

	/**
	 * 根据区域id查询部门绑定的区域数
	 */
	public int countByZoneId(Integer zoneId){
		Assert.notNull(zoneId, "区域id为空,不能统计区域绑定的部门数!");
		return mapper.countByZoneId(zoneId);
	}
	
	/**
	 * 根据条件统计数量
	 */
	public int count(DepartmentZoneRel param){
		return mapper.count(param);
	}
	
	private DepartmentZoneRel load(Integer id){
		DepartmentZoneRel rel = mapper.getById(id);
		Assert.notNull(rel, "部门区域关系不存在[ID:"+id+"]!");
		return rel;
	}
	
}
