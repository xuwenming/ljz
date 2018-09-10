package com.ljz.front.controller;

import com.mobian.absx.F;
import com.mobian.controller.BaseController;
import com.mobian.pageModel.BaseData;
import com.mobian.pageModel.Json;
import com.mobian.service.BasedataServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础数据
 *
 * @author John
 *
 */
@Controller
@RequestMapping("/api/baseData")
public class ApiBaseDataController extends BaseController {

	@Autowired
	private BasedataServiceI basedataService;

	/**
	 * 获取基础数据
	 *
	 * @return
	 */
	@RequestMapping("/basedata")
	@ResponseBody
	public Json basedata(String dataType,String pid) {
		Json j = new Json();
		try{
			BaseData baseData = new BaseData();
			baseData.setBasetypeCode(dataType);
			baseData.setPid(pid);
			j.setObj(basedataService.getBaseDatas(baseData));
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

	/**
	 * 获取基础数据
	 *
	 * @return
	 */
	@RequestMapping("/basedatas")
	@ResponseBody
	public Json basedatas(String dataTypes) {
		Json j = new Json();
		try{
			Map<String, List<BaseData>> obj = new HashMap<String, List<BaseData>>();
			for(String dataType : dataTypes.split(",")) {
				if(F.empty(dataType)) continue;
				BaseData baseData = new BaseData();
				baseData.setBasetypeCode(dataType);
				obj.put(dataType, basedataService.getBaseDatas(baseData));
			}

			j.setObj(obj);
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}
		return j;
	}

}