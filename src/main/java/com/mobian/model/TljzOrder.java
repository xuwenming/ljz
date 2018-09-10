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
@Table(name = "ljz_order")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TljzOrder implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "LjzOrder";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_SHOP_ID = "店铺ID";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_TOTAL_PRICE = "总金额";
	public static final String ALIAS_STATUS = "订单状态";
	public static final String ALIAS_DELIVERY_ADDRESS = "配送地址";
	public static final String ALIAS_CONTACT_PHONE = "联系电话";
	public static final String ALIAS_CONTACT_PEOPLE = "联系人";
	public static final String ALIAS_PAY_STATUS = "支付状态";
	public static final String ALIAS_PAY_WAY = "支付方式";
	public static final String ALIAS_PAY_TIME = "支付时间";
	public static final String ALIAS_FREIGHT = "运费";
	public static final String ALIAS_RECOMMEND = "推荐人id";
	
	//date formats
	public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_PAY_TIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

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
	//
	private java.lang.Integer userId;
	//
	private BigDecimal totalPrice;
	//@Length(max=4)
	private java.lang.String status;
	//@Length(max=512)
	private java.lang.String deliveryAddress;
	//@Length(max=32)
	private java.lang.String contactPhone;
	//@Length(max=32)
	private java.lang.String contactPeople;
	//@Length(max=4)
	private java.lang.String payStatus;
	//@Length(max=4)
	private java.lang.String payWay;
	//
	private java.util.Date payTime;
	//
	private BigDecimal freight;
	//
	private java.lang.Integer recommend;
	//columns END


		public TljzOrder(){
		}
		public TljzOrder(Integer id) {
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
	
	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}
	
	@Column(name = "total_price", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public BigDecimal getTotalPrice() {
		return this.totalPrice;
	}
	
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	@Column(name = "status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.String status) {
		this.status = status;
	}
	
	@Column(name = "delivery_address", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public java.lang.String getDeliveryAddress() {
		return this.deliveryAddress;
	}
	
	public void setDeliveryAddress(java.lang.String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	
	@Column(name = "contact_phone", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getContactPhone() {
		return this.contactPhone;
	}
	
	public void setContactPhone(java.lang.String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	@Column(name = "contact_people", unique = false, nullable = true, insertable = true, updatable = true, length = 32)
	public java.lang.String getContactPeople() {
		return this.contactPeople;
	}
	
	public void setContactPeople(java.lang.String contactPeople) {
		this.contactPeople = contactPeople;
	}
	
	@Column(name = "pay_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getPayStatus() {
		return this.payStatus;
	}
	
	public void setPayStatus(java.lang.String payStatus) {
		this.payStatus = payStatus;
	}
	
	@Column(name = "pay_way", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getPayWay() {
		return this.payWay;
	}
	
	public void setPayWay(java.lang.String payWay) {
		this.payWay = payWay;
	}
	

	@Column(name = "pay_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getPayTime() {
		return this.payTime;
	}
	
	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}
	
	@Column(name = "freight", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public BigDecimal getFreight() {
		return this.freight;
	}
	
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	
	@Column(name = "recommend", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getRecommend() {
		return this.recommend;
	}
	
	public void setRecommend(java.lang.Integer recommend) {
		this.recommend = recommend;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("ShopId",getShopId())
			.append("UserId",getUserId())
			.append("TotalPrice",getTotalPrice())
			.append("Status",getStatus())
			.append("DeliveryAddress",getDeliveryAddress())
			.append("ContactPhone",getContactPhone())
			.append("ContactPeople",getContactPeople())
			.append("PayStatus",getPayStatus())
			.append("PayWay",getPayWay())
			.append("PayTime",getPayTime())
			.append("Freight",getFreight())
			.append("Recommend",getRecommend())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LjzOrder == false) return false;
		if(this == obj) return true;
		LjzOrder other = (LjzOrder)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

