package com.mobian.service.impl;

import java.math.BigDecimal;
import java.util.*;

import com.mobian.absx.F;
import com.mobian.dao.LjzPrizeLogDaoI;
import com.mobian.listener.Application;
import com.mobian.model.TljzPrizeLog;
import com.mobian.pageModel.*;
import com.mobian.service.*;

import com.mobian.thirdpart.wx.WeixinUtil;
import com.mobian.thirdpart.wx.message.req.templateMessage.TemplateData;
import com.mobian.thirdpart.wx.message.req.templateMessage.WxTemplate;
import com.mobian.util.DateUtil;
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

	@Autowired
	private LjzPaymentServiceI ljzPaymentService;

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
			if (ljzPrizeLog.getAddtimeStart() != null) {
				whereHql += " and t.addtime >= :addtimeStart";
				params.put("addtimeStart", ljzPrizeLog.getAddtimeStart());
			}
			if (ljzPrizeLog.getAddtimeEnd() != null) {
				whereHql += " and t.addtime <= :addtimeEnd";
				params.put("addtimeEnd", ljzPrizeLog.getAddtimeEnd());
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
		List<TljzPrizeLog> l = query(hql, prizeLog, ljzPrizeLogDao, "amount", "desc");
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
		Random random = new Random();

		// 获取商品昨日已支付订单
		LjzOrder ljzOrder = new LjzOrder();
		ljzOrder.setStatus("OD10");
		ljzOrder.setGoodsId(goods.getId());
		List<LjzOrder> orders = ljzOrderService.query(ljzOrder);
		if(CollectionUtils.isNotEmpty(orders)) { // 真实中奖
			Integer prizeNumber = goods.getPrizeNumber();
			if(F.empty(prizeNumber)) {
				prizeNumber = Integer.valueOf(Application.getString("SV200", "2"));
			}
			List<Integer> userIds = new ArrayList<>();
			BigDecimal saleTotalAmount = BigDecimal.ZERO;
			for(LjzOrder order : orders) {
				saleTotalAmount = saleTotalAmount.add(order.getTotalPrice().subtract(order.getFreight()));
				if(!userIds.contains(order.getUserId())) userIds.add(order.getUserId());
			}
			if(prizeNumber > userIds.size()) prizeNumber = userIds.size();
			// 奖池总金额
			BigDecimal prizeTotalAmount = saleTotalAmount.multiply(BigDecimal.valueOf(goods.getPrizePre())).divide(BigDecimal.valueOf(100), 0, BigDecimal.ROUND_HALF_UP);

			for(int i=0; i<prizeNumber; i++) {
				BigDecimal prizeAmount = prizeTotalAmount;
				if(prizeNumber > 1) {
					BigDecimal firstPre = new BigDecimal(Application.getString("SV203", "70"));
					BigDecimal firstPrizeAmount = prizeTotalAmount.multiply(firstPre).divide(BigDecimal.valueOf(100), 0, BigDecimal.ROUND_HALF_UP);
					if(i == 0) prizeAmount = firstPrizeAmount;
					else {
						prizeAmount = prizeTotalAmount.subtract(firstPrizeAmount).divide(BigDecimal.valueOf(prizeNumber-1), 0, BigDecimal.ROUND_HALF_UP);
					}
				}

				LjzOrder order = orders.get(random.nextInt(orders.size()));
				Integer userId = order.getUserId();

				// 获取用户中奖购买商品数量
				int quantity = ljzOrderService.getBuyQuantityByUserId(userId, goods.getId());
				// 新增消费中奖记录
				LjzPrizeLog prizeLog = new LjzPrizeLog();
				prizeLog.setUserId(userId);
				prizeLog.setGoodsId(goods.getId());
				prizeLog.setAmount(prizeAmount);
				prizeLog.setQuantity(quantity);
				prizeLog.setRemark(order.getId().toString());
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

				// 推送消息
				LjzPayment ljzPayment = ljzPaymentService.getByOrderId(order.getId());
				LjzUser user = ljzUserService.get(userId);
				Map<String, String> map = new HashMap<>();
				map.put("goodsId", goods.getId().toString());
				map.put("touser", user.getRefId());
				map.put("form_id", ljzPayment.getPrepayId());
				map.put("keyword1", goods.getTitle());
				map.put("keyword2", DateUtil.format(new Date(), "yyyy年MM月dd日"));
				map.put("keyword3", prizeAmount + "元");
				map.put("keyword4", "中奖金额已发放至您的资产钱包中");

				sendPrizeTemplateMessage(map);
			}
		} else { // 模拟中奖
			// 中奖金额
			String agAmountStr = Application.getString("SV201", "50-1000");
			String[] agAmounts = agAmountStr.split("-");
			int minAmount = Integer.valueOf(agAmounts[0]);
			int maxAmount = Integer.valueOf(agAmounts[1]);
			int amount = random.nextInt(maxAmount)%(maxAmount-minAmount+1) + minAmount;
			if(amount%10 != 0) {
				amount += 10-amount%10;
			}

			// 购买件数
			String agNumStr = Application.getString("SV202", "1-10");
			String[] agNums = agNumStr.split("-");
			int minNum = Integer.valueOf(agNums[0]);
			int maxNum = Integer.valueOf(agNums[1]);
			int num = random.nextInt(maxNum)%(maxNum-minNum+1) + minNum;

			// 查询所有模拟用户
			LjzUser ljzUser = new LjzUser();
			ljzUser.setRefType("ag"); // 模拟用户
			List<LjzUser> analogUsers = ljzUserService.query(ljzUser);
			if(CollectionUtils.isNotEmpty(analogUsers)) {
				Integer userId = analogUsers.get(random.nextInt(analogUsers.size())).getId();
				// 新增消费中奖记录
				LjzPrizeLog prizeLog = new LjzPrizeLog();
				prizeLog.setUserId(userId);
				prizeLog.setGoodsId(goods.getId());
				prizeLog.setAmount(BigDecimal.valueOf(amount));
				prizeLog.setQuantity(num);
				prizeLog.setRemark("模拟用户中奖");
				add(prizeLog);
			}
		}
	}

	private void sendPrizeTemplateMessage(Map<String, String> map) {
		WxTemplate temp = new WxTemplate();
		temp.setTouser(map.get("touser"));
		temp.setTemplate_id(WeixinUtil.PRIZE_TEMPLATE_ID);
		temp.setPage("page/component/index?id=" + map.get("goodsId"));
		temp.setForm_id(map.get("form_id"));

		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
		// 商品名称
		TemplateData keyword1 = new TemplateData();
		keyword1.setValue(map.get("keyword1"));
		data.put("keyword1", keyword1);
		// 中奖时间
		TemplateData keyword2 = new TemplateData();
		keyword2.setValue(map.get("keyword2"));
		data.put("keyword2", keyword2);
		// 中奖金额
		TemplateData keyword3 = new TemplateData();
		keyword3.setValue(map.get("keyword3"));
		data.put("keyword3", keyword3);
		// 备注
		TemplateData keyword4 = new TemplateData();
		keyword4.setValue(map.get("keyword4"));
		data.put("keyword4", keyword4);

		temp.setData(data);

		WeixinUtil.sendTemplateMessage(temp);
	}

}
