package com.stosz.tms.service.facebill.impl;

import com.stosz.plat.common.ResultBean;
import com.stosz.plat.utils.ArrayUtils;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.ext.service.IZoneService;
import com.stosz.tms.model.ShippingParcel;
import com.stosz.tms.model.taiwan.LandingCode;
import com.stosz.tms.service.facebill.IFaceBillArrange;
import com.stosz.tms.utils.HttpClientUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2018/1/24 14:18
 */
public class BaseFaceBill implements IFaceBillArrange {

    private Logger logger = LoggerFactory.getLogger(BaseFaceBill.class);

    @Autowired
    private IZoneService zoneService;

    @Override
    public ResultBean<HashMap<String, Object>> getFaceBill(ShippingParcel shippingParcel) {

        ResultBean<HashMap<String, Object>> resultBean = new ResultBean<>();
        return resultBean;
    }
}