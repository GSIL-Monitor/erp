package com.stosz.store.web;

import com.stosz.admin.ext.model.JobAuthorityRel;
import com.stosz.admin.ext.service.IJobAuthorityRelService;
import com.stosz.order.ext.dto.WmsResponse;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.ExcelUtil;
import com.stosz.plat.web.AbstractController;
import com.stosz.store.ext.dto.request.SearchTransferReq;
import com.stosz.store.ext.dto.request.TransferBaseStockReq;
import com.stosz.store.ext.dto.request.TransferReq;
import com.stosz.store.ext.dto.response.ExportTransfer;
import com.stosz.store.ext.enums.TransferTypeEnum;
import com.stosz.store.ext.enums.WmsTypeEnum;
import com.stosz.store.ext.model.Transfer;
import com.stosz.store.ext.model.TransferItem;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.service.SettlementMonthService;
import com.stosz.store.service.TransferItemService;
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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author:yangqinghui
 * @Description:仓库管理
 * @Created Time:2017/11/25 10:10
 * @Update Time:2017/11/25 10:10
 */
@RestController
@RequestMapping("store/transfer")
public class TransferController extends AbstractController {

    private static Logger logger = LoggerFactory.getLogger(TransferController.class);

    @Autowired
    private TransferService transferService;

    @Autowired
    private SettlementMonthService settlementMonthService;

    @Autowired
    private TransferItemService transferItemService;

    @Autowired
    private TransitStockService transitStockService;

    @Autowired
    private IJobAuthorityRelService jobAuthorityRelService;

