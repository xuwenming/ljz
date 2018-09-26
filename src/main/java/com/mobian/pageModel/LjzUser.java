package com.mobian.pageModel;

import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class LjzUser implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.Integer id;	
	private Date addtime;			
	private Date updatetime;			
	private java.lang.Boolean isdeleted;	
	private java.lang.String nickName;	
	private java.lang.String phone;	
	private java.lang.String icon;	
	private java.lang.Integer sex;	
	private java.lang.String refId;	
	private java.lang.String refType;	
	private java.lang.String recommends;	

	private BigDecimal balance;

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
	public void setNickName(java.lang.String nickName) {
		this.nickName = nickName;
	}
	
	public java.lang.String getNickName() {
		return this.nickName;
	}
	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}
	
	public java.lang.String getPhone() {
		return this.phone;
	}
	public void setIcon(java.lang.String icon) {
		this.icon = icon;
	}
	
	public java.lang.String getIcon() {
		return this.icon;
	}
	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}
	
	public java.lang.Integer getSex() {
		return this.sex;
	}
	public void setRefId(java.lang.String refId) {
		this.refId = refId;
	}
	
	public java.lang.String getRefId() {
		return this.refId;
	}
	public void setRefType(java.lang.String refType) {
		this.refType = refType;
	}
	
	public java.lang.String getRefType() {
		return this.refType;
	}
	public void setRecommends(java.lang.String recommends) {
		this.recommends = recommends;
	}
	
	public java.lang.String getRecommends() {
		return this.recommends;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
}
