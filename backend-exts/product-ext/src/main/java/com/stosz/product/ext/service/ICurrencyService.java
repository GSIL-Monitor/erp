package com.stosz.product.ext.service;

import com.stosz.product.ext.model.Currency;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/12/12]
 * 币种对外接口
 */
public interface ICurrencyService {

    String url = "/product/remote/ICurrencyService";

     Currency getByCurrencyCode(String currencyCode);


}
