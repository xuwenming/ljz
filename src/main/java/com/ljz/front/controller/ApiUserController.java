package com.ljz.front.controller;

import com.alibaba.fastjson.JSONObject;
import com.mobian.controller.BaseController;
import com.mobian.interceptors.TokenManage;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.LjzUser;
import com.mobian.service.LjzUserServiceI;
import com.mobian.service.impl.RedisUserServiceImpl;
import com.mobian.thirdpart.wx.HttpUtil;
import com.mobian.thirdpart.wx.WeixinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
* Created by guxin on 2017/4/22.
*
* 用户相关接口
*/
@Controller
@RequestMapping("/api/user")
public class ApiUserController extends BaseController {

    @Autowired
    private TokenManage tokenManage;

    @Autowired
    private RedisUserServiceImpl redisUserService;

    @Autowired
    private LjzUserServiceI ljzUserService;

    /**
     * 登录接口
     * @param code
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public Json login(String code, LjzUser ljzUser, boolean oAuth){
        Json j = new Json();
        try {
            JSONObject jsonObject = JSONObject.parseObject(HttpUtil.httpsRequest(WeixinUtil.getJscode2sessionUrl(code), "get", null));
            if (jsonObject != null && jsonObject.containsKey("openid")) {
                String openid = jsonObject.getString("openid");
                LjzUser user = ljzUserService.getByRef(openid, "wx");
                if(user != null) {
                    String tokenId = tokenManage.buildToken(user.getId().toString(), user.getNickName(), null);
                    j.success();
                    j.setObj(tokenId);
                } else {
                    if(oAuth) {
                        ljzUser.setRefId(openid);
                        ljzUserService.add(ljzUser);
                        String tokenId = tokenManage.buildToken(ljzUser.getId().toString(), ljzUser.getNickName(), null);
                        j.success();
                        j.setObj(tokenId);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("登录接口异常", e);
        }

        return j;
    }


}
