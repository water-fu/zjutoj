package com.zjut.oj.entity.match;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.zjut.oj.common.BaseEntity;

public class ContestInfoBean extends BaseEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    private int contestId ;

    private java.lang.String contestTitle ;

    private java.util.Date startTime ;

    private java.util.Date endTime ;

    private int contestLevel ;

    private int contestStatus ;

    private java.lang.String contestDesc ;

    private Set<ContestProblemBean> problemInfos;

    private Set<ContestUserBean> userInfos;

    private Set<LangInfoBean> langInfos;

    public String getLanguageInfo() {
        StringBuffer sb = new StringBuffer();
        if(langInfos != null) {
            for (LangInfoBean langInfoBean : langInfos) {
                sb.append(langInfoBean.getLanguageName()).append(",");
            }
        }
        return sb.toString();
    }

    public void setContestId(int contestId){
        this.contestId = contestId ;
    }

    public int getContestId(){
        return this.contestId ;
    }

    public void setContestTitle(java.lang.String contestTitle){
        this.contestTitle = contestTitle ;
    }

    public java.lang.String getContestTitle(){
        return this.contestTitle ;
    }

    public void setStartTime(java.util.Date startTime){
        this.startTime = startTime ;
    }

    public java.util.Date getStartTime(){
        return this.startTime ;
    }

    public void setEndTime(java.util.Date endTime){
        this.endTime = endTime ;
    }

    public java.util.Date getEndTime(){
        return this.endTime ;
    }

    public void setContestLevel(int contestLevel){
        this.contestLevel = contestLevel ;
    }

    public int getContestLevel(){
        return this.contestLevel ;
    }

    public void setContestStatus(int contestStatus){
        this.contestStatus = contestStatus ;
    }

    public int getContestStatus(){
        return this.contestStatus ;
    }

    public void setContestDesc(java.lang.String contestDesc){
        this.contestDesc = contestDesc ;
    }

    public java.lang.String getContestDesc(){
        return this.contestDesc ;
    }

    public Set<ContestProblemBean> getProblemInfos() {
        return problemInfos;
    }

    public void setProblemInfos(Set<ContestProblemBean> problemInfos) {
        this.problemInfos = problemInfos;
    }

    public Set<ContestUserBean> getUserInfos() {
        return userInfos;
    }

    public void setUserInfos(Set<ContestUserBean> userInfos) {
        this.userInfos = userInfos;
    }

    public Set<LangInfoBean> getLangInfos() {
        return langInfos;
    }

    public void setLangInfos(Set<LangInfoBean> langInfos) {
        this.langInfos = langInfos;
    }

    public String getStatusDesc() {
        String statusDesc = new String();
        Date currentDate = new Date();
        //开始时间 < 当前时间 	未开始
        if(startTime.getTime() > currentDate.getTime()) {
            statusDesc = "未开始";
        }
        //开始时间 >= 当前时间	已开始
        else if(startTime.getTime() <= currentDate.getTime()) {
            statusDesc = "进行中";
        }
        //结束时间 >= 当前时间	已结束
        if(endTime.getTime() <= currentDate.getTime()) {
            statusDesc = "已结束";
        }

        return statusDesc;
    }
}