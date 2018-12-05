<%@ page import="com.stosz.plat.common.MBox" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%--<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>--%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="shortcut icon" href="/admin/static/images/favicon.ico" type="image/x-icon">
    <title>erp登录</title>
    <style>
        .login{
            height: 100%;
            width:  100%;
            position: relative;
        }
        .login .bg{
            background: url(/admin/static/images/background.jpg);
            background-size: 100vw 100vh;
            height: 100%;
            width:  100%;
            position: absolute;
            z-index: -100;
            animation: breathe 12s linear infinite;
        }

        @keyframes breathe{
            0% {
                opacity: 0.9;
                transform: translate(-5px, 0);
                filter: blur(0px);
            }
            33.3% {
                opacity: 1;
                transform: translate(5px, 5px);
                filter: blur(3px);
            }
            100% {
                opacity: 0.9;
                transform: translate(-5px, 0px);
                filter: blur(0px);
            }
        }
        body, html{
            height: 100%;
            width:  100%;
            padding: 0px;
            margin: 0px;
            overflow: hidden;
            position: relative;
        }

        .themeName{
             position: absolute;
             font-size: 9px;
             bottom: 20px;
             left: 20px;
             color: white;
             font-family: Brush Script Std;
        }
        
        .login input{
            border-radius: 20px;
            border: 1px solid rgba(0, 0, 0, 0.5);
            color: white;
            background: rgba(0, 0, 0, 0.5);
            height: 30px;
            font-size: 14px;
            line-height:20px;
            padding: 5px 20px;
            margin-bottom: 30px;
            outline: none;
            width: 300px;
        }
        .login input:focus{
            /*border: 1px solid rgba(0, 128, 255, 1);*/
        }
        .login .logo{
            top: 30%;
            left: 50%;
            transform: translate(-50%, -50%);
            position: absolute;
        }
        form{
            position: relative;
            left: 50%;
            top: 50%;
            transform: translate(-50%, -50%);
            display: inline-block;
        }
        .login input[type='submit']{
            color: grey;
            padding: 30px auto;
            background: linear-gradient(to bottom, white, rgb(216, 239, 239));
            cursor: pointer;
        }
        .login input[type='submit']:hover{
            background: linear-gradient(to bottom, white, rgba(216, 239, 239, 0.4));
        }
        .message{
            font-size: 12px;
            position: absolute;
            right: 0;
            top: 0;
            transform: translate(0, -20px);
            display: block;
            color: white;
        }
    </style>
</head>
<body>
<div class='login'>
    <div class='bg'></div>
    <div class='themeName'>Breathe</div>
    <div class='logo'><img src='/admin/static/images/loginlogo.png'></div>
    <form action="/admin/login" method="post">
        <input type="hidden" name="<%=MBox.PARAM_BACK_URL%>" value="${backUrl}"/>
    <div class='message'>${message}</div>
    <div><input type="text" name="loginid" placeholder='账号' autofocus></div>
    <div><input type="password" name="password" placeholder='密码'></div>
    <div style='height: 30px;'>&nbsp;</div>
    <div><input type="submit" value="登录"></div>
    </form>
    <script>
        if(top != self){
            top.location = self.location;
        }
    </script>

</div>

</body>
</html>
