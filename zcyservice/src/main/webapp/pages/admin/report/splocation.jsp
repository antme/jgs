<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的订单</title>
<script type="text/javascript" src="/resources/js/highcharts.js"></script>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>

<style type="text/css">
    .highcharts-legend{
        display : none;
    }
    
    tspan {
    	font-size :16px;
    }
    
	.highcharts-container{
	    margin-top : 30px;
	}
</style>
</head>
<body>
	 <div class="p_height_div"></div>
     <span class="span_style"><label>查询条件：</label></span>
     <span class="span_style"><input id="c_s_province" class="easyui-combobox " data-options=" valueField:'id',textField:'text',width:128,multiple:false" style="height:35px"/></span>
     <span class="span_style"><input id="c_s_city" class="easyui-combobox " data-options=" valueField:'id',textField:'text',width:128,multiple:false" style="height:35px"/></span>
     <span class="span_style"><input id="c_s_county" class="easyui-combobox " data-options=" valueField:'id',textField:'text',width:128,multiple:false" style="height:35px"/></span>
     <span class="span_style"><input id="categoryId" class="easyui-combobox" data-options=" valueField:'id',textField:'text',width:128,multiple:false" style="height:35px"/></span>
     <span class="span_style"><button class="public_btn" onclick="querySpLocationData();">&nbsp</button></span>
	 <div class="p_height_div"></div>

    <div id="chart" style="width:300px; margin-left:30%;"></div>
    
    
    <script type="text/javascript">
    $(function () {
        
    	querySpLocationData(1); 
    	  
    });
    function querySpLocationData(type){
    	
    	var c_s_province =$('#c_s_province').combobox('getValue');
    	var c_s_city =$('#c_s_city').combobox('getValue');
    	var c_s_county =$('#c_s_county').combobox('getValue');
    	var c_s_category =$('#categoryId').combobox('getValue');
    	
    	var locationId = undefined;
    	if(c_s_county && c_s_county!="0"){
    		locationId = c_s_county;
    	}else if(c_s_city && c_s_city!="0"){
    		locationId = c_s_city;
    	}else if(c_s_province  && c_s_province!="0"){
    		locationId = c_s_province;
    	}
    	//console.log(locationId);
    
    	var data = {
    			
    			"categoryId" : $('#categoryId').combobox('getValue'),
    			"locationId" : locationId
    	}
    	
    	postAjaxRequest("/ecs/sys/report/location_cate/spcount.do", data, function(response) {
            
	        var colors = Highcharts.getOptions().colors,
	            categories = [ '服务商'],
	            name = '用户数据统计',
	            data = [{
	                    y: response.COUNT,
	                    color: colors[0]
	                }];
	    

	    
	        var chart = $('#chart').highcharts({
	            chart: {
	                type: 'column'
	            },
	            title: {
	                text: '用户数据统计 '
	            },
	            subtitle: {
	                text: ''
	            },
	            xAxis: {
	                categories: categories
	            },
	            yAxis: {
	                title: {
	                    text: '单位（个）'
	                }
	            },
	            plotOptions: {
	                column: {
	                    cursor: 'pointer',
	                    point: {
	                        events: {
	                            click: function() {
	                               
	                            }
	                        }
	                    },
	                    dataLabels: {
	                        enabled: true,
	                        color: colors[0],
	                        style: {
	                            fontWeight: 'bold'
	                        },
	                        formatter: function() {
	                            return this.y +'个';
	                        }
	                    }
	                }
	            },
	            tooltip: {
	                formatter: function() {
	                    var point = this.point,
	                        s = this.x +':<b>'+ this.y +'个 用户</b>';
	                    return s;
	                }
	            },
	            series: [{
	                name: name,
	                data: data,
	                color: 'white'
	            }],
	            exporting: {
	                enabled: false
	            }
	        })
	        .highcharts(); // return chart
	    });
   }
   

    </script>
</body>
</html>