<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzBalanceLog" %>
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
					<th><%=TljzBalanceLog.ALIAS_ADDTIME%></th>	
					<td>
						${ljzBalanceLog.addtime}							
					</td>							
					<th><%=TljzBalanceLog.ALIAS_UPDATETIME%></th>	
					<td>
						${ljzBalanceLog.updatetime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzBalanceLog.ALIAS_ISDELETED%></th>	
					<td>
						${ljzBalanceLog.isdeleted}							
					</td>							
					<th><%=TljzBalanceLog.ALIAS_BALANCE_ID%></th>	
					<td>
						${ljzBalanceLog.balanceId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzBalanceLog.ALIAS_AMOUNT%></th>	
					<td>
						${ljzBalanceLog.amount}							
					</td>							
					<th><%=TljzBalanceLog.ALIAS_REF_ID%></th>	
					<td>
						${ljzBalanceLog.refId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzBalanceLog.ALIAS_REF_TYPE%></th>	
					<td>
						${ljzBalanceLog.refType}							
					</td>							
					<th><%=TljzBalanceLog.ALIAS_REMARK%></th>	
					<td>
						${ljzBalanceLog.remark}							
					</td>							
				</tr>		
		</table>
	</div>
</div>