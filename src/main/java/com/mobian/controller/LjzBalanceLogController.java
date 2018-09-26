package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.service.LjzBalanceLogServiceI;
import com.mobian.service.LjzBalanceServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

/**
 * LjzBalanceLog管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/ljzBalanceLogController")
public class LjzBalanceLogController extends BaseController {

	@Autowired
	private LjzBalanceLogServiceI ljzBalanceLogService;

	@Autowired
	private LjzBalanceServiceI ljzBalanceService;

	/**
	 * 跳转到LjzBalanceLog管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(Integer userId, Integer refType, HttpServletRequest request) {
		request.setAttribute("userId", userId);
		request.setAttribute("refType", refType);
		return "/ljzbalancelog/ljzBalanceLog";
	}

	/**
	 * 获取LjzBalanceLog数据表格
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(LjzBalanceLog ljzBalanceLog, PageHelper ph) {
		if(!F.empty(ljzBalanceLog.getUserId())) {
			LjzBalance ljzBalance = ljzBalanceService.addOrGetBalance(ljzBalanceLog.getUserId(), Integer.valueOf(ljzBalanceLog.getRefType()), BigDecimal.ZERO);
			ljzBalanceLog.setBalanceId(ljzBalance.getId());
			ljzBalanceLog.setRefId(null);
			ljzBalanceLog.setRefType(null);
		}
		return ljzBalanceLogService.dataGrid(ljzBalanceLog, ph);
	}
	/**
	 * 获取LjzBalanceLog数据表格excel
	 * 
	 * @param
	 * @return
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 * @throws IOException 
	 */
	@RequestMapping("/download")
	public void download(LjzBalanceLog ljzBalanceLog, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(ljzBalanceLog,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加LjzBalanceLog页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		LjzBalanceLog ljzBalanceLog = new LjzBalanceLog();
		return "/ljzbalancelog/ljzBalanceLogAdd";
	}

	/**
	 * 添加LjzBalanceLog
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(LjzBalanceLog ljzBalanceLog) {
		Json j = new Json();		
		ljzBalanceLogService.add(ljzBalanceLog);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到LjzBalanceLog查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		LjzBalanceLog ljzBalanceLog = ljzBalanceLogService.get(id);
		request.setAttribute("ljzBalanceLog", ljzBalanceLog);
		return "/ljzbalancelog/ljzBalanceLogView";
	}

	/**
	 * 跳转到LjzBalanceLog修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		LjzBalanceLog ljzBalanceLog = ljzBalanceLogService.get(id);
		request.setAttribute("ljzBalanceLog", ljzBalanceLog);
		return "/ljzbalancelog/ljzBalanceLogEdit";
	}

	/**
	 * 修改LjzBalanceLog
	 * 
	 * @param ljzBalanceLog
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(LjzBalanceLog ljzBalanceLog) {
		Json j = new Json();		
		ljzBalanceLogService.edit(ljzBalanceLog);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除LjzBalanceLog
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		ljzBalanceLogService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
