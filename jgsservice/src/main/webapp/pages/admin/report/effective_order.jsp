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
   
    <div id="chart"></div>
    
    <script type="text/javascript">
    
    $(function () {
        queryOrderStat(1);

        
    });
    
    function queryOrderStat(type){
    	 postAjaxRequest("/ecs/sys/report/effective/order.do", {dateType : type}, function(response) {
             
    		 $('#chart').highcharts({
    		        chart: {
    		            plotBackgroundColor: null,
    		            plotBorderWidth: null,
    		            plotShadow: false
    		        },
    		        title: {
    		            text: '订单总数：' + response.total
    		        },
    		        tooltip: {
    		    	    pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
    		        },
    		        plotOptions: {
    		            pie: {
    		                allowPointSelect: true,
    		                cursor: 'pointer',
    		                dataLabels: {
    		                    enabled: true,
    		                    color: '#000000',
    		                    connectorColor: '#000000',
    		                    format: '<b>{point.name}</b>: {point.percentage:.2f} %'
    		                }
    		            }
    		        },
    		        series: [{
    		            type: 'pie',
    		            name: '所占比例',
    		            data:response.data
    		        }]
    		    });
    	    }, false);
    }
    
 
    </script>
</body>
</html>