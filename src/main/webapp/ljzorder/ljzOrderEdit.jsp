<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzOrder" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/ljzOrderController/edit',
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
				<input type="hidden" name="id" value = "${ljzOrder.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TljzOrder.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TljzOrder.FORMAT_ADDTIME%>'})"   maxlength="0" value="${ljzOrder.addtime}"/>
					</td>							
					<th><%=TljzOrder.ALIAS_UPDATETIME%></th>	
					<td>
					<input class="span2" name="updatetime" type="text" onclick="WdatePicker({dateFmt:'<%=TljzOrder.FORMAT_UPDATETIME%>'})"   maxlength="0" value="${ljzOrder.updatetime}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzOrder.ALIAS_ISDELETED%></th>	
					<td>
											<input class="span2" name="isdeleted" type="text" class="easyui-validatebox span2" data-options="required:true" value="${ljzOrder.isdeleted}"/>
					</td>							
					<th><%=TljzOrder.ALIAS_SHOP_ID%></th>	
					<td>
											<input class="span2" name="shopId" type="text" value="${ljzOrder.shopId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzOrder.ALIAS_USER_ID%></th>	
					<td>
											<input class="span2" name="userId" type="text" value="${ljzOrder.userId}"/>
					</td>							
					<th><%=TljzOrder.ALIAS_TOTAL_PRICE%></th>	
					<td>
											<input class="span2" name="totalPrice" type="text" value="${ljzOrder.totalPrice}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzOrder.ALIAS_STATUS%></th>	
					<td>
											<jb:select dataType="OD" name="status" value="${ljzOrder.status}"></jb:select>	
					</td>							
					<th><%=TljzOrder.ALIAS_DELIVERY_ADDRESS%></th>	
					<td>
											<input class="span2" name="deliveryAddress" type="text" value="${ljzOrder.deliveryAddress}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzOrder.ALIAS_CONTACT_PHONE%></th>	
					<td>
											<input class="span2" name="contactPhone" type="text" value="${ljzOrder.contactPhone}"/>
					</td>							
					<th><%=TljzOrder.ALIAS_CONTACT_PEOPLE%></th>	
					<td>
											<input class="span2" name="contactPeople" type="text" value="${ljzOrder.contactPeople}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzOrder.ALIAS_PAY_STATUS%></th>	
					<td>
											<jb:select dataType="PS" name="payStatus" value="${ljzOrder.payStatus}"></jb:select>	
					</td>							
					<th><%=TljzOrder.ALIAS_PAY_WAY%></th>	
					<td>
											<jb:select dataType="PW" name="payWay" value="${ljzOrder.payWay}"></jb:select>	
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzOrder.ALIAS_PAY_TIME%></th>	
					<td>
					<input class="span2" name="payTime" type="text" onclick="WdatePicker({dateFmt:'<%=TljzOrder.FORMAT_PAY_TIME%>'})"   maxlength="0" value="${ljzOrder.payTime}"/>
					</td>							
					<th><%=TljzOrder.ALIAS_FREIGHT%></th>	
					<td>
											<input class="span2" name="freight" type="text" value="${ljzOrder.freight}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzOrder.ALIAS_RECOMMEND%></th>	
					<td>
											<input class="span2" name="recommend" type="text" value="${ljzOrder.recommend}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>