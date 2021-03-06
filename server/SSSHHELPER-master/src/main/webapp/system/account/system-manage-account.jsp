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
<title>用户管理</title>

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
	//初始化数据函数
	function getData(queryParams){
		$('#grid').datagrid({
			url: '<%=basePath%>account/getpageaccount',
			queryParams: queryParams,
			remoteSort:false,
			nowrap: false, //换行属性
			striped: true, //奇数偶数行颜色区分
			height:600,
			singleSelect:true,
			fitColumns:true,
			collapsible : true, //可折叠
			pageSize: 50,//每页显示的记录条数，默认为10  
	        pageList: [5,10,15,20,25,50,100],//可以设置每页记录条数的列表  
	        pagination: true,//是否这是分页
			rownumbers:true,
			frozenColumns:[[
				{field: 'ck', checkbox: true},          
			]],
			columns: [[
				{field:'userId',title:'ID',sortable:true,width:60,sortable:true,hidden:true},
				{field:'userLoginName',title:'登录名',sortable:true,width:200,sortable:true,
				},
				{field:'userPassword',title:'密码',sortable:true,width:200,sortable:true,
					editor: { type: 'validatebox' }
				},
				{field:'userName',title:'名称',sortable:true,width:150,sortable:true,
				},
				{field:'userSex',title:'性别',sortable:true,width:150,sortable:true,
					formatter:function(value,row,index){
						if(0==value){
							return "女";
						}else if(1==value){
							return "男";
						}
					},
				},
				{field:'userRegisterDate',title:'注册日期',sortable:true,width:150,sortable:true,
					formatter:function(value,row,index){
						var time = myformatter(value);
						//row.startTime = time;
						return time;
					},
				},
				{field:'userSpeak',title:'是否禁言',sortable:true,width:150,sortable:true,
					formatter:function(value,row,index){
						if(0==value){
							return "是";
						}else if(1==value){
							return "否";
						}
					},
				},
				{field:'userAlive',title:'是否禁用',sortable:true,width:150,sortable:true,
					formatter:function(value,row,index){
						if(0==value){
							return "是";
						}else if(1==value){
							return "否";
						}
					},
				},
				{field:'userIntroduce',title:'个人介绍',sortable:true,width:150,sortable:true,
				},
			]],
			toolbar:[
				{//修改数据
					   text:"修改禁言状态",
					   iconCls: "icon-edit",
					   handler: alertSpeak,
				},'-',
				{//修改数据
					   text:"修改禁用状态",
					   iconCls: "icon-edit",
					   handler: alertAlive,
				},'-',
				{//修改数据
					   text:"保存",
					   iconCls: "icon-save",
					   handler: saveData,
				},'-',
				{
					   text:"查询",
					   iconCls: "icon-search",
					   handler: searchData,
				},'-',
			],
			onAfterEdit: function(rowIndex,rowData,changes){
				doedit = undefined;
			},
			onDblClickRow:function(rowIndex, rowData){    
				//$('#grid').datagrid('endEdit',doedit);
				if(doedit==undefined)   //如果存在在编辑的行，就不可以再打开第二个行进行编辑
				{					
					$('#grid').datagrid('selectRow',rowIndex);
		        	$('#grid').datagrid('beginEdit',rowIndex);
		        	doedit=rowIndex;
				}
			},
			onLoadSuccess:function(data){//数据刷新的时候，编辑的坐标设为空
				doedit = undefined;
			},
			
		});
		//分页设置
		var p = $('#grid').datagrid('getPager');
		$(p).pagination({
	        beforePageText: '第',//页数文本框前显示的汉字  
	        afterPageText: '页    共 {pages} 页',  
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',  
	        BeforeRefresh:function(){
				$(this).datagrid('reload'); 
				//获取数据库全部数据
			},
		});
		$('#searchdialog').dialog('close');
	};
	
	function myformatter(value) {//时间转换函数
		if(value != null && value != ""){
			var date = new Date(value*1000);
			var hour = date.getHours();
			var minute = date.getMinutes();
			var second = date.getSeconds();
			if(hour<10){
				hour = "0"+hour;
			}
			if(minute<10){
				minute = "0"+minute;
			}
			if(second<10){
				second = "0"+second;
			}
			return date.getFullYear()+"-"+(date.getMonth() + 1) +"-"+ date.getDate()+" "+ hour + ":" + minute +":"+ second;
		}
	}
	function formatterDate(date) {//时间转换
		var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
		var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"
		+ (date.getMonth() + 1);
		return month+ '/' + day + '/' + date.getFullYear() ;
	};
	function GetDateStr(AddDayCount) {//根据时间间隔计算 相距今天的日期
	    var dd = new Date();
	    dd.setDate(dd.getDate()+AddDayCount);//获取AddDayCount天后的日期
	    var y = dd.getFullYear();
	    var m = dd.getMonth()+1;//获取当前月份的日期
	    var d = dd.getDate();
	    return y+"-"+m+"-"+d;
	}
	function myparser(s) {
		var ss = (s.split('/'));
		if(ss.length > 1)
			return ss[2] + "-" + ss[0] + "-" + ss[1];
		else return s;
	}
 
    //----------------------------导入------------------------------------------
    function searchData(){
    	$('#searchdialog').dialog('open');
    }
  //-------------------------------修改禁言状态-------------------------------------------
    function alertSpeak(){
    	var rows = $('#grid').datagrid('getSelections');
		if(rows.length <= 0){
			$.messager.alert('警告','您没有选择','error');
		}
		else if(rows.length >= 1){
			$.messager.confirm("操作警告", "是否确定修改?", function(data){
				if(data){
					//原来代码开始的位置
					var ids = [];
					for(var i = 0; i < rows.length; ++i){
							ids[i] = rows[i].userId;
					}	
					$.ajax({
			    		type:'post',
			    		url:"<%=basePath%>account/updatespeak",
			    		data:{userid: ids[0]},
			    		success:function(data){
			    			if(1==data){//成功
			    				$.messager.alert('提示','修改成功','info');
			    			}else{
			    				$.messager.alert('提示','修改失败','error');
			    			}
			    			$('#grid').datagrid('reload');
			    		},error:function(){
			    			console.log("fail");
			    		}
			    	});	
					
				}
			});
		}
    }
  //-------------------------------修改禁用状态-------------------------------------------
    function alertAlive(){
    	var rows = $('#grid').datagrid('getSelections');
		if(rows.length <= 0){
			$.messager.alert('警告','您没有选择','error');
		}
		else if(rows.length >= 1){
			$.messager.confirm("操作警告", "是否确定修改?", function(data){
				if(data){
					//原来代码开始的位置
					var ids = [];
					for(var i = 0; i < rows.length; ++i){
							ids[i] = rows[i].userId;
					}	
					$.ajax({
			    		type:'post',
			    		url:"<%=basePath%>account/updatelive",
			    		data:{userid: ids[0]},
			    		success:function(data){
			    			if(1==data){//成功
			    				$.messager.alert('提示','修改成功','info');
			    			}else{
			    				$.messager.alert('提示','修改失败','error');
			    			}
			    			$('#grid').datagrid('reload');
			    		},error:function(){
			    			console.log("fail");
			    		}
			    	});	
					
				}
			});
		}
    }
  //-------------------------------保存-------------------------------------------
	function saveData(){//保存
		$.messager.confirm("操作警告", "确定保存后被修改的数据将不可恢复！！", function(data){
			if(data){
				$('#grid').datagrid('endEdit', doedit);
				var inserted = $('#grid').datagrid('getChanges', 'inserted');
				var updated = $('#grid').datagrid('getChanges', 'updated');
				var insertrow = JSON.stringify(inserted);
				var updatedrow = JSON.stringify(updated);
				if (updated.length > 0) {  
					$.ajax({
			    		type:'post',
			    		url:"<%=basePath%>account/updatePwd",
			    		data:{"rowstr":updatedrow},
			    		success:function(data){
			    			if(1==data){//成功
			    				$.messager.alert('提示','更新成功','info');
			    			}else{
			    				$.messager.alert('提示','更新失败','error');
			    			}
			    			$('#grid').datagrid('reload');
			    		},error:function(){
			    			console.log("fail");
			    		}
			    	});			       
			    }
			}
		});
    }
	//--------------------------------------主体部分！！！-----------------------------
    var doedit = undefined;//用来记录当前编辑的行，如果没有编辑的行则置为undefined
    $(function(){
		//获取数据的查询参数----过滤数据
		var queryParams;
		queryParams = {};
		getData(queryParams);
	});
	$(document).ready(function(){
    	$('#SelectBtn').click(function(){
    		var loginname = $('#searloginname').val();
    		var queryParams;
    		queryParams = {'loginname':loginname};
    		getData(queryParams);
    		$('#searchdialog').dialog('close');
    	});
    });
    //------------------------------------------------------------------------------
</script>
</head>
<body  class = "h2">
	<table id="grid"></table>
	<div id="searchdialog" class="easyui-dialog" title="搜索" style="width:400px;height:200px;"
    data-options="iconCls:'icon-save',resizable:true,modal:true">
    	<div id="totalplane" style="margin-top: 55px;padding-left: 60px;">
    			<font style='width:30px;margin-left: 15px'>登陆名称</font>
    			<input id="searloginname" style='width:150px;margin-left: 5px'>
	  			<a href="javascript:void(0)" id="SelectBtn" class="easyui-linkbutton" iconCls="icon-ok" style="width:150px;height:32px;margin-top: 10px;margin-left: 65px">确定</a>
  		</div>
	</div>
</body>
</html>