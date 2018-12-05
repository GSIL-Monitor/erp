package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.ErpOrderInfoMapper;
import com.stosz.olderp.ext.model.ErpOrderInfo;
import com.stosz.olderp.ext.service.IErpOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther carter
 * create time    2017-12-03
 */
@Service
public class ErpOrderInfoService implements IErpOrderInfoService {

    @Autowired
    private ErpOrderInfoMapper erpOrderInfoMapper;

    @Override
    public ErpOrderInfo findById(Long aLong) {
        return erpOrderInfoMapper.findById(aLong);
    }

    @Override
    public List<ErpOrderInfo> findByIds(List<Integer> ids) {
        if (ids == null || ids.size() == 0) return new ArrayList<>();
        return erpOrderInfoMapper.findByIds(ids);
    }
    @Override
    public List<String> findForwardStatus(List<Integer> ids) {
        return erpOrderInfoMapper.findForwardStatus(ids);
    }

    @Override
    public List<Integer> findSettlements() {
        return erpOrderInfoMapper.findSettlements();
    }

    @Override
    public int findRecords(Integer idOrder) {
        return erpOrderInfoMapper.findRecords(idOrder);
    }

}
