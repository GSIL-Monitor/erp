package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.ErpOrderItemMapper;
import com.stosz.olderp.ext.model.ErpOrderItem;
import com.stosz.olderp.ext.service.IErpOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther carter
 * create time    2017-12-03
 */
@Service
public class ErpOrderItemService implements IErpOrderItemService {

    @Autowired
    private ErpOrderItemMapper erpOrderItemMapper;

    @Override
    public List<ErpOrderItem> findByParam(Integer idOrder_olderp) {
        return erpOrderItemMapper.findByParam(idOrder_olderp);
    }

    @Override
    public List<ErpOrderItem> findByIds(List<Integer> ids) {
        if (ids == null || ids.size() == 0) return new ArrayList<>();
        return erpOrderItemMapper.findByIds(ids);
    }
}
