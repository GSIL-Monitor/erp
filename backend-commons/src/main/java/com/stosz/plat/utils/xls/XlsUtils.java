package com.stosz.plat.utils.xls;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @auther carter
 * create time    2017-12-06
 * 可以控制顺序的xls导出工具类；
 */
public final class XlsUtils {

    private static final Logger logger = LoggerFactory.getLogger(XlsUtils.class);


    public void export(HttpServletResponse response, String fileName, String sheetName, List<XlsRowModel> rows) throws Exception {

        if (fileName == null && fileName.trim().length() == 0) {
            throw new Exception("文件名不能为空");
        }

        fileName = fileName + ".xls";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);


        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(sheetName);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0,0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("sys");

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        LinkedList<XlsCellModel> cells = rows.get(0).getCells();
        for (short i = 0; i < cells.size(); i++)
        {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(cells.get(i).getTitle());
            text.applyFont(HSSFColor.BLACK.index);
            cell.setCellValue(text);
        }

        //产生表格的数据列
       for (int iRow=0; iRow< rows.size(); iRow++)
       {
           row = sheet.createRow(iRow);
           XlsRowModel xlsRowModel = rows.get(iRow);

           LinkedList<XlsCellModel> cellsList = xlsRowModel.getCells();
           for (int iCell = 0; iCell< cellsList.size(); iCell++)
           {
               HSSFCell cell = row.createCell(iCell);
                XlsCellModel xlsCellModel = cellsList.get(iCell);
                String textValue = xlsCellModel.getColumnValue();
                // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                if (null != textValue)
                {
                    Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                    Matcher matcher = p.matcher(textValue);
                    if (matcher.matches()) {// 是数字当作double处理
                        cell.setCellValue(Double.parseDouble(textValue));
                    } else
                    {
                        HSSFRichTextString richString = new HSSFRichTextString(textValue);
                        HSSFFont font3 = workbook.createFont();
                        font3.setColor(HSSFColor.BLACK.index);
                        richString.applyFont(font3);
                        cell.setCellValue(richString);
                    }
                }
           }
       }

        OutputStream out = null;
        try {
            out = response.getOutputStream();
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
