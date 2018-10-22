package com.ljz.front.controller;

import com.mobian.absx.F;
import com.mobian.controller.BaseController;
import com.mobian.exception.ServiceException;
import com.mobian.listener.Application;
import com.mobian.pageModel.*;
import com.mobian.service.LjzBalanceLogServiceI;
import com.mobian.service.LjzBalanceServiceI;
import com.mobian.service.LjzWithdrawLogServiceI;
import com.mobian.util.IpUtil;
import com.mobian.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
* Created by xuwm on 2017/5/17.
*
* 钱包
*/
@Controller
@RequestMapping("/api/balance")
public class ApiBalanceController extends BaseController {

    @Autowired
    private LjzBalanceServiceI ljzBalanceService;

    @Autowired
    private LjzBalanceLogServiceI ljzBalanceLogService;

    @Autowired
    private LjzWithdrawLogServiceI ljzWithdrawLogService;

    /**
     * 获取我的财产
     * @return
     */
    @RequestMapping("/getBalance")
    @ResponseBody
    public Json getBalance(HttpServletRequest request){
        Json j = new Json();
        try {
            SessionInfo s = getSessionInfo(request);
            LjzBalance balance = ljzBalanceService.addOrGetBalance(Integer.valueOf(s.getId()));
            j.success();
            j.setObj(balance);
        } catch (Exception e) {
            logger.error("获取我的财产接口异常", e);
        }

        return j;
    }

    /**
     * 获取钱包明细接口
     */
    @RequestMapping("/queryLogPage")
    @ResponseBody
    public Json dataGrid(LjzBalanceLog balanceLog, PageHelper ph) {
        Json j = new Json();
        try{
            if(ph.getRows() == 0 || ph.getRows() > 50) {
                ph.setRows(10);
            }
            if(F.empty(ph.getSort())) {
                ph.setSort("id");
            }
            if(F.empty(ph.getOrder())) {
                ph.setOrder("desc");
            }

            DataGrid dg = ljzBalanceLogService.dataGrid(balanceLog, ph);
            j.setSuccess(true);
            j.setMsg("获取钱包明细成功！");
            j.setObj(dg);
        } catch(Exception e) {
            j.setMsg(Application.getString(EX_0001));
            logger.error("获取钱包明细接口异常", e);
        }

        return j;
    }

