package com.stosz.product.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.enums.LabelTypeEnum;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.model.LabelObject;
import com.stosz.product.service.LabelObjectService;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 产品中心标签数据管理路由
 * @author he_guitang
 * @version [1.0 , 2018/1/4]
 */
@Controller
@RequestMapping(value="/product/base/labelObject")
public class LabelObjectController extends AbstractController {

    @Resource
    private LabelObjectService service;


    /**
     * 标签的增加
     */
    @RequestMapping(value = "/add")
    @ResponseBody
    public RestResult add(LabelObject param){
        RestResult result = new RestResult();
        Assert.notNull(param.getObjectId(), "标签数据中的对象ID不能为空!");
//        Assert.isTrue(param.getLabelId() != null || (param.getLabelKeyword() != null && !"".equals(param.getLabelKeyword())),
//                "标签数据中的标签不能为空!");
        Assert.notNull(param.getLabelId(), "标签数据中的标签ID不能为空!");
        Assert.isTrue(param.getObjectType() != null && !"".equals(param.getObjectType()), "对象类型不能为空!");
        service.add(param);
        result.setCode(RestResult.NOTICE);
        result.setDesc("标签数据添加成功");
        return result;
    }

    @RequestMapping(value = "/addBatch", method = RequestMethod.POST)
    @ResponseBody
    public RestResult addBatch(Integer objectId, List<Integer> labelIds){
        RestResult result = new RestResult();
        Assert.notNull(objectId, "标签数据中的对象ID不能为空!");
        Assert.isTrue(labelIds != null && labelIds.size() != 0, "标签数据中的标签ID不能为空!");
        service.addBatch(objectId, labelIds, "Product");
        result.setCode(RestResult.NOTICE);
        result.setDesc("标签数据添加成功");
        return result;
    }

    /**
     * 标签的删除
     */
    @RequestMapping(value = "/delete")
    @ResponseBody
    public RestResult delete(@RequestParam Integer labelObjectId){
        RestResult result = new RestResult();
        service.delete(labelObjectId);
        result.setCode(RestResult.NOTICE);
        result.setDesc("标签数据删除成功");
        return result;
    }

    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ResponseBody
    public RestResult deleteBatch(List<Integer> ids){
        RestResult result = new RestResult();
        Assert.isTrue(ids != null && ids.size() != 0, "参数有误,需要删除的标签数据ID集合为空!");
        service.deleteBatch(ids);
        result.setCode(RestResult.NOTICE);
        result.setDesc("标签数据添加成功");
        return result;
    }

    /**
     * 标签的修改
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public RestResult update(LabelObject param){
        RestResult result = new RestResult();
        Assert.notNull(param.getId(), "修改标签数据的时候,其ID不能为空!");
        Assert.notNull(param.getObjectId(), "标签数据中的对象ID不能为空!");
        Assert.notNull(param.getLabelId(), "标签数据中的标签ID不能为空!");
        param.setObjectType(LabelTypeEnum.Product.name());
        service.update(param);
        result.setCode(RestResult.NOTICE);
        result.setDesc("标签数据修改成功");
        return result;
    }


    /**
     * 对外系统提供的单条查询接口
     */
    @RequestMapping(value = "/findByObject")
    @ResponseBody
    public RestResult findByObject(@RequestParam Integer objectId, @RequestParam String objectType){
        RestResult result = new RestResult();
        List<LabelObject> list = service.findByObject(objectId, objectType);
        result.setItem(list);
        result.setDesc("标签数据查询成功!");
        return result;
    }



    /**
     * 对外系统提供的批量查询接口,批量值为100
     */
    @RequestMapping(value = "/findList")
    @ResponseBody
    public RestResult findList(@RequestParam String objectIds, String objectType){
        RestResult result = new RestResult();
        String[] arrayIds = objectIds.split(",");
        Assert.isTrue(arrayIds.length <= 500, "批量查询最多查询100条");
        List<Integer> idsList = new ArrayList<>();
        for (int i = 0; i < arrayIds.length; i++){
            Integer id = Integer.valueOf(arrayIds[i].trim());
            idsList.add(id);
        }
        Map<Integer, List<LabelObject>> map =  service.queryListLabelObject(idsList, objectType);
        result.setItem(map);
        result.setDesc("标签数据批量查询成功!");
        return result;
    }

}
