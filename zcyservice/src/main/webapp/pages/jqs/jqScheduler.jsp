<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<!-- saved from url=(0059)http://www.trirand.com/blog/phpjqgrid/examples/jqscheduler/ -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"></meta>
<title>jqScheduler PHP Demo</title>
<link rel="stylesheet" type="text/css" media="screen" href="/pages/jqs/jqScheduler/jquery-ui-1.8.13.custom.css" />
<link rel="stylesheet" type="text/css" media="screen" href="/pages/jqs/jqScheduler/jquery.ui.tooltip.css" />
<link rel="stylesheet" type="text/css" media="screen" href="/pages/jqs/jqScheduler/ui.jqscheduler.css" />
<link rel="stylesheet" type="text/css" media="screen" href="/pages/jqs/jqScheduler/jqSchedduler.css" />

<script src="/pages/jqs/jqScheduler/jquery.js" type="text/javascript"></script>
<script src="/pages/jqs/jqScheduler/jquery-ui-custom.min.js" type="text/javascript"></script>
<script src="/pages/jqs/jqScheduler/jquery.jqScheduler.js" type="text/javascript"></script>
<style type="text">
        html, body {
            margin: 0;          /* Remove body margin/padding */
            padding: 0;
            overflow: hidden;   /* Remove scroll bars on browser window */
            font-size: 62.5%;

        }
        body {
            font-family: "Trebuchet MS", "Helvetica", "Arial",  "Verdana", "sans-serif";
        }
