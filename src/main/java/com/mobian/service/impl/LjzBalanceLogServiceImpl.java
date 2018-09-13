package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.LjzBalanceDaoI;
import com.mobian.dao.LjzBalanceLogDaoI;
import com.mobian.exception.ServiceException;
import com.mobian.model.TljzBalanceLog;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.LjzBalance;
import com.mobian.pageModel.LjzBalanceLog;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.LjzBalanceLogServiceI;
import com.mobian.service.LjzBalanceServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LjzBalanceLogServiceImpl extends BaseServiceImpl<LjzBalanceLog> implements LjzBalanceLogServiceI {

	@Autowired
	private LjzBalanceLogDaoI ljzBalanceLogDao;

	@Autowired
	private LjzBalanceServiceI ljzBalanceService;

	@Autowired
	private LjzBalanceDaoI ljzBalanceDao;

	@Override
	public DataGrid dataGrid(LjzBalanceLog ljzBalanceLog, PageHelper ph) {
		List<LjzBalanceLog> ol = new ArrayList<LjzBalanceLog>();
		String hql = " from TljzBalanceLog t ";
		DataGrid dg = dataGridQuery(hql, ph, ljzBalanceLog, ljzBalanceLogDao);
		@SuppressWarnings("unchecked")
		List<TljzBalanceLog> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TljzBalanceLog t : l) {
				LjzBalanceLog o = new LjzBalanceLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(LjzBalanceLog ljzBalanceLog, Map<String, Object> params) {
		String whereHql = "";	
		if (ljzBalanceLog != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(ljzBalanceLog.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", ljzBalanceLog.getIsdeleted());
			}		
			if (!F.empty(ljzBalanceLog.getBalanceId())) {
				whereHql += " and t.balanceId = :balanceId";
				params.put("balanceId", ljzBalanceLog.getBalanceId());
			}		
			if (!F.empty(ljzBalanceLog.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", ljzBalanceLog.getAmount());
			}		
			if (!F.empty(ljzBalanceLog.getRefId())) {
				whereHql += " and t.refId = :refId";
				params.put("refId", ljzBalanceLog.getRefId());
			}		
			if (!F.empty(ljzBalanceLog.getRefType())) {
				whereHql += " and t.refType = :refType";
				params.put("refType", ljzBalanceLog.getRefType());
			}		
			if (!F.empty(ljzBalanceLog.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", ljzBalanceLog.getRemark());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(LjzBalanceLog ljzBalanceLog) {
		TljzBalanceLog t = new TljzBalanceLog();
		BeanUtils.copyProperties(ljzBalanceLog, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		ljzBalanceLogDao.save(t);
	}

	@Override
	public void addLogAndUpdateBalance(LjzBalanceLog mbBalanceLog) {
		if (mbBalanceLog.getAmount() == null) {
			throw new ServiceException("余额不允许为null");
		}
		LjzBalance balance = ljzBalanceService.addOrGetBalance(mbBalanceLog.getUserId());
		BigDecimal amount = ljzBalanceDao.getAmountById(balance.getId());
		mbBalanceLog.setBalanceId(balance.getId());
		mbBalanceLog.setBalanceAmount(amount.add(mbBalanceLog.getAmount()));
		add(mbBalanceLog);

		int i = ljzBalanceDao.executeHql("update TljzBalance t set t.amount=amount+" + mbBalanceLog.getAmount() + " where t.id=" + mbBalanceLog.getBalanceId());
		if (i != 1) {
			throw new ServiceException("余额更新失败");
		}
	}

	@Override
	public LjzBalanceLog get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TljzBalanceLog t = ljzBalanceLogDao.get("from TljzBalanceLog t  where t.id = :id", params);
		LjzBalanceLog o = new LjzBalanceLog();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(LjzBalanceLog ljzBalanceLog) {
		TljzBalanceLog t = ljzBalanceLogDao.get(TljzBalanceLog.class, ljzBalanceLog.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(ljzBalanceLog, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		ljzBalanceLogDao.executeHql("update TljzBalanceLog t set t.isdeleted = 1 where t.id = :id",params);
		//ljzBalanceLogDao.delete(ljzBalanceLogDao.get(TljzBalanceLog.class, id));
	}

	@Override
	public List<LjzBalanceLog> query(LjzBalanceLog balanceLog) {
		List<LjzBalanceLog> ol = new ArrayList<>();
		String hql = " from TljzBalanceLog t ";
		@SuppressWarnings("unchecked")
		List<TljzBalanceLog> l = query(hql, balanceLog, ljzBalanceLogDao, "addtime", "desc");
		if (l != null && l.size() > 0) {
			for (TljzBalanceLog t : l) {
				LjzBalanceLog o = new LjzBalanceLog();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

}
