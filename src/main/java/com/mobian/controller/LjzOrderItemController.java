package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.LjzOrderItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.LjzOrderItemServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * LjzOrderItem管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/ljzOrderItemController")
public class LjzOrderItemController extends BaseController {

	@Autowired
	private LjzOrderItemServiceI ljzOrderItemService;


	/**
	 * 跳转到LjzOrderItem管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/ljzorderitem/ljzOrderItem";
	}

	/**
	 * 获取LjzOrderItem数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(LjzOrderItem ljzOrderItem, PageHelper ph) {
		return ljzOrderItemService.dataGrid(ljzOrderItem, ph);
	}
	/**
	 * 获取LjzOrderItem数据表格excel
	 * 
	 * @param user
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	@RequestMapping("/download")
	public void download(LjzOrderItem ljzOrderItem, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(ljzOrderItem,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加LjzOrderItem页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		LjzOrderItem ljzOrderItem = new LjzOrderItem();
		return "/ljzorderitem/ljzOrderItemAdd";
	}

	/**
	 * 添加LjzOrderItem
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(LjzOrderItem ljzOrderItem) {
		Json j = new Json();		
		ljzOrderItemService.add(ljzOrderItem);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到LjzOrderItem查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		LjzOrderItem ljzOrderItem = ljzOrderItemService.get(id);
		request.setAttribute("ljzOrderItem", ljzOrderItem);
		return "/ljzorderitem/ljzOrderItemView";
	}

	/**
	 * 跳转到LjzOrderItem修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		LjzOrderItem ljzOrderItem = ljzOrderItemService.get(id);
		request.setAttribute("ljzOrderItem", ljzOrderItem);
		return "/ljzorderitem/ljzOrderItemEdit";
	}

	/**
	 * 修改LjzOrderItem
	 * 
	 * @param ljzOrderItem
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(LjzOrderItem ljzOrderItem) {
		Json j = new Json();		
		ljzOrderItemService.edit(ljzOrderItem);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除LjzOrderItem
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		ljzOrderItemService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
