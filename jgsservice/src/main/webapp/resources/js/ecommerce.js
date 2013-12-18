var idParam = undefined;
var gridId = undefined;
var ieGridPopupInit = false;
function loadRemotePage(page, config) {	
	if (page) {
		var url = page;
		if (!page.endWith(".html") && !page.endWith(".jsp")) {
			var pages = page.split("/");
			if (pages.length == 3) {
				url = "/pages/" + pages[0] + "/" + pages[1] + "/" + pages[2]
						+ ".jsp";
			}
			if (pages.length == 4) {
				url = "/pages/" + pages[0] + "/" + pages[1] + "/" + pages[2]
						+ "/" + pages[3] + ".jsp";
			}
		}

		$.ajaxSetup({
			cache : false
		// 关闭AJAX相应的缓存
		});
		

		$.ajax({
			url : url,
			success : function(data) {
				
				if(gridId && gridId == "soOrderList"){
					$("#remotePagePopup").show();
					$("#remotePagePopup").html(data);
				}else{
					$("#remotePage").show();
					$("#remotePage").html(data);
				}
			},
			error : function(res){

			}
		});
		

	}



}

function loadRemoteWindowPage(page, config) {

	if (page) {
		var url = page;
		if (!page.endWith(".html") && !page.endWith(".jsp")) {
			var pages = page.split("/");
			if (pages.length == 3) {
				url = "/pages/" + pages[0] + "/" + pages[1] + "/" + pages[2]
						+ ".jsp";
			}
			if (pages.length == 4) {
				url = "/pages/" + pages[0] + "/" + pages[1] + "/" + pages[2]
						+ "/" + pages[3] + ".jsp";
			}
		}

		$.ajaxSetup({
			cache : false
		// 关闭AJAX相应的缓存
		});
		

		$.ajax({
			url : url,
			success : function(data) {
				config.top = 50;
				config.maximizable=false;
				config.minimizable=false;
				config.collapsible=false;
				$("#remotePageWindow").show();
				$("#remotePageWindow").html(data);
				$("#remotePageWindow").window(config);
			},
			error : function(res){

			}
		});
		

	}



}

function postAjaxRequest(url, parameters, callback, loading, hiddenDetaiPage) {
	if(loading == undefined || loading){
		ajaxLoading();
	}
	$.ajax({
		url : url,
		success : function(responsetxt) {
			var res = undefined;
			try{
				eval("res=" + responsetxt);
			}catch(error){
				res = responsetxt;
			}
			if (res && res.code && res.code != "200") {
				dealMessage(res, "错误信息");
			} else {
				if(res){
					eval("callback(res)");
				}
				
				if(hiddenDetaiPage === false){
					
				}else{
					if(loading == undefined || loading){
						
						$(".remotePage").hide();
					}

				}
			}
			if(loading == undefined || loading){
				ajaxLoadEnd();
			}
		},

		error : function(res) {
			if (res && res.status == 200) {
				eval("res.responseText");
			}
			if(loading == undefined || loading){
				ajaxLoadEnd();
			}
		},

		data : parameters,
		method : "post",
		cache: false
	});

}

function ajaxLoading(){  
    $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:document.body.scrollHeight}).appendTo("body");  
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({display:"block",left:($(document.body).outerWidth(true) - 190) / 2,top:($(window).height() - 45) / 2});  
 }  
