package com.mobian.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mobian.absx.F;
import com.mobian.dao.LjzOrderItemDaoI;
import com.mobian.model.TljzOrderItem;
import com.mobian.model.TljzWithdrawLog;
import com.mobian.pageModel.LjzOrderItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.LjzWithdrawLog;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.LjzOrderItemServiceI;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mobian.util.MyBeanUtils;

@Service
public class LjzOrderItemServiceImpl extends BaseServiceImpl<LjzOrderItem> implements LjzOrderItemServiceI {

	@Autowired
	private LjzOrderItemDaoI ljzOrderItemDao;

	@Override
	public DataGrid dataGrid(LjzOrderItem ljzOrderItem, PageHelper ph) {
		List<LjzOrderItem> ol = new ArrayList<LjzOrderItem>();
		String hql = " from TljzOrderItem t ";
		DataGrid dg = dataGridQuery(hql, ph, ljzOrderItem, ljzOrderItemDao);
		@SuppressWarnings("unchecked")
		List<TljzOrderItem> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TljzOrderItem t : l) {
				LjzOrderItem o = new LjzOrderItem();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(LjzOrderItem ljzOrderItem, Map<String, Object> params) {
		String whereHql = "";	
		if (ljzOrderItem != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(ljzOrderItem.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", ljzOrderItem.getIsdeleted());
			}		
			if (!F.empty(ljzOrderItem.getOrderId())) {
				whereHql += " and t.orderId = :orderId";
				params.put("orderId", ljzOrderItem.getOrderId());
			}		
			if (!F.empty(ljzOrderItem.getGoodsId())) {
				whereHql += " and t.goodsId = :goodsId";
				params.put("goodsId", ljzOrderItem.getGoodsId());
			}		
			if (!F.empty(ljzOrderItem.getQuantity())) {
				whereHql += " and t.quantity = :quantity";
				params.put("quantity", ljzOrderItem.getQuantity());
			}		
			if (!F.empty(ljzOrderItem.getBuyPrice())) {
				whereHql += " and t.buyPrice = :buyPrice";
				params.put("buyPrice", ljzOrderItem.getBuyPrice());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(LjzOrderItem ljzOrderItem) {
		TljzOrderItem t = new TljzOrderItem();
		BeanUtils.copyProperties(ljzOrderItem, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		ljzOrderItemDao.save(t);
	}

	@Override
	public LjzOrderItem get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TljzOrderItem t = ljzOrderItemDao.get("from TljzOrderItem t  where t.id = :id", params);
		LjzOrderItem o = new LjzOrderItem();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(LjzOrderItem ljzOrderItem) {
		TljzOrderItem t = ljzOrderItemDao.get(TljzOrderItem.class, ljzOrderItem.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(ljzOrderItem, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		ljzOrderItemDao.executeHql("update TljzOrderItem t set t.isdeleted = 1 where t.id = :id",params);
		//ljzOrderItemDao.delete(ljzOrderItemDao.get(TljzOrderItem.class, id));
	}

	@Override
	public List<LjzOrderItem> queryByOrderId(Integer orderId) {
		List<LjzOrderItem> ol = new ArrayList<>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		List<TljzOrderItem> l = ljzOrderItemDao.find("from TljzOrderItem t  where t.orderId = :orderId", params);
		if (CollectionUtils.isNotEmpty(l)) {
			for (TljzOrderItem t : l) {
				LjzOrderItem o = new LjzOrderItem();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

}
