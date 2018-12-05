package com.stosz.admin.web;

import com.stosz.admin.ext.common.MenuNode;
import com.stosz.admin.ext.model.Menu;
import com.stosz.admin.service.admin.MenuService;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.web.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by XCY on 2017/8/17.
 * desc: 菜单的controller
 */
@Controller
@RequestMapping("/admin/menu")
public class MenuController extends AbstractController {
    private static final String TAG = "MenuController";

    @Resource
    private MenuService menuService;


    @RequestMapping("")
    public String list(Model model, HttpServletRequest request) {
        return "/admin/menu/menu";
    }

    /**
     * 获取到菜单树
     * @return 菜单树
     */
    @RequestMapping("/query")
    @ResponseBody
    public RestResult query() {
        //取出所有菜单
        RestResult result = new RestResult();
        MenuNode menuNode = menuService.getNode();
        Assert.notNull(menuNode, "获取菜单失败！");
        result.setItem(menuNode);
        return result;
    }


    /**
     * 根据id返回对应的菜单
     * @param id id
     * @return 匹配的菜单
     */
    @RequestMapping("/get")
    @ResponseBody
    public RestResult getMenuById(Integer id) {
        RestResult result = new RestResult();
        Assert.notNull(id, "菜单id不能为空");
        Menu menu = menuService.getById(id);
        Assert.notNull(menu, "没有查询到对应的菜单！");
        result.setItem(menu);
        return result;
    }

    /**
     * 添加菜单
     * @param menu 要添加的菜单
     * @return 添加结果
     */
    @RequestMapping("/save")
    @ResponseBody
    public RestResult add(Menu menu) {
        RestResult result = new RestResult();
        Assert.notNull(menu, "不能添加空的菜单！");
        if (menu.getName().equals("")) {
            result.setCode(RestResult.FAIL);
            result.setDesc("菜单名不能为空！");
        } else {
            Integer i = menuService.insert(menu);
            if (i == 2) {
                result.setCode(RestResult.FAIL);
                result.setDesc("同级菜单已经存在与数据库中，重复插入！");
            }
            if (i == 1) {
                result.setItem(menu.getId());
                result.setCode(RestResult.NOTICE);
            }
        }
        return result;
    }

    /**
     * 修改菜单
     * @param menu 要修改的菜单
     * @return 修改结果
     */
    @RequestMapping("/update")
    @ResponseBody
    public RestResult update(Menu menu) {
        RestResult result = new RestResult();
        Assert.notNull(menu, "传入了一个空的菜单！");
        Assert.notNull(menu.getName(), "菜单名不能为空");
        menuService.update(menu);
        result.setCode(RestResult.NOTICE);
        return result;
    }

    /**
     * 根据id删除菜单
     * @param id id
     * @return 删除结果
     */
    @RequestMapping("/delete")
    @ResponseBody
    public RestResult delete(Integer id) {
        RestResult result = new RestResult();
        Assert.notNull(id, "传入的菜单为空！");
        //先判断是否有子菜单
        if (menuService.hasChildNode(id)) {
            result.setCode(RestResult.FAIL);
            result.setDesc("当前菜单还有子菜单，请先删除子菜单！");
        } else {
            menuService.delete(id);
            result.setCode(RestResult.NOTICE);
        }
        return result;
    }

    @RequestMapping("/findUnLeafMenu")
    @ResponseBody
    public RestResult findUnLeafMenu() {
        RestResult result = new RestResult();
        // 获取非叶子节点的数据
        MenuNode menuNode = menuService.getNodeUnLeaf();
        Assert.notNull(menuNode, "获取菜单失败！");
        result.setItem(menuNode);
        return result;
    }

}  
