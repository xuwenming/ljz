package com.mobian.pageModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public class LjzOrder implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.Integer id;	
	private Date addtime;			
	private Date updatetime;			
	private java.lang.Boolean isdeleted;	
	private java.lang.Integer shopId;	
	private java.lang.Integer userId;	
	private BigDecimal totalPrice;
	private java.lang.String status;	
	private java.lang.String deliveryAddress;	
	private java.lang.String contactPhone;	
	private java.lang.String contactPeople;	
	private java.lang.String payStatus;	
	private java.lang.String payWay;	
	private Date payTime;			
	private BigDecimal freight;	
	private java.lang.Integer recommend;	

	private List<LjzOrderItem> orderItemList;
	private Integer goodsId;
	private Boolean isYestoday;

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
	public void setShopId(java.lang.Integer shopId) {
		this.shopId = shopId;
	}
	
	public java.lang.Integer getShopId() {
		return this.shopId;
	}
	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}
	
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	public BigDecimal getTotalPrice() {
		return this.totalPrice;
	}
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	
	public java.lang.String getStatus() {
		return this.status;
	}
	public void setDeliveryAddress(java.lang.String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	public java.lang.String getDeliveryAddress() {
		return this.deliveryAddress;
	}
	public void setContactPhone(java.lang.String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	public java.lang.String getContactPhone() {
		return this.contactPhone;
	}
	public void setContactPeople(java.lang.String contactPeople) {
		this.contactPeople = contactPeople;
	}
	
	public java.lang.String getContactPeople() {
		return this.contactPeople;
	}
	public void setPayStatus(java.lang.String payStatus) {
		this.payStatus = payStatus;
	}
	
	public java.lang.String getPayStatus() {
		return this.payStatus;
	}
	public void setPayWay(java.lang.String payWay) {
		this.payWay = payWay;
	}
	
	public java.lang.String getPayWay() {
		return this.payWay;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	
	public Date getPayTime() {
		return this.payTime;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	
	public BigDecimal getFreight() {
		return this.freight;
	}
	public void setRecommend(java.lang.Integer recommend) {
		this.recommend = recommend;
	}
	
	public java.lang.Integer getRecommend() {
		return this.recommend;
	}

	public List<LjzOrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<LjzOrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Boolean getYestoday() {
		return isYestoday;
	}

	public void setYestoday(Boolean yestoday) {
		isYestoday = yestoday;
	}
}
