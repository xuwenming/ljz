<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzUser" %>
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
					<th><%=TljzUser.ALIAS_ADDTIME%></th>	
					<td>
						${ljzUser.addtime}							
					</td>							
					<th><%=TljzUser.ALIAS_UPDATETIME%></th>	
					<td>
						${ljzUser.updatetime}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzUser.ALIAS_ISDELETED%></th>	
					<td>
						${ljzUser.isdeleted}							
					</td>							
					<th><%=TljzUser.ALIAS_NICK_NAME%></th>	
					<td>
						${ljzUser.nickName}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzUser.ALIAS_PHONE%></th>	
					<td>
						${ljzUser.phone}							
					</td>							
					<th><%=TljzUser.ALIAS_ICON%></th>	
					<td>
						${ljzUser.icon}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzUser.ALIAS_SEX%></th>	
					<td>
						${ljzUser.sex}							
					</td>							
					<th><%=TljzUser.ALIAS_REF_ID%></th>	
					<td>
						${ljzUser.refId}							
					</td>							
				</tr>		
				<tr>	
					<th><%=TljzUser.ALIAS_REF_TYPE%></th>	
					<td>
						${ljzUser.refType}							
					</td>							
					<th><%=TljzUser.ALIAS_RECOMMENDS%></th>	
					<td>
						${ljzUser.recommends}							
					</td>							
				</tr>		
		</table>
	</div>
</div>