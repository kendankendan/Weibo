<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>个人信息</title>
<link href="<%=basePath%>/resources//css/infostyle.css" rel="stylesheet" type="text/css" />

</head>

<body>
    <!-- 个人信息  -->
    <div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    </ul>
    </div>
    
    <div class="mainindex">
    
    
    <div class="welinfo">
    <span><img src="<%=basePath%>/resources/img/sun.png" alt="天气" /></span>
    <b>早上好，欢迎使用</b>
    </div>
    
    <div class="welinfo">
    <span><img src="<%=basePath%>/resources/img/time.png" alt="时间" /></span>
    <i></a>
    </div>
    
    
    <div class="xline"></div>
    <div class="box"></div>
   
</body>

</html>
