package com.stosz.store.service;

import com.stosz.BaseTest;
import com.stosz.plat.utils.JsonUtil;
import com.stosz.store.ext.dto.request.WmsTransferReq;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.stosz.plat.utils.JsonUtils.jsonToObject;


/**
 * @Author:yangqinghui
 * @Description:
 * @Created Time:0x0A
 * @Update Time:2017/11/28 20:08
 */
public class UtilTest extends BaseTest{
    public static void main(String[] args) {
        /*String trackingNo = "1\n5\n0\n-1\n-1\n0\n";
        logger.info(trackingNo.contains("\t"));
        logger.info(trackingNo.contains("\n"));
        String[] nos = trackingNo.split("\r\n");
        String uri = "http://192.168.103.140:8080/wms/api/transfer_in_stock";


        String resultData = "{\"transferNo\":\"YQH201712011\",\"type\":\"2\",\"outWarehouseId\":\"TG\",\"inWarehouseId\":\"Y\",\"shippingName\":\"\",\"shippingNo\":\"\",\"outTime\":\"2017-12-01 13:57:45\",\"remark\":\"\",\"data\":[{\"sku\":\"S009906500028\",\"transferQty\":\"1\",\"outSkuReceived\":\"1\"}]}";
        WmsTransferReq wmsTransferReq = new WmsTransferReq();
        WmsTransferReq wmsTransferReq2 = jsonToObject(resultData, wmsTransferReq.getClass());
        WmsTransferReq wmsTransferReq1 = JsonUtil.readValue(resultData, wmsTransferReq.getClass());
        logger.info(wmsTransferReq1);
        logger.info(wmsTransferReq2);*/

   /*     LocalDateTime localDateTime = LocalDateTime.now().plusMonths(1);

        int month = LocalDateTime.now().getMonth().getValue();
        int year = LocalDateTime.now().getYear();
        BigDecimal amount=new BigDecimal(10);
        BigDecimal divide = amount.subtract(new BigDecimal(3)).negate();
        logger.info(LocalDateTime.of(2018,3,1,0,0).minusSeconds(1));
   */
        List<Map<String,String>> strs = new ArrayList<>();
        Map<String,String> map=new HashMap<>();
        map.put("1","1");
        map.put("2","4");
        map.put("3","1");
        map.put("4","1");
        strs.add(map);
//        logger.info(strs);

    }
}
