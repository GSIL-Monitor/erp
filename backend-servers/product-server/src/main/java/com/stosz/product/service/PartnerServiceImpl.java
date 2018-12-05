package com.stosz.product.service;

import com.stosz.product.ext.enums.TypeEnum;
import com.stosz.product.ext.model.Partner;
import com.stosz.product.ext.service.IPartnerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 合作伙伴
 *
 * @author minxiaolong
 * @create 2018-01-03 17:24
 **/
@Service
public class PartnerServiceImpl implements IPartnerService{

    @Resource
    private PartnerService partnerService;

    @Override
    public List<Partner> findPartnerByType(TypeEnum type) {
        return partnerService.findPartnerByType(type);
    }

    @Override
    public Partner getById(Integer id) {
        return partnerService.getPartnerById(id);
    }

    @Override
    public List<Partner> findNameBySearch(String search, TypeEnum type) {
        return partnerService.findNameBySearch(search,type);
    }

    @Override
    public Partner findByCodeAndType(String code, TypeEnum typeEnum) {
        return partnerService.findByCodeAndType(code,typeEnum);
    }

    @Override
    public Integer insert(Partner partner) {
        return partnerService.insert(partner);
    }

    @Override
    public Boolean update(Partner partner) {
        return partnerService.updatePartner(partner);
    }

    @Override
    public Boolean updateState(Partner partner) {
        return partnerService.updatePartner(partner);
    }

    @Override
    public List<Partner> findByIds(List<Integer> ids) {
        return partnerService.findByIds(ids);
    }
}
