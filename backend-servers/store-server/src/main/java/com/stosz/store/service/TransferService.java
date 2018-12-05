package com.stosz.store.service;

import com.google.common.collect.Lists;
import com.stosz.fsm.FsmConstant;
import com.stosz.fsm.FsmProxyService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.store.ext.dto.request.SearchTransferReq;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.dto.request.TransferReq;
import com.stosz.store.ext.enums.TransferEventEnum;
import com.stosz.store.ext.enums.TransferStateEnum;
import com.stosz.store.ext.enums.TransferTypeEnum;
import com.stosz.store.ext.enums.TransitStockEventEnum;
import com.stosz.store.ext.model.*;
import com.stosz.store.ext.service.IStockService;
import com.stosz.store.mapper.TransferMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/23 17:56
 * @Update Time:
 */
@Service
public class TransferService {

    @Resource
    private TransferMapper transferMapper;

    @Autowired
    private TransferItemService transferItemService;

    @Autowired
    private StockService stockService;

    @Autowired
    private IStockService iStockService;

    @Autowired
    private TransitStockService transitStockService;

    @Autowired
    private TransitItemService transitItemService;

    @Resource
    private FsmProxyService<Transfer> transferFsmProxyService;
    @Resource
    private FsmProxyService<TransitStock> transitStockFsmProxyService;

    private Logger logger = LoggerFactory.getLogger(TransferService.class);

    /**
     * 查询调拨单
     *
     * @param id
     */
    public Transfer findById(Integer id) {
        return transferMapper.getById(id);
    }

    /**
     * 根据运单号查询调拨单
     *
     * @param trackingNoOld
     */
    public Transfer findByTrack(String trackingNoOld) {
        return transferMapper.findByTrack(trackingNoOld);
    }

    /**
     * 添加调拨单
     *
     * @param transfer
     */
    public Integer insert(Transfer transfer) {

        Assert.notNull(transfer.getOutWmsId(), "转出仓不能为空");
        Assert.notNull(transfer.getInWmsId(), "转入仓不能为空");
        transfer.setStateTime(LocalDateTime.now());

        UserDto user = ThreadLocalUtils.getUser();
        transfer.setUpdateAt(LocalDateTime.now());
        transfer.setCreateAt(LocalDateTime.now());
        transfer.setCreatorId(user.getId());
        transfer.setCreator(user.getLastName());
        transfer.setCreateDeptId(user.getDeptId());

        return transferMapper.insert(transfer);
    }


    /**
     * 更新提交人
     *
     * @param transfer
     */
    public void update(Transfer transfer) {
        UserDto user = ThreadLocalUtils.getUser();
        transfer.setApprover(user.getLastName());
        transfer.setPassTime(LocalDateTime.now());
        transferMapper.update(transfer);
    }

    /**
     * 新建调拨（普通仓同仓调拨）
     *
     * @param transfer
     */
    @Transactional(value = "storeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void oneWmsTransfer(Transfer transfer, TransferItem transferItem) {

        logger.info("同仓调拨调拨单transfer：{} 调拨明细transferItem：{}", transfer, transferItem);
        insert(transfer);

        transferItem.setTranId(transfer.getId());
        transferItemService.insert(transferItem);
        transferFsmProxyService.processNew(transfer, "");
        transferFsmProxyService.processEvent(transfer, TransferEventEnum.create, TransferEventEnum.create.display());
    }

    /**
     * 新建跨仓调拨（普通仓 =>普通仓）
     *
     * @param transferSkus
     */
    @Transactional(value = "storeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void normalTransfer(Map<String, Integer> transferSkus, Transfer transfer) throws Exception {
        transfer.setTransferQty(transferSkus.size());
        insert(transfer);
        for (Map.Entry<String, Integer> entry : transferSkus.entrySet()) {
            TransferItem transferItem = new TransferItem();
            transferItem.setTranId(transfer.getId());
            transferItem.setSku(entry.getKey());
            transferItem.setExpectedQty(entry.getValue());
            transferItemService.insert(transferItem);
        }
        transferFsmProxyService.processNew(transfer, "");
        transferFsmProxyService.processEvent(transfer, TransferEventEnum.create, TransferEventEnum.create.display());
    }

