var c_data;
var pric_data;
var childen_data;
var childen_id;
var childen_name;
var did;
var run = true;
var data_subclass=[];
var data_leaves=[];
var data_leaves_leave=[];
var loading=1;
var list_price;
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
					if(c_data[i].isVisible!=false){
						var str=" <li id='"+c_data[i].id+"' class=\"list_li_class\"><div class=\"provinces\">"+c_data[i].name+"</div><div class=\"city\"><ul></ul><div></li>";
						$("#"+letters(c_data[i].name)).append(str);
					}else{}
				}
		    	var ar=$("#"+c_data[i].id).find(".city");
		    	for(var j=0;j<childen_data.length;j++){
		    		if(c_data[i].childen[j].name!==""){
		    		if(c_data[i].childen[j].isVisible){
						var strurl=" <li onclick=\"li_click(this)\" mid="+childen_data[j].id+" parentid="+childen_data[j].parent_id+" title="+c_data[i].childen[j].name+"><a>"+c_data[i].childen[j].name+"</a><label class=\"left_a\"></label></li>";
						ar.find("ul").append(strurl);
						$("#"+c_data[i].childen[j].id).click(function(){
		    				
		    		});
		    		}else{}
		    		}
		    	}
			}	
			display_ul();
			loading_hotcity();
			ajaxLoadEnd();
		}
	});
	
	list_option();
	$(".county").click(function(){
		run=false;
	});
	$(".county_list").click(function(){
		run=false;
	});
	$(".simbtn").click(function(){
		run=false;
	});
	
	$(".disy").click(function(){
		if($(".simbtn").attr("category_id")=="0"){
			alert("请选择分类！");
			prices();
		}else{
			var category_id = $(".simbtn").attr("category_id");
			var location_id = $(".simbtn").attr("location_id");
			var price =$("#"+location_id).find("input").val();
			$.ajax({
				type:'post',
				url:'/ecs/locationPrice/save.do',
				dataType: 'json',
				data:'price='+price+'&location_id='+location_id+'&category_id='+category_id,
				success:function(data){
					$(".county").hide();
					$(".simbtn").hide();
					prices();
				}
			});
		}
	});
	$(".disy3").click(function(){
		if($(".simbtn").attr("category_id")=="0"){
			alert("请选择分类！");
			prices();
		}else{
			var category_id = $(".simbtn").attr("category_id");
			var location_id = $(".simbtn").attr("location_id");
			var price ="0";
			
			if(price==""){
				alert("该地区还没有设定价格！");
			}else{
			$.ajax({
				type:'post',
				url:'/ecs/locationPrice/save.do',
				dataType: 'json',
				data:'price='+price+'&location_id='+location_id+'&category_id='+category_id,
				success:function(data){
					$(".county").hide();
					$(".simbtn").hide();
					prices();
				}
			});
			
			}
		}
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
});
var sunb=1;
/*function loading_county(tid){
	$(".county").find("ul").find("li").remove();
	for(var i=0;i<c_data.length;i++){
		for(var j=0;j<c_data[i].childen.length;j++){
			if(c_data[i].childen[j].id==tid){
				var n=c_data[i].childen[j].childen;
				for(var m=0;m<n.length;m++){
					if(n[m].isVisible==true){
            			var sturl=" <li id="+n[m].id+" parentid="+n[m].parent_id+" title="+n[m].name+"><a>"+n[m].name+"</a><input id='' value='' sid="+n[m].id+" type=\"text\" /></li>";
            			$(".county").find("ul").append(sturl);
					}
					$("#"+n[m].id).find("input").click(function(){
            			var xl=$(this).offset().left;
						var yl=$(this).offset().top;
						var uid=$(this).attr("sid");
						var price_id=$(this).attr("id");
						xl=xl-220; yl=yl-37;
						$(".simbtn").css({"left":xl+"px","top":yl+"px","display":"block"});
						$(".simbtn").attr({"location_id":uid,"category_id":$('#leaves_leave').combobox('getValue'),"id":price_id});
						run=false;
            		});
				}
				break;
			}
		}
	}
	prices();
}*/
function li_click(own){
	var tid=$(own).attr("mid");
	var X = $(own).offset().top;
	var Y = $(own).offset().left;
	X=X-24;var Y=125;
	$(".city").find("li").find("a").css("color","#000");
	$(own).find("a").css("color","red");
	$(".county_list").css({"left":Y+"px","top":X+"px","display":"block"});
   loading_county(tid,X);
   run=false;
}
function loading_county(tid,top){
	top=top+200;
	load_height();
	data_checkbox_del=[];
	$(".county_list_info_list").find("a").remove();
	$(".county_list_info").find(".county_list_info_open").remove();
	$(".cate_one_list").find("a").remove();
	$(".cate_two_list").find("a").remove();
	$(".cate_three_list").find("a").remove();
	$(".cate_list_info_one").find(".cate_list_info_open").remove();
	$(".cate_list_info_two").find(".cate_list_info_open").remove();
	$(".cate_list_info_three").find(".cate_list_info_open").remove();
	$(".county_list").find(".baocun").remove();
	$.ajax({
		type:'post',
		url:'/ecs/location/listbyparent.do',
		dataType: 'json',
		data:'parent='+tid,
		success:function(data){
            var county_data=data.rows;
            var sturl=" <a class=\"all_list_infos all\" id="+tid+" title='全选'><input type='checkbox' onclick=\"all_click()\"/>全选</a>";
			$(".county_list_info_list").append(sturl);
            for(var z=0;z<county_data.length;z++){
        		if(county_data[z].isVisible){
        		var sturl=" <a class=\"county_list_infos\" id="+county_data[z].id+" parentid="+county_data[z].parent_id+" title="+county_data[z].name+"><input type='checkbox' onclick=\"location_click(this)\" />"+county_data[z].name+"</a>";
        			$(".county_list_info_list").append(sturl);

        		}
        	} 
            $(".county_list").append("<div class=\"baocun float_button_left\"><button class=\"disy_red margin-left10\" onclick=\"closed_window();\">关闭</button><button onclick='sumbit_data("+top+")' class=\"disy_blue\">保存</button></div>")
		}
	 });
	loading_one_data();
     
}
function location_click(obj){
	if(data_checkbox_del.length>0){
		for(var i=0;i<data_checkbox_del.length;i++){
			$("#"+data_checkbox_del[i].location_id).attr("checked","checked");
		}
	}
	$(".cate_two_list").find("a").remove();
	$(".cate_three_list").find("a").remove();
	$(".cate_list_info_one").find(".cate_list_info_open").remove();
	$(".cate_list_info_two").find(".cate_list_info_open").remove();
	$(".cate_list_info_three").find(".cate_list_info_open").remove();
	
	//loading_one_data();
}

