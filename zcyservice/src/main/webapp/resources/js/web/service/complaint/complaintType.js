$(document).ready(function() {
	
});

function operating(){
	var row = $('#compTypeList').datagrid('getSelected');
    if (row){
    	var name = row.name;
    	var des = row.description;
    	var id = row.id;

    	$("#name").val(name);
    	$("#description").val(des);
    	$("#compTypeId").val(id);
    }else{
    	$("#name").val("");
    	$("#description").val("");
    	$("#compTypeId").val(null);
    }
    
    //$('#operCompTypeWindow').window('open');
    openDialog("operCompTypeWindow");
}

function editComplaintType(id){
	 $('#addCompTypeForm').form('clear');
	var rows=$("#compTypeList").datagrid('getRows');
    for(var i=0;i<rows.length;i++){
    	if(rows[i].id==id){
    		var name = rows[i].name;
        	var des = rows[i].description;
        	var id = rows[i].id;

        	$("#name").val(name);
        	$("#description").val(des);
        	$("#compTypeId").val(id);
        	$("#orderPosition").val(rows[i].orderPosition);
        	break;
    	}
    }
   // $('#operCompTypeWindow').window('open');
    openDialog("operCompTypeWindow");
}

function deleteComplaintType(id){
	var data = {"id":id};
	postAjaxRequest("/ecs/complaint/complaintType/del.do", data, delcallback);
}

function delcallback(){
	$('#compTypeList').datagrid('reload');
}

function add(){
	 $('#addCompTypeForm').form('clear');
    
    //$('#operCompTypeWindow').window('open');
    openDialog("operCompTypeWindow");
}

function submitForm(){
	
	var name = $("#name").val();
	var des = $("#description").val();
	var id = $("#compTypeId").val();
	
	if (name==null || name == ""){
		alert("类型名称不能为空！");
		return;
	}
	
	var data = {"id":id,"name":name,"description":des, "orderPosition": $("#orderPosition").val()};
	postAjaxRequest("/ecs/complaint/complaintType/add.do", data, callback); 
}

function callback(){
	$('#operCompTypeWindow').window('close');
	$('#compTypeList').datagrid('reload');
}

function formatterOperation(val, row) {
	return '<button onclick=editComplaintType("' + row.id + '")>编辑</button>&nbsp;&nbsp;<button onclick=deleteComplaintType("' + row.id + '");>删除</button>';
}