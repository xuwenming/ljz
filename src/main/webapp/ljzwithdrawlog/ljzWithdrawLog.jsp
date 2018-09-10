<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzWithdrawLog" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>LjzWithdrawLog管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/ljzWithdrawLogController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/ljzWithdrawLogController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/ljzWithdrawLogController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/ljzWithdrawLogController/dataGrid',
			fit : true,
			fitColumns : true,
			border : false,
			pagination : true,
			idField : 'id',
			pageSize : 10,
			pageList : [ 10, 20, 30, 40, 50 ],
			sortName : 'id',
			sortOrder : 'desc',
			checkOnSelect : false,
			selectOnCheck : false,
			nowrap : false,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				hidden : true
				}, {
				field : 'addtime',
				title : '<%=TljzWithdrawLog.ALIAS_ADDTIME%>',
				width : 50		
				}, {
				field : 'updatetime',
				title : '<%=TljzWithdrawLog.ALIAS_UPDATETIME%>',
				width : 50		
				}, {
				field : 'isdeleted',
				title : '<%=TljzWithdrawLog.ALIAS_ISDELETED%>',
				width : 50		
				}, {
				field : 'withdrawNo',
				title : '<%=TljzWithdrawLog.ALIAS_WITHDRAW_NO%>',
				width : 50		
				}, {
				field : 'amount',
				title : '<%=TljzWithdrawLog.ALIAS_AMOUNT%>',
				width : 50		
				}, {
				field : 'serviceAmt',
				title : '<%=TljzWithdrawLog.ALIAS_SERVICE_AMT%>',
				width : 50		
				}, {
				field : 'userId',
				title : '<%=TljzWithdrawLog.ALIAS_USER_ID%>',
				width : 50		
				}, {
				field : 'content',
				title : '<%=TljzWithdrawLog.ALIAS_CONTENT%>',
				width : 50		
				}, {
				field : 'handleStatus',
				title : '<%=TljzWithdrawLog.ALIAS_HANDLE_STATUS%>',
				width : 50		
				}, {
				field : 'handleLoginId',
				title : '<%=TljzWithdrawLog.ALIAS_HANDLE_LOGIN_ID%>',
				width : 50		
				}, {
				field : 'handleRemark',
				title : '<%=TljzWithdrawLog.ALIAS_HANDLE_REMARK%>',
				width : 50		
				}, {
				field : 'handleTime',
				title : '<%=TljzWithdrawLog.ALIAS_HANDLE_TIME%>',
				width : 50		
				}, {
				field : 'paymentNo',
				title : '<%=TljzWithdrawLog.ALIAS_PAYMENT_NO%>',
				width : 50		
				}, {
				field : 'cmmsAmt',
				title : '<%=TljzWithdrawLog.ALIAS_CMMS_AMT%>',
				width : 50		
				}, {
				field : 'refType',
				title : '<%=TljzWithdrawLog.ALIAS_REF_TYPE%>',
				width : 50		
				}, {
				field : 'applyLoginIp',
				title : '<%=TljzWithdrawLog.ALIAS_APPLY_LOGIN_IP%>',
				width : 50		
			}, {
				field : 'action',
				title : '操作',
				width : 100,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canEdit) {
						str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_edit.png');
					}
					str += '&nbsp;';
					if ($.canDelete) {
						str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_delete.png');
					}
					str += '&nbsp;';
					if ($.canView) {
						str += $.formatString('<img onclick="viewFun(\'{0}\');" src="{1}" title="查看"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_link.png');
					}
					return str;
				}
			} ] ],
			toolbar : '#toolbar',
			onLoadSuccess : function() {
				$('#searchForm table').show();
				parent.$.messager.progress('close');

				$(this).datagrid('tooltip');
			}
		});
	});

	function deleteFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.messager.confirm('询问', '您是否要删除当前数据？', function(b) {
			if (b) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/ljzWithdrawLogController/delete', {
					id : id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						dataGrid.datagrid('reload');
					}
					parent.$.messager.progress('close');
				}, 'JSON');
			}
		});
	}

	function editFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '编辑数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/ljzWithdrawLogController/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}

	function viewFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title : '查看数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/ljzWithdrawLogController/view?id=' + id
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/ljzWithdrawLogController/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#form');
					f.submit();
				}
			} ]
		});
	}
	function downloadTable(){
		var options = dataGrid.datagrid("options");
		var $colums = [];		
		$.merge($colums, options.columns); 
		$.merge($colums, options.frozenColumns);
		var columsStr = JSON.stringify($colums);
	    $('#downloadTable').form('submit', {
	        url:'${pageContext.request.contextPath}/ljzWithdrawLogController/download',
	        onSubmit: function(param){
	        	$.extend(param, $.serializeObject($('#searchForm')));
	        	param.downloadFields = columsStr;
	        	param.page = options.pageNumber;
	        	param.rows = options.pageSize;
	        	
       	 }
        }); 
	}
	function searchFun() {
		dataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		dataGrid.datagrid('load', {});
	}
