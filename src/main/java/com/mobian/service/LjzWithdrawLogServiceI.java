package com.mobian.service;

import com.mobian.pageModel.LjzWithdrawLog;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface LjzWithdrawLogServiceI {

	/**
	 * 获取LjzWithdrawLog数据表格
	 * 
	 * @param ljzWithdrawLog
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(LjzWithdrawLog ljzWithdrawLog, PageHelper ph);

	/**
	 * 添加LjzWithdrawLog
	 * 
	 * @param ljzWithdrawLog
	 */
	public void add(LjzWithdrawLog ljzWithdrawLog);

	/**
	 * 获得LjzWithdrawLog对象
	 * 
	 * @param id
	 * @return
	 */
	public LjzWithdrawLog get(Integer id);

	/**
	 * 修改LjzWithdrawLog
	 * 
	 * @param ljzWithdrawLog
	 */
	public void edit(LjzWithdrawLog ljzWithdrawLog);

	/**
	 * 删除LjzWithdrawLog
	 * 
	 * @param id
	 */
	public void delete(Integer id);

}
