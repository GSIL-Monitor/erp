package com.stosz.product.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.deamon.OldErpCategoryNewDeamon;
import com.stosz.product.deamon.ProductDeamonServiceImpl;
import com.stosz.product.deamon.ProductPushService;
import com.stosz.product.ext.model.ProductSku;
import com.stosz.product.service.AttributeValueService;
import com.stosz.product.service.ProductSkuAttributeValueRelService;
import com.stosz.product.service.ProductSkuService;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author he_guitang
 * @version [1.0 , 2017/11/21]
 *          产品中心调度手动触发机制
 *          -查重邮件
 *          -预警邮件
 *          -消档邮件
 */
@Controller
@RequestMapping(value = "/product/deamon")
public class ProductDeamonController extends AbstractController {

    @Resource
    private ProductDeamonServiceImpl productDeamonService;
    @Resource
    private ProductSkuService productSkuService;
    @Resource
    private ProductPushService productPushService;
    @Resource
    private OldErpCategoryNewDeamon oldErpCategoryNewDeamon;

    /**
     * 排重报表重发
     */
    @RequestMapping(value = "/checkEmail", method = RequestMethod.POST)
    @ResponseBody
    public RestResult checkEmail(String startTime, String endTime) {
        RestResult result = new RestResult();
        Assert.isTrue(endTime != null && !"".equals(endTime), "请输入要查询的排重报表的当天时间");
        String[] endTimeArr = endTime.split(",");
        List<Integer> endTimeList = new ArrayList<>();
        List<Integer> startTimeList = new ArrayList<>();
        if (startTime != null && !"".equals(startTime)) {
            String[] startTimeArr = startTime.split(",");
            for (int i = 0; i < startTimeArr.length; i++) {
                startTimeList.add(Integer.valueOf(startTimeArr[i].trim()));
            }
            Assert.isTrue(startTimeList.size() == 3, "输入的开始时间数字只需要年月日,[,分割]");
        }
        for (int i = 0; i < endTimeArr.length; i++) {
            endTimeList.add(Integer.valueOf(endTimeArr[i].trim()));
        }
        Assert.isTrue(endTimeList.size() == 3, "输入的结束时间数字只需要年月日,[,分割]");
        productDeamonService.productNewReportManual(startTimeList, endTimeList);
        result.setCode(RestResult.OK);
        result.setDesc("排重报表重发成功");
        return result;
    }

    /**
     * 五天预警邮件重发
     */
    @RequestMapping(value = "/warningEmail", method = RequestMethod.POST)
    @ResponseBody
    public RestResult warningEmail() {
        RestResult result = new RestResult();
        productDeamonService.warningTask();
        result.setCode(RestResult.OK);
        result.setDesc("五天未出单预警邮件重发成功");
        return result;
    }

    /**
     * 七天未出单产品待下架
     */
    @RequestMapping(value = "/doWaitoffsaleEmail", method = RequestMethod.POST)
    @ResponseBody
    public RestResult doWaitoffsaleEmail() {
        RestResult result = new RestResult();
        productDeamonService.doWaitoffsaleTask();
        result.setCode(RestResult.OK);
        result.setDesc("七天未出单产品待下架手动执行成功");
        return result;
    }

    /**
     * 待下架消档
     */
    @RequestMapping(value = "/disappearedEmail", method = RequestMethod.POST)
    @ResponseBody
    public RestResult disappearedEmail() {
        RestResult result = new RestResult();
        productDeamonService.disppearedTask();
        result.setCode(RestResult.OK);
        result.setDesc("待下架消档/下架手动执行成功");
        return result;
    }

    /**
     * 三天未备案自动消档重发
     */
    @RequestMapping(value = "/noArchiveEmail", method = RequestMethod.POST)
    @ResponseBody
    public RestResult noArchiveEmail() {
        RestResult result = new RestResult();
        productDeamonService.noArchiveTask();
        result.setCode(RestResult.OK);
        result.setDesc("三天未备案自动消档手动执行成功");
        return result;
    }

    /**
     * 产品sku表属性值title更新
     */
    @RequestMapping(value = "/updateAttrValTitle", method = RequestMethod.POST)
    @ResponseBody
    public RestResult updateAttrValTitle() {
        RestResult result = new RestResult();
        while (true) {
            int count = productSkuService.countSum();
            if (count > 0) {
                try {
                    List<ProductSku> skuList = productSkuService.skuList(2000);
                    for (ProductSku sku : skuList) {
                        String skuTitle = productSkuService.getAttValTitle(sku.getSku());
                        if (skuTitle == null) {
                            skuTitle = "";
                        }
                        productSkuService.updateAttrValTitle(skuTitle, sku.getId());
                    }
                } catch (Exception e) {

                }
            } else {
                break;
            }
        }
        result.setCode(RestResult.OK);
        result.setDesc("产品sku表属性值title更新成功!");
        return result;
    }

    /**
     * 产品推送失败,重新推送接口
     */
    @RequestMapping("/againPushProduct")
    @ResponseBody
    public RestResult againPushProduct(String productIds) {
        RestResult result = new RestResult();
        Assert.isTrue(productIds != null && !"".equals(productIds), "产品id参数有误,请重新输入!");
        String[] ids = productIds.split(",");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            try {
                productPushService.pushProductThing(Integer.valueOf(ids[i].trim()));
            } catch (Exception e) {
                list.add(ids[i]);
            }
        }
        if (list.size() == 0) {
            result.setCode(RestResult.NOTICE);
            result.setDesc("产品重新推送成功!");
        } else {
            result.setCode(RestResult.FAIL);
            result.setDesc("产品重新推送成功失败的产品ID为[" + list + "] !");
        }
        return result;
    }

    /**
     * 产品SKU推送失败,重新推送接口
     */
    @RequestMapping("/againPushProductSku")
    @ResponseBody
    public RestResult againPushProductSku(String productIds) {
        RestResult result = new RestResult();
        Assert.isTrue(productIds != null && !"".equals(productIds), " 产品id参数有误,请重新输入!");
        String[] ids = productIds.split(",");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            try {
                productPushService.pushSkuThing(Integer.valueOf(ids[i].trim()));
            } catch (Exception e) {
                list.add(ids[i]);
            }
        }
        if (list.size() == 0) {
            result.setCode(RestResult.NOTICE);
            result.setDesc("产品SKU重新推送成功!");
        } else {
            result.setCode(RestResult.FAIL);
            result.setDesc("产品SKU重新推送成功失败的产品ID为[" + list + "]!");
        }
        return result;
    }

    /**
     * 老ERP品类同步
     */
    @RequestMapping("/oldErpCategoryNewSync")
    @ResponseBody
    public RestResult oldErpCategoryNewSync(){
        RestResult result = new RestResult();
        try {
            oldErpCategoryNewDeamon.oldErpCategoryNewTask();
        }catch (Exception e){
            Assert.isTrue(false, "同步老ERP品类表失败,主要异常信息["+e.getMessage()+"],详细信息请联系管理员查看日志!");
        }
        result.setCode(RestResult.NOTICE);
        result.setDesc("老ERP品类同步成功!");
        return result;
    }

}
