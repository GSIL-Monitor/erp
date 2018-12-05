package com.stosz.product.sync.service;

import com.stosz.olderp.ext.model.OldErpCountry;
import com.stosz.olderp.ext.service.IOldErpCountryService;
import com.stosz.product.ext.model.Country;
import com.stosz.product.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Future;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/11]
 */
@Service
public class OldErpCountrySyncService {
    @Resource
    private CountryService countryService;
    @Resource
    private IOldErpCountryService iOldErpCountryService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Async
    @Transactional(value = "pcTxManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Object> pull() {
        logger.info("========================国家信息同步pull开始了！=======================");
        List<OldErpCountry> oldErpCountries = iOldErpCountryService.findAll();
        Assert.notNull(oldErpCountries, "同步老erp国家信息时，获取到的记录为null");
        Assert.notEmpty(oldErpCountries, "同步老erp国家信息时，获取到的记录为空！");
        for (OldErpCountry oldErpCountry : oldErpCountries) {
            Country countryDB = countryService.get(oldErpCountry.getId());
            String code = oldErpCountry.getCode();
            if ("0".equals(code)) {
                code = null;
            }
            if (countryDB == null) {
                Country country = new Country();
                country.setId(oldErpCountry.getId());
                country.setName(oldErpCountry.getTitle());
                country.setEname(oldErpCountry.getEname());
                country.setCountryCode(code);
                countryService.insertByOld(country);
            } else {
                countryDB.setName(oldErpCountry.getTitle());
                countryDB.setEname(oldErpCountry.getEname());
                countryDB.setCountryCode(code);
                countryService.updateCountrySync(countryDB);
            }
        }
        logger.info("========================国家信息同步pull完成了！=======================");
        return new AsyncResult<>("国家信息同步pull成功！");
    }

    @Async
    public Future<Object> push() {
        logger.info("========================国家信息同步push开始了！=======================");
        List<Country> countryList = countryService.findAll();
        Assert.notNull(countryList, "push国家信息同步时，获取国家信息为null!");
        Assert.notEmpty(countryList, "push国家信息同步时，获取国家信息为空！");
        for (Country country : countryList) {
            String title = country.getName();
            OldErpCountry oldErpCountryDB = iOldErpCountryService.getByUnique(title);
            if (oldErpCountryDB == null) {
                OldErpCountry oldErpCountry = new OldErpCountry();
                oldErpCountry.setId(country.getId());
                oldErpCountry.setCode(country.getCountryCode());
                oldErpCountry.setEname(country.getEname());
                oldErpCountry.setTitle(country.getName());
                iOldErpCountryService.insert(oldErpCountry);
                logger.info("push国家信息成功，将{}插入国家表成功！", oldErpCountry);
            } else {
                oldErpCountryDB.setTitle(country.getName());
                oldErpCountryDB.setEname(country.getEname());
                oldErpCountryDB.setCode(country.getCountryCode());
                iOldErpCountryService.update(oldErpCountryDB);
                logger.info("push国家信息成功，将{}修改为{}国家表成功！", oldErpCountryDB, country);
            }
        }

        logger.info("========================国家信息同步push完成了！=======================");
        return new AsyncResult<>("国家信息同步push成功！");
    }

}  
