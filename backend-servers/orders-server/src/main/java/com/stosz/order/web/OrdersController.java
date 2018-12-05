package com.stosz.order.web;

import com.github.pagehelper.PageInfo;
import com.google.common.base.Strings;
import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.JobAuthorityRel;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.ext.service.IJobAuthorityRelService;
import com.stosz.admin.ext.service.IUserDepartmentRelService;
import com.stosz.fsm.model.IFsmHistory;
import com.stosz.order.ext.dto.*;
import com.stosz.order.ext.enums.OrderEventEnum;
import com.stosz.order.ext.enums.OrderStateEnum;
import com.stosz.order.ext.model.OrderDO;
import com.stosz.order.ext.model.UserZone;
import com.stosz.order.service.OrderService;
import com.stosz.order.service.UserZoneService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.common.RestResult;
import com.stosz.plat.common.ThreadLocalUtils;
import com.stosz.plat.common.UserDto;
import com.stosz.plat.enums.TimeRegionEnum;
import com.stosz.plat.model.FsmHistory;
import com.stosz.plat.utils.ExcelUtil;
import com.stosz.plat.utils.IpUtils;
import com.stosz.plat.web.AbstractController;
import com.stosz.product.ext.model.Product;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wangqian
 * 2017-11-6
 * 订单
 */
@RestController
@RequestMapping("orders/orders")
public class OrdersController extends AbstractController {

    @Autowired
    private OrderService orderService;

//    @Autowired
//    private OrdersLinkService ordersLinkService;

    @Resource
    private IJobAuthorityRelService jobAuthorityRelService;

    @Autowired
    private UserZoneService userZoneService;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IUserDepartmentRelService userDepartmentRelService;


    /**
     * 审单统计
     * 实现逻辑：待审单总数：等于表格分页的数据总数 （前端直接取总条数）
     *           今天：查看今天的待审的数 （后端计算）
     *           今天前：待审总数 - 今天，（由前端计算）
     * @param param
     * @return
     */
    @PostMapping("statistics")
    public RestResult ordersChange(OrderSearchParam param){
        RestResult result = new RestResult();

        Map resultMap = new HashMap();

        //预处理
        preHandle(param);

        //获取待审总数
        List<OrderDO> list = orderService.queryBySearchParam(param);
        PageInfo<OrderDO> totalPageInfo = new PageInfo<>(list);
        resultMap.put("historyWaitAuditCount", totalPageInfo.getTotal());


        //今天待审数

        param.setMinShowTime(LocalDate.now().atTime(0,0,0,0));
        param.setMaxShowTime(LocalDate.now().atTime(23,59,59,59));
        list = orderService.queryBySearchParam(param);
        PageInfo<OrderDO> todayPageInfo = new PageInfo<>(list);
        resultMap.put("todayWaitAuditCount", todayPageInfo.getTotal());

        //今天前待审
        long beforeTodayCount = totalPageInfo.getTotal() -  todayPageInfo.getTotal();
        beforeTodayCount = beforeTodayCount >= 0 ? beforeTodayCount :0;
        resultMap.put("beforeTodayWaitAuditCount", beforeTodayCount);

        result.setItem(resultMap);
        result.setCode(RestResult.OK);
        result.setDesc("查询成功");
        return result;
    }

    /**
     * 订单列表页
     * @param param
     * @return
     */
    @RequestMapping("find")
    public RestResult find(OrderSearchParam param) {
        RestResult result = new RestResult();

        //预处理
        preHandle(param);

//        PageHelper.offsetPage(param.getStart(),param.getLimit());
        List<OrderDO> list = orderService.queryBySearchParam(param);
//        PageInfo<OrderDO> pageInfo = new PageInfo<>(list);
//        result.setTotal((int) pageInfo.getTotal());

        result.setTotal(10000);
        result.setItem(list);
        result.setCode(RestResult.OK);
        result.setDesc("查询成功");
        return result;
    }




    /**
     * 订单摘要
     * @param id
     * @return
     */
    @RequestMapping("order_abstract")
    public RestResult order_abstract(@RequestParam("orderId") Integer id) {
        RestResult result = new RestResult();
        OrderDetailDTO dto = orderService.findOrderAbstractByOrderId(id);
        result.setItem(dto);
        result.setCode(RestResult.OK);
        result.setDesc("查询成功");
        return result;
    }
    /**
     * 状态机历史记录
     * @param id
     * @return
     */
    @RequestMapping("queryFsmHistory")
    public RestResult queryFsmHistory(@RequestParam("orderId") Integer id,
                                      @RequestParam(value = "start", required = false, defaultValue = "0") Integer start,
                                      @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
        RestResult  searchResult = orderService.queryHistory(id,start,limit);
        return searchResult;
    }
    @Deprecated
    private void fillHistoryEnumDisplay(List<IFsmHistory> lst) {
        if (lst == null || lst.isEmpty()) {
            return;
        }
        for (IFsmHistory fsmHistory : lst) {
            if (!(fsmHistory instanceof FsmHistory)) {
                continue;
            }
            FsmHistory h = (FsmHistory) fsmHistory;
            String fsmName = h.getFsmName();
            h.setEventNameDisplay(MBox.getDisplayByFsmEnum(OrderEventEnum.class, h.getEventName()));
            h.setSrcStateDisplay(MBox.getDisplayByFsmEnum(OrderStateEnum.class,h.getSrcState()));
            h.setDstStateDisplay(MBox.getDisplayByFsmEnum(OrderStateEnum.class, h.getDstState()));
        }
    }

