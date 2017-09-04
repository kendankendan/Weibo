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
<link rel="shortcut icon" href="http://static.hdslb.com/images/favicon.ico">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Admin</title>
</head>
    <frameset rows="40,*" cols="*" frameborder="no" border="0" framespacing="0">
	    <frame src="<%=basePath%>system/top.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
	    <frameset cols="242,*" frameborder="no" border="0" framespacing="0">
	    	<frame src="<%=basePath%>system/left.jsp" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame" title="leftFrame" />
	    	<frame src="<%=basePath%>system/myInfo.jsp" name="rightFrame" id="rightFrame" title="rightFrame" />
	  	</frameset>
	</frameset>
<noframes>
	<body>
	</body>
</noframes>
</html>