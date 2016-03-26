package com.zjut.oj.entity.system;

import java.io.Serializable;
import com.zjut.oj.common.BaseEntity;

public class RolePrivBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int id;
	
    private int roleId ;

    private MenuInfoBean menuInfoBean;

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRoleId(int roleId){
        this.roleId = roleId ;
    }

    public int getRoleId(){
        return this.roleId ;
    }

	public MenuInfoBean getMenuInfoBean() {
		return menuInfoBean;
	}

	public void setMenuInfoBean(MenuInfoBean menuInfoBean) {
		this.menuInfoBean = menuInfoBean;
	}
}