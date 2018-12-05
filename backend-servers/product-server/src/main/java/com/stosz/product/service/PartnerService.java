package com.stosz.product.service;

import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.utils.CollectionUtils;
import com.stosz.plat.utils.RandomUtil;
import com.stosz.product.ext.enums.TypeEnum;
import com.stosz.product.ext.model.Partner;
import com.stosz.product.mapper.PartnerMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 合作伙伴Service
 *
 * @author minxiaolong
 * @create 2017-12-29 10:26
 **/
@Service
public class PartnerService {

    @Resource
    private PartnerMapper partnerMapper;

    public RestResult add(Partner partner){
        RestResult  result = new RestResult();
        partner.setNo(RandomUtil.getCharacterAndNumber(32));
        partner.setCreateAt(LocalDateTime.now());
        partner.setUpdateAt(LocalDateTime.now());
        partner.setCreator(ThreadLocalUtils.getUser().getLastName());
        partner.setCreatorId(MBox.getLoginUserId());
        partnerMapper.insertSelective(partner);
        result.setCode(RestResult.NOTICE);
        result.setDesc("添加成功");
        return result;
    }



    public  RestResult delete(Integer id){
        RestResult  result = new RestResult();
        partnerMapper.delete(id);
        result.setCode(RestResult.NOTICE);
        result.setDesc("删除成功");
        return result;
    }

    public RestResult update(Partner partner){
        RestResult  result = new RestResult();
        partner.setUpdateAt(LocalDateTime.now());
        partnerMapper.updateSelective(partner);
        result.setCode(RestResult.NOTICE);
        result.setDesc("修改成功");
        return result;
    }

    public RestResult find(Partner partner){
        RestResult  result = new RestResult();
        List<Partner> list = partnerMapper.find(partner);
        int count = partnerMapper.count(partner);
        result.setItem(list);
        result.setTotal(count);
        result.setCode(RestResult.OK);
        result.setDesc("合作人列表");
        return result;
    }

    public RestResult getById(Integer id){
        RestResult  result = new RestResult();
        Partner partner = partnerMapper.getById(id);
        result.setItem(partner);
        result.setCode(RestResult.OK);
        result.setDesc("合作伙伴详细");
        return  result;
    }

    public Partner getPartnerById(Integer id){
        return partnerMapper.getById(id);
    }

    public List<Partner> findPartnerByType(TypeEnum type){
        Partner partner = new Partner();
        partner.setTypeEnum(type);
        List<Partner> list = partnerMapper.find(partner);
        return list;
    }

    public List<Partner> findNameBySearch(String search,TypeEnum type){
        Partner partner = new Partner();
        partner.setTypeEnum(type);
        partner.setName(search);
        return partnerMapper.find(partner);
    }

    public Partner  findByCodeAndType(String code, TypeEnum typeEnum){
        Partner partner = new Partner();
        partner.setTypeEnum(typeEnum);
        partner.setNo(code);
        List<Partner> list = partnerMapper.find(partner);
        return list.get(0);
    }

    public Integer insert(Partner partner) {
        partner.setCreatorId(MBox.getLoginUserId());
        int state = partnerMapper.insertSelective(partner);
        if(state > 0){
            return partner.getId();
        }
        return 0;
    }

    public Boolean updatePartner(Partner partner) {
        partner.setUpdateAt(LocalDateTime.now());
        int state = partnerMapper.updateSelective(partner);
        if(state > 0){
            return true;
        }
        return false;
    }

    public List<Partner> findByIds(List<Integer> ids){
        Assert.isTrue(CollectionUtils.isNotNullAndEmpty(ids),"传入的id集合不允许为空！");
        return partnerMapper.findByIds(ids);
    }
}
