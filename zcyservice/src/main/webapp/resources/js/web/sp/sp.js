
function formatterNewOrderOperation(val, row) {	
	return '<button class=\"button_color1\" onclick=acceptOrders("' + row.id + '");>接受</button>&nbsp;&nbsp;<button class=\"button_color2\" onclick=openRejectOrdersWindow("' + row.id + '");>拒绝</button>';
}

function formatterAcceptOrderOperation(val, row) {
	
	return '<button onclick=openAssignWorkersWindow("' + row.id + '");>派工</button>&nbsp;&nbsp;<button onclick=openTerminateOrdersWindow("' + row.id + '");>无法完工</button>';
	
}


function formatterAssignOrderOperation(val, row) {
	return '<button onclick=openAssignWorkersWindow("' + row.id + '");>修改</button>&nbsp&nbsp<button onclick=finishOrders("' + row.id + '");>完工</button>&nbsp&nbsp<button onclick=openTerminateOrdersWindow("' + row.id + '");>无法完工</button>';
}


function formatterNewOrderStatus(val, row) {
	
	if(row.isGoodsArrived){
		return "待服务商接受";
	}else{
		return "待客户到货确认";
	}
	
}


function acceptOrders(id) {
	var ids = new Array();
	ids.push(id);
	postAjaxRequest("/ecs/order/so/accept.do", {
		"ids" : ids
	}, function(data) {
		$('#orderList').datagrid('reload');
		getTipInfos();
	});

}

function rejectOrders() {
	if($("#orderRejectForm").form("validate")){
		var data = {
				"id" : $("#id").val(),
				"rejectRemark" : $("#rejectRemark").val(),
				"rejectReason" : $('#rejectReason').combobox('getValue')
		}
		postAjaxRequest("/ecs/order/so/rejectorder.do", data, function(data) {
			$('#orderReject').window('close');
			$('#orderList').datagrid('reload');
		});
	}
}


function finishOrders(id){
	$.messager.confirm('确认完工', '您是否确认完工?', function(r){
        if (!r){
        	
        }else{
        	var ids = new Array();
        	ids.push(id);
        	postAjaxRequest("/ecs/order/so/finish.do", {
        		"ids" : ids
        	}, function(data) {
        		$('#orderList').datagrid('reload');
        	});
        }
    });
	
}

function queryMyOrderBill(type){
	$('#myBillList').datagrid("reload", {type: type});
	
}



function assignWorkersCallBack(data){
	
}


function terminateOrdersCallBack(data){
	
}

function openAssignWorkersWindow(id) {
	var rows = $("#orderList").datagrid('getRows');
	$('#workerAssignForm').form('clear');
	$("#estInstallDateRegion").combobox('setValue', '全天');
    $("#estInstallDateTime").combobox('setValue', '9');
	for(var i=0;i<rows.length;i++){
    	if(rows[i].id==id){
    		if(rows[i].estInstallDate){
    		    rows[i].estInstallDate = rows[i].estInstallDate.replace("00:00:00", "");
    		    $('#estInstallDate').datebox('setValue', rows[i].estInstallDate);	
    		}
    		$('#workerAssignForm').form('load', rows[i]);
    		break;
    	}
    }
	
	$("#id").val(id);
	//$('#workerAssign').window('open');
	openDialog("workerAssign");
	$('#workerAssign').window('center');
}

function assignWorkers() {

	var date = $('#estInstallDate').datebox('getValue');
	
	if(!date){
		alert("请填写预约安装时间");
		return;
	}
	var data = {
		"id" : $("#id").val(),
		"assignRemark" : $("#assignRemark").val(),
		"workerId" : $('#workerId').combobox('getValue'),
		"estInstallDate" : $('#estInstallDate').datebox('getValue'),
		"estInstallDateRegion" : $('#estInstallDateRegion').combobox('getValue'),
		"estInstallDateTime" : $('#estInstallDateTime').combobox('getValue')
	}

	postAjaxRequest("/ecs/order/so/assign.do", data, function(data) {
		$('#workerAssign').window('close');
		$('#orderList').datagrid('reload');
	});

}

function openTerminateOrdersWindow(id) {
	$("#id").val(id);
	//$('#orderTerminate').window('open');
	openDialog("orderTerminate");
	$('#orderTerminate').window('center');
	$('#orderTerminateForm').form('clear');

}

