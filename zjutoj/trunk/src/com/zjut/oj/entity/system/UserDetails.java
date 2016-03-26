package com.zjut.oj.entity.system;

import java.util.List;

public class UserDetails {

	private UserInfoBean userInfoBean;
	
	private List<String> userPrivList;
	
	public UserInfoBean getUser() {
		return this.userInfoBean;
	}
	
	public void setUser(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}

	public List<String> getUserPrivList() {
		return userPrivList;
	}

	public void setUserPrivList(List<String> userPrivList) {
		this.userPrivList = userPrivList;
	}
}
