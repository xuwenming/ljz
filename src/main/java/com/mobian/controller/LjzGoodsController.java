package com.mobian.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mobian.pageModel.Colum;
import com.mobian.pageModel.LjzGoods;
import com.mobian.pageModel.DataGrid;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.PageHelper;
import com.mobian.service.LjzGoodsServiceI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

/**
 * LjzGoods管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/ljzGoodsController")
public class LjzGoodsController extends BaseController {

	@Autowired
	private LjzGoodsServiceI ljzGoodsService;


	/**
	 * 跳转到LjzGoods管理页面
	 * 
	 * @return
	 */
	@RequestMapping("/manager")
	public String manager(HttpServletRequest request) {
		return "/ljzgoods/ljzGoods";
	}

	/**
	 * 获取LjzGoods数据表格
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping("/dataGrid")
	@ResponseBody
	public DataGrid dataGrid(LjzGoods ljzGoods, PageHelper ph) {
		return ljzGoodsService.dataGrid(ljzGoods, ph);
	}
	/**
	 * 获取LjzGoods数据表格excel
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
	public void download(LjzGoods ljzGoods, PageHelper ph,String downloadFields,HttpServletResponse response) throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, IOException{
		DataGrid dg = dataGrid(ljzGoods,ph);		
		downloadFields = downloadFields.replace("&quot;", "\"");
		downloadFields = downloadFields.substring(1,downloadFields.length()-1);
		List<Colum> colums = JSON.parseArray(downloadFields, Colum.class);
		downloadTable(colums, dg, response);
	}
	/**
	 * 跳转到添加LjzGoods页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/addPage")
	public String addPage(HttpServletRequest request) {
		LjzGoods ljzGoods = new LjzGoods();
		return "/ljzgoods/ljzGoodsAdd";
	}

	/**
	 * 添加LjzGoods
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Json add(LjzGoods ljzGoods) {
		Json j = new Json();		
		ljzGoodsService.add(ljzGoods);
		j.setSuccess(true);
		j.setMsg("添加成功！");		
		return j;
	}

	/**
	 * 跳转到LjzGoods查看页面
	 * 
	 * @return
	 */
	@RequestMapping("/view")
	public String view(HttpServletRequest request, Integer id) {
		LjzGoods ljzGoods = ljzGoodsService.get(id);
		request.setAttribute("ljzGoods", ljzGoods);
		return "/ljzgoods/ljzGoodsView";
	}

	/**
	 * 跳转到LjzGoods修改页面
	 * 
	 * @return
	 */
	@RequestMapping("/editPage")
	public String editPage(HttpServletRequest request, Integer id) {
		LjzGoods ljzGoods = ljzGoodsService.get(id);
		request.setAttribute("ljzGoods", ljzGoods);
		return "/ljzgoods/ljzGoodsEdit";
	}

	/**
	 * 修改LjzGoods
	 * 
	 * @param ljzGoods
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Json edit(LjzGoods ljzGoods) {
		Json j = new Json();		
		ljzGoodsService.edit(ljzGoods);
		j.setSuccess(true);
		j.setMsg("编辑成功！");		
		return j;
	}

	/**
	 * 删除LjzGoods
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Json delete(Integer id) {
		Json j = new Json();
		ljzGoodsService.delete(id);
		j.setMsg("删除成功！");
		j.setSuccess(true);
		return j;
	}

}
