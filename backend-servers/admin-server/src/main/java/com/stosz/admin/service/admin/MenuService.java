package com.stosz.admin.service.admin;

import com.stosz.admin.ext.common.MenuNode;
import com.stosz.admin.ext.model.Menu;
import com.stosz.admin.ext.model.User;
import com.stosz.admin.mapper.admin.MenuMapper;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.StoszCoder;
import com.stosz.plat.common.SystemDto;
import com.stosz.plat.enums.SystemEnum;
import com.stosz.plat.service.ProjectConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * 菜单的service
 *
 * @author XCY
 * @version 2017/8/17
 */
//@com.alibaba.dubbo.config.annotation.Service
    @Service
public class MenuService {

    @Resource
    private MenuMapper menuMapper;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProjectConfigService projectConfigService;

    /**
     * 根据职位id获取到对应的菜单和子菜单
     *
     * @param jobId 职位id
     * @return 菜单列表
     */
    public List<Menu> findByJobId(Integer jobId) {
        Assert.notNull(jobId, "职位id不能为空！");
        return menuMapper.findByJobId(jobId);
    }

    /**
     * 根据职位id获取到对应的菜单树
     * 展现左侧菜单用
     *
     * @param jobId 职位id
     * @return 菜单树
     */
    public MenuNode getNodeByJobId(Integer jobId) {
        List<Menu> menuList = menuMapper.findByJobId(jobId);
        Assert.notNull(menuList, "未查询到该职位拥有的菜单！");
        return buildMenuTree(menuList);
    }

    /**
     * 根据职位id获取到对应的权限菜单
     *
     * @param jobId 职位id
     * @return 权限菜单
     */
    public MenuNode getPermission(Integer jobId) {
        HashSet<Integer> menuSet = menuMapper.findIdByJobId(jobId);
        Assert.notNull(menuSet, "未查询到该职位拥有的菜单！");
        List<Menu> allMenuList = menuMapper.query();
        Assert.notNull(allMenuList, "获取所有菜单失败！");
        for (Menu menu : allMenuList) {
            Integer id = menu.getId();
            if (menuSet.contains(id)) {
                menu.setChecked(true);
            }
        }
        return buildMenuTree(allMenuList);
    }

    /**
     * 根据用户id获取菜单列表
     *
     * @param userId 用户id
     * @return 顶级菜单
     */
    public List<Menu> findTopMenuByUserId(Integer userId, HttpServletRequest request) {

        SystemEnum systemEnum = MBox.isInside(request)?SystemEnum.front:SystemEnum.frontOutside;
        String httpPrefix = projectConfigService.getSystemHttp(systemEnum);

        List<Menu> menuList = menuMapper.findTopByUserId(userId);
        for (Menu menu : menuList) {
            menu.setHttpPrefix(httpPrefix);
        }
        Menu menu = new Menu();
        menu.setKeyword("product_img");
        SystemEnum systemEnumImage = MBox.isInside(request)?SystemEnum.image:SystemEnum.imageOutside;
        String imagePrefix = projectConfigService.getSystemHttp(systemEnumImage);
        menu.setHttpPrefix(imagePrefix);
        menuList.add(menu);
        return menuList;
    }

    /**
     * 根据用户id获取菜单列表
     *
     * @param userId 用户id
     * @return 顶级菜单
     */
    public List<Menu> findChildrenMenuByUserId(Integer userId, String keyword) {
        return menuMapper.findChildrenByUserId(userId, keyword);
    }

    public MenuNode getUserMenuNodeByParentNode(Integer userId , String keyword){
        Menu m = menuMapper.getByKeyword(keyword);
        Assert.notNull(m , "菜单关键字:" + keyword + " 不存在！ ");
        List<Menu> menus = menuMapper.findChildrenByUserId(userId, keyword);
        MenuNode root = this.convertToNode(m);
        buildMenuTree(menus , root);
        return root;
    }

    /**
     * 查询完整的菜单
     *
     * @return 菜单列表
     */
    public List<Menu> query() {
        return menuMapper.query();
    }

    /**
     * 查询完整的菜单树
     *
     * @return 菜单树
     */
    public MenuNode getNode() {
        List<Menu> menuList = menuMapper.query();
        Assert.notNull(menuList, "获取到的菜单为空！");
        return buildMenuTree(menuList);
    }

    /**
     * 获取到非叶子节点的所有菜单
     *
     * @return 非叶子节点
     */
    public MenuNode getNodeUnLeaf() {
        List<Menu> menuList = menuMapper.queryUnLeaf();
        Assert.notNull(menuList, "获取到的菜单为空！");
        return buildMenuTree(menuList);
    }

    /**
     * 根据id查询对应的菜单
     *
     * @param id id
     * @return 匹配的菜单
     */
    public Menu getById(Integer id) {
        Menu menu = menuMapper.getById(id);
        //算出该菜单是否为叶子节点
        List<String> menuList = menuMapper.findNameByParentId(id);
        if (menuList == null || menuList.isEmpty()) {
            menu.setLeaf(true);
        }
        return menu;
    }

    /**
     * 新增菜单
     *
     * @param menu 菜单
     * @return 新增结果
     */
    public Integer insert(Menu menu) {
        int i;
        try {
            Integer parentId = menu.getParentId();

            //判断同一级是否有相同名称的菜单
            List<String> list = menuMapper.findNameByParentId(parentId);
            if (list != null && list.size() > 0 && list.indexOf(menu.getName()) >= 0) {
                return 2;
            }
            String no = getMenuNo(parentId);
            menu.setNo(no);
            i = menuMapper.insert(menu);
            Assert.isTrue(i == 1, "保存错误！没有插入数据库！");
            return i;
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new IllegalArgumentException("菜单：" + menu.getName() + "已经存在与数据库中，重复插入！");
        }
    }

