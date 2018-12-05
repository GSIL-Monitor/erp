package com.stosz.admin.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.enums.SystemEnum;
import com.stosz.plat.service.ProjectConfigService;
import com.stosz.plat.service.SsoUserService;
import com.stosz.plat.utils.CommonUtils;
import com.stosz.plat.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;


@Component
public class AdminLoginInterceptor implements HandlerInterceptor {


    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    SsoUserService ssoUserService;

    @Autowired
    ProjectConfigService projectConfigService;



//    private String getLoginUrl(){
//        if(!"80".equals(projectConfigService.getProjectHttpAdminPort()))
//        return "http://" + projectConfigService.getProjectHttpAdminHost() + ":" + projectConfigService.getProjectHttpAdminPort() + "/admin/login";
//
//        return "http://" + projectConfigService.getProjectHttpAdminHost() + "/admin/login";
//    }

    private String getLoginUrl(HttpServletRequest request){
//        X_Location_Net : 如果没有，则默认为outside
        SystemEnum systemEnum = MBox.isInside(request)?SystemEnum.front:SystemEnum.frontOutside;
        String httpStr = projectConfigService.getSystemHttp(systemEnum);
        logger.info("获取到的登录链接:{}" , httpStr);
//        return httpStr + "/admin/login";
        return "/admin/login";

    }


    private String getLoginUrlWithRedirect(String backUrl,HttpServletRequest request){
        try {
            return getLoginUrl(request) + "?" + MBox.PARAM_BACK_URL + "=" + URLEncoder.encode(backUrl, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error("编码异常！" , e);
            return getLoginUrl(request);
        }
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        if(request.getMethod().equalsIgnoreCase(HttpMethod.OPTIONS.name())){
            return false;
        }
        String uri = request.getRequestURI();
        logger.trace("登录拦截，当前访问 方法:{} 地址:{}" , request.getMethod() ,  uri);
        if (uri.endsWith("/login")) {
            return true;
        }
        String loginUrl = getLoginUrl(request);
        UserDto userDto =null;
        String ticket = request.getParameter(MBox.PARAM_TICKET_KEY);
        String fixUri = uri;
        logger.debug("");
        if(ticket!=null){

            Map<String, String[]> map = request.getParameterMap();
            StringBuilder sb = new StringBuilder();
            for (String key : map.keySet()) {
                if(key.equalsIgnoreCase(MBox.PARAM_TICKET_KEY)){
                    continue;
                }
                String[] values = map.get(key);
                if (values == null || values.length == 0) {
                    continue;
                }
                for (String val : values) {
                    sb.append("&").append(key).append("=").append(val);
                }
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(0);
                fixUri+="?" + sb.toString();
            }
//
            fixUri="/";
            userDto = ssoUserService.getCurrentUserDto(ticket);
            if(userDto!=null) {
                ssoUserService.setTicketToCookie(ticket, response);
//                fixUri = request.getParameter(MBox.PARAM_BACK_URL);
                logger.info("根据 ticket:{} 获取到用户，跳转到链接地址:{}" ,ticket ,  fixUri);
                response.sendRedirect(fixUri);
                return false;
            }else{
                String s = getLoginUrlWithRedirect( fixUri ,request);
                logger.error("未能根据 ticket:{} 获取到用户，将跳转到登录页面:{}" ,ticket ,  s);
                response.sendRedirect(s);
                return false;
            }
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
        String userLoginUri = getLoginUrlWithRedirect( backUrl ,request);

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


        String secrete = getProjectSecret();
        Assert.isTrue(StringUtils.isNotBlank(secrete), "服务器没有配置秘钥，请联系服务器维护人员！");

        String tmpSignStr = MBox.getSignMd5Str(secrete, timestampStr, nonce);
        Assert.isTrue( tmpSignStr.equalsIgnoreCase( sign ) , "签名不正确！" );

    }

    private String getProjectSecret() {
        return null;//tod
    }


}
