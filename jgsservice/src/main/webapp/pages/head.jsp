<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>小猫安装平台</title>
<script type="text/javascript" src="resources/js/jquery.min.js"></script>
<script type="text/javascript" src="resources/js/jquery.pngFix.js"></script>
<script type="text/javascript">
var cookievalue = getCookie("pf");
if(cookievalue=="2"){
    $("<link>").attr({ rel: "stylesheet",type: "text/css",href: "/resources/css/pf2.css"}).appendTo("head");
}else{
	$("<link>").attr({ rel: "stylesheet",type: "text/css",href: "/resources/css/pf.css"}).appendTo("head");
}
function getCookie(name) {
    var cookieStr = document.cookie;
    if(cookieStr.length > 0) {
        var cookieArr = cookieStr.split(";"); //将cookie信息转换成数组
        for (var i=0; i<cookieArr.length; i++) {
            var cookieVal = cookieArr[i].split("="); //将每一组cookie(cookie名和值)也转换成数组
            if(cookieVal[0] == name) {
                return unescape(cookieVal[1]); //返回需要提取的cookie值
            }
        }
    }
}
</script>
</head>
<body>
	<div class="head">
		<a class="head_login" href="index.jsp"></a>
		<div class="login_info">
			<!-- <div class="info_photo"></div>-->
			<!-- <input type="button" class="info_done" /> -->
			<div class="info_user">
                <% if("MFC".equals(session.getAttribute("roleName"))){%><a style="color:#fff;" href="/context/小猫服务平台用户手册-厂商.doc" >用户手册</a><%}%>
                <% if("SP".equals(session.getAttribute("roleName"))){%><a style="color:#fff;" href="/context/小猫服务平台用户手册-服务商.doc">用户手册</a><%}%>
            </div>
			<div class="info_user">
                <% if("MFC".equals(session.getAttribute("roleName"))){%><a style="color:#fff;" target="_blank" href="context/mfc.html" >帮助文档</a><%}%>
                <% if("SP".equals(session.getAttribute("roleName"))){%><a style="color:#fff;" target="_blank" href="context/sp.html">帮助文档</a><%}%>
            </div>
			<a class="info_done" href="#" onclick="logout(); return false;"></a>
			<% if("MFC".equals(session.getAttribute("roleName")) || "SP".equals(session.getAttribute("roleName"))){%>
			<div class="info_has_info">
				<div>
					<a style="text-decoration:none" href="?p=message/receiver/list"><span class="info_has_info_title">您的消息</span> <label class="info_has_info_num" ></label></a>
				</div>
			</div>
			<% }%>
			<div class="info_user">
				<% if("MFC".equals(session.getAttribute("roleName"))){%><label>尊敬的厂商用户</label><a href="?p=web/mfc/mfcinfo" style="color:#ff6600"><% out.print(session.getAttribute("userName")); %></a><%}%>
				<% if("SP".equals(session.getAttribute("roleName"))){%><label>尊敬的服务商用户</label><a href="?p=web/sp/spinfo" style="color:#ff6600"><% out.print(session.getAttribute("userName")); %></a><%}%>
				<% if("ADMIN".equals(session.getAttribute("roleName"))){%><label>尊敬的管理员</label><% out.print(session.getAttribute("userName")); %><%}%>
				<% if("CUSTOMER_SERVICE".equals(session.getAttribute("roleName"))){%><label>尊敬的客服</label><% out.print(session.getAttribute("userName")); %><%}%>
				<% if("SUPPER_ADMIN".equals(session.getAttribute("roleName"))){%><label>尊敬的超级管理员</label><% out.print(session.getAttribute("userName")); %><%}%>
				<label>欢迎进入小猫平台</label>
			</div>
			
			<div class="select_pf" style="display:none"><select id="pf"><option value="1" selected="selected">经典皮肤 </option><option value="2">白色恋人 </option></select></div>
		</div>
	</div>
	<script type="text/javascript">

	     
	     $("#pf").change(function(){
	    	 if($(this).find("option:selected").val()=="1"){
	    		 document.cookie = "pf=1";
	    		 $("<link>").attr({ rel: "stylesheet",type: "text/css",href: "/resources/css/pf.css"}).appendTo("head");
	    		 
	    	 }else if($(this).find("option:selected").val()=="2"){
	    		 document.cookie = "pf=2";
	    		 $("<link>").attr({ rel: "stylesheet",type: "text/css",href: "/resources/css/pf2.css"}).appendTo("head");
	    	 }
	    	 
	     });
	</script>
</body>
</html>