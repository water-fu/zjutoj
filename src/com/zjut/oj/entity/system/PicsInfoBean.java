package com.zjut.oj.entity.system;

import java.io.File;
import java.io.Serializable;

import com.zjut.oj.common.BaseEntity;

public class PicsInfoBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private int picId ;

    private java.lang.String picTitle ;

    private java.lang.String picUrl ;
    
    private java.lang.String linkUrl ;
    
    private File file;

    public void setPicId(int picId){
        this.picId = picId ;
    }

    public int getPicId(){
        return this.picId ;
    }

    public void setPicTitle(java.lang.String picTitle){
        this.picTitle = picTitle ;
    }

    public java.lang.String getPicTitle(){
        return this.picTitle ;
    }

    public void setPicUrl(java.lang.String picUrl){
        this.picUrl = picUrl ;
    }

    public java.lang.String getPicUrl(){
        return this.picUrl ;
    }

	public java.lang.String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(java.lang.String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
}