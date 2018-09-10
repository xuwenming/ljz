/*
 * @author John
 * @date - 2018-09-10
 */

package com.mobian.model;

import javax.persistence.*;

import java.util.Date;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@SuppressWarnings("serial")
@Entity
@Table(name = "ljz_user")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TljzUser implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "LjzUser";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_NICK_NAME = "昵称";
	public static final String ALIAS_PHONE = "手机号码";
	public static final String ALIAS_ICON = "头像";
	public static final String ALIAS_SEX = "性别";
	public static final String ALIAS_REF_ID = "第三方账号ID";
	public static final String ALIAS_REF_TYPE = "第三方账号类型";
	public static final String ALIAS_RECOMMENDS = "父级推荐用户id集合 ";
	
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
	//@Length(max=128)
	private java.lang.String nickName;
	//@NotBlank @Length(max=32)
	private java.lang.String phone;
	//@Length(max=512)
	private java.lang.String icon;
	//
	private java.lang.Integer sex;
	//@Length(max=64)
	private java.lang.String refId;
	//@Length(max=10)
	private java.lang.String refType;
	//@Length(max=512)
	private java.lang.String recommends;
	//columns END


		public TljzUser(){
		}
		public TljzUser(Integer id) {
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
	
	@Column(name = "nick_name", unique = false, nullable = true, insertable = true, updatable = true, length = 128)
	public java.lang.String getNickName() {
		return this.nickName;
	}
	
	public void setNickName(java.lang.String nickName) {
		this.nickName = nickName;
	}
	
	@Column(name = "phone", unique = false, nullable = false, insertable = true, updatable = true, length = 32)
	public java.lang.String getPhone() {
		return this.phone;
	}
	
	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}
	
	@Column(name = "icon", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public java.lang.String getIcon() {
		return this.icon;
	}
	
	public void setIcon(java.lang.String icon) {
		this.icon = icon;
	}
	
	@Column(name = "sex", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getSex() {
		return this.sex;
	}
	
	public void setSex(java.lang.Integer sex) {
		this.sex = sex;
	}
	
	@Column(name = "ref_id", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public java.lang.String getRefId() {
		return this.refId;
	}
	
	public void setRefId(java.lang.String refId) {
		this.refId = refId;
	}
	
	@Column(name = "ref_type", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getRefType() {
		return this.refType;
	}
	
	public void setRefType(java.lang.String refType) {
		this.refType = refType;
	}
	
	@Column(name = "recommends", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public java.lang.String getRecommends() {
		return this.recommends;
	}
	
	public void setRecommends(java.lang.String recommends) {
		this.recommends = recommends;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("NickName",getNickName())
			.append("Phone",getPhone())
			.append("Icon",getIcon())
			.append("Sex",getSex())
			.append("RefId",getRefId())
			.append("RefType",getRefType())
			.append("Recommends",getRecommends())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LjzUser == false) return false;
		if(this == obj) return true;
		LjzUser other = (LjzUser)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

