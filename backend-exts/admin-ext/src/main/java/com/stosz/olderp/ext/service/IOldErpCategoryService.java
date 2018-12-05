package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.OldErpCategory;

import java.util.List;

/**
 * 老erp分类的service接口
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/8/30]
 */
public interface IOldErpCategoryService {
    String url = "/admin/remote/IOldErpCategoryService";
    void insert(OldErpCategory oldErpCategory);

    void update(OldErpCategory oldErpCategory);

    void updateStatus(OldErpCategory oldErpCategory);

    List<OldErpCategory> findAll();

    OldErpCategory getById(Integer id);

    void deleteById(Integer id);

    OldErpCategory getByName(String name);
}
