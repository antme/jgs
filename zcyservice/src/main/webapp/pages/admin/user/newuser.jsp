<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resources/js/admin/admin.js"></script>
</head>
<body>

    <h3>新注册用户审核</h3>
	<div class="easyui-tabs" id="new_approve">
		<div title="厂商" style="padding: 10px" >
			<table id="newmfc"  iconCls="icon-save" class="easyui-datagrid_tab"  sortOrder="asc" pagination="true" singleSelect="true" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true">
				<thead>
					<tr>
						<th align="center"  field="mfcStoreName" width="150" sortable="false">店铺名称</th>
						<th align="center"  field="mfcCompanyName" width="150" sortable="false">公司名称</th>
						<th align="center"  field="createdOn" width="120" align="right" sortable="false" >创建时间</th>
						<th align="center"  field="mfcContactPerson" width="80" align="right" sortable="false">联系人</th>
						<th align="center"  field="mfcContactMobilePhone" width="100">联系人手机</th>
						<th data-options="field:'id',formatter:formatterMfcApproveOperation" width="150">操作</th>
					</tr>
				</thead>
			</table>
		</div>
		<div title="服务商" style="padding: 10px">
			<table id="newsp" iconCls="icon-save" class="easyui-datagrid_tab"  sortOrder="asc" pagination="true" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true">
				<thead>
					<tr>
						<th align="center"  field="spUserName" width="150" sortable="false">用户名</th>
						<th align="center"  field="spCompanyName" width="150" sortable="false">公司名称</th>
						<th align="center"  field="createdOn" width="120" align="right" sortable="false" >创建时间</th>
						<th align="center"  field="spContactPerson" width="80" align="right" sortable="false">联系人</th>
						<th align="center"  field="spContactMobilePhone" width="100">联系人手机</th>
						<th data-options="field:'id',formatter:formatterSpApproveOperation" width="150">操作</th>
					</tr>
				</thead>
			</table>
		</div>

	</div>

    <h3>用户信息修改审核</h3>
    <div class="easyui-tabs" id="update_approve">
        <div title="厂商" style="padding: 10px">
            <table id="mfcuplist" class="easyui-datagrid_tab"
                url="/ecs/mfc/listupdate.do" iconCls="icon-save"
                data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true" sortOrder="asc" pagination="true" singleSelect="true">
                <thead>
                    <tr>
                        <th align="center"  field="mfcStoreName" width="150" sortable="false">店铺名称</th>
                        <th align="center"  field="mfcCompanyName" width="150" sortable="false">公司名称</th>
                        <th align="center"  field="createdOn" width="120" align="right" sortable="false" >创建时间</th>
                        <th align="center"  field="mfcContactPerson" width="80" align="right" sortable="false">联系人</th>
                        <th align="center"  field="mfcContactMobilePhone" width="100">联系人手机</th>
                        <th data-options="field:'id',formatter:formatterMfcApproveOperation" width="150">操作</th>
                    </tr>
                </thead>
            </table>
        </div>
        <div title="服务商" style="padding: 10px">
            <table id="spuplist"  class="easyui-datagrid_tab"
                url="/ecs/sp/listupdate.do" iconCls="icon-save"
                data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true" sortOrder="asc" pagination="true">
                <thead>
                    <tr>
                        <th align="center"  field="spUserName" width="150" sortable="false">用户名</th>
                        <th align="center"  field="spCompanyName" width="150" sortable="false">公司名称</th>
                        <th align="center"  field="createdOn" width="120" align="right" sortable="false" >创建时间</th>
                        <th align="center"  field="spContactPerson" width="80" align="right" sortable="false">联系人</th>
                        <th align="center"  field="spContactMobilePhone" width="100">联系人手机</th>
                        <th data-options="field:'id',formatter:formatterSpApproveOperation" width="150">操作</th>
                    </tr>
                </thead>
            </table>
        </div>

    </div>

