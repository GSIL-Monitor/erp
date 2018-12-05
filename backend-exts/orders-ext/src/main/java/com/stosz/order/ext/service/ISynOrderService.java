package com.stosz.order.ext.service;

/**
 * @auther carter
 * create time    2017-12-12
 * syn order  related  interface
 */
public interface ISynOrderService {

    /**
     * syn   order main table info ;
     */
    void synOrderMainInfo();


    /**
     * syn order relation table info
     *
     * like  link info , ordersItem , orderWebInfo
     */
    void synOrderRelationInfo();






}
