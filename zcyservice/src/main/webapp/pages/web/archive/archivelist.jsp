<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>档案查询</title>
<script type="text/javascript" src="resources/js/ecommerce.js"></script>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<link href="/resources/css/easyui.css" rel="stylesheet"/>
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

</script>
</head>
<body>

    <div class="line_clear"></div>
	<div class="public_search_div">
		<div class="line_clear"></div>
		<div class="line_seach">
			<span class="span_style"><label class="display_nones">关键字：</label></span>
			<span class="span_style"><input id="mfcKeyword" class="public_search_input_text display_nones" /></span> 
			<span class="span_style"><label class="display_nones pdf">状态：</label></span>
			<span class="span_style"> 
			     <select class="easyui-combobox display_nones" style="width:128px;height:30px;background:url(/resources/images/public_select.png) no-repeat;" data-options="multiple:false" id="mfcSearchStatus">
					<option value="" selected>用户状态</option>
					<option value="NORMAL">正常</option>
					<option value="LOCKED">已冻结</option>
			     </select>
			</span> 
			<span class="span_style"><button class="public_search_btn display_nones" onclick="mfcsearch()"></button></span>
		</div>
		<div class="line_clear"></div>
	</div>
	<div style="margin-left:40px;">
			<table id="newmfc"  class="easyui-datagrid_tf" url="/ecs/archive/listArchives.do" iconCls="icon-save" sortOrder="asc" pagination="true" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true,width:900">
				<thead>
					<tr>
                        <th align="center"  field="archiveCode"  width="100"  sortable="false">档案编号</th>
                        <th align="center"  field="archiveName"  width="100"  sortable="false">档案名称</th>
                        <th align="center"  field="archiveStatus" width="100" sortable="false" >档案状态</th>
                        <th align="center"  field="createdOn" width="120" sortable="false" >档案归档时间</th>
                        <th align="center"  field="updatedOn" width="120" sortable="false" >档案修改时间</th>
                        
                        <th align="center" data-options="field:'id'" width="150">档案预览</th>
                    </tr>
				</thead>
			</table>
	</div>
	
	<script type="text/javascript">

  $(document).ready(function(){
	 
	
  });
  
  
  </script>
</body>
</html>