package com.zjut.oj.dao.system.impl;

import org.springframework.stereotype.Repository;

import com.zjut.oj.common.DaoSupport;
import com.zjut.oj.dao.system.UserInfoDao;
import com.zjut.oj.entity.system.UserInfoBean;

@Repository("userInfoDao")
public class UserInfoDaoImpl extends DaoSupport<UserInfoBean> implements UserInfoDao
{
}
