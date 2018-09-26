<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzBalanceLog" %>
<%@ page import="com.mobian.model.TljzPrizeLog" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
	<title></title>
	<jsp:include page="../inc.jsp"></jsp:include>

	<script type="text/javascript">
        $(function() {
            var gridMap = {
                handle:function(obj,clallback){
                    if (obj.grid == null) {
                        obj.grid = clallback();
                    } else {
                        obj.grid.datagrid('reload');
                    }
                },
                0: {
                    invoke: function () {
                        gridMap.handle(this,loadPrizeLogDataGrid);
                    }, grid: null
                },
                1: {
                    invoke: function () {
                        gridMap.handle(this,loadShareLogDataGrid);
                    }, grid: null
                }
            };
            $('#goods_view_tabs').tabs({
                onSelect: function (title, index) {
                    gridMap[index].invoke();
                }
            });
            gridMap[0].invoke();
        });

        function loadPrizeLogDataGrid() {
            return $('#prizeLogDataGrid').datagrid({
                url : '${pageContext.request.contextPath}/ljzPrizeLogController/dataGrid?goodsId=${ljzGoods.id}',
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
                    title : '中奖时间',
                    width : 50
                }, {
                    field : 'userId',
                    title : '<%=TljzPrizeLog.ALIAS_USER_ID%>',
                    width : 50
                }, {
                    field : 'amount',
                    title : '<%=TljzPrizeLog.ALIAS_AMOUNT%>',
                    width : 50,
                    formatter:function(value,row){
                        return value.toFixed(2);
                    }
                }, {
                    field : 'quantity',
                    title : '购买数量',
                    width : 50
                }, {
                    field : 'remark',
                    title : '备注',
                    width : 80
                } ] ]
            });
        }

        function loadShareLogDataGrid() {
            return $('#shareLogDataGrid').datagrid({
                url : '${pageContext.request.contextPath}/ljzBalanceLogController/dataGrid?refId=${ljzGoods.id}&refType=BBT005',
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
                    title : '<%=TljzBalanceLog.ALIAS_ADDTIME%>',
                    width : 50
                }, {
                    field : 'userId',
                    title : '用户ID',
                    width : 50
                }, {
                    field : 'amount',
                    title : '赚取金额',
                    width : 50,
                    formatter:function(value,row){
                        return value.toFixed(2);
                    }
                }, {
                    field : 'remark',
                    title : '<%=TljzBalanceLog.ALIAS_REMARK%>',
                    width : 80
                } ] ]
            });
        }
	</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false">
		<div id="goods_view_tabs" class="easyui-tabs" data-options="fit : true,border:false">
			<div title="中奖记录">
				<table id="prizeLogDataGrid"></table>
			</div>
			<div title="转发赚取记录">
				<table id="shareLogDataGrid"></table>
			</div>
		</div>
	</div>
</div>
</body>
</html>