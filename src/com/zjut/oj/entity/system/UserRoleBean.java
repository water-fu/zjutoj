package com.zjut.oj.entity.system;

import java.io.Serializable;
import com.zjut.oj.common.BaseEntity;

public class UserRoleBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	
    private int userId ;
    
    private RoleInfoBean roleInfoBean;

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public RoleInfoBean getRoleInfoBean() {
		return roleInfoBean;
	}

	public void setRoleInfoBean(RoleInfoBean roleInfoBean) {
		this.roleInfoBean = roleInfoBean;
	}
}