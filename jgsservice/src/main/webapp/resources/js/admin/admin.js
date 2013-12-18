//拆单页面服务商数据
var tempSpData = undefined;

function formatterSpApproveOperation(val, row) {
	return '<button onclick=openApproveSpUsers("' + row.id + '");>审核</button>';
}

function formatterMfcApproveOperation(val, row) {
	return '<button onclick=openApproveMfcUsers("' + row.id + '");>审核</button>';
}


function formatterRejectedStatus(val, row){
	
	return "人工处理";
}

function formatterBillOrderStatus(val, row) {

	if (val == "DONE") {
		return "未结算";
	}
	return "已结算";
}

function formatterMfcBillStatus(val, row) {

	if (row.billStatus == "SP_DONE") {
		return "未结算";
	}
	return "已结算";
}

function formatterSoRejectReson(val, row){
	
	if(row.terminateReason){
		return "[无法完工]:" + row.terminateReason;
	}
	
	return "[服务商拒绝]:" + val;
	
}

function formatterSoRejectRemark(val, row){
	
	
	if(row.terminateReason){
		return row.terminateRemark;
	}
	
	return val;
}



function formatterSysRejectedOrderOperation(val, row){
	
	return '<button onclick=openSplitOrdersPage("' + row.id + '");>订单处理</button>';

}

function formatterUserRejectedOrderOperation(val, row){
	
	return '<button onclick=openChangeSppage("' + row.id + '");>选择服务商</button>';

}


function formatterOrder24NoticeOperation(val, row){
	return '<button onclick=cancelOrderNotice("' + row.id + '",' + '"order24List");>取消提醒</button>';
}

function formatterOrder48NoticeOperation(val, row){
	return '<button onclick=cancelOrderNotice("' + row.id + '",' + '"order48List");>取消提醒</button>';
}

/********************权限相关**************/
var groupRole;


function onGroupPermissionClickRow(index, row){
	groupRole = row;
}
function formatterGroupOperation(val, row){
	return '<button onclick=editRoleGroups("' + row.id + '");>编辑</button>';
}


