package com.mobian.controller;

import com.alibaba.fastjson.JSON;
import com.mobian.absx.F;
import com.mobian.pageModel.*;
import com.mobian.service.LjzGoodsServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * LjzGoods管理控制器
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/ljzGoodsController")
public class LjzGoodsController extends BaseController {

	public static final String GOODS = "goods";

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
	 * @param
	 *
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
		if(!F.empty(ljzGoods.getImageUrl())) {
			ljzGoods.setIcon(ljzGoods.getImageUrl().split(";")[0]);
		}
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
		if(!F.empty(ljzGoods.getImageUrl())) {
			List<String> imageUrls = new ArrayList<>();
			for(String image : ljzGoods.getImageUrl().split(";")) {
				if(F.empty(image)) continue;
				imageUrls.add(image);
			}
			ljzGoods.setImageUrls(imageUrls);
		}
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
		if(!F.empty(ljzGoods.getImageUrl())) {
			ljzGoods.setIcon(ljzGoods.getImageUrl().split(";")[0]);
		}
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

	/**
	 * 上传文件
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upload")
	public Json upload(@RequestParam(required = false) MultipartFile imageFile) {
		Json j = new Json();
		try {
			j.setObj(uploadFile(GOODS, imageFile));
			j.success();
		} catch (Exception e) {
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

}
