package com.stosz.product.web;

import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.model.ProductLang;
import com.stosz.product.service.ProductLangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 属性语言包
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Controller
@RequestMapping("/product/productLang")
public class ProductLangController extends AbstractController {

	@Autowired
	private ProductLangService productLangService;

	/**
	 * 产品语言包的添加
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public RestResult add(ProductLang param) {
		RestResult result = new RestResult();
		Assert.notNull(param, "保存失败,传入参数异常");
		Assert.notNull(param.getProductId(), "保存失败,未指明产品");
		MBox.assertLenth(param.getName(), "产品语言包名称", 2, 255);
		MBox.assertLenth(param.getLangCode(), "产品语言包编码", 1, 8);
		productLangService.add(param);
		result.setCode(RestResult.NOTICE);
		result.setDesc("添加产品语言包" + param.getName() + "成功");
		return result;
	}

	/**
	 * 产品语言包的删除
	 * 
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public RestResult delete(@RequestParam Integer id) {
		RestResult result = new RestResult();
		productLangService.delete(id);
		result.setDesc("删除品类语言包id:" + id + "成功！");
		result.setCode(RestResult.NOTICE);
		return result;
	}

	/**
	 * 产品语言包的修改
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public RestResult update(ProductLang param) {
		RestResult result = new RestResult();
		Assert.notNull(param.getId(), "请先选择符合修改的徐选项");
		MBox.assertLenth(param.getName(), "产品语言包名称", 2, 255);
		MBox.assertLenth(param.getLangCode(), "产品语言包编码", 1, 8);
		productLangService.update(param);
		result.setCode(RestResult.NOTICE);
		result.setDesc("产品语言包更新成功！");
		return result;
	}

	/**
	 * 产品语言包列表
	 */
	@RequestMapping(value = "/find")
	@ResponseBody
	public RestResult find(@RequestParam Integer productId) {
		RestResult result = new RestResult();
		List<ProductLang> list = productLangService.findProductLang(productId);
		result.setItem(list);
		result.setDesc("产品语言包查询成功");
		return result;
	}

}
