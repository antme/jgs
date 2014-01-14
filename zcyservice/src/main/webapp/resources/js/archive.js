function formatterArchiveView(val, row, rowindex) {
	
	return '<a href="?p=flexpaper/simple_document&id=' + row.id + '"><img height="40" width="40" src="/resources/images/print-preview.png"></img></a>';
}

//档案管理 事件



// 借阅管理 事件
function openAddrecordWindow(){
	 $('#addrecord').window('setTitle', "新增借阅记录");
	    openDialog("addrecord");
}

$("#addrecordForm").form({
    url : '/ecs/archive/borrow/add.do',
    onSubmit : function() {
        return $(this).form('validate');
    },
    success : function(data) {
        $.messager.alert("添加档案","添加档案成功！");
        $('#addrecord').window('close');
    }
});

function closedwindows(obj){
	$('#'+obj).window('close');
}
$("#submitrecord").click(function(){
	console.log("1");
	$("#borrowingDate").val($('#borrowingDate').datebox('getValue'));
    if($("#borrowingDate").val()=="" || $("#borrowingDate").val()==null){
  	  $.messager.alert("添加失败","请选择调阅日期！");
    }else{
    	console.log("1");
  	  $("#addrecordForm").submit();
    }
});


