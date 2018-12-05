<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<body>
    <h3>订单的基本信息</h3>
    merchantOrderNo: <input type="text" name="order.merchantOrderNo"  value="${order.getMerchantOrderNo()}" /><br>
    orderTypeEnum: <input type="text" name="order.orderTypeEnum"  value="${order.orderTypeEnum}" /><br>
    zoneCode: <input type="text" name="order.zoneCode"  value="${order.zoneCode}" /><br>
    currencyCode: <input type="text" name="order.currencyCode"  value="${order.currencyCode}" /><br>
    orderExchangeRate: <input type="text" name="order.orderExchangeRate"  value="${order.getOrderExchangeRate()}" /><br>
    orderAmount: <input type="text" name="order.orderAmount"  value="${order.orderAmount}" /><br>
    goodsQty: <input type="text" name="order.goodsQty"  value="${order.goodsQty}" /><br>
    ip: <input type="text" name="order.ip"  value="${order.ip}" /><br>
    customerMeassage: <input type="text" name="order.customerMeassage"  value="${order.customerMeassage}" /><br>
    purchaserAt: <input type="text" name="order.purchaserAt"  value="${order.purchaserAt}" /><br>
    getCode: <input type="text" name="order.getCode"  value="${order.getCode}" /><br>
    inputCode: <input type="text" name="order.inputCode"  value="${order.inputCode}" /><br>
    seoUserId: <input type="text" name="order.seoUserId"  value="${order.seoUserId}" /><br>
    creatorId: <input type="text" name="order.creatorId"  value="${order.creatorId}" /><br>
    creator: <input type="text" name="order.creator"  value="${order.creator}" /><br>
    paymentMethod: <input type="text" name="order.paymentMethod"  value="${order.paymentMethod}" /><br>
    orderStatus: <input type="text" name="order.paymentMethod"  value="${order.orderStatusEnum.getDisplay() }" /><br>
    ==========================================================
    <span class="ret"></span>
    <br>
    momo:<input type="text" class="memo"> <br>
   ${btnString}


    <ul>

<c:forEach var="h" items="${his}">
    <li>${h.getEventName()} , ${h.getCreateAt()} , ${h.getMemo()} </li>
</c:forEach>

    </ul>
    <img src="${contextPath}/static/images/orderstate.png">

</body>
<script type="text/javascript" src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".stateEventBton").bind("click",function () {
            $.post("${contextPath}/order/test/changeEvent",{
                    memo:$(".memo").val(),
                    eventName:$(this).attr("event"),
                    orderId:${order.getId()}
                  },
                function (data) {
                  window.location.href="${contextPath}/order/test/${order.getId()}";
                });


        });


    });

</script>


