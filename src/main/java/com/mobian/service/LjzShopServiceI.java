package com.mobian.service;

import com.mobian.pageModel.LjzShop;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface LjzShopServiceI {

	/**
	 * 获取LjzShop数据表格
	 * 
	 * @param ljzShop
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(LjzShop ljzShop, PageHelper ph);

	/**
	 * 添加LjzShop
	 * 
	 * @param ljzShop
	 */
	public void add(LjzShop ljzShop);

	/**
	 * 获得LjzShop对象
	 * 
	 * @param id
	 * @return
	 */
	public LjzShop get(Integer id);

	/**
	 * 修改LjzShop
	 * 
	 * @param ljzShop
	 */
	public void edit(LjzShop ljzShop);

	/**
	 * 删除LjzShop
	 * 
	 * @param id
	 */
	public void delete(Integer id);

}
