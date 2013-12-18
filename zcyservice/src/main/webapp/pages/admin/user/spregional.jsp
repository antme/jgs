<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的订单</title>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
</head>
<body>

   <div class="page_tip">说明： 服务价格为空表示管理员还未设置此区域价格,订单也不会分配给任何服务商</div>
   <span class="span_style"><input id="c_s_province" id="c_s_province" class="easyui-combobox " data-options=" valueField:'id',textField:'text',width:128,multiple:false" style="height:35px"/></span>
   <span class="span_style"><input id="c_s_city" id="c_s_city" class="easyui-combobox " data-options=" valueField:'id',textField:'text',width:128,multiple:false" style="height:35px"/></span>
   <span class="span_style"><input id="c_s_county" id="c_s_county" class="easyui-combobox " data-options=" valueField:'id',textField:'text',width:128,multiple:false" style="height:35px"/></span>
   <span class="span_style"><input id="c_s_category_one"  class="easyui-combobox " data-options=" valueField:'id',textField:'text',width:128,multiple:false" style="height:35px"/></span>
   <span class="span_style"><input id="c_s_category_two"  class="easyui-combobox " data-options=" valueField:'id',textField:'text',width:128,multiple:false" style="height:35px"/></span>
   <span class="span_style"><input id="c_s_category_three"  class="easyui-combobox " data-options=" valueField:'id',textField:'text',width:128,multiple:false" style="height:35px"/></span>
   <br/>
   <br/>
   <span class="span_style">服务商关键字:</span><span class="span_style"><input name="spkeyword" id="spkeyword" class="tpublic_input" style="margin-left:10px;"/></span>
   
   <span class="span_style"><button class="public_btn " onclick="seachMyRegionalList();">&nbsp</button></span>
   <div class="p_height_div"></div>
	<table id="myRegionalList" class="easyui-datagrid_tf" data-options="checkOnSelect:false, remoteFilter:true, fitColumns: true, singleSelect:true, queryParams: {isAdmin:true}" url="/ecs/sp/regionallist.do" iconCls="icon-save" pagination="true" >
		<thead>
			<tr>
				<th align="center"  field="caWholeName"    width="250">品类</th>
				<th align="center"  field="loWholeName"    width="250">服务区域</th>
				<th align="center"  field="spUserName"    width="250">服务商</th>
				<th align="center"  field="price"  width="100" >服务价格(元)</th>
				
			</tr>
		</thead>
	</table>

   

<script type="text/javascript">
$(document).ready(function(){
    loadcate_data("c_s_category_one","c_s_category_two","c_s_category_three");
});
function priceFormatter(val, row){
	if(!val){
		return "管理员未设置价格"
		
	}
	
	return val;
}
function seachMyRegionalList(){
	   
	   var data = {
               "provinceId" : $('#c_s_province').combobox('getValue'),
               "cityId" : $('#c_s_city').combobox('getValue'),
               "countyId" : $('#c_s_county').combobox('getValue'),
               "categoryGrandpaId" : $('#c_s_category_one').combobox('getValue'),
               "categoryParentId" : $('#c_s_category_two').combobox('getValue'),
               "categoryId" : $('#c_s_category_three').combobox('getValue'),
               "spkeyword": $('#spkeyword').val(),
               "isAdmin" : "true"
           }

	   $('#myRegionalList').datagrid("reload", data);
}



</script>
</body>
</html>