    /**
     * 查询产品信息
     * @param webUrl
     * @return
     */
    @RequestMapping("getProductByWebUrl")
    public RestResult getProductByWebUrl(@RequestParam("webUrl") String webUrl) {
        RestResult result = new RestResult();
        List<Product> products = orderService.findProductByWebUrl(webUrl);
        result.setItem(products);
        result.setCode(RestResult.OK);
        result.setDesc("查询成功");
        return result;
    }


    /**
     * 批量查询订单
     * @param type BatchQueryEnum
     * @param content
     * @return
     */
    @RequestMapping("batch_query")
    public RestResult orderBatchQuery(@RequestParam("type") Integer type, @RequestParam("content") String content){
        RestResult result = new RestResult();
        OrderSearchParam param = new OrderSearchParam();
        handleBatchQueryInput(content);
        handlePermission(param);
        // TODO: 2017/12/23  wangqian 调用层级混乱
        BatchQueryResultDTO dto = orderService.batchQuery(type,content,param);
        result.setDesc("查询成功");
        result.setItem(dto);
        result.setCode(RestResult.OK);
        return result;
    }


    /**
     * 导出动作，从session中获取查询条件并查询导出
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "export")
    public void export(HttpServletRequest request, HttpServletResponse response) {
        OrderSearchParam param = (OrderSearchParam) request.getSession().getAttribute("orderExport");
        Assert.notNull(param,"找不到搜索参数");
        request.getSession().removeAttribute("orderExport");
        List<OrderDO> list = orderService.queryBySearchParam(param);
        List<OrderExport> exportLists = list.stream().map(o -> {
            OrderExport oe = new OrderExport();
//            oe.setId(o.getId());
            oe.setMerchantOrderNo(o.getMerchantOrderNo());
            oe.setArea(o.getArea());
            oe.setSeoUserName(o.getSeoUserName());
            oe.setDomain(o.getDomain());
            String customerName =  o.getFirstName() == null ? "": o.getFirstName();
            customerName = customerName + o.getLastName() == null ? "" : o.getLastName();
            oe.setCustomerName(customerName);
            oe.setForeignProductTitle(o.getOrderTitle());
            // TODO: 2017/11/28 内部名？
            oe.setInnerProducctTitle(o.getOrderTitle());
            oe.setPrice(o.getAmountShow());
            String attr = "";
            String sku = "";
            for(OrderItemSpuDTO spuList: o.getSpuList()){
                sku = spuList.getSkuList().stream().map(e -> e.getSku()).collect(Collectors.joining(","));
                attr = spuList.getSkuList().stream().map(s -> s.getSku() + "*" + s.getQty()).collect(Collectors.joining(","));
                sku += ",";
                attr += ",";
            }
            oe.setAtrr(attr);
            oe.setSku(sku);
            oe.setAddress(o.getAddress());
            oe.setQty(o.getGoodsQty());
            oe.setMemo(o.getCustomerMessage());
            oe.setPurchaseAt(null);
            oe.setAssignAt(null);
            oe.setOnlineAt(null);
            oe.setWarehoure(o.getWarehouseName());
            oe.setOrderState(o.getOrderStatusEnum().display());
            oe.setDeliveringAt(null);
            oe.setLogisticsName(o.getLogisticsName());
            oe.setTrackingNo(o.getTrackingNo());
            oe.setLogisticsState(o.getTrackingStatusShow());
            oe.setPayState(o.getPayState().display());
            return oe;
        }).collect(Collectors.toList());

        String[] excludeFields = {"blackLevelKey","creatorId"};
        String[] headers = {"订单号","地区","广告专员","域名","姓名","外部产品名","产品内部名"
                ,"总价","属性","sku","地址","购买产品数量","留言备注","下单时间","到货时间","上线时间","仓库"
                ,"订单状态","发货日期","物流名称","运单号","物流状态","结款状态"};
        try {
            ExcelUtil.exportExcelAndDownload(response,"订单导出","导出数据",headers,exportLists,excludeFields);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return;
    }

    /**
     * 导出预操作，目的是将查询条件存在session中。ajax无法下载文件。
     * @param param
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("pre_export")
    public RestResult preExport(OrderSearchParam param, HttpServletRequest request) throws IOException {
        request.getSession().setAttribute("orderExport", param);
        RestResult result = new RestResult();
        result.setDesc("请求成功");
        result.setCode(RestResult.OK);
        return result;
    }


    /**
     * 列表页-更新备注
     * @param memo
     * @param orderId
     * @return
     */
    @PostMapping("memo_update")
    @ResponseBody
    public RestResult updateOrderMemo(@RequestParam("memo") String memo, @RequestParam("orderId") Integer orderId) {
        RestResult result = new RestResult();
        orderService.updateOrderMemo(orderId,memo);
        result.setCode(RestResult.NOTICE);
        result.setDesc("更新备注成功");
        logger.info("更新备注成功:orderId = {}, memo = {}", orderId, memo);
        return result;
    }
    
