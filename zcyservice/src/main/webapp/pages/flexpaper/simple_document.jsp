<!doctype html>
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

		<script type="text/javascript" src="resources/js/ecommerce.js"></script>
		
		<script type="text/javascript" src="<%=dir %>js/jquery.extensions.min.js"></script>
		<script type="text/javascript" src="<%=dir %>js/flexpaper.js"></script>
		<script type="text/javascript" src="<%=dir %>js/flexpaper_handlers.js"></script>
		<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
		
    </head>
    <body id="b_height">

        <div id="archive_info" style="margin:0px 40px;">
            <div class="width100 font18 margintop10">上 海 市 虹 口 区 劳 动 人 事 争 议 仲 裁 委 员 会 </div>
            <div class="width100 font24 fontweight margintop20">仲 裁 卷 宗</div>
            <br/>
            
            <span class="span_style_label"><label class="display_nones">案号：</label></span>
            <span class="span_style"><div id="archiveCode" class="display_input"></div></span>
            
            <span class="span_style_label"><label class="display_nones">案由：</label></span>
            <span class="span_style"><div id="archiveName" class="display_input"></div></span>
            
            <span class="span_style_label"><label class="display_nones">处理结果：</label></span>
            <span class="span_style"><div id="archiveStatus" class="display_input"></div></span>
            <br/>
            
            <span class="span_style_label"><label class="display_nones">申请人：</label></span>
            <span class="span_style"><div id="archiveApplicant" class="display_input"></div></span>
            
            <span class="span_style_label"><label class="display_nones">被申请人：</label></span>
            <span class="span_style"><div id="archiveOppositeApplicant" class="display_input"></div></span>
            
            <span class="span_style_label"><label class="display_nones">第三人：</label></span>
            <span class="span_style"><div id="archiveThirdPerson" class="display_input"></div></span>
            <br/>
            
            <span class="span_style_label"><label class="display_nones">承办人：</label></span>
            <span class="span_style"><div id="archiveJudge" class="display_input"></div></span>
            
            <span class="span_style_label"><label class="display_nones">立案日期：</label></span>
            <span class="span_style"><div id="archiveOpenDate" class="display_input"></div></span>
            
            <span class="span_style_label"><label class="display_nones">结案日期：</label></span>
            <span class="span_style"><div id="archiveCloseDate" class="display_input"></div></span>
            <br>
            
            <span class="span_style_label"><label class="display_nones">归档日期：</label></span>
            <span class="span_style"><div id="archiveDate" class="display_input"></div></span>
            
            <span class="span_style_label"><label class="display_nones">归档号数：</label></span>
            <span class="span_style"><div id="archiveSerialNumber" class="display_input"></div></span>
            
            <span class="span_style_label"><label class="display_nones">创建日期：</label></span>
            <span class="span_style"><div id="createdOn" class="display_input"></div></span>
            <br>
            
            <span class="span_style_label"><label class="display_nones">修改日期：</label></span>
            <span class="span_style"><div id="updatedOn" class="display_input"></div></span>
            <br/>
            <br/>
        </div>
		<div id="documentViewer" class="flexpaper_viewer" style="position:absolute;width:770px;height:650px;z-index:2"></div>
		<div id="documentFiles" style="width:184px;height:350px; float:right; margin-right:10px;">
		
		 	<ul class="easyui-tree" id="firstTrees" data-options="animate:true, state:'closed'"></ul>
			<ul class="easyui-tree" id="firstAttachTrees" data-options="animate:true, state:'closed'"></ul>
			
			
			<ul class="easyui-tree" id="secondTrees" data-options="animate:true, state:'closed'"></ul>
			<ul class="easyui-tree" id="secondAttachTrees" data-options="animate:true, state:'closed'"></ul>
		
		</div>
		<div class="watermark"></div>
		<script type="text/javascript">
			function getDocumentUrl(document){
				return "/pages/flexpaper/view.jsp?doc={doc}&format={format}&page={page}".replace("{doc}",document);     
			}
			function getDocQueryServiceUrl(document){
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
				$('#documentViewer').FlexPaperViewer({
					 config : {
						 DOC : escape(getDocumentUrl(pdfFilePath)),
						 Scale : 1.0, 
						 ZoomTransition : 'easeOut',
						 ZoomTime : 0.5, 
						 ZoomInterval : 0.1,
						 FitPageOnLoad : true,
						 FitWidthOnLoad : false, 
						 FullScreenAsMaxWindow : true,
						 ProgressiveLoading : true,
						 MinZoomSize : 0.5,
						 MaxZoomSize : 8,
						 StartAtPage : pdfStartPage,
						 SearchMatchAll : true,
						 RenderingOrder : RenderingOrder,

						 ViewModeToolsVisible : true,
						 ZoomToolsVisible : true,
						 NavToolsVisible : true,
						 CursorToolsVisible : true,
						 SearchToolsVisible : true,

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
				 postAjaxRequest("/ecs/archive/files.do", {id:id}, function(data){
					 var firstTrees = data.firstTrees;
					 $('#firstTrees').tree('loadData', firstTrees);
					 $('#firstAttachTrees').tree('loadData', data.firstAttachTrees);
			/* 		 $('#secondTrees').tree('loadData', data.secondTrees);
					 $('#secondAttachTrees').tree('loadData', data.secondAttachTrees); */
					// console.log(data.data);
					 $("#archiveCode").text(data.data.archiveCode);
					 $("#archiveName").text(data.data.archiveName);
					 $("#archiveStatus").text(data.data.archiveStatus);
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
					 
					 
					 var archive = data.data;
					 
					 if( data.firstTrees[0]){
						  var startDocument = data.firstTrees[0].filePath;
					 }					 

					 intPdfView(startDocument, startDocument, startPage);
					 
					 
				 });
                 var offsettop=$("#documentViewer").offset().top;
                 var offsetleft=$("#documentViewer").offset().left;
                 offsettop+=($("#documentViewer").height()/2-135);
                 offsetleft+=($("#documentViewer").width()/2-175);
                 $(".watermark").css("top",offsettop+"px");
                 $(".watermark").css("left",offsetleft+"px");
                 
                 var speed = 2000;
                 function Marqueess() {
                         $(".watermark").css({"position":"absolute","width":"350px","height":"270px","background":"url(/resources/images/back01.png) no-repeat","filter":"alpha(opacity=30)","-moz-opacity":"0.3","-khtml-opacity":"0.3","opacity":"0.3",
                         "_background-image": "none",
                         "_filter": "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='resources/images/table_edit.png',sizingMethod='scale')"});
                         $(".watermark").css("top",offsettop+"px");
                         $(".watermark").css("left",offsetleft+"px");
                 }
                 var MyMaryr = setInterval(Marqueess, speed);
			  }, false);
             
			
		</script>
   </body>
</html>