function ajaxLoading2(top){  
    $("<div class=\"datagrid-mask\"></div>").css({display:"block",width:"100%",height:document.body.scrollHeight}).appendTo("body");  
    $("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({'display':"block",'left':($(document.body).outerWidth(true) - 190) / 2,'top':top});  
 } 
 function ajaxLoadEnd(){  
     $(".datagrid-mask").remove();  
     $(".datagrid-mask-msg").remove();
     run=false;
}  


function forceLogin(){
	window.location.href="/login.jsp";
}


String.prototype.endWith = function(s) {
	if (s == null || s == "" || this.length == 0 || s.length > this.length)
		return false;
	if (this.substring(this.length - s.length) == s)
		return true;
	else
		return false;
	return true;
};


function dealMessage(data, title, redirectPage) {
	$.messager.defaults = {
		ok : "确定",
		cancel : "取消"
	};
	var response = undefined;
	if (data && data.code) {
		response = data;
	} else {
		eval("response=" + data);
	}
	if (data) {
		if (response.code != "200") {
			if (title) {
				$.messager.alert(title, response.msg, 'info');
			} else {
				$.messager.alert("信息", response.msg, 'info');
			}
		}
		if (response.code == "200" && redirectPage) {
			window.location.href = redirectPage;
		}
		
		if (response.code == "200" && response.msg) {
			$.messager.alert("信息", response.msg, 'info');
		}
	} else {
		$.messager.alert('服务器错误', '服务器错误', 'error');
	}
}


function displayInfoMsg(msg){
	$("#content-right-info").text(msg);
	$("#content-right-info").show();
	$('#content-right-info').fadeOut(5000); 
}

function displayAlert(msg){
	
	$.messager.alert('提示信息',msg,'info');
}

function dealMessageWithCallBack(data, title, callback) {
	$.messager.defaults = {
		ok : "确定",
		cancel : "取消"
	};
	var response = undefined;
	if (data && data.code) {
		response = data;
	} else {
		eval("response=" + data);
	}
	if (data) {
		if (response.code != "200") {
			if (title) {
				$.messager.alert(title, response.msg, 'info');
			} else {
				$.messager.alert("信息", response.msg, 'info');
			}
		}

		if (response.code == "200" && response.msg) {
			$.messager.alert("信息", response.msg, 'info');
		}else if (response.code == "200" && callback) {
			eval("callback(response)");
		}
		
	} else {
		$.messager.alert('服务器错误', '服务器错误', 'error');
	}
}


function logout(){
	postAjaxRequest("/ecs/user/logout.do", {}, function(data) {
		window.location.href="https://" + document.location.host + "/login.jsp";
	}, false);
}

// easy ui 获取grid多选数据的id， 可以根据key和filterValue过滤数据
function getGridCheckedIds(gridId, key, filterValue) {
	var rows = $('#' + gridId).datagrid('getChecked');
	var ids = new Array();
	for (i = 0; i < rows.length; i++) {
		if (key) {
			var rowValue = eval("rows[i]." + key);

			if (rowValue && filterValue && rowValue == filterValue) {
				ids.push(rows[i].id);
			}
		} else {
			ids.push(rows[i].id);
		}

	}
	return ids;
}

String.prototype.replaceAll = function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
}

String.prototype.trim = function()
{
         //   用正则表达式将前后空格
         //   用空字符串替代。
         return   this.replace(/(^\s*)|(\s*$)/g,"");
}



function dateFormatter(date) {
	var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}

function dateTimeFormatter(date){//date is the js date
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    var h = date.getHours();
    var minu = date.getMinutes();
    var sec = date.getSeconds();
    
    if(y){
    	return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+" " +(h<10?('0'+h):h)+":"+(minu<10?('0'+minu):minu)+":"+(sec<10?('0'+sec):sec);
    } 
    return "";
}

function showDateTimeFormatter(date){//date is the java date
	date = new Date(date);//covert from java date to js date
	return dateTimeFormatter(date);
}

function showEstDateFormatter(date){//date is the java date

	if(typeof date == "object"){
		date = new Date(date);//covert from java date to js date
		return dateFormatter(date);		
	}
	if (date) {
		date =  date.replace("00:00:00", "");
		return date.split(" ", 1);

	}
	return date;
}

function dateParser(s){
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
}

function dateTimeParser(s){
}



String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}



function formatterServiceOrderStatusForMfc(val, row) {
	
	if (val == "INACTIVE") {
		return "未激活";
	} else if (val == "MANUAL") {
		return "人工处理";
	} else if (val == "NEED_SP_CONFIRM") {
		return "待确认";
	}else if (val == "DONE") {
		if(row.billStatus =="SP_DONE"){
			return "已结算";
		}else if(row.billStatus =="MFC_DONE"){
			return "已结算";
		}else{
			return "安装完成";
		}
	}else if (val == "ACCEPTED") {
		return "已确认待分配";
	}else if (val == "ASSIGNED") {
		return "已分配未安装";
	}else if (val == "CLOSED") {
		return "已结算";
	}else if (val == "CANCELLED") {
		return "已取消";
	}else if (val == "TERMINATED") {
		return "人工处理";
	}else if (val == "REJECTED") {
		return "人工处理";
	}


	return val;
}

