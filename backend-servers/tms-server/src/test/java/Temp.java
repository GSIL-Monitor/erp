import java.io.File;
import java.util.stream.Stream;

public class Temp {

	public static void main(String[] args) {

		String dir = "/data/erp/tms_heimao/back";

		Stream.of(new File(dir).listFiles()).forEach(item -> {
			String filePath = item.getAbsolutePath();
			int index = filePath.indexOf(".");
			String newFilePath = filePath.substring(0, index) + ".NOD.bak";
			item.renameTo(new File(newFilePath));

		});
	}
}
