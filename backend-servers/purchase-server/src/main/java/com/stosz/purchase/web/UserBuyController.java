package com.stosz.purchase.web;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.utils.StringUtil;
import com.stosz.plat.web.AbstractController;
import com.stosz.purchase.ext.enums.UserBuDeptState;
import com.stosz.purchase.ext.model.UserPlatformAccountRel;
import com.stosz.purchase.ext.model.PlatformAccount;
import com.stosz.purchase.service.PlatformAccountService;
import com.stosz.purchase.service.UserPlatformAccountRelService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * qiuqi
 * 采购与买手账号设置
 */
@Controller
@RequestMapping("/purchase/base/userBuy")
public class UserBuyController extends AbstractController {
    @Autowired
 private UserPlatformAccountRelService userPlatformAccountRelService;
    @Autowired
    private PlatformAccountService platformAccountService;


    /**
     * 查询平台和买手账号
     * @param platformAccount
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "queryPlatformAccountList")
    public RestResult queryPlatformAccountList(@Param("platformAccount") PlatformAccount platformAccount) {
        List<PlatformAccount> PlatformAccounts = platformAccountService.find(platformAccount);
        int count = platformAccountService.count(platformAccount);
        RestResult rr = new RestResult();
        rr.setItem(PlatformAccounts);
        rr.setTotal(count);
        return rr;
    }

    /**
     * 增加账号
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "addAccount")
    public RestResult addAccount(@Param("platId") Integer platId,@Param("account") String account) {
        platformAccountService.findPlatIdAccount(platId,account);
        Assert.notNull(platId,"平台账号不能为空");
        Assert.notNull(account,"账号不能为空");
        Assert.isTrue(StringUtil.isAnyEmpty(account),"账号不能为空");
        UserDto user= ThreadLocalUtils.getUser();
        PlatformAccount platformAccount=new PlatformAccount();
        platformAccount.setAccount(account);
        platformAccount.setPlatId(platId);
        platformAccount.setCreatorId(user.getId());
        platformAccount.setCreator(user.getLastName());
        platformAccountService.insert(platformAccount);
        RestResult r = new RestResult();
        r.setCode(RestResult.NOTICE);
        r.setDesc("操作成功");
        return r;
    }

    /**
     * 操作账号状态根据id
     *
     * @param
     * @returnS
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.POST,value="operateAccount")
    public RestResult operateAccount(Integer id, Integer state){
        RestResult restResult = new RestResult();
        Assert.notNull(id, "ID不能为空");
//        Assert.notNull(enable, "状态不能为空");
        PlatformAccount platformAccount = platformAccountService.getById(id);
        Assert.notNull(platformAccount, "记录id[" + id + "]不存在");
        platformAccountService.updateState(id, state);
        restResult.setCode(RestResult.NOTICE);
        restResult.setDesc("操作成功");
        return restResult;
    }

    /**
     * 删除账号
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.POST,value="delAccount")
    public RestResult delAccount(Integer id){
        platformAccountService.delete(id);
        RestResult r=new RestResult();
        r.setCode(RestResult.NOTICE);
        r.setDesc("删除成功");
        return r;
    }

    /*#######################################################*/
    /**
     * 查询用户和平台列表
     * @param userPlatformAccountRel
     * @return
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.GET,value="queryUserPlatformAccountRel")
    public RestResult ueryUserPlatformAccountRel(UserPlatformAccountRel userPlatformAccountRel){
        List<UserPlatformAccountRel> platforms= userPlatformAccountRelService.find(userPlatformAccountRel);
        RestResult rr=new RestResult();
        int count= userPlatformAccountRelService.count(userPlatformAccountRel);
        rr.setTotal(count);
        rr.setItem(platforms);
        return rr;
    }

    @ResponseBody
    @RequestMapping(method=RequestMethod.GET,value="findByPlatAndBuyer")
    public RestResult findByPlatAndBuyer(Integer platId,Integer buyerId){
        List<UserPlatformAccountRel> platforms= userPlatformAccountRelService.findByPlatAndBuyer(platId,buyerId);
        RestResult rr=new RestResult();
        rr.setItem(platforms);
        return rr;
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "getAccountByBuyer")
    public  RestResult getAccountByBuyer(@Param("userPlatformAccountRel")UserPlatformAccountRel userPlatformAccountRel){
//        Assert.notNull(,"买手账号" + "采购员名不能为空");
        List<String> list=userPlatformAccountRelService.getAccountByBuyer(userPlatformAccountRel);
        Integer integer=userPlatformAccountRelService.getAccountCountByBuyer(userPlatformAccountRel);
        RestResult restResult=new RestResult();
        restResult.setTotal(integer);
        restResult.setItem(list);
        return  restResult;
    }

    /**
     * 增加买手
     * @param userPlatformAccountRel
     * @return
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.POST,value="addBuyer")
    public  RestResult addUserPlatformAccountRel(UserPlatformAccountRel userPlatformAccountRel){
        userPlatformAccountRelService.queryByAccount(userPlatformAccountRel);
        UserDto user= ThreadLocalUtils.getUser();
        userPlatformAccountRel.setCreatorId(user.getId());
        userPlatformAccountRel.setCreator(user.getLastName());
        userPlatformAccountRelService.add(userPlatformAccountRel);
        RestResult r=new RestResult();
        r.setCode(RestResult.NOTICE);
        r.setDesc("添加成功");
        return r;
    }

    /**
     *删除买手根据id
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(method=RequestMethod.POST,value="delBuyer")
    public  RestResult delBuyer(Integer id){
        userPlatformAccountRelService.delete(id);
        RestResult r=new RestResult();
        r.setCode(RestResult.NOTICE);
        r.setDesc("删除成功");
        return r;
    }

    /**
     * 操作买手账号状态
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,value="/operateBuyer")
    public  RestResult operateBuyer(Integer id, Integer state){
        UserPlatformAccountRel userPlatformAccountRel=userPlatformAccountRelService.getById(id);
        Assert.notNull(id, "ID不能为空");
        Assert.notNull(state, "状态不能为空");
        Assert.notNull(userPlatformAccountRel, "记录id[" + id + "]不存在");
        userPlatformAccountRelService.updateState(id,state);
        RestResult r=new RestResult();
        r.setCode(RestResult.NOTICE);
        r.setDesc("操作成功");
        return r;
    }
/*    @ResponseBody
    @RequestMapping(method = RequestMethod.POST,value="updateBuyer")
    public RestResult updateBuyer(UserPlatformAccountRel platformAccount){
        userPlatformAccountRelService.update(platformAccount);
        RestResult r=new RestResult();
        r.setCode(RestResult.NOTICE);
        r.setDesc("更新成功");
        return r;
    }*/
}
