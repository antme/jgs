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
    <div class="line_clear"></div>
           <div class="public_title">
              <div class="public_title_icon">​</div>​
              <label class="public_title_text">档案查询</label>
    </div>
    <div class="line_clear"></div>
   <%@ include file="/pages/web/archive/searcharchive.jsp"%>
    <div class="line_clear"></div>
     <div style="margin-left:40px;">
       <span class="span_style">“<img height="16" width="16" src="/resources/images/print-preview.png" />”</span>
       <span class="span_style">代表预览</span>
       
    </div>
	<div style="margin-left:40px;">
			<table id="archiveList"  class="easyui-datagrid_tf" url="/ecs/archive/listPubArchives.do" iconCls="icon-save" sortOrder="asc" pagination="true" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true,width:900">
				<thead>
					<tr>
                        <th align="center"  field="archiveCode"  width="100"  sortable="false">案号</th>
                        <th align="center"  field="archiveApplicant"  width="60"  sortable="false">申请人</th>
                        <th align="center"  field="archiveName"  width="160"  sortable="false">案由</th>
                        <th align="center"  field="archiveType"  formatter="formatterArchiveType"  width="50"  sortable="false">类型</th>
                        <th align="center"  field="archiveStatus" formatter="formatterArchiveStatus"  width="100" sortable="false" >档案状态</th>

                        <th align="center"  field="archiveDate" width="85" formatter="showEstDateFormatter" sortable="false" >归档时间</th>
                        <th align="center" data-options="field:'id'" formatter="formatterArchiveView"  width="50">预览</th>
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