<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String doc = request.getParameter("doc");
String callback = request.getParameter("callback");
if(callback == null){callback = "";}
lib.swfsizequery ss = new lib.swfsizequery();
response.setContentType("application/json");
String outs = "({\"height\":" + ss.getSize(doc,request.getParameter("page"),"height");
outs += ",\"width\":" + ss.getSize(doc,request.getParameter("page"),"width");
%>
<%=callback + outs + ")}" %>