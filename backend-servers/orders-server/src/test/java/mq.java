import com.stosz.plat.rabbitmq.RabbitMQMessage;
import com.stosz.plat.utils.JsonUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther carter
 * create time    2017-12-08
 */
public class mq {

    public static final Logger logger = LoggerFactory.getLogger(mq.class);
    @Test
    public void testJson() {

        String json="{\n" +
                "    \"messageType\": \"frontOrder\",\n" +
                "    \"data\": {\n" +
                "        \"key\": \"c04bfa061c69e13310947e83a3d9276a\",\n" +
                "        \"web_url\": \"www.livestou.com\",\n" +
                "        \"first_name\": \"测试单\",\n" +
                "        \"last_name\": \"\",\n" +
                "        \"tel\": \"0952652569\",\n" +
                "        \"email\": \"liujun@stosz.com\",\n" +
                "        \"address\": \"测试地址4测试地址4\",\n" +
                "        \"remark\": \"\",\n" +
                "        \"zipcode\": \"10150\",\n" +
                "        \"country\": \"中国\",\n" +
                "        \"province\": \"台灣\",\n" +
                "        \"city\": \"\",\n" +
                "        \"area\": \"\",\n" +
                "        \"products\": [\n" +
                "            {\n" +
                "                \"id_product\": \"87859\",\n" +
                "                \"product_id\": \"1018\",\n" +
                "                \"product_title\": \"哥哥专辑红，送大幅海报\",\n" +
                "                \"sale_title\": \"哥哥专辑红，送大幅海报\",\n" +
                "                \"price\": 300,\n" +
                "                \"promotion_price\": 300,\n" +
                "                \"price_title\": \"NT$300\",\n" +
                "                \"qty\": 1,\n" +
                "                \"attrs\": [\n" +
                "                    \"230837\",\n" +
                "                    \"230836\"\n" +
                "                ],\n" +
                "                \"attrs_title\": [\n" +
                "                    \"礼物盒包装\",\n" +
                "                    \"唱片+签名海报\"\n" +
                "                ],\n" +
                "                \"spu\": \"\",\n" +
                "                \"sku\": \"\",\n" +
                "                \"id_activity\": \"\",\n" +
                "                \"product_title_foregin\": \"\"\n" +
                "            }\n" +
                "        ],\n" +
                "        \"id_zone\": \"2\",\n" +
                "        \"code_zone\": \"TW\",\n" +
                "        \"id_department\": \"26\",\n" +
                "        \"id_users\": \"1262\",\n" +
                "        \"identify\": \"1262\",\n" +
                "        \"grand_total\": 300,\n" +
                "        \"currency_code\": \"TWD\",\n" +
                "        \"date_purchase\": \"2017-12-12 17:22:51\",\n" +
                "        \"payment_method\": \"0\",\n" +
                "        \"payment_status\": \"processing\",\n" +
                "        \"payment_details\": \"\",\n" +
                "        \"created_at\": \"2017-12-12 17:22:51\",\n" +
                "        \"ip\": \"127.0.0.1\",\n" +
                "        \"user_agent\": \"Mozilla/5.0 (Linux; Android 5.1.1; Nexus 6 Build/LYZ28E) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Mobile Safari/537.36\",\n" +
                "        \"http_referer\": \"http://www.shopadmin.com/index.php\",\n" +
                "        \"number\": 1,\n" +
                "        \"expends\": [],\n" +
                "        \"web_info\": \"a:14:{s:10:\\\"colorDepth\\\";s:2:\\\"24\\\";s:10:\\\"resolution\\\";s:9:\\\"1920x1080\\\";s:8:\\\"timeZone\\\";N;s:10:\\\"browserLan\\\";s:12:\\\"简体中文\\\";s:9:\\\"httpHeads\\\";a:8:{s:4:\\\"HOST\\\";s:16:\\\"www.livestou.com\\\";s:10:\\\"CONNECTION\\\";s:10:\\\"keep-alive\\\";s:10:\\\"USER-AGENT\\\";s:113:\\\"Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/62.0.3202.94 Safari/537.36\\\";s:6:\\\"ACCEPT\\\";s:3:\\\"*/*\\\";s:7:\\\"REFERER\\\";s:32:\\\"http://www.livestou.com/15153223\\\";s:15:\\\"ACCEPT-ENCODING\\\";s:13:\\\"gzip, deflate\\\";s:15:\\\"ACCEPT-LANGUAGE\\\";s:14:\\\"zh-CN,zh;q=0.9\\\";s:6:\\\"COOKIE\\\";s:329:\\\"_utm=973; POP800_VISIT_TIMES=2; PAGE_VIEW_TIMES=4; POP800_VISITOR_ID_L=9C8FD9D2F13BDE7F9C91D1FB36F976A2; google_js_st=1; PHPSESSID=837ut7d19cjvltjmtogk4k0c00; _gat=1; need_login=0; _ga=GA1.2.1811865322.1512522788; _gid=GA1.2.1999631861.1513040937; uuid=d2ef41b7b8034cf795888cbf193685ba; referer=http://www.shopadmin.com/index.php\\\";}s:16:\\\"orderSubmitTimer\\\";i:14;s:10:\\\"indexTimer\\\";i:23;s:9:\\\"disableJs\\\";i:0;s:6:\\\"device\\\";s:6:\\\"mobile\\\";s:10:\\\"mob_backup\\\";s:0:\\\"\\\";s:7:\\\"website\\\";s:27:\\\"www.livestou.com/644yangdan\\\";s:7:\\\"vercode\\\";s:4:\\\"3860\\\";s:6:\\\"incode\\\";s:4:\\\"3860\\\";s:13:\\\"verify_status\\\";i:1;}\",\n" +
                "        \"order_id\": \"131712120522515480\",\n" +
                "        \"website\": \"www.livestou.com/644yangdan\",\n" +
                "        \"username\": \"yangdan\"\n" +
                "    },\n" +
                "    \"from\": \"front\",\n" +
                "    \"method\": \"insert\"\n" +
                "}";


        HashMap map =  JsonUtil.readValue(json,HashMap.class);


        logger.info(""+map);

    }


    @Test
    public void testGeneMessage(){

        Map<String,String> dataMap = new HashMap<>();
        dataMap.put("id","xxx");
        dataMap.put("name","fuck");


        RabbitMQMessage rabbitMQMessage = new RabbitMQMessage("src/main/webapp/test", "insert", dataMap);

        logger.info(JsonUtil.toJson(rabbitMQMessage));

    }
}
