<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.mobian.model.TljzOrderItem" %>
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
                    gridMap.handle(this,loadGoodsDataGrid);
                }, grid: null
            }
        };
        $('#order_view_tabs').tabs({
            onSelect: function (title, index) {
                gridMap[index].invoke();
            }
        });
        gridMap[0].invoke();
    });

    function loadGoodsDataGrid() {
        return $('#goodsDataGrid').datagrid({
            url : '${pageContext.request.contextPath}/ljzOrderItemController/dataGrid?orderId=${ljzOrder.id}',
            fit : true,
            fitColumns : true,
            border : false,
            pagination : false,
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
                field : 'addtime',
                title : '<%=TljzOrderItem.ALIAS_ADDTIME%>',
                width : 50
            }, {
                field : 'goodsId',
                title : '商品ID',
                width : 50
            }, {
                field : 'quantity',
                title : '购买数量',
                width : 50
            }, {
                field : 'buyPrice',
                title : '<%=TljzOrderItem.ALIAS_BUY_PRICE%>',
                width : 50
            } ] ]
        });
    }

    function delivery() {
        parent.$.modalDialog({
            title: '发货',
            width: 780,
            height: 230,
            href: '${pageContext.request.contextPath}/ljzOrderController/deliveryPage?id=${ljzOrder.id}',
            buttons: [{
                text: '发货',
                handler: function () {
                    parent.$.modalDialog.openner_dataGrid = window;//因为添加成功之后，需要刷新这个dataGrid，所以先预定义好
                    var f = parent.$.modalDialog.handler.find('#form');
                    f.find("input[name=status]").val("OD20");
                    f.submit();
                }
            }]
        });
    }
</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 130px; overflow: hidden;">
		<table class="table table-hover table-condensed">
			<tr>
				<th width="5%">订单ID</th>
				<td width="15%">${ljzOrder.id}</td>
				<th width="5%">订单金额</th>
				<td width="15%">${ljzOrder.totalPrice}</td>
				<th width="5%">运费</th>
				<td width="15%">${ljzOrder.freight}</td>
				<th width="5%">店铺ID</th>
				<td width="15%">${ljzOrder.shopId}</td>
				<th width="5%">用户ID</th>
				<td>${ljzOrder.userId}</td>
			</tr>
			<tr>
				<th>订单状态</th>
				<td>
					${ljzOrder.statusZh}
					<c:if test="${fn:contains(sessionInfo.resourceList, '/ljzOrderController/deliveryPage') and ljzOrder.status=='OD10'}">
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="delivery();">&nbsp;发货&nbsp;</a>
					</c:if>
				</td>
				<th>支付状态</th>
				<td>${ljzOrder.payStatusZh}</td>
				<th>支付时间</th>
				<td><fmt:formatDate value="${ljzOrder.payTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<th>创建时间</th>
				<td><fmt:formatDate value="${ljzOrder.addtime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<th>推荐人ID</th>
				<td>${ljzOrder.recommend}</td>
			</tr>
			<tr>
				<th>收货人</th>
				<td>${ljzOrder.contactPeople}</td>
				<th>联系电话</th>
				<td>${ljzOrder.contactPhone}</td>
				<th>收货地址</th>
				<td colspan="100">${ljzOrder.deliveryAddress}</td>
			</tr>
			<c:if test="${ljzOrder.status == 'OD20'}">
				<tr>
					<th>快递公司</th>
					<td>${ljzOrder.expressNameZh}</td>
					<th>运单编号</th>
					<td>${ljzOrder.expressNo}</td>
					<th>备注</th>
					<td colspan="100">${ljzOrder.remark}</td>
				</tr>
			</c:if>
		</table>
	</div>

	<div data-options="region:'center',border:false">
		<div id="order_view_tabs" class="easyui-tabs" data-options="fit : true,border:false">
			<div title="商品明细">
				<table id="goodsDataGrid"></table>
			</div>
		</div>
	</div>
</div>
</body>
</html>