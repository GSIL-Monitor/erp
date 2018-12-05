package com.stosz.product.web;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.plat.utils.CommonUtils;
import com.stosz.product.ext.model.Category;
import com.stosz.product.ext.model.CategoryUserRel;
import com.stosz.product.ext.model.ProductSku;
import com.stosz.product.ext.service.IWmsService;
import com.stosz.product.service.CategoryService;
import com.stosz.product.service.CategoryUserRelService;
import com.stosz.product.service.ProductSkuService;
import com.stosz.wms.service.WmsService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @auther carter
 * create time    2017-10-16
 *
 * 一些辅助的页面；
 * 1，把xls录入的用户的跟一级分类，身分的对应关系生成sql;
 */
@Controller("ToolController")
@RequestMapping("/product/tool")
public class ToolController {

    private static final Logger logger = LoggerFactory.getLogger(ToolController.class);
    
    @Autowired
    private IUserService userService;

    @Autowired
    private IDepartmentService departmentService;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryUserRelService categoryUserRelService;


    @Autowired
    private ProductSkuService productSkuService;

    @Autowired
    private IWmsService wmsService;


    /*
        找出前100条没有成功推送的sku
     */
    @RequestMapping("/wms_repost_list")
    public void wms_repost_list(HttpServletResponse response) {
        Set<String> wmsPushErrSkus = productSkuService.findWmsPushErrSkus();

        String skus = wmsPushErrSkus.stream()
                .filter(item -> !Strings.isNullOrEmpty(item))
                .collect(Collectors.joining(","));
        resClient(response, skus);
    }


    /**
     * 按照sku进行重推,多个按照,或者换行符号进行分割
     *
     * @param request
     */
    @RequestMapping("/wms_repost")
    public void wms_repost(HttpServletRequest request, HttpServletResponse response) {
        String skus = CommonUtils.getStringValue(request.getParameter("skus"));
        String message = "处理成功";
        if (Strings.isNullOrEmpty(skus)) {
            message = "请输入数据";
            resClient(response, message);
            return;
        }

        String[] splitArray_1 = skus.split(",");
//        String[] splitArray_2 = skus.split("\n");

        Set<String> skuSet = Sets.newHashSet();
        skuSet.addAll(Arrays.asList(splitArray_1));
//        skuSet.addAll(Arrays.asList(splitArray_2));

        skuSet.stream()
                .filter(item -> !Strings.isNullOrEmpty(item))
                .forEach(sku -> wmsService.repostBySku(sku));


    }