function all_click(){
	if($(".all_list_infos").find("input").is(':checked')){
		//console.log($(".county_list_info_list").find(".county_list_infos").find("input"));
		var ll=$(".county_list_info_list").find(".county_list_infos").find("input")
    	for(var i=0;i<ll.length;i++){
    		//console.log($(ll[i]).attr("checked"));
    		if($(ll[i]).is(':checked')){
    			
    		}else{
    			$(ll[i]).click();
    		}
    	}
		
	}else{
	    $(".county_list_info_list").find(".county_list_infos").find("input").removeAttr("checked");
	}
	$(".cate_two_list").find("a").remove();
	$(".cate_three_list").find("a").remove();
	$(".cate_list_info_one").find(".cate_list_info_open").remove();
	$(".cate_list_info_two").find(".cate_list_info_open").remove();
	$(".cate_list_info_three").find(".cate_list_info_open").remove();
}
function loading_one_data(){
	if($(".cate_one_list").find(".county_list_infos").length>0){
		loading_one_css();
	}else{
		$.ajax({
			type:'post',
			url:'/ecs/category/listbyquery.do',
			dataType: 'json',
			data:'level=1&mergeTree=true',
			success:function(data){
				var cates_data=data.rows;
	            for(var z=0;z<cates_data.length;z++){
	        		if(cates_data[z].isVisible!=false){
	        			var sturl=" <a class=\"county_list_infos\" id="+cates_data[z].id+" parentid="+cates_data[z].parent_id+" title="+cates_data[z].name+">"+cates_data[z].name+"</a>";
	        			$(".cate_one_list").append(sturl);
	        			$("#"+cates_data[z].id).click(function(){
	        				$(".cate_one_list").find(".county_list_infos").removeClass("selected_county");
	        				loading_one_css();
            				$(this).addClass("selected_county");
	        				$(".cate_two_list").find("a").remove();
	        				$(".cate_three_list").find("a").remove();
	        				$(".cate_list_info_two").find(".cate_list_info_open").remove();
	        				$(".cate_list_info_three").find(".cate_list_info_open").remove();
	        				loading_two_data($(this).attr("id"));
	        			});
	        		}
	        	}
	            loading_one_css();
			}
	     });
	}
}
function loading_two_data(id){
	load_height();
	 $.ajax({
			type:'post',
			url:'/ecs/category/listbyquery.do',
			dataType: 'json',
			data:'parent_id='+id,
			success:function(data){
				var cated_data=data.rows;
	            for(var z=0;z<cated_data.length;z++){
	            //console.log(cated_data);
         		if(cated_data[z].isVisible!=false){
         			var sturl=" <a class=\"county_list_infos\" id="+cated_data[z].id+" parentid="+cated_data[z].parent_id+" title="+cated_data[z].name+">"+cated_data[z].name+"</a>";
         			$(".cate_two_list").append(sturl);
         			$("#"+cated_data[z].id).click(function(){
         				$(".cate_two_list").find(".county_list_infos").removeClass("selected_county");
         				loading_two_css(id);
         				$(this).addClass("selected_county");
        				$(".cate_three_list").find("a").remove();
        				$(".cate_list_info_three").find(".cate_list_info_open").remove();
         				loading_three_data($(this).attr("id"),id);
         			});
         		}
         	}
	        loading_two_css(id);
	            
			}
      });
}
function loading_three_data(id,pid){
	load_height();
	 $.ajax({
			type:'post',
			url:'/ecs/category/listbyquery.do',
			dataType: 'json',
			data:'parent_id='+id,
			success:function(data){
				var catef_data=data.rows;
	            for(var z=0;z<catef_data.length;z++){
	            	if(catef_data[z].isVisible!=false){
	            		var sturl=" <a class=\"county_list_infos\"  parentid="+catef_data[z].parent_id+" title="+catef_data[z].name+"><label class=\"pddf\">"+catef_data[z].name+" ￥</label><input id="+catef_data[z].id+" class=\"city_input_value afff\" type=\"text\" onchange=\"inputcheckbox('"+catef_data[z].id+"','"+catef_data[z].parent_id+"','"+pid+"')\"/></a>";
	            		$(".cate_three_list").append(sturl);
	            	}
	            }  
	            pricesl(id);
	            //loading_three_css(id);
			}
    }); 
}

