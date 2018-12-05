<%@ page import="java.util.Random" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<body>
<form action="/order/test/save" method="post">
    <h3>订单的基本信息</h3>
    merchantOrderNo: <input type="text" name="order.orderInfo.merchantOrderNo"  value="<%=System.currentTimeMillis()/1000%>" /><br>
    orderTypeEnum: <input type="text" name="order.orderInfo.orderTypeEnum"  value="0" /><br>
    zoneCode: <input type="text" name="order.orderInfo.zoneCode"  value="TW" /><br>
    currencyCode: <input type="text" name="order.orderInfo.currencyCode"  value="ZH" /><br>
    orderExchangeRate: <input type="text" name="order.orderInfo.orderExchangeRate"  value="1" /><br>
    orderAmount: <input type="text" name="order.orderInfo.orderAmount"  value="0" /><br>
    goodsQty: <input type="text" name="order.orderInfo.goodsQty"  value="0" /><br>
    ip: <input type="text" name="order.orderInfo.ip"  value="127.0.0.1" /><br>
    customerMeassage: <input type="text" name="order.orderInfo.customerMeassage"  value="test order" /><br>
    purchaserAt: <input type="text" name="order.orderInfo.purchaserAt"  value="2017-11-17T17:48:47.715" /><br>
    getCode: <input type="text" name="order.orderInfo.getCode"  value="123456" /><br>
    inputCode: <input type="text" name="order.orderInfo.inputCode"  value="123456" /><br>
    seoUserId: <input type="text" name="order.orderInfo.seoUserId"  value="0" /><br>
    creatorId: <input type="text" name="order.orderInfo.creatorId"  value="0" /><br>
    creator: <input type="text" name="order.orderInfo.creator"  value="系统" /><br>
    paymentMethod: <input type="text" name="order.orderInfo.paymentMethod"  value="0" /><br>
    <h3>订单的产品信息</h3>
    spu: <input type="text" name="order.orderItemInfoList.spu"  value="ST50015" /><br>
    sku: <input type="text" name="order.orderItemInfoList.sku"  value="1061602" /><br>
    qty: <input type="text" name="order.orderItemInfoList.qty"  value="1" /><br>
    singleAmount: <input type="text" name="order.orderItemInfoList.singleAmount"  value="100" /><br>
    totalAmount: <input type="text" name="order.orderItemInfoList.totalAmount"  value="100" /><br>
    productId: <input type="text" name="order.orderItemInfoList.productId"  value="<%=new Random(100000000).nextInt()%>" /><br>
    foreignTitle: <input type="text" name="order.orderItemInfoList.foreignTitle"  value="foreignTitle" /><br>
    foreignTotalAmount: <input type="text" name="order.orderItemInfoList.foreignTotalAmount"  value="foreignTotalAmount" /><br>
    attrNameArray: <input type="text" name="order.orderItemInfoList.attrNameArray"  value="attrNameArray" /><br>
    attrValueArray: <input type="text" name="order.orderItemInfoList.attrValueArray"  value="attrValueArray" /><br>
    <h3>订单的联系</h3>
    firstName: <input type="text" name="order.orderLinkInfo.firstName"  value="xxx" /><br>
    lastName: <input type="text" name="order.orderLinkInfo.lastName"  value="yyy" /><br>
    country: <input type="text" name="order.orderLinkInfo.country"  value="中国" /><br>
    telphone: <input type="text" name="order.orderLinkInfo.telphone"  value="13682320515" /><br>
    email: <input type="text" name="order.orderLinkInfo.email"  value="lifuchun@sotosz.com" /><br>
    province: <input type="text" name="order.orderLinkInfo.province"  value="hunan" /><br>
    city: <input type="text" name="order.orderLinkInfo.city"  value="SZ" /><br>
    area: <input type="text" name="order.orderLinkInfo.area"  value="南山" /><br>
    address: <input type="text" name="order.orderLinkInfo.address"  value="动漫园5" /><br>
    zipcode: <input type="text" name="order.orderLinkInfo.zipcode"  value="518000" /><br>
    <h3>订单的附加信息</h3>
    userAgent: <input type="text" name="order.orderWebInfo.userAgent"  value="IE" /><br>
    orderFrom: <input type="text" name="order.orderWebInfo.orderFrom"  value="google" /><br>
    ip: <input type="text" name="order.orderWebInfo.ip"  value="127.0.0.1" /><br>
    webDomain: <input type="text" name="order.orderWebInfo.webDomain"  value="http://www.stosz.com" /><br>
    ==========================================================

    <input type="submit" value="保存订单">

</form>


</body>
