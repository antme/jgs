var swfu,swfu1,swfu2,swf3;
            window.onload = function () {
                swfu = new SWFUpload({
                    upload_url: "<%=uploadUrl.toString()%>",
                    post_params: {"name" : "huliang"},
                    
                    // File Upload Settings
                    file_size_limit : "10 MB",  // 1000MB
                    file_types : "*.*",
                    file_types_description : "所有文件",
                    file_upload_limit : "1",
                    file_queue_limit : "1",
                                    
                    file_queue_error_handler : fileQueueError,
                    file_dialog_complete_handler : fileDialogComplete,//选择好文件后提交
                    file_queued_handler : fileQueued,
                    upload_progress_handler : uploadProgress,
                    upload_error_handler : uploadError,
                    upload_success_handler : uploadSuccess,
                    upload_complete_handler : uploadComplete,
    
                    // Button Settings
                    button_image_url : "/resources/images/SmallSpyGlassWithTransperancy_17x18.png",
                    button_placeholder_id : "spanButtonPlaceholder",
                    button_width: 180,
                    button_height: 18,
                    button_text : '<span class="button">选择文件 <span class="buttonSmall">(10 MB Max)</span></span>',
                    button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
                    button_text_top_padding: 0,
                    button_text_left_padding: 18,
                    button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
                    button_cursor: SWFUpload.CURSOR.HAND,
                    
                    // Flash Settings
                    flash_url : "/resources/js/upload/swfupload.swf",
    
                    custom_settings : {
                        upload_target : "divFileProgressContainer"
                    },
                    // Debug Settings
                    debug: false  //是否显示调试窗口
                });
                swfu1 = new SWFUpload({
                    upload_url: "<%=uploadUrl.toString()%>",
                    post_params: {"name" : "huliang"},
                    
                    // File Upload Settings
                    file_size_limit : "10 MB",  // 1000MB
                    file_types : "*.*",
                    file_types_description : "所有文件",
                    file_upload_limit : "0",
                                    
                    file_queue_error_handler : fileQueueError1,
                    file_dialog_complete_handler : fileDialogComplete1,//选择好文件后提交
                    file_queued_handler : fileQueued1,
                    upload_progress_handler : uploadProgress1,
                    upload_error_handler : uploadError1,
                    upload_success_handler : uploadSuccess1,
                    upload_complete_handler : uploadComplete1,
    
                    // Button Settings
                    button_image_url : "/resources/images/SmallSpyGlassWithTransperancy_17x18.png",
                    button_placeholder_id : "archiveattachment",
                    button_width: 180,
                    button_height: 18,
                    button_text : '<span class="button">选择文件 <span class="buttonSmall">(10 MB Max)</span></span>',
                    button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
                    button_text_top_padding: 0,
                    button_text_left_padding: 18,
                    button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
                    button_cursor: SWFUpload.CURSOR.HAND,
                    
                    // Flash Settings
                    flash_url : "/resources/js/upload/swfupload.swf",
    
                    custom_settings : {
                        upload_target : "divFileProgressContainer1",
                        cancelButtonId: "btnCancel1"
                    },
                    // Debug Settings
                    debug: false  //是否显示调试窗口
                });
                swfu2 = new SWFUpload({
                    upload_url: "<%=uploadUrl.toString()%>",
                    post_params: {"name" : "huliang"},
                    
                    // File Upload Settings
                    file_size_limit : "10 MB",  // 1000MB
                    file_types : "*.*",
                    file_types_description : "所有文件",
                    file_upload_limit : "1",
                                    
                    file_queue_error_handler : fileQueueError2,
                    file_dialog_complete_handler : fileDialogComplete2,//选择好文件后提交
                    file_queued_handler : fileQueued2,
                    upload_progress_handler : uploadProgress2,
                    upload_error_handler : uploadError2,
                    upload_success_handler : uploadSuccess2,
                    upload_complete_handler : uploadComplete2,
    
                    // Button Settings
                    button_image_url : "/resources/images/SmallSpyGlassWithTransperancy_17x18.png",
                    button_placeholder_id : "archiveattachment2",
                    button_width: 180,
                    button_height: 18,
                    button_text : '<span class="button">选择文件 <span class="buttonSmall">(10 MB Max)</span></span>',
                    button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
                    button_text_top_padding: 0,
                    button_text_left_padding: 18,
                    button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
                    button_cursor: SWFUpload.CURSOR.HAND,
                    
                    // Flash Settings
                    flash_url : "/resources/js/upload/swfupload.swf",
    
                    custom_settings : {
                        upload_target : "divFileProgressContainer2",
                        cancelButtonId: "btnCancel2"
                    },
                    // Debug Settings
                    debug: false  //是否显示调试窗口
                });
                swfu3 = new SWFUpload({
                    upload_url: "<%=uploadUrl.toString()%>",
                    post_params: {"name" : "huliang"},
                    
                    // File Upload Settings
                    file_size_limit : "10 MB",  // 1000MB
                    file_types : "*.*",
                    file_types_description : "所有文件",
                    file_upload_limit : "0",
                                    
                    file_queue_error_handler : fileQueueError3,
                    file_dialog_complete_handler : fileDialogComplete3,//选择好文件后提交
                    file_queued_handler : fileQueued3,
                    upload_progress_handler : uploadProgress3,
                    upload_error_handler : uploadError3,
                    upload_success_handler : uploadSuccess3,
                    upload_complete_handler : uploadComplete3,
    
                    // Button Settings
                    button_image_url : "/resources/images/SmallSpyGlassWithTransperancy_17x18.png",
                    button_placeholder_id : "archiveattachment3",
                    button_width: 180,
                    button_height: 18,
                    button_text : '<span class="button">选择文件 <span class="buttonSmall">(10 MB Max)</span></span>',
                    button_text_style : '.button { font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
                    button_text_top_padding: 0,
                    button_text_left_padding: 18,
                    button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
                    button_cursor: SWFUpload.CURSOR.HAND,
                    
                    // Flash Settings
                    flash_url : "/resources/js/upload/swfupload.swf",
    
                    custom_settings : {
                        upload_target : "divFileProgressContainer3",
                        cancelButtonId: "btnCancel3"
                    },
                    // Debug Settings
                    debug: false  //是否显示调试窗口
                });
            };
            function startUploadFile(){
                swfu.startUpload();
            }
            function startUploadFile1(){
                swfu1.startUpload();
            }
            function startUploadFile2(){
                swfu2.startUpload();
            }
            function startUploadFile3(){
                swfu3.startUpload();
            }