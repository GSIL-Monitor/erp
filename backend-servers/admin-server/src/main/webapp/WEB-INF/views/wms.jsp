<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% String context = request.getContextPath();
    request.setAttribute("context", context);%>
<body>
<form method="post" action="${context}/tool/wms">
    重推开始日期：<input type="text" name="start_date"> 重推结束日期：<input type="text" name="end_date">
    <input type="submit" value="重新生成wms消息">
</form>

<h3>按照sku批量重新推送</h3>
<textarea cols="50" rows="30" placeholder="sku,请按照，或者换行作为分隔符号" class="skus">

</textarea><br>
<button class="wms_repost">批量重新推送wms</button>

<button class="list_repost">查询没有推送成功的sku列表</button>

<span style="color: green">${message}</span>
<script type="text/javascript" src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".wms_repost").bind("click", function () {
            $.post("${context}/tool/wms_repost", {"skus": $(".skus").val()}, function (data) {
                $(".ret").empty().append(data);
            });

        });

        $(".list_repost").bind("click", function () {
            $.post("${context}/tool/wms_repost_list", function (data) {
                $(".skus").val(data);
            });

        });

    });
</script>
</body>