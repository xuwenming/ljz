package com.mobian.service.impl;

import com.mobian.absx.F;
import com.mobian.dao.LjzUserDaoI;
import com.mobian.model.TljzPrizeLog;
import com.mobian.model.TljzUser;
import com.mobian.pageModel.*;
import com.mobian.service.LjzBalanceServiceI;
import com.mobian.service.LjzUserServiceI;
import com.mobian.util.MyBeanUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LjzUserServiceImpl extends BaseServiceImpl<LjzUser> implements LjzUserServiceI {

	@Autowired
	private LjzUserDaoI ljzUserDao;

	@Autowired
	private LjzBalanceServiceI ljzBalanceService;

	@Override
	public DataGrid dataGrid(LjzUser ljzUser, PageHelper ph) {
		List<LjzUser> ol = new ArrayList<LjzUser>();
		String hql = " from TljzUser t ";
		DataGrid dg = dataGridQuery(hql, ph, ljzUser, ljzUserDao);
		@SuppressWarnings("unchecked")
		List<TljzUser> l = dg.getRows();
		if (l != null && l.size() > 0) {
			for (TljzUser t : l) {
				LjzUser o = new LjzUser();
				BeanUtils.copyProperties(t, o);
				// 获取余额
				LjzBalance ljzBalance = ljzBalanceService.addOrGetBalance(t.getId());
				o.setBalance(ljzBalance.getAmount());
				ol.add(o);
			}
		}
		dg.setRows(ol);
		return dg;
	}
	

	protected String whereHql(LjzUser ljzUser, Map<String, Object> params) {
		String whereHql = "";	
		if (ljzUser != null) {
			whereHql += " where t.isdeleted = 0 ";
			if (!F.empty(ljzUser.getIsdeleted())) {
				whereHql += " and t.isdeleted = :isdeleted";
				params.put("isdeleted", ljzUser.getIsdeleted());
			}
			if (!F.empty(ljzUser.getId())) {
				whereHql += " and t.id = :id";
				params.put("id", ljzUser.getId());
			}
			if (!F.empty(ljzUser.getNickName())) {
				whereHql += " and t.nickName like :nickName";
				params.put("nickName", "%" + ljzUser.getNickName() + "%");
			}		
			if (!F.empty(ljzUser.getPhone())) {
				whereHql += " and t.phone = :phone";
				params.put("phone", ljzUser.getPhone());
			}		
			if (!F.empty(ljzUser.getIcon())) {
				whereHql += " and t.icon = :icon";
				params.put("icon", ljzUser.getIcon());
			}		
			if (!F.empty(ljzUser.getSex())) {
				whereHql += " and t.sex = :sex";
				params.put("sex", ljzUser.getSex());
			}		
			if (!F.empty(ljzUser.getRefId())) {
				whereHql += " and t.refId = :refId";
				params.put("refId", ljzUser.getRefId());
			}		
			if (!F.empty(ljzUser.getRefType())) {
				whereHql += " and t.refType = :refType";
				params.put("refType", ljzUser.getRefType());
			}		
			if (!F.empty(ljzUser.getRecommends())) {
				whereHql += " and t.recommends = :recommends";
				params.put("recommends", ljzUser.getRecommends());
			}		
		}	
		return whereHql;
	}

	@Override
	public void add(LjzUser ljzUser) {
		TljzUser t = new TljzUser();
		BeanUtils.copyProperties(ljzUser, t);
		//t.setId(jb.absx.UUID.uuid());
		t.setIsdeleted(false);
		ljzUserDao.save(t);
		ljzUser.setId(t.getId());
	}

	@Override
	public LjzUser get(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		TljzUser t = ljzUserDao.get("from TljzUser t  where t.id = :id", params);
		LjzUser o = new LjzUser();
		BeanUtils.copyProperties(t, o);
		return o;
	}

	@Override
	public void edit(LjzUser ljzUser) {
		TljzUser t = ljzUserDao.get(TljzUser.class, ljzUser.getId());
		if (t != null) {
			MyBeanUtils.copyProperties(ljzUser, t, new String[] { "id" , "addtime", "isdeleted","updatetime" },true);
		}
	}

	@Override
	public void delete(Integer id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		ljzUserDao.executeHql("update TljzUser t set t.isdeleted = 1 where t.id = :id",params);
		//ljzUserDao.delete(ljzUserDao.get(TljzUser.class, id));
	}

	@Override
	public LjzUser getByRef(String refId, String refType) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("refId", refId);
		params.put("refType", refType);
		TljzUser t = ljzUserDao.get("from TljzUser t  where t.isdeleted = 0 and t.refId = :refId and t.refType = :refType", params);
		if(t != null) {
			LjzUser o = new LjzUser();
			BeanUtils.copyProperties(t, o);
			return o;
		}
		return null;
	}

	@Override
	public List<LjzUser> query(LjzUser ljzUser) {
		List<LjzUser> ol = new ArrayList<>();
		String hql = " from TljzUser t ";
		@SuppressWarnings("unchecked")
		List<TljzUser> l = query(hql, ljzUser, ljzUserDao);
		if (l != null && l.size() > 0) {
			for (TljzUser t : l) {
				LjzUser o = new LjzUser();
				BeanUtils.copyProperties(t, o);
				ol.add(o);
			}
		}
		return ol;
	}

}
