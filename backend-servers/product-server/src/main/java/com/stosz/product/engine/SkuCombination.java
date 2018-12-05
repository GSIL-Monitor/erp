package com.stosz.product.engine;


import com.stosz.product.ext.model.AttributeValue;

import java.util.ArrayList;
import java.util.List;
/**
 * sku规则
 * 
 * @author  he_guitang
 * @version  [版本号, 1.0]
 */
public class SkuCombination {
	public static void combinateOne(List<AttributeValue> lst, List<List<AttributeValue>> result) {
        if (result.isEmpty()) {
            for(AttributeValue av : lst) {
                List<AttributeValue> tmp = new ArrayList<>();
                tmp.add(av);
                result.add(tmp);
            }
            return;
        }
        List<List<AttributeValue>> result2 = new ArrayList<>();
        for (List<AttributeValue> subList : result) {
            for (AttributeValue str : lst) {
                List<AttributeValue> tmp = new ArrayList<>();
                tmp.addAll(subList);	
                tmp.add(str);						
                result2.add(tmp);
            }
        }
        result.clear();
        result.addAll(result2);
    }
	
	/**
	 * 根据spu生成sku
	 * 		sku为5位数字
	 */
	public static String generateSku(String spu, String sort){
		if(sort == null || "".equals(sort)){
			sort = "0";
		}
		String rex = "^[0-9]{1,5}$";
		//判断  后5位不是纯数字
		Boolean b = sort.matches(rex);
		if(!b) return null;
		//sku达到最大值
		if("99999".equals(sort)){
			return null;
		}
		Integer srt =  Integer.valueOf(sort);
		++srt;
		String str = srt.toString();
		while(str.length() < 5){
			str = "0" + str;
		}
		return spu + str;
	}

}
