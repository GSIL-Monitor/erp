package com.stosz.product.deamon;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stosz.admin.ext.model.Department;
import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IDepartmentService;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.plat.common.MBox;
import com.stosz.plat.enums.SystemEnum;
import com.stosz.plat.rabbitmq.MailSender;
import com.stosz.plat.service.ProjectConfigService;
import com.stosz.plat.utils.HttpClient;
import com.stosz.product.ext.enums.ProductZoneEvent;
import com.stosz.product.ext.enums.ProductZoneState;
import com.stosz.product.ext.model.Product;
import com.stosz.product.ext.model.ProductZone;
import com.stosz.product.service.ProductService;
import com.stosz.product.service.ProductZoneService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 产品区域后台程序
 */
@Service
public class ProductZoneDeamon {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    int taskCount = 500;

    @Resource
    private ProductZoneService productZoneService;
    @Resource
    private ProductService productService;
    @Resource
    private MailSender mailSender;
    @Resource
    private IDepartmentService iDepartmentService;
    @Resource
    private IUserService iUserService;

    @Autowired
    private ProjectConfigService projectConfigService;
    @Value("${token}")
    private String token;

    @Value("${systemId}")
    private String systemId;
    @Value("${mailbox}")
    private String mailbox;
    @Value("${mailboxUserId}")
    private String mailboxUserId;
    @Value("${warningHour}")
    private Integer warningHour;
    @Value("${waitSaleHour}")
    private Integer waitSaleHour;
    @Value("${mailType}")
    private String mailType;
    @Value("${emailSwitch}")
    private Boolean emailSwitch;
    @Value("${product.noConsumerDeptIds}")
    private String noDisDeptIds;

    private Set<Integer> noDisDeptList = new HashSet<>();

    /**
     * 预警调度
     */
    public void doWarningTask() {
        try {
            logger.info("开始调度五天无订单预警功能[时间:" + new Date() + "]");
            warningForNoOrder();
            logger.info("结束调度五天无订单预警功能[时间:" + new Date() + "]");
        } catch (Exception e) {
            logger.error("调度五天无订单预警发生异常！", e);
        }
    }

    /**
     * 销档任务调度，应该每天跑一次的
     */
    public void doWaitoffsaleTask() {
        try {
            logger.info("开始调度无订单==>[待下架]任务");
            waitsaleForNoOrder();
            logger.info("结束无订单==>[待下架]任务");
        } catch (Exception e) {
            logger.error("调度无订单==>[待下架]任务发生异常！", e);
        }
    }

    /**
     * 未备案消档
     */
    public void noArchiveTask(){
        try {
            logger.info("开始调度未备案==>[销档]任务");
            doDisppearedForNoArchive();
            logger.info("结束未备案==>[销档]任务");
        }catch (Exception e){
            logger.error("调度未备案==>[销档]任务发生异常！", e);
        }
    }

    /**
     * 待下架消档任务调度
     */
    public void doDisppearedTask() {
        logger.info("开始调度待下架[待下架]==>[消档]任务");
        doDisppearedForNoOrder();
        logger.info("结束调度待下架[待下架]==>[消档]任务");
    }

    /**
     * 特殊部门,不受七天无订单的消档影响
     */
    private Set<Integer> getNoDisDeptIdSet() {
        try {
            if(noDisDeptList.isEmpty()){
                String[] strs = noDisDeptIds.split(",");
                List<Integer> deptList = new ArrayList<>();
                for (int i = 0; i < strs.length; i++) {
                    deptList.add(Integer.parseInt(strs[i]));
                }
                List<Department> list = iDepartmentService.findByIds(deptList);
                if (list == null || list.size() == 0){
                    noDisDeptList.add(0);
                    return noDisDeptList;
                }
                List<String> noList = list.stream().map(Department::getDepartmentNo).collect(Collectors.toList());
                List<Department> ListResult = iDepartmentService.findByNos(noList);
                noDisDeptList = ListResult.stream().map(Department::getId).collect(Collectors.toSet());
            }
        } catch (NumberFormatException e) {
            logger.error("初始化失败，{}！",e);
        }
        if (noDisDeptList == null || noDisDeptList.size() == 0){
            noDisDeptList.add(0);
        }
        return noDisDeptList;
    }