    /**
     * 新建调拨（转寄=>普通）
     *
     * @param transfer
     */
    @Transactional(value = "storeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void transit2Normal(String[] tracks, Transfer transfer, TransferReq transferReq) throws Exception {
        int tracksSize = tracks.length;
        Assert.notEmpty(tracks, "运单信息格式有误");
        transfer.setState(FsmConstant.STATE_START);
        transfer.setTransferQty(tracksSize);
        insert(transfer);

        for (int i = 0; i < tracksSize; ++i) {
            TransitStock stock = new TransitStock();

            stock.setTrackingNoOld(tracks[i]);
            List<TransitStock> itemList = transitStockService.getTransitList(stock);
            transferReq.setInDeptName(itemList.get(0).getDeptName());
            this.save2TransferItem(transfer, itemList, transferReq);
        }
        transferFsmProxyService.processEvent(transfer, TransferEventEnum.create, TransferEventEnum.create.display());
    }

    /**
     * 转寄仓同仓调拨
     *
     * @param
     * @return
     */
    @Transactional(value = "storeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void sameTransitTransfer(TransferReq transferReq, Transfer transfer) {
        transfer.setState(FsmConstant.STATE_START);
        transfer.setTransferQty(1);//转寄暂时只有1个
        insert(transfer);
        TransitStock stock = new TransitStock();
        stock.setTrackingNoOld(transferReq.getTrackingNo());
        List<TransitStock> itemList = transitStockService.getTransitList(stock);
        this.save2TransferItem(transfer, itemList, transferReq);

        transferFsmProxyService.processEvent(transfer, TransferEventEnum.create, TransferEventEnum.create.display());
    }

    private void save2TransferItem(Transfer transfer, List<TransitStock> itemList, TransferReq transferReq) {
        List<TransferItem> saveList = Lists.newArrayList();
        for (TransitStock item : itemList) {

            TransferItem transferItem = new TransferItem();
            transferItem.setTranId(transfer.getId());
            transferItem.setOutDeptId(item.getDeptId());
            transferItem.setOutDeptName(item.getDeptName());
            if (TransferTypeEnum.fromId(transfer.getType()) == TransferTypeEnum.sameTransit) {
                transferItem.setInDeptId(transfer.getInDeptId());
            } else {
                Assert.isTrue(item.getWmsId() == transferReq.getOutWmsId(),
                        "运单[" + item.getTrackingNoOld() + "]的包裹不在仓库["
                                + transferReq.getOutWmsName() + "]中，操作失败");
                transferItem.setInDeptId(item.getDeptId());
            }
            transferItem.setInDeptName(transferReq.getInDeptName());
            transferItem.setTrackingNo(item.getTrackingNoOld());
            transferItem.setExpectedQty(1);

            saveList.add(transferItem);
        }
        if (TransferTypeEnum.fromId(transfer.getType()) == TransferTypeEnum.transit2Normal) {
            this.saveSkus2TransferItem(saveList, transfer);
        }

        transferItemService.insertBat(saveList);
    }

    /**
     * 逻辑拆包统计sku
     *
     * @param saveList
     * @param transfer
     */
    private void saveSkus2TransferItem(List<TransferItem> saveList, Transfer transfer) {

        List<TransitItem> transitItemList = transitItemService.getSkusByTransferId(transfer.getId());
        Map<String, Integer> skuMap = new HashMap<>();

        for (TransitItem item : transitItemList) {
            Integer qty = item.getQty();
            if (skuMap.containsKey(item.getSku())) {
                qty = qty + skuMap.get(item.getSku());
            }
            skuMap.put(item.getSku(), qty);
        }
        for (Map.Entry<String, Integer> entry : skuMap.entrySet()) {
            TransferItem transferItem = new TransferItem();
            transferItem.setTranId(transfer.getId());
            transferItem.setSku(entry.getKey());
            transferItem.setExpectedQty(entry.getValue());

            transferItem.setOutDeptId(transitItemList.get(0).getDeptId());
            transferItem.setOutDeptName(transitItemList.get(0).getDeptName());
            transferItem.setInDeptId(transitItemList.get(0).getDeptId());
            transferItem.setInDeptName(transitItemList.get(0).getDeptName());

            saveList.add(transferItem);
        }

    }

