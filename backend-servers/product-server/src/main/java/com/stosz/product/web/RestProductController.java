package com.stosz.product.web;

import com.google.common.base.Strings;
import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IUserDepartmentRelService;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.utils.JsonUtil;
import com.stosz.plat.utils.StringUtils;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.model.UserDepartmentRel;
import com.stosz.product.service.ProductService;
import com.stosz.product.service.ProductSkuAsyncService;
import com.stosz.product.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class RestProductController extends AbstractController {


    @Autowired
    private ProductService productService;

    @Resource
    private IUserService iUserService;

    @Resource
    private IUserDepartmentRelService iUserDepartmentRelService;

    @Autowired
    private ZoneService zoneService;
    @Resource
    private ProductSkuAsyncService productSkuAsyncService;

    @Value("${image.host}")
    private String imageURL;

    @GetMapping("/pc/product/{productId}/seoLoginid/{seoLoginid}")
    public RestResult getProductInfoByProductIdAndSeoLoginId(@PathVariable Integer productId, @PathVariable String seoLoginid){

        Assert.notNull(productId,"产品id不允许为空");
        Assert.isTrue(StringUtils.isNotBlank(seoLoginid),"优化师loginid不允许为空");
        Product product = productService.loadById(productId);
        Assert.notNull(product,"找不到"+productId+"对应的产品");


        Assert.isTrue(Arrays.asList("archiving","onsale").contains(product.getState())
                ,"建档中或者已上架的产品才允许拉取产品信息，目前产品状态是："+ product.getStateEnum().display());

        User seoUser = iUserService.getByLoginid(seoLoginid);
        Assert.notNull(seoUser, "请求参数中优化师loginid:" + seoLoginid + "在数据库中不存在!");
        /////////////////////////////////////////////参数校验

        productService.wraperFullInfoByProductAndLoginId(product, seoUser);

        RestResult restResult = new RestResult();
        ////////////////////////////////如果没有可以操作的产品区域，直接返回提示信息；add by carter 20171107
        if(null == product.getProductZoneList() || product.getProductZoneList().isEmpty())
        {
            restResult.setCode(RestResult.FAIL);
            restResult.setDesc("该产品没有可以建站的区域,请确认已经分配好了可以投放的产品区域");
            return restResult;
        }
        wrapperMainImageUrl(product);
        //建站拉取产品的时候,批量生成sku
        productSkuAsyncService.asyncGenerateAllSku(productId);

        restResult.setItem(product);
        return restResult;
    }


    @GetMapping(value = "/pc/archive/product/{productId}/loginid/{operatorLoginid}")
    public RestResult archive(@PathVariable Integer productId,
                              @PathVariable String operatorLoginid,
                              @RequestParam Integer zoneId,
                              @RequestParam String webDirectory,
                              @RequestParam String domain,
                              @RequestParam String siteProductId,
                              @RequestParam String seoLoginId,
                              HttpServletRequest request
    ){

        RestResult restResult = new RestResult();
        productService.frontEndArchive(productId,seoLoginId,zoneId,webDirectory,domain,siteProductId,operatorLoginid);
        Product product = productService.loadById(productId);
        wrapperMainImageUrl(product);
        restResult.setItem(product);

        logger.debug("保存建站记录请求参数:"+request.getQueryString()+" \n保存建站记录结果:"+ JsonUtil.toJson(restResult));

        return restResult;
    }

    /**
     * 把产品图片的url处理成绝对路径的
     * @param product
     */
    private void wrapperMainImageUrl(Product product) {
        if(null!=product &&!Strings.isNullOrEmpty(product.getMainImageUrl())&& !product.getMainImageUrl().startsWith("http"))
            product.setMainImageUrl(imageURL+product.getMainImageUrl());
    }

    @GetMapping(value="/pc/getAdUserList/loginid/{loginid}")
    public RestResult getAdUserList(@PathVariable String loginid, @RequestParam(defaultValue = "") String q){
        User currentUser = iUserService.getByLoginid(loginid);

        Assert.notNull("登录id：" + loginid + "在数据库中没有找到！");

        List<UserDepartmentRel> rel= iUserDepartmentRelService.findByUserId(currentUser.getId() , true);


        List<String> userDepartmentNos = rel.stream().map(UserDepartmentRel::getDepartmentNo).collect(Collectors.toList());
        userDepartmentNos.add(currentUser.getDepartmentNo());

        List<User> users =  iUserService.findByDepartmentNos(userDepartmentNos , q);

        RestResult rr = new RestResult();
        rr.setItem(users);
        return rr;
    }


    @GetMapping(value="/zone/list")
    public RestResult getZoneList(){
        RestResult rr = new RestResult();
        rr.setItem(zoneService.findAll());
        return rr;
    }


}
