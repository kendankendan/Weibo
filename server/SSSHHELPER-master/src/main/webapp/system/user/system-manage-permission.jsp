<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="shortcut icon" href="http://static.hdslb.com/images/favicon.ico">
<title>权限管理</title>

<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/jquery.multiselect.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/style.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/prettify.css" />
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/jquery-ui.css" />
<script type="text/javascript" src="<%=basePath%>resources/js/jquery.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/js/jquery-ui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/js/prettify.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/js/multiselect/jquery.multiselect.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/js/multiselect/jquery.multiselect.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/js/multiselect/jquery.multiselect.filter.js"></script>


<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/easyUI/themes/bootstrap/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>resources/css/easyUI/themes/icon.css">
<script type="text/javascript" src="<%=basePath%>resources/js/easyUI/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>resources/js/easyUI/locale/easyui-lang-zh_CN.js"></script>
<style>
	.datagrid-btable tr{height: 30px;}
</style>
<script>
	//获取链接数据
	function getUrlParam(name) {
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	    if (r != null) return unescape(r[2]); return null; //返回参数值
	}; 
	//初始化数据函数
	function getData(queryParams){
		$('#tt').tree({
			url: '<%=basePath%>rolepermission/systemgetpertrees',
			queryParams: queryParams,
			checkbox: true,
			onlyLeafCheck:true,
			animate:true,  
		    lines:true
		});
	};
	
	function updateRolePerms(permiss,roleid){//更新权限
    	$.ajax({
    		type:'post',
    		url:"<%=basePath%>rolepermission/systemupdatepermiss",
    		data:{'permiss':permiss,'roleid':roleid},
    		success:function(data){
    			if(1==data){//成功
    				$.messager.alert('提示','更新成功','info');
    			}else{
    				$.messager.alert('提示','更新失败','error');
    			}
    			$('#tt').tree('reload');
    		},error:function(){
    			console.log("fail");
    		}
    	});
    }
	
	//--------------------------------------主体部分！！！-----------------------------
    var doedit = undefined;//用来记录当前编辑的行，如果没有编辑的行则置为undefined
    var roleid = "";
    $(function(){
    	roleid = getUrlParam("roleid");	
		//获取数据的查询参数----过滤数据
		var queryParams;
		queryParams = {"roleid":roleid};
		getData(queryParams);
	});
	$(document).ready(function(){
		$('#SelectBtn').click(function(){
			$.messager.confirm("操作警告", "确定保存后被修改的数据将不可恢复！！", function(data){
				if(data){
					//alert("ok");
					var rows = $('#tt').tree('getChecked');
					var prmsid = [];
					if(rows.length!=0){
						for(var i=0;i<rows.length;i++){
							prmsid.push(rows[i].id);
						}	
					}
					console.log(prmsid);
					updateRolePerms(prmsid,roleid);
				}
			});
    	}); 
    });
    //------------------------------------------------------------------------------
</script>
</head>
<body  class = "h2">
	<a href="javascript:void(0)" id="SelectBtn" class="easyui-linkbutton" iconCls="icon-ok" style="width:100px;height:30px;margin-top: 10px;margin-left: 10px;margin-bottom: 10px;">保存</a>
	<ul id="tt"></ul>
</body>
</html>