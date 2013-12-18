var pric_data;
var boolss=undefined;
var category;
var price;
var dataload=false;
function loading_province(fid,sid,tid){
	if($("#"+fid).length==0){
	}else{
		if(pric_data==boolss){
			$.ajax({
				type:'post',
				url:'/ecs/location/list.do',
				dataType: 'json',
				success:function(data){
					pric_data=data.rows;
					loading_data(fid,sid,tid);
					dataload=true;
				}
			});
		}else{
			loading_data(fid,sid,tid);
		}
	}
}

function loadcate_data(fid,sid,tid){
	var data_province = [];
	var data_county = [];
	var data_city = [];
	var categorys;
	$.ajax({
		type:'post',
		url:'/ecs/category/list.do',
		dataType: 'json',
		success:function(data){
			categorys=data.rows;
			data_province.push({"id":0,"text":'选择一级类别'});
			$('#'+fid).combobox('loadData',data_province);
			$('#'+fid).combobox('select','0');
			data_city.push({"id":0,"text":'选择二级类别'});
			$('#'+sid).combobox('loadData',data_city);
			$('#'+sid).combobox('select','0');
			data_county.push({"id":0,"text":'选择三级类别'});
			$('#'+tid).combobox('loadData',data_county);
			$('#'+tid).combobox('select','0');
			for(var i=0;i<categorys.length;i++){
				data_province.push({"id":categorys[i].id,"text":categorys[i].name});
			}
			$('#'+fid).combobox('loadData',data_province);
			$('#'+fid).combobox({onSelect:function(){
				data_city=[];
				var peo = $('#'+fid).combobox('getValue');
						for(var i=0;i<categorys.length;i++){
							if(categorys[i].id==peo){
								for(var j=0;j<categorys[i].childen.length;j++){
									data_city.push({"id":categorys[i].childen[j].id,"text":categorys[i].childen[j].name});
								}
							}
						}
						data_city.push({"id":0,"text":'选择二级类别'});
						$('#'+sid).combobox('loadData',data_city);
						$('#'+sid).combobox('select','0');
					}
			});
			$('#'+sid).combobox({onChange:function(){
				data_county=[];
				  var peo = $('#'+sid).combobox('getValue');
						for(var i=0;i<categorys.length;i++){
								for(var j=0;j<categorys[i].childen.length;j++){
									if(categorys[i].childen[j].id==peo){
										var dll=categorys[i].childen[j].childen;
										for(var z=0;z<dll.length;z++){
											data_county.push({"id":dll[z].id,"text":dll[z].name});
										   }
								     }
							    }
						}
						data_county.push({"id":0,"text":'选择三级类别'});
						$('#'+tid).combobox('loadData',data_county);
						$('#'+tid).combobox('select','0');
					}
			});
		}
	});
	
	
}
function loading_data(fid,sid,tid){
	var data_province = [];
	var data_county = [];
	var data_city = [];
	data_province.push({"id":0,"text":'请选择省'});
	$('#'+fid).combobox('loadData',data_province);
	$('#'+fid).combobox('select','0');
	data_city.push({"id":0,"text":'请选择市'});
	$('#'+sid).combobox('loadData',data_city);
	$('#'+sid).combobox('select','0');
	data_county.push({"id":0,"text":'请选择区'});
	$('#'+tid).combobox('loadData',data_county);
	$('#'+tid).combobox('select','0');
	for(var i=0;i<pric_data.length;i++){
		data_province.push({"id":pric_data[i].id,"text":pric_data[i].name});
	}
	$('#'+fid).combobox('loadData',data_province);
	$('#'+fid).combobox({onSelect:function(){
		data_city=[];
		var peo = $('#'+fid).combobox('getValue');
				for(var i=0;i<pric_data.length;i++){
					if(pric_data[i].id==peo){
						for(var j=0;j<pric_data[i].childen.length;j++){
							data_city.push({"id":pric_data[i].childen[j].id,"text":pric_data[i].childen[j].name});
						}
					}
				}
				data_city.push({"id":0,"text":'请选择市'});
				$('#'+sid).combobox('loadData',data_city);
				$('#'+sid).combobox('select','0');
			}
	});
	$('#'+sid).combobox({onChange:function(){
		data_county=[];
		  var peo = $('#'+sid).combobox('getValue');
				for(var i=0;i<pric_data.length;i++){
						for(var j=0;j<pric_data[i].childen.length;j++){
							if(pric_data[i].childen[j].id==peo){
								var dll=pric_data[i].childen[j].childen;
								for(var z=0;z<dll.length;z++){
									data_county.push({"id":dll[z].id,"text":dll[z].name});
								   }
						     }
					    }
				}
				data_county.push({"id":0,"text":'请选择区'});
				$('#'+tid).combobox('loadData',data_county);
				$('#'+tid).combobox('select','0');
			}
	});
}


