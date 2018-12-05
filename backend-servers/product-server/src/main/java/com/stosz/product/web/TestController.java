package com.stosz.product.web;

import com.stosz.admin.ext.service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @auther carter
 * create time    2017-11-29
 */
@Controller
public class TestController {

    public static final Logger logger = LoggerFactory.getLogger(TestController.class);
    @Autowired
    private IUserService iUserService;

    @RequestMapping("/test/hessian")
    public void testUserService(HttpServletResponse response)
    {

        try {
            response.getWriter().write(iUserService.findAll().toString());
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
    }



}
