package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.OldErpAttribute;

import java.util.List;

/**
 * 老erp属性值的service接口
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/8/30]
 */
public interface IOldErpAttributeService{

    String url = "/admin/remote/IOldErpAttributeService";

    /**
     *
     *
     * 新增属性
     */
    void insert(OldErpAttribute oldErpAttribute);

    /**
     * 删除属性
     */
    void delete(Integer id);

    /**
     * 修改属性
     */
    void update(OldErpAttribute oldErpAttribute);

    /**
     * 根据限制条数和开始行数查询到对应的记录
     *
     * @param limit 限制条数，默认为2000
     * @param start 开始条数
     */
    List<OldErpAttribute> findByLimit(Integer limit, Integer start);

    /**
     * 根据id查询对应的属性
     */
    OldErpAttribute getById(Integer id);

    /**
     * 根据联合唯一主键获取到对应的属性
     */
    OldErpAttribute getByUnique(Integer productId, String title);

    /**
     * 获取老erp属性表的总记录数
     */
    Integer count();

    /**
     * 根据产品id和属性名称判断是否存在
     */
    Integer countByTitle(Integer productId, String title);

    List<OldErpAttribute> findByProductId(Integer productId);

    /**
     * 查询查重产品的属性
     */
    List<OldErpAttribute> findCheckByProductId(Integer productId);
}
