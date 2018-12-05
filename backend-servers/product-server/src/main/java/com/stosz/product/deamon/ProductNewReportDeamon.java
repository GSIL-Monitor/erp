package com.stosz.product.deamon;


import com.stosz.admin.ext.model.User;
import com.stosz.admin.ext.service.IUserService;
import com.stosz.plat.rabbitmq.MailSender;
import com.stosz.product.email.JavaMail;
import com.stosz.product.ext.enums.ProductNewState;
import com.stosz.product.ext.model.ExcludeRepeat;
import com.stosz.product.ext.model.FsmHistory;
import com.stosz.product.ext.model.ProductNew;
import com.stosz.product.service.ProductNewService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

//排重每日报表
@Component
public class ProductNewReportDeamon {

    @Resource
    private ProductNewService productNewService;
    @Resource
    private MailSender mailSender;
    @Resource
    private IUserService iUserService;

    @Value("${excludeRepeatMailbox}")
    private String excludeRepeatMailbox;
    @Value("${mailType}")
    private String mailType;
    @Value("${emailSwitch}")
    private Boolean emailSwitch;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 排重每日报表
     */
    public void excludeRepeatReport() {
        //        LocalDateTime minTime = LocalDateTime.of(2017,10,1,12,0,0);
        LocalDateTime minTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(0, 0, 0));
        LocalDateTime maxTime = LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 0, 0));
        reportForm(minTime, maxTime);
    }

    /**
     * 手动调度排重报表
     */
    public void excludeRepeatReport(List<Integer> startTime, List<Integer> endTime) {
        LocalDateTime minTime;
        if (startTime != null && startTime.size() > 0) {
            minTime = LocalDateTime.of(
                    LocalDate.of(startTime.get(0), startTime.get(1), startTime.get(2)),
                    LocalTime.of(0, 0, 0));
        } else {
            minTime = LocalDateTime.of(
                    LocalDate.of(endTime.get(0), endTime.get(1), endTime.get(2)),
                    LocalTime.of(0, 0, 0));
        }
        LocalDateTime maxTime = LocalDateTime.of(
                LocalDate.of(endTime.get(0), endTime.get(1), endTime.get(2)),
                LocalTime.of(23, 0, 0));
        logger.info("排重报表手动触发开始,报表时间段为[" + minTime + "]到[" + maxTime + "]");
        reportForm(minTime, maxTime);
    }

    /**
     * 排重报表的实现
     */
    private void reportForm(LocalDateTime minTime, LocalDateTime maxTime) {
        List<ExcludeRepeat> bufferList = new ArrayList<>();
        List<Integer> userInfo = productNewService.excludeRepeatUser(minTime, maxTime);
        if (userInfo == null || userInfo.size() == 0) {
            logger.info(minTime + "==>" + maxTime + "之间没有排重相关的信息!");
            return;
        }
        List<User> userList = iUserService.findByIds(new HashSet<>(userInfo));
        Map<Integer, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));
        for (Integer checkerId : userInfo) {
            List<ProductNew> listFsm = productNewService.findCheckDay(minTime, maxTime, "ProductNew", userMap.get(checkerId).getLoginid());
            Map<Integer, List<ProductNew>> mapFsm = listFsm.stream().collect(Collectors.groupingBy(ProductNew::getDepartmentId));
            //拒绝
            List<FsmHistory> cancel = productNewService
                    .findStateNumber(minTime, maxTime, "ProductNew", ProductNewState.cancel.name(), userMap.get(checkerId).getLoginid());
            Map<Integer, List<FsmHistory>> cancelMap = cancel.stream().collect(Collectors.groupingBy(FsmHistory::getDepartmentId));
            //重复
            List<FsmHistory> duplication = productNewService
                    .findStateNumber(minTime, maxTime, "ProductNew", ProductNewState.duplication.name(), userMap.get(checkerId).getLoginid());
            Map<Integer, List<FsmHistory>> duplicationMap = duplication.stream().collect(Collectors.groupingBy(FsmHistory::getDepartmentId));
            //重复通过
            ExcludeRepeat param1 =
                    findFsmHistoryParam(minTime, maxTime, "ProductNew", ProductNewState.checkOk.name(),
                            ProductNewState.duplication.name(), userMap.get(checkerId).getLoginid());
            List<FsmHistory> duplicationOk = productNewService.findFsmHistoryDept(param1);
            Map<Integer, List<FsmHistory>> duplicationOkMap = duplicationOk.stream().collect(Collectors.groupingBy(FsmHistory::getDepartmentId));
            //有风险
            List<FsmHistory> checkWarn = productNewService
                    .findStateNumber(minTime, maxTime, "ProductNew", ProductNewState.checkWarn.name(), userMap.get(checkerId).getLoginid());
            Map<Integer, List<FsmHistory>> checkWarnMap = checkWarn.stream().collect(Collectors.groupingBy(FsmHistory::getDepartmentId));
            //有风险通过
            ExcludeRepeat param2 =
                    findFsmHistoryParam(minTime, maxTime, "ProductNew", ProductNewState.checkOk.name(),
                            ProductNewState.checkWarn.name(), userMap.get(checkerId).getLoginid());
            List<FsmHistory> checkWarnOk = productNewService.findFsmHistoryDept(param2);
            Map<Integer, List<FsmHistory>> checkWarnOkMap = checkWarnOk.stream().collect(Collectors.groupingBy(FsmHistory::getDepartmentId));
            //有风险,主管取消
            ExcludeRepeat param3 =
                    findFsmHistoryParam(minTime, maxTime, "ProductNew", ProductNewState.checkWarn.name(),
                            ProductNewState.cancel.name(), userMap.get(checkerId).getLoginid());
            List<FsmHistory> refuse = productNewService.findFsmHistoryDept(param3);
            Map<Integer, List<FsmHistory>> refuseMap = refuse.stream().collect(Collectors.groupingBy(FsmHistory::getDepartmentId));
            //草稿
            List<FsmHistory> draft = productNewService
                    .findStateNumber(minTime, maxTime, "ProductNew", ProductNewState.draft.name(), userMap.get(checkerId).getLoginid());
            Map<Integer, List<FsmHistory>> draftMap = draft.stream().collect(Collectors.groupingBy(FsmHistory::getDepartmentId));


            mapFsm.forEach((deptId, value) -> {
                ExcludeRepeat repeat = new ExcludeRepeat();
                repeat.setCheckerId(checkerId);
                repeat.setDepartmentId(deptId);
                repeat.setDeptNumber(mapFsm.size());
                repeat.setCancel(entityAssignment(cancelMap, deptId));
                repeat.setDuplication(entityAssignment(duplicationMap, deptId));
                repeat.setDuplicationCheckOk(entityAssignment(duplicationOkMap, deptId));
                repeat.setCheckWarn(entityAssignment(checkWarnMap, deptId));
                repeat.setCheckWarnCheckOk(entityAssignment(checkWarnOkMap, deptId));
                repeat.setRefuse(entityAssignment(refuseMap, deptId));
                repeat.setDraft(entityAssignment(draftMap, deptId));
                if (value != null && value.size() > 0) {
                    repeat.setNumber(value.size());
                    List<ProductNew> lst =
                            value.stream().filter(e -> ProductNewState.checkOk.name().equals(e.getState())).collect(Collectors.toList());
                    List<ProductNew> lst1 =
                            value.stream().filter(e -> ProductNewState.archived.name().equals(e.getState())).collect(Collectors.toList());
                    repeat.setCheckOk(lst.size() + lst1.size());
                    repeat.setDepartmentName(value.get(0).getDepartmentName());
                    repeat.setChecker(userMap.get(checkerId).getLastname());
                } else {
                    repeat.setCheckOk(0);
                    repeat.setDepartmentName("系统(无部门)");
                    repeat.setChecker("系统");
                }
                repeat.setUnCheckOk(repeat.getCancel() +
                        (repeat.getDuplication() - repeat.getDuplicationCheckOk()) +
                        (repeat.getCheckWarn() - repeat.getCheckWarnCheckOk()));//未通过数量
                int num = repeat.getCheckOk() + repeat.getUnCheckOk();
                repeat.setAdoptRate(getPercentage(repeat.getCheckOk(), num, 0));//通过率
                repeat.setUnAdoptRate(getPercentage(repeat.getUnCheckOk(), num, 0));//未通过率
                bufferList.add(repeat);
            });
        }
        if (bufferList == null || bufferList.size() == 0) {
            logger.info("排重今日[" + LocalDateTime.now() + "]报表无数据,直接返回!");
            return;
        }
        if (!emailSwitch) {
            logger.info("排重报表邮件没有打开,不发送邮件,直接返回!");
            return;
        }
        JavaMail javaMail = new JavaMail();
        javaMail.mailSendOut(bufferList, "[" + mailType + "]排重日报[" + minTime + "]-->[" + maxTime + "]", excludeRepeatMailbox);
        logger.info("排重报表邮件发送成功,共[" + bufferList.size() + "]条数据!");
