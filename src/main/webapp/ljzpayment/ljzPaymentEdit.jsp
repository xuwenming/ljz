<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzPayment" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/ljzPaymentController/edit',
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
					parent.$.modalDialog.openner_dataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_dataGrid这个对象，是因为user.jsp页面预定义好了
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
				<input type="hidden" name="id" value = "${ljzPayment.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TljzPayment.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TljzPayment.FORMAT_ADDTIME%>'})"   maxlength="0" value="${ljzPayment.addtime}"/>
					</td>							
					<th><%=TljzPayment.ALIAS_UPDATETIME%></th>	
					<td>
					<input class="span2" name="updatetime" type="text" onclick="WdatePicker({dateFmt:'<%=TljzPayment.FORMAT_UPDATETIME%>'})"   maxlength="0" value="${ljzPayment.updatetime}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzPayment.ALIAS_ISDELETED%></th>	
					<td>
											<input class="span2" name="isdeleted" type="text" class="easyui-validatebox span2" data-options="required:true" value="${ljzPayment.isdeleted}"/>
					</td>							
					<th><%=TljzPayment.ALIAS_ORDER_ID%></th>	
					<td>
											<input class="span2" name="orderId" type="text" value="${ljzPayment.orderId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzPayment.ALIAS_AMOUNT%></th>	
					<td>
											<input class="span2" name="amount" type="text" value="${ljzPayment.amount}"/>
					</td>							
					<th><%=TljzPayment.ALIAS_PAY_WAY%></th>	
					<td>
											<jb:select dataType="PW" name="payWay" value="${ljzPayment.payWay}"></jb:select>	
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzPayment.ALIAS_STATUS%></th>	
					<td>
											<input class="span2" name="status" type="text" value="${ljzPayment.status}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>