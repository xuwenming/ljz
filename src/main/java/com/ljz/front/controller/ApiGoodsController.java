package com.ljz.front.controller;

import com.alibaba.fastjson.JSONObject;
import com.mobian.absx.F;
import com.mobian.concurrent.CacheKey;
import com.mobian.concurrent.CompletionService;
import com.mobian.concurrent.Task;
import com.mobian.controller.BaseController;
import com.mobian.interceptors.TokenManage;
import com.mobian.listener.Application;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.service.impl.CompletionFactory;
import com.mobian.thirdpart.wx.WeixinUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
*
* 用户相关接口
*/
@Controller
@RequestMapping("/api/goods")
public class ApiGoodsController extends BaseController {

    @Autowired
    private LjzGoodsServiceI ljzGoodsService;

    @Autowired
    private LjzPrizeLogServiceI ljzPrizeLogService;

    @Autowired
    private LjzUserServiceI ljzUserService;

    @Autowired
    private LjzBalanceLogServiceI ljzBalanceLogService;

    @Autowired
    private LjzBalanceServiceI ljzBalanceService;

    @Autowired
    private TokenManage tokenManage;

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

    /**
     * 获取商品昨日消费中奖列表
     * @return
     */
    @RequestMapping("/prizelog/list")
    @ResponseBody
    public Json prizeLogList(LjzPrizeLog prizeLog){
        Json j = new Json();
        try {
            if(F.empty(prizeLog.getGoodsId()))
                prizeLog.setGoodsId(Integer.valueOf(Application.getString("SV100", "6")));
            prizeLog.setToday(true);
            List<LjzPrizeLog> list = ljzPrizeLogService.query(prizeLog);
            if(CollectionUtils.isNotEmpty(list)) {
                LjzPrizeLog first = list.get(0);
                list = new ArrayList<>();
                list.add(first);
                for(LjzPrizeLog log : list) {
                    log.setUser(ljzUserService.get(log.getUserId()));
                }
            }
            j.success();
            j.setObj(list);
        } catch (Exception e) {
            logger.error("获取商品详情接口异常", e);
        }

        return j;
    }

    /**
     * 获取商品转发提现名单
     * @return
     */
    @RequestMapping("/sharelog/list")
    @ResponseBody
    public Json shareLogList(Integer id, PageHelper ph){
        Json j = new Json();
        try {
            if(F.empty(id))
                id = Integer.valueOf(Application.getString("SV100", "6"));

            if(ph.getRows() == 0 || ph.getRows() > 50) {
                ph.setRows(10);
            }
            if(F.empty(ph.getSort())) {
                ph.setSort("addtime");
            }
            if(F.empty(ph.getOrder())) {
                ph.setOrder("desc");
            }

            LjzBalanceLog balanceLog = new LjzBalanceLog();
            balanceLog.setRefId(id); // 转发赚取存储商品id
            balanceLog.setRefType("BBT005"); // 转发赚取
            DataGrid dg = ljzBalanceLogService.dataGrid(balanceLog, ph);
            List<LjzBalanceLog> list = dg.getRows();
            if(CollectionUtils.isNotEmpty(list)) {
                CompletionService completionService = CompletionFactory.initCompletion();
                for(LjzBalanceLog log : list) {
                    completionService.submit(new Task<LjzBalanceLog, LjzUser>(new CacheKey("ljzBalance", log.getBalanceId() + ""), log) {
                        @Override
                        public LjzUser call() throws Exception {
                            LjzUser user = new LjzUser();
                            LjzBalance balance = ljzBalanceService.get(getD().getBalanceId());
                            if(balance != null) {
                                user = ljzUserService.get(balance.getRefId());
                            }
                            return user;
                        }

                        protected void set(LjzBalanceLog d, LjzUser v) {
                            d.setUser(v);
                        }
                    });
                }
                completionService.sync();
            }
            j.success();
            j.setObj(dg);
        } catch (Exception e) {
            logger.error("获取商品详情接口异常", e);
        }

        return j;
    }

    /**
     * 获取商品小程序专属二维码
     * @return
     */
    @RequestMapping("/getWxacode")
    @ResponseBody
    public Json getWxacode(Integer id, HttpServletRequest request){
        Json j = new Json();
        try {
            Integer userId = Integer.valueOf(tokenManage.getUid(request));
            LjzUser user = ljzUserService.get(userId);
            if(F.empty(user.getWxacodeUrl())) {
                Map<String, Object> param = new HashMap<>();
                param.put("path", "/page/component/index?id=" + id + "&recommend=" + userId);
                String url = WeixinUtil.getWxacode(JSONObject.toJSONString(param));
                user.setWxacodeUrl(url);
                ljzUserService.edit(user);
            }
            j.success();
            j.setObj(user);
        } catch (Exception e) {
            logger.error("获取小程序专属二维码接口异常", e);
        }

        return j;
    }
}
