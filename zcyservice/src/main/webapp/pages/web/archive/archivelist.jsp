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
<script type="text/javascript" src="resources/js/archive.js"></script>


</head>
<body>

   <%@ include file="/pages/web/archive/searcharchive.jsp"%>
	<div style="margin-left:40px;">
			<table id="archiveList"  class="easyui-datagrid_tf" url="/ecs/archive/listArchives.do" iconCls="icon-save" sortOrder="asc" pagination="true" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true,width:900">
				<thead>
					<tr>
                        <th align="center"  field="archiveCode"  width="100"  sortable="false">档案编号</th>
                        <th align="center"  field="archiveName"  width="100"  sortable="false">档案名称</th>
                        <th align="center"  field="archiveStatus" width="100" sortable="false" >档案状态</th>
                        <th align="center"  field="createdOn" width="120" sortable="false" >档案归档时间</th>
                        <th align="center"  field="updatedOn" width="120" sortable="false" >档案修改时间</th>
                        
                         <th align="center" data-options="field:'id'" formatter="formatterArchiveView"  width="150">档案预览</th>
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