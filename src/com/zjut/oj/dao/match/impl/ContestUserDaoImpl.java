package com.zjut.oj.dao.match.impl;

import org.springframework.stereotype.Repository;

import com.zjut.oj.entity.match.ContestUserBean;
import com.zjut.oj.dao.match.ContestUserDao;
import com.zjut.oj.common.DaoSupport;

@Repository("contestUserDao")
public class ContestUserDaoImpl extends DaoSupport<ContestUserBean> implements ContestUserDao
{
}
