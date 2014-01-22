function formatterArchiveView(val, row, rowindex) {
	
	return '<a href="?p=flexpaper/simple_document&id=' + row.id + '"><img height="16" width="16" src="/resources/images/print-preview.png"></img></a>';
}

function formatterArchiveType(val, row, rowindex) {
	
	if(val == "MAIN"){
		return "正卷中";
	}
	
	return "副卷中";
	
}


function formatterArchiveApproveView(val, row, rowindex) {
	
	return '<span class="span_style"><a href="?p=flexpaper/simple_document&id=' + row.id + '"><img height="16" width="16" src="/resources/images/print-preview.png"></img></a></span><span class="span_style" style="margin-left:10px;"><a href="?p=flexpaper/simple_document&action=approve&id=' + row.id + '"><button class="sh" style="cursor:pointer"></button></a></span>';
}

function formatterArchiveEidt(val, row, rowindex){
	return '<span class="span_style"><a href="?p=flexpaper/simple_document&id=' + row.id + '"><img height="16" width="16" src="/resources/images/print-preview.png"></img></a></span><span class="span_style" style="margin-left:5px;"><a class="table_eidt" href="?p=web/archive/archiveedit&id=' + row.id + '"></a></span><span class="span_style" style="margin-left:5px;"><button class="table_delect" onclick=deletarchiveWindow("'+ row.id+'");>&nbsp;</button></span>';
}
function formatterRecordEidt(val, row, rowindex){
	return '<span class="span_style"><a href="?p=flexpaper/simple_document&id=' + row.id + '"><img height="16" width="16" src="/resources/images/print-preview.png"></img></a></span><span class="span_style" style="margin-left:5px;"><button class="table_eidt" onclick=getrecordWindow("'+ row.id+'");>&nbsp;</button></span>';
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
	    	  console.log("...........");
	          return $(this).form('validate');
	      },
	      success : function(data) {
	
	    	  
	    	  if($("#sid").val()==undefined){
	    		  
	    		  dealMessageWithCallBack(data, "添加档案", function(){
	    			  $("#sid").val(data.id);
		    		  $.messager.alert("添加档案","添加档案成功！");
	    		  })
			   }else{
				   dealMessageWithCallBack(data, "编辑档案", function(){
					   $.messager.alert("编辑档案","编辑档案成功！");
				   })				  
			   }  
	
	      }
	});
	$("#submited").click(function(){
	      $("#archiveOpenDate").val($('#archiveOpenDate').datebox('getValue'));
	      $("#archiveCloseDate").val($('#archiveCloseDate').datebox('getValue'));
	      if($("#mainFile").val()=="" || $("#mainFile").val()==null){
	          $.messager.alert("添加失败","请上传卷宗！");
	      }else{
	          $("#addarchiveForm").submit();
	      }
	      
	});

	
	$("#delerecordForm").form({
		url : '/ecs/archive/destroy.do',
		   onSubmit : function() {
		       return $(this).form('validate');
		   },
		   success : function(data) {
			   $.messager.alert("信息","提交档案销毁申请成功，请耐心等待审核！");
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
		   var archiveId = $("#archiveId").combobox('getValue');
		   if($("#borrowingDate").val()=="" || $("#borrowingDate").val()==null){
		     $.messager.alert("添加失败","请选择调阅日期！");
		   }else if(archiveId=="" || archiveId==null){
			   $.messager.alert("添加失败","请选择卷宗！");
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
		$.messager.confirm('信息','审核过后档案将彻底删除，请确认是否销毁?',function(r){   
		    if (r){   
		    	$("#delerecordForm").submit();  
		    }else{
		    	$('#delerecord').window('close');
		    }   
		});  
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



