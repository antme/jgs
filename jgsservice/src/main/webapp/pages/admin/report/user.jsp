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
      <div class="page_tip">说明： 不输入时间查询所有的，默认显示一个月内注册的用户数</div>

	  <div class="p_height_div"></div>
      <span><label style="margin-left:20px;font-weight:bold">注册日期：</label></span>
      <span class="span_style"><input class="easyui-datebox" required missingMessage="请选择起始时间"  style="width:150px" name="hendDate" id="hstartDate" data-options="formatter:dateFormatter"/></span>    
      <span class="span_style">&nbsp;至&nbsp;&nbsp;&nbsp;</span>
      <span class="span_style"><input class="easyui-datebox" required missingMessage="请选择结束时间" style="width:150px" name="hendDate" id="hendDate" data-options="formatter:dateFormatter"/></span>
      <span class="span_style"><button class="public_btn" onclick="queryUserStat();">&nbsp</button></span>
	  <div class="p_height_div"></div>
      <div class="panl-order-mouth" >
             <ul >
                <li id="first_li" class="two click_back" onclick="queryUserStat(0);">本月</li>
                <li class="first" onclick="queryUserStat(1);">上个月</li>             
                <li class="first" onclick="queryUserStat(2);">前三个月</li>                
             </ul>
      </div>
    <div id="chart"></div>
    
    
    <script type="text/javascript">
    $(".panl-order-mouth").find("ul").find("li").click(function() {
        $(".panl-order-mouth").find("li").removeClass("click_back");
        $(this).addClass("click_back");
    });
    
    function queryUserStat(type){
        var data = {
                "startDate" : $('#hstartDate').datebox('getValue'),
                "endDate" : $('#hendDate').datebox('getValue'),
                "dateType" : type
            }
    	 postAjaxRequest("/ecs/sys/report/userlist.do", data, function(response) {
             
    	        var colors = Highcharts.getOptions().colors,
    	            categories = ['厂商', '服务商', '用户', '客服', '工人'],
    	            name = '用户数据统计',
    	            data = [{
    	                    y: response.MFC,
    	                    color: colors[0]
    	                }, {
    	                    y: response.SP,
    	                    color: colors[1]
    	                }, {
    	                    y: response.USER,
    	                    color: colors[2]
    	                }, {
    	                    y: response.CUSTOMER_SERVICE,
    	                    color: colors[3]
    	                }, {
    	                    y: response.WORKER,
    	                    color: colors[4]
    	                }];
    	    

    	    
    	        var chart = $('#chart').highcharts({
    	            chart: {
    	                type: 'column'
    	            },
    	            title: {
    	                text: '用户数据统计 '
    	            },
    	            subtitle: {
    	                text: '鼠标移到图标上可以看详细信息'
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
    
    $(function () {
       
    	$("#first_li").click();
  
    });
    </script>
</body>
</html>