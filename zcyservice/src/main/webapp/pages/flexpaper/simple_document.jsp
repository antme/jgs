<!doctype html>
<%@page import="com.zcyservice.util.ZcyUtil"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="lib.Config" %>

<%
	Config conf = new Config();

    
	String doc = request.getParameter("doc");
	String id = request.getParameter("id");
	if(doc == null)
		doc = "Paper.pdf";
	String dir = "../pages/";
	
	String startPage = request.getParameter("startPage");
	if(startPage == null){
		startPage = "0";
	}
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
    <head>
        <title>FlexPaper AdaptiveUI JSP Example</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1,width=device-width" />
        <style type="text/css" media="screen">
			html, body	{ height:100%; }
			
			#flashContent { display:none; }
        </style>


		<script type="text/javascript" src="<%=dir %>js/jquery.extensions.min.js"></script>
		<script type="text/javascript" src="<%=dir %>js/flexpaper.js"></script>
		<script type="text/javascript" src="<%=dir %>js/flexpaper_handlers.js"></script>
		<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
	    <script type="text/javascript" src="/resources/js/archive.js"></script>
	    
        
		
    </head>
    <body id="b_height">

<%

    
    String action = request.getParameter("action");
    if(action!=null && action.equalsIgnoreCase("approve")){

%>
        <div id="archive_approve" style="margin:0px 40px;padding-top:20px;">
            <button class="btn_add" onclick="approveArchive();">批准</button>&nbsp;&nbsp;&nbsp;&nbsp;
            <button class="btn_delected" onclick="rejectArchive();">拒绝</button>
        </div>
        
<%
    }
    if(action!=null && action.equalsIgnoreCase("approvedestroy")){

%>        
        <div id="archive_destroy_approve" style="margin:0px 40px;padding-top:20px;">
            <button class="btn_add" onclick="approveDestoryArchive();">批准</button>&nbsp;&nbsp;&nbsp;&nbsp;
            <button class="btn_delected" onclick="rejectDestoryArchive();">拒绝</button>
        </div>
        
<%
    }
%>        
        
         <input type="hidden" id="archiveId"/>
        <div id="archive_info" style="margin:0px 40px;">
            <div class="width100 font18 margintop10">上 海 市 虹 口 区 劳 动 人 事 争 议 仲 裁 委 员 会 </div>
            <div class="width100 font24 fontweight margintop20">仲 裁 卷 宗</div>
            <br/>
            <div class="div_span">
                 <span class="span_style_label width_border_noright"><label class="display_nones">案号：</label></span>
                 <span class="span_style width_border_noright"><div id="archiveCode" class="display_input"></div></span>
            
                 <span class="span_style_label width_border_noright"><label class="display_nones">创建日期：</label></span>
                 <span class="span_style width_border_noright"><div id="createdOn" class="display_input"></div></span>
            
                 <span class="span_style_label width_border_noright"><label class="display_nones">修改日期：</label></span>
                 <span class="span_style width_border"><div id="updatedOn" class="display_input"></div></span>
            </div>
            <div class="div_span div_span_left">
                 <span class="span_style_label width_border_noright div_span_Noleft"><label class="display_nones">案由：</label></span>
                 <span class="span_style width_border_one"><div id="archiveName" class="display_input598"></div></span>
            </div>
            <div class="div_span div_span_left">
                 <span class="span_style_label width_border_noright div_span_Noleft"><label class="display_nones">处理结果：</label></span>
                 <span class="span_style width_border_one"><div id="archiveResult" class="display_input598"></div></span>
            </div>
            <div class="div_span">
                 <span class="span_style_label width_border_noright"><label class="display_nones">申请人：</label></span>
                <span class="span_style width_border_noright"><div id="archiveApplicant" class="display_input"></div></span>
            
                <span class="span_style_label width_border_noright"><label class="display_nones">被申请人：</label></span>
                <span class="span_style width_border_noright"><div id="archiveOppositeApplicant" class="display_input"></div></span>
            
                <span class="span_style_label width_border_noright"><label class="display_nones">第三人：</label></span>
                <span class="span_style width_border"><div id="archiveThirdPerson" class="display_input"></div></span>
            </div>
            <div class="div_span">
                <span class="span_style_label width_border_noright"><label class="display_nones">承办人：</label></span>
                <span class="span_style width_border_noright"><div id="archiveJudge" class="display_input"></div></span>
            
                <span class="span_style_label width_border_noright"><label class="display_nones">立案日期：</label></span>
                <span class="span_style width_border_noright"><div id="archiveOpenDate" class="display_input"></div></span>
            
                <span class="span_style_label width_border_noright"><label class="display_nones">结案日期：</label></span>
                <span class="span_style width_border"><div id="archiveCloseDate" class="display_input"></div></span>
            </div>
            <div class="div_span">
                <span class="span_style_label width_border_bottom"><label class="display_nones">归档日期：</label></span>
                <span class="span_style width_border_bottom"><div id="archiveDate" class="display_input"></div></span>
            
                <span class="span_style_label width_border_bottom"><label class="display_nones">归档号数：</label></span>
                <span class="span_style width_border_bottom"><div id="archiveSerialNumber" class="display_input"></div></span>
            
                <span class="span_style_label width_border_bottom"><label class="display_nones">归档状态</label></span>
                <span class="span_style width_borders"><div class="display_input" id="archiveStatus"></div></span>
            </div>
            
            <%

    if(action!=null && action.equalsIgnoreCase("approvedestroy")){

%>   
            <div class="div_span " id="destroyComments_div" >
                 <span class="span_style_label width_border_noright div_span_Noleft"><label class="display_nones">销毁备注：</label></span>
                 <span class="span_style width_border_one"><div id="destroyComments" class="display_input598"></div></span>
            </div>
            <%
    }
