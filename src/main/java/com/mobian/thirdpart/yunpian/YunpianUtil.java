package com.mobian.thirdpart.yunpian;

import com.mobian.listener.Application;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsBatchSend;
import com.yunpian.sdk.model.SmsSingleSend;

import java.util.Map;

/**
 * Created by wenming on 2017/11/9.
 */
public class YunpianUtil {

    public final static String APPKEY = "YP001";
    public final static String VCODE_100 = "YP100";

    /**
     * 单发
     * @param mobile
     * @param text
     * @return
     */
    public static Result<SmsSingleSend> single_send(String mobile, String text) {
        //初始化clnt,使用单例方式
        YunpianClient clnt = new YunpianClient(Application.getString(APPKEY)).init();

        //发送短信API
        Map<String, String> param = clnt.newParam(2);
        param.put(YunpianClient.MOBILE, mobile);
        param.put(YunpianClient.TEXT, text);
        Result<SmsSingleSend> r = clnt.sms().single_send(param);

        clnt.close();
        return r;
    }

    /**
     * 群发 多个手机号以逗号分隔，一次不要超过1000个
     * @return
     */
    public static Result<SmsBatchSend> batch_send(String mobile, String text) {
        //初始化clnt,使用单例方式
        YunpianClient clnt = new YunpianClient(Application.getString(APPKEY)).init();

        //发送短信API
        Map<String, String> param = clnt.newParam(2);
        param.put(YunpianClient.MOBILE, mobile);
        param.put(YunpianClient.TEXT, text);
        Result<SmsBatchSend> r = clnt.sms().batch_send(param);

        clnt.close();
        return r;
    }
}
