package com.stosz.product.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.model.ProductNewZone;
import com.stosz.product.service.ProductNewZoneService;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.util.List;

/**
 * 新品区域设置Controller
 * @since 2017-08-17
 * @author oxq 
 */
@Controller
@RequestMapping(value = "/product/productNewZone")
public class ProductNewZoneController extends AbstractController {
	
	@Resource
	private ProductNewZoneService productNewZoneService;
	
	
    /**
     * 查看或者编辑调用方法(点击区域设置调用)
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
	public RestResult get(@RequestParam(required=true) Integer productNewId) {
		Assert.notNull(productNewId,"productNewId新品Id不允许为空");
		RestResult result = new RestResult();
		List<ProductNewZone> countries = productNewZoneService.findByProductNewId(productNewId);
		result.setItem(countries);
		return result;
	}
	
    /**
     * 新增调用方法
     * @return
     */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public RestResult add(@RequestParam Integer productNewId, @RequestParam Integer zoneId) {
		RestResult result = new RestResult();

		productNewZoneService.insert(productNewId,zoneId);
		result.setCode(RestResult.NOTICE);
    	result.setDesc("新增成功!");
        return result;
	}
	
	
	/**
	 * 删除调用方法
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public RestResult delete(ProductNewZone country, RedirectAttributes redirectAttributes) {
		RestResult result = new RestResult();
    	Assert.notNull(country,"删除失败,传入参数异常!");
        Assert.notNull(country.getId(),"id不能为空,删除失败!");
		productNewZoneService.delete(country.getId());
		result.setCode(RestResult.NOTICE);
    	result.setDesc("删除成功!");
        return result;
	}

}
