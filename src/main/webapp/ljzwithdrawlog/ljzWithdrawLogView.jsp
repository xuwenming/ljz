<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzWithdrawLog" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/javascript">
	$(function() {
		parent.$.messager.progress('close');
		$('.amount').each(function(){
			$(this).text($.formatMoney($.trim($(this).text())));
		});
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<table class="table table-hover table-condensed">
			<tr>
				<th width="10%"><%=TljzWithdrawLog.ALIAS_WITHDRAW_NO%></th>
				<td width="40%">
					${fdWithdrawLog.withdrawNo}
				</td>
				<th width="10%">申请时间</th>
				<td>
					${fdWithdrawLog.createTimeStr}
				</td>
			</tr>
			<tr>
				<th>申请人姓名</th>
				<td>
					${fdWithdrawLog.userName}
				</td>
				<th>手机号</th>
				<td>
					${fdWithdrawLog.userMobile}
				</td>
			</tr>
			<tr>
				<th><%=TljzWithdrawLog.ALIAS_AMOUNT%></th>
				<td class="amount">
					${fdWithdrawLog.amount}
				</td>
				<th>提现手续费</th>
				<td class="amount">
					${fdWithdrawLog.serviceAmt}
				</td>
			</tr>
			<tr>
				<th>银行</th>
				<td>
					${fdWithdrawLog.bankCodeZh}
				</td>
				<th>开户行支行</th>
				<td>
					${fdWithdrawLog.bankName}
				</td>
			</tr>
			<tr>
				<th>银行卡号</th>
				<td>
					${fdWithdrawLog.bankCard}
				</td>
				<th>开户姓名</th>
				<td>
					${fdWithdrawLog.bankAccount}
				</td>
			</tr>
			<tr>
				<th>处理状态</th>
				<td>
					${fdWithdrawLog.handleStatusZh}
				</td>
				<th>处理人</th>
				<td>
					${fdWithdrawLog.handleLoginName}
				</td>
			</tr>
			<tr>
				<th>处理时间</th>
				<td colspan="3">
					<fmt:formatDate value="${fdWithdrawLog.handleTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
			</tr>
			<tr>
				<th>处理结果</th>
				<td colspan="3">
					${fdWithdrawLog.handleRemark}
				</td>
			</tr>
		</table>
	</div>
</div>