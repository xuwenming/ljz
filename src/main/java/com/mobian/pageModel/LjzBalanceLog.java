package com.mobian.pageModel;

import com.mobian.listener.Application;

import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class LjzBalanceLog implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.Integer id;	
	private Date addtime;			
	private Date updatetime;			
	private java.lang.Boolean isdeleted;	
	private java.lang.Integer balanceId;	
	private BigDecimal amount;
	private BigDecimal balanceAmount;
	private java.lang.Integer refId;
	private java.lang.String refType;	
	private java.lang.String remark;	

	private Integer userId;
	private LjzUser user;

	private Date addtimeStart;
	private Date addtimeEnd;

	public String getRefTypeName() {
		return Application.getString(refType);
	}

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}

	
	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}
	
	public Date getAddtime() {
		return this.addtime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	public Date getUpdatetime() {
		return this.updatetime;
	}
	public void setIsdeleted(java.lang.Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}
	
	public java.lang.Boolean getIsdeleted() {
		return this.isdeleted;
	}
	public void setBalanceId(java.lang.Integer balanceId) {
		this.balanceId = balanceId;
	}
	
	public java.lang.Integer getBalanceId() {
		return this.balanceId;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getAmount() {
		return this.amount;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public void setRefId(java.lang.Integer refId) {
		this.refId = refId;
	}
	
	public java.lang.Integer getRefId() {
		return this.refId;
	}
	public void setRefType(java.lang.String refType) {
		this.refType = refType;
	}
	
	public java.lang.String getRefType() {
		return this.refType;
	}
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	public java.lang.String getRemark() {
		return this.remark;
	}

	public LjzUser getUser() {
		return user;
	}

	public void setUser(LjzUser user) {
		this.user = user;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Date getAddtimeStart() {
		return addtimeStart;
	}

	public void setAddtimeStart(Date addtimeStart) {
		this.addtimeStart = addtimeStart;
	}

	public Date getAddtimeEnd() {
		return addtimeEnd;
	}

	public void setAddtimeEnd(Date addtimeEnd) {
		this.addtimeEnd = addtimeEnd;
	}
}
