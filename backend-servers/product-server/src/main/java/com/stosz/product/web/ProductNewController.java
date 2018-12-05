package com.stosz.product.web;

import com.alibaba.druid.support.json.JSONUtils;
import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.ext.service.IUserDepartmentRelService;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.enums.CategoryUserTypeEnum;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.constant.MenuKeyword;
import com.stosz.product.ext.enums.ProductNewEvent;
import com.stosz.product.ext.enums.ProductNewState;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.model.ProductNew;
import com.stosz.product.ext.model.ProductNewZone;
import com.stosz.product.service.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 产品查重  controller
 *
 * @author oxq
 */
@Controller
@RequestMapping(value = "/product/productNewManage")
public class ProductNewController extends AbstractController {
    @Resource
    private ProductNewService productNewService;
    @Resource
    private ProductNewZoneService productNewZoneService;
    @Resource
    private CategoryUserRelService categoryUserRelService;
    @Resource
    private IUserService iUserService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private ProductService productService;

    /**
     * 新增调用方法
     *
     * @param erpProduct
     * @param model
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public RestResult add(ProductNew erpProduct, Model model, RedirectAttributes redirectAttributes) throws Exception {
        //数据合法性校验
        Assert.notNull(erpProduct, "保存失败,传入参数异常!");
        //判断产品名称是否为空
        Assert.notNull(erpProduct.getTopCategoryId(), "一级分类不能为空");
        Assert.notNull(erpProduct.getClassifyEnum(), "产品特性不能为空");
        Assert.notNull(erpProduct.getSourceEnum(), "产品来源不能为空");
        Assert.notNull(erpProduct.getCustomEnum(), "产品类别不能为空");
        Assert.notNull(erpProduct.getMainImageUrl(), "产品图片不能为空");
        Assert.notNull(erpProduct.getGoalEnum(), "上架目标不能为空");
        Assert.isTrue(erpProduct.getAttributeDesc() != null && !"".equals(erpProduct.getAttributeDesc().trim()), "属性描述不能为空");
        MBox.assertLenth(erpProduct.getTitle(), "产品标题", 1, 80);
        MBox.assertLenth(erpProduct.getInnerName(), "内部名", 1, 80);
        MBox.assertLenth(erpProduct.getSourceUrl(), "来源网址", 5, 500);
        Assert.isTrue(erpProduct.getSourceUrl().matches("^([hH][tT]{2}[pP]:\\/\\/|[hH][tT]{2}[pP][sS]:\\/\\/)(.)+$"),
        		"来源网址需要以http://或https://开头");
        MBox.assertLenth(erpProduct.getMemo(),"备注", 200);
        erpProduct.setCreatorId(MBox.getLoginUserId());
        erpProduct.setCreator(ThreadLocalUtils.getUser().getLoginid());
        User user = iUserService.getByUserId(MBox.getLoginUserId());
        erpProduct.setDepartmentId(user.getDepartmentId());
        erpProduct.setDepartmentNo(user.getDepartmentNo());
        erpProduct.setDepartmentName(user.getDepartmentName());
//        erpProduct.setAdvertTopCategoryId(erpProduct.getTopCategoryId());
        return productNewService.add(erpProduct);
    }


    /**
     * 删除调用方法
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public RestResult delete(@Param("id") Integer id) {
        RestResult result = new RestResult();
        productNewService.delete(id);
        result.setCode(RestResult.NOTICE);
        result.setDesc("新品被成功删除!");
        return result;
    }

    /**
     * 修改调用方法
     * @param request
     * @param productNew
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public RestResult update(HttpServletRequest request, ProductNew productNew) {
        RestResult result = new RestResult();
        Assert.notNull(productNew, "参数接收错误!");
        Assert.notNull(productNew.getId(), "参数Id不能为空!");
        Assert.notNull(productNew.getTopCategoryId(), "一级分类不能为空");
        Assert.notNull(productNew.getClassifyEnum(), "产品特性不能为空");
        Assert.notNull(productNew.getSourceEnum(), "产品来源不能为空");
        Assert.notNull(productNew.getCustomEnum(), "产品类别不能为空");
        Assert.notNull(productNew.getMainImageUrl(), "产品图片不能为空");
        Assert.notNull(productNew.getGoalEnum(), "上架目标不能为空");
        Assert.isTrue(productNew.getAttributeDesc() != null && !"".equals(productNew.getAttributeDesc().trim()), "属性描述不能为空");
        MBox.assertLenth(productNew.getInnerName(), "内部名", 1, 80);
        MBox.assertLenth(productNew.getTitle(), "产品标题", 1, 80);
        MBox.assertLenth(productNew.getSourceUrl(), "来源网址", 5, 500);
        Assert.isTrue(productNew.getSourceUrl().matches("^([hH][tT]{2}[pP]:\\/\\/|[hH][tT]{2}[pP][sS]:\\/\\/)(.)+$"),
        		"来源网址需要以http://或https://开头");
        MBox.assertLenth(productNew.getMemo(),"备注", 200);
        ProductNew productNewTmp = productNewService.load(productNew.getId());
        Assert.isTrue(productNewTmp.getStateEnum() == ProductNewState.draft,"仅新建状态的新品允许修改！");
        return productNewService.update(productNew);
    }

    /**
     * 细化分类调用方法
     */
    @RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
    @ResponseBody
    public RestResult updateCategory(@RequestParam Integer id, @RequestParam Integer categoryId) {
        return productNewService.updateCategory(id,categoryId);
    }

