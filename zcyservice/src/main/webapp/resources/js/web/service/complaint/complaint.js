$(document).ready(function() {
	
});
var customerMobilePhone;
function searchSObycphone(){
	var cphone = $("#customerPhone").val();
	customerMobilePhone = cphone;
	var param1 = {"mobilePhone":cphone};
	var param2 = {"mobilePhone":cphone,"listType":2};
	$('#solist').datagrid('load',param1);
	$('#customercomplaintlist').datagrid('load',param2);
}

var soid;
var mpNo;
function operating(){
	var row = $('#solist').datagrid('getSelected');
    if (row){
    	soid = row.id;
    	operWindowOpen(row);
    }else{
    	alert("请选择一条记录！");
    	return;
    }
}

function operatingInMyComps(){
	var row = $('#mycomplaintlist').datagrid('getSelected');
    if (row){
    	operWindowOpen(row);
    }else{
    	alert("请选择一条记录！");
    	return;
    }
}

function addOrEditComOperating(val, row){
	var compStatus = row.complaintStatus;
	var compId = row.complaintId;
	var compType = row.complaintType;
	var compRemark = row.complaintRemark;
	var ctName = row.complaintTypeName;
	var soId = row.serviceOrderId;
	var command = '编辑投诉';
	if(compId == null || compId  == 'undefined'){
		command = '添加投诉';
	}
	var data = '"' + compStatus + '"' + ',' + '"' + compId + '"' + ',' + '"' + compType + '"' + ',' + '"' + compRemark + '"' + ',' + '"' + ctName + '"' + ',' + '"' + soId + '"';
	return '<button onclick=\'operWindowOpen2(' + data + ');\'>' + command + '</button>';
}

function operWindowOpen2(compStatus, compId, compType, compRemark, ctName, soId){
	//var compStatus = row.complaintStatus;
	if(soId != null && soId != 'undefined'){
		soid = soId;
	}else{
		soid = null;
	}
	if(compStatus == "CLOSED"){
		$("#compType").combobox('disable');
		$("#compStatus").combobox('disable');
		$('#saveCompOperBtn').linkbutton('disable');
	}else{
		$("#compType").combobox('enable');
		$("#compStatus").combobox('enable');
		$('#saveCompOperBtn').linkbutton('enable');
	}
	
	//清空form
	$("#compType").combobox('setValue',null);
	$("#compStatus").combobox('setValue', 'NEW');
	$("#compRemark").val("");
	$("#compId").val(null);
	$('#operComplaintWindow').window('setTitle','投诉-新增');
	
	//var compId = row.complaintId;
	//var compType = row.complaintType;
	//var compRemark = row.complaintRemark;
	if(compId != null && compId != 'undefined'){
		$("#compId").val(compId);
		var data = $("#compType").combobox('getData');
		var delFlag = true;
		for(var i=0; i<data.length; i++){
			if(data[i].id == compType){
				delFlag = false;
				break;
			}
		}
		if (delFlag){
			var add = {"id":compType,"name":ctName}
			data.push(add);
		}
		$("#compType").combobox('setValue',compType);
		$("#compStatus").combobox('setValue', compStatus);
		$("#compRemark").val(compRemark);
		$('#operComplaintWindow').window('setTitle','投诉-编辑');
	}
	//$('#operComplaintWindow').window('open');
	openDialog("operComplaintWindow");
}

function operWindowOpen(row){
	var compStatus = row.complaintStatus;
	if(compStatus == "CLOSED"){
		$("#compType").combobox('disable');
		$("#compStatus").combobox('disable');
		$('#saveCompOperBtn').linkbutton('disable');
	}else{
		$("#compType").combobox('enable');
		$("#compStatus").combobox('enable');
		$('#saveCompOperBtn').linkbutton('enable');
	}
	
	//清空form
	$("#compType").combobox('setValue',null);
	$("#compStatus").combobox('setValue', 'NEW');
	$("#compRemark").val("");
	$("#compId").val(null);
	
	var compId = row.complaintId;
	var compType = row.complaintType;
	var compRemark = row.complaintRemark;
	if(compId != null){
		$("#compId").val(compId);
		var data = $("#compType").combobox('getData');
		var delFlag = true;
		for(var i=0; i<data.length; i++){
			if(data[i].id == compType){
				delFlag = false;
				break;
			}
		}
		if (delFlag){
			var add = {"id":compType,"name":row.complaintTypeName}
			data.push(add);
		}
		$("#compType").combobox('setValue',compType);
		$("#compStatus").combobox('setValue', compStatus);
		$("#compRemark").val(compRemark);
	}
	//$('#operComplaintWindow').window('open');
	openDialog("operComplaintWindow");
}

function submitForm(){
	
	var cstatus = $("#compStatus").combobox('getValue');
	
	var ctype = $("#compType").combobox('getValue');
	if (ctype==null || ctype == ""){
		alert("投诉类型不能为空！");
		return;
	}
	
	var cremark = $("#compRemark").val();
	var id = $("#compId").val();
	var data = {"id":id,"compTypeId":ctype,"compStatus":cstatus,"serviceOrderId":soid,"compRemark":cremark};
	postAjaxRequest("/ecs/complaint/add.do", data, callback); 
}

function callback(){
	$('#operComplaintWindow').window('close');
//	$('#customercomplaintlist').datagrid('reload');
//	$('#allcomplaintlist').datagrid('reload');
//	$('#mycomplaintlist').datagrid('reload');
	$('#solist').datagrid('reload');
}