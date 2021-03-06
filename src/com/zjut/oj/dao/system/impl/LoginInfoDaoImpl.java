package com.zjut.oj.dao.system.impl;

import org.springframework.stereotype.Repository;

import com.zjut.oj.common.DaoSupport;
import com.zjut.oj.dao.system.LoginInfoDao;
import com.zjut.oj.entity.system.UserInfoBean;

@Repository("loginInfoDao")
public class LoginInfoDaoImpl extends DaoSupport<UserInfoBean> implements LoginInfoDao
{
}
