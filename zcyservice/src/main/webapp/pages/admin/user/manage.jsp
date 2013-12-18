<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户管理</title>
<script type="text/javascript" src="resources/js/ecommerce.js"></script>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resources/js/admin/usermanagement.js"></script>
<script type="text/javascript" src="resources/js/city-price.public.js"></script>
<script type="text/javascript" src="/resources/js/public_css.js"></script>
<script type="text/javascript" src="resources/js/validation.js"></script>

<script type="text/javascript">
 function mfcAndSpstatusformatter(val, row, rowindex){
	   var status = row.userStatus;
	   if (status == 'NORMAL'){
		   return "正常";
	   }else if (status == 'LOCKED'){
		   return "已冻结";
	   }else{
		   return "";
	   }
	} 
 function userstatusformatter(val, row, rowindex){
	   var status = row.status;
	   if (status == 'NORMAL'){
		   return "正常";
	   }else if (status == 'LOCKED'){
		   return "已冻结";
	   }else{
		   return "";
	   }
	} 
 /* function workerstatusformatter(val, row, rowindex){
	   var status = row.isActive;
	   if (status){
		   return "正常";
	   }else{
		   return "已冻结";
	   }
	} */ 
 function genderformatter(val, row, rowindex){
	   var gender = row.sex;
	   if (gender == 'male'){
		   return "男";
	   }else if (gender == 'female'){
		   return "女";
	   }else{
		   return "";
	   }
	} 
