<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
</head>



<body>
	<%
    if(session.getAttribute("roleName")!=null){


    String roleName = session.getAttribute("roleName").toString();
    if(roleName.equalsIgnoreCase("mfc")){
    %>

	<%@ include file="/pages/left/left-mfc.jsp"%>
	<%
    }else if(roleName.equalsIgnoreCase("sp")){
    	  %>
	<%@ include file="/pages/left/left-sp.jsp"%>
	<%
    }else if(roleName.equalsIgnoreCase("ADMIN") || roleName.equalsIgnoreCase("SUPPER_ADMIN") || roleName.equalsIgnoreCase("CUSTOMER_SERVICE")){
    	  %>
	<%@ include file="/pages/left/left-admin.jsp"%>
	<%
    }
    }
	%>

</body>
</html>