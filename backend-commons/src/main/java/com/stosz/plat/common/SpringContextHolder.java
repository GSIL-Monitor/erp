package com.stosz.plat.common;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

/**
 * <p>
 * Spring 工具类 ，获取Spring容器中的上下文信息
 * </p>
 * <p>
 * 使用的时候需要注入到 servlet-context.xml 如下设置： <br>
 * <!-- 获取ApplicationContext上下文 -->
 * <bean id="springContextHolder" class="com.xike.backend.commons.spring.SpringContextHolder" />
 * </p>
 * 
 * @author shiqiangguo
 * @Date 2016-07-21
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return context;
    }

    public static <T> T getBean(Class<T> clazz) {
        checkApplicationContext();
        return context.getBean(clazz);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkApplicationContext();
        return (T) context.getBean(name);
    }

    public void setApplicationContext(ApplicationContext ac) throws BeansException {
        context = ac;
    }

    private static void checkApplicationContext() {
        if (context == null) {
            throw new CommonException(
                    "applicaitonContext is null, please define Bean SpringContextHolder in applicationContext.xml");
        }
    }

    /**
     * <p>
     * 国际化文件读取
     * </p>
     * 
     * @param code
     *            the code to lookup up, such as 'calculator.noRateSet'
     * @param args
     *            Array of arguments that will be filled in for params within
     *            the message (params look like "{0}", "{1,date}", "{2,time}"
     *            within a message), or {@code null} if none.
     * @param locale
     *            the Locale in which to do the lookup
     * @return the resolved message
     * @throws NoSuchMessageException
     *             if the message wasn't found
     * @see java.text.MessageFormat
     */
    public static String getMessage(String code, Object[] args, Locale locale) {
        return context.getMessage(code, args, locale);
    }

    /**
     * 获取 HttpServletRequest
     * <p>
     *  使用的时候需要注入到 web.xml 如下设置：
     *  <br>
     *  <listener>
     *     <listener-class>org.springframework.web.context.request.RequestContextListener</listener-class>
     *  </listener>
     * </p>
     */
    public static HttpServletRequest getHttpServletRequest() {
        ServletRequestAttributes requestAttrs = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes());
        if (requestAttrs == null) return null;
        return requestAttrs.getRequest();
    }

    public static HttpSession getSession() {
        HttpServletRequest req = getHttpServletRequest();
        if (req == null) {
            return null;
        }
        return req.getSession();
    }
}
