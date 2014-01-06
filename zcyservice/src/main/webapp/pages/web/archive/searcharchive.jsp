<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div class="line_clear"></div>
    <div class="public_search_div">
        <div class="line_clear"></div>
        <div class="line_seach">
            <span class="span_style_label"><label class="display_nones">档案编号：</label></span>
            <span class="span_style"><input id="mfcKeyword" class="public_search_input_text display_nones" /></span> 
            <span class="span_style_label"><label class="display_nones">档案名称：</label></span>
            <span class="span_style"><input id="mfcKeyword" class="public_search_input_text display_nones" /></span> 
            
            <span class="span_style_label"><label class="display_nones pdf">档案状态：</label></span>
            <span class="span_style"> 
                 <select class="easyui-combobox display_nones" style="width:128px;height:30px;background:url(/resources/images/public_select.png) no-repeat;" data-options="multiple:false" id="mfcSearchStatus">
                    <option value="" selected>档案状态</option>
                    <option value="NORMAL">已归档</option>
                    <option value="LOCKED">未归档</option>
                 </select>
            </span> 
          <div class="line_clear"></div>
            <span class="span_style_label"><label class="display_nones">档案归档时间：</label></span>
            <input class="easyui-datebox" required missingMessage="请选择开始时间" style="width:150px" name="startDate" id="startDate"/>
            <span class="span_style_label">&nbsp;至&nbsp;&nbsp;&nbsp;</span>
            <input class="easyui-datebox" required missingMessage="请选择结束时间"  style="width:150px" name="endDate" id="endDate"/>
            <span class="span_style_label"><label class="display_nones">档案关键字：</label></span>
            <span class="span_style"><input id="mfcKeyword" class="public_search_input_text display_nones" /></span>         
            <span class="span_style" style="margin-left:25px;"><button class="public_search_btn display_nones" onclick="mfcsearch()"></button></span>
        </div>
        <div class="line_clear"></div>
    </div>