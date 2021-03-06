package com.mobian.thirdpart.mns;

import com.aliyun.mns.client.CloudAccount;
import com.aliyun.mns.client.CloudTopic;
import com.aliyun.mns.client.MNSClient;
import com.aliyun.mns.model.BatchSmsAttributes;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;
import com.aliyun.oss.ServiceException;
import com.mobian.absx.F;
import com.mobian.listener.Application;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by john on 17/4/27.
 */
public class Test {

    /**
     *  发送短信
     * @param mobile 多个号码用半角英文逗号隔开
     * @param template
     * @return
     */
    public static TopicMessage sendMns(String mobile, MNSTemplate template) {
        TopicMessage ret = null;
        /**
         * Step 1. 获取主题引用
         */
        CloudAccount account = new CloudAccount("LTAIlEtr6VIrKWaB", "uOnwyGYwUkVL6VmFMVF2JF1xxOjDP7", "http://1790092323412274.mns.cn-shanghai.aliyuncs.com/");
        MNSClient client = account.getMNSClient();
        CloudTopic topic = client.getTopicRef("sms.topic-cn-shanghai");
        /**
         * Step 2. 设置SMS消息体（必须）
         *
         * 注：目前暂时不支持消息内容为空，需要指定消息内容，不为空即可。
         */
        RawTopicMessage msg = new RawTopicMessage();
        msg.setMessageBody("sms-message");
        /**
         * Step 3. 生成SMS消息属性
         */
        MessageAttributes messageAttributes = new MessageAttributes();
        BatchSmsAttributes batchSmsAttributes = new BatchSmsAttributes();
        // 3.1 设置发送短信的签名（SMSSignName）
        batchSmsAttributes.setFreeSignName("骆驼送");
        // 3.2 设置发送短信使用的模板（SMSTempateCode）
        batchSmsAttributes.setTemplateCode(template.getTemplateCode());
        // 3.3 设置发送短信所使用的模板中参数对应的值（在短信模板中定义的，没有可以不用设置）
        BatchSmsAttributes.SmsReceiverParams smsReceiverParams = new BatchSmsAttributes.SmsReceiverParams();

        if(template.getParams() != null) {
            Map<String, String> params = template.getParams();
            for(String key : params.keySet()) {
                smsReceiverParams.setParam(key, params.get(key));
            }
        }
        // 3.4 增加接收短信的号码
        String[] mobiles = mobile.split(",");
        for(String str : mobiles) {
            if(F.empty(str)) continue;
            batchSmsAttributes.addSmsReceiver(str, smsReceiverParams);
        }
        messageAttributes.setBatchSmsAttributes(batchSmsAttributes);
        try {
            /**
             * Step 4. 发布SMS消息
             */
            ret = topic.publishMessage(msg, messageAttributes);
            System.out.println("MessageId: " + ret.getMessageId());
            System.out.println("MessageMD5: " + ret.getMessageBodyMD5());
        } catch (ServiceException se) {
            System.out.println(se.getErrorCode() + se.getRequestId());
            System.out.println(se.getMessage());
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(client != null)
                client.close();
        }

        return ret;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        MNSTemplate template = new MNSTemplate();
        template.setTemplateCode("SMS_91795050");
        Map<String, String> params = new HashMap<String, String>();
        params.put("shopName", "黄智测试水站店");
        params.put("orderId", "100136");
        params.put("deliveryTime", "2017/08/30 12:00");
        template.setParams(params);
        sendMns("18701959799", template);

//        MNSTemplate template = new MNSTemplate();
//        template.setTemplateCode("SMS_63345368");
//        Map<String, String> params = new HashMap<String, String>();
//        params.put("code", "111111");
//        params.put("product", "343434");
//        template.setParams(params);
//        sendMns("18701959799", template);
    }
}
