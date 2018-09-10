<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzPayment" %>
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
					<th><%=TljzPayment.ALIAS_ADDTIME%></th>	
					<td>
						${ljzPayment.addtime}							
					</td>							
					<th><%=TljzPayment.ALIAS_UPDATETIME%></th>	
					<td>
						${ljzPayment.updatetime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzPayment.ALIAS_ISDELETED%></th>	
					<td>
						${ljzPayment.isdeleted}							
					</td>							
					<th><%=TljzPayment.ALIAS_ORDER_ID%></th>	
					<td>
						${ljzPayment.orderId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzPayment.ALIAS_AMOUNT%></th>	
					<td>
						${ljzPayment.amount}							
					</td>							
					<th><%=TljzPayment.ALIAS_PAY_WAY%></th>	
					<td>
						${ljzPayment.payWay}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzPayment.ALIAS_STATUS%></th>	
					<td>
						${ljzPayment.status}							
					</td>							
				</tr>		
		</table>
	</div>
</div>