    /**
     * 五天未出单,预警功能
     */
    private void warningForNoOrder() {
        LocalDate currentDate = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.of(currentDate.minusDays(MBox.WARNING_DAY_ORDER), LocalTime.of(warningHour, 0, 0));
        int count = productZoneService.countWarningForNoOrder(dateTime, ProductZoneState.onsale.name(), getNoDisDeptIdSet());
        logger.info("五天未出单,预警总条数为: {}", count);
        if (count == 0) {
            return;
        }
        List<String> failDataList = new ArrayList<>();
        Map<Integer, List<Product>> bufferMap = new HashMap<>();
        for (int i = 0; i <= count; i += taskCount) {
            List<ProductZone> zoneList = productZoneService.findWarningForNoOrder(dateTime, ProductZoneState.onsale.name(), taskCount, i, getNoDisDeptIdSet());
            Map<Integer, Map<Integer, List<ProductZone>>> map =
                    zoneList.parallelStream().collect(Collectors.groupingBy(ProductZone::getDepartmentId, Collectors.groupingBy(ProductZone::getProductId)));
            takeMapData(bufferMap, map, dateTime, ProductZoneState.onsale, true);
        }
        //邮件发送
        mail("[" + mailType + "]已建站5天预警邮件", bufferMap, failDataList, true,
                new String[]{
                        "您好！您以下产品已连续5天未出单，请注意检查调整，连续7天未出单会自动消档：",
                        "注：产品消档/下架后，该产品所有地区域名的站点会自动关闭，客户将不可访问，如需恢复，则重新排重通过即可"});
        logger.info("由于5天无订单,已发邮件预警通知!");
        emailReceipt("5天预警失败邮件", failDataList);
        logger.info("5天预警邮件回执完成!");
    }

    /**
     * 七天未下单  产品区域由上架变成待下架
     */
    private void waitsaleForNoOrder() {
        LocalDate currentDate = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.of(currentDate.minusDays(MBox.DISPPEARED_DAY_ORDER), LocalTime.of(waitSaleHour, 0, 0));
        int number = productZoneService.countWarningForNoOrder(dateTime, ProductZoneState.onsale.name(), getNoDisDeptIdSet());
        logger.info("七天未下单,产品区域由上架变成待下架总条数为: {}", number);
        if (number == 0) {
            return;
        }
        List<String> failDataList = new ArrayList<>();
        Map<Integer, List<Product>> bufferMap = new HashMap<>();
        Set<Integer> zoneIds = new HashSet<>();
        for (int i = 0; i <= number; i += taskCount) {
            List<ProductZone> zoneList = productZoneService.findWarningForNoOrder(dateTime, ProductZoneState.onsale.name(), taskCount, i , getNoDisDeptIdSet());
            Map<Integer, Map<Integer, List<ProductZone>>> map =
                    zoneList.parallelStream().collect(Collectors.groupingBy(ProductZone::getDepartmentId, Collectors.groupingBy(ProductZone::getProductId)));
            takeMapData(bufferMap, map, dateTime, ProductZoneState.onsale, false);
            map.forEach((deptId, value) -> {
                value.forEach((productId, value2) -> {
                    int countOnsale = productZoneService.coutnLastOnsaleTime(deptId, productId, dateTime, ProductZoneState.onsale.name());
                    int count = productZoneService.countNorderFixDay(deptId, productId, dateTime);
                    //count为0,表示可以全部修改为待下架,否则,不修改
                    if (countOnsale == 0 && count == 0) {
                        zoneIds.addAll(value2.stream().map(ProductZone::getId).collect(Collectors.toSet()));
                    }
                });
            });
        }
        //状态修改
        ProductZone pz = new ProductZone();
        pz.setState(ProductZoneState.onsale.name());
        pz.setStateTime(LocalDateTime.now());
        for (Integer zoneId : zoneIds) {
            pz.setId(zoneId);
            try {
                productZoneService.processEvent(pz, ProductZoneEvent.waitoffsale, "七天无订单待下架！", MBox.getSysUser());
                pz.setState(ProductZoneState.onsale.name());
                logger.info("产品由于7天无订单,产品区域[ID:" + zoneId + "]状态由上架被修改成待下架!");
            } catch (Exception e) {
                logger.info("产品由于7天无订单,产品区域[ID:" + zoneId + "]状态由上架被修改成待下架时,发生异常!");
                logger.error(e.getMessage(),e);
            }
        }
        logger.info("由于7天无订单产品区域状态被修改成待下架,本次一共执行了：{} 个", zoneIds.size());
        mail("[" + mailType + "]已建站7天消档/下架邮件", bufferMap, failDataList, false, new String[]{
                "您好！您的产品因连续7天未出单,已经消档,系统将在次日凌晨关站,请停止广告投放,产品信息如下.",
                ""});
        logger.info("由于7天无订单产品区域状态被修改成待下架,已发邮件通知!");
        emailReceipt("七天未出单变成待下架失败邮件", failDataList);
        logger.info("7天未出单状态修改成待下架,邮件回执完成!");
    }


