package com.mobian.pageModel;

import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class LjzOrderItem implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.Integer id;	
	private Date addtime;			
	private Date updatetime;			
	private java.lang.Boolean isdeleted;	
	private java.lang.Integer orderId;	
	private java.lang.Integer goodsId;	
	private java.lang.Integer quantity;	
	private BigDecimal buyPrice;

	

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
	public void setGoodsId(java.lang.Integer goodsId) {
		this.goodsId = goodsId;
	}
	
	public java.lang.Integer getGoodsId() {
		return this.goodsId;
	}
	public void setQuantity(java.lang.Integer quantity) {
		this.quantity = quantity;
	}
	
	public java.lang.Integer getQuantity() {
		return this.quantity;
	}
	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}
	
	public BigDecimal getBuyPrice() {
		return this.buyPrice;
	}

}
