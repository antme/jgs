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
      <div id="change_btn" style="padding: 10px"><input type="radio" name="archive" checked/><label>正卷宗</label>&nbsp;<input name="archive" type="radio" /><label>副卷宗</label></div>

      <div title="附件上传" style="padding: 10px">
               <div id="fj">
                <div class="ac_div zjz" >
                    <span class="span_style"><label class="ac_title2 ">卷宗</label></span>
                    <span class="span_style span_left_border">
                              <div style="display: inline; ">
                              <span id="spanButtonPlaceholder"></span>
                              </div>
                             <div id="divFileProgressContainer" style="display:none;"></div>
                             <div id="thumbnails" style="border: solid 1px #7FAAFF; background-color: #C5D9FF;min-height:30px;width:530px;">
                             <table id="infoTable" border="0" width="530" style="padding: 2px;margin-top:8px;">
                             </table>
                             </div>
                             <div style="padding-top:5px;">
                                 <input id="btnUpload" type="button" value="上传" class="btn_add"
                                 onclick="startUploadFile();" class="btn3_mouseout" />
                                 <input id="btnCancel" type="button" value="取消" class="btn_add"
                                 onclick="cancelUpload();" disabled="disabled" class="btn3_mouseout" />
                             
                             </div>
                    </span>
                </div>
                <div class="ac_div zjz">
                    <span class="span_style"><label class="ac_title2 ">卷宗附件</label></span>
                    <span class="span_style span_left_border">
                              <div style="display: inline;">
                              <span id="archiveattachment"></span>
                              
                             </div>
                             <div id="divFileProgressContainer1" style="display:none;"></div>
                             <div id="thumbnails1" style="border: solid 1px #7FAAFF; background-color: #C5D9FF;min-height:30px;width:530px;">
                             <table id="infoTable1" border="0" width="530" style=" padding: 2px;margin-top:8px;">
                             </table>
                             </div>
                             <div style="padding-top:5px;">
                                  <input id="btnUpload1" type="button" value="上传" class="btn_add"
                                 onclick="startUploadFile1();" class="btn3_mouseout" />
                                <input id="btnCancel1" type="button" value="取消" class="btn_add"
                                 onclick="cancelUpload1();" disabled="disabled" class="btn3_mouseout" />
                             </div>
                    </span>
                </div>
                
                
                </div>
            </div>

            <div title="基本信息" style="padding: 10px">
                <form action="" id="addarchiveForm" method="post" novalidate>
                    <input  type="hidden" name="year" id="year"/>
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
           
       
        <div style="text-align:center;padding:5px;">
             <button id="submited" class="btn_add">确定</button>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
             <a href="?p=web/archive/archivemanager"><button class="btn_add">取消</button></a>
        </div>
        
<%
	String id = request.getParameter("id");
%>

  <script type="text/javascript">
     $(document).ready(function(){
	    initArchiveManagePage();
	    
	    var id = "<%=id%>";
	    if(id!="null"){
	    	   postAjaxRequest("/ecs/archive/get.do", {id:id}, function(data){
		     	   $("#addarchiveForm").form("clear");
		     	   $("#addarchiveForm").form("load",data.data);
		     	   $("#addarchiveForm").append("<input id='sid' name='id' type='hidden' value='"+id+"' />");
	     	   });
	    
	    }
	 });	  
  </script>

</body>
</html>