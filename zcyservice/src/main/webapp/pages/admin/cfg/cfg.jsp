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

%>

</head>
<body>

	
		<div class="p_height_div"></div>

		<div title="短信设置" style="padding: 10px">
			<form action="/ecs/sys/cfg/add.do" method="post" novalidate class="sys-form">
			
		<div class="">
            <span class=""><label class="">新增档案是否需要审核：</label></span>
            <span class="span_style">
            	<input type="radio" name="isArchiveNeedApprove" id="isArchiveNeedApprove_yes" value="yes" class="sex_input" /> <label>是</label> 
				<input type="radio" name="isArchiveNeedApprove" value="no" id="isArchiveNeedApprove_no" class="sex_input" /> <label>否</label>            
            </span> 
            <div class="line_clear"></div>
            <span class=""><label class="">系统进入维护：</label></span>
          
            <span class="span_style">
           		<input type="radio" name="sysOnline" id="sysOnline_yes" value="yes" class="sex_input" /> <label>是</label> 
                <input type="radio" name="sysOffline" value="no" id="sysOnline_no" class="sex_input" /> <label>否</label>
            </span> 
        </div>
        
        
        
                    <input type="submit" value="保存" />			    
			</form>
		</div>





	<script type="text/javascript">
		$(document).ready(function() {
			loadSystemConfig();
		});
		
		function loadSystemConfig(){
		    
		    postAjaxRequest("/ecs/sys/cfg/list.do", {}, function(data) {    
		        if(data.data){
		            $(".sys-form").form('load',data.data);
		        }else{
		            $(".sys-form").form('load',data);
		        }
		    });
		    
		}

		
		$(".sys-form").form({
		    url : '/ecs/sys/cfg/add.do',
		    onSubmit : function() {
		        return $(this).form('validate');
		    },
		    success : function(data) {
		        dealMessageWithCallBack(data, "规则库管理", function(data){
		        	displayAlert("保存成功");
		        });
		    }
	   });
		
		
		
	</script>
</body>
</html>