function formatterServiceOrderStatus(val, row) {
	if (val == "INACTIVE") {
		return "未激活";
	} else if (val == "MANUAL") {
		return "人工处理";
	} else if (val == "NEED_SP_CONFIRM") {
		return "待确认";
	}else if (val == "DONE") {
		if(row.billStatus =="SP_DONE"){
			return "已结算";
		}else if(row.billStatus =="MFC_DONE"){
			return "已结算";
		}else{
			return "安装完成";
		}
	}else if (val == "ACCEPTED") {
		return "已确认待分配";
	}else if (val == "ASSIGNED") {
		return "已分配未安装";
	}else if (val == "CLOSED") {
		return "已结算";
	}else if (val == "CANCELLED" ) {
		return "已取消";
	}else if (val == "TERMINATED") {
		return "无法完工";
	}else if (val == "REJECTED") {
		return "已拒绝";
	}


	return val;
}


function formatterBillCloseDate(val, row){	
	
	if (row.billStatus == "MFC_DONE" || row.billStatus == "SP_DONE") {	
		return showDateTimeFormatter(val);
	}
	
	return "";
}


function formatterProductOrderDetailWithCode(val, row) {
	return '<a href="#" onclick=openProductOrderDetailPage("' + val + '")>' + val + '</a>';

}

function formatterProductOrderDetail(val, row) {
	return '<a href="#" onclick=openProductOrderDetailPage("' + row.id+ '")>' + val + '</a>';

}

function formatterSPorMfcDetail(val, row){
	var roleName = row.roleName;
	if(roleName == 'SP'){
		return formatterSPDetail(val, row);
	}else{
		return formatterMFCDetail(val, row)
	}
}

function formatterSPDetail(val, row) {//历史审核列表：查看SP详情
	return '<a href="#" onclick=openSPDetailPage("' + row.userId+ '")>' + val + '</a>';
}

function formatterMFCDetail(val, row) {//历史审核列表：查看MFC详情
	return '<a href="#" onclick=openMFCDetailPage("' + row.userId+ '")>' + val + '</a>';
}

function formatterSiteMessageDetail(val, row){//站内信息列表：查看站内信息详情
	var id = row.id;
	var smuid = row.siteMessageUserId;
	if(smuid == null || smuid == ""){
		
	}else{
		id = smuid;
	}
	return '<a href="#" onclick=openSMDetailPage("' + id + '")>' + val + '</a>';
}

function formatterNewsDetail(val, row){//新闻列表：查看新闻详情
	return '<a href="#" onclick=openNewsDetailPage("' + row.id+ '")>' + val + '</a>';
}

function preViewImageFormatter(val, row){//图片预览
	if(val == null || val == ""){
		return "无";
	}
	var val2=val.replaceAll( "\\\\",   "\\\\\\\\");
	return '<a href="#" onclick=perViewImage("' + val2+ '")>' + '预览' + '</a>';
}

function perViewImage(id){
	idParam =id;	
	var config = {
		    width:400,
		    height:300,
		    modal:true,
		    minimizable:false,
		    maximizable:false,
		    collapsible:false,
		    title : "图片预览"
		};
	loadRemotePage("web/image/imagePreview", config);
}

function openSMDetailPage(id){
	idParam =id;	
	var config = {
		    width:800,
		    height:500,
		    modal:true,
		    minimizable:false,
		    maximizable:false,
		    collapsible:false,
		    title : "站内信息详情"
		};
	loadRemoteWindowPage("message/receiver/siteMessageDetail", config);
}

function openProductServiceOrdersPage(id){
	idParam = id;	
	var config = {
		    width:1090,
		    height:600,
		    modal:true,
		    minimizable:false,
		    maximizable:false,
		    collapsible:false,
		    title : "服务订单"
		};
	loadRemoteWindowPage("web/mfc/order/poServiceOrder", config);
	ieGridPopupInit = true;
}



function openNewsDetailPage(id){
	idParam =id;	
	var config = {
		    width:800,
		    height:500,
		    modal:true,
		    minimizable:false,
		    maximizable:false,
		    collapsible:false,
		    title : "新闻详情"
		};
	loadRemoteWindowPage("admin/news/newsDetail", config);
}