    /**
     * 批量细化分类调用方法
     */
    @RequestMapping(value = "/updateCategoryBatch", method = RequestMethod.POST)
    @ResponseBody
    public RestResult updateCategoryBatch(@RequestParam String ids, @RequestParam Integer categoryId) {
        String[] arrayIds = ids.split(",");
        List<Integer> idsList = new ArrayList<>();
        for (int i = 0; i < arrayIds.length; i++){
            Integer id = Integer.valueOf(arrayIds[i].trim());
            idsList.add(id);
        }
        Assert.isTrue(idsList != null && idsList.size() != 0, "批量细化分类的时候请先选择新品ID!");
        return productNewService.updateCategoryBatch(idsList, categoryId);
    }


    /**
     * 状态机处理各种状态的变化
     */
    @RequestMapping(value = "/processEvent", method = RequestMethod.POST)
    @ResponseBody
    public RestResult processEvent(@RequestParam Integer id,
                                   @RequestParam ProductNewState state ,
                                   @RequestParam ProductNewEvent event ,
                                   @RequestParam(required = false,defaultValue = "") String memo,
                                   @RequestParam(required=false) String spu,
                                   @RequestParam(required=false) String productZoneIds,
                                   @RequestParam(required=false, defaultValue = "false") Boolean leftCheckOk) {
        if (ProductNewEvent.backToCreated.equals(event)) {
            MBox.assertLenth(memo, "驳回的备注", 1, 150);
        }
        if (ProductNewEvent.decline.equals(event)) {
            MBox.assertLenth(memo, "拒绝的备注", 1, 150);
        }
        if(state==ProductNewState.waitCheck ){
            if(event==ProductNewEvent.duplicate){
                Assert.notNull(spu,"指定重复产品时，必须指定与哪个产品重复");
            }
        }
        List<Integer> zoneIds = new ArrayList<>();
        if (productZoneIds != null && !"".equals(productZoneIds)){
            String[] arr = productZoneIds.split(",");
            for (String zoneId : arr){
                zoneIds.add(Integer.valueOf(zoneId.trim()));
            }
        }
        productNewService.processEvent(id,state,event,memo,spu ,zoneIds, leftCheckOk);
        RestResult result = new RestResult();
        result.setDesc("新品:" + id + " 状态处理成功！");
        result.setCode(RestResult.NOTICE);
        return result;
    }

    /**
     * 状态机处理各种状态的变化
     *
     * @param state
     * @param event
     * @param memo
     * @return
     */
    @RequestMapping(value = "/processEventBatch", method = RequestMethod.POST)
    @ResponseBody
    public RestResult processEventBatch(@RequestParam String id,
                                        @RequestParam ProductNewState state,
                                        @RequestParam ProductNewEvent event,
                                        @RequestParam(required = false, defaultValue = "") String memo,
                                        @RequestParam(required = false) String spu,
                                        @RequestParam(required = false) String productZoneIds,
                                        @RequestParam(required=false, defaultValue = "false") Boolean leftCheckOk) {
        Assert.hasLength(id, "传入的产品id集合为空！");
        List<Integer> productNewIdList = (List<Integer>) JSONUtils.parse(id);
        List<Integer> zoneIds = new ArrayList<>();
        if (productZoneIds != null && !"".equals(productZoneIds)){
            String[] arr = productZoneIds.split(",");
            for (String zoneId : arr){
                zoneIds.add(Integer.valueOf(zoneId.trim()));
            }
        }
        for (Integer myId : productNewIdList) {
            if (state == ProductNewState.waitCheck) {
                if (event == ProductNewEvent.duplicate) {
                    Assert.notNull(spu, "指定重复产品时，必须指定与哪个产品重复");
                }
            }
            productNewService.processEvent(myId, state, event, memo, spu, zoneIds, leftCheckOk);
        }
        RestResult result = new RestResult();
        result.setDesc("批量处理成功！");
        result.setCode(RestResult.NOTICE);
        return result;
    }


