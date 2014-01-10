<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<script type="text/javascript" src="resources/js/ecommerce.js"></script>
<script type="text/javascript" src="resources/js/public_css.js"></script>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<link href="/resources/css/easyui.css" rel="stylesheet"/>
<script type="text/javascript">
 function mfcAndSpstatusformatter(val, row, rowindex){
	   var status = row.userStatus;
	   if (status == 'NORMAL'){
		   return "正常";
	   }else if (status == 'LOCKED'){
		   return "已冻结";
	   }else{
		   return "";
	   }
	} 
 function userstatusformatter(val, row, rowindex){
	   var status = row.status;
	   if (status == 'NORMAL'){
		   return "正常";
	   }else if (status == 'LOCKED'){
		   return "已冻结";
	   }else{
		   return "";
	   }
	} 
 /* function workerstatusformatter(val, row, rowindex){
	   var status = row.isActive;
	   if (status){
		   return "正常";
	   }else{
		   return "已冻结";
	   }
	} */ 
 function genderformatter(val, row, rowindex){
	   var gender = row.sex;
	   if (gender == 'male'){
		   return "男";
	   }else if (gender == 'female'){
		   return "女";
	   }else{
		   return "";
	   }
	} 
</script>
</head>
<body>
    <div class="line_clear"></div>
    <div class="public_title">
        <div class="public_title_icon">​</div>​
        <label class="public_title_text">用户管理</label>
        <a class="public_title_btn" >
           <span class="span_style"><img src="/resources/images/public_btn_icon.png" class="btn_img" /></span>
           <span class="span_style">添加用户</span>
        </a>
    </div>
    <div class="line_clear"></div>
	<div class="public_search_div">
		<div class="line_clear"></div>
		<div class="line_seach">
			<span class="span_style"><label class="display_nones">关键字：</label></span>
			<span class="span_style"><input id="mfcKeyword" class="public_search_input_text display_nones" /></span> 
			<span class="span_style"><label class="display_nones pdf">状态：</label></span>
			<span class="span_style"> 
			     <select class="easyui-combobox display_nones" style="width:128px;height:30px;background:url(/resources/images/public_select.png) no-repeat;" data-options="multiple:false" id="mfcSearchStatus">
					<option value="" selected>用户状态</option>
					<option value="NORMAL">正常</option>
					<option value="LOCKED">已冻结</option>
			     </select>
			</span> 
			<span class="span_style"><button class="public_search_btn display_nones" onclick="mfcsearch()"></button></span>
		</div>
		<div class="line_clear"></div>
	</div>
	<div style="margin-left:40px;">
			<table id="newmfc"  class="easyui-datagrid_tab" iconCls="icon-save" sortOrder="asc" pagination="true" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true,width:900">
				<thead>
					<tr>
                        <th align="center"  field="mfcCode"  width="100"  sortable="false">系统编号</th>
                        <th align="center"  field="mfcStoreName"  width="100"  sortable="false">用户名</th>
                        <th align="center"  field="mfcCompanyName" width="100" sortable="false" >联系手机</th>
                        <th align="center"  field="mfcLocation" width="120" sortable="false" >Email</th>
                        <th align="center"  field="createdOn" width="80" data-options="formatter:showEstDateFormatter">创建日期</th>
                        <th align="center"  field="userStatus"  data-options="formatter:mfcAndSpstatusformatter" width="60">状态</th>
                        <th align="center" data-options="field:'id'" width="150">操作</th>
                    </tr>
				</thead>
				<tbody>
                    <tr>
                       <td>JG20223448112</td>
                       <td>sea.king</td>
                       <td>18221757291</td>
                       <td>11921209@qq.com</td>
                       <td>2013-12-25</td>
                       <td>正常</td>
                       <td><button onclick="eidtuser();">编辑</button></td>
                    </tr>
                </tbody>
			</table>
			</div>
	<div id="manage_form" style="display:none;">
	<div id="edituser" class="easyui-window" data-options="modal:true,closed:true,minimizable:false,maximizable:false,collapsible:false,iconCls:'icon-save',top:50" style="width:550px;height:auto;padding:10px; top:50px;">
		<form id="edituserform" action="" method="post">
			<input type="hidden" name ="id" id="mfcId"/>
			     <ul class="f-information">
					<li>
						<div class="r-edit-label">用户名：</div>
						<div class="r-edit-field cc">
							<input name="username" id="username" class="r-textbox easyui-validatebox"
								type="text" missingMessage="请输入店铺名称"/> <label class="r-need">*</label>
						</div>
					</li>
					<li class="passwd">
                        <div class="r-edit-label">密码：</div>
                        <div class="r-edit-field cc">
                            <input name="password" id="password" class="r-textbox easyui-validatebox"
                                type="text" missingMessage="请输入店铺名称"/> <label class="r-need">*</label>
                        </div>
                    </li>
					<li>
						<div class="r-edit-label">Email：</div>
						<div class="r-edit-field">
							<input id="email" name="email" class="r-textbox easyui-validatebox"
								required type="text" missingMessage="请输入公司名称" /> <label
								class="r-need">*</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">联系手机：</div>
						<div class="r-edit-field">
							<input id="ContactMobilePhone" name="ContactMobilePhone" class="r-textbox easyui-validatebox"
								type="text" required missingMessage="请输入联系手机" validtype ="mobile" /> <label
								class="r-need">*</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">当前状态：</div>
						<div class="r-edit-field">
							<select class="easyui-combobox" style="width:128px"  data-options="multiple:false" name="mfcStatus" id="mfcStatus">
	                        	<option value="NORMAL" selected>正常</option>
	                        	<option value="LOCKED" >已冻结</option>
	                        </select>
	                        <input name="" />
						</div>
					</li>
					<li>
					    <div class="r-edit-label"></div>
                        <div class="r-edit-field">
                            <button id="btn_mfc_s" type="button" class="r-submit fw">提交</button>
                            <input id="mfc_info_sum" style="display:none" type="submit" value="提交" />
                        </div>
					</li>
				</ul>
		</form>
	</div>
	
	
	</div>
	<script type="text/javascript">

  $(document).ready(function(){
	  loading_css();
	$(".display_nones").removeClass("display_nones");
	   $.extend($.fn.validatebox.defaults.rules, {
		        pwdEquals: {
		                    validator: function(value,param){
		                    return value == $(param[0]).val();
                },
                message: '密码不匹配'
        }
    });
	$('#newmfc').datagrid({url:'/ecs/mfc/manage.do'});
  });
  $(".public_title_btn").click(function(){
	  $('#edituser').window('setTitle', "添加用户");
	  openDialog("edituser");
  });
  function eidtuser(){
	  $('#edituser').window('setTitle', "编辑用户");
      openDialog("edituser");
      $("#mfcStoreName").val("sea.king");
      $("#mfcCompanyName").val("11921209@qq.com");
      $("#mfcContactMobilePhone").val("18221757291");
  }
  </script>
</body>
</html>