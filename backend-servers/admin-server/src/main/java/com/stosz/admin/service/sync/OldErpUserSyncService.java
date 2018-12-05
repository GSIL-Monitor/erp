package com.stosz.admin.service.sync;

import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.olderp.ext.model.OldErpUser;
import com.stosz.olderp.ext.service.IOldErpUserService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

/**
 *
 *
 * @author xiongchenyang
 * @version [1.0 , 2017/9/8]
 */
@Service
@EnableTransactionManagement
public class OldErpUserSyncService {

    @Resource
    private IUserService userService;
    @Resource
    private OldErpTempUserService oldErpTempUserService;
    @Resource
    private IOldErpUserService oldErpUserService;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 老erp用户全量同步到新erp用户,需要在新erp有用户的前提下使用
     */
    @Async
    @Transactional(value = "adminTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Future<Object> pullAll() {
        //先截断临时表
        oldErpTempUserService.truncate();
        //然后根据用户登录名比对
        List<OldErpUser> list = pullByLoginid();
        //将使用登录名比对不上的用户用中文名进行比对
        userNoRepeatSync(list);
        //比对完后，将OA有的用户但是老erp没有的用户插入到老erp中，并回填oldId
        List<User> oaUsers = userService.findByNoOldId();
        for (User oaUser : oaUsers) {
            insert(oaUser);
        }
        return new AsyncResult<>("老erp用户信息同步完成！");
    }

    //查询到老erp用户，根据用户的中文名，到admin.user表找到对应的记录，
    // 如果有且仅有一条，那么比对成功，将老erp用户的id存入oldid,
    // 如果没有或有多条记录对应，那么说明自动比对失败，要人工进行比对，存入临时表
    private void userNoRepeatSync(List<OldErpUser> oldErpUsers) {
        if (oldErpUsers == null || oldErpUsers.isEmpty()) {
            logger.info("没有要通过中文名比对的老erp用户！");
            return;
        }
        for (OldErpUser oldErpUser : oldErpUsers) {
            String userNicename = oldErpUser.getUserNicename();
            List<User> resultUsers = userService.findBylastname(userNicename);
            //查询到对应用户有且仅有一条，那么匹配完成，将老erp用户id存入oldid
            if (resultUsers != null && resultUsers.size() == 1) {
                User user = resultUsers.get(0);
                if (user.getOldId() == null) {
                    user.setOldId(oldErpUser.getId());
                    userService.updateOldId(user);
                    logger.info("同步老erp用户时，将老erp用户{} 关联 新erp用户{} 成功！", oldErpUser, user);
                }
            } else {
                //没有查到对应关系，直接将老erp用户存入临时表，手工比对
                oldErpTempUserService.insert(oldErpUser);
                logger.info("同步老erp用户时， 老erp用户{} 在新erp中没有找到唯一匹配的用户，找到的信息为{}！", oldErpUser, resultUsers);
            }
        }
    }


    private List<OldErpUser> pullByLoginid() {
        List<OldErpUser> oldErpUsers = oldErpUserService.findAll();
        List<OldErpUser> oldErpUserTempList = new ArrayList<>();
        Assert.notNull(oldErpUsers, "获取到的老erp用户为空！");
        Assert.notEmpty(oldErpUsers, "获取到的老erp用户为空！");
        for (OldErpUser erpUser : oldErpUsers) {
            User user = null;
            try {
                user = userService.getByLoginid(erpUser.getUserLogin());
            }catch (Exception e){
                throw new RuntimeException("根据用户登录ID:loginid查询到多个用户,用户ID为["+erpUser.getUserLogin()+"],异常主信息为["+e.getMessage()+"]");
            }
            if (user != null) {
                user.setOldId(erpUser.getId());
                userService.updateOldId(user);
                logger.info("同步老erp用户时，将老erp用户{} 关联 新erp用户{} 成功！", erpUser, user);
            } else {
                oldErpUserTempList.add(erpUser);
                logger.info("同步老erp用户时， 老erp用户{} 在新erp中没有找到唯一匹配的用户登录名，通过中文名称进行匹配！", erpUser);
            }
        }
        return oldErpUserTempList;
    }


    /**
     * 新增用户，添加用户到新erp，如果老erp没有该用户，则也需要新增到老erp
     */
    private void insert(User user) {
        // 获取老erp对应的用户id回填
        //------------------------------- 插入老erp用户信息，获取oldId 回填 --------------------------------
        Integer oldId = oldErpUserService.insertOldErpUser(user);
        Assert.notNull(oldId, "获取老erp用户id失败！");
        user.setOldId(oldId);
        //---------------------------------------------------------------------------------------------------
        //插入到新erp系统
        try {
            userService.updateOldId(user);
        } catch (Exception e) {
            logger.info("OA新增账号，插入新erp失败，用户信息{}", user, e);
            //如果该erp用户插入失败，那么判断老erp是否有新增用户，如果有新增，那么要删除该条记录
            oldErpUserService.deleteOldErpUser(oldId);
            throw new RuntimeException("OA新增账号，插入新erp失败，用户信息[OA用户ID"+user.getId()+",OA登录ID:"+user.getLoginid()+"]");
        }

    }

}
