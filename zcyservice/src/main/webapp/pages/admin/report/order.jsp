<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的订单</title>
<script type="text/javascript" src="/resources/js/highcharts.js"></script>
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


   
    <div id="sp-chart"></div>
    
    
    <script type="text/javascript">

    
    function queryOrderStat(type){
    	 postAjaxRequest("/ecs/sys/report/order.do", {dateType : type}, function(response) {
             
    	        var colors = Highcharts.getOptions().colors,
    	            categories = ['未激活', '未安装', '待评价', '待结算', '系统无法处理', '人为返回', '投诉订单'],
    	            name = '订单状态',
    	            data = [{
    	                    y: response.INACTIVE,
    	                    color: colors[0]
    	                }, {
    	                    y: response.SP_COUNT,
    	                    color: colors[1]
    	                }, {
    	                    y: response.DONE,
    	                    color: colors[2]
    	                }, {
    	                    y: response.CLOSED,
    	                    color: colors[3]
    	                }, {
    	                    y: response.SYS_MANUAL,
    	                    color: colors[4]
    	                }, {
    	                    y: response.USER_MANUAL,
    	                    color: colors[5]
    	                }, {
                            y: response.COMPLAINT,
                            color: colors[6]
                        }];
    	    

    	    
    	        var chart = $('#sp-chart').highcharts({
    	            chart: {
    	                type: 'column'
    	            },
    	            title: {
    	                text: '订单状态汇总 '
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
    	                        s = this.x +':<b>'+ this.y +'个 订单</b>';
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
       
    	queryOrderStat(0);
  
    });
    </script>
</body>
</html>