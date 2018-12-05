package com.stosz.plat.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档 转载时请保留以下信息，注明出处！
 * http://blog.csdn.net/evangel_z/article/details/7332535
 *
 * @author leno
 * @version v1.0
 * 应用泛型，代表任意一个符合javabean风格的类
 * 注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 * byte[]表jpg格式的图片数据
 * <p>
 * 使用说明
 * String[] headers ={"学号", "姓名", "年龄", "性别", "出生日期"};
 * List<Student> dataset = new ArrayList<Student>();
 * dataset.add(new Student(10000001, "张三", 20, true, new Date()));
 * dataset.add(new Student(20000002, "李四", 24, false, new Date()));
 * dataset.add(new Student(30000003, "王五", 22, true, new Date()));
 * try {
 * ExcelUtil.exportExcelAndDownload(response,"文件名","标题",headers,dataset,null);
 * } catch (Exception e) {
 * }
 */
public class ExcelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    private static final String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * @param response
     * @param fileName      文件名
     * @param title         excel表标题
     * @param headers       excel头
     * @param dataset       数据集
     * @param excludeFields 不导出的实体类字段
     * @param <T>
     * @throws Exception
     */
    public static <T> void exportExcelAndDownload(HttpServletResponse response, String fileName, String title, String[] headers,
                                                  Collection<T> dataset, String[] excludeFields) throws Exception {
        if (fileName == null && fileName.trim().length() == 0) {
            throw new Exception("文件名不能为空");
        }

        fileName = fileName + ".xls";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        ServletOutputStream out = response.getOutputStream();
        exportExcel(title, headers, dataset, out, excludeFields, DATE_PATTERN);
        return;
    }

    public static <T> void exportExcel(Collection<T> dataset, String[] excludeFields, OutputStream out) {
        exportExcel("导出EXCEL文档", null, dataset, out, excludeFields, DATE_PATTERN);
    }

    public static <T> void exportExcel(String[] headers, Collection<T> dataset, String[] excludeFields,
                                       OutputStream out) {
        exportExcel("导出EXCEL文档", headers, dataset, out, excludeFields, DATE_PATTERN);
    }

    public static <T> void exportExcel(String[] headers, Collection<T> dataset,
                                       OutputStream out, String[] excludeFields, String pattern) {
        exportExcel("导出EXCEL文档", headers, dataset, out, excludeFields, pattern);
    }

    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     *
     * @param title   表格标题名
     * @param headers 表格属性列名数组
     * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out     与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd "
     */
    @SuppressWarnings("unchecked")
    public static <T> void exportExcel(String title, String[] headers,
                                       Collection<T> dataset, OutputStream out, String[] excludeFields, String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);

        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
                0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("sys");

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        if (headers != null) {
            for (short i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                text.applyFont(HSSFColor.BLACK.index);
                cell.setCellValue(text);
            }
        }


        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            int columnCount = 0;
            for (short i = 0; i < fields.length; i++) {

                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);

                //排除 serialVersionUID
                if (fieldName.equals("serialVersionUID")) {
                    continue;
                }
                //排除字段
                boolean exclueFlags = false;
                if (excludeFields != null) {
                    for (String excludeField : excludeFields) {
                        if (fieldName.equals(excludeField)) {
                            exclueFlags = true;
                            break;
                        }
                    }
                }
                if (exclueFlags) {
                    continue;
                }

                HSSFCell cell = row.createCell(columnCount++);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName);
                    Object value = getMethod.invoke(t);
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if (value instanceof Boolean) {
                        boolean bValue = (Boolean) value;
                        textValue = "是";
                        if (!bValue) {
                            textValue = "否";
                        }
                    } else if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else if (value instanceof LocalDateTime) {
                        LocalDateTime localDateTime = (LocalDateTime) value;
                        DateTimeFormatter
                                formatter = DateTimeFormatter.ofPattern(pattern);
                        textValue = localDateTime.format(formatter);
                    } else if (value instanceof byte[]) {
                        row.setHeightInPoints(60);
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算
                        sheet.setColumnWidth(i, (short) (35.7 * 80));
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                                1023, 255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, workbook.addPicture(
                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else {

                        // 其它数据类型都当作字符串简单处理
                        if (value == null) {
                            textValue = "";
                        } else {
                            textValue = value.toString();
                        }

                    }
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(
                                    textValue);
                            HSSFFont font3 = workbook.createFont();
                            font3.setColor(HSSFColor.BLACK.index);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    logger.error(e.getMessage(), e);
                } catch (NoSuchMethodException e) {
                    logger.error(e.getMessage(), e);
                } catch (IllegalArgumentException e) {
                    logger.error(e.getMessage(), e);
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage(), e);
                } catch (InvocationTargetException e) {
                    logger.error(e.getMessage(), e);
                } finally {
                    // 清理资源
                }
            }
        }
        try {
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }

    }


    /**
     * @param response
     * @param fileName      文件名
     * @param title         excel表标题
     * @param headers       excel头
     * @param dataset       数据集
     * @param <T>
     * @throws Exception
     */
    public static <T> void exportExcelAndDownload(HttpServletResponse response, String fileName, String title, String[] headers,
                                                  String[] fields,Collection<T> dataset) throws Exception {
        if (fileName == null && fileName.trim().length() == 0) {
            throw new Exception("文件名不能为空");
        }

        fileName = fileName + ".xls";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        ServletOutputStream out = response.getOutputStream();
        exportExcel(title, headers,fields, dataset, out, DATE_PATTERN);
        return;
    }

    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     *
     * @param title   表格标题名
     * @param headers 表格属性列名数组
     * @param fields  表格列对应字段数组
     * @param dataset 需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *                javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out     与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern 如果有时间数据，设定输出格式。默认为"yyy-MM-dd "
     */
    @SuppressWarnings("unchecked")
    public static <T> void exportExcel(String title, String[] headers,String[] fields,
                                       Collection<T> dataset, OutputStream out, String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);

        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,
                0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("sys");

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        if (headers != null) {
            for (short i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                text.applyFont(HSSFColor.BLACK.index);
                cell.setCellValue(text);
            }
        }

        // 遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            int columnCount = 0;
            for (short i = 0; i < fields.length; i++) {
                String filedName =  fields[i];
                String getMethodName = "get"
                        + filedName.substring(0, 1).toUpperCase()
                        + filedName.substring(1);

                HSSFCell cell = row.createCell(columnCount++);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName);
                    Object value = getMethod.invoke(t);
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    if (value != null){
                        if (value instanceof Boolean) {
                            boolean bValue = (Boolean) value;
                            textValue = "是";
                            if (!bValue) {
                                textValue = "否";
                            }
                        } else if (value instanceof Date) {
                            Date date = (Date) value;
                            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                            textValue = sdf.format(date);
                        } else if (value instanceof LocalDateTime) {
                            LocalDateTime localDateTime = (LocalDateTime) value;
                            DateTimeFormatter
                                    formatter = DateTimeFormatter.ofPattern(pattern);
                            textValue = localDateTime.format(formatter);
                        } else if (value instanceof byte[]) {
                            row.setHeightInPoints(60);
                            // 设置图片所在列宽度为80px,注意这里单位的一个换算
                            sheet.setColumnWidth(i, (short) (35.7 * 80));
                            byte[] bsValue = (byte[]) value;
                            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                                    1023, 255, (short) 6, index, (short) 6, index);
                            anchor.setAnchorType(2);
                            patriarch.createPicture(anchor, workbook.addPicture(
                                    bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                        }else if(value instanceof  IEnum){
                            textValue = ((IEnum) value).display();
                        }else{
                            // 其它数据类型都当作字符串简单处理
                            if (value == null) {
                                textValue = "";
                            } else {
                                textValue = value.toString();
                            }
                        }
                    }else{
                        value = "";
                    }

                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(
                                    textValue);
                            HSSFFont font3 = workbook.createFont();
                            font3.setColor(HSSFColor.BLACK.index);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    logger.error(e.getMessage(), e);
                } catch (NoSuchMethodException e) {
                    logger.error(e.getMessage(), e);
                } catch (IllegalArgumentException e) {
                    logger.error(e.getMessage(), e);
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage(), e);
                } catch (InvocationTargetException e) {
                    logger.error(e.getMessage(), e);
                } finally {
                    // 清理资源
                }
            }
        }
        try {
            workbook.write(out);
            out.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }

    }


}
