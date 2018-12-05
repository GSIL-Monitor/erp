package com.stosz.admin.service.sync;
import com.stosz.admin.mapper.admin.OldErpDepartmentTempMapper;
import com.stosz.olderp.ext.model.OldErpDepartment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/8/29]
 */
@Service
public class OldErpTempDepartmentService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private OldErpDepartmentTempMapper oldErpDepartmentTempMapper;

    public void insert(OldErpDepartment oldErpDepartment) {
        Integer i = oldErpDepartmentTempMapper.insert(oldErpDepartment);
        Assert.isTrue(i == 1, "将老erp部门信息保存到临时表失败！");
        logger.info("将老erp部门{}保存到临时表成功！", oldErpDepartment);
    }

    public void truncate() {
        oldErpDepartmentTempMapper.truncate();
        logger.info("截断临时表成功！");
    }
}
