package com.stosz.finance.ext.service;


import com.stosz.finance.ext.dto.request.AddPayable;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:财务模块对外接口
 * @Created Time:2017/11/23 14:47
 */
public interface IFinanceService {

    String url = "/store/remote/IFinanceService";

    /**
     * @Description: 对外系统财务生成应付款单
     * @param addPayable
     **/
    void addPayable(AddPayable addPayable);

    /**
     * @Description: 对外系统添加明细
     * @param addPayable
     **/
    void addItem(AddPayable addPayable);

    /**
     * @Description: 对外系统财务生成应付款单
     * @param addPayableList
     **/
    void addPayableList(List<AddPayable> addPayableList);

}
