package com.mobian.service;

import com.mobian.pageModel.LjzGoods;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.PageHelper;

/**
 * 
 * @author John
 * 
 */
public interface LjzGoodsServiceI {

	/**
	 * 获取LjzGoods数据表格
	 * 
	 * @param ljzGoods
	 *            参数
	 * @param ph
	 *            分页帮助类
	 * @return
	 */
	public DataGrid dataGrid(LjzGoods ljzGoods, PageHelper ph);

	/**
	 * 添加LjzGoods
	 * 
	 * @param ljzGoods
	 */
	public void add(LjzGoods ljzGoods);

	/**
	 * 获得LjzGoods对象
	 * 
	 * @param id
	 * @return
	 */
	public LjzGoods get(Integer id);

	/**
	 * 修改LjzGoods
	 * 
	 * @param ljzGoods
	 */
	public void edit(LjzGoods ljzGoods);

	/**
	 * 删除LjzGoods
	 * 
	 * @param id
	 */
	public void delete(Integer id);

    int reduceGoodsCount(Integer goodsId, Integer quantity);
}