function loading_one_css(){
	var city_li=$(".county_list_info_list").find("a");
	for(var i=0;i<city_li.length;i++){
		if($(city_li[i]).find("input").is(':checked')){
			cid=$(city_li[i]).attr("id");
		}
	}
	if(data_checkbox_del.length>0){
		for(var i=0;i<data_checkbox_del.length;i++){
			if(cid==data_checkbox_del[i].location_id){
				$("#"+data_checkbox_del[i].fristid).addClass("selected_countys");
			}
		}
	}else{
	}
}
function loading_two_css(id){
	var city_li=$(".county_list_info_list").find("a");
	for(var i=0;i<city_li.length;i++){
		if($(city_li[i]).find("input").is(':checked')){
			cid=$(city_li[i]).attr("id");
		}
	}
	if(data_checkbox_del.length>0){
		for(var i=0;i<data_checkbox_del.length;i++){
			if(cid==data_checkbox_del[i].location_id && id==data_checkbox_del[i].fristid){
				$("#"+data_checkbox_del[i].twoid).addClass("selected_countys");
			}
		}
	}else{
	}
}

function loading_three_css(id){
	var city_li=$(".county_list_info_list").find("a");
	for(var i=0;i<city_li.length;i++){
		if($(city_li[i]).find("input").is(':checked')){
			cid=$(city_li[i]).attr("id");
		}
	}
	if(data_checkbox_del.length>0){
		for(var i=0;i<data_checkbox_del.length;i++){
			if(cid==data_checkbox_del[i].location_id){
				$("#"+data_checkbox_del[i].category_id).css("color","#006699");
				$("#"+data_checkbox_del[i].category_id).val(data_checkbox_del[i].price);
//				console.log(data_checkbox_del[i].category_id);
//				console.log(data_checkbox_del[i].price);
			}
		}
	}
}

