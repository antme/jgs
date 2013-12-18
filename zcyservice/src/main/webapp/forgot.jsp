<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>找回密码</title>
<link href="resources/css/loginCSS/ui.css" rel="stylesheet">
<link href="resources/css/loginCSS/login.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/resources/css/easyui.css">
<script type="text/javascript" src="resources/js/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script src="/resources/js/user/login.js"></script>
<script type="text/javascript" src="resources/js/ecommerce.js"></script>
<script type="text/javascript" src="resources/js/validation.js"></script>
</head>
<body class="view-login login_back" onResize="left_change()">
      <div class="back_margin forget_back">
          <div class="forget_info" style="_margin-left:">
          	<form action="#" method="post" novalidate id="forget_pwd_form">
               <div>请填写您在申请注册帐号时的手机号码：</div>
               <div><input type="text" name="mobileNumber" class="field-input4 easyui-validatebox" validtype ="mobile"  required missingMessage="请输入手机号码"></input></div>
               <div>请填写验证码：</div>
              	<div><input name="imgCode" id="" class="k-input k-textbox field-input3 easyui-validatebox" style="height:30px;" type="text" deltaX="100" size="10" required missingMessage="请输入验证码"/>
                    <a class="verification"><img src="/ecs/user/forgot/pwd/img.do" id="randomcode" onclick="changeImage();"></a>
               		<a class="yzm_btn" onclick="getPwdCode();" id="getPwdCode">发送验证码</a><span id="getRegCodeInfo"></span>
               	</div>
               	       <script type="text/javascript">                            
                              function changeImage(){
                                   $("#randomcode").attr("src", "/ecs/user/img.do?_id=" + +Math.random());
                              }
                       </script>
               </form>
               <div class="color_6" style="_margin-top:-10px;_float:left;">
                                                                 您的密码已经发送至您手机，请查看短信。如长<br>
                                                                 时间没有收到，请在次点击“<a class="color_r">发送验证码</a>”按钮<br>                                  
               </div> 
          </div>
          <div class="line_div"></div>
          <div class="write_info" style="_margin-left:">
         	 <form action="#" method="post" novalidate id="reset_pwd_form">
              <div>
                  <label>验证码：</label>
                  <input id="pwdCode" class="r-textbox easyui-validatebox"  type="text" name="pwdCode" required missingMessage="请输入手机验证码"/>
              </div>
              <div>
                  <label>新密码：</label>
                  <input id="password" class="r-textbox easyui-validatebox"  type="password" name="password" id="password"  required missingMessage="请输入新密码"/>
              </div>
              <div>
                  <label>确认密码：</label>
                  <input class="r-textbox easyui-validatebox" type="password" required missingMessage="请输入确认密码" validType="pwdEquals['#password']"/>
              </div>
              <div>
                  <span class="span_style" style="width:150px;"><button class="btn-submit2" style="margin-left:0px;" onclick="resetPwdByMobile();"></button></span>
                  <span class="span_style" style="width:150px;"><a class="r-submit rt" href="login.jsp"></a></span>
              </div>
              </form>
          </div>
      </div>
      <script type="text/javascript">
         $(document).ready(function(){
        	 var get_left = (document.body.clientWidth - 628)/2;
        	 $(".forget_back").css("margin-left",get_left);
        	 $("#pwdCode").val("");
             $("#password").val("");
         });
         function left_change(){
        	 var get_left = (document.body.clientWidth - 628)/2;
             $(".forget_back").css("margin-left",get_left);
         }
         $.extend($.fn.validatebox.defaults.rules, {
        	    pwdEquals: {
        	        validator: function(value,param){
        	            return value == $(param[0]).val();
        	        },
        	        message: '密码不匹配'
        	    }
        });
         window.load=function(){
        	    $("#pwdCode").val("");
        	    $("#password").val("");
        	    //$("#userName").val("");
        	};
      </script>
</body>
</html>