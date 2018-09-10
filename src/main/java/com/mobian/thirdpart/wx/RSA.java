package com.mobian.thirdpart.wx;

import com.mobian.listener.Application;

import java.util.*;

/**
 * Created by wenming on 2018/6/2.
 */
public class RSA {

    public static void main(String[] args) {
//        String url = "https://fraud.mch.weixin.qq.com/risk/getpublickey";
//        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
//        parameters.put("mch_id", "1493767442");
//        parameters.put("nonce_str", CreateNoncestr());
//        parameters.put("sign_type", "MD5");
//        parameters.put("sign", createSign("UTF-8", parameters));
//
//        String request = HttpUtil.httpsRequestSSL(url, PayCommonUtil.getRequestXml(parameters));
//        System.out.println(request);

        System.out.println(PayCommonUtil.getRSA("徐文明"));
    }
}
