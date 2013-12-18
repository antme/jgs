<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<form action="" method="post" novalidate id="user_reset_pwd">
	<div class="update_pass_context" style="_margin-top:-19px;">
		<ul>
			<li><label class="user_info_label text_align_right">原始密码：</label> <input
				type="password" class="user_info_input3 easyui-validatebox" required style="_height:28px;_width:192px;"
				name="password" id="password" missingMessage="请输入原始密码" /></li>
			<li><label class="user_info_label text_align_right">新密码：</label> <input
				type="password" class="user_info_input3 easyui-validatebox" required style="_height:28px;_width:192px;"
				name="newPwd" id="newPwd" missingMessage="请输入新密码" /></li>
			<li><label class="user_info_label text_align_right">确认密码：</label> <input
				type="password" class="user_info_input3 easyui-validatebox" required style="_height:28px;_width:192px;"
				missingMessage="请确认密码" validType="pwdEquals['#newPwd']" /></li>
			<li><input class="reset2" type="submit" value="确 定" /></li>
		</ul>
	</div>
</form>

<script type="text/javascript">

	$("#user_reset_pwd").form({
	    url : '/ecs/user/pwd/reset.do',
	    onSubmit : function() {
	        return $(this).form('validate');
	    },
	    success : function(data) {
	        dealMessageWithCallBack(data, "重置密码", function(data){
	            alert("密码修改成功");
	        });
	    }
	});
	
    $.extend($.fn.validatebox.defaults.rules, {
        pwdEquals: {
            validator: function(value,param){
                return value == $(param[0]).val();
            },
            message: '密码不匹配'
        }
    });
</script>