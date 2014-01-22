<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="resources/js/ecommerce.js"></script>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
</head>
<body>
    <div class="line_clear"></div>
    <div class="public_title">
        <div class="public_title_icon">​</div>​
        <label class="public_title_text">权限管理</label>
    </div>
    <div class="line_clear"></div>
	
		<div title="权限设置" style="padding: 10px;margin-left:30px;">
			<button class="btn_add" onclick="openAddGroupWindow();">新增权限</button>
			<div class="line_clear"></div>
			<div>
               <span class="span_style">“<img height="16" width="16" src="/resources/images/table_edit.png" />”</span>
               <span class="span_style">代表编辑</span>
            </div>
			<table class="easyui-datagrid_tf" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true" id="groupList"  url="/ecs/sys/group/list.do" iconCls="icon-save" sortOrder="asc"  pagination="true"  singleSelect="true" data-options="onClickRow: onGroupPermissionClickRow">
	        <thead>
	            <tr>
	                <th align="center"  field="groupName"  sortable="false" width='200' align="center" resizable="true">权限名称</th>
	                <th align="center"  field="description"  sortable="false" width='200' align="center" resizable="true">描述</th>
	                <th data-options="field:'id',formatter:formatterGroupOperation" width='200' align="center" resizable="true">操作</th>
	            </tr>
	        </thead>
	         <tbody>
                    <tr>
                       <td>管理员</td>
                       <td>系统管理员</td>
                       <td><button onclick="openAddGroupWindow();">编辑</button></td>
                    </tr>
                </tbody>
	       </table>
		</div>


<div style="display:none;">
	<div id="addRoleGroup" class="easyui-window" title="编辑权限信息" data-options="modal:true,closed:true,maximizable:false,minimizable:false,collapsible:false,iconCls:'icon-save'" style="width:650px;height:auto;padding:10px;">
			<form action="" id="addRoleGroupForm" method="post" novalidate>
					<input  id="permissionsId" name="id" type="hidden" />
					<input  id="permissions" name="permissions" type="hidden" />
					<table>
						<tbody>
							<tr>
								<td><label for="groupName"> 权限名字 </label>:</td>
								<td><input class="ui-widget-content ui-corner-all ui-input r-textbox easyui-validatebox" id="groupName" name="groupName" size="55" required missingMessage="请输入名称"/></td>
							</tr>
							<tr>
								<td><label for="permissionsel">权限 </label>:</td>
								<td>
								    <div class="r-margin-left">
								       <select id="permissionsel" class="easyui-combotree" data-options="url:'/pages/admin/user/roles.json',method:'post',width:128,height:30" multiple></select>
								    </div>
                                </td>
							</tr>
						
							<tr>
								<td><label for="description"> 描述 </label>:</td>
								<td><textarea rows="15" cols="60" id="description" name="description"></textarea></td>
							</tr>
						
							<tr><td><label>&nbsp;</label></td><td><input class="r-submit" type="submit" value="保存"  id="submit-button"/> </td></tr>
						</tbody>
					</table>
			</form>
	</div>
	
	
	</div>
	
	<script type="text/javascript">
	$(document).ready(function(){
		loading_css();
	});
	function openAddGroupWindow(){
		$("#addRoleGroupForm").form("clear");
		$('#addRoleGroup').window('setTitle', "新增权限组");
	      openDialog("addRoleGroup");
	}
	 
	function formatterGroupOperation(val, row){
		return '<button class="table_eidt" onclick=editRoleGroups("' + row.id + '");></button>';
	}
	
	function editRoleGroups(id){
		var rows = $("#groupList").datagrid('getRows');
		$("#addRoleGroupForm").form("clear");
		for(var i=0;i<rows.length;i++){
	    	if(rows[i].id==id){
	    		$("#addRoleGroupForm").form('load', rows[i]);
	    		var arr = rows[i].permissions.split(",");

	    		$('#permissionsel').combotree('setValues',arr);

	    		break;
	    	}
	    }
		
		//$('#addRoleGroup').window('open');
		openDialog("addRoleGroup");
		
	}

	  
	   $("#addRoleGroupForm").form({
	        url : '/ecs/sys/group/add.do',
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
	    
	</script>
</body>
</html>