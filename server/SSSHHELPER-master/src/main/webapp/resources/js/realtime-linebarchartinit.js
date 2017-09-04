//定义
hashMap = {   
    Set : function(key,value){this[key] = value;},   
    Get : function(key){return this[key];},   
    Contains : function(key){return this.Get(key) == null?false:true;},   
    Remove : function(key){delete this[key];}   
};


//初始化图表
function ChartInit(chartid,title,bardata,linedata,labs){
	var myChart = echarts.init(document.getElementById(chartid));
	var colors = ['#bbb5b5', '#d14a61', '#675bba'];
	option = {
	    color: colors,
			title: {
		        text: title
		    },
		    tooltip: {
		        trigger: 'axis',
		        formatter: function (params) {
		            params = params[0];
		            params1 = params[1];
		            var index = params.dataIndex;
		            return "时间："+params.name + "<br/>"+"文档数量："+params.value+"<br/>"+"执行时间："+params.value+"ms<br/>"+"集合名称："+coll[index];
		        },
		    },
		    toolbox: {
		        feature: {
		            dataView: {show: true, readOnly: false},
		            magicType: {show: true, type: ['line', 'bar']},
		            restore: {show: true},
		            saveAsImage: {show: true}
		        }
		    },
		    legend: {
		        data:['文档数量','执行时间']
		    },
		    xAxis: [
		        {
		        	splitLine:{  
                        show:true,
                    },
		            type: 'category',
		            data: labs
		        }
		    ],
		    yAxis: [
		        {
		        	splitLine:{  
                        show:false,
                    },
		            type: 'value',
		            name: '文档数量',
		            min: 0,
		            interval: 40000,
		            axisLabel: {
		                formatter: '{value} 个'
		            }
		        },
		        {
		        	splitLine:{  
                        show:false,
                    },
		            type: 'value',
		            name: '执行时间',
		            min: 0,
		            interval: 200,
		            axisLabel: {
		                formatter: '{value} ms'
		            }
		        }
		    ],
		    series: [
		        {
		            name:'文档数量',
		            type:'bar',
		            data:bardata
		        },
		        {
		            name:'执行时间',
		            type:'line',
		            yAxisIndex: 1,
		            data:linedata
		        }
		    ]
		};
	myChart.setOption(option);
	hashMap.Set(chartid,myChart);
}
//获取图表横轴数据
function updateChartInterval(chartid,axis1,axis2){
	var myChart = hashMap.Get(chartid);
	var options = myChart.getOption();  
	if(axis1<100){
		axis1 =100;
	}
	if(axis2<200){
		axis2 = 200;
	}
	options.yAxis[0].interval = axis1;
	options.yAxis[1].interval = axis2;
	myChart.setOption(options);
}
//获取图表横轴数据
function getChartLabs(chartid){
	var myChart = hashMap.Get(chartid);
	var options = myChart.getOption();  
	var labs = options.xAxis[0].data;
	return labs;
}
//获取图表柱状数据
function getChartBarData(chartid){
	var myChart = hashMap.Get(chartid);
	var options = myChart.getOption();  
	var bardata = options.series[0].data;
	return bardata;
}
//获取图表折线数据
function getChartlineData(chartid){
	var myChart = hashMap.Get(chartid);
	var options = myChart.getOption();  
	var linedata = options.series[1].data;
	return linedata;
}
//追加图表数据
function updateChartData(chartid,bardata,linedata,labs){
	var myChart = hashMap.Get(chartid);
	var options = myChart.getOption();  
	options.series[0].data = bardata;
	options.series[1].data = linedata;
	options.series[0].desc = insertColl;
	options.xAxis[0].data = labs;
	myChart.setOption(options);
}
//更新图表数据
function appenddateChartData(chartid,bardata,linedata,labs){
	var myChart = hashMap.Get(chartid);
	var options = myChart.getOption();
	var olabs = getChartLabs(chartid);
	var obardata = getChartBarData(chartid);
	var olinedata = getChartlineData(chartid);
	for(var i = 0;i<labs.length;i++){
		olabs.push(labs[i]);
	}
	for(var i = 0;i<bardata.length;i++){
		obardata.push(bardata[i]);
	}
	for(var i = 0;i<linedata.length;i++){
		olinedata.push(linedata[i]);
	}
	options.series[0].data = obardata;
	options.series[1].data = olinedata;
	options.xAxis[0].data = olabs;
	myChart.setOption(options);
}