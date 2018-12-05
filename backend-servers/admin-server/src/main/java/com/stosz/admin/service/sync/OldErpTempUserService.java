package com.stosz.admin.service.sync;

import com.stosz.admin.mapper.admin.OldErpUserTempMapper;
import com.stosz.olderp.ext.model.OldErpUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * 老erp用户临时表的处理service
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/8/29]
 */
@Service
public class OldErpTempUserService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private OldErpUserTempMapper oldErpUserTempMapper;


    public void insert(OldErpUser oldErpUser) {
        Integer i = oldErpUserTempMapper.insert(oldErpUser);
        Assert.isTrue(i == 1, "将老erp用户保存到临时表失败！");
        logger.info("将老erp用户{}保存到临时表成功！", oldErpUser);
    }

    public void truncate() {
        oldErpUserTempMapper.truncate();
        logger.info("截断用户临时表成功！");
    }
}
