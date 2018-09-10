package com.mobian.thirdpart.wx;

import com.mobian.listener.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.PublicKey;
import java.util.*;


public class PayCommonUtil {
	private static Logger log = LoggerFactory.getLogger(PayCommonUtil.class);

	/**
	 * @author 李欣桦
	 * @date 2014-12-5下午2:29:34
	 * @Description：sign签名
	 * @param characterEncoding 编码格式
	 * @param parameters 请求参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters){
		StringBuffer sb = new StringBuffer();
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			Object v = entry.getValue();
			if(null != v && !"".equals(v) 
					&& !"sign".equals(k) && !"key".equals(k)) {
				sb.append(k + "=" + v + "&");
			}
		}
		sb.append("key=" + Application.getString(WeixinUtil.API_KEY));
//		sb.append("key=" + "58sf8pYRxqPIsX2B2gdFQy4ojFlPV5cA");
		String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
	    
	    return sign;
	}

	/**
	 * 是否签名正确,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
	 * @return boolean
	 */
	public static boolean isTenpaySign(String characterEncoding, SortedMap<Object, Object> packageParams) {
		StringBuffer sb = new StringBuffer();
		Set es = packageParams.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if(!"sign".equals(k) && null != v && !"".equals(v)) {
				sb.append(k + "=" + v + "&");
			}
		}

		sb.append("key=" + Application.getString(WeixinUtil.API_KEY));

		//算出摘要
		String mysign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toLowerCase();
		String tenpaySign = ((String)packageParams.get("sign")).toLowerCase();

		return tenpaySign.equals(mysign);
	}

	/**
	 * @author 李欣桦
	 * @date 2014-12-5下午2:32:05
	 * @Description：将请求参数转换为xml格式的string
	 * @param parameters  请求参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getRequestXml(SortedMap<Object,Object> parameters){
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		Set es = parameters.entrySet();
		Iterator it = es.iterator();
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			String k = (String)entry.getKey();
			String v = (String)entry.getValue();
			if ("attach".equalsIgnoreCase(k)||"body".equalsIgnoreCase(k)||"sign".equalsIgnoreCase(k)) {
				sb.append("<"+k+">"+"<![CDATA["+v+"]]></"+k+">");
			}else {
				sb.append("<"+k+">"+v+"</"+k+">");
			}
		}
		sb.append("</xml>");
		return sb.toString();
	}
	/**
	 * @author 李欣桦
	 * @date 2014-12-3上午10:17:43
	 * @Description：返回给微信的参数
	 * @param return_code 返回编码
	 * @param return_msg  返回信息
	 * @return
	 */
	public static String setXML(String return_code, String return_msg) {
		return "<xml><return_code><![CDATA[" + return_code
				+ "]]></return_code><return_msg><![CDATA[" + return_msg
				+ "]]></return_msg></xml>";
	}

	public static String getRSA(String str) {
		byte[] estr = new byte[0];   //对银行账号进行加密
		try {
			InputStream instream = new FileInputStream(new File(PayCommonUtil.class.getClassLoader().getResource("pksc8_public.pem").getPath()));
			PublicKey pub = RSAUtil.getPubKey(instream, "RSA");
			String rsa = "RSA/ECB/OAEPWITHSHA-1ANDMGF1PADDING";
			estr = RSAUtil.encrypt(str.getBytes("UTF-8"), pub, 2048, 11, rsa);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Base64.encode(estr);
	}

	/**
	 * 微信企业付款请求参数
	 * @return
	 */
	public static String requestTransfersXML(Map<String, Object> params) {
		try {
			SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
			// 金额  必填（单位为分必须为整数）
			parameters.put("amount", (int)(params.get("amount")) + "");
			// 企业付款描述信息
			parameters.put("desc", "医家盟提现");
			// 商户号 必填
			parameters.put("mch_id", Application.getString(WeixinUtil.MCH_ID));
			// 随机字符串  必填 不长于32位
			parameters.put("nonce_str", WeixinUtil.CreateNoncestr());
			// 商户订单号  必填
			parameters.put("partner_trade_no", params.get("partner_trade_no").toString());
			// 收款方银行卡号
			parameters.put("enc_bank_no", getRSA(params.get("enc_bank_no").toString()));
			// 收款方用户名
			parameters.put("enc_true_name", getRSA(params.get("enc_true_name").toString()));
			// 收款方开户行
			parameters.put("bank_code", params.get("bank_code").toString());

			// 签名 必填
			String sign = PayCommonUtil.createSign("UTF-8", parameters);
			parameters.put("sign", sign);

			return PayCommonUtil.getRequestXml(parameters);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 微信退款请求参数
	 * @return
	 */
	public static String requestRefundXML(Map<String, Object> params) {
		try {
			SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
			// 公众账号ID 必填
			parameters.put("appid", Application.getString(WeixinUtil.APPID));
			// 商户号 必填
			parameters.put("mch_id", Application.getString(WeixinUtil.MCH_ID));
			// 随机字符串  必填 不长于32位
			parameters.put("nonce_str", WeixinUtil.CreateNoncestr());
			// 操作员  必填
//			parameters.put("op_user_id", Application.getString(WeixinUtil.MCH_ID));
			// 商户退款单号  必填
			parameters.put("out_refund_no", params.get("refund_no").toString());
			// 商户订单号  必填
			parameters.put("out_trade_no", params.get("trade_no").toString());
			// 退款金额  必填（单位为分必须为整数）
			parameters.put("refund_fee", params.get("refund_fee") + "");
			// 总金额  必填（单位为分必须为整数）
			parameters.put("total_fee", params.get("total_fee") + "");

			// 签名 必填
			String sign = PayCommonUtil.createSign("UTF-8", parameters);
			parameters.put("sign", sign);

			return PayCommonUtil.getRequestXml(parameters);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 微信查询订单请求参数
	 * @return
	 */
	public static String requestOrderQueryXML(Map<String, Object> params) {
		try {
			SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
			// 公众账号ID 必填
			parameters.put("appid", Application.getString(WeixinUtil.APPID));
			// 商户号 必填
			parameters.put("mch_id", Application.getString(WeixinUtil.MCH_ID));
			if(params.get("out_trade_no") != null) {
				// 商户订单号
				parameters.put("out_trade_no", params.get("out_trade_no").toString());
			}
			if(params.get("transaction_id") != null) {
				// 微信订单号
				parameters.put("transaction_id", params.get("transaction_id").toString());
			}
			// 随机字符串  必填 不长于32位
			parameters.put("nonce_str", WeixinUtil.CreateNoncestr());

			// 签名 必填
			String sign = PayCommonUtil.createSign("UTF-8", parameters);
			parameters.put("sign", sign);

			return PayCommonUtil.getRequestXml(parameters);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String requestQueryBankXML(String partner_trade_no) {
		try {
			SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
			// 商户号 必填
			parameters.put("mch_id", Application.getString(WeixinUtil.MCH_ID));
			// 随机字符串  必填 不长于32位
			parameters.put("nonce_str", WeixinUtil.CreateNoncestr());
			// 商户订单号  必填
			parameters.put("partner_trade_no", partner_trade_no);
			// 签名 必填
			String sign = PayCommonUtil.createSign("UTF-8", parameters);
			parameters.put("sign", sign);

			return PayCommonUtil.getRequestXml(parameters);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
