/*
 * @author John
 * @date - 2018-09-10
 */

package com.mobian.model;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "ljz_goods")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TljzGoods implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "LjzGoods";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_SHOP_ID = "店铺ID";
	public static final String ALIAS_TITLE = "商品名称";
	public static final String ALIAS_PRICE = "售价";
	public static final String ALIAS_ICON = "icon商品小图标";
	public static final String ALIAS_IMAGE_URL = "商品图片";
	public static final String ALIAS_DESCRIBTION = "商品描述";
	public static final String ALIAS_IS_PUT_AWAY = "是否上下架 0：上架   1:下架";
	public static final String ALIAS_LIMIT_NUMBER = "限购数量";
	public static final String ALIAS_FREIGHT = "运费";
	public static final String ALIAS_SHARE_AMOUNT = "转发金额";
	public static final String ALIAS_PRIZE_PRE = "奖池百分比";
	public static final String ALIAS_PRIZE_AMOUNT = "奖池金额";
	public static final String ALIAS_PRIZE_NUMBER = "中奖人数";
	
	//date formats
	public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

	//可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
	//columns START
	//
	private java.lang.Integer id;
	//@NotNull 
	private java.util.Date addtime;
	//@NotNull 
	private java.util.Date updatetime;
	//@NotNull 
	private java.lang.Boolean isdeleted;
	//
	private java.lang.Integer shopId;
	//@Length(max=128)
	private java.lang.String title;
	//
	private BigDecimal price;
	//@Length(max=100)
	private java.lang.String icon;
	//@Length(max=65535)
	private java.lang.String imageUrl;
	//@Length(max=100)
	private java.lang.String describtion;
	//
	private java.lang.Boolean isPutAway;
	//
	private java.lang.Integer limitNumber;
	//
	private BigDecimal freight;
	//
	private BigDecimal shareAmount;
	//
	private java.lang.Integer prizePre;
	//
	private BigDecimal prizeAmount;
	//
	private java.lang.Integer prizeNumber;
	//columns END


		public TljzGoods(){
		}
		public TljzGoods(Integer id) {
			this.id = id;
		}
	

	public void setId(java.lang.Integer id) {
		this.id = id;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false, length = 10)
	public java.lang.Integer getId() {
		return this.id;
	}
	

	@Column(name = "addtime", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public java.util.Date getAddtime() {
		return this.addtime;
	}
	
	public void setAddtime(java.util.Date addtime) {
		this.addtime = addtime;
	}
	

	@Column(name = "updatetime", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public java.util.Date getUpdatetime() {
		return this.updatetime;
	}
	
	public void setUpdatetime(java.util.Date updatetime) {
		this.updatetime = updatetime;
	}
	
	@Column(name = "isdeleted", unique = false, nullable = false, insertable = true, updatable = true, length = 0)
	public java.lang.Boolean getIsdeleted() {
		return this.isdeleted;
	}
	
	public void setIsdeleted(java.lang.Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}
	
	@Column(name = "shop_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getShopId() {
		return this.shopId;
	}
	
	public void setShopId(java.lang.Integer shopId) {
		this.shopId = shopId;
	}
	
	@Column(name = "title", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public java.lang.String getTitle() {
		return this.title;
	}
	
	public void setTitle(java.lang.String title) {
		this.title = title;
	}
	
	@Column(name = "price", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public BigDecimal getPrice() {
		return this.price;
	}
	
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	@Column(name = "icon", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getIcon() {
		return this.icon;
	}
	
	public void setIcon(java.lang.String icon) {
		this.icon = icon;
	}
	
	@Column(name = "image_url", unique = false, nullable = true, insertable = true, updatable = true, length = 65535)
	public java.lang.String getImageUrl() {
		return this.imageUrl;
	}
	
	public void setImageUrl(java.lang.String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	@Column(name = "describtion", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
	public java.lang.String getDescribtion() {
		return this.describtion;
	}
	
	public void setDescribtion(java.lang.String describtion) {
		this.describtion = describtion;
	}
	
	@Column(name = "is_put_away", unique = false, nullable = true, insertable = true, updatable = true, length = 0)
	public java.lang.Boolean getIsPutAway() {
		return this.isPutAway;
	}
	
	public void setIsPutAway(java.lang.Boolean isPutAway) {
		this.isPutAway = isPutAway;
	}
	
	@Column(name = "limit_number", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getLimitNumber() {
		return this.limitNumber;
	}
	
	public void setLimitNumber(java.lang.Integer limitNumber) {
		this.limitNumber = limitNumber;
	}
	
	@Column(name = "freight", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public BigDecimal getFreight() {
		return this.freight;
	}
	
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	
	@Column(name = "share_amount", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public BigDecimal getShareAmount() {
		return this.shareAmount;
	}
	
	public void setShareAmount(BigDecimal shareAmount) {
		this.shareAmount = shareAmount;
	}
	
	@Column(name = "prize_pre", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getPrizePre() {
		return this.prizePre;
	}
	
	public void setPrizePre(java.lang.Integer prizePre) {
		this.prizePre = prizePre;
	}
	
	@Column(name = "prize_amount", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public BigDecimal getPrizeAmount() {
		return this.prizeAmount;
	}
	
	public void setPrizeAmount(BigDecimal prizeAmount) {
		this.prizeAmount = prizeAmount;
	}
	
	@Column(name = "prize_number", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getPrizeNumber() {
		return this.prizeNumber;
	}
	
	public void setPrizeNumber(java.lang.Integer prizeNumber) {
		this.prizeNumber = prizeNumber;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("ShopId",getShopId())
			.append("Title",getTitle())
			.append("Price",getPrice())
			.append("Icon",getIcon())
			.append("ImageUrl",getImageUrl())
			.append("Describtion",getDescribtion())
			.append("IsPutAway",getIsPutAway())
			.append("LimitNumber",getLimitNumber())
			.append("Freight",getFreight())
			.append("ShareAmount",getShareAmount())
			.append("PrizePre",getPrizePre())
			.append("PrizeAmount",getPrizeAmount())
			.append("PrizeNumber",getPrizeNumber())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LjzGoods == false) return false;
		if(this == obj) return true;
		LjzGoods other = (LjzGoods)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

