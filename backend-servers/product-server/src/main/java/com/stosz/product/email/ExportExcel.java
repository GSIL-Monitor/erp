package com.stosz.product.email;


import com.stosz.product.ext.model.ExcludeRepeat;
import org.apache.poi.hssf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * @author he_guitang
 * @version [1.0 , 2017/11/21]
 */
public class ExportExcel {

    public static final Logger logger = LoggerFactory.getLogger(ExportExcel.class);

    public ByteArrayOutputStream export(List<ExcludeRepeat> list) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("排重报表");
        sheet.setDefaultColumnWidth((int) 15);
        HSSFCellStyle style = wb.createCellStyle();
        HSSFRow row = sheet.createRow(0);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        HSSFCell cell = row.createCell((int) 0);
        cell.setCellValue("排重通过人员");
        cell.setCellStyle(style);
        cell = row.createCell((int) 1);
        cell.setCellValue("部门");
        cell.setCellStyle(style);
        cell = row.createCell((int) 2);
        cell.setCellValue("操作产品数量");
        cell.setCellStyle(style);
        cell = row.createCell((int) 3);
        cell.setCellValue("查重通过数量");
        cell.setCellStyle(style);

        cell = row.createCell((int) 4);
        cell.setCellValue("查重未通过数量");
        cell.setCellStyle(style);
        cell = row.createCell((int) 5);
        cell.setCellValue("通过率");
        cell.setCellStyle(style);
        cell = row.createCell((int) 6);
        cell.setCellValue("未通过率");
        cell.setCellStyle(style);

        cell = row.createCell((int) 7);
        cell.setCellValue("拒绝");
        cell.setCellStyle(style);
        cell = row.createCell((int) 8);
        cell.setCellValue("重复");
        cell.setCellStyle(style);
        cell = row.createCell((int) 9);
        cell.setCellValue("重复通过");
        cell.setCellStyle(style);
        cell = row.createCell((int) 10);
        cell.setCellValue("有风险");
        cell.setCellStyle(style);
        cell = row.createCell((int) 11);
        cell.setCellValue("有风险通过");
        cell.setCellStyle(style);
        cell = row.createCell((int) 12);
        cell.setCellValue("拒绝[排重有风险,主管取消");
        cell.setCellStyle(style);
        cell = row.createCell((int) 13);
        cell.setCellValue("草稿[新品驳回]");
        cell.setCellStyle(style);
        // 向单元格里填充数据
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(list.get(i).getChecker());
            row.createCell(1).setCellValue(list.get(i).getDepartmentName());
            row.createCell(2).setCellValue(list.get(i).getNumber());
            row.createCell(3).setCellValue(list.get(i).getCheckOk());
            row.createCell(4).setCellValue(list.get(i).getUnCheckOk());
            row.createCell(5).setCellValue(list.get(i).getAdoptRate());
            row.createCell(6).setCellValue(list.get(i).getUnAdoptRate());
            row.createCell(7).setCellValue(list.get(i).getCancel());
            row.createCell(8).setCellValue(list.get(i).getDuplication());
            row.createCell(9).setCellValue(list.get(i).getDuplicationCheckOk());
            row.createCell(10).setCellValue(list.get(i).getCheckWarn());
            row.createCell(11).setCellValue(list.get(i).getCheckWarnCheckOk());
            row.createCell(12).setCellValue(list.get(i).getRefuse());
            row.createCell(13).setCellValue(list.get(i).getDraft());
        }
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            return out;
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(),e);
        } catch (IOException e) {
            logger.error(e.getMessage(),e);
        }
        return null;
    }


}
