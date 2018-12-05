package com.stosz.product.web;

import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.model.Label;
import com.stosz.product.service.LabelService;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 标签管理
 * @author he_guitang
 * @version [1.0 , 2018/1/3]
 */
@Controller
@RequestMapping(value="/product/base/label")
public class LabelController extends AbstractController {

    @Resource
    private LabelService service;

    /**
     * 标签的添加
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public RestResult add(Label param){
        RestResult result = new RestResult();
        Assert.notNull(param.getParentId(),"上级标签不能为空!");
//        Assert.isTrue(param.getKeyword() != null && !"".equals(param.getKeyword()),"标签关键词不能为空!");
//        Assert.isTrue(param.getName() != null && !"".equals(param.getName()),"标签名称不能为空!");
        MBox.assertLenth(param.getName(),"标签名称", 1,30);
        MBox.assertLenth(param.getRemark(),"备注", 100);
        service.add(param);
        result.setCode(RestResult.NOTICE);
        result.setDesc("标签添加成功");
        return result;
    }

    /**
     * 标签的删除
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public RestResult delete(@RequestParam Integer id){
        RestResult result = new RestResult();
        service.delete(id);
        result.setCode(RestResult.NOTICE);
        result.setDesc("标签删除成功");
        return result;
    }

    /**
     * 标签的修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public RestResult update(Label param){
        RestResult result = new RestResult();
        Assert.notNull(param.getId(), "标签修改的ID不能为空!");
        Assert.notNull(param.getParentId(),"上级标签不能为空!");
//        Assert.isTrue(param.getKeyword() != null && !"".equals(param.getKeyword()),"标签关键词不能为空!");
//        Assert.isTrue(param.getName() != null && !"".equals(param.getName()),"标签名称不能为空!");
        MBox.assertLenth(param.getName(),"标签名称", 1,30);
        MBox.assertLenth(param.getRemark(),"备注", 100);
        service.update(param);
        result.setCode(RestResult.NOTICE);
        result.setDesc("标签修改成功");
        return result;
    }


    /**
     * 查询标签所有的数据
     */
    @RequestMapping(value = "/tree")
    @ResponseBody
    public RestResult tree(Boolean allPower, Boolean materialPower, Boolean classify){
        RestResult result = new RestResult();
        Label label = service.tree(allPower, materialPower, classify);
        result.setDesc("标签树构建成功");
        result.setItem(label);
        return result;
    }

    /**
     * 标签绑定路由
     */
    @RequestMapping(value = "/findLabelBind")
    @ResponseBody
    public RestResult findLabelBind(@RequestParam Integer objectId, @RequestParam String keyword, @RequestParam String objectType){
        RestResult result = new RestResult();
        Label label = service.findLabelBind(objectId, keyword, objectType);
        result.setDesc("标签绑定查询成功");
        result.setItem(label);
        return result;
    }


}
