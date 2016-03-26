package com.zjut.oj.entity.system;

import java.io.Serializable;
import com.zjut.oj.common.BaseEntity;

public class HustojInfoBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private int matchId ;

    private java.lang.String matchTitle ;

    private java.util.Date signFrom ;

    private java.util.Date signEnd ;

    private java.util.Date matchDate ;

    private int operUser ;

    private java.util.Date operDate ;


    public void setMatchId(int matchId){
        this.matchId = matchId ;
    }

    public int getMatchId(){
        return this.matchId ;
    }

    public void setMatchTitle(java.lang.String matchTitle){
        this.matchTitle = matchTitle ;
    }

    public java.lang.String getMatchTitle(){
        return this.matchTitle ;
    }

    public void setSignFrom(java.util.Date signFrom){
        this.signFrom = signFrom ;
    }

    public java.util.Date getSignFrom(){
        return this.signFrom ;
    }

    public void setSignEnd(java.util.Date signEnd){
        this.signEnd = signEnd ;
    }

    public java.util.Date getSignEnd(){
        return this.signEnd ;
    }

    public void setMatchDate(java.util.Date matchDate){
        this.matchDate = matchDate ;
    }

    public java.util.Date getMatchDate(){
        return this.matchDate ;
    }

    public void setOperUser(int operUser){
        this.operUser = operUser ;
    }

    public int getOperUser(){
        return this.operUser ;
    }

    public void setOperDate(java.util.Date operDate){
        this.operDate = operDate ;
    }

    public java.util.Date getOperDate(){
        return this.operDate ;
    }

}