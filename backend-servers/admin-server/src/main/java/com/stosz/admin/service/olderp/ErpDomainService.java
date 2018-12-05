package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.ErpDomainMapper;
import com.stosz.olderp.ext.model.ErpDomain;
import com.stosz.olderp.ext.service.IErpDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther carter
 * create time    2017-12-03
 */
@Service
public class ErpDomainService implements IErpDomainService {

    @Autowired
    private ErpDomainMapper erpDomainMapper;

    @Override
    public ErpDomain findById(Long aLong) {
        return erpDomainMapper.findById(aLong);
    }

    @Override
    public List<ErpDomain> findByIds(List<Integer> ids) {
        if (ids == null || ids.size() == 0) return new ArrayList<>();
        return erpDomainMapper.findByIds(ids);
    }
}
