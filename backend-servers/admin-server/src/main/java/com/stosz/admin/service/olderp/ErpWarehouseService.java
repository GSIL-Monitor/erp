package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.ErpWarehouseMapper;
import com.stosz.olderp.ext.model.ErpWarehouse;
import com.stosz.olderp.ext.service.IErpWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther carter
 * create time    2017-12-03
 */
@Service
public class ErpWarehouseService implements IErpWarehouseService {

    @Autowired
    private ErpWarehouseMapper erpWarehouseMapper;

    @Override
    public ErpWarehouse findById(Integer idWarehouse) {
        return erpWarehouseMapper.findById(idWarehouse);
    }

    @Override
    public List<ErpWarehouse> findByIds(List<Integer> ids) {
        return erpWarehouseMapper.findByIds(ids);
    }

    @Override
    public List<ErpWarehouse> findAll() {
        return erpWarehouseMapper.findAll();
    }


}
