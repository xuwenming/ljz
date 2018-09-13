package com.ljz.front.controller;

import com.mobian.absx.F;
import com.mobian.concurrent.CacheKey;
import com.mobian.concurrent.CompletionService;
import com.mobian.concurrent.Task;
import com.mobian.controller.BaseController;
import com.mobian.listener.Application;
import com.mobian.pageModel.*;
import com.mobian.service.*;
import com.mobian.service.impl.CompletionFactory;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

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
//            prizeLog.setToday(true);
            List<LjzPrizeLog> list = ljzPrizeLogService.query(prizeLog);
            if(CollectionUtils.isNotEmpty(list)) {
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
    public Json shareLogList(Integer id){
        Json j = new Json();
        try {
            if(F.empty(id))
                id = Integer.valueOf(Application.getString("SV100", "6"));
            LjzBalanceLog balanceLog = new LjzBalanceLog();
            balanceLog.setRefId(id);
            balanceLog.setRefType("BBT005"); // 转发赚取
            List<LjzBalanceLog> list = ljzBalanceLogService.query(balanceLog);
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
            j.setObj(list);
        } catch (Exception e) {
            logger.error("获取商品详情接口异常", e);
        }

        return j;
    }
}
