package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.ErpOrderShippingMapper;
import com.stosz.olderp.ext.model.ErpOrderShipping;
import com.stosz.olderp.ext.service.IErpOrderShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @auther carter
 * create time    2017-12-03
 */
@Service
public class ErpOrderShippingService implements IErpOrderShippingService {

    @Autowired
    private ErpOrderShippingMapper erpOrderShippingMapper;

    @Override
    public List<ErpOrderShipping> findByOrderId(Integer idOrder_olderp) {
        return erpOrderShippingMapper.findByOrderId(idOrder_olderp);
    }

    @Override
    public List<ErpOrderShipping> findByOrderIds(List<Integer> idOrders) {
        return erpOrderShippingMapper.findByOrderIds(idOrders);
    }
}
