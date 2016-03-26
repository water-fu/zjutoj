package com.zjut.oj.entity.system;

import java.io.Serializable;
import java.util.Set;

import com.zjut.oj.common.BaseEntity;

public class RoleInfoBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private int roleId ;

    private java.lang.String roleName ;

    private java.lang.String roleDesc ;
    
    private Set<RolePrivBean> rolePrivs;

    public void setRoleId(int roleId){
        this.roleId = roleId ;
    }

    public int getRoleId(){
        return this.roleId ;
    }

    public void setRoleName(java.lang.String roleName){
        this.roleName = roleName ;
    }

    public java.lang.String getRoleName(){
        return this.roleName ;
    }

    public void setRoleDesc(java.lang.String roleDesc){
        this.roleDesc = roleDesc ;
    }

    public java.lang.String getRoleDesc(){
        return this.roleDesc ;
    }

	public Set<RolePrivBean> getRolePrivs() {
		return rolePrivs;
	}

	public void setRolePrivs(Set<RolePrivBean> rolePrivs) {
		this.rolePrivs = rolePrivs;
	}
}