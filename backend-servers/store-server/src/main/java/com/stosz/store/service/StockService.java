package com.stosz.store.service;

import com.alibaba.druid.sql.visitor.functions.If;
import com.stosz.order.ext.mq.OccupyStockModel;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.rabbitmq.RabbitMQPublisher;
import com.stosz.plat.utils.BeanUtil;
import com.stosz.purchase.ext.model.OrderNotifyRequired;
import com.stosz.store.ext.dto.request.StockChangeReq;
import com.stosz.store.ext.dto.response.StockChangeRes;
import com.stosz.store.ext.model.ChangeStock;
import com.stosz.store.ext.model.Invoicing;
import com.stosz.store.ext.model.PlanRecord;
import com.stosz.store.ext.model.Stock;
import com.stosz.store.mapper.StockMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:2017/11/23 15:49
 * @Update Time:
 */
@Service
public class StockService {

    @Resource
    private StockMapper stockMapper;
    @Autowired
    private InvoicingService invoicingService;
    @Autowired
    private PlanRecordService planRecordService;
    @Autowired
    RabbitMQPublisher rabbitMQPublisher;

    private Logger logger = LoggerFactory.getLogger(StockService.class);

    /**
     * 根据已有信息查询Stock
     *
     * @param stock
     */
    public Stock findByIds(Stock stock) {
        List<Stock> stocks = stockMapper.queryStock(stock);
        Stock queryByIds = new Stock();
        if (stocks.size() > 0) {
            queryByIds = stocks.get(0);
        }
        return queryByIds;
    }

    /**
     * 添加stock
     *
     * @param stock
     */
    public Integer insert(Stock stock) {
        return stockMapper.insert(stock);
    }

    /**
     * 总数 stock
     *
     * @param stock
     */
    public Integer count(Stock stock) {
        return stockMapper.count(stock);
    }

    /**
     * 根据组合id查询仓库数据
     *
     * @param stock
     */
    public List<Stock> queryQty(Stock stock) {
        return stockMapper.queryStock(stock);

    }

    /**
     * 根据组合id查询仓库数据
     *
     * @param wmsId
     */
    public Integer getOtherQty(Integer wmsId, Integer deptId, String sku) {
        return stockMapper.getOtherQty(wmsId, deptId, sku);

    }

    /**
     * 整理Stock实体
     *
     * @param changeStock
     */
    Stock regulateStock(ChangeStock changeStock) {

        /** 根据仓库部门产品sku查询库存记录 无则创建**/
        Stock stock = new Stock(changeStock.getWmsId(),
                changeStock.getDeptId(),
                changeStock.getSku());

        Stock oldStock = findByIds(stock);
        /** 调整各项数据后进行数据合计 在库数=可用数+占用数 失败则返回数据有误**/
        Integer newInstockQty;
        Integer newUsableQty;
        Integer newOccupyQty;
        Integer newIntransitQty;

        if (oldStock.getId() == null) {
            oldStock = stock;
            newInstockQty = changeStock.getInstockChangeQty();
            newUsableQty = changeStock.getUsableChangeQty();
            newOccupyQty = changeStock.getOccupyChangeQty();
            newIntransitQty = changeStock.getIntransitChangeQty();
            oldStock.setDeptNo(changeStock.getDeptNo());
            oldStock.setDeptName(changeStock.getDeptName());
            oldStock.setSpu(changeStock.getSpu());
            oldStock.setCreateAt(LocalDateTime.now());
        } else {
            Assert.isTrue(oldStock.getInstockQty() == oldStock.getUsableQty() + oldStock.getOccupyQty(), "请保存好您的数据，紧急联系相关人员！");
            newInstockQty = oldStock.getInstockQty() + changeStock.getInstockChangeQty();
            newUsableQty = oldStock.getUsableQty() + changeStock.getUsableChangeQty();
            newOccupyQty = oldStock.getOccupyQty() + changeStock.getOccupyChangeQty();
            newIntransitQty = oldStock.getIntransitQty() + changeStock.getIntransitChangeQty();
        }

        if (newUsableQty < 0) {
            stock.setUsableQty(-1);
            return stock;
        }

        Assert.isTrue(newInstockQty >= 0, "库存数小于零，数据操作有误");
        Assert.isTrue(newIntransitQty >= 0, "在途数小于零,数据操作有误");
        Assert.isTrue(newInstockQty == newUsableQty + newOccupyQty, "数据有误，请核对您的数据");

        oldStock.setInstockQty(newInstockQty);
        oldStock.setUsableQty(newUsableQty);
        oldStock.setOccupyQty(newOccupyQty);
        oldStock.setIntransitQty(newIntransitQty);
        oldStock.setUpdateAt(LocalDateTime.now());

        return oldStock;
    }

