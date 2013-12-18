<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resources/js/admin/admin.js"></script>
<script type="text/javascript" src="/resources/js/city-price.public.js"></script>
</head>
<body>
	<div id="groupTab">
		
		<div title="权限组设置" style="padding: 10px">
			<button class="btn_add" onclick="openAddGroupWindow();">新增权限组</button>
			<table class="easyui-datagrid_tf" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true" id="groupList"  url="/ecs/sys/cfg/role/group/list.do" iconCls="icon-save" sortOrder="asc"  pagination="true"  singleSelect="true" data-options="onClickRow: onGroupPermissionClickRow">
	        <thead>
	            <tr>
	                <th align="center"  field="groupName"  sortable="false" width='200' align="center" resizable="true">权限组</th>
	                <th align="center"  field="description"  sortable="false" width='200' align="center" resizable="true">描述</th>
	                <th data-options="field:'id',formatter:formatterGroupOperation" width='200' align="center" resizable="true">操作</th>
	                
	            </tr>
	        </thead>
	       </table>
		</div>
		<div title="用户权限设置" style="padding: 10px">
			<table class="" id="userList"  url="/ecs/sys/cfg/role/bakend/user/list.do" 
		         iconCls="icon-save" sortOrder="asc"  pagination="true"  singleSelect="true"  data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true">
		        <thead>
		            <tr>
		                <th align="center"  field="userName" width='200' align="center" sortable="false" resizable="true">用户名</th>
		                <th align="center"  field="groupName" width='200' align="center" sortable="false">权限组</th>
		                <th data-options="field:'id',formatter:formatterUserGroupOperation" width='200' align="center">操作</th>
		                
		            </tr>
		        </thead>
		       </table>
		</div>
	</div>