function inputcheckbox(bid,pid,sid){
	var cid=[];
	var city_li=$(".county_list_info_list").find("a");
	for(var i=0;i<city_li.length;i++){
		if($(city_li[i]).find("input").is(':checked') && $(city_li[i]).hasClass("county_list_infos")){
			var bie= $(city_li[i]).attr("id");
			cid.push({id:bie});
		}
	}
	if(cid.length==0){
	     alert("请选择区域！");
	}else{
		
		var exists = false;
		//如果已经存在则跟新price属性
		var mprice = $("#"+bid).val();
		for(var i=0;i<cid.length;i++){
			for(index in data_checkbox_del){
				if(data_checkbox_del[index].category_id == bid && data_checkbox_del[index].location_id == cid[i].id && data_checkbox_del[index].twoid==pid && data_checkbox_del[index].fristid==sid){
					data_checkbox_del[index].price=$("#"+bid).val();
				}
			}
		}
		//删除数组中相同的项
		for(var i=0;i<cid.length;i++){
			for(index in data_checkbox_del){
				if(data_checkbox_del[index].category_id == bid && data_checkbox_del[index].location_id == cid[i].id && data_checkbox_del[index].twoid==pid && data_checkbox_del[index].fristid==sid && data_checkbox_del[index].price==mprice){
					data_checkbox_del.splice(index,1);
				}
			}
		}	
		//不存在, 放到数组
		for(var i=0;i<cid.length;i++){
			if(mprice!=""){
				data_checkbox_del.push({"category_id":bid,"location_id":cid[i].id,"price":mprice,"twoid":pid,"fristid":sid});
			}else{
				null_price_callback();
			}	
		}
	}
}
function sumbit_data(top){
	$(".baocun").find('.disy_blue').attr('disabled',"true");
	
	var data_checkbox_delel=[];
	for(var i=0; i<data_checkbox_del.length;i++){
		data_checkbox_delel.push({"category_id":data_checkbox_del[i].category_id,"location_id":data_checkbox_del[i].location_id,"price":data_checkbox_del[i].price});
	}
	if(data_checkbox_del.length!=0){
		var postData = {
				"rows" : JSON.stringify(data_checkbox_delel)
		};

		postAjaxRequest("/ecs/locationPrice/save.do", postData, submit_price_callback,false);
		ajaxLoading2(top);
	}else{
		error_price_callback();
	}
}
function submit_price_callback(){
	//$.messager.confirm('保存成功','更改服务定价成功');
	alert("更改服务定价成功");
	$(".baocun").find('.disy_blue').removeAttr("disabled"); 
	ajaxLoadEnd();
	//closed_window();
}
function null_price_callback(){
	//$.messager.confirm('更改失败','服务定价不能为空');
	alert("服务定价不能为空");
	//closed_window();
}
function error_price_callback(){
	//$.messager.confirm('更改失败','没有更改服务定价');
	alert("没有更改服务定价");
	$(".baocun").find('.disy_blue').removeAttr("disabled"); 
	ajaxLoadEnd();
	//closed_window();
}
function closed_window(){
	$(".county_list").hide();
	$(".city").find("li").find("a").css("color","#000");
	load_height();
}

