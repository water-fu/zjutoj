package com.zjut.oj.entity.study;

import java.io.Serializable;
import java.util.Set;

import com.zjut.oj.common.BaseEntity;
import com.zjut.oj.entity.system.UserInfoBean;

public class ProblemInfoBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private int problemId ;

    private java.lang.String problemTitle ;

    private int problemContent ;

    private int isShow ;
    
    private int timeLimit;
    
    private int memLimit;

    private java.lang.String problemLevel ;

    private java.lang.String problemType ;

    private UserInfoBean problemUserBean;
    
    private ProblemContentBean problemContentBean;
    
    private Set<ProblemAttachBean> problemAttachs;
    
    public ProblemInfoBean() {
    	this.isShow = -1;
    }
    
    public void setProblemId(int problemId){
        this.problemId = problemId ;
    }

    public int getProblemId(){
        return this.problemId ;
    }

    public void setProblemTitle(java.lang.String problemTitle){
        this.problemTitle = problemTitle ;
    }

    public java.lang.String getProblemTitle(){
        return this.problemTitle ;
    }

    public void setProblemContent(int problemContent){
        this.problemContent = problemContent ;
    }

    public int getProblemContent(){
        return this.problemContent ;
    }

    public void setIsShow(int isShow){
        this.isShow = isShow ;
    }

    public int getIsShow(){
        return this.isShow ;
    }

    public int getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit) {
		this.timeLimit = timeLimit;
	}

	public int getMemLimit() {
		return memLimit;
	}

	public void setMemLimit(int memLimit) {
		this.memLimit = memLimit;
	}

	public void setProblemLevel(java.lang.String problemLevel){
        this.problemLevel = problemLevel ;
    }

    public java.lang.String getProblemLevel(){
        return this.problemLevel ;
    }

	public java.lang.String getProblemType() {
		return problemType;
	}

	public void setProblemType(java.lang.String problemType) {
		this.problemType = problemType;
	}

	public UserInfoBean getProblemUserBean() {
		return problemUserBean;
	}

	public void setProblemUserBean(UserInfoBean problemUserBean) {
		this.problemUserBean = problemUserBean;
	}

	public ProblemContentBean getProblemContentBean() {
		return problemContentBean;
	}

	public void setProblemContentBean(ProblemContentBean problemContentBean) {
		this.problemContentBean = problemContentBean;
	}

	public Set<ProblemAttachBean> getProblemAttachs() {
		return problemAttachs;
	}

	public void setProblemAttachs(Set<ProblemAttachBean> problemAttachs) {
		this.problemAttachs = problemAttachs;
	}
}