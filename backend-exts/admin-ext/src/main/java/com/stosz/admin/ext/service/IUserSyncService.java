package com.stosz.admin.ext.service;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/12/5]
 */
public interface IUserSyncService {

    String url = "/admin/remote/IUserSyncService";

    Boolean sync();

    void userSync();

}  
