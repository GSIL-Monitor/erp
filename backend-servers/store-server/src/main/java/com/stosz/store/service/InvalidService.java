package com.stosz.store.service;

import com.stosz.fsm.FsmProxyService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.store.ext.dto.request.SearchInvalidReq;
import com.stosz.store.ext.enums.InvalidEventEnum;
import com.stosz.store.ext.model.Invalid;
import com.stosz.store.ext.model.TransitItem;
import com.stosz.store.ext.model.TransitStock;
import com.stosz.store.mapper.InvalidMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:ChenShifeng
 * @Description: 转寄 报废
 * @Created Time:2017/11/23 15:49
 * @Update Time:
 */
@Service
public class InvalidService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private InvalidMapper mapper;

    @Autowired
    private InvalidItemService invalidItemService;

    @Resource
    private FsmProxyService<Invalid> invalidFsmProxyService;

    @Resource
    private TransitStockService transitStockService;

    /**
     * 添加
     *
     * @param invalid
     */
    public Integer insert(Invalid invalid) {
        UserDto user = ThreadLocalUtils.getUser();

        invalid.setUpdateAt(LocalDateTime.now());
        invalid.setCreateAt(LocalDateTime.now());
        invalid.setCreatorId(user.getId());
        invalid.setCreator(user.getLastName());

        return mapper.insert(invalid);
    }

    /**
     * 新建报废单
     *
     * @param invalid
     */
    public void create(Invalid invalid) {
        invalid.setState("start");
        invalid.setStateTime(LocalDateTime.now());
        insert(invalid);
        invalidFsmProxyService.processEvent(invalid, InvalidEventEnum.create, "新建报废单");
    }

    /**
     * 删除 报废单
     *
     * @param id
     */
    public void delete(Integer id) {
        invalidItemService.deleteByInvalidId(id);
        mapper.delete(id);
    }

    /**
     * 提交
     *
     * @param invalid
     */
    public void submit(Invalid invalid) {
        invalidFsmProxyService.processEvent(invalid, InvalidEventEnum.submit, "提交报废单");
    }

    /**
     * 搜索查询
     *
     * @param
     */
    public RestResult findList(SearchInvalidReq param) {
        RestResult rs = new RestResult();
        int count = mapper.count(param);
        rs.setTotal(count);
        if (count == 0) {
            return rs;
        }
        List<Invalid> lst = mapper.find(param);

        rs.setItem(lst);
        rs.setDesc("查询成功");
        return rs;
    }

    /**
     * 查询明细
     */
    public List<TransitStock> getTransitListByInvalidId(Integer id) {
        List<TransitStock> list = mapper.getTransitListByInvalidId(id);
        transitStockService.fillOrderInfo(list);
        return list;
    }

    /**
     * 更新盘亏数
     *
     * @param id
     * @param
     */
    public void updateQty(Integer id, Integer qty) {
        mapper.updateQty(id, qty);
    }

    public List<TransitItem> getTransitItemListByInvalidId(Integer id) {
        return mapper.getTransitItemListByInvalidId(id);
    }


    /**
     * 审核通过
     */
    public void auditPass(Integer id) {
        Assert.notNull(id, "id不能为空");
        Invalid invalid = mapper.getById(id);

        Assert.notNull(invalid, String.format("id[%s]的报废单不存在", id));
        invalidFsmProxyService.processEvent(invalid, InvalidEventEnum.auditPass, "审核通过");
    }


    /**
     * 审核不通过
     */
    public void reject(Integer id) {
        Assert.notNull(id, "id不能为空");
        Invalid invalid = mapper.getById(id);

        Assert.notNull(invalid, String.format("id[%s]的报废单不存在", id));
        invalidFsmProxyService.processEvent(invalid, InvalidEventEnum.reject, "审核不通过");
    }


}
