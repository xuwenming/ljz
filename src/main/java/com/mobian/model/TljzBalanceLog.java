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
@Table(name = "ljz_balance_log")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TljzBalanceLog implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "LjzBalanceLog";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_BALANCE_ID = "财产id";
	public static final String ALIAS_AMOUNT = "流水金额";
	public static final String ALIAS_REF_ID = "业务id";
	public static final String ALIAS_REF_TYPE = "业务类型";
	public static final String ALIAS_REMARK = "备注";
	
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
	private java.lang.Integer balanceId;
	//@NotNull 
	private BigDecimal amount;
	private BigDecimal balanceAmount;
	//
	private java.lang.Integer refId;
	//@Length(max=10)
	private java.lang.String refType;
	//@NotBlank @Length(max=512)
	private java.lang.String remark;
	//columns END


		public TljzBalanceLog(){
		}
		public TljzBalanceLog(Integer id) {
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
	
	@Column(name = "balance_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getBalanceId() {
		return this.balanceId;
	}
	
	public void setBalanceId(java.lang.Integer balanceId) {
		this.balanceId = balanceId;
	}
	
	@Column(name = "amount", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public BigDecimal getAmount() {
		return this.amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name = "balance_amount", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	@Column(name = "ref_id", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.Integer getRefId() {
		return this.refId;
	}
	
	public void setRefId(java.lang.Integer refId) {
		this.refId = refId;
	}
	
	@Column(name = "ref_type", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getRefType() {
		return this.refType;
	}
	
	public void setRefType(java.lang.String refType) {
		this.refType = refType;
	}
	
	@Column(name = "remark", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public java.lang.String getRemark() {
		return this.remark;
	}
	
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("BalanceId",getBalanceId())
			.append("Amount",getAmount())
			.append("RefId",getRefId())
			.append("RefType",getRefType())
			.append("Remark",getRemark())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LjzBalanceLog == false) return false;
		if(this == obj) return true;
		LjzBalanceLog other = (LjzBalanceLog)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

