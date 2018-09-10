package com.mobian.pageModel;

import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class LjzGoods implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.Integer id;	
	private Date addtime;			
	private Date updatetime;			
	private java.lang.Boolean isdeleted;	
	private java.lang.Integer shopId;	
	private java.lang.String title;	
	private BigDecimal price;
	private java.lang.String icon;	
	private java.lang.String imageUrl;	
	private java.lang.String describtion;	
	private java.lang.Boolean isPutAway;	
	private java.lang.Integer limitNumber;	
	private BigDecimal freight;	
	private BigDecimal shareAmount;	
	private java.lang.Integer prizePre;	
	private BigDecimal prizeAmount;	
	private java.lang.Integer prizeNumber;	

	

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
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	
	public java.lang.String getTitle() {
		return this.title;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public BigDecimal getPrice() {
		return this.price;
	}
	public void setIcon(java.lang.String icon) {
		this.icon = icon;
	}
	
	public java.lang.String getIcon() {
		return this.icon;
	}
	public void setImageUrl(java.lang.String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public java.lang.String getImageUrl() {
		return this.imageUrl;
	}
	public void setDescribtion(java.lang.String describtion) {
		this.describtion = describtion;
	}
	
	public java.lang.String getDescribtion() {
		return this.describtion;
	}
	public void setIsPutAway(java.lang.Boolean isPutAway) {
		this.isPutAway = isPutAway;
	}
	
	public java.lang.Boolean getIsPutAway() {
		return this.isPutAway;
	}
	public void setLimitNumber(java.lang.Integer limitNumber) {
		this.limitNumber = limitNumber;
	}
	
	public java.lang.Integer getLimitNumber() {
		return this.limitNumber;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	
	public BigDecimal getFreight() {
		return this.freight;
	}
	public void setShareAmount(BigDecimal shareAmount) {
		this.shareAmount = shareAmount;
	}
	
	public BigDecimal getShareAmount() {
		return this.shareAmount;
	}
	public void setPrizePre(java.lang.Integer prizePre) {
		this.prizePre = prizePre;
	}
	
	public java.lang.Integer getPrizePre() {
		return this.prizePre;
	}
	public void setPrizeAmount(BigDecimal prizeAmount) {
		this.prizeAmount = prizeAmount;
	}
	
	public BigDecimal getPrizeAmount() {
		return this.prizeAmount;
	}
	public void setPrizeNumber(java.lang.Integer prizeNumber) {
		this.prizeNumber = prizeNumber;
	}
	
	public java.lang.Integer getPrizeNumber() {
		return this.prizeNumber;
	}

}
