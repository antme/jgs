<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title></title>
<link href="resources/css/comm.css" rel="stylesheet"/>
<link href="resources/css/pf.css" rel="stylesheet"/>
<link rel="stylesheet" type="text/css" href="/resources/css/easyui.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/mfc/orderCSS.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/public_class.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/user_info.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/Evaluation_publick.css" />
<link rel="stylesheet" type="text/css" href="/resources/css/admin/admin.css" />
<script type="text/javascript" src="resources/js/jquery.min.js"></script>
<script type="text/javascript" src="resources/js/ecommerce.js"></script>
<script type="text/javascript" src="resources/js/json2.js"></script>
<script type="text/javascript" src="/resources/js/city-price.public.js"></script>
<script type="text/javascript" src="/resources/js/public_css.js"></script>


</head>

<%
	if (session.getValue("userName") == null) {
		String url = request.getServerName();
		//response.sendRedirect("http://" + url + "/login.jsp");
	}
%>

<%
	String userRoleName = null;
	if (session.getAttribute("roleName") != null) {
		userRoleName = session.getAttribute("roleName").toString();
	}
	
	if("USER".equalsIgnoreCase(userRoleName)){
	    response.sendRedirect("/index_user.jsp");
	}
%>

<body onResize="resizeder();">
     <div class="head">
        <%@ include file="pages/head.jsp" %>
     </div>
     <div class="center">
         <div class="left"><%@ include file="pages/left.jsp" %></div>
         <div class="right" >
            <div id="content-right-info" style="color:red; font-size:18px;font-weight:bold;"></div>
            <div id="content-right" style="margin-top:5px; height:auto; overflow:hidden">
	            <% 
	               String pagePath = request.getParameter("p"); 
	               if(pagePath == null){
	                   if("SP".equalsIgnoreCase(userRoleName)){
	                       pagePath = "jqs/jqScheduler";
	                   }else if("MFC".equalsIgnoreCase(userRoleName)){
	                       pagePath = "web/mfc/main";
	                   }else if("SUPPER_ADMIN".equalsIgnoreCase(userRoleName)){
	                       pagePath = "admin/user/role";
	                   }else{
	                       if (session.getAttribute("indexPage") != null) {
	                           pagePath = session.getAttribute("indexPage").toString();
	                       }
	                   }
	               }
	               if(pagePath != null){
	                   pageContext.setAttribute("pagePath","pages/"+pagePath+".jsp");                           
	            
	            %>
	                <jsp:include page="${pagePath}" />
	            
	            <% } %>
            </div>
            <div id="remotePage"  class="remotePage" style="display:none;"></div>
             <div id="remotePageWindow"  style="display:none; overflow-y: scroll;"></div>
         </div>
         <%@ include file="pages/bottom.jsp" %>
		<div class="handle_events" >
		   <div class="handle_events_title">待处理事项</div>
		   <div class="handle_events_text">
		       <div id="tips"></div>
               <div class="next_info"><a onclick="getNextmsg();">下一条</a></div>
		   </div>
		</div>
	</div>
	
	<div style="display:none;">
		<div id="detailWindow"  >
			<span id="detailspan" height="200" style="margin-top:20px;"></span>
		</div>
	</div>
	
      <script type="text/javascript">
        var roleName = "<%=userRoleName%>";
        var pagePath = "<%=pagePath%>";
        
      
        $(document).ready(function(){
        	//样式初始化
        	loading_css();
            loading_css_s();
            loading_css_t();
            loading_nodes_css();          
            
            
            //数据初始化
            var csp=$("#c_s_province");
            var csc=$("#c_s_city");
            var cso=$("#c_s_county");
            if(csp.length!=0 && csc.length!=0 && cso.length!=0){
            	loading_province("c_s_province","c_s_city","c_s_county");
            }
            
            var cateid=$("#categoryId");
            if(cateid.length!=0){
            	loading_category_sum("categoryId");
            }
            
            var ad_sum=$("#c_s_category");
            if(ad_sum.length){
            	loading_category_sum("c_s_category");
            }
            
            var ssp=$("#s_s_province");
            var ssc=$("#s_s_city");
            var sso=$("#s_s_county");
            if(ssp.length!=0 && ssc.length!=0  && sso.length!=0){
            	loading_area("s_s_province", "s_s_city", "s_s_county");
            }
            
            var msp=$("#mfc_s_province");
            var msc=$("#mfc_s_city");
            var mso=$("#mfc_s_county");
            if(msp.length!=0 && msc.length!=0 && mso.length!=0){
            	loading_area("mfc_s_province", "mfc_s_city", "mfc_s_county");
            }
            //初始化表格样式
            updata_tables_css();
            
            
            //初始化回到顶部按钮
            var re=$(".return_top");
            if(re.length==0){
            	
            }else{
            	$(".handle_events").css("right","90px");
            }
            
            //初始化右边高度
            var height =document.documentElement.clientHeight;
            var topheight=height - $(".head").height();
            var index_height = ($(".title_cs").length+8)*40;
            var right_height=$(".right").height();
            if(right_height<=topheight){
                if(right_height<=index_height){
                    $(".right").css("height",index_height+"px");
                    $(".right").css("min-height",index_height+"px");
                }else{
                    $(".right").css("height",topheight);
                    $(".right").css("min-height",topheight);
                }
            }else{
            	if(right_height<=index_height){
                    $(".right").css("height",index_height+"px");
                    $(".right").css("min-height",index_height+"px");
                }
            }   
            
            //初始化页面最小宽度
            $("body").css("min-width","1250px");
            
            
            var links = $("#sidebar a");
            for(index in links){
                if(links[index].href && pagePath && pagePath!=null && links[index].href.endWith(pagePath)){
                    var $a = $(links[index].parentNode);
                    if($a.find("a").hasClass("title_cs")){
                        $(".title_cs").removeClass("display_inline");
                        $a.find("a").addClass("display_inline");
                    }else{
                        $(".title_cs").removeClass("display_inline");
                        $(".display_none").find("a").removeClass("dispaly_back2");
                        $a.parent().show();
                        $a.find("a").addClass("dispaly_back2");
                        $a.parent().parent().find(".title_cs").addClass("display_inline");
                    } 
                }
            }
            
            initDataGridEvent();
        });

        function  resizeder(){
            resizeTabAndGrid();
            $("body").css("min-width","1250px");
        }
        
    

     </script>
    
        <script type="text/javascript">
	    $(document).ready(function(){
	    	
	    	$("img").each(function(){	  
	    		if($(this).attr('src').indexOf("baidu")!=-1){
	    		$(this).hide();
	    		}
	    	});
	    	
	    });
    </script>

	 
	 <script type="text/javascript" src="resources/js/jquery.mb.browser.js"></script>
	 
</body>
</html>