package com.zjut.oj.entity.study;

import java.io.Serializable;

import com.zjut.oj.common.BaseEntity;

public class ProblemContentBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private int problemId ;

    private java.lang.String problemDesc ;

    private java.lang.String problemInput ;

    private java.lang.String problemOutput ;

    private java.lang.String exampleInput ;

    private java.lang.String exampleOutput ;
    
    public void setProblemId(int problemId){
        this.problemId = problemId ;
    }

    public int getProblemId(){
        return this.problemId ;
    }

    public void setProblemDesc(java.lang.String problemDesc){
        this.problemDesc = problemDesc ;
    }

    public java.lang.String getProblemDesc(){
        return this.problemDesc ;
    }

    public void setProblemInput(java.lang.String problemInput){
        this.problemInput = problemInput ;
    }

    public java.lang.String getProblemInput(){
        return this.problemInput ;
    }

    public void setProblemOutput(java.lang.String problemOutput){
        this.problemOutput = problemOutput ;
    }

    public java.lang.String getProblemOutput(){
        return this.problemOutput ;
    }

    public void setExampleInput(java.lang.String exampleInput){
        this.exampleInput = exampleInput ;
    }

    public java.lang.String getExampleInput(){
        return this.exampleInput ;
    }

    public void setExampleOutput(java.lang.String exampleOutput){
        this.exampleOutput = exampleOutput ;
    }

    public java.lang.String getExampleOutput(){
        return this.exampleOutput ;
    }
}