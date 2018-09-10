//package com.ljz.front.controller;
//
//import com.alipay.api.domain.AlipayTradeAppPayModel;
//import com.alipay.api.internal.util.AlipaySignature;
//import com.alipay.api.request.AlipayTradeAppPayRequest;
//import com.alipay.api.response.AlipayTradeAppPayResponse;
//import com.mobian.absx.F;
//import com.mobian.controller.BaseController;
//import com.mobian.exception.ServiceException;
//import com.mobian.listener.Application;
//import com.mobian.pageModel.*;
//import com.mobian.service.impl.RedisUserServiceImpl;
//import com.mobian.thirdpart.alipay.AlipayUtil;
//import com.mobian.thirdpart.wx.HttpUtil;
//import com.mobian.thirdpart.wx.PayCommonUtil;
//import com.mobian.thirdpart.wx.WeixinUtil;
//import com.mobian.thirdpart.wx.XMLUtil;
//import com.mobian.util.IpUtil;
//import com.mobian.util.Util;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.math.BigDecimal;
//import java.util.*;
//
///**
// * 支付接口
// *
// * @author John
// *
// */
//@Controller
//@RequestMapping("/api/pay")
//public class ApiPayController extends BaseController {
//
//	@Autowired
//	private FdBalanceLogServiceI fdBalanceLogService;
//
//	@Autowired
//	private FdPaymentBaseServiceI fdPaymentBaseService;
//
//	@Autowired
//	private RedisUserServiceImpl redisUserService;
//
//	@Autowired
//	private FdCustomerServiceI fdCustomerService;
//
//	/**
//	 * 微信支付
//	 */
//	@RequestMapping("/wxpay")
//	@ResponseBody
//	public Json wxpay(FdPaymentBase payment, HttpServletRequest request) {
//		Json j = new Json();
//		try{
//			SessionInfo s = getSessionInfo(request);
//
//			String orderNo, body = null;
//			if(!F.empty(payment.getOrderNo())) {
//				if(payment.getOrderNo().startsWith("Y")) {
//					body = Application.getString(WeixinUtil.BODY) + " - 专家预约";
//				} else if(payment.getOrderNo().startsWith("Z")){
//					body = Application.getString(WeixinUtil.BODY) + " - 在线咨询";
//				}
//
//				FdPaymentBase exist = fdPaymentBaseService.getByOrderNo(payment.getOrderNo());
//				if(exist != null) {
//					if("2".equals(exist.getStatus())) {
//						j.setMsg("已支付或审核中请耐心等待！");
//						j.fail();
//						return j;
//					}
//				} else {
//					payment.setType("wechat");
//					payment.setStatus("0");
//					payment.setRemark(body);
//					fdPaymentBaseService.add(payment);
//				}
//				orderNo = payment.getOrderNo();
//			} else {
//				FdBalanceLog balanceLog = new FdBalanceLog();
//				balanceLog.setUserId(Long.valueOf(s.getId()));
//				balanceLog.setBalanceNo(Util.CreateNo("Q"));
//				balanceLog.setRefType("BBT001"); // 充值
//				balanceLog.setAmount(BigDecimal.valueOf(payment.getPrice()).divide(new BigDecimal(100)).floatValue());
//				balanceLog.setAmountLog(0f);
//				balanceLog.setStatus(true);
//				fdBalanceLogService.add(balanceLog);
//
//				orderNo = balanceLog.getBalanceNo();
//				body = Application.getString(WeixinUtil.BODY) + " - 钱包充值";
//			}
//			String requestXml = wxRequestXML(orderNo, body, payment.getPrice(), request);
//			System.out.println("~~~~~~~~~~~~微信支付接口请求参数requestXml:" + requestXml);
//			String result = HttpUtil.httpsRequest(WeixinUtil.UNIFIED_ORDER_URL, "POST", requestXml);
//			System.out.println("~~~~~~~~~~~~微信支付接口返回结果result:" + result);
//			Map<String, String> resultMap = XMLUtil.doXMLParse(result);
//			if(resultMap.get("return_code").toString().equalsIgnoreCase("SUCCESS") && resultMap.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
//				SortedMap<Object,Object> obj = returnJsonStr(resultMap);
//				obj.put("orderNo", orderNo);
//				j.setObj(obj);
//				j.setSuccess(true);
//				j.setMsg("微信统一下单成功！");
//			} else {
//				j.setSuccess(true);
//				j.setMsg(resultMap.get("return_msg"));
//			}
//
//		} catch (ServiceException e) {
//			j.setObj(e.getMessage());
//			logger.error("微信统一下单接口异常", e);
//		}catch(Exception e){
//			j.setMsg(Application.getString(EX_0001));
//			logger.error("微信统一下单接口异常", e);
//		}
//
//		return j;
//	}
//
//	/**
//	 * 支付宝支付
//	 */
//	@RequestMapping("/alipay")
//	@ResponseBody
//	public Json alipay(FdPaymentBase payment, HttpServletRequest request) {
//		Json j = new Json();
//		try{
//			SessionInfo s = getSessionInfo(request);
//
//			String subject = "";
//			if(!F.empty(payment.getOrderNo())) {
//				if(payment.getOrderNo().startsWith("Y")) {
//					subject = "医家盟 - 专家预约";
//				} else if(payment.getOrderNo().startsWith("Z")){
//					subject = "医家盟 - 在线咨询";
//				}
//
//				FdPaymentBase exist = fdPaymentBaseService.getByOrderNo(payment.getOrderNo());
//				if(exist != null) {
//					if("2".equals(exist.getStatus())) {
//						j.setMsg("已支付或审核中请耐心等待！");
//						j.fail();
//						return j;
//					}
//				} else {
//					payment.setType("alipay");
//					payment.setStatus("0");
//					payment.setRemark(subject);
//					fdPaymentBaseService.add(payment);
//				}
//
//			} else {
//				FdBalanceLog balanceLog = new FdBalanceLog();
//				balanceLog.setUserId(Long.valueOf(s.getId()));
//				balanceLog.setBalanceNo(Util.CreateNo("Q"));
//				balanceLog.setRefType("BBT002"); // 充值
//				balanceLog.setAmount(BigDecimal.valueOf(payment.getPrice()).divide(new BigDecimal(100)).floatValue());
//				balanceLog.setAmountLog(0f);
//				balanceLog.setStatus(true);
//				fdBalanceLogService.add(balanceLog);
//
//				payment.setOrderNo(balanceLog.getBalanceNo());
//				subject = "医家盟 - 钱包充值";
//			}
//
//
//			AlipayTradeAppPayRequest alipayRequest = new AlipayTradeAppPayRequest();
//			AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
//			model.setSubject(subject);
//			model.setOutTradeNo(payment.getOrderNo());
//			model.setTimeoutExpress("30m");
//			model.setTotalAmount(BigDecimal.valueOf(payment.getPrice()).divide(new BigDecimal(100)).toString()); // 单位元
//			model.setProductCode("QUICK_MSECURITY_PAY");
//			alipayRequest.setBizModel(model);
//			alipayRequest.setNotifyUrl(AlipayUtil.NOTIFY_URL);
//
//			AlipayTradeAppPayResponse response = AlipayUtil.alipayClient.sdkExecute(alipayRequest);
//			j.setObj(response.getBody());
//			j.setSuccess(true);
//			j.setMsg("支付宝统一下单成功！");
//		} catch (ServiceException e) {
//			j.setObj(e.getMessage());
//			logger.error("支付宝统一下单接口异常", e);
//		}catch(Exception e){
//			j.setMsg(Application.getString(EX_0001));
//			logger.error("支付宝统一下单接口异常", e);
//		}
//
//		return j;
//	}
//
//	/**
//	 * 余额支付
//	 *
//	 * @param
//	 * @return
//	 */
//	@RequestMapping("/balancePay")
//	@ResponseBody
//	public Json balancePay(FdPaymentBase payment, String vcode, HttpServletRequest request) {
//		Json j = new Json();
//		try{
//			SessionInfo s = getSessionInfo(request);
//			if(F.empty(vcode)) {
//                j.setMsg("验证码不能为空！");
//                return j;
//            }
//            String oldCode = redisUserService.getValidateCode(s.getName());
//            if(F.empty(oldCode)) {
//                j.setMsg("验证码已过期！");
//                return j;
//            }
//            if(!oldCode.equals(vcode)) {
//                j.setMsg("验证码错误！");
//                return j;
//            }
//
//			FdCustomer customer = fdCustomerService.get(Long.valueOf(s.getId()));
//			if(customer == null || new BigDecimal(customer.getBalance().toString()).multiply(new BigDecimal(100)).intValue() < payment.getPrice()) {
//				j.setMsg("支付失败，余额不足！");
//                return j;
//			}
//
//			String remark = "";
//			if(payment.getOrderNo().startsWith("Y")) {
//				remark = "医家盟 - 专家预约";
//			} else if(payment.getOrderNo().startsWith("Z")){
//				remark = "医家盟 - 在线咨询";
//			}
//
//			FdPaymentBase exist = fdPaymentBaseService.getByOrderNo(payment.getOrderNo());
//			if(exist != null) {
//				if("2".equals(exist.getStatus())) {
//					j.setMsg("已支付或审核中请耐心等待！");
//					j.fail();
//					return j;
//				}
//			}
//
//			payment.setType("balance");
//			payment.setStatus("0");
//			payment.setRemark(remark);
//			fdPaymentBaseService.addOrUpdate(payment);
//
//			j.success();
//			j.setMsg("余额支付成功！");
//		} catch (ServiceException e) {
//			j.setObj(e.getMessage());
//			logger.error("余额支付接口异常", e);
//		} catch(Exception e){
//			j.setMsg(Application.getString(EX_0001));
//			logger.error("余额支付接口异常", e);
//		}
//		return j;
//	}
//
//	@RequestMapping("/wxpay/notify")
//	public synchronized void wxpay_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		InputStream inStream = request.getInputStream();
//		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
//		byte[] buffer = new byte[1024];
//		int len = 0;
//		while ((len = inStream.read(buffer)) != -1) {
//			outSteam.write(buffer, 0, len);
//		}
//		System.out.println("~~~~~~~~~~~~~~~~付款成功~~~~~~~~~");
//		outSteam.close();
//		inStream.close();
//		String result  = new String(outSteam.toByteArray(),"utf-8");//获取微信调用我们notify_url的返回信息
//		Map<Object, Object> map = XMLUtil.doXMLParse(result);
//		for(Object keyValue : map.keySet()){
//			System.out.println(keyValue+"="+map.get(keyValue));
//		}
//
//		//过滤空 设置 TreeMap
//		SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
//		Iterator it = map.keySet().iterator();
//		while (it.hasNext()) {
//			String parameter = (String) it.next();
//			String parameterValue = (String)map.get(parameter);
//
//			String v = "";
//			if(null != parameterValue) {
//				v = parameterValue.trim();
//			}
//			packageParams.put(parameter, v);
//		}
//
//		String resXml = "";
//		if(PayCommonUtil.isTenpaySign("UTF-8", packageParams)) {
//			if("SUCCESS".equals((String)packageParams.get("result_code"))){
//				// 这里是支付成功
//				//////////执行自己的业务逻辑////////////////
//				String mch_id = (String)packageParams.get("mch_id"); //商户号
////				String openid = (String)packageParams.get("openid");  //用户标识
//				String out_trade_no = (String)packageParams.get("out_trade_no"); //商户订单号
//				String total_fee = (String)packageParams.get("total_fee");
//				String transaction_id = (String)packageParams.get("transaction_id"); //微信支付订单
//
//				FdPaymentBase trade = null;
//				if(out_trade_no.startsWith("Y") || out_trade_no.startsWith("Z")) {
//					trade = fdPaymentBaseService.getByOrderNo(out_trade_no);
//				} else if(out_trade_no.startsWith("Q")){
//					FdBalanceLog bl = fdBalanceLogService.getByBalanceNo(out_trade_no);
//					if(bl != null) {
//						trade = new FdPaymentBase();
//						trade.setStatus(bl.getStatus() ? "0" : "2");
//						trade.setPrice(new BigDecimal(bl.getAmount().toString()).multiply(new BigDecimal(100)).intValue());
//					}
//				}
//
//				if(!Application.getString(WeixinUtil.MCH_ID).equals(mch_id)
//						|| trade == null || new BigDecimal(total_fee).compareTo(new BigDecimal(trade.getPrice())) != 0) {
//					logger.info("支付失败,错误信息：" + "参数错误");
//					resXml = PayCommonUtil.setXML("FAIL", "参数错误");
//				}else{
//					if(!"2".equals(trade.getStatus())){
//						//订单状态的修改。根据实际业务逻辑执行
//						if(out_trade_no.startsWith("Y") || out_trade_no.startsWith("Z")) {
//							FdPaymentBase payment = new FdPaymentBase();
//							payment.setOrderNo(out_trade_no);
//							payment.setRefId(transaction_id);
//							payment.setStatus("2");
//
//							fdPaymentBaseService.addOrUpdate(payment);
//						} else if(out_trade_no.startsWith("Q")) {
//							FdBalanceLog balanceLog = new FdBalanceLog();
//							balanceLog.setBalanceNo(out_trade_no);
//							balanceLog.setRefId(transaction_id);
//							balanceLog.setStatus(false);
//
//							fdBalanceLogService.updateLogAndBalance(balanceLog);
//						}
//
//						resXml = PayCommonUtil.setXML("SUCCESS", "OK");
//					}else{
//						resXml = PayCommonUtil.setXML("SUCCESS", "OK");
//						logger.info("订单已处理");
//					}
//				}
//
//			} else {
//				logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
//				resXml = PayCommonUtil.setXML("FAIL", "报文为空");
//			}
//
//
//		} else{
//			resXml = PayCommonUtil.setXML("FAIL", "通知签名验证失败");
//			logger.info("通知签名验证失败");
//		}
//
//		response.getWriter().write(resXml);   //告诉微信服务器，我收到信息了，不要在调用回调action了
//		System.out.println("-------------"+resXml);
//	}
//
//	@RequestMapping("/alipay/notify")
//	public synchronized void alipay_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		Map requestParams = request.getParameterMap();
//		System.out.println("支付宝支付结果通知"+requestParams.toString());
//		//获取支付宝POST过来反馈信息
//		Map<String,String> params = new HashMap<String,String>();
//
//		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
//			String name = (String) iter.next();
//			String[] values = (String[]) requestParams.get(name);
//			String valueStr = "";
//			for (int i = 0; i < values.length; i++) {
//				valueStr = (i == values.length - 1) ? valueStr + values[i]
//						: valueStr + values[i] + ",";
//			}
//			//乱码解决，这段代码在出现乱码时使用。
//			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
//			params.put(name, valueStr);
//			System.out.println(name+"="+valueStr);
//		}
//
//		boolean verify_result = AlipaySignature.rsaCheckV1(params, AlipayUtil.ALIPAY_PUBLIC_KEY,
//				AlipayUtil.CHARSET, AlipayUtil.SIGN_TYPE);
//		System.out.println("-------------"+verify_result);
//		if(verify_result) {
//			if ("TRADE_SUCCESS".equals(params.get("trade_status"))) {
//				String orderNo = params.get("out_trade_no");
//				String trade_no = params.get("trade_no"); // 支付宝交易凭证号
//				if(orderNo.startsWith("Q")) {
//					FdBalanceLog balanceLog = new FdBalanceLog();
//					balanceLog.setBalanceNo(orderNo);
//					balanceLog.setRefId(trade_no);
//					balanceLog.setStatus(false);
//					fdBalanceLogService.updateLogAndBalance(balanceLog);
//				} else {
//					FdPaymentBase payment = new FdPaymentBase();
//					payment.setOrderNo(orderNo);
//					payment.setRefId(trade_no);
//					payment.setStatus("2");
//					fdPaymentBaseService.addOrUpdate(payment);
//				}
//			}
//		}
//		response.getWriter().println("success");
//	}
//
//	/**
//	 * 校验微信支付结果
//	 */
//	@RequestMapping("/wxpay/check")
//	@ResponseBody
//	public Json wxpay_check(String orderNo, HttpServletRequest request) {
//		Json j = new Json();
//		try {
//			FdPaymentBase trade = null;
//			if(orderNo.startsWith("Y") || orderNo.startsWith("Z")) {
//				trade = fdPaymentBaseService.getByOrderNo(orderNo);
//			} else if(orderNo.startsWith("Q")){
//				FdBalanceLog bl = fdBalanceLogService.getByBalanceNo(orderNo);
//				if(bl != null) {
//					trade = new FdPaymentBase();
//					trade.setStatus(bl.getStatus() ? "0" : "2");
//				}
//			}
//			if(trade == null) {
//				j.setMsg("订单号不存在");
//				return j;
//			}
//			if("2".equals(trade.getStatus())) {
//				j.success();
//				j.setMsg("支付成功");
//				j.setObj("SUCCESS");
//			} else {
//				Map<String, Object> params = new HashMap<String, Object>();
//				params.put("out_trade_no", orderNo);
//				String result = HttpUtil.httpsRequest(WeixinUtil.ORDER_QUERY_URL, "POST", PayCommonUtil.requestOrderQueryXML(params));
//				Map<String, String> resultMap = XMLUtil.doXMLParse(result);
//				if("SUCCESS".equals(resultMap.get("result_code"))) {
//					j.success();
//					j.setMsg("查询成功");
//					j.setObj(resultMap.get("trade_state"));
//				} else {
//					j.success();
//					j.setMsg(resultMap.get("return_msg"));
//					j.setObj("FAIL");
//				}
//			}
//		} catch(Exception e){
//			j.setMsg(Application.getString(EX_0001));
//			logger.error("获取钱包明细接口异常", e);
//		}
//
//		return j;
//	}
//
//
//	/**
//	 * 微信支付请求参数
//	 * @return
//	 */
//	private String wxRequestXML(String orderNo, String body, Integer amount, HttpServletRequest request) {
//		try {
//			SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
//			// 公众账号ID 必填
//			parameters.put("appid", Application.getString(WeixinUtil.APPID));
//			// 附加数据 不是必填
//			parameters.put("attach", orderNo);
//			// 商品描述  必填
//			parameters.put("body", body);
//			// 商户号 必填
//			parameters.put("mch_id", Application.getString(WeixinUtil.MCH_ID));
//			// 随机字符串  必填 不长于32位
//			parameters.put("nonce_str", WeixinUtil.CreateNoncestr());
//			// 通知地址  必填
//			parameters.put("notify_url", Application.getString(WeixinUtil.NOTIFY_URL));
//
//			// 商户订单号  必填
//			parameters.put("out_trade_no", orderNo);
//			// 终端IP  必填
//			parameters.put("spbill_create_ip", IpUtil.getIp(request));
//			// 总金额  必填（单位为分必须为整数）
//			parameters.put("total_fee", amount + "");
//			// 交易类型  必填
//			parameters.put("trade_type", "APP");
//
//			// 签名 必填
//			String sign = PayCommonUtil.createSign("UTF-8", parameters);
//			parameters.put("sign", sign);
//
//			return PayCommonUtil.getRequestXml(parameters);
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//
//		return null;
//	}
//
//	private SortedMap<Object,Object> returnJsonStr(Map<String, String> resultMap) {
//		Date nowDate = new Date();
//		SortedMap<Object,Object> params = new TreeMap<Object,Object>();
//		// 公众账号ID
//		params.put("appid", resultMap.get("appid"));
//		// 时间戳
//		params.put("timestamp", Long.toString(nowDate.getTime()).substring(0, 10));
//		// 随机串
//		params.put("noncestr", WeixinUtil.CreateNoncestr());
//		// 商品包信息
//		params.put("package", "Sign=WXPay");
//		// 商户号
//		params.put("partnerid", resultMap.get("mch_id"));
//		// 预支付交易会话ID
//		params.put("prepayid", resultMap.get("prepay_id"));
//		// 微信签名
//		String sign = PayCommonUtil.createSign("UTF-8", params);
//		// paySign的生成规则和Sign的生成规则一致
//		params.put("sign", sign);
//
//		return params;
//	}
//
//}
