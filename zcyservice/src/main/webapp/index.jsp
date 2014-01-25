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
<script type="text/javascript" src="resources/js/ecommerce.js"></script>
<script type="text/javascript" src="/resources/js/archive.js"></script>
<script type="text/javascript" src="/resources/js/jquery.easyui.min.js"></script>
</head>
<%
	if (session.getValue("userName") == null) {
		String url = request.getServerName();
		response.sendRedirect("http://" + url + "/login.jsp");
	}
%>

<body>
    <div class="head">
       <div class="head_logo"></div>
       <div class="user_name">
           <div>
               <label>欢迎<a class="userinfo" onclick="updateUserInfo()"><% out.print(session.getAttribute("userName")); %></a>进入电子信息管理系统</label>
               <a class="end_btn" href="#" onclick="logout();">退出</a>
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
           <li >
              <a href="?p=web/archive/archiveindex">首页</a>
           </li>
           <li class="menu_cline" access="adm_archive_manage"></li>
           <li class="access" access="adm_archive_manage">
              <a href="?p=web/archive/archivemanager">档案管理</a>
           
           </li>
           <li class="menu_cline access" access="adm_archive_approve"></li>
           <li  class="access" access="adm_archive_approve">
              <a href="?p=web/archive/archiveapprove">归档审核</a>          
           </li>
           
           <li class="menu_cline access" access="adm_archive_destory_approve"></li>
           <li  class="access" access="adm_archive_destory_approve">
              <a href="?p=web/archive/archivedestoryapprove">销毁审核</a>          
           </li>
           
           <li class="menu_cline" access="adm_archive_borrow_manager"></li>
      
           <li  class="access" access="adm_archive_borrow_manager">
              <a href="?p=web/archive/archiveborrow">借阅管理</a>         
           </li>
           
           <li class="menu_cline" access="adm_archive_query"></li>
           <li  class="access" access="adm_archive_query">
              <a href="?p=web/archive/archivelist">档案查询</a>         
           </li>
           <li class="menu_cline" access="adm_archive_report"></li>
           <li  class="access" access="adm_archive_report">
              <a href="?p=web/archive/archivereport">数据统计</a>         
           </li>    
           
           <li class="menu_cline" access="adm_user_manage"></li>
          
           <li  class="access" access="adm_user_manage">
              <a class="li_a" href="#" >用户管理</a>
              <ul class="ul_display">
                 <li><a href="?p=admin/user/role">权限管理</a></li>
                 <li><a href="?p=admin/user/manage">用户账号管理</a></li>
              </ul>
           </li>
           <li class="menu_cline" access="adm_sys_settings"></li>
          
          <li class="access" access="adm_sys_settings">
              <a class="li_a" href="#">系统管理</a>
              <ul class="ul_display">
                 <li><a href="?p=admin/cfg/cfg">系统设置</a></li>
                 <li><a href="?p=admin/cfg/sysreport">系统状态监控</a></li>
              </ul>
           </li>
           
       </ul>
    </div>
    <div class="context">
	    <div>
	      <% 
	                   String pagePath = request.getParameter("p"); 
	                   if(pagePath == null){
	         
	                         pagePath = "web/archive/archiveindex";
	                        
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
    <div class="bottom_info">@2014-2024 虹口区劳动争议仲裁院版权所有</div>
    <div class="handle_events" >
           <div class="handle_events_title">待处理事项</div>
           <div class="handle_events_text">
               <div id="tips"><a>测试数据</a></div>
               <div class="next_info"><a onclick="getNextmsg();">下一条</a></div>
           </div>
        </div>
 <div id="user_form" style="display:none;">
    <div id="updateuser" class="easyui-window" data-options="modal:true,closed:true,minimizable:false,maximizable:false,collapsible:false,iconCls:'icon-save',top:50" style="width:550px;height:auto;padding:10px; top:50px;">
        <form id="updateuserform" action="" method="post">
                 <ul class="f-information">
                    <li>
                        <div class="r-edit-label">用户名：</div>
                        <div class="r-edit-field cc">
                            <input name="userName" id="userName" class="r-textbox easyui-validatebox"
                                type="text" missingMessage="请输入用户名" disabled/> <label class="r-need">*</label>
                        </div>
                    </li>
                    <li>
                        <div class="r-edit-label">原密码：</div>
                        <div class="r-edit-field">
                            <input id="password" name="password" class="r-textbox easyui-validatebox"
                                type="password"  /> <label
                                class="r-need">*</label>
                        </div>
                    </li>
                    <li>
                        <div class="r-edit-label">新密码：</div>
                        <div class="r-edit-field">
                            <input id="newPwd" name="newPwd" class="r-textbox easyui-validatebox"
                                type="password"  /> <label
                                class="r-need">*</label>
                        </div>
                    </li>
                    <li>
                        <div class="r-edit-label">联系手机：</div>
                        <div class="r-edit-field">
                            <input id="mobileNumber" name="mobileNumber" class="r-textbox easyui-validatebox"
                                type="text" required missingMessage="请输入联系手机"  /> <label
                                class="r-need">*</label>
                        </div>
                    </li>
                    <li>
                        <div class="r-edit-label">Email：</div>
                        <div class="r-edit-field">
                            <input id="email" name="email" class="r-textbox easyui-validatebox"
                                required type="text" missingMessage="请输入Email" /> <label
                                class="r-need">*</label>
                        </div>
                    </li>
                    <li>
                        <div class="r-edit-label"></div>
                        <div class="r-edit-field">
                            
                            <input id="mfc_info_sum" class="r-submit fw"  type="submit" value="提交" />
                        </div>
                    </li>
                </ul>
        </form>
    </div>
    </div>
 	<script type="text/javascript">
 	    var pagePath = "<%=pagePath%>";
	    $(document).ready(function(){
	    	loading_css();
	    	
	    	var links = $(".index_menu a");
	    	pagePath="?p="+pagePath;
            for(index in links){
                if(links[index].href && pagePath && pagePath!=null && links[index].href.endWith(pagePath)){
                    var $a = $(links[index].parentNode);
                    var a_herf=$(links[index]).attr('href');
                    pagePath=String(pagePath);
                    if($(links[index]).text()=="首页" || $(links[index]).text()=="档案管理" || $(links[index]).text()=="归档审核" ||  $(links[index]).text()=="销毁审核" || $(links[index]).text()=="系统设置"
                    || $(links[index]).text()=="借阅管理" || $(links[index]).text()=="档案查询" || $(links[index]).text()=="数据统计"){                	
                    	$(".index_menu a").removeClass('menu_mouse_css');
                        $(links[index]).addClass('menu_mouse_css');
                    }else if($(links[index]).text()=="权限管理" || $(links[index]).text()=="用户账号管理"){
                    	$(".index_menu a").removeClass('menu_mouse_css');
                        $(links[index].parentNode.parentNode.parentNode).addClass('menu_mouse_css');
                    }else if($(links[index]).text()=="系统设置" || $(links[index]).text()=="系统状态监控"){
                    	$(".index_menu a").removeClass('menu_mouse_css');
                        $(links[index].parentNode.parentNode.parentNode).addClass('menu_mouse_css');
                    }
                }
            }
	    	
	    	initDataGridEvent(); 
	    	loadToDoList();
	    });
	    
	    function logout(){
	    	postAjaxRequest("/ecs/user/logout.do", {}, function(data){
	    		window.location.href="login.jsp";
	         });
	    }
	    
	    function updateUserInfo(){
	    	postAjaxRequest("/ecs/user/info.do", {}, function(data){
	    		$("#updateuserform").form('clear');
                $("#updateuserform").form('load',data.data);
                $('#updateuser').window('setTitle', "修改信息");
                openDialog("updateuser");
             });
	    }
	    
	    $("#updateuserform").form({
	        url : '/ecs/user/update.do',
	        onSubmit : function() {
	            return $(this).form('validate');
	        },
	        success : function(data) {
	        	var info = eval('(' + data + ')');
	                 if(info.code!="200"){
	                     $.messager.alert("修改失败",info.msg);
	                 }else{
	                     $.messager.alert("修改信息","修改信息成功！");
	                 }
	            $('#updateuser').window('close');
	        }
	    });
	    
	    $(".index_menu").find("li").mouseover(function(){
	        $(this).find("a").next(".ul_display").show();
	    });
	    $(".index_menu").find("li").mouseout(function(){
            $(this).find("a").next(".ul_display").hide();
        });
	    
	    
	    var userRoles = undefined;
	    postAjaxRequest("/ecs/user/access.do", {}, function(data) {
	        userRoles = data.data;
	        for(i in userRoles){    

	        	userRolestr +=userRoles[i];

            }
	        
	        checkRoles();
	    }, false);

	    function checkRoles(){
	        var buttons = $('li[access]');
	        buttons.each(function(index){
	            var node = jQuery(buttons[index]);
	            var roleId = node.attr("access");
	            var hasAccess = false;
	            for(i in userRoles){    

	                if(userRoles[i] == roleId){

	                    hasAccess = true;
	                    break;
	                }
	            }
	            if(!hasAccess){
	                node.hide();
	            }else{	            
	                node.show();	               
	            }
	            
	        });
	        
	        
	    }
    </script>
</body>
</html>