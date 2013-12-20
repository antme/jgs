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
                       <input type="text" class="input_middel cal_cen_login_pwd easyui-validatebox"  required missingMessage="请输入用户名"/>
                    </p>
                    <p>
                       <label class="cal_cen_login_lab">密&nbsp;&nbsp;码：</label>
                       <input type="password" class="input_middel cal_cen_login_pwd easyui-validatebox"  required missingMessage="请输入密码"/>
                    </p>
                    <p>
                       <label class="cal_cen_login_lab">验证码：</label>
                       <input type="text" class="input_middel cal_cen_login_yzm easyui-validatebox"   required missingMessage="请输入验证码"/>
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
                    <p class="font_12 f666 cal_cen_tip"><input type="checkbox" class="input_middel cal_cen_login_zddlck" name="Weeked" id="Week" value="on" />下次自动登录</p>
                    <p class="cal_cen_tip"><label class="cal_cen_lab_c" id="tsMsg"></label></p>
                    <span class="cal_cen_login_btn_bg"></span>
                </span>
                </form>
            </div>

            <div class="cal_cen_login_top_right"></div>
        </div>
         <div class="cal_cen_login_footer"></div>
        </div>
</body>
</html>