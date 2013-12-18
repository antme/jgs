var c_data;
var childen_data;
var childen_id;
var childen_name;
var tle;
var did;
var run = true;
var level;
$(document).ready(function(){
	append();
	ajaxLoading();
	$.ajax({
		type:'post',
		url:'/ecs/location/list.do',
		dataType: 'json',
		success:function(data){
			c_data=data.rows;
			for(var i=0;i<c_data.length;i++){
				childen_data=c_data[i].childen;
				if(c_data[i].name=="澳门" || c_data[i].name=="台湾" || c_data[i].name=="香港"){
					continue;
				}else{
					var str=" <li id='"+c_data[i].id+"'isVisible='"+c_data[i].isVisible+"' class=\"list_li_class\"><div sid='"+c_data[i].id+"' class=\"provinces\">"+c_data[i].name+"</div><button sid='"+c_data[i].id+"' fname='"+c_data[i].name+"' class=\"disy6 btn disy_red daslpa\">编辑</button><div class=\"city2\"><ul></ul><div></li>";
				}
				$("#"+letters(c_data[i].name)).append(str);	
				$(".list_li_class").find(".disy6").click(function(){
		    		$(".back_grue").show();
		    		openDialog("dlg2", '编辑省直辖市');
		    		$("#dlg2").find("#twoid").val($(this).parent().attr("id"));
		    		$("#dlg2").find("#twoname").val($(this).parent().find(".provinces").text());
		    		if($(this).parent().attr("isVisible")!='false'){
		    			$("#dlg2").find("#select-Visible2").get(0).selectedIndex=0;
		    		}else{
		    			$("#dlg2").find("#select-Visible2").get(0).selectedIndex=1;
		    		}
		    	});
		    	var ar=$("#"+c_data[i].id).find(".city2");
		    	for(var j=0;j<childen_data.length;j++){
		    		if(c_data[i].childen[j].name!==""){	
		    	    tle=c_data[i].childen[j].childen;	
		    	    var strurl=" <li id="+c_data[i].childen[j].id+" isVisible="+c_data[i].childen[j].isVisible+" isHot="+c_data[i].childen[j].isHot+" parentid="+c_data[i].childen[j].parent_id+" title="+c_data[i].childen[j].name+"><a class=\" \">"+c_data[i].childen[j].name+"</a><button class=\"disy5 btn disy_red daslpa\">编辑</button></li>";
		    		ar.find("ul").append(strurl);
		    		$("#"+c_data[i].childen[j].id).find(".disy5").click(function(){
		    			$(".back_grue").show();
			    		openDialog("dlg3", '编辑市');
			    		$("#dlg3").find("#lid").val($(this).parent().attr("id"));
			    		$("#dlg3").find("#lparent_id").val($(this).parent().attr("parentid"));
			    		$("#dlg3").find("#lname").val($(this).parent().find("a").text());
			    		if($(this).parent().attr("isVisible")!='false'){
			    			$("#dlg3").find("#select-Visible3").get(0).selectedIndex=0;
			    		}else{
			    			$("#dlg3").find("#select-Visible3").get(0).selectedIndex=1;
			    		}
			    		if($(this).parent().attr("isHot")!='false'){
			    			$("#dlg3").find("#select-hot").get(0).selectedIndex=0;
			    		}else{
			    			$("#dlg3").find("#select-hot").get(0).selectedIndex=1;
			    		}
		    		    run=false;
		    		});
		    		$("#"+c_data[i].childen[j].id).find("a").click(function(){
		    			var tid=$(this).parent("li").attr("id");
		    			var X = $(this).offset().top;
		    			X=X-20;var Y=167;
		    			$(".city2").find("li").find("a").css("color","#000");
			    		$(this).css("color","red");
			    		$(".county2").css({"left":Y+"px","top":X+"px","display":"block"});
		    		    loading_county(tid);
		    		    run=false;
		    		});
		    		}
		    	}
		    	var strurl=" <li class='sd_id' parentid="+c_data[i].id+" ><button class=\"disy5 btn disy_green daslpa\" display>+添加</button></li>";
		        ar.find("ul").append(strurl);
		        $(".sd_id").find(".disy5").click(function(){
		        	$(".back_grue").show();
		    		openDialog("dlg4", '添加市');
		    		$("#dlg4").find("#addparent_id").val($(this).parent().attr("parentid"));
		    		$("#dlg4").find("#addname").val("");
		    		run=false;
		    	});
			}
			display_ul();
			loading_hotcity();
			ajaxLoadEnd();
		}
	});
	$("#cate_onesubmit").click(function(){
		levelone_data();
		adm("cate_one");
		run=false;
	});
	$("#locationsubmit").click(function(){
		locationdata();
		adm("location");
		run=false;
	});
	$("#leveltwosubmit").click(function(){
		leveltwo_data();
		adm("cate_two");
		run=false;
	});
	$("#addeveltwosubmit").click(function(){
		addtwo_data();
		adm("cate_two_add");
		run=false;
	});
	
	$("#addcate_three_btn").click(function(){
		addlevelcate_data();
		adm("addcate_three");
		run=false;
	});
	$("#cate_three_btn").click(function(){
		levelcate_data();
		adm("cate_three");
		run=false;
	});
	
	$("#closeddlg4").click(function(){
		$(".back_grue").hide();
		$('#dlg').dialog('close');
		run=false;
	});
	$("#closeddlg1").click(function(){
		$(".back_grue").hide();
		$('#dlg1').dialog('close');
		run=false;
	});
	$("#closeddlg2").click(function(){
		$(".back_grue").hide();
		$('#dlg2').dialog('close');
		run=false;
	});
	$("#closeddlg3").click(function(){
		$(".back_grue").hide();
		$('#dlg3').dialog('close');
		run=false;
	});
	$("#closeddlg5").click(function(){
		$(".back_grue").hide();
		$('#dlg4').dialog('close');
		run=false;
	});
	$("#closeddlg6").click(function(){
		$(".back_grue").hide();
		$('#dlgadd').dialog('close');
		run=false;
	});
	
	$(".county2").click(function(){
		run=false;
	});
	$("#dlg").click(function(){
		return false;
	});
	$("#cate_three").click(function(){
		return false;
	});
	
	$(".center_list").find("li").click(function(){
		$(".center_list").find("ul").find("li").removeClass("mouseclass");
	    $(this).addClass("mouseclass");
	    var text=$(this).text();
	    var objtext=$("#"+text);
	    if(objtext.length==0){
	    }
	    else{
	    	var top=$("#"+text).offset().top;
	    	document.body.scrollTop=top;
	    	document.documentElement.scrollTop=top;
	    }
	    
	});
	
	$(".panel-tool-close").click(function(){$(".back_grue").hide();});
});
function addlevelcate_data(){
	var name,isVisible,defaultAddress,parent_id;
	$("#addcate_three").form({
		url : '/ecs/location/save.do',
		onSubmit : function() {
			name=$(this)[0].name.value;
			parent_id=$(this)[0].parent_id.value;
			if($("#select-Visible6").val()=="0"){
				$(this)[0].isVisible.value=true;
				isVisible=true;
			}else{
				$(this)[0].isVisible.value=false;
				isVisible=false;
			}
			return $(this).form('validate');
		},
		success : function(data) {
			var jsondata = $.parseJSON(data);
			var sturl=" <li id='"+jsondata.id+"' isVisible='"+isVisible+"' defaultAddress='"+defaultAddress+"' parentid='"+parent_id+"' title='"+name+"'><a>"+name+"</a><button class=\"disy3 btn disy_red daslpa\" >编辑</button></li>";
    		$("#"+jsondata.id).find(".county2").find("ul").find(".sd_id").append(sturl);
    		var a_county=$("#"+jsondata.id).find(".disy3");
    		a_county.click(function(){
    			$(".back_grue").show();
    			openDialog("dlg", '编辑区域');
	    		$("#dlg").find("#_id").val($(this).parent().attr("id"));
	    		$("#dlg").find("#_parent_id").val($(this).parent().attr("parentid"));
	    		$("#dlg").find("#_name").val($(this).parent().find("a").text());
	    		if($(this).parent().attr("defaultAddress")!="undefined"){
	    			$("#dlg").find("#_defaultAddress").val($(this).parent().attr("defaultAddress"));
	    		}else{
	    			$("#dlg").find("#_defaultAddress").val("");
	    		}
	    		run=false;
	    	});
			$(".back_grue").hide();
			$('#dlgadd').dialog('close');
			submit_cate_callback();
		}
	});
}
function levelcate_data(){
	var name,id,isVisible,defaultAddress;
	$("#cate_three").form({
		url : '/ecs/location/save.do',
		onSubmit : function() {
			name=$(this)[0].name.value;
			id=$(this)[0].id.value;
			defaultAddress=$(this)[0].defaultAddress.value;
			if($("#select-Visible5").val()=="0"){
				$(this)[0].isVisible.value=true;
				isVisible=true;
			}else{
				$(this)[0].isVisible.value=false;
				isVisible=false;
			}
			return $(this).form('validate');
		},
		success : function(data) {
			$(".back_grue").hide();
			$('#dlg').dialog('close');
			$("#"+id).find("a").text(name);
			$("#"+id).attr({"title":name,"defaultAddress":defaultAddress,"isVisible":isVisible});
			submit_cate_callback();
		}
	});
}
function locationdata(){
	var name,id,isVisible;
	$("#location").form({
		url : '/ecs/location/save.do',
		onSubmit : function() {
			if($("#Visible").val()=="0"){
				$(this)[0].isVisible.value=true;
				isVisible=true;
			}else{
				$(this)[0].isVisible.value=false;
				isVisible=false;
			}
			name=$(this)[0].name.value;
			return $(this).form('validate');
		},
		success : function(data) {
			var jsondata = $.parseJSON(data);
			var stri="<li id='"+jsondata.id+"' isVisible='"+isVisible+"'  class=\"list_li_class\"><div class=\"provinces\" sid='"+jsondata.id+"'>"+name+"</div><button class=\"disy5 btn disy_red daslpa\">编辑</botton></li>";
			$(".list_ul").find(".atd").before(stri);
			var sdpi="<div class=\"city2\"><ul><li class='sd_id shi' parentid="+jsondata.id+" ><button class=\"disy6 shis btn disy_green daslpa\" display>+添加</button></li></ul></div>";
			$("#"+jsondata.id).append(sdpi);				
			$(".shi").find(".shis").click(function(e){
				$(".back_grue").show();
				openDialog("dlg4", '添加市');
				$("#dlg4").find("#addparent_id").val($(this).parent().attr("parentid"));
	    		$("#dlg4").find("#addname").val("");
				run=false;
	    	});
			$("#"+jsondata.id).find(".disy5").click(function(){
	    		$(".back_grue").show();
	    		openDialog("dlg2", '编辑省直辖市');
	    		$("#dlg2").find("#twoid").val($(this).parent().attr("id"));
	    		$("#dlg2").find("#twoname").val($(this).parent().find(".provinces").text());
	    		if($(this).parent().attr("isVisible")!='false'){
	    			$("#dlg2").find("#select-Visible").get(0).selectedIndex=0;
	    		}else{
	    			$("#dlg2").find("#select-Visible").get(0).selectedIndex=1;
	    		}
	    		run=false;
	    	});
			$(".back_grue").hide();
			$('#dlg1').dialog('close');
			submit_cate_callback();
		}
	});
}