document.body.onclick = function(){
    if(run){
    	$(".simbtn").hide();
    	$(".county").hide();
    	//$(".county_list").hide();
    	//$(".city").find("li").find("a").css("color","#000");
    	load_height();
    }
    run = true;
}

function prices(){
	var city_li=$(".county_list_info_list").find("a");
	for(var i=0;i<city_li.length;i++){
		if($(city_li[i]).find("input").is(':checked')){
		  var cid=$(city_li[i]).attr("id");
		}
	}
		$.ajax({
			type:'post',
			url:'/ecs/locationPrice/list.do',
			dataType: 'json',
			data:'location_id='+cid,
			success:function(data){
				$(".county_list").find(".cate_three_list").find("input").val("");
				if(data.total==0){
				}else{
		            for(var i=0;i<data.rows.length;i++){
		            	//console.log(data.rows[i].price);
		               $("#"+data.rows[i].category_id).attr({"id":data.rows[i].category_id});
		               $("#"+data.rows[i].category_id).val(data.rows[i].price);
		            }
				}
				
			}
		});
}
function pricesl(id){
	var ss=[];
	var city_li=$(".county_list_info_list").find(".county_list_infos");
	for(var i=0;i<city_li.length;i++){
		if($(city_li[i]).find("input").is(':checked')){
			ss.push({id:$(city_li[i]).attr("id")});
		}
	}
	if(ss.length>1){
	}else{
		$.ajax({
			type:'post',
			url:'/ecs/locationPrice/list.do',
			dataType: 'json',
			data:'location_id='+ss[0].id,
			success:function(data){
				$(".county_list").find(".cate_three_list").find("input").val("");
				if(data.total==0){
				}else{
		            for(var i=0;i<data.rows.length;i++){
		            	//console.log(data.rows[i].price);
		               $("#"+data.rows[i].category_id).attr({"id":data.rows[i].category_id});
		               $("#"+data.rows[i].category_id).val(data.rows[i].price);
		            }
				}
				loading_three_css(id);
			}
		});
	}
}
function list_option(){
	$.ajax({
		type:'post',
		url:'/ecs/category/list.do',
		dataType: 'json',
		success:function(data){
			pric_data=data.rows;
			for(var i=0;i<pric_data.length;i++){
				if(pric_data[i].isVisible!=false){
					data_subclass.push({"id":pric_data[i].id,"text":pric_data[i].name});
				}
			}
			data_subclass.push({"id":0,"text":'请选择一级分类'});
			$('#subclass').combobox('loadData',data_subclass);
			$("#subclass").combobox('select','0');
			$('#leaves').combobox('loadData',data_leaves);
			$("#leaves").combobox('select','0');
			data_leaves_leave.push({"id":0,"text":'请选择三级分类'});
			$('#leaves_leave').combobox('loadData',data_leaves_leave);
			$("#leaves_leave").combobox('select','0');
		}
	});
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
	for(var i=0;i<26;i++)
	{
		var idnum=String.fromCharCode((65+i));
		if($("#"+idnum).find(".list_li_class").length==0){
			$("#"+idnum).remove();
		}
	}
	if($("#A").find(".list_li_class").length==0){
		$("#A").remove();
	}
}
function loading_hotcity(){
	for(var i=0;i<c_data.length;i++){
		for(var j=0;j<c_data[i].childen.length;j++){
			if(c_data[i].childen[j].isHot==true){
				$(".city_list").find("ul").append("<li class=\"city_color\">"+c_data[i].childen[j].name+"</li>");
				$(".city_list").find(".city_color").click(function(){
					$(".center_list").find("ul").find("li").removeClass("mouseclass");
					$(".city_list").find("ul").find("li").removeClass("mouseclass");
				    $(this).addClass("mouseclass");
				    var text=$(this).text();
				    var text_city=$(".city ").find("li").find("a");
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