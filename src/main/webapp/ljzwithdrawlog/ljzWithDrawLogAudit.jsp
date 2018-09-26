<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/ljzWithdrawLogController/editAudit',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				var checkPwd = "", checkPwdInp = $('#checkPwdInp').val();
				if(checkPwdInp != '') {
					$.ajax({
						type: "GET",
						url: "${pageContext.request.contextPath}/userController/getPublicKey",
						dataType: "json",
						async : false,
						success:function (data) {
							if(data.success) {
								var encrypt = new JSEncrypt();
								encrypt.setPublicKey(data.obj);
								checkPwd = encrypt.encrypt($('#checkPwdInp').val());
							}
						}
					});
				}
				$('#checkPwd').val(checkPwd);
				if (!isValid) {
					parent.$.messager.progress('close');
                    $("input[name=handleStatus]").val($("input[name=oldHandleStatus]").val());
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
					$("input[name=handleStatus]").val($("input[name=oldHandleStatus]").val());
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
				<input type="hidden" name="id" value = "${ljzWithdrawLog.id}"/>
				<input name="handleStatus" type="hidden" value="${ljzWithdrawLog.handleStatus}"/>
				<input name="oldHandleStatus" type="hidden" value="${ljzWithdrawLog.handleStatus}"/>
			<table class="table table-hover table-condensed">

				<tr>
					<th width="10%">审核备注</th>
					<td>
						<textarea name="handleRemark"  class="easyui-validatebox" data-options="required:true"  style="width: 95%"></textarea>
					</td>
				</tr>
				<tr>
					<th>校验密码<font color="red" id="msg">*</font></th>
					<td colspan="3">
						<input id="checkPwd" name="checkPwd" type="hidden" />
						<input id="checkPwdInp" type="password" class="easyui-validatebox span2" data-options="required:true" maxlength="20"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>