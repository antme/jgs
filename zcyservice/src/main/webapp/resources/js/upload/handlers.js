var mainFileAttach="";
var secondFileAttach="";
var count=1;
function fileQueueError(file, errorCode, message) {
	try {
		var imageName = "<font color='red'>文件上传错误</font>";
		var errorName = "";
		if (errorCode === SWFUpload.errorCode_QUEUE_LIMIT_EXCEEDED) {
			errorName = "You have attempted to queue too many files.";
		}

		if (errorName !== "") {
			alert(errorName);
			return;
		}
		
		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			imageName = "<font color='red'>文件大小为0</font>";
			break;
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			imageName = "<font color='red'>文件大小超过限制</font>";
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
		default:
			alert("只能上传一个附件！");
			break;
		}
		addReadyFileInfo(file.id,file.name,imageName,"无法上传");

	} catch (ex) {
		this.debug(ex);
	}
}

function fileQueueError1(file, errorCode, message) {
	try {
		var imageName = "<font color='red'>文件上传错误</font>";
		var errorName = "";
		if (errorCode === SWFUpload.errorCode_QUEUE_LIMIT_EXCEEDED) {
			errorName = "You have attempted to queue too many files.";
		}

		if (errorName !== "") {
			alert(errorName);
			return;
		}
		
		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			imageName = "<font color='red'>文件大小为0</font>";
			break;
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			imageName = "<font color='red'>文件大小超过限制</font>";
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
		default:
			alert(message);
			break;
		}
		addReadyFileInfo1(file.id,file.name,imageName,"无法上传");

	} catch (ex) {
		this.debug(ex);
	}
}
function fileQueueError2(file, errorCode, message) {
	try {
		var imageName = "<font color='red'>文件上传错误</font>";
		var errorName = "";
		if (errorCode === SWFUpload.errorCode_QUEUE_LIMIT_EXCEEDED) {
			errorName = "You have attempted to queue too many files.";
		}

		if (errorName !== "") {
			alert(errorName);
			return;
		}
		
		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			imageName = "<font color='red'>文件大小为0</font>";
			break;
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			imageName = "<font color='red'>文件大小超过限制</font>";
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
		default:
			alert("只能上传一个附件！");
			break;
		}
		addReadyFileInfo2(file.id,file.name,imageName,"无法上传");

	} catch (ex) {
		this.debug(ex);
	}
}
function fileQueueError3(file, errorCode, message) {
	try {
		var imageName = "<font color='red'>文件上传错误</font>";
		var errorName = "";
		if (errorCode === SWFUpload.errorCode_QUEUE_LIMIT_EXCEEDED) {
			errorName = "You have attempted to queue too many files.";
		}

		if (errorName !== "") {
			alert(errorName);
			return;
		}
		
		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			imageName = "<font color='red'>文件大小为0</font>";
			break;
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			imageName = "<font color='red'>文件大小超过限制</font>";
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
		default:
			alert(message);
			break;
		}
		addReadyFileInfo3(file.id,file.name,imageName,"无法上传");

	} catch (ex) {
		this.debug(ex);
	}
}
/**
 * 当文件选择对话框关闭消失时，如果选择的文件成功加入上传队列，
 * 那么针对每个成功加入的文件都会触发一次该事件（N个文件成功加入队列，就触发N次此事件）。
 * @param {} file
 * id : string,			    // SWFUpload控制的文件的id,通过指定该id可启动此文件的上传、退出上传等
 * index : number,			// 文件在选定文件队列（包括出错、退出、排队的文件）中的索引，getFile可使用此索引
 * name : string,			// 文件名，不包括文件的路径。
 * size : number,			// 文件字节数
 * type : string,			// 客户端操作系统设置的文件类型
 * creationdate : Date,		// 文件的创建时间
 * modificationdate : Date,	// 文件的最后修改时间
 * filestatus : number		// 文件的当前状态，对应的状态代码可查看SWFUpload.FILE_STATUS }
 */
function fileQueued(file){
	addReadyFileInfo(file.id,file.name,"");
}
function fileQueued1(file){
	addReadyFileInfo1(file.id,file.name,"");
}
function fileQueued2(file){
	addReadyFileInfo2(file.id,file.name,"");
}
function fileQueued3(file){
	addReadyFileInfo3(file.id,file.name,"");
}

