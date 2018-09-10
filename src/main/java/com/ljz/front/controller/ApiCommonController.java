package com.ljz.front.controller;

import com.mobian.absx.F;
import com.mobian.controller.BaseController;
import com.mobian.exception.ServiceException;
import com.mobian.interceptors.TokenManage;
import com.mobian.listener.Application;
import com.mobian.pageModel.BaseData;
import com.mobian.pageModel.Bug;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.SessionInfo;
import com.mobian.service.BugServiceI;
import com.mobian.service.impl.RedisUserServiceImpl;
import com.mobian.thirdpart.wx.SignUtil;
import com.mobian.thirdpart.wx.WeixinUtil;
import com.mobian.thirdpart.yunpian.YunpianUtil;
import com.mobian.util.HttpUtil;
import com.mobian.util.ImageUtils;
import com.mobian.util.Util;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共模块接口
 * 
 * @author John
 * 
 */
@Controller
@RequestMapping("/api/apiCommon")
public class ApiCommonController extends BaseController {


	@Autowired
	private TokenManage tokenManage;


	@Autowired
	private BugServiceI bugService;

	@Autowired
	private RedisUserServiceImpl redisUserService;
	
	/**
	 * 生成html
	 * @return
	 */
	@RequestMapping("/html")
	public void html(String type,Integer id,HttpServletResponse response) {
		PrintWriter out = null;
		String content = "";
		try{
			response.setContentType("text/html");  
			response.setCharacterEncoding("UTF-8");

			if("BT01".equals(type)) {
//				content = fdMedicinePracticeService.get(id).getContent();
			} else if("BT02".equals(type)) {
//				content = fdMedicineScienceService.get(id).getContent();
			}

			content = ImageUtils.replaceHtmlTag(content, "img", "src", "src=\"", "\"", null);

			out = response.getWriter();
			out.write("<html><head>");
			out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\">");
			out.write("<style type=\"text/css\">");
			out.write("body {font-family:\"微软雅黑\";background-color:#f8f7f5;}");
			out.write("ul,ol,li{padding:0; margin:0;}");
			out.write("img{border:0; line-height:0; width: 100%;}");
			out.write("ol,ul {list-style:none;}");
			out.write("a { color: #000; text-decoration: none; outline: none;}");
			out.write("a img { border: none; }</style>");
			out.write("<script>function load(){var imgstyle=document.getElementsByTagName('img');for(i=0;i<imgstyle.length;i++){imgstyle[i].style.width='100%';}}</script>");
			out.write("</head><body onload='load()'>");
			out.write(content);
			out.write("</body></html>");
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(out!=null){
				out.flush();
				out.close();
			}
		}	
	}

	/**
	 * 获取短信验证码(钱包支付、提现)
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getVCode")
	public Json getVCode(HttpServletRequest request) {
		Json j = new Json();
		try {
			SessionInfo s = getSessionInfo(request);
			String code = Util.CreateNonceNumstr(6); // 生成短信验证码
			Result<SmsSingleSend> result = YunpianUtil.single_send(s.getName(), Application.getDescString(YunpianUtil.VCODE_100).replace("#code#", code));
			if(result.getCode() == 8 || result.getCode() == 22 || result.getCode() == 33) {
				j.setMsg("访问过于频繁，请秒后重试！");
				return j;
			}

			if(result.getCode() == 0) {
				redisUserService.setValidateCode(s.getName(), code, 600L); // 10分钟
				j.setSuccess(true);
				j.setMsg("获取短信验证码成功！");
				return j;
			}
			j.setMsg("获取短信验证码失败！");
		} catch (ServiceException e) {
			j.setObj(e.getMessage());
			logger.error("获取短信验证码接口异常", e);
		} catch (Exception e) {
			j.setMsg(Application.getString(EX_0001));
			logger.error("获取短信验证码接口异常", e);
		}
		return j;
	}
	
	/**
	 * 
	 * @param
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/error")
	public Json error() {
		Json j = new Json();
		j.setObj("token_expire");
		j.setSuccess(false);
		j.setMsg("token过期，请重新登录！");
		return j;
	}

	/**
	 * 微信JS-SDK权限签名
	 * @param signUrl
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/wxJSSign")
	public Json wxJSSign(String signUrl) {
		Json j = new Json();
		try {
			Map<String, String> signMap = SignUtil.sign(signUrl.split("#")[0]);
			signMap.put("appId", Application.getString(WeixinUtil.APPID));
			j.success();
			j.setObj(signMap);
			j.setMsg("微信JS-SDK权限签名成功！");
		}catch(Exception e){
			j.setMsg(Application.getString(EX_0001));
			logger.error("微信JS-SDK权限签名接口异常", e);
		}

		return j;
	}

	@ResponseBody
	@RequestMapping("/validToken")
	public Json validToken() {
		Json j = new Json();
		j.success();
		j.setMsg("token有效！");
		return j;
	}


	/**
	 * app错误日志上传
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upload_errorlog")
	public Json uploadErrorlog(Bug bug, @RequestParam(required = false) MultipartFile logFile) {
		Json j = new Json();
		try{
			bug.setFilePath(uploadFile("errorlog", logFile));
			bug.setTypeId("0"); // 错误
			bug.setId(com.mobian.absx.UUID.uuid());
			bugService.add(bug);
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}		
		return j;
	}
	
	/**
	 * 检查更新
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkUpdate")
	public Json checkUpdate(String versionNo, Integer isAdmin) {
		Json j = new Json();
		try{
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("updateMark", false);

			if(isAdmin == null || isAdmin == 0) {
				String version = Application.getString("VM01", "1.0.0");
				if(F.empty(versionNo) || !versionNo.equals(version)) {
					result.put("updateMark", true);
					result.put("version", version); // 最新版本号
					result.put("filePath", Application.getDescString("APP02")); // 更新地址
					BaseData bd = Application.get("VM04");
					if(bd != null) {
						result.put("downloadPath", bd.getIcon()); // app下载地址
					}

					result.put("isForce", Application.getString("VM03", "0")); // 是否强制
					result.put("updateLog", Application.getDescString("VM02")); // 更新日志
				}
			} else {
				String version = Application.getString("VM11", "1.0.0");
				if(F.empty(versionNo) || !versionNo.equals(version)) {
					result.put("updateMark", true);
					result.put("version", version); // 最新版本号
					result.put("filePath", Application.getDescString("APP04")); // 更新地址
					BaseData bd = Application.get("VM14");
					if(bd != null) {
						result.put("downloadPath", bd.getIcon()); // app下载地址
					}
					result.put("isForce", Application.getString("VM13", "0")); // 是否强制
					result.put("updateLog", Application.getDescString("VM12")); // 更新日志
				}
			}

			j.setObj(result);
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}		
		return j;
	}
	
	
	@ResponseBody
	@RequestMapping("/getIQiYiMP4Url")
	public Json getIQiYiMP4Url(String src, HttpServletRequest request) {
		Json j = new Json();
		try{
			j.setObj(HttpUtil.httpRequest(src, "GET", null));
			j.success();
		}catch(Exception e){
			j.fail();
			e.printStackTrace();
		}		
		return j;
	}

	@ResponseBody
	@RequestMapping("/upload")
	public Json upload(String bizType, @RequestParam(required = false) MultipartFile[] iconFiles, HttpServletRequest request) {
		Json j = new Json();
		try {
			List<String> iconList = new ArrayList<String>();
			for (MultipartFile iconFile : iconFiles) {
				String icon = uploadFile(request, bizType, iconFile);
				iconList.add(icon);
			}
			j.setSuccess(true);
			j.setObj(iconList);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}
}
