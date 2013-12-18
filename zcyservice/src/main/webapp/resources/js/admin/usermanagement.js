$(document)
		.ready(
				function() {
					// mfc
					$("#editMfcForm").form({
						success : function(data) {
							data = eval('(' + data + ')');
							var code = data.code;
							var msg = data.msg;
							if (code == 0) {
								alert(msg);
								return;
							} else {
								$('#editMfc').window('close');
								$('#newmfc').datagrid('reload');
							}
						}
					});
					$("#btn_mfc_s")
							.click(
									function() {
										if ($("#mfc_l_province").combobox(
												'getValue') == "省"
												|| $("#mfc_l_city").combobox(
														'getValue') == "市"
												|| $("#mfc_l_county").combobox(
														'getValue') == "区") {
											alert("请核对区域选择是否完整！");
										} else {
											$("#mfc_info_sum").click();
										}
									});
					// SP
					$("#sp-submit")
							.click(
									function() {
										if ($("#s_s_province").combobox(
												'getValue') == "省"
												|| $("#s_s_city").combobox(
														'getValue') == "市"
												|| $("#s_s_county").combobox(
														'getValue') == "区") {
											alert("请核对区域选择是否完整！");
										} else {
											$("#sp-submit-button").click();
										}
									});

					$("#spform").form({
						onSubmit : function() {
							return $(this).form('validate');
						},
						success : function(data) {
							data = eval('(' + data + ')');
							var code = data.code;
							var msg = data.msg;
							if (code == 0) {
								alert(msg);
								return;
							} else {
								$('#spwin').window('close');
								$('#newsp').datagrid('reload');
							}
						}
					});

					// User
					$("#customerform").form({
						success : function(data) {
							data = eval('(' + data + ')');
							var code = data.code;
							var msg = data.msg;
							if (code == 0) {
								alert(msg);
								return;
							} else {
								$('#customerwin').window('close');
								$('#newmuser').datagrid('reload');
							}
						}
					});

					// Worker
					$("#worker-submit").click(function() {

						var spid = $('#w_ownerId').combobox('getValue');
						$("#ownerId_hidden_id").val(spid);
						$("#worker-submit-button").click();
					});
					$("#workerform").form({
						onSubmit : function() {
							var input = $("#workerType").find("input");
							var value = "";
							$.each(input, function() {
								if ($(this).is(":checked")) {
									value += $(this).attr("value") + ",";
								}
							});
							$(this)[0].workerType.value = value;
							return $(this).form('validate');
						},
						success : function(data) {
							data = eval('(' + data + ')');
							var code = data.code;
							var msg = data.msg;
							if (code == 0) {
								alert(msg);
								return;
							} else {
								$('#workerwin').window('close');
								$('#newworker').datagrid('reload');
							}
						}
					});

					// Customer Service
					$("#csform").form({
						success : function(data) {
							data = eval('(' + data + ')');
							var code = data.code;
							var msg = data.msg;
							if (code == 0) {
								alert(msg);
								return;
							} else {
								$('#cswin').window('close');
								$('#newc-s').datagrid('reload');
							}
						}
					});

				});

function mfcLocationDropdown() {
	$('#mfc_l_province').combobox({
		url : '/ecs/location/listbyparent.do',
		valueField : 'id',
		textField : 'name',
		onSelect : function(rec) {
			var cityUrl = '/ecs/location/listbyparent.do?parent=' + rec.id;
			$('#mfc_l_city').combobox('reload', cityUrl);
			$("#mfc_l_city").combobox('select', '市');
			$("#mfc_l_county").combobox('select', '区');
		},
		loadFilter : function(data) {
			return data.rows;
		}
	});
	$("#mfc_l_province").combobox('select', '省');

	$('#mfc_l_city').combobox({
		valueField : 'id',
		textField : 'name',
		onSelect : function(rec) {
			var disUrl = '/ecs/location/listbyparent.do?parent=' + rec.id;
			$('#mfc_l_county').combobox('reload', disUrl);
			$("#mfc_l_county").combobox('select', '区');
		},
		loadFilter : function(data) {
			return data.rows;
		}
	});

	$('#mfc_l_county').combobox({
		valueField : 'id',
		textField : 'name',
		loadFilter : function(data) {
			return data.rows;
		}
	});
}

