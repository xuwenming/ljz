package com.mobian.service;

import com.mobian.pageModel.LjzPrizeLog;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface LjzPrizeLogServiceI {

	/**
	 * 获取LjzPrizeLog数据表格
	 * 
	 * @param ljzPrizeLog
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(LjzPrizeLog ljzPrizeLog, PageHelper ph);

	/**
	 * 添加LjzPrizeLog
	 * 
	 * @param ljzPrizeLog
	 */
	public void add(LjzPrizeLog ljzPrizeLog);

	/**
	 * 获得LjzPrizeLog对象
	 * 
	 * @param id
	 * @return
	 */
	public LjzPrizeLog get(Integer id);

	/**
	 * 修改LjzPrizeLog
	 * 
	 * @param ljzPrizeLog
	 */
	public void edit(LjzPrizeLog ljzPrizeLog);

	/**
	 * 删除LjzPrizeLog
	 * 
	 * @param id
	 */
	public void delete(Integer id);

	List<LjzPrizeLog> query(LjzPrizeLog prizeLog);
}
