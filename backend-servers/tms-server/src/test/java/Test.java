import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Stream;



public class Test {

	public static final Logger logger = LoggerFactory.getLogger(Test.class);
	public Map<String,Object>map = new HashMap<String,Object>();
	public static int count = 0;

	public static void main(String[] args) throws Exception {

		Pattern pattern = Pattern.compile("620126255554");

		Stream.of(new File("/data/erp/tms_heimao/").listFiles()).forEach(item -> {
			 if(!item.getAbsolutePath().endsWith(".bak")) {
			 return;
			 }
			 int index=item.getAbsolutePath().lastIndexOf(".");
			 String fileName=item.getAbsolutePath().substring(0,index);
			 item.renameTo(new File(fileName));

			// 620126255554
//			String fileContent = FileUtils.readFile(item.getAbsolutePath());
//			String[] lines = fileContent.split("\n");
//			for (String line : lines) {
//				if (line.split("\\|").length < 7) {
//					logger.info(line);
//					continue;
//				}
//				if(line.contains("620083187485")) {
//					logger.info(line);
//					count++;
//				}
//			}
		});
		logger.info(""+count);
	}

}