function levelone_data(){
	var name,id,isVisible;
	$("#cate_one").form({
		url : '/ecs/location/save.do',
		onSubmit : function() {
			if($("#select-Visible2").val()=="0"){
				$(this)[0].isVisible.value=true;
				isVisible=true;
			}else{
				$(this)[0].isVisible.value=false;
				isVisible=false;
			}
			name=$(this)[0].twoname.value;
			id=$(this)[0].twoid.value;
			return $(this).form('validate');
		},
		success : function(data) {
			var jsondata = $.parseJSON(data);
			$(".back_grue").hide();
			$('#dlg2').dialog('close');
			$("#"+jsondata.id).find(".provinces").text(name);
			$("#"+jsondata.id).attr("isVisible",isVisible);
			submit_cate_callback();
		}
	});
}

function leveltwo_data(){
	var name,id,isVisible,isHot,pid;
	$("#cate_two").form({
		url : '/ecs/location/save.do',
		onSubmit : function() {
			if($("#select-Visible3").val()=="0"){
				$(this)[0].isVisible.value=true;
				isVisible=true;
			}else{
				$(this)[0].isVisible.value=false;
				isVisible=false;
			}
			if($("#select-hot").val()=="0"){
				$(this)[0].isHot.value=true;
				isHot=true;
			}else{
				$(this)[0].isHot.value=false;
				isHot=false;
			}
			name=$(this)[0].name.value;
			id=$(this)[0].id.value;
			pid=$(this)[0].parent_id.value;
			return $(this).form('validate');
		},
		success : function(data) {
			//update_hot(isHot,name);
			var jsondata = $.parseJSON(data);
			$(".back_grue").hide();
			$('#dlg3').dialog('close');
			$("#"+jsondata.id).find("a").text(name);
			$("#"+jsondata.id).attr({"isVisible":isVisible,"isHot":isHot,"title":name});
			
			submit_cate_callback();
		}
	});
}

