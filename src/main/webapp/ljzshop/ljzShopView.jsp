<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzShop" %>
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
					<th><%=TljzShop.ALIAS_ADDTIME%></th>	
					<td>
						${ljzShop.addtime}							
					</td>							
					<th><%=TljzShop.ALIAS_UPDATETIME%></th>	
					<td>
						${ljzShop.updatetime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzShop.ALIAS_ISDELETED%></th>	
					<td>
						${ljzShop.isdeleted}							
					</td>							
					<th><%=TljzShop.ALIAS_NAME%></th>	
					<td>
						${ljzShop.name}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzShop.ALIAS_ADDRESS%></th>	
					<td>
						${ljzShop.address}							
					</td>							
					<th><%=TljzShop.ALIAS_CONTACT_PHONE%></th>	
					<td>
						${ljzShop.contactPhone}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzShop.ALIAS_CONTACT_PEOPLE%></th>	
					<td>
						${ljzShop.contactPeople}							
					</td>							
				</tr>		
		</table>
	</div>
</div>