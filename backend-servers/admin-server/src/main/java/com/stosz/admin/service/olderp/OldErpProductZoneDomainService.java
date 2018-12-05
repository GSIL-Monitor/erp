package com.stosz.admin.service.olderp;

import com.stosz.admin.mapper.olderp.OldErpProductZoneDomainMapper;
import com.stosz.olderp.ext.model.OldErpProductZoneDomain;
import com.stosz.olderp.ext.service.IOldErpProductZoneDomainService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author xiongchenyang
 * @version [1.0 , 2017/9/19]
 */
@Service
public class OldErpProductZoneDomainService implements IOldErpProductZoneDomainService {

    @Resource
    private OldErpProductZoneDomainMapper oldErpProductZoneDomainMapper;

    @Override
    public OldErpProductZoneDomain getByUnique(Integer productId, Integer departmentId, Integer zoneId) {
        Assert.notNull(productId, "产品id不允许为空");
        Assert.notNull(zoneId, "区域id不允许为空");
        return oldErpProductZoneDomainMapper.getByUnique(productId, departmentId, zoneId);
    }

    @Override
    public List<OldErpProductZoneDomain> findByLimit(Integer limit, Integer start) {
        Assert.notNull(limit, "限制条数不允许为空！");
        Assert.notNull(start, "起始位置不允许为空！");
        return oldErpProductZoneDomainMapper.findByLimit(limit, start);
    }

    @Override
    public int count() {
        return oldErpProductZoneDomainMapper.count();
    }
}
