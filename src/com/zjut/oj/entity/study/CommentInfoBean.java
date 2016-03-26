package com.zjut.oj.entity.study;

import java.io.Serializable;
import java.util.Set;

import com.zjut.oj.common.BaseEntity;

public class CommentInfoBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private int commentId;

    private Integer parentId;

    private java.lang.String commentDesc;
    
    private Set<CommentInfoBean> childCommentInfo;
    
    private ProblemInfoBean problemInfoBean;
    
    public CommentInfoBean() {
    	this.problemInfoBean = new ProblemInfoBean();
    }

    public void setCommentId(int commentId){
        this.commentId = commentId ;
    }

    public int getCommentId(){
        return this.commentId ;
    }

    public void setParentId(Integer parentId){
        this.parentId = parentId;
    }

    public Integer getParentId(){
        return this.parentId ;
    }
    
    public void setCommentDesc(java.lang.String commentDesc){
        this.commentDesc = commentDesc ;
    }

    public java.lang.String getCommentDesc(){
        return this.commentDesc ;
    }

	public Set<CommentInfoBean> getChildCommentInfo() {
		return childCommentInfo;
	}

	public void setChildCommentInfo(Set<CommentInfoBean> childCommentInfo) {
		this.childCommentInfo = childCommentInfo;
	}

	public ProblemInfoBean getProblemInfoBean() {
		return problemInfoBean;
	}

	public void setProblemInfoBean(ProblemInfoBean problemInfoBean) {
		this.problemInfoBean = problemInfoBean;
	}
}