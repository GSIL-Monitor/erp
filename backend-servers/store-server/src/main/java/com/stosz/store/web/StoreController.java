package com.stosz.store.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.store.ext.model.Wms;
import com.stosz.store.service.WmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:仓库管理
 * @Created Time:2017/11/25 10:10
 * @Update Time:2017/11/25 10:10
 */
@RestController
@RequestMapping("/store")
public class StoreController extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private WmsService wmsService;

    /**
     * 仓库列表
     */
    @RequestMapping(value = "/findList", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public RestResult findList(Wms wms) {

        logger.debug("仓库列表 req:zoneId={},type={}", wms.getZoneId(), wms.getType());
        RestResult result = new RestResult();

        int count = wmsService.count(wms);
        result.setTotal(count);
        if (count == 0) {
            return result;
        }
        List<Wms> wmsList = wmsService.query(wms);

        result.setItem(wmsList);
        return result;
    }

    /**
     * 新增仓库
     */
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    @ResponseBody
    public RestResult addOrUpdate(Wms wms) {

        Assert.notNull(wms.getZoneId(), "请选择区域");
        Assert.notNull(wms.getType(), "请选择仓库类型");
        Assert.notNull(wms.getName(), "请填写仓库名");
        Assert.notNull(wms.getUseWms(), "请选择是否使用wms");
        if ("1".equals(wms.getUseWms())) {
            Assert.notNull(wms.getWmsSysCode(), "请填写wms编码");
        }
        wmsService.insertOrUpdate(wms);
        RestResult result = new RestResult();
        result.setDesc("操作成功");
        return result;
    }

    /**
     * 根据id获取仓库信息
     */
    @RequestMapping(value = "/findStore", method = RequestMethod.POST)
    @ResponseBody
    public RestResult findStore(Integer wmsId) {
        RestResult result = new RestResult();
        if (wmsId != null) {
            Wms wms = wmsService.findById(wmsId);
            if (wms == null) {
                result.setDesc("不存在该仓库信息");
            } else {
                result.setItem(wms);
            }
        }
        return result;
    }

    /**
     * delete
     */
    @RequestMapping(value = "/deleteStore", method = RequestMethod.GET)
    @ResponseBody
    public RestResult deleteStore(Integer wmsId) {
        Assert.notNull(wmsId, "id不能为空");
        RestResult result = new RestResult();
        Wms wms = new Wms();
        wms.setId(wmsId);
        wms.setDeleted(1);
        wmsService.del(wms);

        return result;
    }

    /**
     * enable
     */
    @RequestMapping(value = "/enable", method = RequestMethod.GET)
    @ResponseBody
    public RestResult enable(Integer wmsId) {
        Assert.notNull(wmsId, "id不能为空");
        RestResult result = new RestResult();

        Wms wms = new Wms();
        wms.setId(wmsId);
        wms.setState(1);
        wmsService.changeState(wms);

        return result;
    }

    /**
     * disable
     */
    @RequestMapping(value = "/disable", method = RequestMethod.GET)
    @ResponseBody
    public RestResult disable(Integer wmsId) {
        Assert.notNull(wmsId, "id不能为空");
        RestResult result = new RestResult();

        Wms wms = new Wms();
        wms.setId(wmsId);
        wms.setState(0);
        wmsService.changeState(wms);

        return result;
    }
}