    /**
     * 更新该菜单
     *
     * @param menu 菜单
     */
    public void update(Menu menu) {
        try {
            Menu menuDB = menuMapper.getById(menu.getId());
            Assert.notNull(menuDB, "菜单在数据库中不存在！id:" + menuDB.getId());
            if (!menuDB.getParentId().equals(menu.getParentId())) {
                String no = getMenuNo(menu.getParentId());
                menu.setNo(no);
            }
            List<Menu> menuList = menuMapper.query();
            List<Menu> repeatName = menuList.stream().filter(menu1 -> !Objects.equals(menu1.getId(), menu.getId()) && Objects.equals(menu1.getName(), menu.getName())).collect(Collectors.toList());
            Assert.isTrue(repeatName.size() == 0, "菜单名称：" + menu.getName() + "已经存在，重复插入！");
            List<Menu> repeatKeyword = menuList.stream().filter(menu1 -> !menu1.getId().equals(menu.getId()) && menu1.getKeyword().equals(menu.getKeyword())).collect(Collectors.toList());
            Assert.isTrue(repeatKeyword.size() == 0, "菜单关键字：" + menu.getKeyword() + "已经存在，重复插入！");
            menuMapper.update(menu);
        } catch (org.springframework.dao.DuplicateKeyException e) {
            throw new IllegalArgumentException("菜单：" + menu.getName() + "已经存在与数据库中，重复插入！");
        }

    }


    /**
     * 根据id 删除该菜单和它的子菜单
     *
     * @param id id
     */
    public void delete(Integer id) {
        Integer i = menuMapper.delete(id);
        Assert.isTrue(i >= 1, "删除错误！没有在数据库中删除掉！");
        logger.info("删除菜单成功，菜单id为{}", id);


    }


    public MenuNode getNodeByUser(User user) {
        Assert.notNull(user, "用户为空，不能取到对应的菜单树！");
        Integer id = MBox.getLoginUserId();
        Assert.notNull(id, "用户id不能为空！");
        List<Menu> menuList = menuMapper.getMenuByUserId(id);
        Assert.notNull(menuList, "系统获取到给用户的菜单为空！");
        return buildMenuTree(menuList);
    }

    /**
     * 根据用户获取到该用户拥有的菜单的第一个URL
     *
     * @param user 用户id
     * @return 第一个url
     */
    public String getFirstUrlByUser(User user) {
        Assert.notNull(user, "用户为空，不能取到对应的菜单树！");
        Integer id = MBox.getLoginUserId();
        Assert.notNull(id, "用户id不能为空！");
        String firstUrl = "";
        List<Menu> menuList = menuMapper.getMenuByUserId(id);
        Assert.notNull(menuList, "系统获取到给用户的菜单为空！");
        for (Menu menu : menuList) {
            String menuUrl = menu.getUrl();
            if (menuUrl != null && !"#".equals(menuUrl) && menuUrl.contains("/")) {
                firstUrl = menuUrl;
                break;
            }
        }
        return firstUrl;
    }

    /**
     * 根据父级id获取到下一个菜单编号
     *
     * @param parentId 父级id
     * @return 菜单编号
     */
    private String getMenuNo(Integer parentId) {
        List<String> menuNoList = menuMapper.findNoByParentId(parentId);
        Menu menu = menuMapper.getById(parentId);
        String parentNo = "";
        if (parentId != 0) {
            parentNo = menu.getNo();
        }
        if (menuNoList != null && !menuNoList.isEmpty()) {
            return StoszCoder.toHex(menuNoList, parentNo);
        } else {
            List<String> menuNos = new ArrayList<>();
            menuNos.add(menu.getNo() + "00");
            return StoszCoder.toHex(menuNos, parentNo);
        }
    }


    /**
     * 构建菜单树
     *
     * @param menus 菜单的集合
     * @return 菜单树
     */
    public MenuNode buildMenuTree(List<Menu> menus) {
        MenuNode root = MenuNode.createRootNode();
        buildMenuTree(menus, root);
        return root;
    }


    /**
     * 菜单树的递归
     *
     * @param menus 菜单集合
     * @param root  递归的菜单树
     */
    public void buildMenuTree(List<Menu> menus, MenuNode root) {
        if (menus == null || menus.isEmpty()) {
            return;
        }
        menus.stream().filter(e -> e.getParentId().equals(root.getId())).forEach(e -> root.addChild(convertToNode(e)));
        if (root.getLeaf()) {
            return;
        }
        for (MenuNode node : root.getChildren()) {
            buildMenuTree(menus, node);
        }
    }

    private MenuNode convertToNode(Menu menu) {
        MenuNode node = new MenuNode();
        node.setId(menu.getId());
        node.setName(menu.getName());
        node.setNo(menu.getNo());
        node.setSort(menu.getSort());
        node.setIcon(menu.getIcon());
        node.setRemark(menu.getRemark());
        node.setKeyword(menu.getKeyword());
        node.setParentId(menu.getParentId());
        node.setUrl(menu.getUrl());
        node.setChecked(menu.getChecked());
        return node;
    }

    /**
     * 判断当前菜单下是否还有子菜单
     *
     * @param id id
     * @return 子菜单
     */
    public boolean hasChildNode(Integer id) {
        List<String> list = menuMapper.findNoByParentId(id);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    public List<Menu> findMenusByUserId(Integer userId) {
        return menuMapper.getMenuByUserId(userId);
    }

    public Integer findMenuIdByKeyword(String menuKeyword) {
        return menuMapper.findMenuIdByKeyword(menuKeyword);
    }


}
