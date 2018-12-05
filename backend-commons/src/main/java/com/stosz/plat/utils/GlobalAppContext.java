package com.stosz.plat.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class GlobalAppContext {

    private static ApplicationContext appCtx = null;

    public static ApplicationContext getAppCtx() {
        if (appCtx == null) {
            appCtx = new ClassPathXmlApplicationContext("spring/spring-dataSource.xml");
        }

        return appCtx;
    }

    public static void setAppCtx(ApplicationContext ctx) {
        appCtx = ctx;
    }
}
