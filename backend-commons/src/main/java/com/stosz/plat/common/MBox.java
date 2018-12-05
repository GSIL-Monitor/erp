package com.stosz.plat.common;

//import com.stosz.admin.ext.common.MenuNode;
//import com.stosz.admin.ext.model.Job;
//import com.stosz.admin.ext.model.JobAuthorityRel;
//import com.stosz.admin.ext.model.Menu;
//import com.stosz.admin.ext.model.User;

import com.stosz.plat.enums.__Init__;
import com.stosz.plat.utils.IEnum;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class MBox {

    private static final Logger logger = LoggerFactory.getLogger(MBox.class);


    public static final String COOKIE_TICKET_KEY = "TICKET_KEY";
    public static final String PARAM_TICKET_KEY = "ticket";
    public static final String PARAM_BACK_URL = "backUrl";

    public static final String GLOBAL_SESSION_USER = "GLOBAL_SESSIONOBAL_SESSION_MENU_NODE_JSON";//菜单树
    public static final String GLOBAL_SESSION_MENU_ELEMENT_LIST = "GLOBAL_SESSION_MENU_ELEMENT_LIST"; //存放菜单元素权限
    public static final String GLOBAL_SESSION_JOB = "GLOBAL_SESSION_JOB";//所属职位
    public static final String GLOBAL_SESSION_USER_FIRST_URL = "GLOBAL_SESSION_USER_FIRST_URL";//当前用户有权访问的第一个URL
    public static final String OLD_ERP_DEFAULT_PASSWORD = "###099cbe24378b0911c53bfc24c8a2b8b5";//老erp默认的用户密码123456

    public static final String GLOBAL_SESSION_JOB_AUTHORITY_LIST = "GLOBAL_SESSION_JOB_AUTHORITY_LIST";//当前用户拥有的职位数据权限

    public static final Integer ERP_VERSION = 1;	//新erp版本
    public static final Integer ERP_OLD_VERSION = 0;	//旧erp版本
    public static final Integer ERP_ATTRIBUTE_VERSION = 1;	//新erp属性的标记:1为新erp
    public static final Integer ERP_ATTRIBUTEVALUE_VERSION = 1;	//新erp属性值的标记:1为新erp
    public static final Integer ERP_SKU_LENGTH = 5;			//新erpsku的长度
//    public static final LocalTime MORNING_TIME_LIMIT = LocalTime.of(12, 0, 0); //上午提交时间界限
//    public static final LocalTime AFTERNOON_TIME_LIMIT = LocalTime.of(22, 0, 0); //下午提交时间界限
//    public static final Integer ADVERTISING_SUBMIT_NUMBER = 300;//广告提交新品次数
public static final Integer DISPPEARED_DAY_ORDER = 7;
    public static final Integer WARNING_DAY_ORDER = 5;
    public static final Integer DISPPEARED_DAY_ARCHIVE = 3;
    public static final Integer DISPPEARED_WAIT_TIME_MINUTE = 18 * 60;    //消档待下架产品区域的间隔时间,单位:分钟

    public static final String REQUEST_HEADER_REFERER = "Referer";
    public static final String REQUEST_HEADER_AJAX = "x-requested-with";
    public static final String REQUEST_HEADER_AJAX_VALUE = "XMLHttpRequest";

    public static String REQUEST_HEADER_PROJECT_ID="X-PROJECT-ID";
    public static String REQUEST_HEADER_AUTH_SIGNATURE="X-AUTH-SIGNATURE";
    public static String REQUEST_HEADER_AUTH_TIMESTAMP="X-AUTH-TIMESTAMP";
    public static String REQUEST_HEADER_AUTH_NONCE="X-AUTH-NONCE";

    public static final String BGN_COMPANY_NO  = "1000";

    public static boolean isTestCase=false;

//    public static Integer getLoginUserId() {
//        User user = getLoginUser();
//        return user == null ? null : MBox.getLoginUserId();
//    }
//
//    public static String getLoginUserName(){
//        User u = getLoginUser();
//        if (u == null) {
//            return "";
//        }
//        return u.getLastname();
//    }

//    public static User getLoginUser() {
//        if (isTestCase) {
//            User u = new User();
//            u.setId(1);
//            u.setLoginid("test.py@stosz.com");
//            u.setLastname("单元测试用户");
//            u.setDepartmentId(1);
//            u.setDepartmentName("单元测试部门");
//            u.setDepartmentNo("01");
//            return u;
//        }
//        return getObjectSession(GLOBAL_SESSION_USER);
//    }

    public static Boolean hasPermission(String menuKey , String elementKey){
        Set<String> userPermissions = getObjectSession(GLOBAL_SESSION_MENU_ELEMENT_LIST);
        return userPermissions.contains(menuKey + "." + elementKey);
    }

    public static void setPermissions(Set<String> userPermissions) {
        setObjectSession(GLOBAL_SESSION_MENU_ELEMENT_LIST, userPermissions);
    }



//    public static void setLoginUser(User user) {
//        setObjectSession(GLOBAL_SESSION_USER, user);
//    }
//
//    public static List<Menu> getTopMenu() {
//        return getObjectSession(GLOBAL_SESSION_MENU_TOP);
//    }
//
//    public static List<JobAuthorityRel> getJobAuthorityRel() {
//        return getObjectSession(GLOBAL_SESSION_JOB_AUTHORITY_LIST);
//    }
//
//    public static void setJobAuthorityRel(List<JobAuthorityRel> jobAuthorityRelList){
//        setObjectSession(GLOBAL_SESSION_JOB_AUTHORITY_LIST,jobAuthorityRelList);
//    }
//
//    //一级菜单
//    public static void setTopMenu(List<Menu> topMenus) {
//        setObjectSession(GLOBAL_SESSION_MENU_TOP, topMenus);
//    }
//
//    public static Map<Integer, List<Menu>> getSecondMenu(){
//        return getObjectSession(GLOBAL_SESSION_MENU_SECOND);
//    }
//
//    public static List<Job> getJob() {
//        return getObjectSession(GLOBAL_SESSION_JOB);
//    }
//
//    public static void setJob(List<Job> jobList) {
//        setObjectSession(GLOBAL_SESSION_JOB, jobList);
//    }
//
//    //二级菜单
//    public static void setSecondMenu(Map<Integer, List<Menu>> secondMenus) {
//        setObjectSession(GLOBAL_SESSION_MENU_SECOND, secondMenus);
//    }
//
//    public static void setMenuNode(MenuNode menuNode) {
//        setObjectSession(GLOBAL_SESSION_MENU_NODE, menuNode);
//    }
//
//    public static void setMenuNodeJson(String menuNode) {
//        setObjectSession(GLOBAL_SESSION_MENU_NODE_JSON, menuNode);
//    }
//
//    public static MenuNode getMenuNode() {
//        return getObjectSession(GLOBAL_SESSION_MENU_NODE);
//    }

    public static String getFirstUrl() {
        return getObjectSession(GLOBAL_SESSION_USER_FIRST_URL);
    }

    public static void setFirstUrl(String url) {
        setObjectSession(GLOBAL_SESSION_USER_FIRST_URL, url);
    }

    public static String getOldErpDefaultPassword() {
        return OLD_ERP_DEFAULT_PASSWORD;
    }

    public static String getBackUrl(HttpServletRequest request) {

        String uri = request.getRequestURL().toString();
        String redirect = uri + (isNotBlank(request.getQueryString()) ? ("?" + request.getQueryString()) : "");
        if (MBox.isAjax(request)) {
            String referer = request.getHeader(MBox.REQUEST_HEADER_REFERER);
            if (isNotBlank(referer)) {
                redirect = referer;
            }
        }
        return redirect;
    }


    private static Set<String> getSetBySessionKey(String key) {
        HttpSession session = SpringContextHolder.getSession();
        if (session == null) {
            return Collections.emptySet();
        }
        Set<String> set = (Set<String>) session.getAttribute(key);
        if (CollectionUtils.isEmpty(set)) {
            return Collections.emptySet();
        }
        return set;
    }


    public static final void logout() {
        HttpSession session = SpringContextHolder.getSession();
        session.invalidate();
    }

    public static final String getSysUser() {
        return "System";
    }


    private static void setObjectSession(String key, Object obj) {
        HttpSession session = SpringContextHolder.getSession();
        if (session == null) {
            throw new RuntimeException("设置对象到session时异常，获取到的session为空，key:" + key + " 对象:" + obj);
        }
        session.setAttribute(key, obj);
    }

    private static <T> T getObjectSession(String key) {
        HttpSession session = SpringContextHolder.getSession();
        if (session == null) {
            return null;
        }
        T obj = (T) session.getAttribute(key);
        return obj;
    }

    public static String esc(long start) {
        return "耗时:" + (System.currentTimeMillis() - start) + " 毫秒.";
    }

    public static String trim(String str) {
        if (str == null) {
            return "";
        }
        return str.trim();
    }

    public static boolean notEmpty(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof String) {
            if (((String) obj).trim().length() == 0) {
                return false;
            } else {
                return true;
            }
        }
        if (obj instanceof Collection) {
            Collection<?> col = (Collection<?>) obj;
            return !col.isEmpty();
        }
        return true;
    }


    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


    public static boolean isAjax(HttpServletRequest httpRequest) {
        return httpRequest.getHeader("x-requested-with") != null
                && "XMLHttpRequest".equalsIgnoreCase(httpRequest.getHeader("x-requested-with"));
    }

    public static boolean isInside(HttpServletRequest httpRequest){
        String x_location_net = httpRequest.getHeader("X_Location_Net");
        boolean inside = x_location_net == null || !"outside".equalsIgnoreCase(x_location_net);
        System.out.println("============MBox 调试一下 x_location_net : " + x_location_net + " inside:" + inside+ "================" );
        return inside;

    }

    public static final String FSM_ENUM_TYPE_STATE = "State";
    public static final String FSM_ENUM_TYPE_EVENT = "Event";

    public static String getDisplayByIEnum(IEnum e) {
        return e == null ? "" : e.display();
    }

    public static String getDisplayByFsmEnum(Class className, String val) {
        if (StringUtils.isBlank(val)) {
            return "";
        }
        try {
            Class<IEnum> iEnumClass = (Class<IEnum>)className;
            IEnum[] es = iEnumClass.getEnumConstants();
            for (IEnum x : es) {
                if (x.name().equals(val)) {
                    return x.display();
                }
            }
            //IEnum anEnum = iEnumClass.newInstance();
            return val;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return val;
    }

    public static String getDisplayByFsmEnum(String fsmName, String type, String val) {
        if (StringUtils.isBlank(val)) {
            return "";
        }
        String enumPackage = __Init__.pkg();
        try {
            Class<IEnum> iEnumClass = (Class<IEnum>) Class.forName(enumPackage + "." + fsmName + type);
            IEnum[] es = iEnumClass.getEnumConstants();
            for (IEnum x : es) {
                if (x.name().equals(val)) {
                    return x.display();
                }
            }
            //IEnum anEnum = iEnumClass.newInstance();
            return val;
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        return val;
    }

    public static BigDecimal bigDecimal(Double d) {
        return new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal bigDecimal(Float d) {
        return new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static BigDecimal bigDecimal(String d) {
        return new BigDecimal(d).setScale(2, BigDecimal.ROUND_HALF_UP);
    }


    public static void assertLenth(Object obj, String title, Integer maxLen) {
        assertLenth(obj, title, 0, maxLen);
    }

    public static void assertLenth(Object obj, String title, Integer minLen, Integer maxLen) {
        if (minLen > 0) {
            Assert.notNull(obj, title + "不允许为空！");
        } else {
            if (obj == null)
                return;
        }
        Assert.isTrue(obj.toString().length() >= minLen && obj.toString().length() <= maxLen, title + "长度必须大于等于" + minLen + "且小于等于" + maxLen);
    }

    public static String getSignMd5Str(String secret, String timestamp, String nonce) {
        String str = secret + timestamp + nonce;
        return DigestUtils.md5Hex(str);
    }

    /**
     * 提供一个跟业务对象无关的方法判断登录
     * @return
     */
    public static boolean hasLogin() {
        return true;
    }

    /**
     * 提供一个拿到登录用户id的方法
     * @return
     */
    public static String getLoginid() {
        return Optional.ofNullable(ThreadLocalUtils.getUser()).orElse(new UserDto()).getLoginid();
    }

    public static Integer getLoginUserId() {
        return Optional.ofNullable(ThreadLocalUtils.getUser()).orElse(new UserDto()).getId();
    }

    // 拿到登录的用户名
    public static String getLoginUserName() {
        return Optional.ofNullable(ThreadLocalUtils.getUser()).orElse(new UserDto()).getLastName();
    }

    //
    public static Integer getDepartmentId() {
        return Optional.ofNullable(ThreadLocalUtils.getUser()).orElse(new UserDto()).getDeptId();
    }
    /**
     * 从cookie中取ticket
     * @param request
     * @return ticket
     */
    public static String getTicketByCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String ticketVal = null;
        for(Cookie cookie:cookies){
            if(PARAM_TICKET_KEY.equals(cookie.getName())){
                ticketVal = cookie.getValue()+"";
//                    String redisTicVal = template.opsForValue().get(ticketVal);
            }
        }
        return ticketVal;
    }
}
