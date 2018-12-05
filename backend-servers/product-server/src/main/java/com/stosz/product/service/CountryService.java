package com.stosz.product.service;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.rabbitmq.RabbitMQConfig;
import com.stosz.plat.rabbitmq.RabbitMQMessage;
import com.stosz.plat.rabbitmq.RabbitMQPublisher;
import com.stosz.product.ext.model.Country;
import com.stosz.product.ext.model.Currency;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.mapper.CountryMapper;
import com.stosz.product.mapper.ZoneMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * 国家设置
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Service
public class CountryService {

	@Autowired
	private RabbitMQPublisher rabbitMQPublisher;

	@Resource
	private CountryMapper countryMapper;
	@Resource
	private ZoneMapper zoneMapper;
	@Resource
	private CurrencyService currencyService;

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 国家添加
	 * 
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void addCountry(Country country) {
		try {
			if (country.getCurrencyCode() != null || !"".equals(country.getCurrencyCode())){
				Currency curr = currencyService.getByCurrencyCode(country.getCurrencyCode());
				Assert.notNull(curr,"币种编码["+country.getCurrencyCode()+"]不存在,请确认!");
			}
			countryMapper.insert(country);
			logger.info("国家: {} 添加成功", country.getName());
		} catch (DuplicateKeyException e) {
			throw new IllegalArgumentException("国家名称["+country.getName()+"]或者国家编码["+country.getCountryCode()+"]不能重复");
		}

		rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_insert).setData(country));

	}

	/**
	 * 国家删除
	 * 
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void deleteCountry(Integer countryId) {
		Country countryResult = load(countryId);
		List<Zone> list = zoneMapper.findByCountryId(countryId);
		Assert.isTrue(list == null || list.size() == 0, "国家["+countryResult.getName()+"]下面已经设置了区域,不能删除!");
		countryMapper.delete(countryId);
		logger.info("删除国家成功，{}", countryResult);

		rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_delete).setData(countryResult));
	}

	/**
	 * 国家更新
	 * 
	 */
	@Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	public void updateCountry(Country country) {
		Country countryResult = load(country.getId());
		try {
			if (country.getCurrencyCode() != null || !"".equals(country.getCurrencyCode())){
				Currency curr = currencyService.getByCurrencyCode(country.getCurrencyCode());
				Assert.notNull(curr,"币种编码["+country.getCurrencyCode()+"]不存在,请确认!");
			}
			countryMapper.update(country);
			logger.info("修改国家{}信息成功", countryResult.getName());
		} catch (DuplicateKeyException e) {
			throw new IllegalArgumentException("国家名称["+country.getName()+"]或者国家编码["+country.getCountryCode()+"]不能重复");
		}

		rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_update).setData(country));

	}


    /**
     * 同步时， 国家更新
     */
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updateCountrySync(Country country) {
        Country countryResult = load(country.getId());
        try {
            countryMapper.update(country);
            logger.info("修改国家{}信息成功", countryResult.getName());
        } catch (DuplicateKeyException e) {
            throw new IllegalArgumentException("国家名称[" + country.getName() + "]或者国家编码[" + country.getCountryCode() + "]不能重复");
        }
    }

	/**
	 * 国家列表查询
	 * 
	 */
	public RestResult findCountry(Country country) {
		RestResult result = new RestResult();
		int number = countryMapper.count(country);
		result.setTotal(number);
		if(number == 0){
			return result;
		}
		List<Country> list = countryMapper.find(country);
		result.setItem(list);
		result.setDesc("国家列表查询成功");
		return result;
	}

	/**
	 * 国家列表组件查询
	 *
	 */
	public RestResult findListCountry(Country country) {
		RestResult result = new RestResult();
		List<Country> list = countryMapper.findList(country);
		result.setItem(list);
		result.setDesc("国家列表查询成功");
		return result;
	}

	public Country get(Integer id) {
		Assert.notNull(id, "国家id信息不能为空!");
		return countryMapper.getById(id);
	}

	public Country load(Integer id) {
		Country c = get(id);
		Assert.notNull(c, "国家id:" + id + " 在数据库中不存在！");
		return c;
	}


	public Country findByCode(String countryCode) {
		Assert.hasText(countryCode, "区域编码不能为空!");
		return countryMapper.getByCode(countryCode);
	}

	public void insertByOld(Country country) {
		Assert.notNull(country, "不允许插入空的国家信息！");
		int i = countryMapper.insertByOld(country);
		Assert.isTrue(i == 1, "插入国家信息失败！");
		logger.info("插入国家信息{}成功！", country);

		rabbitMQPublisher.saveMessage(new RabbitMQMessage().setMethod(RabbitMQConfig.method_insert).setData(country));
	}


	public void truncate() {
		countryMapper.truncate();
		logger.info("截断国家表成功！");
	}


	public List<Country> findAll() {
		return countryMapper.findAll();
	}
	
	/**
	 * 国家模糊搜索
	 */
	public RestResult likeName(String name) {
		RestResult result = new RestResult();
	    List<Country> list = countryMapper.likeName(name);
	    result.setItem(list);
	    result.setDesc("国家模糊查询成功");
	    return result;
    }
	
	public Country getById(Integer countryId) {
	    return countryMapper.getById(countryId);
    }

}