%> 
            <br/>
        </div>
        <div class="div_span " style="margin-left:100px;">备注：第一次加载预览文档会比较慢，请耐心等待，第二次以后加载会很快。</div>
		<div id="documentViewer" class="flexpaper_viewer" style="position:absolute;width:720px;height:750px;z-index:2; margin-left: 40px;"></div>
		<div id="documentFiles" style="width:200px;height:350px; float:right; margin-right:10px;">
		
		 	<ul class="easyui-tree" id="firstTrees" data-options="animate:true, state:'closed'"></ul>
			<ul class="easyui-tree" id="firstAttachTrees" data-options="animate:true, state:'closed'"></ul>

            <div class="line_clear"></div>
		
		    <ul ><a id="download"  href="#" style="display:none;">下载档案</a></ul>
		</div>
		<div class="watermark11"></div>
		<script type="text/javascript">
			function getDocumentUrl(document){
				document = encodeURIComponent(document);
				return "/pages/flexpaper/view.jsp?doc={doc}&format={format}&page={page}".replace("{doc}",document);     
			}
			function getDocQueryServiceUrl(document){
				document = encodeURIComponent(document);
				return "/pages/flexpaper/swfsize.jsp&doc={doc}&page={page}".replace("{doc}",document);
			}

			function append_log(msg){
				$('#txt_eventlog').val(msg+'\n'+$('#txt_eventlog').val());
			}
			
			var startPage = <%=startPage%>;
			var id = "<%=id%>";
	
			var RenderingOrder  = '<%=(conf.getConfig("renderingorder.primary", "") + "," +	conf.getConfig("renderingorder.secondary", "")) %>';
			var  jsDirectory = '<%=dir%>js/';
			var localeDirectory = '<%=dir %>locale/';

			var key = '<%= conf.getConfig("licensekey", "") %>';

			String.format = function() {
				var s = arguments[0];
				for (var i = 0; i < arguments.length - 1; i++) {
					var reg = new RegExp("\\{" + i + "\\}", "gm");
					s = s.replace(reg, arguments[i + 1]);
				}
			
				return s;
			};
			
			
			function intPdfView(pdfFilePath, pdfStartDocumentFile, pdfStartPage){
				var canPrint = false;
				if(userRolestr && userRolestr.indexOf("adm_archive_download")!=-1){
                      $("#download").attr("href", "/ecs/archive/dowload.do?id=" + id);
                      $("#download").show();
                }
				
			    if(userRolestr && userRolestr.indexOf("adm_archive_print")!=-1){
			    	canPrint = true;
                }
				
				$('#documentViewer').FlexPaperViewer({
					 config : {
						 DOC : escape(getDocumentUrl(pdfFilePath)),
						 Scale : 1.2, 
						 ZoomTransition : 'easeOut',
						 ZoomTime : 0.5, 
						 ZoomInterval : 0.1,
						 FitPageOnLoad : true,
						 FitWidthOnLoad : false, 
						 FullScreenAsMaxWindow : true,
						 ProgressiveLoading : true,
						 MinZoomSize : 0.5,
						 MaxZoomSize : 2,
						 StartAtPage : pdfStartPage,
						 SearchMatchAll : true,
						 RenderingOrder : RenderingOrder,
						 ViewModeToolsVisible : true,
						 ZoomToolsVisible : true,
						 NavToolsVisible : false,
						 CursorToolsVisible : true,
						 SearchToolsVisible : true,
						 PrintEnabled : canPrint,
						 PrintVisible : canPrint,
						 DocSizeQueryService : "?p=flexpaper/swfsize&doc=" + pdfStartDocumentFile,
						 jsDirectory : jsDirectory,
						 localeDirectory : localeDirectory,

						 JSONDataType : 'jsonp',
						 key : key,

						 WMode : 'Opaque',
						 localeChain: 'zh_CN'
						}
				}); 
			}
			
			 $(document).ready(function(){
			 
				 var h_height=$("#archive_info").height()+650+$(".public_title").height()+100;
				 $(".context").css("height",h_height+"px");
				 $("#archiveId").val(id);
				 postAjaxRequest("/ecs/archive/files.do", {id:id}, function(data){
					 
					
		                 
					 var firstTrees = data.firstTrees;
					 var firstAttachTrees = data.firstAttachTrees;
					 $('#firstTrees').tree('loadData', firstTrees);
					 $('#firstAttachTrees').tree('loadData', data.firstAttachTrees);

					 $("#archiveCode").text(data.data.archiveCode);
					 $("#archiveName").text(data.data.archiveName);
					 $("#archiveStatus").text(formatterArchiveStatus(data.data.archiveStatus));
					 $("#archiveResult").text(formatterArchiveStatus(data.data.archiveResult));
					 $("#createdOn").text(data.data.createdOn);
					 $("#updatedOn").text(data.data.updatedOn);
					 $("#archiveApplicant").text(data.data.archiveApplicant);
					 $("#archiveOppositeApplicant").text(data.data.archiveOppositeApplicant);
					 $("#archiveThirdPerson").text(data.data.archiveThirdPerson);
					 $("#archiveJudge").text(data.data.archiveJudge);
					 $("#archiveOpenDate").text(data.data.archiveOpenDate);
					 $("#archiveCloseDate").text(data.data.archiveCloseDate);
					 $("#archiveDate").text(data.data.archiveDate);
					 $("#archiveSerialNumber").text(data.data.archiveSerialNumber);
					 
					 
					 $("#destroyComments").text(data.data.destroyComments);
					 $('#firstTrees').tree({
						    state: "closed",
							onClick: function(node){
								
								var parent =  $('#firstTrees').tree('getParent', node.target);
								for(var i=0; i< firstTrees.length; i++){
									if(parent.id == firstTrees[i].id){
										
										for(var j=0; j< firstTrees[i].children.length; j++){
											
											if(node.id == firstTrees[i].children[j].id){
												
												intPdfView(firstTrees[i].children[j].filePath, startDocument, firstTrees[i].children[j].pdfMenuPage);
												break;
												
											}
										}
										 break;
									}
								}
							}
					 });
					 
					 $('#firstAttachTrees').tree({
						    state: "closed",
							onClick: function(node){
								
								var parent =  $('#firstAttachTrees').tree('getParent', node.target);
								for(var i=0; i< firstAttachTrees.length; i++){
									if(parent.id == firstAttachTrees[i].id){
										
										for(var j=0; j< firstAttachTrees[i].children.length; j++){
											
											if(node.id == firstAttachTrees[i].children[j].id){
												
												intPdfView(firstAttachTrees[i].children[j].filePath, startDocument, firstAttachTrees[i].children[j].pdfMenuPage);
												break;
												
											}
										}
										 break;
									}
								}
							}
					 });
					 
					 
					 var archive = data.data;
					 
					 if( data.firstTrees[0]){
						  var startDocument = data.firstTrees[0].filePath;
					 }					 

					 intPdfView(startDocument, startDocument, startPage);
		
				 });
         
              
			  }, false);
             
			
		</script>
   </body>
</html>