package com.stosz.purchase.service;

import com.stosz.purchase.ext.model.Platform;
import com.stosz.purchase.mapper.PlatformMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/12/28]
 */
@Service
public class PlatformService {

    @Resource
    private PlatformMapper platformMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public List<Platform> findAll(){
        return platformMapper.findList();
    }
}  
