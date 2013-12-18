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
         <li><a class="title_cs"  href="?p=web/mfc/order/import">订单批量导入</a></li>
           <li>
              <a class="title_cs" id="mfc-order-link">我的订单</a>
              <ul class="display_none">
              	  <li><a href="?p=web/mfc/order/newlist" id="mcf-new-order-link">已导入新订单<span style="color:red; margin-left:5px;" id="new_order_tip"></span></a></li>
                  <li><a href="?p=web/mfc/order/mine">产品订单</a></li>
                  <li><a href="?p=web/mfc/order/serviceorder">服务订单</a></li>
              </ul>          
           </li>
           <li><a class="title_cs" href="?p=web/sp/info/search">查询服务商</a></li>
           <!-- <li><a class="title_cs" href="?p=web/sp/info/searchprice">查询服务价格</a></li> -->
           <li><a class="title_cs" href="?p=web/mfc/order/mybill">我的账单</a></li>
           <li><a class="title_cs" href="?p=web/mfc/mfcinfo">我的信息</a></li>
           <li><a class="title_cs" href="?p=message/receiver/list">站内信息</a></li>
        </ul>
    </div>
      <script type="text/javascript">
    function getTipInfos(){
    	 postAjaxRequest("/ecs/order/mfc/pro/new/count.do", {}, function(data){

             if(data.count > 0){
                 $("#new_order_tip").html("(" + data.count +")");
             }else{
                  $("#new_order_tip").html("");
             }
             
             if(data.count > 0){
             	var tipmsg = {};
             	tipmsg.msg = "<div><a href='?p=web/mfc/order/newlist'>你有" + data.count +"个新订单需要激活</a></div>";
             	tipmsg.type = "NEW_ORDER_MFC_COUNT";
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