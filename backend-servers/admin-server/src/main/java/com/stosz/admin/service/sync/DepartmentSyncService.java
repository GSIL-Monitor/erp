package com.stosz.admin.service.sync;

import com.stosz.admin.ext.service.IDepartmentSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/12/4]
 */
@Service
public class DepartmentSyncService implements IDepartmentSyncService{

    @Resource
    private OaDepartmentSyncService oaDepartmentSyncService;
    @Resource
    private OldErpDepartmentSyncService oldErpDepartmentSyncService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Boolean sync() {
        try {
            //OA部门信息同步
            oaDepartmentSyncService.pull();

            logger.info("同步OA部门信息成功！");

            //OldErp部门信息绑定
            oldErpDepartmentSyncService.sync();

            logger.info("绑定老erp部门信息成功！");
            return true;
        } catch (Exception e) {
            logger.info("部门信息同步失败,{}！",e);
            throw  new RuntimeException("部门信息同步失败!",e);
        }
    }
}
