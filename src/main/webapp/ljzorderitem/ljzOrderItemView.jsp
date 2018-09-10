<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzOrderItem" %>
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
					<th><%=TljzOrderItem.ALIAS_ADDTIME%></th>	
					<td>
						${ljzOrderItem.addtime}							
					</td>							
					<th><%=TljzOrderItem.ALIAS_UPDATETIME%></th>	
					<td>
						${ljzOrderItem.updatetime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzOrderItem.ALIAS_ISDELETED%></th>	
					<td>
						${ljzOrderItem.isdeleted}							
					</td>							
					<th><%=TljzOrderItem.ALIAS_ORDER_ID%></th>	
					<td>
						${ljzOrderItem.orderId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzOrderItem.ALIAS_GOODS_ID%></th>	
					<td>
						${ljzOrderItem.goodsId}							
					</td>							
					<th><%=TljzOrderItem.ALIAS_QUANTITY%></th>	
					<td>
						${ljzOrderItem.quantity}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzOrderItem.ALIAS_BUY_PRICE%></th>	
					<td>
						${ljzOrderItem.buyPrice}							
					</td>							
				</tr>		
		</table>
	</div>
</div>