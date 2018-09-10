<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzUser" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/ljzUserController/edit',
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
				<input type="hidden" name="id" value = "${ljzUser.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TljzUser.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TljzUser.FORMAT_ADDTIME%>'})"   maxlength="0" value="${ljzUser.addtime}"/>
					</td>							
					<th><%=TljzUser.ALIAS_UPDATETIME%></th>	
					<td>
					<input class="span2" name="updatetime" type="text" onclick="WdatePicker({dateFmt:'<%=TljzUser.FORMAT_UPDATETIME%>'})"   maxlength="0" value="${ljzUser.updatetime}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzUser.ALIAS_ISDELETED%></th>	
					<td>
											<input class="span2" name="isdeleted" type="text" class="easyui-validatebox span2" data-options="required:true" value="${ljzUser.isdeleted}"/>
					</td>							
					<th><%=TljzUser.ALIAS_NICK_NAME%></th>	
					<td>
											<input class="span2" name="nickName" type="text" value="${ljzUser.nickName}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzUser.ALIAS_PHONE%></th>	
					<td>
											<input class="span2" name="phone" type="text" class="easyui-validatebox span2" data-options="required:true" value="${ljzUser.phone}"/>
					</td>							
					<th><%=TljzUser.ALIAS_ICON%></th>	
					<td>
											<input class="span2" name="icon" type="text" value="${ljzUser.icon}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzUser.ALIAS_SEX%></th>	
					<td>
											<input class="span2" name="sex" type="text" value="${ljzUser.sex}"/>
					</td>							
					<th><%=TljzUser.ALIAS_REF_ID%></th>	
					<td>
											<input class="span2" name="refId" type="text" value="${ljzUser.refId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzUser.ALIAS_REF_TYPE%></th>	
					<td>
											<input class="span2" name="refType" type="text" value="${ljzUser.refType}"/>
					</td>							
					<th><%=TljzUser.ALIAS_RECOMMENDS%></th>	
					<td>
											<input class="span2" name="recommends" type="text" value="${ljzUser.recommends}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>