function spLocationDropdown() {
	$('#s_s_province').combobox({
		url : '/ecs/location/listbyparent.do',
		valueField : 'id',
		textField : 'name',
		onSelect : function(rec) {
			var cityUrl = '/ecs/location/listbyparent.do?parent=' + rec.id;
			$('#s_s_city').combobox('reload', cityUrl);
			$("#s_s_city").combobox('select', '市');
			$("#s_s_county").combobox('select', '区');
		},
		loadFilter : function(data) {
			return data.rows;
		}
	});
	$("#s_s_province").combobox('select', '省');

	$('#s_s_city').combobox({
		valueField : 'id',
		textField : 'name',
		onSelect : function(rec) {
			var disUrl = '/ecs/location/listbyparent.do?parent=' + rec.id;
			$('#s_s_county').combobox('reload', disUrl);
			$("#s_s_county").combobox('select', '区');
		},
		loadFilter : function(data) {
			return data.rows;
		}
	});

	$('#s_s_county').combobox({
		valueField : 'id',
		textField : 'name',
		loadFilter : function(data) {
			return data.rows;
		}
	});
}

function locationDropdown(pid, cid, aid) {
	var pidS = '#' + pid;
	var cidS = '#' + cid;
	var aidS = '#' + aid;
	$(pidS).combobox({
		url : '/ecs/location/listbyparent.do',
		valueField : 'id',
		textField : 'name',
		onSelect : function(rec) {
			var cityUrl = '/ecs/location/listbyparent.do?parent=' + rec.id;
			$(cidS).combobox('reload', cityUrl);
			$(cidS).combobox('select', '市');
			$(aidS).combobox('select', '区');
		},
		loadFilter : function(data) {
			return data.rows;
		}
	});
	$(pidS).combobox('select', '省');

	$(cidS).combobox({
		valueField : 'id',
		textField : 'name',
		onSelect : function(rec) {
			var disUrl = '/ecs/location/listbyparent.do?parent=' + rec.id;
			$(aidS).combobox('reload', disUrl);
			$(aidS).combobox('select', '区');
		},
		loadFilter : function(data) {
			return data.rows;
		}
	});

	$(aidS).combobox({
		valueField : 'id',
		textField : 'name',
		loadFilter : function(data) {
			return data.rows;
		}
	});
}

function cslockcallback() {
	$('#newc-s').datagrid('reload');
}
function workerdelcallback() {
	$('#newworker').datagrid('reload');
}
function workerlockcallback() {
	$('#newworker').datagrid('reload');
}
function customerlockcallback() {
	$('#newmuser').datagrid('reload');
}
function splockcallback() {
	$('#newsp').datagrid('reload');
}

function mfcsearch() {
	var keyword = $("#mfcKeyword").val();
	var status = $("#mfcSearchStatus").combobox('getValue');
	var param = {
		"keyword" : keyword,
		"userStatus" : status
	}
	$('#newmfc').datagrid('load', param);
}

function spsearch() {
	var keyword = $("#spKeyword").val();
	var status = $("#spSearchStatus").combobox('getValue');
	var param = {
		"keyword" : keyword,
		"userStatus" : status
	}
	$('#newsp').datagrid('load', param);
}

function workersearch() {
	var keyword = $("#workerKeyword").val();
	// var status = $("#workerSearchStatus").combobox('getValue');
	// var param = {"keyword":keyword,"userStatus":status}
	var param = {
		"keyword" : keyword
	}
	$('#newworker').datagrid('load', param);
}

function customersearch() {
	var keyword = $("#customerKeyword").val();
	var status = $("#customerSearchStatus").combobox('getValue');
	var param = {
		"keyword" : keyword,
		"userStatus" : status
	}
	$('#newmuser').datagrid('load', param);
}

function cssearch() {
	var keyword = $("#csKeyword").val();
	var status = $("#csSearchStatus").combobox('getValue');
	var param = {
		"keyword" : keyword,
		"userStatus" : status
	}
	$('#newc-s').datagrid('load', param);
}

