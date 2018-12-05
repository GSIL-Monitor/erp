package com.stosz.order.util;

import com.stosz.order.ext.dto.ModelProp;
import com.stosz.order.ext.dto.OrderItemSpuDTO;
import com.stosz.order.ext.model.OrdersRmaBillItem;
import com.stosz.plat.utils.ExcelUtil;
import com.stosz.plat.utils.IEnum;
import com.stosz.plat.utils.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Excel导出工具类
 *
 * @auther tangtao
 * create time  2017/12/21
 */
public class ExcelUtils {
    public static final String DATE_PATTERN = "yyyy/MM/dd";
    public static final String DATE_TIME_PATTERN = "yyyy/MM/dd HH:mm:ss";
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);
    private static final float ROW_HEADER_HEIGHT = 31.5f;
    private static final float ROW_BODY_HEIGHT = 21.0f;

    private static final short ROW_HEADER_FONT_SIZE = 12;
    private static final short ROW_BODY_FONT_SIZE = 11;

    final static String notnullerror = "请填入第{0}行的{1},{2}不能为空";
    final static String errormsg = "第{0}行的{1}数据导入错误";

    private final static String xls = "xls";
    private final static String xlsx = "xlsx";

    /**
     * @param fileName      下载文件名
     * @param sheetName     表名
     * @param headers       表头
     * @param includeFields 需要导出的属性名
     * @param dataSet       数据集
     * @param pattern       时间格式化
     * @param response
     * @param <T>
     * @throws Exception
     */
    public static <T> void exportExcelAndDownload(String fileName, String sheetName, String[] headers,
                                                  String[] includeFields, Collection<T> dataSet, String pattern, HttpServletResponse response) throws Exception {
        if (fileName == null && fileName.trim().length() == 0) {
            throw new Exception("文件名不能为空");
        }

        String name = fileName + "_" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".xls";

        fileName = new String(name.getBytes("UTF-8"), "ISO-8859-1");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        if (StringUtils.isNotBlank(pattern))
            exportExcel(sheetName, headers, includeFields, dataSet, pattern, response.getOutputStream());
        else exportExcel(sheetName, headers, includeFields, dataSet, DATE_PATTERN, response.getOutputStream());
    }

    /**
     * @param sheetName     表名
     * @param headers       表头
     * @param includeFields 需要导出的属性名
     * @param dataSet       数据集
     * @param pattern       日期格式化模式
     * @param out
     * @param <T>
     */
    public static <T> void exportExcel(String sheetName, String[] headers, String[] includeFields, Collection<T> dataSet, String pattern,
                                       OutputStream out) {

        DateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        DateTimeFormatter
                dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);


        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(sheetName);
        // 设置表格默认列宽度为12个字节
        sheet.setDefaultColumnWidth(18);
//        sheet.setColumnWidth(2, 20 * 256);
//        sheet.setColumnWidth(4, 30 * 256);
//        sheet.setColumnWidth(7, 50 * 256);
        sheet.setDefaultRowHeightInPoints(ROW_BODY_HEIGHT);

