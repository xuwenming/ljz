package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.LjzPaymentDaoI;
import com.mobian.model.TljzPayment;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class LjzPaymentServiceImpl extends BaseServiceImpl<LjzPayment> implements LjzPaymentServiceI {

	@Autowired
	private LjzPaymentDaoI ljzPaymentDao;

	@Autowired
	private LjzPaymentItemServiceI ljzPaymentItemService;

	@Autowired
	private LjzOrderServiceI ljzOrderService;

	@Autowired
	private LjzBalanceLogServiceI ljzBalanceLogService;

	@Autowired
	private LjzOrderItemServiceI ljzOrderItemService;

	@Autowired
	private LjzGoodsServiceI ljzGoodsService;

	@Autowired
	private LjzBalanceServiceI ljzBalanceService;

	@Override
	public DataGrid dataGrid(LjzPayment ljzPayment, PageHelper ph) {
		List<LjzPayment> ol = new ArrayList<LjzPayment>();
		String hql = " from TljzPayment t ";
		DataGrid dg = dataGridQuery(hql, ph, ljzPayment, ljzPaymentDao);
		@SuppressWarnings("unchecked")
		List<TljzPayment> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TljzPayment t : l) {
				LjzPayment o = new LjzPayment();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(LjzPayment ljzPayment, Map<String, Object> params) {
		String whereHql = "";	
		if (ljzPayment != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(ljzPayment.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", ljzPayment.getIsdeleted());
			}		
			if (!F.empty(ljzPayment.getOrderId())) {
				whereHql += " and t.orderId = :orderId";
				params.put("orderId", ljzPayment.getOrderId());
			}		
			if (!F.empty(ljzPayment.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", ljzPayment.getAmount());
			}		
			if (!F.empty(ljzPayment.getPayWay())) {
				whereHql += " and t.payWay = :payWay";
				params.put("payWay", ljzPayment.getPayWay());
			}		
			if (!F.empty(ljzPayment.getStatus())) {
				whereHql += " and t.status = :status";
				params.put("status", ljzPayment.getStatus());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(LjzPayment ljzPayment) {
		TljzPayment t = new TljzPayment();
		BeanUtils.copyProperties(ljzPayment, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		ljzPaymentDao.save(t);
		ljzPayment.setId(t.getId());
	}

	@Override
	public LjzPayment get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TljzPayment t = ljzPaymentDao.get("from TljzPayment t  where t.id = :id", params);
		LjzPayment o = new LjzPayment();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(LjzPayment ljzPayment) {
		TljzPayment t = ljzPaymentDao.get(TljzPayment.class, ljzPayment.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(ljzPayment, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		ljzPaymentDao.executeHql("update TljzPayment t set t.isdeleted = 1 where t.id = :id",params);
		//ljzPaymentDao.delete(ljzPaymentDao.get(TljzPayment.class, id));
	}

	@Override
	public LjzPayment getByOrderId(Integer orderId) {
		TljzPayment t = ljzPaymentDao.get("from TljzPayment t where t.orderId = " + orderId);
		if (t != null) {
			LjzPayment o = new LjzPayment();
			BeanUtils.copyProperties(t, o);
			return o;
		}
		return null;
	}

	@Override
	public void addOrUpdate(LjzPayment payment) {
		LjzPayment paymentQ = getByOrderId(payment.getOrderId());
		boolean transformFlag = true;
		if(paymentQ == null) {
			payment.setStatus(true);
			add(payment);
			paymentQ = payment;
		} else {
			if(paymentQ.getStatus()) transformFlag = false; // 防止重复支付
			payment.setId(paymentQ.getId());
			edit(payment);
		}

		LjzPaymentItem paymentItem = ljzPaymentItemService.getByPaymentId(paymentQ.getId());
		if(paymentItem == null) {
			paymentItem = new LjzPaymentItem();
			paymentItem.setPaymentId(paymentQ.getId());
			paymentItem.setPayWay(paymentQ.getPayWay());
			paymentItem.setAmount(paymentQ.getAmount());
			paymentItem.setRefId(payment.getRefId());
			ljzPaymentItemService.add(paymentItem);
		} else {
			paymentItem.setRefId(payment.getRefId());
			ljzPaymentItemService.edit(paymentItem);
		}

		if(transformFlag) {
			LjzOrder order = ljzOrderService.get(payment.getOrderId());
			// 1、修改订单状态
			order.setStatus("OD10"); // 支付成功
			order.setPayStatus("PS02");
			order.setPayTime(new Date());
			ljzOrderService.edit(order);

			// 2、添加店铺订单收入
			LjzBalance shopBalance = ljzBalanceService.addOrGetBalance(order.getShopId(), 1, BigDecimal.ZERO);
			LjzBalanceLog balanceLog = new LjzBalanceLog();
			balanceLog.setBalanceId(shopBalance.getId());
			balanceLog.setUserId(order.getShopId());
			balanceLog.setRefType("BBT001"); // 订单收入
			balanceLog.setRefId(order.getId());
			balanceLog.setAmount(order.getTotalPrice());
			ljzBalanceLogService.addLogAndUpdateBalance(balanceLog, 1);

			if(!F.empty(order.getRecommend())) {
				List<LjzOrderItem> orderItems = ljzOrderItemService.queryByOrderId(order.getId());
				for(LjzOrderItem orderItem : orderItems) {
					LjzGoods goods = ljzGoodsService.get(orderItem.getGoodsId());
					if(!F.empty(goods.getShareAmount()) && goods.getShareAmount().doubleValue() != 0) {
						// 3、新增推荐人转发赚取
						balanceLog = new LjzBalanceLog();
						balanceLog.setUserId(order.getRecommend());
						balanceLog.setRefType("BBT005"); // 转发赚取
						balanceLog.setRefId(goods.getId());
						balanceLog.setRemark(order.getId().toString()); // 记录订单id
						balanceLog.setAmount(goods.getShareAmount());
						ljzBalanceLogService.addLogAndUpdateBalance(balanceLog);

						// 4、扣除店铺转发赚取
						balanceLog = new LjzBalanceLog();
						balanceLog.setUserId(order.getShopId());
						balanceLog.setRefType("BBT003"); // 转发赚取支出
						balanceLog.setRefId(goods.getId());
						balanceLog.setAmount(goods.getShareAmount().multiply(BigDecimal.valueOf(-1)));
						balanceLog.setRemark(order.getId().toString()); // 记录订单id
						ljzBalanceLogService.addLogAndUpdateBalance(balanceLog, 1);
					}
				}
			}

		}
	}

}
