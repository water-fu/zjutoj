package com.zjut.oj.entity.system;

import java.io.Serializable;
import com.zjut.oj.common.BaseEntity;

public class MailInfoBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private int mailId ;

    private java.lang.String sendAccount ;

    private java.lang.String acceptAccount ;

    private java.lang.String copyAccount ;

    private java.lang.String mailTitle ;

    private java.lang.String mailContent ;

    private HustojUserBean hustojUserBean;

    private int sendState ;

    public void setMailId(int mailId){
        this.mailId = mailId ;
    }

    public int getMailId(){
        return this.mailId ;
    }

    public void setSendAccount(java.lang.String sendAccount){
        this.sendAccount = sendAccount ;
    }

    public java.lang.String getSendAccount(){
        return this.sendAccount ;
    }

    public void setAcceptAccount(java.lang.String acceptAccount){
        this.acceptAccount = acceptAccount ;
    }

    public java.lang.String getAcceptAccount(){
        return this.acceptAccount ;
    }

    public void setCopyAccount(java.lang.String copyAccount){
        this.copyAccount = copyAccount ;
    }

    public java.lang.String getCopyAccount(){
        return this.copyAccount ;
    }

    public void setMailTitle(java.lang.String mailTitle){
        this.mailTitle = mailTitle ;
    }

    public java.lang.String getMailTitle(){
        return this.mailTitle ;
    }

    public void setMailContent(java.lang.String mailContent){
        this.mailContent = mailContent ;
    }

    public java.lang.String getMailContent(){
        return this.mailContent ;
    }

    public HustojUserBean getHustojUserBean() {
		return hustojUserBean;
	}

	public void setHustojUserBean(HustojUserBean hustojUserBean) {
		this.hustojUserBean = hustojUserBean;
	}

	public void setSendState(int sendState){
        this.sendState = sendState ;
    }

    public int getSendState(){
        return this.sendState ;
    }
}