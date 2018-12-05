package com.stosz.product.ext.service;

import com.stosz.product.ext.enums.TypeEnum;
import com.stosz.product.ext.model.Partner;

import java.util.List;

/**
 * 产品数据对外接口
 * @author minxiaolong
 * @version [1.0 , 2018/1/03]
 */
public interface IPartnerService {

    String url = "/product/remote/IPartnerService";

    /**
     * 根据类型查询合作伙伴
     * @param type
     * @return
     */
    List<Partner> findPartnerByType(TypeEnum type);

    Partner getById(Integer id);

    /**
     * 根据名称和合作伙伴类型，模糊搜索
     * @param search 搜索内容
     * @param type 合作伙伴类型
     * @return  匹配的集合
     */
    List<Partner> findNameBySearch(String search,TypeEnum type);


    Partner findByCodeAndType(String code, TypeEnum typeEnum);

    Integer insert(Partner partner);

    Boolean update(Partner partner);

    Boolean updateState(Partner partner);

    List<Partner> findByIds(List<Integer> ids);

}
