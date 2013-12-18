var c_data;
var childen_data;
var tle;
var did;
var run = true;
var loading=1;
var list_price;
var data_checkbox_add=[];
var data_checkbox_del=[];
var load_data;
var category;
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
				if(c_data[i].isVisible!=false){
					if(c_data[i].name=="澳门" || c_data[i].name=="台湾" || c_data[i].name=="香港"){
						continue;
					}else{
						var str=" <li id='"+c_data[i].id+"' class=\"list_li_class\"><div class=\"provinces\">"+c_data[i].name+"</div><div class=\"city2 \"><ul></ul><div></li>";
						$("#"+letters(c_data[i].name)).append(str);	
					}
				}	
		    	var ar=$("#"+c_data[i].id).find(".city2");
		    	for(var j=0;j<childen_data.length;j++){
		    		if(c_data[i].childen[j].name!==""){	
		    	    tle=c_data[i].childen[j].childen;	
		    	    if(c_data[i].childen[j].isVisible!=false){
						var strurl=" <li id="+c_data[i].childen[j].id+" parentid="+c_data[i].childen[j].parent_id+" title="+c_data[i].childen[j].name+"><a>"+c_data[i].childen[j].name+"</a><label class=\"left_a\"></label></li>";
						ar.find("ul").append(strurl);
						$("#"+c_data[i].childen[j].id).click(function(){
			    			var tid=$(this).attr("id");
			    			var X = $(this).offset().top;
			    			var s=$(this);
			    			X=X-22;var Y=125;
			    			$(".city2 ").find("li").css("color","#000");
				    		$(this).css("color","red");
				    		loading_county(tid,X);
				    		$(".county_list").css({"left":Y+"px","top":X+"px","display":"block"});
			    		    run=false;
			    		});
		    	    }
		    		}
		    	}
			}
			display_ul();
			loading_hotcity();
			ajaxLoadEnd();
		}
	});
	//list_option();
	
	$(".center_list").find("li").click(function(){
		$(".center_list").find("ul").find("li").removeClass("mouseclass");
		$(".city_list").find("ul").find("li").removeClass("mouseclass");
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
	
	$(".county_list").click(function(){
		run=false;
	});
});

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
				    var text_city=$(".city2 ").find("li");
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
		            var sturl=" <a class=\"all_list_infos all\"  title='全选'><input onclick=\"all_location_click(this)\" type='checkbox'/>全选</a>";
					$(".county_list_info_list").append(sturl);
		            for(var z=0;z<county_data.length;z++){
	            		if(county_data[z].isVisible!=false){
	            			var sturl;
	            			if(county_data[z].checked){
		            		   sturl=" <a class=\"county_list_infos\" id="+county_data[z].id+" parentid="+county_data[z].parent_id+" title="+county_data[z].name+"><input type=\"checkbox\" checked=\"checked\" /><label>"+county_data[z].name+"</label></a>";

	            			}else{
	            			  sturl=" <a class=\"county_list_infos\" id="+county_data[z].id+" parentid="+county_data[z].parent_id+" title="+county_data[z].name+"><input type=\"checkbox\" /><label>"+county_data[z].name+"</label></a>";
	            			  
	            			}
	            			$(".county_list_info_list").append(sturl);
	            			$("#"+county_data[z].id).find("label").click(function(){
	            				$(".county_list_info_list").find(".county_list_infos").find("label").removeClass("selected_county");
	            				$(".county_list_info_list").find(".county_list_infos").find("label").removeClass("selected_countys");
	            				$(this).addClass("selected_county");
	            				$(".cate_one_list").find("a").remove();
					    		$(".cate_two_list").find("a").remove();
		        				$(".cate_three_list").find("a").remove();
		        				$(".cate_list_info_one").find(".cate_list_info_open").remove();
		        				$(".cate_list_info_two").find(".cate_list_info_open").remove();
		        				$(".cate_list_info_three").find(".cate_list_info_open").remove();
					    		loading_one_data();
					    		$(".county_list_info_list").find("input").removeAttr('checked');
					    		//$(this).parent().find("input").click();
					    		$("#"+$(this).parent().attr("id")).find("input").click();
	            			});
	            		}
	            	}  
		            loading_one_data();	           
		            $(".county_list").append("<div class=\"baocun float_button_left\"><button class=\"disy_red margin-left10\" onclick=\"closed_window();\">关闭</button><button onclick='sumbit_data("+top+")' class=\"disy_blue\">保存</button></div>")
				}
			 });
             
             
}
function all_location_click(obj){
	if($(obj).is(':checked')){
		var ll=$(".county_list_info_list").find(".county_list_infos").find("input");
    	for(var i=0;i<ll.length;i++){
    		if($(ll[i]).is(':checked')){
    			
    		}else{
    			$(ll[i]).click();
    		}
    	}
	}else{
		var ll=$(".county_list_info_list").find(".county_list_infos").find("input");
    	for(var i=0;i<ll.length;i++){
    		if($(ll[i]).is(':checked')){
    			$(ll[i]).click();
    		}else{
    			
    		}
    	}
	}
}
function all_click_three(obj){
	var cid=[];
	var city_li=$(".county_list_info_list").find(".county_list_infos");
	for(var i=0;i<city_li.length;i++){
		if($(city_li[i]).find("input").is(':checked')){
			cid.push({id:$(city_li[i]).attr("id")});
		}
	}
	if(cid.length==0){
		selected_county();
    }else{
    	if($(obj).is(':checked')){
    		var ll=$(".cate_three_list").find(".county_list_infos").find("input");
        	for(var i=0;i<ll.length;i++){
        		if($(ll[i]).is(':checked')){
        			
        		}else{
        			$(ll[i]).click();
        		}
        	}
    	}else{
    		var ll=$(".cate_three_list").find(".county_list_infos").find("input");
        	for(var i=0;i<ll.length;i++){
        		if($(ll[i]).is(':checked')){
        			$(ll[i]).click();
        		}else{
        			
        		}
        	}
    	}
    }
}


