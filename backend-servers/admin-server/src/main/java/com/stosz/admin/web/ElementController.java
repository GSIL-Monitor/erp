package com.stosz.admin.web;

import com.stosz.admin.ext.model.Element;
import com.stosz.admin.ext.model.ElementPermission;
import com.stosz.admin.service.admin.ElementService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.web.AbstractController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author liufeng
 * @version [1.0 , 2017/9/11]
 */
@Controller
@RequestMapping("/admin/element")
public class ElementController extends AbstractController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private ElementService elementService;


    /**
     * 用于跳转到列表页面
     *
     * @return 跳转到页面
     */
    @RequestMapping(method = RequestMethod.GET, value = "")
    @ResponseBody
    public ModelAndView list() {
        return new ModelAndView("/admin/element/element");
    }


    /**
     * 新增元素
     *
     * @param element
     * @return
     */
    @RequestMapping("/saveElement")
    @ResponseBody
    public RestResult saveElement(Element element) {
        RestResult restResult = new RestResult();
        Assert.notNull(element.getName(), "元素名不能为空！");
        Assert.notNull(element.getKeyword(), "元素关键字不能为空！");
        elementService.saveElement(element);
        restResult.setCode(RestResult.NOTICE);
        return restResult;
    }


    /**
     * 新增元素跟菜单的关系
     *
     * @param elementId
     * @param menuKeyword
     * @return
     */
    @RequestMapping("/bindElement")
    @ResponseBody
    public RestResult bindElement(Integer elementId, String menuKeyword) {
        RestResult restResult = new RestResult();
        elementService.bindElement(elementId, menuKeyword);
        restResult.setCode(RestResult.NOTICE);
        return restResult;
    }

    /**
     * 解绑菜单跟元素的映射关系
     * @param menuKeyword
     * @param elementId
     * @return
     */
    @RequestMapping("/unbindElement")
    @ResponseBody
    public RestResult unbindElement(String menuKeyword, Integer elementId) {
        RestResult restResult = new RestResult();
        elementService.unbindElement(elementId, menuKeyword);
        restResult.setCode(RestResult.NOTICE);
        return restResult;
    }


    /**
     * 删除元素
     *
     * @param id
     * @return
     */
    @RequestMapping("/deleteElement")
    @ResponseBody
    public RestResult deleteElement(@RequestParam Integer id) {
        RestResult restResult = new RestResult();
        elementService.deleteElement(id);
        restResult.setCode(RestResult.NOTICE);
        return restResult;
    }

    /**
     * 更新单个元素的信息
     * @param element
     * @return
     */
    @RequestMapping("/updateElement")
    @ResponseBody
    public RestResult updateElement(Element element) {
        RestResult restResult = new RestResult();
        Assert.notNull(element.getName(), "修改的元素名不能为空！");
        Assert.notNull(element.getKeyword(), "修改的元素关键字不能为空！");
        elementService.updateElement(element);
        restResult.setCode(RestResult.NOTICE);
        return restResult;
    }

    /**
     * 获取所有元素
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public RestResult findElements() {
        RestResult restResult = new RestResult();
        List<Element> elements = elementService.findElements();
        Assert.notNull(elements, "获取元素列表失败");
        restResult.setItem(elements);
        return restResult;
    }


    /**
     * 获取菜单下的所有元素
     *
     * @param menuKeyword
     * @return
     */
    @RequestMapping("/findElementsByMenuKeyword")
    @ResponseBody
    public RestResult findElementsByMenuKeyword(String menuKeyword) {
        RestResult restResult = new RestResult();
        List<Element> elements = elementService.findElementsById(menuKeyword);
        restResult.setItem(elements);
        return restResult;
    }

    /**
     * 获取菜单下的所有元素权限
     * @param menuKeyword 菜单keyword
     * @return
     */
    @RequestMapping("/findElementPermissionByMenuKey")
    @ResponseBody
    public RestResult findElementsById(String menuKeyword, Integer jobId) {
        RestResult restResult = new RestResult();
        ElementPermission elements = elementService.findElementsByJobIdAndMenuId(jobId, menuKeyword);
        Assert.notNull(elements, "获取元素权限列表失败");
        restResult.setItem(elements);
        return restResult;
    }

    /**
     * 获取菜单下的所有元素权限
     * @param menuId 菜单id
     * @return
     */
    @RequestMapping("/findElementPermission")
    @ResponseBody
    public RestResult findElementPermission(Integer menuId) {
        RestResult restResult = new RestResult();
        UserDto userDto = ThreadLocalUtils.getUser();
        if(userDto == null){
            restResult.setCode(RestResult.LOGIN);
            return restResult;
        }
        List<Element> list = elementService.findElementPermission(menuId,userDto);
        Assert.notNull(list, "获取元素权限列表失败");
        restResult.setItem(list);
        return restResult;
    }

    /**
     * 获取菜单下的所有元素权限
     *
     * @param menuKeyword 菜单keyword
     * @return
     */
    @RequestMapping("/findElementsByMenuKey")
    @ResponseBody
    public RestResult findElementsByMenuKey(String menuKeyword) {
        RestResult restResult = new RestResult();
        List<Element> elements = elementService.findElementsByMenuKey(menuKeyword);
        Assert.notNull(elements, "获取元素权限列表失败");
        restResult.setItem(elements);
        return restResult;
    }

    /**
     * 新增元素跟菜单的关系
     *
     * @param elementId
     * @param menuKeyword
     * @return
     */
    @RequestMapping("/updateBindElement")
    @ResponseBody
    public RestResult updateBindElement(Integer elementId, String menuKeyword, boolean checked) {
        RestResult restResult = new RestResult();
        elementService.updateBindElement(elementId, menuKeyword, checked);
        restResult.setCode(RestResult.NOTICE);
        return restResult;
    }


}
