<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <style>
        table,th,td {
            text-align: center;
            border-collapse: collapse;
        }
        th,td{
            padding:5px 10px;
        }
    </style>
</head>
<body>

<table border="1">
    <thead>
    <tr>
        <th>物流方式名称</th>
        <th>物流方式code</th>
        <th>物流公司名</th>
        <th>物流公司code</th>
        <th>产品类型</th>
        <th>仓库名称</th>
        <th>预警数值</th>
        <th>现有数值</th>
    </tr>
    </thead>
    <tbody>
        <#list warningDataList as warningData>
            <tr>
                <td>${warningData.shippingWayName}</td>
                <td>${warningData.shippingWayCode}</td>
                <td>${warningData.shippingName}</td>
                <td>${warningData.shippingCode}</td>
                <td>${warningData.productType}</td>
                <td>${warningData.wmsName}</td>
                <td>${warningData.warningNo}</td>
                <td>${warningData.nowExistNo}</td>
            </tr>
        </#list>
    </tbody>
</table>

</body>
</html>