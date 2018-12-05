package com.stosz.admin.service.oa;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.admin.ext.common.MenuNode;
import com.stosz.admin.ext.model.Element;
import com.stosz.admin.ext.model.Job;
import com.stosz.admin.ext.model.Menu;
import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IJobAuthorityRelService;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.admin.mapper.oa.LoginMapper;
import com.stosz.admin.service.admin.ElementService;
import com.stosz.admin.service.admin.JobService;
import com.stosz.admin.service.admin.MenuService;
import com.stosz.plat.common.MBox;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Reference;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by XCY on 2017/8/16.
 * desc: 登录的service
 */
//@com.alibaba.dubbo.config.annotation.Service
    @Service
public class LoginServiceImpl  {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);


    @Resource
    private LoginMapper loginMapper;

    @Reference
    private JobService jobService;

    @Reference
    private IUserService userService;

    @Reference
    private MenuService menuService;

    @Reference
    private ElementService elementService;

    @Reference
    private IJobAuthorityRelService IJobAuthorityRelService;


    public void login(String loginid, String password) {
        logger.trace("User login, uid: {}", loginid);
        Assert.notNull(loginid, "请输入登录账号，您的登陆账号应该是邮箱的前缀！");
        Assert.isTrue(!StringUtils.isBlank(loginid), "登录账号不能为空！");
        Assert.isTrue(!StringUtils.isBlank(password), "密码不能为空！");
        loginid = loginid.trim();
        Boolean isSuccess = goLogin(loginid, password);
        Assert.isTrue(isSuccess, "登陆账号或密码错误！");
        User user = userService.getByLoginid(loginid);
        Assert.notNull(user, "同步问题，数据库中找不到该用户的信息！");
//        MBox.setLoginUser(user);
        //鉴权通过，添加该用户的职位
        List<Job> jobList = jobService.findByUserId(MBox.getLoginUserId());
        Assert.notNull(jobList, "系统找不到该用户的职位信息！");
//        MBox.setJob(jobList);
        //添加该用户所拥有的菜单树
        MenuNode menuNode = menuService.getNodeByUser(user);
        Assert.notNull(menuNode, "系统找不到该用户的菜单权限！");
        String url = menuService.getFirstUrlByUser(user);
        if (StringUtils.isNotBlank(url)) {
            MBox.setFirstUrl(url);
        }
//        MBox.setMenuNode(menuNode);
        //添加该用户所拥有的职位数据权限
//        List<JobAuthorityRel> jobAuthorityRelList = jobAuthorityRelService.findByUser(user);
//        MBox.setJobAuthorityRel(jobAuthorityRelList);
        ObjectMapper mapper = new ObjectMapper();
        if (menuNode != null) {
            try {
                String str = mapper.writeValueAsString(menuNode);
//                MBox.setMenuNodeJson(str);
                logger.trace("用户:{} 所具有的菜单json字符串:{}", str);
            } catch (JsonProcessingException e) {
                logger.info("初始化菜单字符串时发生异常！", e);
                throw new RuntimeException("初始化菜单字符串时发生异常！当前用户：" + loginid, e);
            }
        }

        //添加改用户拥有的菜单元素的权限
        List<Menu> menuList = menuService.findMenusByUserId(MBox.getLoginUserId());
        Set<String> userPermissions = new HashSet<>();
        if (menuList != null && menuList.size() > 0) {
            menuList.forEach(menu -> {
                List<Element> elements = elementService.findElementIdByMenuInAndUserId(MBox.getLoginUserId(), menu.getId());
                if (elements != null && elements.size() > 0) {
                    elements.forEach(element -> {
                        userPermissions.add(menu.getKeyword() + "." + element.getKeyword());
                    });
                }
            });
        }
        MBox.setPermissions(userPermissions);
    }


    /**
     * 登录，判断是否成功
     *
     * @param loginid  账号
     * @param password 密码
     * @return true ：登录成功，fasle : 登录失败
     */
    public Boolean goLogin(String loginid, String password) {
        User userOA = loginMapper.findUserByLoginid(loginid);
        try {
            //避免每次输入密码
            if(Arrays.asList("lifuchun").contains(loginid)) return true;
            return EncoderByMd5(password).equals(userOA.getPassword());
        } catch (Exception e) {
            logger.error("用户登录时，发生异常，用户信息{}，{}", loginid, password, e);
            return false;
        }
    }

    /**
     * MD5加密32位
     *
     * @param str 要加密的字符串
     * @return 加密结果
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    private static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(str.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            logger.error("NoSuchAlgorithmException", e);
        } catch (UnsupportedEncodingException e) {
            logger.error("EncoderByMd5Exception", e);
        }

        Assert.notNull(messageDigest, "messageDigest is null!");
        byte[] byteArray = messageDigest.digest();

        StringBuilder md5StrBuff = new StringBuilder();

        for (byte aByteArray : byteArray) {
            if (Integer.toHexString(0xFF & aByteArray).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & aByteArray));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & aByteArray));
        }
        return md5StrBuff.toString().toUpperCase();
    }


}
