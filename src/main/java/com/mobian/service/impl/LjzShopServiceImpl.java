package com.mobian.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mobian.absx.F;
import com.mobian.dao.LjzShopDaoI;
import com.mobian.model.TljzShop;
import com.mobian.pageModel.LjzShop;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.LjzShopServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mobian.util.MyBeanUtils;

@Service
public class LjzShopServiceImpl extends BaseServiceImpl<LjzShop> implements LjzShopServiceI {

	@Autowired
	private LjzShopDaoI ljzShopDao;

	@Override
	public DataGrid dataGrid(LjzShop ljzShop, PageHelper ph) {
		List<LjzShop> ol = new ArrayList<LjzShop>();
		String hql = " from TljzShop t ";
		DataGrid dg = dataGridQuery(hql, ph, ljzShop, ljzShopDao);
		@SuppressWarnings("unchecked")
		List<TljzShop> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TljzShop t : l) {
				LjzShop o = new LjzShop();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(LjzShop ljzShop, Map<String, Object> params) {
		String whereHql = "";	
		if (ljzShop != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(ljzShop.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", ljzShop.getIsdeleted());
			}		
			if (!F.empty(ljzShop.getName())) {
				whereHql += " and t.name = :name";
				params.put("name", ljzShop.getName());
			}		
			if (!F.empty(ljzShop.getAddress())) {
				whereHql += " and t.address = :address";
				params.put("address", ljzShop.getAddress());
			}		
			if (!F.empty(ljzShop.getContactPhone())) {
				whereHql += " and t.contactPhone = :contactPhone";
				params.put("contactPhone", ljzShop.getContactPhone());
			}		
			if (!F.empty(ljzShop.getContactPeople())) {
				whereHql += " and t.contactPeople = :contactPeople";
				params.put("contactPeople", ljzShop.getContactPeople());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(LjzShop ljzShop) {
		TljzShop t = new TljzShop();
		BeanUtils.copyProperties(ljzShop, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		ljzShopDao.save(t);
	}

	@Override
	public LjzShop get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TljzShop t = ljzShopDao.get("from TljzShop t  where t.id = :id", params);
		LjzShop o = new LjzShop();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(LjzShop ljzShop) {
		TljzShop t = ljzShopDao.get(TljzShop.class, ljzShop.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(ljzShop, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		ljzShopDao.executeHql("update TljzShop t set t.isdeleted = 1 where t.id = :id",params);
		//ljzShopDao.delete(ljzShopDao.get(TljzShop.class, id));
	}

}
