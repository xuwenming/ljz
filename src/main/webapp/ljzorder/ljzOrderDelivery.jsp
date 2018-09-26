<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzOrder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/ljzOrderController/delivery',
			onSubmit : function() {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				var isValid = $(this).form('validate');
				if (!isValid) {
					parent.$.messager.progress('close');
				}
				return isValid;
			},
			success : function(result) {
				parent.$.messager.progress('close');
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_dataGrid.location.reload();//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;">
		<form id="form" method="post">
			<input type="hidden" name="id" value = "${orderId}"/>
			<input name="status" type="hidden"/>
			<table class="table table-hover table-condensed">
				<tr>
					<th width="10%">快递公司</th>
					<td colspan="3">
						<jb:select dataType="EP" name="expressName" required="true"></jb:select>
					</td>
				</tr>
				<tr>
					<th>运单编号</th>
					<td colspan="3">
						<input class="easyui-validatebox span2" data-options="required:true" maxlength="100"  name="expressNo" type="text" style="width: 510px;"/>
					</td>
				</tr>
				<tr>
					<th>备注</th>
					<td colspan="3">
						<textarea class ="easyui-validatebox" data-options="required:true" name="remark" style="width: 510px;"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>