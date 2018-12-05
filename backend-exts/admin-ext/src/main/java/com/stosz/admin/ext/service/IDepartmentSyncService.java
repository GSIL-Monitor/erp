package com.stosz.admin.ext.service;

/**
 * 同步部门信息的接口
 * @author xiongchenyang
 * @version [1.0 , 2017/12/4]
 */
public interface IDepartmentSyncService {

    String url = "/admin/remote/IDepartmentSyncService";

    /**
     * 同步OA的部门信息，绑定老erp的部门信息
     * @return 是否成功
     */
    Boolean sync();
}