    /**
     * 待下架变成 消档/下架  状态
     */
    private void doDisppearedForNoOrder() {
        LocalDate currentDate = LocalDate.now();
        LocalDateTime dateTime = LocalDateTime.of(currentDate, LocalTime.of(waitSaleHour, 0, 0)).minusMinutes(MBox.DISPPEARED_WAIT_TIME_MINUTE);
        List<String> failList = new ArrayList<>();              //关站   失败数据
        int count = productZoneService.countWaitOffsale(ProductZoneState.waitoffsale.name(), dateTime);
        logger.info("七天未下单,产品区域由待下架变成消档/下架总条数为: {}", count);
        if (count == 0) {
            return;
        }
        int number = count / taskCount;
        if (count % taskCount != 0) ++number;
        for (int i = 0; i < number; i++) {
            //直接调用状态机的after事件
            List<ProductZone> list = productZoneService.findWaitOffSale(ProductZoneState.waitoffsale, dateTime, taskCount);
            Map<Integer, List<ProductZone>> map = list.stream().collect(Collectors.groupingBy(ProductZone::getDepartmentId));
            for (Integer deptId : map.keySet()) {
                List<ProductZone> deptProductZones = map.get(deptId);
                Department dept = iDepartmentService.get(deptId);
                if (dept == null) {
                    logger.info("产品区域中的部门ID[" + deptId + "]在部门表中找不到对应的信息 ,直接返回!");
                    return;
                }
                //1.可以定义保存产品id的集合
                Set<Integer> productIds = new HashSet<>();
                for (ProductZone pz : deptProductZones) {
                    if (pz.getStock() == 0) {
                        productZoneService.processEvent(pz, ProductZoneEvent.cancel, "待下架的销档！", MBox.getSysUser());
                        logger.info("[" + deptId + "]部门的[" + pz.getProductId() + "]产品区域[" + pz.getZoneName() + "]由待下架变成已消档!");
                        productIds.add(pz.getProductId());
                    } else {
                        productZoneService.processEvent(pz, ProductZoneEvent.offsale, "待下架的下架！", MBox.getSysUser());
                        logger.info("[" + deptId + "]部门的[" + pz.getProductId() + "]产品区域[" + pz.getZoneName() + "]由待下架变成下架!");
                        productIds.add(pz.getProductId());
                    }
                }
//                //2.部门站点的关闭
//                for (Integer pcId : productIds) {
//                    String oldId = "";
//                    if (dept.getOldId() != null) {
//                        oldId = dept.getOldId().toString();
//                    }
//                    offsaleCloseSite(systemId, pcId.toString(),
//                            deptId.toString(), oldId,
//                            System.currentTimeMillis() / 1000, System.currentTimeMillis() / 1000, failList);
//                    logger.info("[" + deptId + "]部门的[" + pcId + "]产品区域[" + pcId + "]关站成功!");
//                }
//                logger.info("关站产品ID: {} ", productIds);
            }
        }
        if (failList != null && failList.size() > 0) {
            mailSender.sendEmail(mailbox, "下架站点关闭失败", mailTemplate1(failList));
            failList.clear();
        }
    }

    /**
     * 三天未备案
     */
    private void doDisppearedForNoArchive() {
        List<ProductZone> zoneList = productZoneService.findNoArchiveForDisappeared(MBox.DISPPEARED_DAY_ARCHIVE, taskCount);
        for (ProductZone productZone : zoneList) {
            productZoneService.processEvent(productZone, ProductZoneEvent.cancel, "未备案销档！", MBox.getSysUser());
            logger.info("产品区域由于未建档被销档,{}", productZone);
        }
        if (zoneList.size() == taskCount) {
            logger.info("本次销档了:{} 个产品区域，可能还存在未查完需要销档的，继续递归查询！", zoneList.size());
            doDisppearedForNoArchive();
        } else {
            logger.info("本次销档了:{} 个产品区域，计划查询项目:{} 个,已经不需要再查了。", zoneList.size(), taskCount);
        }
        logger.info("本次未建档销档执行了：{} 个", zoneList.size());
    }

