package com.stosz.plat.common;

import com.stosz.plat.enums.ClassifyEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 36进制编码
 * @author he_guitang
 *
 */
public class StoszCoder {

	public static final Logger logger = LoggerFactory.getLogger(StoszCoder.class);
	/**
	 * 十进制和三十六进制间的转换
	 * @param list 传入当前节点的兄弟集合
	 * @return 返回新增的编码
	 */
	public static String toHex(List<String> list , String parentNo){
		if (parentNo == null) {
			parentNo= "";
		}
		int maxId = 0;
//		Collections.sort(list);
		//首先把父结点前面的部分截取掉，如，父结点编码：01 ， 子结点为 0101，0102，0103， 则截取为：01，02，03
		if (!list.isEmpty()) {
			try {
				int parentNoLen = parentNo.length();
				List<Integer> tmpLst = list.stream().map(e -> Integer.parseInt(e.substring(parentNoLen),36)).collect(Collectors.toList());
				maxId = Collections.max(tmpLst);
			} catch (NumberFormatException e) {
				throw new RuntimeException("父结点:[" + parentNo + "]非法！长度和子结点似乎一样！");
			}
			Assert.isTrue(maxId < Integer.parseInt("zz",36),"父结点编码：[" + parentNo + "]非法！");
		}

		String finalNo = Integer.toString(maxId+1, 36).toLowerCase();
		finalNo = StringUtils.leftPad(finalNo,2,'0');
		return parentNo + finalNo;
	}


    /**
     * spu编码
     * 编码规则：{ 1位的产品特性 } + { 7位的产品id }，产品id不足7位时前补0
     * @param classifyEnum
     * @param id
     * @return
     */
	public static String generateSpu(ClassifyEnum classifyEnum, Integer id) {
		return classifyEnum.name() + StringUtils.leftPad(id+"", 7, '0');
	}

    /**
     * sku 编码
     * 编码规则：{spu做前缀} + {4为的sku序号} ，sku序号不足4位时前补0
     * @param spu
     * @param cnt
     * @return
     */
	public static String generateSku(String spu, Integer cnt) {
		return spu + StringUtils.leftPad(cnt+"", 4, '0');
	}

    public static void main(String[] args) {
//        List<String> list = new ArrayList<>();


//		Integer i = Integer.parseUnsignedInt("15" , 36);
//		logger.info(i);
//        String no = toHex(list);
//        logger.info(no);
		String spu=generateSpu(ClassifyEnum.D, 123);
		logger.info("spu is :" + spu);

		String sku = generateSku(spu, 3);
		logger.info("sku is :" + sku);
	}
}