// MFC
var mfc_string = "<li class=\"mfc_str\"> "
		+ "<div class=\"r-edit-label\">密码：</div>"
		+ "<div class=\"r-edit-field\">"
		+ "<input name=\"password\" validType=\"username\"  id=\"mfcpassword\" autocomplete=\"off\"  class=\"r-textbox at easyui-validatebox\" type=\"password\" required=\"true\" missingMessage=\"请输入密码\" /><label class=\"r-need\">*</label>"
		+ "<label>请输入6~16位有效密码</label>"
		+ "</div></li>"
		+ "<li class=\"mfc_str\"><div class=\"r-edit-label\">确认密码：</div>"
		+ "<div class=\"r-edit-field\">"
		+ "<input name=\"passwordConfirm\" autocomplete=\"off\"  class=\"r-textbox at easyui-validatebox\" type=\"password\" required=\"true\" missingMessage=\"请再次输入密码\"  validType=\"pwdEquals['#mfcpassword']\"/><label class=\"r-need\">*</label>"
		+ "</div></li>";

function addNewMfc() {
	$("#mfcId").val("");
	$("#mfcStoreName").val("");
	$("#mfcStoreName").removeAttr("disabled");
	$("#mfcCompanyName").val("");
	$("#mfcCompanyAdress").val("");
	$("#mfcContactPerson").val("");
	$("#mfcContactPhone").val("");
	$("#mfcContactMobilePhone").val("");
	$("#mfcQQ").val("");
	$("#mfcStatus").combobox('select', 'NORMAL');

	$("#mfc_l_province").combobox('select', '省');
	$("#mfc_l_city").combobox('select', '市');
	$("#mfc_l_county").combobox('select', '区');

	// $("#editMfc").attr("title","厂商-添加");
	$('#editMfc').window('setTitle', "厂商-添加");
	// $('#editMfc').window('open');
	openDialog("editMfc");
	var dd = $(".mfc_str");
	if (dd.length > 0) {
		$(".mfc_str").find("input").val("");
	} else {
		$('#editMfc').find(".passwd").after(mfc_string);
		$("#editMfcForm").submit();
	}
	var sfd = $("#c_info");
	if (sfd.length > 0) {
		$(sfd).remove();
	} else {

	}
	checkMfcServiceType();
	mfcLocationDropdown();
}

function mfcFormatterOperation(val, row) {
	var id = row.id;
	var userId = row.userId;
	var us = row.userStatus;
	var data = '"' + id + '"' + ',' + '"' + userId + '"' + ',' + '"' + us + '"';
	return '<button onclick=editMfc("' + id
			+ '")>编辑</button>&nbsp;&nbsp;<button onclick=lockMfc(' + data
			+ ');>冻结</button>&nbsp;&nbsp;<button onclick=unLockMfc(' + data
			+ ');>解冻</button>';
}

function editMfc(id) {
	var rows = $("#newmfc").datagrid('getRows');
	for ( var i = 0; i < rows.length; i++) {
		if (rows[i].id == id) {
			$("#mfcId").val(rows[i].id);
			$("#mfcStoreName").val(rows[i].mfcStoreName);
			$("#mfcStoreName").attr("disabled", "true");
			var sfd = $("#c_info");
			if (sfd.length > 0) {
			} else {
				$(".cc").append("<label id=\"c_info\">不能修改</label>");
			}

			$("#mfcCompanyName").val(rows[i].mfcCompanyName);
			$("#mfcCompanyAdress").val(rows[i].mfcCompanyAdress);
			$("#mfcContactPerson").val(rows[i].mfcContactPerson);
			$("#mfcContactPhone").val(rows[i].mfcContactPhone);
			$("#mfcContactMobilePhone").val(rows[i].mfcContactMobilePhone);
			$("#mfcQQ").val(rows[i].mfcQQ);
			$("#mfcStatus").combobox('select', rows[i].userStatus);
			if (rows[i].mfcServiceType == undefined) {
				checkMfcServiceType();
			} else {
				var listStr = rows[i].mfcServiceType;
				var list = new Array();
				list = listStr.split(",");
				checkMfcServiceType(list);
			}

			mfcLocationDropdown();
			var pid = rows[i].mfcLocationProvinceId;
			var cid = rows[i].mfcLocationCityId;
			var cityUrl = '/ecs/location/listbyparent.do?parent=' + pid;
			$('#mfc_l_city').combobox('reload', cityUrl);
			var disUrl = '/ecs/location/listbyparent.do?parent=' + cid;
			$('#mfc_l_county').combobox('reload', disUrl);

			$("#mfc_l_province").combobox('select',
					rows[i].mfcLocationProvinceId);
			$("#mfc_l_city").combobox('select', rows[i].mfcLocationCityId);
			$("#mfc_l_county").combobox('select', rows[i].mfcLocationAreaId);
			break;
		}
	}
	$('#editMfc').window('setTitle', "厂商-编辑");
	// $('#editMfc').window('open');
	openDialog("editMfc");
	$('#editMfc').find(".mfc_str").remove();
}

