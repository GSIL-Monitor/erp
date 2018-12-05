package com.stosz.purchase.service.finance;

import com.stosz.purchase.ext.model.finance.AmountAdjust;
import com.stosz.purchase.mapper.finance.AmountAdjustMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:
 * @Update Time:2017/12/29 15:39
 */
@Service
public class AmountAdjustService {

    @Resource
    private AmountAdjustMapper mapper;

    public void insert(AmountAdjust model) {
        model.setCreateAt(LocalDateTime.now());
        model.setUpdateAt(LocalDateTime.now());
        mapper.insert(model);
    }


    public List<AmountAdjust> query(AmountAdjust adjust) {
        return mapper.query(adjust);
    }

    public int count(AmountAdjust adjust) {
        return mapper.count(adjust);
    }
    public void deleteByPayId(Integer payId){
        mapper.deleteByPayId(payId);
    }

}
