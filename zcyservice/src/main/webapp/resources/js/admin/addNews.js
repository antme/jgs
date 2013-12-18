var titleKeyword;
$(document).ready(function() {
    /*$(".datebox").css({"width":"152px","height":"34px","background":"none"});
    $(".datebox").find(".combo-text").css({"width":"109px","height":"34px","background":"url(resources/images/public_date_left.png) no-repeat","color":"#000"});
    $(".datebox").find(".combo-arrow").css({"width":"39px","height":"34px","background":"url(resources/images/public_date_right.png) no-repeat"});
    */
	CKEDITOR.replace( 'content', {
    	toolbar: [
    		[ 'Source' ],
    		[ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo' ],
			[ 'Link', 'Unlink', 'Anchor' ],
			[ 'Image', 'Table', 'HorizontalRule', 'SpecialChar' ] ,
			[ 'Maximize' ],
			'/',
			[ 'Bold', 'Italic', 'Strike', '-', 'RemoveFormat' ],
			[ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote' ],
			[ 'Font','FontSize']
    		
    	]
    });
});

function submitForm(){
	var isTop = 0;
	if($("#isTop").is(":checked")){
		isTop = 1;
	}
	var ptime = $('#publishTime').datetimebox('getValue');
	var etime = $('#expiredTime').datetimebox('getValue');
	var title = $("#title").val();
	if(title == ""){
		$('#title').focus();
	}
	if(ptime == ""){
		//$('#publishTime').focus();
		alert("请选择发布时间！");return;
	}
	if(etime == ""){
		//$('#expiredTime').focus();
		alert("请选择结束时间！");return;
	}
	
	/*var editor = $("#content").data("kendoEditor");
	var content = editor.value();*/
	var content = CKEDITOR.instances.content.getData();
	if(content == ""){
		alert("请填写内容！");
	}
	var data = {"title":title,"content":content,"publishTime":ptime,"expiredTime":etime,"isTop":isTop};
	if(ptime != "" && etime != ""){
		postAjaxRequest("/ecs/news/add.do", data, callback);
	}else{
		alert("请填写信息！");
	}
}

function callback(data){
	var code = data.code;
	if (code == 200){
		alert("新闻添加成功！");
	}else{
		alert("新闻添加失败！");
	}
}