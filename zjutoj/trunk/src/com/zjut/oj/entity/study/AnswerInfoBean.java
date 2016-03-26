package com.zjut.oj.entity.study;

import java.io.Serializable;

import com.zjut.oj.common.BaseEntity;

public class AnswerInfoBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private int answerId ;

    private java.lang.String answerDesc ;

    private int isShow ;
    
    private ProblemInfoBean problemInfoBean;

    public void setAnswerId(int answerId){
        this.answerId = answerId ;
    }

    public int getAnswerId(){
        return this.answerId ;
    }

    public void setAnswerDesc(java.lang.String answerDesc){
        this.answerDesc = answerDesc ;
    }

    public java.lang.String getAnswerDesc(){
        return this.answerDesc ;
    }

    public void setIsShow(int isShow){
        this.isShow = isShow ;
    }

    public int getIsShow(){
        return this.isShow ;
    }

	public ProblemInfoBean getProblemInfoBean() {
		return problemInfoBean;
	}

	public void setProblemInfoBean(ProblemInfoBean problemInfoBean) {
		this.problemInfoBean = problemInfoBean;
	}
}