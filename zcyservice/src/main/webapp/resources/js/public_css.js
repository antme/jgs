
function loading_css(){
	var demo=$(".combo");
	$.each(demo,function(){
        if($(this).parent().hasClass("none")){
		}else{
			$(this).css({"width":"128px","height":"30px","background":"url(resources/images/public_select.png) no-repeat","_background":"url(resources/images/public_select2.jpg) no-repeat"});
			$(this).find(".combo-arrow").css({"width":"15px","height":"30px"});
			$(this).find(".combo-text").css({"width":"100px","height":"25px","margin":"2px 0px 0px 2px","line-height":"25px","_width":"93px","_height":"30px","color":"#000","text-align":"center"});
			$(".public_btn").css({"width":"117px","height":"30px","border":"none","cursor":"pointer"});
		}
	});
}
