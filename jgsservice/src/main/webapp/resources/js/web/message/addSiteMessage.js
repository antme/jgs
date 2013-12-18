$(document).ready(function() {
    //$("#content").kendoEditor();
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
    
    var dataTest = [{text:"text1",value:"value1"},{text:"text2",value:"value2"},{text:"text3",value:"value3"}];
    $('#group').combobox({
	    valueField:'value',
	    textField:'text', 
	    multiple:false,
	    data:[{
	    	text:'厂商',
	    	value:'MFC'
	    },{
	    	text:'服务商',
	    	value:'SP'
	    }]
	    /*data:[{
	    	text:'一般用户',
	    	value:'USER'
	    },{
	    	text:'管理员',
	    	value:'ADMIN'
	    },{
	    	text:'超级管理员',
	    	value:'SUPPER_ADMIN'
	    },{
	    	text:'厂商',
	    	value:'MFC'
	    },{
	    	text:'服务商',
	    	value:'SP'
	    },{
	    	text:'客服',
	    	value:'CUSTOMER_SERVICE'
	    }]*/
	});
    
    $('#province').combobox({
	    url:'/ecs/location/listbyparent.do',
	    valueField:'id',
	    textField:'name',
	    multiple:false,
        onSelect: function(rec){
            var cityUrl = '/ecs/location/listbyparent.do?parent='+rec.id;
            $('#city').combobox('reload', cityUrl);
        },
        loadFilter:function(data){
	    	return data.rows;
	    }
	});
    
    $('#city').combobox({
	    valueField:'id',
	    textField:'name',
	    multiple:false,
        onSelect: function(rec){
            var disUrl = '/ecs/location/listbyparent.do?parent='+rec.id;
            $('#district').combobox('reload', disUrl);
        },
        loadFilter:function(data){
	    	return data.rows;
	    }
	});
    
    $('#district').combobox({
	    valueField:'id',
	    textField:'name',
	    multiple:false,
        loadFilter:function(data){
	    	return data.rows;
	    }
	});
    
    $('#status').combobox({
	    valueField:'value',
	    textField:'text',
	    multiple:false,
	    data:[{
	    	text:'冻结',
	    	value:'LOCKED'
	    },{
	    	text:'非冻结',
	    	value:'NORMAL'
	    }]
	});
    
//    $(".datebox").css({"width":"200px","height":"34px","background":"none"});
//    $(".datebox").find(".combo-text").css({"width":"109px","height":"34px","background":"url(resources/images/public_date_left.png) no-repeat","color":"#000"});
//    $(".datebox").find(".combo-arrow").css({"width":"39px","height":"34px","background":"url(resources/images/public_date_right.png) no-repeat"});

    $("#radiodiv :radio").click(function(){
    	var radioValue = $('input[name="all"]:checked').val();
    	if(radioValue == 1){//全选
    		$("#checkboxdiv :input[type='checkbox']").each(function () {
    			this.checked = true;
    		})
    	}else{//反选
    		$("#checkboxdiv :input[type='checkbox']").each(function () {
    			this.checked = !this.checked;
    		})
    	}
    });
    
    loading_css();
    loading_css_t();
    
});

function submitForm(){
	//var ptime = $('#publishTime').datetimebox('getValue');
	var title = $("#title").val();
	if(title == null || title == ""){
		alert("请输入标题！");
		return;
	}
	var editor = $("#content").data("kendoEditor");
	//var content = editor.value();
	var content = CKEDITOR.instances.content.getData();
	if(content == null || content == ""){
		alert("请输入内容！");
		return;
	}
	var ids = new Array();
	$("#checkboxdiv input:checkbox[type='checkbox']:checked").each(function(){
		ids.push(this.value);
	}); 
	var data = {"title":title,"content":content,"userIds":ids};
	postAjaxRequest("/ecs/sitemessage/add.do", data, callback);
}

function callback(data){
	//jump to list page?
	var code = data.code;
	if (code == 200){
		alert("站内消息发布成功！");
		/*$('#callbackdiv').html("<span>********success**********<span/>");
		$('#callbackdiv').show();
		$('#callbackdiv').delay(3000).hide(0)*/
	}else{
		/*$('#callbackdiv').html("<span>********error**********<span/>");
		$('#callbackdiv').show();
		$('#callbackdiv').delay(3000).hide(0)*/
		alert("站内消息发布失败！");
	}
	
}

function search(){
	var group = $("#group").combobox('getValue');
	var province = $("#province").combobox('getValue');
	var city = $("#city").combobox('getValue');
	var district = $("#district").combobox('getValue');
	var status = $("#status").combobox('getValue');
	var keyword = $("#keyword").val();
	
	var searchKeys = {"group":group,"province":province,"city":city,"district":district,"status":status,"keyword":keyword};
	
	postAjaxRequest("/ecs/sitemessage/searchusers.do", searchKeys, searchUsersCallback);
}

function searchUsersCallback(data){
	var users = data.rows;
	var size = users.length;
	var ul = $("#userList");
	$("#userList li").remove();
	for(var i=0; i < size; i++){
		var id = users[i].id;
		var name = users[i].userName;
		var li = "<li><input name='"+id+"' id='"+id+"' type='checkbox' value='"+id+"'/><label for='"+id+"'>"+name+"</label></li>";
		ul.append(li);
	}
}