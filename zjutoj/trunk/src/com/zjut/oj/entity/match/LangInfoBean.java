package com.zjut.oj.entity.match;

import java.io.Serializable;
import com.zjut.oj.common.BaseEntity;

public class LangInfoBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private int languageId ;

    private int contestId ;

    private java.lang.String languageName ;

    public void setLanguageId(int languageId){
        this.languageId = languageId ;
    }

    public int getLanguageId(){
        return this.languageId ;
    }

    public void setContestId(int contestId){
        this.contestId = contestId ;
    }

    public int getContestId(){
        return this.contestId ;
    }

    public void setLanguageName(java.lang.String languageName){
        this.languageName = languageName ;
    }

    public java.lang.String getLanguageName(){
        return this.languageName ;
    }
}