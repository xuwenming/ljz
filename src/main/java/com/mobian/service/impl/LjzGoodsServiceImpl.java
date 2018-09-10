package com.mobian.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mobian.absx.F;
import com.mobian.dao.LjzGoodsDaoI;
import com.mobian.model.TljzGoods;
import com.mobian.pageModel.LjzGoods;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.LjzGoodsServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mobian.util.MyBeanUtils;

@Service
public class LjzGoodsServiceImpl extends BaseServiceImpl<LjzGoods> implements LjzGoodsServiceI {

	@Autowired
	private LjzGoodsDaoI ljzGoodsDao;

	@Override
	public DataGrid dataGrid(LjzGoods ljzGoods, PageHelper ph) {
		List<LjzGoods> ol = new ArrayList<LjzGoods>();
		String hql = " from TljzGoods t ";
		DataGrid dg = dataGridQuery(hql, ph, ljzGoods, ljzGoodsDao);
		@SuppressWarnings("unchecked")
		List<TljzGoods> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TljzGoods t : l) {
				LjzGoods o = new LjzGoods();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(LjzGoods ljzGoods, Map<String, Object> params) {
		String whereHql = "";	
		if (ljzGoods != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(ljzGoods.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", ljzGoods.getIsdeleted());
			}		
			if (!F.empty(ljzGoods.getShopId())) {
				whereHql += " and t.shopId = :shopId";
				params.put("shopId", ljzGoods.getShopId());
			}		
			if (!F.empty(ljzGoods.getTitle())) {
				whereHql += " and t.title = :title";
				params.put("title", ljzGoods.getTitle());
			}		
			if (!F.empty(ljzGoods.getPrice())) {
				whereHql += " and t.price = :price";
				params.put("price", ljzGoods.getPrice());
			}		
			if (!F.empty(ljzGoods.getIcon())) {
				whereHql += " and t.icon = :icon";
				params.put("icon", ljzGoods.getIcon());
			}		
			if (!F.empty(ljzGoods.getImageUrl())) {
				whereHql += " and t.imageUrl = :imageUrl";
				params.put("imageUrl", ljzGoods.getImageUrl());
			}		
			if (!F.empty(ljzGoods.getDescribtion())) {
				whereHql += " and t.describtion = :describtion";
				params.put("describtion", ljzGoods.getDescribtion());
			}		
			if (!F.empty(ljzGoods.getIsPutAway())) {
				whereHql += " and t.isPutAway = :isPutAway";
				params.put("isPutAway", ljzGoods.getIsPutAway());
			}		
			if (!F.empty(ljzGoods.getLimitNumber())) {
				whereHql += " and t.limitNumber = :limitNumber";
				params.put("limitNumber", ljzGoods.getLimitNumber());
			}		
			if (!F.empty(ljzGoods.getFreight())) {
				whereHql += " and t.freight = :freight";
				params.put("freight", ljzGoods.getFreight());
			}		
			if (!F.empty(ljzGoods.getShareAmount())) {
				whereHql += " and t.shareAmount = :shareAmount";
				params.put("shareAmount", ljzGoods.getShareAmount());
			}		
			if (!F.empty(ljzGoods.getPrizePre())) {
				whereHql += " and t.prizePre = :prizePre";
				params.put("prizePre", ljzGoods.getPrizePre());
			}		
			if (!F.empty(ljzGoods.getPrizeAmount())) {
				whereHql += " and t.prizeAmount = :prizeAmount";
				params.put("prizeAmount", ljzGoods.getPrizeAmount());
			}		
			if (!F.empty(ljzGoods.getPrizeNumber())) {
				whereHql += " and t.prizeNumber = :prizeNumber";
				params.put("prizeNumber", ljzGoods.getPrizeNumber());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(LjzGoods ljzGoods) {
		TljzGoods t = new TljzGoods();
		BeanUtils.copyProperties(ljzGoods, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		ljzGoodsDao.save(t);
	}

	@Override
	public LjzGoods get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TljzGoods t = ljzGoodsDao.get("from TljzGoods t  where t.id = :id", params);
		LjzGoods o = new LjzGoods();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(LjzGoods ljzGoods) {
		TljzGoods t = ljzGoodsDao.get(TljzGoods.class, ljzGoods.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(ljzGoods, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		ljzGoodsDao.executeHql("update TljzGoods t set t.isdeleted = 1 where t.id = :id",params);
		//ljzGoodsDao.delete(ljzGoodsDao.get(TljzGoods.class, id));
	}

}
