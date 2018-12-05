package com.stosz.plat.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.stosz.plat.common.RestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BaseGlobalExceptionHandler {


    protected final Logger logger = LoggerFactory.getLogger(getClass());
    protected static final String DEFAULT_ERROR_MESSAGE = "未知错误，请联系管理员！";
    protected static final String NULL_ERROR_MESSAGE = "空指针异常，请联系管理员！";

    protected boolean isAjax(HttpServletRequest httpRequest) {
        return httpRequest.getHeader("x-requested-with") != null
                && "XMLHttpRequest".equalsIgnoreCase(httpRequest.getHeader("x-requested-with"));
    }

    protected ModelAndView handleError(HttpServletRequest req, HttpServletResponse rsp, Exception e, String viewName, HttpStatus status) throws Exception {
        Object a = AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class);
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
            throw e;
        String errorMsg = e instanceof RuntimeException ? e.getMessage() : DEFAULT_ERROR_MESSAGE;
        //空指针异常时，errorMsg为空，RestResult.desc为空，layui,msg(RestResult.desc)报错。
        if(e instanceof NullPointerException){
            errorMsg = NULL_ERROR_MESSAGE;
        }
        String errorStack = Throwables.getStackTraceAsString(e);
        Object uri = req.getRequestURI();
        Map<String, String[]> params = req.getParameterMap();
        if (params == null) {
            params = new HashMap<>();
        }
        String paramStr = objectMapper.writeValueAsString(params);
        logger.error("访问服务出现异常，url:{} param:{} message:{} - {}", uri, paramStr, errorMsg, errorStack);
        if (isAjax(req)) {
            return handleAjaxError(rsp, errorMsg, status);
        }
        return handleViewError(req.getRequestURL().toString(), errorStack, errorMsg, viewName);
    }

    protected ModelAndView handleViewError(String url, String errorStack, String errorMessage, String viewName) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", errorStack);
        mav.addObject("url", url);
        mav.addObject("message", errorMessage);
        mav.addObject("timestamp", new Date());
        mav.setViewName(viewName);
        return mav;
    }

    ObjectMapper objectMapper = new ObjectMapper();

    protected ModelAndView handleAjaxError(HttpServletResponse rsp, String errorMessage, HttpStatus status) throws IOException {
        rsp.setCharacterEncoding("UTF-8");
        // update by carter  ajax 如果有错误信息，状态码应该是200  20170919
//        rsp.setStatus(status.value());
        rsp.setStatus(200);
        rsp.setContentType("application/json;charset=UTF-8");

        RestResult rs = new RestResult();
        rs.setCode(RestResult.FAIL);
        rs.setDesc(errorMessage);
        String msg = objectMapper.writeValueAsString(rs);
        logger.trace("拦截到异常，响应消息：{}", msg);
        PrintWriter writer = rsp.getWriter();
        writer.write(msg);
        writer.flush();
        return null;
    }

}
