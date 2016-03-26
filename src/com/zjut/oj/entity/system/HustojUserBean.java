package com.zjut.oj.entity.system;

import java.io.Serializable;

import com.zjut.oj.common.BaseEntity;

public class HustojUserBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private int userId ;

    private int matchId ;

    private java.lang.String userNo ;

    private java.lang.String userName ;

    private java.lang.String userClass ;

    private java.lang.String userSchool ;

    private java.lang.String userMail ;

    private java.lang.String userTel ;
    
    private java.lang.String matchAccount;
    
    private java.lang.String matchPassword;

    private java.lang.String userExp;
    
    public void setUserId(int userId){
        this.userId = userId ;
    }

    public int getUserId(){
        return this.userId ;
    }

    public void setMatchId(int matchId){
        this.matchId = matchId ;
    }

    public int getMatchId(){
        return this.matchId ;
    }

    public void setUserNo(java.lang.String userNo){
        this.userNo = userNo ;
    }

    public java.lang.String getUserNo(){
        return this.userNo ;
    }

    public void setUserName(java.lang.String userName){
        this.userName = userName ;
    }

    public java.lang.String getUserName(){
        return this.userName ;
    }

    public void setUserClass(java.lang.String userClass){
        this.userClass = userClass ;
    }

    public java.lang.String getUserClass(){
        return this.userClass ;
    }

    public void setUserSchool(java.lang.String userSchool){
        this.userSchool = userSchool ;
    }

    public java.lang.String getUserSchool(){
        return this.userSchool ;
    }

    public void setUserMail(java.lang.String userMail){
        this.userMail = userMail ;
    }

    public java.lang.String getUserMail(){
        return this.userMail ;
    }

    public void setUserTel(java.lang.String userTel){
        this.userTel = userTel ;
    }

    public java.lang.String getUserTel(){
        return this.userTel ;
    }

	public java.lang.String getMatchAccount() {
		return matchAccount;
	}

	public void setMatchAccount(java.lang.String matchAccount) {
		this.matchAccount = matchAccount;
	}

	public java.lang.String getMatchPassword() {
		return matchPassword;
	}

	public void setMatchPassword(java.lang.String matchPassword) {
		this.matchPassword = matchPassword;
	}

	public java.lang.String getUserExp() {
		return userExp;
	}

	public void setUserExp(java.lang.String userExp) {
		this.userExp = userExp;
	}
}