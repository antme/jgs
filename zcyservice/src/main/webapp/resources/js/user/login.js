$(document).ready(function() {
	$("#submit").click(function() {
		$("#submit-button").click();
		return false;
	});

	$("#login-form").form({
		url : '/ecs/user/login.do',
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			dealMessageWithCallBack(data, "登录信息", function(){
				var host = window.location.host;
				window.location.href = "http://" + host + "/index.jsp";
				
			});
			
		}
	});
	
});
window.load=function(){
	$("#userName").val("");
	$("#password").val("");
	//$("#userName").val("");
};
function getPwdCode() {
	$("#forget_pwd_form").form({
		url : '/ecs/user/forgot/pwd/getCode.do',
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			dealMessageWithCallBack(data, "获取手机验证码", function(){
				sendMsgButtonDisable("getPwdCode");
				$(".color_6").show();
			});
		}
	});

	$("#forget_pwd_form").submit();

}

function resetPwdByMobile() {
	$("#reset_pwd_form").form({
		url : '/ecs/user/forgot/pwd/reset.do',
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(data) {
			dealMessage(data, "重设置密码", "/login.jsp");
		},
		error :function(){
			alert("设置密码失败！");
		}
	});
	adm('reset_pwd_form');
}

function  adm(id){
	
	if(window.ActiveXObject)
    {
        var browser=navigator.appName;
        var b_version=navigator.appVersion; 
        var version=b_version.split(";"); 
        var trim_Version=version[1].replace(/[ ]/g,""); 
        if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE6.0") 
        { 
        	$("#"+id).submit();
        }else{
        }
    }else{
    }
}

$(document).ready(function() {
	$(".c_li").click(function(){
        $(this).addClass("c-back");
        $(".f_li").removeClass("f-back");
        $(".a_li").removeClass("z-back");
        $(".g_li").removeClass("g-back");
        $(".b_li").addClass("z-back");
        $(".c-information").show();
        $(".f-information").hide();
        $(".g-information").hide();
        $(".f-select-ul").hide();
        $(".f-information-user").hide();
    });
    $(".f_li").click(function(){
        $(this).addClass("f-back");
        $(".b_li").removeClass("z-back");
        $(".c_li").removeClass("c-back");
        $(".g_li").removeClass("g-back");
        $(".a_li").removeClass("z-back");
        $(".c-information").hide();
        $(".f-information").show();
        $(".f-select-ul").show();
        $(".g-information").hide();
    });
    $(".g_li").click(function(){
        $(".a-li").removeClass("z-back");
        $(this).addClass("g-back");
        $(".c_li").removeClass("c-back");
        $(".f_li").removeClass("f-back");
        $(".a_li").addClass("z-back");
        $(".c-information").hide();
        $(".f-information").hide();
        $(".g-information").show();
        $(".f-select-ul").hide();
        $(".f-information-user").hide();
    });
    $("#qy").click(function(){
    	$(".f-information").show();
    	$(".f-information-user").hide();
    });
    $("#gr").click(function(){
    	$(".f-information").hide();
    	$(".f-information-user").show();
    });
    $("#tds").click(function(){$(".loadpass").find("label").css("color","#979797");});
    $("#tdb").click(function(){$(".loadpass").find("label").css("color","#979797");});
    $("#td").click(function(){$(".loadpass").find("label").css("color","#979797");});
    $("#user-submit").click(function(){   
    	if(document.getElementById("tds").checked){
    		$("#user-submit-button").click();
    	}else{
    		$(".loadpass").find("label").css("color","red");
    	}
    });
    
    $("#mfc-submit").click(function(){
    	var cate_mfc=$("#mfc_category").find("input");
    	if($("#c_s_province").combobox('getValue')=="0"){
    		alert("请选择省");
    	}else if($("#c_s_city").combobox('getValue')=="0"){
    		alert("请选择市");
    	}
    	else if($("#c_s_county").combobox('getValue')=="0"){
    		alert("请选择区");
    	}else{
    		var n=0;
    		for(var i=0;i<cate_mfc.length;i++){
    			if($(cate_mfc[i]).is(':checked')){
    				n++;
    			}
    		}
    		if(n!=0){
    			if(document.getElementById("td").checked){
            		$("#mfc-submit-button").click();
            	}else{
            		$(".loadpass").find("label").css("color","red");
            	} 
    		}else{
    			alert("请选择主营类型");
    		}
    		
    	}
    	 	
    });
    
    $("#sp-submit").click(function(){
    	var cate_mfc=$("#sp_category").find("input");
    	if($("#s_s_province").combobox('getValue')=="0"){
    		alert("请选择省");
    	}else if($("#s_s_city").combobox('getValue')=="0"){
    		alert("请选择市");
    	}
    	else if($("#s_s_county").combobox('getValue')=="0"){
    		alert("请选择区");
    	}else{
    		var n=0;
    		for(var i=0;i<cate_mfc.length;i++){
    			if($(cate_mfc[i]).is(':checked')){
    				n++;
    			}
    		}
    		if(n!=0){
    			if(document.getElementById("tdb").checked){
    	        	$("#sp-submit-button").click(); 
    	    	}else{
    	    		$(".loadpass").find("label").css("color","red");
    	    	}
    		}else{
    			alert("请选择主营类型");
    		}
    		
    	}
    });

    $("#user-info").form({
        url:'/ecs/user/reg.do',
        onSubmit:function(){
            return $(this).form('validate');
        },
        success:function(data){
        	dealMessage(data, "个人注册", "/index.jsp");
        }
    });
    
    $("#mfc").form({
        url:'/ecs/mfc/reg.do',
        onSubmit:function(){
            return $(this).form('validate');
        },
        success:function(data){
        	dealMessageWithCallBack(data, "厂商注册", mfcRegCallBack);
        }
    });
    
    $("#sp").form({
        url:'/ecs/sp/reg.do',
        onSubmit:function(){

            return $(this).form('validate');
        },
        success:function(data){
        	dealMessageWithCallBack(data, "服务商注册", spRegCallBack);
        }
    });
    
});


function getRegCode() {

	var mobile = $("#mobileNumber").val();
	var imgCode = $("#userRegImgCode").val();
	if (!mobile || mobile == "") {
		alert("请输入手机号码");
		return;
	}
	
	if (!imgCode || imgCode == "") {
		alert("请输入验证码");
		return;
	}

	postAjaxRequest("/ecs/user/reg/getCode.do", {
		"mobileNumber" : mobile,
		"imgCode" : imgCode
		
	}, function(data) {
		$.messager.alert("信息", "验证码已发送手机，请注意查收", 'info');
		// 60秒倒计时
		sendMsgButtonDisable("getRegCode");
	});
}

function sendMsgButtonDisable(id) {
	var i = 90;
	var sid = window.setInterval(function() {
		$("#" + id).hide();
		$("#getRegCodeInfo").show();
		$("#getRegCodeInfo").html(i + "秒后可以再次发送");
		i = i - 1;

		if (i == 0) {
			$("#" + id).show();
			$("#getRegCodeInfo").hide();
			window.clearInterval(sid);
		}
	}, 1000);
}



function mfcRegCallBack(data){
	$.messager.alert("信息", "注册信息已经提交，请耐心等待审核，审核过后会有短信提醒通过审核，请注意查收短信", 'info');
}


function spRegCallBack(data) {
	$.messager.alert("信息", "注册信息已经提交，请耐心等待审核，审核过后会有短信提醒通过审核，请注意查收短信", 'info');
}

function whichButton(event)
{
	if(event.keyCode==13){
		$("#submit").click();
	}
}