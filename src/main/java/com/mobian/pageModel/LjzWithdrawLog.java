package com.mobian.pageModel;

import com.mobian.listener.Application;

import java.math.BigDecimal;
import java.util.Date;

@SuppressWarnings("serial")
public class LjzWithdrawLog implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private Integer id;
	private Date addtime;			
	private Date updatetime;			
	private Boolean isdeleted;
	private String withdrawNo;
	private BigDecimal amount;	
	private BigDecimal serviceAmt;
	private Integer userId;
	private String realName;
	private String phone;
	private String content;
	private String handleStatus;
	private String handleLoginId;
	private String handleRemark;
	private Date handleTime;			
	private String paymentNo;
	private BigDecimal cmmsAmt;	
	private String refType;
	private String applyLoginIp;

	private Date addtimeStart;
	private Date addtimeEnd;
	private String handleLoginName;

	public String getHandleStatusZh() {
		return Application.getString(handleStatus);
	}

	public String getRefTypeZh() {
		return Application.getString(refType);
	}

	public void setId(Integer value) {
		this.id = value;
	}
	
	public Integer getId() {
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
	public void setIsdeleted(Boolean isdeleted) {
		this.isdeleted = isdeleted;
	}
	
	public Boolean getIsdeleted() {
		return this.isdeleted;
	}
	public void setWithdrawNo(String withdrawNo) {
		this.withdrawNo = withdrawNo;
	}
	
	public String getWithdrawNo() {
		return this.withdrawNo;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public BigDecimal getAmount() {
		return this.amount;
	}
	public void setServiceAmt(BigDecimal serviceAmt) {
		this.serviceAmt = serviceAmt;
	}
	
	public BigDecimal getServiceAmt() {
		return this.serviceAmt;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getRealName() {
		return this.realName;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return this.phone;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
	
	public String getHandleStatus() {
		return this.handleStatus;
	}
	public void setHandleLoginId(String handleLoginId) {
		this.handleLoginId = handleLoginId;
	}
	
	public String getHandleLoginId() {
		return this.handleLoginId;
	}
	public void setHandleRemark(String handleRemark) {
		this.handleRemark = handleRemark;
	}
	
	public String getHandleRemark() {
		return this.handleRemark;
	}
	public void setHandleTime(Date handleTime) {
		this.handleTime = handleTime;
	}
	
	public Date getHandleTime() {
		return this.handleTime;
	}
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}
	
	public String getPaymentNo() {
		return this.paymentNo;
	}
	public void setCmmsAmt(BigDecimal cmmsAmt) {
		this.cmmsAmt = cmmsAmt;
	}
	
	public BigDecimal getCmmsAmt() {
		return this.cmmsAmt;
	}
	public void setRefType(String refType) {
		this.refType = refType;
	}
	
	public String getRefType() {
		return this.refType;
	}
	public void setApplyLoginIp(String applyLoginIp) {
		this.applyLoginIp = applyLoginIp;
	}
	
	public String getApplyLoginIp() {
		return this.applyLoginIp;
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

	public String getHandleLoginName() {
		return handleLoginName;
	}

	public void setHandleLoginName(String handleLoginName) {
		this.handleLoginName = handleLoginName;
	}
}
