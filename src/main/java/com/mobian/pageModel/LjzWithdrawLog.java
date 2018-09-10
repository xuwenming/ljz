package com.mobian.pageModel;

import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class LjzWithdrawLog implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.Integer id;	
	private Date addtime;			
	private Date updatetime;			
	private java.lang.Boolean isdeleted;	
	private java.lang.String withdrawNo;	
	private BigDecimal amount;	
	private BigDecimal serviceAmt;
	private java.lang.String userId;	
	private java.lang.String content;	
	private java.lang.String handleStatus;	
	private java.lang.String handleLoginId;	
	private java.lang.String handleRemark;	
	private Date handleTime;			
	private java.lang.String paymentNo;	
	private BigDecimal cmmsAmt;	
	private java.lang.String refType;	
	private java.lang.String applyLoginIp;	

	

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
	public void setWithdrawNo(java.lang.String withdrawNo) {
		this.withdrawNo = withdrawNo;
	}
	
	public java.lang.String getWithdrawNo() {
		return this.withdrawNo;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getAmount() {
		return this.amount;
	}
	public void setServiceAmt(BigDecimal serviceAmt) {
		this.serviceAmt = serviceAmt;
	}
	
	public BigDecimal getServiceAmt() {
		return this.serviceAmt;
	}
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	public java.lang.String getUserId() {
		return this.userId;
	}
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	public java.lang.String getContent() {
		return this.content;
	}
	public void setHandleStatus(java.lang.String handleStatus) {
		this.handleStatus = handleStatus;
	}
	
	public java.lang.String getHandleStatus() {
		return this.handleStatus;
	}
	public void setHandleLoginId(java.lang.String handleLoginId) {
		this.handleLoginId = handleLoginId;
	}
	
	public java.lang.String getHandleLoginId() {
		return this.handleLoginId;
	}
	public void setHandleRemark(java.lang.String handleRemark) {
		this.handleRemark = handleRemark;
	}
	
	public java.lang.String getHandleRemark() {
		return this.handleRemark;
	}
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	
	public Date getHandleTime() {
		return this.handleTime;
	}
	public void setPaymentNo(java.lang.String paymentNo) {
		this.paymentNo = paymentNo;
	}
	
	public java.lang.String getPaymentNo() {
		return this.paymentNo;
	}
	public void setCmmsAmt(BigDecimal cmmsAmt) {
		this.cmmsAmt = cmmsAmt;
	}
	
	public BigDecimal getCmmsAmt() {
		return this.cmmsAmt;
	}
	public void setRefType(java.lang.String refType) {
		this.refType = refType;
	}
	
	public java.lang.String getRefType() {
		return this.refType;
	}
	public void setApplyLoginIp(java.lang.String applyLoginIp) {
		this.applyLoginIp = applyLoginIp;
	}
	
	public java.lang.String getApplyLoginIp() {
		return this.applyLoginIp;
	}

}
