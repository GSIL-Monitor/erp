package com;

import com.stosz.plat.utils.StringUtil;
import com.stosz.tms.utils.JsonUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ImportTest {

	public static final Logger logger = LoggerFactory.getLogger(ImportTest.class);

	public static void main(String[] args) {
		try {
			String json = "{\"144\":\"泰国B2C\",\"65\":\"香港CXC\",\"98\":\"香港COE\",\"196\":\"泰国NIM\",\"21\":\"台湾YJY\",\"150\":\"印尼HY-P\""
					+ ",\"198\":\"俄罗斯CDEK-P\",\"138\":\"菲律宾XH-P\",\"174\":\"沙特Aramex 普货\",\"31\":\"香港XL\"}";
			
			HashMap<String, String> hashMap=JsonUtils.jsonToMap(json, HashMap.class);
			Workbook workbook = new HSSFWorkbook(new FileInputStream("D://123.xls"));
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> iterator = sheet.iterator();
			HashSet<String> set=new HashSet<>();
			int count=0;
			while (iterator.hasNext()) {
				Row row = iterator.next();
				String value=row.getCell(1).getStringCellValue();
				String shippingName=hashMap.get(value);
				if(value.equals("174")) {
					String value1=StringUtil.concat(row.getCell(2).getStringCellValue(),"\t",row.getCell(5).getStringCellValue());
					logger.info(value1);
					count++;
				}
//				logger.info(
//						row.getCell(1).getStringCellValue() + "|" + row.getCell(2).getStringCellValue() + "|" + row.getCell(5).getStringCellValue());
			}
			logger.info(JsonUtils.toJson(set));
			logger.info(""+count);
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
	}
}
