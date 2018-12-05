package com.stosz.store.service;

import com.stosz.store.ext.model.TransitDetail;
import com.stosz.store.mapper.TransitDetailMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author:ChenShifeng
 * @Description:    转寄流水
 * @Created Time:2017/11/23 15:49
 * @Update Time:
 */
@Service
public class TransitDetailService {

    @Resource
    private TransitDetailMapper mapper;

    /**
     * 添加detail
     *
     * @param detail
     */
    public Integer insert(TransitDetail detail) {
        detail.setCreateAt(LocalDateTime.now());
        return mapper.insert(detail);
    }


}
