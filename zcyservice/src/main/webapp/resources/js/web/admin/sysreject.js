var poGoodsNumber;
var rowsvalue;
var splitOrderIndex;
var splitOrder;
var selectMfcOrder;
var ordernumber=0;

$(document).ready(function(){
	$(".beark_btn").click(function(){
		if(parseInt(rowsvalue.poGoodsNumber)>ordernumber){
			$('#dlg').dialog('setTitle', '订单处理');
			openDialog("dlg");
			$('#fm').form('load', rowsvalue);
			$(".simbut_btn2").show();
		}else{
			error_info_callback();
		}
	});
	$(".simbut_btn2").click(function(){
	    if($(".select_mfc").is(":hidden")){
	    	var data = $("#Break_list").datagrid("getData");
	    	var orderList = new Array();
	    	for(index in data.rows){
	    		
	    		if(data.rows[index].id.indexOf("split") !=-1){
	    			
	    			if(!data.rows[index].spName){
	    				alert("请为第" +(parseInt(index)+1) +"行的订单选择服务商后再提交");
	    				return false;
	    			}
	    			
	    			orderList.push(data.rows[index]);
	    		}
	    		
	    	}
	    	
	    	if(orderList.length ==0 ){
	    		alert("请先处理订单后再提交");
	    	}else{
		    	
		    	var postData = {
		    			"rows" : JSON.stringify(orderList)
		    	};
		    	
		    	postAjaxRequest("/ecs/order/pro/split.do", postData, function(data) {
		    		alert("订单提交成功");
		    		$("#orderList").datagrid('reload');
		    		getTipInfos();
		    	});
		    	close_window();
	    	}
	    }else{
	    	alert("请确认服务商！");
	    }
	});
});


function formatterBreak_orderperation(val, row){

	if(row.id && row.id.indexOf("split")==-1){
		return "原始订单";
	}else{
		return '<button onclick=open_sp_list("'+row.id+'")>选择服务商</button><button class=\"font_red\" onclick=deleorder("'+row.poGoodsNumber+'","'+row.id+'")>删除</button>';
	}
}
function deleorder(number,owns) {
	var rowIndex = $('#Break_list').datagrid('getRowIndex',owns);
	ordernumber=ordernumber-parseInt(number);
	$('#Break_list').datagrid('deleteRow', rowIndex);
	$(".select_mfc").hide();
	
}

function open_sp_list(id){
	var rows = $("#Break_list").datagrid('getRows');
	for(index in rows){
		if(rows[index].id == id){
			tempSpData = undefined;
			splitOrderIndex = index;
			splitOrder = rows[index];
			$("#plusPrice").val("");
			$("#splitRemark").val("");

			if(splitOrder.plusPrice){
				$("#plusPrice").val(splitOrder.plusPrice);
			}
			
			if(splitOrder.splitRemark){
				$("#splitRemark").val(splitOrder.splitRemark);
			}
			break;
		}
		
	}
	$(".select_mfc").show();
	tempSpData = {};
	tempSpData.rows = [];
	$("#nfc_list").datagrid();

	$("#nfc_list").datagrid('loadData', tempSpData);
	postAjaxRequest("/ecs/sp/for/order/selectspbyaddress.do", {
			"poReceiverAddress" : splitOrder.poReceiverAddress,
			"poGoodsTitle" : splitOrder.poGoodsTitle,
			"cateId" : splitOrder.cateId

		}, function(data){
			tempSpData = data;
			$("#nfc_list").datagrid('loadData',data);

		
	});
	



}

function sorter(a,b){  
	return a>b;
}  

function onSplitOrderClickRow(index, row){

	splitOrderIndex = index;
	splitOrder = row;
	$("#plusPrice").val("");
	$("#splitRemark").val("");

	if(splitOrder.plusPrice){
		$("#plusPrice").val(splitOrder.plusPrice);
	}
	
	if(splitOrder.splitRemark){
		$("#splitRemark").val(splitOrder.splitRemark);
	}
	

}

function onSplitOrderClickCell(rowIndex, field, value){

	var rows = $("#Break_list").datagrid('getRows');
	splitOrderIndex = rowIndex;
	splitOrder = rows[rowIndex];
	
	if(field == "id"){
		tempSpData = undefined;
		open_sp_list();
	}
}

