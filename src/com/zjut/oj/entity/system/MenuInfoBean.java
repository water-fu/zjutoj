package com.zjut.oj.entity.system;

import java.io.Serializable;
import com.zjut.oj.common.BaseEntity;

public class MenuInfoBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private int menuId ;

    private int parentId ;

    private java.lang.String menuName ;
    
    private java.lang.String menuSign;

    private java.lang.String menuUrl ;

    private java.lang.String menuDesc ;

    public void setMenuId(int menuId){
        this.menuId = menuId ;
    }

    public int getMenuId(){
        return this.menuId ;
    }

    public void setParentId(int parentId){
        this.parentId = parentId ;
    }

    public int getParentId(){
        return this.parentId ;
    }

    public void setMenuName(java.lang.String menuName){
        this.menuName = menuName ;
    }

    public java.lang.String getMenuName(){
        return this.menuName ;
    }

    public java.lang.String getMenuSign() {
		return menuSign;
	}

	public void setMenuSign(java.lang.String menuSign) {
		this.menuSign = menuSign;
	}

	public void setMenuUrl(java.lang.String menuUrl){
        this.menuUrl = menuUrl ;
    }

    public java.lang.String getMenuUrl(){
        return this.menuUrl ;
    }

    public void setMenuDesc(java.lang.String menuDesc){
        this.menuDesc = menuDesc ;
    }

    public java.lang.String getMenuDesc(){
        return this.menuDesc ;
    }
}