    /**
     * 将Map的数据放入到map缓冲区(需要对原始数据进行数据处理)productZoneState
     */
    private void takeMapData(Map<Integer, List<Product>> bufferMap, Map<Integer, Map<Integer, List<ProductZone>>> param,
                             LocalDateTime time, ProductZoneState state, Boolean sign) {
        Set<Integer> keySet = param.keySet();
        if (keySet == null || keySet.size() == 0) {
            return;
        }
        List<Department> deptList = iDepartmentService.findByIds(new ArrayList<>(keySet));
        Map<Integer, Department> deptMap = deptList.stream().collect(Collectors.toMap(Department::getId, Function.identity()));
        for (Integer deptId : keySet) {
            Map<Integer, List<ProductZone>> value = param.get(deptId);
            List<Product> bufferList = new ArrayList<>();
            for (Integer productId : value.keySet()) {
                //判断该产品是否需要邮件通知,判断该部门的某一产品的最后下单时间在day天内有单
                int countOnsale = productZoneService.coutnLastOnsaleTime(deptId, productId, time, ProductZoneState.onsale.name());
                int count = productZoneService.countNorderFixDay(deptId, productId, time);
                logger.info("产品销档线程，获取数据，部门:{} 产品：{} 时间之前:{} 计算出productZone数量: {} ", deptId, productId, time, count);
                if (countOnsale == 0 && count == 0) {
                    Product product = productService.getById(productId);
                    if (product == null) {
                        logger.info("产品销档线程根据产品id:{}没有从数据库中找到，忽略", productId);
                        continue;
                    }
                    List<ProductZone> listZone = productZoneService.findByDeptPcId(deptId, productId);
                    StringBuilder sb = new StringBuilder();
                    StringBuilder creator = new StringBuilder();
                    if (listZone != null && listZone.size() > 0) {
                        if (state != null) {
                            listZone = listZone.stream().filter(e -> state.name().equals(e.getState()))
                                    .collect(Collectors.toList());
                        }
                        if (sign) {
                            LocalDateTime maxTime = productZoneService.maxLastOrderTime(deptId, productId);
                            if (maxTime == null) {
                                product.setDays(0);
                                continue;
                            } else {
                                int day = time.getDayOfYear() - maxTime.getDayOfYear();
                                if (day == 0) day = 2;
                                if (day > 2) continue;
                                product.setDays(day);
                            }
                        }
                        for (ProductZone lst : listZone) {
                            sb.append(lst.getZoneName() + " |");
                            if (creator.indexOf(lst.getCreator()) == -1) {
                                creator.append(lst.getCreator() + ",");
                            }
                        }
                    }
                    if (creator.length() > 0) {
                        creator.deleteCharAt(creator.length() - 1);
                    }
                    product.setDepartmentName(deptMap.get(deptId).getDepartmentName());
                    product.setCreator(creator.toString());
                    product.setDepartmentId(deptId);
                    product.setZoneName(sb.toString());
                    //判断是否添加到list中
                    List<Product> list = bufferMap.get(deptId);
                    if (list != null && list.size() != 0) {
                        Set<Integer> ids = list.stream().map(Product::getId).collect(Collectors.toSet());
                        if (ids.contains(product.getId())) {
                            continue;
                        }
                    }
                    bufferList.add(product);
                }
            }
            if (bufferMap.get(deptId) != null && bufferMap.get(deptId).size() > 0) {
                List<Product> list = bufferMap.get(deptId);
                list.addAll(bufferList);
                bufferMap.put(deptId, list);
            } else {
                bufferMap.put(deptId, bufferList);
            }
        }
    }

