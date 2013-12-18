var c_data;
var childen_data;
var childen_id;
var childen_name;
var tle;
var did;
var run = true;
var value_key=undefined;
var zm=['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
Array.prototype.in_array = function(e)  
{  
	for(i=0;i<this.length && this[i]!=e;i++);  
	return !(i==this.length);  
}
$(document).ready(function(){
	append();
	$.ajax({
		type:'post',
		url:'/ecs/category/list.do',
		dataType: 'json',
		success:function(data){
			c_data=data.rows;
			for(var i=0;i<c_data.length;i++){
				childen_data=c_data[i].childen;
				var str=" <li id='"+c_data[i].id+"'isVisible="+c_data[i].isVisible+" sortIndex='"+c_data[i].sortIndex+"' color='"+c_data[i].color+"' iconImage='"+c_data[i].iconImage+"' showImage='"+c_data[i].showImage+"' class=\"list_li_class\"><div sid='"+c_data[i].id+"' class=\"provinces\">"+c_data[i].name+"</div><button sid='"+c_data[i].id+"' fname='"+c_data[i].name+"' class=\"disy6 btn disy_red daslpa\">编辑</button><div class=\"city2\"><ul></ul><div></li>";
				if(zm.in_array(letters(c_data[i].name))==true){
					$("#"+letters(c_data[i].name)).append(str);
				}else{
					$("#ppad").append(str);
				}
		    	var ar=$("#"+c_data[i].id).find(".city2");
		    	$(".list_li_class").find(".disy6").click(function(){
		    		$(".back_grue").show();
		    		$("#dlg1").find("#iconImage").val("");
		    		$("#dlg1").find("#showImage").val("");
		    		$("#dlg1").find("#onecolor").val("");
		    		openDialog("dlg1", '编辑一级类目');
		    		$("#dlg1").find("#onesortIndex").val($(this).parent().attr("sortIndex"));
		    		$("#dlg1").find("#oneid").val($(this).parent().attr("id"));
		    		$("#dlg1").find("#onename").val($(this).parent().find(".provinces").text());
		    		$("#dlg1").css("top",$(this).offset().top+"px");
		    		if($(this).parent().attr("isVisible")!='false'){
		    			$("#dlg1").find("#select-Visible").get(0).selectedIndex=0;
		    		}else{
		    			$("#dlg1").find("#select-Visible").get(0).selectedIndex=1;
		    		}
		    		if($(this).parent().attr("color")!=='undefined'){
		    			var color=$(this).parent().attr("color");
		    			$("#dlg1").find("#onecolor").val(color);
		    		}
		    	});
		    	for(var j=0;j<childen_data.length;j++){
		    		if(c_data[i].childen[j].name!==""){	
		    	    tle=c_data[i].childen[j].childen;	
				    var strurl=" <li id="+c_data[i].childen[j].id+" sortIndex='"+c_data[i].childen[j].sortIndex+"' isVisible="+c_data[i].childen[j].isVisible+" parentid="+c_data[i].childen[j].parent_id+" title="+c_data[i].childen[j].name+"><a class=\" \">"+c_data[i].childen[j].name+"</a><button class=\"disy5 btn disy_red daslpa\">编辑</button></li>";
		    		ar.find("ul").append(strurl);
		    		$("#"+c_data[i].childen[j].id).find(".disy5").click(function(){
		    			$(".back_grue").show();
			    		openDialog("dlg2", '编辑二级类目');
			    		$("#dlg2").find("#twoid").val($(this).parent().attr("id"));
			    		$("#dlg2").find("#twoparent_id").val($(this).parent().attr("parentid"));
			    		$("#dlg2").find("#twoname").val($(this).parent().find("a").text());
			    		$("#dlg2").find("#twosortIndex").val($(this).parent().attr("sortIndex"));
			    		if($(this).parent().attr("isVisible")!='false'){
			    			$("#dlg2").find("#select-Visible2").get(0).selectedIndex=0;
			    		}else{
			    			$("#dlg2").find("#select-Visible2").get(0).selectedIndex=1;
			    		}
			    		run=false;
		    		});
		    		$("#"+c_data[i].childen[j].id).find("a").click(function(){
		    			var sid=$(this).parent("li").attr("id");
		    			var suname=$(this).parent("li").attr("title");
		    			var spid=$(this).parent("li").attr("parentid");
		    			var x = $(this).offset().top;
		    			var y = $(this).offset().left;
		    			x=x-20;y=167;
		    			$(".city2").find("li").find("a").css("color","#000");
		    			$(this).css("color","red");
		    		    $(".county2").css({"left":y+"px","top":x+"px","display":"block"});
		    		    loading_county(sid);
		    		    run=false;
		    		});
		    		}
		    	}
		    	var strurl=" <li class='sd_id' parentid="+c_data[i].id+" ><button class=\"disy5 btn disy_green daslpa\" display>+添加</button></li>";
		        ar.find("ul").append(strurl);
		        $(".sd_id").find(".disy5").click(function(){
		        	$(".back_grue").show();
		    		openDialog("dlg2", '添加二级类目');
		    		$("#dlg2").find("#twoparent_id").val($(this).parent().attr("parentid"));
		    		$("#dlg2").find("#twoid").val("");
		    		$("#dlg2").find("#twoname").val("");
		    		$("#dlg2").find("#twosortIndex").val("");
		    		run=false;
		    	});
			}
			display_ul();
		}
	});
	
	$('#dlg1').click(function(){
		run = false;
	});
	$('#dlg2').click(function(){
		run = false;
	});
	$('#dlg3').click(function(){
		run = false;
	});
	$('.county2').click(function(){
		run = false;
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
	$("#levelonesubmit").click(function(){
		levelone_data();
		adm("levelone");
		run=false;
	});

	$("#leveltwosubmit").click(function(){
		leveltwo_data();
		adm("leveltwo");
		run=false;
	});
	
	$("#levelthreesubmit").click(function(){
		levelthree_data();
		adm("levelthree");
		run=false;
	});
	
});

function loading_county(tid){
	if(did==tid){
	}else if(did!=tid){
		did=tid;
		$(".county2").find("ul").find("li").remove();
		for(var i=0;i<c_data.length;i++){
			childen_data=c_data[i].childen;
	    	for(var j=0;j<childen_data.length;j++){
	            if(c_data[i].childen[j].id==tid){
	            	var data =c_data[i].childen[j].childen;
	            	for(var z=0;z<c_data[i].childen[j].childen.length;z++){
					    var sturl=" <li id="+data[z].id+" sortIndex='"+data[z].sortIndex+"' isVisible="+data[z].isVisible+" splitOrderNumber="+data[z].splitOrderNumber+" keyword='"+data[z].keyword+"' parentid="+data[z].parent_id+" title="+data[z].name+"><a>"+data[z].name+"</a><button class=\"disy5 btn disy_red daslpa\">编辑</button></li>";
	            		$(".county2").find("ul").append(sturl);
	            		$("#"+data[z].id).find(".disy5").click(function(){
	            			$(".back_grue").show();
	            			$("#dlg3").find("#threekeyword").val("");
				    		openDialog("dlg3", '编辑三级类目');
				    		$("#dlg3").find("#threeid").val($(this).parent().attr("id"));
				    		$("#dlg3").find("#threeparent_id").val($(this).parent().attr("parentid"));
				    		$("#dlg3").find("#threename").val($(this).parent().find("a").text());
				    		$("#dlg3").find("#threesplitOrderNumber").val($(this).parent().attr("splitOrderNumber"));
				    		$("#dlg3").find("#sortIndex").val($(this).parent().attr("sortIndex"));
				    		if($(this).parent().attr("isVisible")!='false'){
				    			$("#dlg3").find("#select-Visible3").get(0).selectedIndex=0;
				    		}else{
				    			$("#dlg3").find("#select-Visible3").get(0).selectedIndex=1;
				    		}
				    		var alt =$(this).parent().attr("keyword");
				    		var p='undefined';
				    		if(alt==p){
				    			$("#dlg3").find("#threekeyword").val("");
				    		}else{
				    			$("#dlg3").find("#threekeyword").val($(this).parent().attr("keyword"));
				    		}
				    		run=false;
	            		});
	            	}
	            	var str=" <li parentid="+c_data[i].childen[j].id+" class=\"list_li_class atdss\"><button class=\"disy6 btn disy_green daslpa\">+添加</button>&nbsp<button onclick=\"closed_win()\" class=\"disy5 btn disy_blue\" style=\"display:inline-block;float:left; margin-left:10px;*+margin-top:-16px;\">关闭</button></li>";
			    	$(".county2").find("ul").append(str);
			    	$(".atdss").find(".disy6").click(function(){
			    		$(".back_grue").show();
			    		openDialog("dlg3", '添加三级级类目');
			    		$("#levelthree").find("input").val("");
			    		$("#dlg3").find("#sortIndex").val("");
			    		$("#threeparent_id").val($(this).parent().attr("parentid"));
			    		run=false;
			    	});
	            }
	    	}
		}
	}else{}
}

function levelone_data(){
	var name,id,isVisible,iconImage,showImage,color,sortIndex;
	$("#levelone").form({
		url : '/ecs/category/save.do',
		onSubmit : function() {
			if($("#select-Visible").val()=="0"){
				$(this)[0].isVisible.value=true;
				isVisible=true;
			}else{
				$(this)[0].isVisible.value=false;
				isVisible=false;
			}
			name=$(this)[0].name.value;
			id=$(this)[0].id.value;
			showImage=$(this)[0].showImage.value;
			color=$(this)[0].color.value;
			sortIndex=$(this)[0].sortIndex.value;
			return $(this).form('validate');
		},
		success : function(data) {
			var jsondata = $.parseJSON(data); 
			if(id==""){
				var stri="<li id='"+jsondata.id+"' sortIndex='"+sortIndex+"' isVisible='"+isVisible+"' iconImage='"+iconImage+"' showImage='"+showImage+"'  class=\"list_li_class\"><div class=\"provinces\" sid='"+jsondata.id+"'>"+name+"</div><button class=\"disy5 btn disy_red daslpa\">编辑</botton></li>";
				$(".list_ul").find(".atd").before(stri);
				var sdpi="<div class=\"city2\"><ul><li class='sd_id' parentid="+jsondata.id+" ><button class=\"disy6 btn disy_green daslpa\" display>+添加</button></li></ul></div>";
				$("#"+jsondata.id).append(sdpi);				
				$(".sd_id").find(".disy6").click(function(e){
					$(".back_grue").show();
					openDialog("dlg2", '添加二级级类目');
					$("#dlg2").find("#twoparent_id").val($(this).parent().attr("parentid"));
		    		$("#dlg2").find("#twoid").val("");
		    		$("#dlg2").find("#twoname").val("");
		    		$("#dlg2").find("#twosortIndex").val("");
					run=false;
		    	});
				$("#"+jsondata.id).find(".disy5").click(function(){
		    		$(".back_grue").show();
		    		openDialog("dlg1", '编辑一级类目');
		    		$("#dlg1").find("#oneid").val($(this).parent().attr("id"));
		    		$("#dlg1").find("#onename").val($(this).parent().find(".provinces").text());
		    		if($(this).parent().attr("isVisible")!='false'){
		    			$("#dlg1").find("#select-Visible").get(0).selectedIndex=0;
		    		}else{
		    			$("#dlg1").find("#select-Visible").get(0).selectedIndex=1;
		    		}
		    		$("#dlg1").find("#iconImage").val("");
		    		$("#dlg1").find("#showImage").val("");
		    		$("#dlg1").find("#onesortIndex").val($(this).parent().attr("sortIndex"));
		    		run=false;
		    	});
				loading_cate();
				$(".back_grue").hide();
				$('#dlg1').dialog('close');
				submit_addname_callback();
			}else{
				$(".back_grue").hide();
				$('#dlg1').dialog('close');
				$("#"+id).find(".provinces").text(name);
				$("#"+id).attr("isVisible",isVisible);
				$("#"+id).attr("color",color);
				$("#"+id).attr("sortIndex",sortIndex);
				submit_onename_callback();
			}

		}
	});
}

function leveltwo_data(){
	var tname,tid,isVisible,tpid,sortIndex;
	$("#leveltwo").form({
		url : '/ecs/category/save.do',
		onSubmit : function() {
			if($("#select-Visible2").val()=="0"){
				$(this)[0].isVisible.value=true;
				isVisible=true;
			}else{
				$(this)[0].isVisible.value=false;
				isVisible=false;
			}
			tname=$(this)[0].name.value;
			tid=$(this)[0].id.value;
			tpid=$(this)[0].parent_id.value;
			sortIndex=$(this)[0].sortIndex.value;
			$("#"+tid).attr("isVisible",isVisible);
			return $(this).form('validate');
		},
		success : function(data) {
			var jsondata = $.parseJSON(data);
			if(tid==""){
				var stri="<li id='"+jsondata.id+"' sortIndex='"+sortIndex+"' isVisible='"+isVisible+"'  title='"+tname+"' parentid='"+tpid+"'><a>"+tname+"</a><button class=\"disy5 btn disy_red daslpa\">编辑</button></li>";
				$("#"+tpid).find(".city2").find("ul").find(".sd_id").before(stri);
				$("#"+jsondata.id).find(".disy5").click(function(){
					$(".back_grue").show();
		    		openDialog("dlg2", '编辑二级类目');
		    		$("#dlg2").find("#twoid").val($(this).parent().attr("id"));
		    		$("#dlg2").find("#twoparent_id").val($(this).parent().attr("parentid"));
		    		$("#dlg2").find("#twoname").val($(this).parent().find("a").text());
		    		$("#dlg2").find("#twosortIndex").val($(this).parent().attr("sortIndex"));
		    		if($(this).parent().attr("isVisible")!='false'){
		    			$("#dlg2").find("#select-Visible2").get(0).selectedIndex=0;
		    		}else{
		    			$("#dlg2").find("#select-Visible2").get(0).selectedIndex=1;
		    		}
		    		run=false;
	    		});
				$("#"+jsondata.id).find("a").click(function(){
					var sid=$(this).parent("li").attr("id");
	    			var suname=$(this).parent("li").attr("title");
	    			var spid=$(this).parent("li").attr("parentid");
	    			var x = $(this).offset().top;
	    			var y = $(this).offset().left;
	    			x=x-20;y=167;
	    			$(".city2").find("li").find("a").css("color","#000");
	    			$(this).css("color","red");
	    		    $(".county2").css({"left":y+"px","top":x+"px","display":"block"});
	    		    loading_county(sid);
	    		    run=false;
				});
				$(".back_grue").hide();
				$('#dlg2').dialog('close');
				loading_cate();
				submit_addname_callback();
			}else{
				$(".back_grue").hide();
				$('#dlg2').dialog('close');
				$("#"+tid).find("a").text(tname);
				$("#"+tid).attr("sortIndex",sortIndex);
				sortIndex
				submit_onename_callback();
			}
			
		}
	});
}

function levelthree_data(){
	var hname,hid,isVisible,keyword,splitOrderNumber,sortIndex;
	$("#levelthree").form({
		url : '/ecs/category/save.do',
		onSubmit : function() {
			if($("#select-Visible3").val()=="0"){
				$(this)[0].isVisible.value=true;
				isVisible=true;
			}else{
				$(this)[0].isVisible.value=false;
				isVisible=false;
			}
			hname=$(this)[0].name.value;
			hid=$(this)[0].id.value;
			keyword=$(this)[0].keyword.value;
			splitOrderNumber=$(this)[0].splitOrderNumber.value;
			sortIndex=$(this)[0].sortIndex.value;
			return $(this).form('validate');
		},
		success : function(data) {
			var jsondata = $.parseJSON(data);
			if(hid==""){
				var stri="<li id='"+jsondata.id+"' keyword='"+keyword+"' splitOrderNumber='"+splitOrderNumber+"'  title='"+hname+"' parentid='"+hid+"'><a>"+hname+"</a><button class=\"disy5 btn disy_red daslpa\">编辑</button></li>";
	       	    $(".county2").find("ul").find(".atdss").before(stri);
	       	    $("#"+jsondata.id).find(".disy5").click(function(){
	       	    	$(".back_grue").show();
	       	    	$("#dlg3").find("#threekeyword").val("");
	       	    	openDialog("dlg3", '编辑三级类目');
	       	    	$("#dlg3").find("#threeid").val($(this).parent().attr("id"));
	       	    	$("#dlg3").find("#threeparent_id").val($(this).parent().attr("parentid"));
	       	    	$("#dlg3").find("#threename").val($(this).parent().find("a").text());
	       	    	$("#dlg3").find("#threesplitOrderNumber").val($(this).parent().attr("splitOrderNumber"));
	       	    	$("#dlg3").find("#sortIndex").val($(this).parent().attr("sortIndex"));
	       	    	
	       	    	if($(this).parent().attr("isVisible")!='false'){
	       	    		$("#dlg3").find("#select-Visible3").get(0).selectedIndex=0;
	       	    	}else{
	       	    		$("#dlg3").find("#select-Visible3").get(0).selectedIndex=1;
	       	    	}
	       	    	var alt =$(this).parent().attr("keyword");
	       	    	var p='undefined';
	       	    	if(alt==p){
	       	    		$("#dlg3").find("#threekeyword").val("");
	       	    	}else{
	       	    		$("#dlg3").find("#threekeyword").val($(this).parent().attr("keyword"));
	       	    	}
	       	    	run=false;
	       	    	});
	       	    	$(".back_grue").hide();
	       	    	$('#dlg3').dialog('close');
	       	    	submit_onename_callback();
				}else{
					$(".back_grue").hide();
					$('#dlg3').dialog('close');
					$("#"+hid).find("a").text(hname);
					$("#"+hid).attr("keyword",keyword);
					$("#"+hid).attr("splitOrderNumber",splitOrderNumber);
					$("#"+hid).attr("isVisible",isVisible);
					$("#"+hid).attr("sortIndex",sortIndex);
					submit_onename_callback();
				}
		}
	});
}

function submit_onename_callback(){
	
	$.messager.confirm('修改成功','修改成功');
	run=false;

}
function submit_addname_callback(){
	
	$.messager.confirm('添加成功','添加成功');
	run=false;
}
function closed_win(){
	$(".county2").hide();
	$(".city2").find("li").find("a").css("color","#000");
}
document.body.onclick = function(){
    if(run){
    	//$(".city2").find("li").find("a").css("color","#000");
    	$(".county2").find("li").find("a").css("color","#000");
    }
    run = true;
}

function append(){
	for(var i=0;i<25;i++)
	{
		$(".list_info").append("<ul id='"+String.fromCharCode((65+i))+"' class=\"list_ul\"></ul>");
	}
	$(".list_info").append("<ul id='ppad' class=\"list_ul\"></ul>");
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
	$(".list_info").append("<ul id='add' class=\"list_ul\"></ul><div class=\"line_padding\"></div>");
	 var str=" <li class=\"list_li_class atd\"><button class=\"disy6 btn disy_green daslpa\">+添加</button></li>";
 	$("#add").append(str);
 	$(".atd").find(".disy_green").click(function(e){
 		$(".back_grue").show();
 		openDialog("dlg1", '添加一级级类目');
		$("#dlg1").find("#oneid").val("");
		$("#dlg1").find("#onename").val("");
		$("#dlg1").find("#iconImage").val("");
		$("#dlg1").find("#showImage").val("");
		$("#dlg1").find("#onesortIndex").val("");
 		run=false;
 	});
}
function loading_cate(){
	$.ajax({
		type:'post',
		url:'/ecs/category/list.do',
		dataType: 'json',
		success:function(data){
			c_data=data.rows;
		}
	});
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

