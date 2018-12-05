package com.stosz.product.web;

import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.model.Country;
import com.stosz.product.service.CountryService;
import com.stosz.product.service.ProductZoneService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * 国家设置Controller
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Controller
@RequestMapping(value = "/product/base/country")
public class CountryController extends AbstractController {

	@Autowired
	private CountryService countryService;

	@Resource
	private ProductZoneService productZoneService;

	/**
	 * 国家添加
	 * 
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public RestResult add(Country country) {
		RestResult result = new RestResult();
		Assert.notNull(country, "增加失败,参数错误");
		MBox.assertLenth(country.getName(), "国家名称", 2, 100);
		MBox.assertLenth(country.getEname(), "英文名称", 100);
		MBox.assertLenth(country.getWordCode(), "国家二字码", 2);
		if(country.getCountryCode() != null && !"".equals(country.getCountryCode())){
			Assert.isTrue(country.getCountryCode().matches("[A-Z]{1,3}"), "国家三字码需要全大写,最长为3");
		}
		if(!"".equals(country.getCurrencyCode()) && country.getCurrencyCode() != null){
			Assert.isTrue(country.getCurrencyCode().matches("[A-Z]{1,3}"), "币种三字码需要全大写,最长为3");
		}
		countryService.addCountry(country);
		result.setCode(RestResult.NOTICE);
		result.setDesc("添加成功");
		return result;
	}

	/**
	 * 国家删除
	 * 
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public RestResult delete(@Param("countryId") Integer id) {
		RestResult result = new RestResult();
		countryService.deleteCountry(id);
		result.setCode(RestResult.NOTICE);
		result.setDesc("删除成功");
		return result;
	}

	/**
	 * 国家修改
	 * 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public RestResult update(Country country) {
		RestResult result = new RestResult();
		Assert.notNull(country, "修改失败,参数错误");
		Assert.notNull(country.getId(), "修改失败,请先选定要修改的项");
		MBox.assertLenth(country.getName(), "国家名称", 2, 100);
		MBox.assertLenth(country.getEname(), "英文名称", 100);
		MBox.assertLenth(country.getWordCode(), "国家二字码", 2);
		if(country.getCountryCode() != null && !"".equals(country.getCountryCode())){
			Assert.isTrue(country.getCountryCode().matches("[A-Z]{1,3}"), "国家三字码需要全大写,最长为3");
		}
		if(!"".equals(country.getCurrencyCode()) && country.getCurrencyCode() != null){
			Assert.isTrue(country.getCurrencyCode().matches("[A-Z]{1,3}"), "币种三字码需要全大写,最长为3");
		}
		//修改
		countryService.updateCountry(country);
		result.setCode(RestResult.NOTICE);
		result.setDesc("修改成功");
		return result;
	}

	/**
	 * 国家列表显示
	 * 
	 */
	@RequestMapping(value = "/find")
	@ResponseBody
	public RestResult find(Country country) {
		return countryService.findCountry(country);
	}

	/**
	 * 国家下拉组件
	 *
	 */
	@RequestMapping(value = "/findList")
	@ResponseBody
	public RestResult findList(Country country) {
		return countryService.findListCountry(country);
	}
	
	/**
	 * 国家名模糊搜索
	 * 
	 */
	@RequestMapping(value = "/likeName")
	@ResponseBody
	public RestResult likeName(@RequestParam String name) {
		return countryService.likeName(name);
	}



	/**
	 * 国家区域查询
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public RestResult list(){
		RestResult result = new RestResult();
		List<Country> list = countryService.findAll();
		result.setItem(list);
		result.setDesc("国家列表查询成功");
		return result;
	}


}
