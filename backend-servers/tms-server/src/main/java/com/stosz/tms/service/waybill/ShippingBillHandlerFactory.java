package com.stosz.tms.service.waybill;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ShippingBillHandlerFactory implements InitializingBean {

    //  ailasCode ,根据排序的面单处理器列表
    private Map<String,List<ShippingBillHandler>> shippingBillHandlerMap;

    @Autowired
    private List<AbstractShippingBillHandler> shippingBaillHandlers;


    @Override
    public void afterPropertiesSet() throws Exception {
        shippingBillHandlerMap = new HashMap<>();

        final Map<String, List<AbstractShippingBillHandler>> ShippingBaillHandlerMapByAilasCode
                = shippingBaillHandlers.stream().collect(Collectors.groupingBy(AbstractShippingBillHandler::ailasCode));

        ShippingBaillHandlerMapByAilasCode.forEach((k,v) -> {
            List<ShippingBillHandler> shippingBillHandlers = Lists.newArrayList();

            v.stream().collect(Collectors.groupingBy(AbstractShippingBillHandler::handlerOrder))
                    .forEach((order,handlers) -> {
                        if (handlers.size() > 1)
                            throw new RuntimeException(String.format("ailasCode为:%s的处理器中,排序为:%s的值出现多个",k,order));
                    });

            v.stream().sorted((e1,e2) -> e1.handlerOrder() - e2.handlerOrder())
                    .forEach(e -> {
                        shippingBillHandlers.add(e);
                    });

            shippingBillHandlerMap.put(k,shippingBillHandlers);
        });
    }

    public List<ShippingBillHandler> getHandler(String ailasCode) {
        return shippingBillHandlerMap.get(ailasCode);
    }
}
