package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.LjzShop;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.LjzShopServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * LjzShop管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/ljzShopController")
public class LjzShopController extends BaseController {

	@Autowired
	private LjzShopServiceI ljzShopService;


	/**
	 * 跳转到LjzShop管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/ljzshop/ljzShop";
	}

	/**
	 * 获取LjzShop数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(LjzShop ljzShop, PageHelper ph) {
		return ljzShopService.dataGrid(ljzShop, ph);
	}
	/**
	 * 获取LjzShop数据表格excel
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
	public void download(LjzShop ljzShop, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(ljzShop,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加LjzShop页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		LjzShop ljzShop = new LjzShop();
		return "/ljzshop/ljzShopAdd";
	}

	/**
	 * 添加LjzShop
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(LjzShop ljzShop) {
		Json j = new Json();		
		ljzShopService.add(ljzShop);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到LjzShop查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		LjzShop ljzShop = ljzShopService.get(id);
		request.setAttribute("ljzShop", ljzShop);
		return "/ljzshop/ljzShopView";
	}

	/**
	 * 跳转到LjzShop修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		LjzShop ljzShop = ljzShopService.get(id);
		request.setAttribute("ljzShop", ljzShop);
		return "/ljzshop/ljzShopEdit";
	}

	/**
	 * 修改LjzShop
	 * 
	 * @param ljzShop
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(LjzShop ljzShop) {
		Json j = new Json();		
		ljzShopService.edit(ljzShop);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除LjzShop
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		ljzShopService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
