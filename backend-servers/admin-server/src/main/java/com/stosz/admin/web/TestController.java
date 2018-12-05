package com.stosz.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/10/20]
 */
@Controller
@RequestMapping("/admin")
public class TestController {

    @RequestMapping("test")
    public String list(Model model, HttpServletRequest request) {
        return "/admin/test/test";
    }
}  
