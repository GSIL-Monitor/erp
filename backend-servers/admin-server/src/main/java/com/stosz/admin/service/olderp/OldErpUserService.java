package com.stosz.admin.service.olderp;

import com.stosz.admin.ext.model.User;
import com.stosz.admin.mapper.olderp.OldErpUserMapper;
import com.stosz.olderp.ext.model.OldErpUser;
import com.stosz.olderp.ext.service.IOldErpUserService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by XCY on 2017/8/21.
 * desc: 老erp与oa用户信息的同步
 */
@Service
public class OldErpUserService implements IOldErpUserService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private OldErpUserMapper oldErpUserMapper;

    /**
     * 将新erp用户转为老erp用户存入到数据库中，返回老erp对应的用户id
     *
     * @param user 新erp用户
     * @return 用户id
     */
    public Integer insertOldErpUser(User user) {
        String login = user.getLoginid();
        String lastname = MBox.getLoginUserName();
        String password = MBox.getOldErpDefaultPassword();
        Date birthday = user.getBirthday();
        String sex = user.getSex();
        OldErpUser oldErpUser = new OldErpUser();
        oldErpUser.setUserLogin(login);
        oldErpUser.setUserPass(password);
        oldErpUser.setUserNicename(lastname);
        if (StringUtils.isNotBlank(sex)) {
            oldErpUser.setSex(Short.parseShort(sex));
        }
        oldErpUser.setBirthday(birthday);
        oldErpUser.setUserType(Short.parseShort("1"));
        Integer i = oldErpUserMapper.insert(oldErpUser);
        Assert.isTrue(i == 1, "向老erp新建用户失败！");
        return MBox.getLoginUserId();
    }

    public void deleteOldErpUser(Integer id) {
        try {
            oldErpUserMapper.delete(id);
        } catch (Exception e) {
            logger.error("删除老erp用户失败，用户id:{}", id, e);
        }
    }

    @Override
    public Integer getIdByNiceName(String niceName) {
        return oldErpUserMapper.getIdByNiceName(niceName);
    }

    @Override
    public List<OldErpUser> findNoRepeat() {
        return oldErpUserMapper.findNoRepeat();
    }

    @Override
    public List<OldErpUser> findRepeat() {
        return oldErpUserMapper.findRepeat();
    }

    @Override
    public List<OldErpUser> findAll() {
        return oldErpUserMapper.findAll();
    }

    @Override
    public Integer getIdByLoginId(String loginid) {
        return oldErpUserMapper.getIdByLoginId(loginid);
    }


}
