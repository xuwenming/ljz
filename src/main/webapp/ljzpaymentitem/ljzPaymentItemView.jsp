<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzPaymentItem" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');		
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TljzPaymentItem.ALIAS_ADDTIME%></th>	
					<td>
						${ljzPaymentItem.addtime}							
					</td>							
					<th><%=TljzPaymentItem.ALIAS_UPDATETIME%></th>	
					<td>
						${ljzPaymentItem.updatetime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzPaymentItem.ALIAS_ISDELETED%></th>	
					<td>
						${ljzPaymentItem.isdeleted}							
					</td>							
					<th><%=TljzPaymentItem.ALIAS_PAYMENT_ID%></th>	
					<td>
						${ljzPaymentItem.paymentId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzPaymentItem.ALIAS_AMOUNT%></th>	
					<td>
						${ljzPaymentItem.amount}							
					</td>							
					<th><%=TljzPaymentItem.ALIAS_PAY_WAY%></th>	
					<td>
						${ljzPaymentItem.payWay}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzPaymentItem.ALIAS_REF_ID%></th>	
					<td>
						${ljzPaymentItem.refId}							
					</td>							
					<th><%=TljzPaymentItem.ALIAS_REMARK%></th>	
					<td>
						${ljzPaymentItem.remark}							
					</td>							
				</tr>		
		</table>
	</div>
</div>