<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>电子档案管理系统</title>
<link href="resources/css/web/login/login.css" rel="stylesheet"/>
<link href="resources/css/easyui.css" rel="stylesheet"/>
<script type="text/javascript" src="resources/js/jquery.min.js"></script>
<script type="text/javascript" src="resources/js/jquery.easyui.min.js"></script>
</head>

<%
    if(session.getValue("userName")!=null){
        response.sendRedirect("index.jsp");
    }
%>

<body class="back_body"> 
    <div class="cal_cen_login">
        <div class="cal_cen_login_top">
            <div class="cal_cen_login_top_left"></div>
            <div class="cal_cen_login_top_mid">
                <span class="cal_cen_login_headimg"></span>
                <span class="cal_cen_login_h_line"></span>
                <form id="bd"  action="" method="post" >
                <span class="cal_cen_login_form">
                    <p>
                       <label class="cal_cen_login_lab">用户名：</label>
                       <input id="userName" type="text" name="userName" class="input_middel cal_cen_login_pwd easyui-validatebox"  required missingMessage="请输入用户名"/>
                    </p>
                    <p>
                       <label class="cal_cen_login_lab">密&nbsp;&nbsp;码：</label>
                       <input id="password" type="password" name="password" class="input_middel cal_cen_login_pwd easyui-validatebox"  required missingMessage="请输入密码"/>
                    </p>
                    <p>
                       <label class="cal_cen_login_lab">验证码：</label>
                       <input id="imgCode" type="text" name="imgCode" class="input_middel cal_cen_login_yzm easyui-validatebox"  deltaX="70" required missingMessage="请输入验证码"/>
                       <img  src="/ecs/user/img.do" id="randomcode" onclick="changeImage();" class="input_middel cal_cen_login_yzimg"/>
                    </p>
                    <script type="text/javascript">
                        $(".change_img").click(function(){
                    	   changeImage();
                       });    
                       function changeImage(){
                           $("#randomcode").attr("src", "/ecs/user/img.do?_id=" + +Math.random());
                       }
                    </script>
                    <p class="cal_cen_tip"><label class="cal_cen_lab_c" id="tsMsg"></label></p>
                    <span class="cal_cen_login_btn_bg" onclick="login()"></span>
                </span>
                </form>
            </div>

            <div class="cal_cen_login_top_right"></div>
        </div>
         <div class="cal_cen_login_footer"></div>
        </div>
        <script type="text/javascript">
             function login(){
            	 $("#bd").submit();
             }
             $("#bd").form({
            	 url : '/ecs/user/login.do',
                 onSubmit : function() {
                     return $(this).form('validate');
                 },
                 success : function(data) {
                	 var info = eval('(' + data + ')');
                	 if(info.code!="200"){
                		 $.messager.alert("登录失败",info.msg);
                	 }else{
                		 window.location.href="index.jsp";
                	 }
                 }
             });
             function keyDown(evt) {  
            	evt = (evt) ? evt : ((window.event) ? window.event : "") 
                keyCode = evt.keyCode ? evt.keyCode : (evt.which ? evt.which : evt.charCode);
            	if(keyCode=='13'){
            		if($("#userName").val()!="" && $("#password").val()!="" && $("#imgCode").val()!=""){
            			$("#bd").submit();
            		}
            	}
             }
             document.onkeydown = keyDown;
        </script>
</body>
</html>