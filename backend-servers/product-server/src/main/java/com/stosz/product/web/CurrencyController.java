package com.stosz.product.web;

import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.constant.MenuKeyword;
import com.stosz.product.ext.model.Currency;
import com.stosz.product.ext.model.CurrencyLog;
import com.stosz.product.service.CurrencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;

/**
 * 币种汇率设置Controller
 * 
 * @author he_guitang
 * @version [版本号, 1.0]
 */
@Controller
@RequestMapping("/product/base/currency")
public class CurrencyController extends AbstractController {

	@Autowired
	private CurrencyService currencyService;

	/**
	 * 币种页面
	 * 
	 */
	@RequestMapping(value = "")
	@ResponseBody
	public ModelAndView asList() {
		ModelAndView model = new ModelAndView("pc/base/currency");
		model.addObject("keyword", MenuKeyword.CURRENCY_SETTING);
		return model;
	}

	/**
	 * 币种增加
	 * 
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public RestResult add(Currency currency) {
		RestResult result = new RestResult();
		Assert.notNull(currency, "增加失败,参数错误");
		Assert.notNull(currency.getCurrencyCode(), "币种编码不能为空！");
		Assert.isTrue(currency.getCurrencyCode().matches("[A-Z]{3}"), "币种编码是3个大写字母");
		Assert.notNull(currency.getRateCny(), "到人民币汇率不能为空！");
		Assert.isTrue(currency.getRateCny().toString().matches("^([1-9]\\d{1,3}|\\d)(\\.\\d{1,6})?$"), "到人民币汇率整数最大4位,精确度最大6位");
		Assert.isTrue(currency.getRateCny().compareTo(BigDecimal.ZERO) != 0, "到人民币汇率不能为0");
		if ("".equals(currency.getSymbolLeft().trim())){
			if ("".equals(currency.getSymbolRight().trim())){
				result.setCode(RestResult.FAIL);
				result.setDesc("左货币/右货币有且只能有一个!");
				return result;
			}
		}else{
			if (!"".equals(currency.getSymbolRight().trim())){
				result.setCode(RestResult.FAIL);
				result.setDesc("左货币/右货币有且只能有一个!");
				return result;
			}
		}
		MBox.assertLenth(currency.getSymbolLeft(), "左货币符号", 10);
		MBox.assertLenth(currency.getSymbolRight(), "右货币符号", 10);
		MBox.assertLenth(currency.getName(), "币种名称", 2, 100);
		MBox.assertLenth(currency.getSymbol(), "币种符号", 1);
		// 汇率历史表信息
		UserDto dto = ThreadLocalUtils.getUser();
		Assert.notNull(dto, "获取用户登录信息失败,请登录!");
		CurrencyLog currencyLog = new CurrencyLog();
		currencyLog.setName(currency.getName());
		currencyLog.setCurrencyCode(currency.getCurrencyCode());
		currencyLog.setRateCny(currency.getRateCny());
		currencyLog.setCreatorId(dto.getId());
		currencyLog.setCreator(dto.getLastName());
		currencyService.addCurrency(currency, currencyLog);
		result.setCode(RestResult.NOTICE);
		result.setDesc("添加成功");
		return result;
	}
	
	/**
	 * 币种删除
	 * 
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public RestResult delete(@RequestParam Integer id) {
		RestResult result = new RestResult();
		currencyService.delete(id);
		result.setDesc("币种删除成功");
		result.setCode(RestResult.NOTICE);
		return result;
	}

	/**
	 * 币种修改 修改汇率
	 * 
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public RestResult update(Currency currency) {
		RestResult result = new RestResult();
		Assert.notNull(currency, "修改失败,参数错误");
		Assert.notNull(currency.getId(), "请先选择要修改的币种");
		Assert.isTrue(currency.getCurrencyCode().matches("[A-Z]{3}"), "币种编码是3个大写字母");
		Assert.isTrue(currency.getRateCny().toString().matches("^([1-9]\\d{1,3}|\\d)(\\.\\d{1,6})?$"), "到人民币汇率整数最大4位,精确度最大6位");
		Assert.isTrue(currency.getRateCny().compareTo(BigDecimal.ZERO) != 0, "到人民币汇率不能为0");
		Assert.notNull(currency.getUsable(), "状态错误");
		if ("".equals(currency.getSymbolLeft().trim())){
			if ("".equals(currency.getSymbolRight().trim())){
				result.setCode(RestResult.FAIL);
				result.setDesc("左货币/右货币有且只能有一个!");
				return result;
			}
		}else{
			if (!"".equals(currency.getSymbolRight().trim())){
				result.setCode(RestResult.FAIL);
				result.setDesc("左货币/右货币有且只能有一个!");
				return result;
			}
		}
		MBox.assertLenth(currency.getName(), "币种名称", 2, 100);
		MBox.assertLenth(currency.getSymbol(), "币种符号", 1);
		UserDto dto = ThreadLocalUtils.getUser();
		Assert.notNull(dto, "获取用户登录信息失败,请登录!");
		// 汇率历史表信息
		CurrencyLog currencyLog = new CurrencyLog();
		currencyLog.setName(currency.getName());
		currencyLog.setCurrencyCode(currency.getCurrencyCode());
		currencyLog.setRateCny(currency.getRateCny());
		currencyLog.setCreatorId(dto.getId());
		currencyLog.setCreator(dto.getLastName());
		currencyService.updateCurrency(currency, currencyLog);
		result.setCode(RestResult.NOTICE);
		result.setDesc("修改成功");
		return result;
	}

	/**
	 * 币种列表
	 * 
	 */
	@RequestMapping(value = "/find")
	@ResponseBody
	public RestResult find(Currency currency) {
		return currencyService.findCurrency(currency);
	}

	/**
	 * 币种列表
	 *
	 */
	@RequestMapping(value = "/findAll")
	@ResponseBody
	public RestResult findAll(Currency param) {
		return currencyService.findAll(param);
	}

	/**
	 * 汇率历史(单条汇率的历史)
	 * 
	 */
	@RequestMapping(value = "/history")
	@ResponseBody
	public RestResult history(Currency currency) {
		RestResult result = new RestResult();
		Assert.notNull(currency, "查询失败,参数有误");
		Assert.notNull(currency.getCurrencyCode(), "请先输入要查询的历史");
		List<CurrencyLog> hisList = currencyService.historyCurrency(currency);
		result.setItem(hisList);
		result.setDesc("查询成功");
		return result;
	}
	
	
	/**
	 * 汇率历史(所有币种汇率的历史)
	 * 
	 */
	@RequestMapping(value = "/findHistory")
	@ResponseBody
	public RestResult findHistory(CurrencyLog param) {
		return currencyService.findHistory(param);
	}



	/**
	 * 国家区域查询
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public RestResult list(){
		RestResult result = new RestResult();
		List<Currency> list = currencyService.findAll();
		result.setItem(list);
		result.setDesc("货币列表查询成功");
		return result;
	}

}