    /**
     * 搜 索
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    @ResponseBody
    public RestResult searchTransfer(SearchTransferReq param) {

        if (logger.isDebugEnabled()) {
            logger.debug("search param:{}", param);
        }

        //用户
        UserDto userDto = ThreadLocalUtils.getUser();

        //权限类型（公司，部门，个人）
        JobAuthorityRel jobAuthorityRel = jobAuthorityRelService.getByUser(userDto.getId());
        param.setUserId(userDto.getId());
        param.setDeptId(userDto.getDeptId());
        param.setJobAuthorityRel(jobAuthorityRel.getAuthority());

        return transferService.findPage(param);

    }

    /**
     * 查询调拨单明细
     */
    @RequestMapping(value = "/getTransferItem", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getTransferItem(@RequestParam Integer tranId) {
        if (logger.isDebugEnabled()) {
            logger.debug("search param:{}", tranId);
        }
        RestResult result = new RestResult();
        result.setItem(transferItemService.findByTranIdAndNullTrack(tranId));
        return result;

    }

    /**
     * 查询调拨单包裹明细
     */
    @RequestMapping(value = "/getPackItem", method = RequestMethod.POST)
    @ResponseBody
    public RestResult getPackItem(@RequestParam Integer tranId) {
        if (logger.isDebugEnabled()) {
            logger.debug("search id:{}", tranId);
        }
        RestResult result = new RestResult();
        result.setItem(transitStockService.getTransitByTransferId(tranId));
        return result;

    }

    /**
     * <p>
     * 导出预操作，目的是将查询条件存在session中。
     *
     * @param param
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("preExportExcel")
    public RestResult preExportExcel(SearchTransferReq param, HttpServletRequest request) throws IOException {
        request.getSession().setAttribute("exportTransferExcel", param);
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
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        SearchTransferReq param = (SearchTransferReq) request.getSession().getAttribute("exportTransferExcel");

        try {
            List<Transfer> transferList = transferService.findList(param);

            List<ExportTransfer> exportList = new ArrayList<>(transferList.size());
            if (CollectionUtils.isNotNullAndEmpty(transferList)) {
                for (Transfer ts : transferList) {
                    ExportTransfer res = new ExportTransfer();
                    BeanUtils.copyProperties(ts, res);
                    exportList.add(res);
                }
            }

            String[] excludeFields = {};
            String[] headers = {"调拨单号", "调拨类型", "状态", "调出仓", "调出部门", "调入仓", "调入部门", "总数量", "产品信息",
                    "备注", "创建人", "创建时间"};

            ExcelUtil.exportExcelAndDownload(response, "导出调拨单列表", "导出调拨单数据", headers, exportList, excludeFields);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return;
    }

    /**
     * 添加跨仓调拨
     */
    @RequestMapping(value = "/addTransfer", method = RequestMethod.POST)
    @ResponseBody
    public RestResult addTransfer(TransferReq transferReq) {

        Assert.notNull(transferReq.getInWmsId(), "转入仓不能为空");
        Assert.notNull(transferReq.getOutWmsId(), "转出仓不能为空");
        Assert.notNull(transferReq.getInType(), "转入仓类型不能为空");
        Assert.notNull(transferReq.getOutType(), "转出仓类型不能为空");

        Assert.notNull(transferReq.getTrackingNo(), "未输入运单或者sku值");
        logger.info("/transfer/addTransfer request data the transferReq:{}", transferReq);
        Transfer transfer = new Transfer();
        BeanUtils.copyProperties(transferReq, transfer);
        try {
            if (WmsTypeEnum.self.ordinal() == transferReq.getInType()) {
                if (WmsTypeEnum.self.ordinal() == transferReq.getOutType()) {
                    //普通=>普通
                    transfer.setType(TransferTypeEnum.normal2Normal.ordinal());
                    Map<String, Integer> transferSku = transferService.getTransferSku(transferReq.getTrackingNo());
                    if (transferSku.isEmpty()) {
                        return new RestResult(RestResult.FAIL, "数据有误！");
                    }
                    transferService.normalTransfer(transferSku, transfer);
                } else {
                    //转寄=>普通
                    transfer.setType(TransferTypeEnum.transit2Normal.ordinal());
                    String[] tracks = transferReq.getTrackingNo().split("\\|");
                    if (tracks.length <= 0) {
                        return new RestResult(RestResult.FAIL, "数据有误！");
                    }
                    transferService.transit2Normal(tracks, transfer, transferReq);
                }
            } else {
                //转寄=>转寄
                transfer.setType(TransferTypeEnum.transit2Transit.ordinal());
                String[] tracks = transferReq.getTrackingNo().split("\\|");
                if (tracks.length <= 0) {
                    return new RestResult(RestResult.FAIL, "数据有误！");
                }
                //同转寄=>普通  处理
                transferService.transit2Normal(tracks, transfer, transferReq);
            }

        } catch (Exception e) {
            logger.error("error:{}", e);
            return new RestResult(RestResult.FAIL, "执行失败！");
        }
        return new RestResult(RestResult.OK, "执行成功！");

    }

    /**
     * 添加转寄仓同仓调拨
     */
    @RequestMapping(value = "/addTransitTransfer", method = RequestMethod.POST)
    @ResponseBody
    public RestResult addTransitTransfer(TransferReq transferReq) {
        Assert.notNull(transferReq.getInDeptId(), "调入部门不能为空");
        Assert.notNull(transferReq.getTrackingNo(), "运单不能为空");
        logger.info("/transfer/addTransitTransfer transferReq:{}", transferReq);

        Transfer transfer = new Transfer();
        BeanUtils.copyProperties(transferReq, transfer);
        try {
            //转寄仓同仓
            transfer.setType(TransferTypeEnum.sameTransit.ordinal());
            transferService.sameTransitTransfer(transferReq, transfer);
        } catch (Exception e) {
            logger.error("error:{}", e);
            return new RestResult(RestResult.FAIL, "执行失败！");
        }
        return new RestResult(RestResult.OK, "执行成功！");

    }


    /**
     * 添加普通仓同仓调拨
     */
    @RequestMapping(value = "/addNormalTransfer", method = RequestMethod.POST)
    @ResponseBody
    public RestResult addNormalTransfer(@RequestBody List<TransferBaseStockReq> tranReqs) {
        try {
            //普通仓同仓调拨
            for (TransferBaseStockReq tranReq : tranReqs) {
                Assert.notNull(tranReq.getWmsId(), "仓库id不允许为空！");
                Assert.notNull(tranReq.getOutDeptId(), "出部门id不允许为空！");
                Assert.notNull(tranReq.getInDeptId(), "入部门id不允许为空！");
                Assert.notNull(tranReq.getSku(), "sku不允许为空！");
                logger.info("/transfer/addNormalTransfer transferReq:{}", tranReq);

                Transfer transfer = new Transfer();
                transfer.setOutWmsId(tranReq.getWmsId());
                transfer.setInWmsId(tranReq.getWmsId());
                transfer.setType(TransferTypeEnum.sameNormal.ordinal());
                transfer.setCreateAt(LocalDateTime.now());
                transfer.setStateTime(LocalDateTime.now());
                UserDto user = ThreadLocalUtils.getUser();
                transfer.setCreatorId(user.getId());
                transfer.setCreator(user.getLastName());
                transfer.setInDeptId(tranReq.getInDeptId());
                transfer.setOutDeptId(tranReq.getOutDeptId());
                transfer.setTransferQty(tranReq.getQty());

                TransferItem transferItem = new TransferItem();
                transferItem.setOutDeptId(tranReq.getOutDeptId());
                transferItem.setOutDeptNo(tranReq.getOutDeptNo());
                transferItem.setOutDeptName(tranReq.getOutDeptName());
                transferItem.setInDeptId(tranReq.getInDeptId());
                transferItem.setInDeptNo(tranReq.getOutDeptNo());
                transferItem.setInDeptName(tranReq.getInDeptName());
                transferItem.setSpu(tranReq.getSpu());
                transferItem.setSku(tranReq.getSku());
                transferItem.setExpectedQty(tranReq.getQty());
                //获取当前时间点sku的成本价
                BigDecimal skuCost = settlementMonthService.getSkuCost(transfer.getOutWmsId(), transferItem.getOutDeptId(), transferItem.getSku());
                Assert.notNull(skuCost, "未获取成本价不能进行计算");
                transferItem.setCost(skuCost);
                transferService.oneWmsTransfer(transfer, transferItem);
            }
        } catch (Exception e) {
            logger.error("error:{}", e);
            return new RestResult(RestResult.FAIL, "执行失败！");
        }

        return new RestResult(RestResult.OK, "执行成功！");

    }

    /**
     * 调拨单提交
     */
    @RequestMapping(value = "/transferSubmit", method = RequestMethod.POST)
    @ResponseBody
    public RestResult transferSubmit(Integer transferId) {
        RestResult restResult = new RestResult();
        logger.info("/transfer/transferSubmit request data the transferReq:{}", transferId);
        try {
            transferService.transferSubmit(transferId);
        } catch (Exception e) {
            logger.error("error:{}", e);
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("操作失败");
        }
        return restResult;
    }

    /**
     * 调拨单取消
     */
    @RequestMapping(value = "/transferCancel", method = RequestMethod.POST)
    @ResponseBody
    public WmsResponse cancelTransfer(Integer transferId) {
        WmsResponse restResult = new WmsResponse();
        logger.info("/transfer/transferCancel request data the transferReq:{}", transferId);
        try {
            transferService.transferDelete(transferId);
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
    @RequestMapping(value = "/transferFail", method = RequestMethod.POST)
    @ResponseBody
    public RestResult transferFail(Integer transferId) {
        RestResult restResult = new RestResult();
        logger.info("/transfer/transferFail request data the transferReq:{}", transferId);
        try {
            transferService.transferFail(transferId);
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
    @RequestMapping(value = "/transferFailBat", method = RequestMethod.POST)
    @ResponseBody
    public RestResult transferFailBat(Integer[] transferIds) {
        RestResult restResult = new RestResult();
        logger.info("/transfer/transferFailBat request data the transferReq:{}", transferIds);
        try {
            for (int i = 0; i < transferIds.length; ++i) {
                transferService.transferFail(transferIds[i]);
            }
        } catch (Exception e) {
            logger.error("error:{}", e);
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("操作失败");
        }
        return restResult;
    }

    /**
     * 跨仓调拨单审核通过
     */
    @RequestMapping(value = "/transferApprovePass", method = RequestMethod.POST)
    @ResponseBody
    public RestResult transferApprovePass(Integer transferId) {
        RestResult restResult = new RestResult();
        logger.info("/transfer/transferApprovePass request data the transferId:{}", transferId);
        try {
            transferService.transferApprovePass(transferId);
        } catch (Exception e) {
            logger.error("error:{}", e);
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("操作失败");
        }
        return restResult;
    }

    /**
     * 同仓调拨调出部门审核通过
     */
    @RequestMapping(value = "/outDeptTransPass", method = RequestMethod.POST)
    @ResponseBody
    public RestResult outDeptTransPass(Integer transferId) {
        RestResult restResult = new RestResult();
        logger.info("/transfer/outDeptTransPass request data the transferId:{}", transferId);
        try {
            transferService.outDeptTransPass(transferId);
        } catch (Exception e) {
            logger.error("error:{}", e);
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("操作失败");
        }
        return restResult;
    }

    /**
     * 同仓调拨调入部门审核通过
     */
    @RequestMapping(value = "/inDeptTransPass", method = RequestMethod.POST)
    @ResponseBody
    public RestResult inDeptTransPass(Integer transferId) {
        RestResult restResult = new RestResult();
        logger.info("/transfer/inDeptTransPass request data the transferId:{}", transferId);
        try {
            transferService.inDeptTransPass(transferId);
        } catch (Exception e) {
            logger.error("error:{}", e);
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("操作失败");
        }
        return restResult;
    }

    /**
     * 跨仓调拨单批量审核通过
     */
    @RequestMapping(value = "/transferApprovePassBat", method = RequestMethod.POST)
    @ResponseBody
    public RestResult transferApprovePassBat(Integer[] transferIds) {

        RestResult restResult = new RestResult();

        Assert.notEmpty(transferIds, "transferIds不能为空");
        logger.info("/transfer/transferApprovePass request data the transferIds:{}", transferIds);
        try {
            for (int i = 0; i < transferIds.length; ++i) {
                transferService.transferApprovePass(transferIds[i]);
            }

        } catch (Exception e) {
            logger.error("error:{}", e);
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("操作失败");
        }
        return restResult;
    }


    /**
     * 同仓调拨调出部门批量审核通过
     */
    @RequestMapping(value = "/outDeptTransPassBat", method = RequestMethod.POST)
    @ResponseBody
    public RestResult outDeptTransPassBat(Integer[] transferIds) {
        RestResult restResult = new RestResult();
        logger.info("/transfer/outDeptTransPassBatch request data the transferIds:{}", transferIds);
        try {

            for (int i = 0; i < transferIds.length; ++i) {
                transferService.outDeptTransPass(transferIds[i]);
            }
        } catch (Exception e) {
            logger.error("error:{}", e);
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("操作失败");
        }
        return restResult;
    }

    /**
     * 同仓调拨调入部门批量审核通过
     */
    @RequestMapping(value = "/inDeptTransPassBat", method = RequestMethod.POST)
    @ResponseBody
    public RestResult inDeptTransPassBat(Integer[] transferIds) {
        RestResult restResult = new RestResult();
        logger.info("/transfer/inDeptTransPassBatch request data the transferIds:{}", transferIds);
        try {
            for (int i = 0; i < transferIds.length; ++i) {
                transferService.inDeptTransPass(transferIds[i]);
            }
        } catch (Exception e) {
            logger.error("error:{}", e);
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("操作失败");
        }
        return restResult;
    }


}
