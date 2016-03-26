package com.zjut.oj.common;

import java.io.Serializable;

import com.zjut.oj.entity.system.UserInfoBean;

public class BaseEntity implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private java.util.Date operDate ;

	private UserInfoBean userInfoBean;

	public java.util.Date getOperDate() {
		return operDate;
	}

	public void setOperDate(java.util.Date operDate) {
		this.operDate = operDate;
	}

	public UserInfoBean getUserInfoBean() {
		return userInfoBean;
	}

	public void setUserInfoBean(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}


}
