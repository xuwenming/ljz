package com.mobian.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mobian.absx.F;
import com.mobian.dao.LjzPrizeLogDaoI;
import com.mobian.model.TljzPrizeLog;
import com.mobian.pageModel.LjzPrizeLog;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.LjzPrizeLogServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mobian.util.MyBeanUtils;

@Service
public class LjzPrizeLogServiceImpl extends BaseServiceImpl<LjzPrizeLog> implements LjzPrizeLogServiceI {

	@Autowired
	private LjzPrizeLogDaoI ljzPrizeLogDao;

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

}
