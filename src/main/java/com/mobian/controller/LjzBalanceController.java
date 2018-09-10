package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.LjzBalance;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.LjzBalanceServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * LjzBalance管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/ljzBalanceController")
public class LjzBalanceController extends BaseController {

	@Autowired
	private LjzBalanceServiceI ljzBalanceService;


	/**
	 * 跳转到LjzBalance管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/ljzbalance/ljzBalance";
	}

	/**
	 * 获取LjzBalance数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(LjzBalance ljzBalance, PageHelper ph) {
		return ljzBalanceService.dataGrid(ljzBalance, ph);
	}
	/**
	 * 获取LjzBalance数据表格excel
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
	public void download(LjzBalance ljzBalance, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(ljzBalance,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加LjzBalance页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		LjzBalance ljzBalance = new LjzBalance();
		return "/ljzbalance/ljzBalanceAdd";
	}

	/**
	 * 添加LjzBalance
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(LjzBalance ljzBalance) {
		Json j = new Json();		
		ljzBalanceService.add(ljzBalance);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到LjzBalance查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		LjzBalance ljzBalance = ljzBalanceService.get(id);
		request.setAttribute("ljzBalance", ljzBalance);
		return "/ljzbalance/ljzBalanceView";
	}

	/**
	 * 跳转到LjzBalance修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		LjzBalance ljzBalance = ljzBalanceService.get(id);
		request.setAttribute("ljzBalance", ljzBalance);
		return "/ljzbalance/ljzBalanceEdit";
	}

	/**
	 * 修改LjzBalance
	 * 
	 * @param ljzBalance
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(LjzBalance ljzBalance) {
		Json j = new Json();		
		ljzBalanceService.edit(ljzBalance);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除LjzBalance
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		ljzBalanceService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
