<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzPrizeLog" %>
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
					<th><%=TljzPrizeLog.ALIAS_ADDTIME%></th>	
					<td>
						${ljzPrizeLog.addtime}							
					</td>							
					<th><%=TljzPrizeLog.ALIAS_UPDATETIME%></th>	
					<td>
						${ljzPrizeLog.updatetime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzPrizeLog.ALIAS_ISDELETED%></th>	
					<td>
						${ljzPrizeLog.isdeleted}							
					</td>							
					<th><%=TljzPrizeLog.ALIAS_USER_ID%></th>	
					<td>
						${ljzPrizeLog.userId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzPrizeLog.ALIAS_GOODS_ID%></th>	
					<td>
						${ljzPrizeLog.goodsId}							
					</td>							
					<th><%=TljzPrizeLog.ALIAS_AMOUNT%></th>	
					<td>
						${ljzPrizeLog.amount}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzPrizeLog.ALIAS_QUANTITY%></th>	
					<td>
						${ljzPrizeLog.quantity}							
					</td>							
				</tr>		
		</table>
	</div>
</div>