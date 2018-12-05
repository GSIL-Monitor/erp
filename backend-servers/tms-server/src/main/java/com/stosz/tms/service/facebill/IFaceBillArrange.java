package com.stosz.tms.service.facebill;

import com.stosz.plat.common.ResultBean;
import com.stosz.tms.model.ShippingParcel;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2018/1/24 14:14
 */
public interface IFaceBillArrange {
    /**
     * 获取面单数据
     * @return
     */
    ResultBean<HashMap<String, Object>> getFaceBill(ShippingParcel shippingParcel);
}
