package com.zjut.oj.service.system;

import com.zjut.oj.entity.system.UserInfoBean;
import com.zjut.oj.util.ApplicationException;
import com.zjut.oj.util.PageQueryResult;

public interface UserInfoService
{
    int insert(UserInfoBean userInfoBean) throws ApplicationException;
    
    void delete(UserInfoBean userInfoBean) throws ApplicationException;
    
    void update(UserInfoBean userInfoBean) throws ApplicationException;
    
    UserInfoBean findById(long id) throws ApplicationException;
    
    PageQueryResult queryForPage(UserInfoBean userInfoBean, PageQueryResult pageQueryResult) throws ApplicationException;
}