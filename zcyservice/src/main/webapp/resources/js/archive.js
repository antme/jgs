function formatterArchiveView(val, row, rowindex) {
	
	return '<a href="?p=flexpaper/simple_document&id=' + row.id + '"><img height="40" width="40" src="/resources/images/print-preview.png"></img></a>';
}

function formatterArchiveEidt(val, row, rowindex){
	return '<button class="table_eidt" onclick=getarchiveWindow("'+ row.id+'");>&nbsp;</button>';
}
function formatterRecordEidt(val, row, rowindex){
	return '<button class="table_eidt" onclick=getrecordWindow("'+ row.id+'");>&nbsp;</button>';
}

$(document).ready(function(){

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
	
	$("#addrecordForm").form({
		   url : '/ecs/archive/borrow/add.do',
		   onSubmit : function() {
			   $(this)[0].archiveId.value= $("#archiveId").combobox('getText');
		       return $(this).form('validate');
		   },
		   success : function(data) {
		       $.messager.alert("添加档案","新增借阅记录成功！");
		       $('#addrecord').window('close');
		   }
	});
	
	$("#submitrecord").click(function(){
		   $("#borrowingDate").val($('#borrowingDate').datebox('getValue'));
		   if($("#borrowingDate").val()=="" || $("#borrowingDate").val()==null){
		     $.messager.alert("添加失败","请选择调阅日期！");
		   }else{
		     $("#addrecordForm").submit();
		   }
	});
});

//档案管理 事件
function openAddGroupWindow(){
    $('#addarchive').window('setTitle', "新增卷宗");
    openDialog("addarchive");
    $("#addarchiveForm").form("clear");
}
function getarchiveWindow(id){
    	$('#addarchive').window('setTitle', "编辑借阅记录");
        openDialog("addarchive");
        $.ajax({
            url: '/ecs/archive/get.do',
            dataType: 'json',
            data: {
                id: id
            },
            success: function(data){
                $("#addarchiveForm").form("clear");
                $("input[name='archiveCode']").val(data.data.archiveCode);
                $("input[name='archiveName']").val(data.data.archiveName);
                $("input[name='archiveResult']").val(data.data.archiveResult);
                $("input[name='archiveApplicant']").val(data.data.archiveApplicant);
                $("input[name='archiveOppositeApplicant']").val(data.data.archiveOppositeApplicant);
                $("input[name='archiveThirdPerson']").val(data.data.archiveThirdPerson);
                $("input[name='archiveJudge']").val(data.data.archiveJudge);
                $("#archiveOpenDate").datebox('setValue', data.data.archiveOpenDate);  
                $("#archiveCloseDate").datebox('setValue', data.data.archiveCloseDate);
                $("#archiveDate").datebox('setValue', data.data.archiveDate);
                $("input[name='archiveSerialNumber']").val(data.data.archiveSerialNumber);
            }
        });
}


// 借阅管理 事件
var myloader = function(param,success,error){
    var q = param.q || '';
    if (q.length <= 1){return false}
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
}

function closedwindows(obj){
   $('#'+obj).window('close');
}

function getrecordWindow(id){
   $('#addrecord').window('setTitle', "编辑借阅记录");
   openDialog("addrecord");
	   $.ajax({
           url: '/ecs/archive/borrow/get.do',
           dataType: 'json',
           data: {
        	   id: id
           },
           success: function(data){
        	   $("#addrecordForm").form("clear");
        	   $("#archiveId").combobox("setValue",data.data.archiveId);
        	   $("input[name='borrowingName']").val(data.data.borrowingName);
        	   $("input[name='borrowingOrganization']").val(data.data.borrowingOrganization);
        	   $("#borrowingDate").datebox('setValue', data.data.borrowingDate);
        	   $("#remark").val(data.data.remark);
           }
       });
}


