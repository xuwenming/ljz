package com.mobian.service;

import com.mobian.pageModel.LjzUser;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface LjzUserServiceI {

	/**
	 * 获取LjzUser数据表格
	 * 
	 * @param ljzUser
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(LjzUser ljzUser, PageHelper ph);

	/**
	 * 添加LjzUser
	 * 
	 * @param ljzUser
	 */
	public void add(LjzUser ljzUser);

	/**
	 * 获得LjzUser对象
	 * 
	 * @param id
	 * @return
	 */
	public LjzUser get(Integer id);

	/**
	 * 修改LjzUser
	 * 
	 * @param ljzUser
	 */
	public void edit(LjzUser ljzUser);

	/**
	 * 删除LjzUser
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	LjzUser getByRef(String refId, String refType);

    List<LjzUser> query(LjzUser ljzUser);
}