function openProductOrderDetailPage(id){
	idParam =id;	
	var config = {
		    width:918,
		    height:500,
		    modal:true,
		    minimizable:false,
		    maximizable:false,
		    collapsible:false,
		    title : "产品订单详情"
		};
	loadRemotePage("web/mfc/order/orderDetail", config);
}

function openSPDetailPage(id){
	idParam =id;	
	var config = {
		    width:800,
		    height:500,
		    modal:true,
		    minimizable:false,
		    maximizable:false,
		    collapsible:false,
		    title : "服务商详情"
		};
	loadRemoteWindowPage("web/sp/spDetail", config);
}

function openMFCDetailPage(id){
	idParam =id;	
	var config = {
		    width:800,
		    height:500,
		    modal:true,
		    minimizable:false,
		    maximizable:false,
		    collapsible:false,
		    title : "厂商详情"
		};
	loadRemoteWindowPage("web/mfc/mfcDetail", config);
}



function formatterServiceOrderDetail(val, row) {
	return '<a href="#" onclick=openServiceOrderDetailPage("' + val + '")>' + val + '</a>';

}




function openServiceOrderDetailPage(id){
	idParam =id;	
	var config = {
		    width:918,
		    height:500,
		    modal:true,
		    title : "服务订单详情"
		};
	loadRemotePage("web/sp/order/orderDetail", config);
}


function formatterBillOrderList(val, row){
    return '<button onclick=openBillServiceOrdersPage("' + row.id + '")>结算订单列表</button>';
}

function openBillServiceOrdersPage(id){
    idParam = id;    
     var config = {
             width:1100,
             height:650,
             modal:true,
             title : "订单账单详情"
         };
     loadRemoteWindowPage("web/sp/order/billServiceOrder", config);
     ieGridPopupInit = true;
}

function formatterTitleDetail(val, row) {

	
	var txt = val;
	if (val && val.length > 15) {
		txt = val.substr(0, 15) + "...";		
		txt = "<span   '>" + txt  + "</span><label class=\"right_a titleTooltip\" data=\"" + val + "\" ></label>"
	}

	return val;
	
	

}

function showGridEmptyDataMessage(id) {
	var target = $("#" + id);
	var opts = $(target).datagrid('options');
	var time = 1;
	if($(target).datagrid('getPanel')){
		var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
	
		vc.children('div.datagrid-empty').remove();
		if (!$(target).datagrid('getRows').length) {
	
			var d = $('<div class="datagrid-empty"></div>') .html('无数据').appendTo(vc);
			d.css({
				position : 'absolute',
				left : 0,
				top : 40,
				width : '100%',
				textAlign : 'center'
			});
			var idevent = window.setInterval(function() {
				
				if($(target).datagrid('getRows').length && $(target).datagrid('getRows').length > 0){
					vc.children('div.datagrid-empty').remove();
					clearInterval(idevent);
				}else{
					if(time > 5){
						clearInterval(idevent);
					}
					if(vc.css('height') == '58px'){
	//					clearInterval(idevent);
					}else{
						vc.css('height', 58);
					}
				}
				time++;
		    }, 500);
		} else {
			vc.children('div.datagrid-empty').remove();
		}
	
	}
	$(".datagrid-htable").css("width", "100%");
	$(".datagrid-btable").css("width", "100%");
	
	
	if(id== "soOrderList" && $("#soOrderList").length > 0){
		$("#soOrderList").datagrid('resize', {width:"1064px"});
		 if(jQuery.browser && jQuery.browser.msie && jQuery.browser.version <= 7 && !ieGridPopupInit){	

			 $(".window .datagrid-btable .datagrid-cell").each(function(index){
				 var width = $(this).parent().width();
				 $(this).css("width", width -10);
				 $(this).parent().css("width", width-2);
				 var field = $(this).parent().attr("field");
				 updateParent(field, width);	

			 });

		 }

	}

}
function updateParent(field, width){
	 $(".window .datagrid-header-row td").each(function(index){
		 if( $(this).attr("field") == field){
			
			 $(this).css("width", width -2);
			 $(this).children().css("width", width -10);
		 }
	 });
}

function fixWidth(width)  
{   
	return document.body.clientWidth -270 ; //这里你可以自己做调整  
}



