package com.mobian.service;

import com.mobian.pageModel.LjzBalanceLog;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface LjzBalanceLogServiceI {

	/**
	 * 获取LjzBalanceLog数据表格
	 * 
	 * @param ljzBalanceLog
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(LjzBalanceLog ljzBalanceLog, PageHelper ph);

	/**
	 * 添加LjzBalanceLog
	 * 
	 * @param ljzBalanceLog
	 */
	public void add(LjzBalanceLog ljzBalanceLog);

	/**
	 * 获得LjzBalanceLog对象
	 * 
	 * @param id
	 * @return
	 */
	public LjzBalanceLog get(Integer id);

	/**
	 * 修改LjzBalanceLog
	 * 
	 * @param ljzBalanceLog
	 */
	public void edit(LjzBalanceLog ljzBalanceLog);

	/**
	 * 删除LjzBalanceLog
	 * 
	 * @param id
	 */
	public void delete(Integer id);

    List<LjzBalanceLog> query(LjzBalanceLog balanceLog);

    void addLogAndUpdateBalance(LjzBalanceLog balanceLog);
}
