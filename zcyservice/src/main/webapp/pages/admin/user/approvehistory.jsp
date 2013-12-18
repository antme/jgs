<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>审核历史</title>
<link rel="stylesheet" type="text/css" href="/resources/css/easyui.css">
<link rel="stylesheet" type="text/css" href="/resources/css/public_class.css">
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>

<script type="text/javascript">
function roleformatter(val, row, rowindex){
	   var role = row.roleName;
	   if (role == 'SP'){
		   return "服务商";
	   }else if (role == 'MFC'){
		   return "厂商";
	   }else{
		   return "";
	   }
}
function applytypeformatter(val, row, rowindex){
	   var type = row.applyType;
	   if (type == 'NEW'){
		   return "新申请";
	   }else if (type == 'UPDATE'){
		   return "修改信息";
	   }else{
		   return "";
	   }
}
function approveresultformatter(val, row, rowindex){
	   var r = row.approveResult;
 	   if (r == 'APPROVED'){
		   return "批准";
	   }else if (r == 'REJECT'){
		   return "拒绝";
	   }else{
		   return "";
	   }
} 

function formatterLogData(val, row, rowindex){
     if(val && val!="" && val!=null){
    	return '<button <button class=\"button_color1\" onclick=viewDataDetail("' + row.id + '")>查看</button>';
     }
} 

function viewDataDetail(id){
	
	var rows=$("#approvehistorylist").datagrid('getRows');
    for(var i=0;i<rows.length;i++){
        if(rows[i].id==id){
        	if(rows[i].roleName == 'MFC'){
                var data = JSON.parse(rows[i].logData);

                $('#approveMfcForm').form('clear');
                $('#approveMfcForm').form('load', data);
                openDialog("approveMfc");
        	}else{
                var data = JSON.parse(rows[i].logData);

        		$('#approveSpForm').form('clear');
                $('#approveSpForm').form('load',data);
                $("#spLicenseNo").attr("src", data.spLicenseNo + '?' + Math.random());
                $("#spLicenseNoImg").attr("href", data.spLicenseNo + '?' + Math.random());
                $("#storeImage").attr("src", data.storeImage + '?' + Math.random());
                $("#storeImageShow").attr("href", data.storeImage + '?' + Math.random());
                openDialog("approveSp");
        	}
            break;
        }
    }
}
</script>
</head>
<body>
	<div>
		<h3>审核历史记录</h3>
	</div>
	<div>
		<table id="approvehistorylist" class="easyui-datagrid_tf" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true"
				url="/ecs/sp/admin/approve/history.do" iconCls="icon-save"  sortOrder="asc" pagination="true" singleSelect="true">
				<thead>
					<tr>
						<th align="center" sortable="false" resizable="true" width="150" field="userName"  >用户名</th>
						<th align="center" sortable="false" resizable="true" width="150" field="roleName" data-options="formatter:roleformatter"  >角色</th>
						<th align="center" sortable="false" resizable="true" width="120" field="applyTime"   >申请时间</th>
						<th align="center" sortable="false" resizable="true" width="80" field="applyType" data-options="formatter:applytypeformatter"  >申请类型</th>
						<th align="center" sortable="false" resizable="true" width="120" field="approveTime"  >审核时间</th>
						<th align="center" sortable="false" resizable="true" width="80" field="approveResult" data-options="formatter:approveresultformatter" >审核结果</th>
						<th align="center" sortable="false" resizable="true" width="100" field="logData" data-options="formatter:formatterLogData" >详情</th>
					</tr>
				</thead>
			</table>
	</div>
	
	
<div style="display:none;">

    <div id="approveMfc" class="easyui-window" title="厂商审核" data-options="modal:true,closed:true,minimizable:false,maximizable:false,collapsible:false,iconCls:'icon-save',top:50" style="width:600px;height:auto;padding:10px;">
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
                    
         
                
                </tbody>
            </table>
        </form>
    </div>  
    
    <div id="approveSp" class="easyui-window" title="服务商审核" data-options="modal:true,closed:true,minimizable:false,maximizable:false,collapsible:false,iconCls:'icon-save',top:50" style="width:700px;height:auto;padding:10px;top:80px;">
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
                
<!--                    <tr>
                        <td><div class="r-edit-label"><label for="spQQ"> 服务网点 ：</label></div></td>
                        <td><input class="ui-widget-content ui-corner-all ui-input" name="spBranchAddress" size="55" disabled/></td>
                    </tr> -->
                    <tr>
                        <td><div class="r-edit-label"><label for="spLicenseNo"> 营业执照：</label></div></td>
                        <td><a href="" target="_blank" id="spLicenseNoImg"><img src="" id="spLicenseNo" height="100" width="400"/></a></td>
                    </tr>
                    <tr>
                        <td><div class="r-edit-label"><label for="storeImage"> 店铺照片：</label></div></td>
                        <td><a href="" target="_blank" id="storeImageShow"><img src="" id="storeImage" height="100" width="400"/></a></td>
                    </tr>
               
                
                </tbody>
            </table>
        </form>
    </div>
</div>
</body>
</html>