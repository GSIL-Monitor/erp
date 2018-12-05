package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.OldErpCategoryNew;
import com.stosz.product.ext.model.Category;

import java.util.List;

/**
 * @author he_guitang
 * @version [1.0 , 2018/1/19]
 */
public interface IOldErpCategoryNewService {

    String url = "/admin/remote/IOldErpCategoryNewService";

    void add(OldErpCategoryNew param);

    void delete(Integer id);

    int update(OldErpCategoryNew param);

    int addBatch(List<OldErpCategoryNew> param);

    void truncate();

    int countAll();

    OldErpCategoryNew getLastData();

    int updateRootId();

//    void updateLeaf(Boolean leaf, Integer id);
//
//    void updateById(OldErpCategoryNew param);

}
