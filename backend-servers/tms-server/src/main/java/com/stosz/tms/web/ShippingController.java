package com.stosz.tms.web;

import com.google.common.primitives.Ints;
import com.google.errorprone.annotations.RestrictedApi;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.web.AbstractController;
import com.stosz.tms.enums.HandlerTypeEnum;
import com.stosz.tms.model.Shipping;
import com.stosz.tms.service.ShippingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tms/shipping")
public class ShippingController extends AbstractController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private ShippingService shippingService;

	@RequestMapping(method = RequestMethod.GET)
	public RestResult selectShippingList(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "page", required = true, defaultValue = "1") Integer page,
			@RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit) {

		RestResult restResult = new RestResult();
		restResult.setCode(RestResult.OK);

		int start = (page == null || page <= 0) ? 0 : (page - 1) * limit;
		limit = (limit == null) ? 10 : limit;

		Shipping shipping = new Shipping();

		if (name != null)
			shipping.setShippingName(name);

		int shippingCount = shippingService.getShippingCount(shipping);
		restResult.setTotal(shippingCount);
		if (shippingCount == 0) {
			return restResult;
		}

		List<Shipping> shippingList = shippingService.getShippingList(name, start, limit);
		restResult.setDesc("查询成功");
		restResult.setItem(shippingList);
		return restResult;

	}

	/**
	 * 添加物流商
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public RestResult addShipping(Shipping shipping) {
		Assert.notNull(shipping.getShippingName(), "物流商名称不能为空!");
		Assert.notNull(shipping.getShippingCode(), "物流商代码不能为空!");

		UserDto user = ThreadLocalUtils.getUser();
		shipping.setCreator(user.getLastName());
		shipping.setCreatorId(user.getId());

		RestResult restResult = new RestResult();

		shippingService.addShipping(shipping);
		restResult.setCode(RestResult.NOTICE);
		restResult.setDesc("新增物流商成功");

		return restResult;
	}

	/**
	 * 修改物流商
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "edit")
	public RestResult editShipping(@ModelAttribute Shipping shipping) {

		Assert.notNull(shipping.getId(), "id不能为空!");
		Assert.notNull(shipping.getShippingName(), "商户名称不能为空!");

		UserDto user = ThreadLocalUtils.getUser();
		shipping.setModifier(user.getLastName());
		shipping.setModifierId(user.getId());

		shippingService.editShipping(shipping);

		RestResult restResult = new RestResult();
		restResult.setCode(RestResult.NOTICE);
		restResult.setDesc("更新物流商成功");
		return restResult;
	}

	/**
	 *  获取所有的shippingCode列表
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "codeList")
	public RestResult getShippingCodeList() {
		HandlerTypeEnum[] values = HandlerTypeEnum.values();

		List<Map<String, Object>> codeMapList = new ArrayList<>();

		for (HandlerTypeEnum typeEnum : values) {
			if (typeEnum.isVisible()) {
				Map<String, Object> codeMap = new HashMap<>();
				codeMap.put("code", typeEnum.code());
				codeMap.put("name", typeEnum.display());
				codeMapList.add(codeMap);
			}
		}
		RestResult restResult = new RestResult();
		restResult.setCode(RestResult.OK);
		restResult.setItem(codeMapList);
		return restResult;
	}

}
