package com.stosz.product.service;

import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.rabbitmq.RabbitMQConfig;
import com.stosz.plat.rabbitmq.RabbitMQMessage;
import com.stosz.plat.rabbitmq.RabbitMQPublisher;
import com.stosz.product.ext.model.Country;
import com.stosz.product.ext.model.DepartmentZoneRel;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.ext.service.IZoneService;
import com.stosz.product.mapper.ZoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 区域service
 * 
 * @author  he_guitang
 * @version  [版本号, 1.0]
 */
@Service
public class ZoneService implements IZoneService {
	@Resource
	private ZoneMapper mapper;
	@Resource
	private CountryService countryService;
	@Resource
	private ProductNewZoneService productNewZoneService;
	@Resource
	private ProductZoneService productZoneService;
	@Resource
	private RabbitMQPublisher rabbitMQPublisher;
	@Resource
	private IUserService iUserService;
	@Resource
	private DepartmentZoneRelService departmentZoneRelService;

	private  final Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 区域添加
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void insert(Zone zone){
		try {
			mapper.insert(zone);
			logger.info("区域: {} 添加成功!", zone);
		} catch (DuplicateKeyException e) {
			logger.error(e.getMessage(),e);
			Country country = countryService.getById(zone.getCountryId());
			throw new IllegalArgumentException("国家["+country.getName()+"]下已经有了["+zone.getTitle()+"]区域,或者区域编码["+zone.getCode()+"]已经存在");
		}

		rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_insert).setData(zone));
		
	}

    /**
     * @author xiongchenyang 2017/9/12
     * 同步老erp区域信息时的插入方法，需要插入id
     */
    public void insertOld(Zone zone) {
        try {
            Assert.notNull(zone, "不允许插入空的区域！");
            int i = mapper.insertOld(zone);
            Assert.isTrue(i == 1, "添加区域信息失败！");
            logger.info("添加区域信息{}成功！", zone);
        } catch (DuplicateKeyException e) {
            logger.info("区域{}，在数据库中已经存在，不允许重复插入！", zone);
        }

		rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_insert).setData(zone));
    }

    public void insertList(List<Zone> zoneList) {
        Assert.notNull(zoneList, "要插入的区域集合不允许为空！");
        mapper.insertList(0, zoneList);
        logger.info("批量插入区域信息{}成功！", zoneList);


//        zoneList.stream().forEach(zone->rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_insert).setData(zone)));

    }

    /**
	 * 区域删除
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void delete(Integer id){
		Zone zone = load(id);
		int countNew = productNewZoneService.countByZoneId(id);
		Assert.isTrue(countNew == 0, "区域["+zone.getTitle()+"]已经关联了新品,不能删除!");
		int count = productZoneService.countByZoneCode(zone.getCode());
		Assert.isTrue(count == 0, "区域["+zone.getTitle()+"]已经关联产品,不能删除!");
		int countDeptZone = departmentZoneRelService.countByZoneId(id);
		Assert.isTrue(countDeptZone == 0, "区域["+zone.getTitle()+"]已经关联部门,不能删除!");
		mapper.delete(id);
		logger.info("区域:{} 删除成功",zone);

		rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_delete).setData(zone));
	}
	
	
	
	/**
	 * 区域修改
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void update(Zone zone){
		Zone rest = load(zone.getId());
		try {
			mapper.update(zone);
			logger.info("区域: {} 成功被修改成: {} 成功!",rest,zone);
		} catch (DuplicateKeyException e) {
			Country country = countryService.getById(zone.getCountryId());
			throw new IllegalArgumentException("国家["+country.getName()+"]下已经有了["+zone.getTitle()+"]区域,或者区域编码["+zone.getCode()+"]已经存在");
		}

		rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_update).setData(zone));

	}
	
	/**
	 * 区域单条查询_缓存
	 */
//	@Cacheable(value = "getZoneById", unless = "#result == null")
	public Zone getCacheById(Integer id){
		return mapper.getById(id);
	}
	
	/**
	 * 区域单条查询
	 */
	public Zone getById(Integer id){
		return mapper.getById(id);
	}
	
	public Map<Integer,Zone> findByIds(List<Integer> ids){
		if (ids == null || ids.isEmpty()) {
			return new HashMap<>();
		}
		List<Zone> zones = mapper.findByIds(ids);
		return zones.stream().collect(Collectors.toMap(Zone::getId , Function.identity()));
	}
	
	/**
	 * 批量查询区域
	 */
	public List<Zone> queryByIds(List<Integer> ids){
		Assert.isTrue(ids != null && !ids.isEmpty(), "根据区域id批量查询的参数有误[null或空]");
		return mapper.findByIds(ids);
	}
	
	/**
	 * 区域title模糊
	 */
	public RestResult titleLike(String title) {
		RestResult result = new RestResult();
		List<Zone> list = mapper.titleLike(title);
	    result.setItem(list);
	    result.setDesc("区域名模糊成功");
		return result;
    }
	
	/**
	 * 区域列表
	 */
	public RestResult find(Zone zone){
		RestResult result = new RestResult();
		int count = mapper.count(zone);
		result.setTotal(count);
		if(count == 0){
			return result;
		}
		List<Zone> list = mapper.findList(zone);
		result.setCode(RestResult.OK);
		result.setItem(list);
		result.setDesc("区域列表查询成功");
		return result;
	}
	
	public List<Zone> findStintZone(){
		List<Zone> zoneLst = new ArrayList<Zone>();
		User user = iUserService.getById(ThreadLocalUtils.getUser().getId());
		Assert.notNull(user, "该用户未登录,请重新登录!");
		DepartmentZoneRel rel = new DepartmentZoneRel();
		rel.setDepartmentId(user.getDepartmentId());
		rel.setUsable(true);
		List<DepartmentZoneRel> zoneList = departmentZoneRelService.findAll(rel);
		List<Integer> ids = zoneList.stream().map(DepartmentZoneRel::getZoneId).collect(Collectors.toList());
		if(ids == null || ids.isEmpty()){
			return zoneLst;
		}else{
			zoneLst = mapper.findByIds(ids);
			return zoneLst;
		}
	}
	
	/**
	 * 根据国家id查询区域
	 */
	public List<Zone> findByCountryId(Integer countryId){
		return mapper.findByCountryId(countryId);
	}
	
    /**
     * 区域父ID列表查询
     */
	public List<Zone> findByParentId(Integer parentId) {
		return mapper.findByParentId(parentId);
    }
	
    public Zone load(Integer id) {
        Zone zone = getById(id);
        Assert.notNull(zone, "区域id:" + id + "在数据库中不存在!");
        return zone;
    }

    public Zone getByCode(String code) {
        Assert.notNull(code, "区域编码不允许为空！");
        return mapper.getByCode(code);
    }

    public Zone getByUnique(Integer countryId, String title) {
        Assert.notNull(countryId, "国家id不予许为空！");
        Assert.notNull(title, "区域标题不允许为空！");
        return mapper.getByUnique(countryId, title);
    }

    public List<Zone> findAll() {
        return mapper.findAll();
    }

	@Override
	public String getCountryCodeById(Integer id) {
		//todo
		return null;
	}

}

