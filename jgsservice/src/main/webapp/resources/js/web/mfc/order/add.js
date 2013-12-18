var str,number;
function formatterdelectdata(val, row) {
	return "<div class='cz'><button class='updte_btn' onclick='destroyUser()'>删除</button><button class='updte_btn' onclick='editing(this);return false;'>修改</button></div>";
}
$(window).resize(function() {
	$('.easyui-datagrid').datagrid('resize');
});
function newUser() {
	$('#fm').form('clear');
	$('#dlg').dialog('setTitle', '订单信息');
	openDialog("dlg");
	//$('#fm').form('clear');
	str = "save";
}
function editing(obj) {
	var $obj = $(obj);
	$('#dg').datagrid('clearSelections');
	$(obj).parent().parent().parent().parent().addClass("datagrid-row-selected");
	number=$(obj).parent().parent().parent().parent().attr("datagrid-row-index");
	var row = $('#dg').datagrid('getSelected');
	$('#dlg').dialog('setTitle', '修改订单');
	openDialog("dlg");
	$('#fm').form('load', row);
	str = "update";
}
function saveUser() {
	 
	if(!$("#fm").form('validate')){
		return;
	}
	var row = $("#fm");
	$('#dlg').dialog('close');
	if (str == "save") {
		$("#dg").datagrid('appendRow', {
			poCode : row[0].poCode.value,
			poMemberName : row[0].poMemberName.value,
			poReceiverAddress : row[0].poReceiverAddress.value,
			poReceiverName : row[0].poReceiverName.value,
			poReceiverPhone : row[0].poReceiverPhone.value,
			poReceiverMobilePhone : row[0].poReceiverMobilePhone.value,
			poGoodsTitle : row[0].poGoodsTitle.value,
			poGoodsCate : row[0].poGoodsCate.value,
			poLogisticsNo : row[0].poLogisticsNo.value,
			poOrderRemark : row[0].poOrderRemark.value,
			poGoodsNumber : row[0].poGoodsNumber.value
		});
	} else {
		$('#dg').datagrid('updateRow', {
			index : number,
			row : {
				poCode : row[0].poCode.value,
				poMemberName : row[0].poMemberName.value,
				poReceiverAddress : row[0].poReceiverAddress.value,
				poReceiverName : row[0].poReceiverName.value,
				poReceiverPhone : row[0].poReceiverPhone.value,
				poReceiverMobilePhone : row[0].poReceiverMobilePhone.value,
				poGoodsTitle : row[0].poGoodsTitle.value,
				poGoodsCate : row[0].poGoodsCate.value,
				poLogisticsNo : row[0].poLogisticsNo.value,
				poOrderRemark : row[0].poOrderRemark.value,
				poGoodsNumber : row[0].poGoodsNumber.value
			}
		});
	}
	addCss();
}
function destroyUser() {
	var rowIndex = $('#dg').datagrid('getRowIndex');
	$('#dg').datagrid('deleteRow', rowIndex);
}

function addCss() {
	$(".datagrid-header-row").find("td").each(function() {
		var str = $(this).attr('field');
		$(this).addClass(str);
		$(this).find('div').width($(this).width());
	});
	$(".datagrid-row").find("td").each(function() {
		var str = $(this).attr('field');
		$(this).addClass(str);
	});
	updateCss();
}
function updateCss() {
	$(".datagrid-row").find("td").each(
			function() {
				var classname = $(this).attr('class');
				$(this).find('div').width(
						$(".datagrid-header-row").find('td.' + classname)
								.width());
			});
}


function mfc_submit_orders(){
	$.messager.defaults = {
			  ok : "重新导入",
			  cancel : "跳转"
	};
	var data = $("#dg").datagrid("getData");
	if(data.rows.length!=0){
		var postData = {
				"rows" : JSON.stringify(data.rows)
		};
		postAjaxRequest("/ecs/order/pro/add.do", postData, mfc_submit_orders_callback);
	}else{
		mfc_submit_error_orders_callback();
	}
	
	
}


function mfc_submit_orders_callback(data){
	
	$.messager.confirm('导入成功', '继续导入或跳转到新订单列表页?', function(r){
        if (!r){
        	var value=$("#sidebar").find(".margin_top").find(".title_cs");
        	var nodes;
        	$.each(value,function(){
        		if($(this).text()=="我的订单"){
        			this.click();
                    nodes=$(this).next("ul").find("a:first-child");
                    $.each(nodes,function(){
                    	if($(this).text()=="新订单"){
                    		this.click();
                    	}
                    });
        		}
        	});
        }
});
}

function mfc_submit_error_orders_callback(){
	$.messager.defaults = {
			  ok : "确定",
			  cancel : "跳转"
	};

	$.messager.alert('导入失败', '请添加订单后确认导入！', "warn");
}