package com.stosz.admin.web;

import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台用户的Controller
 *
 * @author shiqiangguo
 * @version 1.0
 * @ClassName UserController
 */
@Controller
@RequestMapping(value = "/admin/user")
public class UserController extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private IUserService userService;

    /**
     * 后台用户列表
     */
    @RequestMapping(method = RequestMethod.GET, value = "/list")
    @ResponseBody
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("mapper/user_list");
        logger.info("后台用户列表");
        return mav;
    }
    
    
    /**
     * 用户自动补全(登录名,loginid,lastname)
     */
    @RequestMapping("/userAutoComplement")
    @ResponseBody
    public RestResult listUsers(@RequestParam(required=false) String search){
    	RestResult result = new RestResult();
        List<User> userList = userService.findByCondition(search);
        result.setItem(userList);
    	result.setCode(RestResult.OK);
    	return result;
    }

}
