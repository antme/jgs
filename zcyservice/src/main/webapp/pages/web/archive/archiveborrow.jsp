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
       <div style="margin-left:50px;">
        <button class="btn_add" onclick="openAddGroupWindow();">新增借阅记录</button>
    </div>
    <div class="line_clear"></div>
    <div style="margin-left:40px;">
            <table id="newmfc"  class="easyui-datagrid_tf" url="/ecs/archive/listArchives.do" iconCls="icon-save" sortOrder="asc" pagination="true" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true,width:900">
                <thead>
                    <tr>
                        <th align="center"  field="archiveCode"  width="100"  sortable="false">档案编号</th>
                        <th align="center"  field="archiveName"  width="150"  sortable="false">案由</th>
                        <th align="center"  field="borrowingName" width="120" sortable="false" >调阅人</th>
                        <th align="center"  field="borrowingOrganization" width="220" sortable="false" >调阅单位</th>
                        <th align="center"  field="borrowingDate" width="120" sortable="false" >调阅日期</th>
                        
                        
                        <th align="center" data-options="field:'id'" formatter="formatterArchiveView"  width="100">备注</th>
                    </tr>
                </thead>
            </table>
    </div>
    

</body>
</html>