function loading_category(id){
	$.ajax({
		type:'post',
		url:'/ecs/category/list.do',
		dataType: 'json',
		success:function(data){
			category=data.rows;
			loading_category_leaves(id);
		}
	});
}
function loading_category_sum(id){
	if($("#"+id).length==0){	
	}else{
		$.ajax({
			type:'post',
			url:'/ecs/category/list.do',
			dataType: 'json',
			success:function(data){
				category=data.rows;
				loading_category_nodes(id);
			}
		});
	}
}
function loading_category_leaves(id){
	var data_category=[];
	for(var i=0;i<category.length;i++){
			for(var j=0;j<category[i].childen.length;j++){
				var data_childen=category[i].childen[j].childen;
				for(var z=0;z<data_childen.length;z++){
					data_category.push({"id":data_childen[z].id,"text":data_childen[z].name});
				}
			}
		}
	data_category.push({"id":"0","text":"请选择"});
    $('#'+id).combobox('loadData',data_category);
    $('#'+id).combobox('select','0');
}



function loading_category_nodes(id){
	var data_category=[];
	for(var i=0;i<category.length;i++){
			for(var j=0;j<category[i].childen.length;j++){
			   data_category.push({"id":category[i].childen[j].id,"text":category[i].childen[j].name});
			}
		}
   data_category.push({"id":"0","text":"请选择类别"});
   $('#'+id).combobox('loadData',data_category);
   $('#'+id).combobox('select','0');
}


function loading_price(){
	$.ajax({
		type:'post',
		url:'/ecs/locationPrice/list.do',
		dataType: 'json',
		success:function(data){
			price=data.rows;
		}
	});
}
function locationPrice(locationPrice){
	$.ajax({
		type:'post',
		url:'/ecs/locationPrice/list.do',
		dataType: 'json',
		success:function(data){
			locationPrice=data.rows;
		}
	});
}

function search_leaves(id){	
	for(var i=0;i<category.length;i++){
			for(var j=0;j<category[i].childen.length;j++){
				var data_childen=category[i].childen[j].childen;
				for(var z=0;z<data_childen.length;z++){
					seach_category.push({"id":data_childen[z].id,"text":data_childen[z].name});
				}
			}
		}
}
function category_value(category_value){
	$.ajax({
		type:'post',
		url:'/ecs/category/list.do',
		dataType: 'json',
		success:function(data){
			if(data.total==0){
				alert("该地区目前没有价格可供查询！");
			}else{
				category_value=data.rows;
			}
		}
	});
}

function append(){
	for(var i=0;i<25;i++)
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
}

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
    }
    
});


function loading_sp(id){
	var data_text = [];
	$.ajax({
		type:'post',
		url:'/ecs/sp/select.do',
		dataType: 'json',
		success:function(data){
			if(data.total==0){
				alert("当前没有服务商可供选择！");
			}else{
				var value= data.rows;
				for(var i=0;i<value.length;i++){
					data_text.push({"id":value[i].id,"text":value[i].spUserName});
				}
				data_text.push({"id":"0","text":"请选择服务商"});
				$('#'+id).combobox('loadData',data_text);
				$('#'+id).combobox('select','0');
			}
		}
	});
	$('#'+id).combobox({onChange:function(){
		var peo = $('#'+id).combobox('getValue');
		if(peo!="0"){
			$.ajax({
				type:'post',
				url:'/ecs/sp/getscore.do',
				dataType: 'json',
				data:'id='+peo,
				success:function(data){
					if(data.code==0){
						alert("当前没有服务商可供选择！");
					}else{
						$("#score").text(data.data.score);
						if(!data.data.scoreGood){
							data.data.scoreGood = 0;
						}
						if(!data.data.scoreMiddle){
							data.data.scoreMiddle = 0;
						}
						if(!data.data.scoreBad){
							data.data.scoreBad = 0;
						}
						$("#scoreGood").text("服务满意("+data.data.scoreGood+"次)");
						$("#scoreMiddle").text("服务一般("+data.data.scoreMiddle+"次)");
						$("#scoreBad").text("服务不满意("+data.data.scoreBad+"次)");
						loading_level(data.data.score,"level");
					}
				}
			 });
		   }
	    }
	});
		
}
function loading_area(fid,sid,tid){
	//$('#'+fid).combobox('setValue','请选择省');
	//$('#'+sid).combobox('setValue','请选择市');
	//$('#'+tid).combobox('setValue','请选择区');
	if($("#"+fid).length==0){
		
	}else{
	$('#'+fid).combobox({
        url:'/ecs/location/listbyparent.do',
        valueField:'id',
        textField:'name',
        onSelect: function(rec){
            loading_area_data(sid,rec.id);
            $('#'+sid).combobox('setValue','请选择市');
        },
        loadFilter:function(data){
            return data.rows;
        }
    });
	$('#'+sid).combobox({
        valueField:'id',
        textField:'name',
        onSelect: function(rec){
            loading_area_data(tid,rec.id);
            $('#'+tid).combobox('setValue','请选择区');
        },
        loadFilter:function(data){
            return data.rows;
        }
    });
	$('#'+tid).combobox({
        valueField:'id',
        textField:'name',
        loadFilter:function(data){
            return data.rows;
        }
    });
	}
}
function loading_area_data(mid,nid){
	 var disUrl = '/ecs/location/listbyparent.do?parent='+nid;
     $('#'+mid).combobox('reload', disUrl);
}
