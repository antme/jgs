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
<link rel="stylesheet" href="/resources/css/upload/button.css" type="text/css" />
<script type="text/javascript" src="/resources/js/archive.js"></script>
<script type="text/javascript" src="/resources/js/upload/swfupload.js"></script>
<script type="text/javascript" src="/resources/js/upload/handlers.js"></script>
<script type="text/javascript" src="/resources/js/upload/swfus.js"></script>
</head>
<body>
    <div class="line_clear"></div>
           <div class="public_title">
              <div class="public_title_icon">​</div>​
              <label class="public_title_text">档案管理</label>
          </div>
        <div class="line_clear"></div>

    <%@ include file="/pages/web/archive/searcharchive.jsp"%>

    <div class="line_clear"></div>
    <div style="margin-left:40px;">
         <button  onclick="window.location.href='?p=web/archive/archiveedit'" class="btn_add" >新增档案</button>
    </div>
    <div class="line_clear"></div>
    <div style="margin-left:40px;">
       <span class="span_style">“<img height="16" width="16" src="/resources/images/print-preview.png" />”</span>
       <span class="span_style">代表预览</span>
       <span class="span_style">“<img height="16" width="16" src="/resources/images/table_edit.png" />”</span>
       <span class="span_style">代表编辑</span>
       <span class="span_style">“<img height="16" width="16" src="/resources/images/delect_btn.png" />”</span>
       <span class="span_style">代表销毁</span>
       <div class="span_style">档案只有审核过后才能开放查询</div>
    </div>
    <div style="margin-left:40px;">
            <table id="archiveList"  class="easyui-datagrid_tf" url="/ecs/archive/listNewArchives.do" iconCls="icon-save" sortOrder="asc" pagination="true" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true,width:900">
                <thead>
                    <tr>
                        <th align="center"  field="archiveCode"  width="140"  sortable="false" formatter="formatterArchiveStyle">案号</th>
                        <th align="center"  field="archiveApplicant"  width="60"  sortable="false">申请人</th>
                        <th align="center"  field="archiveName"  width="160"  sortable="false">案由</th>
                        <th align="center"  field="archiveType"  formatter="formatterArchiveType"  width="50"  sortable="false">类型</th>
                        <th align="center"  field="archiveProcessStatus" formatter="formatterArchiveProcessStatus" width="70" sortable="false" >审核状态</th>
                        
                        <th align="center"  field="archiveDate" width="85" formatter="showEstDateFormatter" sortable="false" >归档时间</th>
                        <th align="center" data-options="field:'eidt'" formatter="formatterArchiveEidt"  width="70">操作</th>
                    </tr>
                </thead>
            </table>
    </div>
    <div style="display:none;">
    
     
    <div id="delerecord" class="easyui-window" title="销毁档案" data-options="modal:true,closed:true,maximizable:false,minimizable:false,collapsible:false,iconCls:'icon-save'" style="width:800px;height:auto;padding:10px;">
            <form action="" id="delerecordForm" method="post" novalidate>
                <input id="did" type="hidden" name="id" />
                <div class="ac_div">
                    <span class="span_style"><label class="ac_title">销毁原因</label></span>
                    <span class="span_style border-left" style="padding:5px;">
                       <textarea id="destroyComments" name="destroyComments" rows="10" cols="59" ></textarea>
                    </span>
                </div>
             </form>
             <div style="text-align:center;padding:5px;">
                     <button class="btn_add" onclick="deletarchive()">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                     <button class="btn_add" onclick="closedwindows('delerecord')">取消</button>
             </div>
    </div>
  </div>
  
  <script type="text/javascript">
     $(document).ready(function(){
	    initArchiveManagePage();
	    
	    
	 });
     function formatterArchiveStyle(val,row){
         if (row.isNew==true || row.isNew=="true"){
             return '<div style="background:url(/resources/images/new.png) no-repeat 3px 0px;padding-left:20px;">'+val+'</div>';
         }
     }
  </script>

</body>
</html>