package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.LjzPayment;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.LjzPaymentServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * LjzPayment管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/ljzPaymentController")
public class LjzPaymentController extends BaseController {

	@Autowired
	private LjzPaymentServiceI ljzPaymentService;


	/**
	 * 跳转到LjzPayment管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/ljzpayment/ljzPayment";
	}

	/**
	 * 获取LjzPayment数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(LjzPayment ljzPayment, PageHelper ph) {
		return ljzPaymentService.dataGrid(ljzPayment, ph);
	}
	/**
	 * 获取LjzPayment数据表格excel
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
	public void download(LjzPayment ljzPayment, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(ljzPayment,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加LjzPayment页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		LjzPayment ljzPayment = new LjzPayment();
		return "/ljzpayment/ljzPaymentAdd";
	}

	/**
	 * 添加LjzPayment
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(LjzPayment ljzPayment) {
		Json j = new Json();		
		ljzPaymentService.add(ljzPayment);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到LjzPayment查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		LjzPayment ljzPayment = ljzPaymentService.get(id);
		request.setAttribute("ljzPayment", ljzPayment);
		return "/ljzpayment/ljzPaymentView";
	}

	/**
	 * 跳转到LjzPayment修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		LjzPayment ljzPayment = ljzPaymentService.get(id);
		request.setAttribute("ljzPayment", ljzPayment);
		return "/ljzpayment/ljzPaymentEdit";
	}

	/**
	 * 修改LjzPayment
	 * 
	 * @param ljzPayment
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(LjzPayment ljzPayment) {
		Json j = new Json();		
		ljzPaymentService.edit(ljzPayment);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除LjzPayment
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		ljzPaymentService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
