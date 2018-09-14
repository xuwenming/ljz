package com.ljz.front.controller;

import com.mobian.absx.F;
import com.mobian.controller.BaseController;
import com.mobian.exception.ServiceException;
import com.mobian.listener.Application;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.LjzPayment;
import com.mobian.pageModel.LjzUser;
import com.mobian.pageModel.SessionInfo;
import com.mobian.service.LjzPaymentServiceI;
import com.mobian.service.LjzUserServiceI;
import com.mobian.thirdpart.wx.HttpUtil;
import com.mobian.thirdpart.wx.PayCommonUtil;
import com.mobian.thirdpart.wx.WeixinUtil;
import com.mobian.thirdpart.wx.XMLUtil;
import com.mobian.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 支付接口
 *
 * @author John
 *
 */
@Controller
@RequestMapping("/api/pay")
public class ApiPayController extends BaseController {

    @Autowired
    private LjzPaymentServiceI ljzPaymentService;

    @Autowired
    private LjzUserServiceI ljzUserService;

	/**
	 * 微信支付
	 */
	@RequestMapping("/wxpay")
	@ResponseBody
	public Json wxpay(LjzPayment payment, String totalPrice, HttpServletRequest request) {
		Json j = new Json();
		try{
			SessionInfo s = getSessionInfo(request);

            LjzPayment ljzPayment = ljzPaymentService.getByOrderId(payment.getOrderId());
            if(ljzPayment != null) {
                if(ljzPayment.getStatus()) {
                    j.setMsg("订单已支付！");
                    return j;
                }
            } else {
                payment.setStatus(false);
                ljzPaymentService.add(payment);
            }

            String orderNo = "OD" + payment.getOrderId();
            int amount = payment.getAmount().multiply(new BigDecimal(100)).intValue();
            LjzUser user = ljzUserService.get(Integer.valueOf(s.getId()));
			String requestXml = requestXML(orderNo, "在线支付", amount, user.getRefId(),"", request);
			System.out.println("~~~~~~~~~~~~微信支付接口请求参数requestXml:" + requestXml);
			String result = HttpUtil.httpsRequest(WeixinUtil.UNIFIED_ORDER_URL, "POST", requestXml);
			System.out.println("~~~~~~~~~~~~微信支付接口返回结果result:" + result);
			Map<String, String> resultMap = XMLUtil.doXMLParse(result);
			if(resultMap.get("return_code").toString().equalsIgnoreCase("SUCCESS") && resultMap.get("result_code").toString().equalsIgnoreCase("SUCCESS")) {
				j.setObj(returnJsonStr(resultMap, request));
				j.setSuccess(true);
				j.setMsg("微信统一下单成功！");
			} else {
				j.setMsg(resultMap.get("return_msg"));
			}

		} catch (ServiceException e) {
			j.setObj(e.getMessage());
			logger.error("微信统一下单接口异常", e);
		}catch(Exception e){
			j.setMsg(Application.getString(EX_0001));
			logger.error("微信统一下单接口异常", e);
		}

		return j;
	}

//	@RequestMapping("/wxpay/notify")
//	public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
//		//订单状态的修改。根据实际业务逻辑执行
//		LjzPayment payment = new LjzPayment();
//		payment.setOrderId(100004);
//		payment.setRefId("1111");
//		payment.setStatus(true);
//		ljzPaymentService.addOrUpdate(payment);
//	}

