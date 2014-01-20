function formatterArchiveView(val, row, rowindex) {
	
	return '<a href="?p=flexpaper/simple_document&id=' + row.id + '"><img height="40" width="40" src="/resources/images/print-preview.png"></img></a>';
}

function formatterArchiveApproveView(val, row, rowindex) {
	
	return '<a href="?p=flexpaper/simple_document&action=approve&id=' + row.id + '"><button style="cursor:pointer">审核</button></a>';
}

function formatterArchiveEidt(val, row, rowindex){
	return '<span class="span_style"><a class="table_eidt" href="?p=web/archive/archiveedit&id=' + row.id + '"></a></span><span class="span_style" style="margin-left:20px;"><button class="table_delect" onclick=deletarchiveWindow("'+ row.id+'");>&nbsp;</button></span>';
}
function formatterRecordEidt(val, row, rowindex){
	return '<button class="table_eidt" onclick=getrecordWindow("'+ row.id+'");>&nbsp;</button>';
}


function formatterArchiveStatus(val, row, rowindex) {
	
	if(val == "ARCHIVED"){
		return "已归档";
	}
	
	return "未归档";
	
}

function formatterArchiveProcessStatus(val, row, rowindex) {
	
	if(val == "DRAFT"){
		return "草稿";
	}else if(val == "NEW"){
		return "待审核";
	}else if(val == "APPROVED"){
		return "审核通过";
	}else if(val == "REJECTED"){
		return "审核拒绝";
	}else if(val == "DESTROYING"){
		return "销毁审核中";
	}else if(val == "DESTROYED"){
		return "已销毁";
	}
	
	return "扫描导入";
	
}

function approveArchive(){
	 postAjaxRequest("/ecs/archive/approve.do", {id:id}, function(data){		 
		 window.location.href="index.jsp?p=web/archive/archiveapprove";
		 
	 });
}

function rejectArchive(){
	 postAjaxRequest("/ecs/archive/reject.do", {id:id}, function(data){
		 window.location.href="index.jsp?p=web/archive/archiveapprove";
	 });
}



function initArchiveManagePage(){

	$("#addarchiveForm").form({
	      url : '/ecs/archive/add.do',
	      onSubmit : function() {
	          return $(this).form('validate');
	      },
	      success : function(data) {
	    	  if($("#sid").val()==undefined){
	    		  $.messager.alert("添加档案","添加档案成功！");
			   }else{
				   $.messager.alert("编辑档案","编辑档案成功！");
			   }
	          
	          $('#addarchive').window('close');
	          $("#archiveList").datagrid('reload');
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
	         
	      }else if($("#secondFile").val()=="" || $("#secondFile").val()==null){
	          
	      }else if($("#secondFileAttach").val()=="" || $("#secondFileAttach").val()==null){
	         
	      }else{
	          $("#addarchiveForm").submit();
	      }
	      
	});
	$("#closed").click(function(){
	      $('#addarchive').window('close');
	});
	
	
	$("#delerecordForm").form({
		url : '/ecs/archive/destroy.do',
		   onSubmit : function() {
		       return $(this).form('validate');
		   },
		   success : function(data) {
			   $.messager.alert("信息","销毁档案成功！");
		       $('#delerecord').window('close');
		       $("#archiveList").datagrid('reload');
		   }
	});
}


function initAddBorrowRecordPage(){
	
	$("#addrecordForm").form({
		   url : '/ecs/archive/borrow/add.do',
		   onSubmit : function() {
		       return $(this).form('validate');
		   },
		   success : function(data) {
			   $("#archiveList").datagrid('reload');
			   if($("#sid").val()==undefined){
				   $.messager.alert("添加","新增借阅记录成功！");
			   }else{
				   $.messager.alert("编辑","编辑借阅记录成功！");
			   }
		       $('#addrecord').window('close');
		   }
	});
	
	$("#submitrecord").click(function(){
		   $("input[name=archiveId]").val($("#archiveId").combobox('getValue'));
		   $("#borrowingDate").val($('#borrowingDate').datebox('getValue'));
		   if($("#borrowingDate").val()=="" || $("#borrowingDate").val()==null){
		     $.messager.alert("添加失败","请选择调阅日期！");
		   }else{
		     $("#addrecordForm").submit();
		   }
	});
	
}

//档案管理 事件
function openAddGroupWindow(){
    $('#addarchive').window('setTitle', "新增卷宗");
    openDialog("addarchive");
    $("#addarchiveForm").form("clear");
    $(".window-shadow").css("height","auto");
    $("#sid").remove();
}
function getarchiveWindow(id){
    	$('#addarchive').window('setTitle', "编辑借阅记录");
        openDialog("addarchive");
        postAjaxRequest("/ecs/archive/get.do", {id:id}, function(data){
     	   $("#addarchiveForm").form("clear");
     	   $("#addarchiveForm").form("load",data.data);
     	   $("#addarchiveForm").append("<input id='sid' name='id' type='hidden' value='"+id+"' />");
        });
        
}

function deletarchiveWindow(id){
	$('#delerecord').window('setTitle', "销毁卷宗");
    openDialog("delerecord");
    $("#delerecordForm").form("clear");
    $("#did").val(id);
}

function deletarchive(){
	if($("#destroyComments").val()=="" || $("#destroyComments").val()==null){
		$.messager.alert("销毁失败","请选择填写销毁原因！");
	}else{
		$("#delerecordForm").submit();
	}
}





// 借阅管理 事件
var myloader = function(param,success,error){
    var q = param.q || '';
   
    $.ajax({
        url: ' /ecs/archive/listArchives.do',
        dataType: 'json',
        data: {
            name_startsWith: q
        },
        success: function(data){
            var items = $.map(data.rows, function(item){
                return {
                    id: item.id,
                    name: item.archiveCode 
                };
            });
            success(items);
        },
        error: function(){
            error.apply(this, arguments);
        }
    });
}
function openAddrecordWindow(){
    $('#addrecord').window('setTitle', "新增借阅记录");
       openDialog("addrecord");
       $("#addrecordForm").form("clear");
       $("#sid").remove();
}

function closedwindows(obj){
   $('#'+obj).window('close');
}

function getrecordWindow(id){
   $('#addrecord').window('setTitle', "编辑借阅记录");
   openDialog("addrecord");
   postAjaxRequest("/ecs/archive/borrow/get.do", {id:id}, function(data){
	   $("#addrecordForm").form("clear");
	   $("#addrecordForm").form("load",data.data);
   });
   $("#addrecordForm").append("<input id='sid' name='id' type='hidden' value='"+id+"' />");
	  
}



function loadReport(){
	
}
