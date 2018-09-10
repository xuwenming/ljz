package com.mobian.service;

import com.mobian.pageModel.LjzPayment;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface LjzPaymentServiceI {

	/**
	 * 获取LjzPayment数据表格
	 * 
	 * @param ljzPayment
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(LjzPayment ljzPayment, PageHelper ph);

	/**
	 * 添加LjzPayment
	 * 
	 * @param ljzPayment
	 */
	public void add(LjzPayment ljzPayment);

	/**
	 * 获得LjzPayment对象
	 * 
	 * @param id
	 * @return
	 */
	public LjzPayment get(Integer id);

	/**
	 * 修改LjzPayment
	 * 
	 * @param ljzPayment
	 */
	public void edit(LjzPayment ljzPayment);

	/**
	 * 删除LjzPayment
	 * 
	 * @param id
	 */
	public void delete(Integer id);

}
