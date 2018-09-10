package com.mobian.thirdpart.alipay;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.aliyun.oss.OSSClient;
import com.mobian.listener.Application;

/**
 * Created by wenming on 2017/11/20.
 */
public class AlipayUtil {

    public static String URL = "https://openapi.alipay.com/gateway.do";
    public static String APP_ID;
    public static String APP_PRIVATE_KEY;
    public static String APP_PUBLIC_KEY;
    public static String ALIPAY_PUBLIC_KEY;
    public static String NOTIFY_URL;
    public static String FORMAT = "json";
    public static String CHARSET = "UTF-8";
    public static String SIGN_TYPE = "RSA2";

    public static AlipayClient alipayClient;

    static {
        APP_ID = Application.getDescString("AL101");
        APP_PRIVATE_KEY = Application.getDescString("AL102");
        APP_PUBLIC_KEY = Application.getDescString("AL103");
        ALIPAY_PUBLIC_KEY = Application.getDescString("AL105");
        NOTIFY_URL = Application.getDescString("AL104");
        alipayClient = new DefaultAlipayClient(URL, APP_ID, APP_PRIVATE_KEY, FORMAT, CHARSET, ALIPAY_PUBLIC_KEY, SIGN_TYPE);
    }
}
