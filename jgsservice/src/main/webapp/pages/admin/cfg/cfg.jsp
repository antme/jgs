<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//Dli HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dli">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>价格</title>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resources/js/admin/admin.js"></script>
<script type="text/javascript" src="resources/js/validation.js"></script>


</head>
<body>
	<div class="easyui-tabs">
		<div title="系统设置" style="padding: 10px">
		<div class="p_height_div"></div>
			<div class="easyui-tabs">
				<div title="短信设置" style="padding: 10px">
				  <form action="/ecs/sys/cfg/add.do"  method="post" novalidate class="sys-form" >
							<label class="user_info_label">禁用短信：</label> 
									    <input type="radio" name="smsDisabled" id="smsDisabled_yes" value="yes" class="sex_input"/> 
									    <label >是</label> 
									    <input type="radio" name="smsDisabled" value="no" id="smsDisabled_no" class="sex_input"/>
							<label >否</label>
							<br />
					<input type="submit" value="保存"/>
				  </form>
				</div>	
				<div title="服务商等级分数" style="padding: 10px">
				  <form action="/ecs/sys/cfg/add.do"  method="post" novalidate class="sys-form" >
					<div>等级分数设置</div>
					<label>1星</label><input type="text" name="level1" class="easyui-validatebox" required missingMessage="请设置分数"/><br>
		            <label>2星</label><input type="text" name="level2" class="easyui-validatebox" required missingMessage="请设置分数" /><br>
		            <label>3星</label><input type="text" name="level3" class="easyui-validatebox" required missingMessage="请设置分数"/><br>
		            <label>4星</label><input type="text" name="level4" class="easyui-validatebox" required missingMessage="请设置分数"/><br>
		            <label>5星</label><input type="text" name="level5" class="easyui-validatebox" required missingMessage="请设置分数"/><br>
		            <label>1钻</label><input type="text" name="level6" class="easyui-validatebox" required missingMessage="请设置分数"/><br>
		            <label>2钻</label><input type="text" name="level7" class="easyui-validatebox" required missingMessage="请设置分数"/><br>
		            <label>3钻</label><input type="text" name="level8" class="easyui-validatebox" required missingMessage="请设置分数"/><br>
		            <label>4钻</label><input type="text" name="level9" class="easyui-validatebox" required missingMessage="请设置分数"/><br>
		            <label>5钻</label><input type="text" name="level10" class="easyui-validatebox" required missingMessage="请设置分数"/><br>            
		            <label>皇冠</label><input type="text" name="level11" class="easyui-validatebox" required missingMessage="请设置分数"/><br>
					<br />
					 <input type="submit" value="保存"/>
		          </form>
				</div>	
				<div title="评价分数设置" style="padding: 10px">
		          <form action="/ecs/sys/cfg/add.do"  method="post" novalidate class="sys-form" >
		            <div>设置好评，差评，中评相应分数</div>
		            <label>好评:</label><input type="text" name="user_score_good" class="easyui-validatebox" validtype ="isNumber" required  missingMessage="请输入好评分数"/>
		            <label>中评:</label><input type="text" name="user_score_middle" class="easyui-validatebox" validtype ="isNumber"  required  missingMessage="请输中评分数"/>
		            <label>差评:</label><input type="text" name="user_score_bad" class="easyui-validatebox" validtype ="isNumber"  required  missingMessage="请输差评分数"/>
		            <br />
		             <input type="submit" value="保存"/>
		          </form>
		        </div>	
		        <div title="百度地图帐号" style="padding: 10px">
                  <form action="/ecs/sys/cfg/add.do"  method="post" novalidate class="sys-form" >
                        <div>保存前请先测试下地图key是否正确</div>
                        <label class="user_info_label">百度地图KEY：</label> 
                        <input type="text" name="baidu_map_key" id="baidu_map_key" class="easyui-validatebox" required  style="width:300px;" missingMessage="请输入百度地图访问key"/>
                    <button onclick="testBaiduMap(); return false;">测试</button><input type="submit" value="保存"/>
                  </form>
                  
                </div>
                <div title="短信通帐号设置" style="padding: 10px">
                     <form action="/ecs/sys/cfg/add.do"  method="post" novalidate class="sys-form" >
                        <div>保存前请先测试账号是否正确</div>
                        <label >企业ID：</label> 
                        <input type="text" name="sms_account_userid" id="sms_account_userid" class="easyui-validatebox" required  missingMessage="请输入企业ID"/>
                        <br>
                        <label >用户名：</label> 
                        <input type="text" name="sms_account_name" id="sms_account_name" class="easyui-validatebox" required  missingMessage="请输入账号"/>
                         <br>
                        <label >密码：</label> 
                        <input type="text" name="sms_account_password" id="sms_account_password" class="easyui-validatebox" required  missingMessage="请输入密码"/>
                         <br>
                    <button onclick="testSmsAccount(); return false;">测试</button><input type="submit" value="保存"/>
                  </form>
                   
                </div>        	
			</div>
		</div>
		<div title="订单匹配规则设置" style="padding: 10px">
		<div class="p_height_div"></div>
			<div class="easyui-tabs">
				<div title="服务商搜索距离" style="padding: 10px">
				  <form action="/ecs/sys/cfg/add.do"  method="post" novalidate class="sys-form" >
					<div>服务商搜索距离：（公里)</div>
					<label>距离</label><input type="text" name="firstCircle" class="easyui-validatebox" validtype ="number"  required missingMessage="请输入距离" /><br>
					<br />
					<input type="submit" value="保存"/>
				  </form>
				</div>
				<div title="安装前提醒时间" style="padding: 10px">
				  <form action="/ecs/sys/cfg/add.do"  method="post" novalidate class="sys-form" >
					<div>订单安装时间前x小时发短信通知服务商</div>
					<label>小时:</label><input type="text" name="hoursBeforinstallNotice" class="easyui-validatebox" validtype ="number"  required  missingMessage="请输入小时数"/>
					<br />
					 <input type="submit" value="保存"/>
		          </form>
				</div>
				<div title="权重设置" style="padding: 10px">
				  <form action="/ecs/sys/cfg/add.do"  method="post" novalidate class="sys-form" >
					<div>权重设置(%)</div>
					<label>距离</label><input type="text"  name="distanceRight" class="easyui-validatebox" required missingMessage="请输入距离权重"/><br>
		            <label>服务商评分等级</label><input type="text" name="scoreRight" class="easyui-validatebox" required missingMessage="请输入服务商评分权重"/>
					<br />
					 <input type="submit" value="保存"/>
		          </form>
				</div>
				
				
				<div title="默认好评时间" style="padding: 10px">
				  <form action="/ecs/sys/cfg/add.do"  method="post" novalidate class="sys-form" >
					<div>完工后X(天)</div>
					<label>天数</label><input type="text" name="orderEvaluationDefaultDays" class="easyui-validatebox" validtype ="number"  required missingMessage="请设置默认好评天数"/>
					<br />
					 <input type="submit" value="保存"/>
		          </form>
				</div>
				<div title="客户确认订单后X小时转人工" style="padding: 10px">
				  <form action="/ecs/sys/cfg/add.do"  method="post" novalidate class="sys-form" >
					<div>转人工X小时数</div>
					<label>小时</label><input type="text" name="orderToSpAfterConfirmedHours" class="easyui-validatebox" validtype ="number"  required missingMessage="请设置转人工小时数"/>
					<br />
					 <input type="submit" value="保存"/>
		          </form>
				</div>
				<div title="订单过滤关键字" style="padding: 10px">
				  <form action="/ecs/sys/cfg/add.do"  method="post" novalidate class="sys-form" >
					<div>导入模板中的备注，不需要安装关键字设置，关键字之间以逗号或者空格隔开</div>
					<span class="span_style"><label>关键字</label></span>
					<span class="span_style"><textarea rows="3" style="font-size:14px;" cols="50" name="order_import_ignore_keyword" class="easyui-validatebox" required missingMessage="请设置过滤关键字"></textarea></span>
					<br />
					 <input type="submit" value="保存"/>
		          </form>
				</div>		
			</div>
		</div>

	</div>
	
	<script type="text/javascript">
		$(document).ready(function() {
			loadSystemConfig();
		});
		
		$(".sys-form").form({
		    url : '/ecs/sys/cfg/add.do',
		    onSubmit : function() {
		        return $(this).form('validate');
		    },
		    success : function(data) {
		        dealMessageWithCallBack(data, "规则库管理", function(data){
		        	displayAlert("保存成功");
		        });
		    }
	   });
		
		function testBaiduMap(){
			
			postAjaxRequest("/ecs/sys/account/test.do", {
				baiduMapKey: $("#baidu_map_key").val()
	        }, function(data) {
	            alert("key正常，可以保存");
	        });
			
			
			return false;
		}
		
		function testSmsAccount(){
			var data = {
	                 "smsAccountUserid" : $("#sms_account_userid").val(),
	                 "smsAccountName" : $("#sms_account_name").val(),
	                 "smsAccountPassword" : $("#sms_account_password").val()
	               };
			 postAjaxRequest("/ecs/sys/account/test.do", data , function(data) {
	                alert("账号无异常,可以保存");
	         });
			 
			return false;
		}
		
	</script>
</body>
</html>