package com.stosz.product.service;

import com.google.common.collect.Sets;
import com.stosz.fsm.FsmProxyService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.product.ext.enums.ProductZoneEvent;
import com.stosz.product.ext.enums.ProductZoneState;
import com.stosz.product.ext.model.ProductNewZone;
import com.stosz.product.ext.model.ProductZone;
import com.stosz.product.mapper.ProductZoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 产品国家表Service
 *
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Service
@Transactional
public class ProductZoneService {

	@Resource
	private ProductZoneMapper mapper;
	@Resource
	private ProductNewZoneService productNewZoneService;

    @Resource
    private FsmProxyService<ProductZone> pcProductZoneFsmProxyService;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	public void insert(ProductZone productZone) {
        Assert.notNull(productZone, "不允许插入空的产品区域信息！");
        int i = mapper.insert(productZone);
        logger.info("插入产品区域信息成功！{} ", productZone);
    }

    public void insertList(List<ProductZone> productZoneList) {
        Assert.notNull(productZoneList, "不允许插入空的产品区域集合！");
        if (productZoneList.isEmpty()) {
            return;
        }
        mapper.insertList(0, productZoneList);
        logger.info("插入产品区域集合{}成功！", productZoneList);
    }

    public void create(ProductZone productZone) {
	    insert(productZone);
        pcProductZoneFsmProxyService.processNew(productZone,"");
		pcProductZoneFsmProxyService.processEvent(productZone, ProductZoneEvent.create, MBox.getLoginUserName());
	}

	/**
	 * 产品国家对应关系的删除
	 */
	public void deleteById(Integer id) {
		mapper.delete(id);
	}

	public void updateCreator(Integer productZoneId, Integer creatorId, String creator, LocalDateTime updateAt) {
		Assert.isTrue(creatorId != null, "该用户没有登录,请重新登录!");
		Assert.isTrue(creator != null && !"".equals(creator), "该用户没有登录,请重新登录!");
		mapper.updateCreator(productZoneId, creatorId, creator, updateAt);
		logger.info("产品区域ID: {} 的创建人更新成==>: {} 成功!", productZoneId, creatorId, creator);
	}

	public void update(ProductZone productZone) {
		Assert.notNull(productZone, "不允许将产品区域信息修改为空！");
		ProductZone productZoneDB = mapper.getById(productZone.getId());
		Assert.notNull(productZoneDB, "要修改的产品区域信息在数据库中不存在！");
		int i = mapper.update(productZone);
		Assert.isTrue(i == 1, "修改产品区域信息失败！");
		logger.info("修改产品区域信息{}为{}成功！", productZoneDB, productZone);
	}

    public void updateLastOrderAt(ProductZone productZone) {
        Assert.notNull(productZone, "不允许将产品区域信息修改为空！");
        ProductZone productZoneDB = mapper.getById(productZone.getId());
        Assert.notNull(productZoneDB, "要修改的产品区域信息在数据库中不存在！");
        int i = mapper.updateLastOrderAt(productZone);
        Assert.isTrue(i == 1, "修改产品区域信息失败！");
        logger.info("修改产品区域信息{}为{}成功！", productZoneDB, productZone);
    }

	public void updateStock(ProductZone productZone) {
		Assert.notNull(productZone, "不允许将产品区域信息修改为空！");
		ProductZone productZoneDB = mapper.getById(productZone.getId());
		Assert.notNull(productZoneDB, "要修改的产品区域信息在数据库中不存在！");
        if (productZone.getStock() == null) {
            productZone.setStock(0);
        }
		if (productZone.getQtyPreout() == null) {
			productZone.setQtyPreout(0);
		}
		if (productZone.getQtyRoad() == null) {
			productZone.setQtyRoad(0);
		}
		int i = mapper.updateStock(productZone);
		Assert.isTrue(i == 1, "修改产品区域信息失败！");
		logger.info("将产品区域信息{}修改为{}成功！", productZoneDB, productZone);
	}

    public void updateState(List<Integer> ids, String state) {
        Assert.notNull(ids, "要修改的id集合为空");
        for (Integer id : Sets.newHashSet(ids))
        {//转换为set，防止id重复，浪费性能 add by carter 20171011
            mapper.updateState(id, state, LocalDateTime.now());
            logger.info("修改产品区域id为{}的状态为{}成功！", id, state);
        }
    }

	public List<ProductZone> find(ProductZone param){
        return mapper.find(param);
    }

    public int updateArchiveUser(String loginid,Integer id) {
        return mapper.updateArchiveUser(loginid, id);
    }

	/**
	 * 根据产品id查询查询产品国家表
	 * --产品,国家,部门关系
	 */
	public List<ProductZone> findByProductId(Integer productId) {
		Assert.notNull(productId, "产品ID为空,不能查询对应的产品区域信息!");
		ProductZone param = new ProductZone();
		param.setProductId(productId);
		return mapper.find(param);
//        return mapper.findByProductId(productId);
    }
	

	public List<ProductZone> findByProductIds(String state, List<Integer> productIds, Integer productNewId) {
		List<ProductZone> zones = mapper.findByProductIds(state, productIds);
		List<ProductNewZone> newZones = productNewZoneService.findByProductNewId(productNewId);
		Set<Integer> list = newZones.stream().map(ProductNewZone::getZoneId).collect(Collectors.toSet());
		for (ProductZone zone : zones){
			if (list.contains(zone.getZoneId())){
				zone.setChecked(true);
			}
		}
		Collections.sort(zones);
		return zones;
	}

