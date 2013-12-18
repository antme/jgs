var titleKeyword;
$(document).ready(function() {
    //$("#content").kendoEditor();
    $(".datebox").css({"width":"152px","height":"34px","background":"none"});
    $(".datebox").find(".combo-text").css({"width":"109px","height":"34px","background":"url(resources/images/public_date_left.png) no-repeat","color":"#000"});
    $(".datebox").find(".combo-arrow").css({"width":"39px","height":"34px","background":"url(resources/images/public_date_right.png) no-repeat"});
    
    $('#newslist').datagrid({
        url:'/ecs/news/list.do',
        singleSelect:true/*,
        queryParams: {
        	keyword: titleKeyword
        }*/
    });
});

function searchNewsByTitle(){
	titleKeyword = $("#titleKeyword").val();
	var param = {"keyword":titleKeyword}
	$('#newslist').datagrid('load',param);
}

function add(){
	//$('#addNewsWindow').window('open');
	openDialog("addNewsWindow");
}

function submitForm(){
	var isTop = 0;
	if($("#isTop").is(":checked")){
		isTop = 1;
	}
	var ptime = $('#publishTime').datetimebox('getValue');
	var title = $("#title").val();
	/*var editor = $("#content").data("kendoEditor");
	var content = editor.value();*/
	var content = CKEDITOR.instances.content.getData();
	var data = {"title":title,"content":content,"publishTime":ptime,"isTop":isTop};
	if(ptime!=""){
		postAjaxRequest("/ecs/news/add.do", data, callback);
	}else{
		alert("请填写信息！");
	}
	
}

function callback(){
	$('#addNewsWindow').window('close');
	$('#newslist').datagrid('reload');
}