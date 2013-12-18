var display=0;
$(".a_ch").click(function(){
	display_change($(this).offset().left,$(this).offset().top);
	$(".title_a").addClass("title_btn_back");
	$(".title_a").removeClass("title_btn");
	$(this).addClass("title_btn");
	$(this).removeClass("title_btn_back");
	
});
$(".a_fu").click(function(){
	display_change($(this).offset().left,$(this).offset().top);
	$(".title_a").addClass("title_btn_back");
	$(".title_a").removeClass("title_btn");
	$(this).addClass("title_btn");
	$(this).removeClass("title_btn_back");
});
$(".a_gr").click(function(){
	display_change($(this).offset().left,$(this).offset().top);
	$(".title_a").addClass("title_btn_back");
	$(".title_a").removeClass("title_btn");
	$(this).addClass("title_btn");
	$(this).removeClass("title_btn_back");
});
$(".a_kh").click(function(){
	display_change($(this).offset().left,$(this).offset().top);
	$(".title_a").addClass("title_btn_back");
	$(".title_a").removeClass("title_btn");
	$(this).addClass("title_btn");
	$(this).removeClass("title_btn_back");
});
$(".a_kf").click(function(){
	display_change($(this).offset().left,$(this).offset().top);
	$(".title_a").addClass("title_btn_back");
	$(".title_a").removeClass("title_btn");
	$(this).addClass("title_btn");
	$(this).removeClass("title_btn_back");
});
$(".a_z").click(function(){
	$(".div_z").hide();
	$(".div_d").show();
});
$(".a_d").click(function(){
	$(".div_d").hide();
	$(".div_z").show();
});
function display_change(x,y){
	x=x-230;
	$(".list_li_back").show();
	$(".list_li_back").css("left",x+"px");
	$(".list_li_back").css("top",y+"px");
	display=1;
}

function formatter() {
	return ' <a class=\"list_btn list_de\">冻结</a><a class=\"list_btn list_sub\">修改</a>';
}