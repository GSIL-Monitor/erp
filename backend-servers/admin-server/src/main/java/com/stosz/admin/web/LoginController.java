package com.stosz.admin.web;

import com.stosz.admin.ext.common.MenuNode;
import com.stosz.admin.ext.model.Job;
import com.stosz.admin.ext.model.Menu;
import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.admin.service.admin.JobAuthorityRelService;
import com.stosz.admin.service.admin.JobService;
import com.stosz.admin.service.admin.MenuService;
import com.stosz.admin.service.oa.SsoLoginService;
import com.stosz.plat.common.JobDto;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.enums.SystemEnum;
import com.stosz.plat.service.ProjectConfigService;
import com.stosz.plat.service.SsoUserService;
import com.stosz.plat.utils.CommonUtils;
import com.stosz.plat.utils.StringUtils;
import com.stosz.plat.web.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.jws.WebParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * shiqiangguo
 *
 */
@Controller
@RequestMapping("/admin")
public class LoginController extends AbstractController {

    @Resource
    private SsoLoginService ssoLoginService;
    @Resource
    private SsoUserService ssoUserService;

    @Resource
    private IUserService userService;

    @Resource
    private MenuService menuService;

    @Resource
    private JobService jobService;

    @Resource
    private JobAuthorityRelService jobAuthorityRelService;

    @Autowired
    private ProjectConfigService projectConfigService;




    @RequestMapping({ "/home" })
    public String home(){
        return "home";
    }



    @RequestMapping({ "","/" })
    public String index(HttpServletRequest request) {
        return   "/admin/home";
    }

