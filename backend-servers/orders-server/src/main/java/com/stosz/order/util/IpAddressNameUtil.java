package com.stosz.order.util;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.net.InetAddress;


public class IpAddressNameUtil {

    private static final Logger logger = LoggerFactory.getLogger(IpAddressNameUtil.class);

    private IpAddressNameUtil(){}

    private volatile static DatabaseReader reader;

    public static String getNameByIp(String ip){
        if(reader == null){
            synchronized (IpAddressNameUtil.class){
                if(reader == null){
                    ClassPathResource resource = new ClassPathResource("GeoLite2-City.mmdb");
                    try {
                        reader = new DatabaseReader.Builder( resource.getFile()).build();
                    } catch (IOException e) {
                        logger.error("加载IP地址库文件【GeoLite2-City.mmdb】异常");
                    }
                }
            }
        }

        if(ip == null || ip.trim().length() == 0){
            return "";
        }
        InetAddress ipAddress = null;
        CityResponse response = null;
        try {
            ipAddress = InetAddress.getByName(ip.trim());
            response = reader.city(ipAddress);
        } catch (Exception e) {
            logger.error("ip地址无法解析");
            return "";
        }
        StringBuilder ipName = new StringBuilder("");
        Country country = response.getCountry();
        if(country != null){
            String countryName = country.getNames().get("zh-CN");
            ipName.append(country == null ? "" : countryName);
            ipName.append(" ");
        }
        City city = response.getCity();
        if(city != null){
            String cityName = city.getNames().get("zh-CN");
            ipName.append(cityName == null ? "" : cityName);
        }
       return ipName.toString();
    }


    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        logger.info(getNameByIp("112.95.135.114"));
        logger.info(getNameByIp("112.95.135.114"));
        logger.info(getNameByIp("112.95.135.114"));

        logger.info(""+(System.currentTimeMillis() - start));
    }


}