function formatterUserGroupOperation(val, row){
	return '<button onclick=openEditUserRoleGroupWindow("' + row.id + '");>设置权限</button>';
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

function openEditMenuRoleGroupWindow(id){
	//$('#editMenuRoleGroup').window('open');
	openDialog("editMenuRoleGroup");
	$('#menuGroupIdSel').combobox('reload');
	$("#editMenuRoleGroupForm").form({
		url : '/ecs/sys/cfg/menu/group/update.do',
		onSubmit : function() {
			$("#menuId").val(id);
			$("#menuGroupId").val($('#menuGroupIdSel').combobox('getValues'));
			return $(this).form('validate');
		},
		success : function(data) {
			dealMessageWithCallBack(data, "编辑用户权限组", function(){
				$('#editMenuRoleGroup').window('close');
				$("#menuList").datagrid("reload");
			});
		}
	});
}

function openEditUserRoleGroupWindow(id){
	var rows = $("#userList").datagrid('getRows');
	
	for(var i=0;i<rows.length;i++){
    	if(rows[i].id==id){
    		$("#editUserRoleGroupForm").form('load', rows[i]);
    		if(rows[i].groupId){
	    		var arr = rows[i].groupId.split(",");
	
	    		$('#groupIdSel').combobox('setValues',arr);
    		}else{
    			$('#groupIdSel').combobox('setValues',[]);
    		}

    		break;
    	}
    }


	//$('#editUserRoleGroup').window('open');
	openDialog("editUserRoleGroup");
	$('#groupIdSel').combobox('reload');

}

function openAddGroupWindow() {
	$("#addRoleGroupForm").form("clear");
	//$('#addRoleGroup').window('open');
	openDialog("addRoleGroup");

}



function openChangeSppage(id) {

	var rows = $("#orderList").datagrid('getRows');
	for(index in rows){
		if(rows[index].id == id){
			tempSpData = undefined;
			selectMfcOrder = rows[index];
			selectMfcOrder.plusPrice = 0;
			selectMfcOrder.spId = undefined;;
			break;
		}
		
	}
	$(".back_demo").show();
	$(".break_list").show();
	$(".select_mfc").show();
	
	tempSpData = undefined;
	$("#nfc_list").datagrid();
	postAjaxRequest("/ecs/sp/for/order/selectsp.do", {"id" : id}, function(data){
			tempSpData = data;
			$("#nfc_list").datagrid('loadData',data);

	});

	
}

function onUserRejectOrderClickRow(index, row){
	selectMfcOrder = row;
	selectMfcOrder.spId = undefined;

}

function onUserRejectOrderClickCell(rowIndex, field, value){
	var rows = $("#orderList").datagrid('getRows');
	selectMfcOrder = rows[rowIndex];
	selectMfcOrder.spId = undefined;;
	
	if(field == "id"){
		tempSpData = undefined;
		openChangeSppage(value);
	}
}


function openSplitOrdersPage(id){
	ordernumber=0;
	$(".back_demo").show();
	$(".break_list").show();
	$("#Break_list").datagrid();
    var rows=$("#orderList").datagrid('getRows');
    for(var i=0;i<rows.length;i++){
    	if(rows[i].id==id){

    		poGoodsNumber=rows[i].poGoodsNumber;
    		rowsvalue=rows[i];
    		orderIndex = i;
    		//清除以前数据
    		$("#Break_list").datagrid('loadData', []);
    		$("#Break_list").datagrid('appendRow', rows[i]);
    		break;
    	}
    }
    
    
}

function cancelProductOrder(id) {
	

	$("#comments").val("");
	var ids = getGridCheckedIds('orderList');
	if(ids.length == 0){		
		alert("请在列表中选择需要取消的订单数据，也可以点击标题左边的选择框全部选择本页数据！");
		return false;
	}else{
		
		$("#ordercomments").window("open");

	}
	
	
}

function addCancelProductOrdersComments() {
	if (confirm("确认取消订单 ?")) {
		var ids = getGridCheckedIds('orderList');
		var data = {
			"ids" : ids,
			comments : $("#comments").val()
		};

		postAjaxRequest("/ecs/order/pro/cancel.do", data, function(data) {
			$("#ordercomments").window("close");
			$('#orderList').datagrid('reload');
			displayInfoMsg("取消产品订单成功，可以在 订单查询 页面查看此订单");
			getTipInfos();
		});
	}

}

function cancelServiceOrder(id) {
	
	$("#comments").val("");
	var ids = getGridCheckedIds('orderList');
	if(ids.length == 0){		
		alert("请在列表中选择需要取消的订单数据，也可以点击标题左边的选择框全部选择本页数据！");
		return false;
	}else{
		
		$("#ordercomments").window("open");

	}
	

}

function addCancelServiceOrdersComments(){
	if (confirm("确认取消订单 ?")) {
		var ids = getGridCheckedIds('orderList');
		var data = {
			"ids" : ids,
			comments : $("#comments").val()
		};

		postAjaxRequest("/ecs/order/so/cancel.do", data, function(data) {
			$("#ordercomments").window("close");

			displayInfoMsg("取消服务订单成功，可以在 我的订单-》订单查询 页面查看此订单");
			$('#orderList').datagrid('reload');
			getTipInfos();
		});	
	}

}


function markOrderIsNoticed(id) {
	var ids = getGridCheckedIds(id);

	if (ids.length == 0) {
		alert("请在列表中选择需要标记的订单数据，也可以点击标题左边的选择框全部选择本页数据！");
		return false;
	} else {
		if (confirm("确认标记订单为已提醒 ?")) {

			var data = {
				"ids" : ids,
				comments : $("#comments").val()
			};

			postAjaxRequest("/ecs/order/so/isnoticed/marker.do", data, function(
					data) {
				$("#" + id).datagrid('reload');
				getTipInfos();
			});
		}
	}
}

function cancelOrderNoticeList(id) {
	var ids = getGridCheckedIds(id);

	if (ids.length == 0) {
		alert("请在列表中选择需要取消的订单数据，也可以点击标题左边的选择框全部选择本页数据！");
		return false;
	} else {
		if (confirm("确认取消提醒 ?取消提醒后订单将不会再出现在提醒列表")) {

			var data = {
				"ids" : ids,
				comments : $("#comments").val()
			};

			postAjaxRequest("/ecs/order/so/cancelNotice.do", data, function(
					data) {
				$("#" + id).datagrid('reload');
				getTipInfos();
			});
		}
	}

}

function openApproveMfcUsers(id) {
	postAjaxRequest("/ecs/mfc/admin/approve/get.do", {
		"id" : id
	}, function(data) {
		$('#approveMfcForm').form('clear');
		$('#approveMfcForm').form('load', data.data);
		//$('#approveMfc').window('open');
		openDialog("approveMfc");
	});
}

function openApproveSpUsers(id) {

	postAjaxRequest("/ecs/sp/admin/approve/get.do", {
		"id" : id
	}, function(data) {
		$('#approveSpForm').form('clear');
		$('#approveSpForm').form('load', data.data);
		$("#spLicenseNo").attr("src", data.data.spLicenseNo + '?' + Math.random());
		$("#spLicenseNoImg").attr("href", data.data.spLicenseNo + '?' + Math.random());
		$("#storeImage").attr("src", data.data.storeImage + '?' + Math.random());
		$("#storeImageShow").attr("href", data.data.storeImage + '?' + Math.random());
		//$('#approveSp').window('open');
		openDialog("approveSp");
	});
}


function approveSp(){
	var id = $("#spId").val();
	postAjaxRequest("/ecs/sp/approve.do", {
		"id" : id
	}, function(data) {
		$('#approveSp').window('close');
		$('#newsp').datagrid('reload');
		$('#spuplist').datagrid('reload');
		getTipInfos();
	});
}

function rejectSp(){
	if(!$("#rejectSpReson").val() || $("#rejectSpReson").val()==""){
		alert("请填写拒绝原因");
		return false;
	}
	var id = $("#spId").val();
	postAjaxRequest("/ecs/sp/reject.do", {
		"id" : id,
		"rejectReson" : $("#rejectSpReson").val()
	}, function(data) {
		$('#approveSp').window('close');
		$('#newsp').datagrid('reload');
		$('#spuplist').datagrid('reload');
		getTipInfos();
	});
}



function approveMfc(){
	var id = $("#mfcId").val();
	postAjaxRequest("/ecs/mfc/approve.do", {
		"id" : id
	}, function(data) {
		$('#approveMfc').window('close');
		$('#newmfc').datagrid('reload');
		$('#mfcuplist').datagrid('reload');
		getTipInfos();
	});
}

function rejectMfc(){
	if(!$("#rejectReson").val() || $("#rejectReson").val()==""){
		alert("请填写拒绝原因");
		return false;
	}
	var id = $("#mfcId").val();
	postAjaxRequest("/ecs/mfc/reject.do", {
		"id" : id,
		"rejectReson" : $("#rejectReson").val()
	}, function(data) {
		$('#approveMfc').window('close');
		$('#newmfc').datagrid('reload');
		$('#mfcuplist').datagrid('reload');
		getTipInfos();
	});
}


function closeUserApproveWindow(){
	$('#approveMfc').window('close');
	$('#approveSp').window('close');
}




/***************ADMIN MANAGE USER INFO***********************/

function formatterMfcEditOperation(val, row) {
	return '<button onclick=openEditMfcUsers("' + row.id + '");>修改</button>';
}

function openEditMfcUsers(id) {
	postAjaxRequest("/ecs/mfc/get.do", {
		"id" : id
	}, function(data) {
		$('#editMfcForm').form('load', data.data);
		//$('#editMfc').window('open');
		openDialog("editMfc");
	});
}


/***************短信模板相关方法***********************/
function loadSmsTemplates(){
	
	postAjaxRequest("/ecs/sys/sms/template/list.do", {}, function(data) {
	
		if(data.rows && data.rows.length >0){
			for(i in data.rows){
				var row = data.rows[i];
				$("#" + row.templateId).val(row.template);
			}
		}
	});
	
}
var jmz = {};
jmz.GetLength = function(str) {
    ///<summary>获得字符串实际长度，中文2，英文1</summary>
    ///<param name="str">要获得长度的字符串</param>
    var realLength = 0, len = str.length, charCode = -1;
    for (var i = 0; i < len; i++) {
        charCode = str.charCodeAt(i);
        if (charCode >= 0 && charCode <= 128) realLength += 1;
        else realLength += 2;
    }
    return realLength;
};
function saveSmsTemplate(templateId){
	console.log($("#"+templateId).val().length);
	
	var data = {
		"templateId" : templateId,
		"template" : $("#" + templateId).val()
	};
	
	postAjaxRequest("/ecs/sys/sms/template/add.do", data, function(data) {
		alert("保存成功");
	});
}


/***************规则库相关***********************/
function loadSystemConfig(){
	
	postAjaxRequest("/ecs/sys/cfg/list.do", {}, function(data) {	
		if(data.data){
			$(".sys-form").form('load',data.data);
		}else{
			$(".sys-form").form('load',data);
		}
	});
	
}


/** ******************账单结算*********************** */
function confirmServiceOrderBill() {

	var ids = getGridCheckedIds('billList', 'soStatus', 'DONE');
	var data = {
		"ids" : ids
	};

	if (ids.length == 0) {
		alert("请选择需要结算的订单");
		return;
	}
	postAjaxRequest("/ecs/order/sp/bill/confirm.do", data, function(data) {
		$("#billList").datagrid('reload');
	});

}


function querySpOrderBill(type) {
	var data = {
		"startDate" : $('#hstartDate').datebox('getValue'),
		"endDate" : $('#hendDate').datebox('getValue'),
		dateType : type
	}

	$('#billList').datagrid("reload", data);

}

function confirmProductOrderBill() {

	var ids = getGridCheckedIds('billList', 'billStatus', 'SP_DONE');
	var data = {
		"ids" : ids
	};

	if (ids.length == 0) {
		alert("请选择需要结算的订单");
		return;
	}
	postAjaxRequest("/ecs/order/mfc/bill/confirm.do", data, function(data) {
		$("#billList").datagrid('reload');
	});

}



function formatterUrlPath(val, row) {

	var displayColumn = "";
	if (row.displayValue) {
		displayColumn = row.displayValue;
	}
	if (row.urlPath == "/ecs/order/pro/split.do" && row.message == "insert"
			&& row.tableName == "ServiceOrder") {
		return "客服拆单 - " + displayColumn;
	} else if (row.urlPath == "/ecs/order/pro/split.do"
			&& row.message == "update" && row.tableName == "ProductOrder") {
		return "客服拆单 - " + displayColumn;
	} else if (row.urlPath == "/ecs/order/pro/split.do"
			&& row.message == "insert" && row.tableName == "User") {
		return "客服拆单生成用户 - " + displayColumn;
	} else if (row.urlPath == "/ecs/order/so/changesp.do") {
		return "更换服务商";
	} else if (row.urlPath == "/ecs/mfc/add.do" && row.message == "update") {
		return "管理员编辑厂商信息 - " + displayColumn;
	} else if (row.urlPath == "/ecs/mfc/add.do" && row.message == "insert"
			&& row.tableName == "Manufacturer") {
		return "管理员新增厂商 - " + displayColumn;
	} else if (row.urlPath == "/ecs/mfc/add.do" && row.message == "insert"
			&& row.tableName == "User") {
		return "管理员新增厂商生成账号 - " + displayColumn;
	} else if (row.urlPath == "/ecs/sp/adminadd.do" && row.message == "insert"
			&& row.tableName == "ServiceProvider") {
		return "管理员新增服务商 - " + displayColumn;
	} else if (row.urlPath == "/ecs/sp/adminadd.do" && row.message == "insert"
			&& row.tableName == "User") {
		return "管理员新增服务商生成账号 - " + displayColumn;
	} else if (row.urlPath == "/ecs/sp/adminaddWorker.do"
			&& row.message == "insert" && row.tableName == "Worker") {
		return "管理员新增工人 - " + displayColumn;
	}else if (row.urlPath == "/ecs/sp/adminaddWorker.do"
			&& row.message == "update" && row.tableName == "Worker") {
		return "管理员编辑工人 - " + displayColumn;
	} else if (row.urlPath == "/ecs/user/adminadd.do" && row.message == "insert" && row.tableName == "User") {
		return "管理员新增用户 - " + displayColumn;
	}else if (row.urlPath == "/ecs/user/adminadd.do" && row.message == "update" && row.tableName == "User") {
		return "管理修改用户 - " + displayColumn;
	}else if (row.urlPath == "/ecs/order/mfc/pro/import.do" && row.message == "insert" && row.tableName == "ProductOrder") {
		return "订单导入 - " + displayColumn;
	}else if (row.urlPath == "/ecs/order/pro/active.do" && row.message == "update" && row.tableName == "ProductOrder") {
		return "产品订单下的服务订单激活 - " + displayColumn;
	}else if (row.urlPath == "/ecs/order/pro/cancel.do" && row.message == "update" && row.tableName == "ProductOrder") {
		return "产品订单取消 - " + displayColumn;
	}else if (row.urlPath == "/ecs/order/pro/active.do" && row.message == "insert" && row.tableName == "ServiceOrder") {
		return "服务订单激活 - " + displayColumn;
	}else if (row.urlPath == "/ecs/order/sp/bill/confirm.do" && row.message == "update" && row.tableName == "ServiceOrder") {
		return "服务商账单确认 - " + displayColumn;
	}else if (row.urlPath == "/ecs/order/mfc/bill/confirm.do" && row.message == "update" && row.tableName == "ServiceOrder") {
		return "厂商账单确认 - " + displayColumn;
	}else if (row.urlPath == "/ecs/order/user/so/confirm.do" && row.message == "update" && row.tableName == "ServiceOrder") {
		return "用户订单到货确认 - " + displayColumn;
	}else if (row.urlPath == "/ecs/order/so/score/add.do" && row.message == "update" && row.tableName == "ServiceOrder") {
		return "订单评价 - " + displayColumn;
	}else if (row.urlPath == "/ecs/order/so/cancel.do" && row.message == "update" && row.tableName == "ServiceOrder") {
		return "服务订单取消 - " + displayColumn;
	}else if (row.urlPath == "/ecs/order/so/assign.do" && row.message == "update" && row.tableName == "ServiceOrder") {
		return "派工 - " + displayColumn;
	}else if (row.urlPath == "/ecs/order/so/accept.do" && row.message == "update" && row.tableName == "ServiceOrder") {
		return "服务商接单 - " + displayColumn;
	}else if (row.urlPath == "/ecs/order/so/finish.do" && row.message == "update" && row.tableName == "ServiceOrder") {
		return "服务商完工 - " + displayColumn;
	}else if (row.urlPath == "/ecs/user/unlock.do" && row.message == "update" && row.tableName == "User") {
		return "用户解冻 - " + displayColumn;
	}else if (row.urlPath == "/ecs/user/lock.do" && row.message == "update" && row.tableName == "User") {
		return "用户冻结 - " + displayColumn;
	}else if (row.urlPath == "/ecs/order/pro/active.do" && row.message == "insert" && row.tableName == "User") {
		return "订单激活产生新用户 - " + displayColumn;
	}else if (row.urlPath == "/ecs/sp/addWorker.do" && row.message == "insert" && row.tableName == "Worker") {
		return "新增工人 - " + displayColumn;
	} else if (row.urlPath == "/ecs/sp/addWorker.do" && row.message == "update" && row.tableName == "Worker") {
		return "修改工人 - " + displayColumn;
	}
	



	return "";
}




