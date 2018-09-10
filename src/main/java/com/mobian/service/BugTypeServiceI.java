package com.mobian.service;

import java.util.List;

import com.mobian.model.Tbugtype;

/**
 * 
 * @author John
 * 
 */
public interface BugTypeServiceI {

	/**
	 * 获得BUG类型列表
	 * 
	 * @return
	 */
	public List<Tbugtype> getBugTypeList();

}
