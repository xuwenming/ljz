package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.LjzWithdrawLog;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.LjzWithdrawLogServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * LjzWithdrawLog管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/ljzWithdrawLogController")
public class LjzWithdrawLogController extends BaseController {

	@Autowired
	private LjzWithdrawLogServiceI ljzWithdrawLogService;


	/**
	 * 跳转到LjzWithdrawLog管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/ljzwithdrawlog/ljzWithdrawLog";
	}

	/**
	 * 获取LjzWithdrawLog数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(LjzWithdrawLog ljzWithdrawLog, PageHelper ph) {
		return ljzWithdrawLogService.dataGrid(ljzWithdrawLog, ph);
	}
	/**
	 * 获取LjzWithdrawLog数据表格excel
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
	public void download(LjzWithdrawLog ljzWithdrawLog, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(ljzWithdrawLog,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加LjzWithdrawLog页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		LjzWithdrawLog ljzWithdrawLog = new LjzWithdrawLog();
		return "/ljzwithdrawlog/ljzWithdrawLogAdd";
	}

	/**
	 * 添加LjzWithdrawLog
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(LjzWithdrawLog ljzWithdrawLog) {
		Json j = new Json();		
		ljzWithdrawLogService.add(ljzWithdrawLog);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到LjzWithdrawLog查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		LjzWithdrawLog ljzWithdrawLog = ljzWithdrawLogService.get(id);
		request.setAttribute("ljzWithdrawLog", ljzWithdrawLog);
		return "/ljzwithdrawlog/ljzWithdrawLogView";
	}

	/**
	 * 跳转到LjzWithdrawLog修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		LjzWithdrawLog ljzWithdrawLog = ljzWithdrawLogService.get(id);
		request.setAttribute("ljzWithdrawLog", ljzWithdrawLog);
		return "/ljzwithdrawlog/ljzWithdrawLogEdit";
	}

	/**
	 * 修改LjzWithdrawLog
	 * 
	 * @param ljzWithdrawLog
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(LjzWithdrawLog ljzWithdrawLog) {
		Json j = new Json();		
		ljzWithdrawLogService.edit(ljzWithdrawLog);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除LjzWithdrawLog
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		ljzWithdrawLogService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
