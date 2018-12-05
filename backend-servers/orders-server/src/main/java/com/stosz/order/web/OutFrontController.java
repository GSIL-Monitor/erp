package com.stosz.order.web;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.stosz.order.ext.model.Orders;
import com.stosz.order.service.OrderService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.CommonUtils;
import com.stosz.plat.web.AbstractController;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @auther carter
 * create time    2017-12-18
 * 对建站的订单对账接口
 */
@Controller()
@RequestMapping("/orders/out/front/")
public class OutFrontController extends AbstractController {


    @Value("${project.rest.secret}")
    private String projectRestSecret;

    @Autowired
    private OrderService orderService;

    /**
     * 文档地址：https://www.showdoc.cc/buguniao?page_id=15716436
     * 订单建站对账接口
     * @param request 请求对象
     * @return
     */
    @PostMapping("checkOrderNos")
    public RestResult  checkOrderNos(HttpServletRequest request)
    {

        RestResult restResult = new RestResult();

        String orderNos = CommonUtils.getStringValue(request.getParameter("orderNos"));
        String sign = CommonUtils.getStringValue(request.getParameter("sign"));


        if(Strings.isNullOrEmpty(orderNos))
        {
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("参数sign缺少");
            return restResult;
        }

        if(Strings.isNullOrEmpty(orderNos))
        {
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("参数orderNos缺少");
            return restResult;
        }


        if (!sign.equalsIgnoreCase(DigestUtils.md5Hex(orderNos+projectRestSecret)))
        {
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("签名错误");
        }
        else
        {

            String[] orderNoArray = orderNos.split("|");
            List<String> merchantOrderNoList = Arrays.asList(orderNoArray);
            List<Orders> orderByMerchantOrderNos = orderService.findOrderByMerchantOrderNos(merchantOrderNoList);

            Map<String,Set<String>> orderNoExistMap  = Maps.newHashMap();

            Set<String> existOrderNoSet = Sets.newHashSet();
            for (Orders orders: orderByMerchantOrderNos)
            {
                existOrderNoSet.add(orders.getMerchantOrderNo());
            }
            orderNoExistMap.put("ok",existOrderNoSet);

            Set<String> notExistOrderNoSet = Sets.newHashSet();
            for(String orderNoNotExist:  merchantOrderNoList)
            {
                if(!existOrderNoSet.contains(orderNoNotExist))
                {
                    notExistOrderNoSet.add(orderNoNotExist);
                }
            }
            orderNoExistMap.put("fail",notExistOrderNoSet);

            restResult.setCode(RestResult.OK);
            restResult.setDesc("查询成功");
            restResult.setItem(orderNoExistMap);

        }

        return restResult;

    }




}