	public List<ProductZone> findBySpu(String spu) {
		return mapper.findBySpu(spu);
	}


    public List<ProductZone> findByProductNewId(Integer productNewId) {
        return mapper.findByProductNewId(productNewId);
    }

    public ProductZone getById(Integer id) {
        return mapper.getById(id);
    }

    public List<ProductZone> findByIds(List<Integer> ids){
		Assert.isTrue(ids != null && ids.size() > 0, "区域ID集合为空,无法查询产品区域信息!");
		return mapper.findByIds(ids);
	}

    public ProductZone getByUnique(String zoneCode, Integer productId, Integer departmentId) {
		return mapper.getByUnique(zoneCode, productId, departmentId);
	}
    
    public RestResult queryByProductId(Integer productId){
    	RestResult result = new RestResult();
    	int count = mapper.queryByProductIdCount(productId);
    	result.setTotal(count);
    	if(count == 0){
    		result.setDesc("没有符合条件的数据");
    		return result;
    	}
    	List<ProductZone> list = mapper.findByProductId(productId);
    	result.setItem(list);
    	result.setDesc("产品区域查询成功");
    	return result;
    }

	/**
	 * 根据产品id、部门获取产品区域信息，建站时会用到
	 *
	 * @param productId
	 * @param departmentId
	 * @return
	 */
	public List<ProductZone> findByProductIdAndDepartmentId(Integer productId, Integer departmentId) {
		return mapper.findByProductIdAndDepartmentId(productId, departmentId);
	}

	public List<ProductZone> findByDate(ProductZone param) {
		Assert.notNull(param, "查询条件不允许为空！");
		Assert.notNull(param.getMinCreateAt(), "开始时间不允许为空！");
		Assert.notNull(param.getMaxCreateAt(), "结束时间不允许为空！");
		return mapper.findByDate(param);
	}

	public int countByDate(LocalDateTime startTime, LocalDateTime endTime) {
		Assert.notNull(startTime, "开始时间不允许为空！");
		Assert.notNull(endTime, "结束时间不允许为空！");
		return mapper.countByDate(startTime, endTime);
	}

	public void processOffsaleEvent(ProductZone productZone, String memo) {
		Integer stock = productZone.getStock();
		if (stock == 0) {
			processEvent(productZone, ProductZoneEvent.cancel, memo, MBox.getLoginUserName());
		} else {
			processEvent(productZone, ProductZoneEvent.offsale, memo, MBox.getLoginUserName());
		}
	}

    public void processEvent(ProductZone productZone,  ProductZoneEvent event, String memo,String uid) {
        if (uid == null || uid.equals("")) {
            uid= MBox.getSysUser();
        }
        this.pcProductZoneFsmProxyService.processEvent(productZone,event,uid,LocalDateTime.now(),memo);

    }


    public void processLog(ProductZone productZone,  ProductZoneEvent event, String memo,String uid) {
        pcProductZoneFsmProxyService.processLog(productZone , event.name() ,uid, LocalDateTime.now(), memo);
    }

    //------------   七天未出单的记录


    /**
     * 找出N天未建档的产品，准备对其进行销档
     */
    public List<ProductZone> findNoArchiveForDisappeared(int day, int limit) {
        //       int n=3;
        List<ProductZone> productZones = mapper.findNoArchiveFixDay(day, limit);
        logger.info("找到 {} 天未建档的产品区域，准备对其进行消档，本次找到记录数:{}", day, productZones.size());
        return productZones;
    }


	/**
	 * 找出N天未出单的产品，准备对其进行销档
	 */
    public List<ProductZone> findNoOrderForDisappeared(int days, int limit) {
        // int n=7;
        List<ProductZone> productZones = mapper.findNoOrderFixDay(days, limit);
        logger.info("找到 {} 天未出单的产品区域，准备对其进行消档，本次找到记录数:{}", days, productZones.size());
        return productZones;
    }

    /**
     * 待下架查询
     */
	public List<ProductZone> findWaitOffSale(ProductZoneState state, LocalDateTime time, int limit) {
		List<ProductZone> list = mapper.findWaitOffSale(state, time, limit, 0);
		return list;
    }

	public int countNorderFixDay(Integer deptId, Integer productId, LocalDateTime time) {
		return mapper.countNorderFixDay(deptId, productId, time);
	}

    public int coutnLastOnsaleTime(Integer deptId, Integer productId, LocalDateTime time, String state) {
        return mapper.coutnLastOnsaleTime(deptId, productId, time, state);
    }

    public List<ProductZone> findByDeptPcId(Integer deptId, Integer productId) {
        List<ProductZone> list = mapper.findByDeptPcId(deptId, productId);
        return list;
    }

	public int countWaitOffsale(String state, LocalDateTime time) {
		return mapper.countWaitOffsale(state, time);
	}

	public int countWarningForNoOrder(LocalDateTime time, String state, Set<Integer> deptIds) {
		return mapper.countWarningForNoOrder(time, state, deptIds);
	}

	public List<ProductZone> findWarningForNoOrder(LocalDateTime time, String state, Integer limit, Integer start, Set<Integer> deptIds) {
		List<ProductZone> list = mapper.findWarningForNoOrder(time, state, limit, start, deptIds);
		return list;
	}


	public LocalDateTime maxLastOrderTime(Integer deptId, Integer productId) {
		return mapper.maxLastOrderTime(deptId, productId);
	}

	public int countByZoneCode(String zoneCode){
		return mapper.countByZoneCode(zoneCode);
	}
	
}
