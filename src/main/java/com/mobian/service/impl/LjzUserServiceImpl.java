package com.mobian.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.mobian.absx.F;
import com.mobian.dao.LjzUserDaoI;
import com.mobian.model.TljzUser;
import com.mobian.pageModel.LjzUser;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.LjzUserServiceI;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mobian.util.MyBeanUtils;

@Service
public class LjzUserServiceImpl extends BaseServiceImpl<LjzUser> implements LjzUserServiceI {

	@Autowired
	private LjzUserDaoI ljzUserDao;

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
			if (!F.empty(ljzUser.getNickName())) {
				whereHql += " and t.nickName = :nickName";
				params.put("nickName", ljzUser.getNickName());
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

}
