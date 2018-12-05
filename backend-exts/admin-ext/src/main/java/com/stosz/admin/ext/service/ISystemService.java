package com.stosz.admin.ext.service;

import com.stosz.plat.common.SystemDto;

/**
 * @auther carter
 * create time    2017-12-04
 */
public interface ISystemService {

    String url="/admin/remote/ISystemService";

    SystemDto getSystemDto(String systemName);

}
