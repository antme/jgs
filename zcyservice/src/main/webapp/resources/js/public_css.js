var nub=0;
function loading_css(){
	if($(".combo").parent().hasClass("class_tag")){	
	}else{
		$(".combo").css({"width":"128px","height":"35px","margin-right":"5px"});
	     $(".combo-arrow").css({"width":"28px","height":"35px","background":"url(resources/images/select_right_back.jpg) no-repeat"});
	     $(".combo-text").css({"width":"100px","height":"35px","line-height":"35px","_width":"93px","_height":"35px","background":"url(resources/images/select_left_back.jpg) no-repeat","color":"#fff","text-align":"center"});
	     $(".public_btn").css({"width":"117px","height":"34px","border":"none","background":"url(resources/images/public_search.png) no-repeat","cursor":"pointer"});
	}
	 
}

function loading_nodes_css(){
	$(".head_bottom").find(".combo").css({"margin-right":"20px","background":"url(resources/images/admin_select.png) no-repeat"});
    $(".head_bottom").find(".combo-arrow").css({"width":"42px","height":"46px","background":"url(resources/images/admin_select_left.png) no-repeat"});
    $(".head_bottom").find(".combo-text").css({"width":"208px","height":"42px","padding":"0","background":"none","margin":"0","text-align":"center","font-size":"16px","font-weight":"bold"});
}
function loading_css_t(){
	if($(".combo").parent().hasClass("class_tag")){	
	}else{
    $(".datebox").css({"width":"152px","height":"34px","_height":"35px","background":"none"});
    $(".datebox").find(".combo-text").css({"width":"109px","height":"34px","_height":"35px","background":"url(resources/images/public_date_left.png) no-repeat","color":"#000"});
    $(".datebox").find(".combo-arrow").css({"width":"39px","height":"34px","_height":"35px","background":"url(resources/images/public_date_right.png) no-repeat"});
    $(".tpublic_input").css({"width":"147px","height":"32px","line-height":"29px","background":"url(resources/images/public_input_text.png) no-repeat","border":"none","color":"#000","margin-top":"0px"})
    $(".public_btn").css({"width":"117px","height":"34px","border":"none","background":"url(resources/images/public_search.png) no-repeat","cursor":"pointer"});
    $(".datebox-button").css({"background-color":"#fff","border":"1px solid #cdcdcd","border-left":"none","border-right":"none"});
    $(".panel-body-noheader").css("border-right","1px solid #cdcdcd");
    $(".datagrid-wrap").css("border-right","none");
	}
}

function loading_css_s(){
    $(".r-margin-left").find(".combo").css({"width":"194px","height":"30px","line-height":"29px","_width":"192px","_height":"30px","background":"url(resources/images/login_icon.png) no-repeat 0px -340px","margin-top":"0px"});
    $(".r-margin-left").find(".combo-text").css({"width":"165px","height":"28px","line-height":"28px","_width":"192px","_height":"26px","background":"none","color":"#000","margin-top":"1px","margin-left":"1px"});
    $(".r-margin-left").find(".combo-arrow").css({"width":"28px","height":"30px","line-height":"29px","_width":"192px","_height":"30px","background":"url(resources/images/select_right_back.jpg) no-repeat"});
}

$(".s_qx").click(function(){
	$(".title_a").removeClass("title_btn");
	$(".title_a").addClass("title_btn_back");
	$(this).removeClass("title_btn_back");
	$(this).addClass("title_btn");
	$.each($(".tabs").find("li"),function(){
		if($(this).find(".tabs-title").text()=="权限组设置"){
			$(this).click();
		}
	});
});
$(".y_qx").click(function(){
	$(".title_a").removeClass("title_btn");
	$(".title_a").addClass("title_btn_back");
	$(this).removeClass("title_btn_back");
	$(this).addClass("title_btn");
	$.each($(".tabs").find("li"),function(){
		if($(this).find(".tabs-title").text()=="用户权限设置"){
			$(this).click();
		}
	});
});

function return_top(){
	document.body.scrollTop=0;
	document.documentElement.scrollTop=0;
}

function  updata_tables_css(){
	var ss=$(".datagrid-btable").find(".datagrid-row").find("td");
	var tr_width=$(".datagrid-row").find("td").width();
	$(".datagrid-row").find("td").find("div").css("width","100%");
}
function load_height(){
	if(nub==0){
		var right_height=$(document).height()+500;
		$(".right").css("height",right_height+"px");
		$(".right").css("min-height",right_height+"px");
		nub++;
	}
}
