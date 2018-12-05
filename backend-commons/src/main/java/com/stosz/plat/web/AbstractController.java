package com.stosz.plat.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * yanghuaqing
 * 17/4/16
 */

public abstract class AbstractController extends BaseGlobalExceptionHandler{


    //比如404的异常就会被这个方法捕获
    @ExceptionHandler(NoHandlerFoundException.class)    
    @ResponseStatus(HttpStatus.NOT_FOUND)    
     public ModelAndView handle404Error(HttpServletRequest req, HttpServletResponse rsp, Exception e) throws Exception {    
           return handleError(req, rsp, e, "error-front", HttpStatus.NOT_FOUND);    
     }    

    //500的异常会被这个方法捕获
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR) 
    public ModelAndView handleError(HttpServletRequest req, HttpServletResponse rsp, Exception e) throws Exception { 
           return handleError(req, rsp, e, "error-front", HttpStatus.INTERNAL_SERVER_ERROR);  
    }

}