    private void resClient(HttpServletResponse response, String message) {
        try {
            response.getWriter().write(message);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @RequestMapping("/wms")
    public ModelAndView wms(HttpServletRequest request)
    {
        ModelAndView modelAndView = new ModelAndView("wms");

        if("post".equalsIgnoreCase(request.getMethod()))
        {
            String start_date = CommonUtils.getStringValue(request.getParameter("start_date"));
            String end_date = CommonUtils.getStringValue(request.getParameter("end_date"));

            Assert.isTrue(!Strings.isNullOrEmpty(start_date),"日期不能为空");
            Assert.isTrue(!Strings.isNullOrEmpty(end_date),"日期不能为空");


            LocalDateTime startTime = LocalDateTime.parse(start_date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime endTime = LocalDateTime.parse(end_date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            int count = productSkuService.countByDate(startTime, endTime);


            ProductSku productSku = new ProductSku();
            productSku.setMinCreateAt(startTime);
            productSku.setMaxCreateAt(endTime);

            for(int i=0;i<(count/1000+1);i++)
            {
                productSku.setStart(i*1000);
                productSku.setLimit(1000);
                List<ProductSku> productSkuList = productSkuService.findByDate(productSku);

                Optional.ofNullable(productSkuList)
                        .ifPresent(item -> item.stream()
                                .forEach(psku ->{
                    wmsService.insertOrUpdateProductMessage(psku,false);
                    try {
                        TimeUnit.MILLISECONDS.sleep(30);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage(),e);
                    }
                } ));



            }

            modelAndView.addObject("message","处理成功"+count+"条数据！");
        }

        return  modelAndView;

    }



    @RequestMapping("/sql")
    public ModelAndView geneSql()
    {
        ModelAndView modelAndView = new ModelAndView("sql");

        //1，读取xls数据；
        StringBuffer sqlStringBuffer = new StringBuffer("INSERT INTO  category_user_rel(user_id,category_id,department_no,user_type,usable) values");

        try (

                XSSFWorkbook workbook = new XSSFWorkbook(new ClassPathResource("品类设置.xls").getInputStream())
        )
        {

            List<User> userList = userService.findAll();
            List<Category> categoryList = categoryService.findAll();
            List<Department> departmentList = departmentService.findAll();

            List<CategoryUserRel> categoryUserRelList = categoryUserRelService.queryAll();


            List<UserCategory> userCategoryList = Lists.newLinkedList();

            workbook.iterator()
                    .forEachRemaining(sheetAt0->{
                        sheetAt0.rowIterator().forEachRemaining(row->{
                            UserCategory userCategory = new UserCategory();
                            row.cellIterator().forEachRemaining(cell -> {

                                switch (cell.getColumnIndex())
                                {
                                    case 0:  userCategory.setUserName(cell.getStringCellValue());break;
                                    case 1:  userCategory.setCategoryName(cell.getStringCellValue());break;
                                    case 2:  userCategory.setRoleName(cell.getStringCellValue());break;
                                }



                            });

                            if(!Strings.isNullOrEmpty(userCategory.getUserName()) && !Strings.isNullOrEmpty(userCategory.getCategoryName()))
                                userCategoryList.add(userCategory);
                        });

                        //对应id;
                        Optional.ofNullable(userCategoryList)
                                .ifPresent(userCategories ->
                                        userCategories.stream().map(userCategory -> {

                                            userList.stream().filter(user -> userCategory.getUserName().equals(user.getLastname()))
                                                    .findFirst().ifPresent(item->{
                                                userCategory.setUserId(item.getId());

                                                Optional.ofNullable(item.getDepartmentId())
                                                        .ifPresent(departmentId-> departmentList.stream().filter(department -> item.getDepartmentId() == department.getId())
                                                                .findFirst()
                                                                .ifPresent(departmentIdItem->userCategory.setDepartmentNo(departmentIdItem.getDepartmentNo())));

                                            });


                                            categoryList.stream().filter(category->userCategory.getCategoryName().equals(category.getName()))
                                                    .findFirst()
                                                    .ifPresent(item->userCategory.setCategoryId(item.getId()));

                                            userCategory.setRoleId(userCategory.getRoleName().equals("排重专员")?1:0);


                                            return userCategory;
                                        }).filter(filterItem1-> categoryUserRelList.stream()
                                                .filter(categoryUserRel ->
                                                        !(categoryUserRel.getUserId() == filterItem1.getUserId()
                                                                && categoryUserRel.getCategoryId() == filterItem1.getCategoryId()
                                                                &&categoryUserRel.getUserType().ordinal()== filterItem1.getRoleId()
                                                                && categoryUserRel.getUsable())).findAny().isPresent())
                                                .filter(filterItem-> null!=filterItem.getUserId()
                                                        && null!= filterItem.getCategoryId()
                                                        && !Strings.isNullOrEmpty(filterItem.getDepartmentNo()))
                                                .forEach(foreachItem->sqlStringBuffer.append("(").append(foreachItem.getUserId())
                                                        .append(",")
                                                        .append(foreachItem.getCategoryId())
                                                        .append(",'")
                                                        .append(foreachItem.getDepartmentNo())
                                                        .append("',")
                                                        .append(foreachItem.getRoleId())
                                                        .append(",1),")));


                        //生成sql；
//                        userCategoryList.stream().filter(xxx->null== xxx.getCategoryId()).map(UserCategory::getCategoryName).collect(Collectors.toSet()).forEach(System.out::println);
//                        userCategoryList.stream().filter(xxx->null== xxx.getUserId()).map(UserCategory::getUserName).collect(Collectors.toSet()).forEach(System.out::println);




                    });
        }
        catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }

        modelAndView.addObject("sql",sqlStringBuffer.deleteCharAt(sqlStringBuffer.length()-1).toString());
        return  modelAndView;
    }


//    @RequestMapping("/sql2")
//    public ModelAndView geneSql2()
//    {
//
//        StringBuffer stringBuffer = new StringBuffer(" delete from category_user_rel where user_id in (");
//
//        departmentService.findAll().stream().filter()
//
//
//        userService.findAll().stream().filter(userItem->userItem.getDepartmentId())
//
//
//    }


    static class UserCategory{

        private Integer userId ;
        private String userName;

        private Integer categoryId;
        private String categoryName;

        private Integer roleId = 0;//0是广告 1 是排重
        private String  roleName="广告人员";

        private String departmentNo="";


        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public Integer getRoleId() {
            return roleId;
        }

        public void setRoleId(Integer roleId) {
            this.roleId = roleId;
        }

        public String getRoleName() {
            return roleName;
        }

        public void setRoleName(String roleName) {
            this.roleName = roleName;
        }

        public String getDepartmentNo() {
            return departmentNo;
        }

        public void setDepartmentNo(String departmentNo) {
            this.departmentNo = departmentNo;
        }

        @Override
        public String toString() {
            return "UserCategory{" +
                    "userId=" + userId +
                    ", userName='" + userName + '\'' +
                    ", categoryId=" + categoryId +
                    ", categoryName='" + categoryName + '\'' +
                    ", roleId=" + roleId +
                    ", roleName='" + roleName + '\'' +
                    ", departmentNo='" + departmentNo + '\'' +
                    '}';
        }
    }

}
