package com.zjut.oj.dao.match.impl;

import org.springframework.stereotype.Repository;

import com.zjut.oj.entity.match.ContestInfoBean;
import com.zjut.oj.dao.match.ContestInfoDao;
import com.zjut.oj.common.DaoSupport;

@Repository("contestInfoDao")
public class ContestInfoDaoImpl extends DaoSupport<ContestInfoBean> implements ContestInfoDao
{
}
