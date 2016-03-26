package com.zjut.oj.entity.system;

import java.io.File;
import java.io.Serializable;

import com.zjut.oj.common.BaseEntity;

public class MessageAttachBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private int attachId ;

	private int messageId ;

    private int attachType ;

    private java.lang.String attachName ;

    private java.lang.String attachUrl ;
    
    private File file;

    public int getAttachId() {
		return attachId;
	}

	public void setAttachId(int attachId) {
		this.attachId = attachId;
	}

	public void setMessageId(int messageId){
        this.messageId = messageId ;
    }

    public int getMessageId(){
        return this.messageId ;
    }

    public void setAttachType(int attachType){
        this.attachType = attachType ;
    }

    public int getAttachType(){
        return this.attachType ;
    }

    public void setAttachName(java.lang.String attachName){
        this.attachName = attachName ;
    }

    public java.lang.String getAttachName(){
        return this.attachName ;
    }

    public void setAttachUrl(java.lang.String attachUrl){
        this.attachUrl = attachUrl ;
    }

    public java.lang.String getAttachUrl(){
        return this.attachUrl ;
    }

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}