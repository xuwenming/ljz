package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.service.LjzOrderServiceI;

import com.mobian.util.ConfigUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * LjzOrder管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/ljzOrderController")
public class LjzOrderController extends BaseController {

	@Autowired
	private LjzOrderServiceI ljzOrderService;


	/**
	 * 跳转到LjzOrder管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/ljzorder/ljzOrder";
	}

	/**
	 * 获取LjzOrder数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(LjzOrder ljzOrder, PageHelper ph) {
		return ljzOrderService.dataGrid(ljzOrder, ph);
	}
	/**
	 * 获取LjzOrder数据表格excel
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
	public void download(LjzOrder ljzOrder, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(ljzOrder,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加LjzOrder页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		LjzOrder ljzOrder = new LjzOrder();
		return "/ljzorder/ljzOrderAdd";
	}

	/**
	 * 添加LjzOrder
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(LjzOrder ljzOrder) {
		Json j = new Json();		
		ljzOrderService.add(ljzOrder);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到LjzOrder查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		LjzOrder ljzOrder = ljzOrderService.get(id);
		request.setAttribute("ljzOrder", ljzOrder);
		return "/ljzorder/ljzOrderView";
	}

	/**
	 * 跳转到LjzOrder修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		LjzOrder ljzOrder = ljzOrderService.get(id);
		request.setAttribute("ljzOrder", ljzOrder);
		return "/ljzorder/ljzOrderEdit";
	}

	/**
	 * 修改LjzOrder
	 * 
	 * @param ljzOrder
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(LjzOrder ljzOrder) {
		Json j = new Json();		
		ljzOrderService.edit(ljzOrder);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除LjzOrder
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		ljzOrderService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

	@RequestMapping("/deliveryPage")
	public String deliveryPage(HttpServletRequest request, Integer id) {
		request.setAttribute("orderId", id);
		LjzOrder ljzOrder = ljzOrderService.get(id);
		request.setAttribute("ljzOrder", ljzOrder);
		return "/ljzorder/ljzOrderDelivery";
	}

	@RequestMapping("/delivery")
	@ResponseBody
	public Json delivery(LjzOrder ljzOrder) {
		Json json = new Json();

		ljzOrder.setStatus("OD20");
		ljzOrderService.edit(ljzOrder);

		json.setSuccess(true);
		json.setMsg("发货成功！");

		return json;
	}

}
