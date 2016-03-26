package com.zjut.oj.service.system;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjut.oj.entity.system.UserInfoBean;
import com.zjut.oj.util.ApplicationException;

@Service
@Transactional
public interface LoginInfoService
{
	UserInfoBean login(UserInfoBean userInfoBean) throws ApplicationException;
    
    void loginOut() throws ApplicationException;
}