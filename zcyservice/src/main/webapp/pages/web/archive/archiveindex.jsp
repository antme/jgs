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

	<div style="margin-top:20px; margin-left:30px;padding-top: 30px;">
		<select onchange="loadReport();">
			<option value="years">按年度统计</option>
			<option value="applicant">按申请人统计</option>
			<option value="oppositeApplicant">按被申请人统计</option>
		</select>
	</div>
	
	
   <div class="p_height_div"></div>

    <div id="sp-chart"></div>
    
    
    <script type="text/javascript">
    var chart;

    
    function queryOrderStat(type){
       
    	var response ={
    			"year13":800,
    			"year12":500,
    			"year14":100
    	};
             var text = "档案数汇总";
           
                var colors = Highcharts.getOptions().colors,
                    categories = ['<a style="text-decoration:none;*+line-height:19px;margin-top:10px;display:inline-block;" href="index.jsp?p=web/archive/archivelist&poStatus=INACTIVE">2012</a>', 
                            '<a style="text-decoration:none;*+line-height:19px;margin-top:10px;display:inline-block;" href="index.jsp?p=web/archive/archivelist&poStatus=NEED_SP_CONFIRM">2013</a>', 
                            '<a style="text-decoration:none;*+line-height:19px;margin-top:10px;display:inline-block;" href="index.jsp?p=web/archive/archivelist&poStatus=ACCEPTED">2014</a>'],
                    name = '订单状态',
                    data = [{
                            y: response.year12,
                            color: colors[0]
                        }, {
                            y: response.year13,
                            color: colors[1]
                        }, {
                            y: response.year14,
                            color: colors[2]
                        }];
            

            
                var chart = $('#sp-chart').highcharts({
                    chart: {
                        type: 'column'
                    },
                    title: {
                        text: text
                    },
                    subtitle: {
                        text: ''
                    },
                    xAxis: {
                        categories: categories
                    },
                    yAxis: {
                        title: {
                            text: '单位（份）'
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
                                    return this.y +'份';
                                }
                            }
                        }
                    },
                    tooltip: {
                        formatter: function() {
                            var url = "index.jsp?p=web/archive/archivelist";

                            if(this.x == "未激活"){
                                url = url + "&poStatus=INACTIVE";
                            }else if(this.x == "待服务商确认"){
                                url = url + "&poStatus=NEED_SP_CONFIRM";
                            }else if(this.x == "已确认待分配"){
                                url = url + "&poStatus=ACCEPTED";
                            }else if(this.x == "已分配未安装"){
                                url = url + "&poStatus=ASSIGNED";
                            }else if(this.x == "人工处理"){
                                url = url + "&poStatus=MANUAL";
                            }else if(this.x == "已取消"){
                                url = url + "&poStatus=CANCELLED";
                            }
                            
                            var point = this.point,
                                s = this.x +': <a target="_blank"  style="color:red;" href="' + url +'">'+ this.y +'</a> 份档案归档';
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
        
           
 
    }
    
    $(function () {
    	queryOrderStat();
       
  
    });
    </script>
</body>
</html>