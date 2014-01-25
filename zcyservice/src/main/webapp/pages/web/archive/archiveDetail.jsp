<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

</head>
<body>

   <div id="archive_info" style="margin:0px 40px;">
   
         <br/>
        <div style="height:30px;*+height:42px;">
            <button class="btn_red" style="float:right;margin-right:12px;font-size:12px;" onclick="$('#archive_info').hide();">关闭</button>
        </div>
            <div class="width100 font24 fontweight ">仲裁卷宗信息</div>
           
            <div class="div_span">
                 <span class="span_style_label width_border_noright"><label class="display_nones">案号：</label></span>
                 <span class="span_style width_border_noright"><div id="archiveCode_info" class="display_input"></div></span>
            
                 <span class="span_style_label width_border_noright"><label class="display_nones">创建日期：</label></span>
                 <span class="span_style width_border_noright"><div id="createdOn_info" class="display_input"></div></span>
            
                 <span class="span_style_label width_border_noright"><label class="display_nones">修改日期：</label></span>
                 <span class="span_style width_border"><div id="updatedOn_info" class="display_input"></div></span>
            </div>
            <div class="div_span div_span_left">
                 <span class="span_style_label width_border_noright div_span_Noleft"><label class="display_nones">案由：</label></span>
                 <span class="span_style width_border_one"><div id="archiveName_info" class="display_input598"></div></span>
            </div>
            <div class="div_span div_span_left">
                 <span class="span_style_label width_border_noright div_span_Noleft"><label class="display_nones">处理结果：</label></span>
                 <span class="span_style width_border_one"><div id="archiveStatus_info" class="display_input598"></div></span>
            </div>
            <div class="div_span">
                 <span class="span_style_label width_border_noright"><label class="display_nones">申请人：</label></span>
                <span class="span_style width_border_noright"><div id="archiveApplicant_info" class="display_input"></div></span>
            
                <span class="span_style_label width_border_noright"><label class="display_nones">被申请人：</label></span>
                <span class="span_style width_border_noright"><div id="archiveOppositeApplicant_info" class="display_input"></div></span>
            
                <span class="span_style_label width_border_noright"><label class="display_nones">第三人：</label></span>
                <span class="span_style width_border"><div id="archiveThirdPerson_info" class="display_input"></div></span>
            </div>
            <div class="div_span">
                <span class="span_style_label width_border_noright"><label class="display_nones">承办人：</label></span>
                <span class="span_style width_border_noright"><div id="archiveJudge_info" class="display_input"></div></span>
            
                <span class="span_style_label width_border_noright"><label class="display_nones">立案日期：</label></span>
                <span class="span_style width_border_noright"><div id="archiveOpenDate_info" class="display_input"></div></span>
            
                <span class="span_style_label width_border_noright"><label class="display_nones">结案日期：</label></span>
                <span class="span_style width_border"><div id="archiveCloseDate_info" class="display_input"></div></span>
            </div>
            <div class="div_span">
                <span class="span_style_label width_border_bottom"><label class="display_nones">归档日期：</label></span>
                <span class="span_style width_border_bottom"><div id="archiveDate_info" class="display_input"></div></span>
            
                <span class="span_style_label width_border_bottom"><label class="display_nones">归档号数：</label></span>
                <span class="span_style width_border_bottom"><div id="archiveSerialNumber_info" class="display_input"></div></span>
            
                <span class="span_style_label width_border_bottom"><label class="display_nones"></label></span>
                <span class="span_style width_borders"><div class="display_input"></div></span>
            </div>
            <br/>
        </div>

	<script type="text/javascript">
		

        $("#archiveCode_info").text(rowArchiveData.archiveCode);
        $("#archiveName_info").text(rowArchiveData.archiveName);
        $("#archiveStatus_info").text(formatterArchiveStatus(rowArchiveData.archiveStatus));
        $("#createdOn_info").text(rowArchiveData.createdOn);
        $("#updatedOn_info").text(rowArchiveData.updatedOn);
        $("#archiveApplicant_info").text(rowArchiveData.archiveApplicant);
        $("#archiveOppositeApplicant_info").text(rowArchiveData.archiveOppositeApplicant);
        $("#archiveThirdPerson_info").text(rowArchiveData.archiveThirdPerson);
        $("#archiveJudge_info").text(rowArchiveData.archiveJudge);
        $("#archiveOpenDate_info").text(rowArchiveData.archiveOpenDate);
        $("#archiveCloseDate_info").text(rowArchiveData.archiveCloseDate);
        $("#archiveDate_info").text(rowArchiveData.archiveDate);
        $("#archiveSerialNumber_info").text(rowArchiveData.archiveSerialNumber);
        
		       	
	</script>
</body>
</html>