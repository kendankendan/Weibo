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
<title>用户角色绑定管理</title>

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
			url: '<%=basePath%>userrole/systemgetpageuserrole',
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
				{field:'id',title:'ID',sortable:true,width:60,sortable:true,hidden:true},
				{field:'account',title:'用户名',sortable:true,width:200,sortable:true,
				},
				{field:'rolename',title:'角色名称',sortable:true,width:200,sortable:true,
				},
			]],
			toolbar:[
				{//添加数据
					   text:"添加",
					   iconCls: "icon-add",
					   handler: addData,
				},'-',
				{//删除数据
					   text:"删除",
					   iconCls: "icon-remove",
					   handler: removeData,
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
				var data = $('#grid').datagrid('getRows');
				var startIndex = [];
				var rowsCount = [];
				var mergeCount = 1;
				for(var i=0;i<data.length;i++){
					if(0!=i){
						if(data[i].account==data[i-1].account){
							if(1==mergeCount&&i!=1){
								startIndex.push(i-1);
							}
							mergeCount = mergeCount + 1 ; 
						}else{
							rowsCount.push(mergeCount);
							mergeCount= 1;
						}
					}else{
						startIndex.push(0);
					}
				}
				if(startIndex.length-1==rowsCount.length){
					rowsCount.push(mergeCount);
				}
				for(var i=0;i<startIndex.length;i++){
					$('#grid').datagrid('mergeCells',{
						index:startIndex[i],
						field:'account',
						rowspan:rowsCount[i]
					});
				}
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
		$('#adddialog').dialog('close');
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
    function getDistinctMetaCode(){//获取所有的jobname
    	$.ajax({
    		type:'post',
    		url:"<%=basePath%>",
    		success:function(data){
    			var list = eval("("+data+")");
    			var jobname = list.jobname;
    			if(jobname.length>0){
    				var str1 = "";
    				for(var i =0;i<jobname.length;i++){
    					str1+="<option value='"+jobname[i]+"'>"+jobname[i]+"</option>";
    				}
    				$('#metacode').html(str1);
    			}
    			$('#metacode').show();
    			$("#metacode").multiselect({
    				noneSelectedText: "==请选择==",
    		        checkAllText: "全选",
    		        uncheckAllText: '全不选',
    		        selectedText:'#项被选中',
    			}).multiselectfilter(); 
    		},error:function(){
    			console.log("fail");
    		}
    	});
    }
 
   
    //---------------------------------添加----------------------------------------
    function addData(){
    	$('#adddialog').dialog('open');
    }
    //----------------------------导入------------------------------------------
    function importData(){
    	$('#searchdialog').dialog('open');
    }
    //-------------------------------删除-------------------------------------------
    function removeData(){
    	var rows = $('#grid').datagrid('getSelections');
		if(rows.length <= 0){
			$.messager.alert('警告','您没有选择','error');
		}
		else if(rows.length >= 1){
			$.messager.confirm("操作警告", "确定删除后将不可恢复！！", function(data){
				if(data){
					//原来代码开始的位置
					var ids = [];
					for(var i = 0; i < rows.length; ++i){
							ids[i] = rows[i].id;
					}	
					$.ajax({
			    		type:'post',
			    		url:"<%=basePath%>userrole/systemdeleteuserrole",
			    		data:{ids: ids.toString()},
			    		success:function(data){
			    			if(1==data){//成功
			    				$.messager.alert('提示','删除成功','info');
			    			}else{
			    				$.messager.alert('提示','删除失败','error');
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
	//------------------------获取用户列表-------------------
	function getUserlist(){//获取所有的user
    	$.ajax({
    		type:'post',
    		async:false,
    		url:"<%=basePath%>user/systemgetalluser",
    		success:function(data){
    			var list = eval("("+data+")");
    			if(list.length>0){
    				var str1 = "";
    				for(var i =0;i<list.length;i++){
    					str1+="<option value='"+list[i].id+"'>"+list[i].account+"</option>";
    				}
    				$('#userlist').html(str1);
    			}
    			$("#userlist").multiselect({
    				noneSelectedText: "==请选择==",
    				multiple: false,
    		        checkAllText: "全选",
    		        uncheckAllText: '全不选',
    		        selectedText:'#项被选中',
    			}).multiselectfilter();
    		},error:function(){
    			console.log("fail");
    		}
    	})
    }
	//------------------------获取角色列表-------------------
	function getRolelist(){//获取所有的role
    	$.ajax({
    		type:'post',
    		async:false,
    		url:"<%=basePath%>role/systemgetallrole",
    		success:function(data){
    			//var list = eval("("+data+")");
    			var list = data;
    			if(list.length>0){
    				var str1 = "";
    				for(var i =0;i<list.length;i++){
    					str1+="<option value='"+list[i].id+"'>"+list[i].name+"</option>";
    				}
    				$('#rolelist').html(str1);
    			}
    			$("#rolelist").multiselect({
    				noneSelectedText: "==请选择==",
    				multiple: false,
    		        checkAllText: "全选",
    		        uncheckAllText: '全不选',
    		        selectedText:'#项被选中',
    			}).multiselectfilter();
    		},error:function(){
    			console.log("fail");
    		}
    	})
    }
	//------------------------添加用户角色绑定-------------------
	function insertUserRole(userid,roleid){
    	$.ajax({
    		type:'post',
    		async:false,
    		url:"<%=basePath%>userrole/systeminsertuserrole",
    		data:{'userid':userid,'roleid':roleid},
    		success:function(data){
    			if(1==data){//成功
    				$('#adddialog').dialog('close');
    				$.messager.alert('提示','添加成功','info');
    			}else{
    				$.messager.alert('提示','添加失败','error');
    			}
    			$('#grid').datagrid('reload');
    		},error:function(){
    			console.log("fail");
    		}
    	})
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
		getUserlist();
		getRolelist();
		$('#addClick').click(function(){//添加新绑定确认按钮
			var userid = $("#userlist").val();
    		if(null==userid){
    			$.messager.alert('提示','选择添加的用户','error');
    		}else{
    			var roleid = $("#rolelist").val();
        		if(null==roleid){
        			$.messager.alert('提示','选择添加的角色','error');
        		}else{
        			insertUserRole(userid[0],roleid[0]);
        		}
    		}
		});
    });
    //------------------------------------------------------------------------------
</script>
</head>
<body  class = "h2">
	<table id="grid"></table>
	<div id="adddialog" class="easyui-dialog" title="添加" style="width:400px;height:250px;" data-options="iconCls:'icon-save',resizable:true,modal:true">
    	<div id="totalplane" style="margin-top: 55px;padding-left: 60px;">
    		用户列表：<select  multiple="multiple" name="example-basic" size="5" id="userlist" style="width:200px;display: none;">
			</select>
			<br/>
			<br/>
			角色列表：<select  multiple="multiple" name="example-basic" size="5" id="rolelist" style="width:200px;display: none;">
			</select>
			<br/>
			<br/>
    		<a href="javascript:void(0)" id="addClick" class="easyui-linkbutton" iconCls="icon-ok" style="width:150px;height:32px;margin-top: 10px;margin-left: 65px">确定</a>
  		</div>
	</div>
</body>
</html>