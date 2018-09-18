package com.mobian.service;

import com.mobian.pageModel.LjzOrder;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

import java.util.List;

/**
 * 
 * @author John
 * 
 */
public interface LjzOrderServiceI {

	/**
	 * 获取LjzOrder数据表格
	 * 
	 * @param ljzOrder
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(LjzOrder ljzOrder, PageHelper ph);

	/**
	 * 添加LjzOrder
	 * 
	 * @param ljzOrder
	 */
	public void add(LjzOrder ljzOrder);

	/**
	 * 获得LjzOrder对象
	 * 
	 * @param id
	 * @return
	 */
	public LjzOrder get(Integer id);

	/**
	 * 修改LjzOrder
	 * 
	 * @param ljzOrder
	 */
	public void edit(LjzOrder ljzOrder);

	/**
	 * 删除LjzOrder
	 * 
	 * @param id
	 */
	public void delete(Integer id);

    void addOrder(LjzOrder order);

    List<LjzOrder> query(LjzOrder ljzOrder);

    int getBuyQuantityByUserId(Integer userId, Integer goodsId);
}
