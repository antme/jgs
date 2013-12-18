$(".title_cs").click(function(){
	$("#sidebar").find("li").find("a.title_cs").removeClass("display_inline");
	$("#sidebar").find("li").removeClass("dispaly_back");
	$("#sidebar").find("li").find("ul").hide();
	if($(this).next("ul").hasClass("display_none")){
		$(this).next("ul").show(); //fadeOut(250)
		$(this).addClass("display_inline");
	}else{
		//$(this).addClass("display_inline");
	}
});