</style>
</head>
<body>
    <div class="page_tip">说明：日历主要包含了即将要安装的订单和个人自定义日历（比如你可以提醒下明天几点有个聚会），安装的订单的时间来源于你和客户谈好的派工时间</div><br>
    <div class="calwrapper ui-widget">
        <div class="content-ui ui-state-default">
            <div class="title_btn">
                <ul>
                    <li>今日提醒</li>
                    <li id="week_schedule">一周提醒</li>
                    <li>本月提醒</li>
                    <li>自定义提醒</li>
                </ul>
            </div>
            <script type="text/javascript">
                $(".title_btn").find("ul").find("li").click(function() {
                    $(".title_btn").find("li").removeClass("click_back");
                    $(this).addClass("click_back");
                    if ($(this).text().trim() == "今日提醒") {
                        $(".fc-button-agendaDay").click();
                        loading_txt();
                    } else if ($(this).text().trim() == "一周提醒") {
                        $(".fc-button-agendaWeek").click();
                        loading_txt();
                    } else if ($(this).text().trim() == "本月提醒") {
                        $(".fc-button-month").click();
                        loading_txt();
                    } else if ($(this).text().trim() == "自定义提醒") {
                        var myDate = new Date();
                        $.jqschedule.eventAddDialog(myDate, false);
                    } else {
                    }
                });
                function getwidth() {
                    var li = $(".title_btn").find("ul").find("li");
                    var width = li.width() - 1;
                    li.css("width","" + width + "px");
                    width = width - 2;
                    li.eq(0).css("width","" + width + "px");
                }
            </script>
            <div class="jqcalendar title_date_div">
                <div
                    style="border: 1px solid #cdcdcd; border-bottom: none; height: 50px;">
                    <label class="loading_txt"></label> <a class="left_btn_a"></a> <a
                        class="right_btn_a"></a>
                </div>
                <script type="text/javascript">
                    $(".left_btn_a").click(function() {
                        $(".fc-button-prev").click();
                        loading_txt();
                    });
                    $(".right_btn_a").click(function() {
                        $(".fc-button-next").click();
                        loading_txt();
                    });
                    function loading_txt() {
                        var txt = $(".fc-header-center").find("h2").text();
                        $(".title_date_div").find(".loading_txt").text(txt);
                    }
                </script>
            </div>
            <div id="calendar" class="jqcalendar fc ui-widget"
                style="height: auto"></div>
            <div id="event" class="jqevent">
                <form action="">
                    <table>
                        <tbody>
                            <tr>
                                <td><label style="width:50px;display:inline-block;" for="title"> 标题 :</label></td>
                                <td><input class="ui-widget-content ui-corner-all ui-input"
                                    id="title" name="title" size="55" /></td>
                            </tr>

                            <!--    <tr>
                <td>
                    <label for="location">Location</label>:
                </td>
                <td>
                    <input class="ui-widget-content ui-corner-all ui-input" id="location" name="location" size="50">
                </td>
            </tr>
            <tr>
                <td>
                    <label for="categories">Category</label>:
                </td>
                <td>
                    <select id="categories" name="categories">
                        <option value="personal">Personal</option><option value="work">Work</option><option value="family">Family</option><option value="holiday">Holiday</option>
                    </select>
                    <label for="access">Access</label>:
                    <select id="access" name="access">
                        <option value="public">Public</option><option value="private">Private</option>
                    </select>
                </td>
            </tr>    -->
                            <tr>
                                <td colspan="2">
                                    <div class="ui-widget-content ui-helper-clearfix ui-line-height"
                                        style="height:0px;border:none;border-bottom:1px solid #cdcdcd;margin-bottom:10px;margin-top:10px;_margin-top:0px;"></div>
                                </td>
                            </tr>
                            <tr style="display:none">
                                <td>&nbsp;</td>
                                <td><input type="checkbox"
                                    class="ui-widget-content ui-corner-all" id="all_day"
                                    name="all_day" /> <label for="all_day"
                                    style="padding-bottom: 3px;"> 整天 </label></td>
                            </tr>
                            <tr>
                                <td><label style="width:60px;display:inline-block;" for="start">预计时间:</label></td>
                                <td><input
                                    class="ui-widget-content ui-corner-all ui-input " id="start"
                                    name="start" size="12" /> <input
                                    class="ui-widget-content ui-corner-all ui-input" id="starttime"
                                    name="starttime" size="8" /></td>
                            </tr>
                            <tr style="display:none">
                                <td><label style="width:60px;display:inline-block;" for="end">结束时间:</label></td>
                                <td><input
                                    class="ui-widget-content ui-corner-all ui-input " id="end"
                                    name="end" size="12" /> <input
                                    class="ui-widget-content ui-corner-all ui-input" id="endtime"
                                    name="endtime" size="8" /></td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <div class="ui-widget-content ui-helper-clearfix ui-line-height"
                                        style="height:0px;border:none;border-bottom:1px solid #cdcdcd;margin-bottom:10px;margin-top:10px;_margin-top:0px;"></div>
                                </td>
                            </tr>

                            <tr>
                                <td style="vertical-align: top;width:60px;display:inline-block;" ><label for="description">描述:</label>
                                </td>
                                <td><textarea class="ui-widget-content ui-corner-all"
                                        id="description" name="description" rows="10" cols="50"></textarea>
                                </td>
                            </tr>
                            <!--<tr>
                <td colspan="2">
                    <div class="ui-widget-content ui-helper-clearfix" style="margin: 0.6em .0em;"></div>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="user_id">Calendar</label>:
                </td>
                <td>
                    <select id="user_id" name="user_id">
                        <option value="1">Calender User 1</option><option value="2">Calendar user 2</option><option value="3">Calendar user 3</option>
                    </select>
                </td>
            </tr>-->
                        </tbody>
                    </table>
                </form>
            </div>

        </div>
        <div id="progresspar"
            style="width: 150px; height: 30px; z-index: 1002; font-size: 13px; position: relative; top: -468.5px; left: 576.5px; display: none;"
            class="ui-widget-content">
            <div id="progressbar"
                style="width: 130px; height: 20px; margin-top: 5px; margin-left: 5px;">
                Loading...</div>
        </div>
        <div id="ui-datepicker-div"
            class="ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all"></div>
        <script type="text/javascript">
            jQuery(document)
                    .ready(
                            function() {
                                jQuery.datepicker
                                        .setDefaults({
                                            "closeText" : "Done",
                                            "prevText" : "Prev",
                                            "nextText" : "Next",
                                            "currentText" : "Today",
                                            "showMonthAfterYear" : false,
                                            "yearSuffix" : "",
                                            "dayNamesMin" : [ "七", "一", "二",
                                                    "三", "四", "五", "六" ],
                                            "weekHeader" : "Wk",
                                            "dateFormat" : "yy-mm-dd",
                                            "monthNames" : [ "一月",
                                                    "二月", "三月",
                                                    "四月", "五月", "六月",
                                                    "七月", "八月",
                                                    "九月", "十月",
                                                    "十一月", "十二月" ],
                                            "monthNamesShort" : [ "一月", "二月",
                                                    "三月", "四月", "五月", "六月",
                                                    "七月", "八月", "九月", "十月",
                                                    "十一月", "十二月" ],
                                            "dayNames" : [ "Sunday", "Monday",
                                                    "Tuesday", "Wednesday",
                                                    "Thursday", "Friday",
                                                    "Saturday" ],
                                            "dayNamesShort" : [ "Sun", "Mon",
                                                    "Tue", "Wed", "Thu", "Fri",
                                                    "Sat" ],
                                            "firstDay" : 1,
                                            "isRTL" : false
                                        });
                                jQuery.timepicker.setDefaults({
                                    "timeOnlyTitle" : "Choose Time",
                                    "timeText" : "Time",
                                    "hourText" : "Hour",
                                    "minuteText" : "Minute",
                                    "secondText" : "Second",
                                    "currentText" : "Now",
                                    "closeText" : "Done",
                                    "ampm" : false,
                                    "stepMinute" : 30
                                });
                                jQuery.jqschedule.locales = {
                                    "dateFormat" : "yyyy-MM-dd",
                                    "bSave" : "保存",
                                    "bAdd" : "添加",
                                    "bDelete" : "删除",
                                    "bCancel" : "取消",
                                    "bChange" : "Change",
                                    "bClose" : "关闭",
                                    "bSearch" : "Find",
                                    "editCaption" : "编辑提醒",
                                    "addCaption" : "新增自定义提醒",
                                    "userCaption" : "Change Calendar",
                                    "searchCaption" : "Advanced Search"
                                };
                                jQuery.jqschedule.calendarid = '#calendar';
                                jQuery.jqschedule.searchOpers = [ "equal",
                                        "not equal", "less", "less or equal",
                                        "greater", "greater or equal",
                                        "begins with", "does not begin with",
                                        "in", "not in", "ends with",
                                        "does not end with", "contains",
                                        "does not contain", "is null",
                                        "is not null" ];
                                jQuery.jqschedule.eventid = '#event';
                                jQuery.jqschedule.calendarurl = '/ecs/calendar/eventcal.do';
                                jQuery('#starttime,#endtime')
                                        .calendricalTimeRange({
                                            "minutes" : "mins",
                                            "onehour" : "1 hr",
                                            "hours" : "hrs",
                                            "isoTime" : true,
                                            "timeInterval" : 30
                                        });
                                jQuery('#calendar')
                                        .fullCalendar(
                                                {
                                                    "isRTL" : false,
                                                    "firstDay" : 1,
                                                    "monthNames" : [ "1月",
                                                            "2月", "3月", "4月",
                                                            "5月", "6月", "7月",
                                                            "8月", "9月", "10月",
                                                            "11月", "12月" ],
                                                    "monthNamesShort" : [ "1月",
                                                            "2月", "3月", "4月",
                                                            "5月", "6月", "7月",
                                                            "8月", "9月", "10月",
                                                            "11月", "12月" ],
                                                    "dayNames" : [ "星期天",
                                                            "星期一", "星期二",
                                                            "星期三", "星期四",
                                                            "星期五", "星期六" ],
                                                    "dayNamesShort" : [ "星期天",
                                                            "星期一", "星期二",
                                                            "星期三", "星期四",
                                                            "星期五", "星期六" ],
                                                    "buttonText" : {
                                                        "prev" : "&nbsp;&#9668;&nbsp;",
                                                        "next" : "&nbsp;&#9658;&nbsp;",
                                                        "prevYear" : "&nbsp;&lt;&lt;&nbsp;",
                                                        "nextYear" : "&nbsp;&gt;&gt;&nbsp;",
                                                        "today" : "today",
                                                        "month" : "month",
                                                        "week" : "week",
                                                        "day" : "day",
                                                        "year" : "year",
                                                        "search" : "search"
                                                    },
                                                    "allDayText" : "整天",
                                                    "axisFormat" : "h(:mm)tt",
                                                    "timeFormat" : {
                                                        "agenda" : "h:mm{ - h:mm}"
                                                    },
                                                    "editable" : true,
                                                    "defaultView" : "agendaWeek",
                                                    "slotMinutes" : 30,
                                                    "selectable" : true,
                                                    "theme" : true,
                                                    "header" : {
                                                        "left" : "prev,next today",
                                                        "center" : "title",
                                                        "right" : "agendaDay,agendaWeek,month,year"
                                                    },
                                                    "eventClick" : jQuery.jqschedule.eventEditDialog,
                                                    "dayClick" : jQuery.jqschedule.eventAddDialog,
                                                    "reportDayClick" : jQuery.jqschedule.openDaySearchDlg,
                                                    "eventDrop" : jQuery.jqschedule.eventDrop,
                                                    "eventResize" : jQuery.jqschedule.eventResize,
                                                    "eventRender" : jQuery.jqschedule.eventRender,
                                                    "events" : jQuery.jqschedule.eventsFunc,
                                                    "loading" : jQuery.jqschedule.eventLoading
                                                });
                                jQuery.jqschedule.processScript();
                                jQuery('#progresspar').position({
                                    of : '.calwrapper'
                                });
                                loading_txt();
                                getwidth();
                                $("#week_schedule").click();
                            });
        </script>
    </div>
</body>
</html>

