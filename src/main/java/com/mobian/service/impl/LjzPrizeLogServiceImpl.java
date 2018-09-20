package com.mobian.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.mobian.absx.F;
import com.mobian.dao.LjzPrizeLogDaoI;
import com.mobian.listener.Application;
import com.mobian.model.TljzPrizeLog;
import com.mobian.pageModel.*;
import com.mobian.service.LjzBalanceLogServiceI;
import com.mobian.service.LjzOrderServiceI;
import com.mobian.service.LjzPrizeLogServiceI;

import com.mobian.service.LjzUserServiceI;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mobian.util.MyBeanUtils;

@Service
public class LjzPrizeLogServiceImpl extends BaseServiceImpl<LjzPrizeLog> implements LjzPrizeLogServiceI {

	@Autowired
	private LjzPrizeLogDaoI ljzPrizeLogDao;

	@Autowired
	private LjzOrderServiceI ljzOrderService;

	@Autowired
	private LjzUserServiceI ljzUserService;

	@Autowired
	private LjzBalanceLogServiceI ljzBalanceLogService;

	@Override
	public DataGrid dataGrid(LjzPrizeLog ljzPrizeLog, PageHelper ph) {
		List<LjzPrizeLog> ol = new ArrayList<LjzPrizeLog>();
		String hql = " from TljzPrizeLog t ";
		DataGrid dg = dataGridQuery(hql, ph, ljzPrizeLog, ljzPrizeLogDao);
		@SuppressWarnings("unchecked")
		List<TljzPrizeLog> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TljzPrizeLog t : l) {
				LjzPrizeLog o = new LjzPrizeLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(LjzPrizeLog ljzPrizeLog, Map<String, Object> params) {
		String whereHql = "";	
		if (ljzPrizeLog != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(ljzPrizeLog.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", ljzPrizeLog.getIsdeleted());
			}		
			if (!F.empty(ljzPrizeLog.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", ljzPrizeLog.getUserId());
			}		
			if (!F.empty(ljzPrizeLog.getGoodsId())) {
				whereHql += " and t.goodsId = :goodsId";
				params.put("goodsId", ljzPrizeLog.getGoodsId());
			}		
			if (!F.empty(ljzPrizeLog.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", ljzPrizeLog.getAmount());
			}		
			if (!F.empty(ljzPrizeLog.getQuantity())) {
				whereHql += " and t.quantity = :quantity";
				params.put("quantity", ljzPrizeLog.getQuantity());
			}
			if(!F.empty(ljzPrizeLog.getToday()) && ljzPrizeLog.getToday()) {
				whereHql += " and DATEDIFF(t.addtime, NOW()) = 0";
			}
		}	
		return whereHql;
	}

	@Override
	public void add(LjzPrizeLog ljzPrizeLog) {
		TljzPrizeLog t = new TljzPrizeLog();
		BeanUtils.copyProperties(ljzPrizeLog, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		ljzPrizeLogDao.save(t);
		ljzPrizeLog.setId(t.getId());
	}

	@Override
	public LjzPrizeLog get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TljzPrizeLog t = ljzPrizeLogDao.get("from TljzPrizeLog t  where t.id = :id", params);
		LjzPrizeLog o = new LjzPrizeLog();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(LjzPrizeLog ljzPrizeLog) {
		TljzPrizeLog t = ljzPrizeLogDao.get(TljzPrizeLog.class, ljzPrizeLog.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(ljzPrizeLog, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		ljzPrizeLogDao.executeHql("update TljzPrizeLog t set t.isdeleted = 1 where t.id = :id",params);
		//ljzPrizeLogDao.delete(ljzPrizeLogDao.get(TljzPrizeLog.class, id));
	}

	@Override
	public List<LjzPrizeLog> query(LjzPrizeLog prizeLog) {
		List<LjzPrizeLog> ol = new ArrayList<>();
		String hql = " from TljzPrizeLog t ";
		@SuppressWarnings("unchecked")
		List<TljzPrizeLog> l = query(hql, prizeLog, ljzPrizeLogDao, "quantity", "desc");
		if (l != null && l.size() > 0) {
			for (TljzPrizeLog t : l) {
				LjzPrizeLog o = new LjzPrizeLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public void addPrizeLogByGoods(LjzGoods goods) {
		List<Integer> userIds = new ArrayList<>();
		Integer prizeNumber = goods.getPrizeNumber();
		if(F.empty(prizeNumber)) {
			prizeNumber = Integer.valueOf(Application.getString("SV200", "2"));
		}
		// 获取商品昨日已支付订单
		LjzOrder ljzOrder = new LjzOrder();
		ljzOrder.setStatus("OD02");
		ljzOrder.setGoodsId(goods.getId());
		List<LjzOrder> orders = ljzOrderService.query(ljzOrder);
		BigDecimal saleTotalAmount = BigDecimal.ZERO;
		if(CollectionUtils.isNotEmpty(orders)) {
			for(LjzOrder order : orders) {
				saleTotalAmount = saleTotalAmount.add(order.getTotalPrice());
			}
		} else {
			saleTotalAmount = new BigDecimal(Application.getString("SV201", "100"));
		}

		BigDecimal prizeAmount = saleTotalAmount.divide(BigDecimal.valueOf(prizeNumber), 0, BigDecimal.ROUND_HALF_UP);

		Random random = new Random();
		if(CollectionUtils.isNotEmpty(orders)) {
			for(int i=0; i<prizeNumber; i++) {
				LjzOrder order = orders.get(random.nextInt(orders.size()));
				Integer userId = order.getUserId();
				if(!userIds.contains(userId)) { // 真实用户中奖
					userIds.add(userId);

					// 获取用户中奖购买商品数量
					int quantit = ljzOrderService.getBuyQuantityByUserId(userId, goods.getId());
					// 新增消费中奖记录
					LjzPrizeLog prizeLog = new LjzPrizeLog();
					prizeLog.setUserId(userId);
					prizeLog.setGoodsId(goods.getId());
					prizeLog.setAmount(prizeAmount);
					prizeLog.setQuantity(quantit);
					add(prizeLog);

					// 新增用户消费中奖
					LjzBalanceLog balanceLog = new LjzBalanceLog();
					balanceLog.setUserId(userId);
					balanceLog.setRefType("BBT004"); // 消费中奖
					balanceLog.setRefId(prizeLog.getId());
					balanceLog.setAmount(prizeAmount);
					ljzBalanceLogService.addLogAndUpdateBalance(balanceLog);

					// 4、扣除店铺消费中奖
					balanceLog = new LjzBalanceLog();
					balanceLog.setUserId(order.getShopId());
					balanceLog.setRefType("BBT002"); // 消费中奖支出
					balanceLog.setRefId(prizeLog.getId());
					balanceLog.setAmount(prizeAmount.multiply(BigDecimal.valueOf(-1)));
					ljzBalanceLogService.addLogAndUpdateBalance(balanceLog, 1);
				}

			}
		}
		int size = userIds.size();
		if(size < prizeNumber) { // 模拟用户中奖
			// 查询所有模拟用户
			LjzUser ljzUser = new LjzUser();
			ljzUser.setRefType("ag"); // 模拟用户
			List<LjzUser> analogUsers = ljzUserService.query(ljzUser);
			if(CollectionUtils.isNotEmpty(analogUsers)) {
				for(int i=0; i<prizeNumber-size; i++) {
					Integer userId = analogUsers.get(random.nextInt(analogUsers.size())).getId();
					if(!userIds.contains(userId)) {
						// 新增消费中奖记录
						LjzPrizeLog prizeLog = new LjzPrizeLog();
						prizeLog.setUserId(userId);
						prizeLog.setGoodsId(goods.getId());
						prizeLog.setAmount(prizeAmount);
						prizeLog.setQuantity(1);
						prizeLog.setRemark("模拟用户中奖");
						add(prizeLog);
					}
				}
			}
		}
	}

}
