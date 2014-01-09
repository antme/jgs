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
    <body>
		<div id="documentViewer" class="flexpaper_viewer" style="position:absolute;width:770px;height:650px"></div>
		<div id="documentFiles" style="width:170px;height:350px; float:right; margin-top:20px;">
		
		 	<ul class="easyui-tree" id="firstTrees" data-options="animate:true, state:'closed'"></ul>
			<ul class="easyui-tree" id="firstAttachTrees" data-options="animate:true, state:'closed'"></ul>
			<ul class="easyui-tree" id="secondTrees" data-options="animate:true, state:'closed'"></ul>
			<ul class="easyui-tree" id="secondAttachTrees" data-options="animate:true, state:'closed'"></ul>
		
		</div>
		<script type="text/javascript">
			function getDocumentUrl(document){
				console.log(document);
				return "/pages/flexpaper/view.jsp?doc={doc}&format={format}&page={page}".replace("{doc}",document);     
			}
			function getDocQueryServiceUrl(document){
				console.log(document);
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

						 WMode : 'window',
						 localeChain: 'zh_CN'
						}
				}); 
			}
			
			 $(document).ready(function(){
				 postAjaxRequest("/ecs/archive/files.do", {id:id}, function(data){
					 
					 var firstTrees = data.firstTrees;
					 $('#firstTrees').tree('loadData', firstTrees);
					 $('#firstAttachTrees').tree('loadData', data.firstAttachTrees);
			/* 		 $('#secondTrees').tree('loadData', data.secondTrees);
					 $('#secondAttachTrees').tree('loadData', data.secondAttachTrees); */
					 
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
					 var startDocument = data.firstTrees[0].filePath;

					 intPdfView(startDocument, startDocument, startPage);
					 
					 
				 })
				 
					
			  });

			
		</script>
   </body>
</html>