    /**
     * 跳转到登录页面
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(@RequestParam (value = MBox.PARAM_BACK_URL, defaultValue = "/admin/") String backUrl , Model model,HttpServletRequest request,HttpServletResponse response) {



        UserDto userDto = ssoUserService.getCurrentUserDto(request);
        if(userDto==null){
            model.addAttribute(MBox.PARAM_BACK_URL, backUrl);
            return "login";
        }
        String ticket = ssoUserService.setUserToRedis(userDto, response);
        model.addAttribute(MBox.PARAM_TICKET_KEY, ticket);
        return "redirect:" + backUrl;
    }

    /**
     * 登录的post请求
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    private String doLogin(@RequestParam(value = "loginid", defaultValue = "") String loginid,
                           @RequestParam(value = "password", defaultValue = "") String password,
                           @RequestParam (value = MBox.PARAM_BACK_URL, defaultValue = "/admin/") String backUrl,
                           HttpServletRequest request,
                           HttpServletResponse response,
                           Model model) {

        try {
            //调用登录验证的方法，判断是否成功
            boolean bl = ssoLoginService.goLogin(loginid, password);

            Assert.isTrue(bl , "账号不存在或者密码错误！");

            User user = userService.getByLoginid(loginid);
            Assert.notNull(user,"账号 " + loginid + " 没有从oa中同步过来！请联系技术部解决此问题！");
            UserDto userDto = new UserDto();
            userDto.setId(user.getId());
            userDto.setLoginid(user.getLoginid());
            userDto.setDeptId(user.getDepartmentId());
            userDto.setDeptName(user.getDepartmentName());
            userDto.setDeptNo(user.getDepartmentNo());
            userDto.setEmail(user.getEmail());
            userDto.setLastName(user.getLastname());
            userDto.setManagerId(user.getManagerId());
            userDto.setMobile(user.getMobile());
            userDto.setCompanyId(user.getCompanyId());

            //登录成功后另外提供权限、职位接口
            //获取用户对应的职位
            List<Job> jobList = jobService.findByUserId(user.getId());
            List<JobDto> jobDtoList = new ArrayList<>();
            for (Job j : jobList) {
                JobDto jobDto = new JobDto();
                jobDto.setId(j.getId());
                jobDto.setName(j.getName());
                jobDtoList.add(jobDto);
            }
            userDto.setJobs(jobDtoList);

            String ticket = ssoUserService.setUserToRedis(userDto, response);
            model.addAttribute(MBox.PARAM_TICKET_KEY, ticket);
            return "redirect:" + backUrl;
        } catch (IllegalArgumentException e) {
            model.addAttribute("message", e.getMessage());
        }catch(Exception e){
            model.addAttribute("message", e.getMessage());
            logger.error("登录时发生异常！" , e);
        }
        return "login";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request , HttpServletResponse response, Model model
    ,@RequestParam (value = MBox.PARAM_BACK_URL, defaultValue = "/admin/") String backUrl){
        ssoUserService.logout(request,response);
//        SystemEnum systemEnum = MBox.isInside(request)?SystemEnum.front:SystemEnum.frontOutside;
//        String httpPrefix = projectConfigService.getSystemHttp(systemEnum);
        model.addAttribute(MBox.PARAM_BACK_URL, backUrl);
        return "redirect:/admin/login";
//        return "redirect:" + httpPrefix + "/admin/login";
    }

    @RequestMapping("/currentUser")
    @ResponseBody
    public RestResult currentUser(HttpServletRequest request){
        UserDto userDto = ssoUserService.getCurrentUserDto(request);
        RestResult rr = new RestResult();
        rr.setItem(userDto);
        return rr;
    }

    @RequestMapping("/getUserByTicket/{ticket}")
    @ResponseBody
    public RestResult getUserByTicket(@PathVariable("ticket") String ticket){
        Assert.isTrue(StringUtils.isNotBlank(ticket), "缺少ticket");
        UserDto userDto = ssoUserService.getCurrentUserDto(ticket);
        if(userDto==null){
            RestResult rr = new RestResult();
            rr.setCode(RestResult.FAIL);
            rr.setDesc("没有此用户数据");
            return rr;
        }
        RestResult rr = new RestResult();
        rr.setItem(userDto);
        rr.setCode(userDto == null ? RestResult.FAIL : RestResult.OK);
        return rr;
    }

    @RequestMapping("/topMenu")
    @ResponseBody
    public RestResult topMenu(HttpServletRequest request,HttpServletResponse response){
        RestResult rr = new RestResult();
        UserDto userDto = ssoUserService.getCurrentUserDto(request);
        if (userDto == null) {
            rr.setCode(RestResult.LOGIN);
            rr.setDesc("登录超时，请重新登录！");
            return rr;
        }


        //通过用户获取顶级菜单
        //1.从数据菜单取出第一级
        // 2.根据admin系统配置中，指定 http前缀
        List<Menu> menuList = menuService.findTopMenuByUserId(userDto.getId() , request);

        rr.setItem(menuList);
        return rr;
    }


    @RequestMapping("/subMenu")
    @ResponseBody
    public RestResult subMenu(@RequestParam String keyword, HttpServletRequest request) {
        RestResult rr = new RestResult();
        UserDto userDto = ssoUserService.getCurrentUserDto(request);
        if (userDto == null) {
            rr.setCode(RestResult.LOGIN);
            rr.setDesc("登录超时，请重新登录！");
            return rr;
        }
        //通过用户获取子级菜单
        // 1.从数据菜单取出子级
        MenuNode node  = menuService.getUserMenuNodeByParentNode(userDto.getId(), keyword);

        rr.setItem(node);
        return rr;
    }

    /**
     * 提供数据权限的接口
     */
    @RequestMapping(value = "/dataPermission", method = RequestMethod.POST)
    @ResponseBody
    public RestResult dataPermission(HttpServletRequest request) {
        RestResult result = new RestResult();
        UserDto user = ssoUserService.getCurrentUserDto(request);
        if (user == null) {
            result.setCode(RestResult.LOGIN);
            result.setDesc("登录超时，请重新登录！");
            return result;
        }
        //获取自己部门和下级部门，获取数据权限的部门，获取职位数据权限
        List<Integer> departmentIds = jobAuthorityRelService.findDepartmentIdsByUser(user);
        result.setItem(departmentIds);
        return result;
    }

}
