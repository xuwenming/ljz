package com.mobian.pageModel;

import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class LjzPrizeLog implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.Integer id;	
	private Date addtime;			
	private Date updatetime;			
	private java.lang.Boolean isdeleted;	
	private java.lang.Integer userId;	
	private java.lang.Integer goodsId;	
	private BigDecimal amount;
	private java.lang.Integer quantity;
	private java.lang.String remark;

	private Boolean isToday;
	private LjzUser user;

	private Date addtimeStart;
	private Date addtimeEnd;

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
	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}
	
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	public void setGoodsId(java.lang.Integer goodsId) {
		this.goodsId = goodsId;
	}
	
	public java.lang.Integer getGoodsId() {
		return this.goodsId;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getAmount() {
		return this.amount;
	}
	public void setQuantity(java.lang.Integer quantity) {
		this.quantity = quantity;
	}
	
	public java.lang.Integer getQuantity() {
		return this.quantity;
	}

	public Boolean getToday() {
		return isToday;
	}

	public void setToday(Boolean today) {
		isToday = today;
	}

	public LjzUser getUser() {
		return user;
	}

	public void setUser(LjzUser user) {
		this.user = user;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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
