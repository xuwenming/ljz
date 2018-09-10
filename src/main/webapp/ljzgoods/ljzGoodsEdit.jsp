<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzGoods" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('#form').form({
			url : '${pageContext.request.contextPath}/ljzGoodsController/edit',
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
				<input type="hidden" name="id" value = "${ljzGoods.id}"/>
			<table class="table table-hover table-condensed">
				<tr>	
					<th><%=TljzGoods.ALIAS_ADDTIME%></th>	
					<td>
					<input class="span2" name="addtime" type="text" onclick="WdatePicker({dateFmt:'<%=TljzGoods.FORMAT_ADDTIME%>'})"   maxlength="0" value="${ljzGoods.addtime}"/>
					</td>							
					<th><%=TljzGoods.ALIAS_UPDATETIME%></th>	
					<td>
					<input class="span2" name="updatetime" type="text" onclick="WdatePicker({dateFmt:'<%=TljzGoods.FORMAT_UPDATETIME%>'})"   maxlength="0" value="${ljzGoods.updatetime}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzGoods.ALIAS_ISDELETED%></th>	
					<td>
											<input class="span2" name="isdeleted" type="text" class="easyui-validatebox span2" data-options="required:true" value="${ljzGoods.isdeleted}"/>
					</td>							
					<th><%=TljzGoods.ALIAS_SHOP_ID%></th>	
					<td>
											<input class="span2" name="shopId" type="text" value="${ljzGoods.shopId}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzGoods.ALIAS_TITLE%></th>	
					<td>
											<input class="span2" name="title" type="text" value="${ljzGoods.title}"/>
					</td>							
					<th><%=TljzGoods.ALIAS_PRICE%></th>	
					<td>
											<input class="span2" name="price" type="text" value="${ljzGoods.price}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzGoods.ALIAS_ICON%></th>	
					<td>
											<input class="span2" name="icon" type="text" value="${ljzGoods.icon}"/>
					</td>							
					<th><%=TljzGoods.ALIAS_IMAGE_URL%></th>	
					<td>
											<input class="span2" name="imageUrl" type="text" value="${ljzGoods.imageUrl}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzGoods.ALIAS_DESCRIBTION%></th>	
					<td>
											<input class="span2" name="describtion" type="text" value="${ljzGoods.describtion}"/>
					</td>							
					<th><%=TljzGoods.ALIAS_IS_PUT_AWAY%></th>	
					<td>
											<input class="span2" name="isPutAway" type="text" value="${ljzGoods.isPutAway}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzGoods.ALIAS_LIMIT_NUMBER%></th>	
					<td>
											<input class="span2" name="limitNumber" type="text" value="${ljzGoods.limitNumber}"/>
					</td>							
					<th><%=TljzGoods.ALIAS_FREIGHT%></th>	
					<td>
											<input class="span2" name="freight" type="text" value="${ljzGoods.freight}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzGoods.ALIAS_SHARE_AMOUNT%></th>	
					<td>
											<input class="span2" name="shareAmount" type="text" value="${ljzGoods.shareAmount}"/>
					</td>							
					<th><%=TljzGoods.ALIAS_PRIZE_PRE%></th>	
					<td>
											<input class="span2" name="prizePre" type="text" value="${ljzGoods.prizePre}"/>
					</td>							
			</tr>	
				<tr>	
					<th><%=TljzGoods.ALIAS_PRIZE_AMOUNT%></th>	
					<td>
											<input class="span2" name="prizeAmount" type="text" value="${ljzGoods.prizeAmount}"/>
					</td>							
					<th><%=TljzGoods.ALIAS_PRIZE_NUMBER%></th>	
					<td>
											<input class="span2" name="prizeNumber" type="text" value="${ljzGoods.prizeNumber}"/>
					</td>							
			</tr>	
			</table>				
		</form>
	</div>
</div>