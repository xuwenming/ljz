<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzGoods" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="jb" uri="http://www.jb.cn/jbtag"%> 
<!DOCTYPE html>
<html>
<head>
<title>LjzGoods管理</title>
<jsp:include page="../inc.jsp"></jsp:include>
<c:if test="${fn:contains(sessionInfo.resourceList, '/ljzGoodsController/editPage')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/ljzGoodsController/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/ljzGoodsController/view')}">
	<script type="text/javascript">
		$.canView = true;
	</script>
</c:if>
<script type="text/javascript">
	var dataGrid;
	$(function() {
		dataGrid = $('#dataGrid').datagrid({
			url : '${pageContext.request.contextPath}/ljzGoodsController/dataGrid',
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
				title : '商品ID',
				width : 30
				}, {
				field : 'title',
				title : '<%=TljzGoods.ALIAS_TITLE%>',
				width : 60,
                formatter : function (value, row, index) {
                    if ($.canView) {
                        return '<a onclick="viewFun(\'' + row.id + '\')">'+value+'</a>';
                    }
                    return value
                }
				}, {
                field : 'shopId',
                title : '<%=TljzGoods.ALIAS_SHOP_ID%>',
                width : 50
            	}, {
                field : 'icon',
                title : '图标',
                width : 50,
                formatter : function(value, row, index) {
                    var str = "";
                    if(value){
                        str = "<img style=\"height: 60px;width: 80px;\" src=\""+value+"\" />";
                    }
                    return str;
                }
            	}, {
				field : 'price',
				title : '<%=TljzGoods.ALIAS_PRICE%>',
				width : 30
				}, {
				field : 'isPutAway',
				title : '状态',
				width : 30,
                formatter : function(value, row, index) {
                    return value == 0 ? '上架' : '下架';
                }
				}, {
				field : 'limitNumber',
				title : '<%=TljzGoods.ALIAS_LIMIT_NUMBER%>',
				width : 30
				}, {
				field : 'freight',
				title : '<%=TljzGoods.ALIAS_FREIGHT%>',
				width : 30
				}, {
				field : 'shareAmount',
				title : '<%=TljzGoods.ALIAS_SHARE_AMOUNT%>',
				width : 30
				}, {
				field : 'prizePre',
				title : '<%=TljzGoods.ALIAS_PRIZE_PRE%>',
				width : 30
				}, {
				field : 'prizeNumber',
				title : '<%=TljzGoods.ALIAS_PRIZE_NUMBER%>',
				width : 30
            	}, {
                field : 'addtime',
                title : '<%=TljzGoods.ALIAS_ADDTIME%>',
                width : 60
			}, {
				field : 'action',
				title : '操作',
				width : 30,
				formatter : function(value, row, index) {
					var str = '';
					if ($.canEdit) {
                        str += '<a onclick="editFun(\'' + row.id + '\')">编辑</a>';
						//str += $.formatString('<img onclick="editFun(\'{0}\');" src="{1}" title="编辑"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_edit.png');
					}
					str += '&nbsp;';
					if ($.canDelete) {
                        str += '<a onclick="deleteFun(\'' + row.id + '\')">删除</a>';
						//str += $.formatString('<img onclick="deleteFun(\'{0}\');" src="{1}" title="删除"/>', row.id, '${pageContext.request.contextPath}/style/images/extjs_icons/bug/bug_delete.png');
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
				$.post('${pageContext.request.contextPath}/ljzGoodsController/delete', {
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
			href : '${pageContext.request.contextPath}/ljzGoodsController/editPage?id=' + id,
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
        var href = '${pageContext.request.contextPath}/ljzGoodsController/view?id=' + id;
        parent.$("#index_tabs").tabs('add', {
            title : '商品详情-' + id,
            content : '<iframe src="' + href + '" frameborder="0" scrolling="auto" style="width:100%;height:98%;"></iframe>',
            closable : true
        });
	}

	function addFun() {
		parent.$.modalDialog({
			title : '添加数据',
			width : 780,
			height : 500,
			href : '${pageContext.request.contextPath}/ljzGoodsController/addPage',
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
	        url:'${pageContext.request.contextPath}/ljzGoodsController/download',
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
							<th>商品ID</th>
							<td>
								<input type="text" name="id" maxlength="10" class="span2"/>
							</td>
							<th><%=TljzGoods.ALIAS_SHOP_ID%></th>	
							<td>
								<input type="text" name="shopId" maxlength="10" class="span2"/>
							</td>
							<th><%=TljzGoods.ALIAS_TITLE%></th>
							<td>
								<input type="text" name="title" maxlength="128" class="span2"/>
							</td>
							<th>状态</th>
							<td>
								<select name="isPutAway" class="easyui-combobox"
										data-options="width:140,height:29,editable:false,panelHeight:'auto'">
									<option value="">全部</option>
									<option value="0">上架</option>
									<option value="1">下架</option>
								</select>
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
		<c:if test="${fn:contains(sessionInfo.resourceList, '/ljzGoodsController/addPage')}">
			<a onclick="addFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'bug_add'">添加</a>
		</c:if>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_add',plain:true" onclick="searchFun();">查询</a><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'brick_delete',plain:true" onclick="cleanFun();">清空条件</a>
		<c:if test="${fn:contains(sessionInfo.resourceList, '/ljzGoodsController/download')}">
			<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'server_go',plain:true" onclick="downloadTable();">导出</a>		
			<form id="downloadTable" target="downloadIframe" method="post" style="display: none;">
			</form>
			<iframe id="downloadIframe" name="downloadIframe" style="display: none;"></iframe>
		</c:if>
	</div>	
</body>
</html>