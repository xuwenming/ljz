package com.mobian.service.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mobian.absx.F;
import com.mobian.dao.LjzOrderDaoI;
import com.mobian.exception.ServiceException;
import com.mobian.model.TljzBalanceLog;
import com.mobian.model.TljzOrder;
import com.mobian.pageModel.*;
import com.mobian.service.LjzGoodsServiceI;
import com.mobian.service.LjzOrderItemServiceI;
import com.mobian.service.LjzOrderServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mobian.util.MyBeanUtils;

@Service
public class LjzOrderServiceImpl extends BaseServiceImpl<LjzOrder> implements LjzOrderServiceI {

	@Autowired
	private LjzOrderDaoI ljzOrderDao;

	@Autowired
	private LjzOrderItemServiceI ljzOrderItemService;

	@Autowired
	private LjzGoodsServiceI ljzGoodsService;

	@Override
	public DataGrid dataGrid(LjzOrder ljzOrder, PageHelper ph) {
		List<LjzOrder> ol = new ArrayList<LjzOrder>();
		String hql = " from TljzOrder t ";
		DataGrid dg = dataGridQuery(hql, ph, ljzOrder, ljzOrderDao);
		@SuppressWarnings("unchecked")
		List<TljzOrder> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TljzOrder t : l) {
				LjzOrder o = new LjzOrder();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(LjzOrder ljzOrder, Map<String, Object> params) {
		String whereHql = "";	
		if (ljzOrder != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(ljzOrder.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", ljzOrder.getIsdeleted());
			}
			if (!F.empty(ljzOrder.getId())) {
				whereHql += " and t.id = :id";
				params.put("id", ljzOrder.getId());
			}
			if (!F.empty(ljzOrder.getShopId())) {
				whereHql += " and t.shopId = :shopId";
				params.put("shopId", ljzOrder.getShopId());
			}		
			if (!F.empty(ljzOrder.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", ljzOrder.getUserId());
			}		
			if (!F.empty(ljzOrder.getTotalPrice())) {
				whereHql += " and t.totalPrice = :totalPrice";
				params.put("totalPrice", ljzOrder.getTotalPrice());
			}		
			if (!F.empty(ljzOrder.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", ljzOrder.getStatus());
			}		
			if (!F.empty(ljzOrder.getDeliveryAddress())) {
				whereHql += " and t.deliveryAddress = :deliveryAddress";
				params.put("deliveryAddress", ljzOrder.getDeliveryAddress());
			}		
			if (!F.empty(ljzOrder.getContactPhone())) {
				whereHql += " and t.contactPhone = :contactPhone";
				params.put("contactPhone", ljzOrder.getContactPhone());
			}		
			if (!F.empty(ljzOrder.getContactPeople())) {
				whereHql += " and t.contactPeople = :contactPeople";
				params.put("contactPeople", ljzOrder.getContactPeople());
			}		
			if (!F.empty(ljzOrder.getPayStatus())) {
				whereHql += " and t.payStatus = :payStatus";
				params.put("payStatus", ljzOrder.getPayStatus());
			}		
			if (!F.empty(ljzOrder.getPayWay())) {
				whereHql += " and t.payWay = :payWay";
				params.put("payWay", ljzOrder.getPayWay());
			}		
			if (!F.empty(ljzOrder.getFreight())) {
				whereHql += " and t.freight = :freight";
				params.put("freight", ljzOrder.getFreight());
			}		
			if (!F.empty(ljzOrder.getRecommend())) {
				whereHql += " and t.recommend = :recommend";
				params.put("recommend", ljzOrder.getRecommend());
			}
			if(!F.empty(ljzOrder.getGoodsId())) {
				whereHql += " and t.id in (select orderId from TljzOrderItem i where i.goodsId = :goodsId and DATEDIFF(i.addtime, NOW()) = -1)";
				params.put("goodsId", ljzOrder.getGoodsId());
			}
		}	
		return whereHql;
	}

	@Override
	public void add(LjzOrder ljzOrder) {
		TljzOrder t = new TljzOrder();
		BeanUtils.copyProperties(ljzOrder, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		ljzOrderDao.save(t);
		ljzOrder.setId(t.getId());
	}

	@Override
	public LjzOrder get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TljzOrder t = ljzOrderDao.get("from TljzOrder t  where t.id = :id", params);
		LjzOrder o = new LjzOrder();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(LjzOrder ljzOrder) {
		TljzOrder t = ljzOrderDao.get(TljzOrder.class, ljzOrder.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(ljzOrder, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		ljzOrderDao.executeHql("update TljzOrder t set t.isdeleted = 1 where t.id = :id",params);
		//ljzOrderDao.delete(ljzOrderDao.get(TljzOrder.class, id));
	}

	@Override
	public void addOrder(LjzOrder order) {
		this.add(order);
		//订单商品信息
		List<LjzOrderItem> orderItemList = order.getOrderItemList();
		if(orderItemList != null && orderItemList.size() > 0) {
			for (LjzOrderItem orderItem : orderItemList) {
				orderItem.setOrderId(order.getId());
				ljzOrderItemService.add(orderItem);
				int r = ljzGoodsService.reduceGoodsCount(orderItem.getGoodsId(), orderItem.getQuantity());
				if(r < 1) {
					throw new ServiceException(String.format("%s,库存不足", orderItem.getGoodsTitle()));
				}
			}
		}
	}

	@Override
	public List<LjzOrder> query(LjzOrder ljzOrder) {
		List<LjzOrder> ol = new ArrayList<>();
		String hql = " from TljzOrder t ";
		@SuppressWarnings("unchecked")
		List<TljzOrder> l = query(hql, ljzOrder, ljzOrderDao);
		if (l != null && l.size() > 0) {
			for (TljzOrder t : l) {
				LjzOrder o = new LjzOrder();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public int getBuyQuantityByUserId(Integer userId, Integer goodsId) {
		String sql = "select sum(quantity) from ljz_order o " +
				"JOIN ljz_order_item oi on oi.order_id = o.id " +
				"where o.user_id = " + userId + " and o.`status` = 'OD02' " +
				"and oi.goods_id = " + goodsId + " and DATEDIFF(o.addtime, NOW()) = -1";
		BigInteger count = ljzOrderDao.countBySql(sql);
		return count == null ? 0 : count.intValue();
	}

}
