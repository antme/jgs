<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>电子档案管理系统</title>
<link href="resources/css/comm.css" rel="stylesheet"/>
<script type="text/javascript" src="resources/js/jquery.min.js"></script>
</head>
<body>
    <div class="head">
       <div class="user_name">
           <div>
               <label>欢迎xx进入电子信息管理系统</label>
               <a class="end_btn">退出</a>
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
    <div class="menu">
       <ul>
           <li>
              <a href="login.jsp">档案管理</a>
              <ul class="ul_display">
                 <li><a href="login.jsp">采集整理</a></li>
                 <li><a href="#">归档管理</a></li>
                 <li><a href="#">变更管理</a></li>
                 <li><a href="#">档案管理</a></li>
                 <li><a href="#">销毁管理</a></li>
              </ul>
           </li>
           <li class="menu_cline"></li>
           <li>
              <a href="#">业务管理</a>
              <ul class="ul_display">
                 <li><a href="#">业务配置</a></li>
                 <li><a href="#">任务管理</a></li>
              </ul>
           </li>
           <li class="menu_cline"></li>
           <li>
              <a href="#">借阅管理</a>
              <ul class="ul_display">
                 <li><a href="#">借阅</a></li>
                 <li><a href="#">归还</a></li>
                 <li><a href="#">催还</a></li>
                 <li><a href="#">催还</a></li>
              </ul>
           </li>
           <li class="menu_cline"></li>
           <li>
              <a href="#">查询统计</a>
              <ul class="ul_display">
                 <li><a href="#">档案查询</a></li>
                 <li><a href="#">数据统计</a></li>
              </ul>
           </li>
           <li class="menu_cline"></li>
           <li>
              <a href="#">工作流管理</a>
              <ul class="ul_display">
                 <li><a href="#">工作流配置</a></li>
                 <li><a href="#">工作流发布</a></li>
                 <li><a href="#">工作流状态查询</a></li>
              </ul>
           </li>
           <li class="menu_cline"></li>
           <li>
              <a href="#">系统管理</a>
              <ul class="ul_display">
                 <li><a href="#">组织结构管理</a></li>
                 <li><a href="#">角色管理</a></li>
                 <li><a href="#">权限管理</a></li>
                 <li><a href="#">用户组管理</a></li>
                 <li><a href="#">日志管理</a></li>
              </ul>
           </li>
       </ul>
    </div>
    <div class="context">
        <div></div>
    </div>
</body>
</html>