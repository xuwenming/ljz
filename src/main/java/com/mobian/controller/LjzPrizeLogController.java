package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.LjzPrizeLog;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.LjzPrizeLogServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * LjzPrizeLog管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/ljzPrizeLogController")
public class LjzPrizeLogController extends BaseController {

	@Autowired
	private LjzPrizeLogServiceI ljzPrizeLogService;


	/**
	 * 跳转到LjzPrizeLog管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/ljzprizelog/ljzPrizeLog";
	}

	/**
	 * 获取LjzPrizeLog数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(LjzPrizeLog ljzPrizeLog, PageHelper ph) {
		return ljzPrizeLogService.dataGrid(ljzPrizeLog, ph);
	}
	/**
	 * 获取LjzPrizeLog数据表格excel
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
	public void download(LjzPrizeLog ljzPrizeLog, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(ljzPrizeLog,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加LjzPrizeLog页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		LjzPrizeLog ljzPrizeLog = new LjzPrizeLog();
		return "/ljzprizelog/ljzPrizeLogAdd";
	}

	/**
	 * 添加LjzPrizeLog
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(LjzPrizeLog ljzPrizeLog) {
		Json j = new Json();		
		ljzPrizeLogService.add(ljzPrizeLog);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到LjzPrizeLog查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		LjzPrizeLog ljzPrizeLog = ljzPrizeLogService.get(id);
		request.setAttribute("ljzPrizeLog", ljzPrizeLog);
		return "/ljzprizelog/ljzPrizeLogView";
	}

	/**
	 * 跳转到LjzPrizeLog修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		LjzPrizeLog ljzPrizeLog = ljzPrizeLogService.get(id);
		request.setAttribute("ljzPrizeLog", ljzPrizeLog);
		return "/ljzprizelog/ljzPrizeLogEdit";
	}

	/**
	 * 修改LjzPrizeLog
	 * 
	 * @param ljzPrizeLog
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(LjzPrizeLog ljzPrizeLog) {
		Json j = new Json();		
		ljzPrizeLogService.edit(ljzPrizeLog);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除LjzPrizeLog
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		ljzPrizeLogService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
