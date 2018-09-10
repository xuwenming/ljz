package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.LjzUser;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.LjzUserServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * LjzUser管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/ljzUserController")
public class LjzUserController extends BaseController {

	@Autowired
	private LjzUserServiceI ljzUserService;


	/**
	 * 跳转到LjzUser管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/ljzuser/ljzUser";
	}

	/**
	 * 获取LjzUser数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(LjzUser ljzUser, PageHelper ph) {
		return ljzUserService.dataGrid(ljzUser, ph);
	}
	/**
	 * 获取LjzUser数据表格excel
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
	public void download(LjzUser ljzUser, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(ljzUser,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加LjzUser页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		LjzUser ljzUser = new LjzUser();
		return "/ljzuser/ljzUserAdd";
	}

	/**
	 * 添加LjzUser
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(LjzUser ljzUser) {
		Json j = new Json();		
		ljzUserService.add(ljzUser);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到LjzUser查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		LjzUser ljzUser = ljzUserService.get(id);
		request.setAttribute("ljzUser", ljzUser);
		return "/ljzuser/ljzUserView";
	}

	/**
	 * 跳转到LjzUser修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		LjzUser ljzUser = ljzUserService.get(id);
		request.setAttribute("ljzUser", ljzUser);
		return "/ljzuser/ljzUserEdit";
	}

	/**
	 * 修改LjzUser
	 * 
	 * @param ljzUser
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(LjzUser ljzUser) {
		Json j = new Json();		
		ljzUserService.edit(ljzUser);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除LjzUser
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		ljzUserService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
