package com.mobian.thirdpart.jpush;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.ClientConfig;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.PlatformNotification;
import com.mobian.listener.Application;
import com.mobian.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by guxin on 2016/9/16.
 *
 * 极光推送工具类，消息内容可以model的消息推送表组成json字符串
 */
public class JPushUtil {

    final private static Logger logger = LoggerFactory.getLogger(JPushUtil.class);

    public static final String DOCTOR_TAG = "doctor";
    public static final String PATIENT_TAG = "patient";

    final private static String PATIENT_APPKEY = "JP100";
    final private static String PATIENT_SECRET = "JP101";
    final private static String DOCTOR_APPKEY = "JP200";
    final private static String DOCTOR_SECRET = "JP201";

    private static JPushClient jpushClient_patient;
    private static JPushClient jpushClient_doctor;

    private static JPushClient getJpushClient(int clientType) {
        if(clientType == 0) {
            if(jpushClient_patient == null) {
                jpushClient_patient = new JPushClient(Application.getString(PATIENT_SECRET), Application.getString(PATIENT_APPKEY), null, ClientConfig.getInstance());
            }
            return jpushClient_patient;
        } else if(clientType == 2){
            if(jpushClient_doctor == null) {
                jpushClient_doctor = new JPushClient(Application.getString(DOCTOR_SECRET), Application.getString(DOCTOR_APPKEY), null, ClientConfig.getInstance());
            }
            return jpushClient_doctor;
        }

        return null;
    }

    /**
     * 向所有平台，所有设备推送通知消息
     */
    public static PushResult pushMessageToAll(String message, Map<String, String> extras, int clientType) {
//        PushPayload payload = PushPayload.alertAll(message);
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setNotification(Notification.newBuilder()
                                .setAlert(message)
                                .addPlatformNotification(AndroidNotification.newBuilder()
                                        .addExtras(extras)
                                        .build())
                                .addPlatformNotification(IosNotification.newBuilder()
                                        .addExtras(extras)
                                        .build())
                                .build()
                ).setOptions(Options.newBuilder()
                                .setApnsProduction("2".equals(Application.getString(Constants.SYSTEM_PUBLISH_SETTING)))
                                .build()
                )
                .build();
        PushResult result = null;
        try {
            result = getJpushClient(clientType).sendPush(payload);
        } catch (APIConnectionException e) {
            logger.error("连接错误，稍后重试", e);
        } catch (APIRequestException e) {
            logger.error("发送错误", e);
        }

        return result;
    }
    /**
     * 向所有平台，所有设备推送自定义消息
     */
    public static PushResult pushMyMessageToAll(String message, Map<String, String> extras, int clientType) {
//        PushPayload payload = PushPayload.messageAll(message);
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.all())
                .setMessage(Message.newBuilder()
                                .setMsgContent(message)
                                .addExtras(extras)
                                .build()
                ).setOptions(Options.newBuilder()
                                .setApnsProduction("2".equals(Application.getString(Constants.SYSTEM_PUBLISH_SETTING)))
                                .build()
                )
                .build();
        PushResult result = null;
        try {
            result = getJpushClient(clientType).sendPush(payload);
        } catch (APIConnectionException e) {
            logger.error("连接错误，稍后重试", e);
        } catch (APIRequestException e) {
            logger.error("发送错误", e);
        }