    /**
     * 获取提现配置
     * @param request
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/getWithdrawConfig")
    public Json getWithdrawConfig(HttpServletRequest request){
        Json j = new Json();
        try{
            Map<String, Object> obj = new HashMap<>();
            SessionInfo s = getSessionInfo(request);

            LjzWithdrawLog log = new LjzWithdrawLog();
            log.setUserId(Integer.valueOf(s.getId()));
            LjzWithdrawLog withdrawLog = ljzWithdrawLogService.get(log);
            obj.put("withdrawLog", withdrawLog);

            BigDecimal max = new BigDecimal(Application.getString("WD02", "1000"));
            LjzBalance balance = ljzBalanceService.addOrGetBalance(Integer.valueOf(s.getId()));
            BigDecimal balanceAmount = balance.getAmount();
            BigDecimal amount = balanceAmount.compareTo(max) >= 0 ? max : balanceAmount;

            int serviceAmtFlag = Integer.valueOf(Application.getString("WD04", "1"));
            BigDecimal serviceAmtPer = new BigDecimal(Application.getString("WD05", "0.1"));
            BigDecimal serviceAmt = BigDecimal.ZERO;
            if(serviceAmtFlag == 1) {
                // 计算手续费1元的最小临界值
                BigDecimal minCritical = BigDecimal.ONE.divide(serviceAmtPer, 2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
                if(amount.doubleValue() <= minCritical.doubleValue()) {
                    serviceAmt = BigDecimal.ONE;
                } else {
                    serviceAmt = amount.multiply(serviceAmtPer).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
                    if(serviceAmt.doubleValue() > 25) { // 最大手续费25
                        serviceAmt = BigDecimal.valueOf(25);
                    }

                }
                amount = amount.subtract(serviceAmt);
            }

            obj.put("amount", amount);
            obj.put("minAmount", Application.getString("WD06", "10"));
            obj.put("serviceAmtPer", serviceAmtPer);
            obj.put("serviceAmt", serviceAmt);
            obj.put("kfWechatNo", Application.getString("SV400"));
            j.setObj(obj);
            j.success();
            j.setMsg("获取提现配置");

        } catch (ServiceException e) {
            j.setObj(e.getMessage());
            logger.error("获取提现配置接口异常", e);
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("获取提现配置接口异常", e);
        }
        return j;
    }

    /**
     * 申请提现
     * @param request
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/applyWithdraw")
    public Json applyWithdraw(LjzWithdrawLog withdrawLog, HttpServletRequest request){
        Json j = new Json();
        try{
            SessionInfo s = getSessionInfo(request);

            String refType = F.empty(withdrawLog.getRefType()) ? "BBT006" : withdrawLog.getRefType();
            LjzWithdrawLog log = new LjzWithdrawLog();
            log.setUserId(Integer.valueOf(s.getId()));
            log.setRefType(refType);
            log.setAddtime(new Date());
            List<LjzWithdrawLog> logs = ljzWithdrawLogService.query(log);
            int cashNum = Integer.valueOf(Application.getString("WD01", "1"));
            if(logs.size() >= cashNum) {
                j.setMsg("今日申请次数已用完，请明日再来");
                return j;
            }

            BigDecimal minAmount = new BigDecimal(Application.getString("WD06", "10"));
            if(F.empty(withdrawLog.getAmount()) || withdrawLog.getAmount().doubleValue() < minAmount.doubleValue()) {
                j.setMsg("单次提现不能低于"+minAmount+"元");
                return j;
            }

            int serviceAmtFlag = Integer.valueOf(Application.getString("WD04", "1"));
            BigDecimal serviceAmtPer = new BigDecimal(Application.getString("WD05", "0.1"));
            BigDecimal serviceAmt = BigDecimal.ZERO;

            if(serviceAmtFlag == 1) {
                // 计算手续费1元的最小临界值
                BigDecimal minCritical = BigDecimal.ONE.divide(serviceAmtPer, 2, BigDecimal.ROUND_HALF_UP).multiply(BigDecimal.valueOf(100));
                if(withdrawLog.getAmount().doubleValue() <= minCritical.doubleValue()) {
                    serviceAmt = BigDecimal.ONE;
                } else {
                    serviceAmt = withdrawLog.getAmount().multiply(serviceAmtPer).divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
                    if(serviceAmt.doubleValue() > 25) { // 最大手续费25
                        serviceAmt = BigDecimal.valueOf(25);
                    }
                }
            }

            BigDecimal max = new BigDecimal(Application.getString("WD02", "1000"));
            if(withdrawLog.getAmount().doubleValue() + serviceAmt.doubleValue() > max.doubleValue()) {
                j.setMsg("单次提现不得超过额度" + max.subtract(serviceAmt) + "元");
                return j;
            }

            LjzBalance balance = ljzBalanceService.addOrGetBalance(Integer.valueOf(s.getId()));
            if(balance == null || balance.getAmount().doubleValue() < (withdrawLog.getAmount().doubleValue() + serviceAmt.doubleValue())) {
                j.setMsg("余额不足！");
                return j;
            }

            withdrawLog.setUserId(Integer.valueOf(s.getId()));
            withdrawLog.setApplyLoginIp(IpUtil.getIp(request));
            withdrawLog.setRefType(refType);
            withdrawLog.setWithdrawNo(Util.CreateNo("T"));
            withdrawLog.setHandleStatus("HS01");
            withdrawLog.setServiceAmt(serviceAmt);
            ljzWithdrawLogService.addAndBalance(withdrawLog);
            j.success();
            j.setMsg("申请成功");

        } catch (ServiceException e) {
            j.setObj(e.getMessage());
            logger.error("申请提现接口异常", e);
        }catch(Exception e){
            j.setMsg(Application.getString(EX_0001));
            logger.error("申请提现接口异常", e);
        }
        return j;
    }
}
