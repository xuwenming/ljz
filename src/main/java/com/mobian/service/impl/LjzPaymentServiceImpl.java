package com.mobian.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mobian.absx.F;
import com.mobian.dao.LjzPaymentDaoI;
import com.mobian.model.TljzPayment;
import com.mobian.pageModel.LjzPayment;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.LjzPaymentServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mobian.util.MyBeanUtils;

@Service
public class LjzPaymentServiceImpl extends BaseServiceImpl<LjzPayment> implements LjzPaymentServiceI {

	@Autowired
	private LjzPaymentDaoI ljzPaymentDao;

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

}