    /**
     * 整理Invoicing实体
     *
     * @param changeStock
     */
    Invoicing regulateInvoicing(ChangeStock changeStock) {
        Invoicing invoicing = new Invoicing();
        if (changeStock.getRecord()) {

            BeanUtils.copyProperties(changeStock, invoicing);
            invoicing.setPlanId(planRecordService.nowPlanId());
            invoicing.setCreateAt(LocalDateTime.now());
            invoicing.setUpdateAt(LocalDateTime.now());
            UserDto user = ThreadLocalUtils.getUser();
            invoicing.setCreatorId(0);
            invoicing.setCreator("系统");
            if (user != null) {
                invoicing.setCreatorId(user.getId());
                invoicing.setCreator(user.getLastName());
            }
        }
        return invoicing;
    }

    /**
     * 获取当前排程表id
     *
     * @param
     */
    Integer getPlanId() {

        int year = LocalDateTime.now().getYear();
        int month = LocalDateTime.now().getMonth().getValue();
        PlanRecord planRecord = planRecordService.findOne(year, month);
        return planRecord.getId();
    }

    /**
     * 修改stock的库存量，可用数，，，，
     *
     * @parama stock
     */
    public Integer updateStockData(ChangeStock changeStock) {

        Assert.notNull(changeStock.getWmsId(), "订单无效，仓库id不允许为空！");
        Assert.notNull(changeStock.getDeptId(), "订单无效，部门id不允许为空！");
        Assert.notNull(changeStock.getSku(), "订单无效，suk不允许为空！");

        logger.info("update stock date the changeStock:{}", changeStock);
        Stock stock = regulateStock(changeStock);
        if (stock.getUsableQty() == -1) {
            return -1;
        }
        logger.info("update stock date the stock:{}", stock);

        /** 更新或保持stock数据 **/
        if (stock.getId() == null) {
            stock.setCreateAt(LocalDateTime.now());
            stock.setVersion(0L);
            int i = stockMapper.insert(stock);
            Assert.isTrue(i == 1, "库存:仓库id" + stock.getWmsId() + "部门id" + stock.getDeptId() + "sku" + stock.getSku() + "乐观锁未更新成功");
        } else {
            stock.setVersion(stock.getVersion() + 1);
            int i = stockMapper.updateStock(stock);
            Assert.isTrue(i == 1, "库存id：" + stock.getId() + "乐观锁未更新成功");
        }
        /** 进出仓记流水 **/
        if (changeStock.getRecord()) {
            Invoicing invoicing = regulateInvoicing(changeStock);
            invoicingService.insert(invoicing);
            logger.info("insert invoicing data the invoicing:{}", invoicing);
        }
        /** 当有用数增加是mq通知订单模块 **/
        if(changeStock.getUsableChangeQty()>0){
            OccupyStockModel occupyStockModel = new OccupyStockModel();
            occupyStockModel.setDept(changeStock.getDeptId());
            occupyStockModel.setSku(changeStock.getSku());
            occupyStockModel.setWarehouseId(changeStock.getWmsId());
            occupyStockModel.setCanAssignQty(stock.getUsableQty());
            //2,产生一个配货消息到MQ
            rabbitMQPublisher.saveMessage(occupyStockModel.MESSAGE_TYPE, occupyStockModel);
        }
        /** 当有用数改变和在途数改变时mq通知采购模块 type:1**/
        if(changeStock.getUsableChangeQty()!=0||changeStock.getIntransitChangeQty()!=0){
            OrderNotifyRequired orderNotifyRequired = new OrderNotifyRequired();
            orderNotifyRequired.setDeptId(changeStock.getDeptId());
            orderNotifyRequired.setSku(changeStock.getSku());
            orderNotifyRequired.setSpu(changeStock.getSpu());
            rabbitMQPublisher.saveMessage(orderNotifyRequired.MESSAGE_TYPE, orderNotifyRequired);
        }
        return stock.getUsableQty();
    }


    @Transactional(value = "storeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<StockChangeRes> updateStockDatas(List<ChangeStock> changeStocks) {
        List<StockChangeRes> stockChangeList = new ArrayList<>();
        for (ChangeStock changeStock : changeStocks) {
            Integer integer = updateStockData(changeStock);
            StockChangeRes stockChangeRes = new StockChangeRes();
            if (integer == -1)
                stockChangeRes.setCode(false);
            else
                stockChangeRes.setCode(true);
            BeanUtil.copy(changeStock, stockChangeRes);
            stockChangeList.add(stockChangeRes);
        }
        return stockChangeList;
    }

    @Transactional(value = "storeTransactionManager", propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean orderUpdateStockData(ChangeStock changeStock) {
        Integer integer = updateStockData(changeStock);
        if(integer==-1)
            return false;
        else
            return true;
    }
}