    /**
     * 查看或者编辑调用方法
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public RestResult get(@RequestParam Integer id) {
        Assert.notNull(id, "id不允许为空");
        RestResult result = new RestResult();
        ProductNew entity = productNewService.getInfo(id);
        result.setCode(RestResult.OK);
        result.setItem(entity);
        return result;
    }

    private RestResult queryList(ProductNew erpProduct){
        RestResult result = new RestResult();
        //获取总记录数
        int counts = productNewService.count(erpProduct);
        result.setCode(RestResult.OK);
        //返回总记录数
        result.setTotal(counts);
        if (counts == 0) {
            //如果数据库中没有，就不用下面的查询了
            return result;
        }
        //获取当前页面需要显示的数据
        List<ProductNew> productList = productNewService.find(erpProduct);


        
        //处理显示新品的国家信息
        if (productList != null && productList.size() > 0) {

            for(ProductNew list : productList){
                list.setCategoryName(categoryService.getSuperiorNameByNo(list.getCategoryNo(),","));
            }


            Map<Integer, ProductNew> idMapProductNewList = productList.stream().collect(Collectors.toMap(ProductNew::getId, Function.identity()));

            Set<Integer> productNewIds = idMapProductNewList.keySet();
            List<ProductNewZone> countries = productNewZoneService.findByProductNewIds(productNewIds);
            // 根据新品id对国家进行分组
            Map<Integer, List<ProductNewZone>> idMapCountries = countries.stream().collect(Collectors.groupingBy(ProductNewZone::getProductNewId));

            for (Integer productNewId : idMapCountries.keySet()) {

                ProductNew pn = idMapProductNewList.get(productNewId);

                Assert.notNull(pn, "新品id:" + productNewId + " 区域信息里面查出来不可能新品中没有！需找工程师仔细排查！！");
                //如何组装，交给前端来完成！
                pn.setProductNewZones(idMapCountries.get(productNewId));

            }
        }

        //返回当前页面的记录数
        result.setItem(productList);
        return result;
    }
    

    /**
     * 页面查询调用方法(新品设置分页)
     *      广告新品查询
     */
    @RequestMapping(value = "/new_ad")
    @ResponseBody
    public RestResult queryAdList(ProductNew erpProduct, HttpServletRequest request, HttpServletResponse response, Model model) {

        //获取当前登录用户的session
        UserDto dto = ThreadLocalUtils.getUser();
        Assert.notNull(dto, "获取用户登录信息失败,请先登录!");
        erpProduct.setCreatorId(dto.getId()); //取当前登录用户的数据
        erpProduct.setOrderBy(" _this.id");  //按照id降序排列
        return queryList(erpProduct);
    }

    @RequestMapping(value = "/findProductNewBySpu")
    @ResponseBody
    public RestResult findProductNewBySpu(String spu) {
        RestResult result = new RestResult();
        Product product = productNewService.findProductBySpu(spu);
        result.setItem(product);
        return result;
    }


    /**
     * 页面查询调用方法(新品查重分页)
     *         查重新品页面
     */
    @RequestMapping(value = "/new_check")
    @ResponseBody
    public RestResult queryCheckList(ProductNew erpProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
        RestResult result = new RestResult();
        List<Integer> departmentids = productService.getDepartmentViewByCurrentUser();
        erpProduct.setDepartmentIds(departmentids);
        //根据页面传入的用户id获取用户品类的信息
        Integer userId = erpProduct.getCheckerId();
//        Set<Integer> categorySet = new HashSet<Integer>();
        if (userId != null && userId.intValue() != 0) {
            //根据用户Id、用户类型获取用户的品类设置
            List<Integer> topCategories = categoryUserRelService.findByUserId(userId, true, CategoryUserTypeEnum.checker);
            /*如果用户的一级品类为空,直接返回空集合*/
            if(topCategories.size() == 0){
            	result.setDesc("该用户没有设置一级品类,没有数据");
            	return result;
            }
            erpProduct.setTopCategories(topCategories);
        }
        //品类搜索
        if (erpProduct.getCategoryId() != null) {
            erpProduct.setCategoryIds(categoryService.categorySearch(erpProduct.getCategoryId()));
        }
     //   return queryList(erpProduct);
        erpProduct.setCheckerId(null);
        if(erpProduct.getAuditorId() != null){
        	erpProduct.setCheckerId(erpProduct.getAuditorId());
        }
        erpProduct.setOrderBy("submit_time");
        erpProduct.setOrder(false);
        return queryList(erpProduct);
    }

    /**
     * 页面查询调用方法(主管审批)
     *
     */
    @RequestMapping(value = "/new_approve")
    @ResponseBody
    public RestResult queryApproveList(ProductNew erpProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
        //原来的方法
    	RestResult result = new RestResult();
        List<Integer> idList = productService.getDepartmentViewByCurrentUser();
        erpProduct.setDepartmentIds(idList);
        //修改后
        return queryList(erpProduct);
    }


}
