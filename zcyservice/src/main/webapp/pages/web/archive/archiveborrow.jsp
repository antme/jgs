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
        <button class="btn_add" onclick="openAddrecordWindow();">新增借阅记录</button>
    </div>
    <div class="line_clear"></div>
    <div style="margin-left:40px;">
            <table id="archiveList"  class="easyui-datagrid_tf" url="/ecs/archive/borrow/list.do" iconCls="icon-save" sortOrder="asc" pagination="true" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true,width:900">
                <thead>
                    <tr>
                        <th align="center"  field="archiveCode"  width="100"  sortable="false">案号</th>
                        <th align="center"  field="archiveName"  width="150"  sortable="false">案由</th>
                        <th align="center"  field="borrowingName" width="120" sortable="false" >调阅人</th>
                        <th align="center"  field="borrowingOrganization" width="220" sortable="false" >调阅单位</th>
                        <th align="center"  field="borrowingDate" width="120" sortable="false" >调阅日期</th>
                        
                        
                        <th align="center" data-options="field:'id'" formatter="formatterArchiveView"  width="100">备注</th>
                        <th align="center" data-options="field:'eidt'" formatter="formatterRecordEidt"  width="100">操作</th>
                    </tr>
                </thead>
            </table>
    </div>
    <div style="display:none;">
    <div id="addrecord" class="easyui-window" title="编辑科室信息" data-options="modal:true,closed:true,maximizable:false,minimizable:false,collapsible:false,iconCls:'icon-save'" style="width:800px;height:auto;padding:10px;">
            <form action="" id="addrecordForm" method="post" novalidate>
                <div class="ac_div" >
                    <span class="span_style"><label class="ac_title">调阅卷宗</label></span>
                    <span class="span_style border-left" style="padding:5px;">
                    <input id="archiveId" class="easyui-combobox" type="text"  data-options="loader: myloader,mode: 'remote',valueField: 'id',textField: 'name',width:128"/>
                    <input name="archiveId" type="hidden"/>
                    </span>
                </div>  
                <div class="ac_div">
                    <span class="span_style"><label class="ac_title">调阅人</label></span>
                    <span class="span_style border-left" style="padding:5px;"><input class="ac_input2 easyui-validatebox" type="text" name="borrowingName" required missingMessage="请输入调阅人"/></span>
                </div> 
                <div class="ac_div ">
                    <span class="span_style"><label class="ac_title">调阅单位</label></span>
                    <span class="span_style border-left"><input class="ac_input easyui-validatebox" type="text" name="borrowingOrganization" required missingMessage="请输入调阅单位"/></span>
                </div>
                <div class="ac_div ">
                    <span class="span_style"><label class="ac_title">调阅日期</label></span>
                    <span class="span_style border-left" style="padding:5px;"><input id="borrowingDate" class="ac_input easyui-datebox " type="text" name="borrowingDate" required missingMessage="请输选择调阅日期"/></span>
                </div>
                <div class="ac_div">
                    <span class="span_style"><label class="ac_title">备注</label></span>
                    <span class="span_style border-left" style="padding:5px;">
                       <textarea id="remark" name="remark" rows="10" cols="59" ></textarea>
                    </span>
                </div>
             </form>
   
            
                <div style="text-align:center;padding:5px;">
                     <button id="submitrecord" class="btn_add">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                     <button class="btn_add" onclick="closedwindows('addrecord')">取消</button>
                </div>
           <script>
       
    </script>
    </div>
  </div>

</body>
</html>