function fileDialogComplete(numFilesSelected, numFilesQueued) {
	try {
		if (numFilesQueued > 0) {
			document.getElementById('btnCancel').disabled = "";
			//this.startUpload();
		}
	} catch (ex) {
		this.debug(ex);
	}
}
function fileDialogComplete1(numFilesSelected, numFilesQueued) {
	try {
		if (numFilesQueued > 0) {
			document.getElementById('btnCancel1').disabled = "";
			//this.startUpload();
		}
	} catch (ex) {
		this.debug(ex);
	}
}
function fileDialogComplete2(numFilesSelected, numFilesQueued) {
	try {
		if (numFilesQueued > 0) {
			document.getElementById('btnCancel2').disabled = "";
			//this.startUpload();
		}
	} catch (ex) {
		this.debug(ex);
	}
}
function fileDialogComplete3(numFilesSelected, numFilesQueued) {
	try {
		if (numFilesQueued > 0) {
			document.getElementById('btnCancel3').disabled = "";
			//this.startUpload();
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadProgress(file, bytesLoaded) {
	try {
		var percent = Math.ceil((bytesLoaded / file.size) * 100);
		var progress = new FileProgress(file,  this.customSettings.upload_target);
		progress.setProgress(percent);
		if (percent === 100) {
			progress.setStatus("");//正在创建缩略图...
			progress.toggleCancel(false, this);
		} else {
			progress.setStatus("正在上传...");
			progress.toggleCancel(true, this);
		}
	} catch (ex) {
		this.debug(ex);
	}
}
function uploadProgress1(file, bytesLoaded) {
	try {
		var percent = Math.ceil((bytesLoaded / file.size) * 100);
		var progress = new FileProgress1(file,  this.customSettings.upload_target);
		progress.setProgress(percent);
		if (percent === 100) {
			progress.setStatus("");//正在创建缩略图...
			progress.toggleCancel(false, this);
		} else {
			progress.setStatus("正在上传...");
			progress.toggleCancel(true, this);
		}
	} catch (ex) {
		this.debug(ex);
	}
}
function uploadProgress2(file, bytesLoaded) {
	try {
		var percent = Math.ceil((bytesLoaded / file.size) * 100);

		var progress = new FileProgress2(file,  this.customSettings.upload_target);
		progress.setProgress(percent);
		if (percent === 100) {
			progress.setStatus("");//正在创建缩略图...
			progress.toggleCancel(false, this);
		} else {
			progress.setStatus("正在上传...");
			progress.toggleCancel(true, this);
		}
	} catch (ex) {
		this.debug(ex);
	}
}
function uploadProgress3(file, bytesLoaded) {

	try {
		var percent = Math.ceil((bytesLoaded / file.size) * 100);

		var progress = new FileProgress3(file,  this.customSettings.upload_target);
		progress.setProgress(percent);
		if (percent === 100) {
			progress.setStatus("");//正在创建缩略图...
			progress.toggleCancel(false, this);
		} else {
			progress.setStatus("正在上传...");
			progress.toggleCancel(true, this);
		}
	} catch (ex) {
		this.debug(ex);
	}
}


function uploadSuccess(file, serverData) {
	try {
		var progress = new FileProgress(file, this.customSettings.upload_target);
		addFileInfo(file.id,"文件上传完成");
		var obj = serverData;
        obj = JSON.parse(obj);
        $("#addarchiveForm").form("load",obj.data);
        $("#mainFile").val(obj.data.filePath);
	} catch (ex) {
		this.debug(ex);
	}
}
function uploadSuccess1(file, serverData) {
	try {
		var progress = new FileProgress1(file,  this.customSettings.upload_target);
		addFileInfo1(file.id,"文件上传完成");
		var obj = serverData;
        obj = JSON.parse(obj);
        mainFileAttach+=obj.data.filePath+","
        $("#mainFileAttach").val(mainFileAttach);
	} catch (ex) {
		this.debug(ex);
	}
}
function uploadSuccess2(file, serverData) {
	try {
		var progress = new FileProgress2(file,  this.customSettings.upload_target);
		addFileInfo2(file.id,"文件上传完成");
		var obj = serverData;
        obj = JSON.parse(obj);
        $("#secondFile").val(obj.data.filePath);
	} catch (ex) {
		this.debug(ex);
	}
}
function uploadSuccess3(file, serverData) {
	try {
		var progress = new FileProgress3(file,  this.customSettings.upload_target);
		addFileInfo3(file.id,"文件上传完成");
		var obj = serverData;
        obj = JSON.parse(obj);
        secondFileAttach+=obj.data.filePath+","
        $("#secondFileAttach").val(secondFileAttach);
	} catch (ex) {
		this.debug(ex);
	}
}


function addFileInfo(fileId,message){
	var row = document.getElementById(fileId);
	row.cells[2].innerHTML = "<font color='green'>"+message+"</font>";
}
function addFileInfo1(fileId,message){
	var row = document.getElementById(fileId);
	row.cells[2].innerHTML = "<font color='green'>"+message+"</font>";
}
function addFileInfo2(fileId,message){
	var row = document.getElementById(fileId);
	row.cells[2].innerHTML = "<font color='green'>"+message+"</font>";
}
function addFileInfo3(fileId,message){
	var row = document.getElementById(fileId);
	row.cells[2].innerHTML = "<font color='green'>"+message+"</font>";
}


function addReadyFileInfo(fileid,fileName,message,status){
	//用表格显示
	var infoTable = document.getElementById("infoTable");
	var row = infoTable.insertRow();
	row.id = fileid;
	var col1 = row.insertCell();
	var col2 = row.insertCell();
	var col3 = row.insertCell();
	var col4 = row.insertCell();
	col4.align = "right";
	col1.innerHTML = message+"";
	col2.innerHTML = fileName;
	if(status!=null&&status!=""){
		col3.innerHTML="<font color='red'>"+status+"</font>";
	}else{
		col3.innerHTML="";
	}
	col4.innerHTML = "<a href='javascript:deleteFile(\""+fileid+"\")'>删除</a>";
	col1.style.width="150";
	col2.style.width="250";
	col3.style.width="80";
	col4.style.width="50";
}
function addReadyFileInfo1(fileid,fileName,message,status){
	//用表格显示
	var infoTable = document.getElementById("infoTable1");
	var row = infoTable.insertRow();
	row.id = fileid;
	var col1 = row.insertCell();
	var col2 = row.insertCell();
	var col3 = row.insertCell();
	var col4 = row.insertCell();
	col4.align = "right";
	col1.innerHTML = message+"";
	col2.innerHTML = fileName;
	if(status!=null&&status!=""){
		col3.innerHTML="<font color='red'>"+status+"</font>";
	}else{
		col3.innerHTML="";
	}
	col4.innerHTML = "<a href='javascript:deleteFile1(\""+fileid+"\")'>删除</a>";
	col1.style.width="150";
	col2.style.width="250";
	col3.style.width="80";
	col4.style.width="50";
}
function addReadyFileInfo2(fileid,fileName,message,status){
	//用表格显示
	var infoTable = document.getElementById("infoTable2");
	var row = infoTable.insertRow();
	row.id = fileid;
	var col1 = row.insertCell();
	var col2 = row.insertCell();
	var col3 = row.insertCell();
	var col4 = row.insertCell();
	col4.align = "right";
	col1.innerHTML = message+" : ";
	col2.innerHTML = fileName;
	if(status!=null&&status!=""){
		col3.innerHTML="<font color='red'>"+status+"</font>";
	}else{
		col3.innerHTML="";
	}
	col4.innerHTML = "<a href='javascript:deleteFile2(\""+fileid+"\")'>删除</a>";
	col1.style.width="150";
	col2.style.width="250";
	col3.style.width="80";
	col4.style.width="50";
}
function addReadyFileInfo3(fileid,fileName,message,status){
	//用表格显示
	var infoTable = document.getElementById("infoTable3");
	var row = infoTable.insertRow();
	row.id = fileid;
	var col1 = row.insertCell();
	var col2 = row.insertCell();
	var col3 = row.insertCell();
	var col4 = row.insertCell();
	col4.align = "right";
	col1.innerHTML = message+" : ";
	col2.innerHTML = fileName;
	if(status!=null&&status!=""){
		col3.innerHTML="<font color='red'>"+status+"</font>";
	}else{
		col3.innerHTML="";
	}
	col4.innerHTML = "<a href='javascript:deleteFile3(\""+fileid+"\")'>删除</a>";
	col1.style.width="150";
	col2.style.width="250";
	col3.style.width="80";
	col4.style.width="50";
}


function cancelUpload(){
	var infoTable = document.getElementById("infoTable");
	var rows = infoTable.rows;
	var ids = new Array();
	var row;
	if(rows==null){
		return false;
	}
	for(var i=0;i<rows.length;i++){
		ids[i] = rows[i].id;
	}	
	for(var i=0;i<ids.length;i++){
		deleteFile(ids[i]);
	}	
}
function cancelUpload1(){
	var infoTable = document.getElementById("infoTable1");
	var rows = infoTable.rows;
	var ids = new Array();
	var row;
	if(rows==null){
		return false;
	}
	for(var i=0;i<rows.length;i++){
		ids[i] = rows[i].id;
	}	
	for(var i=0;i<ids.length;i++){
		deleteFile1(ids[i]);
	}	
}
function cancelUpload2(){
	var infoTable = document.getElementById("infoTable2");
	var rows = infoTable.rows;
	var ids = new Array();
	var row;
	if(rows==null){
		return false;
	}
	for(var i=0;i<rows.length;i++){
		ids[i] = rows[i].id;
	}	
	for(var i=0;i<ids.length;i++){
		deleteFile2(ids[i]);
	}	
}
function cancelUpload3(){
	var infoTable = document.getElementById("infoTable3");
	var rows = infoTable.rows;
	var ids = new Array();
	var row;
	if(rows==null){
		return false;
	}
	for(var i=0;i<rows.length;i++){
		ids[i] = rows[i].id;
	}	
	for(var i=0;i<ids.length;i++){
		deleteFile3(ids[i]);
	}	
}

function deleteFile(fileId){
	//用表格显示
	var infoTable = document.getElementById("infoTable");
	var row = document.getElementById(fileId);
	infoTable.deleteRow(row.rowIndex);
	swfu.cancelUpload(fileId,false);
	var stats = swfu.getStats();
	count=stats.successful_uploads+1;
	swfu.setFileQueueLimit(count);
	swfu.setFileUploadLimit(count);
}
function deleteFile1(fileId){
	//用表格显示
	var infoTable = document.getElementById("infoTable1");
	var row = document.getElementById(fileId);
	infoTable.deleteRow(row.rowIndex);
	swfu.cancelUpload(fileId,false);

}
function deleteFile2(fileId){
	//用表格显示
	var infoTable = document.getElementById("infoTable2");
	var row = document.getElementById(fileId);
	infoTable.deleteRow(row.rowIndex);
	swfu.cancelUpload(fileId,false);
}
function deleteFile3(fileId){
	//用表格显示
	var infoTable = document.getElementById("infoTable3");
	var row = document.getElementById(fileId);
	infoTable.deleteRow(row.rowIndex);
	swfu.cancelUpload(fileId,false);
}

function uploadComplete(file) {
	try {
		/*  I want the next upload to continue automatically so I'll call startUpload here */
		if (this.getStats().files_queued > 0) {
			this.startUpload();
		} else {
			var progress = new FileProgress(file,  this.customSettings.upload_target);
			progress.setComplete();
			progress.setStatus("<font color='red'>所有文件上传完毕!</b></font>");
			progress.toggleCancel(false);
		}
	} catch (ex) {
		this.debug(ex);
	}
}
function uploadComplete1(file) {
	try {
		/*  I want the next upload to continue automatically so I'll call startUpload here */
		if (this.getStats().files_queued > 0) {
			this.startUpload();
		} else {
			var progress = new FileProgress1(file,  "divFileProgressContainer1");
			progress.setComplete();
			progress.setStatus("<font color='red'>所有文件上传完毕!</b></font>");
			progress.toggleCancel(false);
		}
	} catch (ex) {
		this.debug(ex);
	}
}
function uploadComplete2(file) {
	try {
		/*  I want the next upload to continue automatically so I'll call startUpload here */
		if (this.getStats().files_queued > 0) {
			this.startUpload();
		} else {
			var progress = new FileProgress2(file,  "divFileProgressContainer2");
			progress.setComplete();
			progress.setStatus("<font color='red'>所有文件上传完毕!</b></font>");
			progress.toggleCancel(false);
		}
	} catch (ex) {
		this.debug(ex);
	}
}
function uploadComplete3(file) {
	try {
		/*  I want the next upload to continue automatically so I'll call startUpload here */
		if (this.getStats().files_queued > 0) {
			this.startUpload();
		} else {
			var progress = new FileProgress3(file,  this.customSettings.upload_target);
			progress.setComplete();
			progress.setStatus("<font color='red'>所有文件上传完毕!</b></font>");
			progress.toggleCancel(false);
		}
	} catch (ex) {
		this.debug(ex);
	}
}


function uploadError(file, errorCode, message) {
	var imageName =  "<font color='red'>文件上传出错!</font>";
	var progress;
	try {
		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			try {
				progress = new FileProgress(file,  this.customSettings.upload_target);
				progress.setCancelled();
				progress.setStatus("<font color='red'>取消上传!</font>");
				progress.toggleCancel(false);
			}
			catch (ex1) {
				this.debug(ex1);
			}
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			try {
				progress = new FileProgress(file,  this.customSettings.upload_target);
				progress.setCancelled();
				progress.setStatus("<font color='red'>停止上传!</font>");
				progress.toggleCancel(true);
			}
			catch (ex2) {
				this.debug(ex2);
			}
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			imageName = "<font color='red'>文件大小超过限制!</font>";
			break;
		default:
			alert(message);
			break;
		}
		addFileInfo(file.id,imageName);
	} catch (ex3) {
		this.debug(ex3);
	}

}
function uploadError1(file, errorCode, message) {
	var imageName =  "<font color='red'>文件上传出错!</font>";
	var progress;
	try {
		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			try {
				progress = new FileProgress1(file,  this.customSettings.upload_target);
				progress.setCancelled();
				progress.setStatus("<font color='red'>取消上传!</font>");
				progress.toggleCancel(false);
			}
			catch (ex1) {
				this.debug(ex1);
			}
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			try {
				progress = new FileProgress1(file,  this.customSettings.upload_target);
				progress.setCancelled();
				progress.setStatus("<font color='red'>停止上传!</font>");
				progress.toggleCancel(true);
			}
			catch (ex2) {
				this.debug(ex2);
			}
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			imageName = "<font color='red'>文件大小超过限制!</font>";
			break;
		default:
			alert(message);
			break;
		}
		addFileInfo1(file.id,imageName);
	} catch (ex3) {
		this.debug(ex3);
	}

}
function uploadError2(file, errorCode, message) {
	var imageName =  "<font color='red'>文件上传出错!</font>";
	var progress;
	try {
		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			try {
				progress = new FileProgress2(file,  this.customSettings.upload_target);
				progress.setCancelled();
				progress.setStatus("<font color='red'>取消上传!</font>");
				progress.toggleCancel(false);
			}
			catch (ex1) {
				this.debug(ex1);
			}
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			try {
				progress = new FileProgress2(file,  this.customSettings.upload_target);
				progress.setCancelled();
				progress.setStatus("<font color='red'>停止上传!</font>");
				progress.toggleCancel(true);
			}
			catch (ex2) {
				this.debug(ex2);
			}
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			imageName = "<font color='red'>文件大小超过限制!</font>";
			break;
		default:
			alert(message);
			break;
		}
		addFileInfo2(file.id,imageName);
	} catch (ex3) {
		this.debug(ex3);
	}

}
function uploadError3(file, errorCode, message) {
	var imageName =  "<font color='red'>文件上传出错!</font>";
	var progress;
	try {
		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			try {
				progress = new FileProgress3(file,  this.customSettings.upload_target);
				progress.setCancelled();
				progress.setStatus("<font color='red'>取消上传!</font>");
				progress.toggleCancel(false);
			}
			catch (ex1) {
				this.debug(ex1);
			}
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			try {
				progress = new FileProgress3(file,  this.customSettings.upload_target);
				progress.setCancelled();
				progress.setStatus("<font color='red'>停止上传!</font>");
				progress.toggleCancel(true);
			}
			catch (ex2) {
				this.debug(ex2);
			}
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			imageName = "<font color='red'>文件大小超过限制!</font>";
			break;
		default:
			alert(message);
			break;
		}
		addFileInfo3(file.id,imageName);
	} catch (ex3) {
		this.debug(ex3);
	}

}


