package com.stosz.product.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.constant.MenuKeyword;
import com.stosz.product.ext.model.Partner;
import com.stosz.product.service.PartnerService;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * 合作伙伴Controller
 * @author minxiaolong
 * @create 2017-12-29 10:18
 **/
@Controller
@RequestMapping(value = "/product/base/partner")
public class PartnerController extends AbstractController {

    @Resource
    private PartnerService partnerService;

    @RequestMapping(value = "")
    @ResponseBody
    public ModelAndView asList() {
        ModelAndView model = new ModelAndView("/pc/base/partner");
        model.addObject("keyword", MenuKeyword.PARTNER_MANAGEMENT);
        return model;
    }

    /**
     * 新增合作伙伴
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public RestResult add(Partner partner){
        Assert.notNull(partner,"保存失败,传入参数异常!");
        Assert.notNull(partner.getTypeEnum(),"类型不能为空");
        Assert.notNull(partner.getSettlementMethod(),"结算方式不能为空");
        return partnerService.add(partner);
    }

    /**
     * 删除合伙人
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public  RestResult delete(int id){
        return partnerService.delete(id);
    }

    /**
     * 修改合作伙伴信息
     * @param partner
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public RestResult update(Partner partner){
        Assert.notNull(partner,"修改失败,传入参数异常");
        Assert.notNull(partner.getId(),"请先选择一项作为修改项");
        return partnerService.update(partner);
    }

    /**
     * 查询合作伙伴列表
     * @param partner
     * @return
     */
    @RequestMapping(value = "/find", method = RequestMethod.POST)
    @ResponseBody
    public RestResult find(Partner partner){
        return partnerService.find(partner);
    }

    /**
     * 查询合作伙伴详细
     * @param id
     * @return
     */
    @RequestMapping(value = "/getById")
    @ResponseBody
    public RestResult getById(int id){
        return partnerService.getById(id);
    }
}
