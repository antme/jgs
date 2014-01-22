<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import=" org.hyperic.sigar.*" %>	
<!DOCTYPE html PUBLIC "-//W3C//Dli HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dli">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>价格</title>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="resources/js/validation.js"></script>
<%
Sigar sigar = new Sigar();
SysInfo s =new SysInfo();

%>

</head>
<body>

	
		<div class="p_height_div"></div>
		 <div class="line_seach">
            <span class="span_style_label"><label class="display_nones">CPU个数：</label></span>
            <span class="span_style"><%=sigar.getCpuInfoList().length%></span> 
            
             <span class="span_style_label"><label class="display_nones">CPU个数：</label></span>
            <span class="span_style"><%=sigar.getCpuInfoList().length%></span> 
        </div>


</body>
</html>