function addtwo_data(){
	var name,id,isVisible,isHot,pid;
	$("#cate_two_add").form({
		url : '/ecs/location/save.do',
		onSubmit : function() {
			if($("#select-Visible3").val()=="0"){
				$(this)[0].isVisible.value=true;
				isVisible=true;
			}else{
				$(this)[0].isVisible.value=false;
				isVisible=false;
			}
			if($("#select-hot").val()=="0"){
				$(this)[0].isHot.value=true;
				isHot=true;
			}else{
				$(this)[0].isHot.value=false;
				isHot=false;
			}
			name=$(this)[0].name.value;
			id=$(this)[0].id.value;
			pid=$(this)[0].parent_id.value;
			return $(this).form('validate');
		},
		success : function(data) {
			update_hot(isHot,name);
			var jsondata = $.parseJSON(data);
			var stri="<li id='"+jsondata.id+"' isVisible='"+isVisible+"' isHot='"+isHot+"'  title='"+name+"' parentid='"+pid+"'><a>"+name+"</a><button class=\"disy5 btn disy_red daslpa\" >编辑</button></li>";
			$("#"+pid).find(".city2").find("ul").find(".sd_id").before(stri);
			$("#"+jsondata.id).find("a").click(function(){
	    		var x = $(this).offset().top;
	    		var y = $(this).offset().left;
	    		x=x-20;y=167;
	    		$(".city2").find("li").find("a").css("color","#000");
	    		$(this).find("a").css("color","red");
	    		$(".county2").css({"left":y+"px","top":x+"px","display":"block"});
	    		loading_county(jsondata.id);
	    		run=false;
			});
			$("#"+jsondata.id).find(".disy5").click(function(){
				$(".back_grue").show();
	    		openDialog("dlg3", '编辑市');
	    		$("#dlg3").find("#lid").val($(this).parent().attr("id"));
	    		$("#dlg3").find("#lparent_id").val($(this).parent().attr("parentid"));
	    		$("#dlg3").find("#lname").val($(this).parent().find("a").text());
	    		if($(this).parent().attr("isVisible")!='false'){
	    			$("#dlg3").find("#select-Visible3").get(0).selectedIndex=0;
	    		}else{
	    			$("#dlg3").find("#select-Visible3").get(0).selectedIndex=1;
	    		}
	    		if($(this).parent().attr("isHot")!='false'){
	    			$("#dlg3").find("#select-hot").get(0).selectedIndex=0;
	    		}else{
	    			$("#dlg3").find("#select-hot").get(0).selectedIndex=1;
	    		}
    		    run=false;
			});
			$(".back_grue").hide();
			$('#dlg4').dialog('close');
			submit_location_callback();
		}
	});
}

