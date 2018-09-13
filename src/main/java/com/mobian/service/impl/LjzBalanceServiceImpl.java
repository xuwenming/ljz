package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.LjzBalanceDaoI;
import com.mobian.exception.ServiceException;
import com.mobian.model.TljzBalance;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.LjzBalance;
import com.mobian.pageModel.PageHelper;
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
public class LjzBalanceServiceImpl extends BaseServiceImpl<LjzBalance> implements LjzBalanceServiceI {

	@Autowired
	private LjzBalanceDaoI ljzBalanceDao;

	@Override
	public DataGrid dataGrid(LjzBalance ljzBalance, PageHelper ph) {
		List<LjzBalance> ol = new ArrayList<LjzBalance>();
		String hql = " from TljzBalance t ";
		DataGrid dg = dataGridQuery(hql, ph, ljzBalance, ljzBalanceDao);
		@SuppressWarnings("unchecked")
		List<TljzBalance> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TljzBalance t : l) {
				LjzBalance o = new LjzBalance();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(LjzBalance ljzBalance, Map<String, Object> params) {
		String whereHql = "";	
		if (ljzBalance != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(ljzBalance.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", ljzBalance.getIsdeleted());
			}		
			if (!F.empty(ljzBalance.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", ljzBalance.getAmount());
			}		
			if (!F.empty(ljzBalance.getRefId())) {
				whereHql += " and t.refId = :refId";
				params.put("refId", ljzBalance.getRefId());
			}		
			if (!F.empty(ljzBalance.getRefType())) {
				whereHql += " and t.refType = :refType";
				params.put("refType", ljzBalance.getRefType());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(LjzBalance ljzBalance) {
		TljzBalance t = new TljzBalance();
		BeanUtils.copyProperties(ljzBalance, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		ljzBalanceDao.save(t);
	}

	@Override
	public LjzBalance get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TljzBalance t = ljzBalanceDao.get("from TljzBalance t  where t.id = :id", params);
		LjzBalance o = new LjzBalance();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(LjzBalance ljzBalance) {
		TljzBalance t = ljzBalanceDao.get(TljzBalance.class, ljzBalance.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(ljzBalance, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		ljzBalanceDao.executeHql("update TljzBalance t set t.isdeleted = 1 where t.id = :id",params);
		//ljzBalanceDao.delete(ljzBalanceDao.get(TljzBalance.class, id));
	}

	@Override
	public LjzBalance addOrGetBalance(Integer refId) {
		return addOrGetBalance(refId, 2, BigDecimal.ZERO);
	}

	@Override
	public LjzBalance addOrGetBalance(Integer refId, Integer refType, BigDecimal initAmount) {
		LjzBalance o;
		TljzBalance t = ljzBalanceDao.get("from TljzBalance t where t.isdeleted = 0 and t.refType = " + refType + " and refId=" + refId);
		if(t != null && t.getId() != null) {
			o = new LjzBalance();
			BeanUtils.copyProperties(t, o);
		} else {
			if(refId == null)
				throw new ServiceException("refId 不能为空");
			if(refType == null)
				throw new ServiceException("refType 不能为空");
			if(refType == null)
				throw new ServiceException("initAmount 不能为空");
			o = new LjzBalance();
			o.setAmount(initAmount);
			o.setRefId(refId);
			o.setRefType(refType);
			add(o);
		}
		return o;
	}

}