function pagerFilter(data){

    if(!tempSpData){
    	tempSpData = data;
    }
    $("#totalPrice").text(data.price);
    data = {
            total: tempSpData.rows.length,
            rows: tempSpData.rows
     }
    
    var dg = $(this);
    var opts = dg.datagrid('options');
    var pager = dg.datagrid('getPager');
    pager.pagination({
        onSelectPage:function(pageNum, pageSize){
            opts.pageNumber = pageNum;
            opts.pageSize = pageSize;
            pager.pagination('refresh',{
                pageNumber:pageNum,
                pageSize:pageSize
            });
            dg.datagrid('loadData',data);
        }
    });
    if (!data.originalRows){
        data.originalRows = (data.rows);
    }
    var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
    var end = start + parseInt(opts.pageSize);
    data.rows = (data.originalRows.slice(start, end));
    return data;
}



function select_mfc(val, row){
	return '<button class=\"btn_red\" onclick=open_table()>选择</button>';
}
function formatterselect_mfc(val, row){
	return '<button class=\"btn_red\" onclick=selectMfcForOrder("' + row.id + '","' + row.spUserName + '",this)>选择</button>';
}

function selectMfcForOrder(id, name, obj){
	//拆分的订单
	if(splitOrder){
		splitOrder.spName = name;
		splitOrder.spId = id;
	}
	
	//换服务商的订单
	if(selectMfcOrder){
		selectMfcOrder.spName = name;
		selectMfcOrder.spId = id;
	}
	
	$('#Break_list').datagrid('updateRow',{
		index: splitOrderIndex,
		row: splitOrder
	});
	addCss();
	$(".datagrid-view2").find(".sel").text("选择");
	$(".datagrid-view2").find(".sel").addClass("btn_red").removeClass("sel");
	var $obj=$(obj);
	$(obj).text("已选择");
	$(obj).removeClass("btn_red").addClass("sel");
}

function saveUser() {
	if(!$("#fm").form('validate')){
		return;
	}
	var row = $("#fm");
	if(parseInt(row[0].poGoodsNumber.value)=="0"){
		title_info_callback();
	}else{
		if(parseInt(row[0].poGoodsNumber.value)>parseInt(rowsvalue.poGoodsNumber)){
			title_info_callback();
		}else{
			ordernumber=ordernumber+parseInt(row[0].poGoodsNumber.value);
		    if(parseInt(rowsvalue.poGoodsNumber)>=ordernumber){
		    	$("#Break_list").datagrid('appendRow', {
					poCode : row[0].poCode.value,
					poMemberName : row[0].poMemberName.value,
					poReceiverAddress : row[0].poReceiverAddress.value,
					poReceiverName : row[0].poReceiverName.value,
					poGoodsTitle : row[0].poGoodsTitle.value,
					spName : "",
					spId : "",
					splitRemark : "",
					plusPrice : "0",
					id: "split_" + guid(),
					soCode: "",
					poReceiverMobilePhone : row[0].poReceiverMobilePhone.value,
					poGoodsNumber: row[0].poGoodsNumber.value,
					cateId: $("#cateId").combobox('getValue')
				});
				addCss();
				$('#dlg').dialog('close');
				
		    }else{
		    	error_info_callback();
		    	ordernumber=ordernumber-parseInt(row[0].poGoodsNumber.value);
		    }
			
		}
	}
	
}

function error_info_callback(){
	
	$.messager.confirm('无法处理','请核对宝贝数量');

}


function title_info_callback(){
	
	$.messager.confirm('处理失败','请核对宝贝数量');

}

function addCss() {

}
function updateCss() {

}



function close_window(){
	$(".break_list").hide();
	$(".back_demo").hide();
}


function close_dlg(){
	var plusPrice = $("#plusPrice").val();
	if(plusPrice && plusPrice!=""){
		splitOrder.plusPrice = $("#plusPrice").val();
	}
	splitOrder.splitRemark = $("#splitRemark").val();
	$('#Break_list').datagrid('updateRow',{
		index: splitOrderIndex,
		row: splitOrder
	});
	$(".select_mfc").hide();
}
function close_select_mfc_dlg(){
	close_window();
}

function submit_select_mfc_dlg(){
	selectMfcOrder.plusPrice = $("#plusPrice").val();
	if(!selectMfcOrder.spId){
		alert("请选择服务商");
		return false;
	}

	postAjaxRequest("/ecs/order/so/changesp.do", selectMfcOrder, function(data) {
		alert("当前订单已更改服务商");
		close_window();
		$("#orderList").datagrid('reload');
		getTipInfos();
	});
}