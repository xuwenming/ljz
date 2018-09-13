package com.mobian.dao.impl;

import com.mobian.dao.LjzBalanceDaoI;
import com.mobian.model.TljzBalance;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class LjzBalanceDaoImpl extends BaseDaoImpl<TljzBalance> implements LjzBalanceDaoI {


    @Override
    public BigDecimal getAmountById(Integer balanceId) {
        SQLQuery q = getCurrentSession().createSQLQuery("select amount from ljz_balance where id =:id");
        q.setParameter("id", balanceId);
        return (BigDecimal) q.uniqueResult();
    }
}
