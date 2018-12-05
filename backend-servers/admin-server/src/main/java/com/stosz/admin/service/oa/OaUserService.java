package com.stosz.admin.service.oa;

import com.stosz.admin.ext.model.User;
import com.stosz.admin.mapper.oa.LoginMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class OaUserService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final String TICKET = "ticket";

    @Resource
    private LoginMapper loginMapper;
    /**
     * 登录操作
     * @param loginid
     * @param password
     * @return
     * @throws
     */
    public boolean doLogin(String loginid, String password) throws IOException {
        logger.trace("User login, uid: {}", loginid);
        Assert.notNull(loginid, "请输入登录账号，您的登陆账号应该是邮箱的前缀！");
        Assert.isTrue(!StringUtils.isBlank(loginid), "登录账号不能为空！");
        Assert.isTrue(!StringUtils.isBlank(password), "密码不能为空！");
        loginid = loginid.trim();
        /*
        校验用户名和密码(对比OA系统)
         */
        Boolean isSuccess = goLogin(loginid, password);

        return isSuccess;
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
//            if(Arrays.asList("lifuchun","feiheping").contains(loginid)) return true;
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
    private String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
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
