<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
</head>


<body>
    <div id="sidebar">
        <ul class="margin_top">
           <li><a class="title_cs display_inline" href="index.jsp">订单提醒</a>       
           </li>
           <li><a class="title_cs" href="?p=web/sp/order/neworders">新订单<span style="color:red; margin-left:5px;" id="new_order_tip"></span></a></li>
           <li>
              <a class="title_cs">我的订单</a>   
              <ul class="display_none">
                  <li><a href="?p=web/sp/order/acceptorders">已接受新订单</a></li>
                  <li><a href="?p=web/sp/order/assignorders">已派工订单</a></li>
                  <li><a href="?p=web/sp/order/historyorders">所有订单</a></li>
              </ul>                
           </li>
           <li>
              <a class="title_cs">服务区域设置</a>   
              <ul class="display_none">
                  <li><a href="?p=web/sp/sp_regional">服务区域设置</a></li>
                  <li><a href="?p=web/sp/myregional">我的服务区域价格</a></li>
                  <li><a href="?p=web/sp/spstore">服务网点</a></li>
              </ul>                
           </li>
           <li><a class="title_cs" href="?p=web/sp/worker/myworkers">我的工人</a></li>
           <li><a class="title_cs" href="?p=web/sp/mybill">我的账单</a></li>
           <li><a class="title_cs" href="?p=web/sp/Evaluation">我的评价等级</a></li>
           <li><a class="title_cs" href="?p=web/sp/spinfo">我的信息</a></li>
           <li><a class="title_cs" href="?p=message/receiver/list">站内信息</a></li>
        </ul>
    </div>
    
    <script type="text/javascript">
    function getTipInfos(){
        postAjaxRequest("/ecs/order/sp/so/new/count.do", {}, function(data){

            if(data.count > 0){
                $("#new_order_tip").html("(" + data.count +")");
            }else{
                 $("#new_order_tip").html("");
            }
            
            if(data.count > 0){
            	var tipmsg = {};
            	tipmsg.msg = "<div><a href='?p=web/sp/order/neworders'>你有" + data.count +"个新订单需要接受</a></div>";
            	tipmsg.type = "NEW_ORDER_SP_COUNT";
            	updateNoticDiv(tipmsg);
            }
            
        }, false);

	   postAjaxRequest("/ecs/sitemessage/messcount.do", {}, function(data) {
             $(".info_has_info_num").text(data.count);
	    }, false);
	 
    }
    $(document).ready(function(){
        getTipInfos();
        var siid = window.setInterval(function() {
            getTipInfos();          
        }, 30000);
    });
    </script>
    <script src="resources/js/jquery.min.js"></script>
    <script src="resources/js/unicorn.js"></script>
</body>
</html>