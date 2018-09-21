package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.absx.F;
import com.mobian.concurrent.CacheKey;
import com.mobian.concurrent.CompletionService;
import com.mobian.concurrent.Task;
import com.mobian.listener.Application;
import com.mobian.pageModel.*;
import com.mobian.service.LjzWithdrawLogServiceI;
import com.mobian.service.UserServiceI;
import com.mobian.service.impl.CompletionFactory;
import com.mobian.util.ConfigUtil;
import com.mobian.util.Constants;
import com.mobian.util.MD5Util;
import com.mobian.util.RSAUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

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

	@Autowired
	private UserServiceI userService;



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
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(LjzWithdrawLog ljzWithdrawLog, PageHelper ph) {
		DataGrid dg = ljzWithdrawLogService.dataGrid(ljzWithdrawLog, ph);
		List<LjzWithdrawLog> list = dg.getRows();
		if(CollectionUtils.isNotEmpty(list)) {
			CompletionService completionService = CompletionFactory.initCompletion();
			for(LjzWithdrawLog log : list) {
				if(!F.empty(log.getHandleLoginId()))
					completionService.submit(new Task<LjzWithdrawLog, String>(new CacheKey("user", log.getHandleLoginId()), log) {
						@Override
						public String call() throws Exception {
							return userService.getFromCache(getD().getHandleLoginId()).getName();
						}

						protected void set(LjzWithdrawLog d, String v) {
							if(v != null) {
								d.setHandleLoginName(v);
							}
						}
					});
			}
			completionService.sync();
		}
		return dg;
	}
	/**
	 * 获取LjzWithdrawLog数据表格excel
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

		if(!F.empty(ljzWithdrawLog.getHandleLoginId())) {
			ljzWithdrawLog.setHandleLoginName(userService.getFromCache(ljzWithdrawLog.getHandleLoginId()).getName());
		}
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

	/**
	 * 转发至审核页面
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping("/editAuditPage")
	public String editAuditPage(HttpServletRequest request, Integer id) {
		LjzWithdrawLog LjzWithdrawLog = ljzWithdrawLogService.get(id);
		request.setAttribute("ljzWithdrawLog", LjzWithdrawLog);
		return "/ljzwithdrawlog/ljzWithDrawLogAudit";
	}

	@RequestMapping("/editAudit")
	@ResponseBody
	public Json editAudit(LjzWithdrawLog ljzWithdrawLog, String checkPwd, HttpSession session, HttpServletRequest request) {
		Json json = new Json();

		// 获取提现充值密码
		String privateKey = (String)session.getAttribute(RSAUtil.PRIVATE_KEY);
		if(F.empty(privateKey)) {
			json.setMsg("操作失败，请刷新或关闭当前浏览器重新打开！");
			return json;
		}
		checkPwd = RSAUtil.decryptByPravite(checkPwd, privateKey);
		String pwd = Application.getString(Constants.CASH_PASSWORD);
		if(F.empty(checkPwd) || (!F.empty(pwd) && !MD5Util.md5(checkPwd).toUpperCase().equals(pwd.toUpperCase()))) {
			json.fail();
			json.setMsg("校验密码错误");
			return json;
		}


		SessionInfo sessionInfo = (SessionInfo) session.getAttribute(ConfigUtil.getSessionInfoName());
		String loginId = sessionInfo.getId();

		ljzWithdrawLogService.editAudit(ljzWithdrawLog, loginId, request);
		json.success();

		return json;


	}

}
