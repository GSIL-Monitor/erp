package com.stosz.purchase.web;

import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.constant.MenuKeyword;
import com.stosz.purchase.ext.enums.UserBuDeptState;
import com.stosz.purchase.ext.enums.UserProductRelState;
import com.stosz.purchase.ext.model.UserBuDept;
import com.stosz.purchase.ext.model.UserProductRel;
import com.stosz.purchase.service.UserProductRelService;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/12/14]
 */
@Controller
@RequestMapping("/purchase/base/userProductRel")
public class UserProductRelController extends AbstractController{

    @Resource
    private IUserService iUserService;
    @Resource
    private IDepartmentService iDepartmentService;
    @Resource
    private UserProductRelService userProductRelService;


    /**
     * 采购与产品设置列表
     */
    @RequestMapping(method = RequestMethod.GET, value = "userProductRel/list")
    public ModelAndView list() {
        ModelAndView mav = new ModelAndView("purchase/base/userProductRel/list");
        mav.addObject("keyword", MenuKeyword.PURCHASE_USER_DEPART_LIST);
        return mav;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/find")
    @ResponseBody
    public RestResult queryList(UserProductRel userProductRel) {
        RestResult restResult = new RestResult();
        int count = userProductRelService.count(userProductRel);
        restResult.setTotal(count);
        if (count == 0) {
            restResult.setCode(RestResult.OK);
            return restResult;
        }

        List<UserProductRel> userProductRelList = userProductRelService.find(userProductRel);

        restResult.setItem(userProductRelList);
        return restResult;
    }

    /**
     * 新增采购专员与SPU关系
     */
    @RequestMapping("/insert")
    @ResponseBody
    public RestResult addUserProductRel(UserProductRel userProductRel) {
        RestResult restResult = new RestResult();
        Assert.hasText(userProductRel.getSpu(), "SPU不能为空");
        Assert.notNull(userProductRel.getUserId(), "用户不能为空");

        User spuUser = iUserService.getById(userProductRel.getUserId());
        Assert.notNull(spuUser, "用户id[" + userProductRel.getUserId() + "]不存在");

        //User user= MBox.getLoginUser();
        userProductRel.setCreatorId(MBox.getLoginUserId());
        userProductRel.setCreator(MBox.getLoginUserName());
        userProductRel.setUserId(MBox.getLoginUserId());
        userProductRel.setUserName(MBox.getLoginUserName());
        userProductRel.setEnable(UserProductRelState.CREATE.ordinal());
        UserProductRel userProductRelDB = userProductRelService.getBySpuAndId(userProductRel.getSpu(),userProductRel.getUserId());
        Assert.isNull(userProductRelDB,"不允许插入重复的数据！！！");
        userProductRelService.insert(userProductRel);
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("操作成功");
        return restResult;
    }

    /**
     * 删除采购专员与SPU关系
     */
    @RequestMapping(method = RequestMethod.POST, value = "/delete")
    @ResponseBody
    public RestResult delUserProductRel(Integer id) {
        RestResult restResult = new RestResult();
        userProductRelService.delete(id);
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("操作成功");
        return restResult;
    }

    /**
     * 启用/禁用 采购专员与SPU关系
     */
    @RequestMapping(method = RequestMethod.POST, value = "/update")
    @ResponseBody
    public RestResult updateUserProductRel(Integer id, Integer enable) {
        RestResult restResult = new RestResult();
        Assert.notNull(id, "ID不能为空");
        Assert.notNull(enable, "状态不能为空");
        UserProductRel userProductRel = userProductRelService.getById(id);
        Assert.notNull(userProductRel, "记录id[" + id + "]不存在");
        Assert.notNull(UserProductRelState.fromOrdinal(enable), "不允许修改为" + enable);
        userProductRelService.updateState(id, enable);
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("操作成功");
        return restResult;
    }

}