    /**
     * 发送邮件给部门主管
     */
    private void mail(String mailName, Map<Integer, List<Product>> map, List<String> failDataList, Boolean sign, String[] memo) {
        if (map == null || map.size() == 0) {
            logger.info("邮件没有数据可以发送,直接返回!");
            return;
        }
        if (!emailSwitch) {
            logger.info("邮件没有打开,不发送邮件,直接返回!");
            return;
        }
        //获取部门主管邮箱   key为部门id.value为邮箱
        Map<Integer, String> mailInfo = getDeptMail(new ArrayList<>(map.keySet()));
        for (Iterator<Integer> it = map.keySet().iterator(); it.hasNext(); ) {
            Integer key = it.next();
            String email = mailInfo.get(key);
            try {
                if (email != null) {
                    if (map.get(key) != null && map.get(key).size() > 0) {
                        if (sign) {
                            mailSender.sendEmail(mailInfo.get(key), mailName, warningEmailTemplate(map.get(key), memo));    //预警邮件
                        } else {
                            mailSender.sendEmail(mailInfo.get(key), mailName, disppearedEmailTemplate(map.get(key), memo));    //消档邮件
                        }
                    }
                } else {
                    mailSender.sendEmail(mailbox, "[" + mailType + "]邮箱为空的邮件", warningEmailTemplate(map.get(key), memo));
                }
            } catch (Exception e) {
                List<Integer> ids = map.get(key).stream().map(Product::getId).collect(Collectors.toList());
                failDataList.add("给部门[ID:" + key + "]邮箱[" + mailInfo.get(key) + "]发送邮件[产品ID:" + ids + "]失败!");
                List products = map.get(key);
                String str = products == null ? "无数据" : "产品总数:" + products.size() + "-->" + StringUtils.abbreviate(products.toString(), 120);
                logger.info("邮件发送失败! dept:{} email:{} content:{}", key, email, str, e);
            }
        }
    }

    /**
     * 下架的产品关闭站点
     */
    public void offsaleCloseSite(String loginid, String erp_id,
                                 String id_department, String oldid_department,
                                 long act_time, long timestamp, List<String> failList) {
        StringBuilder sb = new StringBuilder();
        String sign = autograph(loginid, erp_id, id_department, oldid_department, act_time, timestamp);
        sb.append("&loginid=" + loginid);
        sb.append("&erp_id=" + erp_id);
        sb.append("&id_department=" + id_department);
        sb.append("&oldid_department=" + oldid_department);
        sb.append("&act_time=" + act_time);
        sb.append("&timestamp=" + timestamp);
        sb.append("&sign=" + sign);
        String param = sb.toString();
        HttpClient http = new HttpClient();
        String closeStationUrl = projectConfigService.getSystemHttp(SystemEnum.superMary);
        closeStationUrl +="/api_product.php?act=deleteProduct";
        String str = http.pub2(closeStationUrl, param);
        if (str == null && "".equals(str)) {
            //失败
            failList.add("关站失败,没有返回信息:请求参数如下loginId=" + loginid + "产品id=" + erp_id + "部门id=" + id_department + "旧部门ID=" + oldid_department + "操作时间戳" + act_time + "请求时间戳" + timestamp +
                    "请求地址:(" + closeStationUrl + param + ")响应为:" + str);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            HashMap map = objectMapper.readValue(str, HashMap.class);
            if (map.get("res") != null || !"".equals(map.get("res"))) {
                if ("succ".equals(map.get("res"))) {
                    logger.info("产品[相关信息:loginid:" + loginid + ",产品id:" + erp_id + "]下架成功!");
                } else {
                    //失败
                    failList.add("关站失败,返回的res不为[succ]:请求参数如下loginId=" + loginid + "产品id=" + erp_id + "部门id=" + id_department + "旧部门ID=" + oldid_department + "操作时间戳" + act_time + "请求时间戳" + timestamp +
                            "请求地址:(" + closeStationUrl + param + ")响应为:" + str);
                }
            } else {
                //失败
                failList.add("关站失败,获取不到返回信息(get(res)为空):请求参数如下loginId=" + loginid + "产品id=" + erp_id + "部门id=" + id_department + "旧部门ID=" + oldid_department + "操作时间戳" + act_time + "请求时间戳" + timestamp +
                        "请求地址:(" + closeStationUrl + param + ")响应为:" + str);
            }
        } catch (IOException e) {
            //失败
            failList.add("关站失败,解析返回值的时候发生异常:请求参数如下loginId=" + loginid + "产品id=" + erp_id + "部门id=" + id_department + "旧部门ID=" + oldid_department + "操作时间戳" + act_time + "请求时间戳" + timestamp +
                    "请求地址:(" + closeStationUrl + param + ")响应为:" + str);
        }
    }

    /**
     * 与建站的交互  签名算法
     * 字符串-->md5加密-->转大写-->大写+token-->md5-->大写
     */
    private String autograph(String loginid, String erp_id,
                             String id_department, String oldid_department,
                             long act_time, long timestamp) {
        StringBuilder sb = new StringBuilder();
        String str = "";
        if (loginid == null) loginid = "";
        if (erp_id == null) erp_id = "";
        if (oldid_department == null) oldid_department = "";
        sb.append("act_time" + act_time);
        sb.append("erp_id" + erp_id);
        sb.append("id_department" + id_department);
        sb.append("loginid" + loginid);
        sb.append("oldid_department" + oldid_department);
        sb.append("timestamp" + timestamp);
        str = DigestUtils.md5Hex(sb.toString());
        str = str.toUpperCase();
        str = str + token;
        str = DigestUtils.md5Hex(str);
        str = str.toUpperCase();
        return str;
    }