	@RequestMapping("/wxpay/notify")
	public synchronized void wxpay_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		InputStream inStream = request.getInputStream();
		ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outSteam.write(buffer, 0, len);
		}
		System.out.println("~~~~~~~~~~~~~~~~付款成功~~~~~~~~~");
		outSteam.close();
		inStream.close();
		String result  = new String(outSteam.toByteArray(),"utf-8");//获取微信调用我们notify_url的返回信息
		Map<Object, Object> map = XMLUtil.doXMLParse(result);
		for(Object keyValue : map.keySet()){
			System.out.println(keyValue+"="+map.get(keyValue));
		}

		//过滤空 设置 TreeMap
		SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String parameter = (String) it.next();
			String parameterValue = (String)map.get(parameter);

			String v = "";
			if(null != parameterValue) {
				v = parameterValue.trim();
			}
			packageParams.put(parameter, v);
		}

		String resXml = "";
		if(PayCommonUtil.isTenpaySign("UTF-8", packageParams)) {
			if("SUCCESS".equals(packageParams.get("result_code"))){
				// 这里是支付成功
				//////////执行自己的业务逻辑////////////////
//				String mch_id = (String)packageParams.get("mch_id"); //商户号
//				String openid = (String)packageParams.get("openid");  //用户标识
				String out_trade_no = (String)packageParams.get("out_trade_no"); //商户订单号
//				String total_fee = (String)packageParams.get("total_fee");
				String transaction_id = (String)packageParams.get("transaction_id"); //微信支付订单

                if(out_trade_no.startsWith("OD")) {

					//订单状态的修改。根据实际业务逻辑执行
					LjzPayment payment = new LjzPayment();
					payment.setOrderId(Integer.valueOf(out_trade_no.substring(2)));
					payment.setRefId(transaction_id);
					payment.setStatus(true);

					ljzPaymentService.addOrUpdate(payment);

					resXml = PayCommonUtil.setXML("SUCCESS", "OK");

                } else {
					logger.info("商户订单号错误：" + out_trade_no);
					resXml = PayCommonUtil.setXML("FAIL", "商户订单号错误");
				}

			} else {
				logger.info("支付失败,错误信息：" + packageParams.get("err_code"));
				resXml = PayCommonUtil.setXML("FAIL", "报文为空");
			}


		} else{
			resXml = PayCommonUtil.setXML("FAIL", "通知签名验证失败");
			logger.info("通知签名验证失败");
		}

		response.getWriter().write(resXml);   //告诉微信服务器，我收到信息了，不要在调用回调action了
		System.out.println("-------------"+resXml);
	}

	/**
	 * 校验微信支付结果
	 */
	@RequestMapping("/wxpay/check")
	@ResponseBody
	public Json wxpay_check(String orderNo, HttpServletRequest request) {
		Json j = new Json();
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
		return j;
	}


    /**
     * 微信支付请求参数
     * @return
     */
    private String requestXML(String orderNo, String body, Integer amount, String openid, String trade_type, HttpServletRequest request) {
        try {
            SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
            // 公众账号ID 必填
            parameters.put("appid", Application.getString(WeixinUtil.APPLET_APPID));
            // 附加数据 不是必填
            parameters.put("attach", orderNo);
            // 商品描述  必填
            parameters.put("body", body);
            // 商户号 必填
            parameters.put("mch_id", Application.getString(WeixinUtil.MCH_ID));
            // 随机字符串  必填 不长于32位
            parameters.put("nonce_str", WeixinUtil.CreateNoncestr());
            // 通知地址  必填
            parameters.put("notify_url", Application.getString(WeixinUtil.NOTIFY_URL));
            // 用户标识  trade_type=JSAPI，此参数必传
            parameters.put("openid", openid);
            // 商户订单号  必填
            parameters.put("out_trade_no", orderNo);
            // 终端IP  必填
            parameters.put("spbill_create_ip", IpUtil.getIp(request));
            // 总金额  必填（单位为分必须为整数）
            parameters.put("total_fee", amount + "");
            // 交易类型  必填
            parameters.put("trade_type", F.empty(trade_type) ? "JSAPI" : trade_type);

            // 签名 必填
            String sign = PayCommonUtil.createSign("UTF-8", parameters);
            parameters.put("sign", sign);

            return PayCommonUtil.getRequestXml(parameters);
        } catch(Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private SortedMap<Object,Object> returnJsonStr(Map<String, String> resultMap, HttpServletRequest request) {
        Date nowDate = new Date();
        SortedMap<Object,Object> params = new TreeMap<Object,Object>();
        // 公众账号ID
        params.put("appId", resultMap.get("appid"));
        // 时间戳
        params.put("timeStamp", Long.toString(nowDate.getTime()));
        // 随机串
        params.put("nonceStr", WeixinUtil.CreateNoncestr());
        // 商品包信息
        params.put("package", "prepay_id=" + resultMap.get("prepay_id"));
        // 微信签名方式
        params.put("signType", WeixinUtil.SIGN_TYPE);
        // 微信签名
        String paySign = PayCommonUtil.createSign("UTF-8", params);
        // paySign的生成规则和Sign的生成规则一致
        params.put("paySign", paySign);
        // 这里用packageValue是预防package是关键字在js获取值出错
        params.put("packageValue", "prepay_id=" + resultMap.get("prepay_id"));

        // 微信版本号，用于前面提到的判断用户手机微信的版本是否是5.0以上版本。
        String userAgent = request.getHeader("user-agent");
        char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger") + 15);
        params.put("agent", new String(new char[]{agent}));

        return params;
    }

}
