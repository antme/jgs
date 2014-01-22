var swfu,swfu1,swfu2,swf3;
            window.onload = function () {
                swfu = new SWFUpload({
                    upload_url: "/ecs/archive/upload.do",
                    post_params: {"archiveUploadKey" : "first"},
                    
                    // File Upload Settings
                    file_size_limit : "100 MB",  // 1000MB
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
                    button_image_url : "/resources/images/file_btn.png",
                    button_placeholder_id : "spanButtonPlaceholder",
                    button_width: 134,
                    button_height: 29,
                    button_text : '',
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
                	upload_url: "/ecs/archive/upload.do",
                    post_params: {"archiveUploadKey" : "firstAttach"},
                    
                    // File Upload Settings
                    file_size_limit : "100 MB",  // 1000MB
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
                    button_image_url : "/resources/images/file_btn.png",
                    button_placeholder_id : "archiveattachment",
                    button_width: 134,
                    button_height: 29,
                    button_text : '',
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