function lockMfc(id, userId, us) {
	if (us == 'LOCKED') {
		alert("该用户已冻结！");
		return;
	}
	var data = {
		"id" : userId
	};
	postAjaxRequest("/ecs/user/lock.do", data, mfclockcallback);
}

function unLockMfc(id, userId, us) {
	if (us == 'NORMAL') {
		alert("该用户未冻结！");
		return;
	}
	var data = {
		"id" : userId
	};
	postAjaxRequest("/ecs/user/unlock.do", data, mfclockcallback);
}

function mfclockcallback() {
	$('#newmfc').datagrid('reload');
}

// SP

var sp_string = "<li class=\"sp_str\"> "
		+ "<div class=\"r-edit-label\">密码：</div>"
		+ "<div class=\"r-edit-field\">"
		+ "<input name=\"password\" validType=\"username\" id=\"sppassword\" autocomplete=\"off\" onfocus=\"this.type='password'\" class=\"r-textbox at easyui-validatebox\" type=\"password\" required=\"true\" missingMessage=\"请输入密码\" /><label class=\"r-need\">*</label>"
		+ "<label>请输入6~16位有效密码</label>"
		+ "</div></li>"
		+ "<li class=\"sp_str\"><div class=\"r-edit-label\">确认密码：</div>"
		+ "<div class=\"r-edit-field\">"
		+ "<input name=\"passwordConfirm\" autocomplete=\"off\" onfocus=\"this.type='password'\" class=\"r-textbox at easyui-validatebox\" type=\"password\" required=\"true\" missingMessage=\"请再次输入密码\"  validType=\"pwdEquals['#mfcpassword']\"/><label class=\"r-need\">*</label>"
		+ "</div></li>";

function addNewSp() {
	$("#spId").val("");
	$("#spUserName").val("");
	$("#spUserName").removeAttr("disabled");
	var sfd = $("#f_info");
	if (sfd.length > 0) {
		$(sfd).remove();
	} else {

	}
	$("#spCompanyName").val("");
	$("#spCompanyAddress").val("");
	$("#spContactPerson").val("");
	$("#spContactPhone").val("");
	$("#spContactMobilePhone").val("");
	$("#spQQ").val("");
	/* $("#spBranchAddress").val(""); */
	$("#spLicenseNo").val("");
	$("#spLicenseImage").attr("src", "");
	$("#storeImage").attr("src", "");
	$("#storeImageShow").attr("src", "");
	$("#spStatus").combobox('select', 'NORMAL');
	$("#s_s_city").combobox('select', '市');
	$("#s_s_county").combobox('select', '区');
	$('#spwin').window('setTitle', '服务商-添加');
	// $('#spwin').window('open');
	openDialog("spwin");
	var dd = $(".sp_str");
	if (dd.length > 0) {
		$(".sp_str").find("input").val("");
	} else {
		$('#spwin').find(".passwd").after(sp_string);
	}

	checkSpServiceType();
	spLocationDropdown();
}

