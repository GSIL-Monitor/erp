package com.stosz.store.web;

import com.google.common.collect.Lists;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.ExcelUtil;
import com.stosz.plat.utils.JsonUtils;
import com.stosz.plat.utils.StringUtils;
import com.stosz.store.ext.dto.request.AddTakeStockReq;
import com.stosz.store.ext.dto.request.ImportDeliverReq;
import com.stosz.store.ext.dto.request.ImportTrackingReq;
import com.stosz.store.ext.dto.request.SearchTransitPageInfoReq;
import com.stosz.store.ext.dto.response.ExportPackRes;
import com.stosz.store.ext.dto.response.SearchTransitPageInfoRes;
import com.stosz.store.ext.enums.ErrMsgEnum;
import com.stosz.store.ext.enums.TransitStateEnum;
import com.stosz.store.ext.model.Transfer;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.service.TakeStockService;
import com.stosz.store.service.TransferService;
import com.stosz.store.service.TransitStockService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 转寄入库
 * @auther ChenShifeng
 * @Date Create time    2017/11/22
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@RestController
@RequestMapping("store/transitStock")
public class TransitStockController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TransitStockService transitStockService;

    @Autowired
    private TakeStockService takeStockService;

    @Autowired
    private TransferService transferService;

    /**
     * 搜索
     */
    @RequestMapping(value = "search", method = RequestMethod.POST)
    @ResponseBody
    public RestResult search(SearchTransitPageInfoReq param, HttpServletRequest request) {

        if (logger.isDebugEnabled()) {
            logger.debug("search param:{}", JsonUtils.toJson(param));
        }

        TransitStock stock = new TransitStock();
        BeanUtils.copyProperties(param, stock);
        if (StringUtils.isNotBlank(param.getOrderIdNew())) {
            stock.setOrderIdNew(Long.parseLong(param.getOrderIdNew()));
        }
        return transitStockService.findList(stock);
    }

    /**
     * 导入运单(转寄入库)
     */
    @RequestMapping(value = "importTracking", method = RequestMethod.POST)
    @ResponseBody
    public RestResult importTracking(ImportTrackingReq param) {

        if (logger.isDebugEnabled()) {
            logger.debug("importTracking param:{}", param);
        }

        RestResult result = new RestResult();
        Assert.notNull(param.getWmsId(), "仓库id,参数错误");
        Assert.notNull(param.getTrack(), "运单信息,参数错误");

        String[] tracks = param.getTrack().split("\\|");

        int tracksSize = tracks.length;
        Assert.notEmpty(tracks, "运单信息格式有误");
        Map<String, String> resMsg = new HashMap<>(tracksSize);

        for (int i = 0; i < tracksSize; ++i) {
            String[] tracksAndLocal = tracks[i].split("\\,");

            if (transitStockService.isInserted(tracksAndLocal[0])) {
                resMsg.put(tracksAndLocal[0], ErrMsgEnum.duplicate.display());
                continue;
            }

            TransitStock transitStock = transitStockService.findByTrackNoOld(tracksAndLocal[0]);
            if (transitStock == null) {
                resMsg.put(tracksAndLocal[0], ErrMsgEnum.errTrackingNo.display());
                continue;
            } else {
                try {
                    switch (TransitStateEnum.fromName(transitStock.getState())) {
                        case wait_inTransit:
                            if (param.getWmsId() != transitStock.getWmsId()) {
                                resMsg.put(transitStock.getTrackingNoOld(), ErrMsgEnum.errWms.display());
                                continue;
                            }
                            break;
                        case transferring:
                            resMsg.put(transitStock.getTrackingNoOld(), ErrMsgEnum.errState.display());
                            continue;
                        case delivered:
                            Transfer transfer = transferService.findByTrack(transitStock.getTrackingNoOld());
                            if (param.getWmsId() != transfer.getInWmsId()) {
                                resMsg.put(transitStock.getTrackingNoOld(), ErrMsgEnum.errWms.display());
                                continue;
                            }
                            break;

                        default:
                    }

                    transitStock.setWmsId(param.getWmsId());
                    transitStock.setStockName(param.getStockName());
                    if (tracksAndLocal.length > 1) {
                        transitStock.setStorageLocal(tracksAndLocal[1]);
                    }
                    logger.debug("inStock [{}]", transitStock);
                    transitStockService.inStock(transitStock);
                } catch (Exception e) {
                    resMsg.put(tracksAndLocal[0], ErrMsgEnum.errOperate.display());
                }
            }
        }
        int success = tracksSize - resMsg.size();
        Map<String, Object> resMap = new HashMap<>();
        resMap.put("total", tracksSize);
        resMap.put("success", success);
        resMap.put("error", resMsg.size());
        resMap.put("errDetail", resMsg);

        result.setItem(resMap);
        return result;
    }

    /**
     * 拣货导出
     * 导出预操作，目的是将查询条件存在session中。ajax无法下载文件。
     *
     * @param param
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("preExportPack")
    public RestResult preExportPack(SearchTransitPageInfoReq param, HttpServletRequest request) throws IOException {
        TransitStock transitStock = new TransitStock();
        BeanUtils.copyProperties(param, transitStock);
        if (StringUtils.isNotBlank(param.getOrderIdNew())) {
            transitStock.setOrderIdNew(Long.parseLong(param.getOrderIdNew()));
        }
        request.getSession().setAttribute("exportPack", transitStock);
        RestResult result = new RestResult();
        result.setCode(RestResult.OK);
        result.setDesc("请求成功");
        return result;
    }

    /**
     * 拣货导出
     * 导出动作，从session中获取查询条件并查询导出
     */
    @RequestMapping(value = "exportPack", method = {RequestMethod.POST, RequestMethod.GET})

    @ResponseBody
    public void exportPack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        TransitStock transitStock = (TransitStock) request.getSession().getAttribute("exportPack");
        List<TransitStock> transitStockList = transitStockService.getTransitList(transitStock);
        List<ExportPackRes> exportPackResList = Lists.newArrayListWithCapacity(transitStockList.size());
        if (CollectionUtils.isNotNullAndEmpty(transitStockList)) {
            //状态改为“转寄拣货中”
            for (TransitStock ts : transitStockList) {

                switch (TransitStateEnum.fromName(ts.getState())) {
                    case matched:
                    case transferring:
                        try {
                            transitStockService.exportPack(ts);
                        } catch (Exception e) {
                            ts.setExportStatus("失败");
                            ts.setExportMsg("操作失败，请重试");
                        }
                        break;

                    default:
                        ts.setExportStatus("失败");
                        ts.setExportMsg("非转寄匹配或者调拨状态，拣货失败");
                        break;
                }

                ExportPackRes res = new ExportPackRes();
                BeanUtils.copyProperties(ts, res);
                exportPackResList.add(res);
            }
        }
        String[] headers = {"地区", "订单号", "新运单号", "姓名", "电话", "内部产品名", "外文产品名", "sku", "总价",
                "产品数量", "送货地址", "留言备注", "下单时间", "订单状态", "运单号", "货位", "邮编", "仓库", "省",
                "市", "区", "导出结果", "失败原因"};
        try {
            ExcelUtil.exportExcelAndDownload(response, "拣货导出名单列表", "拣货导出名单", headers, exportPackResList, null);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return;
    }

    /**
     * 发货导入
     */
    @RequestMapping(value = "importDeliver", method = RequestMethod.POST)
    @ResponseBody
    public RestResult importDeliver(ImportDeliverReq param) {
        RestResult result = new RestResult();
        Assert.notNull(param.getLogisticsId(), "物流id,参数错误");
        Assert.hasText(param.getTrack(), "运单信息,参数错误");

        logger.debug("importDeliver param:{}", param);
        String[] tracks = param.getTrack().split("\\|");
        int tracksSize = tracks.length;
        Assert.notEmpty(tracks, "运单信息格式有误");
        Map<String, String> resMsg = new HashMap<>(tracksSize);

        for (int i = 0; i < tracksSize; ++i) {
            String[] tracksAndOrder = tracks[i].split("\\,");
            if (tracksAndOrder.length > 1) {
                if (transitStockService.isImportDeliver(tracksAndOrder[0], tracksAndOrder[1])) {
                    resMsg.put(tracks[i], ErrMsgEnum.delivered.display());
                    continue;
                }

                TransitStock transitStock = transitStockService.findByOrdersNew(tracksAndOrder[0]);
                if (transitStock == null) {
                    resMsg.put(tracks[i], ErrMsgEnum.errOrderId.display());
                    continue;
                } else {
                    try {
                        transitStock.setOrderIdNew(Long.parseLong(tracksAndOrder[0]));
                        transitStock.setTrackingNoNew(tracksAndOrder[1]);
                        transitStock.setLogisticsNameNew(param.getLogisticsName());
                        transitStock.setLogisticsId(param.getLogisticsId());

                        transitStockService.importDeliver(transitStock);
                    } catch (Exception e) {
                        resMsg.put(tracks[i], ErrMsgEnum.errOperate.display());
                    }
                }

            } else {
                //只传1个，只能算运单号，调拨处理

                if (transitStockService.isTransferDeliver(tracksAndOrder[0])) {
                    resMsg.put(tracks[i], ErrMsgEnum.delivered.display());
                    continue;
                }

                TransitStock transitStock = transitStockService.findByTrackNoOld(tracksAndOrder[0]);
                if (transitStock == null) {
                    resMsg.put(tracks[i], ErrMsgEnum.errTrackingNo.display());
                    continue;
                } else {
                    try {
                        transitStock.setLogisticsNameNew(param.getLogisticsName());
                        transitStock.setLogisticsId(param.getLogisticsId());

                        transitStockService.importDeliver(transitStock);
                    } catch (Exception e) {
                        resMsg.put(tracks[i], ErrMsgEnum.errOperate.display());
                    }
                }
            }
        }

        Map<String, Object> resMap = new HashMap<>();
        resMap.put("total", tracksSize);
        resMap.put("success", tracksSize - resMsg.size());
        resMap.put("error", resMsg.size());
        resMap.put("errDetail", resMsg);

        result.setItem(resMap);
        return result;
    }

    /**
     * 导出预操作，目的是将查询条件存在session中。ajax无法下载文件。
     *
     * @param param
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("preExportExcel")
    public RestResult preExportExcel(SearchTransitPageInfoReq param, HttpServletRequest request) throws IOException {
        TransitStock transitStock = new TransitStock();
        BeanUtils.copyProperties(param, transitStock);
        if (StringUtils.isNotBlank(param.getOrderIdNew())) {
            transitStock.setOrderIdNew(Long.parseLong(param.getOrderIdNew()));
        }
        request.getSession().setAttribute("exportExcel", transitStock);
        RestResult result = new RestResult();
        result.setCode(RestResult.OK);
        result.setDesc("请求成功");
        return result;
    }

    /**
     * 直接导出查询结果Excel
     */
    @RequestMapping(value = "exportExcel", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws Exception {
        TransitStock transitStock = (TransitStock) request.getSession().getAttribute("exportExcel");

        List<TransitStock> transitStockList = transitStockService.getTransitList(transitStock);
        List<SearchTransitPageInfoRes> exportList = new ArrayList<>(transitStockList.size());
        if (CollectionUtils.isNotNullAndEmpty(transitStockList)) {
            for (TransitStock ts : transitStockList) {
                SearchTransitPageInfoRes res = new SearchTransitPageInfoRes();
                BeanUtils.copyProperties(ts, res);
                exportList.add(res);
            }
        }

        String[] excludeFields = {"state", "deptId", "wmsId"};
        String[] headers = {"ID", "原运单号", "地区", "状态", "物流线路", "部门", "当前仓库", "产品信息", "内部名",
                "外文名", "SKU", "原订单号", "库位", "入库方式", "入库时间", "库龄（天）", "已匹配订单号",
                "下单时间", "拣货导出时间", "拣货导出人", "发货运单号", "发货物流", "物流状态", "发货时间"};
        try {
            ExcelUtil.exportExcelAndDownload(response, "导出名单列表", "导出数据", headers, exportList, excludeFields);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return;
    }

    /**
     * 盘点
     */
    @RequestMapping(value = "takeStock", method = RequestMethod.POST)
    @ResponseBody
    public RestResult takeStock(@RequestBody AddTakeStockReq param) {
        Assert.notEmpty(param.getTrackings(), "运单号不能为空");
        takeStockService.create(param);
        return new RestResult();
    }

    /**
     * 根据运单查询包裹信息
     */
    @RequestMapping(value = "getTransitByTrack", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getTransitByTrack(String track) {
        RestResult restResult = new RestResult();
        Assert.hasText(track, "运单号不能为空");

        TransitStock stock = new TransitStock();
        stock.setTrackingNoOld(track);
        List<TransitStock> stockList = transitStockService.getTransitList(stock);
        Assert.notEmpty(stockList, "未查询到此包裹信息");

        restResult.setItem(stockList.get(0));
        return restResult;
    }
}