function loading_one_data(){
	if($(".cate_one_list").find(".county_list_infos").length>0){
		loading_one_css();
	}else{
		$.ajax({
			type:'post',
			url:'/ecs/category/list.do',
			dataType: 'json',
			success:function(data){
				var cate_data=data.rows;
				load_data=data.rows;
				var sturl=" <a class=\"all_list_infos all\" title='全选'><input onclick=\"one_checkbox(this)\" type='checkbox'/>全选</a>";
				$(".cate_one_list").append(sturl);
	            for(var z=0;z<cate_data.length;z++){
	        		if(cate_data[z].isVisible!=false){
	        			if(cate_data.length==0){
	        			}else{
		        			sturl=" <a class=\"county_list_infos\" id="+cate_data[z].id+" parentid="+cate_data[z].parent_id+" title="+cate_data[z].name+"><input type=\"checkbox\" /><label>"+cate_data[z].name+"</label></a>";
		        			$(".cate_one_list").append(sturl);
	        			}
	        			$("#"+cate_data[z].id).find("label").click(function(){
	        				$(".cate_one_list").find(".county_list_infos").find("label").removeClass("selected_county");
            				$(this).addClass("selected_county");
	        				$(".cate_two_list").find("a").remove();
	        				$(".cate_three_list").find("a").remove();
	        				$(".cate_list_info_two").find(".cate_list_info_open").remove();
	        				$(".cate_list_info_three").find(".cate_list_info_open").remove();    		
	        				loading_two_data($(this).parent().attr("id"));
	        			});
	        		}
	        	}
	            loading_one_css();
			}
	     });
	}
}
function loading_two_data(id){
	 $.ajax({
			type:'post',
			url:'/ecs/category/listbyquery.do',
			dataType: 'json',
			data:'parent_id='+id,
			success:function(data){
				var cate_data=data.rows;
				var sturl=" <a class=\"all_list_infos all\" fid="+id+" title='全选'><input  onclick=\"two_checkbox('"+id+"',this)\" type='checkbox'/>全选</a>";
				$(".cate_two_list").append(sturl);
	            for(var z=0;z<cate_data.length;z++){
         		if(cate_data[z].isVisible!=false){
         			var sturl=" <a class=\"county_list_infos\" id="+cate_data[z].id+" parentid="+cate_data[z].parent_id+" title="+cate_data[z].name+"><input  type=\"checkbox\" /><label>"+cate_data[z].name+"</label></a>";
         			$(".cate_two_list").append(sturl);
         			$("#"+cate_data[z].id).find("label").click(function(){
         				$(".cate_two_list").find(".county_list_infos").find("label").removeClass("selected_county");
         				$(this).addClass("selected_county");
        				$(".cate_three_list").find("a").remove();
        				$(".cate_list_info_three").find(".cate_list_info_open").remove();
        				loading_three_data($(this).parent().attr("id"),id);
         			});
         			}
	            }
	            loading_two_css();
			}
      });
}
function loading_three_data(id,pid,bool){
	 $.ajax({
			type:'post',
			url:'/ecs/category/listbyquery.do',
			dataType: 'json',
			data:'parent_id='+id,
			success:function(data){
				var cate_data=data.rows;
				if(cate_data.length==0){
					
				}else{
					var sturl=" <a class=\"all_list_infos all\" fid="+pid+" title='全选' ><input onclick=\"all_click_three(this)\" type='checkbox'/>全选</a>";
					$(".cate_three_list").append(sturl);
				}
	            for(var z=0;z<cate_data.length;z++){
	            	if(cate_data[z].isVisible!=false){
	            		var sturl=" <a class=\"county_list_infos\" parentid="+cate_data[z].parent_id+" title="+cate_data[z].name+"><input id="+cate_data[z].id+" class=\"input_sel\" type=\"checkbox\" onclick=\"three_checkbox('"+cate_data[z].id+"','"+cate_data[z].parent_id+"','"+pid+"')\"/>"+cate_data[z].name+"</a>";
	            		$(".cate_three_list").append(sturl);
	            	}
	            }  
	            loading_three_css();
			}
    }); 
}

