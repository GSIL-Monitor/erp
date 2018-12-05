package com.stosz.olderp.ext.service;

import com.stosz.admin.ext.model.User;
import com.stosz.olderp.ext.model.OldErpUser;

import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/8/25]
 */
public interface IOldErpUserService {
    String url = "/admin/remote/IOldErpUserService";

    Integer insertOldErpUser(User user);

    void deleteOldErpUser(Integer id);

    /**
     * 根据老erp用户中文名称获取到对应id
     *  TODO 等老erp将用户登录名与OA同步后，需要修改改方法的入参为Loginid
     * @param niceName 用户中文名称
     * @return 用户id
     */
    Integer getIdByNiceName(String niceName);

    /**
     * 查询到用户表中所有不重复的数据
     */
    List<OldErpUser> findNoRepeat();

    /**
     * 获取到用户表中所有重复数据
     */
    List<OldErpUser> findRepeat();

    /**
     * 查询所有用户信息
     */
    List<OldErpUser> findAll();

    /**
     * 根据登录名获取到用户id
     */
    Integer getIdByLoginId(String loginid);

}