function spFormatterOperation(val, row) {
	var id = row.id;
	var userId = row.userId;
	var us = row.userStatus;
	var data = '"' + id + '"' + ',' + '"' + userId + '"' + ',' + '"' + us + '"';
	return '<button onclick=editSp("' + id
			+ '")>编辑</button>&nbsp;&nbsp;<button onclick=lockSp(' + data
			+ ');>冻结</button>&nbsp;&nbsp;<button onclick=unLockSp(' + data
			+ ');>解冻</button>';
}

function editSp(id) {
	var rows = $("#newsp").datagrid('getRows');
	for ( var i = 0; i < rows.length; i++) {
		if (rows[i].id == id) {
			$("#spId").val(rows[i].id);
			$("#spUserName").val(rows[i].spUserName);
			$("#spUserName").attr("disabled", "disabled");
			var sfd = $("#f_info");
			if (sfd.length > 0) {
			} else {
				$(".ff").append("<label id=\"f_info\">不能修改</label>");
			}
			$("#spCompanyName").val(rows[i].spCompanyName);
			$("#spCompanyAddress").val(rows[i].spCompanyAddress);
			$("#spContactPerson").val(rows[i].spContactPerson);
			$("#spContactPhone").val(rows[i].spContactPhone);
			$("#spContactMobilePhone").val(rows[i].spContactMobilePhone);
			$("#spQQ").val(rows[i].spQQ);
			/* $("#spBranchAddress").val(rows[i].spBranchAddress); */
			$("#spStatus").combobox('select', rows[i].userStatus);
			var radio = $("#spCompanySize").find("input");
			$.each(radio, function() {
				if ($(this).val() == rows[i].spCompanySize) {
					$(this).click();
				}
			});
			var listStr = rows[i].spServiceType;
			var list = new Array();
			list = listStr.split(",");
			checkSpServiceType(list);
			spLocationDropdown();
			var pid = rows[i].spLocationProvinceId;
			var cid = rows[i].spLocationCityId;
			var cityUrl = '/ecs/location/listbyparent.do?parent=' + pid;
			$('#s_s_city').combobox('reload', cityUrl);
			var disUrl = '/ecs/location/listbyparent.do?parent=' + cid;
			$('#s_s_county').combobox('reload', disUrl);

			$("#s_s_province").combobox('select', rows[i].spLocationProvinceId);
			$("#s_s_city").combobox('select', rows[i].spLocationCityId);
			$("#s_s_county").combobox('select', rows[i].spLocationAreaId);

			if (rows[i].spLicenseNo) {
				$("#spLicenseImage").attr("src", rows[i].spLicenseNo);
			}
			if (rows[i].storeImage) {
				$("#storeImageShow").attr("src", rows[i].storeImage);
			}
			break;
		}
	}
	$('#spwin').window('setTitle', '服务商-编辑');
	// $('#spwin').window('open');
	openDialog("spwin");
	$('#spwin').find(".sp_str").remove();
}

function lockSp(id, userId, us) {
	if (us == 'LOCKED') {
		alert("该用户已冻结！");
		return;
	}
	var data = {
		"id" : userId
	};
	postAjaxRequest("/ecs/user/lock.do", data, splockcallback);
}

function unLockSp(id, userId, us) {
	if (us == 'NORMAL') {
		alert("该用户未冻结！");
		return;
	}
	var data = {
		"id" : userId
	};
	postAjaxRequest("/ecs/user/unlock.do", data, splockcallback);
}

// Worker
function addNewWorker() {
	$("#workerId").val("");

	$("#w_workerName").val("");
	$("#w_idCard").val("");
	$("#w_idCard").removeAttr("disabled");
	var sfd = $("#g_info");
	if (sfd.length > 0) {
		$(sfd).remove();
	} else {

	}
	$("#w_address").val("");
	$("#w_mobilePhone").val("");
	$("#workerType").find("input").removeAttr("checked");
	// $("#w_isActive").combobox('select','1');
	$("#w_ownerId").combobox('select', null);

	$('#workerwin').window('setTitle', '工人-添加');
	// $('#workerwin').window('open');
	openDialog("workerwin");
}

