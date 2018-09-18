package com.mobian.service.impl;

import com.mobian.listener.Application;
import com.mobian.pageModel.LjzBalanceLog;
import com.mobian.pageModel.LjzGoods;
import com.mobian.pageModel.LjzUser;
import com.mobian.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

/**
* Created by john on 16/10/16.
*/
@Service
public class TaskServiceImpl implements TaskServiceI {

    @Autowired
    private LjzGoodsServiceI ljzGoodsService;

    @Autowired
    private LjzPrizeLogServiceI ljzPrizeLogService;

    @Autowired
    private LjzUserServiceI ljzUserService;

    @Autowired
    private LjzBalanceLogServiceI ljzBalanceLogService;

    @Override
    public void prizeTask() {
        try {
            LjzGoods ljzGoods = new LjzGoods();
            ljzGoods.setIsPutAway(false);
            List<LjzGoods> goodsList = ljzGoodsService.query(ljzGoods);
            for(LjzGoods goods : goodsList) {
                ljzPrizeLogService.addPrizeLogByGoods(goods);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void shareTask() {
        try {
            int flag = Integer.valueOf(Application.getString("SV300", "1"));
            if(flag == 0) return;

            Random random = new Random();
            // 查询所有模拟用户
            LjzUser ljzUser = new LjzUser();
            ljzUser.setRefType("ag"); // 模拟用户
            List<LjzUser> analogUsers = ljzUserService.query(ljzUser);

            LjzGoods ljzGoods = new LjzGoods();
            ljzGoods.setIsPutAway(false);
            List<LjzGoods> goodsList = ljzGoodsService.query(ljzGoods);
            for(LjzGoods goods : goodsList) {
                Integer userId = analogUsers.get(random.nextInt(analogUsers.size())).getId();
                // 模拟转发赚取
                LjzBalanceLog balanceLog = new LjzBalanceLog();
                balanceLog.setUserId(userId);
                balanceLog.setRefType("BBT005"); // 转发赚取
                balanceLog.setRefId(goods.getId());
                balanceLog.setRemark("模拟享赚钱");
                balanceLog.setAmount(goods.getShareAmount());
                ljzBalanceLogService.addLogAndUpdateBalance(balanceLog);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
