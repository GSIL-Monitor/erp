import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class NormalTest{

    public static final Logger logger = LoggerFactory.getLogger(NormalTest.class);
    public void test() throws Exception {
//        File database = new File("classpath:GeoIP2-City.mmdb");
        ClassPathResource resource = new ClassPathResource("GeoLite2-City.mmdb");
        DatabaseReader reader = new DatabaseReader.Builder( resource.getFile()).build();

        InetAddress ipAddress = InetAddress.getByName("112.95.135.114");

// Replace "city" with the appropriate method for your database, e.g.,
// "country".
        CityResponse response = reader.city(ipAddress);

        Country country = response.getCountry();
        logger.info(country.getIsoCode());            // 'US'
        logger.info(country.getName());               // 'United States'
        logger.info(country.getNames().get("zh-CN")); // '美国'

        Subdivision subdivision = response.getMostSpecificSubdivision();
        logger.info(subdivision.getName());    // 'Minnesota'
        logger.info(subdivision.getIsoCode()); // 'MN'

        City city = response.getCity();
        logger.info(city.getNames().get("zh-CN")); // 'Minneapolis'

        Postal postal = response.getPostal();
        logger.info(postal.getCode()); // '55455'

        Location location = response.getLocation();
        logger.info(""+location.getLatitude());  // 44.9733
        logger.info(""+location.getLongitude()); // -93.2323
    }


    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        List<Integer> list2 = new ArrayList<>();
//        list2.add(1);
        list1.retainAll(list2);
        logger.info(""+list1);
    }
}
