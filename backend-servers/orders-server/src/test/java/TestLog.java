import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLog {

    private static  final Logger logger = LoggerFactory.getLogger(TestLog.class);


    public static void main(String[] args) {
        logger.info("test a file ");

        String a = null;

        try {
            a.toLowerCase();
        }catch(Exception e){

        logger.info( "test a file {}" ,"123" , e);

        logger.error("异常描述" ,e);


        }
    }
}
