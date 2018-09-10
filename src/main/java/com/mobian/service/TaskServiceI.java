package com.mobian.service;

/**
 * Created by john on 16/10/16.
 */
public interface TaskServiceI {
    /**
     * 剔除环信账号
     */
    void deleteHxAccount();

    /**
     * 同步微信提现状态
     */
    void syncWxWithdrawStatus();

}
