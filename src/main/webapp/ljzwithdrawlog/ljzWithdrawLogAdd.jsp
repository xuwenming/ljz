<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzWithdrawLog" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%>  
<script type="text/javascript">
	$(function() {
	 parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/fdWithdrawLogController/add',
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
				<input type="hidden" name="id"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TljzWithdrawLog.ALIAS_CREATE_TIME%></th>	
					<td>
											<input class="span2" name="createTime" type="text"/>
					</td>							
					<th><%=TljzWithdrawLog.ALIAS_UPDATE_TIME%></th>	
					<td>
											<input class="span2" name="updateTime" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TljzWithdrawLog.ALIAS_STATUS%></th>	
					<td>
											<input class="span2" name="status" type="text"/>
					</td>							
					<th><%=TljzWithdrawLog.ALIAS_WITHDRAW_NO%></th>	
					<td>
											<input class="span2" name="withdrawNo" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TljzWithdrawLog.ALIAS_AMOUNT%></th>	
					<td>
					
											<input  name="amount" type="text" class="easyui-validatebox span2" data-options="required:true"/>
					</td>							
					<th><%=TljzWithdrawLog.ALIAS_USER_ID%></th>	
					<td>
											<input class="span2" name="userId" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TljzWithdrawLog.ALIAS_CONTENT%></th>	
					<td>
											<input class="span2" name="content" type="text"/>
					</td>							
					<th><%=TljzWithdrawLog.ALIAS_BANK_ACCOUNT%></th>	
					<td>
											<input class="span2" name="bankAccount" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TljzWithdrawLog.ALIAS_BANK_CODE%></th>	
					<td>
											<jb:select dataType="BC" name="bankCode"></jb:select>	
					</td>							
					<th><%=TljzWithdrawLog.ALIAS_BANK_NAME%></th>	
					<td>
											<input class="span2" name="bankName" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TljzWithdrawLog.ALIAS_BANK_CARD%></th>	
					<td>
											<input class="span2" name="bankCard" type="text"/>
					</td>							
					<th><%=TljzWithdrawLog.ALIAS_HANDLE_STATUS%></th>	
					<td>
											<jb:select dataType="HS" name="handleStatus"></jb:select>	
					</td>							
				</tr>	
				<tr>	
					<th><%=TljzWithdrawLog.ALIAS_HANDLE_LOGIN_ID%></th>	
					<td>
											<input class="span2" name="handleLoginId" type="text"/>
					</td>							
					<th><%=TljzWithdrawLog.ALIAS_HANDLE_REMARK%></th>	
					<td>
											<input class="span2" name="handleRemark" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TljzWithdrawLog.ALIAS_HANDLE_TIME%></th>	
					<td>
					<input class="span2" name="handleTime" type="text" onclick="WdatePicker({dateFmt:'<%=TljzWithdrawLog.FORMAT_HANDLE_TIME%>'})"  maxlength="0" class="" />
					</td>							
					<th><%=TljzWithdrawLog.ALIAS_PAYMENT_NO%></th>	
					<td>
											<input class="span2" name="paymentNo" type="text"/>
					</td>							
				</tr>	
				<tr>	
					<th><%=TljzWithdrawLog.ALIAS_REF_TYPE%></th>	
					<td>
											<input class="span2" name="refType" type="text"/>
					</td>							
					<th><%=TljzWithdrawLog.ALIAS_APPLY_LOGIN_IP%></th>	
					<td>
											<input class="span2" name="applyLoginIp" type="text"/>
					</td>							
				</tr>	
			</table>		
		</form>
	</div>
</div>