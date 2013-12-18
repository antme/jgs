<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<link href="resources/css/comm.css" rel="stylesheet"/>

<script type="text/javascript" src="resources/js/jquery.min.js"></script>
<script type="text/javascript" src="resources/js/ecommerce.js"></script>
<script type="text/javascript" src="resources/js/json2.js"></script>
</head>

<%
	if (session.getValue("userName") == null) {
		String url = request.getServerName();
		//response.sendRedirect("http://" + url + "/login.jsp");
	}
%>

<%
	String userRoleName = null;
	if (session.getAttribute("roleName") != null) {
		userRoleName = session.getAttribute("roleName").toString();
	}
	
	if("USER".equalsIgnoreCase(userRoleName)){
	    response.sendRedirect("/index_user.jsp");
	}
%>

<body onResize="resizeder();">
     <div class="head">
        <%@ include file="pages/head.jsp" %>
     </div>
     <div class="center">
         <div class="left"><%@ include file="pages/left.jsp" %></div>
         <div class="right" >
            <div id="content-right-info" style="color:red; font-size:18px;font-weight:bold;"></div>
            <div id="content-right" style="margin-top:5px; height:auto; overflow:hidden">
	            <% 
	               String pagePath = request.getParameter("p"); 
	               if(pagePath == null){
	                   if("SP".equalsIgnoreCase(userRoleName)){
	                       pagePath = "jqs/jqScheduler";
	                   }else if("MFC".equalsIgnoreCase(userRoleName)){
	                       pagePath = "web/mfc/main";
	                   }else if("SUPPER_ADMIN".equalsIgnoreCase(userRoleName)){
	                       pagePath = "admin/user/role";
	                   }else{
	                       if (session.getAttribute("indexPage") != null) {
	                           pagePath = session.getAttribute("indexPage").toString();
	                       }
	                   }
	               }
	               if(pagePath != null){
	                   pageContext.setAttribute("pagePath","pages/"+pagePath+".jsp");                           
	            
	            %>
	                <jsp:include page="${pagePath}" />
	            
	            <% } %>
            </div>
            <div id="remotePage"  class="remotePage" style="display:none;"></div>
             <div id="remotePageWindow"  style="display:none; overflow-y: scroll;"></div>
         </div>
         <%@ include file="pages/bottom.jsp" %>
		<div class="handle_events" >
		   <div class="handle_events_title">待处理事项</div>
		   <div class="handle_events_text">
		       <div id="tips"></div>
               <div class="next_info"><a onclick="getNextmsg();">下一条</a></div>
		   </div>
		</div>
	</div>
	
	<div style="display:none;">
		<div id="detailWindow"  >
			<span id="detailspan" height="200" style="margin-top:20px;"></span>
		</div>
	</div>
	
      <script type="text/javascript">
        var roleName = "<%=userRoleName%>";
        var pagePath = "<%=pagePath%>";
        

     </script>
    
        <script type="text/javascript">
	    $(document).ready(function(){
	    	
	    	$("img").each(function(){	  
	    		if($(this).attr('src').indexOf("baidu")!=-1){
	    		$(this).hide();
	    		}
	    	});
	    	
	    });
    </script>

	 
	 <script type="text/javascript" src="resources/js/jquery.mb.browser.js"></script>
	 
</body>
</html>