function submit_location_callback(){
	
	$.messager.confirm('添加成功','添加区域成功');

}

function submit_cate_callback(){
	
	$.messager.confirm('修改成功','修改区域成功');

}


function  adm(id){
	
	if(window.ActiveXObject)
    {
        var browser=navigator.appName;
        var b_version=navigator.appVersion; 
        var version=b_version.split(";"); 
        var trim_Version=version[1].replace(/[ ]/g,""); 
        if(browser=="Microsoft Internet Explorer" && trim_Version=="MSIE7.0") 
        { 
        	$("#"+id).submit();
        }else{
        	$("#"+id).submit();
        }
    }else{
    	$("#"+id).submit();
    }
}

function add_regional_callback(){
	
	$.messager.confirm('添加失败','请核输入区域名称');

}
function update_regional_callback(){
	
	$.messager.confirm('修改失败','请核输入区域名称');

}
function loading_county(tid){
	$(".county2").css("display","block");
	$(".county2").find("ul").find("li").remove();
	$.ajax({
		type:'post',
		url:'/ecs/location/listbyparent.do',
		dataType: 'json',
		data:'parent='+tid,
		success:function(data){
			if(data.rows.length==0){
				var str=" <li parentid='"+tid+"' class=\"list_li_class atd\"><button class=\"disy6 qu btn disy_green daslpa\">+添加</button> &nbsp;&nbsp;<button onclick=\"$('.county2').hide();\" class=\"disy5 btn disy_blue\" style=\"display:inline-block;float:left; margin-left:10px;\">关闭</button></li>";
		    	$(".county2").find("ul").append(str);
		    	$(".atd").find(".qu").click(function(){
		    		$(".back_grue").show();
	    			openDialog("dlgadd", '添加区');
	    			$("#dlgadd").find("#add_parent_id").val($(this).parent().attr("parentid"));
	    			$("#dlgadd").find("#add_name").val("");
	    			$("#dlgadd").find("#_defaultAddress").val("");
		    		run=false;
		    	});
			}else{
				for(var z=0;z<data.rows.length;z++){
					var sturl=" <li id='"+data.rows[z].id+"' isVisible='"+data.rows[z].isVisible+"' defaultAddress='"+data.rows[z].defaultAddress+"' parentid='"+data.rows[z].parent_id+"' title='"+data.rows[z].name+"'><a>"+data.rows[z].name+"</a><button class=\"disy3 btn disy_red daslpa\" >编辑</button></li>";
	        		$(".county2").find("ul").append(sturl);
	        		var a_county=$("#"+data.rows[z].id).find(".disy3");
		    		a_county.click(function(){
		    			$(".back_grue").show();
		    			openDialog("dlg", '编辑区域');
			    		$("#dlg").find("#_id").val($(this).parent().attr("id"));
			    		$("#dlg").find("#_parent_id").val($(this).parent().attr("parentid"));
			    		$("#dlg").find("#_name").val($(this).parent().find("a").text());
			    		if($(this).parent().attr("defaultAddress")!="undefined"){
			    			$("#dlg").find("#_defaultAddress").val($(this).parent().attr("defaultAddress"));
			    		}else{
			    			$("#dlg").find("#_defaultAddress").val("");
			    		}
			    		run=false;
			    	});
	        	}
	        	var str=" <li parentid='"+tid+"' class=\"list_li_class atd\"><button class=\"disy6 qu btn disy_green daslpa\">+添加</button> &nbsp<button onclick=\"closed_win()\" class=\"disy5 btn disy_blue\" style=\"display:inline-block;float:left; margin-left:10px;*+margin-top:-16px;\">关闭</button></li>";
		    	$(".county2").find("ul").append(str);
		    	$(".atd").find(".qu").click(function(){
		    		$(".back_grue").show();
	    			openDialog("dlgadd", '添加区');
	    			$("#dlgadd").find("#add_parent_id").val($(this).parent().attr("parentid"));
	    			$("#dlgadd").find("#add_name").val("");
	    			$("#dlgadd").find("#_defaultAddress").val("");
		    		run=false;
		    	});
			}
		}
	});
}
function closed_win(){
	$(".county2").hide();
	$(".city2").find("li").find("a").css("color","#000");
}
document.body.onclick = function(){
    if(run){
    	$(".scounty3").hide();
    	$(".update_name").hide();
    	$(".update_infos").hide();
    	//$(".city2").find("li").find("a").css("color","#000");
    }
    run = true;
}


