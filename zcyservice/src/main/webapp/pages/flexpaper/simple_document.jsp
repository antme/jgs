<!doctype html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="lib.Config" %>
<%
	Config conf = new Config();


	String doc = request.getParameter("doc");
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

		<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=dir %>js/jquery.extensions.min.js"></script>
		<script type="text/javascript" src="<%=dir %>js/flexpaper.js"></script>
		<script type="text/javascript" src="<%=dir %>js/flexpaper_handlers.js"></script>
		<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
    </head>
    <body>
		<div id="documentViewer" class="flexpaper_viewer" style="position:absolute;width:770px;height:650px"></div>
		<div id="documentFiles" style="width:170px;height:350px; float:right; margin-top:20px;">
		
		 	<ul class="easyui-tree" data-options="url:'/ecs/archive/files.do', method:'get',animate:true,loadFilter:function(data){
							                    	return data.rows;
							                    }"></ul>
	
		
		</div>
		<script type="text/javascript">
			function getDocumentUrl(document){
				console.log("/pages/flexpaper/view.jsp?doc={doc}&format={format}&page={page}".replace("{doc}",document));
				return "/pages/flexpaper/view.jsp?doc={doc}&format={format}&page={page}".replace("{doc}",document);     
			}
			function getDocQueryServiceUrl(document){
				return "/pages/flexpaper/swfsize.jsp&doc={doc}&page={page}".replace("{doc}",document);
			}
			var startDocument = "<%=doc%>";

			function append_log(msg){
				$('#txt_eventlog').val(msg+'\n'+$('#txt_eventlog').val());
			}
			
			var startPage = <%=startPage%>;

			String.format = function() {
				var s = arguments[0];
				for (var i = 0; i < arguments.length - 1; i++) {
					var reg = new RegExp("\\{" + i + "\\}", "gm");
					s = s.replace(reg, arguments[i + 1]);
				}
			
				return s;
			};

			$('#documentViewer').FlexPaperViewer({
				 config : {
					 DOC : escape(getDocumentUrl(startDocument)),
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
					 StartAtPage : startPage,
					 SearchMatchAll : true,
					 RenderingOrder : '<%=(conf.getConfig("renderingorder.primary", "") + "," +	conf.getConfig("renderingorder.secondary", "")) %>',

					 ViewModeToolsVisible : true,
					 ZoomToolsVisible : true,
					 NavToolsVisible : true,
					 CursorToolsVisible : true,
					 SearchToolsVisible : true,

					 DocSizeQueryService : "?p=flexpaper/swfsize&doc=" + startDocument,
					 jsDirectory : '<%=dir%>js/',
					 localeDirectory : '<%=dir %>locale/',

					 JSONDataType : 'jsonp',
					 key : '<%= conf.getConfig("licensekey", "") %>',

					 WMode : 'window',
					 localeChain: 'zh_CN'
					 }
			});
		</script>
   </body>
</html>