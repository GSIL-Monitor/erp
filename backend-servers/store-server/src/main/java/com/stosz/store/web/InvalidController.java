package com.stosz.store.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.store.ext.dto.request.SearchInvalidReq;
import com.stosz.store.ext.enums.ErrMsgEnum;
import com.stosz.store.ext.model.Invalid;
import com.stosz.store.ext.model.InvalidItem;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.service.InvalidItemService;
import com.stosz.store.service.InvalidService;
import com.stosz.store.service.TransitStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:ChenShifeng
 * @Description:报废管理
 * @Created Time:2017/12/25 10:10
 */
@RestController
@RequestMapping("store/invalid")
public class InvalidController extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InvalidService invalidService;
    @Autowired
    private InvalidItemService invalidItemService;
    @Autowired
    private TransitStockService transitStockService;

    /**
     * 新建报废单
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    @ResponseBody
    public RestResult create(Invalid invalid) {
        Assert.notNull(invalid, "invalid不能为空");
        logger.info(" invalid[{}]", invalid);
        RestResult result = new RestResult();
        invalidService.create(invalid);
        result.setItem(invalid);
        return result;
    }

    /**
     * 新建报废单 运单明细
     */
    @RequestMapping(value = "addItem", method = RequestMethod.POST)
    @ResponseBody
    public RestResult addItem(Invalid invalid) {
        Assert.notNull(invalid.getTracks(), "运单不能为空");
        logger.info(" invalid[{}]", invalid);
        RestResult result = new RestResult();
        String[] tracks = invalid.getTracks().split("\\|");

        int tracksSize = tracks.length;
        Assert.notEmpty(tracks, "运单信息格式有误");
        Map<String, String> resMsg = new HashMap<>(tracksSize);

        for (int i = 0; i < tracksSize; ++i) {
            if (invalidItemService.isInserted(tracks[i])) {
                resMsg.put(tracks[i], ErrMsgEnum.invalided.display());
                continue;
            }
            TransitStock transitStock = transitStockService.findByTrackNoOld(tracks[i]);
            if (transitStock == null) {
                resMsg.put(tracks[i], ErrMsgEnum.errTrackingNo.display());
                continue;
            } else {
                InvalidItem invalidItem = new InvalidItem();
                invalidItem.setInvalidId(invalid.getId());
                invalidItem.setTrackingNoOld(transitStock.getTrackingNoOld());
                invalidItem.setTransitId(transitStock.getId());
                invalidItemService.insert(invalidItem);
            }
        }
        int success = tracksSize - resMsg.size();
        if (success > 0) {
            //更新数量
            invalidService.updateQty(invalid.getId(),invalidItemService.countByInvalidId(invalid.getId()));
        }
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("total", tracksSize);
        resMap.put("success", success);
        resMap.put("error", resMsg.size());
        resMap.put("errDetail", resMsg);

        result.setItem(resMap);
        return result;
    }

    /**
     * 提交
     */
    @RequestMapping(value = "submit", method = RequestMethod.POST)
    @ResponseBody
    public RestResult submit(Invalid invalid) {
        Assert.notNull(invalid, "invalid不能为空");
        Assert.isTrue(invalid.getQty() > 0, "没有数量不能提交");
        logger.info(" invalid[{}]", invalid);
        RestResult result = new RestResult();
        invalidService.submit(invalid);
        return result;
    }

    /**
     * 搜 索
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public RestResult search(SearchInvalidReq param) {

        if (logger.isDebugEnabled()) {
            logger.debug("search param:{}", param);
        }

        return invalidService.findList(param);
    }

    /**
     * 查询明细
     */
    @RequestMapping(value = "getDetail", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getDetail(Integer id) {
        Assert.notNull(id, "id不能为空");
        logger.info("into detail..");
        RestResult result = new RestResult();
        List<TransitStock> details = invalidService.getTransitListByInvalidId(id);
        result.setItem(details);
        return result;
    }

    /**
     * 删除报废单
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    @ResponseBody
    public RestResult delete(Integer id) {
        Assert.notNull(id, "id不能为空");
        logger.info(" delete invalid..id[{}]", id);
        RestResult result = new RestResult();
        invalidService.delete(id);
        return result;
    }

    /**
     * 删除报废单 单条明细
     */
    @RequestMapping(value = "deleteItem", method = RequestMethod.POST)
    @ResponseBody
    public RestResult deleteItem(Integer id) {
        Assert.notNull(id, "id不能为空");
        logger.info(" delete invalid..id[{}]", id);
        RestResult result = new RestResult();
        InvalidItem item = invalidItemService.getById(id);
        Assert.notNull(item, "查询不到该信息");

        invalidItemService.deleteByTransitId(id);
        invalidService.updateQty(item.getInvalidId(),invalidItemService.countByInvalidId(item.getInvalidId()));
        return result;
    }

    /**
     * 审核通过
     */
    @RequestMapping(value = "/auditPass", method = RequestMethod.POST)
    @ResponseBody
    public RestResult auditPass(Integer id) {
        RestResult restResult = new RestResult();
        logger.info("/invalid/auditPass request id:{}", id);
        try {
            invalidService.auditPass(id);
        } catch (Exception e) {
            logger.error("error:{}", e);
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("操作失败");
        }
        return restResult;
    }

    /**
     * 批量审核通过
     */
    @RequestMapping(value = "/auditPassBat", method = RequestMethod.POST)
    @ResponseBody
    public RestResult auditPassBat(Integer[] ids) {
        RestResult restResult = new RestResult();
        logger.info("/invalid/auditPassBat request:{}", ids);
        try {
            for (int i = 0; i < ids.length; ++i) {
                invalidService.auditPass(ids[i]);
            }
        } catch (Exception e) {
            logger.error("error:{}", e);
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("操作失败");
        }
        return restResult;
    }

    /**
     * 审核不通过
     */
    @RequestMapping(value = "/reject", method = RequestMethod.POST)
    @ResponseBody
    public RestResult reject(Integer id) {
        RestResult restResult = new RestResult();
        logger.info("/invalid/reject request:{}", id);
        try {
            invalidService.reject(id);
        } catch (Exception e) {
            logger.error("error:{}", e);
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("操作失败");
        }
        return restResult;
    }


    /**
     * 批量审核不通过
     */
    @RequestMapping(value = "/rejectBat", method = RequestMethod.POST)
    @ResponseBody
    public RestResult rejectBat(Integer[] ids) {
        RestResult restResult = new RestResult();
        logger.info("/invalid/rejectBat request:{}", ids);
        try {
            for (int i = 0; i < ids.length; ++i) {
                invalidService.reject(ids[i]);
            }
        } catch (Exception e) {
            logger.error("error:{}", e);
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("操作失败");
        }
        return restResult;
    }
}
