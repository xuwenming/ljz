package com.mobian.service;

import com.mobian.pageModel.LjzOrderItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface LjzOrderItemServiceI {

	/**
	 * 获取LjzOrderItem数据表格
	 * 
	 * @param ljzOrderItem
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(LjzOrderItem ljzOrderItem, PageHelper ph);

	/**
	 * 添加LjzOrderItem
	 * 
	 * @param ljzOrderItem
	 */
	public void add(LjzOrderItem ljzOrderItem);

	/**
	 * 获得LjzOrderItem对象
	 * 
	 * @param id
	 * @return
	 */
	public LjzOrderItem get(Integer id);

	/**
	 * 修改LjzOrderItem
	 * 
	 * @param ljzOrderItem
	 */
	public void edit(LjzOrderItem ljzOrderItem);

	/**
	 * 删除LjzOrderItem
	 * 
	 * @param id
	 */
	public void delete(Integer id);

    List<LjzOrderItem> queryByOrderId(Integer orderId);

    List<LjzOrderItem> query(LjzOrderItem ljzOrderItem);
}