function openRejectOrdersWindow(id) {
	$("#id").val(id);
	//$('#orderReject').window('open');
	openDialog("orderReject");
	$('#orderReject').window('center');
	$('#orderRejectForm').form('clear');
}
function terminateOrder(){
	
	var data = {
			"id" : $("#id").val(),
			"terminateRemark" : $("#terminateRemark").val(),
			"terminateReason" : $('#terminateReason').combobox('getValue')
	}
	
	postAjaxRequest("/ecs/order/so/terminate.do", data, function(data) {
		$('#orderTerminate').window('close');
		$('#orderList').datagrid('reload');
	});
	
}

function submitAssignWorkers(id) {
	$("#workerAssignForm").submit();
}


function submitTerminateOrders(){
	
}


function formatterAcceptOrderStatus(val, row) {

	return "待分配";

}

function formatterAssignOrderStatus(val, row) {
	return "已派工";
}

function formatterHistoryOrderStatus(val, row) {
	return val;
}

function reloadGrid() {
	$('#orderList').datagrid('reload');
}

function addWork() {
	var data = {
		"id" : "",
		"workerName" : "",
		"address" : "",
		"mobilePhone" : "",
		"idCard" : ""
	}
	$("#workerType").find("input").removeAttr("checked");
	$("#addWorkerForm").form('load',data);
	$("#idCard").removeAttr("disabled");
	$('#addWorker').window('setTitle','我的工人-新增');
	//$('#addWorker').window('open');
	openDialog("addWorker");
}

function formatterWorkerOperation(val, row) {
	
	//if(row.isActive){
		return '<button onclick=editWorker("' + row.id + '");>编辑</button>&nbsp;&nbsp;<button onclick=delWorker("' + row.id + '");>删除</button>';
	//}
	//return '<button onclick=editWorker("' + row.id + '");>编辑</button>';
}

function delWorker(id){
	if(confirm("是否确定删除此工人记录?")){
		var data = {"id":id};
		postAjaxRequest("/ecs/sp/worker/del.do", data, function(data) {
			$('#myWorkersList').datagrid('reload');
		});
	}else{
		return;
	}
}

function formatterWorkerStatus(val, row) {
	if(row.isActive){
		return '正常';
	}
	return '已冻结 (解冻请联系管理员)';
}

function inactiveWorker(id){
	postAjaxRequest("/ecs/sp/worker/inactive.do", {
		"id" : id
	}, function(data) {
		$('#myWorkersList').datagrid('reload');
	});
}


function editWorker(id) {
	postAjaxRequest("/ecs/sp/worker/get.do", {
		"id" : id
	}, function(data) {
		$("#workerType").find("input").removeAttr("checked");
		$('#addWorkerForm').form('load', data.data);
		$('#addWorker').window('setTitle','我的工人-编辑');
		//$('#addWorker').window('open');
		var workerType=$("#workerType").find("input");
		if(data.data.workerType!=undefined){
			var sdt=data.data.workerType.split(",");
			for(var j=0;j<workerType.length;j++){
				for(var i=0;i<sdt.length;i++){
					if($(workerType[j]).attr("value")==sdt[i]){
						$(workerType[j]).click();
					}
				}
			}
		}
		$("#idCard").attr("disabled","disabled");
		openDialog("addWorker");
	});
}




function searchServiceProviders(){
	var c_s_province =$('#c_s_province').combobox('getValue');
	var c_s_city =$('#c_s_city').combobox('getValue');
	var c_s_county =$('#c_s_county').combobox('getValue');
	var c_s_category =$('#c_s_category_one').combobox('getValue');
	
	 var categoryGrandpaId=$('#c_s_category_one').combobox('getValue');
     var categoryParentId=$('#c_s_category_two').combobox('getValue');
     
     var categoryId=$('#c_s_category_three').combobox('getValue');
     
 	if(categoryGrandpaId=="0" || categoryGrandpaId==""){
 		alert("请选择一级类别");
 		return false;
 	}
 	
	if(categoryParentId=="0" || categoryParentId==""){
 		alert("请选择二级类别");
 		return false;
 	}
	
	if(categoryId=="0" || categoryId==""){
 		alert("请选择三级类别");
 		return false;
 	}
     
	if(c_s_province=="0" || c_s_province==""){
		alert("请选择省");
	}else{
		if(c_s_city=="0" || c_s_city==""){
			alert("请选择市");
		}else{
			if(c_s_county=="0" || c_s_county==""){
				alert("请选择区");
			}else{
			
					var data = {
							 "countyId" : $('#c_s_county').combobox('getValue'),
							 "categoryGrandpaId" : $('#c_s_category_one').combobox('getValue'),
				             "categoryParentId" : $('#c_s_category_two').combobox('getValue'),
				             "categoryId" : $('#c_s_category_three').combobox('getValue')
						}
					$('#spList').datagrid('reload', data);
				
			}
		}
	}
}