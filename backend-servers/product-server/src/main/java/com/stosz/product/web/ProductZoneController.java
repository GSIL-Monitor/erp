package com.stosz.product.web;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.deamon.ProductZoneDeamon;
import com.stosz.product.ext.enums.ProductZoneState;
import com.stosz.product.ext.model.ProductZone;
import com.stosz.product.service.ProductZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * 产品区域部门Controller
 * 
 * @author he_guitang
 *
 */
@RequestMapping("/product/productZone")
@Controller
public class ProductZoneController extends AbstractController {
	
	@Autowired
	private ProductZoneService productZoneService;

	@Autowired
	private ProductZoneDeamon productZoneDeamon;

	/**
	 * 产品国家页面跳转
	 * 
	 */
	@RequestMapping(value = "")
	@ResponseBody
	public ModelAndView index() {
		ModelAndView model = new ModelAndView("/pc/product/productZone");
		return model;
	}
	
	/**
	 * 根据产品id查询 区域,部门信息
	 */
	@RequestMapping(value = "/find")
	@ResponseBody
	public RestResult findByProductId(@RequestParam Integer productId) {
		RestResult result = new RestResult();
		List<ProductZone> list = productZoneService.findByProductId(productId);
		result.setItem(list);
		result.setDesc("产品对应的部门区域查询成功");
		return result;
	}
	
	/**
	 * 根据产品id查询 区域信息
	 * 	state为查询非该状态的产品区域
	 */
	@RequestMapping(value = "/queryByProductId")
	@ResponseBody
	public RestResult queryByProductId(@RequestParam Integer productId) {
		return productZoneService.queryByProductId(productId);
	}

	/**
	 *根据产品id查询非消档的产品区域
	 */
	@RequestMapping(value = "/findByProId")
	@ResponseBody
	public RestResult findByProId(@RequestParam Integer productId,@RequestParam Integer productNewId){
		RestResult result = new RestResult();
		List<Integer> ids = new ArrayList<>();
		ids.add(productId);
		List<ProductZone> productZones = productZoneService.findByProductIds(ProductZoneState.disappeared.name(), ids, productNewId);
		result.setDesc("查询产品下非消档的区域成功!");
		result.setItem(productZones);
		return result;
	}


	@RequestMapping(value = "/doDisppearedTask")
	@ResponseBody
	public RestResult doDisppearedTask(){
		productZoneDeamon.doDisppearedTask();
		return new RestResult().setDesc("执行销档调度任务成功!");
	}


}





