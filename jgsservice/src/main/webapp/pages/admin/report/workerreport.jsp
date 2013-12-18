<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的订单</title>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>

<style type="text/css">
    .highcharts-legend{
        display : none;
    }
    
    tspan {
    	font-size :16px;
    }
    
	.highcharts-container{
	    margin-top : 30px;
	}
</style>
</head>
<body>
   <label style="font-weight:bold">服务商&nbsp&nbsp</label><input class="easyui-combobox" id="spId" name="spId" style="width:105px;" required missingMessage="请选择服务商"
                data-options="
                valueField:'id',textField:'spUserName',url:'/ecs/sp/select.do',method:'post',multiple:false,
                loadFilter:function(data){
                    return data.rows;
                }
                " />
      <span class="span_style"><button class="public_btn" onclick="querySpWorder();">&nbsp</button></span>
   	<div class="p_height_div"></div>
	<table id="workerReportList" class="easyui-datagrid_tf" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true, loadFilter:pagerFilter"
		url="/ecs/sys/report/workerlist.do" iconCls="icon-save" sortOrder="asc" pagination="true" singleSelect="true">
		<thead>
			<tr>
				<th align="center"  field="spUserName" sortable="false"   width=100>服务商名字</th>
				<th align="center"  field="count" sortable="false"  width=100>工人数</th>
			</tr>
		</thead>
	</table>

    
    <script type="text/javascript">

    
    function querySpWorder(type){
    	
    	$("#workerReportList").datagrid("reload", {spId : $("#spId").combobox('getValue')});
    }
    
 
    </script>
</body>
</html>