<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
</head>


<body>
    <input type="hidden" id="userRoleName" value="ADMIN"/>
    <div id="sidebar">
        <ul class="margin_top">
	    <%
		String r_name = null;
	    if(session.getAttribute("roleName") != null){
	    	r_name = session.getAttribute("roleName").toString();
	    }
	    if(r_name !=null && r_name.equalsIgnoreCase("SUPPER_ADMIN")){
	    %>
	
		<li id="role_set"><a class="title_cs" href="?p=admin/user/role">权限设置</a></li>
		<!-- <li id="role_set"><a class="title_cs" href="?p=admin/cfg/system">第三方帐号设置</a></li> -->
		 
		<%
	   	 }
	    
		%>
          
        
     	   <li class="access" access="adm_user_approve">
     	   		<a class="title_cs" >用户审核<span style="color:red; margin-left:5px;" id="user_approve_tip"></span></a>
     	    	<ul class="display_none">
                  <li><a href="?p=admin/user/newuser">新审核<span style="color:red; margin-left:5px;" id="user_approve_page"></span></a></li>
                  <li><a href="?p=admin/user/approvehistory">历史审核</a></li>
                </ul> 
     	   </li>
     	   <li class="access"  access="adm_user_manage"><a class="title_cs" href="?p=admin/user/manage">用户管理</a></li>
  		   <li class="access"  access="adm_order_manage">
     	   		<a class="title_cs">订单处理<span style="color:red; margin-left:5px;" id="admin_order_tip"></span></a>
     	    	<ul class="display_none">
                  <li><a href="?p=admin/order/sysreject">产品订单处理<span style="color:red; margin-left:5px;" id="sys_order_tip"></span></a></li>
                  <li><a href="?p=admin/order/userreject">服务订单处理<span style="color:red; margin-left:5px;" id="user_order_tip"></span></a></li>
                </ul> 
     	   </li>

     	   <li class="access"  access="adm_order_notice_manage"><a class="title_cs" href="?p=admin/order/noticelist">催单提醒<span style="color:red; margin-left:5px;" id="order_notice_tip"></span></a></li>
     	   <li class="access"  access="adm_bill_manage">
                <a class="title_cs">账单管理</a>
                <ul class="display_none">
                  <li><a href="?p=admin/order/mfcbill">厂商</a></li>
                  <li><a href="?p=admin/order/spbill">服务商</a></li>
                </ul> 
           </li>
           <li class="access"  access="adm_complaint_manage"><a class="title_cs" href="?p=service/complaint/complaint">投诉管理</a></li>
          
           
           <li class="access"  access="adm_setprice_manage"><a class="title_cs" href="?p=admin/price/setprice">定价管理</a></li>
           <li class="access"  access="adm_location_manage"><a class="title_cs" href="?p=admin/regional/Regional">区域管理</a></li>
           <li class="access"  access="adm_category_manage"><a class="title_cs" href="?p=admin/regional/adm_category">品类管理</a></li>

           
           <li class="access"  access="adm_sms_manage"><a class="title_cs" href="?p=admin/sms/template">短信模板管理</a></li>
           <li class="access"  access="adm_rule_manage"><a class="title_cs" href="?p=admin/cfg/cfg">系统设置</a></li>
           <li class="access"  access="adm_complaint_type_manage"><a class="title_cs" href="?p=service/complaint/complaintType">投诉类型管理</a></li>
           
           <li class="access"  access="adm_site_msg_manage"><a class="title_cs">站内信息</a>
     	    	<ul class="display_none">
           			<li><a href="?p=message/suadmin/add">站内信息发布</a></li>
           			<li><a href="?p=message/receiver/adminlist">站内信息</a></li>
                </ul>  
     	   </li>
     	   <li class="access"  access="adm_news_manage"><a class="title_cs">新闻</a>
     	    	<ul class="display_none">
           			<li><a href="?p=admin/news/addNews">添加新闻</a></li>
           			<li><a href="?p=admin/news/newsList">新闻列表</a></li>
                </ul>  
     	   </li>
           
           <li class="access"  access="adm_adv_manage"><a class="title_cs" href="?p=admin/ad/adList">广告位发布</a></li>  
           <li class="access"  access="adm_order_query"><a class="title_cs" href="?p=admin/order/list">订单查询</a></li>
           
           <li class="access"  access="adm_price_query"><a class="title_cs" href="?p=admin/price/admsearchprice">定价查询</a></li>
            
           <li class="access"  access="adm_judge_manage"><a class="title_cs" href="?p=admin/evaluation/evaluation">评价查询</a></li>
           <li class="access"  access="adm_order_query"><a class="title_cs" href="?p=admin/order/history">订单处理历史查询</a></li>
           <li class="access"  access="adm_sp_regional_query"><a class="title_cs" href="?p=admin/user/spregional">服务商服务区域价格查询</a></li>  
           <li class="access"  access="adm_report_manage"><a class="title_cs">数据统计</a>
              <ul class="display_none">
              	<li access="adm_report_manage"><a href="?p=admin/report/user">用户数量统计</a></li> 
              	<li access="adm_report_manage"><a  href="?p=admin/report/sp">服务商分布</a></li>
              	<li access="adm_report_manage"><a  href="?p=admin/report/workerreport">工人统计</a></li>
              	<li access="adm_report_manage"><a  href="?p=admin/report/mfc">厂商分布</a></li>
              	<li access="adm_report_manage"><a  href="?p=admin/report/order">订单状态</a></li>
              	<li access="adm_report_manage"><a  href="?p=admin/report/splocation">区域服务商统计</a></li>
              </ul>
           </li>
           <li class="access"  access="adm_report_manage"><a class="title_cs">效率分析</a>
               <ul class="display_none">
                 <li access="adm_report_manage"><a  href="?p=admin/report/effective_sp">服务商效率</a></li>
                 <li access="adm_report_manage"><a  href="?p=admin/report/effective_kf">客服效率</a></li>
                 <li access="adm_report_manage"><a  href="?p=admin/report/effective_user">用户效率</a></li>
                 <li access="adm_report_manage"><a  href="?p=admin/report/effective_order">订单效率</a></li>
              </ul>
           </li>        
           <li class="access"  access="adm_log_manage"><a class="title_cs" href="?p=admin/cfg/log">日志查看</a></li>
           <li ><a class="title_cs info_access" href="?p=admin/info/adminfo" style="display:none;">我的信息</a></li>
           
        </ul>
    </div>
    <script type="text/javascript">
    
    var userRoles = undefined;
    
	postAjaxRequest("/ecs/user/access.do", {}, function(data) {
		userRoles = data.data;
		checkRoles();
	}, false);

    function checkRoles(){
    	var buttons = $('li[access]');
    	buttons.each(function(index){
    		var node = jQuery(buttons[index]);
    		var roleId = node.attr("access");
    		var hasAccess = false;
    		for(i in userRoles){	

    			if(userRoles[i] == roleId){

    				hasAccess = true;
    				break;
    			}
    		}
    		if(!hasAccess){
    			node.hide();
    		}else{
   			
    			node.show();
    			
    		}
    		
    	});
    	
    	$(".info_access").show();
    }
    
    
    function getTipInfos(){
        postAjaxRequest("/ecs/user/todolist.do", {}, function(data){
        	noticeArray = Array();
        	
        	 if(data.sms_account_userid_error){
                 var tisoordermsg = {};
                 tisoordermsg.msg = "<div>" + data.sms_account_userid_error +"</div>";
                 tisoordermsg.type = "sms_account_userid";
                 updateNoticDiv(tisoordermsg);
             }
             
             if(data.baidu_map_key_error){
                 var tisoordermsg = {};
                 tisoordermsg.msg =  "<div>" + data.baidu_map_key_error +"</div>";
                 tisoordermsg.type = "baidu_map_key";
                 updateNoticDiv(tisoordermsg);
             }
             
            var totalUserCount = data.NEW_SP_COUNT + data.UPDATE_SP_COUNT + data.NEW_MFC_COUNT + data.UPDATE_MFC_COUNT;
            var msg = {};
            if(totalUserCount > 0){
                $("#user_approve_tip").html("(" + totalUserCount +")");
                $("#user_approve_page").html("(" + totalUserCount +")");
            }else{
                 $("#user_approve_tip").html("");
                 $("#user_approve_page").html("");
            }
            if(data.NEW_SP_COUNT > 0){
            	var tipmsg = {};
            	tipmsg.msg = "<div><a href='?p=admin/user/newuser&tab=1'>你有" + data.NEW_SP_COUNT +"个新注册服务商需要审核</a></div>";
            	tipmsg.type = "NEW_SP_COUNT";
            	updateNoticDiv(tipmsg);
            }
            if( $("#new_approve .tabs-title")[1]){
	            if(data.NEW_SP_COUNT > 0){
	            	
	                $("#new_approve .tabs-title")[1].innerHTML= "服务商" + "<font color='red'>(" + data.NEW_SP_COUNT +")</font>";
	            }else{
	            	 $("#new_approve .tabs-title")[1].innerHTML="服务商";
	            }
            }
            if(data.NEW_MFC_COUNT > 0){
            	var newmfcmsg = {};
            	newmfcmsg.msg = "<div><a href='?p=admin/user/newuser'>你有" + data.NEW_MFC_COUNT +"个新注册厂商需要审核</a></div>";
            	newmfcmsg.type = "NEW_MFC_COUNT";
            	updateNoticDiv(newmfcmsg);
            }
            if($("#new_approve .tabs-title")[0]){
	            if(data.NEW_MFC_COUNT > 0){
	            	
	                $("#new_approve .tabs-title")[0].innerHTML= "厂商" + "<font color='red'>(" + data.NEW_MFC_COUNT +")</font>";
	            }else{
	            	$("#new_approve .tabs-title")[0].innerHTML= "厂商";
	            }
            }
            if(data.UPDATE_SP_COUNT > 0){
            	var tipupspmsg = {};
            	tipupspmsg.msg = "<div><a href='?p=admin/user/newuser&tab=1'>你有" + data.UPDATE_SP_COUNT +"个修改的服务商信息需要审核</a></div>";
            	tipupspmsg.type = "UPDATE_SP_COUNT";
            	updateNoticDiv(tipupspmsg);
            }
            if($("#update_approve .tabs-title")[1]){
	            if(data.UPDATE_SP_COUNT > 0){
	                $("#update_approve .tabs-title")[1].innerHTML= "服务商" + "<font color='red'>(" + data.UPDATE_SP_COUNT +")</font>";
	            }else{
	            	$("#update_approve .tabs-title")[1].innerHTML= "服务商";
	            }
            }
            if(data.UPDATE_MFC_COUNT > 0){
            	var tipupmfcmsg = {};
            	tipupmfcmsg.msg = "<div><a href='?p=admin/user/newuser'>你有" + data.UPDATE_MFC_COUNT +"个修改的厂商信息需要审核</a></div>";
            	tipupmfcmsg.type = "UPDATE_MFC_COUNT";
            	updateNoticDiv(tipupmfcmsg);
            }
            
            if( $("#update_approve .tabs-title")[0]){
	            if(data.UPDATE_MFC_COUNT > 0){	            	
	                $("#update_approve .tabs-title")[0].innerHTML= "厂商" + "<font color='red'>(" + data.UPDATE_MFC_COUNT +")</font>";
	            }else{
	            	$("#update_approve .tabs-title")[0].innerHTML= "厂商";
	            }
            }
            
            var totalOrderCount = data.SYS_REJECTED_ORDER_COUNT + data.USER_REJECTED_ORDER_COUNT;
            if(totalOrderCount > 0){
            	 $("#admin_order_tip").html("(" + totalOrderCount +")");
            }else{
                 $("#admin_order_tip").html("");
            }
            
            if(data.SYS_REJECTED_ORDER_COUNT > 0){
            	 var tiproordermsg = {};
            	 tiproordermsg.msg = "<div><a href='?p=admin/order/sysreject'>你有" + data.SYS_REJECTED_ORDER_COUNT +"条产品订单需要处理</a></div>";
            	 tiproordermsg.type = "SYS_REJECTED_ORDER_COUNT";
            	 updateNoticDiv(tiproordermsg);
            	 $("#sys_order_tip").html("(" + data.SYS_REJECTED_ORDER_COUNT +")");
            }else{
                 $("#sys_order_tip").html("");
            }
            
            if(data.USER_REJECTED_ORDER_COUNT > 0){
            	 var tisoordermsg = {};
            	 tisoordermsg.msg = "<div><a href='?p=admin/order/userreject'>你有" + data.USER_REJECTED_ORDER_COUNT +"条服务商返回的订单需要更换服务商</a></div>";
            	 tisoordermsg.type = "USER_REJECTED_ORDER_COUNT";
            	 updateNoticDiv(tisoordermsg);
            	 $("#user_order_tip").html("(" + data.USER_REJECTED_ORDER_COUNT +")");
            }else{
                 $("#user_order_tip").html("");
            }
            
            var totalNoticeCount = data.T_HOURS_ORDER_COUNT + data.F_HOURS_ORDER_COUNT;
            
            if(totalNoticeCount > 0){
            	 $("#order_notice_tip").html("(" + totalNoticeCount +")");
            }else{
            	 $("#order_notice_tip").html("");
            }
            
            if($("#noticetab .tabs-title")[0]){
	            if(data.T_HOURS_ORDER_COUNT > 0){
	                 $("#noticetab .tabs-title")[0].innerHTML= "逾24小时订单提醒" + "<font color='red'>(" + data.T_HOURS_ORDER_COUNT +")</font>";
	            }else{
	            	$("#noticetab .tabs-title")[0].innerHTML="逾24小时订单提醒";
	            }
            }
            
            if($("#noticetab .tabs-title")[1]){
	            if(data.F_HOURS_ORDER_COUNT > 0){
	                 $("#noticetab .tabs-title")[1].innerHTML= "逾48小时订单提醒" + "<font color='red'>(" + data.F_HOURS_ORDER_COUNT +")</font>";
	            }else{
	            	 $("#noticetab .tabs-title")[1].innerHTML= "逾48小时订单提醒";
	            }
            }
            
            
           

        }, false);
    }
    $(document).ready(function(){
    	getTipInfos();
	    var siid = window.setInterval(function() {
	    	getTipInfos();	        
	    }, 10000);
    });
    
    </script>
    <script src="resources/js/jquery.min.js"></script>
    <script src="resources/js/unicorn.js"></script>
</body>
</html>