    /**
     * 提交调拨单
     *
     * @param tranId
     */
    public void transferSubmit(Integer tranId) {
        Transfer transfer = transferMapper.getById(tranId);
        Assert.notNull(transfer, "调拨单不存在");
        transferFsmProxyService.processEvent(transfer, TransferEventEnum.submit, TransferEventEnum.submit.display());
    }

    /**
     * 删除调拨单
     *
     * @param tranId
     */
    public void transferDelete(Integer tranId) {
        transferMapper.delete(tranId);
        transferItemService.delete(transferItemService.findByTranId(tranId));
    }

    /**
     * 调拨单审核不通过
     *
     * @param tranId
     */
    @Transactional(value = "storeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void transferFail(Integer tranId) {
        Transfer transfer = transferMapper.getById(tranId);
        Assert.notNull(transfer, "不存在该调拨单");
        transferFsmProxyService.processEvent(transfer, TransferEventEnum.approve_fail, TransferEventEnum.approve_fail.display());
    }

    /**
     * 同仓调拨调出部门审核通过
     *
     * @param tranId
     */
    @Transactional(value = "storeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void outDeptTransPass(Integer tranId) {

        Transfer transfer = transferMapper.getById(tranId);
        update(transfer);
        transferFsmProxyService.processEvent(transfer, TransferEventEnum.out_approve_pass, TransferEventEnum.out_approve_pass.display());
    }

    /**
     * 同仓调拨调入部门审核通过
     *
     * @param tranId
     */
    @Transactional(value = "storeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void inDeptTransPass(Integer tranId) {

        Transfer transfer = transferMapper.getById(tranId);

        transferFsmProxyService.processEvent(transfer, TransferEventEnum.in_approve_pass, TransferEventEnum.in_approve_pass.display());
    }


    /**
     * 跨仓调拨单审核通过
     *
     * @param tranId
     */
    @Transactional(value = "storeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void transferApprovePass(Integer tranId) {

        Transfer transfer = transferMapper.getById(tranId);
        UserDto user = ThreadLocalUtils.getUser();
        transfer.setApprover(user.getLastName());
        transfer.setPassTime(LocalDateTime.now());
        transferMapper.update(transfer);
        transferFsmProxyService.processEvent(transfer, TransferEventEnum.approve_pass, TransferEventEnum.approve_pass.display());
    }

    /**
     * 调拨出库
     *
     * @param tranId
     */
    @Transactional(value = "storeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void transferOutStock(Integer tranId, List<TransferItem> transferItems) {

        Transfer transfer = transferMapper.getById(tranId);
        transfer.setOutStockTime(LocalDateTime.now());
        transfer.setStateTime(LocalDateTime.now());
        transfer.setUpdateAt(LocalDateTime.now());
        transferMapper.update(transfer);
        for (TransferItem transferItem : transferItems) {
            transferItemService.update(transferItem);
        }
        transferFsmProxyService.processEvent(transfer, TransferEventEnum.out_store, TransferEventEnum.out_store.display());
    }


    /**
     * 调拨入库
     *
     * @param tranId
     */
    @Transactional(value = "storeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void transferInStock(Integer tranId, List<TransferItem> transferItems) {

        Transfer transfer = transferMapper.getById(tranId);
        transfer.setInStockTime(LocalDateTime.now());
        transfer.setStateTime(LocalDateTime.now());
        transfer.setUpdateAt(LocalDateTime.now());
        transferMapper.update(transfer);

        for (TransferItem transferItem : transferItems) {
            transferItemService.update(transferItem);
        }
        transferFsmProxyService.processEvent(transfer, TransferEventEnum.in_store, TransferEventEnum.in_store.display());

    }

