package com.mobian.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mobian.absx.F;
import com.mobian.dao.LjzPaymentItemDaoI;
import com.mobian.model.TljzPaymentItem;
import com.mobian.pageModel.LjzPaymentItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.LjzPaymentItemServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mobian.util.MyBeanUtils;

@Service
public class LjzPaymentItemServiceImpl extends BaseServiceImpl<LjzPaymentItem> implements LjzPaymentItemServiceI {

	@Autowired
	private LjzPaymentItemDaoI ljzPaymentItemDao;

	@Override
	public DataGrid dataGrid(LjzPaymentItem ljzPaymentItem, PageHelper ph) {
		List<LjzPaymentItem> ol = new ArrayList<LjzPaymentItem>();
		String hql = " from TljzPaymentItem t ";
		DataGrid dg = dataGridQuery(hql, ph, ljzPaymentItem, ljzPaymentItemDao);
		@SuppressWarnings("unchecked")
		List<TljzPaymentItem> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TljzPaymentItem t : l) {
				LjzPaymentItem o = new LjzPaymentItem();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(LjzPaymentItem ljzPaymentItem, Map<String, Object> params) {
		String whereHql = "";	
		if (ljzPaymentItem != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(ljzPaymentItem.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", ljzPaymentItem.getIsdeleted());
			}		
			if (!F.empty(ljzPaymentItem.getPaymentId())) {
				whereHql += " and t.paymentId = :paymentId";
				params.put("paymentId", ljzPaymentItem.getPaymentId());
			}		
			if (!F.empty(ljzPaymentItem.getAmount())) {
				whereHql += " and t.amount = :amount";
				params.put("amount", ljzPaymentItem.getAmount());
			}		
			if (!F.empty(ljzPaymentItem.getPayWay())) {
				whereHql += " and t.payWay = :payWay";
				params.put("payWay", ljzPaymentItem.getPayWay());
			}		
			if (!F.empty(ljzPaymentItem.getRefId())) {
				whereHql += " and t.refId = :refId";
				params.put("refId", ljzPaymentItem.getRefId());
			}		
			if (!F.empty(ljzPaymentItem.getRemark())) {
				whereHql += " and t.remark = :remark";
				params.put("remark", ljzPaymentItem.getRemark());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(LjzPaymentItem ljzPaymentItem) {
		TljzPaymentItem t = new TljzPaymentItem();
		BeanUtils.copyProperties(ljzPaymentItem, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		ljzPaymentItemDao.save(t);
	}

	@Override
	public LjzPaymentItem get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TljzPaymentItem t = ljzPaymentItemDao.get("from TljzPaymentItem t  where t.id = :id", params);
		LjzPaymentItem o = new LjzPaymentItem();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(LjzPaymentItem ljzPaymentItem) {
		TljzPaymentItem t = ljzPaymentItemDao.get(TljzPaymentItem.class, ljzPaymentItem.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(ljzPaymentItem, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		ljzPaymentItemDao.executeHql("update TljzPaymentItem t set t.isdeleted = 1 where t.id = :id",params);
		//ljzPaymentItemDao.delete(ljzPaymentItemDao.get(TljzPaymentItem.class, id));
	}

}
