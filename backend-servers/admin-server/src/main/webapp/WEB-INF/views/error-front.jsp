<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
    <title>错误提示！</title>
    <meta charset='utf-8'>
</head>
<body>
<div class="content">
    <%--BEGIN LOGIN FORM--%>
    <div class="row">访问的URL:${url}</div>
    <div class="row">错误消息：${message}</div>
    <div class="row">时间:${timestamp}</div>
    <div class="row">
        异常信息:
        <pre>${exception}</pre>
    </div>
</div>
</body>
<html>
