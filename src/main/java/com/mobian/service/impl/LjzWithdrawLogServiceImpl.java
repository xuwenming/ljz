package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.LjzWithdrawLogDaoI;
import com.mobian.model.TljzWithdrawLog;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.LjzBalanceLog;
import com.mobian.pageModel.LjzWithdrawLog;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.LjzBalanceLogServiceI;
import com.mobian.service.LjzWithdrawLogServiceI;
import com.mobian.util.MyBeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LjzWithdrawLogServiceImpl extends BaseServiceImpl<LjzWithdrawLog> implements LjzWithdrawLogServiceI {

	@Autowired
	private LjzWithdrawLogDaoI ljzWithdrawLogDao;

	@Autowired
	private LjzBalanceLogServiceI ljzBalanceLogService;

	@Override
	public DataGrid dataGrid(LjzWithdrawLog ljzWithdrawLog, PageHelper ph) {
		List<LjzWithdrawLog> ol = new ArrayList<LjzWithdrawLog>();
		String hql = " from TljzWithdrawLog t ";
		DataGrid dg = dataGridQuery(hql, ph, ljzWithdrawLog, ljzWithdrawLogDao);
		@SuppressWarnings("unchecked")
		List<TljzWithdrawLog> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TljzWithdrawLog t : l) {
				LjzWithdrawLog o = new LjzWithdrawLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(LjzWithdrawLog ljzWithdrawLog, Map<String, Object> params) {
		String whereHql = "";	
		if (ljzWithdrawLog != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(ljzWithdrawLog.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", ljzWithdrawLog.getIsdeleted());
			}		
			if (!F.empty(ljzWithdrawLog.getWithdrawNo())) {
				whereHql += " and t.withdrawNo = :withdrawNo";
				params.put("withdrawNo", ljzWithdrawLog.getWithdrawNo());
			}		
			if (!F.empty(ljzWithdrawLog.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", ljzWithdrawLog.getAmount());
			}		
			if (!F.empty(ljzWithdrawLog.getServiceAmt())) {
				whereHql += " and t.serviceAmt = :serviceAmt";
				params.put("serviceAmt", ljzWithdrawLog.getServiceAmt());
			}		
			if (!F.empty(ljzWithdrawLog.getUserId())) {
				whereHql += " and t.userId = :userId";
				params.put("userId", ljzWithdrawLog.getUserId());
			}		
			if (!F.empty(ljzWithdrawLog.getRealName())) {
				whereHql += " and t.realName = :realName";
				params.put("realName", ljzWithdrawLog.getRealName());
			}		
			if (!F.empty(ljzWithdrawLog.getPhone())) {
				whereHql += " and t.phone = :phone";
				params.put("phone", ljzWithdrawLog.getPhone());
			}		
			if (!F.empty(ljzWithdrawLog.getContent())) {
				whereHql += " and t.content = :content";
				params.put("content", ljzWithdrawLog.getContent());
			}		
			if (!F.empty(ljzWithdrawLog.getHandleStatus())) {
				whereHql += " and t.handleStatus = :handleStatus";
				params.put("handleStatus", ljzWithdrawLog.getHandleStatus());
			}		
			if (!F.empty(ljzWithdrawLog.getHandleLoginId())) {
				whereHql += " and t.handleLoginId = :handleLoginId";
				params.put("handleLoginId", ljzWithdrawLog.getHandleLoginId());
			}		
			if (!F.empty(ljzWithdrawLog.getHandleRemark())) {
				whereHql += " and t.handleRemark = :handleRemark";
				params.put("handleRemark", ljzWithdrawLog.getHandleRemark());
			}		
			if (!F.empty(ljzWithdrawLog.getPaymentNo())) {
				whereHql += " and t.paymentNo = :paymentNo";
				params.put("paymentNo", ljzWithdrawLog.getPaymentNo());
			}		
			if (!F.empty(ljzWithdrawLog.getCmmsAmt())) {
				whereHql += " and t.cmmsAmt = :cmmsAmt";
				params.put("cmmsAmt", ljzWithdrawLog.getCmmsAmt());
			}		
			if (!F.empty(ljzWithdrawLog.getRefType())) {
				whereHql += " and t.refType = :refType";
				params.put("refType", ljzWithdrawLog.getRefType());
			}		
			if (!F.empty(ljzWithdrawLog.getApplyLoginIp())) {
				whereHql += " and t.applyLoginIp = :applyLoginIp";
				params.put("applyLoginIp", ljzWithdrawLog.getApplyLoginIp());
			}
			if (ljzWithdrawLog.getAddtime() != null) {
				whereHql += " and to_days(t.addtime) = to_days(now())";
			}
		}	
		return whereHql;
	}

	@Override
	public void add(LjzWithdrawLog ljzWithdrawLog) {
		TljzWithdrawLog t = new TljzWithdrawLog();
		BeanUtils.copyProperties(ljzWithdrawLog, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		ljzWithdrawLogDao.save(t);
		ljzWithdrawLog.setId(t.getId());
	}

	@Override
	public LjzWithdrawLog get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TljzWithdrawLog t = ljzWithdrawLogDao.get("from TljzWithdrawLog t  where t.id = :id", params);
		LjzWithdrawLog o = new LjzWithdrawLog();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(LjzWithdrawLog ljzWithdrawLog) {
		TljzWithdrawLog t = ljzWithdrawLogDao.get(TljzWithdrawLog.class, ljzWithdrawLog.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(ljzWithdrawLog, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		ljzWithdrawLogDao.executeHql("update TljzWithdrawLog t set t.isdeleted = 1 where t.id = :id",params);
		//ljzWithdrawLogDao.delete(ljzWithdrawLogDao.get(TljzWithdrawLog.class, id));
	}

	@Override
	public LjzWithdrawLog get(LjzWithdrawLog log) {
		String hql = " from TljzWithdrawLog t ";
		@SuppressWarnings("unchecked")
		List<TljzWithdrawLog> l = query(hql, log, ljzWithdrawLogDao, "addtime", "desc");
		LjzWithdrawLog o = null;
		if (CollectionUtils.isNotEmpty(l)) {
			o = new LjzWithdrawLog();
			BeanUtils.copyProperties(l.get(0), o);
		}
		return o;
	}

	@Override
	public List<LjzWithdrawLog> query(LjzWithdrawLog log) {
		List<LjzWithdrawLog> ol = new ArrayList<>();
		String hql = " from TljzWithdrawLog t ";
		@SuppressWarnings("unchecked")
		List<TljzWithdrawLog> l = query(hql, log, ljzWithdrawLogDao, "addtime", "desc");
		if (CollectionUtils.isNotEmpty(l)) {
			for (TljzWithdrawLog t : l) {
				LjzWithdrawLog o = new LjzWithdrawLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

	@Override
	public void addAndBalance(LjzWithdrawLog withdrawLog) {
		add(withdrawLog);

		// 提现扣除钱包余额
		LjzBalanceLog balanceLog = new LjzBalanceLog();
		balanceLog.setUserId(withdrawLog.getUserId());
		balanceLog.setRefType(withdrawLog.getRefType());
		balanceLog.setRefId(withdrawLog.getId());
		balanceLog.setAmount(withdrawLog.getAmount().multiply(BigDecimal.valueOf(-1)));
		ljzBalanceLogService.addLogAndUpdateBalance(balanceLog);

		// 提现手续费扣除钱包余额
		if(withdrawLog.getServiceAmt() != null && withdrawLog.getServiceAmt().doubleValue() > 0) {
			balanceLog = new LjzBalanceLog();
			balanceLog.setUserId(withdrawLog.getUserId());
			balanceLog.setRefType("BBT008");
			balanceLog.setRefId(withdrawLog.getId());
			balanceLog.setAmount(withdrawLog.getServiceAmt().multiply(BigDecimal.valueOf(-1)));
			ljzBalanceLogService.addLogAndUpdateBalance(balanceLog);
		}
	}

}