function addImage(src) {
	var newImg = document.createElement("img");
	newImg.style.margin = "5px";

	document.getElementById("thumbnails").appendChild(newImg);
	if (newImg.filters) {
		try {
			newImg.filters.item("DXImageTransform.Microsoft.Alpha").opacity = 0;
		} catch (e) {
			// If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
			newImg.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + 0 + ')';
		}
	} else {
		newImg.style.opacity = 0;
	}

	newImg.onload = function () {
		fadeIn(newImg, 0);
	};
	newImg.src = src;
}
function addImage1(src) {
	var newImg = document.createElement("img");
	newImg.style.margin = "5px";

	document.getElementById("thumbnails1").appendChild(newImg);
	if (newImg.filters) {
		try {
			newImg.filters.item("DXImageTransform.Microsoft.Alpha").opacity = 0;
		} catch (e) {
			// If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
			newImg.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + 0 + ')';
		}
	} else {
		newImg.style.opacity = 0;
	}

	newImg.onload = function () {
		fadeIn(newImg, 0);
	};
	newImg.src = src;
}
function addImage2(src) {
	var newImg = document.createElement("img");
	newImg.style.margin = "5px";

	document.getElementById("thumbnails2").appendChild(newImg);
	if (newImg.filters) {
		try {
			newImg.filters.item("DXImageTransform.Microsoft.Alpha").opacity = 0;
		} catch (e) {
			// If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
			newImg.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + 0 + ')';
		}
	} else {
		newImg.style.opacity = 0;
	}

	newImg.onload = function () {
		fadeIn(newImg, 0);
	};
	newImg.src = src;
}
function addImage3(src) {
	var newImg = document.createElement("img");
	newImg.style.margin = "5px";

	document.getElementById("thumbnails3").appendChild(newImg);
	if (newImg.filters) {
		try {
			newImg.filters.item("DXImageTransform.Microsoft.Alpha").opacity = 0;
		} catch (e) {
			// If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
			newImg.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + 0 + ')';
		}
	} else {
		newImg.style.opacity = 0;
	}

	newImg.onload = function () {
		fadeIn(newImg, 0);
	};
	newImg.src = src;
}



