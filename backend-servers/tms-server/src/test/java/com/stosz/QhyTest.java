package com.stosz;

import com.stosz.tms.base.JUnitBaseTest;
import com.stosz.tms.dto.OrderLinkDto;
import com.stosz.tms.dto.TrackRequest;
import com.stosz.tms.dto.TransportOrderDetail;
import com.stosz.tms.dto.TransportOrderRequest;
import com.stosz.tms.model.api.ShippingExtend;
import com.stosz.tms.service.track.impl.QhyTrackHandler;
import com.stosz.tms.service.transport.impl.QhyTransportHandler;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 正式环境下单,查询轨迹均通
 */
public class QhyTest extends JUnitBaseTest{

    @Autowired
    QhyTransportHandler transportHandler;

    @Autowired
    QhyTrackHandler trackHandler;


    @Test
    public void testAdd() {
        TransportOrderRequest orderRequest = new TransportOrderRequest();
        ShippingExtend extendInfo = new ShippingExtend();
        OrderLinkDto orderLinkDto = new OrderLinkDto();

        orderRequest.setOrderLinkDto(orderLinkDto);
        orderRequest.setOrderNo("85641254666");//订单号
        orderRequest.setAreaId(26);
        orderRequest.setGoodsQty(1);
        orderRequest.setOrderAmount(new BigDecimal(2));//订单金额

        orderLinkDto.setCountryCode("IN");//印度国家二字码
        orderLinkDto.setFirstName("test");
        orderLinkDto.setLastName("test");
        orderLinkDto.setAddress("testAddress");
        orderLinkDto.setCity("shanghai");
        orderLinkDto.setProvince("shanghai");
        orderLinkDto.setTelphone("13333333333");
        orderLinkDto.setZipcode("360733");

        List<TransportOrderDetail> listProduct = new ArrayList<>();
        TransportOrderDetail detail = new TransportOrderDetail();
        detail.setProductNameEN("testTitle");
        detail.setProductNameCN("测试商品");
        detail.setOrderDetailQty(1);
        detail.setPrice(new BigDecimal(12));

        listProduct.add(detail);
        orderRequest.setOrderDetails(listProduct);
        extendInfo.setSenderContactName("lisan");
        extendInfo.setSenderTelphone("14444444444");
        extendInfo.setSenderCountry("CN");//中国二字码
        extendInfo.setSenderProvince("广东省");
        extendInfo.setSenderCity("深圳市");
        extendInfo.setSenderAddress("动漫园");
        extendInfo.setAccount("604b2394a504b593ef2e806737ac9b06");
        extendInfo.setMd5Key("604b2394a504b593ef2e806737ac9b068674992310621bebc6e9326441dd4786");
        //生产环境
        extendInfo.setInterfaceUrl("http://tms.alljoylogistics.com/default/svc/web-service");
        try {
            transportHandler.transportPlaceOrder(orderRequest,extendInfo);
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
    }

    @Test
    public void testQuery(){
        ShippingExtend shippingExtend = new ShippingExtend();
        //生产环境
        shippingExtend.setWaybilltrackUrl("http://tms.alljoylogistics.com/default/svc/web-service");
        shippingExtend.setAccount("604b2394a504b593ef2e806737ac9b06");
        shippingExtend.setMd5Key("604b2394a504b593ef2e806737ac9b068674992310621bebc6e9326441dd4786");
        trackHandler.captureTransTrack(new TrackRequest("ALJAE7336000062YQ"),shippingExtend);
    }

//    @Test
//    public void test11(){
//        String str = "[{\n" +
//                "\t\t\"CountryCode\": \"AD\",\n" +
//                "\t\t\"EName\": \"ANDORRA\",\n" +
//                "\t\t\"CName\": \"安道尔\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"AE\",\n" +
//                "\t\t\"EName\": \"UNITED ARAB EMIRATES\",\n" +
//                "\t\t\"CName\": \"阿拉伯联合酋长国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"AF\",\n" +
//                "\t\t\"EName\": \"AFGHANISTAN\",\n" +
//                "\t\t\"CName\": \"阿富汗\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"AG\",\n" +
//                "\t\t\"EName\": \"ANTIGUA AND BARBUDA\",\n" +
//                "\t\t\"CName\": \"安提瓜及巴布达\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"AI\",\n" +
//                "\t\t\"EName\": \"ANGUILLA\",\n" +
//                "\t\t\"CName\": \"安圭拉岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"AL\",\n" +
//                "\t\t\"EName\": \"ALBANIA\",\n" +
//                "\t\t\"CName\": \"阿尔巴尼亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"AM\",\n" +
//                "\t\t\"EName\": \"ARMENIA\",\n" +
//                "\t\t\"CName\": \"亚美尼亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"AN\",\n" +
//                "\t\t\"EName\": \"NETHERLANDS ANTILLES\",\n" +
//                "\t\t\"CName\": \"荷属安的列斯群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"AO\",\n" +
//                "\t\t\"EName\": \"ANGOLA\",\n" +
//                "\t\t\"CName\": \"安哥拉\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"AR\",\n" +
//                "\t\t\"EName\": \"ARGENTINA\",\n" +
//                "\t\t\"CName\": \"阿根廷\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"AS\",\n" +
//                "\t\t\"EName\": \"AMERICAN SAMOA\",\n" +
//                "\t\t\"CName\": \"美属萨摩亚群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"AT\",\n" +
//                "\t\t\"EName\": \"AUSTRIA\",\n" +
//                "\t\t\"CName\": \"奥地利\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"AU\",\n" +
//                "\t\t\"EName\": \"AUSTRALIA\",\n" +
//                "\t\t\"CName\": \"澳大利亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"AV\",\n" +
//                "\t\t\"EName\": \"大西洋\",\n" +
//                "\t\t\"CName\": \"大西洋\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"AW\",\n" +
//                "\t\t\"EName\": \"ARUBA\",\n" +
//                "\t\t\"CName\": \"阿鲁巴岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"AZ\",\n" +
//                "\t\t\"EName\": \"AZERBAIJAN\",\n" +
//                "\t\t\"CName\": \"阿塞拜疆(独联体)\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"BA\",\n" +
//                "\t\t\"EName\": \"BOSNIA AND HERZEGOVINA\",\n" +
//                "\t\t\"CName\": \"波斯尼亚-黑塞哥维那共和国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"BB\",\n" +
//                "\t\t\"EName\": \"BARBADOS\",\n" +
//                "\t\t\"CName\": \"巴巴多斯\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"BD\",\n" +
//                "\t\t\"EName\": \"BANGLADESH\",\n" +
//                "\t\t\"CName\": \"孟加拉国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"BE\",\n" +
//                "\t\t\"EName\": \"BELGIUM\",\n" +
//                "\t\t\"CName\": \"比利时\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"BF\",\n" +
//                "\t\t\"EName\": \"BURKINA FASO\",\n" +
//                "\t\t\"CName\": \"布基纳法索\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"BG\",\n" +
//                "\t\t\"EName\": \"BULGARIA\",\n" +
//                "\t\t\"CName\": \"保加利亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"BH\",\n" +
//                "\t\t\"EName\": \"BAHRAIN\",\n" +
//                "\t\t\"CName\": \"巴林\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"BI\",\n" +
//                "\t\t\"EName\": \"BURUNDI\",\n" +
//                "\t\t\"CName\": \"布隆迪\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"BJ\",\n" +
//                "\t\t\"EName\": \"BENIN\",\n" +
//                "\t\t\"CName\": \"贝宁\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"BM\",\n" +
//                "\t\t\"EName\": \"BERMUDA\",\n" +
//                "\t\t\"CName\": \"百慕大\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"BN\",\n" +
//                "\t\t\"EName\": \"BRUNEI\",\n" +
//                "\t\t\"CName\": \"文莱\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"BO\",\n" +
//                "\t\t\"EName\": \"BOLIVIA\",\n" +
//                "\t\t\"CName\": \"波利维亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"BR\",\n" +
//                "\t\t\"EName\": \"BRAZIL\",\n" +
//                "\t\t\"CName\": \"巴西\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"BS\",\n" +
//                "\t\t\"EName\": \"BAHAMAS\",\n" +
//                "\t\t\"CName\": \"巴哈马\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"BT\",\n" +
//                "\t\t\"EName\": \"BHUTAN\",\n" +
//                "\t\t\"CName\": \"不丹\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"BV\",\n" +
//                "\t\t\"EName\": \"BOUVET ISLAND\",\n" +
//                "\t\t\"CName\": \"布维岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"BW\",\n" +
//                "\t\t\"EName\": \"BOTSWANA\",\n" +
//                "\t\t\"CName\": \"博茨瓦纳\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"BY\",\n" +
//                "\t\t\"EName\": \"BELARUS\",\n" +
//                "\t\t\"CName\": \"白俄罗斯\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"BZ\",\n" +
//                "\t\t\"EName\": \"BELIZE\",\n" +
//                "\t\t\"CName\": \"伯利兹\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"CA\",\n" +
//                "\t\t\"EName\": \"Canada\",\n" +
//                "\t\t\"CName\": \"加拿大\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"CC\",\n" +
//                "\t\t\"EName\": \"COCOS(KEELING)ISLANDS\",\n" +
//                "\t\t\"CName\": \"科科斯群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"CD\",\n" +
//                "\t\t\"EName\": \"CONGO REPUBLIC\",\n" +
//                "\t\t\"CName\": \"刚果民主共和国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"CF\",\n" +
//                "\t\t\"EName\": \"CENTRAL REPUBLIC\",\n" +
//                "\t\t\"CName\": \"中非共和国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"CG\",\n" +
//                "\t\t\"EName\": \"CONGO\",\n" +
//                "\t\t\"CName\": \"刚果\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"CH\",\n" +
//                "\t\t\"EName\": \"SWITZERLAND\",\n" +
//                "\t\t\"CName\": \"瑞士\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"CI\",\n" +
//                "\t\t\"EName\": \"COTE D'LVOIRE(IVORY)\",\n" +
//                "\t\t\"CName\": \"科特迪瓦(象牙海岸)\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"CK\",\n" +
//                "\t\t\"EName\": \"COOK ISLANDS\",\n" +
//                "\t\t\"CName\": \"库克群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"CL\",\n" +
//                "\t\t\"EName\": \"CHILE\",\n" +
//                "\t\t\"CName\": \"智利\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"CM\",\n" +
//                "\t\t\"EName\": \"CAMEROON\",\n" +
//                "\t\t\"CName\": \"喀麦隆\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"CN\",\n" +
//                "\t\t\"EName\": \"CHINA\",\n" +
//                "\t\t\"CName\": \"中国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"CO\",\n" +
//                "\t\t\"EName\": \"COLOMBIA\",\n" +
//                "\t\t\"CName\": \"哥伦比亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"CR\",\n" +
//                "\t\t\"EName\": \"COSTA RICA\",\n" +
//                "\t\t\"CName\": \"哥斯达黎加\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"CU\",\n" +
//                "\t\t\"EName\": \"CUBA\",\n" +
//                "\t\t\"CName\": \"古巴\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"CV\",\n" +
//                "\t\t\"EName\": \"CAPE VERDE\",\n" +
//                "\t\t\"CName\": \"佛得角群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"CX\",\n" +
//                "\t\t\"EName\": \"CHRISTMAS ISLAND\",\n" +
//                "\t\t\"CName\": \"圣诞岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"CY\",\n" +
//                "\t\t\"EName\": \"CYPRUS\",\n" +
//                "\t\t\"CName\": \"塞浦路斯\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"CZ\",\n" +
//                "\t\t\"EName\": \"CZECH REPUBLIC\",\n" +
//                "\t\t\"CName\": \"捷克共和国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"DE\",\n" +
//                "\t\t\"EName\": \"GERMANY\",\n" +
//                "\t\t\"CName\": \"德国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"DJ\",\n" +
//                "\t\t\"EName\": \"DJIBOUTI\",\n" +
//                "\t\t\"CName\": \"吉布提\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"DK\",\n" +
//                "\t\t\"EName\": \"DENMARK\",\n" +
//                "\t\t\"CName\": \"丹麦\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"DM\",\n" +
//                "\t\t\"EName\": \"DOMINICA\",\n" +
//                "\t\t\"CName\": \"多米尼克\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"DO\",\n" +
//                "\t\t\"EName\": \"DOMINICAN REPUBLIC\",\n" +
//                "\t\t\"CName\": \"多米尼加共合国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"DZ\",\n" +
//                "\t\t\"EName\": \"ALGERIA\",\n" +
//                "\t\t\"CName\": \"阿尔及利亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"EC\",\n" +
//                "\t\t\"EName\": \"ECUADOR\",\n" +
//                "\t\t\"CName\": \"厄瓜多尔\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"EE\",\n" +
//                "\t\t\"EName\": \"ESTONIA\",\n" +
//                "\t\t\"CName\": \"爱沙尼亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"EG\",\n" +
//                "\t\t\"EName\": \"EGYPT\",\n" +
//                "\t\t\"CName\": \"埃及\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"EH\",\n" +
//                "\t\t\"EName\": \"WESTERN SAHARA\",\n" +
//                "\t\t\"CName\": \"西撒哈拉\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"ER\",\n" +
//                "\t\t\"EName\": \"ERITREA\",\n" +
//                "\t\t\"CName\": \"厄里特立亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"ES\",\n" +
//                "\t\t\"EName\": \"SPAIN\",\n" +
//                "\t\t\"CName\": \"西班牙\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"ET\",\n" +
//                "\t\t\"EName\": \"ETHIOPIA\",\n" +
//                "\t\t\"CName\": \"埃塞俄比亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"FI\",\n" +
//                "\t\t\"EName\": \"FINLAND\",\n" +
//                "\t\t\"CName\": \"芬兰\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"FJ\",\n" +
//                "\t\t\"EName\": \"FIJI\",\n" +
//                "\t\t\"CName\": \"斐济\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"FK\",\n" +
//                "\t\t\"EName\": \"FALKLAND ISLAND\",\n" +
//                "\t\t\"CName\": \"福克兰群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"FM\",\n" +
//                "\t\t\"EName\": \"MICRONESIA\",\n" +
//                "\t\t\"CName\": \"密克罗尼西亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"FO\",\n" +
//                "\t\t\"EName\": \"FAROE ISLANDS\",\n" +
//                "\t\t\"CName\": \"法鲁群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"FR\",\n" +
//                "\t\t\"EName\": \"FRANCE\",\n" +
//                "\t\t\"CName\": \"法国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"FX\",\n" +
//                "\t\t\"EName\": \"FRANCE, METROPOLITAN\",\n" +
//                "\t\t\"CName\": \"法属美特罗波利坦\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"GA\",\n" +
//                "\t\t\"EName\": \"GABON\",\n" +
//                "\t\t\"CName\": \"加蓬\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"GB\",\n" +
//                "\t\t\"EName\": \"UNITED KINGDOM\",\n" +
//                "\t\t\"CName\": \"英国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"GD\",\n" +
//                "\t\t\"EName\": \"GRENADA\",\n" +
//                "\t\t\"CName\": \"格林纳达\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"GE\",\n" +
//                "\t\t\"EName\": \"GEORGIA\",\n" +
//                "\t\t\"CName\": \"格鲁吉亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"GF\",\n" +
//                "\t\t\"EName\": \"FRENCH GUIANA\",\n" +
//                "\t\t\"CName\": \"法属圭亚那\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"GG\",\n" +
//                "\t\t\"EName\": \"GUERNSEY\",\n" +
//                "\t\t\"CName\": \"根西岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"GH\",\n" +
//                "\t\t\"EName\": \"GHANA\",\n" +
//                "\t\t\"CName\": \"加纳\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"GI\",\n" +
//                "\t\t\"EName\": \"GIBRALTAR\",\n" +
//                "\t\t\"CName\": \"直布罗陀\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"GL\",\n" +
//                "\t\t\"EName\": \"GREENLAND\",\n" +
//                "\t\t\"CName\": \"格陵兰\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"GM\",\n" +
//                "\t\t\"EName\": \"GAMBIA\",\n" +
//                "\t\t\"CName\": \"冈比亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"GN\",\n" +
//                "\t\t\"EName\": \"GUINEA\",\n" +
//                "\t\t\"CName\": \"几内亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"GP\",\n" +
//                "\t\t\"EName\": \"GUADELOUPE\",\n" +
//                "\t\t\"CName\": \"瓜德罗普\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"GQ\",\n" +
//                "\t\t\"EName\": \"EQUATORIAL GUINEA\",\n" +
//                "\t\t\"CName\": \"赤道几内亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"GR\",\n" +
//                "\t\t\"EName\": \"GREECE\",\n" +
//                "\t\t\"CName\": \"希腊\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"GS\",\n" +
//                "\t\t\"EName\": \"SOUTH GEORGIA AND THE SOUTH SANDWICH ISL\",\n" +
//                "\t\t\"CName\": \"南乔治亚岛和南桑威奇群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"GT\",\n" +
//                "\t\t\"EName\": \"GUATEMALA\",\n" +
//                "\t\t\"CName\": \"危地马拉\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"GU\",\n" +
//                "\t\t\"EName\": \"GUAM\",\n" +
//                "\t\t\"CName\": \"关岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"GW\",\n" +
//                "\t\t\"EName\": \"GUINEA BISSAU\",\n" +
//                "\t\t\"CName\": \"几内亚比绍\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"GY\",\n" +
//                "\t\t\"EName\": \"GUYANA (BRITISH)\",\n" +
//                "\t\t\"CName\": \"圭亚那\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"HK\",\n" +
//                "\t\t\"EName\": \"HONG KONG\",\n" +
//                "\t\t\"CName\": \"香港\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"HM\",\n" +
//                "\t\t\"EName\": \"HEARD ISLAND AND MCDONALD ISLANDS\",\n" +
//                "\t\t\"CName\": \"赫德岛和麦克唐纳岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"HN\",\n" +
//                "\t\t\"EName\": \"HONDURAS\",\n" +
//                "\t\t\"CName\": \"洪都拉斯\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"HR\",\n" +
//                "\t\t\"EName\": \"CROATIA\",\n" +
//                "\t\t\"CName\": \"克罗地亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"HT\",\n" +
//                "\t\t\"EName\": \"HAITI\",\n" +
//                "\t\t\"CName\": \"海地\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"HU\",\n" +
//                "\t\t\"EName\": \"HUNGARY\",\n" +
//                "\t\t\"CName\": \"匈牙利\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"IC\",\n" +
//                "\t\t\"EName\": \"CANARY ISLANDS\",\n" +
//                "\t\t\"CName\": \"加那利群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"ID\",\n" +
//                "\t\t\"EName\": \"INDONESIA\",\n" +
//                "\t\t\"CName\": \"印度尼西亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"IE\",\n" +
//                "\t\t\"EName\": \"IRELAND\",\n" +
//                "\t\t\"CName\": \"爱尔兰\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"IL\",\n" +
//                "\t\t\"EName\": \"ISRAEL\",\n" +
//                "\t\t\"CName\": \"以色列\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"IN\",\n" +
//                "\t\t\"EName\": \"INDIA\",\n" +
//                "\t\t\"CName\": \"印度\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"IO\",\n" +
//                "\t\t\"EName\": \"BRITISH INDIAN OCEAN TERRITORY\",\n" +
//                "\t\t\"CName\": \"英属印度洋地区(查各群岛)\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"IQ\",\n" +
//                "\t\t\"EName\": \"IRAQ\",\n" +
//                "\t\t\"CName\": \"伊拉克\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"IR\",\n" +
//                "\t\t\"EName\": \"IRAN (ISLAMIC REPUBLIC OF)\",\n" +
//                "\t\t\"CName\": \"伊朗\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"IS\",\n" +
//                "\t\t\"EName\": \"ICELAND\",\n" +
//                "\t\t\"CName\": \"冰岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"IT\",\n" +
//                "\t\t\"EName\": \"ITALY\",\n" +
//                "\t\t\"CName\": \"意大利\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"JE\",\n" +
//                "\t\t\"EName\": \"JERSEY\",\n" +
//                "\t\t\"CName\": \"泽西岛(英属)\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"JM\",\n" +
//                "\t\t\"EName\": \"JAMAICA\",\n" +
//                "\t\t\"CName\": \"牙买加\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"JO\",\n" +
//                "\t\t\"EName\": \"JORDAN\",\n" +
//                "\t\t\"CName\": \"约旦\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"JP\",\n" +
//                "\t\t\"EName\": \"JAPAN\",\n" +
//                "\t\t\"CName\": \"日本\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"JU\",\n" +
//                "\t\t\"EName\": \"YUGOSLAVIA\",\n" +
//                "\t\t\"CName\": \"南斯拉夫\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"KA\",\n" +
//                "\t\t\"EName\": \"kenya\",\n" +
//                "\t\t\"CName\": \"肯尼亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"KE\",\n" +
//                "\t\t\"EName\": \"KENYA\",\n" +
//                "\t\t\"CName\": \"肯尼亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"KG\",\n" +
//                "\t\t\"EName\": \"KYRGYZSTAN\",\n" +
//                "\t\t\"CName\": \"吉尔吉斯斯坦\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"KH\",\n" +
//                "\t\t\"EName\": \"CAMBODIA\",\n" +
//                "\t\t\"CName\": \"柬埔寨\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"KI\",\n" +
//                "\t\t\"EName\": \"KIRIBATI REPUBILC\",\n" +
//                "\t\t\"CName\": \"基利巴斯共和国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"KM\",\n" +
//                "\t\t\"EName\": \"COMOROS\",\n" +
//                "\t\t\"CName\": \"科摩罗\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"KN\",\n" +
//                "\t\t\"EName\": \"SAINT KITTS\",\n" +
//                "\t\t\"CName\": \"圣基茨\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"KP\",\n" +
//                "\t\t\"EName\": \"NORTH KOREA\",\n" +
//                "\t\t\"CName\": \"朝鲜\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"KR\",\n" +
//                "\t\t\"EName\": \"SOUTH KOREA\",\n" +
//                "\t\t\"CName\": \"韩国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"KV\",\n" +
//                "\t\t\"EName\": \"KOSOVO\",\n" +
//                "\t\t\"CName\": \"科索沃\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"KW\",\n" +
//                "\t\t\"EName\": \"KUWAIT\",\n" +
//                "\t\t\"CName\": \"科威特\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"KY\",\n" +
//                "\t\t\"EName\": \"CAYMAN ISLANDS\",\n" +
//                "\t\t\"CName\": \"开曼群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"KZ\",\n" +
//                "\t\t\"EName\": \"KAZAKHSTAN\",\n" +
//                "\t\t\"CName\": \"哈萨克斯坦\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"LA\",\n" +
//                "\t\t\"EName\": \"LAOS\",\n" +
//                "\t\t\"CName\": \"老挝\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"LB\",\n" +
//                "\t\t\"EName\": \"LEBANON\",\n" +
//                "\t\t\"CName\": \"黎巴嫩\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"LC\",\n" +
//                "\t\t\"EName\": \"ST. LUCIA\",\n" +
//                "\t\t\"CName\": \"圣卢西亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"LI\",\n" +
//                "\t\t\"EName\": \"LIECHTENSTEIN\",\n" +
//                "\t\t\"CName\": \"列支敦士登\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"LK\",\n" +
//                "\t\t\"EName\": \"SRI LANKA\",\n" +
//                "\t\t\"CName\": \"斯里兰卡\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"LR\",\n" +
//                "\t\t\"EName\": \"LIBERIA\",\n" +
//                "\t\t\"CName\": \"利比里亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"LS\",\n" +
//                "\t\t\"EName\": \"LESOTHO\",\n" +
//                "\t\t\"CName\": \"莱索托\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"LT\",\n" +
//                "\t\t\"EName\": \"LITHUANIA\",\n" +
//                "\t\t\"CName\": \"立陶宛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"LU\",\n" +
//                "\t\t\"EName\": \"LUXEMBOURG\",\n" +
//                "\t\t\"CName\": \"卢森堡\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"LV\",\n" +
//                "\t\t\"EName\": \"LATVIA\",\n" +
//                "\t\t\"CName\": \"拉脱维亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"LY\",\n" +
//                "\t\t\"EName\": \"LIBYA\",\n" +
//                "\t\t\"CName\": \"利比亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MA\",\n" +
//                "\t\t\"EName\": \"MOROCCO\",\n" +
//                "\t\t\"CName\": \"摩洛哥\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MC\",\n" +
//                "\t\t\"EName\": \"MONACO\",\n" +
//                "\t\t\"CName\": \"摩纳哥\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MD\",\n" +
//                "\t\t\"EName\": \"MOLDOVA\",\n" +
//                "\t\t\"CName\": \"摩尔多瓦\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"ME\",\n" +
//                "\t\t\"EName\": \"MONTENEGRO\",\n" +
//                "\t\t\"CName\": \"黑山共和国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MG\",\n" +
//                "\t\t\"EName\": \"MADAGASCAR\",\n" +
//                "\t\t\"CName\": \"马达加斯加\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MH\",\n" +
//                "\t\t\"EName\": \"MARSHALL ISLANDS\",\n" +
//                "\t\t\"CName\": \"马绍尔群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MK\",\n" +
//                "\t\t\"EName\": \"MACEDONIA\",\n" +
//                "\t\t\"CName\": \"马其顿\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"ML\",\n" +
//                "\t\t\"EName\": \"MALI\",\n" +
//                "\t\t\"CName\": \"马里\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MM\",\n" +
//                "\t\t\"EName\": \"MYANMAR\",\n" +
//                "\t\t\"CName\": \"缅甸\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MN\",\n" +
//                "\t\t\"EName\": \"MONGOLIA\",\n" +
//                "\t\t\"CName\": \"蒙古\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MO\",\n" +
//                "\t\t\"EName\": \"MACAU\",\n" +
//                "\t\t\"CName\": \"澳门\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MP\",\n" +
//                "\t\t\"EName\": \"SAIPAN\",\n" +
//                "\t\t\"CName\": \"塞班岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MQ\",\n" +
//                "\t\t\"EName\": \"MARTINIQUE\",\n" +
//                "\t\t\"CName\": \"马提尼克岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MR\",\n" +
//                "\t\t\"EName\": \"MAURITANIA\",\n" +
//                "\t\t\"CName\": \"毛里塔尼亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MS\",\n" +
//                "\t\t\"EName\": \"MONTSERRAT\",\n" +
//                "\t\t\"CName\": \"蒙特塞拉岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MT\",\n" +
//                "\t\t\"EName\": \"MALTA\",\n" +
//                "\t\t\"CName\": \"马尔他\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MU\",\n" +
//                "\t\t\"EName\": \"MAURITIUS\",\n" +
//                "\t\t\"CName\": \"毛里求斯\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MV\",\n" +
//                "\t\t\"EName\": \"MALDIVES\",\n" +
//                "\t\t\"CName\": \"马尔代夫\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MW\",\n" +
//                "\t\t\"EName\": \"MALAWI\",\n" +
//                "\t\t\"CName\": \"马拉维\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MX\",\n" +
//                "\t\t\"EName\": \"MEXICO\",\n" +
//                "\t\t\"CName\": \"墨西哥\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MY\",\n" +
//                "\t\t\"EName\": \"MALAYSIA\",\n" +
//                "\t\t\"CName\": \"马来西亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"MZ\",\n" +
//                "\t\t\"EName\": \"MOZAMBIQUE\",\n" +
//                "\t\t\"CName\": \"莫桑比克\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"NA\",\n" +
//                "\t\t\"EName\": \"NAMIBIA\",\n" +
//                "\t\t\"CName\": \"纳米比亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"NC\",\n" +
//                "\t\t\"EName\": \"NEW CALEDONIA\",\n" +
//                "\t\t\"CName\": \"新喀里多尼亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"NE\",\n" +
//                "\t\t\"EName\": \"NIGER\",\n" +
//                "\t\t\"CName\": \"尼日尔\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"NF\",\n" +
//                "\t\t\"EName\": \"NORFOLK ISLAND\",\n" +
//                "\t\t\"CName\": \"诺褔克岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"NG\",\n" +
//                "\t\t\"EName\": \"NIGERIA\",\n" +
//                "\t\t\"CName\": \"尼日利亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"NI\",\n" +
//                "\t\t\"EName\": \"NICARAGUA\",\n" +
//                "\t\t\"CName\": \"尼加拉瓜\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"NL\",\n" +
//                "\t\t\"EName\": \"NETHERLANDS\",\n" +
//                "\t\t\"CName\": \"荷兰\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"NO\",\n" +
//                "\t\t\"EName\": \"NORWAY\",\n" +
//                "\t\t\"CName\": \"挪威\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"NP\",\n" +
//                "\t\t\"EName\": \"NEPAL\",\n" +
//                "\t\t\"CName\": \"尼泊尔\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"NR\",\n" +
//                "\t\t\"EName\": \"NAURU REPUBLIC\",\n" +
//                "\t\t\"CName\": \"瑙鲁共和国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"NU\",\n" +
//                "\t\t\"EName\": \"NIUE\",\n" +
//                "\t\t\"CName\": \"纽埃岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"NZ\",\n" +
//                "\t\t\"EName\": \"NEW ZEALAND\",\n" +
//                "\t\t\"CName\": \"新西兰\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"OM\",\n" +
//                "\t\t\"EName\": \"OMAN\",\n" +
//                "\t\t\"CName\": \"阿曼\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"PA\",\n" +
//                "\t\t\"EName\": \"PANAMA\",\n" +
//                "\t\t\"CName\": \"巴拿马\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"PE\",\n" +
//                "\t\t\"EName\": \"PERU\",\n" +
//                "\t\t\"CName\": \"秘鲁\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"PF\",\n" +
//                "\t\t\"EName\": \"FRENCH POLYNESIA\",\n" +
//                "\t\t\"CName\": \"塔希堤岛(法属波利尼西亚)\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"PG\",\n" +
//                "\t\t\"EName\": \"PAPUA NEW GUINEA\",\n" +
//                "\t\t\"CName\": \"巴布亚新几内亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"PH\",\n" +
//                "\t\t\"EName\": \"PHILIPPINES\",\n" +
//                "\t\t\"CName\": \"菲律宾\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"PK\",\n" +
//                "\t\t\"EName\": \"PAKISTAN\",\n" +
//                "\t\t\"CName\": \"巴基斯坦\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"PL\",\n" +
//                "\t\t\"EName\": \"POLAND\",\n" +
//                "\t\t\"CName\": \"波兰\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"PM\",\n" +
//                "\t\t\"EName\": \"SAINT PIERRE AND MIQUELON\",\n" +
//                "\t\t\"CName\": \"圣皮埃尔和密克隆群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"PN\",\n" +
//                "\t\t\"EName\": \"PITCAIRN ISLANDS\",\n" +
//                "\t\t\"CName\": \"皮特凯恩群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"PR\",\n" +
//                "\t\t\"EName\": \"PUERTO RICO\",\n" +
//                "\t\t\"CName\": \"波多黎各\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"PS\",\n" +
//                "\t\t\"EName\": \"Palestine Torritories\",\n" +
//                "\t\t\"CName\": \"巴勒斯坦\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"PT\",\n" +
//                "\t\t\"EName\": \"PORTUGAL\",\n" +
//                "\t\t\"CName\": \"葡萄牙\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"PW\",\n" +
//                "\t\t\"EName\": \"PALAU\",\n" +
//                "\t\t\"CName\": \"帕劳\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"PY\",\n" +
//                "\t\t\"EName\": \"PARAGUAY\",\n" +
//                "\t\t\"CName\": \"巴拉圭\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"QA\",\n" +
//                "\t\t\"EName\": \"QATAR\",\n" +
//                "\t\t\"CName\": \"卡塔尔\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"RE\",\n" +
//                "\t\t\"EName\": \"REUNION ISLAND\",\n" +
//                "\t\t\"CName\": \"留尼汪岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"RO\",\n" +
//                "\t\t\"EName\": \"ROMANIA\",\n" +
//                "\t\t\"CName\": \"罗马尼亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"RS\",\n" +
//                "\t\t\"EName\": \"SERBIA, REPUBLIC OF\",\n" +
//                "\t\t\"CName\": \"塞尔维亚共和国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"RU\",\n" +
//                "\t\t\"EName\": \"RUSSIA\",\n" +
//                "\t\t\"CName\": \"俄罗斯\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"RW\",\n" +
//                "\t\t\"EName\": \"RWANDA\",\n" +
//                "\t\t\"CName\": \"卢旺达\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"SA\",\n" +
//                "\t\t\"EName\": \"SAUDI ARABIA\",\n" +
//                "\t\t\"CName\": \"沙特阿拉伯\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"SB\",\n" +
//                "\t\t\"EName\": \"SOLOMON ISLANDS\",\n" +
//                "\t\t\"CName\": \"所罗门群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"SC\",\n" +
//                "\t\t\"EName\": \"SEYCHELLES\",\n" +
//                "\t\t\"CName\": \"塞舌尔\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"SD\",\n" +
//                "\t\t\"EName\": \"SUDAN\",\n" +
//                "\t\t\"CName\": \"苏丹\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"SE\",\n" +
//                "\t\t\"EName\": \"SWEDEN\",\n" +
//                "\t\t\"CName\": \"瑞典\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"SG\",\n" +
//                "\t\t\"EName\": \"SINGAPORE\",\n" +
//                "\t\t\"CName\": \"新加坡\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"SH\",\n" +
//                "\t\t\"EName\": \"ST HELENA\",\n" +
//                "\t\t\"CName\": \"圣赫勒拿岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"SI\",\n" +
//                "\t\t\"EName\": \"SLOVENIA\",\n" +
//                "\t\t\"CName\": \"斯洛文尼亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"SJ\",\n" +
//                "\t\t\"EName\": \"SVALBARD AND JAN MAYEN\",\n" +
//                "\t\t\"CName\": \"斯瓦尔巴岛和扬马延岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"SK\",\n" +
//                "\t\t\"EName\": \"SLOVAKIA REPUBLIC\",\n" +
//                "\t\t\"CName\": \"斯洛伐克共和国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"SL\",\n" +
//                "\t\t\"EName\": \"SIERRA LEONE\",\n" +
//                "\t\t\"CName\": \"塞拉里昂\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"SM\",\n" +
//                "\t\t\"EName\": \"SAN MARINO\",\n" +
//                "\t\t\"CName\": \"圣马力诺\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"SN\",\n" +
//                "\t\t\"EName\": \"SENEGAL\",\n" +
//                "\t\t\"CName\": \"塞内加尔\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"SO\",\n" +
//                "\t\t\"EName\": \"SOMALIA\",\n" +
//                "\t\t\"CName\": \"索马里\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"SR\",\n" +
//                "\t\t\"EName\": \"SURINAME\",\n" +
//                "\t\t\"CName\": \"苏里南\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"SS\",\n" +
//                "\t\t\"EName\": \"SOUTH SUDAN\",\n" +
//                "\t\t\"CName\": \"南苏丹共和国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"ST\",\n" +
//                "\t\t\"EName\": \"SAO TOME AND PRINCIPE\",\n" +
//                "\t\t\"CName\": \"圣多美和普林西比\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"SV\",\n" +
//                "\t\t\"EName\": \"EL SALVADOR\",\n" +
//                "\t\t\"CName\": \"萨尔瓦多\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"SY\",\n" +
//                "\t\t\"EName\": \"SYRIA\",\n" +
//                "\t\t\"CName\": \"叙利亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"SZ\",\n" +
//                "\t\t\"EName\": \"SWAZILAND\",\n" +
//                "\t\t\"CName\": \"斯威士兰\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"TA\",\n" +
//                "\t\t\"EName\": \"TRISTAN DA CUNBA\",\n" +
//                "\t\t\"CName\": \"特里斯坦\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"TC\",\n" +
//                "\t\t\"EName\": \"TURKS AND CAICOS ISLANDS\",\n" +
//                "\t\t\"CName\": \"特克斯和凯科斯群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"TD\",\n" +
//                "\t\t\"EName\": \"CHAD\",\n" +
//                "\t\t\"CName\": \"乍得\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"TF\",\n" +
//                "\t\t\"EName\": \"FRENCH SOUTHERN TERRITORIES\",\n" +
//                "\t\t\"CName\": \"法属南部领土\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"TG\",\n" +
//                "\t\t\"EName\": \"TOGO\",\n" +
//                "\t\t\"CName\": \"多哥\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"TH\",\n" +
//                "\t\t\"EName\": \"THAILAND\",\n" +
//                "\t\t\"CName\": \"泰国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"TJ\",\n" +
//                "\t\t\"EName\": \"TAJIKISTAN\",\n" +
//                "\t\t\"CName\": \"塔吉克斯坦\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"TK\",\n" +
//                "\t\t\"EName\": \"TOKELAU\",\n" +
//                "\t\t\"CName\": \"托克劳\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"TL\",\n" +
//                "\t\t\"EName\": \"EAST TIMOR\",\n" +
//                "\t\t\"CName\": \"东帝汶\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"TM\",\n" +
//                "\t\t\"EName\": \"TURKMENISTAN\",\n" +
//                "\t\t\"CName\": \"土库曼斯坦\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"TN\",\n" +
//                "\t\t\"EName\": \"TUNISIA\",\n" +
//                "\t\t\"CName\": \"突尼斯\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"TO\",\n" +
//                "\t\t\"EName\": \"TONGA\",\n" +
//                "\t\t\"CName\": \"汤加\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"TR\",\n" +
//                "\t\t\"EName\": \"TURKEY\",\n" +
//                "\t\t\"CName\": \"土耳其\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"TT\",\n" +
//                "\t\t\"EName\": \"TRINIDAD AND TOBAGO\",\n" +
//                "\t\t\"CName\": \"特立尼达和多巴哥\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"TV\",\n" +
//                "\t\t\"EName\": \"TUVALU\",\n" +
//                "\t\t\"CName\": \"图瓦卢\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"TW\",\n" +
//                "\t\t\"EName\": \"TAIWAN\",\n" +
//                "\t\t\"CName\": \"台湾\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"TZ\",\n" +
//                "\t\t\"EName\": \"TANZANIA\",\n" +
//                "\t\t\"CName\": \"坦桑尼亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"UA\",\n" +
//                "\t\t\"EName\": \"UKRAINE\",\n" +
//                "\t\t\"CName\": \"乌克兰\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"UG\",\n" +
//                "\t\t\"EName\": \"UGANDA\",\n" +
//                "\t\t\"CName\": \"乌干达\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"UM\",\n" +
//                "\t\t\"EName\": \"UNITED STATES MINOR OUTLYING ISLANDS\",\n" +
//                "\t\t\"CName\": \"美国本土外小岛屿\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"US\",\n" +
//                "\t\t\"EName\": \"UNITED STATES OF AMERICA\",\n" +
//                "\t\t\"CName\": \"美国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"UY\",\n" +
//                "\t\t\"EName\": \"URUGUAY\",\n" +
//                "\t\t\"CName\": \"乌拉圭\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"UZ\",\n" +
//                "\t\t\"EName\": \"UZBEKISTAN\",\n" +
//                "\t\t\"CName\": \"乌兹别克斯坦\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"VA\",\n" +
//                "\t\t\"EName\": \"VATICAN CITY\",\n" +
//                "\t\t\"CName\": \"梵蒂冈\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"VC\",\n" +
//                "\t\t\"EName\": \"SAINT VINCENT AND THE GRENADINES\",\n" +
//                "\t\t\"CName\": \"圣文森特和格林纳丁斯岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"VD\",\n" +
//                "\t\t\"EName\": \"试算\",\n" +
//                "\t\t\"CName\": \"不知名的国家\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"VE\",\n" +
//                "\t\t\"EName\": \"VENEZUELA\",\n" +
//                "\t\t\"CName\": \"委内瑞拉\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"VG\",\n" +
//                "\t\t\"EName\": \"VIRGIN ISLAND (GB)\",\n" +
//                "\t\t\"CName\": \"英属维尔京群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"VI\",\n" +
//                "\t\t\"EName\": \"VIRGIN ISLAND (US)\",\n" +
//                "\t\t\"CName\": \"美属维尔京群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"VN\",\n" +
//                "\t\t\"EName\": \"VIETNAM\",\n" +
//                "\t\t\"CName\": \"越南\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"VU\",\n" +
//                "\t\t\"EName\": \"VANUATU\",\n" +
//                "\t\t\"CName\": \"瓦努阿图\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"VV\",\n" +
//                "\t\t\"EName\": \"llx\",\n" +
//                "\t\t\"CName\": \"不列颠帝国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"WF\",\n" +
//                "\t\t\"EName\": \"WALLIS AND FUTUNA ISLANDS\",\n" +
//                "\t\t\"CName\": \"瓦利斯群岛和富图纳群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"WS\",\n" +
//                "\t\t\"EName\": \"WESTERN SAMOA\",\n" +
//                "\t\t\"CName\": \"西萨摩亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"XB\",\n" +
//                "\t\t\"EName\": \"BONAIRE\",\n" +
//                "\t\t\"CName\": \"伯奈尔岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"XC\",\n" +
//                "\t\t\"EName\": \"CURACAO\",\n" +
//                "\t\t\"CName\": \"库拉索岛(荷兰)\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"XD\",\n" +
//                "\t\t\"EName\": \"ASCENSION\",\n" +
//                "\t\t\"CName\": \"阿森松\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"XE\",\n" +
//                "\t\t\"EName\": \"ST. EUSTATIUS\",\n" +
//                "\t\t\"CName\": \"圣尤斯塔提马斯岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"XG\",\n" +
//                "\t\t\"EName\": \"SPANISH TERRITORIES OF N.AFRICA\",\n" +
//                "\t\t\"CName\": \"北非西班牙属土\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"XH\",\n" +
//                "\t\t\"EName\": \"AZORES\",\n" +
//                "\t\t\"CName\": \"亚速尔群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"XI\",\n" +
//                "\t\t\"EName\": \"MADEIRA\",\n" +
//                "\t\t\"CName\": \"马德拉岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"XJ\",\n" +
//                "\t\t\"EName\": \"BALEARIC ISLANDS\",\n" +
//                "\t\t\"CName\": \"巴利阿里群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"XK\",\n" +
//                "\t\t\"EName\": \"CAROLINE ISLANDS\",\n" +
//                "\t\t\"CName\": \"加罗林群岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"XM\",\n" +
//                "\t\t\"EName\": \"ST. MAARTEN\",\n" +
//                "\t\t\"CName\": \"圣马腾岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"XN\",\n" +
//                "\t\t\"EName\": \"NEVIS\",\n" +
//                "\t\t\"CName\": \"尼维斯岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"XS\",\n" +
//                "\t\t\"EName\": \"SOMALILAND\",\n" +
//                "\t\t\"CName\": \"索马里共和国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"XX\",\n" +
//                "\t\t\"EName\": \"钓鱼岛\",\n" +
//                "\t\t\"CName\": \"钓鱼岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"XY\",\n" +
//                "\t\t\"EName\": \"ST. BARTHELEMY\",\n" +
//                "\t\t\"CName\": \"圣巴特勒米岛\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"YE\",\n" +
//                "\t\t\"EName\": \"YEMEN, REPUBLIC OF\",\n" +
//                "\t\t\"CName\": \"也门阿拉伯共合国\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"YT\",\n" +
//                "\t\t\"EName\": \"MAYOTTE\",\n" +
//                "\t\t\"CName\": \"马约特\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"ZA\",\n" +
//                "\t\t\"EName\": \"SOUTH AFRICA\",\n" +
//                "\t\t\"CName\": \"南非\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"ZE\",\n" +
//                "\t\t\"EName\": \"59下山了\",\n" +
//                "\t\t\"CName\": \"59下山了\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"ZM\",\n" +
//                "\t\t\"EName\": \"ZAMBIA\",\n" +
//                "\t\t\"CName\": \"赞比亚\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"ZR\",\n" +
//                "\t\t\"EName\": \"ZAIRE\",\n" +
//                "\t\t\"CName\": \"扎伊尔\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"ZW\",\n" +
//                "\t\t\"EName\": \"ZIMBABWE\",\n" +
//                "\t\t\"CName\": \"津巴布韦\"\n" +
//                "\t}, {\n" +
//                "\t\t\"CountryCode\": \"ZX\",\n" +
//                "\t\t\"EName\": \"银河系\",\n" +
//                "\t\t\"CName\": \"银河系\"\n" +
//                "\t}]";
//        ArrayList<Map<String,String>> list = JsonUtils.jsonToObject(str,ArrayList.class);
//        File file = new File("F:\\qhy接口国家二字码.txt");
//
//
//        OutputStream out = null;
//        try {
//            out = new FileOutputStream(file);
//            for(Map<String,String> map:list){
//                String message = "二字码："+map.get("CountryCode")+"  国家英文名："+map.get("EName")+"  国家中文名："+map.get("CName")+"\r\n";
//
//                byte[] data = message.getBytes();
//
//                out.write(data);
//            }
//
//        } catch (Exception e) {
//        } finally {
//            try {
//                // 关闭输出流
//                out.close();
//            } catch (Exception e) {
//            }
//        }
//
//    }


}
