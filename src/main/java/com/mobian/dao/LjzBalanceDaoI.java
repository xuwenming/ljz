package com.mobian.dao;

import com.mobian.model.TljzBalance;

import java.math.BigDecimal;

/**
 * LjzBalance数据库操作类
 * 
 * @author John
 * 
 */
public interface LjzBalanceDaoI extends BaseDaoI<TljzBalance> {

    BigDecimal getAmountById(Integer balanceId);
}