function loading_one_css(){
	var location=$(".county_list_info_list").find(".county_list_infos");
	var location_id;
	if(location.length==0){
		
	}else{
		for(var i=0;i<location.length;i++){
			if($(location[i]).find("label").hasClass("selected_county")){
				location_id=location[i].id;
			}
		}
	}
	$.ajax({
		type:'post',
		url:'/ecs/sp/location/listids.do',
		dataType: 'json',
		data:'location_id='+location_id,
		success:function(data){
			category=data.rows;
			for(index in data.rows){
				$(".cate_one_list").find("#"+data.rows[index]).find("input").attr("checked","checked");
			}
			
		}
	});
	if(data_checkbox_del.length>0){
			for(var i=0;i<data_checkbox_del.length;i++){
				if(location_id==data_checkbox_del[i].location_id){
					if(data_checkbox_del[i].isVisible==true){
						$("#"+data_checkbox_del[i].fristid).find("input").attr("checked","checked");
					}else{
						$("#"+data_checkbox_del[i].fristid).find("input").removeAttr('checked');
					}
				}
			}
		
	}else{
	}
}
function loading_two_css(){
	var location=$(".county_list_info_list").find(".county_list_infos");
	var location_id;
	if(location.length==0){
		
	}else{
		for(var i=0;i<location.length;i++){
			if($(location[i]).find("label").hasClass("selected_county")){
				location_id=location[i].id;
			}
		}
	}

	for(index in category){
		$(".cate_two_list").find("#"+category[index]).find("input").click();
	}
	if(data_checkbox_del.length>0){
			for(var i=0;i<data_checkbox_del.length;i++){
				if(location_id==data_checkbox_del[i].location_id){
					if(data_checkbox_del[i].isVisible==true){
						$("#"+data_checkbox_del[i].twoid).find("input").attr("checked","checked");
					}else{
						$("#"+data_checkbox_del[i].twoid).find("input").removeAttr('checked');
					}
				}
			}
		
	}else{
	}
}

