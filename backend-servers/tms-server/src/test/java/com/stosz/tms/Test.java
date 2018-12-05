package com.stosz.tms;

import com.stosz.tms.base.JUnitBaseTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Test extends JUnitBaseTest {

	public static final Logger logger = LoggerFactory.getLogger(Test.class);

	public static void main(String[] args) {

		// List<Integer> list = new ArrayList<>();
		// int max = 1200000;
		// Random random = new Random();
		// for (int i = max; i > 0; i--) {
		// list.add(i);
		// }
		//
		// long start = System.nanoTime();
		// int index = list.indexOf(max - 5);
		// long end = System.nanoTime();
		// logger.info("index value:" + index + ",耗时:" + (end - start));
		//
		// start = System.nanoTime();
		// index = binarySearch(list, max - 5);
		// end = System.nanoTime();
		// logger.info("index value:" + index + ",耗时:" + (end - start));

		// try {
		// logger.info(DESUtils.decrypt("WkUCdKeVKJHPDF8uROisFnNrOHJcFgIs","f88731a6469359a8729ff166f5f3d014"));
		// } catch (Exception e1) {
		// }
		// HessianProxyFactory hessianProxyFactory=new HessianProxyFactory();
		// try {
		// ITransportFacadeService transportFacadeService=(ITransportFacadeService)
		// hessianProxyFactory.create(ITransportFacadeService.class,"http://localhost:8080/tms/remote/ITransportFacadeService");
		//// ResultBean<ShippingExtend>
		// resultBean=transportFacadeService.queryExtendInfo("");
		//// logger.info(resultBean.getCode());
		//// logger.info(resultBean.getItem().getCode());
		// } catch (MalformedURLException e) {
		// // TODO Auto-generated catch block
		// }
		
		String str="48";
		int v=0x30;
		logger.info(str2HexStr(str));
		
	}

	private final static char[] mChars = "0123456789ABCDEF".toCharArray();

	public static String str2HexStr(String str) {
		StringBuilder sb = new StringBuilder();
		byte[] bs = str.getBytes();

		for (int i = 0; i < bs.length; i++) {
			sb.append(mChars[(bs[i] & 0xFF) >> 4]);
			sb.append(mChars[bs[i] & 0x0F]);
			sb.append(' ');
		}
		return sb.toString().trim();
	}

	public static <T extends Comparable<T>> int binarySearch(List<T> list, T key) {
		int index = 0;
		int low = 0;
		int high = list.size() - 1;
		while (low <= high) {
			int mid = (low + high) / 2;
			T value = list.get(mid);
			int val = value.compareTo(key);
			if (val == -1) {
				high = mid + 1;
			} else if (val == 1) {
				low = mid - 1;
			} else {
				index = mid;
				break;
			}
		}
		return index;
	}
}
