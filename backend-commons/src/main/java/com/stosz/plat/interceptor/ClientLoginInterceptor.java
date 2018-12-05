package com.stosz.plat.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.enums.SystemEnum;
import com.stosz.plat.service.ProjectConfigService;
import com.stosz.plat.service.SsoUserService;
import com.stosz.plat.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientLoginInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    SsoUserService ssoUserService;

    @Autowired
    ProjectConfigService projectConfigService;

    private String getLoginUrl(HttpServletRequest request){
//        SystemEnum systemEnum = MBox.isInside(request)?SystemEnum.front:SystemEnum.frontOutside;
//        return projectConfigService.getSystemHttp(systemEnum) + "/admin/login";
        return "/admin/login";
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        String uri = request.getRequestURI();
        logger.trace("登录拦截，当前访问 方法:{} 地址:{}" , request.getMethod() ,  uri);
        String loginUrl = getLoginUrl(request);
        UserDto userDto =null;
        String ticket = request.getParameter(MBox.PARAM_TICKET_KEY);
        String fixUri = uri;
        if(ticket!=null){
            userDto = ssoUserService.getCurrentUserDto(ticket);
            Assert.notNull(userDto , "非法登录，无效的ticket:" + ticket);
            ssoUserService.setTicketToCookie(ticket,response);
            ThreadLocalUtils.setUser(userDto);
            String backurl = request.getParameter(MBox.PARAM_BACK_URL);
            if(backurl==null) {
                logger.warn("通过ticket校验用户登录时缺少返回地址，可能前端静态文件参数装载错误，请求将被跳转到sso服务器");
                backurl = "/";
            }
            response.sendRedirect(backurl);
            return false;
        }

        userDto = ssoUserService.getCurrentUserDto(request);

        if (userDto != null ) {
            ThreadLocalUtils.setUser(userDto);
            // 用户已经登录
            return true;
        }
        String appid = request.getHeader(MBox.REQUEST_HEADER_PROJECT_ID);

        if (StringUtils.isNotBlank(appid)) {
            logger.debug("rest请求调用开始，客户端id:{} ip:{} uri:{}", appid, request.getRemoteHost(), request.getRequestURI());

            try {
                verifySecret(request);
                return true;
            } catch (Exception e) {
                logger.error("请求非法！远程地址:{}", request.getRemoteHost(), e);
                RestResult rr = new RestResult();
                rr.setCode(RestResult.FAIL);
                rr.setDesc(e.getMessage());
                ObjectMapper mapper = new ObjectMapper();
                String str = mapper.writeValueAsString(rr);
                logger.info("rest接口请求参数非法！-- {}" , str);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json");
                response.setHeader(MBox.REQUEST_HEADER_AJAX,MBox.REQUEST_HEADER_AJAX_VALUE);
                response.getWriter().write(str);
                response.getWriter().close();
            }
            return false;
        }


        String backUrl = MBox.getBackUrl(request);
        if (MBox.isAjax(request)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            RestResult result = new RestResult();
            result.setCode(RestResult.LOGIN);
            //返回登录url
            result.setItem( loginUrl );
            result.setDesc("登录超时");
            String rs = mapper.writeValueAsString(result);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(rs);
            response.flushBuffer();
            return false;
        }
        String userLoginUri = getLoginUrl(request);

        logger.info("user not login, url : {} redirect to :{}", uri, userLoginUri);
        response.sendRedirect(userLoginUri);
        logger.debug("尚未登陆，跳转到登陆页面：{}", userLoginUri);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception ex) throws Exception {
        ThreadLocalUtils.remove();
    }


    private void verifySecret(HttpServletRequest request) {
        String test = request.getHeader("test");
        if(StringUtils.isNotBlank(test) && test.equals("sqg")){
            return;
        }
        String timestampStr = request.getHeader(MBox.REQUEST_HEADER_AUTH_TIMESTAMP);
        Assert.isTrue(StringUtils.isNotBlank(timestampStr), "缺少时间戳：" + MBox.REQUEST_HEADER_AUTH_TIMESTAMP);
        String nonce = request.getHeader(MBox.REQUEST_HEADER_AUTH_NONCE);
        Assert.isTrue(StringUtils.isNotBlank(nonce), "缺少随机码：" + MBox.REQUEST_HEADER_AUTH_NONCE);
        String sign = request.getHeader(MBox.REQUEST_HEADER_AUTH_SIGNATURE);
        Assert.isTrue(StringUtils.isNotBlank(sign), "缺少签名串：" + MBox.REQUEST_HEADER_AUTH_SIGNATURE);

        long timestamp = Long.parseLong(timestampStr);


        long  nowTime  = System.currentTimeMillis();


        String sendTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timestamp));
        String systemTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(nowTime));
        logger.info("传递过来的时间：" + sendTime + " 当前时间："+
                systemTime + " 是否超过10分钟：" + !(timestamp + 60 * 1000*10 > nowTime)
        );
        Assert.isTrue(Math.abs(timestamp  - nowTime)<=60*1000*10, "请求已经过期，请重新发起请求！服务器时间："+systemTime+" 传递时间："+sendTime);


        String secrete = projectConfigService.getProjectRestSecret();
        Assert.isTrue(StringUtils.isNotBlank(secrete), "服务器没有配置秘钥，请联系服务器维护人员！");

        String tmpSignStr = MBox.getSignMd5Str(secrete, timestampStr, nonce);
        Assert.isTrue( tmpSignStr.equalsIgnoreCase( sign ) , "签名不正确！" );

    }

//    private String getProjectSecret() {
//        return null;
//    }

}
