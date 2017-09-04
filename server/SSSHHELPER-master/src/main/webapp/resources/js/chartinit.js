//定义
hashMap = {   
    Set : function(key,value){this[key] = value;},   
    Get : function(key){return this[key];},   
    Contains : function(key){return this.Get(key) == null?false:true;},   
    Remove : function(key){delete this[key];}   
};


//初始化图表
function ChartInit(chartid,title,data,labs){
	var myChart = echarts.init(document.getElementById(chartid));
	option = {
	    title: {
	        text: title
	    },
	    tooltip: {
	        trigger: 'axis',
	        formatter: function (params) {
	            params = params[0];
	            return params.name + " - "+params.value;
	        },
	        axisPointer: {
	            animation: false
	        }
	    },
	    xAxis: {
	        splitLine: {
	            show: false
	        },
	        data:labs
	    },
	    yAxis: {
	        type: 'value',
	        boundaryGap: [0, '100%'],
	        splitLine: {
	            show: true
	        }
	    },
	    series: [{
	        name: '模拟数据',
	        type: 'line',
	        showSymbol: false,
	        hoverAnimation: false,
	        data: data
	    }]
	};
	myChart.setOption(option);
	hashMap.Set(chartid,myChart);
}
//获取图表横轴数据
function GetChartLabs(chartid){
	var myChart = hashMap.Get(chartid);
	var options = myChart.getOption();  
	var labs = options.xAxis[0].data;
	return labs;
}
//获取图表数据
function GetChartData(chartid){
	var myChart = hashMap.Get(chartid);
	var options = myChart.getOption();  
	var data = options.series[0].data;
	return data;
}
//更行图表数据
function UpdateChartData(chartid,data,labs){
	var myChart = hashMap.Get(chartid);
	var options = myChart.getOption();  
	options.series[0].data = data;
	options.xAxis[0].data = labs;
	myChart.setOption(options);
}