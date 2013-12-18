<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图片预览</title>
</head>
<body>
	<div id="imagePreviewDlg" class="easyui-dialog" style="width:650px;height:550px;padding:10px 20px;" closed="true" buttons="#dlg-buttons">
        <div class="ftitle">图片预览</div>
        <div class="fitem">
            <img src="" id="previewImage" class="dannyC" height="150" width="300"/>
        </div>
    </div>

	<script type="text/javascript">
		$(".dannyC").attr("src", idParam);
	</script>
</body>
</html>