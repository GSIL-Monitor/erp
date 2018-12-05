package com.stosz.product.ext.service;


import org.springframework.stereotype.Component;

/**
 * @author he_guitang
 * @version [1.0 , 2018/1/9]
 */
@Component
public interface IProductDeamonService {

    String url = "/product/remote/IProductDeamonService";

    /**
     * 产品中心后台程序
     */
    //排重报表自动调度
    void productNewReport();

    //五天无订单预警调度
    void warningTask();

    //七天无订单上架变成待下架调度
    void doWaitoffsaleTask();

    //三天未备案自动消档
    void noArchiveTask();

    //待下架消档任务调度
    void disppearedTask();

    /**
     * 老Erp数据同步
     */
    void oldErpCategoryNewTask();

    /**
     * 产品中心与老erp同步调度
     */

    //新erp同步老erp最后下单时间
    void lastOrderAtSync();

    //新erp同步老erp产品库存
    void stockSync();

}