function loading_three_css(){
	var location=$(".county_list_info_list").find(".county_list_infos");
	var location_id;
	if(location.length==0){
		
	}else{
		for(var i=0;i<location.length;i++){
			if($(location[i]).find("label").hasClass("selected_county")){
				location_id=location[i].id;
			}
		}
	}

	for(index in category){
		$(".cate_three_list").find("#"+category[index]).attr("checked","checked");
	}
	if(data_checkbox_del.length>0){
			for(var i=0;i<data_checkbox_del.length;i++){
				if(location_id==data_checkbox_del[i].location_id){
					if(data_checkbox_del[i].isVisible==true){
						$("#"+data_checkbox_del[i].category_id).attr("checked","checked");
					}else{
						$("#"+data_checkbox_del[i].category_id).removeAttr('checked');
					}
				}
			}
		
	}else{
	}
	
}
function one_checkbox(obj){
	if($(obj).is(':checked')){
		var ll=$(".cate_one_list").find(".county_list_infos").find("input");
		for(var i=0;i<ll.length;i++){
    		if($(ll[i]).is(':checked')){
    			
    		}else{
    			$(ll[i]).click();
    		}
    	}
	}else{
		$(".cate_one_list").find(".county_list_infos").find("input").removeAttr('checked');
	}
	
	var cid=[];
	var city_li=$(".county_list_info_list").find("a");
	for(var i=0;i<city_li.length;i++){
		if($(city_li[i]).find("input").is(':checked')){
			cid.push({id:$(city_li[i]).attr("id")});
		}
	}
	if(cid.length==0){
		selected_county();
	}else{
		
		var exists = false;
		//如果已经存在则跟新isVisible属性
		for (s in cid){
			for(var i=0;i<load_data.length;i++){
					for(var j=0;j<load_data[i].childen.length;j++){
						for(var z=0;z<load_data[i].childen[j].childen.length;z++){
							for(index in data_checkbox_del){
								if(data_checkbox_del[index].category_id == load_data[i].childen[j].childen[z].id && data_checkbox_del[index].location_id == cid[s].id && data_checkbox_del[index].twoid==load_data[i].childen[j].id && data_checkbox_del[index].fristid==load_data[i].id){
									data_checkbox_del.splice(index,1);
									exists = true;
									break;
								}
							}
						}
					}
			}
		}
		//不存在, 放到数组
		if(!exists){
				for (s in cid){
					for(var i=0;i<load_data.length;i++){
							for(var j=0;j<load_data[i].childen.length;j++){
								for(var z=0;z<load_data[i].childen[j].childen.length;z++){
									data_checkbox_del.push({"category_id":load_data[i].childen[j].childen[z].id,"location_id":cid[s].id,"isVisible":true,"twoid":load_data[i].childen[j].id,"fristid":load_data[i].id});
								}
							}
						}
				}	
		}
		$(".cate_two_list").find("a").remove();
		$(".cate_three_list").find("a").remove();
		//console.log(data_checkbox_del);
	}
}

