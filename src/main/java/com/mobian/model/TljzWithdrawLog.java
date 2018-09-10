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
@Table(name = "ljz_withdraw_log")
@DynamicInsert(true)
@DynamicUpdate(true)
public class TljzWithdrawLog implements java.io.Serializable,IEntity{
	private static final long serialVersionUID = 5454155825314635342L;
	
	//alias
	public static final String TABLE_ALIAS = "LjzWithdrawLog";
	public static final String ALIAS_ID = "主键";
	public static final String ALIAS_ADDTIME = "添加时间";
	public static final String ALIAS_UPDATETIME = "修改时间";
	public static final String ALIAS_ISDELETED = "是否删除,1删除，0未删除";
	public static final String ALIAS_WITHDRAW_NO = "提现流水号";
	public static final String ALIAS_AMOUNT = "提现金额";
	public static final String ALIAS_SERVICE_AMT = "手续费";
	public static final String ALIAS_USER_ID = "申请人ID";
	public static final String ALIAS_CONTENT = "申请备注";
	public static final String ALIAS_HANDLE_STATUS = "处理状态";
	public static final String ALIAS_HANDLE_LOGIN_ID = "处理人";
	public static final String ALIAS_HANDLE_REMARK = "处理结果";
	public static final String ALIAS_HANDLE_TIME = "处理时间";
	public static final String ALIAS_PAYMENT_NO = "第三方流水号";
	public static final String ALIAS_CMMS_AMT = "第三方手续费";
	public static final String ALIAS_REF_TYPE = "业务类型";
	public static final String ALIAS_APPLY_LOGIN_IP = "提现IP";
	
	//date formats
	public static final String FORMAT_ADDTIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_UPDATETIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	public static final String FORMAT_HANDLE_TIME = com.mobian.util.Constants.DATE_FORMAT_FOR_ENTITY;
	

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
	//@Length(max=64)
	private java.lang.String withdrawNo;
	//@NotNull 
	private BigDecimal amount;
	//
	private BigDecimal serviceAmt;
	//@Length(max=36)
	private java.lang.String userId;
	//@Length(max=512)
	private java.lang.String content;
	//@Length(max=4)
	private java.lang.String handleStatus;
	//@Length(max=36)
	private java.lang.String handleLoginId;
	//@Length(max=512)
	private java.lang.String handleRemark;
	//
	private java.util.Date handleTime;
	//@Length(max=64)
	private java.lang.String paymentNo;
	//
	private BigDecimal cmmsAmt;
	//@Length(max=10)
	private java.lang.String refType;
	//@Length(max=64)
	private java.lang.String applyLoginIp;
	//columns END


		public TljzWithdrawLog(){
		}
		public TljzWithdrawLog(Integer id) {
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
	
	@Column(name = "withdraw_no", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public java.lang.String getWithdrawNo() {
		return this.withdrawNo;
	}
	
	public void setWithdrawNo(java.lang.String withdrawNo) {
		this.withdrawNo = withdrawNo;
	}
	
	@Column(name = "amount", unique = false, nullable = false, insertable = true, updatable = true, length = 19)
	public BigDecimal getAmount() {
		return this.amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@Column(name = "service_amt", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public BigDecimal getServiceAmt() {
		return this.serviceAmt;
	}
	
	public void setServiceAmt(BigDecimal serviceAmt) {
		this.serviceAmt = serviceAmt;
	}
	
	@Column(name = "user_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.String userId) {
		this.userId = userId;
	}
	
	@Column(name = "content", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public java.lang.String getContent() {
		return this.content;
	}
	
	public void setContent(java.lang.String content) {
		this.content = content;
	}
	
	@Column(name = "handle_status", unique = false, nullable = true, insertable = true, updatable = true, length = 4)
	public java.lang.String getHandleStatus() {
		return this.handleStatus;
	}
	
	public void setHandleStatus(java.lang.String handleStatus) {
		this.handleStatus = handleStatus;
	}
	
	@Column(name = "handle_login_id", unique = false, nullable = true, insertable = true, updatable = true, length = 36)
	public java.lang.String getHandleLoginId() {
		return this.handleLoginId;
	}
	
	public void setHandleLoginId(java.lang.String handleLoginId) {
		this.handleLoginId = handleLoginId;
	}
	
	@Column(name = "handle_remark", unique = false, nullable = true, insertable = true, updatable = true, length = 512)
	public java.lang.String getHandleRemark() {
		return this.handleRemark;
	}
	
	public void setHandleRemark(java.lang.String handleRemark) {
		this.handleRemark = handleRemark;
	}
	

	@Column(name = "handle_time", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public java.util.Date getHandleTime() {
		return this.handleTime;
	}
	
	public void setHandleTime(java.util.Date handleTime) {
		this.handleTime = handleTime;
	}
	
	@Column(name = "payment_no", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public java.lang.String getPaymentNo() {
		return this.paymentNo;
	}
	
	public void setPaymentNo(java.lang.String paymentNo) {
		this.paymentNo = paymentNo;
	}
	
	@Column(name = "cmms_amt", unique = false, nullable = true, insertable = true, updatable = true, length = 19)
	public BigDecimal getCmmsAmt() {
		return this.cmmsAmt;
	}
	
	public void setCmmsAmt(BigDecimal cmmsAmt) {
		this.cmmsAmt = cmmsAmt;
	}
	
	@Column(name = "ref_type", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
	public java.lang.String getRefType() {
		return this.refType;
	}
	
	public void setRefType(java.lang.String refType) {
		this.refType = refType;
	}
	
	@Column(name = "apply_login_ip", unique = false, nullable = true, insertable = true, updatable = true, length = 64)
	public java.lang.String getApplyLoginIp() {
		return this.applyLoginIp;
	}
	
	public void setApplyLoginIp(java.lang.String applyLoginIp) {
		this.applyLoginIp = applyLoginIp;
	}
	
	
	/*
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Addtime",getAddtime())
			.append("Updatetime",getUpdatetime())
			.append("Isdeleted",getIsdeleted())
			.append("WithdrawNo",getWithdrawNo())
			.append("Amount",getAmount())
			.append("ServiceAmt",getServiceAmt())
			.append("UserId",getUserId())
			.append("Content",getContent())
			.append("HandleStatus",getHandleStatus())
			.append("HandleLoginId",getHandleLoginId())
			.append("HandleRemark",getHandleRemark())
			.append("HandleTime",getHandleTime())
			.append("PaymentNo",getPaymentNo())
			.append("CmmsAmt",getCmmsAmt())
			.append("RefType",getRefType())
			.append("ApplyLoginIp",getApplyLoginIp())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof LjzWithdrawLog == false) return false;
		if(this == obj) return true;
		LjzWithdrawLog other = (LjzWithdrawLog)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}*/
}

