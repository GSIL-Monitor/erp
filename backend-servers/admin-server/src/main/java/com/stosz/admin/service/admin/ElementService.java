package com.stosz.admin.service.admin;

import com.google.common.collect.Lists;
import com.stosz.admin.ext.model.Element;
import com.stosz.admin.ext.model.ElementPermission;
import com.stosz.admin.mapper.admin.ElementMapper;
import com.stosz.plat.common.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liufeng
 * @version [1.0 , 2017/9/11]
 */
//@com.alibaba.dubbo.config.annotation.Service
    @Service
public class ElementService {
    @Resource
    private ElementMapper elementMapper;
    @Resource
    private JobService jobService;
    @Resource
    private MenuService menuService;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    /**
     * 新增元素
     * @param element 元素对象
     */
    public void saveElement(Element element) {
        try {
            int i = elementMapper.insert(element);
            Assert.isTrue(i > 0, "新增元素失败！");
            logger.info("新增元素成功：{}", i);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new IllegalArgumentException("元素名或关键词已经存在与数据库中，重复插入！");
        }
    }

    /**
     *绑定元素跟菜单的关系
     *
     * @param elementId 元素Id
     * @param menuKeyword 菜单keyword
     */
    public void bindElement(Integer elementId, String menuKeyword) {
        //插入菜单跟元素的映射表
        Integer menu_id = menuService.findMenuIdByKeyword(menuKeyword);
        try {
            elementMapper.insertMenuElement(null, menu_id, Lists.newArrayList(elementId));
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new IllegalArgumentException("该绑定关系已经存在，不允许重复绑定!");
            } else {
                throw new IllegalArgumentException("绑定菜单元素失败!");
            }
        }
    }

    /**
     * 解绑菜单元素关系
     */
    public void unbindElement(Integer elementId, String menuKeyword) {
        Integer menu_id = menuService.findMenuIdByKeyword(menuKeyword);
        int i = elementMapper.deleteMenuElement(menu_id, elementId);
        Assert.isTrue(i > 0, "删除菜单元素失败！");
        logger.info("删除菜单元素成功：{}", i);
    }

    /**
     * 修改元素
     */
    public void updateElement(Element element) {
        try {
            int i = elementMapper.update(element);
            Assert.isTrue(i > 0, "修改菜单元素失败！");
            logger.info("修改菜单元素成功：{}", i);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new IllegalArgumentException("元素已经存在与数据库中");
        }
    }

    /**
     * 获取所有的元素
     */
    public List<Element> findElements() {
        return elementMapper.findElements();
    }

    /**
     * 获取菜单下的需要分配权限的元素
     *
     */
    public List<Element> findElementsById(String menuKeyword) {
        Integer menu_id = menuService.findMenuIdByKeyword(menuKeyword);
        return elementMapper.findElementsById(menu_id);
    }

    /**
     * 获取元素权限列表
     */
    public ElementPermission findElementsByJobIdAndMenuId(Integer jobId, String menuKeyword) {
        ElementPermission elementPermission = new ElementPermission();
        Integer menu_id = menuService.findMenuIdByKeyword(menuKeyword);
        elementPermission.setMenuId(menu_id);
        //获取当前菜单下的所有的需要赋权限的元素
        List<Element> elements = findElementsById(menuKeyword);
        if (elements != null && elements.size() > 0) {
            //获取哪些元素是有已经赋权
            List<Integer> elementIds = jobService.findElementIds(jobId, menu_id);
            for (Element element : elements) {
                Integer eId = element.getId();
                if (elementIds != null && elementIds.size() > 0 && elementIds.indexOf(eId) >= 0) {
                    element.setChecked(true);
                }
            }
            elementPermission.setElements(elements);
        }

        return elementPermission;
    }

    public List<Element> findElementPermission(Integer menuId , UserDto userDto){
        Integer userId = userDto.getId();
        List<Element> elementList = elementMapper.findElementsById(menuId);
        if(elementList.isEmpty()){
            return new ArrayList<>();
        }
        List<Integer> hasElement = elementMapper.findElementPermission(menuId, userId);
        if(!hasElement.isEmpty()){
            for (Element element: elementList) {
                if(hasElement.contains(element.getId())){
                    element.setChecked(true);
                }
            }
        }
        return elementList;
    }

    public void updateBindElement(Integer elementId, String menuKeyword, boolean checked) {
        if (checked) {
            bindElement(elementId, menuKeyword);
        } else {
            unbindElement(elementId, menuKeyword);
        }
    }

    public List<Element> findElementsByMenuKey(String menuKey) {
        List<Element> elementList = elementMapper.findElements();
        Assert.notNull(elementList, "获取菜单元素权限失败！");
        List<Integer> elementIds = elementMapper.findIdByMenuKey(menuKey);
        if (elementIds != null && !elementIds.isEmpty()) {
            for (Element element : elementList) {
                Integer id = element.getId();
                if (elementIds.contains(id)) {
                    element.setChecked(true);
                }
            }
        }
        return elementList;
    }

    /**
     * 获取当前登录用户的菜单下的所有元素权限
     */
    public List<Element> findElementIdByMenuInAndUserId(Integer userId, Integer menuId) {
        return elementMapper.findElementIdByMenuInAndUserId(menuId, userId);
    }

    public void deleteElement(Integer id) {
        elementMapper.delete(id);
    }
}
