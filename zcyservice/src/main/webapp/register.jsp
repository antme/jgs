<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册</title>
<link href="resources/css/loginCSS/ui.css" rel="stylesheet">
<link href="resources/css/loginCSS/login.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/resources/css/easyui.css">
<script type="text/javascript" src="resources/js/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="resources/js/user/login.js"></script>
<script type="text/javascript" src="resources/js/ecommerce.js"></script>
<script type="text/javascript" src="resources/js/city-price.public.js"></script>
<script type="text/javascript" src="resources/js/validation.js"></script>
<script type="text/javascript" src="resources/js/public_css.js"></script>
<!--[if IE]>
<style type="text/css">
.combo .combo-text {
    text-align: center;
    line-height: 30px;
}
</style>
<![endif]-->
</head>
<body class="login_back">
	<div class="context_border">
		<div class="context_head">
			<ul>
				<li class="c_li c-back">厂&nbsp&nbsp商</li>
				<li class="a_li">&nbsp</li>
				<li class="f_li">服务商</li>
				<li class="b_li z-back">&nbsp</li>
				<li class="g_li">个人</li>
			</ul>
		</div>
		<div class="context_center">
			<!--厂商注册 信息-->
			<form action="/ecs/mfc/reg.do" id="mfc" name="mfc" method="post" novalidate >
				<ul class="c-information">
					<li>
						<div class="r-edit-label">店铺名称：</div>
						<div class="r-edit-field">
							<input name="mfcStoreName" class="r-textbox at easyui-validatebox"
								type="text"  required missingMessage="请输入店铺名称" /><label
								class="r-need">*</label>
						</div>
					</li>
					<li>
                        <div class="r-edit-label">密码：</div>
                        <div class="r-edit-field">
                            <input name="password"  id="mfcpassword" autocomplete="off" onfocus="this.type='password'" class="r-textbox at easyui-validatebox"
                                type="password" validType="username" required="true" missingMessage="请输入密码" /><label
                                class="r-need">*</label>
                        </div>
                    </li>
                    <li>
                        <div class="r-edit-label">确认密码：</div>
                        <div class="r-edit-field">
                            <input name="passwordConfirm" autocomplete="off" onfocus="this.type='password'"
                                class="r-textbox at easyui-validatebox" type="password" required="true"
                                missingMessage="请再次输入密码"  validType="pwdEquals['#mfcpassword']"/><label
                                class="r-need">*</label>
                        </div>
                    </li>
                   <li>
						<div class="r-edit-label">主营类型：</div>
						<div class="r-edit-field" style="height:auto">
							<div class="r-textbox2" id="mfc_category" style="height:auto,width:334px;float:left;">								   
                                                          
							</div>
							<label class="r-need">*</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">公司名称：</div>
						<div class="r-edit-field">
							<input name="mfcCompanyName" class="r-textbox at easyui-validatebox"
								type="text" required missingMessage="请输入公司名称" /> <label
								class="r-need">*</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">所在地：</div>
						<div class="r-edit-field" style="width:416px;">
							<div class="r-select">
								<input id="c_s_province" name="mfcLocationProvinceId" class="easyui-combobox ap " data-options=" valueField:'id',textField:'text' ,width:128,height:35,multiple:false" required missingMessage="请选择省"/>
								<input id="c_s_city"   name="mfcLocationCityId" class="easyui-combobox ap " data-options=" valueField:'id',textField:'text' ,width:128,height:35,multiple:false" required missingMessage="请选择市"/>
								<input id="c_s_county" name="mfcLocationAreaId" class="easyui-combobox ap " data-options=" valueField:'id',textField:'text' ,width:128,height:35,multiple:false" required missingMessage="请选择区"/>
							</div>
						</div>
						
					</li>
					<li>
						<div class="r-edit-label">公司地址：</div>
						
						<div class="r-edit-field">
							<input name="mfcCompanyAdress" class="r-textbox2 r-update easyui-validatebox"
								type="text" style="_margin-left:-8px;" required missingMessage="请输入公司地址"  /> <label
								class="r-need">*</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">联系人：</div>
						<div class="r-edit-field">
							<input name="mfcContactPerson"
								class="r-textbox at easyui-validatebox" type="text" required
								missingMessage="请输入联系人" /> <label class="r-need">*</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">固定电话：</div>
						<div class="r-edit-field">
							<div class="r-textbox at r-margin-left">
								<input name="mfcContactPhone" class="r-qh" type="text" /> <input name=""
									class="r-hm" type="text" />
							</div>
							<!-- <input name="mfcContactPhone" class="r-textbox" type="text" /> -->
						</div>
					</li>
					<li>
						<div class="r-edit-label">手机：</div>
						<div class="r-edit-field">
							<input name="mfcContactMobilePhone" class="r-textbox at easyui-validatebox" type="text"
								type="text" validtype ="mobile" required missingMessage="请输入联系手机"  /> <label
								class="r-need">*</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">QQ：</div>
						<div class="r-edit-field">
							<input name="mfcQQ" class="r-textbox at easyui-validatebox" type="text"  />
						</div>
					</li>
					<li>
						<div class="r-edit-label"></div>
						<div class="r-edit-field">
							<div class="loadpass" >
							  <input id="td" type="checkbox" class="easyui-validatebox" validType="checkbox['mfc','checkbox']" />
							  <label for="td">我已阅读并同意</label>
							  <a href="/pages/agree/mfc_agree.html" target="_Blank" class="text_underline">（小猫电商注册协议）</a>
						    </div>
						</div>
					</li>
					<li>
						<div class="r-edit-label"></div>
						<div class="r-edit-field" style="height:auto;width:400px;">
							<span class="span_style" style="_width:150px;"><a class="r-submit fw" id="mfc-submit"></a></span>
							<span class="span_style" style="_width:150px;"><a class="r-submit rt" href="login.jsp"></a></span>
							<span class="span_style" style="_width:150px;"><input type="submit" value="Submit" style="display: none;" id="mfc-submit-button" /></span> 
						</div>
					</li>
				</ul>
			</form>

			<!--服务商注册 信息-->
			<div class="f-select-ul">
			     <div class="r-edit-label" style="display:none">注册服务商类型：</div>
                 <div class="r-edit-field r-select-style" style="display:none">
                    <div class="type_div">
                        <input id="qy" name="sptype" type="radio" checked="true"/>
                        <label>企业</label>
                        <input id="gr" name="sptype" type="radio"/>
                        <label>个人</label>
                    </div>
                    <label class="r-need">*</label>
                 </div>
			</div> 
			<form action="/ecs/sp/reg.do" id="sp" method="post" novalidate enctype="multipart/form-data">
				<ul class="f-information">
					<li>
						<div class="r-edit-label">用户名：</div>
						<div class="r-edit-field">
							<input name="spUserName" class="r-textbox at easyui-validatebox"
								type="text" required missingMessage="请输入用户名" /> <label class="r-need">*</label>
						</div>
					</li>
					<li>
                        <div class="r-edit-label">密码：</div>
                        <div class="r-edit-field">
                            <input name="password"  id="sppassword" autocomplete="off" onfocus="this.type='password'" class="r-textbox at easyui-validatebox"
                                type="password" validType="username" required="true" missingMessage="请输入密码" /> <label class="r-need">*</label>
                        </div>
                    </li>
                    <li>
                        <div class="r-edit-label">确认密码：</div>
                        <div class="r-edit-field">
                            <input name="passwordConfirm" autocomplete="off" onfocus="this.type='password'"
                                class="r-textbox at easyui-validatebox" type="password" required="true"
                                missingMessage="请再次输入密码"  validType="pwdEquals['#sppassword']"/><label class="r-need">*</label>
                        </div>
                    </li>
					<li>
						<div class="r-edit-label">主营服务类型：</div>
						<div class="r-edit-field" style="height:auto">
							<div class="r-textbox2" id="sp_category" style="height:auto,width:334px;float:left;">								   
                                                          
							</div>
							<label class="r-need">*</label>
						</div>
						<input type="hidden" name="spServiceType" id="spServiceType"/>
					</li>
					<li>
                        <div class="r-edit-label">工人数：</div>
                        <div class="r-edit-field" >
                             <input id="10" type="radio" name="spCompanySize" value="0~10人" checked="checked"/><label for="10">0~10人</label>
                             <input id="11" type="radio" name="spCompanySize" value="10~50人"/><label for="11">10~50人</label>
                             <input id="12" type="radio" name="spCompanySize" value="50~100人"/><label for="12">50~100人</label>
                             <input id="13" type="radio" name="spCompanySize" value="100人以上"/><label for="13">100人以上</label>
                        </div>
                    </li>
					<li>
						<div class="r-edit-label">公司名称：</div>
						<div class="r-edit-field">
							<input name="spCompanyName" class="r-textbox at easyui-validatebox"
								required type="text" missingMessage="请输入公司名称" /> <label
								class="r-need">*</label>
						</div>
					</li>
					<li>
                        <div class="r-edit-label">所在地：</div>
                        <div class="r-edit-field" style="width:416px;">
                            <div class="r-select">
                                <input id="s_s_province" name="spLocationProvinceId" class="easyui-combobox ap " data-options=" valueField:'id',textField:'text' ,width:128,height:35,multiple:false" required missingMessage="请选择省"/>
                                <input id="s_s_city" name="spLocationCityId" class="easyui-combobox ap " data-options=" valueField:'id',textField:'text' ,width:128,height:35,multiple:false" required missingMessage="请选择市"/>
                                <input id="s_s_county" name="spLocationAreaId" class="easyui-combobox ap " data-options=" valueField:'id',textField:'text' ,width:128,height:35,multiple:false" required missingMessage="请选择区"/>
                            </div>
                        </div>
                    </li>
					<li>
						<div class="r-edit-label">公司地址：</div>
						<div class="r-edit-field">
							<input name="spCompanyAddress"
								class="r-textbox2 r-update easyui-validatebox" style="_margin-left:-8px;" type="text"
								required missingMessage="请输入公司地址" /> <label class="r-need">*</label>
						</div>
					</li>
					
					<li>
						<div class="r-edit-label">联系人：</div>
						<div class="r-edit-field">
							<input name="spContactPerson"
								class="r-textbox at easyui-validatebox" type="text" required
								missingMessage="请输入联系人" /> <label class="r-need">*</label>
						</div>
					</li>
					<li>
                        <div class="r-edit-label">固定电话：</div>
                        <div class="r-edit-field">
                            <div class="r-textbox at r-margin-left">
                                <input name="spContactPhone" class="r-qh" type="text" /> <input name="" class="r-hm" type="text" />
                            </div>
                        </div>
                    </li>
					<li>
						<div class="r-edit-label">联系手机：</div>
						<div class="r-edit-field">
							<input name="spContactMobilePhone" class="r-textbox at easyui-validatebox"
								type="text" validtype ="mobile" required missingMessage="请输入联系手机" /> <label
								class="r-need">*</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">营业执照：</div>
						<div class="r-edit-field r-margin-height" style="height:60px">
							<input name="spLicenseNo" class="r-textbox  at easyui-validatebox"  style="height:30px;"
								type="file" validType="fileType['GIF|JPG|PNG']" required 
								invalidMessage="请选择(GIF|JPG|PNG)等格式的图片" missingMessage="请输上传执照" />
							<label class="r-need">*</label>
							<div class="tip_info">上传图片格式为GIF、JPG、PNG,大小限制512KB</div>
						</div>
					</li>
					<li>
						<div class="r-edit-label">企业形象图片：</div>
						<div class="r-edit-field r-margin-height" style="height:60px">
							<input name="storeImage" class="r-textbox at easyui-validatebox" type="file" validType="fileType['GIF|JPG|PNG']" 
							  style="height:30px;"	invalidMessage="请选择(GIF|JPG|PNG)等格式的图片" missingMessage="请输上传图片" />
							<div class="tip_info" style="line-height:20px;">上传图片格式为GIF、JPG、PNG,大小限制512KB</div>
						</div>
					</li>
					<li>
						<div class="r-edit-label">QQ：</div>
						<div class="r-edit-field">
							<input name="spQQ" class="r-textbox at" type="text" />
						</div>
					</li>
					<li>
						<div class="r-edit-label"></div>
						<div class="r-edit-field">
							<div class="loadpass"><input id="tdb" type="checkbox" /><label
								for="tdb">我已阅读并同意</label>
								 <a href="/pages/agree/sp_agree.html" target="_Blank" class="text_underline">（小猫电商注册协议）</a>
						    </div>
						</div>
					</li>
					<li>
						<div class="r-edit-label"></div>
						<div class="r-edit-field">
							<span class="span_style" style="_width:150px;"><a class="r-submit fw" id="sp-submit"></a></span>
							<span class="span_style" style="_width:150px;"><a class="r-submit rt" href="login.jsp"></a></span>
							<span class="span_style" style="_width:150px;"><input type="submit" value="Submit" style="display: none;" id="sp-submit-button" /></span>
						</div>
					</li>
				</ul>
			</form>
			<form action="" id="" method="post">
                <ul class="f-information-user">
                    <li>
                        <div class="r-edit-label">用户名：</div>
                        <div class="r-edit-field">
                            <input name="" class="r-textbox at easyui-validatebox"
                                type="text" required missingMessage="请输入用户名" /> <label class="r-need">*</label>
                        </div>
                    </li>
                    <li>
                        <div class="r-edit-label">姓名：</div>
                        <div class="r-edit-field">
                            <input name="" class="r-textbox at easyui-validatebox"
                                type="text" required missingMessage="请输入用户名" /> <label class="r-need">*</label>
                        </div>
                    </li>
                    <li>
                        <div class="r-edit-label">身份证：</div>
                        <div class="r-edit-field">
                            <input name="" class="r-textbox at easyui-validatebox"
                                type="text" validtype ="idcard" required missingMessage="请输入身份证号" /> <label class="r-need">*</label>
                        </div>
                    </li>
                   <li>
                        <div class="r-edit-label">公司地址：</div>
                        <div class="r-edit-field">
                            <input name=""
                                class="r-textbox2 r-update easyui-validatebox" type="text" 
                                required missingMessage="请输入地址" /> <label class="r-need">*</label>
                        </div>
                    </li>
                    
                   
                    <li>
                        <div class="r-edit-label">联系手机：</div>
                        <div class="r-edit-field">
                            <input name="" class="r-textbox at easyui-validatebox" validtype ="mobile"
                                type="text" required missingMessage="请输入联系手机" /> <label
                                class="r-need">*请填写真实手机</label>
                        </div>
                    </li>
                    
                    <li>
                        <div class="r-edit-label"></div>
                        <div class="r-edit-field">
                            <a class="loadpass"><input id="tdb" type="checkbox" /><label
                                for="td">我已阅读并同意</label><label class="text_underline">（小猫电商注册协议）</label></a>
                        </div>
                    </li>
                    <li>
                        <div class="r-edit-label"></div>
                        <div class="r-edit-field">
                            <span class="span_style"><input type="submit" value="Submit" style="display: none;"
                                id="" /></span><span class="span_style"> <a class="r-submit fw" id=""></a></span>
                                <span class="span_style"><a href="login.jsp">返回登录</a></span>
                        </div>
                    </li>
                </ul>
            </form>
			<!--个人注册 信息-->
			<form action="/ecs/user/reg.do" id="user-info" method="post"
				novalidate>
				<ul class="g-information">
					<li>
						<div class="r-edit-label">用户名：</div>
						<div class="r-edit-field">
							<input name="userName"  id="user_Name" class="r-textbox at easyui-validatebox"
								type="text"  required missingMessage="请输入用户名"/> <span class="get_span"><label class="g-label">*</label></span>
						</div>
					</li>
					<li>
						<div class="r-edit-label">手机号码：</div>
						<div class="r-edit-field">
							<input name="mobileNumber" id="mobileNumber" class="r-textbox at easyui-validatebox"
								type="text" validtype ="mobile" required missingMessage="请输入手机号码"/> <span class="get_span"><label
								class="g-label">*</label></span>
						</div>
					</li>
					<li>
						<div class="r-edit-label">验证码：</div>
						<div class="r-edit-field">
							<input name="imgCode" id="userRegImgCode"
								class="k-input field-input3 at easyui-validatebox" type="text" required style="_height:30px;_margin-left:-5px;"
								missingMessage="请输入验证码" deltaX="100"/> <a class="get_button" style="_height:30px;_background:none;_margin-left:0px;"><img  id="randomcode" src="/ecs/user/img.do" onclick="changeImage();">
								</a> 
						</div>
					</li>
					       <script type="text/javascript">
                                                                  
                                    function changeImage(){
                                         $("#randomcode").attr("src", "/ecs/user/img.do?_id=" + +Math.random());
                                    }
                          </script>
					
	
					<li>
						<div class="r-edit-label">密码：</div>
						<div class="r-edit-field">
							<input name="password"  id="userpassword" autocomplete="off" onfocus="this.type='password'" class="r-textbox at easyui-validatebox"
								type="password" validType="username" required="true" missingMessage="请输入密码" />
								<span class="get_span"><label class="g-label">*</label></span>
						</div>
					</li>
					<li>
						<div class="r-edit-label">确认密码：</div>
						<div class="r-edit-field">
							<input name="userpasswordConfirm" autocomplete="off" onfocus="this.type='password'"
								class="r-textbox at easyui-validatebox" type="password" required="true"
								missingMessage="请再次输入密码"  validType="pwdEquals['#userpassword']"/>
								<span class="get_span"><label class="g-label">*</label></span>
						</div>
					</li>
					<li>
						<div class="r-edit-label"></div>
						<div class="r-edit-field">
							<div class="loadpass"><input id="tds" class="easyui-validatebox" type="checkbox" required="true"
                                missingMessage="请勾选服务协议"/><label for="tds">我已阅读并同意</label><a href="/pages/agree/user_agree.html" target="_Blank" class="text_underline">（小猫电商注册协议）</a></div>
						</div>
					</li>
					<li>
						<div class="r-edit-label"></div>
						<div class="r-edit-field">
							<span class="span_style" style="_width:150px;"><a class="r-submit fw" id="user-submit"></a></span>
							<span class="span_style" style="_width:150px;"><a class="r-submit rt" href="login.jsp"></a></span>
							<span class="span_style" style="_width:150px;"><input type="submit" value="Submit" style="display: none;"id="user-submit-button" /></span>
						</div>
					</li>
				</ul>
			</form>
		</div>
		<div class="context_bottom"></div>
		<div class="login_bottom">沪ICP备13039322号</div>
		
	</div>
	<script type="text/javascript">
	    <!--  此处样式修改放在此处才有效，勿放入JS文件-->
		$(document).ready(function() {
			loading_css();
			if(navigator.userAgent.indexOf('Firefox') >= 0)
            {
                $(".combo-text").css({"float":"left","width":"100px","height":"35px","-moz-height":"34px","-moz-line-height":"34px;","background":"url(resources/images/select_left_back.jpg) no-repeat","color":"#fff","line-height":"30px"});
            }else{
                $(".combo-text").css({"width":"100px","height":"35px","-moz-height":"34px","-moz-line-height":"34px;","background":"url(resources/images/select_left_back.jpg) no-repeat","color":"#fff","line-height":"30px"});
            }
		    $(".r-margin-left").find(".combo").css({"width":"194px","height":"30px","line-height":"29px","_width":"192px","_height":"30px","background":"url(resources/images/login_icon.png) no-repeat 0px -340px"});
		    $(".r-margin-left").find(".combo-text").css({"width":"162px","height":"30px","line-height":"29px","_width":"192px","_height":"26px","background":"none","color":"#000","margin-top":"3px"});
		    $(".r-margin-left").find(".combo-arrow").css({"width":"28px","height":"30px","line-height":"29px","_width":"192px","_height":"30px","background":"url(resources/images/select_right_back.jpg) no-repeat"});
		    $(".r-select-style").find(".combo-text").css({"width":"164px","margin-top":"0px","background":"none"});
		    loading_province("c_s_province","c_s_city","c_s_county");
		    loading_province("s_s_province","s_s_city","s_s_county");
		    
		    checkSpServiceType();
		    checkMfcServiceType();
		    
	    });

		
        $.extend($.fn.validatebox.defaults.rules, {
            pwdEquals: {
                validator: function(value,param){
                    return value == $(param[0]).val();
                },
                message: '密码不匹配'
            }
        });
	</script>
</body>
</html>