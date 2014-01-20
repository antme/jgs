<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的订单</title>
<script type="text/javascript" src="/resources/js/highcharts.js"></script>
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

	<div style="margin-top:20px; margin-left:40px;padding-top: 30px;">
		<select onchange="loadReport();">
			<option value="years">按年度统计</option>
			<option value="applicant">按申请人统计</option>
			<option value="oppositeApplicant">按被申请人统计</option>
		</select>
	</div>
	
	
   <div class="p_height_div"></div>
   <div class="line_clear"></div>
   <div style="margin-left:40px;">
            <table id="archiveListYear"  class="easyui-datagrid_tf" url="/ecs/archive/count.do" iconCls="icon-save" sortOrder="asc" pagination="true" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true,width:900">
                <thead>
                    <tr>
                        <th align="center"  field="archiveCode"  width="100"  sortable="false">年</th>
                        <th align="center"  field="archiveName"  width="150"  sortable="false">卷宗数</th>
                    </tr>
                </thead>
            </table>
    </div>
    
   <div style="margin-left:40px;">
            <table id="archiveListApplicant"  class="easyui-datagrid_tf" url="/ecs/archive/count.do" iconCls="icon-save" sortOrder="asc" pagination="true" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true,width:900">
                <thead>
                    <tr>
                        <th align="center"  field="archiveCode"  width="100"  sortable="false">申请人</th>
                        <th align="center"  field="archiveName"  width="150"  sortable="false">卷宗数</th>
                    </tr>
                </thead>
            </table>
    </div>    
    
   <div style="margin-left:40px;">
            <table id="archiveListOptApplicant"  class="easyui-datagrid_tf" url="/ecs/archive/count.do" iconCls="icon-save" sortOrder="asc" pagination="true" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true,width:900">
                <thead>
                    <tr>
                        <th align="center"  field="archiveCode"  width="100"  sortable="false">被申请人</th>
                        <th align="center"  field="archiveName"  width="150"  sortable="false">卷宗数</th>
                    </tr>
                </thead>
            </table>
    </div>
    
        
   <script type="text/javascript">
 
    function queryOrderStat(type){
       
    
 
    }
    
    $(function () {
    	queryOrderStat();
    });
    </script>
</body>
</html>