function change_data(){
	var c_s_province =$('#c_s_province').combobox('getValue');
	var c_s_city =$('#c_s_city').combobox('getValue');
	var c_s_county =$('#c_s_county').combobox('getValue');
	var c_s_category =$('#c_s_category').combobox('getValue');
	if(c_s_province=="0" || c_s_province==""){
		alert("请选择省");
	}else{
		if(c_s_city=="0" || c_s_city==""){
			alert("请选择市");
		}else{
			if(c_s_county=="0" || c_s_county==""){
				alert("请选择区");
			}else{
				if(c_s_category=="0" || c_s_category==""){
					alert("请选择产品类别！");
				}else{
					seach_price(c_s_county,c_s_category);
				}
			}
		}
	}
}
function seach_price(lid, category_id) {

	var data = {
		"category_id" : category_id,
		"location_id" : lid
	}

	$('#price_list').datagrid("reload", data);

}