function append(){
	for(var i=0;i<26;i++)
	{
		$(".list_info").append("<ul id='"+String.fromCharCode((65+i))+"' class=\"list_ul\"></ul>");
	}
}
function letters(text){
	var value=CC2PY(text);
	value=value.split("");
	return value[0];
}
function display_ul(){
	for(var i=0;i<25;i++)
	{
		var idnum=String.fromCharCode((65+i));
		if($("#"+idnum).find(".list_li_class").length==0){
			$("#"+idnum).remove();
		}
	}
	if($("#A").find(".list_li_class").length==0){
		$("#A").remove();
	}
	$(".list_info").append("<ul id='add' class=\"list_ul\"></ul>");
	 var str=" <li class=\"list_li_class atd\"><button class=\"disy6 shen btn disy_green daslpa\">+添加</button></li>";
	$("#add").append(str);
	$(".atd").find(".shen").click(function(){
		$(".back_grue").show();
 		openDialog("dlg1", '添加省直辖市');
		$("#dlg1").find("#name").val("");
		run=false;
	});
}

function loading_hotcity(){
	for(var i=0;i<c_data.length;i++){
		for(var j=0;j<c_data[i].childen.length;j++){
			if(c_data[i].childen[j].isHot==true && c_data[i].childen[j].isVisible==true){
				$(".city_list").find("ul").append("<li class=\"city_color\">"+c_data[i].childen[j].name+"</li>");
				$(".city_list").find(".city_color").click(function(){
					$(".center_list").find("ul").find("li").removeClass("mouseclass");
					$(".city_list").find("ul").find("li").removeClass("mouseclass");
				    $(this).addClass("mouseclass");
				    var text=$(this).text();
				    var text_city=$(".city2 ").find("li").find("a");
				    for(var i=0;i<text_city.length;i++){
				    	if($(text_city[i]).text()==text){
				    		var litop=$(text_city[i]).offset().top;
				    		document.body.scrollTop=litop;
					    	document.documentElement.scrollTop=litop;
				    	}
				    } 
				});
			}
		}
	}
}
function update_hot(isHot,name){
	if(isHot==true){
		add_hot_city(name);
	}else{
		delect_hot_city(name);
	}
}


function delect_hot_city(name){
	var city=$(".city_color");
	$.each(city,function(){
		if($(this).text()==name){
			$(this).remove();
		}
	});
}
function add_hot_city(name){
	$(".city_list").find("ul").append("<li class=\"city_color\">"+name+"</li>");
	hot_city();
}

function hot_city(){
	$(".city_list").find(".city_color").click(function(){
		$(".center_list").find("ul").find("li").removeClass("mouseclass");
		$(".city_list").find("ul").find("li").removeClass("mouseclass");
	    $(this).addClass("mouseclass");
	    var text=$(this).text();
	    var text_city=$(".city2 ").find("li").find("a");
	    for(var i=0;i<text_city.length;i++){
	    	if($(text_city[i]).text()==text){
	    		var litop=$(text_city[i]).offset().top;
	    		document.body.scrollTop=litop;
		    	document.documentElement.scrollTop=litop;
	    	}
	    } 
	});
}