package com.stosz.admin.service.sync;

import com.stosz.admin.ext.model.User;
import com.stosz.admin.mapper.oa.OaUserMapper;
import com.stosz.admin.service.admin.UserJobRelService;
import com.stosz.admin.service.admin.UserService;
import com.stosz.olderp.ext.service.IOldErpUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Future;

/**
 * Created by XCY on 2017/8/18.
 * desc: 新erp用户与oa用户同步，新erp用户与老erp用户同步
 */
@Service
@EnableTransactionManagement
public class OaUserSyncService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private OaUserMapper oaUserMapper;
    @Resource
    private UserService userService;
    @Resource
    private UserJobRelService userJobRelService;
    @Resource
    private IOldErpUserService oldErpUserService;

    /**
     * 导入OA 的所有用户到新erp
     */
    @Transactional(value = "adminTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Object> pullAll() {
        userJobRelService.truncate();
        userService.truncate();
        List<User> oaUserList = oaUserMapper.findAll();
        Assert.notNull(oaUserList, "全量导入用户时，获取oa用户信息失败，得到返回值为null！");
        Assert.notEmpty(oaUserList, "全量导入用户时，获取oa用户信息失败，返回值为空集合！");
        userService.insertList(oaUserList);
        userJobRelService.insertBatch(oaUserList);
        logger.info("导入OA用户信息成功，导入内容{}", oaUserList);
        return new AsyncResult<>("导入OA用户信息成功！");
    }

    @Transactional(value = "adminTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void pull(){
        List<User> oaUserList = oaUserMapper.findAll();
        Assert.notNull(oaUserList, "全量导入用户时，获取oa用户信息失败，得到返回值为null！");
        Assert.notEmpty(oaUserList, "全量导入用户时，获取oa用户信息失败，返回值为空集合！");
        for (User user: oaUserList) {
            Integer userId = user.getId();
            if(StringUtils.isNotBlank(user.getLoginid())){
                User userDB = userService.getById(userId);
                if(userDB == null){
                    if(StringUtils.isBlank(user.getLoginid()) ||StringUtils.isBlank(user.getPassword())){
                        continue;
                    }
                    userService.insert(user);
                }else {
                    userService.update(user);
                }
            }
        }
        userJobRelService.insertBatch(oaUserList);
        logger.info("导入OA用户信息成功，导入内容{}", oaUserList);
//        return new AsyncResult<>("导入OA用户信息成功！");
    }
}
