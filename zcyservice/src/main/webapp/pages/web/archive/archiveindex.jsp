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


	
   <div class="p_height_div"></div>

    <div id="sp-chart"></div>
    
    
    <script type="text/javascript">
    
    
    var charData = new Array();
    var categories = new Array();

    var chart;
    var colors = Highcharts.getOptions().colors;

    
    $(document).ready(function(){
        
	 postAjaxRequest("/ecs/archive/indexcount.do", {}, function(data){
		 var i =0;
		 for(key in data){
			 if(key!="code"){
			 
			 var itemData = {};
			 itemData.y = data[key];
			 itemData.color =colors[i];
			 i++;
			 charData.push(itemData);
			 
			 categories.push('<a style="text-decoration:none;*+line-height:19px;margin-top:10px;display:inline-block;" href="index.jsp?p=web/archive/archivelist&year='+key.replace("year", "") +'">' + key.replace("year", "")+'年</a>');
			 }
		 }
		 queryOrderStat();
		
	 });
	 
	
	 
    });

    
    function queryOrderStat(){
	    	
             var text = "档案数汇总";
           

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
                         
                            var point = this.point,
                                s = this.x +': <a target="_blank"  style="color:red;" href="' + url +'">'+ this.y +'</a> 份档案归档';
                            return s;
                        }
                    },
                    series: [{
                        name: name,
                        data: charData,
                        color: 'white'
                    }],
                    exporting: {
                        enabled: false
                    }
                })
                .highcharts(); // return chart
        
           
 
    }
    

    </script>
</body>
</html>