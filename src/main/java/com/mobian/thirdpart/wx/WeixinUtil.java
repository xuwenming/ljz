package com.mobian.thirdpart.wx;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.mobian.absx.F;
import com.mobian.listener.Application;
import com.mobian.thirdpart.redis.Key;
import com.mobian.thirdpart.redis.Namespace;
import com.mobian.thirdpart.redis.RedisUtil;
import com.mobian.thirdpart.wx.bean.Menu;
import com.mobian.thirdpart.wx.message.req.templateMessage.WxTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class WeixinUtil {
	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);
	public final static String APPID = "WP001";
	public final static String APPSECRET = "WP002";
	public final static String MCH_ID = "WP003";
	public final static String API_KEY = "WP004";
	public final static String BODY = "WP005";
	public final static String NOTIFY_URL = "WP006";
	public final static String TOKEN = "WP007";
	public final static String KF_ONLINE_TIME = "WP300";

	public final static String APPLET_APPID = "WA001";
	public final static String APPLET_APPSECRET = "WA002";

	private static RedisUtil redisUtil = Application.getBean(RedisUtil.class);

	/**
	 * 签名加密方式
	 */
	public final static String SIGN_TYPE = "MD5";

	/**
	 * 微信支付统一接口(POST)
	 */
	public final static String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

	/**
	 * 微信支付退款接口(POST)
	 */
	public final static String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

	/**
	 * 微信支付企业付款接口(POST)
	 */
	public final static String TRANSFERS_URL = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

	/**
	 * 微信支付企业付款到银行卡接口
	 */
	public final static String PAY_BANK_URL = "https://api.mch.weixin.qq.com/mmpaysptrans/pay_bank";

	/**
	 * 查询微信支付企业付款到银行卡接口
	 */
	public final static String QUERY_BANK_URL = "https://api.mch.weixin.qq.com/mmpaysptrans/query_bank";

	/**
	 * 微信查询订单接口(POST)
	 */
	public final static String ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

	/**
	 * 微信用户授权，通过code换取用户信息
	 */
	public final static String AUTHORIZE_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?&appid=APPID&secret=APPSECRET&code=CODE&grant_type=authorization_code";

	/**
	 * 获取access_token的接口地址（GET） 限200（次/天）
	 */
	public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	public final static String JSAPI_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	public final static String DOWNLOAD_MEDIA_URL = "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";

	/**
	 * 客服接口-发消息
	 */
	public final static String CUSTOM_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

	public final static String TEMPLATE_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

	/**
	 * 小程序模板消息接口
	 */
	public final static String MINI_TEMPLATE_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN";

	/**
	 * 获取获取用户基本信息
	 */
	public final static String USERINFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	public final static String OAUTH_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	public final static String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	/**
	 * 小程序code 获取 session_key 和 openid 接口地址
	 */
	public final static String WXAPP_JSCODE2SESSION = "https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code";

	public final static String GET_WXACODE = "https://api.weixin.qq.com/wxa/getwxacode?access_token=ACCESS_TOKEN";


	/**
	 * 小程序中奖结果通知
	 */
	public final static String PRIZE_TEMPLATE_ID = "Ipk-45aFy0-dVsQ7QS4juGJbfJSCqWiClDPE-cnd4f8";


	public static String CreateNoncestr(int length) {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < length; i++) {
			Random rd = new Random();
			res += chars.indexOf(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	public static String CreateNoncestr() {
		String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		String res = "";
		for (int i = 0; i < 16; i++) {
			Random rd = new Random();
			res += chars.charAt(rd.nextInt(chars.length() - 1));
		}
		return res;
	}

	public static String getAuthorizeUrl(String code) {
		String authorize_url = AUTHORIZE_URL
	    		.replace("APPID", Application.getString(APPID))
	    		.replace("APPSECRET", Application.getString(APPSECRET))
	    		.replace("CODE", code);
		return authorize_url;
	}

	public static String getUserInfoUrl(String openid, String access_token) {
		String userInfo_url = null;
		String token = access_token;
		if(F.empty(token)) {
			token = (String)redisUtil.get(Key.build(Namespace.WX_CONFIG, "wx_access_token"));
			userInfo_url = USERINFO_URL;
		} else {
			userInfo_url = OAUTH_USERINFO_URL;
		}
		userInfo_url = userInfo_url
				.replace("ACCESS_TOKEN", token)
				.replace("OPENID", openid);

		return userInfo_url;
	}

	public static String getDownloadMediaUrl(String mediaId) {
		String requestUrl = WeixinUtil.DOWNLOAD_MEDIA_URL
				.replace("ACCESS_TOKEN", (String)redisUtil.get(Key.build(Namespace.WX_CONFIG, "wx_access_token")))
				.replace("MEDIA_ID", mediaId);
		return requestUrl;
	}

	/**
	 * 获取access_token
	 * @return
	 */
	public static AccessToken getAccessToken() {
		AccessToken accessToken = null;
		String requestUrl = ACCESS_TOKEN_URL.replace("APPID", Application.getString(APPID)).replace("APPSECRET", Application.getString(APPSECRET));
		JSONObject jsonObject = JSONObject.parseObject(HttpUtil.httpsRequest(requestUrl, "GET", null));
		// 如果请求成功
		if (null != jsonObject && !F.empty(jsonObject.getString("access_token"))) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	/**
	 * 获取access_token
	 * @return
	 */
	public static AccessToken getAccessToken(String appId, String appSecret) {
		AccessToken accessToken = null;
		String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appId).replace("APPSECRET", appSecret);
		JSONObject jsonObject = JSONObject.parseObject(HttpUtil.httpsRequest(requestUrl, "GET", null));
		// 如果请求成功
		if (null != jsonObject && !F.empty(jsonObject.getString("access_token"))) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
			} catch (JSONException e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}

	/**
	 * 获取js调用api所使用ticket
	 * @param accessToken 获取到的accesstoken
	 * @return
	 */
	public static JsapiTicket getJsapiTicket(String accessToken) {
		JsapiTicket jsapiTicket = null;

		String requestUrl = JSAPI_TICKET_URL.replace("ACCESS_TOKEN", accessToken);
		JSONObject jsonObject = JSONObject.parseObject(HttpUtil.httpsRequest(requestUrl, "GET", null));
		// 如果请求成功
		if (null != jsonObject) {
			try {
				jsapiTicket = new JsapiTicket();
				jsapiTicket.setJsapi_ticket(jsonObject.getString("ticket"));
				jsapiTicket.setExpiresIn(jsonObject.getIntValue("expires_in"));
			} catch (JSONException e) {
				jsapiTicket = null;
				// 获取token失败
				log.error("获取ticket失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return jsapiTicket;
	}

	public static String sendCustomMessage(String messageJson) {
		String accessToken = (String)redisUtil.get(Key.build(Namespace.WX_CONFIG, "wx_access_token"));

		return sendCustomMessage(messageJson, accessToken);
	}

	public static String sendCustomMessage(String messageJson, String accessToken) {
		String requestUrl = CUSTOM_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);
		String result = HttpUtil.httpsRequest(requestUrl, "POST", messageJson);
		System.out.println("messageJson:" + messageJson);
		System.out.println("sendCustomMessage.result:" + result);
		return result;
	}

	/**
	 * 创建菜单
	 *
	 * @param menu 菜单实例
	 * @param accessToken 有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String requestUrl = CREATE_MENU_URL.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.toJSON(menu).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = JSONObject.parseObject(HttpUtil.httpsRequest(requestUrl, "POST", jsonMenu));

		if (null != jsonObject) {
			result = jsonObject.getIntValue("errcode");
			if (0 != jsonObject.getIntValue("errcode")) {
				log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
			}
		}

		return result;
	}

	public static int sendTemplateMessage(WxTemplate wxTemplate) {
		String accessToken = (String)redisUtil.get(Key.build(Namespace.WX_CONFIG, "applet_wx_access_token"));
		if(accessToken == null) {
			AccessToken token = getAccessToken(Application.getString(APPLET_APPID), Application.getString(APPLET_APPSECRET));
			if(token != null) {
				accessToken = token.getToken();
				redisUtil.set(Key.build(Namespace.WX_CONFIG, "applet_wx_access_token"), token.getToken(), token.getExpiresIn() - 200, TimeUnit.SECONDS);
			}
		}
		return sendTemplateMessage(wxTemplate, accessToken);
	}

	/**
	 * 发送模板消息
	 *
	 * @param accessToken 有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int sendTemplateMessage(WxTemplate wxTemplate, String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String requestUrl = MINI_TEMPLATE_MESSAGE_URL.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String json = JSONObject.toJSONString(wxTemplate);
		// 调用接口模板消息
		JSONObject jsonObject = JSONObject.parseObject(HttpUtil.httpsRequest(requestUrl, "POST", json));
		System.out.println("模板消息json数据：" + json);
		System.out.println("模板消息返回信息：" + jsonObject);
		if (null != jsonObject) {
			if (0 != jsonObject.getIntValue("errcode")) {
				result = jsonObject.getIntValue("errcode");
				log.error("模板消息 errcode:{} errmsg:{}", jsonObject.getIntValue("errcode"), jsonObject.getString("errmsg"));
			}
		}

		return result;
	}

	public static String getOAuthUrl(String url) {
		String oauth_url = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE&connect_redirect=1#wechat_redirect"
				.replace("APPID", Application.getString(APPID))
				.replace("REDIRECT_URI", Application.getString("SV100") + url)
				.replace("SCOPE", "snsapi_base");
		return oauth_url;
	}

	public static String getJscode2sessionUrl(String code) {
		String authorize_url = WXAPP_JSCODE2SESSION
				.replace("APPID", Application.getString(APPLET_APPID))
				.replace("SECRET", Application.getString(APPLET_APPSECRET))
				.replace("JSCODE", code);
		return authorize_url;
	}

	/**
	 * 获取小程序二维码
	 * @return
	 */
	public static String getWxacode(String param) {
		String accessToken = (String)redisUtil.get(Key.build(Namespace.WX_CONFIG, "applet_wx_access_token"));
		if(accessToken == null) {
			AccessToken token = getAccessToken(Application.getString(APPLET_APPID), Application.getString(APPLET_APPSECRET));
			if(token != null) {
				accessToken = token.getToken();
				redisUtil.set(Key.build(Namespace.WX_CONFIG, "applet_wx_access_token"), token.getToken(), token.getExpiresIn() - 200, TimeUnit.SECONDS);
			}
		}
		return getWxacode(param, accessToken);
	}

	public static String getWxacode(String param, String accessToken) {

		// 拼装创建菜单的url
		String requestUrl = GET_WXACODE.replace("ACCESS_TOKEN", accessToken);
		// 获取小程序二维码
		String result = HttpUtil.downloadWxacode(requestUrl, "POST", param);
		System.out.println("获取小程序二维码：" + result);

		return result;
	}

	public static void main(String[] args) {



		/**/AccessTokenInstance.accessToken = getAccessToken("wx88821c48181cb549", "c38b7311ed73cd3de419363d427f3286");

//		WxTemplate temp = new WxTemplate();
//		temp.setTouser("ohFc8wC_pC8rKd8ONuIYaCZnMBYU");
//		temp.setUrl("http://zcys2016.com/wsale/api/apiHomeController/home");
//		temp.setTemplate_id("FSOPl6yBweh1TZ3GbwMUOOYEj6kuBA2UX7bCLagVCAA");
//
//		Map<String, TemplateData> data = new HashMap<String, TemplateData>();
//		TemplateData first = new TemplateData();
//		first.setValue("你有一件拍品已过期，无人出价。");
//		first.setColor("#0000E3");
//		data.put("first", first);
//		TemplateData keyword1 = new TemplateData();
//		keyword1.setValue("和田玉");
//		keyword1.setColor("#0000E3");
//		data.put("keyword1", keyword1);
//		TemplateData keyword2 = new TemplateData();
//		keyword2.setValue(DateUtil.format(new Date(), Constants.DATE_FORMAT));
//		keyword2.setColor("#0000E3");
//		data.put("keyword2", keyword2);
//		TemplateData keyword3 = new TemplateData();
//		keyword3.setValue(DateUtil.format(new Date(), Constants.DATE_FORMAT));
//		keyword3.setColor("#0000E3");
//		data.put("keyword3", keyword3);
//		TemplateData remark = new TemplateData();
//		remark.setValue("\n提醒：查看拍品");
//		//remark.setColor("#0000E3");
//		data.put("remark", remark);
//		temp.setData(data);
//		System.out.println(sendTemplateMessage(temp, AccessTokenInstance.accessToken.getToken()));

		/*AccessTokenInstance.accessToken = getAccessToken("wx79bc1c0347c691a5", "3b86cd0181d7247b5ef7fc231f9ba8fd");
		TextMessage tm = new TextMessage();
		Text text = new Text();
		StringBuffer buffer = new StringBuffer();
		buffer.append("您有一条来自『风声』的新消息。").append("\n\n");
		buffer.append("<a href='http://zcys2016.com/wsale/api/apiChat/chat?toUserId=C4193A9BEE9542028FDDAE0942092BD6'>点击查看</a>");
		text.setContent(buffer.toString());
		tm.setTouser("ohFc8wC_pC8rKd8ONuIYaCZnMBYU");
		tm.setMsgtype("text");
		tm.setText(text);
		//Custom c = new Custom();
		//c.setKf_account("wmingfx@dcdsk001");
		//tm.setCustomservice(c);

		Article a1 = new Article();
		a1.setTitle("上海莫辩信息科技有限公司-Snow");
		a1.setDescription("上海莫辩信息科技有限公司");
		a1.setPicurl("http://wx.qlogo.cn/mmopen/ajNVdqHZLLCJOEk3DnNghiaZE0PybOe02QAoPN6X83vEslOx0ibFeS9ClcCewTAa6f59HQvVnjJKyRoe3YiakZs7A/0");
		a1.setUrl("http://www.mobiang.com/");

		Article a2 = new Article();
		a2.setTitle("上海莫辩信息科技有限公司-Ryan");
		a2.setDescription("上海莫辩信息科技有限公司");
		a2.setPicurl("http://wx.qlogo.cn/mmopen/1LlgQzJVOyAkic5QmTFOxWa40SrZqaA7UAyoKdpWwSf4A4VEnlz3Xkzicd05ibO0UKaXXIj7GQPRDtHRL5LibvicB9YSVBibM322NX/0");
		a2.setUrl("http://www.mobiang.com/");

		News news = new News();
		news.setArticles(new Article[]{a1,a2});
		ArticleMessage am = new ArticleMessage();
		am.setTouser("oebsQxCBr_JVJWCcoIqtOu0dRxiI");
		am.setMsgtype("news");
		am.setNews(news);

		String json = JSONObject.toJSONString(tm);
		System.out.println(json);
		System.out.println(sendCustomMessage(json, AccessTokenInstance.accessToken.getToken()));*/
	}
}