function initDataGridEvent(){

	//表格和tab自动适应大小
    if($(".easyui-datagrid_tf").length > 0){
        $(".easyui-datagrid_tf").each(function(index){        	
        	var id = this.id;
        	initDataGrid(id);
        })         
    }  

}

function initDataGrid(id, param){
	if(param ){
		if(!param.width){
			param.width = fixWidth(1);
		}
	}else{
		param = {width:fixWidth(1)};
	}
	if(id){

    	$("#" + id).datagrid(param);
		var opt = $("#" + id).datagrid('options');
		if(opt){
			opt.showRefresh = false;
        	opt.onLoadSuccess = function(){
        		ieGridPopupInit = false;
        		showGridEmptyDataMessage(id);  	   
        		
        	
        		
        	};
        	opt.onClickCell = loadPageDetail;
        	
        	opt.onBeforeLoad = function(){
        		$(".remotePage").hide();
        	},
        	opt.onSelect = function(rowIndex, rowData){
        		$("#" + id + " .datagrid-btable").find("tr").removeClass("datagrid-row-selected");
        		$("#" + id + " .datagrid-btable").find("tr[datagrid-row-index=" + rowIndex + "]").addClass("datagrid-row-selected");
        	}
		}
	}
}


function resizeTabAndGrid(){

    //表格和tab自动适应大小
    if($(".easyui-datagrid_tf").length > 0){
        $(".easyui-datagrid_tf").each(function(index){
        	var id = this.id;        	
        	if(id){   
                $("#" + id).datagrid('resize', {width:fixWidth(1)}); 
                showGridEmptyDataMessage(id);
        	}
        })
         
    }  

    if($(".easyui-tabs").length > 0){
        $(".easyui-tabs").each(function(index){
            var id = this.id;
            if(id){
                $("#" + id).tabs('resize');
            }
        })
         
    }
}


function loadPageDetail(rowIndex, field, value){
	var rowData = undefined;
	var rows =  $(this).datagrid('getRows');
	if($(this)[0].id){
		gridId = $(this)[0].id;
	}
	for(index in rows){
		
		if(index == rowIndex){
			rowData = rows[index];
			break;
		}
	}
	$("#logDetail").html("");
	if(field != "id"){
		if(!rowData.poId && rowData.poStatus){
			openProductOrderDetailPage(rowData.id);
		}else if(rowData.soStatus){
			openServiceOrderDetailPage(rowData.id);
		}else if(rowData.tableName && rowData.tableName=="ServiceOrder"){
			openServiceOrderDetailPage(rowData.dataId);
		}else if(rowData.tableName && rowData.tableName=="ProductOrder"){
			openProductOrderDetailPage(rowData.dataId);
		}else if(rowData.tableName && (rowData.tableName=="Manufacturer" || rowData.tableName=="User" || rowData.tableName=="ServiceProvider"  || rowData.tableName=="Worker")){
			
			if(rowData.data){
				createLogPage(rowData.data, rowData.tableName);
			}
			
		}else{
		
			$(".remotePage").hide();
		}
		
	}else{

	}
	return false;
}

function createLogPage(data, tableName){
	$("#logDetail").html("");
	$(".remotePage").hide();
	var labels = {};
	
	var mfclabels = {
			mfcCompanyName: "厂商公司", 
			mfcCompanyAdress: "厂商公司地址", 
			mfcContactPerson: "厂商联系人", 
			mfcContactPhone: "厂商电话", 
			mfcContactMobilePhone: "厂商联系手机", 
			mfcServiceTypeStr: "主营类型", 
			mfcLocation: "所在地", 
			mfcQQ: "厂商qq"
	}
	
	var splabels = {
			spUserName: "用户名", 
			spServiceTypeStr: "主营服务类型", 
			spCompanySize: "工人数", 
			spCompanyName: "公司名称", 
			spCompanyAddress: "公司地址", 
			spContactPerson: "联系人", 
			spLocation: "所在地", 
			spContactPhone: "联系电话", 
			spContactMobilePhone :"联系手机", 
			spQQ: "qq"
	}
	
	var workderlabels = {
			workerName: "工人名", 
			idCard: "请输入身份证", 
			address: "地址", 
			mobilePhone: "手机号码", 
			workerType: "工人类型"
	}
	
	if(tableName == "Manufacturer"){
		labels = mfclabels;
	}
	if(tableName == "ServiceProvider"){
		labels = splabels;
	}
	if(tableName == "Worker"){
		labels = workderlabels;
	}
	if(data){
		var div = $("#logDetail");
		$(div).show();
		var jsonData = JSON.parse(data);
		for(var key in jsonData){		
			if(labels[key]){
				$('<div class="fitem width_455px"><span class="span_style_s"><label>' + labels[key] + ': </label></span><span class="span_style_s"><label class="width_360">' + jsonData[key] +'</label></span></div>').appendTo(div);
			}
		}

	}
}

