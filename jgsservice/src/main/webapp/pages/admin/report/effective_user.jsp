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
      <span class="span_style"><label style="margin-left:20px;font-weight:bold">日期：</label></span>
      <span class="span_style">
      <input class="easyui-datebox" required missingMessage="请选择起始时间"  style="width:150px" name="hendDate" id="hstartDate" data-options="formatter:dateFormatter"/></span>    
      <span class="span_style">&nbsp;至&nbsp;&nbsp;&nbsp;</span>
      <span class="span_style">
      <input class="easyui-datebox" required missingMessage="请选择结束时间" style="width:150px" name="hendDate" id="hendDate" data-options="formatter:dateFormatter"/></span>
      <select class="easyui-combobox" name="effectiveType" data-options="multiple:false" style="width:128px;" id="effectiveType"> 
        <option value="week">周</option>
        <option value="month">月</option>
      </select> 
  
      <span class="span_style"><button class="public_btn" onclick="queryOrderStat();">&nbsp</button></span>
      <div class="p_height_div"></div>
      
      

    <div id="chart"></div>
    
    
    <script type="text/javascript">
    $(function () {
        
        queryOrderStat();

          
    });
    function queryOrderStat(){
        
           var data = {
                    "startDate" : $('#hstartDate').datebox('getValue'),
                    "endDate" : $('#hendDate').datebox('getValue'),
                    "effectiveType" : $("#effectiveType").combobox('getValue')
                }
        
         postAjaxRequest("/ecs/sys/report/effective/user.do", data, function(response) {
             
             $('#chart').highcharts({
                    title: {
                        text: response.title,
                        x: -20 //center
                    },
                    
                    xAxis: {
                        categories:response.categories
                    },
                    yAxis: {
                        title: {
                            text: '小时'
                        },
                        plotLines: [{
                            value: 0,
                            width: 1,
                            color: '#808080'
                        }]
                    },
                    tooltip: {
                        valueSuffix: '小时'
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'right',
                        verticalAlign: 'middle',
                        borderWidth: 0
                    },
                    series: [{
                        name: '用户平均评价时间',
                        data: response.times
                    }]
                });
             
            }, false);
    }
    

    </script>
</body>
</html>