function fadeIn(element, opacity) {
	var reduceOpacityBy = 5;
	var rate = 30;	// 15 fps


	if (opacity < 100) {
		opacity += reduceOpacityBy;
		if (opacity > 100) {
			opacity = 100;
		}

		if (element.filters) {
			try {
				element.filters.item("DXImageTransform.Microsoft.Alpha").opacity = opacity;
			} catch (e) {
				// If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
				element.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + opacity + ')';
			}
		} else {
			element.style.opacity = opacity / 100;
		}
	}

	if (opacity < 100) {
		setTimeout(function () {
			fadeIn(element, opacity);
		}, rate);
	}
}
function fadeIn1(element, opacity) {
	var reduceOpacityBy = 5;
	var rate = 30;	// 15 fps


	if (opacity < 100) {
		opacity += reduceOpacityBy;
		if (opacity > 100) {
			opacity = 100;
		}

		if (element.filters) {
			try {
				element.filters.item("DXImageTransform.Microsoft.Alpha").opacity = opacity;
			} catch (e) {
				// If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
				element.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + opacity + ')';
			}
		} else {
			element.style.opacity = opacity / 100;
		}
	}

	if (opacity < 100) {
		setTimeout(function () {
			fadeIn1(element, opacity);
		}, rate);
	}
}
function fadeIn2(element, opacity) {
	var reduceOpacityBy = 5;
	var rate = 30;	// 15 fps


	if (opacity < 100) {
		opacity += reduceOpacityBy;
		if (opacity > 100) {
			opacity = 100;
		}

		if (element.filters) {
			try {
				element.filters.item("DXImageTransform.Microsoft.Alpha").opacity = opacity;
			} catch (e) {
				// If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
				element.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + opacity + ')';
			}
		} else {
			element.style.opacity = opacity / 100;
		}
	}

	if (opacity < 100) {
		setTimeout(function () {
			fadeIn2(element, opacity);
		}, rate);
	}
}
function fadeIn3(element, opacity) {
	var reduceOpacityBy = 5;
	var rate = 30;	// 15 fps


	if (opacity < 100) {
		opacity += reduceOpacityBy;
		if (opacity > 100) {
			opacity = 100;
		}

		if (element.filters) {
			try {
				element.filters.item("DXImageTransform.Microsoft.Alpha").opacity = opacity;
			} catch (e) {
				// If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
				element.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + opacity + ')';
			}
		} else {
			element.style.opacity = opacity / 100;
		}
	}

	if (opacity < 100) {
		setTimeout(function () {
			fadeIn3(element, opacity);
		}, rate);
	}
}
/* ******************************************
 *	FileProgress Object
 *	Control object for displaying file info
 * ****************************************** */

