package com.ljz.front.controller;

import com.alibaba.fastjson.JSONObject;
import com.mobian.absx.F;
import com.mobian.controller.BaseController;
import com.mobian.interceptors.TokenManage;
import com.mobian.listener.Application;
import com.mobian.pageModel.Json;
import com.mobian.pageModel.LjzGoods;
import com.mobian.pageModel.LjzUser;
import com.mobian.service.LjzGoodsServiceI;
import com.mobian.service.LjzUserServiceI;
import com.mobian.service.impl.RedisUserServiceImpl;
import com.mobian.thirdpart.wx.HttpUtil;
import com.mobian.thirdpart.wx.WeixinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
* Created by guxin on 2017/4/22.
*
* 用户相关接口
*/
@Controller
@RequestMapping("/api/goods")
public class ApiGoodsController extends BaseController {

    @Autowired
    private LjzGoodsServiceI ljzGoodsService;

    /**
     * 获取商品详情
     * @return
     */
    @RequestMapping("/detail")
    @ResponseBody
    public Json detail(Integer id){
        Json j = new Json();
        try {
            if(F.empty(id))
                id = Integer.valueOf(Application.getString("SV100", "6"));
            LjzGoods ljzGoods = ljzGoodsService.get(id);
            if(!F.empty(ljzGoods.getImageUrl())) {
                List<String> imageUrls = new ArrayList<>();
                for(String image : ljzGoods.getImageUrl().split(";")) {
                    if(F.empty(image)) continue;
                    imageUrls.add(image);
                }
                ljzGoods.setImageUrls(imageUrls);
            }
            j.success();
            j.setObj(ljzGoods);
        } catch (Exception e) {
            logger.error("获取商品详情接口异常", e);
        }

        return j;
    }


}