<div style="display:none;">
	<div id="addRoleGroup" class="easyui-window" title="编辑权限组" data-options="modal:true,closed:true,maximizable:false,minimizable:false,collapsible:false,iconCls:'icon-save'" style="width:650px;height:auto;padding:10px;">
			<form action="" id="addRoleGroupForm" method="post" novalidate>
					<input  id="permissionsId" name="id" type="hidden" />
					<input  id="permissions" name="permissions" type="hidden" />
					<table>
						<tbody>
							<tr>
								<td><label for="groupName"> 权限组名字 </label>:</td>
								<td><input class="ui-widget-content ui-corner-all ui-input tabs_input_css easyui-validatebox" id="groupName" name="groupName" size="55" required missingMessage="请输入名称"/></td>
							</tr>
							<tr>
								<td><label for="permissionsel">权限 </label>:</td>
								<td>
								    <div class="r-margin-left">
								       <select id="permissionsel" class="easyui-combotree" data-options="url:'/pages/admin/user/roles.json',method:'post',width:194,height:32" multiple style="width:128px;"></select>
								    </div>
                                </td>
							</tr>
							<tr>
                                <td><label for="indexPageCom"> 首页设置 </label>:</td>
                                <td> <input class="easyui-combobox" id="indexPageCom" name="indexPage" data-options="valueField:'indexPage',textField:'text',multiple:false,width:128,height:35"></td>
                            </tr>
							<tr>
								<td><label for="description"> 描述 </label>:</td>
								<td><textarea rows="15" cols="60" id="description" name="description"></textarea></td>
							</tr>
						
							<tr><td><input class="btn_blue" type="submit" value="保存"  id="submit-button"/> </td></tr>
						</tbody>
					</table>
			</form>
	</div>
	
	<div id="editUserRoleGroup" class="easyui-window" title="编辑用户权限" data-options="modal:true,closed:true,maximizable:false,minimizable:false,collapsible:false,iconCls:'icon-save'" style="width:650px;height:450px;padding:10px;">
			<form action="/ecs/sys/cfg/user/group/update.do" id="editUserRoleGroupForm" method="post" novalidate>
					<input  id="userId" name="id" type="hidden" />
					<input  id="groupId" name="groupId" type="hidden" />
					<table>
						<tbody>
							
							<tr>
								<td><label for="groupIdSel"> 权限组 </label>:</td>
								<td><select id="groupIdSel"  class="easyui-combobox easyui-validatebox" required missingMessage="请选择权限组" data-options="
							                    valueField:'id',
							                    textField:'groupName',
							                    url:'/ecs/sys/cfg/role/group/select.do',
							                    panelHeight:'auto',
							                    method:'post',
							                    width:128,
							                    height:35,
							                    multiple:false,
							                    loadFilter:function(data){
							                    	return data.rows;
							                    }"
								multiple style="width:200px;"></select></td>
							</tr>					
							<tr><td><input class="btn_blue" type="submit" value="保存"  id="submit-button"/> </td></tr>
						</tbody>
					</table>
			</form>
	</div>
	</div>
	
	<script type="text/javascript">
	 $('#groupTab').tabs({
 	    border:false,
 	    onSelect:function(title){
 	    	if(title=="权限组设置"){
 	    	     $('#groupList').datagrid({
 	    	         url:'/ecs/sys/cfg/role/group/select.do'
 	    	     });
 	
 	    	}else if(title =="用户权限设置"){
 	    		 $('#userList').datagrid({
                     url:'/ecs/sys/cfg/role/bakend/user/list.do'
               });  
 	    	}
 	    }
 	});
	 
	  $("#editUserRoleGroupForm").form({
	        url : '/ecs/sys/cfg/user/group/update.do',
	        onSubmit : function() {
	            $("#groupId").val($('#groupIdSel').combobox('getValues'));
	            return $(this).form('validate');
	        },
	        success : function(data) {
	            dealMessageWithCallBack(data, "编辑用户权限组", function(){
	                $('#editUserRoleGroup').window('close');
	                $("#userList").datagrid("reload");
	            });
	        }
	    });
	  
	   $("#addRoleGroupForm").form({
	        url : '/ecs/sys/cfg/role/group/add.do',
	        onSubmit : function() {
	            $("#permissions").val($('#permissionsel').combotree('getValues'));
	            return $(this).form('validate');
	        },
	        success : function(data) {
	            dealMessageWithCallBack(data, "添加权限组", function(){
	                $('#addRoleGroup').window('close');
	                $("#groupList").datagrid("reload");
	            });
	        }
	    });
	    $(document).ready(function(){
	    	
	    	var roles_data = undefined;       
	    	  postAjaxRequest("/pages/admin/user/roles.json?_id=xxx", {}, function(data) {
	    		  roles_data = data;
	    	    })
	    	$('#permissionsel').combotree({
	    		onCheck: function(param){
	    	      var oldINdexPage =  $('#indexPageCom').combobox('getValue');
	    	      var selectRoles = $('#permissionsel').combotree("getValues");
	    	      var indexPages = Array();
	    	       for(role in roles_data){
	    	    	   for(sel in selectRoles){
	    	    		   if(roles_data[role].id == selectRoles[sel]){
	    	    			   indexPages.push(roles_data[role]);
	    	    			   break;
	    	    		   }
	    	    	   }
	    	       }
	    	       $('#indexPageCom').combobox("loadData", indexPages);
	    	       $('#indexPageCom').combobox('clear');
	    	       if(indexPages[0]){
	    	    	   var find = false;
	    	    	   for(index in indexPages){
	    	    		   if(indexPages.indexpage == oldINdexPage){
	    	    			   find = true;
	    	    			   break;
	    	    		   }
	    	    	   }
	    	    	   
	    	    	   if(find){
	    	    	   	   $('#indexPageCom').combobox('select', oldINdexPage);
	    	    	   }else{
	    	    		   $('#indexPageCom').combobox('select', indexPages[0].indexPage);
	    	    	   }
	    	    	   
	    	       }
	    	    }
	    	});
	    	
	    });
	</script>
</body>
</html>