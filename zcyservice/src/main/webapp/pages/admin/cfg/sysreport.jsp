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



int cpuLength = sigar.getCpuInfoList().length;
CpuInfo infos[] = sigar.getCpuInfoList();
CpuPerc cpu;
cpu = sigar.getCpuPerc();

Mem mem = sigar.getMem();  
int memFree = (int)(mem.getFree()/(1024l*1024l));  
int memTotal = (int)(mem.getTotal()/(1024l*1024l));  
int memUsed = memTotal- memFree;

OperatingSystem OS = OperatingSystem.getInstance(); 


long Total=0;
long Free=0;
long Used=0;
String name=null;
FileSystem fslist[] = sigar.getFileSystemList(); 
FileSystemUsage usage = null;
FileSystem fs=null;
for(int i=0;i<fslist.length;i++){
    fs = fslist[i];
    usage = sigar.getFileSystemUsage(fs.getDirName());
    Total=Total+usage.getTotal();
    Free=Free+usage.getFree();
    Used=Used+usage.getUsed();
    name=fs.getSysTypeName();
}
Total=Total/(1024*1024);
Free=Free/(1024*1024);
Used=Used/(1024*1024);
    
    
%>
<style type="text/css">
.bottom_style{
border-bottom:1px solid #cdcdcd;
}
</style>
</head>
<body>

	
		<div class="p_height_div"></div>
		<div style="margin:40px;">
		 <div class="div_span" >
            <span class="span_style_label width_border_noright"><label class="display_nones">CPU个数：</label></span>
            <span class="span_style width_border_noright"><div class="display_input"><%=sigar.getCpuInfoList().length%></div></span> 

            <span class="span_style_label width_border_noright"><label class="display_nones">CPU总量：</label></span>
            <span class="span_style width_border_noright"><div class="display_input"><%= infos[0].getMhz()+"MHz" %></div></span>
            
            <span class="span_style_label width_border_noright"><label class="display_nones">CPU使用率：</label></span>
            <span class="span_style width_border"><div class="display_input"><%= CpuPerc.format(cpu.getCombined()) %></div></span>
        </div>
        <div class="div_span" id="mem" >
            <span class="span_style_label width_border_noright"><label class="display_nones">内存使用量：</label></span>
            <span class="span_style width_border_noright"><div class="display_input"><%=memUsed+"M"%></div></span>
        
            <span class="span_style_label width_border_noright"><label class="display_nones">内存剩余：</label></span>
            <span class="span_style width_border_noright"><div class="display_input"><%=memFree+"M"%></div></span> 

            <span class="span_style_label width_border_noright"><label class="">内存大小：</label></span>
            <span class="span_style width_border"><div class="display_input"><%=memTotal+"M"%></div></span> 
        </div>
        
        <div class="div_span" >
            <span class="span_style_label width_border_noright"><label class="display_nones">磁盘总量</label></span>
            <span class="span_style width_border_noright"><div class="display_input"><%=Total+"G" %></div></span>
        
            <span class="span_style_label width_border_noright"><label class="display_nones">使用量：</label></span>
            <span class="span_style width_border_noright"><div class="display_input"><%=Used+"G" %></div></span> 

            <span class="span_style_label width_border_noright"><label class="">剩余量：</label></span>
            <span class="span_style width_border"><div class="display_input"><%=Free+"G" %></div></span>
        </div>
        
        <div class="div_span">
            <span class="span_style_label width_border_noright bottom_style" ><label class="display_nones">操作系统名称：</label></span>
            <span class="span_style width_border_noright bottom_style"><div class="display_input"><%=OS.getVendorName() %></div></span>
        
            <span class="span_style_label width_border_noright bottom_style"><label class="display_nones">文件系统类型：</label></span>
            <span class="span_style width_border_noright bottom_style"><div class="display_input"><%=name %></div></span> 

            <span class="span_style_label width_border_noright bottom_style"><label class="display_nones"></label></span>
            <span class="span_style width_border bottom_style"><div class="display_input"></div></span> 
        </div>
       </div>

</body>
</html>