//        HSSFCellStyle listStyle = workbook.createCellStyle();
//        HSSFFont listFont = workbook.createFont();
//        listFont.setFontHeight((short) 10);
//        listStyle.setFont(listFont);
//        sheet.setDefaultColumnStyle(7, listStyle);

        // 产生表格标题行
        HSSFRow header = sheet.createRow(0);
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        HSSFFont headerFont = workbook.createFont();

        header.setHeightInPoints(ROW_HEADER_HEIGHT);
        headerStyle.setWrapText(true);//设置自动换行
        headerStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        headerStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);//水平居中
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        headerStyle.setFont(headerFont);
        headerStyle.setFillBackgroundColor(HSSFColor.BLUE_GREY.index);//背景色
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN); //下边框
        headerStyle.setBorderLeft(BorderStyle.THIN);//左边框
        headerStyle.setBorderTop(BorderStyle.THIN);//上边框
        headerStyle.setBorderRight(BorderStyle.THIN);//右边框
        headerFont.setBold(true);//加粗
        headerFont.setFontHeightInPoints(ROW_HEADER_FONT_SIZE);

        if (headers != null) {
            for (short i = 0; i < headers.length; i++) {
                HSSFCell cell = header.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
        }

        // 普通cell样式
        HSSFCellStyle bodyStyle = workbook.createCellStyle();
        HSSFFont bodyFont = workbook.createFont();

//        bodyFont.setFontName("宋体");
        bodyFont.setFontHeightInPoints(ROW_BODY_FONT_SIZE);
        bodyStyle.setWrapText(true);//设置自动换行
        bodyStyle.setAlignment(HorizontalAlignment.CENTER_SELECTION);//水平居中
        bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        bodyStyle.setBorderBottom(BorderStyle.THIN); //下边框
        bodyStyle.setBorderLeft(BorderStyle.THIN);//左边框
        bodyStyle.setBorderTop(BorderStyle.THIN);//上边框
        bodyStyle.setBorderRight(BorderStyle.THIN);//右边框
        bodyStyle.setFont(bodyFont);


        Iterator<T> it = dataSet.iterator();
        int rowNum = 0;
        while (it.hasNext()) {

            T t = it.next();
            Class cls = t.getClass();
            HSSFRow row = sheet.createRow(++rowNum);
            row.setHeightInPoints(ROW_BODY_HEIGHT);

            for (int i = 0; i < includeFields.length; i++) {
                try {
                    HSSFCell cell = row.createCell(i);
                    String getMethodName = "get"
                            + includeFields[i].substring(0, 1).toUpperCase()
                            + includeFields[i].substring(1);
                    Method getMethod = cls.getMethod(getMethodName);
                    Object value = getMethod.invoke(t);

                    // 判断值的类型后进行强制类型转换
                    String textValue = changeType(value, simpleDateFormat, dateTimeFormatter);
                    cell.setCellValue(textValue);
                    cell.setCellStyle(bodyStyle);

                } catch (NoSuchMethodException e) {
                    logger.error(e.getMessage(),e);
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage(),e);
                } catch (InvocationTargetException e) {
                    logger.error(e.getMessage(),e);
                }

            }
        }
        writeData(workbook, out);
    }

    private static void writeData(HSSFWorkbook workbook, OutputStream out) {
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

    private static String changeType(Object value, DateFormat simpleDateFormat, DateTimeFormatter dateTimeFormatter) {
        String textValue = "";
        if (value instanceof Boolean) {
            boolean bValue = (Boolean) value;
            textValue = bValue ? "是" : "否";
        } else if (value instanceof Date) {
            Date date = (Date) value;
            textValue = simpleDateFormat.format(date);
        } else if (value instanceof LocalDate) {
            LocalDate localDateTime = (LocalDate) value;
            textValue = localDateTime.format(dateTimeFormatter);
        } else if (value instanceof LocalDateTime) {
            LocalDateTime localDateTime = (LocalDateTime) value;
            textValue = localDateTime.format(dateTimeFormatter);
        } else if (value instanceof IEnum) {
            IEnum iEnum = (IEnum) value;
            textValue = iEnum.display();
        } else if (value instanceof List) {
            textValue = paraseSKU(value);
        } else {
            if (value == null) {
                textValue = "";
            } else {
                textValue = value.toString();
            }
        }
        return textValue;
    }


    /**
     * 拼接spu，sku字符串
     *
     * @param value
     * @return
     */
    private static String paraseSKU(Object value) {
        List list = (List) value;
        if (list.isEmpty())
            return "";

        StringBuilder stringBuilder = new StringBuilder();
        Object o = list.get(0);

        if (o instanceof OrderItemSpuDTO) {
            for (OrderItemSpuDTO item : (List<OrderItemSpuDTO>) list) {
                //TODO
                stringBuilder.append(item.getSpu() + "-" + item.getTitle() + ",");

                for (OrderItemSpuDTO.OrderItemSku sku : item.getSkuList()) {
                    stringBuilder.append(sku.getAttr() + " x " + sku.getQty() + ";");
                }

            }
        } else if (o instanceof OrdersRmaBillItem) {
            for (OrdersRmaBillItem item : (List<OrdersRmaBillItem>) list) {
                stringBuilder.append(item.getProductTitle() + " * " + item.getOrdersItemApplyQty() + ",");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    /**
     * 导入Excel
     *
     * @param clazz
     * @param multipartFile
     * @return
     * @throws Exception
     */
    public static List importExcel(Class<?> clazz, MultipartFile multipartFile) throws Exception {
        String fileName = multipartFile.getOriginalFilename();
        checkFileName(fileName);
        // 取得Excel
        HSSFWorkbook wb = new HSSFWorkbook(multipartFile.getInputStream());
        HSSFSheet sheet = wb.getSheetAt(0);
        Field[] fields = clazz.getDeclaredFields();
        List<Field> fieldList = new ArrayList<Field>(fields.length);
        for (Field field : fields) {
            if (field.isAnnotationPresent(ModelProp.class)) {
                ModelProp modelProp = field.getAnnotation(ModelProp.class);
                if (modelProp.importIndex() != -1) {
                    fieldList.add(field);
                }
            }
        }
        // 行循环
        List modelList = new ArrayList<>(sheet.getPhysicalNumberOfRows() * 2);
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            // 数据模型
            Object model = (Object) clazz.newInstance();
            int nullCount = 0;
            Exception nullError = null;
            for (Field field : fieldList) {
                ModelProp modelProp = field.getAnnotation(ModelProp.class);
                HSSFCell cell = sheet.getRow(i).getCell(modelProp.importIndex());
                try {
                    if (cell == null || cell.toString().length() == 0) {
                        nullCount++;
                        if (!modelProp.nullable()) {
                            nullError = new Exception(String.format(notnullerror, new String[]{"" + (1 + i), modelProp.name(), modelProp.name()}));
                        }
                    } else if (field.getType().equals(Date.class)) {
                        if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                            BeanUtils.setProperty(model, field.getName(), new Date(parseDate(parseString(cell))));
                        } else {
                            BeanUtils.setProperty(model, field.getName(),
                                    new Date(cell.getDateCellValue().getTime()));
                        }
                    } else if (field.getType().equals(LocalDateTime.class)) {
                        if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                            BeanUtils.setProperty(model, field.getName(),
                                    LocalDateTime.parse(String.valueOf(cell).trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                        }
                    } else if (field.getType().equals(Integer.class)) {
                        if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                            BeanUtils.setProperty(model, field.getName(), (int) cell.getNumericCellValue());
                        } else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                            BeanUtils.setProperty(model, field.getName(), Integer.parseInt(parseString(cell)));
                        }
                    } else if (field.getType().equals(BigDecimal.class)) {
                        if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                            BeanUtils.setProperty(model, field.getName(),
                                    new BigDecimal(cell.getNumericCellValue()));
                        } else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                            BeanUtils.setProperty(model, field.getName(), new BigDecimal(parseString(cell)));
                        }
                    } else {
                        if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
                            BeanUtils.setProperty(model, field.getName(),
                                    new BigDecimal(cell.getNumericCellValue()));
                        } else if (Cell.CELL_TYPE_STRING == cell.getCellType()) {
                            BeanUtils.setProperty(model, field.getName(), parseString(cell));
                        }
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(),e);
                    throw new Exception(String.format(errormsg, new String[]{"" + (1 + i), modelProp.name()})
                            + "," + e.getMessage());
                }
            }
            if (nullCount == fieldList.size()) {
                break;
            }
            if (nullError != null) {
                throw nullError;
            }
            modelList.add(model);
        }
        return modelList;
    }

    private static void checkFileName(String fileName) {
        Assert.notNull(fileName, "文件名为空");
        Assert.isTrue(fileName.endsWith(xls) || fileName.endsWith(xlsx), "该文件不是execl文件");
    }

    private static String parseString(HSSFCell cell) {
        return String.valueOf(cell).trim();
    }

    private static long parseDate(String dateString) throws ParseException {
        if (dateString.indexOf("/") == 4) {
            return new SimpleDateFormat("yyyy/MM/dd").parse(dateString).getTime();
        } else if (dateString.indexOf("-") == 4) {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateString).getTime();
        } else if (dateString.indexOf("年") == 4) {
            return new SimpleDateFormat("yyyy年MM月dd").parse(dateString).getTime();
        } else if (dateString.length() == 8) {
            return new SimpleDateFormat("yyyyMMdd").parse(dateString).getTime();
        } else {
            return new Date().getTime();
        }
    }

    public static String DateToString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }
    }
}