function workerFormatterOperation(val, row) {
	var id = row.id;
	var us = row.isActive;
	var data = '"' + id + '"' + ',' + '"' + us + '"';
	return '<button onclick=editWorker("' + row.id
			+ '")>编辑</button>&nbsp;&nbsp;<button onclick=delWorker("' + row.id
			+ '");>删除</button>';
	// return '<button onclick=editWorker("' + row.id + '")>编辑</button><button
	// onclick=lockWorker(' + data + ');>冻结</button><button
	// onclick=unLockWorker(' + data + ');>解冻</button>';
}

function editWorker(id) {
	var rows = $("#newworker").datagrid('getRows');
	$("#workerType").find("input").removeAttr("checked");
	for ( var i = 0; i < rows.length; i++) {
		if (rows[i].id == id) {
			$("#spId").val(rows[i].id);
			$("#workerId").val(rows[i].id);
			$("#w_workerName").val(rows[i].workerName);
			$("#w_idCard").val(rows[i].idCard);
			$("#w_idCard").attr("disabled", "disabled");
			var sfd = $("#g_info");
			if (sfd.length > 0) {

			} else {
				$(".gg").append("<label id=\"g_info\">不能修改</label>");
			}
			$("#w_address").val(rows[i].address);
			$("#w_mobilePhone").val(rows[i].mobilePhone);
			// $("#w_isActive").combobox('select',rows[i].isActive);
			$("#w_ownerId").combobox('select', rows[i].ownerId);
			var workerType = $("#workerType").find("input");
			if (rows[i].workerType != undefined) {
				var sdt = rows[i].workerType.split(",");
				for ( var j = 0; j < workerType.length; j++) {
					for ( var i = 0; i < sdt.length; i++) {
						if ($(workerType[j]).attr("value") == sdt[i]) {
							$(workerType[j]).click();
						}
					}
				}
			}
			break;
		}
	}
	$('#workerwin').window('setTitle', '工人-编辑');
	// $('#workerwin').window('open');
	openDialog("workerwin");
}

function delWorker(id) {
	if (confirm("是否确定删除此工人记录?")) {
		var data = {
			"id" : id
		};
		postAjaxRequest("/ecs/sp/worker/del.do", data, workerdelcallback);
	} else {
		return;
	}

}

function lockWorker(id, userStatus) {
	if (userStatus == 'true') {
		var data = {
			"id" : id
		};
		postAjaxRequest("/ecs/sp/worker/lock.do", data, workerlockcallback);
	} else {
		alert("该用户已冻结！");
		return;
	}
}

function unLockWorker(id, userStatus) {
	if (userStatus == 'true') {
		alert("该用户未冻结！");
		return;
	}
	var data = {
		"id" : id
	};
	postAjaxRequest("/ecs/sp/worker/unlock.do", data, workerlockcallback);
}

// Customer

var cu_string = "<li class=\"cu_str\"> "
		+ "<div class=\"r-edit-label\">密码：</div>"
		+ "<div class=\"r-edit-field\">"
		+ "<input name=\"password\" validType=\"username\" id=\"khpassword\" autocomplete=\"off\" onfocus=\"this.type='password'\" class=\"r-textbox at easyui-validatebox\" type=\"password\" required=\"true\" missingMessage=\"请输入密码\" /><label class=\"r-need\">*</label>"
		+ "<label>请输入6~16位有效密码</label>"
		+ "</div></li>"
		+ "<li class=\"cu_str\"><div class=\"r-edit-label\">确认密码：</div>"
		+ "<div class=\"r-edit-field\">"
		+ "<input name=\"passwordConfirm\" autocomplete=\"off\" onfocus=\"this.type='password'\" class=\"r-textbox at easyui-validatebox\" type=\"password\" required=\"true\" missingMessage=\"请再次输入密码\"  validType=\"pwdEquals['#mfcpassword']\"/><label class=\"r-need\">*</label>"
		+ "</div></li>";

