package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.OldErpQuantityMapper;
import com.stosz.olderp.ext.service.IOldErpQuantityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/6]
 */
@Service
public class OldErpQuantityService implements IOldErpQuantityService {

    @Resource
    private OldErpQuantityMapper oldErpQuantityMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Override
//    public List<Product> findByLimit(Integer limit, Integer start) {
//        Assert.notNull(limit, "限制条数不能为空！");
//        Assert.notNull(start, "起始位置不能为空！");
//        return oldErpQuantityMapper.findByLimit(limit, start);
//    }
//
//    //TODO 需要老erp产品库存表添加更新时间字段
//    @Override
//    public List<Product> findByDate(Date startTime, Date endTime) {
//        return null;
//    }

    @Override
    public int count() {
        return oldErpQuantityMapper.count();
    }
}
