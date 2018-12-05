package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.OldErpCheckProductMapper;
import com.stosz.olderp.ext.model.OldErpCheckProduct;
import com.stosz.olderp.ext.service.IOldErpCheckProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/10/11]
 */
@Service
public class OldErpCheckProductService implements IOldErpCheckProductService {

    @Resource
    private OldErpCheckProductMapper oldErpCheckProductMapper;


    @Override
    public List<OldErpCheckProduct> findAll() {
        return oldErpCheckProductMapper.findAll();
    }
}