function openDialog(id, title){
	$("#"+id).dialog('open').dialog('setTitle', title);		

	var o=$("#"+id);
    var itop=(document.documentElement.clientHeight-o.height())/2+$(document).scrollTop();
    var ileft=(document.documentElement.clientWidth-o.width())/2+document.documentElement.scrollLeft+document.body.scrollLeft;
 
	$('#'+id).dialog("move", {left:ileft, top:itop});

}


function formatterFindServiceOrderOperation(val, row){	
	if(row.poStatus !="MANUAL" && row.poStatus !="INACTIVE"){
		return '<button class=\"button_color1\" onclick=openProductServiceOrdersPage("' + row.id + '")>已配服务订单</button>';
	}
}




function s4() {
	return Math.floor((1 + Math.random()) * 0x10000).toString(16).substring(1);
};

function guid() {
	return s4() + s4() + '-' + s4() + '-' + s4() + '-' + s4() + '-' + s4()
			+ s4() + s4();
}

function checkMfcServiceType(arr) {
	loadServerType("mfc_category", arr, false);
}

function checkSpServiceType(arr) {

	loadServerType("sp_category", arr, true);
}

function loadServerType(id, checkedArra, isSp){
	
	postAjaxRequest("/ecs/category/listLevel1.do", {}, function(data) {
		var rows = data.rows;
		var div = $("#" + id);
         div.html("");
		
		for (index in rows) {
			var row = rows[index];
			var checked = false;
			if (checkedArra) {
				for ( var i = 0; i < checkedArra.length; i++) {
					if (row.id == checkedArra[i]) {
						checked = true;
						break;
					}
				}
			}
			
			if(isSp){
				if (checked) {
					$('<span class="span_style_s"><input type="checkbox" name="spServiceTypeArray[]" checked="true" id="' + row.id + id + '" value="' + row.id + '" /></span>').appendTo(div);
				} else {
					$('<span class="span_style_s"><input type="checkbox" name="spServiceTypeArray[]" id="' + row.id+ id + '" value="' + row.id + '" /></span>').appendTo(div);
				}
			}else{
				if (checked) {
					$('<span class="span_style_s"><input type="checkbox" name="mfcServiceTypeArray[]" checked="true" id="' + row.id+ id + '" value="' + row.id + '" /></span>').appendTo(div);
				} else {
					$('<span class="span_style_s"><input type="checkbox" name="mfcServiceTypeArray[]" id="' + row.id+ id + '" value="' + row.id + '" /></span>').appendTo(div);
				}
			}
			$('<span class="span_style_s"><label for="' + row.id+ id + '">' + row.name + '</label></span>').appendTo(div);
			if (index > 0 && (index % 3 == 0)) {
				$('<br>').appendTo(div);
			}
		}

	}, false);
}



var noticeArray = Array();
var msgIndex = 0;
function updateNoticDiv(msg){
	var find = false;
	for(index in noticeArray){
		var item = noticeArray[index];
		if(item.type == msg.type){
			item.msg = msg.msg;
			find = true;

			msgIndex = index;
			break;
		}
	
	}
	if(!find){
		noticeArray.push(msg);
	}
	 var tipDiv = $("#tips");
	 tipDiv.html("");
     $(msg.msg).appendTo(tipDiv);
     
}

function getNextmsg() {
	
	if(msgIndex >= noticeArray.length ){
		msgIndex = 0;
	}
	for (index in noticeArray) {
		if (index == msgIndex) {
			var item = noticeArray[index];
			msgIndex = msgIndex + 1;
		    var tipDiv = $("#tips");
			 tipDiv.html("");
		     $(item.msg).appendTo(tipDiv);
			break;
		}

	}
}


function pagerFilter(response){


    data = {
            total: response.rows.length,
            rows: response.rows
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