<div style="display:none;">

	<div id="approveMfc" class="easyui-window" title="厂商审核" data-options="modal:true,closed:true,maximizable:false,minimizable:false,collapsible:false,iconCls:'icon-save',top:50" style="width:600px;height:auto;padding:10px;">
		<input type="hidden" name ="id"/>
		<form action="" id="approveMfcForm">
		<input type="hidden" name ="id" id="mfcId"/>
			<table>
				<tbody>
					<tr>
						<td><div class="r-edit-label">店铺名称：</div></td>
						<td><input class="ui-widget-content ui-corner-all ui-input" name="mfcStoreName" size="55" disabled/></td>
					</tr>
					<tr>
						<td><div class="r-edit-label"><label for="mfcCompanyName"> 公司名称：</label></div></td>
						<td><input class="ui-widget-content ui-corner-all ui-input" name="mfcCompanyName" size="55" disabled/></td>
					</tr>
					<tr>
						<td><div class="r-edit-label">主营类型：</div></td>
						<td><input class="ui-widget-content ui-corner-all ui-input" name="mfcServiceTypeStr" size="55" disabled/></td>
					</tr>
					<tr>
					<td><div class="r-edit-label"><label for="mfcLocation"> 所在地 ：</label></div></td>
						<td><input class="ui-widget-content ui-corner-all ui-input" name="mfcLocation" size="55" disabled/></td>
					</tr>
					<tr>
						<td><div class="r-edit-label"><label for="mfcCompanyAdress"> 公司地址： </label></div></td>
						<td><input class="ui-widget-content ui-corner-all ui-input" name="mfcCompanyAdress" size="55" disabled/></td>
					</tr>
					<tr>
						<td><div class="r-edit-label"><label for="mfcContactPerson"> 联系人： </label></div></td>
						<td><input class="ui-widget-content ui-corner-all ui-input" name="mfcContactPerson" size="55" disabled/></td>
					</tr>
					<tr>
						<td><div class="r-edit-label"><label for="mfcContactPhone"> 固定电话： </label></div></td>
						<td><input class="ui-widget-content ui-corner-all ui-input" name="mfcContactPhone" size="55" disabled/></td>
					</tr>
					<tr>
						<td><div class="r-edit-label"><label for="mfcContactMobilePhone"> 手机：</label></div></td>
						<td><input class="ui-widget-content ui-corner-all ui-input" name="mfcContactMobilePhone" size="55" disabled/></td>
					</tr>
					<tr>
						<td><div class="r-edit-label"><label for="mfcQQ"> QQ：</label></div></td>
						<td><input class="ui-widget-content ui-corner-all ui-input" name="mfcQQ" size="55" disabled/></td>
					</tr>
					
				    <tr>
		                  <td><span class="r-edit-label span_style_top" style="line-height:15px;">拒绝原因：</span></td>
		                  <td><input class="ui-widget-content ui-corner-all ui-input easyui-validatebox" name="rejectReson" id="rejectReson" size="55" required missingMessage="拒绝原因"/> <div>拒绝原因将以短信(新注册用户)或者站内信(更新用户)形式发送给用户</div></td>
		            </tr>
					<tr>
					    <td></td>
						<td><div style="margin-top:5px;"><button onclick="approveMfc(); return false;">批准</button> <button onclick="rejectMfc(); return false;">拒绝</button> <button onclick="closeUserApproveWindow(); return false;">取消</button></div></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>	
	
	<div id="approveSp" class="easyui-window" title="服务商审核" data-options="modal:true,closed:true,maximizable:false,minimizable:false,collapsible:false,iconCls:'icon-save',top:50" style="width:700px;height:auto;padding:10px;top:80px;">
		<form action="" id="approveSpForm">
		   <input type="hidden" name ="id" id="spId"/>
			<table>
				<tbody>
					<tr>
						<td><div class="r-edit-label">用户名：</div></td>
						<td><input class="ui-widget-content ui-corner-all ui-input" name="spUserName" size="55" disabled/></td>
					</tr>
					<tr>
						<td><div class="r-edit-label">主营服务类型：</div></td>
						<td><input class="ui-widget-content ui-corner-all ui-input" name="spServiceTypeStr" size="55" disabled/></td>
					</tr>
					<tr>
                        <td><div class="r-edit-label">工人数：</div></td>
                        <td><input class="ui-widget-content ui-corner-all ui-input" name="spCompanySize" size="55" disabled/></td>
                    </tr>
					<tr>
						<td><div class="r-edit-label"><label for="spCompanyName"> 公司名称：</label></div></td>
						<td><input class="ui-widget-content ui-corner-all ui-input" name="spCompanyName" size="55" disabled/></td>
					</tr>
					<tr>
					<td><div class="r-edit-label"><label for="spLocation"> 所在地：</label></div></td>
						<td><input class="ui-widget-content ui-corner-all ui-input" name="spLocation" size="55" disabled/></td>
					</tr>
					<tr>
						<td><div class="r-edit-label"><label for="spCompanyAddress"> 公司地址：</label></div></td>
						<td><input class="ui-widget-content ui-corner-all ui-input" name="spCompanyAddress" size="55" disabled/></td>
					</tr>
					<tr>
						<td><div class="r-edit-label"><label for="spContactPerson"> 联系人： </label></div></td>
						<td><input class="ui-widget-content ui-corner-all ui-input" name="spContactPerson" size="55" disabled/></td>
					</tr>
					<tr>
						<td><div class="r-edit-label"><label for="spContactPhone"> 固定电话：</label></div></td>
						<td><input class="ui-widget-content ui-corner-all ui-input" name="spContactPhone" size="55" disabled/></td>
					</tr>
				     <tr>
                        <td><div class="r-edit-label"><label for="spContactMobilePhone"> 联系手机：</label></div></td>
                        <td><input class="ui-widget-content ui-corner-all ui-input" name="spContactMobilePhone" size="55" disabled/></td>
                    </tr>
					<tr>
						<td><div class="r-edit-label"><label for="spQQ"> QQ：</label></div></td>
						<td><input class="ui-widget-content ui-corner-all ui-input" name="spQQ" size="55" disabled/></td>
					</tr>
				
