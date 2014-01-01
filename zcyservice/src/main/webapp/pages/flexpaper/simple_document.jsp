<!doctype html>
<%@page import="lib.Config" %>
<%
	Config conf = new Config();


	String doc = request.getParameter("doc");
	if(doc == null)
		doc = "Paper.pdf";
	String dir = "../pages/";
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">
    <head>
        <title>FlexPaper AdaptiveUI JSP Example</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="viewport" content="initial-scale=1,user-scalable=no,maximum-scale=1,width=device-width" />
        <style type="text/css" media="screen">
			html, body	{ height:100%; }
			body { margin:0; padding:0; overflow:auto; }
			#flashContent { display:none; }
        </style>

		<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
		<script type="text/javascript" src="<%=dir %>js/jquery.extensions.min.js"></script>
		<script type="text/javascript" src="<%=dir %>js/flexpaper.js"></script>
		<script type="text/javascript" src="<%=dir %>js/flexpaper_handlers.js"></script>
    </head>
    <body>
		<div id="documentViewer" class="flexpaper_viewer" style="position:absolute;width:770px;height:500px">ssssssssss</div>
		<script type="text/javascript">
			function getDocumentUrl(document){
				return "/pages/flexpaper/view.jsp&doc={doc}&format={format}&page={page}".replace("{doc}",document);     
			}
			function getDocQueryServiceUrl(document){
				return "?p=flexpaper/swfsize&doc={doc}&page={page}".replace("{doc}",document);
			}
			var startDocument = "<%=doc%>";

			function append_log(msg){
				$('#txt_eventlog').val(msg+'\n'+$('#txt_eventlog').val());
			}

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
					 Scale : 0.6, 
					 ZoomTransition : 'easeOut',
					 ZoomTime : 0.5, 
					 ZoomInterval : 0.1,
					 FitPageOnLoad : true,
					 FitWidthOnLoad : false, 
					 FullScreenAsMaxWindow : false,
					 ProgressiveLoading : false,
					 MinZoomSize : 0.2,
					 MaxZoomSize : 5,
					 SearchMatchAll : false,
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
					 localeChain: 'en_US'
					 }
			});
		</script>
   </body>
</html>