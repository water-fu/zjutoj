package com.zjut.oj.entity.study;

import java.io.File;
import java.io.Serializable;

import com.zjut.oj.common.BaseEntity;

public class ProblemAttachBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private int attachId ;

    private int problemId ;

    private java.lang.String attachName ;

    private java.lang.String attachUrl ;
    
    private File attachFile;
    
    public void setAttachId(int attachId){
        this.attachId = attachId ;
    }

    public int getAttachId(){
        return this.attachId ;
    }

    public void setProblemId(int problemId){
        this.problemId = problemId ;
    }

    public int getProblemId(){
        return this.problemId ;
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

	public File getAttachFile() {
		return attachFile;
	}

	public void setAttachFile(File attachFile) {
		this.attachFile = attachFile;
	}
}