function two_checkbox(pid,obj){
	if($(obj).is(':checked')){
		var ll=$(".cate_two_list").find(".county_list_infos").find("input");
		for(var i=0;i<ll.length;i++){
    		if($(ll[i]).is(':checked')){
    			
    		}else{
    			$(ll[i]).click();
    		}
    	}
	}else{
		$(".cate_two_list").find(".county_list_infos").find("input").removeAttr('checked');
	}
	var cid=[];
	var city_li=$(".county_list_info_list").find("a");
	for(var i=0;i<city_li.length;i++){
		if($(city_li[i]).find("input").is(':checked')){
			cid.push({id:$(city_li[i]).attr("id")});
		}
	}
	if(cid.length==0){
		selected_county();
	}else{
		
		var exists = false;
		//如果已经存在则跟新isVisible属性
		for (s in cid){
			for(var i=0;i<load_data.length;i++){
			     if(pid==load_data[i].id){
			    	 for(var j=0;j<load_data[i].childen.length;j++){
							for(var z=0;z<load_data[i].childen[j].childen.length;z++){
								for(index in data_checkbox_del){
									if(data_checkbox_del[index].category_id == load_data[i].childen[j].childen[z].id && data_checkbox_del[index].location_id == cid[s].id && data_checkbox_del[index].twoid==load_data[i].childen[j].id && data_checkbox_del[index].fristid==load_data[i].id){
										data_checkbox_del.splice(index,1);
										exists = true;
										break;
									}
								}
							}
						} 
			     }	
			}
		}
		
		//不存在, 放到数组
		if(!exists){
				for (s in cid){
					for(var i=0;i<load_data.length;i++){
						if(pid==load_data[i].id){
							for(var j=0;j<load_data[i].childen.length;j++){
								for(var z=0;z<load_data[i].childen[j].childen.length;z++){
									data_checkbox_del.push({"category_id":load_data[i].childen[j].childen[z].id,"location_id":cid[s].id,"isVisible":true,"twoid":load_data[i].childen[j].id,"fristid":load_data[i].id});
								}
							}
						}
					}
				}
				
	
		}
		//console.log(data_checkbox_del);
		$(".cate_three_list").find("a").remove();
	}
}

function three_checkbox(bid,pid,sid){
	var cid=[];
	var city_li=$(".county_list_info_list").find(".county_list_infos");
	for(var i=0;i<city_li.length;i++){
		if($(city_li[i]).find("input").is(':checked')){
			cid.push({id:$(city_li[i]).attr("id")});
		}
	}
	if(cid.length==0){
		selected_county();
	}else{
		
		var exists = false;
		//如果已经存在则跟新isVisible属性
		for(i in cid){
			for(index in data_checkbox_del){
				if(data_checkbox_del[index].category_id == bid && data_checkbox_del[index].location_id == cid[i].id && data_checkbox_del[index].twoid==pid && data_checkbox_del[index].fristid==sid){
					data_checkbox_del.splice(index,1);
					exists = true;
					break;
				}
			}
		}
		
		
		//不存在, 放到数组
		if(!exists){
			for(i in cid){
				if($("#"+bid).is(':checked')){
					data_checkbox_del.push({"category_id":bid,"location_id":cid[i].id,"isVisible":true,"twoid":pid,"fristid":sid});
				}else{
					data_checkbox_del.push({"category_id":bid,"location_id":cid[i].id,"isVisible":false,"twoid":pid,"fristid":sid});
				}	
			}
			
		}
	}
}

function sumbit_data(top){
	var data_checkbox_delel=[];
	$(".baocun").find('.disy_blue').attr('disabled',"true");
	for(var i=0; i<data_checkbox_del.length;i++){
		data_checkbox_delel.push({"category_id":data_checkbox_del[i].category_id,"location_id":data_checkbox_del[i].location_id,"isVisible":data_checkbox_del[i].isVisible});
	}
	if(data_checkbox_del.length!=0){
		var postData = {
				"rows" : JSON.stringify(data_checkbox_delel)
		};
		postAjaxRequest("/ecs/sp/location/updatebatch.do", postData, sp_submit_cate_callback,false);
		ajaxLoading2(top);
	}else{
		sp_error_cate_callback();
		$(".baocun").find('.disy_blue').removeAttr("disabled"); 
	}
}
function selected_county(){
	//$.messager.confirm('保存失败','请选择市\县\区');
	alert('请选择市\县\区');
}
function sp_submit_cate_callback(){
	//$.messager.confirm('保存成功','更改服务区域成功');
	$(".baocun").find('.disy_blue').removeAttr("disabled"); 
	alert('更改服务区域成功');
	ajaxLoadEnd();
}
function sp_error_cate_callback(){
	//$.messager.confirm('提示','未更改服务区域！');
	alert('更改失败,请核对更改信息！设定单个品类服务区域请选择三级类别！');
	ajaxLoadEnd();
}

function closed_window(){
	$(".county_list").hide();
	$(".city2").find("li").css("color","#000");
}

document.body.onclick = function(){
    if(run){
    	$(".county_list").hide();
    	$(".city2").find("li").css("color","#000");
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
