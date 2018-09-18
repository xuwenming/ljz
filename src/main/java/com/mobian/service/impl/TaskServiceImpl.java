package com.mobian.service.impl;

import com.mobian.pageModel.LjzGoods;
import com.mobian.service.LjzGoodsServiceI;
import com.mobian.service.LjzPrizeLogServiceI;
import com.mobian.service.TaskServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* Created by john on 16/10/16.
*/
@Service
public class TaskServiceImpl implements TaskServiceI {

    @Autowired
    private LjzGoodsServiceI ljzGoodsService;

    @Autowired
    private LjzPrizeLogServiceI ljzPrizeLogService;

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
}
