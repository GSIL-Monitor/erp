package com.stosz.product.web;

import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.constant.MenuKeyword;
import com.stosz.product.ext.model.Zone;
import com.stosz.product.service.ZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * 区域controller
 * 
 * @author  he_guitang
 * @version  [版本号, 1.0]
 */
@RequestMapping("/product/base/zone")
@Controller
public class ZoneController extends AbstractController {
	
	@Autowired
	private ZoneService service;
	
	/**
	 * 区域页面
	 */
	@RequestMapping(value = "")
	public ModelAndView index(){
		ModelAndView model = new ModelAndView("/pc/base/zone");
		model.addObject("keyword", MenuKeyword.ZONE_SETTING);
		return model;
	}
	
	/**
	 * 区域增加
	 */
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public RestResult add(Zone zone){
		RestResult result = new RestResult();
		Assert.notNull(zone, "添加失败,参数有误");
		Assert.notNull(zone.getCountryId(), "区域所属的国家不能为空");
		MBox.assertLenth(zone.getCode(), "区域编码", 1, 20);
		MBox.assertLenth(zone.getTitle(), "区域名称", 2, 50);
		MBox.assertLenth(zone.getCurrency(), "币种", 1, 20);
		MBox.assertLenth(zone.getSort(), "排序", 10);
		service.insert(zone);
		result.setCode("区域添加成功");
		result.setCode(RestResult.NOTICE);
		return result;
	}
	
	/**
	 * 区域删除
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@ResponseBody
	public RestResult add(@RequestParam Integer id){
		RestResult result = new RestResult();
		service.delete(id);
		result.setDesc("区域删除成功");
		result.setCode(RestResult.NOTICE);
		return result;
	}
	
	/**
	 * 区域修改
	 */
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	@ResponseBody
	public RestResult update(Zone zone){
		RestResult result = new RestResult();
		Assert.notNull(zone, "添加失败,参数有误");
		Assert.notNull(zone.getId(),"请先选择要修改的区域");
//		Assert.notNull(zone.getParentId(), "区域所属国家不能为空");
//		MBox.assertLenth(zone.getParentId(), "父id",10);
		Assert.notNull(zone.getCountryId(), "区域所属的国家不能为空");
		MBox.assertLenth(zone.getCode(), "区域编码", 1, 20);
		MBox.assertLenth(zone.getTitle(), "区域名称", 2, 50);
		MBox.assertLenth(zone.getCurrency(), "币种", 1, 20);
		service.update(zone);
		result.setDesc("区域修改成功");
		result.setCode(RestResult.NOTICE);
		return result;
	}
	
	/**
	 * 区域单条查询
	 */
	@RequestMapping(value = "/get")
	@ResponseBody
	public RestResult get(@RequestParam Integer id){
		RestResult result = new RestResult();
		Zone zone = service.getById(id);
		result.setItem(zone);
		result.setDesc("区域查询成功");
		return result;
	}
	
	/**
	 * 区域单条查询
	 */
	@RequestMapping(value = "/titleLike")
	@ResponseBody
	public RestResult titleLike(@RequestParam String title){
		return service.titleLike(title);
	}
	
	/**
	 * 区域列表查询
	 */
	@RequestMapping(value = "/find")
	@ResponseBody
	public RestResult find(Zone zone){
		return service.find(zone);
	}
	
	/**
	 * 新品区域列表查询
	 */
	@RequestMapping(value = "/findStintZone")
	@ResponseBody
	public RestResult findStintZone(){
		RestResult result = new RestResult();
		List<Zone> list = service.findStintZone();
		result.setCode(RestResult.OK);
		result.setItem(list);
		result.setDesc("部门区域查询成功!");
		return result;
	}
	
	/**
	 * 父ID列表查询
	 */
	@RequestMapping(value = "/findByParentId")
	@ResponseBody
	public RestResult findByParentId(@RequestParam Integer parentId){
		RestResult result = new RestResult();
		List<Zone> list = service.findByParentId(parentId);
		result.setItem(list);
		result.setDesc("区域上级列表查询成功");
		return result;
	}
	
	/**
	 * 查询国家下面的区域
	 */
	@RequestMapping(value = "/findByCountryId")
	@ResponseBody
	public RestResult findByCountryId(@RequestParam Integer countryId){
		RestResult result = new RestResult();
		List<Zone> list = service.findByCountryId(countryId);
		result.setItem(list);
		result.setDesc("国家区域列表查询成功");
		return result;
	}

	/**
	 * 国家区域查询
	 */
	@RequestMapping(value = "/findAll")
	@ResponseBody
	public RestResult findAll(){
		RestResult result = new RestResult();
		List<Zone> list = service.findAll();
		result.setItem(list);
		result.setDesc("国家区域列表查询成功");
		return result;
	}


	/**
	 * 国家区域查询
	 */
	@RequestMapping(value = "/list")
	@ResponseBody
	public RestResult list(){
		return findAll();
	}
	
	
}