function FileProgress(file, targetID) {
	this.fileProgressID = "divFileProgress";
	this.fileProgressWrapper = document.getElementById(this.fileProgressID);
	if (!this.fileProgressWrapper) {
		this.fileProgressWrapper = document.createElement("div");
		this.fileProgressWrapper.className = "progressWrapper";
		this.fileProgressWrapper.id = this.fileProgressID;

		this.fileProgressElement = document.createElement("div");
		this.fileProgressElement.className = "progressContainer";

		var progressCancel = document.createElement("a");
		progressCancel.className = "progressCancel";
		progressCancel.href = "#";
		progressCancel.style.visibility = "hidden";
		progressCancel.appendChild(document.createTextNode(" "));

		var progressText = document.createElement("div");
		progressText.className = "progressName";
		progressText.appendChild(document.createTextNode("上传文件: "+file.name));

		var progressBar = document.createElement("div");
		progressBar.className = "progressBarInProgress";

		var progressStatus = document.createElement("div");
		progressStatus.className = "progressBarStatus";
		progressStatus.innerHTML = "&nbsp;";

		this.fileProgressElement.appendChild(progressCancel);
		this.fileProgressElement.appendChild(progressText);
		this.fileProgressElement.appendChild(progressStatus);
		this.fileProgressElement.appendChild(progressBar);

		this.fileProgressWrapper.appendChild(this.fileProgressElement);
		document.getElementById(targetID).style.height = "75px";
		document.getElementById(targetID).appendChild(this.fileProgressWrapper);
		fadeIn(this.fileProgressWrapper, 0);

	} else {
		this.fileProgressElement = this.fileProgressWrapper.firstChild;
		this.fileProgressElement.childNodes[1].firstChild.nodeValue = "上传文件: "+file.name;
	}

	this.height = this.fileProgressWrapper.offsetHeight;

}
function FileProgress1(file, targetID){
	this.fileProgressID = "divFileProgress1";
	this.fileProgressWrapper = document.getElementById(this.fileProgressID);
	if (!this.fileProgressWrapper) {
		this.fileProgressWrapper = document.createElement("div");
		this.fileProgressWrapper.className = "progressWrapper1";
		this.fileProgressWrapper.id = this.fileProgressID;

		this.fileProgressElement = document.createElement("div");
		this.fileProgressElement.className = "progressContainer1";

		var progressCancel = document.createElement("a");
		progressCancel.className = "progressCancel1";
		progressCancel.href = "#";
		progressCancel.style.visibility = "hidden";
		progressCancel.appendChild(document.createTextNode(" "));

		var progressText = document.createElement("div");
		progressText.className = "progressName1";
		progressText.appendChild(document.createTextNode("上传文件: "+file.name));

		var progressBar = document.createElement("div");
		progressBar.className = "progressBarInProgress1";

		var progressStatus = document.createElement("div");
		progressStatus.className = "progressBarStatus1";
		progressStatus.innerHTML = "&nbsp;";

		this.fileProgressElement.appendChild(progressCancel);
		this.fileProgressElement.appendChild(progressText);
		this.fileProgressElement.appendChild(progressStatus);
		this.fileProgressElement.appendChild(progressBar);

		this.fileProgressWrapper.appendChild(this.fileProgressElement);
		document.getElementById(targetID).style.height = "75px";
		document.getElementById(targetID).appendChild(this.fileProgressWrapper);
		fadeIn(this.fileProgressWrapper, 0);

	} else {
		this.fileProgressElement = this.fileProgressWrapper.firstChild;
		this.fileProgressElement.childNodes[1].firstChild.nodeValue = "上传文件: "+file.name;
	}

	this.height = this.fileProgressWrapper.offsetHeight;
}
function FileProgress2(file, targetID){
	this.fileProgressID = "divFileProgress2";
	this.fileProgressWrapper = document.getElementById(this.fileProgressID);
	if (!this.fileProgressWrapper) {
		this.fileProgressWrapper = document.createElement("div");
		this.fileProgressWrapper.className = "progressWrapper2";
		this.fileProgressWrapper.id = this.fileProgressID;

		this.fileProgressElement = document.createElement("div");
		this.fileProgressElement.className = "progressContainer2";

		var progressCancel = document.createElement("a");
		progressCancel.className = "progressCancel2";
		progressCancel.href = "#";
		progressCancel.style.visibility = "hidden";
		progressCancel.appendChild(document.createTextNode(" "));

		var progressText = document.createElement("div");
		progressText.className = "progressName2";
		progressText.appendChild(document.createTextNode("上传文件: "+file.name));

		var progressBar = document.createElement("div");
		progressBar.className = "progressBarInProgress2";

		var progressStatus = document.createElement("div");
		progressStatus.className = "progressBarStatus2";
		progressStatus.innerHTML = "&nbsp;";

		this.fileProgressElement.appendChild(progressCancel);
		this.fileProgressElement.appendChild(progressText);
		this.fileProgressElement.appendChild(progressStatus);
		this.fileProgressElement.appendChild(progressBar);

		this.fileProgressWrapper.appendChild(this.fileProgressElement);
		document.getElementById(targetID).style.height = "75px";
		document.getElementById(targetID).appendChild(this.fileProgressWrapper);
		fadeIn(this.fileProgressWrapper, 0);

	} else {
		this.fileProgressElement = this.fileProgressWrapper.firstChild;
		this.fileProgressElement.childNodes[1].firstChild.nodeValue = "上传文件: "+file.name;
	}

	this.height = this.fileProgressWrapper.offsetHeight;
}
function FileProgress3(file, targetID){
	this.fileProgressID = "divFileProgress3";
	this.fileProgressWrapper = document.getElementById(this.fileProgressID);
	if (!this.fileProgressWrapper) {
		this.fileProgressWrapper = document.createElement("div");
		this.fileProgressWrapper.className = "progressWrapper3";
		this.fileProgressWrapper.id = this.fileProgressID;

		this.fileProgressElement = document.createElement("div");
		this.fileProgressElement.className = "progressContainer3";

		var progressCancel = document.createElement("a");
		progressCancel.className = "progressCancel3";
		progressCancel.href = "#";
		progressCancel.style.visibility = "hidden";
		progressCancel.appendChild(document.createTextNode(" "));

		var progressText = document.createElement("div");
		progressText.className = "progressName3";
		progressText.appendChild(document.createTextNode("上传文件: "+file.name));

		var progressBar = document.createElement("div");
		progressBar.className = "progressBarInProgress3";

		var progressStatus = document.createElement("div");
		progressStatus.className = "progressBarStatus3";
		progressStatus.innerHTML = "&nbsp;";

		this.fileProgressElement.appendChild(progressCancel);
		this.fileProgressElement.appendChild(progressText);
		this.fileProgressElement.appendChild(progressStatus);
		this.fileProgressElement.appendChild(progressBar);

		this.fileProgressWrapper.appendChild(this.fileProgressElement);
		document.getElementById(targetID).style.height = "75px";
		document.getElementById(targetID).appendChild(this.fileProgressWrapper);
		fadeIn(this.fileProgressWrapper, 0);

	} else {
		this.fileProgressElement = this.fileProgressWrapper.firstChild;
		this.fileProgressElement.childNodes[1].firstChild.nodeValue = "上传文件: "+file.name;
	}

	this.height = this.fileProgressWrapper.offsetHeight;
}
FileProgress.prototype.setProgress = function (percentage) {
	this.fileProgressElement.className = "progressContainer green";
	this.fileProgressElement.childNodes[3].className = "progressBarInProgress";
	this.fileProgressElement.childNodes[3].style.width = percentage + "%";
};
FileProgress1.prototype.setProgress = function (percentage) {
	this.fileProgressElement.className = "progressContainer1 green";
	this.fileProgressElement.childNodes[3].className = "progressBarInProgress1";
	this.fileProgressElement.childNodes[3].style.width = percentage + "%";
};
FileProgress2.prototype.setProgress = function (percentage) {
	this.fileProgressElement.className = "progressContainer2 green";
	this.fileProgressElement.childNodes[3].className = "progressBarInProgress2";
	this.fileProgressElement.childNodes[3].style.width = percentage + "%";
};
FileProgress3.prototype.setProgress = function (percentage) {
	this.fileProgressElement.className = "progressContainer3 green";
	this.fileProgressElement.childNodes[3].className = "progressBarInProgress3";
	this.fileProgressElement.childNodes[3].style.width = percentage + "%";
};

