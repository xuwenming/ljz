<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzWithdrawLog" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('.amount').each(function(){
			$(this).text($.trim($(this).text().toFixed(2)));
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<table class="table table-hover table-condensed">
			<tr>
				<th width="10%"><%=TljzWithdrawLog.ALIAS_WITHDRAW_NO%></th>
				<td width="40%">
					${ljzWithdrawLog.withdrawNo}
				</td>
				<th width="10%">申请人ID</th>
				<td>
					${ljzWithdrawLog.userId}
				</td>
			</tr>
			<tr>
				<th><%=TljzWithdrawLog.ALIAS_AMOUNT%></th>
				<td class="amount">
					${ljzWithdrawLog.amount}
				</td>
				<th>提现手续费</th>
				<td class="amount">
					${ljzWithdrawLog.serviceAmt}
				</td>
			</tr>
			<tr>
				<th>真实姓名</th>
				<td>
					${ljzWithdrawLog.realName}
				</td>
				<th>手机号</th>
				<td>
					${ljzWithdrawLog.phone}
				</td>
			</tr>
			<tr>
				<th>处理状态</th>
				<td>
					${ljzWithdrawLog.handleStatusZh}
				</td>
				<th>处理人</th>
				<td>
					${ljzWithdrawLog.handleLoginName}
				</td>
			</tr>
			<tr>
				<th>处理时间</th>
				<td>
					<fmt:formatDate value="${ljzWithdrawLog.handleTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<th>申请时间</th>
				<td>
					<fmt:formatDate value="${ljzWithdrawLog.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<tr>
				<th>处理结果</th>
				<td colspan="3">
					${ljzWithdrawLog.handleRemark}
				</td>
			</tr>
		</table>
	</div>
</div>