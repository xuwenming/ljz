package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.LjzPaymentItem;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.LjzPaymentItemServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * LjzPaymentItem管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/ljzPaymentItemController")
public class LjzPaymentItemController extends BaseController {

	@Autowired
	private LjzPaymentItemServiceI ljzPaymentItemService;


	/**
	 * 跳转到LjzPaymentItem管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/ljzpaymentitem/ljzPaymentItem";
	}

	/**
	 * 获取LjzPaymentItem数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(LjzPaymentItem ljzPaymentItem, PageHelper ph) {
		return ljzPaymentItemService.dataGrid(ljzPaymentItem, ph);
	}
	/**
	 * 获取LjzPaymentItem数据表格excel
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
	public void download(LjzPaymentItem ljzPaymentItem, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(ljzPaymentItem,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加LjzPaymentItem页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		LjzPaymentItem ljzPaymentItem = new LjzPaymentItem();
		return "/ljzpaymentitem/ljzPaymentItemAdd";
	}

	/**
	 * 添加LjzPaymentItem
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(LjzPaymentItem ljzPaymentItem) {
		Json j = new Json();		
		ljzPaymentItemService.add(ljzPaymentItem);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到LjzPaymentItem查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		LjzPaymentItem ljzPaymentItem = ljzPaymentItemService.get(id);
		request.setAttribute("ljzPaymentItem", ljzPaymentItem);
		return "/ljzpaymentitem/ljzPaymentItemView";
	}

	/**
	 * 跳转到LjzPaymentItem修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		LjzPaymentItem ljzPaymentItem = ljzPaymentItemService.get(id);
		request.setAttribute("ljzPaymentItem", ljzPaymentItem);
		return "/ljzpaymentitem/ljzPaymentItemEdit";
	}

	/**
	 * 修改LjzPaymentItem
	 * 
	 * @param ljzPaymentItem
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(LjzPaymentItem ljzPaymentItem) {
		Json j = new Json();		
		ljzPaymentItemService.edit(ljzPaymentItem);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除LjzPaymentItem
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		ljzPaymentItemService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