//        mailSender.sendEmail(excludeRepeatMailbox, "排重日报",
//                mailTemplate(bufferList, new String[]{
//                        "排重日报[" + minTime + "]-->[" + maxTime + "]", ""}));
    }


    private Integer entityAssignment(Map<Integer, List<FsmHistory>> map, Integer deptId) {
        if (map.get(deptId) != null && map.get(deptId).size() > 0) {
            return map.get(deptId).size();
        }
        return 0;
    }

    /**
     * 状态机历史查询参数
     */
    private ExcludeRepeat findFsmHistoryParam(LocalDateTime minTime, LocalDateTime maxTime,
                                              String fsmName, String dstName, String otherState, String optId) {
        if (optId == null || "".equals(optId)) {
            optId = "系统";
        }
        ExcludeRepeat param = new ExcludeRepeat();
        param.setMinCreateAt(minTime);
        param.setMaxCreateAt(maxTime);
        param.setFsmName(fsmName);
        param.setDstState(dstName);
        param.setOtherState(otherState);
        param.setOptUid(optId);
        return param;
    }

    /**
     * 获取百分数
     */
    private String getPercentage(int molecule, int denominator, int decimal) {
        if (denominator == 0) {
            return "0%";
        }
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(decimal);
        String result = numberFormat.format((float) molecule / (float) denominator * 100);
        if (result == null || "".equals(result)) {
            return "0%";
        }
        return result + "%";
    }


    /**
     * 邮件内容模板
     */
    private String mailTemplate(List<ExcludeRepeat> list, String[] memo) {
        StringBuilder sb = new StringBuilder();
        sb.append("<span style='font-size:18px'>" + memo[0] + "</span></br>");
        sb.append("<table border=\"1\" cellspacing=\"0\"><tr><td width=\"100px\">排重通过人员</td>" +
                "<td width=\"250px;\" style=\"text-align:center;\">部门</td>" +
                "<td width=\"100px;\" style=\"text-align:center;\">数量</td>" +
                "<td width=\"100px;\" style=\"text-align:center;\">查重通过数量</td>" +
                "<td width=\"100px;\" style=\"text-align:center;\">取消</td>" +
                "<td width=\"200px;\" style=\"text-align:center;\">拒绝[排重有风险,广告取消]</td>" +
                "<td width=\"120px;\" style=\"text-align:center;\">草稿[新品驳回]</td></tr>");
        for (ExcludeRepeat lst : list) {
            sb.append("<tr>" +
                    "<td>" + lst.getChecker() + "</td>" +
                    "<td>" + lst.getDepartmentName() + "</td>" +
                    "<td>" + lst.getNumber() + "</td>" +
                    "<td>" + lst.getCheckOk() + "</td>" +
                    "<td>" + lst.getCancel() + "</td>" +
                    "<td>" + lst.getRefuse() + "</td>" +
                    "<td>" + lst.getDraft() + "</td>" +
                    "</tr>");
        }
        sb.append("</table>");
        sb.append("<span style='color:red;font-size:20px'>" + memo[1] + "</span><br/>");
        return sb.toString();
    }

}