<!-- 					<tr>
						<td><div class="r-edit-label"><label for="spQQ"> 服务网点 ：</label></div></td>
						<td><input class="ui-widget-content ui-corner-all ui-input" name="spBranchAddress" size="55" disabled/></td>
					</tr> -->
					<tr>
						<td><div class="r-edit-label"><label for="spLicenseNo"> 营业执照：</label></div></td>
						<td><a href="" target="_blank" id="spLicenseNoImg"><img src="" id="spLicenseNo" height="150" width="400"/></a></td>
					</tr>
					<tr>
						<td><div class="r-edit-label"><label for="storeImage"> 店铺照片：</label></div></td>
						<td><a href="" target="_blank" id="storeImageShow"><img src="" id="storeImage" height="150" width="400"/></a></td>
					</tr>
				    <tr>
                          <td><div class="r-edit-label">拒绝原因：</div></td>
                          <td><input class="ui-widget-content ui-corner-all ui-input easyui-validatebox" id="rejectSpReson" name="rejectSpReson" size="55" /> <br>拒绝原因将以短信(新注册用户)或者站内信(更新用户)形式发送给用户</td>
                    </tr>
					<tr>
					    <td></td>
						<td><div style="margin-top:5px;"><button onclick="approveSp(); return false;">批准</button> <button onclick="rejectSp();return false;">拒绝</button> <button onclick="closeUserApproveWindow(); return false;">取消</button></div></td>
					</tr>
				</tbody>
			</table>
		</form>
	</div>
</div>
<%out.println(request.getParameter("tab")); %>
    <script type="text/javascript">
    $(document).ready(function(){
    	var new_approve_tab = $('#new_approve').tabs({
            border:false,
            onSelect:function(title){
                if(title=="厂商"){
                     $('#newmfc').datagrid({
                         url:'/ecs/mfc/listnew.do',
                         singleSelect:true
                     });
                }else if(title=="服务商"){     
                     $('#newsp').datagrid({
                         url:'/ecs/sp/listnew.do',
                         singleSelect:true
                   });    
                }
               
            }
        });
    	
     	var update_tab = $('#update_approve').tabs({
            border:false,
            onSelect:function(title){
                if(title=="厂商"){
                     $('#mfcuplist').datagrid({
                         url:'/ecs/mfc/listupdate.do',
                         singleSelect:true
                     });
                }else if(title=="服务商"){     
                     $('#spuplist').datagrid({
                         url:'/ecs/sp/listupdate.do',
                         singleSelect:true
                   });    
                }
               
            }
        });
     	
     	var tab = <%=request.getParameter("tab")%>;
     	if(tab=="1"){
     		$('#new_approve').tabs('select',1);
     		$('#update_approve').tabs('select',1);
     	}
     	
    });
    </script>
</body>
</html>