    /**
     * 搜索调拨单信息
     *
     * @param req
     */
    public RestResult findPage(SearchTransferReq req) {
        RestResult rs = new RestResult();
        int count = transferMapper.count(req);
        rs.setTotal(count);
        if (count == 0) {
            return rs;
        }
        List<Transfer> lst = transferMapper.findPage(req);

        rs.setItem(lst);
        rs.setDesc("查询成功");
        return rs;
    }

    /**
     * 查询调拨单信息List
     *
     * @param req
     */
    public List<Transfer> findList(SearchTransferReq req) {
        return transferMapper.findList(req);
    }

    /**
     * 根据调拨单编号处理库存，出入库操作
     *
     * @param transferItems
     * @param transfer
     */
    public void updateStock(List<TransferItem> transferItems, Transfer transfer, String state) {

        for (TransferItem transferItem : transferItems) {

            StockChangeReq stockChangeReq = new StockChangeReq();
            stockChangeReq.setWmsId(transfer.getOutWmsId());
            stockChangeReq.setDeptId(transferItem.getOutDeptId());
            stockChangeReq.setSku(transferItem.getSku());
            stockChangeReq.setType(state);
            stockChangeReq.setChangeQty(transferItem.getExpectedQty());

            stockChangeReq.setVoucherNo(transferItem.getId().toString());
            stockChangeReq.setDeptNo(transferItem.getInDeptNo());
            stockChangeReq.setDeptName(transferItem.getInDeptName());
            stockChangeReq.setSpu(transferItem.getSpu());
            stockChangeReq.setChangeAt(transferItem.getCreateAt());

            ChangeStock changeStock = iStockService.transferChangeStockQty(stockChangeReq);
            stockService.updateStockData(changeStock);
        }
    }

    /**
     * 修改包裹状态
     *
     * @param eventEnum 转寄仓事件
     */
    public void changePack(List<TransferItem> itemList, TransitStockEventEnum eventEnum) {
        Assert.isTrue(itemList.size() > 0, "无调拨明细存在");
        for (TransferItem item : itemList) {
            if (StringUtils.hasText(item.getTrackingNo())) {
                TransitStock stock = transitStockService.findByTrackNoOld(item.getTrackingNo());
                transitStockFsmProxyService.processEvent(stock, eventEnum, eventEnum.display());
            }
        }

    }

    /**
     * 解析导入信息为shk Map
     *
     * @param ids
     */
    public Map<String, Integer> getTransferSku(String ids) {

        Assert.notNull(ids, "需要调拨的数据不能为空");
        String[] skuOrOn = ids.split("\\|");

        Map<String, Integer> skuMap = new HashMap<String, Integer>();
        if (skuOrOn.length > 0) {
            for (String id : skuOrOn) {
                if (id.contains(",")) {
                    String[] skuAndQty = id.split(",");
                    if (skuAndQty.length < 1) break;
                    Integer qty = Integer.parseInt(skuAndQty[1]);
                    if (skuMap.containsKey(skuAndQty[0])) {
                        qty = qty + skuMap.get(skuAndQty[0]);
                    }
                    skuMap.put(skuAndQty[0], qty);
                }
            }
        }
        return skuMap;
    }

    /**
     * 转寄仓入库
     *
     * @param tranId
     */
    public void transferInTransit(Integer tranId) {
        Transfer transfer = transferMapper.getById(tranId);
        if (TransferStateEnum.in_stored == TransferStateEnum.fromName(transfer.getState())) {
            return;
        }
        transferFsmProxyService.processEvent(transfer, TransferEventEnum.in_transit, TransferEventEnum.in_transit.display());
    }

    /**
     * 转寄仓出库
     *
     * @param tranId
     */
    public void transferOutTransit(Integer tranId) {
        Transfer transfer = transferMapper.getById(tranId);
        if (TransferStateEnum.out_stored == TransferStateEnum.fromName(transfer.getState())) {
            return;
        }
        transferFsmProxyService.processEvent(transfer, TransferEventEnum.out_transit, TransferEventEnum.out_transit.display());
    }

}
