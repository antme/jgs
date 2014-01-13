<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>电子档案管理系统</title>
<link href="resources/css/comm.css" rel="stylesheet"/>
<link href="resources/css/easyui.css" rel="stylesheet"/>
<link href="resources/css/public_class.css" rel="stylesheet"/>
<script type="text/javascript" src="/resources/js/jquery.min.js"></script>
<script type="text/javascript" src="/resources/js/public_css.js"></script>
<script type="text/javascript" src="/resources/js/ecommerce.js"></script>
</head>
<body>
    <div class="head">
       <div class="user_name">
           <div>
               <label>欢迎xx进入电子信息管理系统</label>
               <a class="end_btn" href="login.jsp">退出</a>
           </div>
           <div class="time">
               <label id="time"></label>
               <script type="text/javascript">
                  var speed = 1000;
                  function Marquee() {
                      var myDate = new Date();
                      $("#time").text(myDate.toLocaleString());
                  }
                  var MyMar = setInterval(Marquee, speed);
               </script>
           </div>
       </div>
    </div>
    <div class="index_menu">
       <ul>
        	<li>
              <a href="?p=web/archive/archivereport">首页</a>
           
           </li>
           <li class="menu_cline"></li>
           <li>
              <a href="?p=web/archive/archivemanager">档案管理</a>
           
           </li>
           <li class="menu_cline"></li>
           <li>
              <a href="?p=web/archive/archiveapprove">档案审核</a>          
           </li>
           
           <li class="menu_cline"></li>
      
           <li>
              <a href="?p=web/archive/archiveborrow">借阅管理</a>         
           </li>
           <li class="menu_cline"></li>
           <li>
              <a href="?p=web/archive/archivelist">档案查询</a>         
           </li>
           <li class="menu_cline"></li>
           <li>
              <a href="?p=web/archive/archivereport">数据统计</a>         
           </li>    
           
           
           
           <li class="menu_cline"></li>
          
           <li>
              <a href="#">用户管理</a>
              <ul class="ul_display">
                 <li><a href="?p=admin/user/role">权限管理</a></li>
                 <li><a href="?p=admin/user/manage">用户账号管理</a></li>
              </ul>
           </li>
           <li class="menu_cline"></li>
          
           <li>
              <a href="#">系统管理</a>
              <ul class="ul_display">
                 <li><a href="#">系统设置</a></li>
                 <li><a href="#">系统状态监控</a></li>
              </ul>
           </li>
       </ul>
    </div>
    <div class="context">
	    <div>
	      <% 
	                   String pagePath = request.getParameter("p"); 
	                   if(pagePath == null){
	         
	                         pagePath = "web/archive/archivereport";
	                        
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
    
 	<script type="text/javascript">
	    $(document).ready(function(){
	    	loading_css();
	    	initDataGridEvent(); 
	    });
    </script>
</body>
</html>