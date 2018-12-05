package com.stosz.tms.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.utils.StringUtil;
import com.stosz.plat.web.AbstractController;
import com.stosz.tms.model.ShippingWay;
import com.stosz.tms.service.ShippingWayService;

@RestController
@RequestMapping("/tms/base/shippingway")
public class ShippingWayController extends AbstractController {

	@Autowired
	private ShippingWayService shippingWayService;

	@RequestMapping("addShippingWay")
	@ResponseBody
	public RestResult addShippingWay(  ShippingWay shippingWay) {
//
		UserDto userDto=ThreadLocalUtils.getUser();
		shippingWay.setCreatorId(userDto.getId());
		shippingWay.setCreator(userDto.getLastName());
		shippingWayService.addShippingWay(shippingWay);
		RestResult restResult = new RestResult();
		restResult.setCode(RestResult.NOTICE);
		restResult.setDesc("新增成功");
		return restResult;
	}

	@RequestMapping("query")
	@ResponseBody
	public RestResult query(ShippingWay shippingWay) {
		RestResult restResult = new RestResult();
		int count = shippingWayService.count(shippingWay);
		restResult.setTotal(count);
		if (count <= 0) {
			restResult.setCode(RestResult.OK);
			return restResult;
		}
//		final Integer start = shippingWay.getStart();
//		final Integer limit = shippingWay.getLimit();
//
//		//开始位置
//		shippingWay.setStart((start == null || start <= 0) ? 0 : (start -1)* limit);
//		//需要显示的条数
//		shippingWay.setLimit((limit == null ) ? 10 : limit);

		List<ShippingWay> shippingWays = shippingWayService.queryList(shippingWay);
		restResult.setItem(shippingWays);
		restResult.setCode(RestResult.OK);
		return restResult;
	}

	@RequestMapping(method = RequestMethod.DELETE)
	public RestResult delete(
		@RequestParam(name = "id",required = true) Integer id
	){
		shippingWayService.delete(id);
		RestResult restResult = new RestResult();
		restResult.setCode(RestResult.NOTICE);
		restResult.setDesc("删除物流线路成功");
		return restResult;
	}

	@RequestMapping(value = "/updateState",method = RequestMethod.PUT)
	public RestResult updateState(
			@ModelAttribute ShippingWay shippingWay
	){
		Assert.notNull(shippingWay.getId(),"id不能为空");
		Assert.notNull(shippingWay.getState(),"状态不能为空");
		Assert.isTrue(shippingWay.getState() == 1 || shippingWay.getState() == 2,"非法状态值" );

		UserDto userDto=ThreadLocalUtils.getUser();
		shippingWay.setModifierId(userDto.getId());
		shippingWay.setModifier(userDto.getLastName());

		shippingWayService.updateState(shippingWay);
		RestResult restResult = new RestResult();
		restResult.setCode(RestResult.NOTICE);
		restResult.setDesc("更新物流线路状态成功");
		return restResult;
	}

	@RequestMapping(method = RequestMethod.PUT)
	public RestResult update(ShippingWay shippingWay, HttpServletRequest request) {
		String aliasName = request.getParameter("aliasName");
		int general = StringUtil.strToInt(request.getParameter("general"), -1);
		int specia = StringUtil.strToInt(request.getParameter("specia"), -1);
//		if (shippingWay.getExpressSheetType() == 0) {// 不区分货物类型
//			shippingWay.setAllowedProductType(0);
//			shippingWay.setShippingGeneralName(aliasName);
//		} else if (shippingWay.getExpressSheetType() == 1) {
//			if (general == 1 && specia == 1) {
//				shippingWay.setAllowedProductType(0);
//			} else if (general == 1) {
//				shippingWay.setAllowedProductType(1);
//			} else if (specia == 1) {
//				shippingWay.setAllowedProductType(2);
//			}
//		}

		UserDto userDto=ThreadLocalUtils.getUser();
		shippingWay.setModifierId(userDto.getId());
		shippingWay.setModifier(userDto.getLastName());

		shippingWayService.update(shippingWay);
		RestResult restResult = new RestResult();
		restResult.setCode(RestResult.NOTICE);
		restResult.setDesc("修改成功");
		return restResult;
	}




	/**
	 * 查询所有的物流线路，去除仓库已关联的
	 * @return
	 */
	@RequestMapping(value = "queryExceptStoreExist",method = RequestMethod.GET)
	public RestResult queryExceptStoreExist(
			@RequestParam(name = "storeId",required = true)Integer storeId,
			@RequestParam(name = "type",required = true,defaultValue = "1")Integer type
	){
		List<ShippingWay> shippingWays = shippingWayService.queryExceptStoreExist(storeId,type);
		RestResult restResult = new RestResult();
		restResult.setCode(RestResult.OK);
		restResult.setItem(shippingWays);
		return restResult;
	}


}
