package com.stosz.plat.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * execl文件读取工具类
 * @author zh
 */
public class ExeclRenderUtils {

    private final static String xls = "xls";
    private final static String xlsx = "xlsx";
    private static int sheetNum = 0;//要解析的sheet下标

    public static List<List<String>> readExecl(MultipartFile multipartFile){
        final String filename = multipartFile.getOriginalFilename();
        checkFileName(filename);

        List<List<String>> rowDataList = Lists.newArrayList();

        final Workbook workbook = getWorkbook(multipartFile);
        try {
            //只获取第一个sheet的数据
            final Sheet sheet = workbook.getSheetAt(0);

            //获得当前sheet的开始行
            int firstRowNum  = sheet.getFirstRowNum();
            //获得当前sheet的结束行
            int lastRowNum = sheet.getLastRowNum();
            //循环除了第一行的所有行
            for(int rowNum = firstRowNum+1;rowNum <= lastRowNum;rowNum++){
                //获得当前行
                Row row = sheet.getRow(rowNum);
                if(row == null){
                    continue;
                }
                //获得当前行的开始列
                int firstCellNum = row.getFirstCellNum();
                //获得当前行的列数
                int lastCellNum = row.getPhysicalNumberOfCells();

                List<String> cellValues = Lists.newArrayList();

                //循环当前行
                for(int cellNum = firstCellNum; cellNum < lastCellNum;cellNum++){
                    Cell cell = row.getCell(cellNum);
                    cellValues.add(getCellValue(cell));
                }
                rowDataList.add(cellValues);
            }
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return rowDataList;
    }

    @Deprecated
    public static  List  readExecl(MultipartFile file,Class clazz,List fileds){
        final String filename = file.getOriginalFilename();
        checkFileName(filename);
        List eList = new ArrayList();
        StringBuilder retJson = new StringBuilder();
        final Workbook workbook = getWorkbook(file);
        //只获取第一个sheet的数据
        final Sheet sheet = workbook.getSheetAt(0);
        retJson.append("[");
        int lastRowNum = sheet.getLastRowNum();//最后一行
        for(int i = 1 ;i <=lastRowNum;i++){
            Row row = sheet.getRow(i);//获得行
            Field[] beanFiled= clazz.getDeclaredFields();
            String rowJson = readExcelRow(row,fileds);
            retJson.append(rowJson);
            if(i<lastRowNum-1)
                retJson.append(",");
        }
        retJson.append("]");
        eList.add(retJson.toString());
        return eList;
    }

    /**
     * 转换为json对
     * @return
     */
    private static String toJsonItem(String name,String val){
        return "\""+name+"\":\""+val+"\"";
    }
    /*
     * 读取行值
     * @return
     */
    private static String readExcelRow(Row row,List fileds){
        StringBuilder rowJson = new StringBuilder();
        int lastCellNum = row.getPhysicalNumberOfCells();
        rowJson.append("{");
        for(int i = 0 ;i<lastCellNum;i++){
            Cell cell = row.getCell(i);
            String cellVal = getCellValue(cell);
            rowJson.append(toJsonItem(fileds.get(i).toString(), cellVal));
            if(i<lastCellNum-1)
                rowJson.append(",");
        }
        rowJson.append("}");
        return rowJson.toString();
    }

    private static void checkFileName(String fileName){
        Assert.notNull(fileName,"文件名为空");
        Assert.isTrue(fileName.endsWith(xls) || fileName.endsWith(xlsx),"该文件不是execl文件");
    }


    private static Workbook getWorkbook(MultipartFile multipartFile){
        Workbook workbook = null;
        String fileName = multipartFile.getOriginalFilename();

        try {
            InputStream is = multipartFile.getInputStream();
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith(xls)){
                //2003
                workbook = new HSSFWorkbook(is);
            } else if(fileName.endsWith(xlsx)){
                //2007
                workbook = new XSSFWorkbook(is);
            }

        }catch (Exception e){
            throw  new RuntimeException(e);
        }
        return workbook;
    }

    private static Workbook getWorkbook(File file){
        Workbook workbook = null;
        String fileName = file.getName();

        try {
            InputStream is = new FileInputStream(file);
            //根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
            if(fileName.endsWith(xls)){
                //2003
                workbook = new HSSFWorkbook(is);
            } else if(fileName.endsWith(xlsx)){
                //2007
                workbook = new XSSFWorkbook(is);
            }

        }catch (Exception e){
            throw  new RuntimeException(e);
        }
        return workbook;
    }


    private static String getCellValue(Cell cell){
        String cellValue = "";
        if(cell == null){
            return cellValue;
        }
        //把数字当成String来读，避免出现1读成1.0的情况
        if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            cell.setCellType(Cell.CELL_TYPE_STRING);
        }
        //判断数据的类型
        switch (cell.getCellType()){
            case Cell.CELL_TYPE_NUMERIC: //数字
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case Cell.CELL_TYPE_STRING: //字符串
                cellValue = String.valueOf(cell.getStringCellValue());
                break;
            case Cell.CELL_TYPE_BOOLEAN: //Boolean
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case Cell.CELL_TYPE_FORMULA: //公式
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case Cell.CELL_TYPE_BLANK: //空值
                cellValue = "";
                break;
            case Cell.CELL_TYPE_ERROR: //故障
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
                break;
        }
        return cellValue;
    }

}
