package com.stosz.store.service;

import com.stosz.store.ext.model.Invalid;
import com.stosz.store.ext.model.InvalidItem;
import com.stosz.store.mapper.InvalidItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author:ChenShifeng
 * @Description: 报废 明细
 * @Created Time:2017/11/23 15:49
 * @Update Time:
 */
@Service
public class InvalidItemService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private InvalidItemMapper mapper;

    /**
     * 添加
     *
     * @param stock
     */
    public Integer insert(InvalidItem stock) {
        return mapper.insert(stock);
    }

    /**
     * 删除 by invalidId
     *
     * @param invalidId
     */
    public void deleteByInvalidId(Integer invalidId) {
        mapper.deleteByInvalidId(invalidId);
    }

    /**
     * 删除 by transitId
     *
     * @param transitId
     */
    public void deleteByTransitId(Integer transitId) {
        mapper.deleteByTransitId(transitId);
    }

    /**
     * 是否已存在
     *
     * @param
     */
    public Boolean isInserted(String trackNo) {

        if (mapper.count(trackNo) > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 根据报废单号统计包裹数
     *
     * @param
     */
    public Integer countByInvalidId(Integer InvalidId) {
        return mapper.countByInvalidId(InvalidId);
    }

    /**
     * id查item
     */
    public InvalidItem getById(Integer id) {
        return mapper.getById(id);
    }
}
