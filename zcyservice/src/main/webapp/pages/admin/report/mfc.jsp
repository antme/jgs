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
      <span class="span_style"><label style="margin-left:20px;">注册日期：</label></span>
      <span class="span_style"><input class="easyui-datebox" required missingMessage="请选择起始时间"  style="width:150px" name="hendDate" id="hstartDate" data-options="formatter:dateFormatter"/></span>    
      <span class="span_style">&nbsp;至&nbsp;&nbsp;&nbsp;</span>
      <span class="span_style"><input class="easyui-datebox" required missingMessage="请选择结束时间" style="width:150px" name="hendDate" id="hendDate" data-options="formatter:dateFormatter"/></span>
      <span class="span_style"><button class="public_btn" onclick="queryMfcData();">&nbsp</button></span>
	  <div class="p_height_div"></div>
      <div class="panl-order-mouth" >
             <ul >
                <li id="first_li" class="two click_back" onclick="queryMfcData(0);">本月</li>
                <li class="first" onclick="queryMfcData(1);">上个月</li>             
                <li class="first" onclick="queryMfcData(2);">前三个月</li>                
             </ul>
      </div>
    <div id="chart"></div>
    
    
    <script type="text/javascript">
    $(".panl-order-mouth").find("ul").find("li").click(function() {
        $(".panl-order-mouth").find("li").removeClass("click_back");
        $(this).addClass("click_back");
    });
    
    function queryMfcData(type){
        var data = {
                "startDate" : $('#hstartDate').datebox('getValue'),
                "endDate" : $('#hendDate').datebox('getValue'),
                "dateType" : type
            }
    	 postAjaxRequest("/ecs/sys/report/location/mfc.do", data, function(response) {
             
    		 $('#chart').highcharts({
    		        chart: {
    		            plotBackgroundColor: null,
    		            plotBorderWidth: null,
    		            plotShadow: false
    		        },
    		        title: {
    		            text: '厂商分布比例，总数：' + response.total
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
    
    $(function () {
       
    	$("#first_li").click();
  
    });
    </script>
</body>
</html>