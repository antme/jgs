<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>企辕电商项目</title>
<link rel="stylesheet" type="text/css" href="/resources/css/easyui.css">
<script src="../resources/js/jquery.min.js"></script>
<script src="/resources/js/user/login.js"></script>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resources/js/ecommerce.js"></script>
<script type="text/javascript" src="resources/js/validation.js"></script>
<link href="resources/css/loginCSS/ui.css" rel="stylesheet">
<link href="resources/css/loginCSS/login.css" rel="stylesheet">


</head>
<%
    if(session.getValue("userName")!=null){
        response.sendRedirect("index.jsp");
    }
%>
<script type="text/javascript">
window.onload = function(){
	document.getElementById("userName").focus();
	
}
</script>
<body class="view-login login_back" > 
    <!-- null
    onkeyup="whichButton(event)"
    8080
 -->
    <form action="/ecs/user/login.do" method="post" novalidate id="login-form">
        <div class="container">
            <!-- Begin Content -->
            <div class="login well">
                <div class="login_title"></div>
                <div id="form-login" class="k-edit-form-container">
                    <ul>
                        <li>
                            <div class="k-edit-label">
                                <label for="userName">用户名:</label>
                            </div>
                            <div class="k-edit-field">
                                <div class="k-input k-textbox field-input1">
                                    <input name="userName" id="userName" class="div_input_login easyui-validatebox" type="text" size="15"   required missingMessage="请输入用户名或手机号码" />
                                </div>
                                <div class="tip_info">可以输入用户名或手机号</div>
                            </div>
                        </li>
                        <li>
                            <div class="k-edit-label">
                                <label>密&nbsp    码:</label>
                            </div>
                            <div class="k-edit-field ">
                                <div class="k-input k-textbox field-input2">
                                   <input name="password" id="password" autocomplete="off"  data-bind="value: password" class="div_input_login easyui-validatebox" required missingMessage="请输入密码"  type="password" size="15" />
                                </div>
                            </div>
                        </li>
                        <li>
                            <div class="k-edit-label">
                                <label>验证码:</label>
                            </div>
                            <div class="k-edit-field">
                                <input name="imgCode" id="" class="k-input k-textbox field-input3 easyui-validatebox" type="text" deltaX="100" size="15" style="_height:30px;" validType="unnormal" required missingMessage="请输入验证码"/>
                                <a class="verification"><img id="randomcode" src="/ecs/user/img.do" onclick="changeImage();" ></a>
                                <a class="change_img">换一张</a>
                                <script type="text/javascript">
                                    $(".change_img").click(function(){
                                    	changeImage();
                                    });    
                                    function changeImage(){
                                    	 $("#randomcode").attr("src", "/ecs/user/img.do?_id=" + +Math.random());
                                    }
                                </script>
                            </div>
                        </li>
                        <li class="dis_clear">
                            <div class="k-edit-label"></div>
                            <div class="k-edit-field">
                                 <a class="loadpass"><input id="td" name="remember" type="checkbox"/><label for="td">记住密码</label></a>
                                 <a class="getpassdword" href="forgot.jsp">忘记密码！</a>
                            </div>
                             
                        </li>
                        <li class="dis_clear_top">
                            <div class="k-edit-field btn_bottom">
                                <input type="submit" class="btn-submit text_indent60" value="" />
                                <a href="register.jsp" class="btn-registered"></a>
                            </div>
                                
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </form>
    
    <noscript>警告！为正确操作管理后台，JavaScript 必须启用。</noscript>    
    <script type="text/javascript" src="resources/js/jquery.tooltip.js"></script>
        <script type="text/javascript">
	    $(document).ready(function(){
	    	$("img").each(function(){	  
	    		if($(this).attr('src').indexOf("baidu")!=-1){
	    		$(this).hide();
	    		}
	    	});
	    	
	    });
    </script>
    

<div class="login_bottom">沪ICP备13039322号</div>
</body>
</html>