<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %> 
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
 <head>
        <title>Your Admin Panel</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

        <link rel="stylesheet" type="text/css" href="<%=path%>/resources/css/easyUI/themes/bootstrap/easyui.css">
		<link rel="stylesheet" type="text/css" href="<%=path%>/resources/css/easyUI/themes/icon.css">
        
        <!-- jQuery AND jQueryUI -->
        <script type="text/javascript" src="<%=basePath%>/resources/js/libs/jquery/1.6/jquery.min.js"></script>
        <script type="text/javascript" src="<%=basePath%>/resources/js/libs/jqueryui/1.8.13/jquery-ui.min.js"></script>
        
        <script type="text/javascript">
        	$(document).ready(function(){
        		  $('.subul').find('li').find('a').click(function(){
        				//$(this).parent().siblings().removeClass('current');
        				$('.subul').find('li').removeClass('current');
        				$(this).parent().addClass('current');
        		  });
        		  $('#sidebarul').find('li').each(function(){
        			  var key = $(this).find('.subul').html();
        			  if(null==key)
        				  return;
        			  key = key.replace(/[\r\n]/g, ""); 
        			  key = key.replace(/\s+/g, '');
        			  if(key==""){
        				  $(this).hide();
        			  }
        		  })
        	});
        	
        </script>
        <link rel="stylesheet" href="<%=basePath%>/resources/css/min.css" />
        <script type="text/javascript" src="<%=basePath%>/resources/js/min.js"></script>
    </head>
    <body>
        
        <div id="sidebar">
            <ul id="sidebarul">
            	<li><a href="#"><img src="<%=basePath%>/resources/img/icons/menu/brush.png" alt="" />系统管理</a>
                    <ul class="subul">
                    	 <shiro:hasPermission name="system:user">  
                         	<li ><a target="rightFrame" href="<%=basePath%>system/user/system-manage-user.jsp">用户管理</a></li>
                         </shiro:hasPermission> 
                         <shiro:hasPermission name="system:role">  
                         <li ><a target="rightFrame" href="<%=basePath%>system/user/system-manage-role.jsp">角色管理</a></li>
                         </shiro:hasPermission> 
                         <shiro:hasPermission name="system:permission">  
                         <li ><a target="rightFrame" href="<%=basePath%>system/user/system-manage-user-role.jsp">用户角色管理</a></li>
                         </shiro:hasPermission> 
                         <shiro:hasPermission name="system:userrole">  
                         <li ><a target="rightFrame" href="<%=basePath%>system/user/system-manage-permission-role.jsp">角色权限管理</a></li>
                         </shiro:hasPermission> 
                    </ul>
                </li>
                <li><a href="#"><img src="<%=basePath%>/resources/img/icons/menu/calendar.png" alt="" />会员管理</a>
                    <ul class="subul">
                         <shiro:hasPermission name="account:info">  
                         <li ><a target="rightFrame" href="<%=basePath%>system/account/system-manage-account.jsp">会员信息管理</a></li>
                         </shiro:hasPermission> 
                         <shiro:hasPermission name="account:weibo">  
                         <li ><a target="rightFrame" href="<%=basePath%>system/weibo/system-manage-weibo.jsp">微博管理</a></li>
                         </shiro:hasPermission> 
                    </ul>
                </li>
            </ul>
        </div>
                
                        
    </body>
</html>