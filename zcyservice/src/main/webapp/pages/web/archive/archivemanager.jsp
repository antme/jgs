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
    
    <div style="margin-left:40px;">
        <a href="?p=web/archive/archiveedit"><button class="btn_add" >新增档案</button></a>
    </div>
    <div class="line_clear"></div>
    <div style="margin-left:40px;">
            <table id="archiveList"  class="easyui-datagrid_tf" url="/ecs/archive/listArchives.do" iconCls="icon-save" sortOrder="asc" pagination="true" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true,width:900">
                <thead>
                    <tr>
                        <th align="center"  field="archiveCode"  width="110"  sortable="false">案号</th>
                        <th align="center"  field="archiveName"  width="150"  sortable="false">案由</th>
                        <th align="center"  field="archiveType"  formatter="formatterArchiveType"  width="50"  sortable="false">类型</th>
                        <th align="center"  field="archiveStatus" formatter="formatterArchiveStatus"  width="70" sortable="false" >档案状态</th>
                        <th align="center"  field="archiveProcessStatus" formatter="formatterArchiveProcessStatus" width="70" sortable="false" >审核状态</th>
                        
                        <th align="center"  field="createdOn" width="100" sortable="false" >归档时间</th>
                        <th align="center" data-options="field:'id'" formatter="formatterArchiveView"  width="60">预览</th>
                        <th align="center" data-options="field:'eidt'" formatter="formatterArchiveEidt"  width="100">操作</th>
                    </tr>
                </thead>
            </table>
    </div>
    <div style="display:none;">
    <div id="addarchive" class="easyui-window" title="编辑科室信息" data-options="modal:true,closed:true,maximizable:false,minimizable:false,collapsible:false,iconCls:'icon-save'" style="width:900px;height:auto;padding:10px;">
       <div class="easyui-tabs" style="height:auto;">
            <div title="基本信息" style="padding: 10px">
                <form action="" id="addarchiveForm" method="post" novalidate>
                    <input  type="hidden" name="year" id="year"/>
                    <div class="width100 font18 margintop10">上 海 市 虹 口 区 劳 动 人 事 争 议 仲 裁 委 员 会 </div>
                    <div class="width100 font24 fontweight margintop20">仲 裁 卷 宗</div>
                    <div class="ac_div">
                    <span class="span_style"><label class="ac_title">卷宗编号</label></span>
                    <span class="span_style border-left span_width"><input class="ac_input2 easyui-validatebox" type="text" name="archiveCode" required missingMessage="请输入卷宗编号"/></span>
                    </div>  
                    <div class="ac_div">
                    <span class="span_style"><label class="ac_title">案&nbsp;&nbsp;&nbsp;&nbsp;由</label></span>
                    <span class="span_style border-left"><input class="ac_input easyui-validatebox" type="text" name="archiveName" required missingMessage="请输入卷案由"/></span>
                    </div>
                    <div class="ac_div">
                    <span class="span_style"><label class="ac_title">处理结果</label></span>
                    <span class="span_style border-left"><input class="ac_input easyui-validatebox" type="text" name="archiveResult" required missingMessage="请输入处理结果"/></span>
                    </div> 
                    <div class="ac_div overflow">
                      <label class="ac_title2 width_101px font16">
                                                                                    当<br>
                                                                                    事<br>
                                                                                    人<br>
                      </label>
                      <div class="ac_div2">
                         <div class="ac_div width_500px">
                              <span class="span_style"><label class="ac_title2">申请人</label></span>
                              <span class="span_style border-left span_width"><input class="ac_input2 easyui-validatebox" type="text" name="archiveApplicant" required missingMessage="请输入申请人"/></span>
                         </div>
                         <div class="ac_div width_500px">
                              <span class="span_style"><label class="ac_title2">被申请人</label></span>
                              <span class="span_style border-left span_width"><input class="ac_input2 easyui-validatebox" type="text" name="archiveOppositeApplicant" required missingMessage="请输入被申请人"/></span>
                         </div>
                         <div class="width_500px">
                            <span class="span_style"><label class="ac_title2">第三人</label></span>
                            <span class="span_style border-left span_width"><input class="ac_input2" type="text" name="archiveThirdPerson" /></span>
                         </div>
                         </div>
                    </div>
                    <div class="ac_div">
                    <span class="span_style"><label class="ac_title">承办人</label></span>
                    <span class="span_style border-left span_width"><input class="ac_input2 easyui-validatebox" type="text" name="archiveJudge" required missingMessage="请输入承办人"/></span>
                    </div>
                    <div class="ac_div">
                    <div class="ac_div">
                         <span class="span_style"><label class="ac_title2">立案日期</label></span>
                         <span class="span_style border-left-right span_width2"><input id="archiveOpenDate" class="ac_input2 easyui-datebox " type="text" name="archiveOpenDate" required missingMessage="请输选择立案日期"/></span>
                         <span class="span_style"><label class="ac_title2">结案日期</label></span>
                         <span class="span_style border-left span_width2"><input id="archiveCloseDate" class="ac_input2 easyui-datebox " type="text" name="archiveCloseDate" required missingMessage="请输选择结案日期"/></span>
                    </div>
                    <div>
                         <span class="span_style"><label class="ac_title2">归档日期</label></span>
                         <span class="span_style border-left-right span_width2"><input id="archiveDate" class="easyui-datebox" type="text" name="archiveDate" /></span>
                         <span class="span_style"><label class="ac_title2">归档号数</label></span>
                         <span class="span_style border-left span_width"><input class="ac_input2" type="text" name="archiveSerialNumber" /></span>
                    </div>
                    </div>
                    <input id="mainFile" type="hidden" name="mainFile"/>
                    <input id="mainFilkeAttach" type="hidden" name="mainFilkeAttach"/>
                    <input id="secondFile" type="hidden" name="secondFile"/>
                    <input id="secondFileAttach" type="hidden" name="secondFileAttach"/>
                 </form>
             </div>
            <div title="附件上传" style="padding: 10px">
               <div id="fj">
                <div class="ac_div">
                    <span class="span_style"><label class="ac_title2 ">正卷宗</label></span>
                    <span class="span_style span_left_border">
                              <div style="display: inline; border: solid 1px #7FAAFF; background-color: #C5D9FF; padding: 5px; *+padding:2px; _padding:2px;">
                              <span id="spanButtonPlaceholder"></span>
                              <input id="btnUpload" type="button" value="上  传" style="width:60px;"
                                 onclick="startUploadFile();" class="btn3_mouseout" onMouseUp="this.className='btn3_mouseup'"
                                 onmousedown="this.className='btn3_mousedown'"
                                 onMouseOver="this.className='btn3_mouseover'"
                                 onmouseout="this.className='btn3_mouseout'"/>
                             <input id="btnCancel" type="button" value="取消所有上传" style="width:120px;"
                                 onclick="cancelUpload();" disabled="disabled" class="btn3_mouseout" onMouseUp="this.className='btn3_mouseup'"
                                 onmousedown="this.className='btn3_mousedown'"
                                 onMouseOver="this.className='btn3_mouseover'"
                                 onmouseout="this.className='btn3_mouseout'"/>
                             </div>
                             <div id="divFileProgressContainer"></div>
                             <div id="thumbnails">
                             <table id="infoTable" border="0" width="530" style="display: none; border: solid 1px #7FAAFF; background-color: #C5D9FF; padding: 2px;margin-top:8px;">
                             </table>
                             </div>
                    </span>
                </div>
                <div class="ac_div">
                    <span class="span_style"><label class="ac_title2 ">正卷宗附件</label></span>
                    <span class="span_style span_left_border">
                              <div style="display: inline; border: solid 1px #7FAAFF; background-color: #C5D9FF; padding: 5px; *+padding:2px; _padding:2px;">
                              <span id="archiveattachment"></span>
                              <input id="btnUpload1" type="button" value="上  传" style="width:60px;"
                                 onclick="startUploadFile1();" class="btn3_mouseout" onMouseUp="this.className='btn3_mouseup'"
                                 onmousedown="this.className='btn3_mousedown'"
                                 onMouseOver="this.className='btn3_mouseover'"
                                 onmouseout="this.className='btn3_mouseout'"/>
                             <input id="btnCancel1" type="button" value="取消所有上传" style="width:120px;"
                                 onclick="cancelUpload1();" disabled="disabled" class="btn3_mouseout" onMouseUp="this.className='btn3_mouseup'"
                                 onmousedown="this.className='btn3_mousedown'"
                                 onMouseOver="this.className='btn3_mouseover'"
                                 onmouseout="this.className='btn3_mouseout'"/>
                             </div>
                             <div id="divFileProgressContainer1"></div>
                             <div id="thumbnails1">
                             <table id="infoTable1" border="0" width="530" style="display: none; border: solid 1px #7FAAFF; background-color: #C5D9FF; padding: 2px;margin-top:8px;">
                             </table>
                             </div>
                    </span>
                </div>
                <div class="ac_div">
                    <span class="span_style "><label class="ac_title2 ">副卷宗</label></span>
                    <span class="span_style span_left_border">
                              <div style="display: inline; border: solid 1px #7FAAFF; background-color: #C5D9FF; padding: 5px; *+padding:2px; _padding:2px;">
                              <span id="archiveattachment2"></span>
                              <input id="btnUpload2" type="button" value="上  传" style="width:60px;"
                                 onclick="startUploadFile2();" class="btn3_mouseout" onMouseUp="this.className='btn3_mouseup'"
                                 onmousedown="this.className='btn3_mousedown'"
                                 onMouseOver="this.className='btn3_mouseover'"
                                 onmouseout="this.className='btn3_mouseout'"/>
                             <input id="btnCancel2" type="button" value="取消所有上传" style="width:120px;"
                                 onclick="cancelUpload2();" disabled="disabled" class="btn3_mouseout" onMouseUp="this.className='btn3_mouseup'"
                                 onmousedown="this.className='btn3_mousedown'"
                                 onMouseOver="this.className='btn3_mouseover'"
                                 onmouseout="this.className='btn3_mouseout'"/>
                             </div>
                             <div id="divFileProgressContainer2"></div>
                             <div id="thumbnails2">
                             <table id="infoTable2" border="0" width="530" style="display: none; border: solid 1px #7FAAFF; background-color: #C5D9FF; padding: 2px;margin-top:8px;">
                             </table>
                             </div>
                    </span>
                </div>
                <div class="ac_div">
                    <span class="span_style "><label class="ac_title2 ">副卷宗附件</label></span>
                    <span class="span_style span_left_border">
                              <div style="display: inline; border: solid 1px #7FAAFF; background-color: #C5D9FF; padding: 5px; *+padding:2px; _padding:2px;">
                              <span id="archiveattachment3"></span>
                              <input id="btnUpload3" type="button" value="上  传" style="width:60px;"
                                 onclick="startUploadFile3();" class="btn3_mouseout" onMouseUp="this.className='btn3_mouseup'"
                                 onmousedown="this.className='btn3_mousedown'"
                                 onMouseOver="this.className='btn3_mouseover'"
                                 onmouseout="this.className='btn3_mouseout'"/>
                             <input id="btnCancel3" type="button" value="取消所有上传" style="width:120px;"
                                 onclick="cancelUpload3();" disabled="disabled" class="btn3_mouseout" onMouseUp="this.className='btn3_mouseup'"
                                 onmousedown="this.className='btn3_mousedown'"
                                 onMouseOver="this.className='btn3_mouseover'"
                                 onmouseout="this.className='btn3_mouseout'"/>
                             </div>
                             <div id="divFileProgressContainer3"></div>
                             <div id="thumbnails3">
                             <table id="infoTable3" border="0" width="530" style="display: none; border: solid 1px #7FAAFF; background-color: #C5D9FF; padding: 2px;margin-top:8px;">
                             </table>
                             </div>
                    </span>
                </div>
                </div>
            </div>
        </div>
        <div style="text-align:center;padding:5px;">
             <button id="submited" class="btn_add">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <button id="closed" class="btn_add">取消</button>
        </div>
    </div>
     
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
  </script>

</body>
</html>