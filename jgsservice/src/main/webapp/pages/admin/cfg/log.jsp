<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的订单</title>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/resources/js/admin/admin.js"></script>
</head>
<body>

	       <span class="span_style"><input style="width:150px" name="pkeyword" id="pkeyword" placehodler="关键字" class="tpublic_input"/></span>
	       <span class="span_style"><button class="public_btn" onclick="searchLog();"> </button></span>
	       <div class="p_height_div"></div>
		   <table id="logList"  class="easyui-datagrid_tf" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true"  url="/ecs/sys/log/listlog.do"  iconCls="icon-save" sortOrder="asc"  pagination="true"  >
	        <thead>
	            <tr>
	                <th align="center"  field="userName"   width="80"  sortable="false" resizable="true">操作人</th>
	                <th align="center"  field="createdOn"  width="80"  sortable="false" >操作时间</th>
	                <th align="center"  field="message"   width="500"  formatter="formatterUrlPath" >描述 </th>	                
	            </tr>
	        </thead>
	       </table>
          <div id="logDetail">
            
          </div>
 
       <script type="text/javascript">
       		var logType = "";
       		if($("#logType")){
       			 logType = $("#logType").val();
       		}
       
           function searchLog(){
        	   var data = {
        			   logType: logType,
        	           keyword : $('#pkeyword').val()
        	         }
        	   $('#logList').datagrid('reload', data);
           }
           
          
           
           


       </script>

</body>
</html>