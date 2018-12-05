package com.stosz.order.web;


import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.order.ext.enums.UseableEnum;
import com.stosz.order.ext.model.UserZone;
import com.stosz.order.service.UserZoneService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.ext.service.IZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wangqian
 * 客服与地区
 */
@RestController
@RequestMapping("/orders/user_zone/")
public class UserZoneController extends AbstractController {

    @Autowired
    private UserZoneService userZoneService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IZoneService zoneService;



    @RequestMapping(value = "find")
    public RestResult find(UserZone userZone) {
        userZone.setUseStatus(userZone.getUseStatus().equals(UseableEnum.other)?null:userZone.getUseStatus());
        return userZoneService.findUserZone(userZone);
    }


    /**
     * 客服区域添加方法
     *
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public RestResult add(@RequestParam("userId") Integer userId, @RequestParam("zoneIds") String zoneIds) {
        RestResult result = new RestResult();
        Assert.notNull(userId, "添加失败,参数错误");
        Assert.notNull(zoneIds,"添加失败,参数错误");
        List<Integer> ids = Arrays.stream(zoneIds.split(",")).map(e -> Integer.valueOf(e)).collect(Collectors.toList());
        List<UserZone> userZoneList = new ArrayList();
        User user = userService.getById(userId);
        UserDto userDto = ThreadLocalUtils.getUser();
        for(Integer zoneId : ids){
            UserZone userZone = new UserZone();
            userZone.setUseStatus(UseableEnum.use);
            Zone zone = zoneService.getById(zoneId);
            userZone.setUserName(user.getLastname());
            userZone.setUserId(userId);
            userZone.setZoneId(zoneId);
            userZone.setZoneName(zone.getTitle());
            userZone.setCreatorId(userDto.getId());
            userZone.setCreator(userDto.getLastName());
            userZoneList.add(userZone);
        }
        userZoneService.add(userZoneList);;
        result.setCode(RestResult.NOTICE);
        result.setDesc("添加成功");
        return result;
    }

    @PostMapping(value = "disable")
    public RestResult disable(@RequestParam("id") Integer id) {
        RestResult result = new RestResult();
        UserZone userZone = userZoneService.findById(id);
        Assert.notNull(userZone, "数据异常！");
        userZone.setUseStatus(UseableEnum.unuse);
        userZoneService.update(userZone);
        result.setDesc("关闭成功");
        result.setCode(RestResult.NOTICE);
        return result;
    }

    @PostMapping(value = "enable")
    public RestResult enable(@RequestParam("id") Integer id) {
        RestResult result = new RestResult();
        UserZone userZone = userZoneService.findById(id);
        Assert.notNull(userZone, "数据异常！");
        userZone.setUseStatus(UseableEnum.use);
        userZoneService.update(userZone);
        result.setDesc("开启成功");
        result.setCode(RestResult.NOTICE);
        return result;
    }

    @PostMapping(value = "delete")
    public RestResult delete(@RequestParam("id") Integer id) {
        RestResult result = new RestResult();
        UserZone userZone = userZoneService.findById(id);
        Assert.notNull(userZone, "数据异常！");
        userZoneService.delete(id);
        result.setDesc("删除成功");
        result.setCode(RestResult.NOTICE);
        return result;
    }
}
