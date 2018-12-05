package com.stosz.tms.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.tms.model.ShippingParcel;
import com.stosz.tms.model.ShippingParcelDetail;
import com.stosz.tms.service.ShippingParcelDetailService;
import com.stosz.tms.vo.ShippingParcelListVo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/tms/shippingParcelDetail")
public class ShippingParcelDetailController extends AbstractController {

    @Resource
    private ShippingParcelDetailService service;

    @RequestMapping(value = "query",method = RequestMethod.POST)
    public RestResult queryList(ShippingParcelDetail shippingParcelDetail){
        RestResult restResult = new RestResult();
        int count = service.count(shippingParcelDetail);
        restResult.setTotal(count);
        if (count <= 0) {
            restResult.setCode(RestResult.OK);
            return restResult;
        }

//        final Integer start = shippingParcelDetail.getStart();
//        final Integer limit = shippingParcelDetail.getLimit();
//
//        //开始位置
//        shippingParcelDetail.setStart((start == null || start <= 0) ? 0 : (start -1)* limit);
//        //需要显示的条数
//        shippingParcelDetail.setLimit((limit == null ) ? 10 : limit);

        final List<ShippingParcelDetail> parcelDetails = service.queryList(shippingParcelDetail);
        restResult.setItem(parcelDetails);
        restResult.setCode(RestResult.OK);
        return restResult;
    }
}
