package com.stosz.store.web;

import com.stosz.plat.common.RestResult;
import com.stosz.store.ext.dto.request.SearchTakeStockReq;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.service.TakeStockService;
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
 * @Description: 盘点
 * @auther ChenShifeng
 * @Date Create time    2017/12/13
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@RestController
@RequestMapping("store/takeStock")
public class TakeStockController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TakeStockService takeStockService;

    /**
     * 搜索
     */
    @RequestMapping(value = "search", method = RequestMethod.POST)
    @ResponseBody
    public RestResult search(SearchTakeStockReq param) {
        logger.info("into takeStock..{}",param);
        return takeStockService.findList(param);
    }

    /**
     * 查询明细
     */
    @RequestMapping(value = "getDetail", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getDetail(Integer id) {
        Assert.notNull(id, "id不能为空");
        logger.info("into takeStock..");
        RestResult result = new RestResult();
        List<TransitStock> details = takeStockService.getTransitListById(id);
        result.setItem(details);
        return result;
    }

    /**
     * 初审审核通过
     */
    @RequestMapping(value = "/firstAuditPass", method = RequestMethod.POST)
    @ResponseBody
    public RestResult firstAuditPass(Integer id) {
        RestResult restResult = new RestResult();
        logger.info("/takeStock/firstAuditPass request id:{}", id);
        try {
            takeStockService.firstAuditPass(id);
        } catch (Exception e) {
            logger.error("error:{}", e);
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("操作失败");
        }
        return restResult;
    }

    /**
     * 批量初审审核通过
     */
    @RequestMapping(value = "/firstAuditPassBat", method = RequestMethod.POST)
    @ResponseBody
    public RestResult firstAuditPassBat(Integer[] ids) {
        RestResult restResult = new RestResult();
        logger.info("/takeStock/firstAuditPassBat request:{}", ids);
        try {
            for (int i = 0; i < ids.length; ++i) {
                takeStockService.firstAuditPass(ids[i]);
            }
        } catch (Exception e) {
            logger.error("error:{}", e);
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("操作失败");
        }
        return restResult;
    }

    /**
     * 复审审核通过
     */
    @RequestMapping(value = "/passFinance", method = RequestMethod.POST)
    @ResponseBody
    public RestResult passFinance(Integer id) {
        RestResult restResult = new RestResult();
        logger.info("/takeStock/passFinance request  id:{}", id);
        try {
            takeStockService.passFinance(id);
        } catch (Exception e) {
            logger.error("error:{}", e);
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("操作失败");
        }
        return restResult;
    }

    /**
     * 批量复审审核通过
     */
    @RequestMapping(value = "/passFinanceBat", method = RequestMethod.POST)
    @ResponseBody
    public RestResult passFinanceBat(Integer[] ids) {
        RestResult restResult = new RestResult();
        logger.info("/takeStock/passFinanceBat request:{}", ids);
        try {
            for (int i = 0; i < ids.length; ++i) {
                takeStockService.passFinance(ids[i]);
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
    @RequestMapping(value = "/rejectFirst", method = RequestMethod.POST)
    @ResponseBody
    public RestResult rejectFirst(Integer id) {
        RestResult restResult = new RestResult();
        logger.info("/takeStock/rejectFirst request date the Req:{}", id);
        try {
            takeStockService.rejectFirst(id);
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
    @RequestMapping(value = "/rejectFirstBat", method = RequestMethod.POST)
    @ResponseBody
    public RestResult rejectFirstBat(Integer[] ids) {
        RestResult restResult = new RestResult();
        logger.info("/takeStock/rejectFirstBat request:{}", ids);
        try {
            for (int i = 0; i < ids.length; ++i) {
                takeStockService.rejectFirst(ids[i]);
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
    @RequestMapping(value = "/rejectFinance", method = RequestMethod.POST)
    @ResponseBody
    public RestResult rejectFinance(Integer id) {
        RestResult restResult = new RestResult();
        logger.info("/takeStock/rejectFinance request :{}", id);
        try {
            takeStockService.rejectFinance(id);
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
    @RequestMapping(value = "/rejectFinanceBat", method = RequestMethod.POST)
    @ResponseBody
    public RestResult rejectFinanceBat(Integer[] ids) {
        RestResult restResult = new RestResult();
        logger.info("/takeStock/rejectFinanceBat request:{}", ids);
        try {
            for (int i = 0; i < ids.length; ++i) {
                takeStockService.rejectFinance(ids[i]);
            }
        } catch (Exception e) {
            logger.error("error:{}", e);
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("操作失败");
        }
        return restResult;
    }


}
