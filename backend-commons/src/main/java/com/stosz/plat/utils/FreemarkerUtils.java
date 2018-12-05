package com.stosz.plat.utils;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.*;
import java.util.Map;

/**
 *  ferrmarker格式化工具类
 */
public class FreemarkerUtils {

    public static String format(String path, Map<String,Object> dataMap){
        // 处理模版
        Writer out = null;
        String templateName = "explore";//模板页的标记名称
        Configuration cfg = new Configuration();
        cfg.setObjectWrapper(new DefaultObjectWrapper());
        cfg.setDefaultEncoding("UTF-8");   //这个一定要设置，不然在生成的页面中 会乱码
        String templateSource="";
        InputStream inStream = null;
        final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            inStream =  FreemarkerUtils.class.getResourceAsStream(path);

            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            byte[] data = new byte[4096];
            int count = -1;
            while ((count = inStream.read(data, 0, 4096)) != -1) {
                outStream.write(data, 0, count);
            }
            templateSource = outStream.toString("UTF-8");

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (null != inStream) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        Reader reader = new StringReader(templateSource);

        try {
            out = new PrintWriter( new OutputStreamWriter(byteArrayOutputStream));
            Template template = new Template(templateName, reader, cfg, "UTF-8");
            template.process(dataMap, out, new DefaultObjectWrapper());//动态加载模板

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return byteArrayOutputStream.toString();
    }
}
