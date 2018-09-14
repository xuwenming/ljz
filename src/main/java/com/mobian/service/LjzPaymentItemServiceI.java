package com.mobian.service;

import com.mobian.pageModel.LjzPaymentItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface LjzPaymentItemServiceI {

	/**
	 * 获取LjzPaymentItem数据表格
	 * 
	 * @param ljzPaymentItem
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(LjzPaymentItem ljzPaymentItem, PageHelper ph);

	/**
	 * 添加LjzPaymentItem
	 * 
	 * @param ljzPaymentItem
	 */
	public void add(LjzPaymentItem ljzPaymentItem);

	/**
	 * 获得LjzPaymentItem对象
	 * 
	 * @param id
	 * @return
	 */
	public LjzPaymentItem get(Integer id);

	/**
	 * 修改LjzPaymentItem
	 * 
	 * @param ljzPaymentItem
	 */
	public void edit(LjzPaymentItem ljzPaymentItem);

	/**
	 * 删除LjzPaymentItem
	 * 
	 * @param id
	 */
	public void delete(Integer id);

    LjzPaymentItem getByPaymentId(Integer paymentId);
}
