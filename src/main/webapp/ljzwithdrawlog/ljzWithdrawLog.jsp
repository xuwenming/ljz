<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzWithdrawLog" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>FdWithdrawLog管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/fdWithdrawLogController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/fdWithdrawLogController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/fdWithdrawLogController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/fdWithdrawLogController/editAudit')}">
	<script type="text/javascript">
		$.canEditAudit = true;
	</script>
</c:if>
	<c:if test="${fn:contains(sessionInfo.resourceList, '/fdWithdrawLogController/viewStatus')}">
		<script type="text/javascript">
			$.canViewStatus = true;
		</script>
	</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/fdWithdrawLogController/dataGrid',
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
			nowrap : true,
			striped : true,
			rownumbers : true,
			singleSelect : true,
			columns : [ [ {
				field : 'id',
				title : '编号',
				width : 150,
				hidden : true
				}, {
				field : 'withdrawNo',
				title : '提现单号',
				width : 60,
				formatter : function (value, row, index) {
					if ($.canView) {
						return '<a onclick="viewFun(\'' + row.id + '\')">'+value+'</a>';
					}
					return value
				}
				}, {
				field : 'createTime',
				title : '申请时间',
				width : 60,
				formatter : function (value, row, index) {
					return new Date(value).format('yyyy-MM-dd HH:mm:ss');
				}
				}, {
				field : 'userName',
				title : '申请人姓名',
				width : 40
				}, {
				field : 'userMobile',
				title : '申请人手机号',
				width : 50
				}, {
				field : 'amount',
				title : '<%=TljzWithdrawLog.ALIAS_AMOUNT%>',
				width : 40,
				align: "right",
				formatter: function (value, row, index) {
					if (value != null)
						return $.formatMoney(value);
					else
						return '--';
				}
				}, {
				field : 'handleStatusZh',
				title : '<%=TljzWithdrawLog.ALIAS_HANDLE_STATUS%>',
				width : 40,
				formatter: function (value, row, index) {
					var str;
					if(row.handleStatus == "HS01") str = value;
					else if(row.handleStatus == "HS02") str = '<font style="color:#1AAFF0;">'+value+'</font>';
					else if(row.handleStatus == "HS03")str =  '<font color="#f6383a;">'+value+'</font>';
					else if(row.handleStatus == "HS04")str =  '<font color="#4cd964;">'+value+'</font>';
					else str =  '<font color="#F00;">'+value+'</font>';

					return str;
				}
				}, {
				field : 'handleLoginName',
				title : '<%=TljzWithdrawLog.ALIAS_HANDLE_LOGIN_ID%>',
				width : 40
				}, {
				field : 'refTypeZh',
				title : '<%=TljzWithdrawLog.ALIAS_REF_TYPE%>',
				width : 50,
				formatter: function (value, row, index) {
					if(row.refType == 'BBT007') return '提现-患者端';
					else return '提现-医生端';
				}
			}, {
				field : 'action',
				title : '操作',
				width : 50,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canViewStatus && row.handleStatus == 'HS02') {
						str += '<a onclick="viewStatus(\'' + row.withdrawNo + '\')">查看状态</a>';
					}
					str += '&nbsp;';
					if ($.canEditAudit && row.handleStatus == 'HS01'){
						str += '<a onclick="editAuditFun(\'' + row.id + '\',\'' + row.auditType + '\')">审核</a>';
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


	function editAuditFun(id) {
		if (id == undefined) {
			var rows = dataGrid.datagrid('getSelections');
			id = rows[0].id;
		}
		parent.$.modalDialog({
			title: '提现审核',
			width: 780,
			height: 200,
			href: '${pageContext.request.contextPath}/fdWithdrawLogController/editAuditPage?id=' + id ,
			buttons: [{
				text: '同意',
				handler: function () {
					var f = parent.$.modalDialog.handler.find('#form');
					var $handleStatus = f.find("input[name=handleStatus]");
					if($handleStatus.val() == 'HS02') {
						parent.$.messager.alert('提示', '提现已审核通过，请勿重复操作', 'info');
						return;
					}
					parent.$.messager.confirm('询问', '同意提现，是否继续？', function(b) {
						if (b) {
							parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
							$handleStatus.val("HS02");
							f.submit();
						}
					});
				}
			},
				{
					text: '拒绝',
					handler: function () {
						parent.$.messager.confirm('询问', '拒绝提现余额退回，是否继续？', function(b) {
							if (b) {
								parent.$.modalDialog.openner_dataGrid = dataGrid;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
								var f = parent.$.modalDialog.handler.find('#form');
								f.find("input[name=handleStatus]").val("HS03");
								f.submit();
							}
						});
					}
				}
			]
		});
	}

	function viewStatus(withdrawNo) {
		$.post('${pageContext.request.contextPath}/fdWithdrawLogController/viewStatus', {
			withdrawNo : withdrawNo
		}, function(result) {
			if (result.success) {
				if(result.obj) {
					if(result.obj.status == 'PROCESSING') {
						parent.$.messager.alert('提示', '提现正在处理中...', 'info');
					} else if(result.obj.status == 'SUCCESS') {
						parent.$.messager.alert('提示', '提现已处理成功', 'info');
					} else {
						parent.$.messager.alert('提示', '1、提现失败<br>2、原因：' + result.obj.reason + '<br><br>请及时将该提现审核拒绝！', 'error');
					}

				} else {
					parent.$.messager.alert('提示', '接口异常', 'error');
				}

			}
		}, 'JSON');
	}

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
				$.post('${pageContext.request.contextPath}/fdWithdrawLogController/delete', {
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
			href : '${pageContext.request.contextPath}/fdWithdrawLogController/editPage?id=' + id,
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
			href : '${pageContext.request.contextPath}/fdWithdrawLogController/view?id=' + id
		});
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/fdWithdrawLogController/addPage',
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
	        url:'${pageContext.request.contextPath}/fdWithdrawLogController/download',
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
		<div data-options="region:'north',title:'查询条件',border:false" style="height: 70px; overflow: hidden;">
			<form id="searchForm">
				<table class="table table-hover table-condensed" style="display: none;">
					<tr>
						<th>提现单号</th>
						<td>
							<input type="text" name="withdrawNo" maxlength="50" class="span2"/>
						</td>
						<th>提现申请人</th>
						<td>
							<jb:selectGrid dataType="userId" name="userId"></jb:selectGrid>
						</td>
						<th>处理状态</th>
						<td>
							<jb:select dataType="HS" name="handleStatus"></jb:select>
						</td>
						<th>申请时间</th>
						<td colspan="3"><input class="span2" name="createTimeStartDate"
											   placeholder="点击选择时间"
											   onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
											   readonly="readonly" />至<input class="span2"
																			 name="createTimeEndDate" placeholder="点击选择时间"
																			 onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
																			 readonly="readonly" /></td>

					</tr>

				</table>
			</form>
		</div>
		<div data-options="region:'center',border:false">
			<table id="dataGrid"></table>
		</div>
	</div>
	<div id="toolbar" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/fdWithdrawLogController/addPage')}">
			<%--<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'bug_add'">添加</a>--%>
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/fdWithdrawLogController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>	
</body>
</html>