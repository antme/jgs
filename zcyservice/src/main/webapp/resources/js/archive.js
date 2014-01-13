function formatterArchiveView(val, row, rowindex) {
	
	return '<a href="?p=flexpaper/simple_document&id=' + row.id + '"><img height="40" width="40" src="/resources/images/print-preview.png"></img></a>';
}

//档案管理 事件
function openAddGroupWindow(){
    $('#addarchive').window('setTitle', "新增卷宗");
    openDialog("addarchive");
}

$("#addarchiveForm").form({
      url : '/ecs/archive/add.do',
      onSubmit : function() {
          return $(this).form('validate');
      },
      success : function(data) {
          $.messager.alert("添加档案","添加档案成功！");
          $('#addarchive').window('close');
      }
});
$("#submited").click(function(){
	  $("#archiveOpenDate").val($('#archiveOpenDate').datebox('getValue'));
	  $("#archiveCloseDate").val($('#archiveCloseDate').datebox('getValue'));
      if($("#archiveOpenDate").val()=="" || $("#archiveOpenDate").val()==null){
    	  $.messager.alert("添加失败","请选择立案日期！");
      }else if($("#archiveCloseDate").val()=="" || $("#archiveCloseDate").val()==null){
    	  $.messager.alert("添加失败","请选择结案日期！");
      }else if($("#mainFile").val()=="" || $("#mainFile").val()==null){
          $.messager.alert("添加失败","请上传正卷宗！");
      }else if($("#mainFilkeAttach").val()=="" || $("#mainFilkeAttach").val()==null){
          $.messager.alert("添加失败","请上传正卷宗附件！");
      }else if($("#secondFile").val()=="" || $("#secondFile").val()==null){
          $.messager.alert("添加失败","请上传副卷宗！");
      }else if($("#secondFileAttach").val()=="" || $("#secondFileAttach").val()==null){
          $.messager.alert("添加失败","请上传副卷宗附件！");
      }else{
    	  $("#addarchiveForm").submit();
      }
      
});
$("#closed").click(function(){
      $('#addarchive').window('close');
});


// 借阅管理 事件
function openAddrecordWindow(){
	 $('#addrecord').window('setTitle', "新增借阅记录");
	    openDialog("addrecord");
}

$("#addrecordForm").form({
    url : '/ecs/archive/add.do',
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
function submitrecord(){
	$("#borrowingDate").val($('#borrowingDate').datebox('getValue'));
    if($("#borrowingDate").val()=="" || $("#borrowingDate").val()==null){
  	  $.messager.alert("添加失败","请选择调阅日期！");
    }else{
  	  $("#addrecordForm").submit();
    }
}


