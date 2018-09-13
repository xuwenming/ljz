package com.mobian.service;

import com.mobian.pageModel.LjzBalance;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import java.math.BigDecimal;

/**
 * 
 * @author John
 * 
 */
public interface LjzBalanceServiceI {

	/**
	 * 获取LjzBalance数据表格
	 * 
	 * @param ljzBalance
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(LjzBalance ljzBalance, PageHelper ph);

	/**
	 * 添加LjzBalance
	 * 
	 * @param ljzBalance
	 */
	public void add(LjzBalance ljzBalance);

	/**
	 * 获得LjzBalance对象
	 * 
	 * @param id
	 * @return
	 */
	public LjzBalance get(Integer id);

	/**
	 * 修改LjzBalance
	 * 
	 * @param ljzBalance
	 */
	public void edit(LjzBalance ljzBalance);

	/**
	 * 删除LjzBalance
	 * 
	 * @param id
	 */
	public void delete(Integer id);

    LjzBalance addOrGetBalance(Integer refId);

	LjzBalance addOrGetBalance(Integer refId, Integer refType, BigDecimal initAmount);
}
