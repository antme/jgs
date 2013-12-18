function formatterOperation(val, row) {
	return '<button <button class=\"button_color1\" onclick=editorProductOrders("' + row.id + '")>编辑</button>';
}



function formatterMfcBillOrderStatus(val, row) {

	if (row.billStatus == "NEW") {
		return "未结算";

	} else if (row.billStatus == "MFC_DONE") {
		return "已结算";

	}
	return "部分结算";

}


function querySpOrderBill(type) {
	var data = {
		"startDate" : $('#hstartDate').datebox('getValue'),
		"endDate" : $('#hendDate').datebox('getValue'),
		dateType : type
	}

	$('#billList').datagrid("reload", data);

}

function editorProductOrders(id){
	$('#dlg').dialog('setTitle', '修改订单');
	openDialog("dlg");
	var rows=$("#orderList").datagrid('getRows');
    for(var i=0;i<rows.length;i++){
    	if(rows[i].id==id){
    		$("#editorderform").form('load',rows[i]);
    		break;
    	}
    }
}


function deleteProductOrders(){
	var ids = getGridCheckedIds('orderList', 'poStatus', 'INACTIVE');
	if(ids.length == 0){		
		alert("请在列表中选择需要删除的数据，也可以点击标题左边的选择框全部选择本页的数据删除！");
		return false;
	}else{
		
		if(confirm("确认删除所选订单 ?")){
			postAjaxRequest("/ecs/order/mfc/pro/delete.do", {
				"ids" : ids
			}, function(data) {
				alert("删除成功");
				$('#orderList').datagrid('reload');
			});
		}
	}
	

}

function deleteAllProductOrders(){
	if(confirm("确认删除所有订单 ?")){
		postAjaxRequest("/ecs/order/mfc/pro/deleteAll.do", {
			
		}, function(data) {
			alert("删除成功,如果需要恢复，请重新导入订单");
			getTipInfos();
			$('#orderList').datagrid('reload');
		});
	}

}



function activeProductOrder() {

	var ids = getGridCheckedIds('orderList', 'poStatus', 'INACTIVE');
	if(ids.length == 0){		
		alert("请在列表中选择需要激活的数据，也可以点击标题左边的选择框全部选择本页的数据激活！");
		return false;
	}else{
		if(confirm("确认激活所选订单 ?")){
		var data = {
				"ids" : ids
			};
			postAjaxRequest("/ecs/order/pro/active.do", data, function(data) {
				alert("激活成功，激活后所选订单将分配给服务商");
				getTipInfos();
				$('#orderList').datagrid('reload');
			});
		}
	}
//	var ids = new Array();
//	ids.push(id);


}

function reloadGrid() {
	getTipInfos();
	$('#orderList').datagrid('reload');
}

function formatterProductOrderStatus(val, row) {
	if (val == "INACTIVE") {
		return "未激活";
	} else if (val == "MANUAL") {
		return "人工处理";
	} else if (val == "NEED_SP_CONFIRM") {
		return "待服务商确认";
	}else if (val == "DONE") {
		return "安装完成";
	}else if (val == "ACCEPTED") {
		return "已确认待分配";
	}else if (val == "ASSIGNED") {
		return "已分配未安装";
	}else if (val == "CLOSED") {
		return "已结算";
	}else if (val == "CANCELLED") {
		return "已取消";
	}else if (val == "REJECTED") {
		return "人工处理";
	}else if (val == "TERMINATED") {
		return "人工处理";
	}
	

	return val;
}

