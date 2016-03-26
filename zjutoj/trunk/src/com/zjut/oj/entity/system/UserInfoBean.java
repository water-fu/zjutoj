package com.zjut.oj.entity.system;

import java.io.Serializable;
import java.util.Set;

public class UserInfoBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private int userId ;

    private int userType ;

    private java.lang.String loginSign ;

    private java.lang.String loginPass ;

    private java.lang.String userMail ;

    private java.lang.String userIden ;

    private java.lang.String userTel ;

    private java.lang.String userName ;

    private java.lang.String userMemo ;

    private int operUser ;

    private java.util.Date operDate ;

    private Set<UserRoleBean> userRoles;

    public void setUserId(int userId){
        this.userId = userId ;
    }

    public int getUserId(){
        return this.userId ;
    }

    public void setUserType(int userType){
        this.userType = userType ;
    }

    public int getUserType(){
        return this.userType ;
    }

    public void setLoginSign(java.lang.String loginSign){
        this.loginSign = loginSign ;
    }

    public java.lang.String getLoginSign(){
        return this.loginSign ;
    }

    public void setLoginPass(java.lang.String loginPass){
        this.loginPass = loginPass ;
    }

    public java.lang.String getLoginPass(){
        return this.loginPass ;
    }

    public void setUserMail(java.lang.String userMail){
        this.userMail = userMail ;
    }

    public java.lang.String getUserMail(){
        return this.userMail ;
    }

    public void setUserIden(java.lang.String userIden){
        this.userIden = userIden ;
    }

    public java.lang.String getUserIden(){
        return this.userIden ;
    }

    public void setUserTel(java.lang.String userTel){
        this.userTel = userTel ;
    }

    public java.lang.String getUserTel(){
        return this.userTel ;
    }

    public void setUserName(java.lang.String userName){
        this.userName = userName ;
    }

    public java.lang.String getUserName(){
        return this.userName ;
    }

    public void setUserMemo(java.lang.String userMemo){
        this.userMemo = userMemo ;
    }

    public java.lang.String getUserMemo(){
        return this.userMemo ;
    }

    public void setOperUser(int operUser){
        this.operUser = operUser ;
    }

    public int getOperUser(){
        return this.operUser ;
    }

    public void setOperDate(java.util.Date operDate){
        this.operDate = operDate ;
    }

    public java.util.Date getOperDate(){
        return this.operDate ;
    }

	public Set<UserRoleBean> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRoleBean> userRoles) {
		this.userRoles = userRoles;
	}
}