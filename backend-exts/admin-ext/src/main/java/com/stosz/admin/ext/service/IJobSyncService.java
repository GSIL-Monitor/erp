package com.stosz.admin.ext.service;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/12/5]
 */
public interface IJobSyncService {
    String url = "/admin/remote/IJobSyncService";

    Boolean pull();

    void jobSync();
}
