$(document).ready(function() {
    $(".datebox").css({"width":"200px","height":"34px","background":"none"});
    $(".datebox").find(".combo-text").css({"width":"109px","height":"34px","background":"url(resources/images/public_date_left.png) no-repeat","color":"#000"});
    $(".datebox").find(".combo-arrow").css({"width":"39px","height":"34px","background":"url(resources/images/public_date_right.png) no-repeat"});
    
    //FIXME: NOT USE KENDO
    $("#files").kendoUpload({
        async: {
            saveUrl: "/ecs/ad/upload.do",
            autoUpload: true
        },
        upload:function onUpload(e) {
            var files = e.files;
            $.each(files, function () {
            	var ex = this.extension.toLowerCase();
                if (!(ex == ".jpg" || ex == ".gif" || ex == ".png")) {
                    alert("只支持后缀jpg/gif/png")
                    e.preventDefault();
                }
            });
        },
        localization: {
            select: "广告图片..."
        },
        showFileList: false,
        success:function(e){
        	var url = e.response.url;
        	$("#imageUrl").val(url);
    		$("#previewImage").attr("src",url);
        }
    });	
});

function add(){
	//$('#addAdWindow').window('open');
	openDialog("addAdWindow");
}

function submitForm(){
	var ptime = $('#publishTime').datetimebox('getValue');
	var page = $("#page").combobox('getValue');
	var location = $("#location").combobox('getValue');
	var imageUrl = $("#imageUrl").val();
	var href = $("#href").val();
	var data = {"page":page,"location":location,"imageUrl":imageUrl,"href":href,"publishTime":ptime};
	if(ptime==""){
		alert("请填写时间！");
	}else{
		postAjaxRequest("/ecs/ad/add.do", data, callback);
	}
}

function callback(){

	$('#addAdWindow').window('close');
	$('#adlist').datagrid('reload');
}

function preview(){
	//$('#previewWindow').window('open');
	openDialog("previewWindow");
}