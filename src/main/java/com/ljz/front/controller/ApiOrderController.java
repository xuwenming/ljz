package com.ljz.front.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.mobian.absx.F;
import com.mobian.controller.BaseController;
import com.mobian.interceptors.TokenManage;
import com.mobian.listener.Application;
import com.mobian.pageModel.*;
import com.mobian.service.LjzGoodsServiceI;
import com.mobian.service.LjzOrderServiceI;
import com.mobian.service.LjzUserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
*
* 用户相关接口
*/
@Controller
@RequestMapping("/api/order")
public class ApiOrderController extends BaseController {

    @Autowired
    private LjzOrderServiceI ljzOrderService;

    @Autowired
    private LjzGoodsServiceI ljzGoodsService;

    @Autowired
    private TokenManage tokenManage;

    @Autowired
    private LjzUserServiceI ljzUserService;

    /**
     * 创建订单
     * @return
     */
    @RequestMapping("/add")
    @ResponseBody
    public Json add(String orderParam, HttpServletRequest request){
        Json j = new Json();
        try {
            if(F.empty(orderParam)) {
                j.setMsg("下单参数为不能为空！");
                return j;
            }
            LjzOrder order = JSON.parseObject(orderParam, new TypeReference<LjzOrder>(){});
            if(order == null) {
                j.setMsg("下单参数为不能为空！");
                return j;
            }
            //验证订单参数
            BigDecimal totalPrice = order.getTotalPrice();
            if(totalPrice == null || totalPrice.doubleValue() < 0) {
                j.setMsg("总金额必需不小于0！");
                return j;
            }
            //验证订单商品信息
            List<LjzOrderItem> orderItemList = order.getOrderItemList();
            if(orderItemList == null || orderItemList.size() == 0) {
                j.setMsg("商品信息不能为空！");
                return j;
            }
            BigDecimal realPrice = BigDecimal.ZERO;
            for (LjzOrderItem orderItem : orderItemList) {
                //验证商品的id，商品数量，商品购买价格
                Integer goodsId = orderItem.getGoodsId();
                if(F.empty(goodsId) || F.empty(orderItem.getQuantity()) || orderItem.getQuantity() < 1
                        || F.empty(orderItem.getBuyPrice())) {
                    j.setMsg("商品基本信息错误！" + "商品id：" + goodsId);
                    return j;
                }
                //验证商品是否存在
                LjzGoods goods = ljzGoodsService.get(goodsId);
                orderItem.setGoodsTitle(goods.getTitle());
                if(goods == null || goods.getIsdeleted()) {
                    j.setMsg("商品不存在！" + "商品id：" + goodsId);
                    return j;
                }
                //验证商品库存是否足够
                if(goods.getQuantity() < orderItem.getQuantity()) {
                    j.setMsg("商品库存不足！" + "商品id：" + goodsId);
                    return j;
                }
                //验证购买价格
                realPrice = realPrice.add(goods.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))).add(goods.getFreight());
            }

            if(totalPrice.compareTo(realPrice) != 0) {
                j.setMsg("订单金额计算错误！");
                return j;
            }
            Integer userId = Integer.valueOf(tokenManage.getUid(request));
            LjzUser user = ljzUserService.get(userId);
            if(!F.empty(user.getRecommends())) {
                String[] recommends = user.getRecommends().split(",");
                order.setRecommend(Integer.valueOf(recommends[recommends.length - 1]));
            }
            order.setUserId(userId);
            ljzOrderService.addOrder(order);
            j.setSuccess(true);
            j.setObj(order.getId());
            j.setMsg("下单成功！");
        } catch (Exception e) {
            logger.error("获取商品详情接口异常", e);
        }

        return j;
    }


}