function addNewCustomer() {
	$("#customerId").val("");
	$("#u_name").val("");
	$("#u_phone").val("");
	$("#u_mobileNumber").val("");
	$("#u_addresses").val("");
	$("#u_defaultAddress").val("");
	$("#u_sex").combobox('select', 'male');
	$("#u_status").combobox('select', 'NORMAL');
	$("#u_ct_city").combobox('select', '市');
	$("#u_ct_county").combobox('select', '区');
	$('#customerwin').window('setTitle', '客户-添加');
	// $('#customerwin').window('open');
	openDialog("customerwin");
	locationDropdown('u_ct_province', 'u_ct_city', 'u_ct_county');
	var dd = $(".cu_str");
	if (dd.length > 0) {
		$(".cu_str").find("input").val("");
	} else {
		$("#customerwin").find(".passwd").after(cu_string);
	}

}

function customerFormatterOperation(val, row) {
	var id = row.id;
	var us = row.status;
	var data = '"' + id + '"' + ',' + '"' + us + '"';
	return '<button onclick=editCustomer("' + row.id
			+ '")>编辑</button>&nbsp;&nbsp;<button onclick=lockCustomer(' + data
			+ ');>冻结</button>&nbsp;&nbsp;<button onclick=unLockCustomer('
			+ data + ');>解冻</button>';
}

function editCustomer(id) {
	var rows = $("#newmuser").datagrid('getRows');
	for ( var i = 0; i < rows.length; i++) {
		if (rows[i].id == id) {
			$("#customerId").val(rows[i].id);
			$("#u_name").val(rows[i].name);
			$("#u_phone").val(rows[i].phone);
			$("#u_mobileNumber").val(rows[i].mobileNumber);
			$("#u_addresses").val(rows[i].addresses);
			$("#u_defaultAddress").val(rows[i].defaultAddress);
			$("#u_sex").combobox('select', rows[i].sex);
			$("#u_status").combobox('select', rows[i].status);

			locationDropdown('u_ct_province', 'u_ct_city', 'u_ct_county');
			var pid = rows[i].userLocationProvinceId;
			var cid = rows[i].userLocationCityId;
			var cityUrl = '/ecs/location/listbyparent.do?parent=' + pid;
			$('#u_ct_city').combobox('reload', cityUrl);
			var disUrl = '/ecs/location/listbyparent.do?parent=' + cid;
			$('#u_ct_county').combobox('reload', disUrl);

			$("#u_ct_province").combobox('select',
					rows[i].userLocationProvinceId);
			$("#u_ct_city").combobox('select', rows[i].userLocationCityId);
			$("#u_ct_county").combobox('select', rows[i].userLocationAreaId);
			break;
		}
	}
	$('#customerwin').window('setTitle', '客户-编辑');
	// $('#customerwin').window('open');
	openDialog("customerwin");
	$('#customerwin').find('.cu_str').remove();
}

function lockCustomer(id, userStatus) {
	if (userStatus == 'LOCKED') {
		alert("该用户已冻结！");
		return;
	}
	var data = {
		"id" : id
	};
	postAjaxRequest("/ecs/user/lock.do", data, customerlockcallback);
}

function unLockCustomer(id, userStatus) {
	if (userStatus == 'NORMAL') {
		alert("该用户未冻结！");
		return;
	}
	var data = {
		"id" : id
	};
	postAjaxRequest("/ecs/user/unlock.do", data, customerlockcallback);
}

// Customer Service

var cs_string = "<li class=\"cs_str\"> "
		+ "<div class=\"r-edit-label\">密码：</div>"
		+ "<div class=\"r-edit-field\">"
		+ "<input name=\"password\" validType=\"username\" id=\"kfpassword\" autocomplete=\"off\" onfocus=\"this.type='password'\" class=\"r-textbox at easyui-validatebox\" type=\"password\" required=\"true\" missingMessage=\"请输入密码\" /><label class=\"r-need\">*</label>"
		+ "<label>请输入6~16位有效密码</label>"
		+ "</div></li>"
		+ "<li class=\"cs_str\"><div class=\"r-edit-label\">确认密码：</div>"
		+ "<div class=\"r-edit-field\">"
		+ "<input name=\"passwordConfirm\" autocomplete=\"off\" onfocus=\"this.type='password'\" class=\"r-textbox at easyui-validatebox\" type=\"password\" required=\"true\" missingMessage=\"请再次输入密码\"  validType=\"pwdEquals['#mfcpassword']\"/><label class=\"r-need\">*</label>"
		+ "</div></li>";