</script>
</head>
<body>
	<div id="callbackdiv" style="display:none,height:10px,width:auto,border:solid 1px red,background:#CAFFFF"></div>
	<div class="easyui-tabs" id="userManagementTabs">
		<div title="厂商" style="padding: 10px">
			<button id="addNewMfc" class="display_nones" onclick="addNewMfc()">添加</button><!-- <button id="lockMfcBtn">冻结</button><button id="unlockMfcBtn">解冻</button><br/> -->
			<div class="line_clear"></div>
			<div class="line_seach">
			   <span class="span_style"><label class="display_nones">关键字：</label></span>
			   <span class="span_style"><input style="width:200px" id="mfcKeyword" class="tpublic_input r-textboxd display_nones"/></span>
			   <span class="span_style"><label class="display_nones pdf">状态：</label></span>
			   <span class="span_style">
			      <select class="easyui-combobox display_nones" style="width:128px" data-options="multiple:false" id="mfcSearchStatus">
              	     <option value="" selected>用户状态</option>
             	     <option value="NORMAL">正常</option>
            	     <option value="LOCKED" >已冻结</option>
                  </select>
               </span>
       		   <span class="span_style"><button class="public_btn display_nones" onclick="mfcsearch()"></button></span>
       		</div>
       		<div class="line_clear"></div>
			<table id="newmfc"  class="easyui-datagrid_tab" iconCls="icon-save" sortOrder="asc" pagination="true" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true">
				<thead>
					<tr>
                        <th align="center"  field="mfcCode"  width="70"  sortable="false">系统编号</th>
                        <th align="center"  field="mfcStoreName"  width="100"  sortable="false">店铺名称</th>
                        <th align="center"  field="mfcCompanyName" width="100" sortable="false" >公司名称</th>
                        <th align="center"  field="mfcLocation" width="120" sortable="false" >所在地</th>
                        <th align="center"  field="mfcCompanyAdress" width="120" sortable="false" >公司地址</th>
                        <th align="center"  field="mfcContactPerson"  width="60" sortable="false" >联系人</th>
                        <th align="center"  field="mfcContactMobilePhone" width="80" >联系手机</th>
                        <th align="center"  field="createdOn" width="80" data-options="formatter:showEstDateFormatter">注册日期</th>
                        <th align="center"  field="userStatus"  data-options="formatter:mfcAndSpstatusformatter" width="60">状态</th>
                        <th data-options="field:'id',formatter:mfcFormatterOperation" width="150">操作</th>
                    </tr>
				</thead>
			</table>
		</div>
		<div title="服务商" style="padding: 10px">
			<button id="addnewspBtn" class="display_nones" onclick="addNewSp()">添加</button><!-- <button id="lockSpBtn">冻结</button><button id="unlockSpBtn">解冻</button><br/> -->
			<div class="line_clear"></div>
			<div class="line_seach">
			<span class="span_style"><label class="display_nones">关键字：</label></span>
			<span class="span_style"><input style="width:200px" id="spKeyword" class="tpublic_input r-textboxd display_nones"/></span>
			<span class="span_style"><label class="display_nones pdf">状态：</label></span>
			<span class="span_style">
			<select class="easyui-combobox display_nones" style="width:128px"  data-options="multiple:false" id="spSearchStatus">
            	<option value="" selected>用户状态</option>
            	<option value="NORMAL">正常</option>
            	<option value="LOCKED" >已冻结</option>
            </select>
            </span>
       		<span class="span_style"><button class="public_btn display_nones" onclick="spsearch()"></button></span>
       		</div>
       		<div class="line_clear"></div>
			<table id="newsp" class="easyui-datagrid_tab" iconCls="icon-save" sortOrder="asc" pagination="true" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true">
				<thead>
					<tr>
                        <th align="center"  field="spCode" width="70" sortable="false">系统编号</th>
                        <th align="center"  field="spUserName" width="100px"  sortable="false">用户名</th>
                        <th align="center"  field="spCompanyName" width="100px"  sortable="false">公司名称</th>
                        <!-- <th align="center"  field="spCompanyAddress" width="100px"  sortable="false">公司地址</th> -->
                        <th align="center"  field="spLocation" width="150px" sortable="false" >所在地</th>
                        <th align="center"  field="spContactPerson" width="60" sortable="false" >联系人</th>
                        <th align="center"  field="spContactMobilePhone" width="80" sortable="false" >联系手机</th>
                        <!-- <th align="center"  field="spLevel" width="30px" sortable="false" >综合服务评级</th> -->
                        <th align="center"  field="spLicenseNo" width="70px" sortable="false"  formatter="preViewImageFormatter">营业执照</th>
                        <th align="center"  field="createdOn" width="80" data-options="formatter:showEstDateFormatter">注册日期</th>
                        <th align="center"  field="userStatus" width="30px" data-options="formatter:mfcAndSpstatusformatter" >状态</th>
                        <th data-options="field:'id',formatter:spFormatterOperation" width="100" >操作</th>
                    </tr>
				</thead>
			</table>
		</div>
		<div title="工人" style="padding: 10px">
			<button id="addnewworkerBtn" class="display_nones" onclick="addNewWorker()">添加</button><!-- <button id="lockWorkerBtn">冻结</button><button id="unlockWorkerBtn">解冻</button><br/> -->
			<div class="line_clear"></div>
			<div class="line_seach">
			<span class="span_style"><label class="display_nones">关键字：</label></span>
			<span class="span_style"><input style="width:200px" id="workerKeyword" class="tpublic_input r-textboxd display_nones"/></span>
			<!-- <span class="span_style"><label>状态：</label></span>
			<span class="span_style">
			<select class="easyui-combobox"  data-options="multiple:false" id="workerSearchStatus">
            	<option value="" selected>用户状态</option>
            	<option value="1">正常</option>
            	<option value="0" >已冻结</option>
            </select>
            </span> -->
       		<span class="span_style"><button class="public_btn display_nones" onclick="workersearch()"></button></span>
       		</div>
       		<div class="line_clear"></div>
			<table id="newworker" iconCls="icon-save" class="easyui-datagrid_tab" sortOrder="asc" pagination="true" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true">
				<thead>
					<tr>
                        <th align="center"  field="workerName" width="60" sortable="false" >工人名</th>
                        <th align="center"  field="mobilePhone" width="100" sortable="false" >手机号码</th>
                        <th align="center"  field="idCard" width="120" sortable="false" >身份证</th>
                        <th align="center"  field="address" width="200"  sortable="false">地址</th>
                        <th align="center"  field="userName" width="80"  sortable="false">服务商</th>
                        <!-- <th align="center"  field="isActive" width="80"  sortable="false" data-options="formatter:workerstatusformatter">状态</th> -->
                        <th data-options="field:'id',formatter:workerFormatterOperation" width="100" >操作</th>
                    </tr>
				</thead>
			</table>
		</div>
		<div title="买家" style="padding: 10px">
			<button id="addnewcustomerBtn" class="display_nones" onclick="addNewCustomer()">添加</button><!-- <button id="lockCustomerBtn">冻结</button><button id="unlockCustomerBtn">解冻</button><br/> -->
			<div class="line_clear"></div>
			<div class="line_seach">
			<span class="span_style"><label class="display_nones">关键字：</label></span>
			<span class="span_style"><input style="width:200px" id="customerKeyword" class="tpublic_input r-textboxd display_nones"/></span>
			<span class="span_style"><label class="display_nones pdf">状态：</label></span>
			<span class="span_style">
			<select class="easyui-combobox display_nones" style="width:128px" data-options="multiple:false" id="customerSearchStatus">
            	<option value="" selected>用户状态</option>
            	<option value="NORMAL">正常</option>
            	<option value="LOCKED" >已冻结</option>
            </select>
            </span>
       		<span class="span_style"><button class="public_btn display_nones" onclick="customersearch()"></button></span>
       		</div>
       		<div class="line_clear"></div>
			<table id="newmuser" class="easyui-datagrid_tab" iconCls="icon-save" sortOrder="asc" pagination="true" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true">
				<thead>
					<tr>
                        <th align="center"  field="userName" width="80" sortable="false" >登录名</th>
                        <th align="center"  field="name" width="80" sortable="false" >姓名</th>
                        <th align="center"  field="sex" width="40" sortable="false"  data-options="formatter:genderformatter">性别</th>
                        <!-- <th align="center"  field="addresses" width="80"  sortable="false">地址</th> -->
                        <th align="center"  field="userLocation" width="130" sortable="false">所在地</th>
                        <th align="center"  field="mobileNumber" width="100" >联系人手机</th>
                        <th align="center"  field="status" width="40"  data-options="formatter:userstatusformatter">状态</th>
                        <th align="center" data-options="field:'id',formatter:customerFormatterOperation" width="140" >操作</th>
                    </tr>
				</thead>
			</table>
		</div>
		<div title="客服" style="padding: 10px">
			<button id="addnewCSBtn" class="display_nones" onclick="addNewCs()">添加</button>
			<div class="line_clear"></div>
			<div class="line_seach">
			<span class="span_style"><label class="display_nones">关键字：</label></span>
			<span class="span_style"><input style="width:200px" id="csKeyword" class="tpublic_input r-textboxd display_nones"/></span>
			<span class="span_style"><label class="display_nones pdf">状态：</label></span>
			<span class="span_style">
			<select class="easyui-combobox display_nones" style="width:128px"  data-options="multiple:false" id="csSearchStatus">
            	<option value="" selected>用户状态</option>
            	<option value="NORMAL">正常</option>
            	<option value="LOCKED" >已冻结</option>
            </select>
            </span>
       		<span class="span_style"><button class="public_btn display_nones" onclick="cssearch()"></button></span>
       		</div>
       		<div class="line_clear"></div>
			<table id="newc-s" class="easyui-datagrid_tab" iconCls="icon-save" sortOrder="asc" pagination="true" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true">
				<thead>
					<tr>
                        <th align="center"  field="userName" sortable="false"  width="80">用户名</th>
                        <th align="center"  field="userCode" sortable="false"  width="80">客服编号</th>
                        <th align="center"  field="name" sortable="false"  width="80">姓名</th>
                        <th align="center"  field="sex" sortable="false"  data-options="formatter:genderformatter"  width="60">性别</th>
                        <th align="center"  field="userExtPhone"  sortable="false"  width="100">分机号</th>
                        <th align="center"  field="mobileNumber"   width="100">手机</th>
                        <th align="center"  field="status"  data-options="formatter:userstatusformatter" width="60">状态</th>
                        <th align="center" data-options="field:'id',formatter:csFormatterOperation" width="150">操作</th>
                    </tr>
				</thead>
			</table>
		</div>
	</div>
	
	<div id="manage_form" style="display:none;">
	<div id="editMfc" class="easyui-window" data-options="modal:true,closed:true,minimizable:false,maximizable:false,collapsible:false,iconCls:'icon-save',top:50" style="width:650px;height:auto;padding:10px; top:50px;">
		<form id="editMfcForm" action="/ecs/mfc/add.do" method="post">
			<input type="hidden" name ="id" id="mfcId"/>
			
			     <ul class="f-information">
					<li class="passwd">
						<div class="r-edit-label">店铺名称：</div>
						<div class="r-edit-field cc">
							<input name="mfcStoreName" id="mfcStoreName" class="r-textbox easyui-validatebox"
								type="text" missingMessage="请输入店铺名称"/> <label class="r-need">*</label><label id="c_info">不能修改</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">公司名称：</div>
						<div class="r-edit-field">
							<input id="mfcCompanyName" name="mfcCompanyName" class="r-textbox easyui-validatebox"
								required type="text" missingMessage="请输入公司名称" /> <label
								class="r-need">*</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">主营服务类型：</div>
						<div class="r-edit-field r-select-style" style="height:auto">
							<div class=" r-margin-left"  style="margin-left:0px; height:auto;float:left;">
								<div class="r-edit-field" id="mfc_category" style="width:300px; height:auto;margin-left:-5px;">                                
                                                          
                               </div>
							</div>
							<label class="r-need">*</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">所在地：</div>
						<div class="r-edit-field" style="width:411px">
							<div class="r-select">
								<input id="mfc_l_province" name="mfcLocationProvinceId"  class="easyui-combobox ap" data-options="valueField:'id',textField:'text',width:128,height:35,multiple:false"/>
                                <input id="mfc_l_city" name="mfcLocationCityId" class="easyui-combobox ap" data-options="valueField:'id',textField:'text',width:128,height:35,multiple:false"/>
                                <input id="mfc_l_county" name="mfcLocationAreaId" class="easyui-combobox ap" data-options="valueField:'id',textField:'text',width:128,height:35,multiple:false"/>
							</div>
						</div>
					</li>
					<li>
						<div class="r-edit-label">公司地址：</div>
						<div class="r-edit-field2">
							<input id="mfcCompanyAdress" name="mfcCompanyAdress"
								class="r-textbox2 r-update easyui-validatebox" type="text" style="*+margin-left:0px;"
								required missingMessage="请输入公司地址" /> <label class="r-need">*</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">联系人：</div>
						<div class="r-edit-field">
							<input id="mfcContactPerson" name="mfcContactPerson"
								class="r-textbox easyui-validatebox" type="text" required
								missingMessage="请输入联系人" /> <label class="r-need">*</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">联系电话：</div>
						<div class="r-edit-field">
							<input id="mfcContactPhone" name="mfcContactPhone" class="r-textbox" type="text" />
						</div>
					</li>
					<li>
						<div class="r-edit-label">联系手机：</div>
						<div class="r-edit-field">
							<input id="mfcContactMobilePhone" name="mfcContactMobilePhone" class="r-textbox easyui-validatebox"
								type="text" required missingMessage="请输入联系手机" validtype ="mobile" /> <label
								class="r-need">*</label>
						</div>
					</li>
					<li> 
						<div class="r-edit-label">QQ：</div>
						<div class="r-edit-field">
							<input id="mfcQQ" name="mfcQQ" class="r-textbox" type="text" />
						</div>
					</li>
					<li>
						<div class="r-edit-label">当前状态：</div>
						<div class="r-edit-field">
							<select class="easyui-combobox" style="width:128px"  data-options="multiple:false" name="mfcStatus" id="mfcStatus">
	                        	<option value="NORMAL" selected>正常</option>
	                        	<option value="LOCKED" >已冻结</option>
	                        </select>
						</div>
					</li>
					
					<li>
					    <div class="r-edit-label"></div>
                        <div class="r-edit-field4">
                            <button id="btn_mfc_s" type="button" class="r-submit fw">提交</button>
                            <input id="mfc_info_sum" style="display:none" type="submit" value="提交" />
                        </div>
					</li>
				</ul>
		</form>
	</div>
	
	<div id="spwin" class="easyui-window" title="服务商" data-options="modal:true,closed:true,maximizable:false,minimizable:false,collapsible:false,iconCls:'icon-save',top:50" style="width:700px;height:auto;padding:10px;top:50px;">

		<form action="/ecs/sp/adminadd.do" id="spform" method="post" novalidate enctype="multipart/form-data">
			
			<input type="hidden" name ="id" id="spId"/>
				<ul class="f-information">
					<li class="passwd">
						<div class="r-edit-label">用户名：</div>
						<div class="r-edit-field ff">
							<input name="spUserName" id="spUserName" class="r-textbox easyui-validatebox"
								type="text"  /> <label class="r-need">*</label><label id="f_info">不能修改</label>
						</div>
					</li>
					
					<li>
						<div class="r-edit-label">主营服务类型：</div>
						<div class="r-edit-field r-select-style" style="height:auto">
							<div class=" r-margin-left"  style="margin-left:0px; height:auto;float:left;">
								<div class="r-edit-field" id="sp_category" style="width:300px; height:auto;margin-left:-5px;">                                
                                                          
                               </div>
							</div>
							<label class="r-need">*</label>
						</div>
					</li>
					<li>
                        <div class="r-edit-label">工人数：</div>
                        <div class="r-edit-field" id="spCompanySize" >
                             <input id="10" type="radio" name="spCompanySize" value="0~10人" checked="checked"/><label for="10">0~10人</label>
                             <input id="11" type="radio" name="spCompanySize" value="10~50人"/><label for="11">10~50人</label>
                             <input id="12" type="radio" name="spCompanySize" value="50~100人"/><label for="12">50~100人</label>
                             <input id="13" type="radio" name="spCompanySize" value="100人以上"/><label for="13">100人以上</label>
                        </div>
                    </li>
					<!-- <li>
						<div class="r-edit-label">主营服务类型：</div>
						<div class="r-edit-field r-select-style">
							<select class="easyui-combobox" name="spServiceType" id="spServiceType">
	                        	<option value="111">111</option>
	                        	<option value="222" selected>222</option>
	                        	<option value="333">333</option>
	                        </select>
	                        <label class="r-need">*</label>
						</div>
					</li> -->
					<li>
						<div class="r-edit-label">公司名称：</div>
						<div class="r-edit-field">
							<input id="spCompanyName" name="spCompanyName" class="r-textbox easyui-validatebox"
								required type="text" missingMessage="请输入公司名称" /> <label
								class="r-need">*</label>
						</div>
					</li>
					
					<li>
						<div class="r-edit-label">所在地：</div>
						<div class="r-edit-field" style="width:411px;">
							<div class="r-select">
								<input id="s_s_province" name="spLocationProvinceId" class="easyui-combobox ap" data-options="valueField:'id',textField:'text',width:128,height:35,multiple:false"/>
                                <input id="s_s_city" name="spLocationCityId" class="easyui-combobox ap" data-options="valueField:'id',textField:'text',width:128,height:35,multiple:false"/>
                                <input id="s_s_county" name="spLocationAreaId" class="easyui-combobox ap" data-options="valueField:'id',textField:'text',width:128,height:35,multiple:false"/>
							</div>
						</div>
					</li>
					<li>
						<div class="r-edit-label">公司地址：</div>
						<div class="r-edit-field2">
							<input id="spCompanyAddress" name="spCompanyAddress"
								class="r-textbox2 r-update easyui-validatebox" type="text"
								required missingMessage="请输入公司地址" /> <label class="r-need">*</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">联系人：</div>
						<div class="r-edit-field">
							<input id="spContactPerson" name="spContactPerson"
								class="r-textbox easyui-validatebox" type="text" required
								missingMessage="请输入联系人" /> <label class="r-need">*</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">固定电话：</div>
						<div class="r-edit-field">
							<input id="spContactPhone" name="spContactPhone" class="r-textbox" type="text"/>
						</div>
					</li>
					<li>
						<div class="r-edit-label">联系手机：</div>
						<div class="r-edit-field">
							<input id="spContactMobilePhone" name="spContactMobilePhone" class="r-textbox easyui-validatebox"
								type="text" required missingMessage="请输入手机" validtype ="mobile"/> <label
								class="r-need">*</label>
						</div>
					</li>
					<li style="position: relative;">
						<div class="r-edit-label">营业执照：</div>
						<div class="r-edit-field r-margin-height">
							<input id="spLicenseNo" name="spLicenseNo" class="r-textbox" 
								type="file" validType="fileType['GIF|JPG|PNG']" invalidMessage="请选择(GIF|JPG|PNG)等格式的图片"/>
						</div>
						<div style="position: absolute;right:-74px; top:-120px; display:none">
							<img src="" id="spLicenseImage" height="150" width="300"/>
						</div>
					</li>
					<li style="position: relative;">
						<div class="r-edit-label">企业形象图片：</div>
						<div class="r-edit-field r-margin-height">
							<input id="storeImage" name="storeImage" class="r-textbox" type="file" validType="fileType['GIF|JPG|PNG']" invalidMessage="请选择(GIF|JPG|PNG)等格式的图片" />
							
						</div>
						<div style="position: absolute;right:-74px; top:10px; display:none"">
							<img src="" id="storeImageShow" height="150" width="300"/>
						</div>
					</li>
					<li>
						<div class="r-edit-label">QQ：</div>
						<div class="r-edit-field">
							<input id="spQQ" name="spQQ" class="r-textbox" type="text" />
						</div>
					</li>
