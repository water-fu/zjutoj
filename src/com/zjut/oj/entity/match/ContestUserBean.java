package com.zjut.oj.entity.match;

import java.io.Serializable;
import com.zjut.oj.common.BaseEntity;
import com.zjut.oj.entity.system.UserInfoBean;

public class ContestUserBean extends BaseEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
    private int id ;

    private int contestId ;
    
    private UserInfoBean userInfo;
    
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

	public UserInfoBean getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(UserInfoBean userInfo) {
		this.userInfo = userInfo;
	}
}