    /**
     * 订单编辑
     * @param orderDetailDTO
     * @return
     * @throws Exception
     **/
    @RequestMapping("orderEdit")
    public RestResult orderEdit(@RequestBody OrderDetailDTO orderDetailDTO) {
        RestResult result = new RestResult();
        orderService.orderEdit(orderDetailDTO);
        result.setCode(RestResult.NOTICE);
        result.setDesc("修改成功");
        return result;
    }

    /**
     * 取消订单
     * @param orderId
     * @param memo
     * @param reasonType 请参考 OrderCancelReasonEnum
     * @return
     * @throws Exception
     */
    @PostMapping("orderCancel")
    public RestResult orderCancel(@RequestParam("orderId") Integer orderId, @RequestParam("memo") String memo, @RequestParam("reasonType") String reasonType) throws Exception {
        RestResult result = new RestResult();
        orderService.orderCancel(orderId,reasonType,memo);
        result.setCode(RestResult.NOTICE);
        result.setDesc("取消成功");
        return result;
    }
    /**
     * 退换货处理
     * @param id
     * 
     * @return
     * @throws Exception 
     */
    
    @RequestMapping("ordersChange")
    public RestResult ordersChange(@RequestParam("orderId") Integer id) throws Exception {
        RestResult result = new RestResult();
        //orderService.ordersChange(id);
        result.setCode(RestResult.NOTICE);
        result.setDesc("退货成功");
        return result;
    }



    private void preHandle(OrderSearchParam param){

        // 权限处理，将部门及数据权限填充到param字段中
        handlePermission(param);

        //关键词转换成特定搜索条件
        handleKeyWord(param);

        //处理分页和搜索时间区域
        handlePageAndTime(param);
    }

    private void handleBatchQueryInput(String param) {
        if(param != null && param.contains(",")){
            throw new RuntimeException("数据包含特殊字符，请重新输入");
        }
    }

    private void handlePageAndTime(OrderSearchParam param) {
        if(param.getLimit() == null || param.getStart() == null){
            param.setStart(0);
            param.setLimit(20);
        }

        if(param.getTimeRegion() != null){
            param.setTimeRegionBegin(param.getTimeRegion().getTimeRegionPair().getLeft());
            param.setTimeRegionEnd(param.getTimeRegion().getTimeRegionPair().getRight());
        }else{
            param.setTimeRegionBegin(TimeRegionEnum.InThreeMonth.getTimeRegionPair().getLeft());
            param.setTimeRegionEnd(TimeRegionEnum.InThreeMonth.getTimeRegionPair().getRight());
        }
    }

    private void handleKeyWord(OrderSearchParam param) {
        String keyWord = param.getKeyWord();
        if (!Strings.isNullOrEmpty(keyWord)) {
            if(keyWord.contains("@")) {
                param.setEmail(keyWord.replaceAll("\\s*", ""));
            } else if (IpUtils.ipCheck(keyWord)) {
                param.setIp(keyWord.replaceAll("\\s*", "") );
            } else if (keyWord.startsWith("www.")) {
                param.setDomain(keyWord.replaceAll("\\s*", "") );
            } else if (StringUtils.isNumeric(keyWord)) {
                param.setTel(keyWord.replaceAll("\\s*", ""));
            } else {
                param.setAddress(keyWord);
            }
        }
    }

    private void handlePermission(OrderSearchParam param) {
        //用户
        UserDto userDto = ThreadLocalUtils.getUser();
        param.setDeptId(userDto.getDeptId());
        param.setUserId(userDto.getId());

        //获取查询部门的所有子部门
        List<Integer> depts = null;
        if(param.getDepartmentId() != null){
            Department department = departmentService.get(param.getDepartmentId());
            if(department != null) {
                List<Department> departmentList = departmentService.findByNo(department.getDepartmentNo());
                depts = departmentList.stream().map(e -> e.getId()).collect(Collectors.toList());
                param.setSearchDepts(depts);
            }
        }

        //权限类型（公司，部门，个人）
        JobAuthorityRel jobAuthorityRel = jobAuthorityRelService.getByUser(userDto.getId());
        param.setAuthorityType(jobAuthorityRel.getJobAuthorityRelEnum().ordinal());
        List<Integer> authorityDepts = userDepartmentRelService.findDeptIdByUser(userDto.getId());
        param.setAuthorityDepts(authorityDepts);

        //地区权限
        List<UserZone>  userZoneList = userZoneService.findEnableUserZoneByUserId(userDto.getId());
        List<Integer> zoneIds = userZoneList.stream().map(z -> z.getZoneId()).collect(Collectors.toList());
        param.setZoneIds(zoneIds);
    }



}