<!-- 					<li>
						<div class="r-edit-label">网点：</div>
						<div class="r-edit-field">
							<input id="spBranchAddress" name="spBranchAddress" class="r-textbox" type="text" />
						</div>
					</li> -->
					<li>
						<div class="r-edit-label">当前状态：</div>
						<div class="r-edit-field">
							<select class="easyui-combobox"  style="width:128px" data-options="multiple:false" name="spStatus" id="spStatus">
	                        	<option value="NORMAL" selected>正常</option>
	                        	<option value="LOCKED" >已冻结</option>
	                        </select>
						</div>
					</li>
					
					<li>
						<div>
							
						<!-- <input type="submit" value="提交" /><a class="r-submit fw" id="sp-submit">提交</a> -->
						<div class="r-edit-label"></div>
                        <div class="r-edit-field4">
                            <input type="submit" value="Submit" style="display: none;" id="sp-submit-button" /> 
                            <button type="button" class="r-submit fw" id="sp-submit">提交</button>
                        </div>
						</div>
					</li>
				</ul>
			</form>
	</div>
	
	<div id="customerwin" class="easyui-window" title="客户" data-options="modal:true,closed:true,maximizable:false,minimizable:false,collapsible:false,iconCls:'icon-save',top:50" style="width:650px;height:auto;padding:10px;top:50px;">
		<form action="/ecs/user/adminadd.do" id="customerform" method="post" novalidate>
			
			<input type="hidden" name ="id" id="customerId"/>
			<input type="hidden" name ="roleName" id="roleName" value="USER"/>
				<ul class="f-information">
					
					<li class="passwd">
						<div class="r-edit-label">姓名：</div>
						<div class="r-edit-field">
							<input name="name" id="u_name" class="r-textbox easyui-validatebox" type="text"/>
						</div>
					</li>
					
					<li>
						<div class="r-edit-label">性别：</div>
						<div class="r-edit-field r-select-style">
							<select class="easyui-combobox" style="width:128px" data-options="multiple:false" name="sex" id="u_sex">
	                        	<option value="male">男</option>
	                        	<option value="female" selected>女</option>
	                        </select>
						</div>
					</li>
					<li>
						<div class="r-edit-label">电话：</div>
						<div class="r-edit-field">
							<input id="u_phone" name="phone" class="r-textbox" type="text"/>
						</div>
					</li>
					<li>
						<div class="r-edit-label">手机：</div>
						<div class="r-edit-field">
							<input id="u_mobileNumber" name="mobileNumber" class="r-textbox easyui-validatebox"
								required type="text" missingMessage="请输入手机号码" validtype ="mobile"/> <label
								class="r-need">*</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">所在地：</div>
						<div class="r-edit-field" style="width:411px;">
							<div class="r-select">
								<input id="u_ct_province" name="userLocationProvinceId" class="easyui-combobox ap" data-options="valueField:'id',textField:'text',width:128,height:35,multiple:false"/>
                                <input id="u_ct_city" name="userLocationCityId" class="easyui-combobox ap" data-options="valueField:'id',textField:'text',width:128,height:35,multiple:false"/>
                                <input id="u_ct_county" name="userLocationAreaId" class="easyui-combobox ap" data-options="valueField:'id',textField:'text',width:128,height:35,multiple:false"/>
							</div>
						</div>
					</li>
					<li>
						<div class="r-edit-label">地址：</div>
						<div class="r-edit-field2">
							<input id="u_addresses" name="addresses"
								class="r-textbox2 r-update " type="text"/>
						</div>
					</li>
					<!-- <li>
						<div class="r-edit-label">所在地：</div>
						<div class="r-edit-field2">
							<input id="u_defaultAddress" name="defaultAddress"
								class="r-textbox2 r-update " type="text"/>
						</div>
					</li> -->
					<li>
						<div class="r-edit-label">当前状态：</div>
						<div class="r-edit-field">
							<select class="easyui-combobox" style="width:128px"  data-options="multiple:false" name="status" id="u_status">
	                        	<option value="NORMAL" selected>正常</option>
	                        	<option value="LOCKED" >已冻结</option>
	                        </select>
						</div>
					</li>
					
					<li>
						<div class="r-edit-label"></div>
                        <div class="r-edit-field4">
						<input type="submit" value="提交" />
						</div>
					</li>
				</ul>
			</form>
	</div>
	
	<div id="workerwin" class="easyui-window" title="工人" data-options="modal:true,closed:true,maximizable:false,minimizable:false,collapsible:false,iconCls:'icon-save',top:50" style="width:650px;height:340px;padding:10px;top:50px;">
		<form action="/ecs/sp/adminaddWorker.do" id="workerform" method="post" novalidate>
			
			<input type="hidden" name ="id" id="workerId"/>
				<ul class="f-information">
					<li>
						<div class="r-edit-label">工人名：</div>
						<div class="r-edit-field">
							<input name="workerName" id="w_workerName" class="r-textbox easyui-validatebox"
								type="text" required missingMessage="请输入工人名" /> <label
								class="r-need">*</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">身份证：</div>
						<div class="r-edit-field gg">
							<input name="idCard" id="w_idCard" class="r-textbox easyui-validatebox"
								type="text"  missingMessage="请输入身份证" validtype ="idcard"/> <label
								class="r-need">*</label><label id="g_info">不能修改</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">地址：</div>
						<div class="r-edit-field">
							<input name="address" id="w_address" class="r-textbox easyui-validatebox"
								type="text" required missingMessage="请输入地址" /> <label
								class="r-need">*</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">手机号码：</div>
						<div class="r-edit-field">
							<input name="mobilePhone" id="w_mobilePhone" class="r-textbox easyui-validatebox"
								type="text" required missingMessage="请输入手机号码" validtype ="mobile"/> <label
								class="r-need">*</label>
						</div>
					</li>
					<li>
                        <div class="r-edit-label">工人类型 ：</div>
                        <div id="workerType" class="r-edit-field" >
                            <input id="20" type="checkbox"  value="水电工"/><label for="20">水电工</label>
                            <input id="21" type="checkbox"  value="泥瓦工"/><label for="21">泥瓦工</label>
                            <input id="22" type="checkbox"  value="木工"/><label for="22">木工</label>
                            <input id="23" type="checkbox"  value="油漆工"/><label for="23">油漆工</label>
                        </div>
                    </li>
                    <input id ="workerTypes" type="hidden" name="workerType"/>
					<!-- <li>
						<div class="r-edit-label">状态：</div>
						<div class="r-edit-field r-select-style">
							<select class="easyui-combobox"  data-options="multiple:false" id="w_isActive">
	                        	<option value="1" selected>正常</option>
	                        	<option value="0">已冻结</option>
	                        </select>
	                        <input type="hidden" name = "isActive" id="isActive_hidden_id"/>
						</div>
					</li> -->
					
					<li>
						<div class="r-edit-label">所属服务商：</div>
						<div class="r-edit-field">
						
							<input style="width:128px" class="easyui-combobox" id="w_ownerId" required missingMessage="请选择服务商"
							            data-options="
							                    valueField:'userId',
							                    textField:'spUserName',
							                    url:'/ecs/sp/select.do',
							                    panelHeight:'auto',
							                    multiple:false,
							                    loadFilter:function(data){
							                    	return data.rows;
							                    }"/>
							<input type="hidden" name = "ownerId" id="ownerId_hidden_id"/>
						</div>
					</li>
					
					<li>
						<div>
						<div class="r-edit-label"></div>
                        <div class="r-edit-field4">
                            <input type="submit" value="Submit" style="display: none;" id="worker-submit-button" /> 
                            <button type="button" class="r-submit fw" id="worker-submit">提交</button>
                        </div>
						</div>
					</li>
					
					<!-- <li>
						<div class="r-edit-label"></div>
                        <div class="r-edit-field4">
						<input type="submit" value="提交" />
						</div>
					</li> -->
				</ul>
			</form>
	</div>
	
	<div id="cswin" class="easyui-window" title="客服" data-options="modal:true,closed:true,minimizable:false,maximizable:false,collapsible:false,iconCls:'icon-save',top:50" style="width:650px;height:550px;padding:10px;top:50px;">
		<form action="/ecs/user/adminadd.do" id="csform" method="post" novalidate>
			<input type="hidden" name ="id" id="csId"/>
			<input type="hidden" name ="roleName" id="cs_roleName" value="CUSTOMER_SERVICE"/>
				<ul class="f-information">
					<li>
						<div class="r-edit-label">客服编号：</div>
						<div class="r-edit-field">
							<input name="userCode" id="cs_userCode" class="r-textbox easyui-validatebox" disabled type="text"/>
							<label>客服编号自动生成</label>
						</div>
					</li>
					<li class="passwd">
						<div class="r-edit-label">登陆名：</div>
						<div class="r-edit-field">
							<input name="userName" id="cs_userName" class="r-textbox easyui-validatebox"
								type="text" required missingMessage="请输入登陆名" /> <label
								class="r-need">*</label>
						</div>
					</li>
					<!-- <li class="passwd">
                        <div class="r-edit-label">密码：</div>
                        <div class="r-edit-field">
                            <input name="password"  id="kfpassword" autocomplete="off" onfocus="this.type='password'" class="r-textbox at easyui-validatebox"
                                type="password" required="true" missingMessage="请输入密码" /><label class="r-need">*</label>
                        </div>
                    </li>
                    <li class="passwd">
                        <div class="r-edit-label">确认密码：</div>
                        <div class="r-edit-field">
                            <input name="passwordConfirm" autocomplete="off" onfocus="this.type='password'"
                                class="r-textbox at easyui-validatebox" type="password" required="true"
                                missingMessage="请再次输入密码"  validType="pwdEquals['#kfpassword']"/><label class="r-need">*</label>
                        </div>
                    </li> -->
					<li>
						<div class="r-edit-label">姓名：</div>
						<div class="r-edit-field">
							<input name="name" id="cs_name" class="r-textbox easyui-validatebox"
								type="text" required missingMessage="请输入姓名" /> <label
								class="r-need">*</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">性别：</div>
						<div class="r-edit-field r-select-style">
							<select class="easyui-combobox" style="width:128px" data-options="multiple:false" name="sex" id="cs_sex">
	                        	<option value="male">男</option>
	                        	<option value="female" selected>女</option>
	                        </select>
	                        <label class="r-need">*</label>
						</div>
					</li>
					<!-- <li>
						<div class="r-edit-label">电话：</div>
						<div class="r-edit-field">
							<input id="cs_phone" name="phone" class="r-textbox" type="text"  /> <label
								class="r-need">*</label>
						</div>
					</li> -->
					<li>
						<div class="r-edit-label">手机：</div>
						<div class="r-edit-field">
							<input id="cs_mobileNumber" name="mobileNumber" class="r-textbox easyui-validatebox"
								required type="text" missingMessage="请输入手机号码" validtype ="mobile"/> <label class="r-need">*</label>
						</div>
					</li>
					<li>
						<div class="r-edit-label">分机号：</div>
						<div class="r-edit-field">
							<input id="cs_userExtPhone" name="userExtPhone" class="r-textbox " type="text"/>
						</div>
					</li>
					<li>
						<div class="r-edit-label">QQ：</div>
						<div class="r-edit-field">
							<input id="cs_userQQ" name="userQQ" class="r-textbox" type="text"/>
						</div>
					</li>
					<li>
						<div class="r-edit-label">所在地：</div>
						<div class="r-edit-field" style="width:411px;">
							<div class="r-select">
								<input id="u_cs_province" name="userLocationProvinceId" class="easyui-combobox ap" data-options="valueField:'id',textField:'text',width:128,height:35,multiple:false"/>
                                <input id="u_cs_city" name="userLocationCityId" class="easyui-combobox ap" data-options="valueField:'id',textField:'text',width:128,height:35,multiple:false"/>
                                <input id="u_cs_county" name="userLocationAreaId" class="easyui-combobox ap" data-options="valueField:'id',textField:'text',width:128,height:35,multiple:false"/>
							</div>
						</div>
					</li>
					<!-- <li>
						<div class="r-edit-label">地址：</div>
						<div class="r-edit-field2">
							<input id="cs_addresses" name="addresses"
								class="r-textbox2 r-update easyui-validatebox" type="text"
								required missingMessage="请输入地址" /> <label class="r-need">*</label>
						</div>
					</li> -->
					<!-- <li>
						<div class="r-edit-label">所在地：</div>
						<div class="r-edit-field2">
							<input id="cs_defaultAddress" name="defaultAddress"
								class="r-textbox2 r-update easyui-validatebox" type="text"
								required missingMessage="请输入所在地" /> <label class="r-need">*</label>
						</div>
					</li> -->
					<li>
						<div class="r-edit-label">当前状态：</div>
						<div class="r-edit-field">
							<select class="easyui-combobox" style="width:128px;"  data-options="multiple:false" name="status" id="cs_status">
	                        	<option value="NORMAL" selected>正常</option>
	                        	<option value="LOCKED" >已锁定</option>
	                        </select>
						</div>
					</li>
					
					<li>
						<div class="r-edit-label"></div>
                        <div class="r-edit-field4">
						<input type="submit" value="提交" />
						</div>
					</li>
				</ul>
			</form>
	</div>
	</div>
	<script type="text/javascript">

  $(document).ready(function(){
	$(".display_nones").removeClass("display_nones");
	$.extend($.fn.validatebox.defaults.rules, {
        pwdEquals: {
            validator: function(value,param){
                return value == $(param[0]).val();
            },
            message: '密码不匹配'
        }
    });
    $('#userManagementTabs').tabs({
		    border:false,
		    onSelect:function(title){
		    	if(title=="厂商"){
		    	     $('#newmfc').datagrid({
		    	         url:'/ecs/mfc/manage.do'
		    	     });
		    	}else if(title=="服务商"){   	
		    		 $('#newsp').datagrid({
	                     url:'/ecs/sp/manage.do',
		    	         singleSelect:true
		    	         /*queryParams: {
	                         listType: 2,
	                         mobilePhone:customerMobilePhone
	                     } */
	               });                 
		    	}else if(title=="工人"){    	
		    		 $('#newworker').datagrid({
	                     url:'/ecs/sp/worker/manage.do',
		    	         singleSelect:true
	               });                 
		    	}else if(title=="买家"){    	
		    		 $('#newmuser').datagrid({
	                     url:'/ecs/user/manage.do?roleName=user',
	                     /* queryParams: {
	                         roleName: "user"
	                     }, */
		    	         singleSelect:true
	               });                 
		    	}else if(title=="客服"){    	
		    		 $('#newc-s').datagrid({
	                     url:'/ecs/user/manage.do?roleName=customer_service',
	                     /* queryParams: {
	                    	 roleName: "customer_service"
	                     }, */
		    	         singleSelect:true
	               });                 
		    	}
		    }
		});
    });
       </script>
</body>
</html>