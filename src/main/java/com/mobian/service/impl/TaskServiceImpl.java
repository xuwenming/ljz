//package com.mobian.service.impl;
//
//import com.mobian.absx.F;
//import com.mobian.interceptors.TokenManage;
//import com.mobian.listener.Application;
//import com.mobian.pageModel.*;
//import com.mobian.service.*;
//import com.mobian.thirdpart.easemob.HuanxinUtil;
//import com.mobian.util.Constants;
//import com.mobian.util.DateUtil;
//import org.apache.commons.collections.CollectionUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///**
//* Created by john on 16/10/16.
//*/
//@Service
//public class TaskServiceImpl implements TaskServiceI {
//
//    @Autowired
//    private TokenManage tokenManage;
//
//    @Autowired
//    private FdMemberServiceI fdMemberService;
//
//    @Autowired
//    private FdWithdrawLogServiceI fdWithdrawLogService;
//
//    @Autowired
//    private FdMessageServiceI fdMessageService;
//
//    @Autowired
//    private FdBalanceLogServiceI fdBalanceLogService;
//
//    @Override
//    public void deleteHxAccount() {
//        try {
//            int status = Integer.valueOf(Application.getString(HuanxinUtil.OPEN_STATUS, "1"));
//            if(status == 1) {
//                List<FdMember> memberList = fdMemberService.queryAllByDelHxAccount();
//                if(CollectionUtils.isNotEmpty(memberList)) {
//                    for(FdMember member : memberList) {
//                        // 删除环信账号
//                        HuanxinUtil.delUser(member.getIsAdmin() + "-" + member.getMobile());
//                        // 更新环信状态
//                        FdMember o = new FdMember();
//                        o.setId(member.getId());
//                        o.setHxStatus(false);
//                        fdMemberService.edit(o);
//
//                        // 清除当前用户的登录token
//                        tokenManage.destroyTokenByMbUserId(member.getId() + "");
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void syncWxWithdrawStatus() {
//        try {
//            // 查询所有的审核成功的提现记录
//            FdWithdrawLog log = new FdWithdrawLog();
//            log.setHandleStatus("HS02");
//            List<FdWithdrawLog> withdrawLogs = fdWithdrawLogService.query(log);
//            if(CollectionUtils.isNotEmpty(withdrawLogs)) {
//                for(FdWithdrawLog withdrawLog : withdrawLogs) {
//                    Map<String, String> map = fdWithdrawLogService.queryStatus(withdrawLog.getWithdrawNo());
//                    FdWithdrawLog updatelog = new FdWithdrawLog();
//                    updatelog.setId(withdrawLog.getId());
//                    if("SUCCESS".equals(map.get("status"))) {
//                        updatelog.setHandleStatus("HS04"); // 提现到账
//                        updatelog.setUpdateTime(new Date().getTime());
//                        fdWithdrawLogService.edit(updatelog);
//
//                        // 添加用户消息
//                        FdMember user = fdMemberService.get(Integer.valueOf(withdrawLog.getUserId()));
//                        FdMessage message = new FdMessage();
//                        message.setTitle("提现到账通知");
//                        String content = "尊敬的用户您好，您的提现已到账，请注意查收!" +
//                                "\n到账时间：" + DateUtil.format(new Date(), Constants.DATE_FORMAT) +
//                                "\n提现单号：" + withdrawLog.getWithdrawNo() +
//                                "\n提现金额：" + BigDecimal.valueOf(withdrawLog.getAmount()).divide(new BigDecimal(100)) + "元" +
//                                "\n手续费：" + BigDecimal.valueOf(withdrawLog.getServiceAmt()).divide(new BigDecimal(100)) + "元" +
//                                "\n实际到账：" + BigDecimal.valueOf(withdrawLog.getAmount()).divide(new BigDecimal(100)) + "元" +
//                                "\n银行：" + withdrawLog.getBankCodeZh() +
//                                "\n开户行支行：" + withdrawLog.getBankName() +
//                                "\n银行卡号：" + withdrawLog.getBankCard() +
//                                "\n开户人姓名：" + withdrawLog.getBankAccount() +
//                                "\n\n有任何疑问可通过客户端直接联系我们，谢谢！";
//                        message.setContent(content);
//                        message.setUserId(Integer.valueOf(withdrawLog.getUserId()));
//                        message.setMtype("MT02");
//                        message.setIsRead(false);
//                        message.setAlias("0_" + user.getMobile());
//                        message.setPushMessage(new PushMessage("M401", "您在医家盟申请的提现已到账!"));
//                        fdMessageService.addAndPushMessage(message);
//
//                    } else if("FAILED".equals(map.get("status")) || "BANK_FAIL".equals(map.get("status"))) {
//                        String reason = map.get("reason");
//                        reason = F.empty(reason) ? "微信提现失败，请核对您的信息" : reason;
//
//                        updatelog.setHandleStatus("HS05"); // 提现失败
//                        updatelog.setUpdateTime(new Date().getTime());
//                        updatelog.setHandleRemark(reason);
//                        fdWithdrawLogService.edit(updatelog);
//
//                        // 余额退回
//                        FdBalanceLog balanceLog = new FdBalanceLog();
//                        balanceLog.setUserId(Long.valueOf(withdrawLog.getUserId()));
//                        balanceLog.setRefType("BBT009");
//                        balanceLog.setRefId(withdrawLog.getId() + "");
//                        balanceLog.setAmount(BigDecimal.valueOf(withdrawLog.getAmount() + withdrawLog.getServiceAmt()).divide(new BigDecimal(100)).floatValue());
//                        balanceLog.setStatus(false);
//                        balanceLog.setNote("提现失败，余额退回");
//                        fdBalanceLogService.addLogAndUpdateBalance(balanceLog);
//
//                        // 添加用户消息
//                        FdMember user = fdMemberService.get(Integer.valueOf(withdrawLog.getUserId()));
//                        FdMessage message = new FdMessage();
//                        message.setTitle("提现失败提醒");
//                        Calendar c = Calendar.getInstance();
//                        c.setTimeInMillis(withdrawLog.getCreateTime());
//                        String content = "尊敬的用户您好，您的提现第三方平台提现失败!" +
//                                "\n提现时间：" + DateUtil.format(c.getTime(), Constants.DATE_FORMAT) +
//                                "\n提现单号：" + withdrawLog.getWithdrawNo() +
//                                "\n提现金额：" + BigDecimal.valueOf(withdrawLog.getAmount()).divide(new BigDecimal(100)) + "元" +
//                                "\n手续费：" + BigDecimal.valueOf(withdrawLog.getServiceAmt()).divide(new BigDecimal(100)) + "元" +
//                                "\n实际退额：" + BigDecimal.valueOf(withdrawLog.getAmount() + withdrawLog.getServiceAmt()).divide(new BigDecimal(100)) + "元" +
//                                "\n银行：" + withdrawLog.getBankCodeZh() +
//                                "\n开户行支行：" + withdrawLog.getBankName() +
//                                "\n银行卡号：" + withdrawLog.getBankCard() +
//                                "\n开户人姓名：" + withdrawLog.getBankAccount() +
//                                "\n原因：" + reason +
//                                "\n\n钱款已退回余额，有任何疑问可通过客户端直接联系我们，谢谢！";
//                        message.setContent(content);
//                        message.setUserId(Integer.valueOf(withdrawLog.getUserId()));
//                        message.setMtype("MT02");
//                        message.setIsRead(false);
//                        message.setAlias("0_" + user.getMobile());
//                        message.setPushMessage(new PushMessage("M401", "您在医家盟申请的提现失败，钱款已退回余额!"));
//                        fdMessageService.addAndPushMessage(message);
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
