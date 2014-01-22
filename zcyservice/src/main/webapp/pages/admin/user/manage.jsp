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

 function userstatusformatter(val, row, rowindex){
	   var status = row.userStatus;
	   if (status == 'NORMAL'){
		   return "正常";
	   }else if (status == 'LOCKED'){
		   return "已冻结";
	   }else{
		   return "正常";
	   }
	} 

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
        <label class="public_title_text">用户账号管理</label>
    </div>
    <div class="line_clear"></div>
	<div class="public_search_div">
		<div class="line_clear"></div>
		<div class="line_seach">
			<span class="span_style"><label class="display_nones">关键字：</label></span>
			<span class="span_style"><input id="userKeyword" class="public_search_input_text display_nones" /></span> 
			<span class="span_style"><label class="display_nones pdf">状态：</label></span>
			<span class="span_style"> 
			     <select class="easyui-combobox display_nones" style="width:128px;height:30px;background:url(/resources/images/public_select.png) no-repeat;" data-options="multiple:false" id="userSearchStatus">
					<option value="" selected>用户状态</option>
					<option value="NORMAL">正常</option>
					<option value="LOCKED">已冻结</option>
			     </select>
			</span> 
			<span class="span_style" style="margin-left:10px;"><button class="public_search_btn display_nones" onclick="searchUser()"></button></span>
			<span class="span_style"><div style="margin-left:10px;color:red;font-size:12px;">关键字搜索：多个关键字之间请用空格隔开</div></span>
		</div>
		<div class="line_clear"></div>
		<button class="btn_add" onclick="openAdduserWindow();">新增用户</button>
		<div class="line_clear"></div>
	</div>
	<div style="margin-left:40px;">
         <span class="span_style">“<img height="16" width="16" src="/resources/images/table_edit.png" />”</span>
         <span class="span_style">代表编辑</span>
    </div>
	<div style="margin-left:40px;">
			<table id="userList"  class="easyui-datagrid_tf" iconCls="icon-save" url="/ecs/user/manage.do" sortOrder="asc" pagination="true" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true,width:900">
				<thead>
					<tr>
                        <th align="center"  field="userName"  width="100"  sortable="false">用户名</th>
                        <th align="center"  field="name"  width="100"  sortable="false">姓名</th>
                        <th align="center"  field="mobileNumber" width="100" sortable="false" >联系手机</th>
                        <th align="center"  field="email" width="120" sortable="false" >Email</th>
                        <th align="center"  field="createdOn" width="80" >创建日期</th>
                        <th align="center"  field="userStatus"  data-options="formatter:userstatusformatter" width="60">状态</th>
                        <th align="center" data-options="field:'id'" formatter="formatteruserEidt" width="150">操作</th>
                    </tr>
				</thead>				
			</table>
			</div>
	<div id="manage_form" style="display:none;">
	<div id="edituser" class="easyui-window" data-options="modal:true,closed:true,minimizable:false,maximizable:false,collapsible:false,iconCls:'icon-save',top:50" style="width:550px;height:auto;padding:10px; top:50px;">
		<form id="edituserform" action="" method="post">
			     <ul class="f-information">
					<li>
						<div class="r-edit-label">用户名：</div>
						<div class="r-edit-field cc">
							<input name="userName" id="userName" class="r-textbox easyui-validatebox"
								type="text" required missingMessage="请输入用户名"/> <label class="r-need">*</label>
						</div>
					</li>
					<li>
                        <div class="r-edit-label">密码：</div>
                        <div class="r-edit-field">
                            <input id="password" name="password" class="r-textbox easyui-validatebox"
                                type="password"  /> <label
                                class="r-need">*</label>
                        </div>
                    </li>
                    <li>
                        <div class="r-edit-label">确认密码：</div>
                        <div class="r-edit-field">
                            <input id="password2" name="password" class="r-textbox easyui-validatebox"
                                type="password" validType="pwdEquals['#password']" /> <label
                                class="r-need">*</label>
                        </div>
                    </li>
					<li>
						<div class="r-edit-label">联系手机：</div>
						<div class="r-edit-field">
							<input id="mobileNumber" name="mobileNumber" class="r-textbox easyui-validatebox"
								type="text" required missingMessage="请输入联系手机"  /> <label
								class="r-need">*</label>
						</div>
					</li>
					<li>
                        <div class="r-edit-label">Email：</div>
                        <div class="r-edit-field">
                            <input id="email" name="email" class="r-textbox easyui-validatebox"
                                required type="text"  /> 
                        </div>
                    </li>
                    <li>
                        <div class="r-edit-label">权限：</div>
                        <div class="r-edit-field">
                            <select id="groupIdSel"  name="groupId" class="easyui-combobox easyui-validatebox"  data-options="
							                    valueField:'id',
							                    textField:'groupName',
							                    panelHeight:'auto',
							                    method:'post',
							                    width:128,
							                    height:35,
							                    url:'/ecs/sys/group/select.do',
							                    multiple:false,
							                    loadFilter:function(data){
							                    	return data.rows;
							                    }"
								multiple="false" style="width:200px"></select><label
                                class="r-need">*</label>
                        </div>
                    </li>
					<li>
						<div class="r-edit-label">当前状态：</div>
						<div class="r-edit-field">
							<select class="easyui-combobox" style="width:128px"  data-options="multiple:false" id="selectUserStatus">
	                        	<option value="NORMAL" selected="selected">正常</option>
	                        	<option value="LOCKED" >已冻结</option>
	                        </select>
	                        <input id="userStatus" type="hidden" name="userStatus" />
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
	   $.extend($.fn.validatebox.defaults.rules, {
		        pwdEquals: {
		                    validator: function(value,param){
		                    return value == $(param[0]).val();
                },
                message: '密码不匹配'
        }
    });
  });
  function openAdduserWindow(){
	  $('#edituser').window('setTitle', "添加用户");
	  openDialog("edituser");
	  $("#edituserform").form('clear');
	  $("#rid").remove();
	  $("#userName").removeAttr("disabled");
	  $("#selectUserStatus").combobox('setValue',"NORMAL"); 
  }
  function eidtuser(){
	  $('#edituser').window('setTitle', "编辑用户");
      openDialog("edituser");
      $("#userName").attr("disabled","disabled");
  }
  
  function formatteruserEidt(val, row, rowindex){
	  return '<button class="table_eidt" onclick=getrecordWindow("'+ row.id +'") >&nbsp;</button>';
  }
  function getrecordWindow(id){
	  var data = $("#userList").datagrid('getData');
	  $("#edituserform").form('clear');
	  $("#edituserform").append("<input id='sid' name='id' type='hidden' value='"+id+"' />");
	  for(var i=0;i<data.rows.length;i++){
		  if(data.rows[i].id==id){
			  
			  
			  $("#edituserform").form('load',data.rows[i]);
			  $("#password").val("");
			  $("#password2").val("");
			  if(data.rows[i].userStatus){
			  	$("#selectUserStatus").combobox('setValue',data.rows[i].userStatus);
			  }else{
				  $("#selectUserStatus").combobox('setValue',"NORMAL"); 
			  }
			  eidtuser();
		  }
	  }
  }
  $("#btn_mfc_s").click(function(){
	  $("#userStatus").val($("#selectUserStatus").combobox('getValue'));
	  var ss=$("#groupIdSel").combobox('getValue');
	  if(ss=="" ||  ss==null){
		  $.messager.alert("信息","请选择用户权限！");
	  }else{
		  $("#mfc_info_sum").click();
	  }
  });
  $("#edituserform").form({
	  url : '/ecs/user/admin/add.do',
      onSubmit : function() {
          return $(this).form('validate');
      },
      success : function(data) {
    	  var info = eval('(' + data + ')');
          if($("#sid").val()==undefined){
        	  if(info.code!="200"){
        		  $.messager.alert("登录失败",info.msg);
        	  }else{
        		  $.messager.alert("添加用户","添加用户成功！");
        	  }
              
           }else{
        	   if(info.code!="200"){
                   $.messager.alert("登录失败",info.msg);
               }else{
            	   $.messager.alert("编辑用户","编辑用户成功！");
               }
           }
          
          $('#edituser').window('close');
          $("#userList").datagrid('reload');
      }
  });
  
  function searchUser(){
	  var data = {
			  "userStatus" : $("#userSearchStatus").combobox('getValue'),
			   "keyword" : $("#userKeyword").val()
	  }
	  
	  $("#userList").datagrid("reload", data);
  }
  </script>
</body>
</html>