FileProgress.prototype.setComplete = function () {
	this.fileProgressElement.className = "progressContainer blue";
	this.fileProgressElement.childNodes[3].className = "progressBarComplete";
	this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress1.prototype.setComplete = function () {
	this.fileProgressElement.className = "progressContainer1 blue";
	this.fileProgressElement.childNodes[3].className = "progressBarComplete1";
	this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress2.prototype.setComplete = function () {
	this.fileProgressElement.className = "progressContainer2 blue";
	this.fileProgressElement.childNodes[3].className = "progressBarComplete2";
	this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress3.prototype.setComplete = function () {
	this.fileProgressElement.className = "progressContainer3 blue";
	this.fileProgressElement.childNodes[3].className = "progressBarComplete3";
	this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress.prototype.setError = function () {
	this.fileProgressElement.className = "progressContainer red";
	this.fileProgressElement.childNodes[3].className = "progressBarError";
	this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress1.prototype.setError = function () {
	this.fileProgressElement.className = "progressContainer1 red";
	this.fileProgressElement.childNodes[3].className = "progressBarError1";
	this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress2.prototype.setError = function () {
	this.fileProgressElement.className = "progressContainer2 red";
	this.fileProgressElement.childNodes[3].className = "progressBarError2";
	this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress3.prototype.setError = function () {
	this.fileProgressElement.className = "progressContainer3 red";
	this.fileProgressElement.childNodes[3].className = "progressBarError3";
	this.fileProgressElement.childNodes[3].style.width = "";

};


FileProgress.prototype.setCancelled = function () {
	this.fileProgressElement.className = "progressContainer";
	this.fileProgressElement.childNodes[3].className = "progressBarError";
	this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress1.prototype.setCancelled = function () {
	this.fileProgressElement.className = "progressContainer1";
	this.fileProgressElement.childNodes[3].className = "progressBarError1";
	this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress2.prototype.setCancelled = function () {
	this.fileProgressElement.className = "progressContainer2";
	this.fileProgressElement.childNodes[3].className = "progressBarError2";
	this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress3.prototype.setCancelled = function () {
	this.fileProgressElement.className = "progressContainer3";
	this.fileProgressElement.childNodes[3].className = "progressBarError3";
	this.fileProgressElement.childNodes[3].style.width = "";

};

FileProgress.prototype.setStatus = function (status) {
	this.fileProgressElement.childNodes[2].innerHTML = status;
};
FileProgress1.prototype.setStatus = function (status) {
	$(".progressBarStatus1").html(status);
	//fileProgressElement.innerHTML = status;
};
FileProgress2.prototype.setStatus = function (status) {
	$(".progressBarStatus2").html(status);
};
FileProgress3.prototype.setStatus = function (status) {
	$(".progressBarStatus3").html(status);
};



FileProgress.prototype.toggleCancel = function (show, swfuploadInstance) {
	this.fileProgressElement.childNodes[0].style.visibility = show ? "visible" : "hidden";
	if (swfuploadInstance) {
		var fileID = this.fileProgressID;
		this.fileProgressElement.childNodes[0].onclick = function () {
			swfuploadInstance.cancelUpload(fileID);
			return false;
		};
	}
};
FileProgress1.prototype.toggleCancel = function (show, swfuploadInstance) {
	var fileProgressElement=$(".progressContainer1");
	fileProgressElement.childNodes[0].style.visibility = show ? "visible" : "hidden";
	if (swfuploadInstance) {
		var fileID = this.fileProgressID;
		fileProgressElement.childNodes[0].onclick = function () {
			swfuploadInstance.cancelUpload(fileID);
			return false;
		};
	}
};
FileProgress2.prototype.toggleCancel = function (show, swfuploadInstance) {
	var fileProgressElement=$(".progressContainer2");
	fileProgressElement.childNodes[0].style.visibility = show ? "visible" : "hidden";
	if (swfuploadInstance) {
		var fileID = this.fileProgressID;
		fileProgressElement.childNodes[0].onclick = function () {
			swfuploadInstance.cancelUpload(fileID);
			return false;
		};
	}
};
FileProgress3.prototype.toggleCancel = function (show, swfuploadInstance) {
	var fileProgressElement=$(".progressContainer3");
	fileProgressElement.childNodes[0].style.visibility = show ? "visible" : "hidden";
	if (swfuploadInstance) {
		var fileID = this.fileProgressID;
		fileProgressElement.childNodes[0].onclick = function () {
			swfuploadInstance.cancelUpload(fileID);
			return false;
		};
	}
};
