package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.OldErpOrderMapper;
import com.stosz.olderp.ext.model.OldErpOrder;
import com.stosz.olderp.ext.model.OldErpOrderLink;
import com.stosz.olderp.ext.service.IOldErpOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OldErpOrderService implements IOldErpOrderService {

    @Autowired
    private OldErpOrderMapper oldErpOrderMapper;



    /**
     * 增量查询查找老ERP订单联系人
     * 通过id排序
     * @param offset 分段查询起始位置
     * @param limit 分段查询条数
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @Override
    public List<OldErpOrderLink> findOldErpOrderLinkInc(Integer offset, Integer limit, LocalDateTime beginTime, LocalDateTime endTime){
        Assert.isTrue(offset >= 0 , "offset必须大于等于0");
        Assert.isTrue(limit > 0 , "offset必须大于0");
        Assert.notNull(beginTime , "开始时间不能为空");
        Assert.notNull(endTime , "结束时间不能为空");
        return oldErpOrderMapper.findOldErpOrderLinkInc(offset,limit,beginTime,endTime);
    }


    /**
     * 增量统计老ERP订单联系人数量
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @return
     */
    @Override
    public Integer countOldErpOrderLinkInc(LocalDateTime beginTime, LocalDateTime endTime){
        Assert.notNull(beginTime , "开始时间不能为空");
        Assert.notNull(endTime , "结束时间不能为空");
        return oldErpOrderMapper.countOldErpOrderLinkInc(beginTime,endTime);
    }


    /**
     * 查找老ERP订单联系人
     * 通过id排序
     * @param offset 分段查询起始位置
     * @param limit 分段查询条数
     * @return
     */
    @Override
    public List<OldErpOrderLink> findOldErpOrderCustomer(Integer offset, Integer limit){
        Assert.isTrue(offset >= 0 , "offset必须大于等于0");
        Assert.isTrue(limit > 0 , "offset必须大于0");
        return oldErpOrderMapper.findRiskOldErpOrderLink(offset,limit);
    }


    /**
     * 统计老ERP订单联系人数量
     * @return
     */
    @Override
    public Integer countOldErpOrderCustomer(){
        return oldErpOrderMapper.countOldErpOrderLink();
    }

    @Override
    public List<OldErpOrderLink> findRiskOldErpOrderLinkByTel(String tel, Integer offset, Integer limit){
        Assert.isTrue(offset >= 0 , "offset必须大于等于0");
        Assert.isTrue(limit > 0 , "limit必须大于0");
        return oldErpOrderMapper.findRiskOldErpOrderLinkByTel(tel,limit,offset);
    }

    @Override
    public List<OldErpOrder> fetchByIdRegion(int idMin, int idMax) {
        List<OldErpOrder>  oldErpOrders = oldErpOrderMapper.fetchByIdRegion(idMin,idMax);
        return oldErpOrders;
    }

    @Override
    public int getMaxId() {
        return oldErpOrderMapper.getMaxId();
    }

    @Override
    public List<OldErpOrder> findOldErpOrder(Integer offset, Integer limit, LocalDateTime beginTime, LocalDateTime endTime) {
        Assert.isTrue(offset >= 0 , "offset必须大于等于0");
        Assert.isTrue(limit > 0 , "offset必须大于0");
        Assert.notNull(beginTime , "开始时间不能为空");
        Assert.notNull(endTime , "结束时间不能为空");
        return oldErpOrderMapper.findErpOrder(offset,limit,beginTime,endTime);
    }

    @Override
    public Integer countOldErpOrder(LocalDateTime beginTime, LocalDateTime endTime) {
        Assert.notNull(beginTime , "开始时间不能为空");
        Assert.notNull(endTime , "结束时间不能为空");
        return oldErpOrderMapper.countErpOrder(beginTime,endTime);
    }

}
