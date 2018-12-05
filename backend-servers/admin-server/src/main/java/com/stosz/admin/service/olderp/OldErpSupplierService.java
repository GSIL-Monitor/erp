package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.OldErpSupplierMapper;
import com.stosz.olderp.ext.model.OldErpSupplier;
import com.stosz.olderp.ext.service.IOldErpSupplierService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/11/27]
 */
@Service
public class OldErpSupplierService implements IOldErpSupplierService{

    @Resource
    private OldErpSupplierMapper mapper;

    @Override
    public List<OldErpSupplier> findAll() {
        return mapper.findAll();
    }
}
