package com.zjut.oj.entity.match;

import java.io.Serializable;
import com.zjut.oj.common.BaseEntity;
import com.zjut.oj.entity.study.ProblemInfoBean;

public class ContestProblemBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private int id ;

    private int contestId ;

    private ProblemInfoBean problemInfoBean;

    public void setId(int id){
        this.id = id ;
    }

    public int getId(){
        return this.id ;
    }

    public void setContestId(int contestId){
        this.contestId = contestId ;
    }

    public int getContestId(){
        return this.contestId ;
    }

	public ProblemInfoBean getProblemInfoBean() {
		return problemInfoBean;
	}

	public void setProblemInfoBean(ProblemInfoBean problemInfoBean) {
		this.problemInfoBean = problemInfoBean;
	}
}