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

            <span class="span_style"><label class="">系统进入维护：</label></span>
          
            <span class="span_style">
           		<input type="radio" name="sysOnline" id="sysOnline_yes" value="yes" class="sex_input" /> <label>是</label> 
                <input type="radio" name="sysOnline" value="no" id="sysOnline_no" class="sex_input" /> <label>否</label>
                <span class="span_style" style="margin-left:50px;"><label class="">备注系统进入维护后用户将不能登录</label></span>
            </span> 
        
            
        </div>
        
        
        
                    <input type="submit" value="保存" />			    
			</form>
		</div>
		<hr>
    		<div class="p_height_div"></div>
            
            <span class="span_style"><label class="">扫描新档案：</label></span>
          
            <span class="span_style">
           		<button onclick="scanDocument();">扫描</button>
            </span> 



	<script type="text/javascript">
		$(document).ready(function() {
			loadSystemConfig();
		});
		function scanDocument(){
			postAjaxRequest("/ecs/archive/scan.do", {}, function(data) {  
				displayAlert("扫描完毕");
			})
		}
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