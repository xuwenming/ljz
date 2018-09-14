package com.mobian.pageModel;

import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class LjzPayment implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.Integer id;	
	private Date addtime;			
	private Date updatetime;			
	private java.lang.Boolean isdeleted;	
	private java.lang.Integer orderId;	
	private BigDecimal amount;
	private java.lang.String payWay;	
	private java.lang.Boolean status;	

	private String refId;
	

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
	public void setOrderId(java.lang.Integer orderId) {
		this.orderId = orderId;
	}
	
	public java.lang.Integer getOrderId() {
		return this.orderId;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getAmount() {
		return this.amount;
	}
	public void setPayWay(java.lang.String payWay) {
		this.payWay = payWay;
	}
	
	public java.lang.String getPayWay() {
		return this.payWay;
	}
	public void setStatus(java.lang.Boolean status) {
		this.status = status;
	}
	
	public java.lang.Boolean getStatus() {
		return this.status;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}
}