</script>
</head>
<body>
	<div class="easyui-layout" data-options="fit : true,border : false">
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 160px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
						<tr>	
							<th><%=TljzWithdrawLog.ALIAS_ADDTIME%></th>	
							<td>
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TljzWithdrawLog.FORMAT_ADDTIME%>'})" id="addtimeBegin" name="addtimeBegin"/>
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TljzWithdrawLog.FORMAT_ADDTIME%>'})" id="addtimeEnd" name="addtimeEnd"/>
							</td>
							<th><%=TljzWithdrawLog.ALIAS_UPDATETIME%></th>	
							<td>
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TljzWithdrawLog.FORMAT_UPDATETIME%>'})" id="updatetimeBegin" name="updatetimeBegin"/>
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TljzWithdrawLog.FORMAT_UPDATETIME%>'})" id="updatetimeEnd" name="updatetimeEnd"/>
							</td>
							<th><%=TljzWithdrawLog.ALIAS_ISDELETED%></th>	
							<td>
											<input type="text" name="isdeleted" maxlength="0" class="span2"/>
							</td>
							<th><%=TljzWithdrawLog.ALIAS_WITHDRAW_NO%></th>	
							<td>
											<input type="text" name="withdrawNo" maxlength="64" class="span2"/>
							</td>
						</tr>	
						<tr>	
							<th><%=TljzWithdrawLog.ALIAS_AMOUNT%></th>	
							<td>
											<input type="text" name="amount" maxlength="19" class="span2"/>
							</td>
							<th><%=TljzWithdrawLog.ALIAS_SERVICE_AMT%></th>	
							<td>
											<input type="text" name="serviceAmt" maxlength="19" class="span2"/>
							</td>
							<th><%=TljzWithdrawLog.ALIAS_USER_ID%></th>	
							<td>
											<input type="text" name="userId" maxlength="36" class="span2"/>
							</td>
							<th><%=TljzWithdrawLog.ALIAS_CONTENT%></th>	
							<td>
											<input type="text" name="content" maxlength="512" class="span2"/>
							</td>
						</tr>	
						<tr>	
							<th><%=TljzWithdrawLog.ALIAS_HANDLE_STATUS%></th>	
							<td>
											<jb:select dataType="HS" name="handleStatus"></jb:select>	
							</td>
							<th><%=TljzWithdrawLog.ALIAS_HANDLE_LOGIN_ID%></th>	
							<td>
											<input type="text" name="handleLoginId" maxlength="36" class="span2"/>
							</td>
							<th><%=TljzWithdrawLog.ALIAS_HANDLE_REMARK%></th>	
							<td>
											<input type="text" name="handleRemark" maxlength="512" class="span2"/>
							</td>
							<th><%=TljzWithdrawLog.ALIAS_HANDLE_TIME%></th>	
							<td>
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TljzWithdrawLog.FORMAT_HANDLE_TIME%>'})" id="handleTimeBegin" name="handleTimeBegin"/>
								<input type="text" class="span2" onclick="WdatePicker({dateFmt:'<%=TljzWithdrawLog.FORMAT_HANDLE_TIME%>'})" id="handleTimeEnd" name="handleTimeEnd"/>
							</td>
						</tr>	
						<tr>	
							<th><%=TljzWithdrawLog.ALIAS_PAYMENT_NO%></th>	
							<td>
											<input type="text" name="paymentNo" maxlength="64" class="span2"/>
							</td>
							<th><%=TljzWithdrawLog.ALIAS_CMMS_AMT%></th>	
							<td>
											<input type="text" name="cmmsAmt" maxlength="19" class="span2"/>
							</td>
							<th><%=TljzWithdrawLog.ALIAS_REF_TYPE%></th>	
							<td>
											<input type="text" name="refType" maxlength="10" class="span2"/>
							</td>
							<th><%=TljzWithdrawLog.ALIAS_APPLY_LOGIN_IP%></th>	
							<td>
											<input type="text" name="applyLoginIp" maxlength="64" class="span2"/>
							</td>
						</tr>	
				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/ljzWithdrawLogController/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'bug_add'">添加</a>
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">过滤条件</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/ljzWithdrawLogController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>	
</body>
</html>