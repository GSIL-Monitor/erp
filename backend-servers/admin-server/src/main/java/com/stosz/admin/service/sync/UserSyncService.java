package com.stosz.admin.service.sync;

import com.stosz.admin.ext.service.IUserSyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/12/5]
 */
@Service
public class UserSyncService implements IUserSyncService {

    @Resource
    private OaUserSyncService oaUserSyncService;
    @Resource
    private OldErpUserSyncService oldErpUserSyncService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public Boolean sync() {
        //同步oa的用户信息
        oaUserSyncService.pull();

        logger.info("OA用户信息同步成功！");

        //同步老erp的用户信息
        oldErpUserSyncService.pullAll();

        logger.info("老erp用户信息绑定成功！");

        return true;
    }

    @Override
    public void userSync() {
        logger.info("======================OA用户同步调度开始了！====================");
//        Future<Object> future = oaUserSyncService.pull();
        try {
            oaUserSyncService.pull();
//            String result = (String) future.get();
            logger.info("同步部门信息成功");
            Future<Object> future1 = oldErpUserSyncService.pullAll();
            String result1 = (String) future1.get();
            logger.info(result1);
        } catch (InterruptedException | ExecutionException | RuntimeException e) {
            logger.error("同步部门信息出现异常", e);
        }
        logger.info("======================OA用户同步调度完成了！====================");
    }

}