    /**
     * 获取当前部门的主管邮箱
     * 参数为部门id的集合
     */
    public Map<Integer, String> getDeptMail(List<Integer> param) {
        Map<Integer, String> mailInfo = new HashMap<>();
        List<Department> deptList = iDepartmentService.findByIds(param);
        Set<String> master = deptList.stream().map(Department::getMasterId).collect(Collectors.toSet()); //得到对应有用户id,查询用户信息
        Set<Integer> masterId = new HashSet<>();
        for (String str : master) {
            if (str.split(",")[0] == null || "".equals(str.split(",")[0])) str = mailboxUserId;
            masterId.add(Integer.valueOf(str.split(",")[0]));
        }
        List<User> userList = iUserService.findByIds(masterId);
        for (User u : userList) {
            mailInfo.put(u.getDepartmentId(), u.getEmail());
        }
        return mailInfo;
    }

    /**
     * 邮件回执
     * 失败邮件提醒开发
     */
    private void emailReceipt(String info, List<String> failDataList) {
        if (failDataList != null && failDataList.size() > 0) {
            mailSender.sendEmail(mailbox, info, mailTemplate1(failDataList));
        }
    }

    /**
     * 邮件内容模板
     */
    private String warningEmailTemplate(List<Product> list, String[] memo) {
        StringBuilder sb = new StringBuilder();
        if (list == null || list.size() == 0) {
            sb.append("没有要消档的产品信息!");
            return sb.toString();
        }
        sb.append("<span style='font-size:18px'>" + list.get(0).getDepartmentName() + ":</span></br>");
        sb.append("<span style='font-size:18px'>" + memo[0] + "</span></br>");
        sb.append("<table border=\"1\" cellspacing=\"0\"><tr><td width=\"100px\">产品ID</td><td width=\"250px;\" style=\"text-align:center;\">相关创建人</td>" +
                "<td width=\"400px;\" style=\"text-align:center;\">产品名称</td>" +
                "<td width=\"400px;\" style=\"text-align:center;\">剩余天数</td>" +
                "<td width=\"2000px;\" style=\"text-align:center;\">区域</td>" +
                "</tr>");
        for (Product product : list) {
            sb.append("<tr>" +
                    "<td>" + product.getId() + "</td>" +
                    "<td>" + product.getCreator() + "</td>" +
                    "<td>" + product.getTitle() + "</td>" +
                    "<td>" + product.getDays() + "</td>" +
                    "<td>" + product.getZoneName() + "</td>" +
                    "</tr>");
        }
        sb.append("</table>");
        sb.append("<span style='color:red;font-size:20px'>" + memo[1] + "</span><br/>");
        return sb.toString();
    }

    /**
     * 邮件内容模板
     */
    private String disppearedEmailTemplate(List<Product> list, String[] memo) {
        StringBuilder sb = new StringBuilder();
        if (list == null || list.size() == 0) {
            sb.append("没有要消档的产品信息!");
            return sb.toString();
        }
        sb.append("<span style='font-size:18px'>" + list.get(0).getDepartmentName() + ":</span></br>");
        sb.append("<span style='font-size:18px'>" + memo[0] + "</span></br>");
        sb.append("<table border=\"1\" cellspacing=\"0\"><tr><td width=\"100px\">产品ID</td><td width=\"250px;\" style=\"text-align:center;\">相关创建人</td>" +
                "<td width=\"400px;\" style=\"text-align:center;\">产品名称</td>" +
                "<td width=\"2000px;\" style=\"text-align:center;\">区域</td>" +
                "</tr>");
        for (Product product : list) {
            sb.append("<tr>" +
                    "<td>" + product.getId() + "</td>" +
                    "<td>" + product.getCreator() + "</td>" +
                    "<td>" + product.getTitle() + "</td>" +
                    "<td>" + product.getZoneName() + "</td>" +
                    "</tr>");
        }
        sb.append("</table>");
        sb.append("<span style='color:red;font-size:20px'>" + memo[1] + "</span><br/>");
        return sb.toString();
    }

    /**
     * 邮件内容模板
     */
    private String mailTemplate1(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append("<span style='color:red;font-size:15px'>" + str + "</span><br/>");
        }
        return sb.toString();
    }

}