function addNewCs() {
	$("#csId").val("");
	$("#cs_userName").val("");
	$("#cs_userName").removeAttr("readonly");
	$("#cs_name").val("");
	// $("#cs_phone").val("");
	$("#cs_mobileNumber").val("");
	// $("#cs_addresses").val("");
	// $("#cs_defaultAddress").val("");
	$("#cs_userQQ").val("");
	$("#cs_userCode").val("");
	$("#cs_sex").combobox('select', 'male');
	$("#cs_status").combobox('select', 'NORMAL');

	$("#u_cs_city").combobox('select', '市');
	$("#u_cs_county").combobox('select', '区');

	$('#cswin').window('setTitle', '客服-添加');
	// $('#cswin').window('open');
	openDialog("cswin");
	var dd = $(".cs_str");
	if (dd.length > 0) {
		$(".cs_str").find("input").val("");
	} else {
		$('#cswin').find('.passwd').after(cs_string);
	}

	locationDropdown('u_cs_province', 'u_cs_city', 'u_cs_county');
}

function csFormatterOperation(val, row) {
	var id = row.id;
	var us = row.userStatus;
	var data = '"' + id + '"' + ',' + '"' + us + '"';
	return '<button onclick=editCs("' + row.id
			+ '")>编辑</button>&nbsp;&nbsp;<button onclick=lockCs(' + data
			+ ');>冻结</button>&nbsp;&nbsp;<button onclick=unLockCs(' + data
			+ ');>解冻</button>';
}

function editCs(id) {
	var rows = $("#newc-s").datagrid('getRows');
	for ( var i = 0; i < rows.length; i++) {
		if (rows[i].id == id) {
			$("#csId").val(rows[i].id);
			$("#cs_userName").val(rows[i].userName);
			$("#cs_userName").attr("readonly", "readonly");
			$("#cs_name").val(rows[i].name);
			$("#cs_userExtPhone").val(rows[i].userExtPhone);
			// $("#cs_phone").val(rows[i].phone);
			$("#cs_mobileNumber").val(rows[i].mobileNumber);
			// $("#cs_addresses").val(rows[i].addresses);
			// $("#cs_defaultAddress").val(rows[i].defaultAddress);
			$("#cs_userQQ").val(rows[i].userQQ);
			$("#cs_userCode").val(rows[i].userCode);

			$("#cs_sex").combobox('select', rows[i].sex);
			$("#cs_status").combobox('select', rows[i].status);

			locationDropdown('u_cs_province', 'u_cs_city', 'u_cs_county');
			var pid = rows[i].userLocationProvinceId;
			var cid = rows[i].userLocationCityId;
			var cityUrl = '/ecs/location/listbyparent.do?parent=' + pid;
			$('#u_cs_city').combobox('reload', cityUrl);
			var disUrl = '/ecs/location/listbyparent.do?parent=' + cid;
			$('#u_cs_county').combobox('reload', disUrl);

			$("#u_cs_province").combobox('select',
					rows[i].userLocationProvinceId);
			$("#u_cs_city").combobox('select', rows[i].userLocationCityId);
			$("#u_cs_county").combobox('select', rows[i].userLocationAreaId);
			break;
		}
	}
	$('#cswin').window('setTitle', '客服-编辑');
	// $('#cswin').window('open');
	openDialog("cswin");
	$("#cswin").find(".cs_str").remove();
}

function lockCs(id, userStatus) {
	if (userStatus == 'LOCKED') {
		alert("该用户已冻结！");
		return;
	}
	var data = {
		"id" : id
	};
	postAjaxRequest("/ecs/user/lock.do", data, cslockcallback);
}

function unLockCs(id, userStatus) {
	if (userStatus == 'NORMAL') {
		alert("该用户未冻结！");
		return;
	}
	var data = {
		"id" : id
	};
	postAjaxRequest("/ecs/user/unlock.do", data, cslockcallback);
}

function openSpRegionalPage(id) {

	idParam = id;
	var config = {
		width : 850,
		height : 500,
		modal : true,
		title : "服务区域"
	};
	loadRemotePage("web/sp/myregional", config);
}
