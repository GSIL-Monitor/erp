package com.stosz.olderp.ext.service;

import com.stosz.olderp.ext.model.OldErpBlackList;

import java.util.List;

/**
 * @auther carter
 * create time    2017-12-03
 */
public interface IOldErpBlackListService {

    String url = "/admin/remote/IOldErpBlackListService";

    List<OldErpBlackList> getByIdRegion(int start, int fetch_count);

    int getOldMaxId();

}
