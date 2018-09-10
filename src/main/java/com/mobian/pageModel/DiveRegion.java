package com.mobian.pageModel;


public class DiveRegion implements java.io.Serializable {

	private static final long serialVersionUID = 5454155825314635342L;

	private java.lang.Integer id;	
	private java.lang.Integer regionLevel;	
	private java.lang.String regionNameZh;	
	private java.lang.String regionNameEn;	
	private java.lang.String regionParentId;	
	private java.lang.String regionId;	

	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	public java.lang.Integer getId() {
		return this.id;
	}

	
	public void setRegionLevel(java.lang.Integer regionLevel) {
		this.regionLevel = regionLevel;
	}
	
	public java.lang.Integer getRegionLevel() {
		return this.regionLevel;
	}
	public void setRegionNameZh(java.lang.String regionNameZh) {
		this.regionNameZh = regionNameZh;
	}
	
	public java.lang.String getRegionNameZh() {
		return this.regionNameZh;
	}
	public void setRegionNameEn(java.lang.String regionNameEn) {
		this.regionNameEn = regionNameEn;
	}
	
	public java.lang.String getRegionNameEn() {
		return this.regionNameEn;
	}
	public void setRegionParentId(java.lang.String regionParentId) {
		this.regionParentId = regionParentId;
	}
	
	public java.lang.String getRegionParentId() {
		return this.regionParentId;
	}
	public void setRegionId(java.lang.String regionId) {
		this.regionId = regionId;
	}
	
	public java.lang.String getRegionId() {
		return this.regionId;
	}

}