        return result;
    }

    /**
     * 向某个平台，某个别名，推送一条消息，单推时用此方法
     *
     * platformString的值为:all ios android android_ios(android和ios)
     *
     * alias:用户别名，每个用户设置不同的别名(同账号的意思)，则可进行单推；若若干用户同一个别名，则为群推
     */
    public static PushResult pushMessageToAlias(String alias, String message, Map<String, String> extras, int clientType) {

//        Platform platform = null;
//        if("android".equals(platformString)) {
//            platform = Platform.android();
//        } else if("ios".equals(platformString)) {
//            platform = Platform.ios();
//        } else if("android_ios".equals(platformString)) {
//            platform = Platform.android_ios();
//        } else {
//            platform = Platform.all();
//        }
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.newBuilder()
                                .setAlert(message)
                                .addPlatformNotification(AndroidNotification.newBuilder()
                                        .addExtras(extras)
                                        .build())
                                .addPlatformNotification(IosNotification.newBuilder()
                                        .addExtras(extras)
                                        .build())
                                .build()
                ).setOptions(Options.newBuilder()
                                .setApnsProduction("2".equals(Application.getString(Constants.SYSTEM_PUBLISH_SETTING)))
                                .build()
                )
                .build();
        PushResult result = null;
        try {
            result = getJpushClient(clientType).sendPush(payload);
        } catch (APIConnectionException e) {
            logger.error("连接错误，稍后重试", e);
        } catch (APIRequestException e) {
            logger.error("发送错误", e);
        }

        return result;
    }

    /**
     * 向某个平台，某个别名，推送一条消息，单推时用此方法
     *
     * platformString的值为:all ios android android_ios(android和ios)
     *
     * alias:用户别名，每个用户设置不同的别名(同账号的意思)，则可进行单推；若若干用户同一个别名，则为群推
     */
    public static PushResult pushMyMessageToAlias(String alias, String message, Map<String, String> extras, int clientType) {

//        Platform platform = null;
//        if("android".equals(platformString)) {
//            platform = Platform.android();
//        } else if("ios".equals(platformString)) {
//            platform = Platform.ios();
//        } else if("android_ios".equals(platformString)) {
//            platform = Platform.android_ios();
//        } else {
//            platform = Platform.all();
//        }
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(alias))
                .setMessage(Message.newBuilder()
                                .setMsgContent(message)
                                .addExtras(extras)
                                .build()
                ).setOptions(Options.newBuilder()
                                .setApnsProduction("2".equals(Application.getString(Constants.SYSTEM_PUBLISH_SETTING)))
                                .build()
                )
                .build();
        PushResult result = null;
        try {
            result = getJpushClient(clientType).sendPush(payload);
        } catch (APIConnectionException e) {
            logger.error("连接错误，稍后重试", e);
        } catch (APIRequestException e) {
            logger.error("发送错误", e);
        }

        return result;
    }

    /**
     * 向某个平台，某些标签，推送一条消息，群推时用此方法
     *
     * platformString的值为:all ios android android_ios(android和ios)
     *
     * tag:标签，多用户打上同一标签，可用于群推
     */
    public static PushResult pushMessageToTag(String tag, String message, Map<String, String> extras, int clientType) {

//        Platform platform = null;
//        if("android".equals(platformString)) {
//            platform = Platform.android();
//        } else if("ios".equals(platformString)) {
//            platform = Platform.ios();
//        } else if("android_ios".equals(platformString)) {
//            platform = Platform.android_ios();
//        } else {
//            platform = Platform.all();
//        }
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(tag))
                .setNotification(Notification.newBuilder()
                                .setAlert(message)
                                .addPlatformNotification(AndroidNotification.newBuilder()
                                        .addExtras(extras)
                                        .build())
                                .addPlatformNotification(IosNotification.newBuilder()
                                        .addExtras(extras)
                                        .build())
                                .build()
                ).setOptions(Options.newBuilder()
                                .setApnsProduction("2".equals(Application.getString(Constants.SYSTEM_PUBLISH_SETTING)))
                                .build()
                )
                .build();
        PushResult result = null;
        try {
            result = getJpushClient(clientType).sendPush(payload);
        } catch (APIConnectionException e) {
            logger.error("连接错误，稍后重试", e);
        } catch (APIRequestException e) {
            logger.error("发送错误", e);
        }

        return result;
    }

    /**
     * 向某个平台，某些标签，推送一条消息，群推时用此方法
     *
     * platformString的值为:all ios android android_ios(android和ios)
     *
     * tag:标签，多用户打上同一标签，可用于群推
     */
    public static PushResult pushMyMessageToTag(String tag, String message, Map<String, String> extras, int clientType) {

//        Platform platform = null;
//        if("android".equals(platformString)) {
//            platform = Platform.android();
//        } else if("ios".equals(platformString)) {
//            platform = Platform.ios();
//        } else if("android_ios".equals(platformString)) {
//            platform = Platform.android_ios();
//        } else {
//            platform = Platform.all();
//        }
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.tag(tag))
                .setMessage(Message.newBuilder()
                                .setMsgContent(message)
                                .addExtras(extras)
                                .build()
                ).setOptions(Options.newBuilder()
                                .setApnsProduction("2".equals(Application.getString(Constants.SYSTEM_PUBLISH_SETTING)))
                                .build()
                )
                .build();
        PushResult result = null;
        try {
            result = getJpushClient(clientType).sendPush(payload);
        } catch (APIConnectionException e) {
            logger.error("连接错误，稍后重试", e);
        } catch (APIRequestException e) {
            logger.error("发送错误", e);
        }

        return result;
    }

}
