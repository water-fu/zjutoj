package com.zjut.oj.entity.system;

import java.io.Serializable;
import com.zjut.oj.common.BaseEntity;

public class MailTempBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private int tempId ;

    private java.lang.String tempTitle ;

    private java.lang.String tempContent ;

    private int tempType ;

    public void setTempId(int tempId){
        this.tempId = tempId ;
    }

    public int getTempId(){
        return this.tempId ;
    }

    public void setTempTitle(java.lang.String tempTitle){
        this.tempTitle = tempTitle ;
    }

    public java.lang.String getTempTitle(){
        return this.tempTitle ;
    }

    public void setTempContent(java.lang.String tempContent){
        this.tempContent = tempContent ;
    }

    public java.lang.String getTempContent(){
        return this.tempContent ;
    }

    public void setTempType(int tempType){
        this.tempType = tempType ;
    }

    public int getTempType(){
        return this.tempType ;
    }
}